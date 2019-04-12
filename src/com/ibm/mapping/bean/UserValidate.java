package com.ibm.mapping.bean;

/**
 * 
 * @author pradeep
 *
 */
public class UserValidate {
	private String errorMessage;
	private int errorCode;
	private String resultMessage;
	private boolean validUser;
	private String name;
	private String groupName;

	/**
	 * @return the groupName
	 */
	public final String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName
	 *            the groupName to set
	 */
	public final void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public final void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the errorMessage
	 */
	public final String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public final void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the errorCode
	 */
	public final int getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode
	 *            the errorCode to set
	 */
	public final void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the resultMessage
	 */
	public final String getResultMessage() {
		return resultMessage;
	}

	/**
	 * @param resultMessage
	 *            the resultMessage to set
	 */
	public final void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	/**
	 * @return the validUser
	 */
	public final boolean isValidUser() {
		return validUser;
	}

	/**
	 * @param validUser
	 *            the validUser to set
	 */
	public final void setValidUser(boolean validUser) {
		this.validUser = validUser;
	}
}
