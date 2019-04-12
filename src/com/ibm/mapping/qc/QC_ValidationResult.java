package com.ibm.mapping.qc;

/**
 * @author Sanket.
 * 
 */

public class QC_ValidationResult {

	private int mandatory_checks = 0; 	
	private int optional_checks = 0;
	private String typeWP="";
	private String name = "";
	private String date;
	private String ticketNo;
	private boolean mrsFileExists;
	private boolean afterIbmFileExists;
	private boolean beforeIbmFileExists;
	private boolean mapOrMxlFileExists;
	private boolean txoFileExists;
	private boolean compileFileExists_WTX;
	private boolean isToday;
	private String typeOfMap = "";
	private String mrsXLSFormat;
	private String fileEmpty;
	private String mrsConventionFollowed = "";
	private String WPConventionFollowed = "";
	private String opConventionFollowed = "";
	private String rpConventionFollowed = "";
	private String inputfileCount = "0";
	private String outputfileCount = "0";
	private String before_outputfileCount = "0";
	private String after_outputfileCount = "0";
	private String reportFileCount = "0";
	
	private String mapName = "";
	private String authorName = "";
	private String presessionUpdate = "";
	private String final_score = "0";
	private boolean latesttxoFileExists;
	private boolean latesttxoFileExists_WTX;
	private boolean mapNameSame;
	private boolean ticketNoSame;
	private boolean validUser;

	public String getTypeWP() {
		return typeWP;
	}

	public void setTypeWP(String typeWP) {
		this.typeWP = typeWP;
	}
	
	public boolean isToday() {
		return isToday;
	}

	public void setToday(boolean isToday) {
		this.isToday = isToday;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public boolean isMrsFileExists() {
		return mrsFileExists;
	}

	public void setMrsFileExists(boolean mrsFileExists) {
		this.mrsFileExists = mrsFileExists;
	}
	
	public boolean isMapNameSame() {
		return mapNameSame;
	}

	public void setMapNameSame(boolean mapNameSame) {
		this.mapNameSame = mapNameSame;
	}

	public boolean isTicketNoSame() {
		return ticketNoSame;
	}

	public void setTicketNoSame(boolean ticketNoSame) {
		this.ticketNoSame = ticketNoSame;
	}

	public boolean isAfterIbmFileExists() {
		return afterIbmFileExists;
	}

	public boolean isValidUser() {
		return validUser;
	}

	public void setValidUser(boolean validUser) {
		this.validUser = validUser;
	}

	public void setAfterIbmFileExists(boolean afterIbmFileExists) {
		this.afterIbmFileExists = afterIbmFileExists;
	}

	public boolean isBeforeIbmFileExists() {
		return beforeIbmFileExists;
	}

	public void setBeforeIbmFileExists(boolean beforeIbmFileExists) {
		this.beforeIbmFileExists = beforeIbmFileExists;
	}

	public boolean isMapOrMxlFileExists() {
		return mapOrMxlFileExists;
	}

	public void setMapOrMxlFileExists(boolean mapOrMxlFileExists) {
		this.mapOrMxlFileExists = mapOrMxlFileExists;
	}

	public boolean isTxoFileExists() {
		return txoFileExists;
	}

	public void setTxoFileExists(boolean txoFileExists) {
		this.txoFileExists = txoFileExists;
	}
	
	public boolean isLatestTxoFileExists() {
		return latesttxoFileExists;
	}

	public void setLatestTxoFileExists(boolean latesttxoFileExists) {
		this.latesttxoFileExists = latesttxoFileExists;
	}
	
	public boolean isLatestTxoFileExists_WTX() {
		return latesttxoFileExists_WTX;
	}

	public void setLatestTxoFileExists_WTX(boolean latesttxoFileExists_WTX) {
		this.latesttxoFileExists_WTX = latesttxoFileExists_WTX;
	}
	
	public boolean isCompileFileExists_WTX() {
		return compileFileExists_WTX;
	}

	public void setCompileFileExists_WTX(boolean compileFileExists_WTX) {
		this.compileFileExists_WTX = compileFileExists_WTX;
	}
	
	public String getTypeOfMap() {
		return typeOfMap;
	}

	public void setTypeOfMap(String typeOfMap) {
		this.typeOfMap = typeOfMap;
	}
	
	public String getMrsConventionFollowed(){
		return mrsConventionFollowed;
	}
	public void setMrsConventionFollowed(String mrsConventionFollowed){
		this.mrsConventionFollowed = mrsConventionFollowed;
	}
	
	public void setMrsXLSFormat(String mrsXLSFormat) {
		this.mrsXLSFormat = mrsXLSFormat;
	}
	
	public String getMrsXLSFormat() {
		return mrsXLSFormat;
	}
	
	public String getWPConventionFollowed() {
		return WPConventionFollowed;
	}

	public void setWPConventionFollowed(String wPConventionFollowed) {
		WPConventionFollowed = wPConventionFollowed;
	}
	
	public String getInputFileCount(){
		return inputfileCount;
	}
	public void setInputFileCount(String inputfileCount){
		this.inputfileCount = inputfileCount;
	}
	
	public String getOutputFileCount(){
		return outputfileCount;
	}
	public void setOutputFileCount(String outputfileCount){
		this.outputfileCount = outputfileCount;
	}
	
	public String getMapName(){
		return mapName;
	}
	public void setMapName(String mapName){
		this.mapName = mapName;
	}
	
	public String getAuthorName(){
		return authorName;
	}
	public void setAuthorName(String authorName){
		this.authorName = authorName;
	}
	
	public String getReportFileCount() {
		return reportFileCount;
	}

	public void setReportFileCount(String reportFileCount) {
		this.reportFileCount = reportFileCount;
	}
	
	public String getOpConventionFollowed() {
		return opConventionFollowed;
	}

	public void setOpConventionFollowed(String opConventionFollowed) {
		this.opConventionFollowed = opConventionFollowed;
	}

	public String getRpConventionFollowed() {
		return rpConventionFollowed;
	}

	public void setRpConventionFollowed(String rpConventionFollowed) {
		this.rpConventionFollowed = rpConventionFollowed;
	}
	
	public String getPresessionUpdate() {
		return presessionUpdate;
	}

	public void setPresessionUpdate(String presessionUpdate) {
		this.presessionUpdate = presessionUpdate;
	}
	
	public String getFinal_score() {
		return final_score;
	}

	public void setFinal_score(String final_score) {
		this.final_score = final_score;
	}

	public String getFileEmpty() {
		return fileEmpty;
	}

	public void setFileEmpty(String fileEmpty) {
		this.fileEmpty = fileEmpty;
	}
	
	public String getBefore_outputfileCount() {
		return before_outputfileCount;
	}

	public void setBefore_outputfileCount(String before_outputfileCount) {
		this.before_outputfileCount = before_outputfileCount;
	}

	public String getAfter_outputfileCount() {
		return after_outputfileCount;
	}

	public void setAfter_outputfileCount(String after_outputfileCount) {
		this.after_outputfileCount = after_outputfileCount;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(name);
		sb.append("\n");
		sb.append(date);
		sb.append("\n");
		sb.append("Is Today: ").append(isToday);
		sb.append("\n");
		sb.append(ticketNo);
		sb.append("\n");
		sb.append("MRS File: ").append(mrsFileExists);
		sb.append("\n");
		sb.append("After IBM: ").append(afterIbmFileExists);
		sb.append("\n");
		sb.append("Before IBM: ").append(beforeIbmFileExists);
		sb.append("\n");
		sb.append("Map or Mxl: ").append(mapOrMxlFileExists);
		sb.append("\n");
		sb.append("Txo File: ").append(txoFileExists);
		sb.append("\n");

		return sb.toString();
	}

	public int getMandatory_checks() {
		return mandatory_checks;
	}

	public void setMandatory_checks(int mandatory_checks) {
		this.mandatory_checks = mandatory_checks;
	}

	public int getOptional_checks() {
		return optional_checks;
	}

	public void setOptional_checks(int optional_checks) {
		this.optional_checks = optional_checks;
	}
}
