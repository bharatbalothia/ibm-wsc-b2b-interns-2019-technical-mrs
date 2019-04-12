package com.ibm.mapping.qa;

import java.awt.BorderLayout;
import java.io.*;
import java.lang.ProcessBuilder.Redirect;
import java.util.*;

import javax.swing.JOptionPane;

import java.text.*;


public class FreeFormat {
	
	public static void main(String [] args) {
		String inputPathMXL = args[0];
		String outputDirectory = args[1];
		outputDirectory = args[1] + "\\";
		String mapOut = "";
		File inputFile = new File(inputPathMXL);
		String inputFileName = inputFile.getName().replaceAll("(?i).mxl", "");
		mapOut = outputDirectory + inputFileName + "_con.mxl";
		String line = "";
		
		
		try {
			// FileReader for MXL
        	
        	FileInputStream iFIS = new FileInputStream(inputPathMXL);
			InputStreamReader mapReader = new InputStreamReader(iFIS , "UTF-8");
			// BufferedReader for MXL
			BufferedReader mxl = new BufferedReader(mapReader);
			
			// FileWriter for MXL
			OutputStreamWriter mapWriter = new OutputStreamWriter(new FileOutputStream(mapOut), "UTF-8");
			//BufferedWriter for MXL
			BufferedWriter mxlOut = new BufferedWriter(mapWriter);
			
			while ((line = mxl.readLine()) != null) {
				if (line.equals("<DataType>string</DataType>")) {
					mxlOut.write(line);
					mxlOut.newLine();
					line = mxl.readLine();
					while(!(line.startsWith("<Format>"))) {
						mxlOut.write(line);
						mxlOut.newLine();
						line = mxl.readLine();
					}
					line = "<Format></Format>";
				}
				mxlOut.write(line);
				mxlOut.newLine();
			}
			mxl.close();
			mxlOut.close();
			
        	
		}
		catch(Exception e) {}
	}
}