package com.ibm.mapping.qc;
/**
 * @author Sanket.
 * 
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class QC_MxlParser {

	//log variable 
	static Logger log = Logger.getLogger(QC_MxlParser.class.getName());
	static DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
	static DateFormat formatter1 = new SimpleDateFormat("MM-dd-yyyy");
	static String authorName = "";
	static String mapName = "";
	private static class Entry {
		private String Name;
		private String date;
		private String ticketNumber;
	}

	public static void parse(File file, QC_ValidationResult result,String isGentran) {
		String strDate = "";
	//	Pattern pattern = Pattern.compile("(Modified|Created)\\s+By\\s+(.*)\\s+On\\s+(\\d+/\\d+/\\d+).*Ticket[#-](.*)",	Pattern.CASE_INSENSITIVE);
		Pattern pattern = Pattern.compile("(Created|Modified|Updated)\\s*+By\\s*+(.*)\\s*+On\\s*+(\\d+/\\d+/\\d+).*\\s*Case[#-][:]?(.*)",	Pattern.CASE_INSENSITIVE);
		
		String fileName = file.getName();
		String typeOfMap = "";
		if (fileName.contains("_I_") || fileName.contains("_IB_")){
			typeOfMap = "INBOUND";
		}else if (fileName.contains("_O_") || fileName.contains("_OB_")){
			typeOfMap = "OUTBOUND";
		}else{
			typeOfMap = "XML";
		}

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setNamespaceAware(false);
		try {
			DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();

			InputSource is = new InputSource(new FileReader(file));
			Document doc = null;
			doc = documentBuilder.parse(is);

			NodeList preSessionRules = doc.getElementsByTagName("PreSessionRule");
			for (int i = 0; i < preSessionRules.getLength(); i++) {
				String content = getContent(preSessionRules.item(i));

				List<Entry> entryList = new ArrayList<Entry>();
				Matcher matcher = pattern.matcher(content);
				
				try {
				while (matcher.find()) {
					Entry entry = new Entry();
					String str=matcher.group(1);
					System.out.println("**** "+str+" ****");
					//String str1=matcher.group(2);
					//System.out.println("**** "+str1+" ****");
					entry.Name = matcher.group(2);
					strDate = matcher.group(3);
					entry.date = strDate;
					entry.ticketNumber = matcher.group(4);
					System.out.println("1 entry.ticketNumber.trim()=>"+entry.ticketNumber.trim());
					//result.setTicketNo(entry.ticketNumber.trim());
					entryList.add(entry);
				}
				}catch(IllegalStateException is1) {
					is1.printStackTrace();
					log.error("Presession pattern seems to be incorrect..IllegalStateException been raised for map presession contents");	
				}catch(IndexOutOfBoundsException ie) {
					ie.printStackTrace();
					log.error("Presession pattern seems to be incorrect..IndexOutOfBoundsException been raised for map presession contents...please make sure that no extra whitespace is present in the pattern");
				}
				
				if(!matcher.find())
				result.setPresessionUpdate("NO");
				System.out.println("****1 "+strDate);    // first entry in pre-session
				// and get that as a Date
				Date today = new Date();
				String todayDateStr = "";

				Entry latestEntry = getLatestEntry(entryList);
				if (latestEntry != null) {

					result.setName(latestEntry.Name);
					String dateFor = latestEntry.date;           // // latest entry in pre-session
					if (dateFor.contains("/")) {
						todayDateStr = formatter.format(today);
					} else {
						todayDateStr = formatter1.format(today);
					}
					System.out.println("****2 "+dateFor);
					System.out.println("2 latestEntry.ticketNumber.trim()=>"+latestEntry.ticketNumber.trim());
					String TicketNo=latestEntry.ticketNumber.trim();
					
					if(TicketNo.startsWith("5377-")) {
						System.out.println("************** <><><><> *********");
						TicketNo=TicketNo.substring(5, TicketNo.length());
					}
					
					System.out.println("****2 TicketNo "+TicketNo);
					
					result.setTicketNo(TicketNo);
					
					Date date = new Date();
					DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
					String dateS = df.format(date);
					System.out.println("****3 "+dateS);
					if(dateFor.equals(dateS)) {
					result.setPresessionUpdate("YES");
					//result.setTicketNo(latestEntry.ticketNumber);
					}
					else
					result.setPresessionUpdate("NO");
					
					result.setDate(dateFor);
					//result.setTicketNo(latestEntry.ticketNumber);
					result.setToday(false);
					if (todayDateStr.equalsIgnoreCase(dateFor)) {
						result.setToday(true);
					}
					result.setTypeOfMap(typeOfMap);
				}
				break;
			}
			
			NodeList ediAssociationsIN = doc.getElementsByTagName("EDIAssociations_IN");
			NodeList ediAssociationsOUT = doc.getElementsByTagName("EDIAssociations_OUT");
			iterateNode(ediAssociationsIN);
			iterateNode(ediAssociationsOUT);
			
			NodeList summaryList = doc.getElementsByTagName("Summary");
			
			getSummary(result, summaryList, fileName,isGentran);

			System.out.println("");
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (SAXException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} 
		finally {

		}

	}


	public static void getSummary(QC_ValidationResult result, NodeList summaryList, String fileName,String isGentran){
			Node node = summaryList.item(0);
			NodeList nlist = node.getChildNodes();
			for (int j = 0; j < nlist.getLength(); j++) {
				
				Node cNode = nlist.item(j);
				if (cNode instanceof Element) {
					Element elem = (Element) cNode;
					String tagName = elem.getNodeName();
					if (tagName.equalsIgnoreCase("Author")){
						authorName = elem.getTextContent();
						if (authorName.equalsIgnoreCase("IBM")){
							//System.out.println("Sanket");
							authorName = "YES";
						}else{
							authorName = "NO ( " + authorName+" )";
						}
					}else if(tagName.equalsIgnoreCase("Description")){
						fileName = fileName.replace(".mxl", "");						
							
						//if(isGentran=="Y") {
						System.out.println("fileName BEFORE=>"+fileName);
						if(fileName.endsWith("_SI")) {
						   //fileName=fileName.replace("_SI","");
							fileName=fileName.substring(0, fileName.length()-3).trim();
						   if(fileName.startsWith("STAN") || fileName.startsWith("Stan"))
							  fileName=fileName.substring(0,fileName.length()-9).trim();
						}
						/*else if(fileName.startsWith("STAN") || fileName.startsWith("Stan"))
							  fileName=fileName.substring(0,fileName.length()-9);*/
						//}
						
						System.out.println("fileName AFTER=>"+fileName);
						mapName = elem.getTextContent();
						System.out.println("mapName=>"+mapName);
						if (mapName.equalsIgnoreCase(fileName)){
							mapName = "YES";
						}else{
							mapName = "NO ( " + mapName+" )";
						}
					}
				//	doc.renameNode(elem, elem.getNamespaceURI(), toTag);
				}
			}
			System.out.println("Map Name in Edit Details=>"+mapName);
			result.setMapName(mapName);
			result.setAuthorName(authorName);
			
	}
	
	public static void iterateNode(NodeList ediAssociationsIN){
		for (int i = 0; i < ediAssociationsIN.getLength(); i++) {
			Node node = ediAssociationsIN.item(i);
			NodeList nlist = node.getChildNodes();
			for (int j = 0; j < nlist.getLength(); j++) {
				
				Node cNode = nlist.item(j);
				if (cNode instanceof Element) {
					Element elem = (Element) cNode;
					System.out.println(elem.getTagName()+" ==> "+elem.getTextContent());
				//	doc.renameNode(elem, elem.getNamespaceURI(), toTag);
				}
			}
			
			if (ediAssociationsIN.item(i) instanceof Element) {
				Element elem = (Element) ediAssociationsIN.item(i);
				System.out.println(elem);
			}
		}
	}
	private static Entry getLatestEntry(List<Entry> entryList) {
		Entry latestEntry = null;
		if ( entryList != null && !entryList.isEmpty()){
		latestEntry = entryList.get(0);
		for (Entry entry : entryList) {
			Date date = null;
			Date dateLatest = null;
			try {
				date = formatter.parse(entry.date);
				dateLatest = formatter.parse(latestEntry.date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("date>>>>>>>>>>>>"+date);
			System.out.println("dateLatest>>>>>>>>>"+dateLatest);
			if (date.after(dateLatest)) {
				latestEntry = entry;
			}
		}
		}
		return latestEntry;
	}

	private static String getContent(Node item) {
		Element element = (Element) item;
		return element.getTextContent();
	}	
}
