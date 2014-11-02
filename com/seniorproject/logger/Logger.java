package com.seniorproject.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.net.*;
import java.io.*;


public class Logger {

	private File logFile = new File("com/seniorproject/logFile.txt");

    public void writeToLog(String string) {
		try {
   			BufferedWriter bw = new BufferedWriter(new FileWriter(logFile.getAbsoluteFile(), true));
   			bw.newLine();
   			bw.write(string);
   			bw.close();
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
		} 
    }

    public int getNumberLines(){
    	try {
    		LineNumberReader lnr = new LineNumberReader(new FileReader(logFile));
			lnr.skip(Long.MAX_VALUE);
			int temp = lnr.getLineNumber() + 1;
			lnr.close();
			return temp;
			
		} catch (Exception e){
			System.err.println(e.getMessage());
		}
		return 0;
    }

    public String readFromLog() {
    	String log = "";
    	try {
    		BufferedReader br = new BufferedReader(new FileReader(logFile));
 			String line = null;

 			while ((line = br.readLine()) != null) 
 			{
   				log += line + "\n";
 			}
 			return log;
 		}
 		catch (Exception e) {
 			System.err.println(e.getMessage());
 		}
 		return null;
    }
}