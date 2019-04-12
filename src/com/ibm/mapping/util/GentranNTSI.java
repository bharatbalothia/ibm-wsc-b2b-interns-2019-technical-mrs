package com.ibm.mapping.util;

// Created by Sukanya Dasgupta on July 12, 2015.

import java.awt.BorderLayout;
import java.io.*;
import java.util.*;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.text.*;

import org.jdom2.*;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.filter.*;
import org.jdom2.filter.ElementFilter;
import org.jdom2.output.XMLOutputter;
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

public class GentranNTSI {
	public static String correctExtendedRule (String line) {
		String resultLine;
		resultLine = line.replaceAll("(?i) divisionlookup", " codelist").replaceAll("(?i) divisionxref", " codelist").replaceAll("(?i) tablename", " name").replaceAll("(?i) item", " sendercode").replaceAll("(?i) partneritem", " Text1").replaceAll("(?i) myitem", " sendercode").replaceAll("(?i)update document", "//UPDATE DOCUMENT");
		return resultLine;
	}
	public static void migrate_MAP(String inputPathMXL,String inputPathPTF,String outputDirectory,String characterEncoding,String userName,String delimiter_1,String delimiter_2) {

	
		//Taking input path and output directory as input from command line.
		/*String inputPathMXL = args[0];
		String inputPathPTF = args[1];
		
		String outputDirectory = args[2];
        String characterEncoding = args[3];
        String userName = args[4];*/
        
        String currentDate;
        DateFormat currentDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        currentDate = currentDateFormat.format(new Date());
		
        String rootDir = outputDirectory.substring(0, outputDirectory.lastIndexOf("\\"));
        String rootPath = rootDir + "\\";
        outputDirectory = outputDirectory + "\\";
        int flagPresession = 0;
        int flow = 0; //0=OUTBOUND 1=INBOUND
        String mode = "";
		String sendercode = "";
		String segment = "";
		String segmentPTF = "";
		String segmentMXL = "";
        String line1 = "";
        String firstWord = "";
        String fileName = "ptf.txt";
		fileName = inputPathPTF;
        String codelist = null;
        String field = "";
        String squery = "";
		String mapName = "";
		String mapOut = "";
		String query = "";
		BufferedWriter eW;
        int ISAelementCount = 16;
        int compositeFlag = 1;
		int error20001 = 0;
		int extrul = 1;
		int flagISA = 0;
		int flagENV = 0;
		int countISA = 0;
		String tran = "";
		String idSEG = "";
		int idELE = 0;
		int flagID = 0;
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
		Set<String> codelists = new TreeSet<String>();
		int possyntaxout = 0;
		String action = "";
		int flag_SR = 0;
		
		
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
		EDI.add(new TransType(870, "BSR", 3));
		
		
		Iterator<TransType> EDIiter = EDI.iterator();
		
		
		Logger logger = Logger.getLogger("MyLog");  
		FileHandler fh;
		
		int mapPos;
		
		List<String> segmentList_S = new ArrayList<String>();
		List<String> fieldList_S = new ArrayList<String>();	
		List<String> queryList_S = new ArrayList<String>();
		
		mapName = inputPathMXL;
		File inputFile = new File(inputPathMXL);
		String inputFileName = inputFile.getName().replaceAll("(?i).mxl", "");
		//System.out.println("inputFileName: " + inputFileName);
		mapOut = outputDirectory + inputFileName + ".mxl";
		//System.out.println("mapOut: " + mapOut);
		

        ArrayDeque<String> ptf_lines = new ArrayDeque<String>();
 
        // This will reference one line at a time
        String line = null;
        String pop_line = null;
        String fields = "";
        String temps = "";
        int pos_star = 0;
        int pos = 0;
        String[] map_from = new String[3];

        try {
            // FileReader for PTF
            FileReader fileReader = new FileReader(fileName);
			// BufferedReader for PTF
            BufferedReader bufferedReader = new BufferedReader(fileReader);
				
			try {
			
				if  (!(mapName.equals(""))) {
					
					String errorFileName = outputDirectory + "error20001.txt";
					try {
						FileWriter errorWriter = new FileWriter(errorFileName);
						eW = new BufferedWriter(errorWriter);
						SAXBuilder saxBuilder = new SAXBuilder();
						Document mapX= saxBuilder.build(mapName);
						//System.out.println("Root Element: " + mapX.getRootElement().getName());
						ElementFilter fieldE = new ElementFilter("Field");
						Iterator<Element> XMLfields =  mapX.getDescendants(fieldE);
						Element currentField;
						Namespace ns = Namespace.getNamespace("http://www.stercomm.com/SI/Map");
						
						while (XMLfields.hasNext()) {
							currentField = XMLfields.next();
							
							Element storeE = currentField.getChild("StoreLimit", ns);
							if (storeE != null) {
								Element dataType = storeE.getChild("DataType", ns);
								Element format = storeE.getChild("Format", ns);
								
								if (((format.getText()).equals("")) && (!(dataType.getText().equals("string")))) {
									if (error20001 == 0) {
										error20001 = 1;
									}
									eW.write("Field: " + currentField.getChild("Name", ns).getText());
									eW.newLine();
									eW.write("Datatype: " + dataType.getText());
									eW.newLine();
									eW.write("Format: " + format.getText());
									eW.newLine();
									eW.newLine();
								
								}
							}
						}
						if (error20001 == 0) {
							eW.write("No errors found.");
							
						}
						eW.close();
					}
					catch(IOException ex) {}
				}
			
				
				// FileReader for MXL
				FileReader mapReader = new FileReader(mapName);
				// BufferedReader for MXL
				BufferedReader mxl = new BufferedReader(mapReader);
				// FileWriter for MXL
				FileWriter mapWriter = new FileWriter(mapOut);
				//BufferedWriter for MXL
				BufferedWriter mxlOut = new BufferedWriter(mapWriter);

				FileWriter clR = new FileWriter(outputDirectory + "Codelist_Report.txt");
				BufferedWriter clRep = new BufferedWriter(clR);
				clRep.write("\n****************************** CODELIST REPORT ******************************\n\n");
								
				fh = new FileHandler(outputDirectory + "MapLog.log");  
				logger.addHandler(fh);

				
				SimpleFormatter formatter = new SimpleFormatter();  
				fh.setFormatter(formatter); 
				
				
				
				//Reading PTF
				while((line = bufferedReader.readLine()) != null) {
					//Check if it is a segment.
					if (line.startsWith("  Segment ")) {
						line = line.replaceAll(" +", " ");
						segmentPTF = line.split(" ")[2].replace("*", "");
					}
					//Check if it is a codelist rule.
					if ((line.indexOf("SELECT FROM") >= 0) && (line.indexOf("SELECT FROM         Division") < 0)) 
					{	
						
						pos_star = -1;
						while (pos_star == -1)
						{
							pop_line = ptf_lines.pop();
							pos_star = pop_line.indexOf("*");
						}
						
						field = pop_line.substring(0,pos_star).trim();
						
						logger.info("Reading SELECT Standard Rule on field " + field + ".\nSkipping this rule. Table name not recognized.\nPlease check.\n\n");
						clRep.write("Codelist rule couldn't be identified. Please check rule on field: " + field + "\n");
					}
					if (line.indexOf("SELECT FROM         Division") > 0) {
						pos_star = -1;
						while (pos_star == -1)
						{
							pop_line = ptf_lines.pop();
							pos_star = pop_line.indexOf("*");
						}
						field = pop_line.substring(0,pos_star).trim();
						if (field.startsWith("Pcdata"))
							field = field.substring(7);
						sendercode = "#" + field;
						fieldList_S.add(field);
						query = "select ";
						//pos = line.indexOf("SELECT FROM         Division") + 37;
						pos = 112;
						fields = "";
						temps = "";
						codelist = line.substring(pos);
						codelist = codelist.trim();
						while((line = bufferedReader.readLine()) != null) {
							line = line.trim();
							pos = line.indexOf("MAP FROM");
							if (pos == -1)
								break;
							else {
								line = (line.substring(pos + 8)).trim();
								line = line.replaceAll("Text 1", "Text1").replaceAll("Text 2", "Text2").replaceAll("Text 3", "Text3").replaceAll("Text 4", "Text4").replaceAll("My Item", "sendercode");
								
								map_from = line.split(" +");
								if (map_from[0].equals("sendercode")) {
									sendercode = "#" + map_from[2].trim();
								}
								else {
									fields = fields + map_from[0];
									fields = fields + ", ";
									temps = temps + "#" + map_from[2].trim();
									temps = temps + ", ";
								}
							} 
						}
						fields = fields.substring(0, fields.length() - 2);
						temps = temps.substring(0, temps.length() - 2);
                        if (segmentPTF.equals("ISA"))
                            sendercode = "trim(" + sendercode + ")";
						query += fields;
						query += " into ";
						query += temps;
						query += " from codelist where name=\"";
						query += codelist;
						query += "\" and sendercode = ";
						query += sendercode;
						query += ";";
						queryList_S.add(query);
						segmentList_S.add(segmentPTF);
						logger.info("Reading codelist rule on field " + field + ".\nQuery: " + query + "\n\n");
						clRep.write("Codelist rule found in standard rule of field: " + field + " codelist name: " + codelist + "\n");
						codelists.add(codelist);
					}
					else 
						ptf_lines.push(line);
				
					if (line.trim().equals("Extended Rules")) {
						extrul = 1;
						break;
					}
				}

				// Iterators for list of segments and fields and corresponding queries with standard rules.
				Iterator<String> segmentIterator = segmentList_S.iterator();
				Iterator<String> fieldIterator = fieldList_S.iterator();
				Iterator<String> queryIterator = queryList_S.iterator();
				if (fieldIterator.hasNext()) {
					segment = segmentIterator.next();
					field = fieldIterator.next();
					query = queryIterator.next();
				}

				//Reading MXL and writing to new MXL.
				while(((line = mxl.readLine()) != null)) {
					String lineLow = line.toLowerCase();
					if ((((lineLow.trim().indexOf("select ")) == 0) || ((lineLow.trim().indexOf("update document")) == 0)) && (!(lineLow.trim().startsWith("//"))))  {
						logger.info("Correcting extended rule: " + line + "\n\n");
						lineTN = line;
						if (lineLow.contains("tablename")) {
							flagTN = 1;
							lineTN = line.substring(lineLow.indexOf("tablename") + 9);
						}
						if ((flagTN == 1) && ((lineTN.indexOf("\"")) >= 0)) {
							clName = lineTN.substring(lineTN.indexOf("\"") + 1);
							if (clName.contains("\""))
								clName = clName.substring(0, clName.indexOf("\""));
							flagTN = 0;
							clRep.write("Codelist rule found and corrected in extended rule for codelist: " + clName + "\n");
							codelists.add(clName);
						}
						line = GentranNTSI.correctExtendedRule(line);
						while (!(line.contains(";"))) {
							mxlOut.write(line);
							mxlOut.newLine();
							line = mxl.readLine();
							lineLow = line.toLowerCase();
							lineTN = line;
							if (lineLow.contains("tablename")) {
								flagTN = 1;
								lineTN = line.substring(lineLow.indexOf("tablename") + 9);
							}
							if ((flagTN == 1) && ((lineTN.indexOf("\"")) >= 0)) {
								clName = lineTN.substring(lineTN.indexOf("\"") + 1);
								if (clName.contains("\""))
									clName = clName.substring(0, clName.indexOf("\""));
								flagTN = 0;
								clRep.write("Codelist rule found and corrected in extended rule for codelist: " + clName + "\n");
								codelists.add(clName);
							}
							line = GentranNTSI.correctExtendedRule(line);
							logger.info("Correcting extended rule: " + line + "\n\n");
						}
					}
					
					//Setting author to IBM.
                    if (line.startsWith("<Author>")) {
                        line = "<Author>IBM</Author>";
                        logger.info("Setting author name to IBM.\n\n");
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
	                    			logger.info("Transaction identified as " + EDIinst + " Process data shall be updated from segment: " + idSEG + ", element: " + idELE + ". \n\n");
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
                    
                    //Writing username and date in precession rule.
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
                    
                    //Setting syntax token.
                    if (line.equals("<SyntaxTokens>")) {
                        mxlOut.write("<SyntaxTokens>\n<Token>\n<Code>A</Code>\n<Range>\n<Start>Z</Start>\n<End>A</End>\n</Range>\n<Range>\n<Start>z</Start>\n<End>a</End>\n</Range>\n<Char>SP</Char>\n<Char>SP</Char>\n</Token>\n<Token>\n<Code>N</Code>\n<Range>\n<Start>9</Start>\n<End>0</End>\n</Range>\n<Char>.</Char>\n<Char>-</Char>\n<Char>+</Char>\n</Token>\n<Token>\n<Code>X</Code>\n<Range>\n<Start>Z</Start>\n<End>A</End>\n</Range>\n<Range>\n<Start>z</Start>\n<End>a</End>\n</Range>\n<Range>\n<Start>9</Start>\n<End>0</End>\n</Range>\n<Range>\n<Start>0xFF</Start>\n<End>0x01</End>\n</Range>\n<Char>.</Char>\n<Char>-</Char>\n<Char>+</Char>\n<Char>SP</Char>\n<Char>(</Char>\n<Char>)</Char>\n<Char>:</Char>\n<Char>/</Char>\n<Char>%</Char>\n<Char>\\</Char>\n<Char>!</Char>\n<Char>\"</Char>\n<Char>$</Char>\n<Char>^</Char>\n<Char>&amp;</Char>\n<Char>*</Char>\n<Char>_</Char>\n<Char>=</Char>\n<Char>[</Char>\n<Char>]</Char>\n<Char>{</Char>\n<Char>}</Char>\n<Char>;</Char>\n<Char>'</Char>\n<Char>?</Char>\n<Char>,</Char>\n<Char>&lt;</Char>\n<Char>&gt;</Char>\n<Char>~</Char>\n<Char>0xA3</Char>\n<Char>#</Char>\n<Char>|</Char>\n<Char>@</Char>\n</Token>\n<Token>\n<Code>J</Code>\n<Range>\n<Start>Z</Start>\n<End>A</End>\n</Range>\n<Range>\n<Start>z</Start>\n<End>a</End>\n</Range>\n<Range>\n<Start>9</Start>\n<End>0</End>\n</Range>\n<Range>\n<Start>/</Start>\n<End>!</End>\n</Range>\n<Range>\n<Start>@</Start>\n<End>:</End>\n</Range>\n<Range>\n<Start>`</Start>\n<End>[</End>\n</Range>\n<Range>\n<Start>~</Start>\n<End>{</End>\n</Range>\n<Range>\n<Start>0xDF</Start>\n<End>0xA1</End>\n</Range>\n<Char>SP</Char>\n</Token>\n");
                        logger.info("Setting syntax token.\n\n");
                        while((line = mxl.readLine()) != null) {
                            if (line.equals("</SyntaxTokens>"))
                                break;
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
				    if ((flagOUTPUT_DEL == 1) && (line.contains("<PosSyntax>")) && (possyntaxout == 0)) {
				    	File dirR = new File(rootDir);
				    	String[] rootF = dirR.list();
				    	List<String> rootFiles = Arrays.asList(rootF);
				    	if (rootFiles.contains("possyntax.txt")) {
				    		FileReader psf = new FileReader(rootPath + "possyntax.txt");
				    		BufferedReader psb = new BufferedReader(psf);
				    		del1 = "";
				    		del2 = "";
				    		String psbl;
				    		psbl = psb.readLine();
				    		if (psbl != null)
				    			action = psbl;
				    		psbl = psb.readLine();
				    		if (psbl != null)
				    			del1 = psbl;
				    		psbl = psb.readLine();
				    		if (psbl != null)
				    			del2 = psbl;
				    		psb.close();
				    		possyntaxout = 1;
				    		if (action.equals("DO")) {
				    			mxlOut.write(line);
						        mxlOut.newLine();
						        if (!(del1.equals("")))
						           mxlOut.write("<DelimiterUsed>yes</DelimiterUsed>\n<Delimiter1>" + del1 + "</Delimiter1>\n");
						        else
						           mxlOut.write("<DelimiterUsed>no</DelimiterUsed>\n<Delimiter1/>\n");
						        if (!(del2.equals("")))
						      	   mxlOut.write("<Delimiter2Used>yes</Delimiter2Used>\n<Delimiter2>" + del2 + "</Delimiter2>\n");
						        else
					        	   mxlOut.write("<Delimiter2Used>no</Delimiter2Used>\n<Delimiter2/>\n");
				   	            while(!(line.contains("<RecordLengthUsed>"))) {
						       	   line = mxl.readLine();
						        }
						        line = line.substring(line.indexOf("<RecordLengthUsed>"));
				    		}
				    		
				    	}
				    	else {
				    		FileWriter psfw = new FileWriter(rootPath + "possyntax.txt");
				    		BufferedWriter psbw = new BufferedWriter(psfw);
					        //del1 = "";
					        //del2 = "";

					        /*int result = JOptionPane.showConfirmDialog(null, myPanel, 
					                 "Enter delimiter values for positional output syntax.", JOptionPane.OK_CANCEL_OPTION);*/
					        possyntaxout = 1;
					        //if (result == JOptionPane.OK_OPTION) {
					           psbw.write("DO");
					           psbw.newLine();
					        
					           psbw.write(delimiter_1);
					           psbw.newLine();
					           psbw.write(delimiter_2);
					           mxlOut.write(line);
					           mxlOut.newLine();
					           if (!(delimiter_1.equals("")))
					        	   mxlOut.write("<DelimiterUsed>yes</DelimiterUsed>\n<Delimiter1>" + delimiter_1 + "</Delimiter1>\n");
					           else
					        	   mxlOut.write("<DelimiterUsed>no</DelimiterUsed>\n<Delimiter1/>\n");
					           if (!(delimiter_2.equals("")))
					        	   mxlOut.write("<Delimiter2Used>yes</Delimiter2Used>\n<Delimiter2>" + delimiter_2 + "</Delimiter2>\n");
					           else
					        	   mxlOut.write("<Delimiter2Used>no</Delimiter2Used>\n<Delimiter2/>\n");
					           while(!(line.contains("<RecordLengthUsed>"))) {
					        	   line = mxl.readLine();
					           }
					           line = line.substring(line.indexOf("<RecordLengthUsed>"));
					        /*}
					        else {
					        	psbw.write("DON'T");
					        }*/
					        psbw.close();
				    	}
				    }
				    
				    if ((line.contains("<JavaEncoding>")) && (flagOUTPUT == 1)) {
                    	line = "<JavaEncoding>UTF8 (UTF8)</JavaEncoding>";
                    	logger.info("Setting java encoding for XML output to UTF8 (UTF8). \n\n");
                    	flagOUTPUT = 0;
                    }
                    if  ((line.contains("<UseCharEntityRef>")) && (flagOUTPUT == 1)) {
				    	mxlOut.write("<JavaEncoding>UTF8 (UTF8)</JavaEncoding>");
				    	mxlOut.newLine();
				    	logger.info("Setting java encoding for XML output to UTF8 (UTF8). \n\n");
				    	
				    }
                    
                    if ((line.contains("<Composite>")) && (countISA == 15))
                    {
                        compositeFlag = 0;
                        ISAelementCount = 18;
                    }
                    
                    if ((line.contains("<Field>")) && (countISA == 15) && (compositeFlag == 1)) {
                        mxlOut.write("<Composite>\n<ID>1036</ID>\n<Name>ELEMENT</Name>\n<Description></Description>\n<Active>1</Active>\n<ChildCount>2</ChildCount>\n<Note></Note>\n<Mandatory>no</Mandatory>\n<MinUsage>0</MinUsage>\n<MaxUsage>1</MaxUsage>\n<TreatAsRepeat>no</TreatAsRepeat>\n<Field>\n<ID>1035</ID>\n<Name>ELEMENT_1</Name>\n<Description></Description>\n<Active>1</Active>\n<ChildCount>0</ChildCount>\n<Note></Note>\n<Mandatory>no</Mandatory>\n<NotUsed>no</NotUsed>\n<FieldNumber>0</FieldNumber>\n<StoreGroup>65535</StoreGroup>\n<StoreField>65535</StoreField>\n<BusinessName></BusinessName>\n<StoreLimit>\n<MaxLen>256</MaxLen>\n<MinLen>0</MinLen>\n<Signed>no</Signed>\n<DataType>string</DataType>\n<ImpliedDecimalPos>0</ImpliedDecimalPos>\n<ImplicitDecimal>no</ImplicitDecimal>\n<AllowSignedDecimal>0</AllowSignedDecimal>\n<Format></Format>\n<BinaryOutput>0</BinaryOutput>\n<BinaryWidth>0</BinaryWidth>\n</StoreLimit>\n<Element>0</Element>\n<ElementOpt>0</ElementOpt>\n<SubElement>0</SubElement>\n<SubElementOpt>0</SubElementOpt>\n<MinUsage>0</MinUsage>\n<MaxUsage>1</MaxUsage>\n<Binary>0</Binary>\n<TreatAsRepeat>no</TreatAsRepeat>\n<AlwaysOutputDelimiter>no</AlwaysOutputDelimiter>\n</Field>\n<Field>\n<ID>1034</ID>\n<Name>ELEMENT_2</Name>\n<Description></Description>\n<Active>1</Active>\n<ChildCount>0</ChildCount>\n<Note></Note>\n<Mandatory>no</Mandatory>\n<NotUsed>no</NotUsed>\n<FieldNumber>0</FieldNumber>\n<StoreGroup>65535</StoreGroup>\n<StoreField>65535</StoreField>\n<BusinessName></BusinessName>\n<StoreLimit>\n<MaxLen>256</MaxLen>\n<MinLen>0</MinLen>\n<Signed>no</Signed>\n<DataType>string</DataType>\n<ImpliedDecimalPos>0</ImpliedDecimalPos>\n<ImplicitDecimal>no</ImplicitDecimal>\n<AllowSignedDecimal>0</AllowSignedDecimal>\n<Format></Format>\n<BinaryOutput>0</BinaryOutput>\n<BinaryWidth>0</BinaryWidth>\n</StoreLimit>\n<Element>0</Element>\n<ElementOpt>0</ElementOpt>\n<SubElement>0</SubElement>\n<SubElementOpt>0</SubElementOpt>\n<MinUsage>0</MinUsage>\n<MaxUsage>1</MaxUsage>\n<Binary>0</Binary>\n<TreatAsRepeat>no</TreatAsRepeat>\n<AlwaysOutputDelimiter>no</AlwaysOutputDelimiter>\n</Field>\n</Composite>\n");
                        logger.info("Adding composite element to ISA.\n\n");
                    }
                    
                    
                    if (flagENV == 1) {
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
					if (flagISA == 1) {
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

                    

                    
					String searchLine = "<Name>" + field + "</Name>";
					mxlOut.write(line);
					mxlOut.newLine();
					if ((line.indexOf("<Segment>")) >= 0) {
						while ((line = mxl.readLine()) != null) {
							mxlOut.write(line);
							mxlOut.newLine();
							if (line.startsWith("<Name>")) {
								line = line.trim();
								int pos1 = line.indexOf("</");
								segmentMXL = line.substring(6, pos1);
								cntELE = -1;
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
					
					if ((line.equals(searchLine)) && (segment.equals(segmentMXL))) {
						
						 if ((flagID == 1) && (segmentMXL.equals(idSEG)))
							cntELE += 1;
						
						 if ((flagID == 1) && (segmentMXL.equals(idSEG)) && (cntELE == idELE)) {
							 updateS = "UPDATE processdata set xpathresult = #" + field + " where xpath = \"TranslationOutput/BusinessReference\";";
							 flagUPDATE = 1;
						 }
						 
						 	
						 while ((line = mxl.readLine()) != null) {
							 if (line.contains("<OUTPUT>")) {
			                    flagOUTPUT = 1;
		                    	flagOUTPUT_DEL = 1;
			                 }
							 
							 if (line.startsWith("<CharacterEncoding>")) {
								line = "<CharacterEncoding>" + characterEncoding + "</CharacterEncoding>";
                                logger.info("Setting character encoding.\n\n");
							}
							
							 if ((flagOUTPUT_DEL == 1) && (line.contains("<PosSyntax>")) && (possyntaxout == 0)) {
							    	File dirR = new File(rootDir);
							    	String[] rootF = dirR.list();
							    	List<String> rootFiles = Arrays.asList(rootF);
							    	if (rootFiles.contains("possyntax.txt")) {
							    		FileReader psf = new FileReader(rootPath + "possyntax.txt");
							    		BufferedReader psb = new BufferedReader(psf);
							    		del1 = "";
							    		del2 = "";
							    		String psbl;
							    		psbl = psb.readLine();
							    		if (psbl != null)
							    			action = psbl;
							    		psbl = psb.readLine();
							    		if (psbl != null)
							    			del1 = psbl;
							    		psbl = psb.readLine();
							    		if (psbl != null)
							    			del2 = psbl;
							    		psb.close();
							    		possyntaxout = 1;
							    		if (action.equals("DO")) {
							    			mxlOut.write(line);
									        mxlOut.newLine();
									        if (!(del1.equals("")))
									           mxlOut.write("<DelimiterUsed>yes</DelimiterUsed>\n<Delimiter1>" + del1 + "</Delimiter1>\n");
									        else
									           mxlOut.write("<DelimiterUsed>no</DelimiterUsed>\n<Delimiter1/>\n");
									        if (!(del2.equals("")))
									      	   mxlOut.write("<Delimiter2Used>yes</Delimiter2Used>\n<Delimiter2>" + del2 + "</Delimiter2>\n");
									        else
								        	   mxlOut.write("<Delimiter2Used>no</Delimiter2Used>\n<Delimiter2/>\n");
							   	            while(!(line.contains("<RecordLengthUsed>"))) {
									       	   line = mxl.readLine();
									        }
									        line = line.substring(line.indexOf("<RecordLengthUsed>"));
							    		}
							    		
							    	}
							    	else {
							    		FileWriter psfw = new FileWriter(rootPath + "possyntax.txt");
							    		BufferedWriter psbw = new BufferedWriter(psfw);
								        //del1 = "";
								        //del2 = "";

								        /*int result = JOptionPane.showConfirmDialog(null, myPanel, 
								                 "Enter delimiter values for positional output syntax.", JOptionPane.OK_CANCEL_OPTION);*/
								        possyntaxout = 1;
								        //if (result == JOptionPane.OK_OPTION) {
								           psbw.write("DO");
								           psbw.newLine();
								        
								           psbw.write(delimiter_1);
								           psbw.newLine();
								           psbw.write(delimiter_2);
								           mxlOut.write(line);
								           mxlOut.newLine();
								           if (!(delimiter_1.equals("")))
								        	   mxlOut.write("<DelimiterUsed>yes</DelimiterUsed>\n<Delimiter1>" + delimiter_1 + "</Delimiter1>\n");
								           else
								        	   mxlOut.write("<DelimiterUsed>no</DelimiterUsed>\n<Delimiter1/>\n");
								           if (!(delimiter_2.equals("")))
								        	   mxlOut.write("<Delimiter2Used>yes</Delimiter2Used>\n<Delimiter2>" + delimiter_2 + "</Delimiter2>\n");
								           else
								        	   mxlOut.write("<Delimiter2Used>no</Delimiter2Used>\n<Delimiter2/>\n");
								           while(!(line.contains("<RecordLengthUsed>"))) {
								        	   line = mxl.readLine();
								           }
								           line = line.substring(line.indexOf("<RecordLengthUsed>"));
								        /*}
								        else {
								        	psbw.write("DON'T");
								        }*/
								        psbw.close();
							    	}
							    }
							 
                            if ((line.contains("<JavaEncoding>")) && (flagOUTPUT == 1)) {
                            	line = "<JavaEncoding>UTF8 (UTF8)</JavaEncoding>";
                            	logger.info("Setting java encoding for XML output to UTF8 (UTF8). \n\n");
                            	flagOUTPUT = 0;
                            }
                            if  ((line.contains("<UseCharEntityRef>")) && (flagOUTPUT == 1)) {
        				    	mxlOut.write("<JavaEncoding>UTF8 (UTF8)</JavaEncoding>");
        				    	mxlOut.newLine();
        				    	logger.info("Setting java encoding for XML output to UTF8 (UTF8). \n\n");
        				    	
        				    }
							lineLow = line.toLowerCase();
							if ((((lineLow.trim().indexOf("select ")) == 0) || ((lineLow.trim().indexOf("update document")) == 0)) && (!(lineLow.trim().startsWith("//"))))  {
								logger.info("Correcting extended rule: " + line + "\n\n");
								lineTN = line;
								if (lineLow.contains("tablename")) {
									flagTN = 1;
									lineTN = line.substring(lineLow.indexOf("tablename") + 9);
								}
								if ((flagTN == 1) && ((lineTN.indexOf("\"")) >= 0)) {
									clName = lineTN.substring(lineTN.indexOf("\"") + 1);
									if (clName.contains("\""))
										clName = clName.substring(0, clName.indexOf("\""));
									flagTN = 0;
									clRep.write("Codelist rule found and corrected in extended rule for codelist: " + clName + "\n");
									codelists.add(clName);
								}
								line = GentranNTSI.correctExtendedRule(line);
								while (!(line.contains(";"))) {
									mxlOut.write(line);
									mxlOut.newLine();
									line = mxl.readLine();
									lineLow = line.toLowerCase();
									lineTN = line;
									if (lineLow.contains("tablename")) {
										flagTN = 1;
										lineTN = line.substring(lineLow.indexOf("tablename") + 9);
									}
									if ((flagTN == 1) && ((lineTN.indexOf("\"")) >= 0)) {
										clName = lineTN.substring(lineTN.indexOf("\"") + 1);
										if (clName.contains("\""))
											clName = clName.substring(0, clName.indexOf("\""));
										flagTN = 0;
										clRep.write("Codelist rule found and corrected in extended rule for codelist: " + clName + "\n");
										codelists.add(clName);
									}
									line = GentranNTSI.correctExtendedRule(line);
									logger.info("Correcting extended rule: " + line + "\n\n");
								}
							}
							if (flagENV == 1) {
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
							if (flagISA == 1) {
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
							
                            if ((line.contains("<Composite>")) && (countISA == 15))
                            {
                                compositeFlag = 0;
                                ISAelementCount = 18;
                            }
                            if ((line.contains("<Field>")) && (countISA == 15) && (compositeFlag == 1)) {
                                mxlOut.write("<Composite>\n<ID>1036</ID>\n<Name>ELEMENT</Name>\n<Description></Description>\n<Active>1</Active>\n<ChildCount>2</ChildCount>\n<Note></Note>\n<Mandatory>no</Mandatory>\n<MinUsage>0</MinUsage>\n<MaxUsage>1</MaxUsage>\n<TreatAsRepeat>no</TreatAsRepeat>\n<Field>\n<ID>1035</ID>\n<Name>ELEMENT_1</Name>\n<Description></Description>\n<Active>1</Active>\n<ChildCount>0</ChildCount>\n<Note></Note>\n<Mandatory>no</Mandatory>\n<NotUsed>no</NotUsed>\n<FieldNumber>0</FieldNumber>\n<StoreGroup>65535</StoreGroup>\n<StoreField>65535</StoreField>\n<BusinessName></BusinessName>\n<StoreLimit>\n<MaxLen>256</MaxLen>\n<MinLen>0</MinLen>\n<Signed>no</Signed>\n<DataType>string</DataType>\n<ImpliedDecimalPos>0</ImpliedDecimalPos>\n<ImplicitDecimal>no</ImplicitDecimal>\n<AllowSignedDecimal>0</AllowSignedDecimal>\n<Format></Format>\n<BinaryOutput>0</BinaryOutput>\n<BinaryWidth>0</BinaryWidth>\n</StoreLimit>\n<Element>0</Element>\n<ElementOpt>0</ElementOpt>\n<SubElement>0</SubElement>\n<SubElementOpt>0</SubElementOpt>\n<MinUsage>0</MinUsage>\n<MaxUsage>1</MaxUsage>\n<Binary>0</Binary>\n<TreatAsRepeat>no</TreatAsRepeat>\n<AlwaysOutputDelimiter>no</AlwaysOutputDelimiter>\n</Field>\n<Field>\n<ID>1034</ID>\n<Name>ELEMENT_2</Name>\n<Description></Description>\n<Active>1</Active>\n<ChildCount>0</ChildCount>\n<Note></Note>\n<Mandatory>no</Mandatory>\n<NotUsed>no</NotUsed>\n<FieldNumber>0</FieldNumber>\n<StoreGroup>65535</StoreGroup>\n<StoreField>65535</StoreField>\n<BusinessName></BusinessName>\n<StoreLimit>\n<MaxLen>256</MaxLen>\n<MinLen>0</MinLen>\n<Signed>no</Signed>\n<DataType>string</DataType>\n<ImpliedDecimalPos>0</ImpliedDecimalPos>\n<ImplicitDecimal>no</ImplicitDecimal>\n<AllowSignedDecimal>0</AllowSignedDecimal>\n<Format></Format>\n<BinaryOutput>0</BinaryOutput>\n<BinaryWidth>0</BinaryWidth>\n</StoreLimit>\n<Element>0</Element>\n<ElementOpt>0</ElementOpt>\n<SubElement>0</SubElement>\n<SubElementOpt>0</SubElementOpt>\n<MinUsage>0</MinUsage>\n<MaxUsage>1</MaxUsage>\n<Binary>0</Binary>\n<TreatAsRepeat>no</TreatAsRepeat>\n<AlwaysOutputDelimiter>no</AlwaysOutputDelimiter>\n</Field>\n</Composite>\n");
                                logger.info("Adding composite element to ISA.\n\n");
                            }

							
							mxlOut.write(line);
							mxlOut.newLine();
							if (line.equals("</StoreLimit>")) {
								line = mxl.readLine();
								if (line.indexOf("<Link>") >= 0) {
									mxlOut.write(line);
									mxlOut.newLine();
									line = mxl.readLine();
								}
								if ((line.indexOf("<ExplicitRule>")) >= 0) {
									lineLow = line.toLowerCase();
									if ((((lineLow.trim().indexOf("select ")) == 0) || ((lineLow.trim().indexOf("update document")) == 0)) && (!(lineLow.trim().startsWith("//"))))  {
										logger.info("Correcting extended rule: " + line + "\n\n");
										lineTN = line;
										if (lineLow.contains("tablename")) {
											flagTN = 1;
											lineTN = line.substring(lineLow.indexOf("tablename") + 9);
										}
										if ((flagTN == 1) && ((lineTN.indexOf("\"")) >= 0)) {
											clName = lineTN.substring(lineTN.indexOf("\"") + 1);
											if (clName.contains("\""))
												clName = clName.substring(0, clName.indexOf("\""));
											flagTN = 0;
											clRep.write("Codelist rule found and corrected in extended rule for codelist: " + clName + "\n");
											codelists.add(clName);
										}
										line = GentranNTSI.correctExtendedRule(line);
										while (!(line.contains(";"))) {
											mxlOut.write(line);
											mxlOut.newLine();
											line = mxl.readLine();
											lineLow = line.toLowerCase();
											lineTN = line;
											if (lineLow.contains("tablename")) {
												flagTN = 1;
												lineTN = line.substring(lineLow.indexOf("tablename") + 9);
											}
											if ((flagTN == 1) && ((lineTN.indexOf("\"")) >= 0)) {
												clName = lineTN.substring(lineTN.indexOf("\"") + 1);
												if (clName.contains("\""))
													clName = clName.substring(0, clName.indexOf("\""));
												flagTN = 0;
												clRep.write("Codelist rule found and corrected in extended rule for codelist: " + clName + "\n");
												codelists.add(clName);
											}
											line = GentranNTSI.correctExtendedRule(line);
											logger.info("Correcting extended rule: " + line + "\n\n");
										}
									}
									if (!(line.equals("<ExplicitRule>"))) { 
										pos = line.indexOf("</ExplicitRule>");
										if (pos >= 0) {
											if (pos == 14) {
												String linePart1 = line.substring(0, pos);
												String linePart2 = line.substring(pos);
												mxlOut.write(linePart1);
												mxlOut.newLine();
												mxlOut.write(query);
												logger.info("Writing standard rule: \nField: " + field + "\nQuery: " + query + "\n\n");
												if (updateS.length() > 0) {
													mxlOut.newLine();
													mxlOut.write(updateS);
													updateS = "";
												}
												mxlOut.write(linePart2);
												mxlOut.newLine();
												break;
											}
											else {
												String linePart1 = line.substring(0, 14);
												String linePart2 = line.substring(14, pos);
												String linePart3 = line.substring(pos);
												mxlOut.write(linePart1);
												mxlOut.newLine();
												if ((linePart2.startsWith("//")) ||
													(linePart2.startsWith("integer")) ||
													(linePart2.startsWith("object")) ||
													(linePart2.startsWith("string")) ||
													(linePart2.startsWith("datetime")) ||
													(linePart2.startsWith("real"))) {
														mxlOut.write(linePart2);
														mxlOut.newLine();
														mxlOut.write(query);
														mxlOut.newLine();
														logger.info("Writing standard rule: \nField: " + field + "\nQuery: " + query + "\n\n");
														if (updateS.length() > 0) {
															mxlOut.write(updateS);
															mxlOut.newLine();
															updateS = "";
														}
														mxlOut.write(linePart3);
														mxlOut.newLine();
														break;
												}
												else {
													mxlOut.write(query);
													mxlOut.newLine();
													logger.info("Writing standard rule: \nField: " + field + "\nQuery: " + query + "\n\n");
													if (updateS.length() > 0) {
														mxlOut.write(updateS);
														mxlOut.newLine();
														updateS = "";
													}
													mxlOut.write(linePart2);
													mxlOut.newLine();
													mxlOut.write(linePart3);
													mxlOut.newLine();
													break;
												}
											}
										}
										else {
											String linePart1 = line.substring(0, 14);
											String linePart2 = line.substring(14);
											mxlOut.write(linePart1);
											mxlOut.newLine();
											if ((linePart2.startsWith("//")) ||
												(linePart2.startsWith("integer")) ||
												(linePart2.startsWith("object")) ||
												(linePart2.startsWith("string")) ||
												(linePart2.startsWith("datetime")) ||
												(linePart2.startsWith("real"))) {
													mxlOut.write(linePart2);
													mxlOut.newLine();
											}
											else {
												mxlOut.write(query);
												mxlOut.newLine();
												logger.info("Writing standard rule: \nField: " + field + "\nQuery: " + query + "\n\n");
												if (updateS.length() > 0) {
													mxlOut.write(updateS);
													mxlOut.newLine();
													updateS = "";
												}
												mxlOut.write(linePart2);
												mxlOut.newLine();
												break;
											}	
										}
									}
									else {
										mxlOut.write(line);
										mxlOut.newLine();
									}
									while((line = mxl.readLine()) != null) {
										String lineLower = line.toLowerCase();
										if ((lineLower.startsWith("//")) ||
											(lineLower.startsWith("integer")) ||
											(lineLower.startsWith("object")) ||
											(lineLower.startsWith("string")) ||
											(lineLower.startsWith("datetime")) ||
											(lineLower.startsWith("real"))) {
												mxlOut.write(line);
												mxlOut.newLine();
										}
										else {
											mxlOut.write(query);
											logger.info("Writing standard rule: \nField: " + field + "\nQuery: " + query + "\n\n");
											mxlOut.newLine();
											lineLow = line.toLowerCase();
											if ((((lineLow.trim().indexOf("select ")) == 0) || ((lineLow.trim().indexOf("update document")) == 0)) && (!(lineLow.trim().startsWith("//")))) {
												logger.info("Correcting extended rule: " + line + "\n\n");
												lineTN = line;
												if (lineLow.contains("tablename")) {
													flagTN = 1;
													lineTN = line.substring(lineLow.indexOf("tablename") + 9);
												}
												if ((flagTN == 1) && ((lineTN.indexOf("\"")) >= 0)) {
													clName = lineTN.substring(lineTN.indexOf("\"") + 1);
													if (clName.contains("\""))
														clName = clName.substring(0, clName.indexOf("\""));
													flagTN = 0;
													clRep.write("Codelist rule found and corrected in extended rule for codelist: " + clName + "\n");
													codelists.add(clName);
												}
												line = GentranNTSI.correctExtendedRule(line);
												while (!(line.contains(";"))) {
													mxlOut.write(line);
													mxlOut.newLine();
													line = mxl.readLine();
													lineLow = line.toLowerCase();
													lineTN = line;
													if (lineLow.contains("tablename")) {
														flagTN = 1;
														lineTN = line.substring(lineLow.indexOf("tablename") + 9);
													}
													if ((flagTN == 1) && ((lineTN.indexOf("\"")) >= 0)) {
														clName = lineTN.substring(lineTN.indexOf("\"") + 1);
														if (clName.contains("\""))
															clName = clName.substring(0, clName.indexOf("\""));
														flagTN = 0;
														clRep.write("Codelist rule found and corrected in extended rule for codelist: " + clName + "\n");
														
														codelists.add(clName);
													}
													line = GentranNTSI.correctExtendedRule(line);
													logger.info("Correcting extended rule: " + line + "\n\n");
												}
											}
											mxlOut.write(line);											
											mxlOut.newLine();
											break;
										}
									}
									break;
								}
								else {
									mxlOut.write("<ExplicitRule>");
									mxlOut.newLine();
									mxlOut.write(query);
									logger.info("Writing standard rule: \nField: " + field + "\nQuery: " + query + "\n\n");
									mxlOut.write("</ExplicitRule>");
									mxlOut.newLine();
									mxlOut.write(line);
									mxlOut.newLine();
									break;
								}
							}
						}
						if (fieldIterator.hasNext()) {
							segment = segmentIterator.next();
							field = fieldIterator.next();
							query = queryIterator.next();
						}	
					}
				
					if ((flagID == 1) && (segmentMXL.equals(idSEG)) && (line.contains("<Name>"))) {
						cntELE += 1;
						if ((flagUPDATE == 0) && (cntELE == idELE)) {
							idELEName = line.substring(6);
							if (idELEName.contains("</Name>"))
								idELEName = idELEName.substring(0, (idELEName.indexOf("</Name>")));
							
							while ((flagUPDATE == 0) && ((line = mxl.readLine()) != null)) {
								lineLow = line.toLowerCase();
								if ((((lineLow.trim().indexOf("select ")) == 0) || ((lineLow.trim().indexOf("update document")) == 0)) && (!(lineLow.trim().startsWith("//"))))  {
									logger.info("Correcting extended rule: " + line + "\n\n");
									lineTN = line;
									if (lineLow.contains("tablename")) {
										flagTN = 1;
										lineTN = line.substring(lineLow.indexOf("tablename") + 9);
									}
									if ((flagTN == 1) && ((lineTN.indexOf("\"")) >= 0)) {
										clName = lineTN.substring(lineTN.indexOf("\"") + 1);
										if (clName.contains("\""))
											clName = clName.substring(0, clName.indexOf("\""));
										flagTN = 0;
										clRep.write("Codelist rule found and corrected in extended rule for codelist: " + clName + "\n");
										codelists.add(clName);
									}
									line = GentranNTSI.correctExtendedRule(line);
									while (!(line.contains(";"))) {
										mxlOut.write(line);
										mxlOut.newLine();
										line = mxl.readLine();
										lineLow = line.toLowerCase();
										lineTN = line;
										if (lineLow.contains("tablename")) {
											flagTN = 1;
											lineTN = line.substring(lineLow.indexOf("tablename") + 9);
										}
										if ((flagTN == 1) && ((lineTN.indexOf("\"")) >= 0)) {
											clName = lineTN.substring(lineTN.indexOf("\"") + 1);
											if (clName.contains("\""))
												clName = clName.substring(0, clName.indexOf("\""));
											flagTN = 0;
											clRep.write("Codelist rule found and corrected in extended rule for codelist: " + clName + "\n");
											codelists.add(clName);
										}
										line = GentranNTSI.correctExtendedRule(line);
										logger.info("Correcting extended rule: " + line + "\n\n");
									}
								}
								
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
								
								mxlOut.write(line);
								mxlOut.newLine();
							}
							
						}
					}
					
					
					/*if ((flagID == 1) && (segmentMXL.equals(idSEG)) && (flagUPDATE == 0)) {
						while (((line = mxl.readLine()) != null) && (flagUPDATE == 0)) {
							if (line.contains("<Name>")) {
								cntELE += 1;
								idELEName = line.substring(6);
								if (idELEName.contains("</Name>"))
									idELEName = idELEName.substring(0, (idELEName.indexOf("</Name>")));
							}
							if (line.contains("<ExplicitRule>")) {
								line.replaceAll("<ExplicitRule>", ("<ExplicitRule>\nUPDATE processdata set xpathresult = #" + idELEName + " where xpath = \"TranslationOutput/BusinessReference\";\n"));
								flagUPDATE = 1;
							}
						}
					}*/
				}
				//Close BufferedReader and BufferedWriter for MXL.
				int no_of_cl = codelists.size();
				int cnt = 0;
				if (no_of_cl > 0) {
					clRep.write("\n****************************************************************************\n");
					clRep.write("\n" + no_of_cl + " codelists found. Codelist names: \n\n");
					Iterator<String> clI = codelists.iterator();
					while(clI.hasNext()) {
						cnt++;
						clRep.write(cnt + ")\t" + clI.next() + "\n");
					}
					clRep.write("\n****************************************************************************\n");
				}
				else {
					clRep.write("\n****************************************************************************\n");
					clRep.write("\t\t\tNo codelists found.");
					clRep.write("\n****************************************************************************\n");
				}
				
				fh.close();
				bufferedReader.close();
				mxl.close();
				mxlOut.close();
				clRep.close();
				
				
			}
			catch(FileNotFoundException ex) {
				ex.printStackTrace();
			}
			catch(IOException ex) {
				ex.printStackTrace();
				
			}
			catch(JDOMException e){
				e.printStackTrace();
			}
			
        }
        catch(FileNotFoundException ex) {
        	ex.printStackTrace();
                          
        }
        
    }
}
