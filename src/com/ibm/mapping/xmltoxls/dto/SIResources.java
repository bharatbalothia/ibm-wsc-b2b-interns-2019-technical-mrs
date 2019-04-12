package com.ibm.mapping.xmltoxls.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represents the CODE_LISTS root tag in the given xml sheet.
 * @author kaviyah
 *
 */
@XmlRootElement(name="SI_RESOURCES")
@XmlAccessorType(XmlAccessType.FIELD)
public class SIResources {
	
	@XmlElement(name="CODE_LISTS")
	CodeLists codeLists;
	/**
	 * @return the codeLists
	 */
	public CodeLists getCodeLists() {
		return codeLists;
	}

	/**
	 * @param codeLists the codeLists to set
	 */
	public void setCodeLists(CodeLists codeLists) {
		this.codeLists = codeLists;
	}


	
	

}
