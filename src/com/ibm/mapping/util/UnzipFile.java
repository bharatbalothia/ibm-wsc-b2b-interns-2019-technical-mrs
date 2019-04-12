package com.ibm.mapping.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 
 * @author pradeep
 *
 */
public final class UnzipFile {
	public String unzipFile(String filePath, String userName, String fileName,boolean IsQAToolUsed) {

		FileInputStream fis = null;
		ZipInputStream zipIs = null;
		ZipEntry zEntry = null;

		String folder = "C:\\" + userName + "_" + fileName;
		
		FileUtils.cleanDirectory(new File(folder));
		try {
			fis = new FileInputStream(filePath);
			zipIs = new ZipInputStream(new BufferedInputStream(fis));

			while ((zEntry = zipIs.getNextEntry()) != null) {
				try {
					byte[] tmp = new byte[4 * 1024];
					FileOutputStream fos = null;

					// Check if output directory exists.
					File outputDir = new File(folder);

					if (!outputDir.exists()) {
						//System.out.println("Output Directory Name");
						// Create a output directory.
						outputDir.mkdir();
					}
					String opFilePath = "C:\\" + userName + "_" + zEntry.getName();
					/*if(IsQAToolUsed) {
					File file = new File(opFilePath);
					System.out.println("file.getAbsolutePath()=>"+file.getAbsolutePath());
					if(file.getName().endsWith(".zip"))
					unzip(file.getAbsolutePath(),file.getAbsolutePath().substring(0,file.getAbsolutePath().length()-4));	
					}*/

					//System.out.println("Extracting file to " + opFilePath);
					
					if(!zEntry.isDirectory()) {
					fos = new FileOutputStream(opFilePath);
					int size = 0;
					while ((size = zipIs.read(tmp)) != -1) {
						fos.write(tmp, 0, size);
					}
					fos.flush();
					fos.close();
					}
					else {
					//System.out.println("isDirectory()=>true");
					File dir = new File(opFilePath);
		            dir.mkdir();
		            if(IsQAToolUsed) {
		            String FilePath = "C:\\" + userName + "_" + zEntry.getName();
		            //System.out.println("dir name = " + FilePath);
		            
					//System.out.println("Extracting file to " + opFilePath);
					
					fos = new FileOutputStream(FilePath);
					int size = 0;
					while ((size = zipIs.read(tmp)) != -1) {
						fos.write(tmp, 0, size);
					}
					fos.flush();
					fos.close();
		            }
					}
				} catch (Exception ex) {

				}
			}
			zipIs.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return folder;
	}
	
	public void unzip(String zipFilePath, String destDir) {
        File dir = new File(destDir);
        // create output directory if it doesn't exist
        File input = new File(zipFilePath);
       // System.out.println(input.exists());
        if(!dir.exists()) dir.mkdirs();
        FileInputStream fis;
        //buffer for read and write data to file
        byte[] buffer = new byte[1024];
        try {
            fis = new FileInputStream(zipFilePath);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry ze = zis.getNextEntry();
            while(ze != null){
                String fileName = ze.getName();
                File newFile = new File(destDir + File.separator + fileName);
                //System.out.println("Unzipping to "+newFile.getAbsolutePath());
                //create directories for sub directories in zip
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
                }
                fos.close();
                //close this ZipEntry
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            //close last ZipEntry
            zis.closeEntry();
            zis.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
         
    }

	/**
	 * Count files in a directory (including files in all subdirectories)
	 * 
	 * @param directory
	 *            the directory to start in
	 * @return the total number of files
	 */
	public static int countFilesInDirectory(File directory) {
		int count = 0;
		for (File file : directory.listFiles()) {
			if (file.isFile()) {
				count++;
			}
			if (file.isDirectory()) {
				count += countFilesInDirectory(file);
			}
		}
		return count;
	}

	public static void main(String a[]) {

		// UnzipFile mfe = new UnzipFile();

		/*
		 * mfe.unzipFile(
		 * "C:\\Softwares\\eclipse-jee-luna-SR1-RC3-win32-x86_64\\workspace\\MasterDashBoard\\accumulatorReport\\input.zip"
		 * , "pradeep_p_13");
		 */

		System.out.println(countFilesInDirectory(new File(
				"C:\\MappingSupport\\Report\\pradeep_p_13\\output")));
	}
}
