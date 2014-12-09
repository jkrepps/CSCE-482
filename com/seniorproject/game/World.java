package com.seniorproject.game;
import java.util.Random;


public class World
{
	private enum weathers{
	FAIR, CLEAR, BREEZY, SPARSE_CLOUDS, CLOUDY, COLD, HOT, RAINING, LIGHTING, HURRICANE, EARTHQUAKE, MONSOON;
	
		@Override public String toString() {
	   //only capitalize the first letter
	   String s = super.toString();
	   return s;
	   }
	}
	
	private enum daytimes{
	
	MORNING, MIDDAY, AFTERNOON, EVENING, NIGHT, MIDNIGHT, EARLYMORNING;
	
		public daytimes next()
	    {
		int length = daytimes.values().length - 1;
	        return this.ordinal() < length
				 ? daytimes.values()[this.ordinal() + 1]
				 : daytimes.values()[0];
	    }
		
		@Override public String toString() {
	   //only capitalize the first letter
	   String s = super.toString();
	   return s;
	   }
	}
    Random rand = new Random();
	
	private final int wsize = weathers.values().length;
	private final int dsize = daytimes.values().length;
	
	private weathers w = weathers.CLEAR;
	private daytimes d = daytimes.MORNING;
	
    public World() { // initialization function
	
		int randomw = rand.nextInt(wsize);
		int randomd = rand.nextInt(dsize);
		w = weathers.values()[randomw];
		d = daytimes.values()[0];
    }
    
	
	
	public void SetWeather()
	{
		
		boolean chosen = false;
		int chosenvalue = 0;
		int randomw = 0;
		
		while(chosen != true)
		{	
			randomw = rand.nextInt(4); //pick from a hat kind of algorithm.
			if (randomw == 0)			//if the lowest of 4 numbers is chosen, then the current 
										//hat(weather) is selected
				chosen = true;			//this means since each hat is tried in order, and you stop once one wins
										//earlier hats are much more likely to be chosen 
										//(pass the test before others have a chance)
			else
			{
				chosenvalue++;
				if (chosenvalue == wsize)
				{
					chosenvalue = 0;
				}
			}
		}
		
		w = weathers.values()[chosenvalue];
	}
	public void SetDaytime()
	{
		d = d.next();
	}
	public String GetWeather()
	{
		return w.toString();
	}
	public String GetDaytime()
	{
		return d.toString();
	}
   
   
}