package com.ibm.mapping.ec;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class Util {
	Document document = null;
	Document si =null;
	@SuppressWarnings("unused")
	private Element enp;
	int flagX12 = 0;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getKeys(Row keys){
		List key = new ArrayList();
		for(Cell cell :keys){
			int index = cell.getColumnIndex();
			String value = cell.getStringCellValue();
			key.add(index, value);
		}
		return key;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getValue(Row values ,Row index , List keys) {
		Map record = new HashMap();
		for(Cell cell : values ){
			String key = index.getCell(cell.getColumnIndex()).getStringCellValue();
			int type = values.getCell(cell.getColumnIndex()).getCellType();
			if(type == HSSFCell.CELL_TYPE_NUMERIC){
				int intcellValue = (int) values.getCell(cell.getColumnIndex()).getNumericCellValue();
				record.put(key, intcellValue);
			}else if(type == HSSFCell.CELL_TYPE_STRING){
				String cellValue = values.getCell(cell.getColumnIndex()).getStringCellValue();
				record.put(key, cellValue);
			}
		}
		//System.out.println(record.toString());
		return record;
	}
	public Util() {
		// TODO Auto-generated constructor stub
	}
	public void writeTofile(org.jdom.Document doc , int choice , String outfile ) {
		Writer writer = null;
		try{
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
			//String outfile = "C:/";
			
		
			if(choice == 1){
				writer = new FileWriter(outfile + "XML_EDIFACT_IB_Envelope.xml");
				xmlOutput.output(doc,writer);
				//xmlOutput.output(doc, new FileWriter(outfile + "XML_EDIFACT_IB_Envelope.xml"));
			}
			if(choice == 2 ){
				writer = new FileWriter(outfile + "XML_EDIFACT_OB_Envelope.xml");
				xmlOutput.output(doc,writer);
				//xmlOutput.output(doc, new FileWriter(outfile + "XML_EDIFACT_OB_Envelope.xml"));
			}
			if(choice == 3 ){
				writer = new FileWriter(outfile + "XML_ANSI_IB_Envelope.xml");
				xmlOutput.output(doc,writer);
				//xmlOutput.output(doc, new FileWriter(outfile + "XML_ANSI_INB_Envelope.xml"));
			}
			if(choice == 4 ){
				writer = new FileWriter(outfile + "XML_ANSI_OB_Envelope.xml");
				xmlOutput.output(doc,writer);
				//xmlOutput.output(doc, new FileWriter(outfile + "XML_ANSI_OB_Envelope.xml"));
			}
			if(choice == 5 ){
				writer = new FileWriter(outfile + "XML_VDA_IB_Envelope.xml");
				xmlOutput.output(doc,writer);
				//xmlOutput.output(doc, new FileWriter(outfile + "XML_VDA_INB_Envelope.xml"));
			}
			if(choice == 6 ){
				writer = new FileWriter(outfile + "XML_VDA_OB_Envelope.xml");
				xmlOutput.output(doc,writer);
				//xmlOutput.output(doc, new FileWriter(outfile + "XML_VDA_OB_Envelope.xml"));
			}
			if(choice == 7 ){
				writer = new FileWriter(outfile + "XML_TRADACOM_IB_Envelope.xml");
				xmlOutput.output(doc,writer);
				//xmlOutput.output(doc, new FileWriter(outfile + "XML_TRADACOM_INB_Envelope.xml"));
			}
			if(choice == 8 ){
				writer = new FileWriter(outfile + "XML_TRADACOMS_OB_Envelope.xml");
				xmlOutput.output(doc,writer);
				//xmlOutput.output(doc, new FileWriter(outfile + "XML_TRADACOMS_OB_Envelope.xml"));
			}
			//JFrame frame = new JFrame("Saved File");			
			//JOptionPane.showMessageDialog(frame, "Completed");
			//System.out.println("printed");
			//System.out.println("File Saved!");
			
			} catch (IOException io) {
			//System.out.println(io.getMessage());
		}
		finally {
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	}

	
