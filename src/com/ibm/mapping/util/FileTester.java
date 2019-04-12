package com.ibm.mapping.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.ibm.mapping.servlet.Constants;

/**
 * @author Sanket
 *
 */
public class FileTester {
	
			//log variable 
			static Logger log = Logger.getLogger(FileTester.class.getName());
			
		      public static void generateTestData(String inputDirectory,
		  			String outputDirectory,String serverURL, String serverUsername, String serverPassword,String usageType)
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
		  			File txoFile = null;
		  			File[] listOfFiles = inputDir.listFiles();
		  			for (int i = 0; i < listOfFiles.length; i++) {

		  				if (listOfFiles[i].isFile()) {
		  					
		  					String inputFileName = listOfFiles[i].getName();
		  					
		  					if (inputFileName.endsWith(".txo"))
		  					{
		  						txoFile = listOfFiles[i];
		  					}
		  					
		  				}
		  			}
		  			if(listOfFiles.length > 0) {
		  				File exeFile = new File(Constants.DEFAULT_MAPPER_EXE);
		  				try {
		  					if(txoFile != null) {
		  					if(txoFile.exists() && exeFile.exists() && listOfFiles.length > 1) {
							run_MUILTIPLE_TEST(exeFile,serverURL,serverUsername,serverPassword,txoFile,usageType);
							copyFolder(inputDir,outputDir);
		  					}
		  					}
						} catch (IOException e) {
							e.printStackTrace();
						}
		  			}

		  		}else {
		  			log.error("The input directory '" + inputDirectory
		  					+ "' does not exists.");
		  		}
		  	
		      }

	public static void run_MUILTIPLE_TEST(File exeFile, String serverURL, String serverUsername, String serverPassword,File txoFile,String usageType) throws IOException {
		
		int rp_cnt=0,op_cnt=0;
		String inputfileName = null;
		ArrayList<String> input_list = getInputFiles(txoFile.getParent());
		op_cnt = getOutputFiles_Count(txoFile.getParent());
		rp_cnt = getReportFiles_Count(txoFile.getParent());
		
		//System.out.println("input_list.size()=>"+input_list.size());
		if(input_list.size() > 0 && input_list.size() != op_cnt && input_list.size() != rp_cnt) {
			
        for(int i=0;i<input_list.size();i++) {
        
        inputfileName = input_list.get(i);
        if(inputfileName.contains(" "))
        inputfileName=inputfileName.replaceAll("\\s", "");
		
		String cmd = null;
		
		//cmd = "Mapper.exe -t adminib-l2gsrpo:20033 admin password C:\\CEVA_CAT_XML_OB_204_4010.txo C:\\test.xml C:\\ Y";
		
		cmd = exeFile.getName() + " -t " + serverURL + " " + serverUsername + " " + serverPassword + " " + txoFile.getAbsolutePath() + " " + txoFile.getParent() + File.separator + inputfileName + " " + txoFile.getParent() + " " + "Y";
		File dir = new File(exeFile.getParent());
		
		//System.out.println("CMD=> "+cmd);
		
        String[] command = {"cmd", "/c", cmd};
        ProcessBuilder probuilder = new ProcessBuilder( command );

        //You can set up your work directory
        probuilder.directory(dir);
        
        Process process = probuilder.start();
        
        //Wait to get exit value
        try {
            int exitValue = process.waitFor();
            //System.out.println("\n\nExit Value is " + exitValue);
            
            if(exitValue==0) {
            File opfile =new File(txoFile.getParent()+File.separator+"MapTestTxResult.txt");
            File reportfile =new File(txoFile.getParent()+File.separator+"MapTestTxReport.xml");
            
            if(opfile.exists())
            renameOutputFile(opfile,input_list.get(i),usageType);
            
            if(reportfile.exists())
            renameTxReport(reportfile,input_list.get(i),usageType);
           
            }
            else
            log.error("There is some issue in Output generation!!!");
            
        } catch (InterruptedException e) {
        	log.error(e.getMessage());
        }       
  	  
	    }
        
        File genFile = new File(txoFile.getParent()+File.separator+"GIS_Map_Test_ErrorReport.txt");
        
        if(genFile.exists())
        genFile.delete();
        
        op_cnt = getOutputFiles_Count(txoFile.getParent());
  	    rp_cnt = getReportFiles_Count(txoFile.getParent());
  		
  	    if(input_list.size() > 0 && input_list.size() == op_cnt && input_list.size() == rp_cnt)
  	    log.error("All input files been tested and uploaded in the txo folder!!!");
        
	  }
	  else if(input_list.size() > 0 && input_list.size() == op_cnt && input_list.size() == rp_cnt)
		   log.error("All input files been already tested!!!");
	  else if(input_list.size() == 0)
		   log.error("Please provide one or more input files in the txo folder!!!");
		
	}
	
	public static void renameOutputFile(File oldfile,String inputFileName,String usageType) {
		
		int dot_pos = inputFileName.lastIndexOf(".");
		String renameVal = null;
		inputFileName = inputFileName.substring(0, dot_pos);
		
		if(usageType.equals("parature"))
		renameVal=oldfile.getParent()+File.separator+inputFileName+"_IBM_Output.txt";
		else if(usageType.equals("development")) {
			 DateFormat df = new SimpleDateFormat("MMddyyyy");
			 Date dateobj = new Date();
			 renameVal=oldfile.getParent()+File.separator+inputFileName+"_RSC_OUTPUT_"+df.format(dateobj)+".txt";
		}
						
		File newfile =new File(renameVal);
		
		//System.out.println("********* "+newfile.getName());
		if(oldfile.renameTo(newfile))
		log.error("Rename succesful");
		else
		log.error("Rename failed");
	}
	
	public static void renameTxReport(File oldfile,String inputFileName,String usageType) {
		
		int dot_pos = inputFileName.lastIndexOf(".");
		inputFileName = inputFileName.substring(0, dot_pos);
		
		File newfile =new File(oldfile.getParent()+File.separator+inputFileName+"_Translation_Report.xml");
		
		if(oldfile.renameTo(newfile))
		log.error("Rename succesful");
		else
		log.error("Rename failed");
	}
	
	public static ArrayList<String> getInputFiles(String path) {
		
		String inputFileName = null;
		ArrayList<String> input_list = new ArrayList<String>();
		
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		        //System.out.println("File " + listOfFiles[i].getName());
		        if( !listOfFiles[i].getName().toLowerCase().endsWith(".jpg") && !listOfFiles[i].getName().toLowerCase().endsWith(".xls") && !listOfFiles[i].getName().toLowerCase().endsWith(".xlsx") && !listOfFiles[i].getName().toLowerCase().endsWith(".png") && !listOfFiles[i].getName().toLowerCase().endsWith(".txo") && !listOfFiles[i].getName().toLowerCase().endsWith(".map") && !listOfFiles[i].getName().toLowerCase().endsWith(".mxl") && !listOfFiles[i].getName().toLowerCase().contains("ibm_output") && !listOfFiles[i].getName().toLowerCase().contains("translation_report") && !listOfFiles[i].getName().toLowerCase().contains("translationreport") && !listOfFiles[i].getName().toLowerCase().contains("rsc_notes") && !listOfFiles[i].getName().toLowerCase().contains("ptf") && !listOfFiles[i].getName().toLowerCase().contains("codelist") && !listOfFiles[i].getName().toLowerCase().contains("lookup") && !listOfFiles[i].getName().equals("GIS_Map_Test_ErrorReport.txt")) {
		          if(listOfFiles[i].getName().contains(" ")) {
		          File oldFile = new File(listOfFiles[i].getAbsolutePath());
		          inputFileName=listOfFiles[i].getName().replaceAll("\\s", "_");
		          File newFile = new File(oldFile.getParent()+File.separator+inputFileName);
		  		  if(oldFile.renameTo(newFile))
		  		  log.error("Rename succesful for removing space from test file");
		  		  else
		  		  log.error("Rename failed for removing space from test file");
		          }
		          else
		          inputFileName=listOfFiles[i].getName();
		        
		          input_list.add(inputFileName);
		        }
		      } /*else if (listOfFiles[i].isDirectory()) {
		    	log.error("Directory " + listOfFiles[i].getName());
		      }*/
		    }
			return input_list;
		    
	}
	
	public static int getOutputFiles_Count(String path) {
		
		int op_cnt=0;
		
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		        if( !listOfFiles[i].getName().toLowerCase().contains("ptf") && !listOfFiles[i].getName().toLowerCase().endsWith(".jpg") && !listOfFiles[i].getName().toLowerCase().endsWith(".xls") && !listOfFiles[i].getName().toLowerCase().endsWith(".xlsx") && !listOfFiles[i].getName().toLowerCase().endsWith(".png") && !listOfFiles[i].getName().toLowerCase().endsWith(".txo") && !listOfFiles[i].getName().toLowerCase().endsWith(".map") && !listOfFiles[i].getName().toLowerCase().endsWith(".mxl") && listOfFiles[i].getName().toLowerCase().contains("ibm_output") && !listOfFiles[i].getName().toLowerCase().contains("translation_report") && !listOfFiles[i].getName().toLowerCase().contains("translationreport") && !listOfFiles[i].getName().toLowerCase().contains("rsc_notes") && !listOfFiles[i].getName().toLowerCase().contains("codelist") && !listOfFiles[i].getName().toLowerCase().contains("lookup") && !listOfFiles[i].getName().equals("GIS_Map_Test_ErrorReport.txt"))
		        op_cnt=op_cnt+1;
		      } /*else if (listOfFiles[i].isDirectory()) {
		        System.out.println("Directory " + listOfFiles[i].getName());
		      }*/
		    }
			return op_cnt;		    
	}
	
	public static int getReportFiles_Count(String path) {
		
		int rp_cnt=0;
		
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		        if( !listOfFiles[i].getName().toLowerCase().contains("ptf") && !listOfFiles[i].getName().toLowerCase().endsWith(".jpg") && !listOfFiles[i].getName().toLowerCase().endsWith(".xls") && !listOfFiles[i].getName().toLowerCase().endsWith(".xlsx") && !listOfFiles[i].getName().toLowerCase().endsWith(".png") && !listOfFiles[i].getName().toLowerCase().endsWith(".txo") && !listOfFiles[i].getName().toLowerCase().endsWith(".map") && !listOfFiles[i].getName().toLowerCase().endsWith(".mxl") && !listOfFiles[i].getName().toLowerCase().contains("ibm_output") && ( listOfFiles[i].getName().toLowerCase().contains("translation_report") || listOfFiles[i].getName().toLowerCase().contains("translationreport") )  && !listOfFiles[i].getName().toLowerCase().contains("rsc_notes") && !listOfFiles[i].getName().toLowerCase().contains("codelist") && !listOfFiles[i].getName().toLowerCase().contains("lookup") && !listOfFiles[i].getName().equals("GIS_Map_Test_ErrorReport.txt"))
		        rp_cnt=rp_cnt+1;
		      } /*else if (listOfFiles[i].isDirectory()) {
		        System.out.println("Directory " + listOfFiles[i].getName());
		      }*/
		    }
			return rp_cnt;		    
	}
	
    public static void copyFolder(File src, File dest)
        	throws IOException{
        	
        	if(src.isDirectory()){
        		
        		//if directory not exists, create it
        		if(!dest.exists()){
        		   dest.mkdir();
        		   //System.out.println("Directory copied from " + src + "  to " + dest);
        		}
        		
        		//list all the directory contents
        		String files[] = src.list();
        		
        		for (String file : files) {
        		   //construct the src and dest file structure
        		   File srcFile = new File(src, file);
        		   File destFile = new File(dest, file);
        		   //recursive copy
        		   copyFolder(srcFile,destFile);
        		}
        	   
        	}else{
        		//if file, then copy it
        		//Use bytes stream to support all file types
        		InputStream in = new FileInputStream(src);
        	    OutputStream out = new FileOutputStream(dest); 
        	                     
        	        byte[] buffer = new byte[1024];
        	    
        	        int length;
        	        //copy the file content in bytes 
        	        while ((length = in.read(buffer)) > 0){
        	    	   out.write(buffer, 0, length);
        	        }
     
        	        in.close();
        	        out.close();
        	        //System.out.println("File copied from " + src + " to " + dest);
        	}
        }

}
