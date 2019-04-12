package com.ibm.mapping.ec;

import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;

public class Tradacom_Outbound {

	

	
@SuppressWarnings({ "rawtypes", "unused" })
public Element envelopeHeader(int flag , Document doc ,Map xls , Namespace  n){
		

		String type =null;
		String standard = null;
		String envID = null;
		String senderID = null;
		String recieverID= null;
		String version = null;
		String function = null;
		Element Envelope = null;
		Envelope = new Element("ENVELOPE" ,n);

		if(flag == 0){
			
			standard = "TRADACOMS";
			senderID = xls.get("FROM Sender Code:").toString();
			recieverID = xls.get("UNTO Receiver Code:").toString();
			envID = xls.get("FROM Sender Code:").toString() + "_"+ xls.get("FROM Sender Code").toString() + "_" + xls.get("UNTO Receiver Code:").toString() 
					+ "_" + xls.get("UNTO Receiver Code") + "_" + "O_" + xls.get("APRF Application Reference").toString() +"_MHD_" + standard ;
			
			type = "MHD MTR";
			
			function = "0";
			
		}
		
		if(flag == 1 ){
			//Tradacom  stx 
			
			standard = "TRADACOMS";
			senderID = xls.get("FROM Sender Code").toString();
			recieverID = xls.get("UNTO Receiver Code").toString();
			envID = xls.get("FROM Sender Code:").toString() + "_"+ xls.get("FROM Sender Code").toString() + "_" + xls.get("UNTO Receiver Code:").toString() 
					+ "_" + xls.get("UNTO Receiver Code") + "_" + "O_" + xls.get("APRF Application Reference").toString() +"_STX_" + standard ;
			
			type = "STX END";
			
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
				Element NAME = new Element("NAME" ,n).setText(envID);
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
public Tradacom_Outbound(Document doc , Map xls , Namespace n , String outputDirectory ) {
	Element si_Resources = doc.getRootElement();
	Element  doc_Envelope = new Element("DOC_ENEVELOPES" ,n);

	
	if(!doc.hasRootElement()){
		
		si_Resources.addContent(doc_Envelope);
	}
	
	int flag = 0;
	Element envelopeTradacom = envelopeHeader(flag , doc , xls ,n);
	doc.getRootElement().getChild("DOCUMENT_ENVELOPES" ,n).addContent(envelopeTradacom);
		
	if(flag == 0 ){
		//Tradacom  envelope Parameters 
		
		Element ENVELOPE_PARMS_Trade = new Element("ENVELOPE_PARMS" ,n);
		envelopeTradacom.addContent(ENVELOPE_PARMS_Trade);
		
		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME",n).setText("AccepterLookupAlias" )).
				addContent(new Element("VALUE" ,n).setText(xls.get("Accepter Lookup Alias").toString())).
				addContent(new Element("INHERITED" ,n).setText("false")));
		
		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("BankID")).
				addContent(new Element("VALUE" ,n).setText(xls.get("Bank Identity Code").toString())).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("BankInformation")).
				addContent(new Element("VALUE" ,n).setText(xls.get("Bank Information").toString())).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("BankName")).
				addContent(new Element("VALUE" ,n).setText(xls.get("Bank Name").toString())).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("CurrencyIndicator")).
				addContent(new Element("VALUE" ,n).setText(xls.get("Currency Indicator").toString())).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("CustomerAddress1")).
				addContent(new Element("VALUE" ,n).setText(xls.get("CADD Customer's Address Line 1").toString())).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("CustomerAddress2")).
				addContent(new Element("VALUE" ,n).setText(xls.get("CADD Customer's Address Line 2").toString())).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("CustomerAddress3")).
				addContent(new Element("VALUE" ,n).setText(xls.get("CADD Customer's Address Line 3").toString())).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("CustomerAddress4")).
				addContent(new Element("VALUE" ,n).setText(xls.get("CADD Customer's Address Line 4").toString())).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("CustomerAlphaVatCode")).
				addContent(new Element("VALUE" ,n).setText(xls.get("VATR Customer's Alphanumeric VAT Registration Number").toString())).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("CustomerId")).
				addContent(new Element("VALUE" ,n).setText(xls.get("CIDN Customer's Identity Allocated by Supplier").toString())).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("CustomerLocationNumber")).
				addContent(new Element("VALUE" ,n).setText(xls.get("CIDN Customer's EAN Location Number").toString())).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("CustomerName")).
				addContent(new Element("VALUE" ,n)).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("CustomerNumericVatCode")).
				addContent(new Element("VALUE" ,n).setText(xls.get("VATR Customer's Numeric VAT Registration Number").toString())).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("CustomerPostCode")).
				addContent(new Element("VALUE" ,n).setText(xls.get("CADD Customer's Post Code").toString())).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("EnvelopeMapName")).
				addContent(new Element("VALUE" ,n).setText(xls.get("Enveloping map name").toString())).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("global_control_no")).
				addContent(new Element("VALUE" ,n).setText("NO")).
				addContent(new Element("INHERITED" , n).setText("false")));
		

		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("InvoiceSystemEANLocationNumber")).
				addContent(new Element("VALUE" ,n).setText(xls.get("INLO Invoice System EAN Location Number").toString())).
				addContent(new Element("INHERITED" , n).setText("false")));

		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("InvoiceSystemSuppliersOwnCode")).
				addContent(new Element("VALUE" ,n).setText(xls.get("INLO Invoice System Supplier's Own Code ").toString())).
				addContent(new Element("INHERITED" , n).setText("false")));

		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("LimitInterchangeSizeT")).
				addContent(new Element("VALUE" ,n).setText("NO")).
				addContent(new Element("INHERITED" , n).setText("false")));

		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("MapName")).
				addContent(new Element("VALUE" ,n).setText(xls.get("Translation Map Name").toString())).
				addContent(new Element("INHERITED" , n).setText("false")));

		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("MaxInterchangeSizeT")).
				addContent(new Element("VALUE" ,n)).
				addContent(new Element("INHERITED" , n).setText("false")));

		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("MessageType")).
				addContent(new Element("VALUE" ,n).setText(xls.get("APRF Application Reference").toString())).
				addContent(new Element("INHERITED" , n).setText("false")));

		String nextEnvelope = xls.get("FROM Sender Code:").toString() + "_"+ xls.get("FROM Sender Code").toString() + "_" + xls.get("UNTO Receiver Code:").toString() 
				+ "_" + xls.get("UNTO Receiver Code") + "_" + "O_" + xls.get("APRF Application Reference").toString() + "_STX_TRADACOMS" ;

		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("NextEnvelope")).
				addContent(new Element("VALUE" ,n).setText(nextEnvelope)).
				addContent(new Element("INHERITED" , n).setText("false")));

		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("ServiceCode")).
				addContent(new Element("VALUE" ,n).setText(xls.get("Service Code").toString())).
				addContent(new Element("INHERITED" , n).setText("false")));

		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("SupplierAddress1")).
				addContent(new Element("VALUE" ,n).setText(xls.get("SADD Supplier's Address Line 1").toString())).
				addContent(new Element("INHERITED" , n).setText("false")));



		
		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("SupplierAddress2")).
				addContent(new Element("VALUE" ,n).setText(xls.get("SADD Supplier's Address Line 2").toString())).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("SupplierAddress3")).
				addContent(new Element("VALUE" ,n).setText(xls.get("SADD Supplier's Address Line 3").toString())).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("SupplierAddress4")).
				addContent(new Element("VALUE" ,n).setText(xls.get("SADD Supplier's Address Line 4").toString())).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("SupplierAlphaVatCode")).
				addContent(new Element("VALUE" ,n).setText(xls.get("VATN Supplier's Alphanumeric VAT Registration Number").toString())).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("SupplierId")).
				addContent(new Element("VALUE" ,n).setText(xls.get("SIDN Supplier's Identity Allocated by Customer:").toString())).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("SupplierLocationNumber")).
				addContent(new Element("VALUE" ,n).setText(xls.get("SIDN Supplier's EAN Location Number").toString())).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("SupplierName")).
				addContent(new Element("VALUE" ,n).setText(xls.get("SNAM Supplier's Name:").toString())).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("SupplierNumericVatCode")).
				addContent(new Element("VALUE" ,n).setText(xls.get("VATN Supplier's Numeric VAT Registration Number").toString())).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("SupplierPostCode")).
				addContent(new Element("VALUE" ,n).setText(xls.get("SADD Supplier's Post Code").toString())).
						addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("TransactionCode")).
				addContent(new Element("VALUE" ,n).setText(xls.get("TCDE Transaction Code:").toString())).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("TransactionType")).
				addContent(new Element("VALUE" ,n).setText(xls.get("TTYP Transaction Type:").toString())).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("UseCorrelationOverrides")).
				addContent(new Element("VALUE" ,n).setText("All")).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("UserCode")).
				addContent(new Element("VALUE" ,n).setText("User Code")).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("ValidateInput")).
				addContent(new Element("VALUE" ,n).setText("YES")).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_Trade.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("ValidateOutput")).
				addContent(new Element("VALUE" ,n).setText("YES")).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		
	}
	
	flag = 1 ;
	Element envelopeTradacomSTX = envelopeHeader(flag , doc , xls ,n);
	doc.getRootElement().getChild("DOCUMENT_ENVELOPES" ,n).addContent(envelopeTradacomSTX);
		
	if(flag == 1 ){
		//Tradacom  envelope Parameters 
		
		Element ENVELOPE_PARMS_TradeSTX = new Element("ENVELOPE_PARMS" ,n);
		envelopeTradacomSTX.addContent(ENVELOPE_PARMS_TradeSTX);
		
		ENVELOPE_PARMS_TradeSTX.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("ApplicationReference")).
				addContent(new Element("VALUE" ,n).setText(xls.get("APRF Application Reference").toString())).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_TradeSTX.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("BP")).
				addContent(new Element("VALUE" ,n).setText("BPN_TRADACOMSEnvelopeUnifiedPostProcessor")).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_TradeSTX.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("BPDataExtractMode")).
				addContent(new Element("VALUE" ,n).setText("UseBP")).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_TradeSTX.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("ContractID")).
				addContent(new Element("VALUE" ,n)).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_TradeSTX.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("EncodeDocument")).
				addContent(new Element("VALUE" ,n).setText("YES")).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_TradeSTX.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("Encoding")).
				addContent(new Element("VALUE" ,n).setText("Cp1252")).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		
		ENVELOPE_PARMS_TradeSTX.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("EnvelopeMapName")).
				addContent(new Element("VALUE" ,n).setText(xls.get("EnvelopeMapName STXEND").toString())).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_TradeSTX.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("ExtractDirectory")).
				addContent(new Element("VALUE" ,n)).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_TradeSTX.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("ExtractFilename")).
				addContent(new Element("VALUE" ,n)).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_TradeSTX.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("ExtractMailbox")).
				addContent(new Element("VALUE" ,n)).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_TradeSTX.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("ExtractMailboxMessageName")).
				addContent(new Element("VALUE" ,n)).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_TradeSTX.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("global_control_no")).
				addContent(new Element("VALUE" ,n).setText("NO")).
				addContent(new Element("INHERITED" , n).setText("false")));
		

		ENVELOPE_PARMS_TradeSTX.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("InvokeBPForEachDoc")).
				addContent(new Element("VALUE" ,n).setText("NO")).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_TradeSTX.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("InvokeBPMode")).
				addContent(new Element("VALUE" ,n).setText("SpecifyBP")).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_TradeSTX.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("LimitInterchangeSizeI")).
				addContent(new Element("VALUE" ,n).setText("NO")).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_TradeSTX.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("MaxDocsPerInterchange")).
				addContent(new Element("VALUE" ,n)).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_TradeSTX.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("MaxInterchangeSizeI")).
				addContent(new Element("VALUE" ,n)).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_TradeSTX.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("ReceiverName")).
				addContent(new Element("VALUE" ,n).setText(xls.get("UNTO Receiver Name").toString())).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_TradeSTX.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("ReceiverReference")).
				addContent(new Element("VALUE" ,n).setText(xls.get("RCRF Receiver Reference").toString())).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_TradeSTX.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("SenderName")).
				addContent(new Element("VALUE" ,n).setText(xls.get("FROM Sender Name").toString())).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_TradeSTX.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("Streamed")).
				addContent(new Element("VALUE" ,n).setText("YES")).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		ENVELOPE_PARMS_TradeSTX.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("SyntaxRulesId")).
				addContent(new Element("VALUE" ,n).setText(xls.get("STDS Syntax Rules ID").toString())).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		
		
		ENVELOPE_PARMS_TradeSTX.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("SyntaxVersion")).
				addContent(new Element("VALUE" ,n).setText(xls.get("STDS Syntax Version").toString())).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		
		
		
		
		
		ENVELOPE_PARMS_TradeSTX.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("TransmissionPriority")).
				addContent(new Element("VALUE" ,n).setText(xls.get("PRCD Transmission Priority").toString())).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		
		
		ENVELOPE_PARMS_TradeSTX.addContent(new Element("ENVELOPE_PARM" ,n)
				.addContent(new Element("NAME" ,n).setText("UseCorrelationOverrides")).
				addContent(new Element("VALUE" ,n).setText("ALL")).
				addContent(new Element("INHERITED" , n).setText("false")));
		
		 
	 }
	
	doc.setBaseURI("charles");
	int choice = 8 ;
	Util util = new Util();
	 util.writeTofile(doc , choice , outputDirectory);
}
}
