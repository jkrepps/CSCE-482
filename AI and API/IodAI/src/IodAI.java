/**
 * @(#)APIClient.java
 *
 *
 * @author 
 * @version 1.00 2014/11/8
 */

public class IodAI 
{
       
static String username;
static String password;
static String hostName;
static int portNumber;
static double money = 0;
static double income = 0;
static double science = 0;
static String weather = "";
static String daytime = "";
static Inventory m_Inventory;
static Techs m_Techs;
static Games m_Games;
static Market m_GameMarket;
static LOG m_Log;
static IoDAPI API;

    
    
    public static void main(String[] args) 
    {
    	if (args.length != 4) {		//if the client runs without proper input arguments
            System.err.println(
                "Usage: java APIClient <username> <password> <host name> <port number>");
            System.exit(1);
        }
		
        
       	username = args[0];
       	password = args[1];
        String hostName = args[2];
        int portNumber = Integer.parseInt(args[3]);
        
        
        //new API instance.
    	API = new IoDAPI(username, password, hostName, portNumber);
    	
    	//Connect to server
	    String login = API.Connect();
	    System.out.println(login);
	    
	    
	    //get Game List
	    m_Games = API.getGameList();
	    //iterator of game to connect to from the list
	   	int GameToConnectTo = 1;
	   	//connect to a game
	   	API.gameConnect(GameToConnectTo);
	    
	    //once connected, loop until any call returns with ("you have won!"/"you have lost.")
	    while(true)
	    {	
	    
	    	//Gather info about surroundings
	    	GatherInfo();

	    	//React to situation
		   	Evaluate();
		    
		    	
    	}	
    }
    public static void GatherInfo()
    {
    	//science
		science = API.getScience();
		
		//income
		income = API.getIncome();
		
    	//money
    	money = API.getMoney();
    	
    	//weather
    	weather = API.getWeather();
    	
    	//daytime
    	daytime = API.getDaytime();
    	
    	//inventory
    	m_Inventory = API.getInventory();
    	
    	//Look at what items are avAPIlable
    	m_GameMarket = API.getGameMarket();
    	
    	//techlist
    	m_Techs = API.getGameTech();
    	
    	//check logs
    	m_Log = API.getLogs();
    }
    public static void Evaluate()
    {
    	//should I buy
    		//buy tech if enough science
    		BuyTech();
			//buy something if applicable
			PurchasePhase();
			//sell something
			SellPhase();
			//buy techs if enough science
    }
    public static void BuyTech()
    {
    	for(int i = 0; i < m_Techs.getSize(); i++)
    	{
    		if(science >= m_Techs.getCostAt(i))
    		{
    			System.out.println("Science = " + science + " and cost of " + m_Techs.getNameAt(i) + " is " + m_Techs.getCostAt(i));
    			//buy Technology
    			API.gameBuyTech(m_Techs.getNameAt(i));
    			science = API.getScience();
    		}
    	}
    }
    public static void PurchasePhase()
    {
    	int acceptableIncomeThreshold = 20;
    	
    	if(income > acceptableIncomeThreshold && m_Techs.getSize() > 0)
    	{
    		InvestInScience();
    	}
    	else
    	{
    		BuyMostCostEfficientMarketItem();
    	}	
    }
    public static void InvestInScience()
    {
    	String itemname = "Science";
    	double cost = 0;
    	double itemIncome = 0;
    	String incomeType = "ASSET";
    	double bestRatio = 0;
    	int cheapestIndex = 0;
    	for(int i = 0; i < m_GameMarket.getSize(); i++)
    	{
	    	itemname = m_GameMarket.getNameAt(i);
			cost = m_GameMarket.getCostAt(i);
			itemIncome = m_GameMarket.getIncomeAmountAt(i);
			incomeType = m_GameMarket.getIncomeAt(i);
			if(itemIncome/cost > bestRatio && incomeType.equals("Science"))
			{
				bestRatio = itemIncome/cost;
				cheapestIndex = i;
			}
    	}
    	if (money >= m_GameMarket.getCostAt(cheapestIndex))
    	{
    		if(haveEnoughWorkers(cheapestIndex))
    		{
	    		System.out.println("Money = " + money + " and cost of " + m_GameMarket.getNameAt(cheapestIndex) + " is " + m_GameMarket.getCostAt(cheapestIndex));
	    		int amount = 1;
	    		//buy from the game market
	    		API.gameBuy(m_GameMarket.getNameAt(cheapestIndex),amount);
				money = API.getMoney();
				System.out.println(money);	
    		}
    		else
    		{
    			buyWorker(cheapestIndex);
    		}
    		
    	}
    	else
    		System.out.println("not enough money");
    }
    public static void BuyMostCostEfficientMarketItem()
    {
    	String itemname = "Science";
    	double cost = 0;
    	double itemIncome = 0;
    	String incomeType = "ASSET";
    	double bestRatio = 0;
    	int cheapestIndex = 0;
    	for(int i = 0; i < m_GameMarket.getSize(); i++)
    	{
	    	itemname = m_GameMarket.getNameAt(i);
			cost = m_GameMarket.getCostAt(i);
			itemIncome = m_GameMarket.getIncomeAmountAt(i);
			incomeType = m_GameMarket.getIncomeAt(i);
			if(itemIncome/cost > bestRatio && incomeType.equals("Money"))
			{
				bestRatio = itemIncome/cost;
				cheapestIndex = i;
			}
    	}
    	if (money >= m_GameMarket.getCostAt(cheapestIndex))
    	{
    		if(haveEnoughWorkers(cheapestIndex))
    		{
	    		System.out.println("Money = " + money + " and cost of " + m_GameMarket.getNameAt(cheapestIndex) + " is " + m_GameMarket.getCostAt(cheapestIndex));
	    		int amount = 1;
	    		//buy from the game market
	    		API.gameBuy(m_GameMarket.getNameAt(cheapestIndex),amount);
				money = API.getMoney();
				System.out.println(money);
			}
    		else
    		{
    			buyWorker(cheapestIndex);
    		}
    	}
    	else
    		System.out.println("not enough money");
    }
    public static void SellPhase()
    {
    	for(int i = 0; i < m_Inventory.getSize(); i++)
    	{
    		if(m_Inventory.getCategoryAt(i).equals("ASSET") && !m_Inventory.getNameAt(i).equals("Science"))
    		{
    			System.out.println("Selling " + m_Inventory.getNameAt(i));
    			//sell to the game market
    			API.gameSell(m_Inventory.getNameAt(i),(int)m_Inventory.getAmountAt(i));
    		}
    	}
		money = API.getMoney();
		System.out.println(money);	
    }
    public static boolean haveEnoughWorkers(int marketIndex)
    {
    	String itemName = m_GameMarket.getNameAt(marketIndex);
    	String workerName = m_GameMarket.getWorkerTypeAt(marketIndex);
    	int workerNum = (int)m_GameMarket.getWorkerNumAt(marketIndex);
    	for(int i = 0; i < m_Inventory.getSize(); i++)
    	{
    		if(m_Inventory.getNameAt(i).equals(workerName) && m_Inventory.getAmountAt(i) >= workerNum && m_Inventory.getCategoryAt(i).equals("WORKER"))
    		{
    			return true;
    		}
    	}
    	return false;
    }
    public static void buyWorker(int marketIndex)
    {
    	String workerName = m_GameMarket.getWorkerTypeAt(marketIndex);
    	int workerNum = (int)m_GameMarket.getWorkerNumAt(marketIndex);
    	
    	for(int i = 0; i < m_GameMarket.getSize(); i++)
    	{
    		if(m_GameMarket.getNameAt(i).equals(workerName) && m_GameMarket.getCostAt(i) <= money)
    		{
    			System.out.println("Money = " + money + " and cost of " + m_GameMarket.getNameAt(i) + " is " + m_GameMarket.getCostAt(i));
	    		int amount = 1;
	    		//buy from the game market
	    		API.gameBuy(m_GameMarket.getNameAt(i),amount);
				money = API.getMoney();
				System.out.println(money);
    		}
    	}
    }
    public static boolean isInteger(String s) 
    {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    // only got here if we didn't return false
	    return true;
	}
}
