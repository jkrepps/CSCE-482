/**
 * @(#)Market.java
 *
 *
 * @author 
 * @version 1.00 2014/11/8
 */
import java.util.*;

public class Market {

Vector<String> itemnames;
Vector<String> categories;
Vector<Double> costs;
Vector<String> incomes;
Vector<String> workerTypes;
Vector<Double> workerNums;
Vector<Double> lands;
Vector<Double> incomeAmounts;



    public Market() 
    {
    	itemnames = new Vector<String>();
    	categories = new Vector<String>();
		costs = new Vector<Double>();
		incomes = new Vector<String>();
		workerTypes = new Vector<String>();
		workerNums = new Vector<Double>();
		lands = new Vector<Double>();
		incomeAmounts = new Vector<Double>();
		
    }
    public void addItem(String itemname, double cost, String income, String workerType, double workerNum, double land, double incomeAmount)
    {
    	itemnames.add(itemname);
    	//categories.add(category);
    	costs.add(cost);
    	incomes.add(income);
    	workerTypes.add(workerType);
    	workerNums.add(workerNum);
    	lands.add(land);
    	incomeAmounts.add(incomeAmount);
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
    public String getIncomeAt(int i)
    {
    	return incomes.elementAt(i);
    }
    public String getWorkerTypeAt(int i)
    {
    	return workerTypes.elementAt(i);
    }
    public double getWorkerNumAt(int i)
    {
    	return workerNums.elementAt(i);
    }
    public double getLandAt(int i)
    {
    	return lands.elementAt(i);
    }
    public double getIncomeAmountAt(int i)
    {
    	return incomeAmounts.elementAt(i);
    }
    
    
}