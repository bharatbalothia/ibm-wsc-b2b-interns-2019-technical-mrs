package com.ibm.mapping.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

/**
 * 
 * This class is responsible for generating ddf file for Positional Layout DDF
 * Generator
 * 
 */
public class DDFGenerator {

	// log variable
	static Logger log = Logger.getLogger(DDFGenerator.class.getName());

	/**
	 * Generates ddf file based on the excel sheet given as input
	 * 
	 * @param inputPath
	 * @param outputDirectory
	 */
	public void genrateDdf(String inputPath, String outputDirectory) {

		String File_Name;
		try {
			/****************************** location for getting File ********************************************/
			File file_read = new File(inputPath);
			FileInputStream file = new FileInputStream(file_read);
			File_Name = file_read.getName().replace(".xls", "");

			//System.out.println(File_Name);

			/***************** Error Message: If file is not .xls **************************************/
			// Get the workbook instance for XLS file
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			// Get first sheet from the workbook
			HSSFSheet sheet = workbook.getSheetAt(0);

			// for creating .ddf
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			DOMImplementation domImpl = doc.getImplementation();
			DocumentType doctype = domImpl.createDocumentType("GENTRANDDF", "",
					"gentran_ddf.dtd");
			doc.appendChild(doctype);

			// root element GENTRANDDF
			Element rootElement = doc.createElement("GENTRANDDF");
			doc.appendChild(rootElement);
			rootElement.setAttribute("VERSION", "1.0");

			// element POSFILE
			Element childPOSFILE = doc.createElement("POSFILE");
			rootElement.appendChild(childPOSFILE);
			childPOSFILE.setAttribute("NAME", "POSFILE");
			childPOSFILE.setAttribute("ACTIVE", "yes");
			childPOSFILE.setAttribute("DELIM1", "0x0d");
			childPOSFILE.setAttribute("DELIM2", "0x0a");
			childPOSFILE.setAttribute("RECLENGTH", "0");

			Element childPOSREC = null, childPOSFIELD = null;

			int count_row = 0;
			String RName = "", description = "", tagPos = "", minLoop = "", maxLoop = "", recTag = "";
			String FName = "", fDescription = "", fMandatory = "", strtPos = "", fLen = "", fType = "", fFormat = "";
			for (Row temp : sheet) {
				if (!isRowEmpty(temp)) {
					count_row++;
					if (count_row > 2) {
						// For header Tag
						if (count_row == 3 && (temp.getCell(0) == null || temp.getCell(0).toString() == "")) {
							/***********
							 * Error Messaage : As per the standarad schema 3rd
							 * row should start with A Record Name
							 *********************************/
							log.info("As per the standarad schema 3rd row should start with A Record Name");
							return;
							//System.exit(0);
						}

						if (temp.getCell(0) != null
								&& temp.getCell(0).toString() != "") {
							if ((temp.getCell(0).toString()).contains("END")) {
								break;
							}

							RName = temp.getCell(0).toString();
							description = temp.getCell(1).toString();
							recTag = temp.getCell(2).toString();
							tagPos = temp.getCell(3).toString();
							minLoop = temp.getCell(4).toString();
							maxLoop = temp.getCell(5).toString();

							childPOSREC = doc.createElement("POSREC");
							childPOSFILE.appendChild(childPOSREC);
							childPOSREC.setAttribute("NAME", RName);
							childPOSREC
									.setAttribute("DESCRIPTION", description);
							childPOSREC.setAttribute("ACTIVE", "YES");
							childPOSREC.setAttribute("MINLOOP", minLoop);
							childPOSREC.setAttribute("MAXLOOP", maxLoop);
							childPOSREC.setAttribute("LOOPCONTROL", "normal");
							childPOSREC.setAttribute("TAG", recTag);
							childPOSREC.setAttribute("TAGPOS", tagPos);
							childPOSREC.setAttribute("FLOATING", "no");
						} else {
							if (temp.getCell(1) == null
									|| temp.getCell(1).toString() == "") {
								/***********
								 * Error Messaage : As per the standarad schema
								 * Each row should have field Name
								 *********************************/
								log.info("As per the standarad schema Each row should have field Name. Row: "+ count_row + " is not having any field name");
								return;
								//System.exit(0);
							}

							if (temp.getCell(1) != null
									&& temp.getCell(1).toString() != "") {
								FName = temp.getCell(1).toString();
								fDescription = temp.getCell(2).toString();
								strtPos = temp.getCell(3).toString();
								Cell feildSt = temp.getCell(3);
								//take value from formula field
								if (feildSt.getCellType() == Cell.CELL_TYPE_FORMULA) {
									//System.out.println("Formula is "
										//	+ feildSt.getCellFormula());
									switch (feildSt
											.getCachedFormulaResultType()) {
									case Cell.CELL_TYPE_NUMERIC:
										double value = feildSt
												.getNumericCellValue();
										strtPos = String.valueOf(value);
										break;
									case Cell.CELL_TYPE_STRING:
										strtPos = feildSt
												.getRichStringCellValue()
												.getString();
										break;

									}
								}
							}
							fLen = temp.getCell(4).toString();
							fMandatory = temp.getCell(5).toString();
							fType = temp.getCell(6).toString();
							fFormat = temp.getCell(7).toString();

							// Put values
							childPOSFIELD = doc.createElement("POSFIELD");
							childPOSREC.appendChild(childPOSFIELD);
							childPOSFIELD.setAttribute("NAME", FName);
							childPOSFIELD.setAttribute("DESCRIPTION",
									fDescription);
							childPOSFIELD.setAttribute("ACTIVE", "YES");
							childPOSFIELD.setAttribute("MANDATORY", fMandatory);
							childPOSFIELD.setAttribute("STARTPOS", strtPos);
							childPOSFIELD.setAttribute("LENGTH", fLen);
							childPOSFIELD.setAttribute("MINDATALEN", "1");
							childPOSFIELD.setAttribute("MAXDATALEN", fLen);
							childPOSFIELD.setAttribute("TYPE", fType);
							childPOSFIELD.setAttribute("JUSTIFICATION", "left");
							childPOSFIELD.setAttribute("FORMAT", fFormat);
						}
					}
				}
			}

			// For tranfering document into .ddf file or xml file.
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			// transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,
			// doctype.getPublicId());
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,
					doctype.getSystemId());

			DOMSource source = new DOMSource(doc);

			/**************************************** Path for output file *****************************/
			String report_name = outputDirectory + File_Name + ".ddf";
			StreamResult result = new StreamResult(new File(report_name));

			transformer.transform(source, result);

			// Acknowladgement
			//System.out.println("File saved!");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
			log.error(tfe.getMessage());
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
			log.error(pce.getMessage());
		} catch (NullPointerException ne) {
			ne.printStackTrace();
			log.error(ne.getMessage());
		}
		

	}

	/**
	 * Check whether the particular row of the given excel file is empty or not
	 * 
	 * @param row
	 * @return
	 */
	private static boolean isRowEmpty(Row row) {
		for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
			Cell cell = row.getCell(c);
			if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
				return false;
		}
		return true;
	}

}
