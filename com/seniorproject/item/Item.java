package com.seniorproject.item;

import java.util.Random;
import java.util.*;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class Item
{
    private int itemid;
    private int price;
	private int type;
	private int size;
    private String name;
	private int icon;
    
	Random rand = new Random(); 
    
    public Item(int itemid, String fileName) {
    	this.itemid=itemid;
		String[][] data = readFileToArray(fileName, "\t");  /*This is just a really long and silly parsing function I have used in the past, 
																it has many applications so I like using it*/
		
		setStats(data[itemid]);								// give each item the stats stored in the items file.
		
		System.out.println("MADE items!");					// testing purposes, let me know items were created
	}
												/*Standard object manipulation functions*/
	public int getItemID() {
		return itemid;
	}
	
	public int getType() {
		return type;
	}
	public int getSize() {
		return size;
	}
	public int getPrice() {
		return price;
	}

    public void setStats(String[] s) {
    	
    	price = Integer.parseInt(s[1].trim());
    	name = s[2].trim();
    	size = Integer.parseInt(s[3].trim());
    	//icon = Integer.parseInt(s[4].trim());
    	//type = Integer.parseInt(s[4].trim());
    	//price = Integer.parseInt(s[5].trim());
    	//onetimeuse = Integer.parseInt(s[6].trim());
    }

    
    public String getName() { return name; }
	
	
	
	/*Pretty complicated parsing function, shouldnt have much use when we move data storage to a more appropriate medium, this shouldn't cause a problem and I can explain if needed.*/
	public String[][] readFileToArray(String fileName, String delimiter) {
		String dataStr = "";
		
		String[] lineArray;
		
		int numLines = 0;
		int numCols = 0;
		
		//delimiter = "\t";
		
		try {
			InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
		
			Scanner s = new Scanner(is).useDelimiter("\\A");
			dataStr = s.hasNext() ? s.next() : "";
			
			s.close();
			is.close();
			
		} catch (Exception e) {
			System.out.println("couldn't get " + fileName); 
			e.printStackTrace();
		}
		
		
		
		dataStr += "\n";
		
		dataStr.replace("\r","");
				
			numLines = dataStr.split("\n").length;
			String[] colCountArray = dataStr.split("\n");
			
			for(int i = 0; i < colCountArray.length; i++) {
				int cols = colCountArray[i].split(delimiter).length;
				
				if(cols > numCols) numCols = cols;
				
			}


		String[][] lines = new String[numLines][numCols];
		String temp = "";
		int nline = 0;
		for (int i=0; i < dataStr.length(); i++) {
			char c = dataStr.charAt(i);
			if(c == '\n') {
				Pattern pattern = Pattern.compile(Pattern.quote(delimiter));
				temp = temp.replace("\n", "");
				lineArray = pattern.split(temp);
				
				if(!(temp.length() < 1 || lineArray.length == 1 || temp.length() == 0 || temp.charAt(0) == '/')) {
					lines[nline] = lineArray;
					nline++;
				}
				temp = "";
			}
			temp += c;
		}
		//System.out.println("nline " + nline + " numCols " + numCols);
		//System.out.println(Arrays.deepToString(lines));
		String[][] linesClean = new String[nline][numCols];
		
		int y = 0;
		for(int j = 0; j < lines.length; j++) {
			//System.out.println("lines[j][0] " + lines[j][0]);
				if(lines[j][0] != null) {
					linesClean[y] = lines[j];
					y++;
				}
		}	
		//System.out.println("y " + y);
		//System.out.println(Arrays.deepToString(linesClean));
		return linesClean;
	}
}
