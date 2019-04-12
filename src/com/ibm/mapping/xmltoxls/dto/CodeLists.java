package com.ibm.mapping.xmltoxls.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *This class represent the CODE_LISTS tag in input xml sheet.
 * @author kaviyah
 *
 */
@XmlRootElement(name="CODE_LISTS")
@XmlAccessorType(XmlAccessType.FIELD)
public class CodeLists {

	@XmlElement(name ="CODE_LIST_XREF")
	private CodeListXref codeList;

	/**
	 * @return the codeList
	 */
	public CodeListXref getCodeList() {
		return codeList;
	}

	/**
	 * @param codeList the codeList to set
	 */
	public void setCodeList(CodeListXref codeList) {
		this.codeList = codeList;
	}


	
	
}
