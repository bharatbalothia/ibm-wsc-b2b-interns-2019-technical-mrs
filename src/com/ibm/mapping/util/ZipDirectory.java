package com.ibm.mapping.util;

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

public class ZipDirectory {
	static Logger log = Logger.getLogger(FileUtils.class);

	public static String zipIn(String folderpath) throws IOException {

		File directoryToZip = new File(folderpath);

		List<File> fileList = new ArrayList<File>();
		//System.out.println("---Getting references to all files in: "
			//	+ directoryToZip.getCanonicalPath());
		getAllFiles(directoryToZip, fileList);
		//System.out.println("---Creating zip file");
		String zipFileName = getWriteZipFile(directoryToZip, fileList);
		//System.out.println("---Done");
		return zipFileName;
	}

	public static void getAllFiles(File dir, List<File> fileList) {
		try {
			File[] files = dir.listFiles();
			for (File file : files) {
				fileList.add(file);
				if (file.isDirectory()) {
					log.error("directory:" + file.getCanonicalPath());
					getAllFiles(file, fileList);
				} else {
					log.error("     file:" + file.getCanonicalPath());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getWriteZipFile(File directoryToZip,
			List<File> fileList) {
		String zipfileName = directoryToZip.getAbsolutePath() + ".zip";
		try {

			FileOutputStream fos = new FileOutputStream(zipfileName);

			//System.out.println(zipfileName);

			ZipOutputStream zos = new ZipOutputStream(fos);

			for (File file : fileList) {
				if (!file.isDirectory()) { // we only zip files, not directories
					addToZip(directoryToZip, file, zos);
				}
			}

			zos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return zipfileName;
	}

	public static void addToZip(File directoryToZip, File file,
			ZipOutputStream zos) throws FileNotFoundException, IOException {

		FileInputStream fis = new FileInputStream(file);

		// we want the zipEntry's path to be a relative path that is relative
		// to the directory being zipped, so chop off the rest of the path
		String zipFilePath = file.getCanonicalPath().substring(
				directoryToZip.getCanonicalPath().length() + 1,
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

	public static void main(String[] args) throws IOException {

		File directoryToZip = new File(
				"C:\\Spark\\spark-1.2.0\\assembly\\src\\deb\\control");

		List<File> fileList = new ArrayList<File>();
		//System.out.println("---Getting references to all files in: "
			//	+ directoryToZip.getCanonicalPath());
		getAllFiles(directoryToZip, fileList);
		//System.out.println("---Creating zip file");
		//System.out.println("Zip File Name : "
		//		+ getWriteZipFile(directoryToZip, fileList));
		//System.out.println("---Done");

	}
}
