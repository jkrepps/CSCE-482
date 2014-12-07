/**
 * @(#)Market.java
 *
 *
 * @author 
 * @version 1.00 2014/11/8
 */
import java.util.*;

public class Inventory {

Vector<String> itemnames;
Vector<String> categories;
Vector<Double> costs;
Vector<Double> amounts;

    public Inventory() 
    {
    	itemnames = new Vector<String>();
    	categories = new Vector<String>();
		costs = new Vector<Double>();
		amounts = new Vector<Double>();		
    }
    public void addItem(String itemname, double cost, String category, double amount)
    {
    	itemnames.add(itemname);
    	costs.add(cost);
    	categories.add(category);
    	amounts.add(amount);
    }
    
    public int getSize()
    {
    	return itemnames.size();
    }
    public String getNameAt(int i)
    {
    	return itemnames.elementAt(i);
    }
    public double getCostAt(int i)
    {
    	return costs.elementAt(i);
    }
    public String getCategoryAt(int i)
    {
    	return categories.elementAt(i);
    }
    public double getAmountAt(int i)
    {
    	return amounts.elementAt(i);
    }
 
    
    
}