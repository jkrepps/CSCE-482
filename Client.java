import java.io.*;
import java.net.*;

public class Client {

static String name = "";
static String password = "";
//static Player player;
    public static void main(String[] args) throws IOException {
        
        if (args.length != 2) {		//if the client runs without proper input arguments
            System.err.println(
                "Usage: java Client <host name> <port number>");
            System.exit(1);
        }
		
		
		Login(); 					//first step is to log in
		
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
		
        try {
            Socket echoSocket = new Socket(hostName, portNumber);	//set up and connect to server instance
            PrintWriter out =
                new PrintWriter(echoSocket.getOutputStream(), true);	//output stream
            BufferedReader in =
                new BufferedReader(
                    new InputStreamReader(echoSocket.getInputStream()));	//input stream
            BufferedReader stdIn =
                new BufferedReader(
                    new InputStreamReader(System.in));
        
            String userInput;
			out.println("name\t"+name + "\t" + password);		// send the first message to the server with user's credentials
			
			String loginStatement = in.readLine();							//determine if login was successful 
			System.out.println(loginStatement);
			
			if (loginStatement.equals("Incorrect log in"))					// if login not successful, then keep trying until you get through.
			while(loginStatement.equals("Incorrect log in"))
			{
				System.out.println("try again");
				Login();
				out.println("name\t"+name + "\t" + password);
				loginStatement = in.readLine();
				System.out.println(loginStatement);
			}
			
			
            while ((userInput = stdIn.readLine()) != null) {			//once connected, begin chat with the server, processing info as it comes back.
				if(!userInput.equals("read"))
                out.println(userInput);
				ProcessReturn(userInput, in);
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        } 
    }
	
	public static void ProcessReturn(String input, BufferedReader in) // process every response from the server
	{
		try
		{
			if(input.equals("gamelist"))					// read in multiple lines for the player -(server sends a number relating to the number of players[lines to get ready to read])
			{
				int num = Integer.parseInt(in.readLine());
				System.out.println(num);
				for(int i = 0; i<num; i++)
				{
					System.out.println(in.readLine());
				}
			}
			else if(input.equals("playerlist"))					// read in multiple lines for the player -(server sends a number relating to the number of players[lines to get ready to read])
			{
				int num = Integer.parseInt(in.readLine());
				for(int i = 0; i<num; i++)
				{
					System.out.println(in.readLine());
				}
			}
			else if(input.equals("itemlist"))			// read in multiple lines for the items -(server sends a number relating to the number of items[lines to get ready to read])
			{
				int num = Integer.parseInt(in.readLine());
				for(int i = 0; i<num; i++)
				{
					System.out.println(in.readLine());
				}
			}
			else if(input.equals("getInfra"))			// read in multiple lines for the items -(server sends a number relating to the number of items[lines to get ready to read])
			{
				int num = Integer.parseInt(in.readLine());
				for(int i = 0; i<num; i++)
				{
					System.out.println(in.readLine());
				}
			}
			else if(input.equals("logfile"))			// read in multiple lines for the items -(server sends a number relating to the number of items[lines to get ready to read])
			{
				int num = Integer.parseInt(in.readLine());
				for(int i = 0; i<num; i++)
				{
					System.out.println(in.readLine());
				}
			}
			else
			System.out.println(in.readLine());		//otherwise just read the response.
	
		} 
		catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to ");
            System.exit(1);
        } 
	}
	
	public static void Login()
	{
	try{
		System.out.print("username => ");			//enter username
		BufferedReader uname =
                new BufferedReader(
                    new InputStreamReader(System.in));
		name = uname.readLine(); 
		
		System.out.print("password => ");			//enter password
		BufferedReader upassword =
                new BufferedReader(
                    new InputStreamReader(System.in));
		password = upassword.readLine(); 
		
		
		//player = new Player(100, name, password);	//create a new player instance to hold the data
		
		
		} catch (UnknownHostException e) {
            System.err.println("Don't know about host ");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to ");
            System.exit(1);
        } 
		
		
	}
}