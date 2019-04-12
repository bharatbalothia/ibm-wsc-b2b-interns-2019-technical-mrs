package com.ibm.mapping.ec;

import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;

public class VDA_Outbound {

	public VDA_Outbound() {
		// TODO Auto-generated constructor stub
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
		Envelope = new Element("ENVELOPE" ,n);

		if(flag == 1){
			envID = (String) xls.get("Envelope Name");
			senderID = (String) xls.get("Sender ID (or Supplier Number/Data Sender)");
			recieverID = (String) xls.get("Receiver ID (or Customer Number/Data Source Number)");	
			type = "VDA";
			standard = "VDA";
			function = "0";
			
		}
		
		//Envelope Header Details 
		Element ENVELOPE_ID = new Element("ENVELOPE_ID" ,n).setText(envID);
		Envelope.addContent(ENVELOPE_ID);
		Element ENVELOPE_EXTERNAL_ID = new Element("ENVELOPE_EXTERNAL_ID" ,n);
		Envelope.addContent(ENVELOPE_EXTERNAL_ID);
		Element ENVELOPE_VERSION = new Element("ENVELOPE_VERSION" ,n).setText("1");
		Envelope.addContent(ENVELOPE_VERSION);
		Element SIResourceDefaultVersion = new Element("SIResourceDefaultVersion" ,n).setText("true");
		Envelope.addContent(SIResourceDefaultVersion);
		Element TYPE = new Element("TYPE" ,n).setText(type);
		Envelope.addContent(TYPE);
		Element NAME = new Element("NAME"  ,n).setText(envID);
		Envelope.addContent(NAME);
		Element STANDARD = new Element("STANDARD" ,n).setText(standard);
		Envelope.addContent(STANDARD);
		Element CONTROL_NUMBER = new Element("CONTROL_NUMBER" ,n).setText("1");
		Envelope.addContent(CONTROL_NUMBER);
		Element FUNCTION = new Element("FUNCTION" ,n).setText(function);
		Envelope.addContent(FUNCTION);
		Element EXTENDS_ID = new Element("EXTENDS_ID" ,n);
		Envelope.addContent(EXTENDS_ID);
		Element EXTENDS_VERSION = new Element("EXTENDS_VERSION" ,n).setText("-1");
		Envelope.addContent(EXTENDS_VERSION);
		Element SENDER_ID = new Element("SENDER_ID" ,n).setText(senderID);
		Envelope.addContent(SENDER_ID);
		Element RECEIVER_ID = new Element("RECEIVER_ID" ,n).setText(recieverID);
		Envelope.addContent(RECEIVER_ID);
		Element COMMENTS = new Element("COMMENTS" ,n).setText(envID);
		Envelope.addContent(COMMENTS);
		Element USERNAME = new Element("USERNAME" ,n).setText("admin");
		Envelope.addContent(USERNAME);
		Element CREATE_DATE = new Element("CREATE_DATE" ,n);
		Envelope.addContent(CREATE_DATE);
		Element USES_GLOBAL_CONTROL_NUMBER = new Element("USES_GLOBAL_CONTROL_NUMBER" ,n).setText("0");
		Envelope.addContent(USES_GLOBAL_CONTROL_NUMBER);

		return Envelope;

	}
	
	@SuppressWarnings("rawtypes")
	public VDA_Outbound(Document doc , Map xls , Namespace n , String outputDirectory) {

		Element si_Resources = doc.getRootElement();
		Element  doc_Envelope = new Element("DOCUMENT_ENEVELOPES" ,n);

		if(!doc.hasRootElement()){
			si_Resources.addContent(doc_Envelope);
		}
		int flag = 1;
		
		Element envelopeVDA = envelopeHeader(flag , doc , xls ,n);
		doc.getRootElement().getChild("DOCUMENT_ENVELOPES" ,n).addContent(envelopeVDA);
		
		if(flag == 1 ){
			Element ENVELOPE_PARMS_VDA = new Element("ENVELOPE_PARMS" ,n);
			envelopeVDA.addContent(ENVELOPE_PARMS_VDA);

			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM" ,n)
					.addContent(new Element("NAME" ,n).setText("AccepterLookupAlias")).
					addContent(new Element("VALUE" ,n).setText(xls.get("Accepter Lookup Alias").toString())).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM"  ,n)
					.addContent(new Element("NAME"  ,n).setText("BP")).
					addContent(new Element("VALUE" ,n).setText(xls.get("Business Process List").toString())).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM"  ,n)
					.addContent(new Element("NAME"  ,n).setText("BPDataExtractMode")).
					addContent(new Element("VALUE" ,n).setText("UseBP")).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM"  ,n)
					.addContent(new Element("NAME" ,n).setText("CarrierNumber")).
					addContent(new Element("VALUE" ,n).setText(xls.get("Carrier Number").toString())).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM"  ,n)
					.addContent(new Element("NAME" ,n).setText("CompRegNumber")).
					addContent(new Element("VALUE" ,n).setText(xls.get("Comp. Reg. Number").toString())).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM"  ,n)
					.addContent(new Element("NAME" ,n).setText("ContractID")).
					addContent(new Element("VALUE" ,n)).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM"  ,n)
					.addContent(new Element("NAME" ,n).setText("EncodeDocument")).
					addContent(new Element("VALUE" ,n).setText(xls.get("Encode Document (YES/NO)").toString())).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM"  ,n)
					.addContent(new Element("NAME" ,n).setText("Encoding")).
					addContent(new Element("VALUE" ,n).setText(xls.get("Encoding").toString())).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM"  ,n)
					.addContent(new Element("NAME" ,n).setText("EnvelopeMapName")).
					addContent(new Element("VALUE" ,n).setText("BldIvda")).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM"  ,n)
					.addContent(new Element("NAME" ,n).setText("ExtractDirectory")).
					addContent(new Element("VALUE" ,n)).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM"  ,n)
					.addContent(new Element("NAME" ,n).setText("ExtractFilename")).
					addContent(new Element("VALUE" ,n)).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM"  ,n)
					.addContent(new Element("NAME" ,n).setText("ExtractMailbox")).
					addContent(new Element("VALUE" ,n)).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM"  ,n)
					.addContent(new Element("NAME" ,n).setText("ExtractMailboxMessageName")).
					addContent(new Element("VALUE" ,n)).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM"  ,n)
					.addContent(new Element("NAME" ,n).setText("global_control_no")).
					addContent(new Element("VALUE" ,n).setText("NO")).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM"  ,n)
					.addContent(new Element("NAME" ,n).setText("InvokeBPForEachDoc")).
					addContent(new Element("VALUE" ,n).setText("NO")).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM"  ,n)
					.addContent(new Element("NAME" ,n).setText("InvokeBPMode")).
					addContent(new Element("VALUE" ,n).setText("SpecifyBP")).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM"  ,n)
					.addContent(new Element("NAME" ,n).setText("LimitInterchangeSizeI")).
					addContent(new Element("VALUE" ,n).setText("NO")).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM"  ,n)
					.addContent(new Element("NAME" ,n).setText("MapName")).
					addContent(new Element("VALUE" ,n).setText(xls.get(" Translation Map Name").toString())).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM"  ,n)
					.addContent(new Element("NAME" ,n).setText("MaxDocsPerInterchange")).
					addContent(new Element("VALUE" ,n)).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM"  ,n)
					.addContent(new Element("NAME" ,n).setText("MaxInterchangeSizeI")).
					addContent(new Element("VALUE" ,n)).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM"  ,n)
					.addContent(new Element("NAME" ,n).setText("PaymentDate")).
					addContent(new Element("VALUE" ,n).setText(xls.get("Payment Date (YYMMDD)").toString())).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM"  ,n)
					.addContent(new Element("NAME" ,n).setText("PaymentIndicationNumber")).
					addContent(new Element("VALUE" ,n).setText(xls.get("Payment Indication Number").toString())).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM"  ,n)
					.addContent(new Element("NAME" ,n).setText("SalesTaxIDNumberReceiver")).
					addContent(new Element("VALUE" ,n).setText(xls.get("Sales Tax ID Number (Receiver)").toString())).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM"  ,n)
					.addContent(new Element("NAME" ,n).setText("SalesTaxIDNumberSender")).
					addContent(new Element("VALUE" ,n).setText(xls.get("Sales Tax ID Number (Sender)").toString())).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM"  ,n)
					.addContent(new Element("NAME" ,n).setText("SubSuppliers")).
					addContent(new Element("VALUE" ,n).setText(xls.get("Sub Suppliers").toString())).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM"  ,n)
					.addContent(new Element("NAME" ,n).setText("TransactionType")).
					addContent(new Element("VALUE" ,n).setText(xls.get("Message Type").toString())).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM", n)
					.addContent(new Element("NAME" ,n).setText("TransmissionPurposeCode")).
					addContent(new Element("VALUE" ,n).setText(xls.get("Transmission Purpose Code").toString())).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM" ,n)
					.addContent(new Element("NAME" ,n).setText("UseCorrelationOverrides")).
					addContent(new Element("VALUE" ,n).setText("NO")).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME" ,n).setText("ValidateInput")).
					addContent(new Element("VALUE" ,n).setText("YES")).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM"  ,n)
					.addContent(new Element("NAME" ,n).setText("ValidateOutput")).
					addContent(new Element("VALUE" ,n).setText("YES")).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_VDA.addContent(new Element("ENVELOPE_PARM"  ,n)
					.addContent(new Element("NAME" ,n).setText("WarehouseKeeperCode")).
					addContent(new Element("VALUE" ,n).setText(xls.get("Warehouse Keeper Code").toString())).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			
		}
		
		int choice = 6 ;
		Util util = new Util();
		util.writeTofile(doc , choice , outputDirectory);
	}
	
	}


