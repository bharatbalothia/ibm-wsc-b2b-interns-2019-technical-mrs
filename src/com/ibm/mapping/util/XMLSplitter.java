package com.ibm.mapping.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XMLSplitter {

	public static void generateXmlFiles(String inputDirectory,
			String ouputDirectory) {

		String limiter = null;
		String sCurrentLine;
		StringBuilder builder = new StringBuilder();
		/*try (BufferedReader br = new BufferedReader(new FileReader(
				inputDirectory+"\\234053957.dat")))*/
		try{
			BufferedReader br = new BufferedReader(new FileReader(
					inputDirectory+"\\234053957.dat"));
			while ((sCurrentLine = br.readLine()) != null) {

				builder.append(sCurrentLine);

			}
            br.close();
			builder.replace(builder.indexOf("<"), builder.indexOf(">") + 1, "");
			builder.replace(builder.lastIndexOf("<"),
					builder.lastIndexOf(">") + 1, "");
			// System.out.println(builder);
			String limiterExtracter = builder.toString();
			limiter = limiterExtracter.substring(0,
					limiterExtracter.indexOf(">") + 1).replace("<", "</");

			List<String> list = new ArrayList<String>();
			//System.out.println("Return Value :");
			for (String retval : limiterExtracter.split(limiter)) {
				list.add(retval + limiter);
			}

			for (int i = 0; i < list.size(); i++) {
				try {

					String fileName = limiter.replace("</", "")
							.replace(">", "").trim();
					//System.out.println(limiter);
					//System.out.println(fileName);
					File file = new File(ouputDirectory + fileName + (i + 1)
							+ ".xml");

					if (!file.exists()) {

						file.createNewFile();
					}

					FileWriter fw = new FileWriter(file.getAbsoluteFile());
					String header = new String(
							"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
					fw.write(header);
					fw.write(list.get(i));
					fw.close();

					//System.out.println("Done");

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//System.out.println(list.get(1));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		String limiter = null;
		String sCurrentLine;
		StringBuilder builder = new StringBuilder();
		/*try (BufferedReader br = new BufferedReader(
				new FileReader(
						"C:\\Users\\IBM_ADMIN\\Desktop\\XMLSPLITTER\\Input\\234053957.dat")))*/
		try{
			BufferedReader br = new BufferedReader(
					new FileReader(
							"C:\\Users\\IBM_ADMIN\\Desktop\\XMLSPLITTER\\Input\\234053957.dat"));
			while ((sCurrentLine = br.readLine()) != null) {

				builder.append(sCurrentLine);

			}
            br.close();
			builder.replace(builder.indexOf("<"), builder.indexOf(">") + 1, "");
			builder.replace(builder.lastIndexOf("<"),
					builder.lastIndexOf(">") + 1, "");
			// System.out.println(builder);
			String limiterExtracter = builder.toString();
			limiter = limiterExtracter.substring(0,
					limiterExtracter.indexOf(">") + 1).replace("<", "</");

			List<String> list = new ArrayList<String>();
			//System.out.println("Return Value :");
			for (String retval : limiterExtracter.split(limiter)) {
				list.add(retval + limiter);
			}

			for (int i = 0; i < list.size(); i++) {
				try {

					String fileName = limiter.replace("</", "")
							.replace(">", "").trim();
					//System.out.println(limiter);
					//System.out.println(fileName);
					File file = new File("C:\\XMLSPlitter\\" + fileName
							+ (i + 1) + ".xml");

					if (!file.exists()) {

						file.createNewFile();
					}

					FileWriter fw = new FileWriter(file.getAbsoluteFile());
					String header = new String(
							"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
					fw.write(header);
					fw.write(list.get(i));
					fw.close();

					//System.out.println("Done");

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//System.out.println(list.get(1));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
