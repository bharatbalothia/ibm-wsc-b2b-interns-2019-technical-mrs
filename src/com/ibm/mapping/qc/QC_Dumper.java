package com.ibm.mapping.qc;

/**
 * @author Sanket.
 * 
 */

import java.io.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import com.ibm.mapping.servlet.Constants;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.ibm.mapping.util.*;

public class QC_Dumper {
	
	//log variable 
	static Logger log = Logger.getLogger(QC_Dumper.class.getName());
	
	int score_cnt = 0;
	double final_score = 0;
	boolean isBSO_Auth = false;
	boolean isdb_present = true;
	ArrayList<String> missing_list = new ArrayList<String>();
	String jdbcClassName = Constants.jdbcClassName;
	String schemaName = Constants.jdbcSchemaName;
	String url = Constants.jdbcUrl;
	String userName = Constants.jdbcUserName;
	String password = Constants.jdbcPassword;
	DBConnection con = null;
    
	private String getYesorNo(boolean today) {
		return today ? "YES" : "NO";
	}
			
	public boolean is_QCDB_Present() throws Exception {
		try {
			con = new DBConnection(jdbcClassName, url, schemaName,userName, password);
			
			     try{
				 String query ="select * from qc.checks";
				 ResultSet rs = con.query(query);
				 
				 if(rs==null) {
					 isdb_present=false;
					 log.error("There is no data present in the QC Checks table of the database");
				 }
				 else
				 rs.close();
				 }
				 catch(SQLException je) {
					 isdb_present=false;
					 log.error("QC Checks table does not exist in the database"); 
					 log.error(je.getMessage());
				 }
				 
				 try{
				 String query ="select * from qc.qcmaster";
				 ResultSet rs = con.query(query);
				 rs.close();
				 }
				 catch(SQLException je) {
					 isdb_present=false;
					 log.error("QC Master table does not exist in the database"); 
					 log.error(je.getMessage());
				 }
				 
				 try{
				 String query ="select * from qc.qcdetail";
				 ResultSet rs = con.query(query);
				 rs.close();

				 }
				 catch(SQLException je) {
					 isdb_present=false;
					 log.error("QC Detail table does not exist in the database"); 
					 log.error(je.getMessage());
				 }

		}
	   catch(SQLException se){
		   log.error(se.getMessage());
	   }finally {
		  try{
		  if(con!=null)
		  con.shutdown();
		  //System.out.println("DB Closed 2 ****");
		  }catch(SQLException se){
		    se.printStackTrace();
		  }
	   }
		return isdb_present;
	}
	
	public ArrayList<String> missingCHECKS(QC_ValidationResult result,String map_name,String wp_name,String req_typ,String ticket_no,String isWTX,String isGentran) throws Exception {
		//System.out.println("WP Name : " + wp_name);		
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);	

		try {
		con = new DBConnection(jdbcClassName, url, schemaName,userName, password);
		
		log.info("Database connection to server is successful!!!");
		
		 String cnt_query_1 ="select count(*) from qc.checks where checktype='M' and status='A'";
		 String cnt_query_2 ="select count(*) from qc.checks where checktype='O' and status='A'";
		 ResultSet rs3 = con.query(cnt_query_1);
		 ResultSet rs4 = con.query(cnt_query_2);
		 int mandatory_checks = 0; 	
		 int optional_checks = 0;
		 if(rs3!=null)
		 {
			while(rs3.next())
			{   
			mandatory_checks=rs3.getInt(1);
			result.setMandatory_checks(mandatory_checks);
			////System.out.println("No of mandatory checks:"+mandatory_checks);
			}
		 }		  		
		 if(rs4!=null)
		 {
			while(rs4.next())
			{   
			optional_checks=rs4.getInt(1);
			result.setOptional_checks(optional_checks);
			////System.out.println("No of optional checks:"+optional_checks);
			}
		 }
		
        String query_1 ="select checkid from qc.checks where status='A'";
		ResultSet rs1 = con.query(query_1);
	
		 if(rs1!=null)
		 {
			 while(rs1.next())
			 {   			     
 			     switch(rs1.getString("CHECKID")) {
 			     
 			     case "C01": //System.out.println("CHECKID 1");
 			    //System.out.println("result.getWPConventionFollowed(=>"+result.getWPConventionFollowed());
 			     			 if (result.getWPConventionFollowed() != "YES") { 
 			     		        String query_2 ="select checkdescription from qc.checks where checkid='"+rs1.getString("CHECKID")+"'";
 			     				ResultSet rs2 = con.query(query_2);
 			     				if(rs2!=null)
 			     				{
 			     				while(rs2.next()) 
 			     				missing_list.add(rs2.getString("CHECKDESCRIPTION"));		
 			     				}
 			     				rs2.close();
 			     			 }
 			     			break;	
 			     				
 			     case "C02"://System.out.println("CHECKID 2");
 			    //System.out.println("getYesorNo(result.isCompileFileExists_WTX())=>"+getYesorNo(result.isCompileFileExists_WTX()));
 			   //System.out.println("getYesorNo(result.isMapOrMxlFileExists())=>"+getYesorNo(result.isMapOrMxlFileExists()));
 			  //System.out.println("getYesorNo(result.isTxoFileExists())=>"+getYesorNo(result.isTxoFileExists()));
 			     			if ((getYesorNo(result.isMapOrMxlFileExists()) != "YES" || getYesorNo(result.isTxoFileExists()) != "YES" && isWTX=="N")||(getYesorNo(result.isMapOrMxlFileExists()) != "YES" || getYesorNo(result.isCompileFileExists_WTX()) != "YES" && isWTX=="Y")) {
 			     		        String query_2 ="select checkdescription from qc.checks where checkid='"+rs1.getString("CHECKID")+"'";
 			     				ResultSet rs2 = con.query(query_2);
 			     				if(rs2!=null)
 			     				{
 			     				while(rs2.next()) 
 			     				missing_list.add(rs2.getString("CHECKDESCRIPTION"));		
 			     				}
 			     				rs2.close();
 			     			 }
 			     			break;
 			     case "C03"://System.out.println("CHECKID 3");
 			     			if (result.getMapName() != "YES" && isWTX=="N") {
 			     				String query_2 ="select checkdescription from qc.checks where checkid='"+rs1.getString("CHECKID")+"'";
 			     				ResultSet rs2 = con.query(query_2);
 			     				if(rs2!=null)
 			     				{
 			     				while(rs2.next()) 
 			     				missing_list.add(rs2.getString("CHECKDESCRIPTION"));		
 			     				}
 			     				rs2.close();
 			     			 }
 			     			break;
 			     			
 			     case "C04"://System.out.println("CHECKID 4");
 			     			if (getYesorNo(result.isMrsFileExists()) != "YES") {
 			     				String query_2 ="select checkdescription from qc.checks where checkid='"+rs1.getString("CHECKID")+"'";
 			     				ResultSet rs2 = con.query(query_2);
 			     				if(rs2!=null)
 			     				{
 			     				while(rs2.next()) 
 			     				missing_list.add(rs2.getString("CHECKDESCRIPTION"));		
 			     				}
 			     				rs2.close();
 			     			}
 			     			break;
 			     			
 			     case "C05"://System.out.println("CHECKID 5");
 			     			if (result.getMrsConventionFollowed() != "YES") {
 			     				String query_2 ="select checkdescription from qc.checks where checkid='"+rs1.getString("CHECKID")+"'";
 			     				ResultSet rs2 = con.query(query_2);
 			     				if(rs2!=null)
 			     				{
 			     				while(rs2.next()) 
 			     				missing_list.add(rs2.getString("CHECKDESCRIPTION"));		
 			     				}
 			     				rs2.close();
 			     			}
 			     			break;
 			     			
 			     case "C06"://System.out.println("CHECKID 6");
 			     			if (result.getMrsXLSFormat() != "YES") {
 			     				String query_2 ="select checkdescription from qc.checks where checkid='"+rs1.getString("CHECKID")+"'";
 			     				ResultSet rs2 = con.query(query_2);
 			     				if(rs2!=null)
 			     				{
 			     					while(rs2.next()) 
 			     					missing_list.add(rs2.getString("CHECKDESCRIPTION"));		
 			     				}
 			     				rs2.close();
 			     			}
 			     			break;
 			     			
 			    case "C07"://System.out.println("CHECKID 7");
 			                 // Do Nothing
 			                 //This check is optional which is not implemented (MRS highlighted in red color)
 			               break;
			     case "C08"://System.out.println("CHECKID 8");
			     			  if ( ( ( !result.getOutputFileCount().equals(result.getReportFileCount()) || !result.getOutputFileCount().equals(result.getInputFileCount()) ) && (!result.getBefore_outputfileCount().equals(result.getAfter_outputfileCount())||(result.getBefore_outputfileCount()=="0"&&result.getAfter_outputfileCount()=="0")) && req_typ.equals("MCR") && isGentran=="N") 
			     				|| ( ( !result.getOutputFileCount().equals(result.getReportFileCount()) || !result.getOutputFileCount().equals(result.getInputFileCount()) ) && req_typ.equals("NEW") && isGentran=="N") 
			     				|| ( ( !result.getOutputFileCount().equals(result.getInputFileCount())  || (!result.getBefore_outputfileCount().equals(result.getAfter_outputfileCount())&& result.getBefore_outputfileCount()!="0") ) && req_typ.equals("MCR") && isGentran=="Y" ) 
			     				|| ( !result.getOutputFileCount().equals(result.getInputFileCount()) && req_typ.equals("NEW") && isGentran=="Y" ) ) {
			     			String query_2 ="select checkdescription from qc.checks where checkid='"+rs1.getString("CHECKID")+"'";
							ResultSet rs2 = con.query(query_2);
							if(rs2!=null)
							{
	     					while(rs2.next()) 
	     					missing_list.add(rs2.getString("CHECKDESCRIPTION"));		
							}
							rs2.close();
						  }
			     		  break;
						
 			     case "C09"://System.out.println("CHECKID 9");
 			     			if (result.getOpConventionFollowed() != "YES") {
 			     				String query_2 ="select checkdescription from qc.checks where checkid='"+rs1.getString("CHECKID")+"'";
 			     				ResultSet rs2 = con.query(query_2);
 			     				if(rs2!=null)
 			     				{
		     					while(rs2.next()) 
		     					missing_list.add(rs2.getString("CHECKDESCRIPTION"));		
 			     				}
 			     				rs2.close();
 			     			}
 			     			break;
	     			
 			     case "C10"://System.out.println("CHECKID 10");
 			     			if (result.getRpConventionFollowed() != "YES") {
				     				String query_2 ="select checkdescription from qc.checks where checkid='"+rs1.getString("CHECKID")+"'";
				     				ResultSet rs2 = con.query(query_2);
				     				if(rs2!=null)
				     				{
				     					while(rs2.next()) 
				     					missing_list.add(rs2.getString("CHECKDESCRIPTION"));		
				     				}
				     				rs2.close();
				     			}
				     		break; 	
	     			
	     			case "C11"://System.out.println("CHECKID 11");
	     						if (result.getFileEmpty() != "YES") {
				     				String query_2 ="select checkdescription from qc.checks where checkid='"+rs1.getString("CHECKID")+"'";
				     				ResultSet rs2 = con.query(query_2);
				     				if(rs2!=null)
				     				{
				     					while(rs2.next()) 
				     					missing_list.add(rs2.getString("CHECKDESCRIPTION"));		
				     				}
				     				rs2.close();
				     			}
				     			break;
				     			
	     			case "C12"://System.out.println("CHECKID 12");
				     	        if(req_typ.equals("MCR") && map_name.toLowerCase().startsWith("whir") && isWTX=="N") {
				     	        	//if ((getYesorNo(result.isBeforeIbmFileExists()) != "YES" && getYesorNo(result.isAfterIbmFileExists()) != "YES") || (getYesorNo(result.isBeforeIbmFileExists()) != "YES" || getYesorNo(result.isAfterIbmFileExists()) != "YES") && !result.getBefore_outputfileCount().equals(result.getAfter_outputfileCount()) && !result.getBefore_outputfileCount().equals(result.getReportFileCount()) && result.getOutputFileCount()!="0" && result.getInputFileCount()!="0") {
				     	           if ( ( !result.getBefore_outputfileCount().equals(result.getAfter_outputfileCount()) || (result.getBefore_outputfileCount() == "0" && result.getAfter_outputfileCount() == "0" )) && result.getOutputFileCount() !="0" && result.getInputFileCount() !="0") { 
				     	        	 String query_2 ="select checkdescription from qc.checks where checkid='"+rs1.getString("CHECKID")+"'";
					     			 ResultSet rs2 = con.query(query_2);
					     			 if(rs2!=null)
					     			 {
					     			 while(rs2.next()) 
					     			 missing_list.add(rs2.getString("CHECKDESCRIPTION"));		
					     			 }
					     			 rs2.close();
				     	   		  }
				     	        }
				     			break;
	     			
	     			case "C13"://System.out.println("CHECKID 13");
	     						if (result.getPresessionUpdate() != "YES" && isWTX=="N") {
				     				String query_2 ="select checkdescription from qc.checks where checkid='"+rs1.getString("CHECKID")+"'";
				     				ResultSet rs2 = con.query(query_2);
				     				if(rs2!=null)
				     				{
				     					while(rs2.next()) 
				     					missing_list.add(rs2.getString("CHECKDESCRIPTION"));		
				     				}
				     				rs2.close();
				     			}
				     			break;
	     			
	     			case "C14"://System.out.println("CHECKID 14");
	     						if (getYesorNo(result.isTicketNoSame()) != "YES" && isWTX=="N") {
				     				String query_2 ="select checkdescription from qc.checks where checkid='"+rs1.getString("CHECKID")+"'";
				     				ResultSet rs2 = con.query(query_2);
				     				if(rs2!=null)
				     				{
				     					while(rs2.next()) 
				     					missing_list.add(rs2.getString("CHECKDESCRIPTION"));		
				     				}
				     				rs2.close();
				     			}
				     			break;
				     			
	     			case "C15"://System.out.println("CHECKID 15");
	     			//System.out.println("getYesorNo(result.isLatestTxoFileExists_WTX())=>"+getYesorNo(result.isLatestTxoFileExists_WTX()));
	     						if ((getYesorNo(result.isLatestTxoFileExists()) != "YES" && isWTX=="N") || (getYesorNo(result.isLatestTxoFileExists_WTX()) != "YES" && isWTX=="Y")) {
									String query_2 ="select checkdescription from qc.checks where checkid='"+rs1.getString("CHECKID")+"'";
									ResultSet rs2 = con.query(query_2);
									if(rs2!=null)
									{
			     					while(rs2.next()) 
			     					missing_list.add(rs2.getString("CHECKDESCRIPTION"));		
									}
									rs2.close();
			     			    }
			     			    break;
	     			
	     			case "C16"://System.out.println("CHECKID 16");
	     			//System.out.println("getYesorNo(result.isMapNameSame())=>"+getYesorNo(result.isMapNameSame()));
	     						if (getYesorNo(result.isMapNameSame()) != "YES") {
			     				String query_2 ="select checkdescription from qc.checks where checkid='"+rs1.getString("CHECKID")+"'";
			     				ResultSet rs2 = con.query(query_2);
			     				if(rs2!=null)
			     				{
			     					while(rs2.next()) 
			     					missing_list.add(rs2.getString("CHECKDESCRIPTION"));		
			     				}
			     				rs2.close();
							   }
			     			   break;
			     			  
	     			default:JOptionPane.showMessageDialog(null,"There is no logic present for this check");break;
 			     
 			     }

			 }
		 }
	
		 rs1.close();
		 rs4.close();
		 rs3.close();
		 
		/* if (map_name.toLowerCase().startsWith("whir")) { // Whirlpool customer
			  if(req_typ=="MCR") {
				score_cnt = (mandatory_checks+(optional_checks-1)) - missing_list.size();                                     // -1 is done because 1 optional check is not implemented
				//System.out.println("score_cnt=>"+score_cnt);
				final_score=((double)score_cnt)/(mandatory_checks+1)*100;
				if (score_cnt < (mandatory_checks+1)) {				
			    JOptionPane.showMessageDialog(null,"Score is  "+ df.format(final_score)+ "%...Click OK to view missing checks and try again !!!");
				}	else if (score_cnt==(mandatory_checks+1)) {
			    JOptionPane.showMessageDialog(null,"You have passed mandatory QC checks successfully!!!");
				}
			  }
			  if(req_typ=="NEW") {
				score_cnt = mandatory_checks - missing_list.size();
				//System.out.println("score_cnt=>"+score_cnt);
				final_score=((double)score_cnt)/(mandatory_checks)*100;
				if (score_cnt < (mandatory_checks)) {				
					JOptionPane.showMessageDialog(null,"Score is  "+ df.format(final_score)+ "%...Click OK to view missing checks and try again !!!");
				}	else if (score_cnt==(mandatory_checks)) {
			    JOptionPane.showMessageDialog(null,"You have passed mandatory QC checks successfully!!!");
				}
			  }
			} else {
			   //if(isWTX=="N") {
				score_cnt = mandatory_checks - missing_list.size();
				//System.out.println("score_cnt=>"+score_cnt);
				final_score=((double)score_cnt)/(mandatory_checks)*100;
				//System.out.println("final_score=>"+final_score);
				if (score_cnt < (mandatory_checks)) {				
				JOptionPane.showMessageDialog(null,"Score is  "+ df.format(final_score)+ "%...Click OK to view missing checks and try again !!!");
				} else if (score_cnt==(mandatory_checks)) {
			    JOptionPane.showMessageDialog(null,"You have passed mandatory QC checks successfully!!!");
				}
				}*/
		       
		 				
	   }catch(SQLException se){
		   log.error("SQLException been raised :"+se.getMessage());
		   //System.exit(0);
	   }finally {
		   try{
		      if(con!=null)
				 con.shutdown();
		      //System.out.println("DB Closed 3 ****");
			  }catch(SQLException se){
				    se.printStackTrace();
		   }
	   }
		
		return missing_list;
		 
	}
	
	public boolean updateDB(QC_ValidationResult result,String map_name,String wp_name,String req_typ,String user_name,String email_id,String ticket_no,String isWTX,int missing_list_size,String isGentran,String isGSA_Upload,double final_score) throws Exception {
		boolean issue_qcmaster=false;
		String sql_stmt;
		String jar_version = "Web"/*"AppStore"*/;
		
		//System.out.println("WP Name : " + wp_name);	
		//System.out.println("JAR VERSION : " + jar_version);
		//System.out.println("final_score=>"+final_score);
		//JOptionPane.showMessageDialog(null,"JAR VERSION : " + jar_version);
		//score_cnt = score_cnt + 1;//testing purpose
		try {
		con = new DBConnection(jdbcClassName, url, schemaName,userName, password);
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());	 
		
		//System.out.println("final_score=>"+final_score);
		
		try {
		//Insert in master table
		if(missing_list_size==0 && isGSA_Upload == "Y")   // GSA Upload
		sql_stmt = "insert into qc.qcmaster values(default,'"+wp_name+"','"+user_name+"','"+email_id+"',TIMESTAMP('"+currentTimestamp+"'),'Y',TIMESTAMP('"+currentTimestamp+"'),'"+ticket_no+"',DECIMAL("+final_score+",5,2),'"+req_typ+"','"+jar_version+"')";
		else
		sql_stmt = "insert into qc.qcmaster values(default,'"+wp_name+"','"+user_name+"','"+email_id+"',TIMESTAMP('"+currentTimestamp+"'),'N',null,'"+ticket_no+"',DECIMAL("+final_score+",5,2),'"+req_typ+"','"+jar_version+"')";
		con.update(sql_stmt);
		}catch(SQLException se) {
		  issue_qcmaster=true;
		  log.error(se.getMessage());
			
		}
		
		String sql_stmt_1="select id from qc.qcmaster order by rdate desc fetch first 1 row only";
		 
		 ResultSet rs = con.query(sql_stmt_1);
		 int id = 0;
		 
		 if(rs!=null)
		 {
		 while(rs.next())
		 {
		 id=rs.getInt("id");
		 //System.out.println("LATEST ID:"+id);
		 }
		 }
		
        String query_1 ="select checkid from qc.checks where status='A'";//"select count(*) from qc.checks where checktype='M' and status='A'";
		ResultSet rs1 = con.query(query_1);
	
		if(missing_list_size>0 && issue_qcmaster==false) {
		 
		if(rs1!=null)
		 {
			 while(rs1.next())
			 {   			     
 			     switch(rs1.getString("CHECKID")) {
 			     
 			     case "C01": //System.out.println("CHECKID 1");
 			     			 if (result.getWPConventionFollowed() != "YES") { 

 			     				String query_3 = "insert into qc.qcdetail(id,checkid) values("+id+",'"+rs1.getString("CHECKID")+"')";
 			     				con.update(query_3);
 			     			 }
 			     			break;	
 			     				
 			     case "C02"://System.out.println("CHECKID 2");
 			     			if ((getYesorNo(result.isMapOrMxlFileExists()) != "YES" || getYesorNo(result.isTxoFileExists()) != "YES" && isWTX=="N")||(getYesorNo(result.isMapOrMxlFileExists()) != "YES" || getYesorNo(result.isCompileFileExists_WTX()) != "YES" && isWTX=="Y")) {
 	
 			     				String query_3 = "insert into qc.qcdetail(id,checkid) values("+id+",'"+rs1.getString("CHECKID")+"')";
 			     				con.update(query_3);
 			     			 }
 			     			break;
 			     case "C03"://System.out.println("CHECKID 3");
 			     			if (result.getMapName() != "YES" && isWTX=="N") {

 			     				String query_3 = "insert into qc.qcdetail(id,checkid) values("+id+",'"+rs1.getString("CHECKID")+"')";
 			     				con.update(query_3);
 			     			 }
 			     			break;
 			     			
 			     case "C04"://System.out.println("CHECKID 4");
 			     			if (getYesorNo(result.isMrsFileExists()) != "YES") {

 			     				String query_3 = "insert into qc.qcdetail(id,checkid) values("+id+",'"+rs1.getString("CHECKID")+"')";
 			     				con.update(query_3);
 			     			}
 			     			break;
 			     			
 			     case "C05"://System.out.println("CHECKID 5");
 			     			if (result.getMrsConventionFollowed() != "YES") {

 			     				String query_3 = "insert into qc.qcdetail(id,checkid) values("+id+",'"+rs1.getString("CHECKID")+"')";
 			     				con.update(query_3);
 			     			}
 			     			break;
 			     			
 			     case "C06"://System.out.println("CHECKID 6");
 			     			if (result.getMrsXLSFormat() != "YES") {

 			     				String query_3 = "insert into qc.qcdetail(id,checkid) values("+id+",'"+rs1.getString("CHECKID")+"')";
 			     				con.update(query_3);
 			     			}
 			     			break;
 			     			
 			    case "C07"://System.out.println("CHECKID 7");
 			               //Do Nothing
 			               break;
			     case "C08"://System.out.println("CHECKID 8");
			     		  	  if ( ( ( !result.getOutputFileCount().equals(result.getReportFileCount()) || !result.getOutputFileCount().equals(result.getInputFileCount()) ) && (!result.getBefore_outputfileCount().equals(result.getAfter_outputfileCount())||(result.getBefore_outputfileCount()=="0"&&result.getAfter_outputfileCount()=="0")) && req_typ.equals("MCR") && isGentran=="N") 
			     				|| ( ( !result.getOutputFileCount().equals(result.getReportFileCount()) || !result.getOutputFileCount().equals(result.getInputFileCount()) ) && req_typ.equals("NEW") && isGentran=="N") 
			     				|| ( ( !result.getOutputFileCount().equals(result.getInputFileCount()) || (!result.getBefore_outputfileCount().equals(result.getAfter_outputfileCount())&& result.getBefore_outputfileCount()!="0") ) && req_typ.equals("MCR") && isGentran=="Y" ) 
			     				|| ( !result.getOutputFileCount().equals(result.getInputFileCount()) && req_typ.equals("NEW") && isGentran=="Y" ) ) {

			     			String query_3 = "insert into qc.qcdetail(id,checkid) values("+id+",'"+rs1.getString("CHECKID")+"')";
			     			con.update(query_3);
						  }
			     		  break;
						
 			     case "C09"://System.out.println("CHECKID 9");
 			                //System.out.println("result.getOpConventionFollowed()=>"+result.getOpConventionFollowed());
 			     			if (result.getOpConventionFollowed() != "YES") {

 			     				String query_3 = "insert into qc.qcdetail(id,checkid) values("+id+",'"+rs1.getString("CHECKID")+"')";
 			     				con.update(query_3);
 			     			}
 			     			break;
	     			
 			     case "C10"://System.out.println("CHECKID 10");
 			     			if (result.getRpConventionFollowed() != "YES") {

	 			     				String query_3 = "insert into qc.qcdetail(id,checkid) values("+id+",'"+rs1.getString("CHECKID")+"')";
	 			     				con.update(query_3);
				     			}
				     		break; 	
	     			
	     			case "C11"://System.out.println("CHECKID 11");
	     						if (result.getFileEmpty() != "YES") {

	 			     				String query_3 = "insert into qc.qcdetail(id,checkid) values("+id+",'"+rs1.getString("CHECKID")+"')";
	 			     				con.update(query_3);
				     			}
				     			break;
				     			
	     			case "C12"://System.out.println("CHECKID 12");
				     	        if(req_typ.equals("MCR") && map_name.toLowerCase().startsWith("whir") && isWTX=="N") {
				     	        	//if ((getYesorNo(result.isBeforeIbmFileExists()) != "YES" && getYesorNo(result.isAfterIbmFileExists()) != "YES") || (getYesorNo(result.isBeforeIbmFileExists()) != "YES" || getYesorNo(result.isAfterIbmFileExists()) != "YES") && !result.getBefore_outputfileCount().equals(result.getAfter_outputfileCount()) && !result.getBefore_outputfileCount().equals(result.getReportFileCount()) && result.getOutputFileCount()!="0" && result.getInputFileCount()!="0") {
				     	         if ( ( !result.getBefore_outputfileCount().equals(result.getAfter_outputfileCount()) || (result.getBefore_outputfileCount() == "0" && result.getAfter_outputfileCount() == "0" )) && result.getOutputFileCount() !="0" && result.getInputFileCount() !="0") {
		 			     			 String query_3 = "insert into qc.qcdetail(id,checkid) values("+id+",'"+rs1.getString("CHECKID")+"')";
		 			     			 con.update(query_3);
				     	   		  }
				     	        }
				     			break;
	     			
	     			case "C13"://System.out.println("CHECKID 13");
	     						if (result.getPresessionUpdate() != "YES" && isWTX=="N") {

	 			     				String query_3 = "insert into qc.qcdetail(id,checkid) values("+id+",'"+rs1.getString("CHECKID")+"')";
	 			     				con.update(query_3);
				     			}
				     			break;
	     			
	     			case "C14"://System.out.println("CHECKID 14");
	     						if (getYesorNo(result.isTicketNoSame()) != "YES" && isWTX=="N") {

	 			     				String query_3 = "insert into qc.qcdetail(id,checkid) values("+id+",'"+rs1.getString("CHECKID")+"')";
	 			     				con.update(query_3);
				     			}
				     			break;
				     			
	     			case "C15"://System.out.println("CHECKID 15");
	     						if ((getYesorNo(result.isLatestTxoFileExists()) != "YES" && isWTX=="N") || (getYesorNo(result.isLatestTxoFileExists_WTX()) != "YES" && isWTX=="Y")) {

	 			     				String query_3 = "insert into qc.qcdetail(id,checkid) values("+id+",'"+rs1.getString("CHECKID")+"')";
	 			     				con.update(query_3);
			     			    }
			     			    break;
	     			
	     			case "C16"://System.out.println("CHECKID 16");
	     						if (getYesorNo(result.isMapNameSame()) != "YES") {

 			     				String query_3 = "insert into qc.qcdetail(id,checkid) values("+id+",'"+rs1.getString("CHECKID")+"')";
 			     				con.update(query_3);
							   }
			     			   break;
			     			  
	     			default:JOptionPane.showMessageDialog(null,"There is no logic present for this check");;break;
 			     
 			     }

			 }
		  }
		}
		
		rs1.close();
		rs.close();
		 
		/* String sql2="Select * from QC.QCMASTER";
		 
		 ResultSet rset3 = con.query(sql2);
		 //System.out.println("------- QC.QCMASTER --------- \n");
		 if(rset3!=null)
		 {
		 while(rset3.next())
		 {
		 //System.out.println("ID:"+rset3.getInt("ID"));
		 //System.out.println("WP:"+rset3.getString("WP"));
		 //System.out.println("USER:"+rset3.getString("USER"));
		 //System.out.println("EMAIL:"+rset3.getString("EMAIL"));
		 //System.out.println("RDATE:"+rset3.getTimestamp("RDATE"));
		 //System.out.println("UPLOAD:"+rset3.getString("UPLOAD"));
		 //System.out.println("UDATE:"+rset3.getTimestamp("UDATE"));
		 //System.out.println("TICKET:"+rset3.getString("TICKET"));
		 //System.out.println("SCORE:"+rset3.getFloat("SCORE"));
		 //System.out.println("REQUESTTYPE:"+rset3.getString("REQUESTTYPE"));
		 //System.out.println("JAR_VERSION:"+rset3.getString("JAR_VERSION"));
		 }
		 }
		 rset3.close();
		
		 String sql="Select * from QC.QCDETAIL";
		 
		 ResultSet rset1 = con.query(sql);
		 //System.out.println("------- QC.QCDETAIL --------- \n");
		 if(rset1!=null)
		 {
		 while(rset1.next())
		 {
		 //System.out.println("ID:"+rset1.getInt("ID"));
		 //System.out.println("CHECKID:"+rset1.getString("CHECKID"));
		 //System.out.println("REASON:"+rset1.getString("REASON"));
		 }
		 }
		 rset1.close();
		
		String sql3="Select * from QC.CHECKS";
		 
		 ResultSet rset1 = con.query(sql3);
		 //System.out.println("------- QC.CHECKS --------- \n");
		 if(rset1!=null)
		 {
		 while(rset1.next())
		 {
		 //System.out.println("CHECKID:"+rset1.getString("CHECKID"));
		 //System.out.println("CHECKNAME:"+rset1.getString("CHECKNAME"));
		 //System.out.println("CHECKDESCRIPTION:"+rset1.getString("CHECKDESCRIPTION"));
		 //System.out.println("STATUS:"+rset1.getString("STATUS"));
		 //System.out.println("CHECKTYPE:"+rset1.getString("CHECKTYPE"));
		 }
		 }*/
	   }catch(SQLException se){
		   se.printStackTrace();
	   }finally {
		   try{
			 if(con!=null)
				con.shutdown();
			 //System.out.println("DB Closed 4 ****");
			  }catch(SQLException se){
				     se.printStackTrace();
			 } 
	   }
		return issue_qcmaster;
		
		 
	}
}
