package address.view;

//package com.example.remunerativeindustries;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class Network 
{
	private static final long serialVersionUID = -7060210544600464481L;  
	private PrintWriter printwriter;
	private String message = "";
	private String user;
	private String pass;
	private String serverIp;
	private int PortNo;
	PrintWriter out;
	BufferedReader in;
	BufferedReader stdIn;
	Socket echoSocket;
	
    public Network(String u, String p, String sIp, int pNo) { // initialization function
	
		user = u;
		pass = p;
		serverIp = sIp;
		PortNo = pNo;
    }
    public String getUSer() {
    	return user;
    }
    public void SetNetInfo(String u, String p, String sIp, int pNo)
    {
    	user = u;
		pass = p;
		serverIp = sIp;
		PortNo = pNo;
    }
	public String StartConnection()
	{
		String loginStatement = "Incorrect log in";
		try {
            echoSocket = new Socket(serverIp, PortNo);
            out =
                new PrintWriter(echoSocket.getOutputStream(), true);
            in =
                new BufferedReader(
                    new InputStreamReader(echoSocket.getInputStream()));
            stdIn =
                new BufferedReader(
                    new InputStreamReader(System.in));
        
            String userInput;
            message = "name" + "\t"+user + "\t" + pass;
			out.println(message);
			
			
			
			
			System.out.println("made it to here?");
			loginStatement = in.readLine();
			System.out.println("output is " + loginStatement);
			//System.out.println(loginStatement);
			
			return loginStatement;
			
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host ");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection ");
            System.exit(1);
        }
		return loginStatement;
	}
	public String getUser()
	{
		return user;
	}
	public void SendMessage(String message)
	{
		out.println(message);
	}
	public String RecieveMessage()
	{
		String rstring = "";
		try 
		{
			
			rstring = in.readLine();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rstring;
	}
	public void CloseSocket()
	{
		out.println("Bye.");//message for server to cut this thread
		try {
			
			//close the i/o streams
			out.close();
			in.close();
			//close the socket
			echoSocket.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

		private static Network instance;

	  static 
	  {
	      instance = new Network();
	  }

	  private Network() 
	  {
	  }

	  public static Network getInstance() {
	      return Network.instance;
	  }
   
}