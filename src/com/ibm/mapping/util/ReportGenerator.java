package com.ibm.mapping.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.ibm.mapping.servlet.Constants;

/**
 * 
 * 
 * @author
 * 
 */
public final class ReportGenerator implements Constants {

	//log variable 
	static Logger log = Logger.getLogger(ReportGenerator.class.getName());
	
	public static void exceuteMap(String inputDirectoryName,String outputDirectoryName, String utilxslFile) {

		File inputDir = new File(inputDirectoryName);
		//System.out.println("TEST");
		//System.out.println("RG inputDirectoryName="+inputDirectoryName);
		//System.out.println("exist="+inputDir.exists());
		File utilxslFileCheck = new File(utilxslFile);

		// Check if output directory exists.
		File outputDir = new File(outputDirectoryName);

		if (!outputDir.exists()) {
			// Create a output directory.
			outputDir.mkdirs();
		}
		FileUtils.cleanDirectory(outputDir);

		// Check if util xsl file exists.
		if (utilxslFileCheck.exists()) {

			// Check if input directory exists.
			if (inputDir.exists()) {

				File[] listOfFiles = inputDir.listFiles();

				for (int i = 0; i < listOfFiles.length; i++) {

					if (listOfFiles[i].isFile() && listOfFiles[i].getName().toLowerCase().endsWith(".mxl")) {
						try {

							String inputFileName = listOfFiles[i].getName();

							String outputFilename = inputFileName.substring(0,
									inputFileName.lastIndexOf("."))
									+ "_output.xml";

							// generate the output							
					         
							TransformerFactory.newInstance().newTransformer(
											new StreamSource(new File(
													utilxslFile)))
									.transform(
											new StreamSource(new File(
													listOfFiles[i].getPath())),
											new StreamResult(new File(
													outputDirectoryName
															+ outputFilename)));

							//System.out.println(outputFilename
							//		+ " file is generated for " + inputFileName
							//		+ " in the folder " + outputDirectoryName);

						} catch (TransformerException e) {
							{
								log.error("Exception occured : "
										+ e.getMessage());
							}

						}
					}
				}
			} else {
				log.error("The input directory '" + inputDirectoryName
						+ "' does not exists.");
			}

		} else {

			log.error("The utilxslFile does not exists in the  path "
					+ utilxslFile);
		}
	}
	
	public static void updateMap(String inputDirectoryName,String outputDirectoryName, String fieldMinsToZero, String removeAllConditionalRules, String setAllStringsToFreeFormat, String setAllTagsToLen7, String setCharacterEncodingsToUTF8) {

		System.out.println("fieldMinsToZero=>"+fieldMinsToZero);
		System.out.println("removeAllConditionalRules=>"+removeAllConditionalRules);
		System.out.println("setAllStringsToFreeFormat=>"+setAllStringsToFreeFormat);
		System.out.println("setAllTagsToLen7=>"+setAllTagsToLen7);
		System.out.println("setCharacterEncodingsToUTF8=>"+setCharacterEncodingsToUTF8);
		File inputDir = new File(inputDirectoryName);
		//System.out.println("TEST");
		//System.out.println("RG inputDirectoryName="+inputDirectoryName);
		//System.out.println("exist="+inputDir.exists());
		
		File fieldMinsToZeroFile = new File(FieldMinsToZero_XSL);
		File removeAllConditionalRulesFile = new File(removeAllConditionalRules_XSL);
		File setAllStringsToFreeFormatFile = new File(setAllStringsToFreeFormat_XSL);
		File setAllTagsToLen7File = new File(setAllTagsToLen7_XSL);
		File setCharacterEncodingsToUTF8File = new File(setCharacterEncodingsToUTF8_XSL);

		// Check if output directory exists.
		File outputDir = new File(outputDirectoryName);

		if (!outputDir.exists()) {
			// Create a output directory.
			outputDir.mkdirs();
		}
		FileUtils.cleanDirectory(outputDir);

		// Check if util xsl file exists.
		

			// Check if input directory exists.
			if (inputDir.exists()) {

				File[] listOfFiles = inputDir.listFiles();

				for (int i = 0; i < listOfFiles.length; i++) {

					if (listOfFiles[i].isFile() && listOfFiles[i].getName().toLowerCase().endsWith(".mxl")) {
						
						try {
							System.out.println("Map Function Working for free format");
							String inputFileName = listOfFiles[i].getName();

							//File updatefile = new File(inputDirectoryName + inputFileName.substring(0, inputFileName.length()-4)+"_UPDATE.mxl");
							String outputFilename = inputFileName.substring(0, inputFileName.length()-4)+"_UPDATE.mxl";//.substring(0,inputFileName.lastIndexOf("."))+ "_output.xml";

							// generate the output							
							if (fieldMinsToZeroFile.exists() && fieldMinsToZero.equalsIgnoreCase("true")) {
							TransformerFactory.newInstance().newTransformer(new StreamSource(new File(fieldMinsToZeroFile.getAbsolutePath()))).transform(new StreamSource(new File(listOfFiles[i].getPath())),new StreamResult(new File(inputDirectoryName+ outputFilename)));
							}
							if (setAllStringsToFreeFormatFile.exists() && setAllStringsToFreeFormat.equalsIgnoreCase("true")) {
							TransformerFactory.newInstance().newTransformer(new StreamSource(new File(setAllStringsToFreeFormatFile.getAbsolutePath()))).transform(new StreamSource(new File(inputDirectoryName+ outputFilename)),new StreamResult(new File(inputDirectoryName+ inputFileName)));
							}
							
							//Copy file from input dir to output dir
							try {
								copyFileUsingFileChannels(inputDirectoryName+ inputFileName,outputDirectoryName+ inputFileName);
							} catch (IOException e) {
								e.printStackTrace();
							}
							

						} catch (TransformerException e) {
                                 log.error("Exception occured : " + e.getMessage());
						}
					  }
					}
				
			} else {
				log.error("The input directory '" + inputDirectoryName + "' does not exists.");
			}

		/*} else {

			log.error("The utilxslFile does not exists in the  path " + setAllStringsToFreeFormatFile.getAbsolutePath());
		}*/
	}

	public static void generateFiles(String inputDirectory,
			String ouputDirectory) {

		File inputDir = new File(inputDirectory);

		// Check if output directory exists.
		File outputDir = new File(ouputDirectory);

		if (!outputDir.exists()) {
			// Create a output directory.
			outputDir.mkdirs();
		}
		FileUtils.cleanDirectory(outputDir);

		// Check if input directory exists.
		if (inputDir.exists()) {

			File[] listOfFiles = inputDir.listFiles();

			for (int i = 0; i < listOfFiles.length; i++) {

				if (listOfFiles[i].isFile()) {
					try {

						String inputFileName = listOfFiles[i].getName();

						// Business logic

						String limiter = null;
						String sCurrentLine;
						StringBuilder builder = new StringBuilder();
						/*try (BufferedReader br = new BufferedReader(
								new FileReader(inputDirectory + "\\"
										+ inputFileName)))*/ 
						try{
							BufferedReader br = new BufferedReader(
													new FileReader(inputDirectory + "\\"
															+ inputFileName));
							while ((sCurrentLine = br.readLine()) != null) {

								builder.append(sCurrentLine);

							}
                            br.close();
							builder.replace(builder.indexOf("<"),
									builder.indexOf(">") + 1, "");
							builder.replace(builder.lastIndexOf("<"),
									builder.lastIndexOf(">") + 1, "");
							//System.out.println(builder);
							String limiterExtracter = builder.toString();
							limiter = limiterExtracter.substring(0,
									limiterExtracter.indexOf(">") + 1).replace(
									"<", "</");
							// if child tag contains namespace
							if(limiter.toLowerCase().contains("xmlns")) {
							int namespace_pos = limiter.toLowerCase().indexOf("xmlns");
							limiter = limiter.substring(0, namespace_pos).trim()+">";
							//System.out.println("limiter=>"+limiter);
							}

							List<String> list = new ArrayList<String>();
							//System.out.println("list cnt=>"+list.size());
							//System.out.println("Return Value :");
							for (String retval : limiterExtracter
									.split(limiter)) {
								list.add(retval + limiter);
							}

							for (int j = 0; j < list.size(); j++) {
								try {

									String fileName = limiter.replace("</", "")
											.replace(">", "").trim();

									File file = new File(ouputDirectory
											+ fileName + (j + 1) + ".xml");

									if (!file.exists()) {
										file.createNewFile();
									} else {
										file = new File(ouputDirectory
												+ fileName + "_" + i + "_"
												+ (j + 1) + ".xml");
										file.createNewFile();
									}

									FileWriter fw = new FileWriter(
											file.getAbsoluteFile());
									String header = new String(
											"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
									fw.write(header);
									fw.write(list.get(j));
									fw.close();

								} catch (IOException e) {
									log.error(e.getMessage());
								}
							}

						} catch (IOException e) {
							log.error(e.getMessage());
						}

					} catch (Exception e) {
						{
							log.error("Exception occured : "
									+ e.getMessage());
						}

					}
				}
			}
		} else {
			log.error("The input directory '" + inputDirectory
					+ "' does not exists.");
		}

	}
	/**
	 * @param inputDirectory
	 * @param ouputDirectory
	 * This function is responsible for down grade map version 8.0 to Version 5.4
	 */
	public static void downgradeMap(String inputDirectory,
			String ouputDirectory) {
		File inputDir = new File(inputDirectory);

		// Check if output directory exists.
		File outputDir = new File(ouputDirectory);

		if (!outputDir.exists()) {
			// Create a output directory.
			outputDir.mkdirs();
		}
		FileUtils.cleanDirectory(outputDir);
		// Check if input directory exists.
		if (inputDir.exists()) {

			File[] listOfFiles = inputDir.listFiles();

			for (int i = 0; i < listOfFiles.length; i++) {

				if (listOfFiles[i].isFile() && listOfFiles[i].getName().toLowerCase().endsWith(".mxl")) {
					String inputFileName = listOfFiles[i].getName();
					//System.out.println("inputFileName=>"+inputFileName);
					try {
						DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
						DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
						Document document = documentBuilder.parse(inputDirectory + "\\"+ inputFileName);
						
						// Get tag name
						//use item(0) to get the first node with tage name 
						Node SerializationVersion = document.getElementsByTagName("SerializationVersion").item(0);
						
						if(!SerializationVersion.getTextContent().trim().equals("823")) {
						SerializationVersion.setTextContent("823");								
			            deleteElement(document);	
						}
						else
						log.info("Map is already in 5.x version");
						
						// write the DOM object to the file
						TransformerFactory transformerFactory = TransformerFactory.newInstance();

						Transformer transformer = transformerFactory.newTransformer();
						DOMSource domSource = new DOMSource(document);

						StreamResult streamResult = new StreamResult(new File(ouputDirectory+ inputFileName));
						transformer.transform(domSource, streamResult);

						//System.out.println("The XML File was updated successfully!!!!!!.....NJY URSELF :D");
						//System.out.println("BYE...BYE");

					} catch (ParserConfigurationException pce) {
						pce.printStackTrace();
						log.error(pce.getMessage());
					} catch (TransformerException tfe) {
						tfe.printStackTrace();
						log.error(tfe.getMessage());
					} catch (IOException ioe) {
						ioe.printStackTrace();
						log.error(ioe.getMessage());
					} catch (SAXException sae) {
						sae.printStackTrace();
						log.error(sae.getMessage());
					}
				}
			}
		}else {
			/*System.out.println("The input directory '" + inputDirectory
					+ "' does not exists.");*/
			log.error("The input directory '" + inputDirectory
					+ "' does not exists.");
		}
	}
	/**
	 * @param doc
	 * This method is responsible for deleting elements 
	 */
	private static void deleteElement(Document doc) {
        NodeList Flags = doc.getElementsByTagName("Flags");
        Element flg = null;
        
        /*for(int i=0; i<Flags.getLength();i++){
            flg = (Element) Flags.item(i);
            
            Node ErrorForNotUsed = flg.getElementsByTagName("ErrorForNotUsed").item(0);
            flg.removeChild(ErrorForNotUsed);
            
            Node SuspendGroupProcessing = flg.getElementsByTagName("SuspendGroupProcessing").item(0);
            flg.removeChild(SuspendGroupProcessing);              
            
            Node SWIFTValidation = flg.getElementsByTagName("SWIFTValidation").item(0);
            flg.removeChild(SWIFTValidation);  
            
            Node KeepTrailingZeroes = flg.getElementsByTagName("KeepTrailingZeroes").item(0);
            flg.removeChild(KeepTrailingZeroes);  
             
            }*/
        
        Element element = (Element) doc.getElementsByTagName("ErrorForNotUsed").item(0);
        element.getParentNode().removeChild(element);
        
        Element element1 = (Element) doc.getElementsByTagName("SuspendGroupProcessing").item(0);
        element1.getParentNode().removeChild(element1);
        
        Element element2 = (Element) doc.getElementsByTagName("SWIFTValidation").item(0);
        element2.getParentNode().removeChild(element2);
        
        Element element3 = (Element) doc.getElementsByTagName("KeepTrailingZeroes").item(0);
        element3.getParentNode().removeChild(element3);
        	        
        NodeList Group = doc.getElementsByTagName("Group");
        Element grp = null;
       
        for(int i=0; i<Group.getLength();i++){
            grp = (Element) Group.item(i);               	            
          
            Node GroupChoiceType = grp.getElementsByTagName("GroupChoiceType").item(0);
            grp.removeChild(GroupChoiceType);                    
        }
        
        
        NodeList Field = doc.getElementsByTagName("Field");
        Element fld = null;
       
        for(int i=0; i<Field.getLength();i++){
        	fld = (Element) Field.item(i);               	            
          
            Node NotUsed = fld.getElementsByTagName("NotUsed").item(0);
            fld.removeChild(NotUsed);                    
        }   
         
    }
	
	public static void generateDDF(String inputDirectory,String outputDirectory)
	{
		File inputDir = new File(inputDirectory);
		DDFGenerator ddfGen;

		// Check if output directory exists.
		File outputDir = new File(outputDirectory);

		if (!outputDir.exists()) {
			// Create a output directory.
			outputDir.mkdirs();
		}
		FileUtils.cleanDirectory(outputDir);
		// Check if input directory exists.
		if (inputDir.exists()) {
			File[] listOfFiles = inputDir.listFiles();
			for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {

					String inputFileName = listOfFiles[i].getName();
					if(inputFileName.contains(".xls"))
					{
						ddfGen = new DDFGenerator();
						ddfGen.genrateDdf(inputDirectory + "\\"+ inputFileName, outputDirectory);
					}
			}
		 }
		}

	}
	
	///Added for MRS Generator - Manish
	public static void generateMRS(String inputDirectoryName,String outputDirectoryName, String utilxslFile_Input,String utilxslFile_Output, String utilxslFile_Meta, String fielddef, String inactvfld, String usageType) {
		

		File inputDir = new File(inputDirectoryName);
		File utilxslFile1Check = new File(utilxslFile_Input);
		File utilxslFile2Check = new File(utilxslFile_Output);
		File utilxslFile3Check = new File(utilxslFile_Meta);
		
		DateFormat dateFormat = new SimpleDateFormat("MMddyyyy");
		Date date = new Date();
		String todDate = dateFormat.format(date);
		
		// Check if output directory exists.
		File outputDir = new File(outputDirectoryName);

		if (!outputDir.exists()) {
			// Create a output directory.
			outputDir.mkdirs();
		}
		FileUtils.cleanDirectory(outputDir);

		// Check if util xsl file exists.
		if (utilxslFile1Check.exists() && utilxslFile2Check.exists() && utilxslFile3Check.exists()) {
			
			// Check if input directory exists.
			if (inputDir.exists()) {

					File[] listOfFiles = inputDir.listFiles();
					//System.out.println("listofFile : "+ inputDir.listFiles());
					for (int i = 0; i < listOfFiles.length; i++) {
						//System.out.println("Inside loop for list of files");
						if (listOfFiles[i].isFile() && listOfFiles[i].getName().toLowerCase().endsWith(".mxl")) {
							try {
								//System.out.println("***** In generateMRS() ******");
								String AgencyID = null,VersionID = null;
								
								DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
								dbFactory.setNamespaceAware(false);
								try {
									DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
									InputSource is = new InputSource(new FileReader(listOfFiles[i].getAbsolutePath()));
									Document doc = null;
									doc = documentBuilder.parse(is);doc.normalize();
									NodeList rowList = doc.getElementsByTagName("EDIAssociations_OUT");
									
									//if(rowList.getLength() > 0) {
										Node row = rowList.item(0);
										NodeList colList = row.getChildNodes();
										for(int j=0;j<colList.getLength();j++) {
											Node col = colList.item(j);
											if(col.getNodeName().equalsIgnoreCase("AgencyID"))
											AgencyID = col.getTextContent();
											if(col.getNodeName().equalsIgnoreCase("VersionID"))
											VersionID = col.getTextContent();
										}
									//}
								    
							} catch (ParserConfigurationException e) {
								log.error(e.getMessage());
							} catch (FileNotFoundException e) {
								log.error(e.getMessage());
							} catch (SAXException e) {
								log.error(e.getMessage());
							} catch (IOException e) {
								log.error(e.getMessage());
							}
							catch (NullPointerException e) {
								log.error(e.getMessage());
							}
	
								String inputFileName = listOfFiles[i].getName();
								String mapName = listOfFiles[i].getName().substring(0, listOfFiles[i].getName().lastIndexOf("."));
								String mrsName = "MRS_" + inputFileName.substring(0,inputFileName.lastIndexOf(".")) + "_" + todDate /*+ ".xlsx"*/;
								
								File mrsDir = new File(outputDirectoryName+File.separator+mrsName);
								
								if(!mrsDir.exists())
								mrsDir.mkdir();
							
								//System.out.println("mrsDir=>"+mrsDir.getAbsolutePath());
								
								//System.out.println("Input file name : " + inputFileName);
								
								String outputFilename = inputFileName.substring(0,
										inputFileName.lastIndexOf("."))
										+ "_output.xml";
								
								String file= inputDir+File.separator +inputFileName;
								TransformerFactory objTransFactory = TransformerFactory.newInstance();
								
								//Generate Input.xml using Input.xsl
								Transformer objTransformer = objTransFactory.newTransformer(new StreamSource(new File(utilxslFile_Input)));
								objTransformer.setParameter("withInactiveFields", fielddef);
								objTransformer.setParameter("withoutFieldDefs", inactvfld);
								objTransformer.transform(new StreamSource(file),
								new StreamResult(mrsDir.getAbsolutePath()+File.separator+"input.xml"));
								
								//Generate Output.xml using Output.xsl
								objTransformer = objTransFactory.newTransformer(new StreamSource(new File(utilxslFile_Output)));
								objTransformer.setParameter("withInactiveFields", fielddef);
								objTransformer.setParameter("withoutFieldDefs", inactvfld);
								file= inputDir+File.separator +inputFileName;
								objTransformer.transform(new StreamSource(file),
								new StreamResult(mrsDir.getAbsolutePath()+File.separator+"output.xml"));
								
								//This MRS Update is only for outbound edi map
								if(AgencyID!=null && VersionID!=null) {
								if(AgencyID.equalsIgnoreCase("a")||AgencyID.equalsIgnoreCase("e")||AgencyID.equalsIgnoreCase("o")||AgencyID.equalsIgnoreCase("u")||AgencyID.equalsIgnoreCase("x"))
								validateSheet.updateXLSX(mrsDir.getAbsolutePath()+File.separator+"output.xml",AgencyID,VersionID);
								}
								
								//Generate Output.xml using Output.xsl
								objTransformer = objTransFactory.newTransformer(new StreamSource(new File(utilxslFile_Meta)));
								objTransformer.setParameter("mapFileName", inputFileName);
								file= inputDir+File.separator +inputFileName;
								objTransformer.transform(new StreamSource(file),
								new StreamResult(mrsDir.getAbsolutePath()+File.separator+"meta.xml"));
												
								//Copy other xml files to generate xls 
								copyFileUsingFileChannels(Constants.MRSGENERATOR_STYLESXML, mrsDir.getAbsolutePath()+File.separator+"styles.xml");
								copyFileUsingFileChannels(Constants.MRSGENERATOR_WRKBOOKXML , mrsDir.getAbsolutePath()+File.separator+"workbook.xml");
								copyFileUsingFileChannels(Constants.MRSGENERATOR_CONTENTXML , mrsDir.getAbsolutePath()+File.separator+"[Content_Types].xml");
																
								File srcFolder = new File(Constants.MRSGENERATOR_RELSFOLDER);
						    	File destFolder = new File(mrsDir.getAbsolutePath()+File.separator+"_rels");
						    	
						    	//make sure source exists
						    	if(!srcFolder.exists()){
						 
						            //System.out.println("_rels Directory does not exist.");
						           //just exit
						           //System.exit(0);
						    		return;
						 
						        }else{
						   
						           try{
						        	copyFolder(srcFolder,destFolder);
						           }catch(IOException e){
						        	   log.error(e.getMessage());
						        	//error, just exit
						             //System.exit(0);
						        	 return;
						           }
						        }
						    	
						    	//System.out.println("mrsName=>"+mrsName);
						    	ZipDirectory.zipIn(mrsDir.getAbsolutePath());
						    							    	
						    	File mrsZip = new File(outputDirectoryName+File.separator+mrsName+".zip");
						    	File mrsFile = new File(outputDirectoryName+File.separator+mrsName+".xlsx");
						    	//System.out.println("mrsFile=>"+mrsFile.getAbsolutePath());
						    	
						    	if(mrsZip.renameTo(mrsFile))
						    	log.error("Rename succesful");
						    	else
						    	log.error("Rename failed");
								
						    	File outputXLS = new File(mrsDir.getAbsolutePath()+File.separator+"output.xml");
						    	//System.out.println("usageType=>"+usageType);
						    	
						    	/*if(outputXLS.exists())
						    	validateSheet.updateXLSX(outputDirectoryName,outputXLS.getAbsolutePath(),mapName);*/
						    	
								if(usageType.equals("development") && outputXLS.exists()) {
									//System.out.println("***** In generateMRS() - DEV ******");
								 if(!validateSheet.checkXLSX(outputDirectoryName,outputXLS.getAbsolutePath(),mapName)) {
									 //System.out.println("Add notes not present");
									 if(mrsFile.exists())
									 mrsFile.delete();
								  }
							    }
								
								// Code for deletion of MRS Directory
								FileUtils.cleanDirectory(mrsDir);
								
								if(mrsDir.exists()) {
								
								//System.out.println("Deleting MRS Dir");
								File[] mrsFiles = mrsDir.listFiles();
								//System.out.println("mrsFiles.length=>"+mrsFiles.length);
								for (int j = 0; j < mrsFiles.length; j++) {
									if(mrsFiles[j].isDirectory())
									mrsFiles[j].delete();
									
								}
								mrsDir.delete();
								//System.out.println("Deleted MRS Dir");	
								
								}
				
							//	System.out.println(" file is generated for " + inputFileName
								//		+ " in the folder " + outputDirectoryName);

							} catch (TransformerException e) 
								{
								log.error("Exception occured : "
										+ e.getMessage());
								} catch (Exception e){
									log.error("Exception "+ e);
								}

						}
					
				}
			} else {
				log.error("The input directory '" + inputDirectoryName
						+ "' does not exists.");
			}

		} else {

			log.error("One of the xls file required to transform does not exists in the  path ");
		}
		//System.out.println("********* End of Generate MRS /n**********/n");
		
	}  //end Code for MRS Generator
	
	// Code to generate report for error 20001 
	
	public static boolean generate_Error20001Report(String inputDirectoryName, String outputDirectoryName) throws ParserConfigurationException, SAXException, IOException,  TransformerFactoryConfigurationError, TransformerException {
	      
		boolean isCorrectData = false;
		ArrayList<String> before_map_list= new ArrayList<String>();
		ArrayList<String> after_map_list= new ArrayList<String>();
		
		File inputDir = new File(inputDirectoryName);

		// Check if output directory exists.
		File outputDir = new File(outputDirectoryName);

		if (!outputDir.exists()) {
			// Create a output directory.
			outputDir.mkdirs();
		}
		FileUtils.cleanDirectory(outputDir);

		// Check if input directory exists.
		if (inputDir.exists()) {

			File[] listOfFiles = inputDir.listFiles();
			File controlFile = null,testFile = null;
			
			for (int i = 0; i < listOfFiles.length; i++) {
				
				if (listOfFiles[i].isFile() && listOfFiles[i].getName().toLowerCase().endsWith(".mxl")) {
				 				 				 
				 if(listOfFiles[i].getName().toLowerCase().contains("before"))
				 before_map_list.add(listOfFiles[i].getAbsolutePath());
		         
				 
				 if(listOfFiles[i].getName().toLowerCase().contains("after"))
				 after_map_list.add(listOfFiles[i].getAbsolutePath());		         
		         
				}
			 }
				 
				 if(before_map_list.size()==after_map_list.size() && before_map_list.size() > 0) {
				 isCorrectData = true;	 				
				 for(int i=0;i<before_map_list.size();i++) {
					 
				 controlFile = new File(before_map_list.get(i));
				 testFile    = new File(after_map_list.get(i));
				 
				 //System.out.println("controlFile=>"+controlFile.getAbsolutePath());
				 //System.out.println("testFile=>"+testFile.getAbsolutePath());
				 
				 String mapName = controlFile.getName().substring(0, controlFile.getName().lastIndexOf("."));
				 
				 if(mapName.contains("before"))
					mapName = mapName.replace("_before", "");
				 else if(mapName.contains("BEFORE"))
					  mapName = mapName.replace("_BEFORE", "");
				 
				 //System.out.println("mapName=>"+mapName);
				 
		         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		         DocumentBuilder dBuilder;

		         dBuilder = dbFactory.newDocumentBuilder();

		         Document controldoc = dBuilder.parse(controlFile);
		         controldoc.getDocumentElement().normalize();
		         
		         Document testdoc = dBuilder.parse(testFile);
		         testdoc.getDocumentElement().normalize();
		         
		         Document resultDoc = dBuilder.newDocument();
		         String expression_ip = ".//Field/StoreLimit[DataType/text() = 'date']";
		         
		         resultDoc = CompareMaps(controldoc , testdoc , expression_ip , resultDoc ) ;
		         
		        // DOM to file
		         
		         Transformer transformer = TransformerFactory.newInstance().newTransformer();
		         transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		         transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		         Result output = new StreamResult(new File(outputDirectoryName+File.separator+mapName+"_output.xml"));
		         Source input = new DOMSource(resultDoc);

		         transformer.transform(input, output);
		         
				 }
		         				 
			 }						
		}
		return isCorrectData;
		
}

private static Document CompareMaps(Document controldoc, Document testdoc,
		String expression_ip , Document resultDoc )  {
	
	
	String matchexpression = null;
	
	Element rootElement = resultDoc.createElement("Result");
	resultDoc.appendChild(rootElement);
	
	   
	XPath xPath =  XPathFactory.newInstance().newXPath();
	NodeList nodeList = null;
	try {
		nodeList = (NodeList) xPath.compile(expression_ip).evaluate(controldoc, XPathConstants.NODESET);
	} catch (XPathExpressionException e) {
		// TODO Auto-generated catch block
		//System.out.println("Expression failed to get the data for the particular Node");
		e.printStackTrace();
	}
	
    for (int i = 0; i < nodeList.getLength(); i++) {
   	 Node storeNode = nodeList.item(i); 
   	 
   	Element storeElement = (Element) storeNode;
          	
    String format_control = storeElement.getElementsByTagName("Format").item(0).getTextContent();
    
    Element fieldElement =  (Element) storeElement.getParentNode();
    
    String name_control = (String)fieldElement.getElementsByTagName("Name").item(0).getTextContent();
    
    	matchexpression = ".//Field[Name/text()='"+name_control+"']";
   	  
         
    NodeList fnodeList = null;
	try {
		fnodeList = (NodeList) xPath.compile(matchexpression).evaluate(testdoc, XPathConstants.NODESET);
	} catch (XPathExpressionException e) {
		// TODO Auto-generated catch block
		//System.out.println("Expression failed at match expression");
		e.printStackTrace();
	}
	
	
  	 Node fieldNode = fnodeList.item(0);  
  	if(fieldNode != null){
     
  	 Element fElement_testdoc = (Element) fieldNode;
  	 Element sElement_testdoc = (Element) fElement_testdoc.getElementsByTagName("StoreLimit").item(0);
  	 String format = sElement_testdoc.getElementsByTagName("Format").item(0).getTextContent();
  	  
  	if(!(format.equals(format_control))){
  		
  		Element eField = resultDoc.createElement("Field");
  		rootElement.appendChild(eField);
  		
  		Element eFieldName = resultDoc.createElement("FieldName");
  		eFieldName.appendChild(resultDoc.createTextNode(name_control));
  		eField.appendChild(eFieldName);
  		
  		Element eDatatype = resultDoc.createElement("DataType");
  		eDatatype.appendChild(resultDoc.createTextNode("date"));
  		eField.appendChild(eDatatype);
  		
  		Element eFormat= resultDoc.createElement("Format");
  		eFormat.appendChild(resultDoc.createTextNode(format_control));
  		eField.appendChild(eFormat);
  		//System.out.println("AFter Modofication : "+storeElement.getElementsByTagName("Format").item(0).getTextContent());
   
  	  }
	}
	
	else{
		
		//System.out.println(name_control + " Record does not exist");
	}
        	 
    }
	
	return resultDoc;
}
	
	
	// Added Function copyFileUsingFileChannels used for MRS Code generator -- Manish
	private static void copyFileUsingFileChannels(String strSource, String strDest)
	        throws IOException {
		
	    FileChannel inputChannel = null;
	    FileChannel outputChannel = null;
	    File source = new File(strSource);
	    File dest= new File(strDest);
	    try {
	        inputChannel = new FileInputStream(source).getChannel();
	        outputChannel = new FileOutputStream(dest).getChannel();
	        outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
	    } finally {
	        inputChannel.close();
	        outputChannel.close();
	    }
	} //End CopyFileUsingFileChannels
	
	// Added Function copyFileUsingFileChannels used for MRS Code generator -- Manish
	public static void copyFolder(File src, File dest)
	    	throws IOException{
	 
	    	if(src.isDirectory()){
	 
	    		//if directory not exists, create it
	    		if(!dest.exists()){
	    		   dest.mkdir();
	    		  // System.out.println("Directory copied from " 
	                   //           + src + "  to " + dest);
	    		}
	 
	    		//list all the directory contents
	    		String files[] = src.list();
	 
	    		for (String file : files) {
	    		   //construct the src and dest file structure
	    		   File srcFile = new File(src, file);
	    		   File destFile = new File(dest, file);
	    		   //recursive copy
	    		   copyFolder(srcFile,destFile);
	    		}
	 
	    	}else{
	    		//if file, then copy it
	    		//Use bytes stream to support all file types
	    		InputStream in = new FileInputStream(src);
	    	        OutputStream out = new FileOutputStream(dest); 
	 
	    	        byte[] buffer = new byte[1024];
	 
	    	        int length;
	    	        //copy the file content in bytes 
	    	        while ((length = in.read(buffer)) > 0){
	    	    	   out.write(buffer, 0, length);
	    	        }
	 
	    	        in.close();
	    	        out.close();
	    	       // System.out.println("File copied from " + src + " to " + dest);
	    	}
	    }//End CopyFolder

}
