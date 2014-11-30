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
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class PlayerMarketScreen extends Activity {

	//up arrow
	//private static final int NUM_ROWS = 7;
	//private static final int NUM_COLS = 10;

	Button buttons[][];// = new Button[NUM_ROWS][NUM_COLS];
	
	int appwidth = 0;
	int appheight = 0;
	int currentSelected = 0;
	Network mNetwork;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_game_screen);//new DrawingView(this));
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		mNetwork = Network.getInstance();
		System.out.println("user is " + mNetwork.getUser());

		appheight=metrics.heightPixels;
		appwidth=metrics.widthPixels;
		
		PopulateButtons();
	}
	

	public void PopulateButtons()
	{
		mNetwork.SendMessage("playeritemlist");
		String rstring = mNetwork.RecieveMessage();
		int rows = 0;
		if(!rstring.equals("Copy: playeritemlist"))
		{
			rows = Integer.parseInt(rstring);
		}
		else
		{
			rows = 1;
			Toast.makeText(this, rstring,Toast.LENGTH_SHORT).show(); 
		}
		final int NUM_ROWS = rows;
		final int NUM_COLS = 1;
		buttons = new Button[NUM_ROWS][NUM_COLS];
		TableLayout table = (TableLayout) findViewById(R.id.tableForBuying);
		//rstring = mNetwork.RecieveMessage(); // get column names
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
				button.setText(rstring); // Make text not clip on small buttons 
				button.setPadding(0, 0, 0, 0); 
				button.setOnClickListener(new View.OnClickListener() 
				{
					@Override public void onClick(View v) 
					{ 
						gridButtonClicked(FINAL_COL, FINAL_ROW, NUM_ROWS, NUM_COLS);
						currentSelected = FINAL_ROW;
						System.out.println(currentSelected);
						buttons[currentSelected][0].setPressed(true);
					} 
				}); 
				tableRow.addView(button); buttons[row][col] = button; 
				//buttons[currentSelected][0].setPressed(false);
			} 
		}buttons[currentSelected][0].setPressed(true);
		
	} 
	private void gridButtonClicked(int col, int row, int NUM_ROWS, int NUM_COLS) 
	{ 
		Toast.makeText(this, "Button clicked: " + col + "," + currentSelected, Toast.LENGTH_SHORT).show(); 
		Button button = buttons[row][col]; 
		// Lock Button Sizes: lockButtonSizes(NUM_ROWS, NUM_COLS); // Does not scale image.
		// button.setBackgroundResource(R.drawable.�action_lock_pink); // Scale image to button: Only works in JellyBean! 
		// Image from Crystal Clear icon set, under LGPL // http://commons.wikimedia.org/wiki/Cry...
		int newWidth = button.getWidth();
		int newHeight = button.getHeight();
		buttons[row][col].setPressed(true);
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
		
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.men.play, menu);
		return true;
	}

}
