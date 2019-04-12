package com.ibm.mapping.util;

//Created by Sukanya Dasgupta on July 25, 2015.

import java.io.*;
import java.lang.ProcessBuilder.Redirect;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.ibm.mapping.servlet.Constants;

import javax.swing.JFileChooser;

import java.awt.Component;
import java.awt.Desktop;

public class GentranToolC {
	//log variable 
	static Logger log = Logger.getLogger(GentranToolC.class.getName());
	
    public static void generateSI_MAP(String inputDirectory,
  			String outputDirectory,String encoding,String presessionName, String delimiter_1, String delimiter_2) {
    	
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
  		
  		//String inputDir = "C:";
       
        Date date = new Date();
        String m1, m2;
        m1 = "";
        m2 = "";
        String userN;
        int mapPTFFlag = 0;
        int flagMapper = 0;
        String filesNotFound = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = dateFormat.format(date);
        //String outputDir = inputDir + "\\" + "SIMaps" + timestamp;
        String dirPath, dirName;
        String PTFname;
        int dirFlag = 0;
        String map, PTF, out;
        String [] arguments = new String[5];
        File dir = new File(inputDirectory);
        String [] fileList = dir.list(new FilenameFilter() {
            @Override
            public boolean accept(File pathname, String name) {
                String filePath = pathname.toString() + "\\" + name;
                File fileP = new File(filePath);
                
                return ((fileP.isFile()) && (name != null));
            }
        });
        
        List<String> fL = Arrays.asList(fileList);
        ArrayList<String> maps = new ArrayList<String>();
        ArrayList<String> PTFs = new ArrayList<String>();
        ArrayList<String> DIRs = new ArrayList<String>();
        
        //Convert maps to MXLs.
        for (String f : fileList) {
        	if (f.toLowerCase().endsWith(".map")) {
        		if (flagMapper == 0) {
        			flagMapper = 1;
        			m1 = Constants.DEFAULT_MAPPER_EXE;
	            	m2 = Constants.MAPPER_EXE;       			
        		}
        		try {
        			String fout = f.substring(0, f.length() - 4) + "_MigrateCopy.map";
        			String foutmxl = f.substring(0, f.length() - 4) + "_MigrateCopy.mxl";
        			String foutmxlF = f.substring(0, f.length() - 4) + ".mxl";
        			copyFile(inputDirectory + "\\" + f,inputDirectory + "\\" + fout);
        			
        			try {
        				//String cmd = m1 + " -s 24 "+ inputDirectory + "\\" + fout;
        				//Process p = Runtime.getRuntime().exec(cmd);
        				//p.waitFor();
        				ProcessBuilder pb = new ProcessBuilder(m1, "-s","24", inputDirectory + File.separator + fout);
						pb.start().waitFor();
        			}
        			catch (Exception e) {log.error(e.getMessage());}
        			try {
        				//String cmd = m2 + " -s 24 "+ inputDirectory + "\\" + fout;
        				//Process p = Runtime.getRuntime().exec(cmd);
        				//p.waitFor();  
        				ProcessBuilder pb = new ProcessBuilder(m2, "-s","24", inputDirectory + File.separator + fout);
						pb.start().waitFor();
        			}
        			catch (Exception e) {log.error(e.getMessage());}
        			
        			copyFile(inputDirectory + "\\" + foutmxl,inputDirectory + "\\" + foutmxlF);   
        			
        		}
        		catch (Exception e) {
        			log.error(e.getMessage());       			
        		}
        		
        	}
        }
        
        fileList = dir.list(new FilenameFilter() {
            @Override
            public boolean accept(File pathname, String name) {
                String filePath = pathname.toString() + "\\" + name;
                File fileP = new File(filePath);
                
                return ((fileP.isFile()) && (name != null));
            }
        });
        
        fL = Arrays.asList(fileList);
        
        //Checking for MXL files and corresponding PTFs in folder.
        for (String f : fileList) {
            if (f.toLowerCase().endsWith(".mxl") && !f.toLowerCase().contains("migratecopy")) {
                if (dirFlag == 0) {
                    new File(outputDirectory).mkdir();
                    dirFlag = 1;
                }
                mapPTFFlag = 0;
                dirName = f.substring(0, f.length() - 4);
                
                for (String f2 : fileList) {
                	if ((f2.substring(0, f2.length() - 4).equals(dirName)) && (f2.toLowerCase().endsWith(".txt"))) {
                		mapPTFFlag = 1;
                		PTFname = f2;
                		maps.add(inputDirectory + "\\" + f);
                        PTFs.add(inputDirectory + "\\" + PTFname);
                        dirPath = outputDirectory + "\\" + dirName;
                        new File(dirPath).mkdir();
                        DIRs.add(dirPath);
                	}
                }
                
                if (mapPTFFlag == 0) {
                	filesNotFound += f + " doesn't have a corresponding PTF.\n";
                }
              
            }
        }
        if (dirFlag == 0) {
            log.info("No maps found.");
            return;
        }
        if (!(filesNotFound.equals(""))) {
        	filesNotFound += "Note: Name of the PTF should be the same as that of the map (e.g. PTF for ABC.mxl should be named ABC.txt).";
        	log.info(filesNotFound);
        }
        
        if (maps.size() == 0) {
        	return;
        }
        
        Iterator<String> mapIterator = maps.iterator();
        Iterator<String> PTFIterator = PTFs.iterator();
        Iterator<String> DIRIterator = DIRs.iterator();

        //Call migration tool GentranNTSI for every map and PTF. Program takes 5 arguments
        //1) MXL
        //2) PTF
        //3) Output Directory
        //4) Character Encoding
        //5) User Name
        while (mapIterator.hasNext()) {
            
            map = mapIterator.next();
            PTF = PTFIterator.next();
            out = DIRIterator.next();
            //System.out.println("map=>"+map);
            //System.out.println("PTF=>"+PTF);
            //System.out.println("out=>"+out);
            GentranNTSI.migrate_MAP(map, PTF, out, encoding, presessionName,delimiter_1,delimiter_2);
            /*try {
				ZipDirectory.zipIn(out);
			} catch (IOException e) {
				e.printStackTrace();
			}*/
        }
        
        try {
        	File ps = new File(outputDir + "\\" + "possyntax.txt");
        	ps.delete();
        }
        catch(Exception e) {
        }
        
  		}else {
  			log.error("The input directory '" + inputDirectory
  					+ "' does not exists.");
  		}
  	
    }
    
    public static void copyFile(String aPath, String bPath) {
    	InputStream inStream = null;
	    OutputStream outStream = null;
 
    	try{
    		
    		File afile =new File(aPath);
    	    File bfile =new File(bPath);
            
    	    if(!bfile.exists()) {
    	    
    	    inStream = new FileInputStream(afile);
    	    outStream = new FileOutputStream(bfile);
 
    	    byte[] buffer = new byte[1024];
 
    	    int length;
    	    //copy the file content in bytes 
    	    while ((length = inStream.read(buffer)) > 0){
 
    	    	outStream.write(buffer, 0, length);
 
    	    }
 
    	    inStream.close();
    	    outStream.close();
    	    
    	    }
 
    	    //System.out.println("Map is copied successful!");
 
    	}catch(IOException e){
    		e.printStackTrace();
    		log.error(e.getMessage());
    	}
	}
}