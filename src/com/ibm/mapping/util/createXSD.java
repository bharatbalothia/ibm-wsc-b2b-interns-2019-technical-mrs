package com.ibm.mapping.util;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.*;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

/**
 * @author Sanket
 *
 */
public class createXSD {
	
	    //log variable 
		static Logger log = Logger.getLogger(createXSD.class.getName());
		
	      public static void generateSchema(String inputDirectory,
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
	  					
	  					/*if(inputFileName.endsWith(".xls"))
	  					{
	  						outputFileName = inputFileName.replace(".xls", "");
	  						//converts xls to xml
	  						codeListToXML(listOfFiles[i],outputDirectory,outputFileName);
	  					}else */if (inputFileName.endsWith(".xml"))
	  					{
	  						outputFileName = inputFileName.replace(".xml", "");
	  						//generate xsd from xml file
	  						try {
								genXSD(listOfFiles[i],outputDirectory);
							} catch (IOException e) {
								e.printStackTrace();
							}
	  					}
	  					else
	  					{
	  						log.error("The service only accepts xml file.");
	  					}
	  					
	  				}
	  			}
	  		}else {
	  			log.error("The input directory '" + inputDirectory
	  					+ "' does not exists.");
	  		}
	  	
	      }


	public static void genXSD(File xml_file,String outputDirectory) throws IOException {
		
		String cmd = null,fileName = null;
		File dir = new File(xml_file.getParent());
		
		if(xml_file.exists()) {
			
		if(xml_file.getName().endsWith("XML"))
		fileName = xml_file.getName().replaceFirst(".XML", "").trim();
		else if(xml_file.getName().endsWith("xml"))
			 fileName = xml_file.getName().replaceFirst(".xml", "").trim();
	
		cmd = "inst2xsd -design vb -enumerations never -outPrefix " + fileName + " " + "\"" + xml_file.getName() + "\"";
		
		//System.out.println("CMD=> "+cmd);
		
        String[] command = {"cmd", "/c", cmd};
        ProcessBuilder probuilder = new ProcessBuilder( command );

        //You can set up your work directory
        probuilder.directory(dir);
        
        Process process = probuilder.start();
                
        //Wait to get exit value
        try {
            int exitValue = process.waitFor();
            
            if(exitValue==0) {
            	
            String path = null;
            File inputXSD = null,outputXSD = null;
            if(xml_file.getAbsolutePath().endsWith(".xml"))
            path = xml_file.getAbsolutePath().replace(".xml", "");
            
            if(path!=null)
            inputXSD = new File(path+"0.xsd");
            
            outputXSD = new File(outputDirectory+inputXSD.getName());
            
            if(!outputXSD.exists())
            outputXSD.createNewFile();
            
            if(outputXSD.exists() && inputXSD.exists())
            copyFile(inputXSD,outputXSD);
    
            }
            else
            log.error("There is some issue in XSD creation!!!");
            
        } catch (InterruptedException e) {
        	log.error(e.getMessage());
        }		
		}	
		
	}
	
	public static void copyFile(File source, File dest)
			throws IOException {
		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		try {
			inputChannel = new FileInputStream(source).getChannel();
			outputChannel = new FileOutputStream(dest).getChannel();
			outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
		} finally {
			inputChannel.close();
			outputChannel.close();
		}
	}

}
