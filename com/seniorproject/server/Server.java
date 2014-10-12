package com.seniorproject.server;

import java.util.concurrent.TimeUnit;
import java.net.*;
import java.io.*;

import com.seniorproject.dao.DaoFactoryImpl;
import com.seniorproject.game.Player;
import com.seniorproject.game.Weather;
import com.seniorproject.item.Item;


public class Server {
static int NUMPLAYERS = 12;
static int NUMITEMS = 10;
static Player[] players = new Player[NUMPLAYERS]; // array of all the players who can/have connected for this game.
static Item[] item = new Item[NUMITEMS]; // array of all the items in the game
static Weather weather = new Weather();


	public static void initialize() //read in all the items from the items file so they will be stored and ready before anyone connects
	{
		for(int i = 0; i < item.length; i++) {
			item[i] = new Item(i,"items/items.txt");
		}
		new Thread(new WeatherThread()).start();
	}
	
	/**
	 * Main functions that "starts" the server
	 * Usage:
	 * java Server <port number> <db url> <db username> <db password>
	 * 
	 * @param args 
	 * @throws IOException
	 */
    public static void main(String[] args) throws IOException { // main function to set up connections
        
		
		InetAddress ip;
        String hostname;
        try {						
            ip = InetAddress.getLocalHost();  //for testing purposes, this section displays the current servers local IP and hostname
            hostname = ip.getHostName();
			
            System.out.println("Your current IP address : " + ip);
            System.out.println("Your current Hostname : " + hostname);
			System.out.println(InetAddress.getLocalHost().getHostName());
 
        } catch (UnknownHostException e) {
 
            e.printStackTrace();
        }
		
        if (args.length < 4) {		//if the server is not called with enough arguments
            System.err.println("Usage: java Server <port number> <db url> <db username> <db password>");
            System.exit(1);
        }
		
		initialize(); // initialize Items
		
		// Initialize database connections
		try {
			DaoFactoryImpl.initialize(args[1], args[2], args[3]);
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
		} 
		
        int portNumber = Integer.parseInt(args[0]);

        try { 
            ServerSocket serverSocket = new ServerSocket(portNumber);	//set up the socket to watch the port for connections
			while (true)
			{
            Socket clientSocket = serverSocket.accept();  // if a client connects - 
			new Thread(new SocketThread(clientSocket)).start();  // start a new thread to handle the client
			}
            
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
	public static class SocketThread implements Runnable {  // client thread, each client goes through this process individually and simultaneously. 

        private Socket socket;

        public SocketThread(Socket socket) {// accept client socket connection.
            this.socket = socket;
        }

        @Override
        public void run() {	//start thread
			try{
				PrintWriter out =
					new PrintWriter(socket.getOutputStream(), true);	//output stream 
				BufferedReader in = new BufferedReader(
					new InputStreamReader(socket.getInputStream())); 	// input stream
				
				
				Player p = new Player(0, "noname", "nopass"); // create a new player Object that will have credentials determined later
			
			
				String inputLine, outputLine;
															/*Note: when the client connects, it sends a message with credentials for the server to evaluate.
															When the server first creates the connection, it already has a message waiting for it and Processes it.
															This process is the login process and is directed to the Login() function*/
				outputLine = Process(in.readLine(), p);		// evaluate waiting message - Login()
				out.println(outputLine);					// respond/initiate chat
				
				while(outputLine.equals("Incorrect log in")) //if the output line was an incorrect login message, the client will be asked to resend credentials, therefore the 
				{											 //login process is repeated until a correct login is used. 
				outputLine = Process(in.readLine(), p);												
				out.println(outputLine);					 //respond
				}
				
				
				while ((inputLine = in.readLine()) != null) { // now that a connection is established, client and server take turns talking back and forth
					outputLine = Process(inputLine, p); 	// the Server's response depends on how it Process() an input.
					System.out.println(inputLine);			// display the Clients initial statement for records
					out.println(outputLine);				//send the Server's response to the client
					if (inputLine.equals("Bye.")|| outputLine.equals("Copy: Bye.")||outputLine.equals("Incorrect log in")) //if you type "Bye." its like logging out. 
						break;
				}
				out.close(); //close this client socket
				in.close();
				socket.close();
				} catch (IOException e) {
				System.out.println("Exception caught when trying to listen on port or listening for a connection");
				System.out.println(e.getMessage());
			}
        }
    }
	
	
	public static String Process (String input, Player p) //Process an input from a player p
	{
		String outputLine = "";
		String delims = "[ ]+";
		String[] tokens = input.split(delims); // parse the input command into tokens and process keywords
		if(tokens[0].equals("name"))   			  //name = Login
		{
			try {
				Login.UserLogin(tokens[1], tokens[2]);
			} catch (ServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// outputLine = Login(tokens[1],tokens[2],p);
		}
		else if(tokens[0].equals("playerlist"))   //playerlist = show all current players
		{
			outputLine += Integer.toString(NUMPLAYERS) + "";
			for(int i = 0; i < NUMPLAYERS; i++)
				if(players[i] != null)
					outputLine += "\n-" + players[i].getName();
				else
					outputLine += "\n -empty Slot";
		}
		else if(tokens[0].equals("itemlist"))	 //itemlist = show a list of all items
		{
			outputLine += Integer.toString(NUMITEMS+1);
			outputLine += "\n NAME		PRICE		SIZE";
			for(int i = 0; i < NUMITEMS; i++)
					outputLine += "\n" + item[i].getName() + " \t\t" + item[i].getPrice() + " \t\t" + item[i].getSize();
		}
		else if(tokens[0].equals("weather"))	 
		{
			outputLine = weather.GetWeather();
		}
		else if(tokens[0].equals("sweather"))	 
		{
			weather.SetWeather();
			outputLine = "changed weather";
		}
		else									//otherwise simply repeat the input command.
		outputLine = "Copy: " + input;
		return outputLine;
	}
	
	
	public static String Login(String username, String password, Player p)  //Login for users.
	{
				for(int i = 0; i < NUMPLAYERS; i++) //Go through the available player slots
				{
					if(players[i] != null && players[i].getName().equals(username) && players[i].getPass().equals(password)) //if a player logs in with an existing username and password
						{
						p = players[i];
						String output = "Successfully relogged to the server, welcome "+ p.getName();
						System.out.println(p.getName() + "joined.");
						return output;
						}
					else if(players[i] != null && players[i].getName().equals(username) && !players[i].getPass().equals(password))//if a player logs in with an existing username and wrong password
						{
						//p = players[i];
						String output = "Incorrect log in";
						return output;
						}
					else if(players[i] == null)				//IMPORTANT: if the server reaches an empty player slot, then the current username must not be in use and new credentials are created.
						{
						p = players[i] = new Player(100, username, password);   //100 = starting money (just for now) 
						String output = "Successfully connected to the server, welcome "+ p.getName();
						System.out.println(p.getName() + "joined.");
						return output;
						}
				}
		return "Incorrect log in";
	}
	
	
	/*WEATHER THREAD*/
	public static class WeatherThread implements Runnable {  // client thread, each client goes through this process individually and simultaneously. 



        public WeatherThread() {// accept client socket connection.
        }

        @Override
        public void run() {	//start thread
			while(true)
			{
				weather.SetWeather();
				System.out.println("new weather is : " + weather.GetWeather());
				try
				{
				TimeUnit.MINUTES.sleep(30);
				} catch (InterruptedException e) {
                    e.printStackTrace();
                }
			}
        }
    }
	
}