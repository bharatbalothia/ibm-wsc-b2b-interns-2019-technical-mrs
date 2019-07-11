package com.ibm.mapping.servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import com.ibm.mapping.ec.RecreatorMain;
import com.ibm.mapping.service.DataService;
import com.ibm.mapping.service.DataServiceImpl;
import com.ibm.mapping.util.FileUtils;
import com.ibm.mapping.util.ReportGenerator;
import com.ibm.mapping.util.UnzipFile;
import com.ibm.mapping.util.ZipDirectory;
import com.ibm.mapping.util.validateSheet;

/**
 * 
 * @author pradeep
 * 
 */
@WebServlet("/UploadServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 20, // 20MB
maxFileSize = 1024 * 1024 * 100, // 100MB
maxRequestSize = 1024 * 1024 * 250) // 250MB
public class UploadServlet extends HttpServlet implements Constants {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(UploadServlet.class);

	/**
	 * handles file upload
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int zipSize = 0;
		String uId = null;
		String excludeflddef = null;
		String includeinactivefld = null;
		String inputDirectoryName = null;
		String mapName=null;
		String ticketNo = null;
		String ext = null;
		File tempFolder = null, tempIDir = null, tempFolder1 = null;
		String reportPath = null;
		boolean isTxoLatest=false;
		boolean isQCPassed=false;
		boolean isCorrectData = false;
		ArrayList<String> inputDir = new ArrayList<String>();
		ArrayList<Integer> inputDirSize = new ArrayList<Integer>();

		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(30 * 60);
		System.out.println(request);
		String usageType = request.getParameter("usageType");
		
		String groupName = "RSC_B2B";//session.getAttribute("groupName").toString();
	    System.out.println("In upload servlet: Group Name" + groupName);

		String selectedValue = request.getParameter("selectedValue");
		String reportName = request.getParameter("reportName");

		ext = request.getParameter("ext");
		System.out.println("Upload survlet called");
		// Added for MRS utility - Manish
		if (request.getParameter("Fdef") != null) {
			excludeflddef = request.getParameter("Fdef");
			log.info("Exclude field defination : " +
			excludeflddef);
		}

		if (request.getParameter("InactiveFld") != null) {
			includeinactivefld = request.getParameter("InactiveFld");
			log.info("Include inactive field : " + includeinactivefld);
		}
		// / end code for MRS utility

		String emailId = null;
		if (session.getAttribute("email") != null) {
			emailId = session.getAttribute("email").toString();
		} else {
			emailId = "temp_map@in.ibm.com";
		}

		String utilxslFile = null;
		emailId = emailId.substring(0, emailId.indexOf("@"));
		String OUTPUT_DIRECTORY_NAME = "C:\\MappingSupport\\Report\\" + emailId
				+ "\\output\\";
		// gets absolute path of the web application
		String appPath = request.getServletContext().getRealPath("");
		// constructs path of the directory to save uploaded file
		String savePath = appPath + File.separator + emailId + SAVE_DIR;
		//System.out.println("savePath=>"+savePath);

		// creates the save directory if it does not exists
		File fileSaveDir = new File(savePath);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdir();
		}
		String fileName = null;
		//System.out.println("ext=>"+ ext);
		if(ext.length()>0) {
		for (Part part : request.getParts()) {
			fileName = extractFileName(part);
			//System.out.println("Part.write == "+savePath + File.separator + fileName);
			part.write(savePath + File.separator + fileName);
	    }
		}
	
		if(fileName!=null) {
		if(fileName.startsWith("IBM_")) {
		ticketNo = fileName.substring(4, fileName.length());
		ticketNo = ticketNo.substring(0, 8);
		//System.out.println("ticketNo=>"+ticketNo);
		}
		}

		UnzipFile unzipFile = new UnzipFile();
		//System.out.println("CNT=>"+fileSaveDir.listFiles().length);
		String filePath = fileSaveDir.getAbsolutePath() + "\\" + fileName;
		//System.out.println("filePath=>"+filePath);
		File zipFile = new File(filePath);
		
		if(filePath.endsWith(".zip") && reportName.equals("qc_tool")) {
		double bytes = zipFile.length();
		double kilobytes = (bytes / 1024);
		zipSize = (int) Math.round(kilobytes);
		isTxoLatest = isTxoLatest(filePath);			
		}
		
		log.info("Uploaded file Name : " + fileName);
		log.info("Server path " + filePath);		 
	
		if(/*ext!=null*/ext.length()>0) {
		
		/*for (Part part : request.getParts()) {
				fileName = extractFileName(part);
				System.out.println("Part.write == "+savePath + File.separator + fileName);
				part.write(savePath + File.separator + fileName);
		}*/
		//System.out.println("fileName=>"+fileName);
		if (ext.trim().equals(".zip")) {
			
			if(reportName.equals("qa_tool")) {
			
			inputDirectoryName = unzipFile.unzipFile(filePath, emailId,fileName.substring(0, fileName.lastIndexOf(".")),true);
			//System.out.println("inputDirectoryName ZIP=>"+inputDirectoryName);
			
			File zipDir = new File(inputDirectoryName);
			
			File[] listOfFiles = zipDir.listFiles();
			for (int i = 0; i < listOfFiles.length; i++) {
				if(listOfFiles[i].getName().endsWith(".zip")) {
				double bytes = listOfFiles[i].length();
			    double kilobytes = (bytes / 1024);
		    	int kb = (int) Math.round(kilobytes);
				inputDirSize.add(kb);
				//inputDir.add(listOfFiles[i].getName());
				unzipFile.unzip(listOfFiles[i].getAbsolutePath(), listOfFiles[i].getAbsolutePath().substring(0,listOfFiles[i].getAbsolutePath().lastIndexOf("\\")));
				listOfFiles[i].delete();
				}
			}
			
			//File zipDir1 = new File(inputDirectoryName);
			
			File[] listOfFiles1 = zipDir.listFiles();
			for (int i = 0; i < listOfFiles1.length; i++) {
				if(listOfFiles1[i].isDirectory()) 
				inputDir.add(listOfFiles1[i].getName());	
			}
			
			}
			else {
			inputDirectoryName = unzipFile.unzipFile(filePath, emailId,fileName.substring(0, fileName.lastIndexOf(".")),false);
			
			//System.out.println("inputDirectoryName ZIP=>"+inputDirectoryName);
			
			File zipDir = new File(inputDirectoryName);
			//FileTester.copyFolder(tempIDir, tempFolder1);
			File[] listOfFiles = zipDir.listFiles();
			for (int i = 0; i < listOfFiles.length; i++) {

				if(listOfFiles[i].getName().endsWith(".map") || listOfFiles[i].getName().endsWith(".mxl")) 
				mapName = listOfFiles[i].getName();
				
				if(listOfFiles[i].getName().endsWith(".map") && !reportName.equals("file_transmission") && !reportName.equals("qc_tool") && !reportName.equals("qa_tool") && !reportName.equals("gentran_migration_tool")) {
					try {
						ProcessBuilder pb = new ProcessBuilder(MAPPER_EXE, "-s","24", inputDirectoryName + File.separator + mapName);
						pb.start().waitFor();
						//FileUtils.deleteFile(tempFolder1);
					} catch (Exception e) {
						log.error(e.getMessage());
					}				
				}
				
			}
		 }
		} else {
			
			// ************** Code to allow single file upload apart from zip
			// file *************

			inputDirectoryName = "C:\\" + emailId + "_" + fileName.substring(0, fileName.lastIndexOf("."));
			
			tempFolder = new File(inputDirectoryName);
			
			if (tempFolder.exists())
			    tempFolder.delete();
			
			//System.out.println("tempFolder.exists()=>"+tempFolder.exists());
			
			if (!tempFolder.exists())
				tempFolder.mkdir();

			if (tempFolder.listFiles() != null)
			FileUtils.cleanDirectory(tempFolder);

			String inputPath = savePath + File.separator + fileName;
			String outputPath = inputDirectoryName + File.separator + fileName;
			tempIDir = new File(inputPath);

			if (tempIDir.exists()) {

				tempFolder1 = new File(outputPath);

				if (!tempFolder1.exists()) {
					if (tempFolder1.createNewFile())
						log.info("created File: "
								+ tempFolder1.exists());
				}
				if (tempFolder1.exists()) {
					CopyOption[] options = new CopyOption[] {
							StandardCopyOption.REPLACE_EXISTING,
							StandardCopyOption.COPY_ATTRIBUTES };

					Files.copy(tempIDir.toPath(), tempFolder1.toPath(), options);				
				}

			}
			
			// ****************** Code to convert .map file to .mxl
			if (ext.trim().equals(".map") && ext!=null && !reportName.equals("file_transmission")) {
				try {
					log.info("output path: "+outputPath);
					//System.out.println("output path: "+outputPath);
					ProcessBuilder pb = new ProcessBuilder(MAPPER_EXE, "-s","24", outputPath);
					// pb.directory(tempFolder);
					pb.start().waitFor();
					FileUtils.deleteFile(tempFolder1);
					/*
					 * if(tempFolder.listFiles() != null) {
					 * System.out.println("length: "
					 * +tempFolder.listFiles().length); }
					 */
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			}
			
			//System.out.println("inputDirectoryName FILE=>"+inputDirectoryName);

		}
	   }

		FileUtils.deleteDirectory(new File(savePath));
		List<String> deletedFiles = null;

		log.info("Report Name : " + request.getParameter("reportName"));
		//System.out.println("reportName="+reportName);
		if (!reportName.equals(null)) {
			if (reportName.equals("accumulator")) {
				uId = "U06";
				utilxslFile = ACCUMULATOR_XSL;
				ReportGenerator.exceuteMap(inputDirectoryName,OUTPUT_DIRECTORY_NAME, utilxslFile);
				// utilxslFile = "C:\\resource\\AccumUtility.xsl";
				deletedFiles = FileUtils.deletedFileList(inputDirectoryName);
			} else if (reportName.equals("mrsgenerator")) {    
				uId = "U13";
				String utilInputxsl = MRSGENERATOR_INPUTXSL;
				String utilOutputxsl = MRSGENERATOR_OUTPUTXSL;
				String utilMetaxsl = MRSGENERATOR_METAXSL;
				/*
				 * String utilInputxsl = "C:\\resource\\input.xsl"; String
				 * utilOutputxsl = "C:\\resource\\output.xsl"; String utilMetaxsl =
				 * "C:\\resource\\meta.xsl";
				 */
				deletedFiles = FileUtils.deletedFileList(inputDirectoryName);
				if(mapName!=null || fileName!=null) {
				ReportGenerator.generateMRS(inputDirectoryName,OUTPUT_DIRECTORY_NAME, utilInputxsl, utilOutputxsl,utilMetaxsl, excludeflddef, includeinactivefld, request.getParameter("usageType"));
				}
			}
			/*else {
			}
				ReportGenerator.exceuteMap(inputDirectoryName,OUTPUT_DIRECTORY_NAME, utilxslFile);
			}*/

		}
		
		DateFormat dateFormat = new SimpleDateFormat("MMddyyyy");
		Date date = new Date();
		String todDate = dateFormat.format(date);
		
		String zipFileName = ZipDirectory.zipIn(OUTPUT_DIRECTORY_NAME);
		//System.out.println("zipFileName=>"+zipFileName);
        //updated rename logic for tech mrs
		
		/*if (reportName.equals("mrsgenerator") && (!zipFileName.equals(null))) {			
			String newName = null;
			
			if(mapName!=null && ext.trim().equals(".zip")) {
			if(mapName.endsWith(".map"))
				mapName=mapName.replace(".map", "");
			else if(mapName.endsWith(".mxl"))
				 mapName=mapName.replace(".mxl", "");
 
			newName = OUTPUT_DIRECTORY_NAME + File.separator + "MRS_" + mapName + "_" + todDate + ".xlsx";
			}
			else if(fileName!=null && !ext.trim().equals(".zip")) {
				if(fileName.endsWith(".map"))
					fileName=fileName.replace(".map", "");
				else if(fileName.endsWith(".mxl"))
					fileName=fileName.replace(".mxl", "");
	 
				newName = OUTPUT_DIRECTORY_NAME + File.separator + "MRS_" + fileName + "_" + todDate + ".xlsx";
			}
			if(newName!=null) {
			new File(zipFileName).renameTo(new File(newName));
			zipFileName = newName;
			File outputXLS = new File(OUTPUT_DIRECTORY_NAME+File.separator+"output.xml");
			if(request.getParameter("usageType").equals("development") && outputXLS.exists()) {
			 if(!validateSheet.checkXLSX(outputXLS.getAbsolutePath())) {
				 //System.out.println("Add notes not present");
				 reportPath = OUTPUT_DIRECTORY_NAME + File.separator + "ADD_NOTES_MISSING_REPORT_"  + todDate + ".html";
				 if(reportPath!=null) {
				 new File(zipFileName).renameTo(new File(reportPath));
				 zipFileName = reportPath;
				 }
			  }
		    }
		  }
		}*/
		HashMap<String,Integer> input_map = new HashMap<String,Integer>();
		//System.out.println("inputDirectoryName=>"+inputDirectoryName);
		if (/*!reportName.equals("file_transmission")*/ext.length() > 0) {

		File[] listOfFiles = new File(inputDirectoryName).listFiles();
		
		boolean isDuplicateMXL = false;
		String map_name = null;
		
		for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		    	  if (listOfFiles[i].getName().toLowerCase().endsWith(".map")) {
		    		  map_name = listOfFiles[i].getName().replace(".map", "");
		    	  }
		    	  
		    	  if(map_name!=null) {
		    	  if (listOfFiles[i].getName().toLowerCase().endsWith(".mxl") && listOfFiles[i].getName().replace(".mxl", "").equals(map_name)) 
		    		  isDuplicateMXL =  true;
		    	  }
		      }
		}
		//System.out.println("isDuplicateMXL=>"+isDuplicateMXL);
		
        // This code is used to update files table with input files
		
		for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		    	  
		    	  double bytes = listOfFiles[i].length();
				  double kilobytes = (bytes / 1024);
				  int kb = (int) Math.round(kilobytes);
				  
                  if (reportName.equals("multiplefiletester")) {
					  if (!listOfFiles[i].getName().toLowerCase().contains("ibm_output") && !listOfFiles[i].getName().toLowerCase().contains("rsc_output") && !listOfFiles[i].getName().toLowerCase().contains("translation_report"))
			          input_map.put(listOfFiles[i].getName(), kb);
				  }
				  else if (reportName.equals("xsdcreate")) {
					      if (!listOfFiles[i].getName().toLowerCase().endsWith(".xsd"))
						  input_map.put(listOfFiles[i].getName(), kb);					  
				  }
				  else if ((reportName.equals("accumulator")||reportName.equals("codeList")||/*reportName.equals("error20001")||*/reportName.equals("downgrade")||reportName.equals("mrsgenerator")||reportName.equals("empty_tag_generator")) && isDuplicateMXL==true) {
				        if (listOfFiles[i].getName().toLowerCase().endsWith(".mxl"))
					        input_map.put(listOfFiles[i].getName(), kb);					  
				  }
				  else if(isCorrectData && reportName.equals("error20001")) {
					    if (listOfFiles[i].getName().toLowerCase().endsWith(".mxl") && listOfFiles[i].getName().toLowerCase().contains("before"))
					        input_map.put(listOfFiles[i].getName().toLowerCase().replace("_before", ""), kb);	
				  }
				  else if(reportName.equals("gentran_migration_tool")) {
					    if (listOfFiles[i].getName().toLowerCase().endsWith(".mxl") && !listOfFiles[i].getName().toLowerCase().contains("migratecopy"))
					    	input_map.put(listOfFiles[i].getName(), kb);	
				  }
				  /*else if(reportName.equals("qa_tool")) {
					   if (listOfFiles[i].getName().toLowerCase().endsWith(".zip"))
					   input_map.put(listOfFiles[i].getName(), kb);					  
				  }*/
				  else if(!reportName.equals("error20001") && !reportName.equals("qc_tool") && !reportName.equals("qa_tool"))
				  input_map.put(listOfFiles[i].getName(), kb);
		      }
		  }
		}
		
		String user = "rajrobin";//session.getAttribute("name").toString();
		String email = "rajrobin@in.ibm.com";//session.getAttribute("email").toString();
		String noOfFiles = String.valueOf(UnzipFile.countFilesInDirectory(new File(OUTPUT_DIRECTORY_NAME)));
		String noOfInputFiles = String.valueOf(input_map.size());
		String project = null;
	    
		  //System.out.println("IP CNT=>"+noOfInputFiles);

		   /*for (Map.Entry<String,Integer> entry : input_map.entrySet()) {
		    	String key = entry.getKey();
		    	int value = entry.getValue();
		    	System.out.println("FILENAME=>"+key + " FILESIZE=>"+value+"\n");
		    }*/
		int ticket = 0;
		String fid;
		if (!reportName.equals("file_transmission")) {
		if (request.getParameter("usageType").equals("development")) 
			project = selectedValue;
		 else 
			ticket = Integer.parseInt(selectedValue);
		}
		
		DataService service;
		try {
			service = new DataServiceImpl(jdbcClassName, jdbcUrl, jdbcSchemaName, jdbcUserName, jdbcPassword);

			FileUtils.deleteFile(new File(filePath));
			//System.out.println("DELETING IP DIR..");
			if(inputDirectoryName!=null)
			FileUtils.deleteDirectory(new File(inputDirectoryName)); 
			//System.out.println("DELETED IP DIR..");
			fid = service.getFID();
			//updated condition for deletedFiles=0
			if (reportName.equals("file_transmission")) {
				//System.out.println("ext***"+ext);
				if(ext.length()==0)
				service.insertDasboardDetails(user, email, uId, ticket,project,"0",groupName);	
				else {
				service.insertDasboardDetails(user, email, uId, ticket,project, "1",groupName);
				
				for (Map.Entry<String,Integer> entry : input_map.entrySet()) {
					service =new DataServiceImpl(jdbcClassName, jdbcUrl,jdbcSchemaName, jdbcUserName, jdbcPassword);
			    	String filename = entry.getKey();
			    	int filesize = entry.getValue();
			    	//System.out.println("FILENAME=>"+filename + " FILESIZE=>"+filesize+"\n");
			    	// insert data to File table			    	
			    	 if(!reportName.equals("qc_tool") && !reportName.equals("qa_tool")) 
					 service.insertFileDetails(fid, filename, filesize);
			      }
			   }
			}
			if(deletedFiles!=null) {
			if (Integer.parseInt(noOfFiles) > 0 && deletedFiles.size() == 0) {
				 
				 if(reportName.equals("qc_tool")) {
					  if(isQCPassed)
				      service.insertDasboardDetails(user, email, uId, ticket,project, "1",groupName);
				 }
				 else if(reportName.equals("qa_tool")) 
				 service.insertDasboardDetails(user, email, uId, ticket,project,inputDir.size()+"",groupName);
				 else
				 service.insertDasboardDetails(user, email, uId, ticket,project, noOfInputFiles,groupName);
				 
				request.setAttribute(
						"message",
						"<a  style=\"color: red;\" href=\"DownloadFileServlet?fileName="
								+ zipFileName + "\"><u>Download "
								+ reportName.toUpperCase() + " report</u>"
								+ "</a>");
                System.out.println(zipFileName);
				List<File> fileList = new ArrayList<File>();
				ZipDirectory.getAllFiles(new File(OUTPUT_DIRECTORY_NAME),
						fileList);
				
				/*ListIterator<File> fileListItr = fileList.listIterator();
				while (fileListItr.hasNext()) {
					service =new DataServiceImpl(jdbcClassName, jdbcUrl,
							jdbcSchemaName, jdbcUserName, jdbcPassword);
					File file = fileListItr.next();
					// insert data to File table
					service.insertFileDetails(fid, file.getName());
				}*/
				
				for (Map.Entry<String,Integer> entry : input_map.entrySet()) {
					service =new DataServiceImpl(jdbcClassName, jdbcUrl,jdbcSchemaName, jdbcUserName, jdbcPassword);
			    	String filename = entry.getKey();
			    	int filesize = entry.getValue();
			    	//System.out.println("FILENAME=>"+filename + " FILESIZE=>"+filesize+"\n");
			    	// insert data to File table			    	
			    	 if(!reportName.equals("qc_tool") && !reportName.equals("qa_tool")) 
					 service.insertFileDetails(fid, filename, filesize);
			    }				
				
				if(reportName.equals("qc_tool") && isQCPassed) {
				service =new DataServiceImpl(jdbcClassName, jdbcUrl,jdbcSchemaName, jdbcUserName, jdbcPassword);
			    service.insertFileDetails(fid, fileName, zipSize);
				}
				
				/*for(int i=0;i<inputDir.size();i++) 
				System.out.println(inputDir.get(i) + " = " +inputDirSize.get(i));*/
				
				if(reportName.equals("qa_tool")) {
					
					for(int i=0;i<inputDir.size();i++) {
					service =new DataServiceImpl(jdbcClassName, jdbcUrl,jdbcSchemaName, jdbcUserName, jdbcPassword);
					service.insertFileDetails(fid, inputDir.get(i)+".zip", inputDirSize.get(i));
					}
					
				}

			} else {
				request.setAttribute("message","There were no valid input files in zip folder");
			}
		  }
			
		} catch (Exception e) {
			throw new ServletException(e);
		}

		if (!reportName.equals(null)) {// This is used for
										// code,error2001,accumulator
			// code,error2001,accumulator
            if(deletedFiles!=null) {
			if (deletedFiles.size() > 0) {/*
										 * request.setAttribute(
										 * "filesNotProcessed" ,
										 * "Files not processed due to invalid format :"
										 * + "<strong>" + deletedFiles +
										 * "</strong>");
										 */
				String deletedFilesinHTML = "";
				for (int i = 0; i < deletedFiles.size(); i++) {
					deletedFilesinHTML += "<p style='color: red;'>"
							+ "<strong>" + deletedFiles.get(i) + "</strong>"
							+ "<p/>";
				}
				request.setAttribute("filesNotProcessed",
						"<h3>Files not processed due to invalid format :</h3><p></p>"
								+ deletedFilesinHTML);

			}
		  }

		}
		getServletContext().getRequestDispatcher("/reportOuput.jsp").forward(
				request, response);

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("Upload survlet called");
		getServletContext().getRequestDispatcher("/index.jsp").forward(req,
				resp);

	}

	/**
	 * Extracts file name from HTTP header content-disposition
	 * 
	 * @param part
	 * @return
	 */
	private String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {

			if (s.trim().startsWith("filename")) {

				return s.substring(s.indexOf("=") + 2, s.length() - 1);
			}
		}
		return "";
	}
	
	private  boolean isTxoLatest(String filePath) {
		boolean isTxoLatest = false;
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		
		File ip_file = new File(filePath);
		String fileName = ip_file.getName();
		//System.out.println("fileName=>"+fileName);
		Date map_date = null;
		Date mmc_date = null;
		Date lnx_date = null;
		Date txo_date = null;
		
		if(fileName.length() > 13 && fileName.startsWith("IBM_")) {
			
		String str1 = fileName.substring(13,fileName.length());
		String map_name = str1.substring(0, str1.length() - 13);
		//System.out.println("map_name=>"+map_name);
		
		
		ZipFile zf = null;
		try {
			zf = new ZipFile(filePath);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		Enumeration entries = zf.entries();
		while (entries.hasMoreElements()) {
           ZipEntry ze = (ZipEntry) entries.nextElement();
           
           try {
        	    if(ze.getName().contains(map_name+".map") || ze.getName().contains(map_name+".mxl") || ze.getName().contains(map_name+".mms"))
        	    	map_date = sdf.parse(sdf.format(ze.getTime()));
        	    if(ze.getName().contains(map_name+".txo") || ze.getName().contains(map_name+".tpl"))
        	    	txo_date = sdf.parse(sdf.format(ze.getTime()));
        	    if(ze.getName().contains(map_name+".mmc"))
        	    	mmc_date = sdf.parse(sdf.format(ze.getTime()));
        	    if(ze.getName().contains(map_name+".lnx"))
        	    	lnx_date = sdf.parse(sdf.format(ze.getTime()));
        	    
        	    if(map_date!=null && txo_date!=null && (mmc_date==null && lnx_date==null)) {
        	    if (txo_date.compareTo(map_date) > 0 || txo_date.equals(map_date))
        	       isTxoLatest = true;
        	    }
        	    
        	    if(mmc_date!=null && lnx_date!=null && map_date!=null) {
        	    if (mmc_date.compareTo(map_date) > 0 || lnx_date.equals(map_date))
 	        	    isTxoLatest = true;	
        	    }     	    
				//System.out.println("FILE=>"+ze.getName() + "  LAST MODIFIED=>"+ sdf.parse(sdf.format(ze.getTime())) );
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
	  }
		
		return isTxoLatest;
	}

}