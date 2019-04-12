package com.ibm.mapping.qc;

import java.io.BufferedWriter;

/**
 * @author Sanket.
 * 
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.FileHandler;
import org.apache.log4j.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ibm.mapping.util.*;
import com.ibm.mapping.bean.*;
import com.ibm.mapping.servlet.*;
import com.ibm.bluepages.BPResults;
import com.ibm.bluepages.BluePages;


public class QC_Tester {
	//log variable 
	static Logger log = Logger.getLogger(QC_Tester.class.getName());

	static String mapName = "";
	static String path = "";
	static String name_of_map = "";
	static String map_name_Stanley = "";
	static File[] listFiles = null;
	static boolean is_QCDB_Present;
	static boolean issue_qcmaster;
	static boolean isQCPassed;
	ArrayList<String> input_file_list= new ArrayList<String>();
	static File[] listFiles1 = null;
	static Date map_date = null;
	static Date mxl_date = null;
	static Date txo_date = null;
	static Date mmc_date = null;
	static Date lnx_date = null;
	static int mapCount = 0;
	static int inputFileCount = 0;
	static int outputFileCount = 0;
	static int modified_outputFileCount = 0;
	static int modified_reportFileCount = 0;
	static int before_outputFileCount = 0;
	static int after_outputFileCount = 0;
	static int before_reportCount = 0;
	static int after_reportCount = 0;
	static int reportFileCount = 0;
	static int emptyFileCount = 0;
	static int map_cnt = 0;
	static int mxl_cnt = 0;
	static int mrs_cnt = 0;
	static int compileFileCount_WTX = 0;
	static String isEmpty = "YES";
	static String isGentran = "N";
	static String isWTX = "N";
	static String isMXL = "N";
	static String ispdf = "N";
	static String isDuplicateMap = "N";
	static String isfolderEmpty = "N";
	static String isImportMap="N";
	static String map_name = "";
	static String wp_name = "";
	static String ticket_no = "";
	static int isInternal_WP;
	static String missing_str = "";
	public static final String[] map_types = { "TRANSLATION", "PREPROCESSOR","POSTPROCESSOR","DOC-EXTRACT","TYPING","HR","PASSTHROUGH" };
	static String before_report = "n";
	static String after_report = "n";
	private static String user = "";

    public static boolean generateQCReport(String inputDirectory,String outputDirectory,String req_typ,String email_id,String gsa_id, String gsa_pwd,boolean isTxoLatest)
	{
    	    req_typ = req_typ.toUpperCase();
    	    //System.out.println("req_typ=>"+req_typ);
    	    //System.out.println("email_id=>"+email_id);
    	    //System.out.println("gsa_id=>"+gsa_id);
    	    //System.out.println("gsa_pwd=>"+gsa_pwd);
    	    
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
		    validateWP(inputDirectory,outputDirectory,req_typ,email_id,gsa_id,gsa_pwd,isTxoLatest);	
		    //System.out.println("isQCPassed=>"+isQCPassed);
	  		}else {
	  			log.error("The input directory '" + inputDirectory
	  					+ "' does not exists.");
	  		}
	  		
	  		return isQCPassed;	  	
	}
	
	
	public QC_ValidationResult checkFolder(File folder,String req_typ,String wp_name,boolean isTxoLatest) {
		//System.out.println("fileName: "+folder.getName());
		//System.out.println("In checkFolder()=>req_typ=>"+req_typ);
		QC_ValidationResult result = new QC_ValidationResult();

		isInternal_WP = folder.getName().indexOf("IBM_");		

		if (isInternal_WP != -1) {
			// //System.out.println("Internal WP");
			//wp_name=folder.getName();
			String str1 = wp_name.substring(13,wp_name.length());
			map_name = str1.substring(0, str1.length() - 9);
			map_name_Stanley=str1.substring(0, str1.length());
			////System.out.println("map_name: "+map_name);
			ticket_no = wp_name.substring(4, 12);
			////System.out.println("ticket_no "+ticket_no);
		}
		
		String WPConventionFollowed = isWPNamingConvention(wp_name);
		result.setWPConventionFollowed(WPConventionFollowed);
		
		 listFiles = folder.listFiles();
		 if (listFiles != null) {
		    for (File file : listFiles) {
			////System.out.println("***2 "+file.getName());
			File curr_file = new File(file.getAbsolutePath());
			double bytes = curr_file.length();
			double kilobytes = (bytes / 1024);
			////System.out.println("FILE NAME => "+file.getName() + " SIZE =>"+kilobytes);
			if (kilobytes == 0) {
				emptyFileCount=emptyFileCount+1;
				//System.out.println("Empty files: "+file.getName());
			}
			
			checkFile(file, result,req_typ);
		   }
		 }
		    
		if (listFiles != null) {
			for (File file : listFiles) {
				String path = file.getName();
				if ((path.contains(map_name+".mxl") || path.contains(map_name+".map") || path.contains(map_name_Stanley+".map") || path.contains(map_name+".mms"))&& !path.contains(map_name+"_SI.map")) {
					mapName = path.replaceAll(".mxl", "");
					mapName = mapName.replaceAll(".map", "");
					mapName = mapName.replaceAll(".mms", "");
					break;
				}
			}
		}
		    
		//System.out.println("mapName=>"+mapName);
		//System.out.println("map_name=>"+map_name);
		//System.out.println("map_name_Stanley=>"+map_name_Stanley);
		
		if(mapName.toLowerCase().startsWith("stan")) {   // Stanley map=> map name_current date
			if(map_name_Stanley.equalsIgnoreCase(mapName))
			    result.setMapNameSame(true);
		}
		else {
			 if(map_name.equalsIgnoreCase(mapName))
			    result.setMapNameSame(true);
		}
			
		//System.out.println("No. of Empty files: "+emptyFileCount);
        if(emptyFileCount>0)
        	result.setFileEmpty("NO");
        else
        	result.setFileEmpty("YES");
        
        //System.out.println("compileFileCount_WTX=>"+compileFileCount_WTX);
		if(compileFileCount_WTX==2)
		result.setCompileFileExists_WTX(true);
		
		if(map_date!=null && txo_date!=null && mxl_date==null) {
		if (txo_date.compareTo(map_date) > 0 || txo_date.equals(map_date)) {
		//System.out.println("txo date is greater than map date");
		result.setLatestTxoFileExists(true);
		}
		//else
		//System.out.println("map date is greater than txo date");
		}
		
		if(mxl_date!=null && txo_date!=null && map_date==null) {
		if (txo_date.compareTo(mxl_date) > 0 || txo_date.equals(mxl_date)) {
		//System.out.println("txo date is greater than mxl date");
		result.setLatestTxoFileExists(true);
		}
		//else
		//System.out.println("mxl date is greater than txo date");
		}
		
		if(map_date!=null && mmc_date!=null && lnx_date!=null) {
		if ((mmc_date.compareTo(map_date) > 0 || lnx_date.compareTo(map_date) > 0) || ( mmc_date.equals(map_date)||lnx_date.equals(map_date) ) ) {
		//System.out.println("mmc date,lnx date is greater than map date");
		result.setLatestTxoFileExists_WTX(true);
		}
		//else
		//System.out.println("map date is greater than mmc date/lnx date");
		}
		
		//System.out.println("entry.ticketNumber=>"+result.getTicketNo());
		
		if(result.getTicketNo()!=null) {
		if(result.getTicketNo().equals(ticket_no))
		  result.setTicketNoSame(true);
		}
		
		if(result.getPresessionUpdate()=="NO")
		   result.setTicketNoSame(false);
		
		if(isGentran=="Y") {
		  result.setRpConventionFollowed("YES");
		  //result.setReportFileCount(outputFileCount+"");
		}
		
		if(isWTX=="Y") {
			  result.setFileEmpty("YES");
			  result.setMrsConventionFollowed("YES");
			  result.setMrsFileExists(true);
			  result.setMrsXLSFormat("YES");
			  result.setOpConventionFollowed("YES");
			  result.setRpConventionFollowed("YES");
			  result.setReportFileCount(outputFileCount+"");
			  result.setInputFileCount(outputFileCount+"");
		}
		
		if(req_typ.equals("MCR") && isGentran=="N"&& isWTX=="N") {
		if(map_name.toLowerCase().startsWith("whir"))
		  result.setOpConventionFollowed("YES");
		}
		
	    if(mapName.toLowerCase().startsWith("sial") && isGentran=="N" && isWTX=="N" && ispdf=="Y") {
	    //System.out.println("*******TEST 2******");
		result.setMrsConventionFollowed("YES");
	    result.setMrsFileExists(true);
	    result.setMrsXLSFormat("YES");
	  }
	    
		//System.out.println("inputFileCount=>"+inputFileCount);
		//System.out.println("getoutputFileCount()=>"+result.getOutputFileCount());
		//System.out.println("getBefore_outputfileCount()=>"+result.getBefore_outputfileCount());
		//System.out.println("getAfter_outputfileCount()=>"+result.getAfter_outputfileCount());
		
		if (inputFileCount==0) {
		    if(isWTX=="N") {
			result.setOpConventionFollowed("YES");
			result.setRpConventionFollowed("YES");
		   }
		}
		
		int op_cnt = Integer.parseInt(result.getOutputFileCount());
		int before_cnt = Integer.parseInt(result.getBefore_outputfileCount());
		int after_cnt = Integer.parseInt(result.getAfter_outputfileCount());
		int rp_cnt = Integer.parseInt(result.getReportFileCount());
		
		//System.out.println("rp_cnt=>"+rp_cnt);
		//if(!map_name.toLowerCase().startsWith("whir")) {
		if(op_cnt != rp_cnt) {

			if(rp_cnt>op_cnt)
			result.setOpConventionFollowed("NO");
			
			if(before_cnt > 0 || after_cnt > 0) {
			if(op_cnt>rp_cnt && op_cnt != (2*rp_cnt) )
			result.setRpConventionFollowed("NO");
			
			if(rp_cnt == inputFileCount && rp_cnt > 0 && inputFileCount > 0 )
			   result.setRpConventionFollowed("YES");
			}
			else if(op_cnt>rp_cnt)
				 result.setRpConventionFollowed("NO");
		}
		else 
		if(inputFileCount>rp_cnt && inputFileCount>op_cnt) {
			   result.setOpConventionFollowed("NO");
			   result.setRpConventionFollowed("NO");
		}
		//}
		//System.out.println("before_reportCount=>"+before_reportCount);
		//System.out.println("after_reportCount=>"+after_reportCount);
		
		if(before_reportCount == after_reportCount && before_reportCount == inputFileCount && before_reportCount > 0 && inputFileCount > 0  && after_reportCount > 0 )
		   result.setRpConventionFollowed("YES");
		else if(before_reportCount != after_reportCount)
		       result.setRpConventionFollowed("NO");
		
		if(isGentran=="Y") 
		result.setRpConventionFollowed("YES");
		
		if(isTxoLatest)
		result.setLatestTxoFileExists(true);
		else
		result.setLatestTxoFileExists(false);
			
		return result;
	}

	private void checkFile(File file, QC_ValidationResult result,String req_typ) {
		String fileName = file.getName();
		//System.out.println("fileName=>"+fileName);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss"); 
		//result.setOpConventionFollowed("NO");
		//result.setRpConventionFollowed("NO");
		
		if (isInternal_WP != -1)
			result.setTypeWP("INTERNAL");
		else
			result.setTypeWP("EXTERNAL");

		// MRS is optional for this type of maps so MRS checks also will be
		// optional
		      if ( map_name.toLowerCase().contains("typ") 
				|| map_name.toLowerCase().contains("typing")
				|| map_name.toLowerCase().contains("docextract")
				|| map_name.toLowerCase().contains("docext")
				|| map_name.toLowerCase().contains("preproc")
				|| map_name.toLowerCase().contains("preprocessor")
				|| map_name.toLowerCase().contains("pre_processor")
				|| map_name.toLowerCase().contains("postproc")
				|| map_name.toLowerCase().contains("postprocessor")
				|| map_name.toLowerCase().contains("post_processor")
				|| map_name.toLowerCase().contains("passthrough")
				|| map_name.toLowerCase().contains("hr")) {	
			//System.out.println("TEST11111111111111111111");
			/*if (file.getName().startsWith("MRS_")) {
				result.setMrsFileExists(true);				
				String mrsConventionFollowed = isMRSNamingConvention(file.getName());
				//System.out.println("**********12345** => "+ mrsConventionFollowed);
				result.setMrsConventionFollowed(mrsConventionFollowed);

				String mrsXLSFormat = isMrsXLSFormat(file.getName());
				result.setMrsXLSFormat(mrsXLSFormat);
			} else*/ //if (result.getMrsConventionFollowed() == null) {
			 if (mrs_cnt==0) {
				//System.out.println("TEST22222222222222222222");
				result.setMrsFileExists(true);
				result.setMrsConventionFollowed("YES");
				result.setMrsXLSFormat("YES");
			}
		}
		
		
		
	   if (fileName.toLowerCase().endsWith(".xml") || fileName.toLowerCase().endsWith(".txt") || fileName.toLowerCase().endsWith(".edi") || fileName.toLowerCase().endsWith(".dat") || fileName.toLowerCase().endsWith(".rnet") || fileName.toLowerCase().endsWith(".unk") || fileName.toLowerCase().endsWith(".csv") || fileName.toLowerCase().endsWith(".flt") || fileName.toLowerCase().endsWith(".idoc") || fileName.toLowerCase().endsWith(".idc") || fileName.toLowerCase().endsWith(".810") || fileName.toLowerCase().endsWith(".850") || fileName.toLowerCase().endsWith(".856") || fileName.toLowerCase().endsWith(".gis") || fileName.toLowerCase().endsWith(".prp") || !fileName.contains(".")) {
		if( /*!fileName.toLowerCase().contains("_modified") && */ !fileName.toLowerCase().contains("_ibm_output") && !fileName.toLowerCase().contains("translation_report") && !fileName.toLowerCase().contains("rsc_notes")&&!fileName.toLowerCase().contains("ptf")&&!fileName.toLowerCase().contains("p2f")&&!fileName.toLowerCase().contains("ibm_output_afterchange")&&!fileName.toLowerCase().contains("ibm_output_beforeChange")&&!fileName.toLowerCase().contains("output")&&!fileName.toLowerCase().contains("table")&&!fileName.toLowerCase().contains("lookup")&&!fileName.toLowerCase().contains("expected")&&!fileName.toLowerCase().contains("codelist")&&!fileName.toLowerCase().contains("code_list")&&!fileName.toLowerCase().contains("maptesttxresult")&&!fileName.toLowerCase().contains("maptesttxreport")&&!fileName.toLowerCase().contains("report")&&!fileName.toLowerCase().contains("processdata")&&!fileName.toLowerCase().contains("process_data")&&isWTX=="N") {
		inputFileCount++;
		input_file_list.add(fileName.toLowerCase());
	   }
		result.setInputFileCount(inputFileCount+"");
		//System.out.println("INPUT"+inputFileCount);
	   }


		// //System.out.println("fileName: "+fileName);
		if (fileName.startsWith("MRS_")) {

			if (!map_name.toLowerCase().contains("typ") || !map_name.contains("typing")
					|| !map_name.toLowerCase().contains("docextract")
					|| !map_name.toLowerCase().contains("docext")
					|| !map_name.toLowerCase().contains("preproc")
					|| !map_name.toLowerCase().contains("preprocessor")
					|| !map_name.toLowerCase().contains("pre_processor")
					|| !map_name.toLowerCase().contains("postproc")
					|| !map_name.toLowerCase().contains("postprocessor")
					|| !map_name.toLowerCase().contains("post_processor")
					|| !map_name.toLowerCase().contains("passthrough")
					|| !map_name.toLowerCase().contains("hr")) {	
				result.setMrsFileExists(true);
				String mrsConventionFollowed = isMRSNamingConvention(fileName);
				result.setMrsConventionFollowed(mrsConventionFollowed);

				String mrsXLSFormat = isMrsXLSFormat(fileName);
				result.setMrsXLSFormat(mrsXLSFormat);
			}

		} else if ( ( fileName.toLowerCase().contains("ibm_output_afterchange") || fileName.toLowerCase().contains("ibm_output_after_change") ) && req_typ.equals("MCR")) {
			result.setAfterIbmFileExists(true);
			result.setOpConventionFollowed("YES");
			outputFileCount++;
			after_outputFileCount++;
			result.setAfter_outputfileCount(after_outputFileCount+"");
			//outputFileCount++;
			//result.setInputFileCount(outputFileCount + "");
			result.setOutputFileCount(outputFileCount + "");
		} else if (  ( fileName.toLowerCase().contains("ibm_output_beforechange") || fileName.toLowerCase().contains("ibm_output_before_change") )  && req_typ.equals("MCR")) {
			result.setBeforeIbmFileExists(true);
			result.setOpConventionFollowed("YES");
			outputFileCount++;
			before_outputFileCount++;	
			result.setBefore_outputfileCount(before_outputFileCount+"");
			result.setOutputFileCount(outputFileCount + "");
		} else //if ((fileName.endsWith(".map") || fileName.endsWith(".mms")) && !fileName.contains("_SI.map")) {
			if (fileName.equalsIgnoreCase(map_name+".map") || fileName.equalsIgnoreCase(map_name+".mms") || fileName.equalsIgnoreCase(map_name_Stanley+".map")) {
			mapCount=mapCount+1;
			result.setMapOrMxlFileExists(true);
			try {
				map_date=sdf.parse(sdf.format(file.lastModified()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			//System.out.println("Last modified date/time of map=>" + map_date);	
		} else //if (fileName.endsWith(".mxl")) {
			if (fileName.equalsIgnoreCase(map_name+".mxl") || fileName.equalsIgnoreCase(map_name+"_SI.mxl") || fileName.equalsIgnoreCase(map_name_Stanley+"_SI.mxl")) {
			mapCount=mapCount+1;
			result.setMapOrMxlFileExists(true);
			QC_MxlParser.parse(file, result,isGentran);
			try {
				if (fileName.equalsIgnoreCase(map_name+".mxl"))
				mxl_date=sdf.parse(sdf.format(file.lastModified()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			//System.out.println("Last modified date/time of mxl=>" + mxl_date);	
		} else //if (fileName.endsWith(".txo") || fileName.endsWith(".tpl")) {
			if (fileName.equalsIgnoreCase(map_name+".txo") || fileName.equalsIgnoreCase(map_name+".tpl") || fileName.equalsIgnoreCase(map_name_Stanley+".tpl")) {
			result.setTxoFileExists(true);
			try {
				txo_date=sdf.parse(sdf.format(file.lastModified()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			//System.out.println("Last modified date/time of txo=>" + txo_date);
		} else //if (fileName.endsWith(".mmc") || fileName.endsWith(".lnx")) {
			if (fileName.equalsIgnoreCase(map_name+".mmc") || fileName.equalsIgnoreCase(map_name+".lnx")) {
			if(fileName.contains(map_name))
			compileFileCount_WTX=compileFileCount_WTX+1;
			
			try {
				if(fileName.endsWith(".mmc"))
				mmc_date=sdf.parse(sdf.format(file.lastModified()));
				
				if(fileName.endsWith(".lnx"))
				lnx_date=sdf.parse(sdf.format(file.lastModified()));
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
			//System.out.println("Last modified date/time of mmc=>" + mmc_date);
			//System.out.println("Last modified date/time of lnx=>" + lnx_date);
		} else if ( fileName.toLowerCase().contains("_ibm_output") && (!fileName.toLowerCase().contains("before")&&!fileName.toLowerCase().contains("after")&&!fileName.toLowerCase().contains("ibm_output_afterchange")&&!fileName.toLowerCase().contains("ibm_output_beforechange")&&!fileName.toLowerCase().contains("preprocessor")&&!fileName.toLowerCase().contains("postprocessor")&&!fileName.toLowerCase().contains("docextract")&&!fileName.toLowerCase().contains("typing")) ) {
			//if ( fileName.toLowerCase().contains("_ibm_output") && (!fileName.toLowerCase().contains("before")&&!fileName.toLowerCase().contains("after")&&!fileName.toLowerCase().contains("ibm_output_afterchange")&&!fileName.toLowerCase().contains("ibm_output_beforechange")&&!fileName.toLowerCase().contains("preprocessor")&&!fileName.toLowerCase().contains("postprocessor")) ) //{
				   //System.out.println("************ TEST *******************");
				    result.setOpConventionFollowed("YES");
			        outputFileCount++;
			        if(fileName.toLowerCase().contains("modified"))
			           modified_outputFileCount++;
			        
				    result.setOutputFileCount(outputFileCount + ""); //}
		} 
		else if ( fileName.toLowerCase().contains("translation_report") && (!fileName.toLowerCase().contains("before")&&!fileName.toLowerCase().contains("after"))&& req_typ.equals("NEW")) {
			//System.out.println("NEW=>TX REPORT");
			result.setRpConventionFollowed("YES");
			//System.out.println(fileName);
			reportFileCount++;
			result.setReportFileCount(reportFileCount + "");
		}
		else if ( fileName.toLowerCase().contains("translation_report") && req_typ.equals("MCR")) {
			if(( fileName.toLowerCase().contains("translation_report_beforechange") || fileName.toLowerCase().contains("translation_report_before_change") )) {
				before_report="y";
				//reportFileCount++;
				before_reportCount = before_reportCount + 1;
			}
			if( ( fileName.toLowerCase().contains("translation_report_afterchange") || fileName.toLowerCase().contains("translation_report_after_change") )) {
				after_report="y";
				//reportFileCount++;
				after_reportCount = after_reportCount + 1;
			}
			
			/* if(before_report=="y" && after_report=="y") {
			   result.setRpConventionFollowed("YES");
			   //reportFileCount++;
			}
			else*/
			   if(before_report!="y" && after_report!="y") {
			   result.setRpConventionFollowed("YES");
			   //reportFileCount++;
			   }
			
			   if(fileName.toLowerCase().contains("modified"))
		           modified_reportFileCount++;
			   
			reportFileCount++;
			//System.out.println(fileName);
			result.setReportFileCount(reportFileCount + "");
			//System.out.println("before_report " + before_report);
			//System.out.println("after_report " + after_report);
		}
		 else if (fileName.endsWith(".pdf")) {
			 //System.out.println("*******TEST 1******");
			 ispdf="Y";
			}
		 /*else if (inputFileCount==0) {
			    if(isWTX=="N") {
				result.setOpConventionFollowed("YES");
				result.setRpConventionFollowed("YES");
			   }
			}*/
		
		//System.out.println("result.getOpConventionFollowed()=>"+result.getOpConventionFollowed());
		//System.out.println("result.getRpConventionFollowed()=>"+result.getRpConventionFollowed());
		//System.out.println("*** OP Cnt ***"+outputFileCount);
		//System.out.println("++++++++++ getoutputFileCount()=>"+result.getOutputFileCount());
		//System.out.println("*** RP Cnt ***"+reportFileCount);
		
	}

	public static void validateWP(String inputDirectory,String outputDirectory,String req_typ, String email_id, String gsa_id, String gsa_pwd,boolean isTxoLatest) { 
		
		Hashtable row;
		boolean isInternal_WP_1 = false;
		String map = null;
		String host = "pokgsa.ibm.com";
		int port = 22;
		String remoteDirectory = "/pokgsa/projects/s/saas_mapping_paraturezip";
		String isGSA_Upload = "N";
		String isgenMAP = "N";
		int no_of_maps = 0;
		int score_cnt = 0;
		double final_score = 0;
		int mandatory_checks = 0; 	
		int optional_checks = 0;
		listFiles = null;
		listFiles1 = null;
		map_date = null;
		mxl_date = null;
		txo_date = null;
		mmc_date = null;
		lnx_date = null;
		mapCount = 0;
		inputFileCount = 0;
		outputFileCount = 0;
		reportFileCount = 0;
		emptyFileCount = 0;
		before_outputFileCount = 0;
		after_outputFileCount = 0;
		modified_outputFileCount = 0;
		modified_reportFileCount = 0;
		before_reportCount = 0;
		after_reportCount = 0;
		map_cnt = 0;
		mxl_cnt = 0;
		mrs_cnt=0;
		compileFileCount_WTX = 0;
		isEmpty = "YES";
		isGentran = "N";
		isWTX = "N";
		isMXL = "N";
		ispdf = "N";
		isDuplicateMap = "N";
		isImportMap="N";
		isfolderEmpty="N";
		before_report = "n";
		after_report = "n";
		mapName="";
		map_name = "";
		wp_name = "";
		ticket_no = "";
		name_of_map = "";
		map_name_Stanley = "";
		   		
   		File inputDir = new File(inputDirectory);
   		//System.out.println("inputDir=>"+inputDir.getName());
   		
   		if(inputDir.exists() && inputDir.isDirectory()) {
   		  if(inputDir.listFiles().length==0) {
   		  log.error("Given workpacket is empty");
   		  return;
   		  }
   		}
   		else
   		return;
   		   		
   		try {
			  			  
			  Pattern pattern = Pattern.compile("IBM_+\\d{8}+_+(.*)",Pattern.CASE_INSENSITIVE);
			  Pattern date_pattern = Pattern.compile("\\d{8}",Pattern.CASE_INSENSITIVE);
			  DateFormat df = new SimpleDateFormat("MMddyyyy");
		      Calendar calobj = Calendar.getInstance();
		      
		      try {
		      String wp_date = null;
		      int index =  inputDir.getName().indexOf("_"); 
		      wp_name=inputDir.getName().substring(index+1, inputDir.getName().length());
		      
			  if(inputDir.getName().length() > 8)
		      wp_date = inputDir.getName().substring(inputDir.getName().length()-8);
			  
			  //System.out.println("wp_date=>"+wp_date);
			  //System.out.println("wp_name=>"+wp_name);
		      //System.out.println("current date=>"+df.format(calobj.getTime()));
			  
			  Matcher matcher = pattern.matcher(wp_name);
			  Matcher date_matcher = date_pattern.matcher(wp_date);
			  
			   if(!wp_name.contains(" ")) {			    	
				if (matcher.find() && date_matcher.find()) {
					//System.out.println("WP Pattern matched");	
					//if(folder.getName().contains(df.format(calobj.getTime())))
						isInternal_WP_1=true;
				}
			   }
			   else
			   log.info("It seems that whitespace(s) present in the specified work packet..");
		      }catch(java.lang.NullPointerException ne) {
		    	  ne.printStackTrace();
		    	  log.error(ne.getMessage());
		      }
				
				//System.out.println("isInternal_WP_1: "+isInternal_WP_1);	
				
				if(!isInternal_WP_1)    // Not Valid Internal Work Packet 
				return;
				
				String str1 = wp_name.substring(13,wp_name.length());
				name_of_map = str1.substring(0, str1.length() - 9);
				//System.out.println("name_of_map=>"+name_of_map);

				
				BPResults results;
					
				//System.out.println("email_id=>"+email_id);

				results= BluePages.getPersonsByInternet(email_id);

				// * Make sure the method didn't fail unexpectedly. 

				if (!results.succeeded()) 
	            System.err.println("BluePage Error: " + results.getStatusMsg());

					// * Display the Name for matching person. 
			    if (results.rows() == 0)
				System.err.println("No matches found.");
				else 
				  {
					for (int i = 0; i < results.rows(); i++) 
						{
							row = results.getRow(i);
							user=(String)row.get("NAME");
							//if(user.length() > 40)
							//user=user.substring(0, 40);
							//System.out.println("User Name: "+user);
						}
		         }
				
			   			
			listFiles1 = inputDir.listFiles();
			for (File file : listFiles1) {                            
				 if(file.getName().endsWith(name_of_map+".tpl") || file.getName().endsWith(map_name_Stanley+".tpl"))
					 isGentran="Y"; 
				 if(file.getName().endsWith(".mms")) 
					 map_cnt=map_cnt+1;
				 if(file.getName().endsWith(".mxl")) { 
					 map_cnt=map_cnt+1;
					 mxl_cnt=mxl_cnt+1;
				 }
				 if(file.getName().endsWith(".map")) 
					 map_cnt=map_cnt+1;
	
				 if(file.getName().endsWith(name_of_map+".mms")) 
					 isWTX="Y";
				 if(file.getName().endsWith(name_of_map+".mxl")) {
					 isMXL="Y";
					 no_of_maps=no_of_maps+1;
				 }
				 if(file.getName().endsWith(name_of_map+".map")) {
					 no_of_maps=no_of_maps+1;
				 }
				 if(file.getName().contains("_SI.map")) 
					 isgenMAP="Y";
				 if(file.getName().startsWith("MRS_"))
					 mrs_cnt=mrs_cnt+1;
			}
						
			if(map_cnt==0) 
			log.info("No map is present in the work packet folder");			  
			
			
			if(no_of_maps==2) {
			   isDuplicateMap="Y";
			   log.info("Duplicate map/mxl present in the work packet folder...please remove one of them!!!");				  
			}
			
			//System.out.println("map_cnt=>"+map_cnt);
			//System.out.println("isGentran=>"+isGentran);
			//System.out.println("isWTX=>"+isWTX);
			//System.out.println("isDuplicateMap=>"+isDuplicateMap);
			
			QC_Dumper dumper = new QC_Dumper();
						
			//if(isWTX=="N"&&isGentran=="Y"&&isMXL=="N"&&isgenMAP=="N") {
			is_QCDB_Present = dumper.is_QCDB_Present();
	    	if(isWTX=="N" && isMXL=="N" && isDuplicateMap=="N" && map_cnt>0 && mxl_cnt==0 && is_QCDB_Present==true /*&& map_typ != null*/) {
	    			    				    	   
			listFiles1 = inputDir.listFiles();
			for (File file : listFiles1) {                            
				 if(file.getName().endsWith(".map")) 
					 map=file.getName();
			}
			
			try {
			map=map.replaceAll(".map", "");
			//System.out.println("map=>>"+map);
			
			if(map.contains("_o_") || map.contains("_O_"))
				isImportMap="Y";
			
				listFiles1 = inputDir.listFiles();
				for (File file : listFiles1) {
					if(file.getName().endsWith(".map")) 
                       duplicateMAP(inputDirectory, map);
			    }

				listFiles1 = inputDir.listFiles();
				for (File file : listFiles1) {
					if(file.getName().contains("_SI.map")) {
						try {
							ProcessBuilder pb = new ProcessBuilder(Constants.MAPPER_EXE, "-s","24", inputDir + File.separator + file.getName());
							pb.start().waitFor();
						} catch (Exception e) {
							log.error(e.getMessage());
						}
				}
			  }
			 
	    	}catch(java.lang.NullPointerException ne) {
	    		log.error(ne.getMessage());				 
			 }

			}
			
			listFiles1 = inputDir.listFiles();
			for (File file : listFiles1) {                            
				 if(file.getName().endsWith("_SI.mxl")) {
					 File curr_file = new File(file.getAbsolutePath());
					 double bytes = curr_file.length();
					 double kilobytes = (bytes / 1024);
					 if(kilobytes==0) {
					 log.info("There is some issue in generating mxl from the map file");
					 deleteMaps(listFiles1,inputDir);
					 return;
					 //System.exit(0);
					}
				 }
			}
		  		
			if(isDuplicateMap=="N" && map_cnt>0 && is_QCDB_Present==true /*&& map_typ != null*/) {
				
			QC_Tester main = new QC_Tester();
			//System.out.println("In validateWP()=>req_typ=>"+req_typ);
			QC_ValidationResult result = main.checkFolder(inputDir,req_typ,wp_name,isTxoLatest);
			
			//dumper.calculateSCORE(result, map_name, req_typ, isWTX,isGentran);
			
			try{
			ArrayList<String> list = dumper.missingCHECKS(result,map_name,wp_name,req_typ,ticket_no,isWTX,isGentran);
			
			set_QCPassed(list.size());
			
			//System.out.println("Missing Checks Count=>"+list.size());
			DecimalFormat df1 = new DecimalFormat();
			df1.setMaximumFractionDigits(2);	
			 
			mandatory_checks = result.getMandatory_checks();
			optional_checks = result.getOptional_checks();
			//System.out.println("mandatory_checks=>"+mandatory_checks);
			//System.out.println("optional_checks=>"+optional_checks);
			
			 if (map_name.toLowerCase().startsWith("whir")) { // Whirlpool customer
			  if(req_typ.equals("MCR")) {
				score_cnt = (mandatory_checks+(optional_checks-1)) - list.size();                                     // -1 is done because 1 optional check is not implemented
				//System.out.println("score_cnt=>"+score_cnt);
				final_score=((double)score_cnt)/(mandatory_checks+1)*100;
				if (score_cnt < (mandatory_checks+1)) {				
				//System.out.println("Score is  "+ df1.format(final_score)+ "%...Click OK to view missing checks and try again !!!");
				}	else if (score_cnt==(mandatory_checks+1)) {
				//System.out.println("You have passed mandatory QC checks successfully!!!");
				}
			  }
			  if(req_typ.equals("NEW")) {
				score_cnt = mandatory_checks - list.size();
				//System.out.println("score_cnt=>"+score_cnt);
				final_score=((double)score_cnt)/(mandatory_checks)*100;
				if (score_cnt < (mandatory_checks)) {				
				//System.out.println("Score is  "+ df1.format(final_score)+ "%...Click OK to view missing checks and try again !!!");
				}	else if (score_cnt==(mandatory_checks)) {
				//System.out.println("You have passed mandatory QC checks successfully!!!");
				}
			  }
			} else {
			   //if(isWTX=="N") {
				score_cnt = mandatory_checks - list.size();
				//System.out.println("score_cnt=>"+score_cnt);
				final_score=((double)score_cnt)/(mandatory_checks)*100;
				//System.out.println("final_score=>"+final_score);
				if (score_cnt < (mandatory_checks)) {				
				//System.out.println("Score is  "+ df1.format(final_score)+ "%...Click OK to view missing checks and try again !!!");
				} else if (score_cnt==(mandatory_checks)) {
				//System.out.println("You have passed mandatory QC checks successfully!!!");
				}
				}
				
			if(list.size()>0) {				
			issue_qcmaster=dumper.updateDB(result,map_name,wp_name,req_typ,user,email_id,ticket_no,isWTX,list.size(),isGentran,"",final_score);
			}
			
			//System.out.println("issue_qcmaster=>"+issue_qcmaster);
			
			if(issue_qcmaster==false) {
			if(list.size()>0 && list != null) {	
			BufferedWriter writer = new BufferedWriter( new FileWriter(outputDirectory+"QC_REPORT_"+df.format(calobj.getTime())+".html") );
		    writer.write("<html><head><h3 style='background-color:yellow;width:12%;'>QC SCORE => "+df1.format(final_score)+"%</h3><h3>Missing QC Check table is given below,</h3><br/><style>table {width:50%;}table, th, td {border: 1px solid black;border-collapse: collapse;}th, td {padding: 5px;text-align: Left;}table#t01 tr:nth-child(even) {background-color: #eee;}table#t01 tr:nth-child(odd) {background-color:#fff;}table#t01 th	{background-color: black;color: white;}</style></head><body><table id='t01'><tr><th>SR.NO</th><th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;CHECK DESCRIPTION</th></tr>");
				   
			for (int i = 0; i < list.size(); i++) {				
            writer.write("<tr><td>"+(i+1)+"</td><td>"+list.get(i)+"</td></tr>");
			 }
			writer.write("</table></body></html>");
	        writer.close();
			}
            
			if(list.size()>0) {

			   listFiles1 = inputDir.listFiles();
			   deleteMaps(listFiles1,inputDir);
			   log.info("QC checks been validated and missing checks been updated to user and Database");
			   log.info("****************************** End of Execution **********************************");
			   return;
			}
			}
			
			createZIPFolder inputZIP = new createZIPFolder();
			//String wp=folder.getName().replaceAll(" ", "");
			int pos=inputDir.getAbsolutePath().indexOf(inputDir.getName());
			String dest_folderPath = outputDirectory + File.separator + wp_name.substring(0,wp_name.length())+".zip";
			String dest_folderPath_EXT = outputDirectory + File.separator + wp_name.substring(4,wp_name.length())+".zip";
			//String dest_folderPath=inputDir.getAbsolutePath().substring(0,pos)+wp_name.substring(0,wp_name.length())+".zip";
			//String dest_folderPath_EXT=inputDir.getAbsolutePath().substring(0,pos)+wp_name.substring(4,wp_name.length())+".zip";
			//System.out.println("dest_folderPath_EXT: "+dest_folderPath_EXT);
						
			  String files;
			  int no_of_zip_files = 0;
			  File op_folder = new File(outputDirectory);
			  File[] listOfFiles = op_folder.listFiles(); 
			  //System.out.println("Existing zip files:");
			  for (int i = 0; i < listOfFiles.length; i++) 
			  {
			   if (listOfFiles[i].isFile()) 
			   {
			   files = listOfFiles[i].getName();
			       if (files.endsWith(".zip")) {
			    	   //System.out.println("***ZIP****");
			       /* if(files.contains(wp_name)) {
			        	//System.out.println("***ZIP_INT****");
				       no_of_zip_files=no_of_zip_files+1;
				       //System.out.println(files);
			        }*/
			        
			        if(files.contains(wp_name.subSequence(4, wp_name.length()))) {
			        	//System.out.println("***ZIP_EXT****");
				       no_of_zip_files=no_of_zip_files+1;
				       //System.out.println(files);
			        }
			     }
			   }
			 }
			  
				listFiles1 = inputDir.listFiles();
				deleteMaps(listFiles1,inputDir);
				
			  //System.out.println("no_of_zip_files=>"+no_of_zip_files);
			  if(no_of_zip_files==0 && list.size()==0) {    // create internal and external work packet once passed mandatory checks
                     
			    	 //System.out.println("gsa_id=>"+gsa_id);
			    	 //System.out.println("gsa_pwd=>"+gsa_pwd);
			    	 
			    	 //System.out.println("dest_folderPath=>"+dest_folderPath);
			    	
			    	inputZIP.create_InternalZIP(inputDir.getAbsolutePath(),dest_folderPath,isWTX);
			    	if(isWTX=="N")
			    	inputZIP.create_ExternalZIP(inputDir.getAbsolutePath(),dest_folderPath_EXT);
			    		 
				    log.info("All mandatory QC checks been passed and zip files been created...");
				      
					      boolean gsa_auth=UploadSFTPUtil.uploadFile(host, port, gsa_id, gsa_pwd, remoteDirectory, dest_folderPath);
					      if(gsa_auth) {
					      isGSA_Upload = "Y";
					      issue_qcmaster=dumper.updateDB(result,map_name,wp_name,req_typ,user,email_id,ticket_no,isWTX,list.size(),isGentran,isGSA_Upload,final_score);
					      if(issue_qcmaster==false) {
					      //System.out.println("Latest work packet been uploaded to GSA and database been updated!!!");
					      log.info("Latest work packet been uploaded to GSA and database been updated!!!");
					      log.info("****************************** End of QC Execution **********************************");
					      return;
					       }
					      }
					      else {
					    	  issue_qcmaster=dumper.updateDB(result,map_name,wp_name,req_typ,user,email_id,ticket_no,isWTX,list.size(),isGentran,"",final_score);
					    	  BufferedWriter writer = new BufferedWriter( new FileWriter(outputDirectory+"QC_REPORT_"+df.format(calobj.getTime())+".html") );
							  writer.write("<html><head><h3 style='background-color:yellow;width:12%;'>QC SCORE => "+df1.format(final_score)+"%</h3><h3>GSA Upload : GSA Authentication Failed!!!</h3><br/></head><body></body></html>");	 
						      writer.close();
					    	  return;
					      }
				  
			   }
			  
			  }catch(SQLException se) {
            	log.error(se.getMessage());
             }
			
			}
		  
   	   } catch (Exception e1) {
   		log.error(e1.getMessage());
	} finally {
   		   
   	   }
	}

	private String isMRSNamingConvention(String fileName) {
		String mrsStr = "MRS_";
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("MMddyyyy");
		String dateS = df.format(date);
		String mrsXls = mrsStr + map_name + "_" + dateS + ".xls";
		// //System.out.println("***1 "+fileName);
		// //System.out.println("***2 "+mrsXls);
		// String mrsXlsx = mrsStr+mapName+"_"+dateS+".xlsx";
		// String isMrsNaming = "NO";
		String mrsFollowed = "";// +" ->( " +fileName +" )";
		if (fileName.equalsIgnoreCase(mrsXls))
			// isMrsNaming = "YES";
			// mrsFollowed = isMrsNaming+"   ===> MRS:  " +mrsXls;
			mrsFollowed = "YES";
		else
			mrsFollowed = "NO";
		// else if (fileName.equalsIgnoreCase(mrsXlsx)){
		// isMrsNaming = "YES";
		// mrsFollowed = isMrsNaming+"   ===> MRS:  " +mrsXlsx;
		// mrsFollowed = isMrsNaming;
		// }
		// //System.out.println(dateS);//System.out.println(mrsXls);//System.out.println(mrsXls);
		return mrsFollowed;
	}

	private String isMrsXLSFormat(String fileName) {
		String mrsXLSFormat = "";
		if (fileName.endsWith("xls") || fileName.endsWith("pdf"))
			mrsXLSFormat = "YES";
		else if (fileName.endsWith("xlsx"))
			mrsXLSFormat = "NO";

		return mrsXLSFormat;
	}

	private String isWPNamingConvention(String WPName) {

		int isInternal_WP = WPName.indexOf("IBM_");

		// //System.out.println("Map name: "+map_name);

		Date date = new Date();
		DateFormat df = new SimpleDateFormat("MMddyyyy");
		String dateS = df.format(date);
		String isWPNaming = "NO";
		String wpFollowed = isWPNaming;

		if (isInternal_WP != -1) {
			//System.out.println("Internal WP");
			String internalWP = "IBM_" + ticket_no + "_" + map_name + "_"
					+ dateS;
			//System.out.println(internalWP);
			if (WPName.equalsIgnoreCase(internalWP)) {
				isWPNaming = "YES";
				wpFollowed = isWPNaming;
			}
		} 
		//System.out.println(wpFollowed);
		return wpFollowed;
	}
	
	public static void deleteMaps(File[] listfiles,File folder) {
		listFiles = folder.listFiles();
		for (File file : listFiles) {
			if(file.getName().contains("_SI.map")) {
				file.delete();
		}
			if(file.getName().contains("_SI.mxl")) {
				file.delete();
		}
	   }
	}
	
	public static void duplicateMAP(String folderpath,String map_name) {
    	InputStream inStream = null;
	    OutputStream outStream = null;
 
    	try{
 
    	    File afile =new File(folderpath+"\\"+map_name+".map");
    	    File bfile =new File(folderpath+"\\"+map_name+"_SI.map");
 
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
    	}
	}
	
    public static void set_QCPassed(int missing_cnt)
    {
        if(missing_cnt==0)
        QC_Tester.isQCPassed = true;
        else
        QC_Tester.isQCPassed = false;
        
        //return QC_Tester.isQCPassed;
    }
    
    /*public boolean isQCPassed() {
		return isQCPassed;
	}

	public void setQCPassed(boolean isQCPassed) {
		QC_Tester.isQCPassed = isQCPassed;
	}*/
	   
}
