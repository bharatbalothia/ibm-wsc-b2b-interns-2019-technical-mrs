/**
 * 
 */
package com.ibm.mapping.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * @author Pradeep.
 *
 */
public class DBConnection {

	Connection conn;
	static Logger log  = Logger.getLogger(DBConnection.class);

	public DBConnection(String jdbcClassName, String url, String schemaName,
			String userName, String password) throws Exception {
		try {
			Class.forName(jdbcClassName);
			conn = DriverManager.getConnection(url + schemaName, userName,
					password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("There is an issue with connecting the DB or BSO authentication is not done");
			log.error("Could not start DB : " + e);
			throw new Exception(e);
		}

	}

	public void shutdown() throws SQLException {

		conn.close();
	}

	// SQL command for SELECT
	public synchronized ResultSet query(String expression) throws SQLException {

		Statement st = null;

		st = conn.createStatement(); // statement objects can be reused with

		// repeated calls to execute but we
		// choose to make a new one each time
		return st.executeQuery(expression); // run the query

	}

	// SQL commands for CREATE, DROP, INSERT and UPDATE
	public synchronized int update(String expression) throws SQLException {

		Statement st = null;

		st = conn.createStatement(); // statements

		int i = st.executeUpdate(expression); // run the query

		if (i == -1) {
			System.out.println("db error : " + expression);
		}

		st.close();

		return i;
	}

	public static void main(String[] args) throws Exception {

		String jdbcClassName = "com.ibm.db2.jcc.DB2Driver";
		String url = "jdbc:db2://9.30.161.135:50001/";
		String userName = "db2inst1";
		String password = "db@inst!";
		String schemaName = "QC";

		DBConnection db = null;
		ArrayList<String> data;

		try {
			db = new DBConnection(jdbcClassName, url, schemaName, userName,
					password);
		} catch (Exception startDBException) {
			log.error("There is an issue with connecting the DB or BSO authentication is not done");
			log.error("Could not start DB : " + startDBException);

		} 
		
		/*data = getCodesTableData(db);
		System.out.println(data.size());
		for(int i=0;i<data.size();i++) {
		if(data.get(i).startsWith("0188_L_"))
		System.out.println(data.get(i).replace("0188_L_", ""));
		}*/
		/*
		 * String user = "Pradeep P"; String email = "pradeep_p_131@in.ibm.com";
		 * String UID = "U01"; int ticket = 1234878; String project = "ALC";
		 * String NOOFfiles = "6";
		 * 
		 * StringBuilder query = new StringBuilder(); if (ticket != 0) { query =
		 * new StringBuilder(
		 * "INSERT into QC.DAshboard(user,email,UID, ticket, NOOFfiles)Values("
		 * ); query.append("'").append(user).append("','").append(email)
		 * .append("','").append(UID).append("','").append(ticket)
		 * .append("','").append(NOOFfiles).append("')"); } else { query = new
		 * StringBuilder(
		 * "INSERT into QC.DAshboard(user,email,UID, project, NOOFfiles)Values("
		 * ); query.append("'").append(user).append("','").append(email)
		 * .append("','").append(UID).append("','").append(project)
		 * .append("','").append(NOOFfiles).append("')"); }
		 */
		// System.out.println(query);
		// db.update(query.toString());

		// Get data of PROJECTS table

		/*System.out
				.println("=====================xxxxxxxxxxxxxxxxxxxxxx======================================================");
		System.out.println("PROJECTS table data");
		System.out
				.println("=====================xxxxxxxxxxxxxxxxxxxxxx======================================================");
		getProjectTableData(db);
		System.out
				.println("=====================xxxxxxxxxxxxxxxxxxxxxx======================================================");

		// Get data of UTILITIES table

		System.out
				.println("=====================xxxxxxxxxxxxxxxxxxxxxx======================================================");
		System.out.println("UTILITIES table data");
		System.out
				.println("=====================xxxxxxxxxxxxxxxxxxxxxx======================================================");
		getUtilitiesTableData(db);
		System.out
				.println("=====================xxxxxxxxxxxxxxxxxxxxxx======================================================");

		// Get data of DASHBOARD table

		System.out
				.println("=====================xxxxxxxxxxxxxxxxxxxxxx======================================================");
		System.out.println("DASHBOARD table data");
		System.out
				.println("=====================xxxxxxxxxxxxxxxxxxxxxx======================================================");
		geDashBoardTableData(db);
		System.out
				.println("=====================xxxxxxxxxxxxxxxxxxxxxx======================================================");*/

	}

	public static void getProjectTableData(DBConnection db) {
		try {

			ResultSet resultSet = db.query("SELECT * FROM QC.PROJECTS");
			System.out.println("Column Name");
			if (resultSet != null) {
				log.info("PROJECTNAME<=====>STATUS");
				log.info("---------------------------------------------------------------------");
				while (resultSet.next()) {

					log.info(resultSet.getString(1) + "<=====>"
							+ resultSet.getString(2));
				}
			}

		} catch (SQLException ex3) {

			log.error(ex3);
			ex3.printStackTrace();
		}
	}
	
	public static ArrayList<String> getCodesTableData(DBConnection db) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			ResultSet resultSet = db.query("SELECT ELEMENTID,VALUE,DESCRIPTION FROM QC.CODES WHERE AGENCY='X' AND VERSION='004010'");
			if (resultSet != null) {
				while (resultSet.next()) {
                    data.add(resultSet.getString("ELEMENTID") + "_" + resultSet.getString("VALUE") + "_" + resultSet.getString("DESCRIPTION"));
				}
			}

		} catch (SQLException ex3) {

			log.error(ex3);
			ex3.printStackTrace();
		}
		return data;
	}

	public static void getUtilitiesTableData(DBConnection db) {
		try {

			ResultSet resultSet = db.query("SELECT * FROM QC.UTILITIES");

			if (resultSet != null) {
				System.out.println("Column Name");
				System.out
						.println("UID<=====>UNAME<=====>UDESCRIPTION<=====>EFFORTSAVED<=====>STATUS");
				System.out
						.println("-------------------------------------------------------------------------------------------------------------------------");
				while (resultSet.next()) {

					System.out.println(resultSet.getString(1) + "<=====>"
							+ resultSet.getString(2) + "<=====>"
							+ resultSet.getString(3) + "<=====>"
							+ resultSet.getString(4) + resultSet.getString(5));
				}
			}

		} catch (SQLException ex3) {

			log.error(ex3);
			ex3.printStackTrace();
		}
	}

	public static void geDashBoardTableData(DBConnection db) {
		try {

			ResultSet resultSet = db.query("SELECT * FROM QC.DASHBOARD");

			if (resultSet != null) {
				System.out.println("Column Name");
				System.out
						.println("USER<=====>EMAIL<=====>EDATE<=====>UID<=====>TICKET<=====>PROJECT<=====>NOOFFILES");
				System.out
						.println("---------------------------------------------------------------------");
				while (resultSet.next()) {

					System.out.println(resultSet.getString(1) + "<=====>"
							+ resultSet.getString(2) + "<=====>"
							+ resultSet.getString(3) + "<=====>"
							+ resultSet.getString(4) + "<=====>"
							+ resultSet.getString(5) + "<=====>"
							+ resultSet.getString(6) + "<=====>"
							+ resultSet.getString(7));
				}
			}

		} catch (SQLException ex3) {

			log.error(ex3);
			ex3.printStackTrace();
		}
	}
}
