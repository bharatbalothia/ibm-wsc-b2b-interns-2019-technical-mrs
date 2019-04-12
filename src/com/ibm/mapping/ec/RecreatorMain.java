package com.ibm.mapping.ec;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;

import com.ibm.mapping.servlet.Constants;
import com.ibm.mapping.util.FileTester;
import com.ibm.mapping.util.FileUtils;

public class RecreatorMain {

	//log variable 
	static Logger log = Logger.getLogger(RecreatorMain.class.getName());
	
	public RecreatorMain() {
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings({ "rawtypes", "unused"/*, "resource"*/ })
    public static void createEnvelope(String inputDirectory,
			String outputDirectory,String envType)
    {
		File inputDir = new File(inputDirectory);

		// Check if output directory exists.
		File outputDir = new File(outputDirectory);

		//System.out.println("outputDir=>"+outputDir.getAbsolutePath());		
		
		if (!outputDir.exists()) {
			// Create a output directory.
			outputDir.mkdirs();
		}
		
		//System.gc();
		FileUtils.cleanDirectory(outputDir);
	
		/*File[] listOfOPFiles = outputDir.listFiles();
		
		if(listOfOPFiles.length > 0) {
		
		for (int i = 0; i < listOfOPFiles.length; i++) {
		System.out.println("OP FILES CNT BFR=>"+listOfOPFiles.length);
				if (listOfOPFiles[i].isFile()) 
					listOfOPFiles[i].delete();
		}
		}*/
		
		//System.out.println("OP FILES CNT AFT=>"+outputDir.listFiles().length);
				
		// Check if input directory exists.
		if (inputDir.exists()) {
			
			File[] listOfFiles = inputDir.listFiles();
  			for (int i = 0; i < listOfFiles.length; i++) {

  				if (listOfFiles[i].isFile()) {
  					
  					String inputFileName = listOfFiles[i].getName();
  					
  					if (inputFileName.toLowerCase().endsWith(".xls"))
  					{
  					
  						//Innitailizing XML document 
  						Document doc = new Document();
  									
  						//adding Namespace(Prolog)
  						
  						Namespace n1 = Namespace.getNamespace("http://www.stercomm.com/SI/SI_IE_Resources");
  						Namespace n2 = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
  						Element si_resource = new Element("SI_RESOURCES" ,n1);
  						si_resource.addNamespaceDeclaration(n2);
  						si_resource.setAttribute("GISVersion", "4.0.0-0");
  						
  						//Setting Root Element 
  						doc.setRootElement(si_resource);
  						Element doc_enve = new Element("DOCUMENT_ENVELOPES" ).setNamespace(n1);	
  						doc.getRootElement().addContent(doc_enve);		
  						
  						try {
  							//File selectedFile = new File(inputDirectory);
  							FileInputStream inputStream = new FileInputStream(listOfFiles[i]/*.getAbsolutePath()*/);
  							
  							HSSFWorkbook workbook = null;
  							HSSFSheet worksheet = null;
  							Map values = null;
  							Util u = new Util();
  							List keys = null;
  							
  							workbook = new HSSFWorkbook(inputStream);
  							worksheet = workbook.getSheetAt(0);
  							
  							Row row = worksheet.getRow(0);
  							int lastRow = worksheet.getLastRowNum();
  							//Key the cloumns IDs as a key 
  							for(int rowCount = 0 ; rowCount <= lastRow ; rowCount++ ){
  								if(rowCount ==0 ){
  										
  									keys = u.getKeys(row);
  									//System.out.println(lastRow);
  									//System.out.println(keys.toString());
  									
  									}
  								if(rowCount > 0){
  									values = new HashMap();
  									Row r2=  worksheet.getRow(rowCount);
  									values = u.getValue(r2, row,  keys);
  														
  									switch (envType) {
  									//On the choice entered by the user construtor is called respectively 
  									case "1":
  										X12_Inbound x12Inbound = new X12_Inbound(doc , values ,n1, outputDirectory);
  										break;
  									case "2" :
  										X12_Outbound  x12outbound = new X12_Outbound(doc, values ,n1, outputDirectory);
  										break;
  									case "3" :
  										Edifact_Inbound ediIn = new Edifact_Inbound(doc, values ,n1, outputDirectory);
  										break;
  									case "4" :
  										Edifact_Outbound ediOut = new Edifact_Outbound(doc , values , n1, outputDirectory);
  										break;
  									case "5" :
  										VDA_Inbound vda = new VDA_Inbound(doc,values , n1, outputDirectory);
  										break;
  									case "6" :
  										VDA_Outbound vdaOut = new VDA_Outbound(doc, values ,n1, outputDirectory);
  										break;
  									case "7" :
  										Tradacom_Inbound ti = new Tradacom_Inbound(doc, values ,n1, outputDirectory);
  										break;					
  									case "8" :
  										Tradacom_Outbound to = new Tradacom_Outbound(doc, values , n1, outputDirectory);
  										break;
  									default:
  										break;
  									}
  								}
  							}
  							
  							inputStream.close();
  							workbook.close();
  						} catch (IOException e) {
  							e.printStackTrace();
  						}
  						catch (NullPointerException e) {
  							e.printStackTrace();
  						}

  					}else {
  						log.error("The input directory '" + inputDirectory
  								+ "' does not exists.");
  					}
  						
  					}
  					
  				}
  			}
	
    }

}
