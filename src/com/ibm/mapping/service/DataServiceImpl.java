package com.ibm.mapping.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.mapping.util.DBConnection;

public class DataServiceImpl implements DataService {

	DBConnection db = null;
	Logger log = Logger.getLogger(DataServiceImpl.class);

	public DataServiceImpl(String jdbcClassName, String url, String schemaName,
			String userName, String password) throws Exception {

		try {
			db = new DBConnection(jdbcClassName, url, schemaName, userName,
					password);
		} catch (Exception startDBException) {

			log.error("Could not start DB : " + startDBException);
			throw new Exception(startDBException);

		}

	}

	@Override
	public List<String> getProjectNames(String query) throws SQLException {
		List<String> projectNames = null;
		try {

			ResultSet resultSet = db.query(query);
			projectNames = new ArrayList<String>();
			if (resultSet != null) {

				while (resultSet.next()) {

					projectNames.add(resultSet.getString(1));
				}
			}

			log.info("Project names : " + projectNames);
		} catch (SQLException ex3) {
			ex3.printStackTrace();
			log.error("Error occured while closing the connection " + ex3);
			throw new SQLException(ex3);

		} finally {
			try {
				db.shutdown();
			} catch (SQLException e) {
				log.error("Error occured while closing the connection " + e);
				throw new SQLException(e);
			}
		}
		return projectNames;
	}

	@Override
	public int insertDasboardDetails(String user, String email, String uId,
			int ticket, String project, String noOfFiles, String groupName)
			throws SQLException {
		
		String modifyemail=email;
		Encription en= new Encription();
		user=en.Encript(user);
		email=modifyemail.substring(modifyemail.lastIndexOf("@") + 1);
		
		int rowsUpdated = 0;
		StringBuilder query = new StringBuilder();
		// get FID
		String fidStr = getFID();
		//System.out.println("Group Value : " + groupName);
		//System.out.println("FID Value " + fidStr);
		if (ticket != 0) {
			query = new StringBuilder(
					"INSERT into QC.DASHBOARD(USER,EMAIL,UID, TICKET, NOOFFILES,FID,TEAMNAME)Values(");
			query.append("'").append(user).append("','").append(email)
					.append("','").append(uId).append("','").append(ticket)
					.append("','").append(noOfFiles).append("','")
					.append(fidStr).append("','").append(groupName)
					.append("')");

		} else {
			query = new StringBuilder(
					"INSERT into QC.DASHBOARD(USER,EMAIL,UID, PROJECT, NOOFFILES,FID,TEAMNAME)Values(");
			query.append("'").append(user).append("','").append(email)
					.append("','").append(uId).append("','").append(project)
					.append("','").append(noOfFiles).append("','")
					.append(fidStr).append("','").append(groupName)
					.append("')");
		}
		//System.out.println(query);
		log.info(query);
		try {
			rowsUpdated = db.update(query.toString());
			log.info(rowsUpdated
					+ " records updated successfully in QC.DASHBOARD table");
		} catch (SQLException e) {
			log.error("Errror While inserting the record" + e);
			throw new SQLException(e);
		} finally {
			try {
				db.shutdown();
			} catch (SQLException e) {
				log.error("Error occured while closing the connection" + e);
				throw new SQLException(e);
			}
		}
		return rowsUpdated;
	}

	public String getFID() throws SQLException {
		int fid = 0;

		try {
			ResultSet fidRs = db.query("select max(FID)+1 from QC.Dashboard");
			while (fidRs.next()) {
				fid = fidRs.getInt(1);
			}
		} catch (SQLException e) {
			log.error("Error while taking value of FID " + e);
			throw new SQLException(e);
		}

		return String.valueOf(fid);
	}

	public static void main(String[] args) throws Exception {

		String jdbcClassName = "com.ibm.db2.jcc.DB2Driver";
		String url = "jdbc:db2://9.118.0.214:50001/";
		String userName = "db2inst1";
		String password = "db@inst!";
		String schemaName = "QC";

		DataService service = new DataServiceImpl(jdbcClassName, url,schemaName, userName, password);
		// service.getProjectNames("SELECT PROJECTNAME FROM QC.PROJECTS WHERE STATUS='A'");

		String user = "Pradeep P";
		String email = "pradeep_p@in.ibm.com";
		String UID = "U01";
		int ticket = 0;
		String project = "ALC";
		String NOOFfiles = "6";
		String groupName = "RSC";
		//System.out.println("DESC=>"+service.fetchQualifierDetails("X","004010","0188","L"));
		//service.insertDasboardDetails(user, email, UID, ticket, project,NOOFfiles, groupName);
		// test getFID()
		//DataServiceImpl serviceImpl = new DataServiceImpl(jdbcClassName, url,schemaName, userName, password);
		//String fid = serviceImpl.getFID();
		// to test insertFileDetails
		// serviceImpl.insertFileDetails(fid, "COOP_CL_GNR1_O_810_4010.mxl");

	}

	@Override
	public int insertFileDetails(String fid, String fileName,int fileSize)
			throws SQLException {
		int rowsUpdated = 0;
		StringBuilder query = new StringBuilder();
		query = new StringBuilder("INSERT into QC.Files(FID,FILENAME,SIZE) Values(");
		query.append("'").append(fid).append("','").append(fileName).append("','").append(fileSize)
				.append("')");
		try {
			rowsUpdated = db.update(query.toString());
			log.info(rowsUpdated
					+ " records updated successfully in QC.Files table");
		} catch (SQLException e) {
			log.error("Errror While inserting the record" + e);
			throw new SQLException(e);
		} finally {
			try {
				db.shutdown();
			} catch (SQLException e) {
				log.error("Error occured while closing the connection" + e);
				throw new SQLException(e);
			}
		}
		return rowsUpdated;
	}
	
	@Override
	public List<String> fetchQualifierDetails(String AgencyID, String VersionID/*,String FieldName, String Qualifier*/)
			throws SQLException {
		//String qualf_desc = null;
		StringBuilder query = new StringBuilder();
		ArrayList<String> data = new ArrayList<String>();
		query = new StringBuilder("SELECT ELEMENTID,VALUE,DESCRIPTION FROM QC.CODES WHERE AGENCY = ");
		query.append("'").append(AgencyID).append("' AND VERSION = '").append(VersionID)/*.append("' AND ELEMENTID = '").append(FieldName)
				.append("' AND VALUE='").append(Qualifier)*/.append("'");
		//System.out.println("query=>"+query);
		try {
			ResultSet fidRs = db.query(query.toString());
			while (fidRs.next()) {
			//qualf_desc = fidRs.getString("DESCRIPTION");
			data.add(fidRs.getString("ELEMENTID") + "_" + fidRs.getString("VALUE") + "_" + fidRs.getString("DESCRIPTION"));	
			}
		} catch (SQLException e) {
			log.error("Errror While fetching the record" + e);
			throw new SQLException(e);
		} finally {
			try {
				db.shutdown();
			} catch (SQLException e) {
				log.error("Error occured while closing the connection" + e);
				throw new SQLException(e);
			}
		}
		//return qualf_desc;
		return data;
	}
}
