package com.ibm.mapping.ec;

import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;

public class VDA_Inbound {

	public VDA_Inbound() {
		
	}
	
	@SuppressWarnings({ "rawtypes", "unused" })
	public Element envelopeHeader(int flag , Document doc ,Map xls , Namespace n){
			
		String type =null;
		String standard = null;
		String envID = null;
		String senderID = null;
		String recieverID= null;
		String version = null;
		String function = null;
		Element Envelope = null;
		Envelope = new Element("ENVELOPE" , n);

		if(flag == 1){
			
			envID = xls.get("Envelope Name").toString();
			senderID = xls.get("Sender ID (or Supplier Number/Data Sender)").toString();
			recieverID = xls.get("Receiver ID (or Customer Number/Data Source Number)").toString();
			type = "VDA";
			standard = "VDA";
			function = "0";
			
		}
		

		//Envelope Header Details 
		Element ENVELOPE_ID = new Element("ENVELOPE_ID" ,n ).setText(envID);
		Envelope.addContent(ENVELOPE_ID);
		Element ENVELOPE_EXTERNAL_ID = new Element("ENVELOPE_EXTERNAL_ID",n);
		Envelope.addContent(ENVELOPE_EXTERNAL_ID);
		Element ENVELOPE_VERSION = new Element("ENVELOPE_VERSION", n).setText("1");
		Envelope.addContent(ENVELOPE_VERSION);
		Element SIResourceDefaultVersion = new Element("SIResourceDefaultVersion",n).setText("true");
		Envelope.addContent(SIResourceDefaultVersion);
		Element TYPE = new Element("TYPE" ,n).setText(type);
		Envelope.addContent(TYPE);
		Element NAME = new Element("NAME",n).setText(envID);
		Envelope.addContent(NAME);
		Element STANDARD = new Element("STANDARD",n).setText(standard);
		Envelope.addContent(STANDARD);
		Element CONTROL_NUMBER = new Element("CONTROL_NUMBER",n).setText("1");
		Envelope.addContent(CONTROL_NUMBER);
		Element FUNCTION = new Element("FUNCTION",n).setText(function);
		Envelope.addContent(FUNCTION);
		Element EXTENDS_ID = new Element("EXTENDS_ID",n);
		Envelope.addContent(EXTENDS_ID);
		Element EXTENDS_VERSION = new Element("EXTENDS_VERSION",n).setText("-1");
		Envelope.addContent(EXTENDS_VERSION);
		Element SENDER_ID = new Element("SENDER_ID",n).setText(senderID);
		Envelope.addContent(SENDER_ID);
		Element RECEIVER_ID = new Element("RECEIVER_ID",n).setText(recieverID);
		Envelope.addContent(RECEIVER_ID);
		Element COMMENTS = new Element("COMMENTS",n).setText(envID);
		Envelope.addContent(COMMENTS);
		Element USERNAME = new Element("USERNAME" ,n).setText("admin");
		Envelope.addContent(USERNAME);
		Element CREATE_DATE = new Element("CREATE_DATE" ,n);
		Envelope.addContent(CREATE_DATE);
		Element USES_GLOBAL_CONTROL_NUMBER = new Element("USES_GLOBAL_CONTROL_NUMBER",n).setText("0");
		Envelope.addContent(USES_GLOBAL_CONTROL_NUMBER);

		return Envelope;
	
	}
	
	@SuppressWarnings("rawtypes")
	public VDA_Inbound(Document doc , Map xls, Namespace n, String outputDirectory) {
		
		int flag = 1;
		Element envelopeVDA = envelopeHeader(flag , doc , xls , n);
		doc.getRootElement().getChild("DOCUMENT_ENVELOPES" , n).addContent(envelopeVDA);
		
		if(flag == 1 ){
			//ISA envelope Parameters 
			Element ENVELOPE_PARMS_VDA = new Element("ENVELOPE_PARMS",n);
			envelopeVDA.addContent(ENVELOPE_PARMS_VDA);

			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM").setNamespace(n)
					.addContent(new Element("NAME").setNamespace(n).setNamespace(n).setText("BP")).
					addContent(new Element("VALUE").setNamespace(n).setNamespace(n).setText(xls.get("Comp. Reg. Number").toString())).
					addContent(new Element("INHERITED").setNamespace(n).setNamespace(n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM").setNamespace(n)
					.addContent(new Element("NAME").setNamespace(n).setText("BPDataExtractMode")).
					addContent(new Element("VALUE").setNamespace(n).setText("UseBP")).
					addContent(new Element("INHERITED").setNamespace(n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM").setNamespace(n)
					.addContent(new Element("NAME").setNamespace(n).setText("ComplianceCheckMapName")).
					addContent(new Element("VALUE").setNamespace(n).setText(xls.get("Carrier Number").toString())).
					addContent(new Element("INHERITED").setNamespace(n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM").setNamespace(n)
					.addContent(new Element("NAME").setNamespace(n).setText("ContractID")).
					addContent(new Element("VALUE").setNamespace(n)).
					addContent(new Element("INHERITED").setNamespace(n).setText("false")));
			
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM").setNamespace(n)
					.addContent(new Element("NAME").setNamespace(n).setText("ControlNumberAge")).
					addContent(new Element("VALUE").setNamespace(n)).
					addContent(new Element("INHERITED").setNamespace(n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM").setNamespace(n)
					.addContent(new Element("NAME").setNamespace(n).setText("EnvelopeMapName")).
					addContent(new Element("VALUE").setNamespace(n).setText("BrkIvda")).
					addContent(new Element("INHERITED").setNamespace(n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM").setNamespace(n)
					.addContent(new Element("NAME").setNamespace(n).setText("ExtractDirectory")).
					addContent(new Element("VALUE").setNamespace(n)).
					addContent(new Element("INHERITED").setNamespace(n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM").setNamespace(n)
					.addContent(new Element("NAME").setNamespace(n).setText("ExtractFilename")).
					addContent(new Element("VALUE").setNamespace(n)).
					addContent(new Element("INHERITED").setNamespace(n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM").setNamespace(n)
					.addContent(new Element("NAME").setNamespace(n).setText("ExtractMailbox")).
					addContent(new Element("VALUE").setNamespace(n)).
					addContent(new Element("INHERITED").setNamespace(n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM").setNamespace(n)
					.addContent(new Element("NAME").setNamespace(n).setText("ExtractMailboxMessageName")).
					addContent(new Element("VALUE").setNamespace(n)).
					addContent(new Element("INHERITED").setNamespace(n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM").setNamespace(n)
					.addContent(new Element("NAME").setNamespace(n).setText("InvokeBPMode")).
					addContent(new Element("VALUE").setNamespace(n).setText("SpecifyBP")).
					addContent(new Element("INHERITED").setNamespace(n).setText("false")));
			
			
			
			
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM").setNamespace(n)
					.addContent(new Element("NAME").setNamespace(n).setText("KeepTranslatedDocument")).
					addContent(new Element("VALUE").setNamespace(n).setText(xls.get("Warehouse Keeper Code").toString())).
					addContent(new Element("INHERITED").setNamespace(n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM").setNamespace(n)
					.addContent(new Element("NAME").setNamespace(n).setText("MessageType")).
					addContent(new Element("VALUE").setNamespace(n).setText(xls.get("Accepter Lookup Alias").toString())).
					addContent(new Element("INHERITED").setNamespace(n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM").setNamespace(n)
					.addContent(new Element("NAME").setNamespace(n).setText("PerformComplianceCheck")).
					addContent(new Element("VALUE").setNamespace(n).setText("YES")).
					addContent(new Element("INHERITED").setNamespace(n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM").setNamespace(n)
					.addContent(new Element("NAME").setNamespace(n).setText("TestFlag")).
					addContent(new Element("VALUE").setNamespace(n).setText(xls.get("Sales Tax ID Number (Sender)").toString())).
					addContent(new Element("INHERITED").setNamespace(n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM").setNamespace(n)
					.addContent(new Element("NAME").setNamespace(n).setText("TransactionAgency")).
					addContent(new Element("VALUE").setNamespace(n).setText(xls.get("Message Type").toString())).
					addContent(new Element("INHERITED").setNamespace(n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM").setNamespace(n)
					.addContent(new Element("NAME").setNamespace(n).setText("TransactionRelease")).
					addContent(new Element("VALUE").setNamespace(n).setText(xls.get("Sales Tax ID Number (Receiver)").toString())).
					addContent(new Element("INHERITED").setNamespace(n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM").setNamespace(n)
					.addContent(new Element("NAME").setNamespace(n).setText("TransactionVersion")).
					addContent(new Element("VALUE").setNamespace(n).setText(xls.get("Sub Suppliers").toString())).
					addContent(new Element("INHERITED").setNamespace(n).setNamespace(n).setText("false")));
		}
		
		int choice = 5 ;
		Util util = new Util();
		util.writeTofile(doc, choice, outputDirectory);
	}
	
	

}
