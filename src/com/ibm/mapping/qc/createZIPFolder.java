package com.ibm.mapping.qc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

public class createZIPFolder {
	static String flag_MAP="N";
	static String wp_name = null;
	//log variable 
	static Logger log = Logger.getLogger(createZIPFolder.class.getName());
	
	public void create_InternalZIP(String srcFolder, String destZipFile, String isWTX) throws IOException {
		File directoryToZip = new File(srcFolder);

		List<File> fileList = new ArrayList<File>();
		//System.out.println("---Getting references to all files in: " + directoryToZip.getCanonicalPath());
		getAllFiles_INT(directoryToZip, fileList, isWTX);
		//System.out.println("---Creating zip file");
		writeZipFile_X(directoryToZip, fileList,destZipFile);
		//System.out.println("---Done");
	}
	
	public void create_ExternalZIP(String srcFolder, String destZipFile) throws IOException {
		File directoryToZip = new File(srcFolder);

		List<File> fileList = new ArrayList<File>();
		//System.out.println("---Getting references to all files in: " + directoryToZip.getCanonicalPath());
		getAllFiles_EXT(directoryToZip, fileList);
		//System.out.println("---Creating zip file");
		writeZipFile_X(directoryToZip, fileList,destZipFile);
		//System.out.println("---Done");
	}
    
	public static void getAllFiles_INT(File dir, List<File> fileList, String isWTX) {
		int no_of_maps = 0;
		int pos = dir.getName().indexOf("_");
		int len = dir.getName().length();
		wp_name = dir.getName().substring(pos+1, len);
		//System.out.println("in zip wp_name=>"+wp_name);
		File[] listFiles1 = null;
		listFiles1 = dir.listFiles();
		for (File file : listFiles1) {                             // convert map to mxl if map is present
			//System.out.println("***1 "+file.getName());
			 if(file.getName().endsWith(".map") || file.getName().endsWith(".mxl"))  
				 no_of_maps=no_of_maps+1;
			 if(file.getName().endsWith(".map"))  
			     flag_MAP="Y";		 
		}
		File[] files = dir.listFiles();
		for (File file : files) {
			if(isWTX.equals("Y"))
			fileList.add(file);
			else {									    				    

			    if(flag_MAP=="Y" && no_of_maps == 2) {
			      if(!file.getName().endsWith(".mxl") && !file.getName().endsWith(".bak")) {
			    	  fileList.add(file);
			      }
			    }
			    else {
			    	if(!file.getName().endsWith(".bak") && !file.getName().endsWith("_SI.map") && !file.getName().endsWith("_SI.mxl")) {
			    	fileList.add(file);  
			      }
			    }
			}
				
			if (file.isDirectory()) {
				//System.out.println("directory:" + file.getCanonicalPath());
				getAllFiles_INT(file, fileList, isWTX);
			} else {
				//System.out.println("     file:" + file.getCanonicalPath());
			}
		}
	}
	
	public static void getAllFiles_EXT(File dir, List<File> fileList) {
		File[] files = dir.listFiles();
		for (File file : files) {
			
			if(!file.getName().endsWith(".bak") && !file.getName().endsWith(".mxl") && !file.getName().endsWith(".map") && !file.getName().endsWith(".txo") && !file.getName().endsWith(".tpl") && !file.getName().endsWith(".mms") && !file.getName().endsWith(".mmc") && !file.getName().endsWith(".lnx"))
			fileList.add(file);
			
			if (file.isDirectory()) {
				//System.out.println("directory:" + file.getCanonicalPath());
				getAllFiles_EXT(file, fileList);
			} else {
				//System.out.println("     file:" + file.getCanonicalPath());
			}
		}
	}
	

	public static void writeZipFile_X(File directoryToZip, List<File> fileList, String destZipFile) {

		try {
			//FileOutputStream fos = new FileOutputStream(directoryToZip.getName() + ".zip");
			FileOutputStream fos = new FileOutputStream(destZipFile);
			ZipOutputStream zos = new ZipOutputStream(fos);

			for (File file : fileList) {
				if (!file.isDirectory()) { // we only zip files, not directories
					addToZip(directoryToZip, file, zos);
				}
			}

			zos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	public static void addToZip(File directoryToZip, File file, ZipOutputStream zos) throws FileNotFoundException,
			IOException {

		FileInputStream fis = new FileInputStream(file);

		// we want the zipEntry's path to be a relative path that is relative
		// to the directory being zipped, so chop off the rest of the path
		String zipFilePath = file.getCanonicalPath().substring(directoryToZip.getCanonicalPath().length() + 1,
				file.getCanonicalPath().length());
		//System.out.println("Writing '" + zipFilePath + "' to zip file");
		ZipEntry zipEntry = new ZipEntry(zipFilePath);
		zos.putNextEntry(zipEntry);

		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zos.write(bytes, 0, length);
		}

		zos.closeEntry();
		fis.close();
	}
}