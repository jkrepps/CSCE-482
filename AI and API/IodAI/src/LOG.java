/**
 * @(#)LOG.java
 *
 *
 * @author 
 * @version 1.00 2014/11/8
 */
import java.util.*;

public class LOG {
Vector<String> entries;

    public LOG() 
    {
    	entries = new Vector<String>();
    }
    
    public void addEntry(String entry)
    {
    	entries.add(entry);
    }
    
    public String getEntry(int i)
    {
    	return entries.elementAt(i);
    }
    
    
}