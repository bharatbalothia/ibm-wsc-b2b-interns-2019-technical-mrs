package com.ibm.mapping.xmltoxls.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represent the CODE_LIST_XREF tag in input xml sheet.
 * @author kaviyah
 *
 */
@XmlRootElement(name="CODE_LIST_XREF")
@XmlAccessorType(XmlAccessType.FIELD)
public class CodeListXref {
@XmlElement(name="PARTNER_KEY")
private String partnerKey;
@XmlElement(name="LIST_TYPE")
private String listType;
@XmlElement(name="LIST_NAME")
private String listName;
@XmlElement(name="SENDER_ID")
private String senderId;
@XmlElement(name="RECEIVER_ID")
private String receiverId;
@XmlElement(name="LIST_VERSION")
private String listVersion;
@XmlElement(name="SIResourceDefaultVersion")
private String siDfltVersion;
@XmlElement(name="STATUS")
private String status;
@XmlElement(name="COMMENTS")
private String comments;
@XmlElement(name="USERNAME")
private String userName;
@XmlElement(name="CREATE_DATE")
private String createDate;
@XmlElement(name="CODE_LIST_XREF_ITEMS")
private CodeListXrefItems items;
/**
 * @return the partnerKey
 */
public String getPartnerKey() {
	return partnerKey;
}
/**
 * @param partnerKey the partnerKey to set
 */
public void setPartnerKey(String partnerKey) {
	this.partnerKey = partnerKey;
}
/**
 * @return the listType
 */
public String getListType() {
	return listType;
}
/**
 * @param listType the listType to set
 */
public void setListType(String listType) {
	this.listType = listType;
}
/**
 * @return the listName
 */
public String getListName() {
	return listName;
}
/**
 * @param listName the listName to set
 */
public void setListName(String listName) {
	this.listName = listName;
}
/**
 * @return the senderId
 */
public String getSenderId() {
	return senderId;
}
/**
 * @param senderId the senderId to set
 */
public void setSenderId(String senderId) {
	this.senderId = senderId;
}
/**
 * @return the receiverId
 */
public String getReceiverId() {
	return receiverId;
}
/**
 * @param receiverId the receiverId to set
 */
public void setReceiverId(String receiverId) {
	this.receiverId = receiverId;
}
/**
 * @return the listVersion
 */
public String getListVersion() {
	return listVersion;
}
/**
 * @param listVersion the listVersion to set
 */
public void setListVersion(String listVersion) {
	this.listVersion = listVersion;
}
/**
 * @return the siDfltVersion
 */
public String getSiDfltVersion() {
	return siDfltVersion;
}
/**
 * @param siDfltVersion the siDfltVersion to set
 */
public void setSiDfltVersion(String siDfltVersion) {
	this.siDfltVersion = siDfltVersion;
}
/**
 * @return the status
 */
public String getStatus() {
	return status;
}
/**
 * @param status the status to set
 */
public void setStatus(String status) {
	this.status = status;
}
/**
 * @return the comments
 */
public String getComments() {
	return comments;
}
/**
 * @param comments the comments to set
 */
public void setComments(String comments) {
	this.comments = comments;
}
/**
 * @return the userName
 */
public String getUserName() {
	return userName;
}
/**
 * @param userName the userName to set
 */
public void setUserName(String userName) {
	this.userName = userName;
}
/**
 * @return the createDate
 */
public String getCreateDate() {
	return createDate;
}
/**
 * @param createDate the createDate to set
 */
public void setCreateDate(String createDate) {
	this.createDate = createDate;
}
/**
 * @return the items
 */
public CodeListXrefItems getItems() {
	return items;
}
/**
 * @param items the items to set
 */
public void setItems(CodeListXrefItems items) {
	this.items = items;
}


}
