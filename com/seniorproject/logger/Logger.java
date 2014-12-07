package com.seniorproject.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.net.*;
import java.io.*;


public class Logger {
  //private File logFile = new File("com/seniorproject/logFile.txt");

  public void writeToLog(int gameId, String string) {
		try {
      File logFile =  new File("com/seniorproject/" + Integer.toString(gameId) + ".txt");
      if(logFile.exists() && !logFile.isDirectory())
      {
   		 BufferedWriter bw = new BufferedWriter(new FileWriter(logFile.getAbsoluteFile(), true));
   		 bw.newLine();
   		 bw.write(string);
   		 bw.close();
      }
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
		} 
  }

  public int getNumberLines(int gameId){
    try {
      File logFile = new File("com/seniorproject/" + Integer.toString(gameId) + ".txt");
      System.out.println("HI from get number lines");
      if(logFile.exists() && !logFile.isDirectory())
      {
    	 LineNumberReader lnr = new LineNumberReader(new FileReader(logFile));
			 lnr.skip(Long.MAX_VALUE);
			 int temp = lnr.getLineNumber() + 1;
			 lnr.close();
       System.out.println("Temp = " + temp);
       //if(temp == 0) return 1;
       return temp + 1;	
      }
		} catch (Exception e){
			System.err.println(e.getMessage());
		}
		return 1;
  }

  public String readFromLog(int gameId) {
    String log = "";
    try {
      File logFile = new File("com/seniorproject/" + Integer.toString(gameId) + ".txt");
      System.out.println("HI from read from log");
      if(logFile.exists() && !logFile.isDirectory())
      {
    	 BufferedReader br = new BufferedReader(new FileReader(logFile));
 			  String line = null;
 			  while ((line = br.readLine()) != null) 
 			  {
          System.out.println("HI FROM inside the log: " + line);
   		   	log += line + "\n";
 			  }
 			  return log;
      }
 		} catch (Exception e) {
 			System.err.println(e.getMessage());
 		}
 		return " ";
  }




  public void writeToChatLog(int gameId, String string) {
      try {
        File logFile =  new File("com/seniorproject/chat" + Integer.toString(gameId) + ".txt");
        if(logFile.exists() && !logFile.isDirectory())
        {
         BufferedWriter bw = new BufferedWriter(new FileWriter(logFile.getAbsoluteFile(), true));
         bw.newLine();
         bw.write(string);
         bw.close();
        }
      } catch (Exception e1) {
        System.err.println(e1.getMessage());
      } 
    }

    public int getNumberLinesChat(int gameId){
      try {
        File logFile = new File("com/seniorproject/chat" + Integer.toString(gameId) + ".txt");
       // System.out.println("HI from get number lines");
        if(logFile.exists() && !logFile.isDirectory())
        {
         LineNumberReader lnr = new LineNumberReader(new FileReader(logFile));
         lnr.skip(Long.MAX_VALUE);
         int temp = lnr.getLineNumber() + 1;
         lnr.close();
         //System.out.println("Temp = " + temp);
         //if(temp == 0) return 1;
         return temp + 1; 
        }
      } catch (Exception e){
        System.err.println(e.getMessage());
      }
      return 1;
    }

    public String readFromChatLog(int gameId) {
      String log = "";
      try {
        File logFile = new File("com/seniorproject/chat" + Integer.toString(gameId) + ".txt");
       // System.out.println("HI from read from log");
        if(logFile.exists() && !logFile.isDirectory())
        {
         BufferedReader br = new BufferedReader(new FileReader(logFile));
          String line = null;
          while ((line = br.readLine()) != null) 
          {
            //System.out.println("HI FROM inside the log: " + line);
            log += line + "\n";
          }
          return log;
        }
      } catch (Exception e) {
        System.err.println(e.getMessage());
      }
      return " ";
    }

}