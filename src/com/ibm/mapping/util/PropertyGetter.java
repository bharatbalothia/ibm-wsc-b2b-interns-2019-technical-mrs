package com.ibm.mapping.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyGetter {

	private static String group;

	public static String getPropertyValue(String propertyName) {

		String filename = "C:\\Softwares\\eclipse-jee-luna-SR1-RC3-win32-x86_64\\workspace\\mappingDashBoard\\resource\\mapping.properties";
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(filename));
		} catch (IOException e) {
			System.out.println(" Error " + e);
			System.exit(1);
		}
		group = properties.getProperty(propertyName);

		//System.out.println(group);
		return group;
	}

	public static void main(String[] args) {
		System.out.println(PropertyGetter.getPropertyValue("group"));
	}
}
