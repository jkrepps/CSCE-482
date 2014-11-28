package com.example.remunerativeindustries;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class TechScreen extends Activity {

	//up arrow
	//private static final int NUM_ROWS = 7;
	//private static final int NUM_COLS = 10;

	Button buttons[][];// = new Button[NUM_ROWS][NUM_COLS];
	String dataStrings[];
	Button Refresh;
	Button buyButton;
	int appwidth = 0;
	int appheight = 0;
	int currentSelected = 0;
	Network mNetwork;
	TextView money;
	TextView science;
	TextView marketing;
	EditText units;
	TextView data;
	TableLayout table;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_tech);//new DrawingView(this));
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		mNetwork = Network.getInstance();
		System.out.println("user is " + mNetwork.getUser());
		table = (TableLayout) findViewById(R.id.tableForTech);
		appheight=metrics.heightPixels;
		appwidth=metrics.widthPixels;
		money = (TextView) findViewById(R.id.textView1);
		science = (TextView) findViewById(R.id.textView2);
		marketing = (TextView) findViewById(R.id.textView3);
		data = (TextView) findViewById(R.id.infoText);
		
		Refresh = (Button) findViewById(R.id.button1);
		buyButton = (Button) findViewById(R.id.button2);
		Refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				PopulateButtons();
				
			}
		});
		buyButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String message = "buytech\t" + getName(dataStrings[currentSelected]);
				System.out.println(message);
				mNetwork.SendMessage(message);
				String rval = mNetwork.RecieveMessage();
				Toast.makeText(TechScreen.this, rval, Toast.LENGTH_SHORT).show();
				PopulateButtons();
				
			}
		});
		PopulateButtons();
		System.out.println("7");
	}
	

	public void PopulateButtons()
{
		
		table.removeAllViews();
		currentSelected = 0;
		money.setText(Html.fromHtml(getMoney()), TextView.BufferType.SPANNABLE );
		science.setText( Html.fromHtml(getScience()), TextView.BufferType.SPANNABLE);
		marketing.setText( Html.fromHtml(getMarketing()), TextView.BufferType.SPANNABLE );
		mNetwork.SendMessage("techlist");
		String rstring = mNetwork.RecieveMessage();
		if(rstring.equals("You have lost.") || rstring.equals("You have won!"))
		{
			return;
		}
		else
		{
			final int NUM_ROWS = Integer.parseInt(rstring);
			final int NUM_COLS = 1;
			buttons = new Button[NUM_ROWS][NUM_COLS];
			dataStrings = new String[NUM_ROWS];
			
			for (int row = 0; row < NUM_ROWS; row++) 
			{ 
				TableRow tableRow = new TableRow(this); 
				tableRow.setLayoutParams(new TableLayout.LayoutParams( TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 1.0f)); 
				table.addView(tableRow); 
				for (int col = 0; col < NUM_COLS; col++)
				{ 
					final int FINAL_COL = col; 
					final int FINAL_ROW = row; 
					Button button = new Button(this); 
					button.setLayoutParams(new TableRow.LayoutParams( TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f)); 
					rstring = mNetwork.RecieveMessage();
					dataStrings[row] = rstring;
					button.setText(getName(rstring)); // Make text not clip on small buttons 
					button.setPadding(0, 0, 0, 0); 
					button.setOnClickListener(new View.OnClickListener() 
					{
						@Override public void onClick(View v) 
						{ 
							currentSelected = FINAL_ROW;
							gridButtonClicked(FINAL_COL, FINAL_ROW, NUM_ROWS, NUM_COLS);
							System.out.println(currentSelected);
							buttons[currentSelected][0].setPressed(true);
						} 
					}); 
					tableRow.addView(button); buttons[row][col] = button; 
					//buttons[currentSelected][0].setPressed(false);
				} 
			}
			if(NUM_ROWS > 0)
			{
				buttons[currentSelected][0].setPressed(true);
				String tokens[] = tokenize(dataStrings[currentSelected]);
				String infoText = formatInfo(tokens);
				data.setText(infoText);
			}
		}
	}
	private void gridButtonClicked(int col, int row, int NUM_ROWS, int NUM_COLS) 
	{ 
		System.out.println("click 1");
		String tokens[] = tokenize(dataStrings[currentSelected]);
		String infoText = formatInfo(tokens);
		data.setText(infoText);
		Button button = buttons[row][col]; 
		// Lock Button Sizes: lockButtonSizes(NUM_ROWS, NUM_COLS); // Does not scale image.
		// button.setBackgroundResource(R.drawable.­action_lock_pink); // Scale image to button: Only works in JellyBean! 
		// Image from Crystal Clear icon set, under LGPL // http://commons.wikimedia.org/wiki/Cry...
		int newWidth = button.getWidth();
		int newHeight = button.getHeight();
		buttons[row][col].setPressed(true);
		money.setText(Html.fromHtml(getMoney()), TextView.BufferType.SPANNABLE );
		science.setText( Html.fromHtml(getScience()), TextView.BufferType.SPANNABLE);
		marketing.setText( Html.fromHtml(getMarketing()), TextView.BufferType.SPANNABLE );
		//Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.action_lock_pink);
		//Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
		//Resources resource = getResources();
		//button.setBackground(new BitmapDrawable(resource, scaledBitmap)); // Change text on button:
		//button.setText(); 
	}

	private void lockButtonSizes(int NUM_ROWS, int NUM_COLS) 
	{ 
		for (int row = 0; row != NUM_ROWS; row++) 
		{ 
			for (int col = 0; col != NUM_COLS; col++) 
			{ 
				Button button = buttons[row][col]; 
				int width = button.getWidth(); 
				button.setMinWidth(width); 
				button.setMaxWidth(width); 
				int height = button.getHeight(); 
				button.setMinHeight(height); 
				button.setMaxHeight(height); 
			} 
		} 
	}
	private String[] tokenize(String s)
	{
		String delims = "\t";
		String[] tokens = s.split(delims);
		
		return tokens;
	}
	private String formatInfo(String[] tokens)
	{
		String output = "name = " + tokens[0] + "\n";
		output += "Price = " + tokens[1] + " Science\n";
		
		return output;
	}
	private String getName(CharSequence c)
	{
		String t = "";
		for(int i = 0; i < c.length(); i++)
		{
			if(c.charAt(i) == '\t')
			break;
			t += c.charAt(i);
		}
		
		
		return t;
	}	
	public String getMoney()
	{
		mNetwork.SendMessage("money");
		String retval = mNetwork.RecieveMessage();
		return "<font color='green'>Money = "+retval+"</font>";
	}
	public String getScience()
	{
		mNetwork.SendMessage("science");
		String retval = mNetwork.RecieveMessage();
		return "<font color='blue'>Science = "+retval+"</font>";
	}
	public String getMarketing()
	{
		mNetwork.SendMessage("marketing");
		String retval = mNetwork.RecieveMessage();
		return "<font color='red'>Marketing = "+retval +"</font>";
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.men.play, menu);
		return true;
	}

}
