/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibm.mapping.util;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.ibm.bluepages.BPResults;
import com.ibm.bluepages.BluePages;
import com.ibm.mapping.bean.UserValidate;
import com.ibm.swat.password.ReturnCode;
import com.ibm.swat.password.cwa2;

/**
 * 
 * @author pradeep
 *
 */
public class BlueGroupLDAPLogin {
	/**
	 * 
	 * @param user
	 * @param pass
	 * @param group
	 * @return
	 */
	//log variable 
	static Logger log = Logger.getLogger(BlueGroupLDAPLogin.class.getName());
	
	public static UserValidate validateUser(String user, String pass,
			String group) {
		UserValidate userValidate = new UserValidate();
		boolean ret = false;
		String groups[] = group.split(",");
		Vector<String> group1 = new Vector<String>(Arrays.asList(groups));

		try {

			cwa2 CWA2 = new cwa2();
			ret = CWA2.authenticate_throw(user, pass,"ldap://bluepages.ibm.com:389");
			//ret=true;
			if (ret == true) {
				log.error(user + " Authenticated");

				// Check if User exist in group
				ReturnCode rc = CWA2.inAnyGroup(user, group1);

				for (int i = 0; i < groups.length; i++) {
					if (CWA2.inAGroup(user, groups[i]).getCode() == 0) {

						rc = CWA2.inAGroup(user, groups[i]);
						//System.out.println(groups[i]);
						userValidate.setGroupName(groups[i]);
						break;
					}
				}

				//System.out.println("Group check result " + user + " "+ rc.getMessage() + " Code : " + rc.getCode());
				userValidate.setErrorCode(rc.getCode());
				userValidate.setErrorMessage(rc.getMessage());
				if (rc.getCode() != 0) {
					ret = false;
				}

				/*** Check User details from BluePages ****/
				BPResults results;
				results = BluePages.getPersonsByInternet(user);

				/* * Display the Name for matching person. */
				if (results.rows() == 0)
				log.error("No matches found.");
				else {
					for (int i = 0; i < results.rows(); i++) {
						Hashtable<String, String> row = results.getRow(i);
						//System.out.println(row.entrySet());
						userValidate.setName((String) row.get("NAME").replace(
								", ", " "));
						//System.out.println((String) row.get("NAME"));

					}
				}
				// System.out.println(objBPQuerier.findPerson(user));

			}
		} catch (Exception e) {
			userValidate.setErrorMessage(e.getMessage());
			log.error(" In catch error " + e);
			ret = false;
		}
		userValidate.setValidUser(ret);
		return userValidate;
	}
	
	public static boolean isValidBlueGrpUser(String user, String pass, String group) {
		boolean isValid = false;
		boolean isValid_BG = false;
		try
		{
			cwa2 CWA2 = new cwa2();
			isValid = CWA2.authenticate_throw(user,pass, "ldap://bluepages.ibm.com:389");
			//isValid=true;
			if (isValid==true)
			{
				ReturnCode rc= CWA2.inAGroup(user,group);
				
				if(rc.getCode()==5 && rc.getMessage()=="User Not In Group")
					isValid_BG=false;
				else
					isValid_BG=true;
					
				//JOptionPane.showMessageDialog(null,rc.toString());
				System.out.println(user + " " + rc.getMessage()+ " "+group);
				log.info(user + " " + rc.getMessage()+ " "+group);
				
			}		  
	        
		}	
		catch (Exception e)
		{
			System.out.println(" Error "+ e);
			log.error(e.getMessage());
	    } 
		return isValid_BG;   
	}

	public static void main(String[] args) {
		String group = "RSC Map Change Team,PER_RSC,SaaS_MapDevelopment,Implementation_RSC";
		String groups[] = group.split(",");
		Vector<String> group1 = new Vector<String>(Arrays.asList(groups));
		//System.out.println(group1);
	}

}
