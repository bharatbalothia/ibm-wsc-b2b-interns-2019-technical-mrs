package com.ibm.mapping.ec;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class X12_Outbound {
	@SuppressWarnings({ "unused", "rawtypes" })
	public Element envelopeHeader(int flag , Document doc ,Map xls ,Namespace n1){

		String type =null;
		String standard = null;
		String envID = null;
		String senderID = null;
		String recieverID= null;
		String version = null;
		String function = null;
		Element Envelope = null;
		Envelope = new Element("ENVELOPE" ,n1);

		String nextEnvelope = null;
		
		//ISA Header
		if(flag == 0 ){

			//#Client_Name:3 + "_" + #Client_Qualifier:3 + "_" + #Client_Interchange_ID:3 + "_" + #TP_Qualifier:3 + "_" + #TP_Interchange_ID:3 + "_" + #TP_Element_Delimiter:3 + "_" + #
			//TP_Sub_Element_Delimiter:3 + "_" + #TP_Segment_Terminator:3 + "_" + "O" + "_" + #Test_Indicator__T_P_:3 + "_" + #Transaction_ID:3 + "_" + #Interchange_Version__ISA_12_:3 + "_ISA";

			senderID = xls.get("Client Interchange ID").toString();

			recieverID =  xls.get("TP Interchange ID").toString();
			
			envID = xls.get("Client Name").toString() +"_" + xls.get("Client Qualifier").toString() +"_" +xls.get("Client Interchange ID").toString()+"_"+
					xls.get("TP Qualifier").toString() + "_" +
					xls.get("TP Interchange ID").toString()+ "_"+xls.get("TP Element Delimiter").toString() +"_"+
					xls.get("TP Sub Element Delimiter").toString() + "_"+ xls.get("TP Segment Terminator").toString() +
					"_" +"O_" + xls.get("Test Indicator (T/P)").toString() +"_" + xls.get("Transaction ID").toString()+  "_"+
					 xls.get("Interchange Version (ISA 12)").toString()+"_ISA";

			type = "ISA IEA";
			standard = "X12";
			function = "0";

		}
		//GS header
		if(flag == 1 ){

			// #Client_Name:3 + "_" + #Client_Group_ID:3 + "_" + #TP_Group_ID:3 + "_" + "O" + "_" + #Test_Indicator__T_P_:3 + "_" + #Transaction_ID:3 + "_" + #Group_Version__GS08_:3 + "_
			//  GS";
			senderID = xls.get("Client Interchange ID").toString();

			recieverID =  xls.get("TP Group ID").toString();
			
			
			envID = xls.get("Client Name").toString() +"_" + xls.get("Client Group ID").toString() +"_" +xls.get("TP Group ID").toString()+"_O_"+
					 xls.get("Test Indicator (T/P)").toString() +"_" + xls.get("Transaction ID").toString()+  "_"+
					xls.get("Group Version (GS08)").toString()+"_GS";

			type = "GS GE";
			standard = "X12";
			function = "0";
		}
		//ST header 
		if(flag == 2 ){

			// #Client_Name:3 + "_" + #Client_Trans_ID:2 + "_" + #TP_Trans_ID:2 + "_" + "O" + "_" + #Test_Indicator__T_P_:3 + "_" + #Accepter_Lookup_Alias:3 + "_ST";  
			//senderID = xls.get("Client Interchange ID").toString();
			
			//recieverID =  xls.get("TP Group ID").toString();
			String s1 = xls.get("TP Interchange ID").toString();
			String s2 = "G" + s1.substring(s1.length() - 5 ) + xls.get("Transaction ID").toString() + "OB";
			senderID = s2;
			recieverID = s1.substring(s1.length() - 5 ) + xls.get("Transaction ID").toString() + "OB";
			//System.out.println(s2);
			
			envID =  xls.get("Client Name").toString() +"_" + s2 +"_" + s2 + "_O_"  + xls.get("Test Indicator (T/P)").toString() + "_"
					+ xls.get("Transaction ID").toString() + "_ST"; ;
					 

			type = "ST SE";
			standard = "X12";
			function = "0";
		}
		if(xls.get("Generate 997 Envelopes(YES/NO)").toString().equals("YES")){
			if(flag == 3 ){

				// #Client_Name:3 + "_" + #Client_Trans_ID:2 + "_" + #TP_Trans_ID:2 + "_" + "O" + "_" + #Test_Indicator__T_P_:3 + "_" + #Accepter_Lookup_Alias:3 + "_ST";  
				//senderID = xls.get("Client Interchange ID").toString();
				
				//recieverID =  xls.get("TP Group ID").toString();
				
				senderID = xls.get("TP Interchange ID").toString();
				recieverID = xls.get("Client Interchange ID").toString();
				
				
				envID =  xls.get("Client Name").toString() +"_" + xls.get("Client Interchange ID").toString() +"_" + xls.get("TP Interchange ID").toString() + "_I_"  + xls.get("Test Indicator (T/P)").toString() + "_"
						+ xls.get("Interchange Version (ISA 12)").toString() + "_ISA"; ;
						 

				type = "ISA IEA";
				standard = "X12";
				function = "1";
			}//997 GS
			if(flag == 4){
				senderID = xls.get("TP Group ID").toString();
				recieverID =  xls.get("Client Interchange ID").toString();
				envID = xls.get("Client Name").toString() +"_" + xls.get("Client Interchange ID").toString() +"_" +
						xls.get("TP Group ID").toString() +"_I_" +"997_" +xls.get("Group Version (GS08)").toString()
						+"_GS";
				type = "GS GE";
				standard  = "X12";
				function = "1";
			}
			if(flag == 5 ){
				senderID = xls.get("TP Group ID").toString();
				recieverID =  xls.get("Client Interchange ID").toString();
				envID = xls.get("Client Name").toString() +"_" + xls.get("Client Interchange ID").toString() +"_" +
						xls.get("TP Group ID").toString() +"_I_" +"997_" +xls.get("Group Version (GS08)").toString()
						+"_ST";
				type = "ST SE";
				standard  = "X12";
				function = "1";
			}
		}
		

		//Envelope Header Details
		Element ENVELOPE_ID = new Element("ENVELOPE_ID",n1).setText(envID);
		Envelope.addContent(ENVELOPE_ID);
		Element ENVELOPE_EXTERNAL_ID = new Element("ENVELOPE_EXTERNAL_ID" ,n1);
		Envelope.addContent(ENVELOPE_EXTERNAL_ID);
		Element ENVELOPE_VERSION = new Element("ENVELOPE_VERSION" ,n1).setText("1");
		Envelope.addContent(ENVELOPE_VERSION);
		Element SIResourceDefaultVersion = new Element("SIResourceDefaultVersion",n1).setText("true");
		Envelope.addContent(SIResourceDefaultVersion);
		Element TYPE = new Element("TYPE" ,n1).setText(type);
		Envelope.addContent(TYPE);
		Element NAME = new Element("NAME").setNamespace(n1).setText(envID);
		Envelope.addContent(NAME);
		Element STANDARD = new Element("STANDARD" ,n1).setText(standard);
		Envelope.addContent(STANDARD);
		Element CONTROL_NUMBER = new Element("CONTROL_NUMBER" ,n1).setText("1");
		Envelope.addContent(CONTROL_NUMBER);
		Element FUNCTION = new Element("FUNCTION" ,n1).setText(function);
		Envelope.addContent(FUNCTION);
		Element EXTENDS_ID = new Element("EXTENDS_ID" ,n1);
		Envelope.addContent(EXTENDS_ID);
		Element EXTENDS_VERSION = new Element("EXTENDS_VERSION",n1).setText("-1");
		Envelope.addContent(EXTENDS_VERSION);
		Element SENDER_ID = new Element("SENDER_ID" ,n1).setText(senderID);
		Envelope.addContent(SENDER_ID);
		Element RECEIVER_ID = new Element("RECEIVER_ID" ,n1).setText(recieverID);
		Envelope.addContent(RECEIVER_ID);
		Element COMMENTS = new Element("COMMENTS" ,n1).setText(envID);
		Envelope.addContent(COMMENTS);
		Element USERNAME = new Element("USERNAME" ,n1).setText("admin");
		Envelope.addContent(USERNAME);
		Element CREATE_DATE = new Element("CREATE_DATE" ,n1);
		Envelope.addContent(CREATE_DATE);
		Element USES_GLOBAL_CONTROL_NUMBER = new Element("USES_GLOBAL_CONTROL_NUMBER" ,n1).setText("0");
		Envelope.addContent(USES_GLOBAL_CONTROL_NUMBER);

		return Envelope;

	}


	public X12_Outbound(Document doc , @SuppressWarnings("rawtypes") Map xls,Namespace n1,String outputDirectory) {
		int flag = 0;

		/*Element si_Resources = doc.getRootElement();
		Element  doc_Envelope = new Element("DOC_ENEVELOPES", n1);

		if(!doc.hasRootElement()){
			si_Resources.addContent(doc_Envelope);
		}*/

		Element envelopeISA = envelopeHeader(flag, doc , xls ,n1);
		doc.getRootElement().getChild("DOCUMENT_ENVELOPES", n1).addContent(envelopeISA);

		if(flag == 0 ){
			//ISA envelope Parameters 
			Element ENVELOPE_PARMS = new Element("ENVELOPE_PARMS", n1);
			envelopeISA.addContent(ENVELOPE_PARMS);

			ENVELOPE_PARMS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("AccepterLookupAlias")).
					addContent(new Element("VALUE").setNamespace(n1).setText("TA1")).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));


			ENVELOPE_PARMS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("AcknowledgementOverdueTime")).
					addContent(new Element("VALUE").setNamespace(n1).setText(xls.get("Acknowledgement Overdue Time (HR)").toString())).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));


			ENVELOPE_PARMS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("AcknowledgementOverdueTimeMinutes")).
					addContent(new Element("VALUE").setNamespace(n1).setText(xls.get("Acknowledgement Overdue Time (MIN)").toString())).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));


			ENVELOPE_PARMS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("BP")).
					addContent(new Element("VALUE").setNamespace(n1)).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));


			ENVELOPE_PARMS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("BPDataExtractMode")).
					addContent(new Element("VALUE").setNamespace(n1)).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));

			ENVELOPE_PARMS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("ContractID")).
					addContent(new Element("VALUE").setNamespace(n1)).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));

			ENVELOPE_PARMS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("ElementSeparator")).
					addContent(new Element("VALUE").setNamespace(n1).setText("0x" + xls.get("TP Element Delimiter").toString())).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));

			ENVELOPE_PARMS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("EncodeDocument")).
					addContent(new Element("VALUE").setNamespace(n1).setText(xls.get("Encode DOC (YES/NO)").toString())).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));

			ENVELOPE_PARMS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("Encoding")).
					addContent(new Element("VALUE").setNamespace(n1)).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));

			ENVELOPE_PARMS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("global_control_no")).
					addContent(new Element("VALUE").setNamespace(n1).setText("NO")).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));

			ENVELOPE_PARMS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("InterchangeAcknowledgmentRequested")).
					addContent(new Element("VALUE").setNamespace(n1).setText("0")).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));


			ENVELOPE_PARMS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("InterchangeAuthorizationInformation")).
					addContent(new Element("VALUE").setNamespace(n1).setText(xls.get("Interchange_Authorization_Information (optional)").toString())).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));

			ENVELOPE_PARMS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("InterchangeAuthorizationInformationQualifier")).
					addContent(new Element("VALUE").setNamespace(n1).setText(xls.get("Interchange_Authorization_Qual").toString())).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));

			ENVELOPE_PARMS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("InterchangeControlStandardsIdentifier")).
					addContent(new Element("VALUE").setNamespace(n1).setText(xls.get("Interchange Standards Agency (ISA11)").toString())).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));

		

			ENVELOPE_PARMS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("InterchangeControlVersionNumber")).
					addContent(new Element("VALUE").setNamespace(n1).setText(xls.get("Interchange Version (ISA 12)").toString())).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));

			ENVELOPE_PARMS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("InterchangeReceiverIDQualifier")).
					addContent(new Element("VALUE").setNamespace(n1).setText(xls.get("TP Qualifier").toString())).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));

			ENVELOPE_PARMS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("InterchangeSecurityInformation")).
					addContent(new Element("VALUE").setNamespace(n1).setText(xls.get("Interchange_Security_Information (optional)").toString())).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));

			ENVELOPE_PARMS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("InterchangeSecurityInformationQualifier")).
					addContent(new Element("VALUE").setNamespace(n1).setText(xls.get("Interchange_Security_Qual").toString())).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));

			ENVELOPE_PARMS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("InterchangeSenderIDQualifier")).
					addContent(new Element("VALUE").setNamespace(n1).setText(xls.get("Client Qualifier").toString())).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));

			ENVELOPE_PARMS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("InterchangeTestIndicator")).
					addContent(new Element("VALUE").setNamespace(n1).setText(xls.get("Test Indicator (T/P)").toString())).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));

			ENVELOPE_PARMS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("InvokeBPMode")).
					addContent(new Element("VALUE").setNamespace(n1).setText("SpecifyBP")).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));

			ENVELOPE_PARMS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("ReciprocateAuthAndSecurityMode")).
					addContent(new Element("VALUE").setNamespace(n1).setText("1")).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));

			ENVELOPE_PARMS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("ReleaseCharacter")).
					addContent(new Element("VALUE").setNamespace(n1)).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));

			ENVELOPE_PARMS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("SegmentTerminator")).
					addContent(new Element("VALUE").setNamespace(n1).setText("0x"+ xls.get("TP Segment Terminator").toString())).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));

			ENVELOPE_PARMS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("Streamed")).
					addContent(new Element("VALUE").setNamespace(n1).setText("YES")).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));

			ENVELOPE_PARMS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("SubElementSeparator")).
					addContent(new Element("VALUE").setNamespace(n1).setText("0x"+xls.get("TP Sub Element Delimiter").toString())).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
			
			
			
		}
		
		
		flag = 1 ;
		if(flag==1){
			Element envelopeGS = envelopeHeader(flag, doc , xls ,n1);
			doc.getRootElement().getChild("DOCUMENT_ENVELOPES", n1).addContent(envelopeGS);

			Element ENVELOPE_PARMS_GS = new Element("ENVELOPE_PARMS" ,n1);
			envelopeGS.addContent(ENVELOPE_PARMS_GS);
			
			
			
			ENVELOPE_PARMS_GS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("AcknowledgementOverdueTime")).
					addContent(new Element("VALUE").setNamespace(n1).setText(xls.get("Acknowledgement Overdue Time (HR)").toString())).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
			
			ENVELOPE_PARMS_GS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("AcknowledgementOverdueTimeMinutes")).
					addContent(new Element("VALUE").setNamespace(n1).setText(xls.get("Acknowledgement Overdue Time (MIN)").toString())).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
			
			ENVELOPE_PARMS_GS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("ExpectAcknowledgement")).
					addContent(new Element("VALUE").setNamespace(n1).setText(xls.get("Expect 997 (YES/NO)").toString())).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));

	

			ENVELOPE_PARMS_GS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("global_control_no")).
					addContent(new Element("VALUE").setNamespace(n1).setText("NO")).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));

			ENVELOPE_PARMS_GS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("GroupResponsibleAgencyCode")).
					addContent(new Element("VALUE").setNamespace(n1).setText(xls.get("Standards Board (ANSI)").toString())).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));

			ENVELOPE_PARMS_GS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("GroupVersionReleaseIDCode")).
					addContent(new Element("VALUE").setNamespace(n1).setText(xls.get("Group Version (GS08)").toString())).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));

			ENVELOPE_PARMS_GS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("GroupFunctionalIDCode")).
					addContent(new Element("VALUE").setNamespace(n1).setText(xls.get("Functional ID Code (GS01)").toString())).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
			
			String nextEnvelope = xls.get("Client Name").toString() +"_" + xls.get("Client Qualifier").toString() +"_" +xls.get("Client Interchange ID").toString()+"_"+
					xls.get("TP Qualifier").toString() + "_" +
					xls.get("TP Interchange ID").toString()+ "_"+xls.get("TP Element Delimiter").toString() +"_"+
					xls.get("TP Sub Element Delimiter").toString() + "_"+ xls.get("TP Segment Terminator").toString() +
					"_" +"O_" + xls.get("Test Indicator (T/P)").toString() +"_" + xls.get("Transaction ID").toString()+  "_"+
					xls.get("Interchange Version (ISA 12)").toString()+"_ISA";
			
			ENVELOPE_PARMS_GS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("NextEnvelope")).
					addContent(new Element("VALUE").setNamespace(n1).setText(nextEnvelope)).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));		
		}
		flag = 2 ;
		if(flag == 2 ){
			//ST Parameter
			Element envelopeST = envelopeHeader(flag, doc , xls ,n1);
			doc.getRootElement().getChild("DOCUMENT_ENVELOPES" ,n1).addContent(envelopeST);

			Element ENVELOPE_PARMS_ST = new Element("ENVELOPE_PARMS" ,n1);
			envelopeST.addContent(ENVELOPE_PARMS_ST);

			ENVELOPE_PARMS_ST.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("AccepterLookupAlias")).
					addContent(new Element("VALUE").setNamespace(n1).setText(xls.get("Transaction ID").toString())).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
		
			ENVELOPE_PARMS_ST.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("global_control_no")).
					addContent(new Element("VALUE").setNamespace(n1).setText("NO")).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
			ENVELOPE_PARMS_ST.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("MapNameMode")).
					addContent(new Element("VALUE").setNamespace(n1).setText("Specify")).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
		
		
			ENVELOPE_PARMS_ST.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("MapName")).
					addContent(new Element("VALUE").setNamespace(n1).setText(xls.get("Translation Map Name").toString())).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
			

			String nextEnvelope = xls.get("Client Name").toString() +"_" + xls.get("Client Group ID").toString() +"_" +xls.get("TP Group ID").toString()+"_O_"+
					 xls.get("Test Indicator (T/P)").toString() +"_" + xls.get("Transaction ID").toString()+  "_"+
					xls.get("Group Version (GS08)").toString()+"_GS";
			
			ENVELOPE_PARMS_ST.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("NextEnvelope")).
					addContent(new Element("VALUE").setNamespace(n1).setText(nextEnvelope)).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
			
			ENVELOPE_PARMS_ST.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("PerformHIPAACompCheck")).
					addContent(new Element("VALUE").setNamespace(n1)).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
			
			ENVELOPE_PARMS_ST.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("TransactionReceiverIDQualifier")).
					addContent(new Element("VALUE").setNamespace(n1).setText(xls.get("TP Qualifier").toString())).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
			
			ENVELOPE_PARMS_ST.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("TransactionSenderIDQualifier")).
					addContent(new Element("VALUE").setNamespace(n1).setText(xls.get("Client Qualifier").toString())).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
			
			ENVELOPE_PARMS_ST.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("TransactionSetIDCode")).
					addContent(new Element("VALUE").setNamespace(n1).setText(xls.get("Transaction ID").toString())).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
			
			ENVELOPE_PARMS_ST.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("ValidateInput")).
					addContent(new Element("VALUE").setNamespace(n1)).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
			
			ENVELOPE_PARMS_ST.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
					.addContent(new Element("NAME").setNamespace(n1).setText("ValidateOutput")).
					addContent(new Element("VALUE").setNamespace(n1)).
					addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
			
			
		}
		if(xls.get("Generate 997 Envelopes(YES/NO)").toString().equals("YES")){
			flag =3 ;
			if(flag == 3){
				
				Element envelope997ISA = envelopeHeader(flag, doc , xls ,n1);
				doc.getRootElement().getChild("DOCUMENT_ENVELOPES" ,n1).addContent(envelope997ISA);

				Element ENVELOPE_PARMS_997ISA = new Element("ENVELOPE_PARMS" ,n1);
				envelope997ISA.addContent(ENVELOPE_PARMS_997ISA);

				ENVELOPE_PARMS_997ISA.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("AcceptNonCompliantInterchanges")).
						addContent(new Element("VALUE").setNamespace(n1).setText("NO")).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				ENVELOPE_PARMS_997ISA.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("ControlNumberAge")).
						addContent(new Element("VALUE").setNamespace(n1)).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				ENVELOPE_PARMS_997ISA.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("ErrorBP")).
						addContent(new Element("VALUE").setNamespace(n1).setText(xls.get("Bootstrapped 997 BP").toString())).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				ENVELOPE_PARMS_997ISA.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("GenerateAcknowledgement")).
						addContent(new Element("VALUE").setNamespace(n1).setText("NEVER")).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				
				ENVELOPE_PARMS_997ISA.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("global_control_no")).
						addContent(new Element("VALUE").setNamespace(n1).setText("NO")).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				ENVELOPE_PARMS_997ISA.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("InterchangeControlVersionNumber")).
						addContent(new Element("VALUE").setNamespace(n1).setText(xls.get("Interchange Version (ISA 12)").toString())).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				ENVELOPE_PARMS_997ISA.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("InterchangeReceiverIDQualifier")).
						addContent(new Element("VALUE").setNamespace(n1)).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				ENVELOPE_PARMS_997ISA.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("InterchangeSenderIDQualifier")).
						addContent(new Element("VALUE").setNamespace(n1)).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
			
				
				ENVELOPE_PARMS_997ISA.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("InterchangeTestIndicator")).
						addContent(new Element("VALUE").setNamespace(n1).setText(xls.get("Test Indicator (T/P)").toString())).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				ENVELOPE_PARMS_997ISA.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("OutDocEncoding")).
						addContent(new Element("VALUE").setNamespace(n1)).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				ENVELOPE_PARMS_997ISA.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("PerformControlNumberSequenceCheck")).
						addContent(new Element("VALUE").setNamespace(n1).setText("NO")).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				ENVELOPE_PARMS_997ISA.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("PerformDuplicateControlNumberCheck")).
						addContent(new Element("VALUE").setNamespace(n1).setText("NO")).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				ENVELOPE_PARMS_997ISA.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("RetainEnvelope")).
						addContent(new Element("VALUE").setNamespace(n1).setText("YES")).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				ENVELOPE_PARMS_997ISA.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("SpecifyOutDocEncoding")).
						addContent(new Element("VALUE").setNamespace(n1).setText("NO")).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				ENVELOPE_PARMS_997ISA.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("TA1ALAFormat")).
						addContent(new Element("VALUE").setNamespace(n1).setText("1")).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
			}
			//997 GS Parameters
			flag = 4;
			if(flag == 4){
				Element envelope997GS = envelopeHeader(flag, doc , xls ,n1);
				doc.getRootElement().getChild("DOCUMENT_ENVELOPES" ,n1).addContent(envelope997GS);

				Element ENVELOPE_PARMS_997GS = new Element("ENVELOPE_PARMS" ,n1);
				envelope997GS.addContent(ENVELOPE_PARMS_997GS);

				ENVELOPE_PARMS_997GS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("997ALAFormat")).
						addContent(new Element("VALUE").setNamespace(n1)).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				ENVELOPE_PARMS_997GS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("AcceptNonCompliantGroups")).
						addContent(new Element("VALUE").setNamespace(n1).setText("NO")).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				ENVELOPE_PARMS_997GS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("ControlNumberAge")).
						addContent(new Element("VALUE").setNamespace(n1)).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));

				ENVELOPE_PARMS_997GS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("ErrorBP")).
						addContent(new Element("VALUE").setNamespace(n1).setText(xls.get("Bootstrapped 997 BP").toString())).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));

				ENVELOPE_PARMS_997GS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("GenerateAcknowledgement")).
						addContent(new Element("VALUE").setNamespace(n1).setText("NO")).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));

				ENVELOPE_PARMS_997GS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("global_control_no")).
						addContent(new Element("VALUE").setNamespace(n1).setText("NO")).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));

				ENVELOPE_PARMS_997GS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("GroupFunctionalIDCode")).
						addContent(new Element("VALUE").setNamespace(n1).setText("FA")).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				ENVELOPE_PARMS_997GS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("GroupResponsibleAgencyCode")).
						addContent(new Element("VALUE").setNamespace(n1).setText(xls.get("Standards Board (ANSI)").toString())).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				ENVELOPE_PARMS_997GS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("GroupVersionReleaseIDCode")).
						addContent(new Element("VALUE").setNamespace(n1).setText(xls.get("Group Version (GS08)").toString())).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				ENVELOPE_PARMS_997GS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("PerformControlNumberSequenceCheck")).
						addContent(new Element("VALUE").setNamespace(n1).setText("NO")).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				ENVELOPE_PARMS_997GS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("PerformDuplicateControlNumberCheck")).
						addContent(new Element("VALUE").setNamespace(n1).setText("NO")).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				ENVELOPE_PARMS_997GS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("RetainEnvelope")).
						addContent(new Element("VALUE").setNamespace(n1).setText("YES")).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				ENVELOPE_PARMS_997GS.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("SendAckImmediately")).
						addContent(new Element("VALUE").setNamespace(n1).setText("NO")).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				

			}
			flag = 5 ;
			if(flag == 5 ){
				Element envelope997ST = envelopeHeader(flag, doc , xls ,n1);
				doc.getRootElement().getChild("DOCUMENT_ENVELOPES", n1).addContent(envelope997ST);

				Element ENVELOPE_PARMS_997ST = new Element("ENVELOPE_PARMS" ,n1);
				envelope997ST.addContent(ENVELOPE_PARMS_997ST);
				
				ENVELOPE_PARMS_997ST.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("BatchLikeTransactions")).
						addContent(new Element("VALUE").setNamespace(n1).setText("NO")).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				ENVELOPE_PARMS_997ST.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("BP")).
						addContent(new Element("VALUE").setNamespace(n1).setText(xls.get("Bootstrapped 997 BP").toString())).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				ENVELOPE_PARMS_997ST.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("BPDataExtractMode")).
						addContent(new Element("VALUE").setNamespace(n1).setText("UseBP")).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				ENVELOPE_PARMS_997ST.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("BPInvokeOrSetInPD")).
						addContent(new Element("VALUE").setNamespace(n1).setText("Invoke")).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				ENVELOPE_PARMS_997ST.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("ControlNumberAge")).
						addContent(new Element("VALUE").setNamespace(n1)).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				ENVELOPE_PARMS_997ST.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("ComplianceCheckMapName")).
						addContent(new Element("VALUE").setNamespace(n1).setText("BPN_997_TO_ACKXML")).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				ENVELOPE_PARMS_997ST.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("ErrorBP")).
						addContent(new Element("VALUE").setNamespace(n1).setText(xls.get("Bootstrapped 997 BP").toString())).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				ENVELOPE_PARMS_997ST.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("ErrorBPNameMode")).
						addContent(new Element("VALUE").setNamespace(n1).setText("Specify")).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				ENVELOPE_PARMS_997ST.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("AcceptNonCompliantTransactions")).
						addContent(new Element("VALUE").setNamespace(n1).setText("NO")).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				ENVELOPE_PARMS_997ST.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("global_control_no")).
						addContent(new Element("VALUE").setNamespace(n1).setText("NO")).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				ENVELOPE_PARMS_997ST.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("GroupVersionReleaseIDCode")).
						addContent(new Element("VALUE").setNamespace(n1).setText(xls.get("Group Version (GS08)").toString())).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				ENVELOPE_PARMS_997ST.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("InterchangeTestIndicator")).
						addContent(new Element("VALUE").setNamespace(n1).setText(xls.get("Test Indicator (T/P)").toString())).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				ENVELOPE_PARMS_997ST.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("InvokeBPMode")).
						addContent(new Element("VALUE").setNamespace(n1).setText("SpecifyBP")).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				ENVELOPE_PARMS_997ST.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("KeepTranslatedDocument")).
						addContent(new Element("VALUE").setNamespace(n1).setText("YES")).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				ENVELOPE_PARMS_997ST.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("MapNameMode")).
						addContent(new Element("VALUE").setNamespace(n1).setText("Specify")).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				ENVELOPE_PARMS_997ST.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("PerformControlNumberSequenceCheck")).
						addContent(new Element("VALUE").setNamespace(n1).setText("NO")).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				
				ENVELOPE_PARMS_997ST.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("PerformComplianceCheck")).
						addContent(new Element("VALUE").setNamespace(n1).setText("YES")).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				
				ENVELOPE_PARMS_997ST.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("PerformDuplicateControlNumberCheck")).
						addContent(new Element("VALUE").setNamespace(n1).setText("NO")).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				
				ENVELOPE_PARMS_997ST.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("PerformHIPAACompCheck")).
						addContent(new Element("VALUE").setNamespace(n1)).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				ENVELOPE_PARMS_997ST.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("RetainEnvelope")).
						addContent(new Element("VALUE").setNamespace(n1).setText("YES")).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				
				ENVELOPE_PARMS_997ST.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("TransactionSetIDCode")).
						addContent(new Element("VALUE").setNamespace(n1).setText("997")).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				ENVELOPE_PARMS_997ST.addContent(new Element("ENVELOPE_PARM").setNamespace(n1)
						.addContent(new Element("NAME").setNamespace(n1).setText("ValidateOutput")).
						addContent(new Element("VALUE").setNamespace(n1).setText("YES")).
						addContent(new Element("INHERITED").setNamespace(n1).setText("false")));
				
				
			}
		}
			
				
		int choice = 4 ; 
		
		Util util = new Util();
		util.writeTofile(doc , choice, outputDirectory);
		
		/*XMLOutputter xmlOutput = new XMLOutputter();
		xmlOutput.setFormat(Format.getPrettyFormat());

		try {
			xmlOutput.output(doc, new FileWriter(outputDirectory + "XML_EDIFACT_OB_Envelope.xml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/


	
	}



	// TODO Auto-generated constructor stub


}


