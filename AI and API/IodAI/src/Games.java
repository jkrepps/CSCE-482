
import java.util.*;
class Games {
Vector<String> gameIds;
Vector<String> GameNames;
Vector<Double> gameSizes;


    public Games() 
    {
    	gameIds = new Vector<String>();
    	GameNames = new Vector<String>();
		gameSizes = new Vector<Double>();
    }
    
    public void addItem(String gameId, /*String category,*/ String GameName, double gameSize)
    {
    	gameIds.add(gameId);
    	gameSizes.add(gameSize);//categories.add(category);
    	GameNames.add(GameName);
    }
    public void addItem(String gameId /*String category, String GameName, double gameSize*/)
    {
    	gameIds.add(gameId);
    }
    public int getSize()
    {
    	return gameIds.size();
    }
    public String getGameAt(int i)
    {
    	return gameIds.elementAt(i);
    }
    public String getNameAt(int i)
    {
    	return GameNames.elementAt(i);
    }
    public double getSizeAt(int i)
    {
    	return gameSizes.elementAt(i);
    }
    
    
}
