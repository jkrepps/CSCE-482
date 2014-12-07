/**
 * @(#)IoDAPI.java
 *
 *
 * @author 
 * @version 1.00 2014/11/8
 */
 import java.io.*;
import java.net.*;


public class IoDAPI {
static String name = "";
static String password = "";
String hostName;
int portNumber;
PrintWriter out;
BufferedReader in;
BufferedReader stdIn;


    public IoDAPI(String user, String pass, String host, int port) 
    {
    	name = user;
    	password = pass;
    	hostName = host;
    	portNumber = port;
    }
    
    public String Connect()
    {
    	String loginStatement = "NULL";
    	try {
            Socket echoSocket = new Socket(hostName, portNumber);	//set up and connect to server instance
            out =
                new PrintWriter(echoSocket.getOutputStream(), true);	//output stream
            in =
                new BufferedReader(
                    new InputStreamReader(echoSocket.getInputStream()));	//input stream
            stdIn =
                new BufferedReader(
                    new InputStreamReader(System.in));
        
            String userInput;
			out.println("name\t"+name + "\t" + password);		// send the first message to the server with user's credentials
			
			loginStatement = in.readLine();							//determine if login was successful 
			
		} catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        } 
        	
        return loginStatement;
    }
    
    public Market getGameMarket()
    {
    	Market m = new Market();
    	String delim = "\t";
    	String[] tokens;
    	int num = 0;
    	String itemline = "";
    	
    	out.println("itemlist");
    	try
    	{
			num = Integer.parseInt(in.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(int i = 0; i<num; i++)
		{
			try
			{
				itemline = in.readLine();
			}catch(Exception e) {
       			System.err.println(e);
   			}
			tokens = itemline.split(delim); // parse the input command into tokens and process keywords
			m.addItem(tokens[0], Double.parseDouble(tokens[1]), tokens[2], tokens[3],
				Double.parseDouble(tokens[4]),Double.parseDouble(tokens[5]), Double.parseDouble(tokens[6]));
		}	
		return m;
    }
    public Inventory getInventory()
    {
    	Inventory m = new Inventory();
    	String delim = "\t";
    	String[] tokens;
    	int num = 0;
    	String itemline = "";
    	
    	out.println("getResources");
    	try
    	{
			num = Integer.parseInt(in.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(int i = 0; i<num; i++)
		{
			try
			{
				itemline = in.readLine();
			}catch(Exception e) {
       			System.err.println(e);
   			}
			tokens = itemline.split(delim); // parse the input command into tokens and process keywords
			m.addItem(tokens[0], Double.parseDouble(tokens[1]), tokens[2], Double.parseDouble(tokens[3]));
		}	
		return m;
    }
    public Techs getGameTech()
    {
    	Techs m = new Techs();
    	String delim = "\t";
    	String[] tokens;
    	int num = 0;
    	String techline = "";
    	
    	out.println("techlist");
    	try
    	{
			num = Integer.parseInt(in.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(int i = 0; i<num; i++)
		{
			try
			{
				techline = in.readLine();
			}catch(Exception e) {
       			System.err.println(e);
   			}
			tokens = techline.split(delim); // parse the input command into tokens and process keywords
			m.addItem(tokens[0], Double.parseDouble(tokens[1]));
		}	
		return m;
    }
    public LOG getLogs()
    {
    	LOG l = new LOG();
    	int num = 0;
    	String itemline = "";
    	
    	out.println("logfile");
    	try
    	{
			num = Integer.parseInt(in.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(int i = 0; i<num; i++)
		{
			try
			{
				itemline = in.readLine();
			}catch(Exception e) {
       			System.err.println(e);
   			}
			l.addEntry(itemline);
		}	
        	
		return l;
    }
    
    public double getMoney()
    {

    	out.println("money");
    	double money = 0;
    	try
    	{
			money = Double.parseDouble(in.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return money;
    }
    public double getScience()
    {

    	out.println("science");
    	double science = 0;
    	try
    	{
			science = Double.parseDouble(in.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return science;
    }
    public double getMarketing()
    {

    	out.println("marketing");
    	double marketing = 0;
    	try
    	{
			marketing = Double.parseDouble(in.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return marketing;
    }
    public double getIncome()
    {

    	out.println("income");
    	double income = 0;
    	try
    	{
			income = Double.parseDouble(in.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return income;
    }
	public String getWeather()
    {

    	out.println("weather");
    	String weather = "";
    	try
    	{
			weather = in.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return weather;
    }
    public String getDaytime()
    {

    	out.println("daytime");
    	String time = "";
    	try
    	{
			time = in.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return time;
    }
    public boolean gameBuy(String itemname, int number)
    {
		String response = "";
    	out.println("buy\t"+itemname + "/t" + number);
    	try
    	{
			response = in.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		if(response.equals("purchased "+ number + " units of " + itemname))
			return true;
		else
			return false;
    }
    public boolean gameBuyTech(String techname)
    {
		String response = "";
    	out.println("buytech\t"+techname);
    	try
    	{
			response = in.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		if(response.equals("purchased "+ techname))
			return true;
		else
			return false;
    }
    public boolean gameSell(String itemname, int number)
    {
		String response = "";
    	out.println("sell\t"+itemname);
    	try
    	{
			response = in.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(response.equals("sold " + number + " units of " + itemname))
			return true;
		else
			return false;
    }
    public Games getGameList()
    {

    	Games m = new Games();
    	String delim = "\t";
    	String[] tokens;
    	int num = 0;
    	String gameline = "";
    	
    	out.println("gamelist");
    	try
    	{
			num = Integer.parseInt(in.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(int i = 0; i<num; i++)
		{
			try
			{
				gameline = in.readLine();
			}catch(Exception e) {
       			System.err.println(e);
   			}
			tokens = gameline.split(delim); // parse the input command into tokens and process keywords
			m.addItem(tokens[0]);
		}	
        	
		return m;
    }
    public boolean newGame(int numplayers)
    {

    	out.println("newgame\t"+numplayers);
    	
		return true;
    }
    public boolean gameConnect(int gameID)
    {

    	out.println("connect\t"+gameID);
    	
    	String response = "";
    	try
    	{
			response = in.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(response.equals("success"))
			return true;
		else
			return false;
    }
    public String[] getPlayerlist()
    {
    	out.println("playerlist");
    	int num = 0;
    	String[] response = null;
    	try
    	{
    		num = Integer.parseInt(in.readLine());
			response = new String[num];
			for(int i = 0; i < num; i++)
				response[i] = in.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
    }    
}