/**
 * 
 */
package com.example.remunerativeindustries;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author Lance
 *
 */
public class HudScreen extends Activity {
	Button GameMarket;
	Button PlayerMarket;
	Button Tech;
	Button Owned;
	Button Refresh;
	Button Send;
	
	TextView Logs;
	TextView ChatLogs;
	TextView money;
	TextView science;
	TextView marketing;
	TextView income;
	
	EditText chatWindow;
	
	Network mNetwork;
	int appheight;
	int appwidth;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		System.out.println("made it to here.");
		setContentView(R.layout.activity_hudscreen);
		Logs = (TextView) findViewById(R.id.editText1);
		ChatLogs = (TextView) findViewById(R.id.editText2);
		money = (TextView) findViewById(R.id.textView1);
		science = (TextView) findViewById(R.id.textView2);
		marketing = (TextView) findViewById(R.id.textView3);
		income = (TextView) findViewById(R.id.textView4);
		mNetwork = Network.getInstance();
		GameMarket = (Button) findViewById(R.id.button1);
		PlayerMarket= (Button) findViewById(R.id.button2);
		Tech= (Button) findViewById(R.id.button3);
		Refresh= (Button) findViewById(R.id.button4);
		Owned= (Button) findViewById(R.id.button5);
		Send = (Button) findViewById(R.id.button6);
		chatWindow = (EditText) findViewById(R.id.editText3);
		/*
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		//
		System.out.println("user is " + mNetwork.getUser());

		appheight = metrics.heightPixels;
		appwidth = metrics.widthPixels;
		*/
		PopulateLog();
		populateChatLogs();
		
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
				i = new Intent(HudScreen.this, GameScreen.class); 
				
				System.out.println("loading...");
		        startActivity(i);
			} 
		}); 
		Owned.setOnClickListener(new View.OnClickListener() 
		{
			@Override public void onClick(View v) 
			{ 
				Intent i;
				i = new Intent(HudScreen.this, OwnedResources.class); 
				
				System.out.println("loading...");
		        startActivity(i);
			} 
		}); 
		Refresh.setOnClickListener(new View.OnClickListener() 
		{
			@Override public void onClick(View v) 
			{ 
				PopulateLog();
			} 
		});
		Send.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v){
				String message = chatWindow.getText().toString();
				if(!message.isEmpty()) {
					mNetwork.SendMessage("chat\t" + message);
					mNetwork.RecieveMessage();
					chatWindow.setText("");
					populateChatLogs();
				}
				else {
					populateChatLogs();
				}
			}
		});
	}

	

	public void PopulateLog()
	{
		
		mNetwork.SendMessage("logfile");
		String num = mNetwork.RecieveMessage();
		String finaloutput = "";
		
		if(!num.equals("You have lost.") && !num.equals("You have won!"))
		{
			int number = Integer.parseInt(num);
			for(int i = 0; i < number; i++)
			{
				finaloutput += mNetwork.RecieveMessage() + "\n";
			}
		}
		else
		{
			finaloutput += num;
		}
		
		money.setText(Html.fromHtml(getMoney()), TextView.BufferType.SPANNABLE );
		science.setText( Html.fromHtml(getScience()), TextView.BufferType.SPANNABLE);
		marketing.setText( Html.fromHtml(getMarketing()), TextView.BufferType.SPANNABLE );
		income.setText( Html.fromHtml(getIncome()), TextView.BufferType.SPANNABLE );
		
	    Logs.setText( finaloutput );
	}

	public void populateChatLogs() {
		mNetwork.SendMessage("chatfile");
		String nums = mNetwork.RecieveMessage();
		String finalOutput = "";
		if(!nums.equals("You have lost.") && !nums.equals("You have won!")) {
			int number = Integer.parseInt(nums);
			for(int i=0; i<number; ++i) {
				finalOutput += mNetwork.RecieveMessage() + "\n";
			}
		}
		else {
			finalOutput += nums;
		}
		ChatLogs.setText(finalOutput);
	}
	
	public String getMoney()
	{
		mNetwork.SendMessage("money");
		String retval = mNetwork.RecieveMessage();
		return "<font color='green'>:   </font>"+retval+'\t';
	}
	public String getScience()
	{
		mNetwork.SendMessage("science");
		String retval = mNetwork.RecieveMessage();
		return "<font color='blue'>:   </font>"+retval+'\t';
	}
	public String getMarketing()
	{
		mNetwork.SendMessage("marketing");
		String retval = mNetwork.RecieveMessage();
		return "<font color='red'>Marketing</font> = "+retval +'\t';
	}
	public String getIncome()
	{
		mNetwork.SendMessage("income");
		String retval = mNetwork.RecieveMessage();
		return "<font color='yellow'>Income = "+retval +"</font>";
	}
	
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.men.play, menu);
		return true;
	}*/

}
