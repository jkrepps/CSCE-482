/**
 * 
 */
package com.example.remunerativeindustries;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author Lance
 *
 */
public class HudScreen extends Activity {
	Button GameMarket;
	Button PlayerMarket;
	Button Tech;
	
	Network mNetwork;
	int appheight;
	int appwidth;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("made it to here.");
		setContentView(R.layout.activity_hudscreen);
		
		mNetwork = Network.getInstance();
		GameMarket = (Button) findViewById(R.id.button1);
		PlayerMarket= (Button) findViewById(R.id.button2);
		Tech= (Button) findViewById(R.id.button3);
		
		/*
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		//
		System.out.println("user is " + mNetwork.getUser());

		appheight = metrics.heightPixels;
		appwidth = metrics.widthPixels;
		*/
		PopulateLog();
		
		GameMarket.setOnClickListener(new View.OnClickListener() 
		{
			@Override public void onClick(View v) 
			{ 
				Intent i;
				i = new Intent(HudScreen.this, GameScreen.class); 
				
				System.out.println("loading...");
		        startActivity(i);
			} 
		}); 
		Tech.setOnClickListener(new View.OnClickListener() 
		{
			@Override public void onClick(View v) 
			{ 
				Intent i;
				i = new Intent(HudScreen.this, TechScreen.class); 
				
				System.out.println("loading...");
		        startActivity(i);
			} 
		}); 
		PlayerMarket.setOnClickListener(new View.OnClickListener() 
		{
			@Override public void onClick(View v) 
			{ 
				Intent i;
				i = new Intent(HudScreen.this, PlayerMarketScreen.class); 
				
				System.out.println("loading...");
		        startActivity(i);
			} 
		}); 
	}

	

	public void PopulateLog()
	{
		TextView headerValue = (TextView) findViewById(R.id.editText1);
		mNetwork.SendMessage("logfile");
		String num = mNetwork.RecieveMessage();
		String finaloutput = "";
		
		int number = Integer.parseInt(num);
		for(int i = 0; i < number; i++)
		{
			finaloutput += mNetwork.RecieveMessage() + "\n";
		}
		
	    headerValue.setText( finaloutput );
	}

	
		
	
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.men.play, menu);
		return true;
	}*/

}
