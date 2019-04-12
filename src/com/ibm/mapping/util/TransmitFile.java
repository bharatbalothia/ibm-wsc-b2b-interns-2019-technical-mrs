package com.ibm.mapping.util;

/*Author: Pulkit V Jain, Kritika Agarwal and Sanket Patil*/

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedWriter;
import java.io.File;
import org.apache.commons.net.ftp.*;
import org.apache.log4j.Logger;

import com.ibm.mapping.servlet.Constants;

public class TransmitFile implements Constants {
	//log variable 
	static Logger log = Logger.getLogger(TransmitFile.class.getName());
	
	public static void upload(HttpServletRequest request, HttpServletResponse response,String inputDirectory,String outputDirectory) throws ServletException, IOException {
		
		File outputDir = new File(outputDirectory);

		if (!outputDir.exists()) {
			// Create a output directory.
			outputDir.mkdirs();
		}
		FileUtils.cleanDirectory(outputDir);
		
		File file = new File(outputDirectory + "Output.txt");
		
		// if file doesnt exists, then create it
		if (!file.exists()) {
		file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsolutePath());
		BufferedWriter bw = new BufferedWriter(fw);
		
		String selectedValue = request.getParameter("selected");
		//System.out.println("selected=>"+selectedValue);

		if (selectedValue.charAt(0) == 'X') {			
			String X12SenderQualifier, X12SenderID, X12ReceiverQualifier, X12ReceiverID;
			
			X12SenderQualifier = request.getParameter("senderQualifier_X12_name").toUpperCase();
			X12SenderID = request.getParameter("senderID_X12_name").toUpperCase();
			X12ReceiverQualifier = request.getParameter("receiverQualifier_X12_name").toUpperCase();
			X12ReceiverID = request.getParameter("receiverID_X12_name").toUpperCase();
			
			for (int i = X12SenderID.length(); i < 15; i++) {
				X12SenderID = X12SenderID + " ";
			}
			for (int i = X12ReceiverID.length(); i < 15; i++) {
				X12ReceiverID = X12ReceiverID + " ";
			}

			String X12_File = "ISA*00*          *00*          *" + X12SenderQualifier + "*" + X12SenderID + "*"
					+ X12ReceiverQualifier + "*" + X12ReceiverID + "*941221*1045*U*00200*000000002*0*T*>~GS*PO*"
					+ X12SenderID.trim() + "*" + X12ReceiverID.trim()
					+ "*941221*1045*2*X*004010~ST*850*0002~BEG*00*NE*101*1001*911221*0001*AC~PER*CR*ORDERNET INSTALLATIONS*TE*1-800-947-7355~SSS*N*AX*I0012****EDI COMMUNICATIONS INSTALLATION~DTM*008*920214*1015*ES~N1*BT*FLINTSTONE'S MARKET, INC.*9*0012121219999~N2*\"JUST A STONE-AGE'S THROW AWAY!\"~N3*P.O. BOX 2000 B.C.~N4*BEDROCK*BC*12345~PKG*F****SCOOBY SNACKS.~PO1*1*144*BX***ZZ*99999001~PID*F****BURT REYNOLDS WRAP~PO1*2*240*LB***ZZ*99999002~PID*F****BUSTER CRAB CAKES~PO1*3*120*EA***ZZ*99999003~PID*F****IRVING BERLIN BAGELS~PO1*4*280*SA***ZZ*99999004~PID*F****HOGAN'S HEROES' HOAGIES~PO1*5*250*DL***ZZ*99999005~PID*F****FRANKENSTIEN'S FRANKENFURTERS~PO1*6*468*DZ***ZZ*99999006~PID*F****FELLINI FETTUCINI~PO1*7*104*CA***ZZ*99999007~PID*F****RODNEY'S RATFINK DRINKS~PO1*8*364*CA***ZZ*99999008~PID*F****POPEYE'S PINEAPPLES~PO1*9*180*PK***ZZ*99999009~PID*F****CARMEN MIRANDA FRUIT COCKTAIL~PO1*10*400*CA***ZZ99999010~PID*F****NORMAN BATES' DATES\"JUST LIKE MOTHER USED TO MAKE !\"~PO1*11*60*BX***ZZ*99999011~PID*F****BOB HOPE SOAP~CTT*11******ASSORTED FOOLISHNESS AND GENERAL NONSENSE~SE*34*0002~GE*1*2~IEA*1*000000002~";
			bw.write(X12_File);
			bw.close();			
		}
		else if (selectedValue.charAt(0) == 'R') {
			String RecSenderQualifier, RecSenderID, RecReceiverQualifier, RecReceiverID;
			
			RecSenderQualifier = request.getParameter("senderQualifier_Rec_name").toUpperCase();
			RecSenderID = request.getParameter("senderID_Rec_name").toUpperCase();
			RecReceiverQualifier = request.getParameter("receiverQualifier_Rec_name").toUpperCase();
			RecReceiverID = request.getParameter("receiverID_Rec_name").toUpperCase();

			String Edifact_Recadv = "UNA:+.? 'UNB+UNOA:2+" + RecSenderID + ":" + RecSenderQualifier + "+"
					+ RecReceiverID + ":" + RecReceiverQualifier
					+ "+140430:1015+3378'UNH+1+RECADV:D:96A:UN:::L16'BGM+632::9:L17+2438+9'DTM+137:201404301015:203'RFF+ON:3378'NAD+MS+RHENUS_DE:100:Z'NAD+MR+4002132000008:100:9'CPS+1'LIN+1++000000009100014406:IN'QTY+48:00000016:PCE'QVR+7300++:::73T1'DTM+97:201404301015:203'RFF+ALO:0012915995:10:L16'LIN+2++000000002100075488:IN'QTY+48:00000016:PCE'QVR+7300++:::73T1'DTM+97:201404301015:203'RFF+ALO:0012915995:20:L16'LIN+3++000000009100011353:IN'QTY+48:00000008:PCE'QVR+7300++:::73T1'DTM+97:201404301015:203'RFF+ALO:0012915995:30:L16'LIN+4++000000002100070985:IN'QTY+48:00000016:PCE'QVR+7300++:::73T1'DTM+97:201404301015:203'RFF+ALO:0012915995:40:L16'LIN+5++000000002100070986:IN'QTY+48:00000016:PCE'QVR+7300++:::73T1'DTM+97:201404301015:203'RFF+ALO:0012915995:50:L16'UNT+33+1'UNZ+1+3378'";
			;
			bw.write(Edifact_Recadv);
			bw.close();
		}
		else if (selectedValue.charAt(0) == 'D') {
			String DesSenderQualifier, DesSenderID, DesReceiverQualifier, DesReceiverID;
			
			DesSenderQualifier = request.getParameter("senderQualifier_Des_name").toUpperCase();
			DesSenderID = request.getParameter("senderID_Des_name").toUpperCase();
			DesReceiverQualifier = request.getParameter("receiverQualifier_Des_name").toUpperCase();
			DesReceiverID = request.getParameter("receiverID_Des_name").toUpperCase();

			String Edifact_Desadv = "UNA:+.? 'UNB+UNOC:3+" + DesSenderID + ":" + DesSenderQualifier + "+"
					+ DesReceiverID + ":" + DesReceiverQualifier
					+ "+160324:1635+24'UNH+1+DESADV:D:96A:UN:EAN005'BGM+351+PB16-02665+9'DTM+137:20160324:102'DTM+11:20160323:102'RFF+ON:1692871'RFF+VN:VO16-01769'NAD+SU+8718732000008::9'NAD+BY+8713082000005::9'NAD+DP+8713082000005::9++This is+a Test+File++3316 GB+NL'NAD+IV+8713082000005::9++Sample+Desadv+File++3300 AB+NL'CPS+1'LIN+1++8718732000565:EN'IMD+F+DSC+::91:ABCDEFGHIJ'IMD+F+35+::91:L32'IMD+C+98+W31::91'QTY+12:1'RFF+ON:1692871'RFF+VN:VO16-01769'QVR+0:21'LIN+2++8718732000572:EN'IMD+F+DSC+::91:Puma Leather Jacket'IMD+F+35+::91:L32'IMD+C+98+W32::91'QTY+12:1'RFF+ON:1692871'RFF+VN:VO16-01769'QVR+0:21'LIN+3++8718732000688:EN'IMD+F+DSC+::91:Sample Edifact File'IMD+F+35+::91:L34'IMD+C+98+W32::91'QTY+12:1'RFF+ON:1692871'RFF+VN:VO16-01769'QVR+0:21'UNT+36+1'UNZ+1+24'";
			bw.write(Edifact_Desadv);
			bw.close();
		}
		else if (selectedValue.charAt(0) == 'V') {
			String VDASenderID, VDAReceiverID;

			VDASenderID = request.getParameter("senderID_VDA_name").toUpperCase();
			VDAReceiverID = request.getParameter("receiverID_VDA_name").toUpperCase();

			for (int i = VDASenderID.length(); i < 9; i++) {
				VDASenderID = VDASenderID + " ";
			}
			for (int i = VDAReceiverID.length(); i < 9; i++) {
				VDAReceiverID = VDAReceiverID + " ";
			}

			String VDA_File = "51101" + VDASenderID + VDAReceiverID
					+ "0091500916060923                                                                                         51201AA 717-2    060923717-1    100115  1P5X  B01896BA                                        AA   1SB STT  S       SECU-HL     51301060922002189890000000000001320000000004829444444000000816061002000000564061009000000588061016000000720061023000000756      51401061030000000396061106000000492061113000000480061120000000480061127000000492555555000000000061200000001176070100000002232   51401070200000002868070300000002193000000000000000                                                                              5150106010106101500000067970601010611260000010121                               070401                                          51801                                       D                                                                                   51201AA 717-2    060923717-1    100115  1P5X  B01896BA                                        AA   1SB STT  S       SECU-HL     51301060922002189890000000000001440000000005465444444000000600061002000000468061009000000444061016000000564061023000000528      51401061030000000660061106000000828061113000000840061120000000840061127000000804555555000000000061200000001896070100000003768   51401070200000003096070300000003051000000000000000                                                                              5150106010106101500000069770601010611260000011237                               070401                                          51801                                       D                                                                                   5190100000010000002000000200000040000000000000200000010000002";
			bw.write(VDA_File);
			bw.close();
		}
		else if (selectedValue.charAt(0) == 'N') {
			String nonediFile = "A Sample file from IBM";
			bw.write(nonediFile);
			bw.close();
			
			//File f = new File(outputDir + File.separator+ "Output.txt");
			FileInputStream fis = new FileInputStream(file);

			FTPClient client = new FTPClient();

			client.connect(Constants.FTPServerUrl, Constants.FTPPortNo);
			client.enterLocalPassiveMode();

			client.login("SFSH5U6L", "W0T_R3GK15");

			client.changeWorkingDirectory("send/commit");
			boolean chc = client.storeFile("Sample", fis);
			//System.out.println("ftp upload success flag = "+chc);
			
			if(chc)
			request.setAttribute("message","The generated "+ selectedValue +" file ("+file.getName()+") is successfully uploaded to FTP Server.");
			else
			request.setAttribute("message","The generated "+ selectedValue +" file ("+file.getName()+") is successfully uploaded to FTP Server.");
			
			client.disconnect();
			fis.close();

			//File deleteFile = new File(outputDir + File.separator+ "Output.txt");
			//deleteFile.delete();
			
			return;
		}
		else if (selectedValue.charAt(0) == 'S') {	
			
			if(file.exists())
			file.delete();
			
			File inputDir = new File(inputDirectory);
			// Check if input directory exists.
			if (inputDir.exists()) {

				File[] listOfFiles = inputDir.listFiles();

				for (int i = 0; i < listOfFiles.length; i++) {

					if (listOfFiles[i].isFile()) {
						
						//System.out.println("file name = "+listOfFiles[i].getName());
						
						FileInputStream fis = new FileInputStream(listOfFiles[i]);

						FTPClient client = new FTPClient();

						client.connect(Constants.FTPServerUrl, Constants.FTPPortNo);
						client.enterLocalPassiveMode();
						
						client.login("SFSH5U6I", "1RQ6P09DC-");

						//client.login("SFSH5U6L", "W0T_R3GK15");

						client.changeWorkingDirectory("send/commit");
						boolean chc = client.storeFile("Sample", fis);
						//System.out.println("ftp upload success flag = "+chc);
												
						if(chc)
						request.setAttribute("message","The browsed file ("+listOfFiles[i].getName()+") is successfully uploaded to FTP Server.");
						else
						request.setAttribute("message","The browsed file ("+listOfFiles[i].getName()+") is successfully uploaded to FTP Server.");
						
						client.disconnect();
						fis.close();
												
					}
				}
			} else {
				log.error("The input directory '" + inputDirectory + "' does not exists.");
			}
			bw.close();
			return;
		}

		//File f = new File(outputDir + File.separator+ "Output.txt");
		FileInputStream fis = new FileInputStream(file);

		FTPClient client = new FTPClient();

		client.connect(Constants.FTPServerUrl, Constants.FTPPortNo);
		client.enterLocalPassiveMode();

		client.login("SFSH5U6I", "1RQ6P09DC-");

		client.changeWorkingDirectory("send/commit");
		boolean chc = client.storeFile("Sample", fis);
		//System.out.println("ftp upload success flag = "+chc);
		
		if(chc)
		request.setAttribute("message","The generated "+ selectedValue +" file ("+file.getName()+") is successfully uploaded to FTP Server.");
		else
		request.setAttribute("message","The generated "+ selectedValue +" file ("+file.getName()+") is successfully uploaded to FTP Server.");
		
		client.disconnect();
		fis.close();

		return;
		//File deleteFile = new File(outputDir + File.separator+ "Output.txt");
		//deleteFile.delete();
	
	}
}