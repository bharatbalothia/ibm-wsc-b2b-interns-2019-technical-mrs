package com.ibm.mapping.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.OldExcelFormatException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 
 * @author pradeep
 *
 */
public final class FileUtils {

	static Logger log = Logger.getLogger(FileUtils.class);
	/**
	 * Deletes directory with all files and subdirectories
	 */
	/**
	 * 
	 * @param path
	 */
	public static void deleteDirectory(File path) {
		//System.out.println("path=>"+path);
		cleanDirectory(path);
		
		//System.out.println("IP DIR EXIST=>"+path.exists());
		//System.out.println("IP DIR FILES CNT=>"+path.listFiles().length);
		File[] files = path.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) 
			files[i].delete();			
		}
		if(path.exists())
		path.delete();
		//System.out.println("IP DIR EXIST=>"+path.exists());
	}

	/**
	 * Deletes all files and subdirectories in directory
	 */
	/**
	 * 
	 * @param path
	 */
	public static void cleanDirectory(File path) {
		if (!path.exists())
			return;

		File[] files = path.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				cleanDirectory(files[i]);				
			}
			
			if(files[i].isFile())
			files[i].delete();
		}
		
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				files[i].delete();			
			}
		}
	   //System.out.println("Directory " + path + " deleted successfully");
	}

	public static boolean isDirectoryExists(String directoryName) {
		boolean directoryExists = true;
		File directory = new File(directoryName);
		if (!directory.exists()) {
			directoryExists = false;
			directory.mkdirs();
			//System.out.println("New directory : " + directoryName
				//	+ " is created");
		} else {
			//System.out.println("Directory : " + directoryName
			//		+ " already exists");
		}
		return directoryExists;
	}

	/**
	 * 
	 * @param path
	 */
	public static void deleteFile(File path) {
		if (path.exists()) {
			path.delete();
			//System.out.println("File " + path + " deleted successfully");
		}
	}

	public static List<String> deletedFileList(String inputDirectoryName) {
		List<String> deletedFiles = new ArrayList<String>();

		if (isDirectoryExists(inputDirectoryName)) {
			//System.out.println("Directory exists");

			File[] files = new File(inputDirectoryName).listFiles();
			for (int i = 0; i < files.length; i++) {
				String fileName = files[i].getName();

				if (fileName.contains(".")) {
					if (!fileName.substring(fileName.lastIndexOf("."))
							.equalsIgnoreCase(".mxl") && !fileName.substring(fileName.lastIndexOf("."))    
							.equalsIgnoreCase(".map") && !fileName.contains("ADD_NOTES_MISSING_REPORT")) {
						files[i].delete();
						deletedFiles.add(fileName);

					}
				}
			}
			//System.out.println("Deleted Files : " + deletedFiles.size());

		}

		return deletedFiles;
	}

	public static List<String> deleteFileList(String inputDirectoryName,
			String fileExtensionType) {
		List<String> deletedFiles = new ArrayList<String>();

		if (isDirectoryExists(inputDirectoryName)) {
			//System.out.println("Directory exists");

			File[] files = new File(inputDirectoryName).listFiles();
			for (int i = 0; i < files.length; i++) {
				String fileName = files[i].getName();

				if (fileName.contains(".")) {
					if (!fileName.substring(fileName.lastIndexOf("."))
							.equalsIgnoreCase(fileExtensionType)) {
						files[i].delete();
						deletedFiles.add(fileName);

					}
				}
			}
			//System.out.println("Deleted Files : " + deletedFiles);

		}

		return deletedFiles;
	}

	/**
	 * To delete the invalid input file for reportType="ddfgenerate"
	 * 
	 * @param inputDirectoryName
	 * @param reportType
	 * @return
	 */
	public static List<String> deletedFileList(String inputDirectoryName,
			String reportType) {
		List<String> deletedFiles = new ArrayList<String>();

		if (isDirectoryExists(inputDirectoryName)) {

			File[] files = new File(inputDirectoryName).listFiles();
			for (int i = 0; i < files.length; i++) {
				String fileName = files[i].getName();

				if (fileName.contains(".")) {
					if (reportType.equals("ddfgenerate")
							&& (!fileName.substring(fileName.lastIndexOf("."))
									.equalsIgnoreCase(".xls"))) {
						files[i].delete();
						deletedFiles.add(fileName);

					} else if(reportType.equals("codelistcreate") || reportType.equals("codelistmerge"))
					{
						if(fileName.substring(fileName.lastIndexOf("."))
								.equalsIgnoreCase(".xls"))
						{
							try
							{
								//check whether the excel belongs to old format, that results exception
								HSSFWorkbook     workBook = new HSSFWorkbook (new FileInputStream(files[i]));
								
							}catch(OldExcelFormatException oe)
							{
								log.error("Error faced by file " + fileName +"Error message: "+oe.getMessage());
								files[i].delete();
								deletedFiles.add(fileName);
								continue;
							} catch (FileNotFoundException e) {
								log.error("Error faced by file " + fileName +"Error message: "+e.getMessage());
							} catch (IOException e) {
								log.error("Error faced by file " + fileName +"Error message: "+e.getMessage());
								files[i].delete();
								deletedFiles.add(fileName);
								continue;
							}
						}else if(!fileName.substring(fileName.lastIndexOf("."))
								.equalsIgnoreCase(".xls"))
						{
							if(!fileName.substring(fileName.lastIndexOf("."))
									.equalsIgnoreCase(".xml"))
							{
								files[i].delete();
								deletedFiles.add(fileName);
							}else
							{
								//check valid xml
								if(!CodeListCreator.isValidXml(files[i]))
								{
									files[i].delete();
									deletedFiles.add(fileName);
								}
								
							}
						}
					} else if (reportType.equals("idoctagUpdater")
							&& (!fileName.substring(fileName.lastIndexOf("."))
									.equalsIgnoreCase(".mxl"))) {
						files[i].delete();
						deletedFiles.add(fileName);

					} else if (reportType.equals("xsdcreate")
							&& (!fileName.substring(fileName.lastIndexOf("."))
									.equalsIgnoreCase(".xml"))) {
						files[i].delete();
						deletedFiles.add(fileName);
					}
				}
			}
			//System.out.println("Deleted Files : " + deletedFiles);
		}
		return deletedFiles;
	}
	
	public static void main(String[] args) {
		// cleanDirectory(new File("C:\\input"));
		deletedFileList("C:\\Softwares\\eclipse-jee-luna-SR1-RC3-win32-x86_64\\workspace\\MasterDashBoard\\accumulatorReport\\input");
	}
}
