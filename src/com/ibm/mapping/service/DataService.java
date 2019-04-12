/**
 * 
 */
package com.ibm.mapping.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pradeep
 *
 */
public interface DataService {

	List<String> getProjectNames(String query) throws SQLException;

	int insertDasboardDetails(String user, String email, String uId,
			int ticket, String project, String noOfFiles, String groupName)
			throws SQLException;

	int insertFileDetails(String fid, String fileName, int fileSize) throws SQLException;
	
	List<String> fetchQualifierDetails(String AgencyID, String VersionID/*,String FieldName, String Qualifier*/) throws SQLException;

	String getFID() throws SQLException;

}
