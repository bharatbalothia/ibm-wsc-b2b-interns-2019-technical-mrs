package com.ibm.mapping.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import com.ibm.mapping.xmltoxls.dto.SIResources;

import com.ibm.mapping.xmltoxls.dto.CodeListXref;
import com.ibm.mapping.xmltoxls.dto.CodeListXrefItem;
import com.ibm.mapping.xmltoxls.dto.CodeListXrefItems;
import com.ibm.mapping.xmltoxls.dto.CodeLists;
import com.ibm.mapping.util.NamespaceFilter;

public final class CodeListCreator {
	//log variable 
	static Logger log = Logger.getLogger(CodeListCreator.class.getName());
	
      public static void generateCodeList(String inputDirectory,
  			String outputDirectory)
      {
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
  					String outputFileName;
  					
  					if(inputFileName.endsWith(".xls"))
  					{
  						outputFileName = inputFileName.replace(".xls", "");
  						//converts xls to xml
  						codeListToXML(listOfFiles[i],outputDirectory,outputFileName);
  					}else if (inputFileName.endsWith(".xml"))
  					{
  						outputFileName = inputFileName.replace(".xml", "");
  						//add xml to xls functionality
  						xmltoXls(listOfFiles[i], outputDirectory, outputFileName);
  					}
  					else
  					{
  						log.error("The service only accepts xml or xls file only.");
  					}
  					
  				}
  			}
  		}else {
  			//System.out.println("The input directory '" + inputDirectory
  			//		+ "' does not exists.");
  			log.error("The input directory '" + inputDirectory
  					+ "' does not exists.");
  		}
  	
      }
      
      public static void xmltoXls(File inputFile, String outputDirectory, String fileName)
      {
         
  		FileOutputStream outputStr = null;
  					int count =1;
  	    try {
  	    	// jaxb object
  	    	JAXBContext jaxbContext = JAXBContext.newInstance(SIResources.class);
  	    	//unmarshaller object
  			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  			//Create an XMLReader to use with our filter
  			XMLReader reader = XMLReaderFactory.createXMLReader();
  			//Create the filter (to add name space) and set the xmlReader as its parent.
  			NamespaceFilter inFilter = new NamespaceFilter("http://www.stercomm.com/SI/SI_IE_Resources", false);
  			inFilter.setParent(reader);

  			//Prepare the input, in this case a java.io.File (output)
  			InputSource is = new InputSource(new FileInputStream(inputFile));

  			//Create a SAXSource specifying the filter
  			SAXSource source = new SAXSource(inFilter, is);
  			SIResources siResource = (SIResources)jaxbUnmarshaller.unmarshal(source);
  			// create xls file and write data
  			outputStr = new FileOutputStream (outputDirectory+fileName+".xls");
  			HSSFWorkbook workbook = new HSSFWorkbook();
  			HSSFSheet worksheet = workbook.createSheet("Codelist Entries");
  	        //set header field
  			setHeader(worksheet);
  			CodeLists codeLists = siResource.getCodeLists();
  			if (codeLists != null)
  			{
  			CodeListXref xref = codeLists.getCodeList();
  			
  			if(xref != null)
  			{
  			CodeListXrefItems items = xref.getItems();
  			
  			for(CodeListXrefItem eachCode : items.getCodeListXrefItem())
  			{
  				if(eachCode!= null)
  				{
  					HSSFRow row = worksheet.createRow(count);
  					//set data in excel sheet row
  					setData(xref, row,eachCode);
  				}
  				
  				
  				count++;
  			}
  			}else{
  				log.error( fileName + " missing required tag");
  			}
  			}else{
  				log.error( fileName + " missing required tag");
  			}
  			
  			workbook.write(outputStr);
  			workbook.close();
  			outputStr.flush();
  			outputStr.close();
  			
  		} catch (JAXBException e) {
  			log.error("Error while processing "+ fileName+ " The error message is "+e.getMessage());
  		} catch (FileNotFoundException e) {
  			log.error("Error while processing "+ fileName+ " The error message is "+e.getMessage());
  		} catch (IOException e) {
  			log.error("Error while processing "+ fileName+ " The error message is "+e.getMessage());
  		} catch (SAXException e) {
  			log.error("Error while processing "+ fileName+ " The error message is "+e.getMessage());
		}
  	     
      }
      
      public static void codeListToXML(File inputFile, String outputDirectory, String fileName)
      {

			POIFSFileSystem fileSystem = null;
			try {

		        fileSystem = new POIFSFileSystem (new FileInputStream(inputFile));
		        HSSFWorkbook     workBook = new HSSFWorkbook (fileSystem);
		        HSSFSheet         sheet    = (HSSFSheet) workBook.getSheetAt (0); 
   
			        int rowNum = sheet.getLastRowNum()+1;   // zero-based function
			        int colNum = sheet.getRow(0).getLastCellNum(); // one-based function
			        int row_no=0;
			        String[][] data = new String[rowNum][colNum];
			        
			        for (int i=0; i<rowNum; i++){
			            HSSFRow row = sheet.getRow(i);
			            if(row != null) {
			              if(isRowEmpty(row)==false) {
			            	
			            	//System.out.println("row_no=>"+row_no);
			                for (int j=0; j<colNum; j++){
			                    HSSFCell cell = row.getCell(j, Row.CREATE_NULL_AS_BLANK);
			                    String value = cellToString(cell);
			                    data[row_no][j] = value;
			                    //System.out.println("The value is " + value +" i= "+row_no + "j= "+j);

			                }
			                row_no=row_no+1;
			               }
			             }
			           }

		        //Initializing the XML document
		        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		        //set name space aware
		        factory.setNamespaceAware(true);
		        DocumentBuilder builder = factory.newDocumentBuilder();
		        Document document = builder.newDocument();
		        
		        Element rootElement = document.createElement("SI_RESOURCES");
		        //set name space
		        //rootElement.setAttributeNS("http://www.stercomm.com/SI/SI_IE_Resources", "xsi", "http://www.w3.org/2001/XMLSchema-instance");
		        rootElement.setAttribute("xmlns", "http://www.stercomm.com/SI/SI_IE_Resources");
		        rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		        
		        //create CODE_LISTS
		        Element CODE_LISTS = document.createElement("CODE_LISTS");
		        Element CODE_LIST_XREF = document.createElement("CODE_LIST_XREF");
		        Element LIST_NAME = document.createElement("LIST_NAME");
		        Element SENDER_ID = document.createElement("SENDER_ID");
		        Element RECEIVER_ID = document.createElement("RECEIVER_ID");
		        Element LIST_VERSION = document.createElement("LIST_VERSION");
		        Element SIResourceDefaultVersion = document.createElement("SIResourceDefaultVersion");
		        Element STATUS = document.createElement("STATUS");
		        Element COMMENTS = document.createElement("COMMENTS");
		        Element USERNAME = document.createElement("USERNAME");
		        Element CREATE_DATE = document.createElement("CREATE_DATE");
		        Element CODE_LIST_XREF_ITEMS = document.createElement("CODE_LIST_XREF_ITEMS");
		        
		        CODE_LIST_XREF.appendChild(LIST_NAME);
		        Text st = document.createTextNode(data[1][2].trim());
		        LIST_NAME.appendChild(st);
		        
		        CODE_LIST_XREF.appendChild(SENDER_ID);
		        CODE_LIST_XREF.appendChild(RECEIVER_ID);
		        
		        CODE_LIST_XREF.appendChild(LIST_VERSION);
		        Text LV = document.createTextNode("1");
		        LIST_VERSION.appendChild(LV);
		        
		        CODE_LIST_XREF.appendChild(SIResourceDefaultVersion);
		        Text SIRDV = document.createTextNode("true");
		        SIResourceDefaultVersion.appendChild(SIRDV);
		        
		        CODE_LIST_XREF.appendChild(STATUS);
		        Text ST = document.createTextNode("1");
		        STATUS.appendChild(ST);
		        
		        CODE_LIST_XREF.appendChild(COMMENTS);
		        
		        
		        CODE_LIST_XREF.appendChild(USERNAME);
		        Text UN = document.createTextNode("Joe User");
		        USERNAME.appendChild(UN);
		        
		        CODE_LIST_XREF.appendChild(CREATE_DATE);
		        Text CD = document.createTextNode(""+ Calendar.getInstance().getTime());
		        CREATE_DATE.appendChild(CD);
		        
		        
		        CODE_LIST_XREF.appendChild(CODE_LIST_XREF_ITEMS);
		        CODE_LISTS.appendChild(CODE_LIST_XREF);
		        rootElement.appendChild(CODE_LISTS);
		        document.appendChild(rootElement);

		        
		        for (int index = 1; index < row_no; index++){
		            Element productElement = document.createElement("CODE_LIST_XREF_ITEM");
		            CODE_LIST_XREF_ITEMS.appendChild(productElement);
		            
		            

		            for(int j=3; j<colNum; j++) {
		                String headerString = data[0][j];
		                String s = data[index][j];
		                Element headerElement = document.createElement(headerString);
		                productElement.appendChild(headerElement);
		                headerElement.appendChild(document.createTextNode(s));
		            }
		        }

		        TransformerFactory tFactory = TransformerFactory.newInstance();

		        Transformer transformer = tFactory.newTransformer();
		        //Add indentation to output
		        transformer.setOutputProperty
		        (OutputKeys.INDENT, "yes");
		        transformer.setOutputProperty(
		                "{http://xml.apache.org/xslt}indent-amount", "2");

		        DOMSource source = new DOMSource(document);
		        //path for output file
		        String report_name;
		        if(!data[1][2].trim().isEmpty())
		        report_name = outputDirectory + data[1][2].trim() + ".xml";
		        else
		        report_name = outputDirectory + fileName+ ".xml";
		        StreamResult result = new StreamResult(new File(report_name));
		        //StreamResult result = new StreamResult(System.out);
		        transformer.transform(source, result);
	            // close the workbook object
		        workBook.close();
		        
		    } catch (ParserConfigurationException pce) {
				pce.printStackTrace();
				log.error(pce.getMessage());
			} catch (TransformerException tfe) {
				tfe.printStackTrace();
				log.error(tfe.getMessage());
			} catch (IOException ioe) {
				ioe.printStackTrace();
				log.error(ioe.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
		
      }
      
  	public static String cellToString (HSSFCell cell){

		int type;
		Object result;
		result= "";
		if(cell!=null) {
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
		        result = cell.getBooleanCellValue ();
		        break;
		    default:
		        //throw new RunTimeException("There are not support for this type of cell");
		        }
		}

		return result.toString();
		}
      
     /*Set data in the given row of the excel sheet
 	 * @param codeListItem
 	 * @param row
 	 */
 	private static void setData(CodeListXref codeListItem , HSSFRow row, CodeListXrefItem xrefItem)
 	{
 		HSSFCell cell1 = row.createCell(0);
 		//cell1.setCellValue("PARTNER_KEY");
 		if(codeListItem.getPartnerKey() != null)
 		{
 			cell1.setCellType(Cell.CELL_TYPE_STRING);
 			cell1.setCellValue(codeListItem.getPartnerKey());
 		}
 		HSSFCell cell2 = row.createCell(1);
 		//cell2.setCellValue("LIST_TYPE");
 		if(codeListItem.getListType() != null)
 		{
 			cell2.setCellType(Cell.CELL_TYPE_STRING);
 			cell2.setCellValue(codeListItem.getListType());
 		}
 		HSSFCell cell3 = row.createCell(2);
 		//cell3.setCellValue("LIST_NAME");
 		if(codeListItem.getListName() != null)
 		{
 			cell3.setCellType(Cell.CELL_TYPE_STRING);
 			cell3.setCellValue(codeListItem.getListName());
 		}
 		HSSFCell cell4 = row.createCell(3);
 		//cell4.setCellValue("SENDER_ITEM");
 		if(xrefItem.getSenderItem() != null)
 		{
 			cell4.setCellType(Cell.CELL_TYPE_STRING);
 			cell4.setCellValue(xrefItem.getSenderItem());
 		}
 		HSSFCell cell5 = row.createCell(4);
 		//cell5.setCellValue("RECEIVER_ITEM");
 		if(xrefItem.getReceiverItem() != null)
 		{
 			cell5.setCellType(Cell.CELL_TYPE_STRING);
 			cell5.setCellValue(xrefItem.getReceiverItem());
 		}
 		HSSFCell cell6 = row.createCell(5);
 		//cell6.setCellValue("DESCRIPTION");
 		if(xrefItem.getDescription() != null)
 		{
 			cell6.setCellType(Cell.CELL_TYPE_STRING);
 			cell6.setCellValue(xrefItem.getDescription());
 		}
 		HSSFCell cell7 = row.createCell(6);
 		//cell7.setCellValue("TEXT1");
 		if(xrefItem.getText1() != null)
 		{
 			cell7.setCellType(Cell.CELL_TYPE_STRING);
 			cell7.setCellValue(xrefItem.getText1());
 		}
 		HSSFCell cell8 = row.createCell(7);
 		//cell8.setCellValue("TEXT2");
 		if(xrefItem.getText2() != null)
 		{
 			cell8.setCellType(Cell.CELL_TYPE_STRING);
 			cell8.setCellValue(xrefItem.getText2());
 		}
 		HSSFCell cell9 = row.createCell(8);
 		//cell9.setCellValue("TEXT3");
 		if(xrefItem.getText3() != null)
 		{
 			cell9.setCellType(Cell.CELL_TYPE_STRING);
 			cell9.setCellValue(xrefItem.getText3());
 		}
 		HSSFCell cell10 = row.createCell(9);
 		//cell10.setCellValue("TEXT4");
 		if(xrefItem.getText4() != null)
 		{
 			cell10.setCellType(Cell.CELL_TYPE_STRING);
 			cell10.setCellValue(xrefItem.getText4());
 		}
 		HSSFCell cell11 = row.createCell(10);
 		//cell11.setCellValue("TEXT5");
 		if(xrefItem.getText5() != null)
 		{
 			cell11.setCellType(Cell.CELL_TYPE_STRING);
 			cell11.setCellValue(xrefItem.getText5());
 		}
 		HSSFCell cell12 = row.createCell(11);
 		//cell12.setCellValue("TEXT6");
 		if(xrefItem.getText6() != null)
 		{
 			cell12.setCellType(Cell.CELL_TYPE_STRING);
 			cell12.setCellValue(xrefItem.getText6());
 		}
 		HSSFCell cell13 = row.createCell(12);
 		//cell13.setCellValue("TEXT7");
 		if(xrefItem.getText7() != null)
 		{
 			cell13.setCellType(Cell.CELL_TYPE_STRING);
 			cell13.setCellValue(xrefItem.getText7());
 		}
 		HSSFCell cell14 = row.createCell(13);
 		//cell14.setCellValue("TEXT8");
 		if(xrefItem.getText8() != null)
 		{
 			cell14.setCellType(Cell.CELL_TYPE_STRING);
 			cell14.setCellValue(xrefItem.getText8());
 		}
 		HSSFCell cell15 = row.createCell(14);
 		//cell15.setCellValue("TEXT9");
 		if(xrefItem.getText9() != null)
 		{
 			cell15.setCellType(Cell.CELL_TYPE_STRING);
 			cell15.setCellValue(xrefItem.getText9());
 		}
 	}
 	/**
 	 * Set the first row in the output excel sheet
 	 * @param worksheet
 	 */
 	private static void setHeader(HSSFSheet worksheet)
 	{
 		HSSFRow row1 = worksheet.createRow(0);
 		HSSFCell cell1 = row1.createCell(0);
 		cell1.setCellValue("PARTNER_KEY");
 		HSSFCell cell2 = row1.createCell(1);
 		cell2.setCellValue("LIST_TYPE");
 		HSSFCell cell3 = row1.createCell(2);
 		cell3.setCellValue("LIST_NAME");
 		HSSFCell cell4 = row1.createCell(3);
 		cell4.setCellValue("SENDER_ITEM");
 		HSSFCell cell5 = row1.createCell(4);
 		cell5.setCellValue("RECEIVER_ITEM");
 		HSSFCell cell6 = row1.createCell(5);
 		cell6.setCellValue("DESCRIPTION");
 		HSSFCell cell7 = row1.createCell(6);
 		cell7.setCellValue("TEXT1");
 		HSSFCell cell8 = row1.createCell(7);
 		cell8.setCellValue("TEXT2");
 		HSSFCell cell9 = row1.createCell(8);
 		cell9.setCellValue("TEXT3");
 		HSSFCell cell10 = row1.createCell(9);
 		cell10.setCellValue("TEXT4");
 		HSSFCell cell11 = row1.createCell(10);
 		cell11.setCellValue("TEXT5");
 		HSSFCell cell12 = row1.createCell(11);
 		cell12.setCellValue("TEXT6");
 		HSSFCell cell13 = row1.createCell(12);
 		cell13.setCellValue("TEXT7");
 		HSSFCell cell14 = row1.createCell(13);
 		cell14.setCellValue("TEXT8");
 		HSSFCell cell15 = row1.createCell(14);
 		cell15.setCellValue("TEXT9");
 	}
 	
 	public static boolean isValidXml(File inputFile)
 	{
 		boolean isValid = true;
 		
 	      // jaxb object
	    	JAXBContext jaxbContext;
			try {
				jaxbContext = JAXBContext.newInstance(SIResources.class);
				
				//unmarshaller object
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				//Create an XMLReader to use with our filter
				XMLReader reader = XMLReaderFactory.createXMLReader();
				//Create the filter (to add name space) and set the xmlReader as its parent.
				NamespaceFilter inFilter = new NamespaceFilter("http://www.stercomm.com/SI/SI_IE_Resources", false);
				inFilter.setParent(reader);

				//Prepare the input, in this case a java.io.File (output)
				InputSource is = new InputSource(new FileInputStream(inputFile));

				//Create a SAXSource specifying the filter
				SAXSource source = new SAXSource(inFilter, is);
				SIResources siResource = (SIResources)jaxbUnmarshaller.unmarshal(source);
				if(siResource.getCodeLists() == null)
				{
					isValid = false;
					log.info(inputFile.getName()+ " required data not found.");
				}
			} catch (JAXBException e) {
				isValid = false;
				log.error(e.getMessage());
			} catch (SAXException e) {
				isValid = false;
				log.error(e.getMessage());
			} catch (FileNotFoundException e) {
				log.error(e.getMessage());
			}
	    	
 		
 		return isValid;
 	}
 	
	/*public static boolean isValidHeader(HSSFRow row) {
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
		ne.printStackTrace();
		log.error(ne.getMessage());
	    }
		
		return isValid;
		
	}*/
	
	public static boolean isRowEmpty(HSSFRow row) {
	    for (int c = 2/*row.getFirstCellNum()*/; c < row.getLastCellNum(); c++) {
	        Cell cell = row.getCell(c);
	        if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
	            return false;
	    }
	    return true;
	}
}
