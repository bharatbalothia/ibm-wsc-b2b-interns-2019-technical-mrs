package com.ibm.mapping.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author Sanket
 *
 */
public final class mxlTagUpdater {
	
	static int dialogButton;
	static HashMap<String, String> hmap = new HashMap<String, String>();
	//log variable 
	static Logger log = Logger.getLogger(mxlTagUpdater.class.getName());
	
    public static void updateMXLTags(String inputDirectory,
			String outputDirectory)
    {
		File inputDir = new File(inputDirectory);

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
					//String outputFileName;
					
					if(inputFileName.endsWith(".mxl"))
					{
						//outputFileName = inputFileName.replace(".mxl", "");
						parseMXL(listOfFiles[i],outputDirectory,inputFileName);
						
					}
					else
					{
						log.error("The service only accepts mxl file.");
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
	
	public static void parseMXL(File file, String outputDirectory, String fileName) {

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setNamespaceAware(false);

		try {
			DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
			InputSource is = new InputSource(new FileReader(file));
			Document doc = null;
			doc = documentBuilder.parse(is);doc.normalize();
			
			NodeList mapperList = doc.getElementsByTagName("Mapper");
			Element mapper=(Element) mapperList.item(0);
			
			NodeList inputList =  mapper.getElementsByTagName("INPUT");
			Element input=(Element) inputList.item(0);
			
			NodeList posSyntaxList =  input.getElementsByTagName("PosSyntax");
			Element posSyntax=(Element) posSyntaxList.item(0);
			
			//Element group=getNodebyName("Group",posSyntax);
			
			NodeList groupList=posSyntax.getElementsByTagName("Group");
			Element group=(Element)groupList.item(0);
			
			NodeList posRecordList=group.getElementsByTagName("PosRecord");
			for(int i=0;i<posRecordList.getLength();i++){
				
				NodeList blockSigList=((Element)posRecordList.item(i)).getElementsByTagName("BlockSig");
				//Element blockSig=(Element)blockSigList.item(0);
				Node blockSig=blockSigList.item(0);
					NodeList tagList=((Element) blockSig).getElementsByTagName("Tag");
					for(int j=0;j<tagList.getLength()-1;j++){                                    //(length-1) because for each BlockSig there are two <tag> and we want first and ignore second value
						if(!(tagList.item(j) instanceof Text) ){
							if(!tagList.item(j).getTextContent().equals("$$$")) {
								Node sNode = getNodebyTagName("Name",posRecordList.item(i));
								//System.out.println(sNode.getNodeName()+"="+sNode.getTextContent());
								//System.out.println(tagList.item(j).getNodeName()+"="+tagList.item(j).getTextContent());
							    if( /*!tagList.item(j).getTextContent().toLowerCase().startsWith("z") && !tagList.item(j).getTextContent().toLowerCase().startsWith("edi_dc40") &&*/ tagList.item(j).getTextContent().trim().length() > 7) {
							    	String extra = tagList.item(j).getTextContent().trim().substring(tagList.item(j).getTextContent().trim().length()-3);
							    	//System.out.println("extra=>"+extra);
							    	Pattern pattern = Pattern.compile("\\d{3}",Pattern.CASE_INSENSITIVE);
							    	Matcher matcher = pattern.matcher(extra);
							    	if(matcher.find()) {
							    	   hmap.put(tagList.item(j).getTextContent().trim(),tagList.item(j).getTextContent().trim().substring(0, tagList.item(j).getTextContent().trim().length()-3));
								       tagList.item(j).setTextContent(tagList.item(j).getTextContent().trim().substring(0, tagList.item(j).getTextContent().trim().length()-3));
								       //System.out.println("UPDATED => "+tagList.item(j).getNodeName()+"="+tagList.item(j).getTextContent().trim());	
							    	}
							    }

							}
						}						
					}					
			}
			
			Transformer xformer = TransformerFactory.newInstance().newTransformer();
	        xformer.transform(new DOMSource(doc), new StreamResult(new File(outputDirectory+fileName)));	
			//System.out.println("hmap.size()=>"+hmap.size());
			
	        if(hmap.size()>0) {
	        	DateFormat df = new SimpleDateFormat("MMddyyyy");
		        Calendar calobj = Calendar.getInstance();
		    	BufferedWriter writer = new BufferedWriter( new FileWriter(outputDirectory+"IDOC_TAG_REPORT_"+df.format(calobj.getTime())+".html") );
		    	writer.write("<html><head><style>table {width:25%;}table, th, td {border: 1px solid black;border-collapse: collapse;}th, td {padding: 5px;text-align: left;}table#t01 tr:nth-child(even) {background-color: #eee;}table#t01 tr:nth-child(odd) {background-color:#fff;}table#t01 th	{background-color: black;color: white;}</style></head><body><table id='t01'><tr><th>Old Record Tag</th><th>Trimmed Record Tag</th></tr>");

		    	/* Display content using Iterator*/
		        Set set = hmap.entrySet();
		        Iterator iterator = set.iterator();
		        while(iterator.hasNext()) {
		        	
		           Map.Entry mentry = (Map.Entry)iterator.next();
		           //writer.write(i++ +") "+mentry.getKey() + " => "+mentry.getValue() +"\n");
		           writer.write("<tr><td>"+mentry.getKey()+"</td><td>"+mentry.getValue()+"</td></tr>");
		           //writer.write("|   Old tag=>"+ mentry.getKey() + "   |           |   New tag=>"+mentry.getValue() +"   |\n");
		        }
		        writer.write("</table></body></html>");
		        writer.close();
		        
	            hmap.clear();
	        }
	        else if(hmap.size()==0) {
	        	 DateFormat df = new SimpleDateFormat("MMddyyyy");
		         Calendar calobj = Calendar.getInstance();
		    	 BufferedWriter writer = new BufferedWriter( new FileWriter(outputDirectory+"IDOC_TAG_REPORT_"+df.format(calobj.getTime())+".html") );
		    	 writer.write("<html><head><h2><b>There was no tag needs to be trimmed in the IDOC.</b></h2></head><body>");
		         writer.write("</body></html>");
		         writer.close();		   
	        }

			
		} catch (ParserConfigurationException e) {
			log.error("Error while processing "+ file.getName()+ " The error message is "+e.getMessage());
		} catch (FileNotFoundException e) {
			log.error("Error while processing "+ file.getName()+ " The error message is "+e.getMessage());
		} catch (SAXException e) {
			log.error("Error while processing "+ file.getName()+ " The error message is "+e.getMessage());
		} catch (IOException e) {
			log.error("Error while processing "+ file.getName()+ " The error message is "+e.getMessage());
		}
		catch (NullPointerException e) {
			log.error("Error while processing "+ file.getName()+ " The error message is "+e.getMessage());
		}
		catch (TransformerException e) {
			log.error("Error while processing "+ file.getName()+ " The error message is "+e.getMessage());
		}
		finally {

		}

	}
	
	private static Node getNodebyTagName(String tagName, Node node) {
		Node expectedNode=null;
		NodeList nodeList=node.getChildNodes();
		for(int i=0;i<nodeList.getLength();i++){
			
			expectedNode=nodeList.item(i);
			if(expectedNode.getNodeName().equals(tagName) && !(expectedNode instanceof Text)){
				return expectedNode;
			}
		}
		return expectedNode;
	}

	public static Element getFirstChildElement(Node node)
    {
        node = node.getFirstChild();
        while (node != null && node.getNodeType() != Node.ELEMENT_NODE)
        {
            node = node.getNextSibling();
        }
        return (Element) node;
    }
}
