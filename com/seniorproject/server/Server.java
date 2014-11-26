package com.seniorproject.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.ArrayList;

import com.seniorproject.dao.DaoObject;
import com.seniorproject.dao.GameDao;
import com.seniorproject.dao.PlayerDao;
import com.seniorproject.dao.ResourceDao;
import com.seniorproject.dao.TechDao;
import com.seniorproject.game.Game;
import com.seniorproject.game.Player;
import com.seniorproject.game.World;
import com.seniorproject.logger.Logger;
import com.seniorproject.resource.Resource;
import com.seniorproject.resource.Tech;


public class Server {
static int NUMPLAYERS = 12;
static int NUMITEMS = 10;
static Player[] players = new Player[NUMPLAYERS]; // array of all the players who can/have connected for this game
static World world = new World();
static float startingPlayerMoney = 100.0f;
static int playerid = 0;
static Login login;
static PlayerDao playerDao;
static GameDao gameDao;
static DaoObject dao;


private static ResourceDao resourceDao;// = new ResourceDao(dao.getConnection());
private static TechDao techDao;
private static Logger logger = new Logger();
private static List<Game> gameList;

	public static int initializeGame(Game game, DaoObject dao) throws Exception
	{
		GameDao gameDao = new GameDao(dao.getConnection());
		int gameId = gameDao.createGame(game);
		game.startGameThread(gameId, dao);
		gameList.add(game);
		return gameId;
		
		//new Thread(new GameThread()).start();//change to new Thread(new WeatherThread(int gameid)).start();
	}
	public static int reinitializeGame() throws Exception
	{
		gameList = new ArrayList<Game> ();
		List<Player> playerList = new ArrayList<Player> ();
		GameDao gameDao = new GameDao(dao.getConnection());
		try {
			gameList = gameDao.getFullGameList(); 
		
		for (int i = 0; i < gameList.size(); i++) 
		{
			gameList.get(i).startGameThread(i+1, dao);
			playerList = playerDao.getPlayersForGame(gameList.get(i));
			 
			for (int j = 0; j < playerList.size(); j++) 
			{
				gameList.get(i).insertPlayer(playerList.get(j));
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
		//new Thread(new GameThread()).start();//change to new Thread(new WeatherThread(int gameid)).start();
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
		
		dao = new DaoObject();
		// Initialize database connections
		try {
			System.out.println(dao.initialize(args[1], args[2], args[3]));
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
		} 
		
		
		
		//initialize Login
		login = new Login(dao);
		System.out.println("After login \n");
		playerDao = new PlayerDao(dao.getConnection());
		resourceDao = new ResourceDao(dao.getConnection());
		techDao = new TechDao(dao.getConnection());
		gameDao = new GameDao(dao.getConnection());
		
		
		//reInitialize all games that may have been closed
		try
		{
		reinitializeGame();
		} catch (Exception e) {
		System.out.println(e.getMessage());
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
			PlayerDao playerDao = new PlayerDao(dao.getConnection());
        }

        @Override
        public void run() {	//start thread
			try{
			
			
				System.out.println("Inside Socketthread \n");
				PrintWriter out =
					new PrintWriter(socket.getOutputStream(), true);	//output stream 
				BufferedReader in = new BufferedReader(
					new InputStreamReader(socket.getInputStream())); 	// input stream
				
				
				Player p = new Player(-1, "noname", startingPlayerMoney, 0.67, dao.getConnection()); // create a new player Object that will have credentials determined later
			
			
				String inputLine, outputLine;
															/*Note: when the client connects, it sends a message with credentials for the server to evaluate.
															When the server first creates the connection, it already has a message waiting for it and Processes it.
															This process is the login process and is directed to the Login() function*/
				outputLine = processRequest(in.readLine(), p);		// evaluate waiting message - Login()
				out.println(outputLine);					// respond/initiate chat
				
				while(outputLine.equals("Incorrect log in")) //if the output line was an incorrect login message, the client will be asked to resend credentials, therefore the 
				{											 //login process is repeated until a correct login is used. 
				outputLine = processRequest(in.readLine(), p);												
				out.println(outputLine);					 //respond
				}
				
				
				while ((inputLine = in.readLine()) != null) { // now that a connection is established, client and server take turns talking back and forth
					outputLine = processRequest(inputLine, p); 	// the Server's response depends on how it Process() an input.
					System.out.println(inputLine);			// display the Clients initial statement for records
					out.println(outputLine);				//send the Server's response to the client
					if (inputLine.equals("Bye.")|| outputLine.equals("Copy: Bye.")||outputLine.equals("Incorrect log in")) //if you type "Bye." its like logging out. 
						break;
				}
				out.close(); //close this client socket
				in.close();
				socket.close();
				} catch (Exception e) {	
				System.out.println("Exception caught when trying to listen on port or listening for a connection");
				System.out.println(e.getMessage());
			}
        }
    }
	
	/**
	 * Handles all the input through the listening socket and responds with the appropriate action
	 * @param input
	 * @param p
	 * @return
	 */
	
	public static String processRequest (String input, Player p) throws Exception//Process an input from a player p
	{
		String outputLine = "";
		String delims = "\t";
		String[] tokens = input.split(delims); // parse the input command into tokens and process keywords
		if(tokens[0].equals("name"))   			  //name = Login
		{
			try {
				String returnMessage = login.UserLogin(tokens[1], tokens[2]);
				p.setPlayerName(tokens[1]);
				if (returnMessage == "Relog") {
					outputLine = "Successfully relogged: " + tokens[1];
					//p.setPlayerMoney(playerDao.getPlayerMoney(tokens[1]));
				}
				else if (returnMessage == "NewUser"){
					outputLine = "Welcome new user: " + tokens[1];
					p.setPlayerId(playerid);
					try {
						// TODO Add a reasonable user name and game id
						playerDao.createPlayer(new Player(0,p.getPlayerName(), 1000f, 0.6, dao.getConnection()), p.getPlayerName(), 0);
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}			
		
					playerid ++;
				}
				else
					outputLine = "Incorrect log in";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// outputLine = Login(tokens[1],tokens[2],p);
		}
		/*
		else if(tokens[0].equals("gamelist"))   //playerlist = show all current players
		{
			String username = tokens[1];
			List<Game> gameList = new ArrayList<Game> ();
			
			try {
				gameList = gameDao.getGameList(username); 
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			outputLine += Integer.toString(gameList.size());
			for (Game g : gameList) {
				outputLine += "\n-" + g.getGameId();
			}
			
		}
		*/
		else if (tokens[0].equals("gamelist"))
		{
			List<Game> gameList = new ArrayList<Game> ();
			
			try {
				gameList = gameDao.getGameList(); 
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(gameList.size());
			outputLine += Integer.toString(gameList.size());
			for (Game g : gameList) {
				outputLine += "\n" + g.getGameId();
			}
		}
		else if(tokens[0].equals("newgame"))   //playerlist = show all current players
		{
			String username = p.getPlayerName();
			String maxPlayers = tokens[1];
			List<String> playerlist;
			int numberItems;
			Game newGame = new Game(Integer.parseInt(maxPlayers), 12, 30);
			int gameId;
			int index = 0;
			try {
				gameId = initializeGame(newGame, dao);
				System.out.println("gameId = "+gameId);
				//get correct game from game ID
				for(int i = 0; i < gameList.size(); i++)
				{
					if(gameList.get(i).getGameId() == gameId)
						index = i+1;
				}
				Player temp = playerDao.checkPlayer(gameId, username, gameList.get(index));
				
				p.copyPlayer(temp);
				playerDao.setPlayerMoney( username, p.getPlayerId(), playerDao.getPlayerMoney(username,gameId) );
				p.setPlayerMoney( playerDao.getPlayerMoney( username, gameId));
				System.out.println("playerId = " + p.getPlayerId());
			
				playerlist = playerDao.getPlayerList(gameId);
				numberItems = playerDao.getGameSize(gameId);
				outputLine += Integer.toString(numberItems);
				for(int i = 0; i < numberItems; i++) 
				{
					outputLine += "\n" + playerlist.get(i);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		else if(tokens[0].equals("connect"))   //playerlist = show all current players
		{
			int gameId = Integer.parseInt(tokens[1]);
			String username = p.getPlayerName();
			Player temp = playerDao.checkPlayer(gameId, username, gameList.get(gameId-1));
			
			// game is full
			if(temp == null) {
				outputLine += "failed";
				
			}
			else {
				p.copyPlayer(temp);
				//TODO WHAT THE FUCK IS HAPPENING HERE why 
				playerDao.setPlayerMoney( username, p.getPlayerId(), playerDao.getPlayerMoney(username,gameId) );
				p.setPlayerMoney( playerDao.getPlayerMoney( username, gameId));
				System.out.println(p.getPlayerId());
				
				if (p == null)
					outputLine += "failed";
				else 
					outputLine += "success";
			}
				
		}
		else if(p.getPlayerId() == -1)
		{
			outputLine = "Connect to a game first.";
		}
		/// From here on out, it is not possible for a user to issue this command if they have not connected to a game
		/// This checks to see if the current player has a game over. if so then they cant do anything
		else if(playerDao.getPlayerStatus(p.getPlayerId()) == -1)
		{
			outputLine = "You have lost.";
		}
		else if(playerDao.getPlayerStatus(p.getPlayerId()) == 1)
		{
			outputLine = "You have won!";
		}
		else if(tokens[0].equals("playerlist"))   //playerlist = show all current players
		{
			int gameID = playerDao.getGameId(p.getPlayerId());
			List<String> playerlist;
			int numberItems = 0;
			try 
			{
				playerlist = playerDao.getPlayerList(gameID);
				numberItems = playerDao.getGameSize(gameID);
			
				outputLine += Integer.toString(numberItems);
				
				for(int i = 0; i < numberItems; i++) 
				{
					outputLine += "\n" + playerlist.get(i);
				}
			} catch (Exception e) {
					e.printStackTrace();
			}
		}
		else if(tokens[0].equals("itemlist"))	 //itemlist = show a list of all items
		{
			int numberItems = 0;
			List<Resource> list;
			try {
				list = resourceDao.getResourceList(p);
				numberItems = list.size();
			} catch (Exception e) {
				e.printStackTrace();
			}
			outputLine += Integer.toString(numberItems);
			try 
			{
				list = resourceDao.getResourceList(p);
				for(int i = 0; i < numberItems; i++) 
				{
					outputLine += "\n" + list.get(i).getResourceName() + "\t" + list.get(i).getResourcePrice();
				}
			} catch (Exception e) {
					e.printStackTrace();
			}
			
		}
		else if(tokens[0].equals("techlist"))	 //itemlist = show a list of all items
		{
			int numberItems = 0;
			List<Tech> list;
			try {
				list = techDao.getTechList(p);
				numberItems = list.size();
			} catch (Exception e) {
				e.printStackTrace();
			}
			outputLine += Integer.toString(numberItems);
			try 
			{
				list = techDao.getTechList(p);
				for(int i = 0; i < numberItems; i++) 
				{
					outputLine += "\n" + list.get(i).getTechName() + "\t" + list.get(i).getTechPrice();
				}
			} catch (Exception e) {
					e.printStackTrace();
			}
			
		}
		else if (tokens[0].equals("buy"))
		{
			int result = p.buyResource(tokens[1], Integer.parseInt(tokens[2]));
			//1 = resource name 2 = numUnits
			if ( result == 1)
			//System.out.println(p.getPlayerId());
				outputLine += "purchased "+ tokens[2] + " units of " + tokens[1];
			else if (result == 0)
				outputLine += "Not enough money";
			else if (result == -1 )
				outputLine += "Item doesn't exist";
			else if (result == -2 )
				outputLine += "Error with update of database entry";
			else if (result == -3 )
				outputLine += "Not enough skilled workers";
			else 
				outputLine += "UNKNOWN ERROR";
		}
		else if (tokens[0].equals("buytech"))
		{
			//1 = resource name 2 = numUnits
			int result = p.buyTech(tokens[1]);
			if (result == 1)
				outputLine += "purchased " + tokens[1];
			else if (result == 0)
				outputLine += "Not enough money";
			else if (result == -1 )
				outputLine += "Item doesn't exist";
			else if (result == -2 )
				outputLine += "Error with update of database entry";
			else 
				outputLine += "UNKNOWN ERROR";
		}
		else if (tokens[0].equals("getResources"))
		{
			int numberItems = 0;
			try {
				numberItems = playerDao.getResources(p.getPlayerId()).size();
			} catch (Exception e) {
				e.printStackTrace();
			}
			outputLine += Integer.toString(numberItems);
			for(int i = 0; i < numberItems; i++) {
					try {
						outputLine += "\n" + playerDao.getResources(p.getPlayerId()).get(i).getResourceName();
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		}
		else if (tokens[0].equals("sell"))
		{
			//1 = resource name, 2 = numUnits
			if(p.sellResource(tokens[1], Integer.parseInt(tokens[2])) == 1)
				outputLine += "sold " + tokens[2] + " units of " + tokens[1];
			else if (p.sellResource(tokens[1], Integer.parseInt(tokens[2])) == 0)
				outputLine += "Not enough items in player inventory";
			else if (p.sellResource(tokens[1], Integer.parseInt(tokens[2])) == -1)
				outputLine += "Item doesn't exist";
			else
				outputLine += "UNKNOWN ERROR";
			
		}

		else if (tokens[0].equals("logfile"))
		{

			outputLine += logger.getNumberLines();
			outputLine += logger.readFromLog();
		}
		else if (tokens[0].equals("money"))
		{
			outputLine += p.getPlayerMoney();
		}
		else if(tokens[0].equals("weather"))	 
		{
			outputLine = gameList.get(playerDao.getGameId(p.getPlayerId())).getWeather();
		}
		else if(tokens[0].equals("daytime"))	 
		{
			outputLine = gameList.get(playerDao.getGameId(p.getPlayerId())).getDaytime();
		}
		else	//otherwise simply repeat the input command.
		outputLine = "Copy: " + input;
		return outputLine;
	}
	
}