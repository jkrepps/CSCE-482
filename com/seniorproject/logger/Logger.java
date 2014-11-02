package com.seniorproject.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.net.*;
import java.io.*;


public class Logger {

	File logFile = new File("com/seniorproject/logFile.txt");

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
}