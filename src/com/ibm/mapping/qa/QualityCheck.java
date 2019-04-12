// Created by Sukanyaine Dasgupta on July 12, 2015.

package com.ibm.mapping.qa;

import java.awt.BorderLayout;
import java.io.*;
import java.util.*;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.text.*;


import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

class TransType {
	int trans;
	String seg;
	int ele;
	
	TransType (int trans, String seg, int ele) {
		this.trans = trans;
		this.seg = seg;
		this.ele = ele;
	}
	
	int getTrans() {
		return trans;
	}
	String getSeg() {
		return seg;
	}
	int getEle() {
		return ele;
	}
	
}


public class QualityCheck {
	public static String correctExtendedRule (String line) {
		String resultLine;
		resultLine = line.replaceAll("(?i) divisionlookup", " codelist").replaceAll("(?i) divisionxref", " codelist").replaceAll("(?i) tablename", " name").replaceAll("(?i) item", " sendercode").replaceAll("(?i) partneritem", " Text1").replaceAll("(?i) myitem", " sendercode").replaceAll("(?i)update document", "//UPDATE DOCUMENT");
		return resultLine;
	}
	public static void main(String [] args) {

	
		//Taking input path and output directory as input from command line.
		String inputPathMXL = args[0];		
		String outputDirectory = args[1];
	    File opDir = new File(outputDirectory);
        String characterEncoding = args[2];
        //System.out.println("inputPathMXL="+inputPathMXL);
        //System.out.println("outputDirectory="+outputDirectory);
        //System.out.println("characterEncoding="+characterEncoding);
        String userName = "";
        
        String currentDate;
        DateFormat currentDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        currentDate = currentDateFormat.format(new Date());
		
        String currDir = outputDirectory.substring(outputDirectory.lastIndexOf("\\") + 1);
        String inputDIR = (inputPathMXL.substring(0, inputPathMXL.lastIndexOf("\\")));
        inputDIR = inputDIR.substring(0, inputDIR.lastIndexOf("\\") + 1);
        //JOptionPane.showMessageDialog(null, "inputDIR: " + inputDIR);
        //String rootPath = rootDir + "\\";
        outputDirectory = args[1] + "\\";
        int flagPresession = 0;
        int flow = 0; //0=OUTBOUND 1=INBOUND
        String mode = "";
		String sendercode = "";
		String segment = "";
		String segmentMXL = "";
        String line1 = "";
        String firstWord = "";
        String codelist = null;
        String field = "";
        String squery = "";
		String mapName = "";;
		String mapOut = "";
		String query = "";
		String linelow = "";
		BufferedWriter eW;
        int ISAelementCount = 16;
        int compositeFlag = 1;
		int error20001 = 0;
		int extrul = 1;
		int flagISA = 0;
		int flagNEWFILE = 0;
		int flagENV = 0;
		int countISA = 0;
		String tran = "";
		String idSEG = "";
		int idELE = 0;
		int flagID = 0;
		int countML = 0;
		String updateS = "";
		int cntELE = 0;
		int flagUPDATE = 0;
		String idELEName = "";
		int flagOUTPUT = 0;
		int flagIDerror = 0;
		int flagTN = 0;
		int flagOUTPUT_DEL = 0;
		String lineTN;
		String clName;
		String del1;
		String del2;
		int flagPR = 0;
		Set<String> codelists = new TreeSet<String>();
		int possyntaxout = 0;
		String action = "";
		int flag_SR = 0;
		int flagST = 0;
		int countST = 0, countGS = 0, countUNG = 0, countUNH = 0;
		int fileNameLogicExists = 0;
		int reportCount = 0;
		String GS02 = "", GS03 = "", ST01 = "", GS06 = "", UNG02 = "", UNG03 = "", UNH02 = "", UNH01 = "";
		String fileNameLogicX = "//********************************************************\n// Set the FileName for the BP to use\n//********************************************************\nstring[256] strProcessID;\nstring[256] strFileName;\nstrProcessID =\"\";\nstrFileName = \"\";\nselect xpathresult into strProcessID from processdata where Xpath=\"ProcessID\";\nstrFileName = #G142 + \"_\" + #G124 + \"_\" + #0143 + \".\" + strProcessID + #0028;\nupdate processdata set xpathresult = strFileName where xpath=\"TranslationOutput/FileName\";";
		
		String fileNameLogicE = "//********************************************************\n// Set the FileName for the BP to use\n//********************************************************\nstring[256] strProcessID;\nstring[256] strFileName;\n\nstrProcessID =\"\";\nstrFileName = \"\";\nselect xpathresult into strProcessID from processdata where Xpath=\"ProcessID\";\nstrFileName = #UNG02_1 + \"_\" + #UNG03_1 + \"_\" + #UNH02_1 + \".\" + strProcessID + #UNH01;\nupdate processdata set xpathresult = strFileName where xpath=\"TranslationOutput/FileName\";\n";
		
		
		ArrayList<TransType> EDI = new ArrayList<TransType>();
		EDI.add(new TransType(160, "BAX", 8));
		EDI.add(new TransType(180, "BGN", 2));
		EDI.add(new TransType(753, "BGN", 2));
		EDI.add(new TransType(754, "BGN", 2));
		EDI.add(new TransType(810, "BIG", 2));
		EDI.add(new TransType(812, "BCD", 2));
		EDI.add(new TransType(816, "BHT", 3));
		EDI.add(new TransType(818, "BSC", 1));
		EDI.add(new TransType(820, "TRN", 2));
		EDI.add(new TransType(824, "BGN", 2));
		EDI.add(new TransType(828, "BAU", 1));
		EDI.add(new TransType(830, "BFR", 2));
		EDI.add(new TransType(831, "BGN", 2));
		EDI.add(new TransType(832, "BCT", 2));
		EDI.add(new TransType(850, "BEG", 3));
		EDI.add(new TransType(855, "BAK", 3));
		EDI.add(new TransType(856, "BSN", 2));
		EDI.add(new TransType(860, "BCH", 2));
		EDI.add(new TransType(861, "BRA", 1));
		EDI.add(new TransType(862, "BSS", 2));
		EDI.add(new TransType(869, "BSI", 3));
		EDI.add(new TransType(870, "BSR", 3));
		EDI.add(new TransType(875, "G50", 3));
		EDI.add(new TransType(880, "G01", 2));
		EDI.add(new TransType(940, "W05", 2));
		EDI.add(new TransType(943, "W06", 2));
		EDI.add(new TransType(944, "W17", 3));
		EDI.add(new TransType(945, "W06", 2));
		EDI.add(new TransType(947, "W15", 2));
		
		
		
		Iterator<TransType> EDIiter = EDI.iterator();
		
		
		Logger logger = Logger.getLogger("MyLog");  
		FileHandler fh;
		
		int mapPos;
		
		
		mapName = inputPathMXL;
		File inputFile = new File(inputPathMXL);
		String inputFileName = inputFile.getName().replaceAll("(?i).mxl", "");
		//JOptionPane.showConfirmDialog(null, "A: " + inputFileName);
		//System.out.println("inputFileName: " + inputFileName);
		mapOut = outputDirectory + inputFileName.substring(0, inputFileName.length() - 3) + ".mxl";
		//JOptionPane.showConfirmDialog(null, "B: " + mapOut);
		//mapOut = outputDirectory + currDir + ".mxl";
		
		//System.out.println("mapOut: " + mapOut);
		
		/*JOptionPane.showMessageDialog(null, "INPUTFILENAME: " + inputFileName);
		JOptionPane.showMessageDialog(null, "OUTPUTFILENAME: " + mapOut);
		*/
 
        // This will reference one line at a time
        String line = null;
        String pop_line = null;
        String fields = "";
        String temps = "";
        int pos_star = 0;
        int pos = 0;
        String[] map_from = new String[3];

        try {
			// FileReader for MXL
        	
        	FileInputStream iFIS = new FileInputStream(mapName);
			InputStreamReader mapReader = new InputStreamReader(iFIS , "UTF-8");
			// BufferedReader for MXL
			BufferedReader mxl = new BufferedReader(mapReader);
			
			// FileWriter for MXL
			OutputStreamWriter mapWriter = new OutputStreamWriter(new FileOutputStream(mapOut), "UTF-8");
			//BufferedWriter for MXL
			BufferedWriter mxlOut = new BufferedWriter(mapWriter);

			fh = new FileHandler(outputDirectory + "MapLog.log");  
			logger.addHandler(fh);

			String reportOut = opDir.getParent() + File.separator + "MapReviewReport.txt";
			
			//JOptionPane.showMessageDialog(null, reportOut);
			
			File reportOutFile = new File(reportOut);
			/*if (!reportOutFile.exists()) {
				reportOutFile.createNewFile();
				flagNEWFILE = 1;
			}*/
			
			
			FileWriter reivewReport = new FileWriter(reportOut, true);
			BufferedWriter reviewOut = new BufferedWriter(reivewReport);
			/*if (flagNEWFILE == 1) {
				
			}*/
			reviewOut.write("WORKPACKET: " + currDir);
			reviewOut.newLine();
			reviewOut.newLine();
			
			
			SimpleFormatter formatter = new SimpleFormatter();  
			fh.setFormatter(formatter); 
			
			
			
			
				
				//Reading MXL and writing to new MXL.
			while(((line = mxl.readLine()) != null)) {
				String lineLow = line.toLowerCase();
					
				//Setting author to IBM.
				if (line.startsWith("<Author>")) {
					line = "<Author>IBM</Author>";
					mxlOut.write(line);
					logger.info("Setting author name to IBM.\n\n");
					line = mxl.readLine();
					line = "<Description>" + inputFileName + "</Description>";
					logger.info("Setting map name in Details to " + inputFileName + ".\n\n");
				}
			
				if (line.contains("<EDIAssociations_IN>")) {
					mxlOut.write(line);
					mxlOut.newLine();
					line = mxl.readLine();
					while (!(line.contains("<AgencyDescription>"))) {
						mxlOut.write(line);
						mxlOut.newLine();
						//JOptionPane.showMessageDialog(null, line);
						if ((line.contains("<VersionID>")) && (line.length() > 11)) {
							if (!((line.substring(11).startsWith("</VersionID>")))) {
								flow = 1;
							}
						}
						if ((line.contains("<BindingID>")) && (line.length() > 11)) {
							if (!((line.substring(11).startsWith("</BindingID>")))) {
								tran = line.substring(11);
								if (tran.indexOf("</BindingID>") > 0) {
									tran = tran.substring(0, tran.indexOf("</BindingID>"));
								}
							}
						}
						line = mxl.readLine();
					}
					if (flow == 1) {
						mode = line.substring(19);
						//JOptionPane.showMessageDialog(null, line);
						if (mode.contains("</AgencyDescription>")) {
							int indexmode = mode.indexOf("</AgencyDescription>");
							mode = mode.substring(0, indexmode);
						}
						mode = mode.trim();
						mode = mode.toLowerCase();
                    }
				}
				if ((flow == 0) && (line.contains("<EDIAssociations_OUT>"))) {
					mxlOut.write(line);
					mxlOut.newLine();
					line = mxl.readLine();
					while (!(line.contains("<BindingID>"))) {
						mxlOut.write(line);
						mxlOut.newLine();
						line = mxl.readLine();
					}
					if (line.length() > 11) {
						if (!((line.substring(11).startsWith("</BindingID>")))) {
							tran = line.substring(11);
							if (tran.indexOf("</BindingID>") > 0) {
								tran = tran.substring(0, tran.indexOf("</BindingID>"));
							}
						}
					}
				}
                    
				if ((flagID == 0) && (tran.length() > 0)) {
					int tranint;
					TransType EDIinst;
					try {
						tranint = Integer.parseInt(tran);
						while (EDIiter.hasNext()) {
							EDIinst = EDIiter.next();
							if (EDIinst.getTrans() == tranint) {
								idSEG = EDIinst.getSeg();
								idELE = EDIinst.getEle();
								flagID = 1;
								//JOptionPane.showMessageDialog(null, idSEG, "", JOptionPane.INFORMATION_MESSAGE);
								logger.info("Transaction identified as " + tranint + ". \nProcess data shall be updated from segment: " + idSEG + ", element: " + idELE + ". \n\n");
								break;
							}
						}
					
                    }
                    catch (NumberFormatException nfe) {}
					if ((flagID == 0) && (flagIDerror == 0)) {
						logger.info("Transaction could not be identified. Process data XPATH 'TranslationOutput/BusinessReference' not updated. Please add manually. \n\n");
						flagIDerror = 1;
					}
						
				}
                    
                    
                    
                    //Setting syntax record.
				if ((line.contains("<TagDelimiterUsed>")) && (flow == 1) && (flag_SR == 0)) {
					
					if (mode.equals("x12"))
						mxlOut.write("<TagDelimiterUsed>no</TagDelimiterUsed>\n<TagDelimiter/><SegmentDelimiterUsed>no</SegmentDelimiterUsed>\n<SegmentDelimiter/><ElementDelimiterUsed>no</ElementDelimiterUsed>\n<ElementDelimiter/><RepeatingElementDelimiterUsed>no</RepeatingElementDelimiterUsed>\n<RepeatingElementDelimiter/><SubElementDelimiterUsed>no</SubElementDelimiterUsed>\n<SubElementDelimiter/><ReleaseCharacterUsed>no</ReleaseCharacterUsed>\n<ReleaseCharacter/><DecimalSeparatorUsed>no</DecimalSeparatorUsed>\n<DecimalSeparator/>\n<SyntaxRecordUsed>yes</SyntaxRecordUsed>\n<SyntaxRecord>\n<Length>107</Length>\n<Tag>ISA</Tag>\n<TagOffset>0</TagOffset>\n<ContainsData>yes</ContainsData>\n<TagDelimOffset>3</TagDelimOffset>\n<SegmentDelimOffset>105</SegmentDelimOffset>\n<ElementDelimOffset>3</ElementDelimOffset>\n<RepeatDelimOffset>65535</RepeatDelimOffset>\n<SubElementDelimOffset>104</SubElementDelimOffset>\n<RelCharOffset>65535</RelCharOffset>\n<DecimalSeparatorOffset>65535</DecimalSeparatorOffset>\n</SyntaxRecord>\n");
					if (mode.equals("edifact"))
						mxlOut.write("<TagDelimiterUsed>no</TagDelimiterUsed>\n<TagDelimiter/><SegmentDelimiterUsed>no</SegmentDelimiterUsed>\n<SegmentDelimiter/><ElementDelimiterUsed>no</ElementDelimiterUsed>\n<ElementDelimiter/><RepeatingElementDelimiterUsed>no</RepeatingElementDelimiterUsed>\n<RepeatingElementDelimiter/><SubElementDelimiterUsed>no</SubElementDelimiterUsed>\n<SubElementDelimiter/><ReleaseCharacterUsed>no</ReleaseCharacterUsed>\n<ReleaseCharacter/><DecimalSeparatorUsed>no</DecimalSeparatorUsed>\n<DecimalSeparator/>\n<SyntaxRecordUsed>yes</SyntaxRecordUsed>\n<SyntaxRecord>\n<Length>9</Length>\n<Tag>UNA</Tag>\n<TagOffset>0</TagOffset>\n<ContainsData>no</ContainsData>\n<TagDelimOffset>4</TagDelimOffset>\n<SegmentDelimOffset>8</SegmentDelimOffset>\n<ElementDelimOffset>4</ElementDelimOffset>\n<RepeatDelimOffset>7</RepeatDelimOffset>\n<SubElementDelimOffset>3</SubElementDelimOffset>\n<RelCharOffset>6</RelCharOffset>\n<DecimalSeparatorOffset>5</DecimalSeparatorOffset>\n</SyntaxRecord>\n");
					
					if ((mode.equals("x12")) || (mode.equals("edifact"))) {
						logger.info("Setting syntax record.\n\n");
						flag_SR = 1;
						while ((line = mxl.readLine()) != null) {
							if (line.contains("<StreamData>"))
								break;
						}
					}
					
				}
				
				// Checking if PreSession rule exists.
				if (line.contains("<PreSessionRule>")) {
					if (!(line.trim().equals("<PreSessionRule>"))) {
						if ((line.toLowerCase().contains("//created by")) || (line.toLowerCase().contains("//updated by"))) {
							flagPR = 1;
						}
					}
					else {
						mxlOut.write(line);
						mxlOut.newLine();
						line = mxl.readLine();
						if ((line.toLowerCase().contains("//created by")) || (line.toLowerCase().contains("//updated by"))) {
							flagPR = 1;
						}
					}
				}
				
                    
				/*//Writing username and date in precession rule.
				if ((line.contains("<PreSessionRule>")) && (userName != null) && (userName.length() != 0)) {
					flagPresession = 1;
					String part2 = line.substring(16);
					mxlOut.write("<PreSessionRule>");
					mxlOut.write("//Migrated by " + userName + " on " + currentDate + ".");
					mxlOut.newLine();
					mxlOut.write(part2);
					mxlOut.newLine();
					logger.info("Adding user name and date in presession rule.\n\n");
					line = mxl.readLine();
				}
                    
				if ((flagPresession == 0) && (line.contains("</MapDetails>")) && (userName != null) && (userName.length() != 0)) {
					flagPresession = 1;
					mxlOut.write("<ExplicitRule>\n<PreSessionRule>");
					mxlOut.write("//Migrated by " + userName + " on " + currentDate + ".");
					mxlOut.newLine();
					mxlOut.write("</PreSessionRule>");
					mxlOut.newLine();
					mxlOut.write("<PostSessionRule></PostSessionRule>");
					mxlOut.newLine();
					mxlOut.write("</ExplicitRule>");
					
				}
                 */   
                    //Setting syntax token.
				if (line.equals("<SyntaxTokens>")) {
					mxlOut.write("<SyntaxTokens>\n<Token>\n<Code>A</Code>\n<Range>\n<Start>Z</Start>\n<End>A</End>\n</Range>\n<Range>\n<Start>z</Start>\n<End>a</End>\n</Range>\n<Char>SP</Char>\n<Char>SP</Char>\n</Token>\n<Token>\n<Code>N</Code>\n<Range>\n<Start>9</Start>\n<End>0</End>\n</Range>\n<Char>.</Char>\n<Char>-</Char>\n<Char>+</Char>\n</Token>\n<Token>\n<Code>X</Code>\n<Range>\n<Start>Z</Start>\n<End>A</End>\n</Range>\n<Range>\n<Start>z</Start>\n<End>a</End>\n</Range>\n<Range>\n<Start>9</Start>\n<End>0</End>\n</Range>\n<Range>\n<Start>0xFF</Start>\n<End>0x01</End>\n</Range>\n<Char>.</Char>\n<Char>-</Char>\n<Char>+</Char>\n<Char>SP</Char>\n<Char>(</Char>\n<Char>)</Char>\n<Char>:</Char>\n<Char>/</Char>\n<Char>%</Char>\n<Char>\\</Char>\n<Char>!</Char>\n<Char>\"</Char>\n<Char>$</Char>\n<Char>^</Char>\n<Char>&amp;</Char>\n<Char>*</Char>\n<Char>_</Char>\n<Char>=</Char>\n<Char>[</Char>\n<Char>]</Char>\n<Char>{</Char>\n<Char>}</Char>\n<Char>;</Char>\n<Char>'</Char>\n<Char>?</Char>\n<Char>,</Char>\n<Char>&lt;</Char>\n<Char>&gt;</Char>\n<Char>~</Char>\n<Char>0xA3</Char>\n<Char>#</Char>\n<Char>|</Char>\n<Char>@</Char>\n</Token>\n<Token>\n<Code>J</Code>\n<Range>\n<Start>Z</Start>\n<End>A</End>\n</Range>\n<Range>\n<Start>z</Start>\n<End>a</End>\n</Range>\n<Range>\n<Start>9</Start>\n<End>0</End>\n</Range>\n<Range>\n<Start>/</Start>\n<End>!</End>\n</Range>\n<Range>\n<Start>@</Start>\n<End>:</End>\n</Range>\n<Range>\n<Start>`</Start>\n<End>[</End>\n</Range>\n<Range>\n<Start>~</Start>\n<End>{</End>\n</Range>\n<Range>\n<Start>0xDF</Start>\n<End>0xA1</End>\n</Range>\n<Char>SP</Char>\n</Token>\n");
					mxlOut.flush();
					logger.info("Setting syntax token.\n\n");
					while((line = mxl.readLine()) != null) {
						if (line.contains("</SyntaxTokens>")) {
							int posST = line.indexOf("</SyntaxTokens>");
							line = line.substring(posST);
							break;
						}
							
					}
				}
                    
				if (line.contains("<OUTPUT>")) {
					flagOUTPUT = 1;
					flagOUTPUT_DEL = 1;
				}

				if (line.startsWith("<CharacterEncoding>")) {
					line = "<CharacterEncoding>" + characterEncoding + "</CharacterEncoding>";
					logger.info("Setting character encoding.\n\n");
				}
				
				
				//Checking if file name logic exists.
				if ((flow == 1) && (line.contains("\"TranslationOutput/FileName\";"))) {
					//File name logic exists.
					fileNameLogicExists = 1;
				}
				
				
				if ((line.contains("<JavaEncoding>")) && (flagOUTPUT == 1)) {
					line = "<JavaEncoding>UTF8 (UTF8)</JavaEncoding>";
					logger.info("Setting java encoding for XML output to UTF8 (UTF8). \n\n");
					flagOUTPUT = 0;
				}
				if  ((line.contains("<UseCharEntityRef>")) && (flagOUTPUT == 1)) {
					mxlOut.write("<JavaEncoding>UTF8 (UTF8)</JavaEncoding>");
					mxlOut.newLine();
					mxlOut.flush();
					logger.info("Setting java encoding for XML output to UTF8 (UTF8). \n\n");
					
				}
				
				//Code for removing Correlation Data. Not needed now.
				/*if (line.contains("<ImplicitRuleDef>")) {
					String prevpline = line;
					line = mxl.readLine();
					if (line.contains("<UseUpdate>")) {
						String prevline = line;
						String tablename = "";
						line = mxl.readLine();
						tablename = line.substring(11, line.indexOf("</TableName>"));
						if (tablename.equals("Correlation Data")) {
							while (!(line.equals("</UseUpdate>"))) {
								line = mxl.readLine();
							}
							line = mxl.readLine();
							if (line.equals("</ImplicitRuleDef>")) {
								line = mxl.readLine();
							}
							else {
								mxlOut.write(prevpline);
								mxlOut.newLine();
							}
						}
						else {
							mxlOut.write(prevpline);
							mxlOut.newLine();
							mxlOut.write(prevline);
							mxlOut.newLine();
						}
							
					}
					else {
						mxlOut.write(prevpline);
						mxlOut.newLine();
					}
				}
				*/
				
                    
				if ((line.contains("<Composite>")) && (countISA == 15)) {
					compositeFlag = 0;
					ISAelementCount = 18;
				}
				
				if ((line.contains("<Field>")) && (countISA == 15) && (compositeFlag == 1)) {
					mxlOut.write("<Composite>\n<ID>1036</ID>\n<Name>ELEMENT</Name>\n<Description></Description>\n<Active>1</Active>\n<ChildCount>2</ChildCount>\n<Note></Note>\n<Mandatory>no</Mandatory>\n<MinUsage>0</MinUsage>\n<MaxUsage>1</MaxUsage>\n<TreatAsRepeat>no</TreatAsRepeat>\n<Field>\n<ID>1035</ID>\n<Name>ELEMENT_1</Name>\n<Description></Description>\n<Active>1</Active>\n<ChildCount>0</ChildCount>\n<Note></Note>\n<Mandatory>no</Mandatory>\n<NotUsed>no</NotUsed>\n<FieldNumber>0</FieldNumber>\n<StoreGroup>65535</StoreGroup>\n<StoreField>65535</StoreField>\n<BusinessName></BusinessName>\n<StoreLimit>\n<MaxLen>256</MaxLen>\n<MinLen>0</MinLen>\n<Signed>no</Signed>\n<DataType>string</DataType>\n<ImpliedDecimalPos>0</ImpliedDecimalPos>\n<ImplicitDecimal>no</ImplicitDecimal>\n<AllowSignedDecimal>0</AllowSignedDecimal>\n<Format></Format>\n<BinaryOutput>0</BinaryOutput>\n<BinaryWidth>0</BinaryWidth>\n</StoreLimit>\n<Element>0</Element>\n<ElementOpt>0</ElementOpt>\n<SubElement>0</SubElement>\n<SubElementOpt>0</SubElementOpt>\n<MinUsage>0</MinUsage>\n<MaxUsage>1</MaxUsage>\n<Binary>0</Binary>\n<TreatAsRepeat>no</TreatAsRepeat>\n<AlwaysOutputDelimiter>no</AlwaysOutputDelimiter>\n</Field>\n<Field>\n<ID>1034</ID>\n<Name>ELEMENT_2</Name>\n<Description></Description>\n<Active>1</Active>\n<ChildCount>0</ChildCount>\n<Note></Note>\n<Mandatory>no</Mandatory>\n<NotUsed>no</NotUsed>\n<FieldNumber>0</FieldNumber>\n<StoreGroup>65535</StoreGroup>\n<StoreField>65535</StoreField>\n<BusinessName></BusinessName>\n<StoreLimit>\n<MaxLen>256</MaxLen>\n<MinLen>0</MinLen>\n<Signed>no</Signed>\n<DataType>string</DataType>\n<ImpliedDecimalPos>0</ImpliedDecimalPos>\n<ImplicitDecimal>no</ImplicitDecimal>\n<AllowSignedDecimal>0</AllowSignedDecimal>\n<Format></Format>\n<BinaryOutput>0</BinaryOutput>\n<BinaryWidth>0</BinaryWidth>\n</StoreLimit>\n<Element>0</Element>\n<ElementOpt>0</ElementOpt>\n<SubElement>0</SubElement>\n<SubElementOpt>0</SubElementOpt>\n<MinUsage>0</MinUsage>\n<MaxUsage>1</MaxUsage>\n<Binary>0</Binary>\n<TreatAsRepeat>no</TreatAsRepeat>\n<AlwaysOutputDelimiter>no</AlwaysOutputDelimiter>\n</Field>\n</Composite>\n");
					mxlOut.flush();
					logger.info("Adding composite element to ISA.\n\n");
				}
                    
				if (line.indexOf("</Segment>") >= 0) {
					segmentMXL = "";
				}    
				if ((flow == 1) && (flagENV == 1)) {
					if (line.indexOf("</Segment>") >= 0) {
						flagENV = 0;
						logger.info("Updating " + segmentMXL + " fields.\n\n");
						segmentMXL = "";
					}
					if (line.startsWith("<Mandatory>"))
						line = "<Mandatory>no</Mandatory>";
					if (line.startsWith("<MaxLen>"))
						line = "<MaxLen>256</MaxLen>";
					if (line.startsWith("<MinLen>"))
						line = "<MinLen>0</MinLen>";
					if (line.startsWith("<DataType>"))
						line = "<DataType>string</DataType>";
					if (line.startsWith("<Format>"))
						line = "<Format>X</Format>";												
				}
				if ((flow == 1) && (flagISA == 1)) {
					if (line.indexOf("</Segment>") >= 0) {
						flagISA = 0;
						countISA = 0;
						logger.info("Updating " + segmentMXL + " fields.\n\n");
						segmentMXL = "";
					}
					if (countISA >= 0) {
						if (line.startsWith("<Mandatory>"))
							line = "<Mandatory>no</Mandatory>";
						if (line.startsWith("<MaxLen>"))
							line = "<MaxLen>256</MaxLen>";
						if (line.startsWith("<MinLen>"))
							line = "<MinLen>0</MinLen>";
						if (line.startsWith("<DataType>"))
							line = "<DataType>string</DataType>";
						if (line.startsWith("<Format>")) {
							line = "<Format>X</Format>";
							countISA = countISA + 1;
							if (countISA == ISAelementCount)
								countISA = -1;
						}
					}
				}
				
				if ((segmentMXL.equals("ST")) && (line.contains("<Field>"))) {
					countST += 1;
					if (countST == 1) {
						mxlOut.write(line);
						mxlOut.newLine();
						while (!((line = mxl.readLine()).contains("<Name>"))) {
							mxlOut.write(line);
							mxlOut.newLine();
						}
						int posend = line.indexOf("</Name>");
						ST01 = "#" + line.substring(6, posend);
					}
				}
				
				if ((segmentMXL.equals("GS")) && (line.contains("<Field>"))) {
					countGS += 1;
					if (countGS == 2) {
						mxlOut.write(line);
						mxlOut.newLine();
						while (!((line = mxl.readLine()).contains("<Name>"))) {
							mxlOut.write(line);
							mxlOut.newLine();
						}
						int posend = line.indexOf("</Name>");
						GS02 =  "#" + line.substring(6, posend);
					}
					if (countGS == 3) {
						mxlOut.write(line);
						mxlOut.newLine();
						while (!((line = mxl.readLine()).contains("<Name>"))) {
							mxlOut.write(line);
							mxlOut.newLine();
						}
						int posend = line.indexOf("</Name>");
						GS03 =  "#" + line.substring(6, posend);
					}
					if (countGS == 6) {
						mxlOut.write(line);
						mxlOut.newLine();
						while (!((line = mxl.readLine()).contains("<Name>"))) {
							mxlOut.write(line);
							mxlOut.newLine();
						}
						int posend = line.indexOf("</Name>");
						GS06 =  "#" + line.substring(6, posend);
						
						
					}
					
				}
				
				if ((segmentMXL.equals("UNG")) && (line.contains("<Field>"))) {
					countUNG += 1;
					//JOptionPane.showMessageDialog(null, countUNG, "", JOptionPane.INFORMATION_MESSAGE);
					if (countUNG == 2) {
						mxlOut.write(line);
						mxlOut.newLine();
						mxlOut.flush();
						while (!((line = mxl.readLine()).contains("<Name>"))) {
							mxlOut.write(line);
							mxlOut.newLine();
							mxlOut.flush();
						}
						int posend = line.indexOf("</Name>");
						UNG02 =  "#" + line.substring(6, posend);
						
					}
					if (countUNG == 4) {
						mxlOut.write(line);
						mxlOut.newLine();
						mxlOut.flush();
						while (!((line = mxl.readLine()).contains("<Name>"))) {
							mxlOut.write(line);
							mxlOut.newLine();
							mxlOut.flush();
						}
						int posend = line.indexOf("</Name>");
						UNG03 =  "#" + line.substring(6, posend);
						
					}
					
					
				}
				if ((segmentMXL.equals("UNH")) && (line.contains("<Field>"))) {
					countUNH += 1;
					if (countUNH == 1) {
						mxlOut.write(line);
						mxlOut.newLine();
						mxlOut.flush();
						while (!((line = mxl.readLine()).contains("<Name>"))) {
							mxlOut.write(line);
							mxlOut.newLine();
							mxlOut.flush();
						}
						int posend = line.indexOf("</Name>");
						UNH01 =  "#" + line.substring(6, posend);
					}
					if (countUNH == 2) {
						mxlOut.write(line);
						mxlOut.newLine();
						while (!((line = mxl.readLine()).contains("<Name>"))) {
							mxlOut.write(line);
							mxlOut.newLine();
							mxlOut.flush();
						}
						int posend = line.indexOf("</Name>");
						UNH02 =  "#" + line.substring(6, posend);
					}
					
					
				}
				
				/*if (line.contains("<MinLen>1</MinLen>")) {
					countML += 1;
					//JOptionPane.showMessageDialog(null, countML + " -- " + line, "", JOptionPane.INFORMATION_MESSAGE);
				}*/
				
				mxlOut.write(line);
				mxlOut.newLine();
				mxlOut.flush();
				
				
				if ((line.indexOf("<Segment>")) >= 0) {
					while ((line = mxl.readLine()) != null) {
						mxlOut.write(line);
						mxlOut.newLine();
						if (line.startsWith("<Tag>")) {
							line = line.trim();
							int pos1 = line.indexOf("</");
							segmentMXL = line.substring(5, pos1);
							cntELE = 0;
							break;
						}
					}
				}
					
				if ((segmentMXL.equals("GS")) ||
					(segmentMXL.equals("ST")) ||
					(segmentMXL.equals("SE")) ||
					(segmentMXL.equals("GE")) ||
					(segmentMXL.equals("IEA"))) {
					flagENV = 1;
					
				}
				if (segmentMXL.equals("ISA")) {
					flagISA = 1;
					
				}
				
					
				//JOptionPane.showMessageDialog(null, "LINE: " + line);
				
				/*if ((flow == 1) && (segmentMXL.equals("ST")) && (countST == 2) && (line.contains("</StoreLimit>"))) {
					fileNameLogicX = "//********************************************************\n// Set the FileName for the BP to use\n//********************************************************\nstring[256] strProcessID;string[256] strFileName;\nstrProcessID =\"\";\nstrFileName = \"\";\nselect xpathresult into strProcessID from processdata where Xpath=\"ProcessID\";\nstrFileName = " +
							GS02 +
							" + \"_\" + " +
							GS03 +
							" + \"_\" + " + 
							ST01 +
							" + \"_\" + strProcessID + \".IDOC\"" +
							";\nupdate processdata set xpathresult = strFileName where xpath=\"TranslationOutput/FileName\";"; 
					line = mxl.readLine();
					linelow = line.toLowerCase();
					if (line.contains("<ExplicitRule>")) {
						mxlOut.write("<ExplicitRule>");
					
						line = line.substring(14);
						linelow = line.toLowerCase().trim();
						while(	(linelow.startsWith("integer")) ||
								(linelow.startsWith("string")) ||
								(linelow.startsWith("object")) ||
								(linelow.startsWith("real")) ||
								(linelow.startsWith("datetime")) ||
								(linelow.startsWith("//"))) {
							mxlOut.write(line);
							mxlOut.newLine();
							line = mxl.readLine();
							linelow = line.toLowerCase().trim();
						}
						mxlOut.write(fileNameLogicX);
						mxlOut.newLine();
						mxlOut.newLine();
						mxlOut.write(line);
						mxlOut.newLine();
					}
					else {
						mxlOut.write("<ExplicitRule>");
						mxlOut.newLine();
						mxlOut.write(fileNameLogicX);
						mxlOut.newLine();
						mxlOut.write("</ExplicitRule>");
						mxlOut.newLine();
						mxlOut.write(line);
						mxlOut.newLine();
						logger.info("Added file name logic to ST02.\n\n");
					}
				}*/
				if ((flow == 1) && (segmentMXL.equals("UNH")) && (countUNH == 2) && (line.contains("</StoreLimit>"))) {
					fileNameLogicE = "//********************************************************\n// Set the FileName for the BP to use\n//********************************************************\nstring[256] strProcessID;\nstring[256] strFileName;\n\nstrProcessID =\"\";\nstrFileName = \"\";\nselect xpathresult into strProcessID from processdata where Xpath=\"ProcessID\";\nstrFileName = " +
							UNG02 +
							" + \"_\" + " +
							UNG03 +
							" + \"_\" + " +
							UNH02 +
							" + \"_\" + strProcessID + \".IDOC\"" +
							";\nupdate processdata set xpathresult = strFileName where xpath=\"TranslationOutput/FileName\";\n";
					//JOptionPane.showMessageDialog(null, fileNameLogicE, "", JOptionPane.INFORMATION_MESSAGE);
					
					line = mxl.readLine();
					linelow = line.toLowerCase();
					if (line.contains("<ExplicitRule>")) {
						mxlOut.write("<ExplicitRule>");
						mxlOut.flush();
						line = line.substring(14);
						linelow = line.toLowerCase().trim();
						while(	(linelow.startsWith("integer")) ||
								(linelow.startsWith("string")) ||
								(linelow.startsWith("object")) ||
								(linelow.startsWith("real")) ||
								(linelow.startsWith("datetime")) ||
								(linelow.startsWith("//"))) {
							mxlOut.write(line);
							mxlOut.newLine();
							mxlOut.flush();
							line = mxl.readLine();
							linelow = line.toLowerCase().trim();
						}
						mxlOut.write(fileNameLogicE);
						mxlOut.newLine();
						mxlOut.newLine();
						mxlOut.flush();
						mxlOut.write(line);
						mxlOut.flush();
						mxlOut.newLine();
						logger.info("Added file name logic to UNH02.\n\n");
					}
					else {
						mxlOut.write("<ExplicitRule>");
						mxlOut.newLine();
						mxlOut.write(fileNameLogicE);
						mxlOut.newLine();
						mxlOut.write("</ExplicitRule>");
						mxlOut.newLine();
						mxlOut.write(line);
						mxlOut.newLine();
					}
				}
				
					
				if ((flagID == 1) && (segmentMXL.equals(idSEG)) && (line.contains("<Name>"))) {
					cntELE += 1;
					
					if ((flagUPDATE == 0) && (cntELE == idELE)) {
						//JOptionPane.showMessageDialog(null, "TRANSACTIONUPDATE", "", JOptionPane.INFORMATION_MESSAGE);
						
						idELEName = line.substring(6);
						if (idELEName.contains("</Name>"))
							idELEName = idELEName.substring(0, (idELEName.indexOf("</Name>")));
						
						while ((flagUPDATE == 0) && ((line = mxl.readLine()) != null)) {
							lineLow = line.toLowerCase();
							
							if (line.contains("</ExplicitRule>")) {
								line = line.replaceAll("</ExplicitRule>", ("\nUPDATE processdata set xpathresult = #" + idELEName + " where xpath = \"TranslationOutput/BusinessReference\";\n</ExplicitRule>"));
								flagUPDATE = 1;
								//JOptionPane.showMessageDialog(null, "UPDATED", "", JOptionPane.INFORMATION_MESSAGE);
							}
							if ((flagUPDATE == 0) && (line.contains("<FieldType>"))) {
								mxlOut.write("<ExplicitRule>UPDATE processdata set xpathresult = #" + idELEName + " where xpath = \"TranslationOutput/BusinessReference\";\n</ExplicitRule>");
								mxlOut.newLine();
								flagUPDATE = 1;
							}
							if ((flagUPDATE == 0) && (line.contains("<ImplicitRuleDef>"))) {
								mxlOut.write("<ExplicitRule>UPDATE processdata set xpathresult = #" + idELEName + " where xpath = \"TranslationOutput/BusinessReference\";\n</ExplicitRule>");
								mxlOut.newLine();
								flagUPDATE = 1;
							}
							if ((flagUPDATE == 0) && (line.contains("<ConditionalRuleDef>"))) {
								mxlOut.write("<ExplicitRule>UPDATE processdata set xpathresult = #" + idELEName + " where xpath = \"TranslationOutput/BusinessReference\";\n</ExplicitRule>");
								mxlOut.newLine();
								flagUPDATE = 1;
							}
							if ((flagUPDATE == 0) && (line.contains("<Element>"))) {
								mxlOut.write("<ExplicitRule>UPDATE processdata set xpathresult = #" + idELEName + " where xpath = \"TranslationOutput/BusinessReference\";\n</ExplicitRule>");
								mxlOut.newLine();
								flagUPDATE = 1;
							}
							
							//Code for removing Correlation Data. Not needed now.
							/*if (line.contains("<ImplicitRuleDef>")) {
								String prevpline = line;
								line = mxl.readLine();
								if (line.contains("<UseUpdate>")) {
									String prevline = line;
									String tablename = "";
									line = mxl.readLine();
									tablename = line.substring(11, line.indexOf("</TableName>"));
									if (tablename.equals("Correlation Data")) {
										while (!(line.equals("</UseUpdate>"))) {
											line = mxl.readLine();
										}
										line = mxl.readLine();
										if (line.equals("</ImplicitRuleDef>")) {
											line = mxl.readLine();
										}
										else {
											mxlOut.write(prevpline);
											mxlOut.newLine();
										}
									}
									else {
										mxlOut.write(prevpline);
										mxlOut.newLine();
										mxlOut.write(prevline);
										mxlOut.newLine();
									}
										
								}
								else {
									mxlOut.write(prevpline);
									mxlOut.newLine();
								}
							}
							*/
							
							
							mxlOut.write(line);
							mxlOut.newLine();
						}
						
					}
				}
					
				
					
			}
			//Close BufferedReader and BufferedWriter for MXL.
				
			mxl.close();
			mxlOut.close();
			
			
			if (flagPR == 0) {
				reportCount++;
				reviewOut.write(reportCount + ". No created/updated statement found in PreSessionRule. Please add.");
				reviewOut.newLine();
			}
			
			if (fileNameLogicExists == 0) {
				reportCount++;
				reviewOut.write(reportCount + ". File name logic couldn't be found in map. Please add.");
				reviewOut.newLine();
			}
			
			
			fh.close();
			
			if (reportCount == 0) {
				reviewOut.write("Nothing to be reported.");
				reviewOut.newLine();
			}
			reviewOut.newLine();
			reviewOut.write("*****************************************************");
			reviewOut.newLine();
			reviewOut.newLine();
			
			reviewOut.close();
				
		}
		catch(IOException ex) {
			JOptionPane.showMessageDialog(null, ("Error reading file '" + mapName + "'"), "", JOptionPane.ERROR_MESSAGE);
			
		}
		
        
    }
}
