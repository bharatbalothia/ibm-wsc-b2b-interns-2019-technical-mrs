//Created by Sukanya Dasgupta on July 25, 2015.

package com.ibm.mapping.qa;

import java.io.*;
import java.lang.ProcessBuilder.Redirect;
import java.util.*;

import org.apache.log4j.Logger;

//import java.util.spi.CalendarDataProvider;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.ibm.mapping.servlet.Constants;
import com.ibm.mapping.util.FileUtils;



public class KHQualityTool {
	//log variable 
	static Logger log = Logger.getLogger(KHQualityTool.class.getName());

	public static String validate_WP_For_QA(String inputDirectory,String outputDirectory,String projectType) {
		//System.out.println("inputDirectory="+inputDirectory);
		File inputDir = new File(inputDirectory);
		String dname = inputDir.getName();
		//System.out.println("dname="+dname);

  		// Check if output directory exists.
  		File outputDir = new File(outputDirectory);

  		if (!outputDir.exists()) {
  			// Create a output directory.
  			outputDir.mkdirs();
  		}
  		FileUtils.cleanDirectory(outputDir);
  		// Check if input directory exists.
  		//System.out.println("inputDir.exists()=>"+inputDir.exists());
  		if(inputDir.exists()) {
	    String characterEncoding = "Cp1252 (Cp1252)";
	    String [] CE = {"Cp1252 (Cp1252)", "UTF8 (UTF8)"};
	    ArrayList<ReviewComment> workPacketReviewList = new ArrayList<ReviewComment>();
	    ArrayList<ReviewComment> mapReviewList = new ArrayList<ReviewComment>();
	    	    
	    Boolean mapFound=false;
	    Date date = new Date();
	    String m1, m2;
	    String TXOname="";
	    m1 = Constants.DEFAULT_MAPPER_EXE;
	    m2 = Constants.MAPPER_EXE;
	    String userN;
	    int flagMapper = 0;
	    DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	    DateFormat workPacketFormat = new SimpleDateFormat("yyyyMMdd");
	    String timestamp = dateFormat.format(date);
	    String workPacketStamp = workPacketFormat.format(date);
	    //String outputDir = inputDir + "\\" + "SIMaps" + timestamp;
	    String dirPath, dirName;
	    int dirFlag = 0;
	    String map, out;
	    String [] arguments = new String[5];
	    String inputDirSC;
	    File dir = new File(inputDirectory);
	    String[] fileList;
		int flagDIRNAME = 0;
		int flagDIRDATE = 0;
		int flagTXO = 0;
		int flagMAP = 0;
		int flagMRS = 0;
		int flagDIFF = 0 ;
		int flagDIFFREPORT = 0;
		int flagRSC = 0;
		int flagMapper2 = 1;
		int reviewCount = 0;
		String changedDname = "";
		
		
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
        //System.out.println("dl size = " + dL.size());
	    //Convert maps to MXLs.
	    
	    for (String ds: dL) {
	    	flagDIRNAME = 0;
	    	flagDIRDATE = 0;
	    	File d = new File(inputDirectory + "\\" + ds);
	    	//String dname = d.getName(); //Workpacekt Name 
	    	//System.out.println("dname="+dname);
	    	//JOptionPane.showMessageDialog(null, dname, "", JOptionPane.INFORMATION_MESSAGE);
            // FileWriter for report
			try {
				//OutputStreamWriter reportWriter = new OutputStreamWriter(new FileOutputStream(reviewReport), "UTF-8");
				//BufferedWriter for report
				//BufferedWriter reviewOut = new BufferedWriter(reportWriter);
				try{
					//reviewOut.write("******************REVIEW REPORT******************");
					//reviewOut.newLine();
					//reviewOut.newLine();
				}catch(Exception e){
				}
				ReviewComment workPacketReview = new ReviewComment();
				//workPacketReview.addReviewComment("******************REVIEW REPORT******************");
				
				
				flagDIRNAME = 0;
				flagTXO = 0;
				flagMRS = 0;
				flagDIFF = 0;
				flagMAP = 0;
				flagDIFFREPORT = 0;
				flagRSC = 0;
				reviewCount = 0;
				
				String folderDate = dname.substring(dname.length()-8);
				//System.out.println("folderDate="+folderDate);
				if ((folderDate.matches("\\d{8}"))) {
					//System.out.println("match");
					int yy = Integer.parseInt(folderDate.substring(0, 4));
					if(yy >= 2000 && yy <= 3000){
						//JOptionPane.showMessageDialog(null, yy);
						int mm = Integer.parseInt(folderDate.substring(4,6));
						if(mm >= 1 && mm <= 12){	
							//JOptionPane.showMessageDialog(null, mm);
							int dd = Integer.parseInt(folderDate.substring(6,8));
							if(dd >= 1 && dd <=31){
								//JOptionPane.showMessageDialog(null, dd);
								flagDIRNAME = 1;
							}
						}
					
					
					}
					if (flagDIRNAME == 1) {
						if (!(folderDate.equals(workPacketStamp))) {
							flagDIRDATE = 2;
							
						}
					}
					if(!(flagDIRNAME ==1)){
						//JOptionPane.showMessageDialog(null, "Date Format is wrong");
					}
					
					
					// check folder name
					//JOptionPane.showMessageDialog(null, "BLUB");
					
				}
				//System.out.println("flagDIRNAME="+flagDIRNAME);
				//System.out.println("flagDIRDATE="+flagDIRDATE);
				if (flagDIRNAME == 0) {
						reviewCount++;
						workPacketReview.addReviewComment(reviewCount + ". Directory Name: \"" + dname + "\" doesn't match the requirement.");
				}
				else if (flagDIRDATE == 2) {
						reviewCount++;
						workPacketReview.addReviewComment(reviewCount + ". Directory Name: \"" + dname + "\" doesn't have the current date.");
				}

				//System.out.println("testting....");
				
				String mapName = "";
				if (flagDIRNAME == 1)
					mapName = dname.substring(0, dname.length() - 9) + ".mxl";
				
				//System.out.println("mapName="+mapName);
				
				fileList = dir.list(new FilenameFilter() {
					@Override
					public boolean accept(File pathname, String name) {
						String filePath = pathname.toString() + "\\" + name;
						File fileP = new File(filePath);
						
						return ((fileP.isFile()) && (name != null));
					}
				});
				
				//System.out.println("fl size= " + fileList.length);
				//Check if fileList contains a file ending with .txo, .map/.mxl
				mapFound = false;
				for (String f: fileList) {
					if (f.toLowerCase().endsWith(".bak")) {
						String bakFileName = inputDir + "\\" + ds + "\\" + f;
						File bakFile = new File(bakFileName);
						try {
							bakFile.delete();
						}
						catch(Exception e) {}
					}
					if  (f.toLowerCase().endsWith(".txo")) {
						flagTXO = 1;
						TXOname = f;
					}
					if(f.toLowerCase().startsWith("mrs") && (f.toLowerCase().endsWith(".xls")|| (f.toLowerCase().endsWith(".xlsx")))){
						flagMRS = 1;
					}
					if(f.toLowerCase().startsWith("diff") && (f.toLowerCase().endsWith(".xls")|| (f.toLowerCase().endsWith(".xlsx")))){
						flagDIFF = 1;
					}
					if (f.toLowerCase().contains("diff") && (f.toLowerCase().endsWith("html"))) {
						flagDIFFREPORT = 1;
					}
					if (f.toLowerCase().contains("rsc") && (f.toLowerCase().contains("notes")) && (f.toLowerCase().endsWith("txt"))) {
						flagRSC = 1;
					}

					if ((f.toLowerCase().endsWith(".mxl") && !(mapFound))) {
						flagMAP = 1;
						mapFound = true;
						if ((!(mapName.equals(f))) && (flagDIRNAME == 1)) {
							try {
								reviewCount++;
								//reviewOut.write(reviewCount + ". Map Name: \"" + f + "\" doesn't match directory name. Map name changed to \"" + mapName + "\"");
								workPacketReview.addReviewComment(reviewCount + ". Map Name: \"" + f + "\" doesn't match directory name. Map name changed to \"" + mapName + "\"");
								//reviewOut.newLine();
								//reviewOut.newLine();
							}
							catch(Exception e) {}
							/*try {
								String command2 = "copy \"" + inputDir + "\\" + dname + "\\" + f + "\" \"" + inputDir + "\\" + dname + "\\"  + mapName + "\"";
								String[] command5 = {"cmd", "/k", command2};
								ProcessBuilder p4 = new ProcessBuilder(command5);
								p4.redirectOutput(Redirect.INHERIT);
								p4.redirectError(Redirect.INHERIT);
								Process p4p = p4.start();
								p4p.waitFor();
							}
							catch(Exception e) {}*/
							copyFile(inputDirectory + "\\" + f,inputDirectory + "\\" + mapName);  
						}
						if (flagDIRNAME == 0) {
							mapName = f;
							changedDname = mapName.replaceAll(".mxl", "");
							changedDname = changedDname + "_" + workPacketStamp;
						}
						if (flagDIRDATE == 2) {
							changedDname = dname.substring(0, dname.length() - 9);
							changedDname = changedDname + "_" + workPacketStamp;
						}
						String foutmxlF = mapName.replaceAll(".mxl", "_MF.mxl");
						/*try {
							String command2 = "copy \"" + inputDir + "\\" + dname + "\\" + mapName + "\" \"" + inputDir + "\\" + dname + "\\"  + foutmxlF + "\"";
							String[] command6 = {"cmd", "/k", command2};
							ProcessBuilder p5 = new ProcessBuilder(command6);
							p5.redirectOutput(Redirect.INHERIT);
							p5.redirectError(Redirect.INHERIT);
							Process p5p = p5.start();
							p5p.waitFor();
						}
						catch(Exception e) {}*/
						copyFile(inputDirectory + "\\" + mapName,inputDirectory + "\\" + foutmxlF);  
						if ((flagDIRNAME == 0) || (flagDIRDATE == 2)) {
							try 
							{
								String mapDIRNAME = inputDirectory.replace("\\", "/");
								File mapDIR = new File(mapDIRNAME /*+ "/" + dname*/);
								File newMAPDIR = new File(outputDir.getParent() + File.separator + changedDname);
								outputDirectory = outputDir.getParent() + File.separator + changedDname;
								//newMAPDIR.mkdir();
								//System.out.println("outputDir="+outputDir.getAbsolutePath());
								//System.out.println("new outputDir="+newMAPDIR.getAbsolutePath());
								outputDir.renameTo(newMAPDIR);
								dname = changedDname;
								workPacketReview.appendReviewComment(0, "Directory name changed to " + changedDname + ".");
								//JOptionPane.showMessageDialog(null, "INIT: " + mapDIR.getPath());
								//JOptionPane.showMessageDialog(null, "CHANGED: " + newMAPDIR.getPath());
							}
							catch (Exception e) {
								//JOptionPane.showMessageDialog(null, e.getMessage());
							}
						}
						maps.add(inputDirectory + "\\" + foutmxlF);
						File oDir = new File(inputDirectory);
						//oDir.mkdir();
						DIRs.add(oDir.getPath());
					}
					if ((f.toLowerCase().endsWith(".map")) && !(mapFound)) {
						flagMAP =1;
						if (flagDIRNAME == 0) {
							mapName = f.replaceAll(".map",".mxl");
							changedDname = mapName.replaceAll(".mxl", "");
							changedDname = changedDname + "_" + workPacketStamp;
						}
						if (flagDIRDATE == 2) {
							changedDname = dname.substring(0, dname.length() - 9);
							changedDname = changedDname + "_" + workPacketStamp;
						}
						
						if (flagMapper2 == 1) {
							try {
								mapFound = true;
								//String fout = f.substring(0, f.length() - 4) + "_MigrateCopy.map";
								String fout = mapName.replaceAll(".mxl",".map");
								
								//String foutmxl = f.substring(0, f.length() - 4) + "_MigrateCopy.mxl";
								String foutmxl = mapName;
								
								int cnt;
								cnt = 0;
								String l;
								String foutmxlF = mapName.replaceAll(".mxl", "_MF.mxl");
								String command2 = "";
								/*if (flagDIRNAME == 1) {
									command2 = "copy \"" + inputDir + "\\" + dname + "\\" + f + "\" \"" + inputDir + "\\" + dname + "\\" + fout + "\"";
									String[] command = {"cmd", "/k", command2};
									ProcessBuilder p1 = new ProcessBuilder(command);
									p1.redirectOutput(Redirect.INHERIT);
									p1.redirectError(Redirect.INHERIT);
									Process p1p = p1.start();
									p1p.waitFor();
								}*/
								
								/*try {
									command2 = "\"\"" + m2 + "\"" + " -s 24 "+ inputDirectory + "\\" + dname + "\\" + f + "\"";
									String[] command4 = {"cmd", "/k", command2};
									System.out.println("command2="+command2);
									ProcessBuilder p3 = new ProcessBuilder(command4);
									p3.redirectOutput(Redirect.INHERIT);
									p3.redirectError(Redirect.INHERIT);
									Process p3p = p3.start();
									p3p.waitFor();
									
								}
								catch (Exception e) {}*/
								try {
			        				ProcessBuilder pb = new ProcessBuilder(m2, "-s","24", inputDirectory +  "\\" + f);
									pb.start().waitFor();
			        			}
			        			catch (Exception e) {log.error(e.getMessage());}
								
								
								if (flagDIRNAME == 1) {
									if (!(f.replaceAll(".map", ".mxl").equals(foutmxl))) {
										/*command2 = "copy \"" + inputDir + "\\" + dname + "\\" + f.replaceAll(".map", ".mxl") + "\" \"" + inputDir + "\\" + dname + "\\" + foutmxl + "\"";
										String[] command = {"cmd", "/k", command2};
										ProcessBuilder p1 = new ProcessBuilder(command);
										p1.redirectOutput(Redirect.INHERIT);
										p1.redirectError(Redirect.INHERIT);
										Process p1p = p1.start();
										p1p.waitFor();*/
										
										copyFile(inputDirectory + "\\" + f.replaceAll(".map", ".mxl"),inputDirectory + "\\" + foutmxl);
										
										/*command2 = "del \"" + inputDir + "\\" + dname + "\\"  + f.replaceAll(".map", ".mxl") + "\"";
										String[] command6 = {"cmd", "/k", command2};
										ProcessBuilder p5 = new ProcessBuilder(command6);
										p5.redirectOutput(Redirect.INHERIT);
										p5.redirectError(Redirect.INHERIT);
										Process p5p = p5.start();
										p5p.waitFor(); */
										reviewCount++; 
										//reviewOut.write(reviewCount + ". Map name changed to match directory name. Current map name: " + foutmxl);
										workPacketReview.addReviewComment(reviewCount + ". Map name changed to match directory name. Current map name: " + foutmxl);
										//reviewOut.newLine();
										//reviewOut.newLine();
									}
								}
								
								
								/*try {
									//JOptionPane.showMessageDialog(null, foutmxl, "", JOptionPane.INFORMATION_MESSAGE);
									command2 = "copy \"" + inputDir + "\\" + dname + "\\" + foutmxl + "\" \"" + inputDir + "\\" + dname + "\\"  + foutmxlF + "\"";
									String[] command5 = {"cmd", "/k", command2};
									ProcessBuilder p4 = new ProcessBuilder(command5);
									p4.redirectOutput(Redirect.INHERIT);
									p4.redirectError(Redirect.INHERIT);
									Process p4p = p4.start();
									p4p.waitFor();
								}
								catch (Exception e) {}*/
								
								copyFile(inputDirectory + "\\" + foutmxl,inputDirectory + "\\" + foutmxlF);   
								
								
								/*try {
									
									command2 = "del \"" + inputDir + "\\" + dname + "\\"  + fout + "\"";
									String[] command6 = {"cmd", "/k", command2};
									ProcessBuilder p5 = new ProcessBuilder(command6);
									p5.redirectOutput(Redirect.INHERIT);
									p5.redirectError(Redirect.INHERIT);
									Process p5p = p5.start();
									p5p.waitFor();
									if (!(fout.equals(f))) {
										command2 = "del \"" + inputDir + "\\" + dname + "\\"  + foutmxl + "\"";
										String[] command7 = {"cmd", "/k", command2};
										ProcessBuilder p6 = new ProcessBuilder(command7);
										p6.redirectOutput(Redirect.INHERIT);
										p6.redirectError(Redirect.INHERIT);
										Process p6p = p6.start();
										p6p.waitFor();
									}
									
								}
								catch (IOException e) {
									JOptionPane.showMessageDialog(null, e, "", JOptionPane.INFORMATION_MESSAGE);
								}*/
								
								if ((flagDIRNAME == 0) || (flagDIRDATE == 2)) {
									try {
										String mapDIRNAME = inputDirectory.replace("\\", "/");
										File mapDIR = new File(mapDIRNAME /*+ "/" + dname*/);
										File newMAPDIR = new File(outputDir.getParent() + File.separator + changedDname);
										outputDirectory = outputDir.getParent() + File.separator + changedDname;
										//newMAPDIR.mkdir();
										//System.out.println("outputDir="+outputDir.getAbsolutePath());
										//System.out.println("new outputDir="+newMAPDIR.getAbsolutePath());
										outputDir.renameTo(newMAPDIR);
										dname = changedDname;
										workPacketReview.appendReviewComment(0, "Directory name changed to " + changedDname + ".");
										//JOptionPane.showMessageDialog(null, "INIT: " + mapDIR.getPath());
										//JOptionPane.showMessageDialog(null, "CHANGED: " + newMAPDIR.getPath());
									}
									catch (Exception e) {
										//JOptionPane.showMessageDialog(null, e.getMessage());
									}
								}
								maps.add(inputDirectory + "\\" + foutmxlF);
								File oDir = new File(inputDirectory);
								//oDir.mkdir();
								DIRs.add(oDir.getPath());
								dirFlag = 1;
								
							}
							catch (Exception e) {}
						}
						
					} 
			
				}
				
				if (flagTXO == 0) {
					reviewCount++;
					//reviewOut.write(reviewCount + ". No TXO found.");
					workPacketReview.addReviewComment(reviewCount + ". No TXO found.");
					//reviewOut.newLine();
				}
				else {
					if (!(TXOname.substring(0, TXOname.length() - 4).equals(mapName.substring(0, mapName.length() - 4)))) {
						reviewCount++;
						//reviewOut.write(reviewCount + ". TXO name doesn't match map name.");
						workPacketReview.addReviewComment(reviewCount + ". TXO name doesn't match map name.");
						//reviewOut.newLine();
						//reviewOut.newLine();
					}
				}
				if(flagMAP == 0){
					reviewCount++;
					//reviewOut.write(reviewCount + ". No MAP or MXL is found.");
					workPacketReview.addReviewComment(reviewCount + ". No MAP or MXL is found.");
					//reviewOut.newLine();
					//reviewOut.newLine();
				}
				if(flagMRS == 0){
					reviewCount++;
					//reviewOut.write(reviewCount + ". No MRS found.");
					workPacketReview.addReviewComment(reviewCount + ". No MRS found.");
					//reviewOut.newLine();
					//reviewOut.newLine();
				}
				if ((flagDIFFREPORT == 0) && (projectType.equals("Migration Project"))) {
					reviewCount++;
					//reviewOut.write(reviewCount + ". No DIFF Report found.");
					workPacketReview.addReviewComment(reviewCount + ". No DIFF Report found.");
					//reviewOut.newLine();
					//reviewOut.newLine();
				}
				if ((flagDIFF == 0) && (projectType.equals("Migration Project"))) {
					reviewCount++;
					//reviewOut.write(reviewCount + ". No DIFF sheet found.");
					workPacketReview.addReviewComment(reviewCount + ". No DIFF sheet found.");
					//reviewOut.newLine();
					//reviewOut.newLine();
				}
				if (flagRSC == 0) {
					reviewCount++;
					//reviewOut.write(reviewCount + ". No RSC notes found.");
					workPacketReview.addReviewComment(reviewCount + ". No RSC notes found.");
					//reviewOut.newLine();
					//reviewOut.newLine();
				} 
				if (reviewCount == 0) {
					//reviewOut.write("No review comments found for workpacket files.");
					workPacketReview.addReviewComment("No review comments found for workpacket files.");
					//reviewOut.newLine();
					//reviewOut.newLine();
				}
				
				workPacketReview.setDirectoryName(dname);
				//reviewOut.write("*************************************************");
				//workPacketReview.addReviewComment("*************************************************");
				workPacketReviewList.add(workPacketReview);
				//JOptionPane.showMessageDialog(null, workPacketReview.getMapName(), "", JOptionPane.INFORMATION_MESSAGE);
				//reviewOut.close();
				//reportWriter.close();
			}
			catch(Exception e) {}
		}
	    if (maps.size() > 0) {
		    try {
			    String reviewReport = outputDir.getParent() + "\\" + "WorkPacketReviewReport.txt";
			    FileWriter reportWriter = new FileWriter(reviewReport, true);
		    	//OutputStreamWriter reportWriter = new OutputStreamWriter(new FileOutputStream(reviewReport), "UTF-8");
				//BufferedWriter for report
				BufferedWriter reviewOut = new BufferedWriter(reportWriter);
				
			    Iterator<ReviewComment> workPacketReviewIterator = workPacketReviewList.iterator();
		    	
				reviewOut.write("******************REVIEW REPORT******************");
				reviewOut.newLine();
				reviewOut.newLine();
				
		    	while (workPacketReviewIterator.hasNext()) {
		    		ReviewComment workPacketReview = workPacketReviewIterator.next();
		    		reviewOut.write("WORKPACKET: " + workPacketReview.getDirectoryName());
		    		reviewOut.newLine();
		    		reviewOut.newLine();
		    		ArrayList<String> comments = workPacketReview.getReviewComments();
		    		Iterator<String> commentsIterator = comments.iterator();
		    		String comment;
		    		while(commentsIterator.hasNext()) {
		    			comment = commentsIterator.next();
		    			reviewOut.write(comment);
		    			reviewOut.newLine();;
		    		}
		    		reviewOut.newLine();
		    		reviewOut.write("**************************************************");
		    		reviewOut.newLine();
		    		reviewOut.newLine();
		    	}
		    	reviewOut.close();
		    	reportWriter.close();
		    }
		    catch(Exception e) {
		    	
		    }
		    /*try {
		    	String reportOut = inputDir + "\\" + "MapReviewReport.txt";
		    	File reportOutFile = new File(reportOut);
		    	reportOutFile.createNewFile();
		    	OutputStreamWriter mapReportWriter = new OutputStreamWriter(new FileOutputStream(reportOut), "UTF-8");
		    	BufferedWriter mapReportOut = new BufferedWriter(mapReportWriter);
		    	mapReportOut.write("******************MAP REVIEW REPORT******************");
		    	mapReportOut.newLine();
		    	mapReportOut.newLine();
		    	mapReportOut.close();
		    	mapReportWriter.close();
		    }
		    catch(Exception e) {}*/
	    }
		
        /*for (String f : fileList) {
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
        			String fout = f.substring(0, f.length() - 4) + "_MigrateCopy.map";
        			String foutmxl = f.substring(0, f.length() - 4) + "_MigrateCopy.mxl";
        			int cnt;
        			cnt = 0;
        			String l;
        			String foutmxlF = f.substring(0, f.length() - 4) + ".mxl";
        			String command2 = "copy \"" + inputDir + "\\" + f + "\" \"" + inputDir + "\\" + fout + "\"";
        			String[] command = {"cmd", "/k", command2};
        			ProcessBuilder p1 = new ProcessBuilder(command);
        			p1.redirectOutput(Redirect.INHERIT);
        			p1.redirectError(Redirect.INHERIT);
        			Process p1p = p1.start();
        			p1p.waitFor();
        			
        			/*try {
        				command2 = "\"" + m1 + "\"" + " -s 24 "+ inputDir + "\\" + fout;
        				String[] command3 = {"cmd", "/k", command2};
        				ProcessBuilder p2 = new ProcessBuilder(command3);
        				p2.redirectOutput(Redirect.INHERIT);
        				p2.redirectError(Redirect.INHERIT);
        				Process p2p = p2.start();
        				p2p.waitFor();
        			}
        			catch (Exception e) {}
        			try {
        				command2 = "\"\"" + m2 + "\"" + " -s 24 "+ inputDir + "\\" + fout + "\"";
        				String[] command4 = {"cmd", "/k", command2};
        				ProcessBuilder p3 = new ProcessBuilder(command4);
        				p3.redirectOutput(Redirect.INHERIT);
        				p3.redirectError(Redirect.INHERIT);
        				Process p3p = p3.start();
        				p3p.waitFor();
            			
        			}
        			catch (Exception e) {}
        			
        			try {
        				//JOptionPane.showMessageDialog(null, foutmxl, "", JOptionPane.INFORMATION_MESSAGE);
        				command2 = "copy \"" + inputDir + "\\" + foutmxl + "\" \"" + inputDir + "\\" + foutmxlF + "\"";
        				String[] command5 = {"cmd", "/k", command2};
        				ProcessBuilder p4 = new ProcessBuilder(command5);
        				p4.redirectOutput(Redirect.INHERIT);
        				p4.redirectError(Redirect.INHERIT);
        				Process p4p = p4.start();
        				p4p.waitFor();
        			}
        			catch (Exception e) {}
        			
        			
        			try {
        				
        				command2 = "del \"" + inputDir + "\\" + fout + "\"";
        				String[] command6 = {"cmd", "/k", command2};
        				ProcessBuilder p5 = new ProcessBuilder(command6);
        				p5.redirectOutput(Redirect.INHERIT);
        				p5.redirectError(Redirect.INHERIT);
        				Process p5p = p5.start();
        				p5p.waitFor();
        				command2 = "del \"" + inputDir + "\\" + foutmxl + "\"";
        				String[] command7 = {"cmd", "/k", command2};
        				ProcessBuilder p6 = new ProcessBuilder(command7);
        				p6.redirectOutput(Redirect.INHERIT);
        				p6.redirectError(Redirect.INHERIT);
        				Process p6p = p6.start();
        				p6p.waitFor();
        			}
        			catch (IOException e) {
        				JOptionPane.showMessageDialog(null, e, "", JOptionPane.INFORMATION_MESSAGE);
        			}
        			
        			
        			
        		}
        		catch (Exception e) {
        			
        		}
        		
        	}
        }
        */
        /*fileList = dir.list(new FilenameFilter() {
            @Override
            public boolean accept(File pathname, String name) {
                String filePath = pathname.toString() + "\\" + name;
                File fileP = new File(filePath);
                
                return ((fileP.isFile()) && (name != null));
            }
        });
        
        fL = Arrays.asList(fileList);
        
        //Checking for MXL files
        for (String f : fileList) {
            if (f.toLowerCase().endsWith(".mxl")) {
                if (dirFlag == 0) {
                    new File(outputDir).mkdir();
                    dirFlag = 1;
                }
                dirName = f.substring(0, f.length() - 4);
               	maps.add(inputDir + "\\" + f);
                dirPath = outputDir + "\\" + dirName;
                new File(dirPath).mkdir();
                DIRs.add(dirPath);
              
            }
        }*/
        /*if (dirFlag == 0) {
            JOptionPane.showMessageDialog(null, "No maps found.", "", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }*/
        
        if (maps.size() == 0) {
        	//JOptionPane.showMessageDialog(null, "No maps found.", "", JOptionPane.INFORMATION_MESSAGE);
        	//return;//System.exit(0);
        }
        
        //Select character encoding (Cp1252/UTF8)
        /*Object selected = JOptionPane.showInputDialog(null, "Select character encoding.", "Selection", JOptionPane.DEFAULT_OPTION, null, CE, "0");
        if (selected != null) {
            characterEncoding = selected.toString();
        }
        else {
        	JOptionPane.showMessageDialog(null, "Action aborted.", "", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
        */
        
        
        
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
            arguments[1] = outputDirectory;
            arguments[2] = characterEncoding;
            QualityCheck.main(arguments);
            try {
            	File delmap = new File(map);
            	delmap.delete();
            }
            catch(Exception e) {
            }
            
        }
        
        try {
        	File ps = new File(inputDir + "\\" + "possyntax.txt");
        	ps.delete();
        }
        catch(Exception e) {
        }
        
        log.info("Please find corrected maps and review reports in " + outputDir + " folder.");
	    }else {
			log.error("The input directory '" + inputDirectory
					+ "' does not exists.");
		}
		return outputDirectory;
    }
	
    public static void copyFile(String aPath, String bPath) {
    	InputStream inStream = null;
	    OutputStream outStream = null;
 
    	try{
    		
    		File afile =new File(aPath);
    	    File bfile =new File(bPath);
            
    	    if(!bfile.exists()) {
    	    
    	    inStream = new FileInputStream(afile);
    	    outStream = new FileOutputStream(bfile);
 
    	    byte[] buffer = new byte[1024];
 
    	    int length;
    	    //copy the file content in bytes 
    	    while ((length = inStream.read(buffer)) > 0){
 
    	    	outStream.write(buffer, 0, length);
 
    	    }
 
    	    inStream.close();
    	    outStream.close();
    	    
    	    }
 
    	    //System.out.println("Map is copied successful!");
 
    	}catch(IOException e){
    		e.printStackTrace();
    		log.error(e.getMessage());
    	}
	}
}