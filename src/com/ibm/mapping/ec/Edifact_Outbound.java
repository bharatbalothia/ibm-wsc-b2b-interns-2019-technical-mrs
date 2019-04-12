package com.ibm.mapping.ec;

import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;

public class Edifact_Outbound {

	public Edifact_Outbound() {
	}
	public Element envelopeHeader(int flag, Document doc, @SuppressWarnings("rawtypes") Map xls ,Namespace n) {
		String type = null;
		String standard = null;
		String envID = null;
		String senderID = null;
		String recieverID = null;
		@SuppressWarnings("unused")
		Element ep = null;
		Element Envelope = null;
		String function = null;
		Envelope = new Element("ENVELOPE" ,n);
		// unb Header
		if (xls.get("Create CONTROL(YES/NO)").toString().equals("YES")) {
			if (flag == 0) {
				senderID = xls.get("TP Interchange ID").toString();
				recieverID = xls.get("Client Interchange ID").toString();
				//System.out.println(recieverID);
				envID = xls.get("Client Name").toString() + "_" + xls.get("Client Interchange ID").toString() + "_"
						+ xls.get("TP Interchange ID").toString() + "_I_"
						+ xls.get("Transaction ID UNH 02 01").toString() + "_CONTROL_UNB";
				standard = xls.get("Standards Board (EDIFACT)").toString();
				type = "UNB UNZ Syntax 4";
				function = "1";
			}
			if (flag == 1) {
				senderID = xls.get("TP Interchange ID").toString();
				recieverID = xls.get("Client Interchange ID").toString();
				//System.out.println(recieverID);
				envID = xls.get("Client Name").toString() + "_" + xls.get("Client Interchange ID").toString() + "_"
						+ xls.get("TP Interchange ID").toString() + "_I_" + "UNG";
				standard = xls.get("Standards Board (EDIFACT)").toString();
				type = "UNG UNE Syntax 4";
				function = "1";
			}
			if (flag == 2) {
				senderID = xls.get("TP Interchange ID").toString();
				recieverID = xls.get("Client Interchange ID").toString();
				//System.out.println(recieverID);
				envID = xls.get("Client Name").toString() + "_" + xls.get("Client Interchange ID").toString() + "_"
						+ xls.get("Client Application ID(SAME as Client Interchange if no separate app id)").toString()
						+ "_" + xls.get("TP Interchange ID").toString() + "_I_"
						+ xls.get("Transaction ID UNH 02 01").toString() + "_CONTROL_UNH";
				standard = xls.get("Standards Board (EDIFACT)").toString();
				type = "UNH UNT Syntax 4";
				function = "1";
			}
		}
		if (flag == 3) {
			senderID = xls.get("Client Interchange ID").toString();
			recieverID = xls.get("TP Interchange ID").toString();
			//System.out.println(recieverID);
			envID = xls.get("Client Name").toString() + "_" + xls.get("Client Interchange ID").toString() + "_"
					+ xls.get("TP Interchange ID").toString() + "_O_" + xls.get("Transaction ID UNH 02 01").toString()
					+ "_UNB";
			standard = xls.get("Standards Board (EDIFACT)").toString();
			type = "UNB UNZ Syntax 4";
			function = "0";
		}
		if(flag == 4 ){
			senderID = xls.get("Client Interchange ID").toString();
			recieverID = xls.get("TP Interchange ID").toString();
			//System.out.println(recieverID);
			envID = xls.get("Client Name").toString() + "_" + xls.get("Client Interchange ID").toString() + "_"
					+ xls.get("TP Interchange ID").toString() + "_O_" + xls.get("MessageReleaseNumber (UNH 02 03)").toString()
					+ "_UNG";
			standard = xls.get("Standards Board (EDIFACT)").toString();
			type = "UNG UNE Syntax 4";
			function = "0";
		}
		if(flag == 5 ){
			senderID = xls.get("Client Application ID(SAME as Client Interchange if no separate app id)").toString();
			recieverID = xls.get("TP Application CODE(SAME as TP Interchange if no separate app id)").toString();
			//System.out.println(recieverID);
			envID = xls.get("Client Name").toString() + "_" + xls.get("Client Interchange ID").toString() + "_" +
					xls.get("Client Application ID(SAME as Client Interchange if no separate app id)").toString() + "_"+
					xls.get("TP Interchange ID").toString() +"_" + 
					xls.get("TP Application CODE(SAME as TP Interchange if no separate app id)").toString() + 
					"_O_" + xls.get("Transaction ID UNH 02 01").toString() +"_" + xls.get("Message Version Number (UNH 02 02)").toString() +
					"_" + xls.get("Transaction ID UNH 02 01").toString() + "_" + "UNH";
			standard = xls.get("Standards Board (EDIFACT)").toString();
			type = "UNH UNT Syntax 4";
			function = "0";
		}
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
		Element NAME = new Element("NAME", n).setText(envID);
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
	public Edifact_Outbound(Document doc, Map xls ,Namespace n, String outputDirectory) {

		int flag = 0;
		Element si_Resources = doc.getRootElement();
		Element doc_Envelope = new Element("DOC_ENEVELOPES" ,n);
		if (!doc.hasRootElement()) {
			si_Resources.addContent(doc_Envelope);
		}
		Element envelopeUNB = envelopeHeader(flag, doc, xls ,n);
		doc.getRootElement().getChild("DOCUMENT_ENVELOPES" ,n).addContent(envelopeUNB);
		// unb parameters
		if (xls.get("Create CONTROL(YES/NO)").toString().equals("YES")) {
			if (flag == 0) {
				Element ENVELOPE_PARMS_UNB = new Element("ENVELOPE_PARMS" ,n );
				envelopeUNB.addContent(ENVELOPE_PARMS_UNB);
				ENVELOPE_PARMS_UNB.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("AcceptNonCompliantInterchanges"))
						.addContent(new Element("VALUE" ,n).setText("NO"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNB.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("AcknowledgementDetailLevel"))
						.addContent(new Element("VALUE" ,n).setText("Interchange"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNB.addContent(new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("CONTRLALAFormat"))
						.addContent(new Element("VALUE" ,n).setText("1"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNB.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("ControlNumberAge")).addContent(new Element("VALUE" ,n))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNB.addContent(new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("EDIPostProcessorMode"))
						.addContent(new Element("VALUE" ,n).setText("queue"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNB.addContent(new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("ErrorBP"))
						.addContent(new Element("VALUE" ,n).setText("BPN_EDIFACTDeenvelopeUnifiedPostProcessor"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNB.addContent(new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("GenerateAcknowledgement"))
						.addContent(new Element("VALUE" ,n).setText("YES"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNB.addContent(new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("global_control_no"))
						.addContent(new Element("VALUE" ,n).setText("NO"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNB.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("InterchangeRecipientIDCodeQualifier"))
						.addContent(new Element("VALUE" ,n).setText(xls.get("Client Qualifier").toString()))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNB.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("InterchangeRecipientInternalID"))
						.addContent(new Element("VALUE" ,n)).addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNB.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("InterchangeRecipientInternalSubID"))
						.addContent(new Element("VALUE" ,n)).addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNB.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("InterchangeSenderIDCodeQualifier"))
						.addContent(new Element("VALUE" ,n)).addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNB.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("InterchangeSenderInternalID"))
						.addContent(new Element("VALUE" ,n)).addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNB.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("InterchangeSenderInternalSubID"))
						.addContent(new Element("VALUE" ,n)).addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNB.addContent(new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("InterchangeTestIndicator"))
						.addContent(new Element("VALUE" ,n).setText("1"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNB.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("PerformControlNumberSequenceCheck"))
						.addContent(new Element("VALUE" ,n).setText("NO"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNB.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("PerformDuplicateControlNumberCheck"))
						.addContent(new Element("VALUE" ,n).setText("NO"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNB.addContent(new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("RetainEnvelope"))
						.addContent(new Element("VALUE" ,n).setText("YES"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNB.addContent(new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("SendAckImmediately"))
						.addContent(new Element("VALUE" ,n).setText("YES"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNB.addContent(new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("Syntax3AckResponse"))
						.addContent(new Element("VALUE" ,n).setText("AcceptReject"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNB.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("WriteS002AndS003SubElements"))
						.addContent(new Element("VALUE" ,n).setText("NO"))
						.addContent(new Element("INHERITED" ,n).setText("false")));
			}
		}

		flag = 1;

		if (xls.get("Create UNG (YES/NO)").toString().equals("YES") | xls.get("Create UNG (YES/NO)").toString().equals("Y")|xls.get("Create UNG (YES/NO)").toString().equals("y")) {
			//System.out.println("hello");
			if (flag == 1) {

				Element envelopeUNG = envelopeHeader(flag, doc, xls ,n);
				doc.getRootElement().getChild("DOCUMENT_ENVELOPES" , n).addContent(envelopeUNG);

				Element ENVELOPE_PARMS_UNG = new Element("ENVELOPE_PARMS" ,n);
				envelopeUNG.addContent(ENVELOPE_PARMS_UNG);

				ENVELOPE_PARMS_UNG.addContent(
						new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("AcceptNonCompliantGroups"))
						.addContent(new Element("VALUE" ,n).setText("NO"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNG.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("ControlNumberAge")).addContent(new Element("VALUE" ,n))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNG
				.addContent(new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("ErrorBP"))
						.addContent(new Element("VALUE" ,n).setText("BPN_EDIFACTDeenvelopeUnifiedPostProcessor"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNG.addContent(
						new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("global_control_no"))
						.addContent(new Element("VALUE" ,n).setText("NO"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNG.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("GroupRecipientIDCodeQualifier"))
						.addContent(new Element("VALUE" ,n).setText("*"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNG.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("GroupSenderIDCodeQualifier"))
						.addContent(new Element("VALUE" ,n).setText("*"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNG.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("PerformControlNumberSequenceCheck"))
						.addContent(new Element("VALUE" ,n).setText("NO"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNG.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("PerformDuplicateControlNumberCheck"))
						.addContent(new Element("VALUE" ,n).setText("NO"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNG.addContent(
						new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("RetainEnvelope"))
						.addContent(new Element("VALUE" ,n).setText("YES"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNG.addContent(
						new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("SecureGroupInbound"))
						.addContent(new Element("VALUE" ,n).setText("NO"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

			}
			flag = 2;
			if (flag == 2) {

				Element envelopeCUNH = envelopeHeader(flag, doc, xls ,n);
				doc.getRootElement().getChild("DOCUMENT_ENVELOPES" , n).addContent(envelopeCUNH);

				Element ENVELOPE_PARMS_UNH = new Element("ENVELOPE_PARMS" ,n );
				envelopeCUNH.addContent(ENVELOPE_PARMS_UNH);

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("AcceptNonCompliantTransactions"))
						.addContent(new Element("VALUE" ,n).setText("NO"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(
						new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("BatchLikeTransactions"))
						.addContent(new Element("VALUE" ,n).setText("NO"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH
				.addContent(new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("BP"))
						.addContent(new Element("VALUE" ,n).setText("BPN_EDIFACTDeenvelopeUnifiedPostProcessor"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(
						new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("BPDataExtractMode"))
						.addContent(new Element("VALUE" ,n).setText("UseBP"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(
						new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("BPInvokeOrSetInPD"))
						.addContent(new Element("VALUE" ,n).setText("Invoke"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("BPNameGroupVersion1")).addContent(new Element("VALUE" ,n))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("BPNameReceiver1")).addContent(new Element("VALUE" ,n))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("BPNameReceiverQual1")).addContent(new Element("VALUE" ,n))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("BPNameSender1")).addContent(new Element("VALUE" ,n))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("BPNameSenderQual1")).addContent(new Element("VALUE" ,n))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("BPNameStandard1")).addContent(new Element("VALUE" ,n))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("BPNameTrnsSet1")).addContent(new Element("VALUE" ,n))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(
						new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("ComplianceCheckMapName"))
						.addContent(new Element("VALUE" ,n).setText("BPN_CONTRL_TO_ACKXML"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("ContractID")).addContent(new Element("VALUE" ,n))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("ControlNumberAge")).addContent(new Element("VALUE" ,n))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH
				.addContent(new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("ErrorBP"))
						.addContent(new Element("VALUE" ,n).setText("BPN_EDIFACTDeenvelopeUnifiedPostProcessor"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("ErrorBPNameGroupVersion1"))
						.addContent(new Element("VALUE" ,n)).addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(
						new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("ErrorBPNameMode"))
						.addContent(new Element("VALUE" ,n).setText("Specify"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("ErrorBPNameReceiver1"))
						.addContent(new Element("VALUE" ,n)).addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("ErrorBPNameReceiverQual1"))
						.addContent(new Element("VALUE" ,n)).addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("ErrorBPNameSender1")).addContent(new Element("VALUE" ,n))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("ErrorBPNameSenderQual1"))
						.addContent(new Element("VALUE" ,n)).addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("ErrorBPNameStandard1"))
						.addContent(new Element("VALUE" ,n)).addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("ErrorBPNameTrnsSet1")).addContent(new Element("VALUE" ,n))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("ErrorIfBPNotFound")).addContent(new Element("VALUE" ,n))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("ErrorIfErrorBPNotFound"))
						.addContent(new Element("VALUE" ,n)).addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("ErrorIfMapNotFound")).addContent(new Element("VALUE" ,n))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("ExtractDirectory")).addContent(new Element("VALUE" ,n))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("ExtractFilename")).addContent(new Element("VALUE" ,n))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("ExtractMailbox")).addContent(new Element("VALUE" ,n))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("ExtractMailboxMessageName"))
						.addContent(new Element("VALUE" ,n)).addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("GenBPName1")).addContent(new Element("VALUE" ,n))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("GenCtrlNameOpts1")).addContent(new Element("VALUE" ,n))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("GenCtrlNumNameMsgType1"))
						.addContent(new Element("VALUE" ,n)).addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("GenCtrlNumNameMsgVersionRelease1"))
						.addContent(new Element("VALUE" ,n)).addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("GenErrorBPName1")).addContent(new Element("VALUE" ,n))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("GenMapName1")).addContent(new Element("VALUE" ,n))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(
						new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("global_control_no"))
						.addContent(new Element("VALUE" ,n).setText("NO"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(
						new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("InterchangeTestIndicator"))
						.addContent(new Element("VALUE" ,n).setText("1"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH
				.addContent(new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("InvokeBPMode"))
						.addContent(new Element("VALUE" ,n).setText("SpecifyBP"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(
						new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("KeepTranslatedDocument"))
						.addContent(new Element("VALUE" ,n).setText("YES"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("MapNameGroupVersion1"))
						.addContent(new Element("VALUE" ,n)).addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH
				.addContent(new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("MapNameMode"))
						.addContent(new Element("VALUE" ,n).setText("Specify"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("MapNameReceiver1")).addContent(new Element("VALUE" ,n))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("MapNameReceiverQual1"))
						.addContent(new Element("VALUE" ,n)).addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("MapNameSender1")).addContent(new Element("VALUE" ,n))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("MapNameSenderQual1")).addContent(new Element("VALUE" ,n))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("MapNameStandard1")).addContent(new Element("VALUE" ,n))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("MapNameTrnsSet1")).addContent(new Element("VALUE" ,n))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("MessageAssociationAssignedCode"))
						.addContent(new Element("VALUE" ,n)).addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("MessageControllingAgency"))
						.addContent(new Element("VALUE" ,n)).addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("MessageRecipientIDCodeQualifier"))
						.addContent(new Element("VALUE" ,n)).addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("MessageReleaseNumber"))
						.addContent(new Element("VALUE" ,n)).addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("MessageSenderIDCodeQualifier"))
						.addContent(new Element("VALUE" ,n)).addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH
				.addContent(new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("MessageType"))
						.addContent(new Element("VALUE" ,n).setText("CONTRL"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("MessageTypeSubFunctionID"))
						.addContent(new Element("VALUE" ,n)).addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(
						new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("MessageVersionNumber"))
						.addContent(new Element("VALUE" ,n)
								.setText(xls.get("Message Version Number (UNH 02 02)").toString()))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(
						new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("PerformComplianceCheck"))
						.addContent(new Element("VALUE" ,n).setText("YES"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("PerformControlNumberSequenceCheck"))
						.addContent(new Element("VALUE" ,n).setText("NO"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("PerformDuplicateControlNumberCheck"))
						.addContent(new Element("VALUE" ,n)).addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(
						new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("RetainEnvelope"))
						.addContent(new Element("VALUE" ,n).setText("YES"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(
						new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("SecureDocumentInbound"))
						.addContent(new Element("VALUE" ,n).setText("NO"))
						.addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
						.addContent(new Element("NAME", n).setText("SecurityCertificateInbound"))
						.addContent(new Element("VALUE" ,n)).addContent(new Element("INHERITED" ,n).setText("false")));

				ENVELOPE_PARMS_UNH.addContent(
						new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("ValidateOutput"))
						.addContent(new Element("VALUE" ,n).setText("YES"))
						.addContent(new Element("INHERITED" ,n).setText("false")));
			}


		}
		//Outbound Unb 
		flag = 3;
		if (flag == 3) {

			Element envelopeEDIUNB = envelopeHeader(flag, doc, xls ,n);
			doc.getRootElement().getChild("DOCUMENT_ENVELOPES" , n).addContent(envelopeEDIUNB);

			Element ENVELOPE_PARMS_EdiUNH = new Element("ENVELOPE_PARMS",n);
			envelopeEDIUNB.addContent(ENVELOPE_PARMS_EdiUNH);

			ENVELOPE_PARMS_EdiUNH.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("AcknowledgementOverdueTime"))
					.addContent(
							new Element("VALUE" ,n).setText(xls.get("Acknowledgement Overdue Time (HR)").toString()))
					.addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_EdiUNH.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("AcknowledgementOverdueTimeMinutes"))
					.addContent(
							new Element("VALUE" ,n).setText(xls.get("Acknowledgement Overdue Time (MIN)").toString()))
					.addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_EdiUNH
			.addContent(new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("BP"))
					.addContent(new Element("VALUE" ,n).setText("BPN_EDIFACTEnvelopeUnifiedPostProcessor"))
					.addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_EdiUNH.addContent(
					new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("BPDataExtractMode"))
					.addContent(new Element("VALUE" ,n).setText("UseBP"))
					.addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_EdiUNH.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("ContractID")).addContent(new Element("VALUE" ,n))
					.addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_EdiUNH.addContent(
					new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("DecimalSeparator"))
					.addContent(
							new Element("VALUE" ,n).setText("0x" + xls.get("TP Decimal Separator").toString()))
					.addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_EdiUNH.addContent(
					new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("ElementSeparator"))
					.addContent(
							new Element("VALUE" ,n).setText("0x" + xls.get("TP Element Delimiter").toString()))
					.addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_EdiUNH.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("EncodeDocument")).
					addContent(new Element("VALUE" ,n).setText(xls.get("Set Encoding on Outbound UNB (YES/NO)").toString()))
					.addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_EdiUNH
			.addContent(new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("Encoding"))
					.addContent(new Element("VALUE" ,n).setText(xls.get("Encoding Type").toString()))
					.addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_EdiUNH.addContent(
					new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("global_control_no"))
					.addContent(new Element("VALUE" ,n).setText("NO"))
					.addContent(new Element("INHERITED" ,n).setText("false")));

			String interchange = null;
			if(xls.get("Create CONTROL(YES/NO)").toString().equals("YES")){
				interchange = "1";
			}
			ENVELOPE_PARMS_EdiUNH.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("InterchangeAcknowledgementRequest"))
					.addContent(new Element("VALUE" ,n).setText(interchange))
					.addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_EdiUNH.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("InterchangeAgreementID"))
					.addContent(new Element("VALUE" ,n)).addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_EdiUNH.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("InterchangeApplicationReference"))
					.addContent(new Element("VALUE" ,n)).addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_EdiUNH.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("InterchangeRecipientIDCodeQualifier"))
					.addContent(new Element("VALUE" ,n)).
					addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_EdiUNH.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("InterchangeRecipientInternalID"))
					.addContent(new Element("VALUE" ,n)).addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_EdiUNH.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("InterchangeRecipientInternalSubID"))
					.addContent(new Element("VALUE" ,n)).addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_EdiUNH.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("InterchangeRecipientReferencePassword"))
					.addContent(new Element("VALUE" ,n)).addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_EdiUNH.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("InterchangeRecipientReferencePasswordQualifier"))
					.addContent(new Element("VALUE" ,n)).addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_EdiUNH.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("InterchangeProcessingPriorityCode"))
					.addContent(new Element("VALUE" ,n)).addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_EdiUNH.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("InterchangeCharacterEncoding"))
					.addContent(new Element("VALUE" ,n)).addContent(new Element("INHERITED" ,n).setText("false")));

			String testIndicator = xls.get("Test Indicator (T/P)").toString();
			if (testIndicator.equals("T")) {
				testIndicator = "1";
			}
			ENVELOPE_PARMS_EdiUNH.addContent(
					new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("InterchangeTestIndicator"))
					.addContent(new Element("VALUE" ,n).setText(testIndicator))
					.addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_EdiUNH.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("InterchangeSenderIDCodeQualifier"))
					.addContent(new Element("VALUE" ,n).setText(xls.get("Client Qualifier").toString())).addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_EdiUNH.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("InterchangeSenderInternalID"))
					.addContent(new Element("VALUE" ,n)).addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_EdiUNH.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("InterchangeSenderInternalSubID"))
					.addContent(new Element("VALUE" ,n)).addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_EdiUNH.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("InterchangeSyntaxIdentifier"))
					.addContent(new Element("VALUE" ,n).setText(xls.get("Syntax Identifier (UNB 01 01)").toString())).addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_EdiUNH.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("InterchangeSyntaxVersionNumber"))
					.addContent(new Element("VALUE" ,n).setText(xls.get("Syntax Version Number (UNB 01 02)").toString()))
					.addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_EdiUNH
			.addContent(new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("InvokeBPMode"))
					.addContent(new Element("VALUE" ,n).setText("SpecifyBP"))
					.addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_EdiUNH.addContent(
					new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("ReleaseCharacter"))
					.addContent(
							new Element("VALUE" ,n).setText("0x" + xls.get("TP Release Character").toString()))
					.addContent(new Element("INHERITED" ,n).setText("false")));

			String segmentTerminator = "0x" + xls.get("TP Segment Terminator").toString();
			String RepeatingElementSeparator = null;
			String SpecifyDelmiters = null;
			if (segmentTerminator == "") {
				RepeatingElementSeparator = "";
				SpecifyDelmiters = "NO";
			} else {
				RepeatingElementSeparator = "0x";
				SpecifyDelmiters = "YES";
			}


			ENVELOPE_PARMS_EdiUNH.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("RepeatingElementSeparator"))
					.addContent(new Element("VALUE" ,n).setText(RepeatingElementSeparator))
					.addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_EdiUNH.addContent(
					new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("SegmentTerminator"))
					.addContent(new Element("VALUE" ,n).setText(segmentTerminator))
					.addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_EdiUNH.addContent(
					new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("SpecifyDelmiters"))
					.addContent(new Element("VALUE" ,n).setText(SpecifyDelmiters))
					.addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_EdiUNH
			.addContent(new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("Streamed"))
					.addContent(new Element("VALUE" ,n).setText("YES"))
					.addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_EdiUNH.addContent(
					new Element("ENVELOPE_PARM" , n).addContent(new Element("NAME", n).setText("SubElementSeparator"))
					.addContent(new Element("VALUE" ,n)
							.setText("0x" + xls.get("TP Sub Element Delimiter").toString()))
					.addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_EdiUNH.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("Una")).addContent(new Element("VALUE" ,n).setText("YES"))
					.addContent(new Element("INHERITED" ,n).setText("false")));
		}

		//outbound UNH
		flag = 4 ;
		if(flag == 4 ){

			Element envelopeUNH = envelopeHeader(flag, doc, xls ,n);
			doc.getRootElement().getChild("DOCUMENT_ENVELOPES" , n).addContent(envelopeUNH);

			Element ENVELOPE_PARMS_UNH = new Element("ENVELOPE_PARMS" ,n);
			envelopeUNH.addContent(ENVELOPE_PARMS_UNH);


			ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("global_control_no")).
					addContent(new Element("VALUE" ,n).setText("NO")).
					addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("GroupControllingAgency")).
					addContent(new Element("VALUE" ,n).setText(xls.get("Message Version Number (UNH 02 02)").toString())).
					addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("GroupRecipientIDCodeQualifier")).
					addContent(new Element("VALUE" ,n)).
					addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("GroupSenderIDCodeQualifier")).
					addContent(new Element("VALUE" ,n)).
					addContent(new Element("INHERITED" ,n).setText("false")));

			String nextEnveope = xls.get("Client Name").toString() + "_" + xls.get("Client Interchange ID").toString() + "_"
					+ xls.get("TP Interchange ID").toString() + "_O_" + xls.get("Transaction ID UNH 02 01").toString()
					+ "_UNB";
			ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("NextEnvelope")).
					addContent(new Element("VALUE" ,n).setText(nextEnveope)).
					addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("SecureGroupOutbound")).
					addContent(new Element("VALUE" ,n).setText("NO")).
					addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_UNH.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("UseCorrelationOverrides")).
					addContent(new Element("VALUE" ,n).setText("ALL")).
					addContent(new Element("INHERITED" ,n).setText("false")));


		}
		// Outbound UNB
		flag = 5 ;
		if(flag == 5 ){
			Element envelopeEdiUNB = envelopeHeader(flag, doc, xls ,n);
			doc.getRootElement().getChild("DOCUMENT_ENVELOPES" ,n).addContent(envelopeEdiUNB);

			Element ENVELOPE_PARMS_UNB = new Element("ENVELOPE_PARMS"  ,n);
			envelopeEdiUNB.addContent(ENVELOPE_PARMS_UNB);

			ENVELOPE_PARMS_UNB.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("AccepterLookupAlias")).
					addContent(new Element("VALUE" ,n).setText(xls.get("Accepter Lookup Alias").toString())).
					addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_UNB.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("global_control_no")).
					addContent(new Element("VALUE" ,n).setText("NO")).
					addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_UNB.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("MapName")).
					addContent(new Element("VALUE" ,n).setText(xls.get("Translation Map Name").toString())).
					addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_UNB.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("MapNameMode")).
					addContent(new Element("VALUE" ,n).setText("Specify")).
					addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_UNB.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("MessageAssociationAssignedCode")).
					addContent(new Element("VALUE" ,n)).
					addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_UNB.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("MessageControllingAgency")).
					addContent(new Element("VALUE" ,n).setText(xls.get("Controlling Agency (UNH 02 04)").toString())).
					addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_UNB.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("MessageRecipientIDCodeQualifier")).
					addContent(new Element("VALUE" ,n)).
					addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_UNB.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("MessageReleaseNumber")).
					addContent(new Element("VALUE" ,n).setText(xls.get("MessageReleaseNumber (UNH 02 03)").toString())).
					addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_UNB.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("MessageSenderIDCodeQualifier")).
					addContent(new Element("VALUE" ,n).setText(xls.get("Client Qualifier").toString())).
					addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_UNB.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("MessageType")).
					addContent(new Element("VALUE" ,n).setText(xls.get("Transaction ID UNH 02 01").toString())).
					addContent(new Element("INHERITED" ,n).setText("false")));
			ENVELOPE_PARMS_UNB.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("MessageVersionNumber")).
					addContent(new Element("VALUE" ,n).setText(xls.get("Message Version Number (UNH 02 02)").toString())).
					addContent(new Element("INHERITED" ,n).setText("false")));

			String nextEnv = xls.get("Client Name").toString() + "_" + xls.get("Client Interchange ID").toString() + "_"
					+ xls.get("TP Interchange ID").toString() + "_O_" + xls.get("MessageReleaseNumber (UNH 02 03)").toString()
					+ "_UNG"; 
			ENVELOPE_PARMS_UNB.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("NextEnvelope")).
					addContent(new Element("VALUE" ,n).setText(nextEnv)).
					addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_UNB.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("SecureDocumentOutbound")).
					addContent(new Element("VALUE" ,n).setText("NO")).
					addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_UNB.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("ValidateInput")).
					addContent(new Element("VALUE" ,n).setText("YES")).
					addContent(new Element("INHERITED" ,n).setText("false")));

			ENVELOPE_PARMS_UNB.addContent(new Element("ENVELOPE_PARM" , n)
					.addContent(new Element("NAME", n).setText("ValidateOutput")).
					addContent(new Element("VALUE" ,n)).
					addContent(new Element("INHERITED" ,n).setText("false")));


		}
		int choice = 2;
		Util util = new Util();
		util.writeTofile(doc , choice , outputDirectory );
	}

}
