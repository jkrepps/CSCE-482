import java.util.*;
class Techs {

Vector<String> itemnames;
Vector<String> categories;
Vector<Double> costs;


    public Techs() 
    {
    	itemnames = new Vector<String>();
    	categories = new Vector<String>();
		costs = new Vector<Double>();
    }
    
    public void addItem(String itemname, /*String category,*/ double cost)
    {
    	itemnames.add(itemname);
    	//categories.add(category);
    	costs.add(cost);
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
    
    
}
