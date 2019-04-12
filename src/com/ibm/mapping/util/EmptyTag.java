package com.ibm.mapping.util;

/*import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.output.*;*/
import org.apache.log4j.Logger;
import java.io.*;
import java.util.*;


public class EmptyTag {
	
	//log variable 
	static Logger log = Logger.getLogger(EmptyTag.class.getName());
	
      public static void generate_EmptyTag(String inputDirectory, String outputDirectory, String tagType)
      {
    	String line;
  		int flagOutput = 0;
  		int flag1 = 0;
  		int flag2 = 0;
  		int particleNo = 0;
        ArrayList<String> maps = new ArrayList<String>();
          
        //System.out.println("tagType=>"+tagType);
        
  		File inputDir = new File(inputDirectory);

  		// Check if output directory exists.
  		File outputDir = new File(outputDirectory);

  		if (!outputDir.exists()) {
  			// Create a output directory.
  			outputDir.mkdirs();
  		}
  		FileUtils.cleanDirectory(outputDir);
  		// Check if input directory exists.
  		if (inputDir.exists()) {
  			
  	        String [] fileList = inputDir.list(new FilenameFilter() {
  	            @Override
  	            public boolean accept(File pathname, String name) {
  	                String filePath = pathname.toString() + "\\" + name;
  	                File fileP = new File(filePath);
  	                
  	                return ((fileP.isFile()) && (name != null));
  	            }
  	        });
  	        
  	        for (String f : fileList) {
  	            if (f.toLowerCase().endsWith(".mxl")) {
  	            	maps.add(inputDir + "\\" + f);
  	            }
  	        
  	        }
  	        
  	        if(maps.size() > 0) {
  	        	
  	  		Deque<String> mxlDEQ = new ArrayDeque<String>();
  			Deque<String> mxlDEQTEMP = new ArrayDeque<String>();
  			
  			
  			for (String inputPathMXL : maps) {
  				particleNo = 0;
  				
  				try {
  					File input_map = new File(inputPathMXL);
  					//String outputPathMXL = inputPathMXL.substring(0, inputPathMXL.length() - 4) + "_OUT.mxl";
  					String outputPathMXL = outputDirectory + File.separator+input_map.getName().substring(0, input_map.getName().length() - 4) + "_OUT.mxl";
  					FileInputStream iFIS = new FileInputStream(inputPathMXL);
  					InputStreamReader mapReader = new InputStreamReader(iFIS);
  					BufferedReader mxl = new BufferedReader(mapReader);
  					
  					FileOutputStream oFOS = new FileOutputStream(outputPathMXL);
  					OutputStreamWriter mapWriter = new OutputStreamWriter(oFOS);
  					BufferedWriter mxlOut = new BufferedWriter(mapWriter);
  					
  					particleNo = 0;
  					flagOutput = 0;
  					
  					
  					if (tagType.equals("<EmptyTag/>")) 
  					{
  						while (((line = mxl.readLine()) != null)) {
  							mxlDEQ.push(line);
  							if (line.contains("<OUTPUT>")) {
  								flagOutput = 1;
  							}
  							if (flagOutput == 1) {
  								if (line.contains("<RecordType>pcdata</RecordType>")) {
  									flag1 = 0;
  									String popline = "";
  									while((flag1 == 0) || (!(popline.contains("<Min>")))) {
  										popline = mxlDEQ.pop();
  										mxlDEQTEMP.push(popline);
  										if (popline.contains("<XMLRecord>"))
  											flag1 = 1;
  									}
  									popline = popline.replaceAll("<Min>0</Min>", "<Min>1</Min>");
  									mxlDEQ.push(popline);
  									mxlDEQTEMP.pop();
  									while (!(mxlDEQTEMP.isEmpty())) {
  										mxlDEQ.push(mxlDEQTEMP.pop());
  									}
  									
  								}
  							}
  						}
  						
  						while(!(mxlDEQ.isEmpty())) {
  							line = mxlDEQ.removeLast();
  							mxlOut.write(line);
  							mxlOut.newLine();
  						}
  					}
  					else {
  						while (((line = mxl.readLine()) != null)) {
  							mxlDEQ.push(line);
  							if (line.contains("<OUTPUT>")) {
  								flagOutput = 1;
  							}
  							if (flagOutput == 1) {
  								if (line.contains("<RecordType>pcdata</RecordType>")) {
  									flag1 = 0;
  									String popline = "";
  									while (flag1 != 2) {
  										popline = mxlDEQ.pop();
  										mxlDEQTEMP.push(popline);
  										//if (popline.contains("<XMLRecord>"))
  										//	flag1 = 1;
  										if (popline.contains("<Min>"))
  											flag1 += 1;
  									}
  									
  									popline = popline.replaceAll("<Min>0</Min>", "<Min>1</Min>");
  									mxlDEQ.push(popline);
  									mxlDEQTEMP.pop();
  									flag1 = 0;
  									while (flag1 == 0) {
  										popline = mxlDEQTEMP.pop();
  										if (!(popline.contains("<XMLRecord>")))
  											mxlDEQ.push(popline);
  										else
  											flag1 = 1;
  									}
  									
  									
  									popline = popline.replaceAll("<XMLRecord>", "");
  									mxlDEQ.push(popline);
  									particleNo += 1;
  									mxlDEQ.push("<XMLParticleGroup>");
  									mxlDEQ.push("<ID>" + particleNo + "</ID>");
  									mxlDEQ.push("<Name>" + particleNo + "</Name>");
  									mxlDEQ.push("<Description></Description>");
  									mxlDEQ.push("<Active>1</Active>");
  									mxlDEQ.push("<ChildCount>1</ChildCount>");
  									mxlDEQ.push("<Note></Note>");
  									mxlDEQ.push("<Min>1</Min>");
  									mxlDEQ.push("<Max>1</Max>");
  									mxlDEQ.push("<PromoteGroup>yes</PromoteGroup>");
  									mxlDEQ.push("<ParticleType>or</ParticleType>");
  									mxlDEQ.push("<XMLRecord>");
  									
  									
  									while (!(mxlDEQTEMP.isEmpty())) {
  										popline = mxlDEQTEMP.pop();
  										if (popline.startsWith("<Name>"))
  											popline = "<Name>_pcd_" + particleNo + "</Name>";
  										mxlDEQ.push(popline);
  									}
  									flag2 = 0;
  									while (((flag2 == 0) && (line = mxl.readLine()) != null)) {
  										
  										if (line.contains("</XMLRecord>")) {
  											flag2 = 1;
  											line = line.replaceAll("</XMLRecord>", "</XMLRecord></XMLParticleGroup>");
  										}
  										
  										mxlDEQ.push(line);
  									}
  										
  									
  								}
  							}
  							
  						}
  						while(!(mxlDEQ.isEmpty())) {
  							line = mxlDEQ.removeLast();
  							mxlOut.write(line);
  							mxlOut.newLine();
  						}
  					}
  					mxl.close();
  					mxlOut.close();
  					
  		 		}
  				catch (Exception e) {
  					e.printStackTrace();
  					log.error(e.getMessage());
  				}
  			}
  	        	
  	        	
  	        }

  		}else {
  			log.error("The input directory '" + inputDirectory
  					+ "' does not exists.");
  		}
  	
      }
}
