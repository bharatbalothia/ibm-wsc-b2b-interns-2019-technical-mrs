package com.ibm.mapping.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
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
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.ibm.mapping.service.DataService;
import com.ibm.mapping.service.DataServiceImpl;
import com.ibm.mapping.servlet.Constants;
/**
 * @author Sanket
 *
*/
public class validateSheet implements Constants {
	
	static int data_type_col_no = 0,notes_col_no = 0,name_col_no = 0,link_col_no=0,std_rule_col_no=0,ext_rule_col_no=0;
	//log variable 
	static Logger log = Logger.getLogger(validateSheet.class.getName());
	public static boolean checkXLSX(String outputDirectoryName, String path, String mapName) {
        HashMap<Integer,String> missing_map = new HashMap<Integer,String>();
        File file = new File(path);
        data_type_col_no = 0;notes_col_no = 0;name_col_no = 0;link_col_no=0;std_rule_col_no=0;ext_rule_col_no=0;
		boolean isXML = false,isAddNotesPresent = false;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setNamespaceAware(false);
		try {
			DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
			InputSource is = new InputSource(new FileReader(file));
			Document doc = null;
			doc = documentBuilder.parse(is);doc.normalize();

			NodeList rowList = doc.getElementsByTagName(/*"xls:row"*/"row");
			//System.out.println("ROW COUNT=>"+rowList.getLength());
			
			if(rowList.getLength() > 0) {
				
			/*Node firstROW = rowList.item(0);
			NodeList firstROWColList = firstROW.getChildNodes();
			Node firstROWfirstCOL = firstROWColList.item(1);*/
			NodeList secondROWColList = null,seventhROWColList = null;
			if(rowList.getLength() > 1) {
			Node secondROW = rowList.item(1);
			secondROWColList = secondROW.getChildNodes();
			Node secondROWfirstCOL = secondROWColList.item(1);
			if(secondROWfirstCOL.getTextContent().trim().equalsIgnoreCase("output header")) {
			   //System.out.println("XML LAYOUT");
			   isXML=true;
			   }
			}
			if(rowList.getLength() > 6 && isXML == true) {
			Node seventhROW = rowList.item(6);			
			seventhROWColList = seventhROW.getChildNodes();	
			}

			/*if(firstROWfirstCOL.getTextContent().trim().equalsIgnoreCase("edi:"))
			System.out.println("EDI STANDARD LAYOUT");*/
			
			
			if(!isXML) {
				
				if(secondROWColList!=null)
				setColumnNos(secondROWColList);
				
				for(int j=2;j<rowList.getLength();j++) {
				Node row = rowList.item(j);
				if(row!=null && data_type_col_no > 0 && link_col_no > 0 && std_rule_col_no > 0 && ext_rule_col_no > 0 && notes_col_no > 0 && name_col_no > 0) {
				NodeList colList = row.getChildNodes();
				if(!colList.item(1).getTextContent().trim().isEmpty()) {
				if((colList.item(data_type_col_no).getTextContent().trim().equalsIgnoreCase("string")|| colList.item(data_type_col_no).getTextContent().trim().equalsIgnoreCase("numeric") || colList.item(data_type_col_no).getTextContent().trim().equalsIgnoreCase("date")) && colList.item(1).getTextContent().trim().equalsIgnoreCase("yes")) {
				
    			if( !colList.item(link_col_no).getTextContent().trim().isEmpty() || (!colList.item(std_rule_col_no).getTextContent().trim().isEmpty() || !colList.item(ext_rule_col_no).getTextContent().trim().isEmpty()) ) {
    			/*if(colList.item(notes_col_no).getTextContent().trim().isEmpty()) 
    			missing_map.put(j+1,colList.item(name_col_no).getTextContent().trim());*/
    				
    			//Indirect links
    			if(!colList.item(link_col_no).getTextContent().trim().isEmpty() && (colList.item(link_col_no).getTextContent().trim().toLowerCase().contains("temp") || colList.item(link_col_no).getTextContent().trim().toLowerCase().contains("temporary") || colList.item(link_col_no).getTextContent().trim().toLowerCase().contains("xxx") || colList.item(link_col_no).getTextContent().trim().toLowerCase().contains("$$$")) && colList.item(notes_col_no).getTextContent().trim().isEmpty() ) 
    			missing_map.put(j+1,colList.item(name_col_no).getTextContent().trim());	
    			
    			//Extended rule present at output
    			if(!colList.item(ext_rule_col_no).getTextContent().trim().isEmpty() && colList.item(notes_col_no).getTextContent().trim().isEmpty() ) 
        		missing_map.put(j+1,colList.item(name_col_no).getTextContent().trim());	
    			
    			}
    			
				}
				}
				}
				}
			}
			else {
				if(seventhROWColList!=null)
				setColumnNos(seventhROWColList);
				
				for(int j=7;j<rowList.getLength();j++) {
				Node row = rowList.item(j);
				if(row!=null && data_type_col_no > 0 && link_col_no > 0 && std_rule_col_no > 0 && ext_rule_col_no > 0 && notes_col_no > 0 && name_col_no > 0) {
				NodeList colList = row.getChildNodes();
				if(!colList.item(1).getTextContent().trim().isEmpty()) {
				if((colList.item(data_type_col_no).getTextContent().trim().equalsIgnoreCase("string")|| colList.item(data_type_col_no).getTextContent().trim().equalsIgnoreCase("numeric") || colList.item(data_type_col_no).getTextContent().trim().equalsIgnoreCase("date")) && colList.item(1).getTextContent().trim().equalsIgnoreCase("yes")) {
				
    			if( !colList.item(link_col_no).getTextContent().trim().isEmpty() || (!colList.item(std_rule_col_no).getTextContent().trim().isEmpty() || !colList.item(ext_rule_col_no).getTextContent().trim().isEmpty()) ) {
    			/*if(colList.item(notes_col_no).getTextContent().trim().isEmpty()) 
    			missing_map.put(j+1,colList.item(name_col_no).getTextContent().trim());*/
    			
    			//Indirect links
    			if(!colList.item(link_col_no).getTextContent().trim().isEmpty() && (colList.item(link_col_no).getTextContent().trim().toLowerCase().contains("temp") || colList.item(link_col_no).getTextContent().trim().toLowerCase().contains("temporary") || colList.item(link_col_no).getTextContent().trim().toLowerCase().contains("xxx") || colList.item(link_col_no).getTextContent().trim().toLowerCase().contains("$$$")) && colList.item(notes_col_no).getTextContent().trim().isEmpty() ) 
    			missing_map.put(j+1,colList.item(name_col_no).getTextContent().trim());	
    			
    			//Extended rule present at output
    			if(!colList.item(ext_rule_col_no).getTextContent().trim().isEmpty() && colList.item(notes_col_no).getTextContent().trim().isEmpty() ) 
        		missing_map.put(j+1,colList.item(name_col_no).getTextContent().trim());	
    			
    			}
    			
				}
				}
				}
				}
			 }
			
			//System.out.println("missing_map.size()=>"+missing_map.size());
			
		    if(missing_map.size()==0)
		    isAddNotesPresent = true;
		    else {
		    	DateFormat df = new SimpleDateFormat("MMddyyyy");
		        Calendar calobj = Calendar.getInstance();
		        //System.out.println(df.format(calobj.getTime()));
		        //System.out.println("file.getParent()=>"+file.getParent());
		    	BufferedWriter writer = new BufferedWriter( new FileWriter(/*file.getParent()*/outputDirectoryName+"\\ADD_NOTES_MISSING_REPORT_"+/*df.format(calobj.getTime())*/mapName+".html") );
		    	writer.write("<html><head><style>table {width:25%;}table, th, td {border: 1px solid black;border-collapse: collapse;}th, td {padding: 5px;text-align: left;}table#t01 tr:nth-child(even) {background-color: #eee;}table#t01 tr:nth-child(odd) {background-color:#fff;}table#t01 th	{background-color: black;color: white;}</style></head><body><table id='t01'><tr><th>ROW #</th><th>FIELD DETAILS</th></tr>");

		    	//System.out.println("\nAdditional Notes is missing for below fields in the MRS : \n");
		    	Map<Integer,String> sortedMap = new TreeMap<Integer,String>(missing_map);
		    	//System.out.println(sortedMap);
		    	Set mapSet = (Set) sortedMap.entrySet();
		    	Iterator mapIterator = mapSet.iterator();
		    	int index=0;
		        while (mapIterator.hasNext()) 
		        {
		        index++;
		        Map.Entry mapEntry = (Map.Entry) mapIterator.next();
		        //String key = (String) mapEntry.getKey();
		        //String value = (String) mapEntry.getValue();
		        //missing_str = missing_str + index+") ROW#: "+mapEntry.getKey()+"      field: "+mapEntry.getValue() + "\n";
		        //System.out.println(index+") ROW#: "+mapEntry.getKey()+" field: "+mapEntry.getValue());
		        writer.write("<tr><td>"+mapEntry.getKey()+"</td><td>"+mapEntry.getValue()+"</td></tr>");
		        }
		        writer.write("</table></body></html>");
		        writer.close();
		     }
		   }
		   else
		   log.error("Empty excel sheet of MRS");
		    			
			
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
		finally {

		}
		
		//System.out.println("isAddNotesPresent=>"+isAddNotesPresent);
		return isAddNotesPresent;

	}
	
	public static void updateXLSX(String path, String AgencyID, String VersionID) {
		
		//System.out.println("AgencyID=>"+AgencyID);
		//System.out.println("VersionID=>"+VersionID);
        File file = new File(path);
        data_type_col_no = 0;notes_col_no = 0;name_col_no = 0;link_col_no=0;std_rule_col_no=0;ext_rule_col_no=0;
		boolean isXML = false;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setNamespaceAware(false);
		try {
			DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
			InputSource is = new InputSource(new FileReader(file));
			Document doc = null;
			doc = documentBuilder.parse(is);doc.normalize();

			NodeList rowList = doc.getElementsByTagName(/*"xls:row"*/"row");
			//System.out.println("ROW COUNT=>"+rowList.getLength());
			
			if(rowList.getLength() > 0) {
				
			/*Node firstROW = rowList.item(0);
			NodeList firstROWColList = firstROW.getChildNodes();
			Node firstROWfirstCOL = firstROWColList.item(1);*/
			NodeList secondROWColList = null;
			if(rowList.getLength() > 1) {
			Node secondROW = rowList.item(1);
			secondROWColList = secondROW.getChildNodes();
			Node secondROWfirstCOL = secondROWColList.item(1);
			if(secondROWfirstCOL.getTextContent().trim().equalsIgnoreCase("output header")) {
			   //System.out.println("XML LAYOUT");
			   isXML=true;
			   }
			}
			
			if(!isXML) {
				List<String> edi_data = null;
				DataService service = new DataServiceImpl(jdbcClassName, jdbcUrl,jdbcSchemaName, jdbcUserName, jdbcPassword);
				try {
					edi_data = service.fetchQualifierDetails(AgencyID,VersionID);
					} catch (SQLException e) {
						e.printStackTrace();
				}
				
				if(secondROWColList!=null)
				setColumnNos(secondROWColList);
				
				for(int j=2;j<rowList.getLength();j++) {
				Node row = rowList.item(j);
				if(row!=null && data_type_col_no > 0 && link_col_no > 0 && std_rule_col_no > 0 && ext_rule_col_no > 0 && notes_col_no > 0 && name_col_no > 0) {
				NodeList colList = row.getChildNodes();
				if(!colList.item(1).getTextContent().trim().isEmpty()) {
				if((colList.item(data_type_col_no).getTextContent().trim().equalsIgnoreCase("string")|| colList.item(data_type_col_no).getTextContent().trim().equalsIgnoreCase("numeric") || colList.item(data_type_col_no).getTextContent().trim().equalsIgnoreCase("date")) && colList.item(1).getTextContent().trim().equalsIgnoreCase("yes")) {
				
    			if( !colList.item(link_col_no).getTextContent().trim().isEmpty() || (!colList.item(std_rule_col_no).getTextContent().trim().isEmpty() || !colList.item(ext_rule_col_no).getTextContent().trim().isEmpty()) ) {

    			if(colList.item(std_rule_col_no).getTextContent().trim().contains("Use Constant:")) {
    		    //System.out.println("*** STD RULE ***");
    		    String[] parts = null;
    			String ELEMENTID = null,qualifier = null,qualf_desc = null;
    			int index = colList.item(std_rule_col_no).getTextContent().trim().indexOf("Use Constant:");
    			qualifier = colList.item(std_rule_col_no).getTextContent().trim().substring(index+13, colList.item(std_rule_col_no).getTextContent().trim().length()).trim();
    			//System.out.println("qualifier=> "+qualifier);
    			String field_name = null;
    			Matcher m = Pattern.compile("\\[([^\\]]+)]").matcher(colList.item(name_col_no).getTextContent().trim());
    		     while(m.find()) {
    		     field_name = m.group(1);    
    		    }
    			//System.out.println("field_name=>"+field_name);
    			if(field_name.contains(":")) { 
    			parts = field_name.split(":");
    			ELEMENTID = parts[0];
    			}
    			else
    			ELEMENTID = field_name;
    			//System.out.println("ELEMENTID=>"+ELEMENTID);
    			if(edi_data!=null & edi_data.size() > 0 && qualifier!=null && ELEMENTID!=null) {
        			String pattern = ELEMENTID + "_" + qualifier + "_";
        			for(int i=0;i<edi_data.size();i++) {
        				if(edi_data.get(i).startsWith(pattern)) {
        				qualf_desc = edi_data.get(i).replace(pattern, "");
        				break;
        			}
        		 }
        		//System.out.println("qualf_desc=>"+qualf_desc);
        		if(qualf_desc!=null) {
        			String notes = colList.item(notes_col_no).getTextContent().trim();
        			NodeList isNodeList = colList.item(notes_col_no).getChildNodes();
        			for(int i =0;i<isNodeList.getLength();i++) {
        				Node testNode = isNodeList.item(i);
        				if(testNode instanceof Element & testNode != null) {
        				NodeList tNodeList = testNode.getChildNodes();
        				for(int k =0;k<tNodeList.getLength();k++) {
        					Node tNode = tNodeList.item(k);
            				if(tNode instanceof Element & tNode != null) 
            				tNode.setTextContent(notes + " [ Qualifier Description = '"+qualf_desc+"' ]");	
        				}   				
        			   }
        			  }
        		   }
        		 }
    			}
    			else if(!colList.item(ext_rule_col_no).getTextContent().trim().isEmpty()) {
    				String field_name = null;
        			Matcher m = Pattern.compile("\\[([^\\]]+)]").matcher(colList.item(name_col_no).getTextContent().trim());
        		     while(m.find()) {
        		     field_name = m.group(1);    
        		    }
        			String start = "#" + field_name + "=";
        			if(colList.item(ext_rule_col_no).getTextContent().trim().replaceAll(" ","").startsWith(start)) {
        				//System.out.println("*** EXT RULE ***");
        				//System.out.println("field_name=>"+field_name);
        				String[] parts = null;
            			String ELEMENTID = null,qualifier = null,qualf_desc = null;
            			if(field_name.contains(":")) { 
            			parts = field_name.split(":");
            			ELEMENTID = parts[0];
            			}
            			else
            			ELEMENTID = field_name;
            			//System.out.println("ELEMENTID=>"+ELEMENTID);
            			Pattern p = Pattern.compile("\"([^\"]*)\"");
            		    Matcher m1 = p.matcher(colList.item(ext_rule_col_no).getTextContent().trim().replaceAll(" ",""));
            		    while (m1.find()) {
            		    qualifier = m1.group(1);
            		    }
            		    //System.out.println("qualifier=>"+qualifier);
            			/*try {
            			  if(qualifier!=null)
        				  qualf_desc = service.fetchQualifierDetails(AgencyID,VersionID,ELEMENTID,qualifier);
        				} catch (SQLException e) {
        					e.printStackTrace();
        				}*/
            		    if(edi_data!=null & edi_data.size() > 0 && qualifier!=null && ELEMENTID!=null) {
                			String pattern = ELEMENTID + "_" + qualifier + "_";
                			for(int i=0;i<edi_data.size();i++) {
                				if(edi_data.get(i).startsWith(pattern)) {
                				qualf_desc = edi_data.get(i).replace(pattern, "");
                				break;
                			}
                		 }
                		//System.out.println("qualf_desc=>"+qualf_desc);
                		if(qualf_desc!=null) {
                			String notes = colList.item(notes_col_no).getTextContent().trim();
                			NodeList isNodeList = colList.item(notes_col_no).getChildNodes();
                			for(int i =0;i<isNodeList.getLength();i++) {
                				Node testNode = isNodeList.item(i);
                				if(testNode instanceof Element & testNode != null) {
                				NodeList tNodeList = testNode.getChildNodes();
                				for(int k =0;k<tNodeList.getLength();k++) {
                					Node tNode = tNodeList.item(k);
                    				if(tNode instanceof Element & tNode != null) 
                    				tNode.setTextContent(notes + " [ Qualifier Description = '"+qualf_desc+"' ]");	
                				}   				
                			   }
                			  }
                		   }
                		}	
        			}
    			}
    			
    			}
    			
				}
				}
				}					         
				}
				// write the content into xml file
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = null;
				try {
					transformer = transformerFactory.newTransformer();
				} catch (TransformerConfigurationException e1) {
					log.error(e1.getMessage());
				}
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File(path));
				try {
					transformer.transform(source, result);
				} catch (TransformerException e) {
					log.error(e.getMessage());
				}

				//System.out.println("Done");
			}
			
		   }
		   else
		   log.error("Empty excel sheet of MRS");
		    			
			
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
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		finally {

		}
	}
	
public static void setColumnNos(NodeList list) {
	
	for(int i=0;i<list.getLength();i++) {
		if(list.item(i).getTextContent().trim().equalsIgnoreCase("data type"))
			data_type_col_no=i;
		if(list.item(i).getTextContent().trim().equalsIgnoreCase("notes"))
			notes_col_no=i;
		if(list.item(i).getTextContent().trim().equalsIgnoreCase("name"))
			name_col_no=i;
    	if(list.item(i).getTextContent().trim().equalsIgnoreCase("xpath") || list.item(i).getTextContent().trim().equalsIgnoreCase("field") || list.item(i).getTextContent().trim().equalsIgnoreCase("element")) 
		   link_col_no = i;
    	if(list.item(i).getTextContent().trim().equalsIgnoreCase("std. rule") || list.item(i).getTextContent().trim().equalsIgnoreCase("standard rules")) 
    	   std_rule_col_no = i;
    	if(list.item(i).getTextContent().trim().equalsIgnoreCase("ext. rule") || list.item(i).getTextContent().trim().equalsIgnoreCase("extended rules")) 
    	   ext_rule_col_no = i;
	}
	
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
