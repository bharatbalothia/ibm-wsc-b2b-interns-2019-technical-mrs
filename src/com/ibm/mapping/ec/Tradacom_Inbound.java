package com.ibm.mapping.ec;

import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;

public class Tradacom_Inbound {

	public Tradacom_Inbound() {
		
	}
	
	@SuppressWarnings("unused")
	public Element envelopeHeader(int flag , Document doc ,@SuppressWarnings("rawtypes") Map xls ,Namespace n){
		

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
			
			senderID = xls.get("Client ID").toString();
			recieverID = xls.get("TP Interchange ID (STX 02 01)").toString();
			envID = xls.get("Client Name").toString() + "_"+ xls.get("Client ID").toString() + "_" + xls.get("TP Interchange ID (STX 02 01)").toString() 
					+ "_I_"  + xls.get("Message Type") + "_" + "STX_TRADACOMS";
			
			type = "STX END";
			standard = "TRADACOMS";
			function = "1";
			
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
				Element NAME = new Element("NAME" ,n).setText(envID);
				Envelope.addContent(NAME);
				Element STANDARD = new Element("STANDARD" ,n).setText(standard);
				Envelope.addContent(STANDARD);
				Element CONTROL_NUMBER = new Element("CONTROL_NUMBER" ,n).setText("1");
				Envelope.addContent(CONTROL_NUMBER);
				Element FUNCTION = new Element("FUNCTION" ,n).setText(function);
				Envelope.addContent(FUNCTION);
				Element EXTENDS_ID = new Element("EXTENDS_ID"  ,n);
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
	
	public Tradacom_Inbound(Document doc , @SuppressWarnings("rawtypes") Map xls , Namespace n , String outputDirectory){
		
		Element si_Resources = doc.getRootElement();
		Element  doc_Envelope = new Element("DOC_ENEVELOPES" ,n);

		if(!doc.hasRootElement()){
			si_Resources.addContent(doc_Envelope);
		}
		
		int flag = 1;
		Element envelopeTradacom = envelopeHeader(flag , doc , xls ,n) ;
		doc.getRootElement().getChild("DOCUMENT_ENVELOPES" , n).addContent(envelopeTradacom);
			
		if(flag == 1 ){
			//Tradacom  envelope Parameters 
			
			Element ENVELOPE_PARMS_Trade = new Element("ENVELOPE_PARMS",n);
			envelopeTradacom.addContent(ENVELOPE_PARMS_Trade);
			
			ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM"  , n)
					.addContent(new Element("NAME" ,n).setText("BP")).
					addContent(new Element("VALUE" ,n).setText("BPN_TRADACOMSDeenvelopeUnifiedPostProcessor")).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME" ,n).setText("BPDataExtractMode")).
					addContent(new Element("VALUE" ,n).setText("UseBP")).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME" ,n).setText("ComplianceCheckMapName")).
					addContent(new Element("VALUE" ,n).setText(xls.get("Translation Map Name").toString())).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME" ,n).setText("ContractID")).
					addContent(new Element("VALUE" ,n)).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME" ,n).setText("ControlNumberAge")).
					addContent(new Element("VALUE" ,n)).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			
			ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME" ,n).setText("ExtractDirectory")).
					addContent(new Element("VALUE" ,n)).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			
			ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME" ,n).setText("ExtractFilename")).
					addContent(new Element("VALUE" ,n)).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME" ,n).setText("ExtractMailbox")).
					addContent(new Element("VALUE" ,n)).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME" ,n).setText("ExtractMailboxMessageName")).
					addContent(new Element("VALUE" ,n)).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME" ,n).setText("InvokeBPMode")).
					addContent(new Element("VALUE" ,n).setText("SpecifyBP")).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME" ,n).setText("KeepTranslatedDocument")).
					addContent(new Element("VALUE" ,n).setText("YES")).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME" ,n).setText("MessageType")).
					addContent(new Element("VALUE" ,n).setText(xls.get("Message Type").toString())).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME" ,n).setText("PerformComplianceCheck")).
					addContent(new Element("VALUE" ,n).setText("YES")).
					addContent(new Element("INHERITED" ,n).setText("false")));
			
			
		}
		
		int choice = 7 ;
		Util util = new Util();
		util.writeTofile(doc , choice , outputDirectory);	
	}

}
