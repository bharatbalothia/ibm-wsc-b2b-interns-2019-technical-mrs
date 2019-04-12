package com.ibm.mapping.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

/**
 * @author Sanket
 *
 */

public final class codelistMerger {
	//log variable 
	static Logger log = Logger.getLogger(codelistMerger.class.getName());
	
	public static void generate_MergeCodeList(String inputDirectory,
  			String outputDirectory)
      {
		String outputFileName1 = null,outputFileName2 = null;
		File oldXML = null,newXLS = null;
  		File inputDir = new File(inputDirectory);

  		// Check if output directory exists.
  		File outputDir = new File(outputDirectory);

  		if (!outputDir.exists()) {
  			// Create a output directory.
  			outputDir.mkdirs();
  		}
  		FileUtils.cleanDirectory(outputDir);
  		// Check if input directory exists.
  		if (inputDir.exists()) {

  			File[] listOfFiles = inputDir.listFiles();

  			for (int i = 0; i < listOfFiles.length; i++) {

  				if (listOfFiles[i].isFile()) {
  					
  					String inputFileName = listOfFiles[i].getName();
  					
  					if(inputFileName.endsWith(".xls"))
  					{
  						newXLS = listOfFiles[i];
  						outputFileName2 = inputFileName.replace(".xls", "");
  						//converts xls to xml
  						//CodeListCreator.codeListToXML(listOfFiles[i],outputDirectory,outputFileName2);
  					}
  					else
  					if (inputFileName.endsWith(".xml"))
  					{
  						oldXML = listOfFiles[i];
  						outputFileName1 = inputFileName.replace(".xml", "");
  						//add xml to xls functionality
  						//CodeListCreator.xmltoXls(listOfFiles[i], outputDirectory, outputFileName1);
  					}
  					else
  					{
  						log.error("The service only accepts xml or xls file only.");
  					}
  					
  				}
  			}
  			
  			//System.out.println("oldXML=>"+oldXML.getAbsolutePath());
  			//System.out.println("newXLS=>"+newXLS.getAbsolutePath());
  			try {
  			if(outputFileName1.length()>0 && outputFileName2.length()>0 && oldXML.exists() && newXLS.exists() ) {
  			File mergeFile = null;
  			//System.out.println("1");
  			CodeListCreator.xmltoXls(oldXML, outputDirectory, outputFileName1+"_merge");
  			mergeFile = new File(outputDirectory+outputFileName1+"_merge.xls");
  			
  			if(mergeFile.exists()) {
  				
  				try {
  	  				//System.out.println("\n2");
  					if(mergeXLS(outputDirectory+outputFileName1+"_merge.xls",newXLS.getAbsolutePath())) {
  					CodeListCreator.codeListToXML(mergeFile,outputDirectory,mergeFile.getName().replace(".xls", ""));
  					}
  				} catch (Exception e) {
  					e.printStackTrace();
  				}
 
  			mergeFile.delete();
  			}
  				
  			}
  		 }catch(NullPointerException ne) {log.error(ne.getMessage());}
  		}else {
  			/*System.out.println("The input directory '" + inputDirectory
  					+ "' does not exists.");*/
  			log.error("The input directory '" + inputDirectory
  					+ "' does not exists.");
  		}
  	
      }

	public static boolean mergeXLS(String old_xls_path,String new_xls_path) throws Exception {
		boolean isEmptyRows = false;
		boolean isValidHeader = false;
		InputStream oldEntry = new FileInputStream(old_xls_path);
		InputStream newEntry = new FileInputStream(new_xls_path);
		HSSFWorkbook old_wb = new HSSFWorkbook(oldEntry);
		HSSFWorkbook new_wb = new HSSFWorkbook(newEntry);
		HSSFSheet old_sheet=old_wb.getSheetAt(0);
		HSSFSheet new_sheet=new_wb.getSheetAt(0);
				
		for(int i=1;i<old_sheet.getLastRowNum()+1;i++) {
			HSSFRow old_row = old_sheet.getRow(i);
			if(old_row != null) {
				if(isRowEmpty(old_row))
				   isEmptyRows=true;
			}
		}
		
		//if(isEmptyRows)
	    //JOptionPane.showMessageDialog(null,"There are empty rows present in the old excel sheet...please remove and try again!!!");
		
		if(isValidHeader(new_sheet.getRow(0)))
		isValidHeader = true;
		//else
		//JOptionPane.showMessageDialog(null,"Excel sheet header is different...please update and try again!!!");
		
		if(isValidHeader==true && isValidHeader(old_sheet.getRow(0)) && isEmptyRows == false) {
		for(int i=1;i<new_sheet.getLastRowNum()+1;i++) {
		boolean isPresent = false;	
		HSSFRow new_row = new_sheet.getRow(i);
		if(new_row != null) {
			if(containsValue(new_row, new_row.getFirstCellNum(), new_row.getLastCellNum())) {
			   //System.out.println("new_row.getCell(3)=>"+new_row.getCell(3));
				for(int k=1;k<old_sheet.getLastRowNum()+1;k++) {
				HSSFRow old_row = old_sheet.getRow(k);
				
				if(old_row != null) {
					if(containsValue(old_row, old_row.getFirstCellNum(), old_row.getLastCellNum())) {
						
					// IF CodeList Name is same for both
					 if(cellToString(new_row.getCell(2)).equals(cellToString(old_row.getCell(2)))) {   
					  // IF sender id and receiver id are same
			          if(cellToString(new_row.getCell(3)).equals(cellToString(old_row.getCell(3))) && cellToString(new_row.getCell(4)).equals(cellToString(old_row.getCell(4)))) {
			        	  //System.out.println("New Entry is already present in old xls");
			        	  //System.out.println("*****1 "+cellToString(new_row.getCell(3)));
			        	  isPresent = true;
			        	  			        	  
			        	  if(new_row.getLastCellNum()<=old_row.getLastCellNum()) {
			        		//System.out.println("*** 1 ***");  
			        	  for(int c=5;c<new_row.getLastCellNum();c++) {
			        	  try {
			        	  if(!cellToString(new_row.getCell(c)).isEmpty() /*&& old_row.getCell(c).getStringCellValue().length()>0*/) {
			        	  //System.out.println("new_row.getCell("+c+").getStringCellValue()=>"+cellToString(new_row.getCell(c)));
			        	  
			        	  if(cellToString(old_row.getCell(c)).length() > 0) {
			        	  //if(new_row.getLastCellNum()<=old_row.getLastCellNum()) {
			        	  if(!cellToString(new_row.getCell(c)).equalsIgnoreCase(cellToString(old_row.getCell(c))))
				          updateCell_OldXLS(old_xls_path,cellToString(new_row.getCell(c)),k,c);
			        	  }
			        	  else if(cellToString(old_row.getCell(c)).length() == 0)
			        		   addCell_OldXLS(old_xls_path,cellToString(new_row.getCell(c)),k,c);
			        	  
			        	  }
			        	  else {
			        		  if(cellToString(old_row.getCell(c)).length() > 0)
			        		  deleteCell_OldXLS(old_xls_path,k,c);
			        	  }
			        	  }
			        	  catch(NullPointerException ne) {
			        		  log.error(ne.getMessage());
			        	  }
			        	  
			        	  }
			        	  }
			        	  //System.out.println(old_row.getLastCellNum());
			        	  if(new_row.getLastCellNum()>old_row.getLastCellNum()) {
			        	  //System.out.println("*** 2 ***"); 
			        	  try {
			        	  for(int c=5;c<old_row.getLastCellNum();c++) {
			        	  if(!cellToString(new_row.getCell(c)).isEmpty() /*&& old_row.getCell(c).getStringCellValue().length()>0*/) {
			        	  //System.out.println("1 new_row.getCell("+c+").getStringCellValue()=>"+cellToString(new_row.getCell(c)));
			        	  if(!cellToString(new_row.getCell(c)).equalsIgnoreCase(cellToString(old_row.getCell(c))) /*|| old_row.getCell(c).getStringCellValue().length() == 0 */)
				          updateCell_OldXLS(old_xls_path,cellToString(new_row.getCell(c)),k,c);
			        	  }
			        	  else {
			        	  if(cellToString(old_row.getCell(c)).length() > 0)
			        	  deleteCell_OldXLS(old_xls_path,k,c);
			        	  }
			        	  }
			        	  
			        	  for(int c=old_row.getLastCellNum();c<new_row.getLastCellNum();c++) {
			        	  if(!cellToString(new_row.getCell(c)).isEmpty() /*&& old_row.getCell(c).getStringCellValue().length()>0*/) {
			        	  //System.out.println("2 new_row.getCell("+c+").getStringCellValue()=>"+cellToString(new_row.getCell(c)));
			        	 // if(!new_row.getCell(c).getStringCellValue().equalsIgnoreCase(old_row.getCell(c).getStringCellValue()) || old_row.getCell(c).getStringCellValue().length() == 0 )
				          addCell_OldXLS(old_xls_path,cellToString(new_row.getCell(c)),k,c);
			        	  }
			        	  else {
			        	  if(cellToString(old_row.getCell(c)).length() > 0)
			        	  deleteCell_OldXLS(old_xls_path,k,c);
			        	  }
			        	  }
			        	  } catch(NullPointerException ne) {
			        		   log.error(ne.getMessage());
			        	  }
			        	     
			        	  }
			        	  
			              break;
				      }
			          //else 
			          if( ( cellToString(new_row.getCell(3)).equals(cellToString(old_row.getCell(3))) && (cellToString(new_row.getCell(4)).length()==0||cellToString(old_row.getCell(4)).length()==0) )
			           || ( cellToString(new_row.getCell(4)).equals(cellToString(old_row.getCell(4))) && (cellToString(new_row.getCell(3)).length()==0||cellToString(old_row.getCell(4)).length()==0) )) {
			        	  
			        	  //System.out.println("New Entry is already present in old xls with null value");
			        	  //System.out.println("*****2 "+cellToString(new_row.getCell(3)));
			        	  isPresent = true;
			        	  			        	  
			        	  if(new_row.getLastCellNum()<=old_row.getLastCellNum()) {
			        		//System.out.println("*** 1 ***");  
			        	  for(int c=3;c<new_row.getLastCellNum();c++) {
			        	  try {
			        	  if(!cellToString(new_row.getCell(c)).isEmpty() /*&& old_row.getCell(c).getStringCellValue().length()>0*/) {
			        	  //System.out.println("new_row.getCell("+c+").getStringCellValue()=>"+cellToString(new_row.getCell(c)));
			        	  
			        	  if(cellToString(old_row.getCell(c)).length() > 0) {
			        	  //if(new_row.getLastCellNum()<=old_row.getLastCellNum()) {
			        	  if(!cellToString(new_row.getCell(c)).equalsIgnoreCase(cellToString(old_row.getCell(c))))
				          updateCell_OldXLS(old_xls_path,cellToString(new_row.getCell(c)),k,c);
			        	  }
			        	  else if(cellToString(old_row.getCell(c)).length() == 0)
			        		   addCell_OldXLS(old_xls_path,cellToString(new_row.getCell(c)),k,c);
			        	  
			        	  }
			        	  else {
			        		  if(cellToString(old_row.getCell(c)).length() > 0)
			        		  deleteCell_OldXLS(old_xls_path,k,c);
			        	  }
			        	  
			        	  }
			        	  catch(NullPointerException ne) {
			        		  log.error(ne.getMessage());		        		  
			        	  }
			        	  
			        	  }
			        	  }
			        	  //System.out.println(old_row.getLastCellNum());
			        	  if(new_row.getLastCellNum()>old_row.getLastCellNum()) {
			        	  //System.out.println("*** 2 ***"); 
			        	  try {
			        	  for(int c=3;c<old_row.getLastCellNum();c++) {
			        	  if(!cellToString(new_row.getCell(c)).isEmpty() /*&& old_row.getCell(c).getStringCellValue().length()>0*/) {
			        	  //System.out.println("1 new_row.getCell("+c+").getStringCellValue()=>"+cellToString(new_row.getCell(c)));
			        	  if(!cellToString(new_row.getCell(c)).equalsIgnoreCase(cellToString(old_row.getCell(c))) /*|| old_row.getCell(c).getStringCellValue().length() == 0 */)
				          updateCell_OldXLS(old_xls_path,cellToString(new_row.getCell(c)),k,c);
			        	  }
			        	  else {
			        	  if(cellToString(old_row.getCell(c)).length() > 0)
			        	  deleteCell_OldXLS(old_xls_path,k,c);
			        	  }
			        	  }
			        	  
			        	  for(int c=old_row.getLastCellNum();c<new_row.getLastCellNum();c++) {
			        	  if(!cellToString(new_row.getCell(c)).isEmpty() /*&& old_row.getCell(c).getStringCellValue().length()>0*/) {
			        	  //System.out.println("2 new_row.getCell("+c+").getStringCellValue()=>"+cellToString(new_row.getCell(c)));
			        	 // if(!new_row.getCell(c).getStringCellValue().equalsIgnoreCase(old_row.getCell(c).getStringCellValue()) || old_row.getCell(c).getStringCellValue().length() == 0 )
				          addCell_OldXLS(old_xls_path,cellToString(new_row.getCell(c)),k,c);
			        	  }
			        	  else {
			        	  if(cellToString(old_row.getCell(c)).length() > 0)
			        	  deleteCell_OldXLS(old_xls_path,k,c);
			        	  }
			        	  }
			        	  } catch(NullPointerException ne) {
			        		   log.error(ne.getMessage());	
			        	  }
			        	     
			        	  }
			        	  
			              break;
			        	  
			          }
			          
			          /*if(cellToString(new_row.getCell(3)).length()==0 || cellToString(old_row.getCell(3)).length()==0 || cellToString(new_row.getCell(4)).length()==0 || cellToString(old_row.getCell(4)).length()==0)
			          isPresent=true;*/
					 }
				   }
			    }
			  }
				
			  if(!isPresent) {
				  //System.out.println("New Entry was not there in the old xls so it is added in old xls"); 
				  addRow_OldXLS(old_xls_path,new_row);
			  }
			}
		 }
		}
	   }
		old_wb.close();
		new_wb.close();
		

		return isValidHeader;
	
  }
	
	public static boolean isValidHeader(HSSFRow row) {
		boolean isValid = false;
		
		try {
		if(cellToString(row.getCell(0)).equalsIgnoreCase("PARTNER_KEY") && cellToString(row.getCell(1)).equalsIgnoreCase("LIST_TYPE")
		   && cellToString(row.getCell(2)).equalsIgnoreCase("LIST_NAME") && cellToString(row.getCell(3)).equalsIgnoreCase("SENDER_ITEM")
		   && cellToString(row.getCell(4)).equalsIgnoreCase("RECEIVER_ITEM") && cellToString(row.getCell(5)).equalsIgnoreCase("DESCRIPTION")
		   && cellToString(row.getCell(6)).equalsIgnoreCase("TEXT1") && cellToString(row.getCell(7)).equalsIgnoreCase("TEXT2")
		   && cellToString(row.getCell(8)).equalsIgnoreCase("TEXT3") && cellToString(row.getCell(9)).equalsIgnoreCase("TEXT4")
		   && cellToString(row.getCell(10)).equalsIgnoreCase("TEXT5") && cellToString(row.getCell(11)).equalsIgnoreCase("TEXT6")
		   && cellToString(row.getCell(12)).equalsIgnoreCase("TEXT7") && cellToString(row.getCell(13)).equalsIgnoreCase("TEXT8")
		   && cellToString(row.getCell(14)).equalsIgnoreCase("TEXT9"))
		   isValid =true;
		}
	    catch(NullPointerException ne) {
	    log.error(ne.getMessage());
	    }
		
		return isValid;
		
	}
	
	public static void addRow_OldXLS(String path,HSSFRow new_row) throws Exception {
				
		try {
		    FileInputStream file = new FileInputStream(new File(path));

		    HSSFWorkbook workbook = new HSSFWorkbook(file);
		    HSSFSheet sheet = workbook.getSheetAt(0);
		    HSSFRow row = sheet.createRow(sheet.getLastRowNum()+1);
		 
		    for(int i=2;i<new_row.getLastCellNum();i++) {				
				try {
				if(cellToString(new_row.getCell(i)).length() > 0) {
					HSSFCell cell = row.createCell(i);
				    cell.setCellValue(cellToString(new_row.getCell(i)));
				    //System.out.println("ADD ENTRY=> "+cellToString(new_row.getCell(i)));
				}
				}catch (NullPointerException e) {
			     log.error(e.getMessage());
			    }
		    }

		    file.close();

		    FileOutputStream outFile = new FileOutputStream(new File(path));
		    workbook.write(outFile);
		    workbook.close();
		    outFile.close();

		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		} 
	}
	
	public static void updateCell_OldXLS(String path,String new_value,int row_no,int cell_no) {
		
		try {
		    FileInputStream file = new FileInputStream(new File(path));

		    HSSFWorkbook workbook = new HSSFWorkbook(file);
		    HSSFSheet sheet = workbook.getSheetAt(0);
		    HSSFRow i_row = sheet.getRow(row_no);
		    //System.out.println("sheet.getRow(row_no).getCell(cell_no).getStringCellValue().length()="+sheet.getRow(row_no).getCell(cell_no).getStringCellValue().length());
		    //if(!sheet.getRow(row_no).getCell(cell_no).getStringCellValue().equalsIgnoreCase(new_value)|| sheet.getRow(row_no).getCell(cell_no).getStringCellValue().length() == 0) {
		    if(!cellToString(i_row.getCell(cell_no)).equalsIgnoreCase(new_value)|| cellToString(i_row.getCell(cell_no)).length() == 0) {
		       HSSFRow row = sheet.getRow(row_no);
		       HSSFCell myCell;
		       myCell = row.getCell(cell_no);
		       myCell.setCellValue(new_value);
		    }

		    file.close();

		    FileOutputStream outFile = new FileOutputStream(new File(path));
		    workbook.write(outFile);
		    workbook.close();
		    outFile.close();

		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		catch (NullPointerException e) {
			log.error(e.getMessage());
		}
		
	}
	
	public static void addCell_OldXLS(String path,String new_value,int row_no,int cell_no) {
		
		try {
		    FileInputStream file = new FileInputStream(new File(path));

		    HSSFWorkbook workbook = new HSSFWorkbook(file);
		    HSSFSheet sheet = workbook.getSheetAt(0);
		    HSSFRow row = sheet.getRow(row_no);
		    HSSFCell myCell;
		    myCell = row.getCell(cell_no);
		    
		    if(myCell==null)
		    myCell = row.createCell(cell_no);
		    myCell.setCellValue(new_value);

		    file.close();

		    FileOutputStream outFile = new FileOutputStream(new File(path));
		    workbook.write(outFile);
		    workbook.close();
		    outFile.close();

		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		} catch (NullPointerException e) {
			log.error(e.getMessage());
		}

		
	}
	
	public static void deleteCell_OldXLS(String path,int row_no,int cell_no) {
		   //System.out.println("Delete Funct()");
		try {
		    FileInputStream file = new FileInputStream(new File(path));

		    HSSFWorkbook workbook = new HSSFWorkbook(file);
		    HSSFSheet sheet = workbook.getSheetAt(0);
		    HSSFRow row = sheet.getRow(row_no);
		    HSSFCell myCell;
		    myCell = row.getCell(cell_no);
		    
		    if(myCell!=null) {
		    myCell.setCellValue("");
		    myCell.setCellType(Cell.CELL_TYPE_BLANK);
		    }

		    file.close();

		    FileOutputStream outFile = new FileOutputStream(new File(path));
		    workbook.write(outFile);
		    workbook.close();
		    outFile.close();

		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		} catch (NullPointerException e) {
			log.error(e.getMessage());
		}
		
	}
	
	public static boolean containsValue(HSSFRow row, int fcell, int lcell) 
	{
	    boolean flag = false;
	    for (int i = fcell+1; i < lcell; i++) {
	    if ( String.valueOf(row.getCell(i)).length() == 0 || row.getCell(i) == null) { } 
	    else {
	                flag = true;
	        }
	    }
	        return flag;
	}
	
	public static boolean isRowEmpty(HSSFRow row) {
	    for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
	        Cell cell = row.getCell(c);
	        if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
	            return false;
	    }
	    return true;
	}
	
	public static String cellToString (HSSFCell cell) {

		int type;
		Object result;
		result= "";
		if(cell != null) {
		type = cell.getCellType();

		    switch(type) {


		    case 0://numeric value in excel
		        result = cell.getNumericCellValue();
		        double number = (double) result;
		        if(number==(int)number) {
		        result = BigDecimal.valueOf((int)number).toPlainString();	
		        }
		        else
		        result = BigDecimal.valueOf(number).toPlainString();
		        break;
		    case 1: //string value in excel
		        result = cell.getStringCellValue();
		        break;
		    case 2: //boolean value in excel
		        result = cell.getBooleanCellValue();
		        break;
		    default:
		        //throw new RunTimeException("There are not support for this type of cell");
		        }
		}

		return result.toString();
   }
	
}