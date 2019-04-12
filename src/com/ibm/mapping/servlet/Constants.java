package com.ibm.mapping.servlet;

/**
 * 
 * @author pradeep
 *
 */
public interface Constants {

	// String GROUP = "RSC Map Change Team,MAP Dev & Support";
	// String GROUP = "RSC Map Change Team,MAP Dev & Support";

	// String GROUP = "MAP Dev & Support";
	String GROUP = "RSC Map Change Team,PER_RSC,RSC_B2B,RSC SsaS Map Development,Implementation_RSC,NAM_Map_Team,EMEA_Map_Team,B2B_OnPrem,MAP Dev & Support,Mail Box Services";
	String LOGON_ERROR_VALUE = "<p style=\"color:red;margin-left:8px\"><strong>Invalid UserName Or Password Or You do not have access to AppStore application. If you believe you need access, please contact Manjit S Sodhi/India/IBM with a business justification.</strong></p>";
	String LDAP_ERROR_VALUE = "<p style=\"color:red;margin-left:8px\">There is an error in LDAP Server";
	String jdbcClassName = "com.ibm.db2.jcc.DB2Driver";
	String jdbcUrl = "jdbc:db2://9.30.161.135:50001/"; //"jdbc:db2://9.30.161.135:50000/";
	String jdbcUserName = "db2inst1"; //"db2user";
	String jdbcPassword = "db@inst!";//"db2user@";
	String jdbcSchemaName = "QC";
	String FTPServerUrl = "209.95.232.130";
	int FTPPortNo = 21;
	/**
	 * Name of the directory where uploaded files will be saved, relative to the
	 * web application directory.
	 */
	String SAVE_DIR = "uploadFiles";
	// String ACCUMULATOR_XSL =
	// "C:\\Softwares\\eclipse-jee-luna-SR1-RC3-win32-x86_64\\workspace\\mappingDashBoard\\resource\\AccumUtility.xsl";
	// String CODELIST_XSL =
	// "C:\\Softwares\\eclipse-jee-luna-SR1-RC3-win32-x86_64\\workspace\\mappingDashBoard\\resource\\CodeListUtility.xsl";
	// String ERRROR_20001_XSL =
	// "C:\\Softwares\\eclipse-jee-luna-SR1-RC3-win32-x86_64\\workspace\\mappingDashBoard\\resource\\Error20001Report.xsl";
	// Added for MRS Generator - Manish
	// String MRSGENERATOR_INPUTXSL =
	// "C:\\OfficialData\\mappingDashBoard_1205\\resource\\input.xsl";
	// String MRSGENERATOR_OUTPUTXSL =
	// "C:\\OfficialData\\mappingDashBoard_1205\\resource\\output.xsl";
	// String MRSGENERATOR_METAXSL =
	// "C:\\OfficialData\\mappingDashBoard_1205\\resource\\meta.xsl";
	// String MRSGENERATOR_WRKBOOKXML =
	// "C:\\OfficialData\\mappingDashBoard_1205\\resource\\workbook.xml";
	// String MRSGENERATOR_STYLESXML =
	// "C:\\OfficialData\\mappingDashBoard_1205\\resource\\styles.xml";
	// String MRSGENERATOR_CONTENTXML =
	// "C:\\OfficialData\\mappingDashBoard_1205\\resource\\[Content_Types].xml";
	// String MRSGENERATOR_RELSFOLDER =
	// "C:\\OfficialData\\mappingDashBoard_1205\\resource\\_rels";
	// / End constants for MRS Generator
    
	// **** SERVER PATH
	
	/*String ACCUMULATOR_XSL = "C:\\MappingFiles\\mappingDashBoard\\xsl\\AccumUtility.xsl";
	String CODELIST_XSL = "C:\\MappingFiles\\mappingDashBoard\\xsl\\CodeListUtility.xsl";
	String ERRROR_20001_XSL = "C:\\MappingFiles\\mappingDashBoard\\xsl\\Error20001Report.xsl";

	String MRSGENERATOR_INPUTXSL = "C:\\MappingFiles\\mappingDashBoard\\xsl\\input.xsl";
	String MRSGENERATOR_OUTPUTXSL = "C:\\MappingFiles\\mappingDashBoard\\xsl\\output.xsl";
	String MRSGENERATOR_METAXSL = "C:\\MappingFiles\\mappingDashBoard\\xsl\\meta.xsl";
	String MRSGENERATOR_WRKBOOKXML = "C:\\MappingFiles\\mappingDashBoard\\xsl\\workbook.xml";
	String MRSGENERATOR_STYLESXML = "C:\\MappingFiles\\mappingDashBoard\\xsl\\styles.xml";
	String MRSGENERATOR_CONTENTXML = "C:\\MappingFiles\\mappingDashBoard\\xsl\\[Content_Types].xml";
	String MRSGENERATOR_RELSFOLDER = "C:\\MappingFiles\\mappingDashBoard\\xsl\\_rels";
	
	String MAPPER_EXE = "C:\\Program Files (x86)\\Sterling Commerce\\Map Editor\\MapperX.exe"; 
	String DEFAULT_MAPPER_EXE = "C:\\Program Files (x86)\\Sterling Commerce\\Map Editor\\Mapper.exe";  */
	//String EXCELCONV = "C:/Program Files (x86)/Microsoft Office/Office12/excelcnv.exe";
	
	// *********
    
	 // **** LOCAL PATH
	 String ACCUMULATOR_XSL = "C:\\resource\\AccumUtility.xsl";
	 String CODELIST_XSL = "C:\\resource\\CodeListUtility.xsl";
	 String ERRROR_20001_XSL = "C:\\resource\\Error20001Report.xsl";
	 String FieldMinsToZero_XSL = "C:\\resource\\FieldMinsToZero.xsl";
	 String removeAllConditionalRules_XSL = "C:\\resource\\removeAllConditionalRules.xsl";
	 String setAllStringsToFreeFormat_XSL = "C:\\resource\\setAllStringsToFreeFormat.xsl";
	 String setAllTagsToLen7_XSL = "C:\\resource\\setAllTagsToLen7.xsl";
	 String setCharacterEncodingsToUTF8_XSL = "C:\\resource\\setCharacterEncodingsToUTF8.xsl";

	 String MRSGENERATOR_INPUTXSL = "C:\\resource\\input.xsl";
	 String MRSGENERATOR_OUTPUTXSL = "C:\\resource\\output.xsl";
	 String MRSGENERATOR_METAXSL = "C:\\resource\\meta.xsl";
	 String MRSGENERATOR_WRKBOOKXML = "C:\\resource\\workbook.xml";
	 String MRSGENERATOR_STYLESXML = "C:\\resource\\styles.xml";
	 String MRSGENERATOR_CONTENTXML = "C:\\resource\\[Content_Types].xml";
	 String MRSGENERATOR_RELSFOLDER = "C:\\resource\\_rels";
	 	  			 
	 String DEFAULT_MAPPER_EXE = "C:\\Program Files (x86)\\Sterling Commerce\\Map Editor 8.x\\Mapper.exe";
	 String MAPPER_EXE = "C:\\Program Files (x86)\\Sterling Commerce\\Map Editor 8.x\\MapperX.exe";	
	 
	 // **********

	// constants used for logger configuration
	public static final String LOG4J_CONFIG = "log4j-properties";
	public static final String LOG4J_FILE_ROOT = "log4j-output-dir";
	public static final String LOG4J_DFLT_FILE_ROOT = "WEB-INF";
	public static final String LOG4J_DFLT_SYSTEM_PROPERTY_NAME = "log4jRoot";
	public static final String LOG4J_CONFIG_WATCH = "log4j-watch";

}
