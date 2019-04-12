package com.ibm.mapping.xmltoxls.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represent the CODE_LIST_XREF_ITEM tag in input xml sheet.
 * @author kaviyah
 *
 */
@XmlRootElement(name="CODE_LIST_XREF_ITEM")
@XmlAccessorType(XmlAccessType.FIELD)
public class CodeListXrefItem {
	
	@XmlElement(name="SENDER_ITEM")
	private String senderItem;
	@XmlElement(name="RECEIVER_ITEM")
	private String receiverItem;
	@XmlElement(name="TEXT1")
	private String text1;
	@XmlElement(name="TEXT2")
	private String text2;
	@XmlElement(name="TEXT3")
	private String text3;
	@XmlElement(name="TEXT4")
	private String text4;
	@XmlElement(name="TEXT5")
	private String text5;
	@XmlElement(name="TEXT6")
	private String text6;
	@XmlElement(name="TEXT7")
	private String text7;
	@XmlElement(name="TEXT8")
	private String text8;
	@XmlElement(name="TEXT9")
	private String text9;
	@XmlElement(name="DESCRIPTION")
	private String description;
	
	/**
	 * @return the senderItem
	 */
	public String getSenderItem() {
		return senderItem;
	}
	/**
	 * @param senderItem the senderItem to set
	 */
	public void setSenderItem(String senderItem) {
		this.senderItem = senderItem;
	}
	/**
	 * @return the receiverItem
	 */
	public String getReceiverItem() {
		return receiverItem;
	}
	/**
	 * @param receiverItem the receiverItem to set
	 */
	public void setReceiverItem(String receiverItem) {
		this.receiverItem = receiverItem;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the text1
	 */
	public String getText1() {
		return text1;
	}
	/**
	 * @param text1 the text1 to set
	 */
	public void setText1(String text1) {
		this.text1 = text1;
	}
	/**
	 * @return the text2
	 */
	public String getText2() {
		return text2;
	}
	/**
	 * @param text2 the text2 to set
	 */
	public void setText2(String text2) {
		this.text2 = text2;
	}
	/**
	 * @return the text3
	 */
	public String getText3() {
		return text3;
	}
	/**
	 * @param text3 the text3 to set
	 */
	public void setText3(String text3) {
		this.text3 = text3;
	}
	/**
	 * @return the text4
	 */
	public String getText4() {
		return text4;
	}
	/**
	 * @param text4 the text4 to set
	 */
	public void setText4(String text4) {
		this.text4 = text4;
	}
	/**
	 * @return the text5
	 */
	public String getText5() {
		return text5;
	}
	/**
	 * @param text5 the text5 to set
	 */
	public void setText5(String text5) {
		this.text5 = text5;
	}
	/**
	 * @return the text6
	 */
	public String getText6() {
		return text6;
	}
	/**
	 * @param text6 the text6 to set
	 */
	public void setText6(String text6) {
		this.text6 = text6;
	}
	/**
	 * @return the text7
	 */
	public String getText7() {
		return text7;
	}
	/**
	 * @param text7 the text7 to set
	 */
	public void setText7(String text7) {
		this.text7 = text7;
	}
	/**
	 * @return the text8
	 */
	public String getText8() {
		return text8;
	}
	/**
	 * @param text8 the text8 to set
	 */
	public void setText8(String text8) {
		this.text8 = text8;
	}
	/**
	 * @return the text9
	 */
	public String getText9() {
		return text9;
	}
	/**
	 * @param text9 the text9 to set
	 */
	public void setText9(String text9) {
		this.text9 = text9;
	}

}
