//Created by Sukanya Dasgupta on July 25, 2015.

package com.ibm.mapping.qa;

import java.io.*;
import java.lang.ProcessBuilder.Redirect;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import java.awt.Component;
import java.awt.Desktop;



class FreeFormatTool {
    public static void main (String [] args) {
	    String inputDir = "C:";
	    String characterEncoding = "Cp1252 (Cp1252)";
	    String [] CE = {"Cp1252 (Cp1252)", "UTF8 (UTF8)"};
	    
	    //Choose directory with maps
	    JFileChooser chooser = new JFileChooser();
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    chooser.setDialogTitle("Select directory");
	    Component parent = null;
	    int returnVal = chooser.showOpenDialog(parent);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	        inputDir = chooser.getSelectedFile().getPath();
	    }
	    else if (returnVal == JFileChooser.CANCEL_OPTION) {
	        //System.out.println("No folder selected.");
	        JOptionPane.showMessageDialog(null, "No folder selected.", "", JOptionPane.INFORMATION_MESSAGE);
	        System.exit(0);
	    }
	    
	    
	    
	    
	    Date date = new Date();
	    String m1, m2;
	    m1 = "";
	    m2 = "";
	    String userN;
	    int flagMapper = 0;
	    DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	    String timestamp = dateFormat.format(date);
	    String outputDir = inputDir + "\\" + "SIMaps" + timestamp;
	    String dirPath, dirName;
	    int dirFlag = 0;
	    String map, out;
	    String [] arguments = new String[5];
	    String inputDirSC;
	    File dir = new File(inputDir);
	    String[] fileList;
	    //JOptionPane.showMessageDialog(null, inputDir, "", JOptionPane.INFORMATION_MESSAGE);
        
	    String[] dirList = dir.list(/*new FilenameFilter() {
	        @Override
	        public boolean accept(File pathname, String name) {
	            String filePath = pathname.toString();
	            File fileP = new File(filePath);
	            
	            return ((fileP.isDirectory()));
	            
	        }
	    }*/);
	    dirList = dir.list();
	    
	    List<String> dL = Arrays.asList(dirList);
	    ArrayList<String> maps = new ArrayList<String>();
	    ArrayList<String> DIRs = new ArrayList<String>();
	    //JOptionPane.showMessageDialog(null, dL, "", JOptionPane.INFORMATION_MESSAGE);
        
	    //Convert maps to MXLs.
	    for (String ds: dL) {
	    	File d = new File(inputDir + "\\" + ds);
	    	String dname = d.getName();
	    	//JOptionPane.showMessageDialog(null, dname, "", JOptionPane.INFORMATION_MESSAGE);
            
	    	fileList = d.list(new FilenameFilter() {
	            @Override
	            public boolean accept(File pathname, String name) {
	                String filePath = pathname.toString() + "\\" + name;
	                File fileP = new File(filePath);
	                
	                return ((fileP.isFile()) && (name != null));
	            }
	    	});
	    	for (String f: fileList) {
	    		if (f.toLowerCase().endsWith(".map")) {
	    			if (flagMapper == 0) {
	        			flagMapper = 1;
	        			//Choose folder MapEditor 8.0 in your computer.
	        			JFileChooser chooserSC = new JFileChooser("C:\\Program Files (x86)\\Sterling Commerce");
	        	        chooserSC.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	        	        chooserSC.setDialogTitle("Select directory of Map Editor 8.0");
	        	        Component parentSC = null;
	        	        int returnValSC = chooserSC.showOpenDialog(parentSC);
	        	        if(returnValSC == JFileChooser.APPROVE_OPTION) {
	        	            inputDirSC = chooserSC.getSelectedFile().getPath();
	        	            File dirSC = new File(inputDirSC);
	        	            String [] fileListSC = dirSC.list(new FilenameFilter() {
	        	                @Override
	        	                public boolean accept(File pathname, String name) {
	        	                    //name = pathname.getName().toLowerCase();
	        	                    String filePath = pathname.toString() + "\\" + name;
	        	                    File fileP = new File(filePath);
	        	                    
	        	                    return ((fileP.isFile()) && (name != null));
	        	                }
	        	            });
	        	            List<String> fLSC = Arrays.asList(fileListSC);
	        	            //Checking if folder contains Mapper.exe and MapperX.exe
	        	            if (!(fLSC.contains("MapperX.exe"))) {
	        	            	JOptionPane.showMessageDialog(null, "MapperX.exe not found. .map files won't be considered.", "", JOptionPane.INFORMATION_MESSAGE);
	        	            	break;
	        	            }
	        	            else {
	        	            	m2 = inputDirSC + "\\MapperX.exe";
	        	            }
	        	            
	        	        }
	        	        else if (returnValSC == JFileChooser.CANCEL_OPTION) {
	        	            //System.out.println("No folder selected.");
	        	            JOptionPane.showMessageDialog(null, "No folder selected. .map files won't be considered.", "", JOptionPane.INFORMATION_MESSAGE);
	        	            break;
	        	        }
	        		}
	    			try {
	        			//String fout = f.substring(0, f.length() - 4) + "_MigrateCopy.map";
	    				String fout = f;
	        			
	        			//String foutmxl = f.substring(0, f.length() - 4) + "_MigrateCopy.mxl";
	    				String foutmxl = f.substring(0, f.length() - 4) + ".mxl";
	        			
	        			int cnt;
	        			cnt = 0;
	        			String l;
	        			String command2;
	        			String foutmxlF = f.substring(0, f.length() - 4) + "_MF.mxl";
	        			/*String command2 = "copy \"" + inputDir + "\\" + dname + "\\" + f + "\" \"" + inputDir + "\\" + dname + "\\" + fout + "\"";
	        			String[] command = {"cmd", "/k", command2};
	        			ProcessBuilder p1 = new ProcessBuilder(command);
	        			p1.redirectOutput(Redirect.INHERIT);
	        			p1.redirectError(Redirect.INHERIT);
	        			Process p1p = p1.start();
	        			p1p.waitFor();*/
	        			
	        			try {
	        				command2 = "\"\"" + m2 + "\"" + " -s 24 "+ inputDir + "\\" + dname + "\\" + fout + "\"";
	        				String[] command4 = {"cmd", "/k", command2};
	        				ProcessBuilder p3 = new ProcessBuilder(command4);
	        				p3.redirectOutput(Redirect.INHERIT);
	        				p3.redirectError(Redirect.INHERIT);
	        				Process p3p = p3.start();
	        				p3p.waitFor();
	            			
	        			}
	        			catch (Exception e) {}
	        			
	        			
	        			dirFlag = 1;
	        			
	    			}
	    			catch (Exception e) {}
	    			
	    			
	    		}
     	
			}
		}
        
	    for (String ds: dL) {
	    	File d = new File(inputDir + "\\" + ds);
	    	String dname = d.getName();
	    	//JOptionPane.showMessageDialog(null, dname, "", JOptionPane.INFORMATION_MESSAGE);
            
	    	fileList = d.list(new FilenameFilter() {
	            @Override
	            public boolean accept(File pathname, String name) {
	                String filePath = pathname.toString() + "\\" + name;
	                File fileP = new File(filePath);
	                
	                return ((fileP.isFile()) && (name != null));
	            }
	    	});
	    	
	    	for (String f : fileList) {
	    		if (f.endsWith(".mxl")) {
	    			maps.add(inputDir + "\\" + dname + "\\" + f);
	    			DIRs.add(inputDir + "\\" + dname);
	    			
	    		}
	    	}
	    }
		
        
        if (maps.size() == 0) {
        	JOptionPane.showMessageDialog(null, "No maps found.", "", JOptionPane.INFORMATION_MESSAGE);
        	System.exit(0);
        }
        
        
        
        
        Iterator<String> mapIterator = maps.iterator();
        Iterator<String> DIRIterator = DIRs.iterator();
        
        
        //Input user name to update in Precession rule.
        //userN = JOptionPane.showInputDialog("Leave empty if you don't want presession rule to be updated.\nPlease enter your name: ");
        userN = "";
        
        //Call migration tool QualityCheck for every map. Program takes 4 arguments
        //1) MXL
        //2) Output Directory
        //3) Character Encoding
        //4) User Name
        while (mapIterator.hasNext()) {
            
            map = mapIterator.next();
            out = DIRIterator.next();
            arguments[0] = map;
            arguments[1] = out;
            FreeFormat.main(arguments);
            try {
            	File delmap = new File(map);
            	delmap.delete();
            	File newmap = new File(map.replace(".mxl", "_con.mxl"));
            	newmap.renameTo(delmap);
            }
            catch(Exception e) {
            }
            
            
        }
        
        /*try {
        	File ps = new File(inputDir + "\\" + "possyntax.txt");
        	ps.delete();
        }
        catch(Exception e) {
        }*/
        String finalMessage = "Please find migrated maps in " + inputDir + " folder.";
        
        
        //Open folder with migrated maps.
        try {
        	JOptionPane.showMessageDialog(null, finalMessage, "", JOptionPane.INFORMATION_MESSAGE);
            File folderName = new File(inputDir);
            Desktop desktop = Desktop.getDesktop();
            desktop.open(folderName);
        	
        }
        catch (Exception e) {
        }
    }
}