package com.ibm.mapping.xmltoxls.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represent the CODE_LIST_XREF_ITEMS tag in input xml sheet.
 * @author kaviyah
 *
 */
@XmlRootElement(name="CODE_LIST_XREF_ITEMS")
@XmlAccessorType(XmlAccessType.FIELD)
public class CodeListXrefItems {
	
	@XmlElement(name ="CODE_LIST_XREF_ITEM")
   List<CodeListXrefItem> codeListXrefItem;

/**
 * @return the codeListXrefItem
 */
public List<CodeListXrefItem> getCodeListXrefItem() {
	return codeListXrefItem;
}

/**
 * @param codeListXrefItem the codeListXrefItem to set
 */
public void setCodeListXrefItem(List<CodeListXrefItem> codeListXrefItem) {
	this.codeListXrefItem = codeListXrefItem;
}
   
}
