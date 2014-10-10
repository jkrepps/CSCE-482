import java.util.Random;


class Weather
{
	static enum weathers{
	FAIR, CLEAR, BREEZY, SPARSE_CLOUDS, CLOUDY, RAINING, LIGHTING, COLD, HOT, HURRICANE, EARTHQUAKE, MONSOON;
	
		@Override public String toString() {
	   //only capitalize the first letter
	   String s = super.toString();
	   return s;
	   }
	}
	
	static enum daytimes{
	
	MORNING, MIDDAY, AFTERNOON, EVENING, NIGHT, MINDNIGHT, EARLYMORNING;
	
	
		@Override public String toString() {
	   //only capitalize the first letter
	   String s = super.toString();
	   return s;
	   }
	}
    Random rand = new Random();
	
	private static final int wsize = weathers.values().length;
	private static final int dsize = daytimes.values().length;
	
	weathers w;
	daytimes d;
	
    public Weather() { // initialization function
	
		int randomw = rand.nextInt(wsize);
		w = weathers.values()[randomw];
		d = daytimes.values()[rand.nextInt(dsize)];
    }
    
	
	
	public void SetWeather()
	{
		int randomw = rand.nextInt(wsize);
		w = weathers.values()[randomw];
	}
	public String GetWeather()
	{
		return w.toString();
	}
	
   
   
}