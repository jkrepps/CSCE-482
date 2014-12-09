package com.example.remunerativeindustries;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class GameSelectionScreen extends Activity {

	//up arrow
	//private static final int NUM_ROWS = 7;
	//private static final int NUM_COLS = 10;

	Button buttons[][];// = new Button[NUM_ROWS][NUM_COLS];
	
	int appwidth = 0;
	int appheight = 0;
	int currentSelected = 0;
	Network mNetwork;
	Button button;
	EditText numPlayers;
	EditText gameName;
	EditText length;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_game_selection);//new DrawingView(this));
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		mNetwork = Network.getInstance();
		System.out.println("user is " + mNetwork.getUser());

		appheight=metrics.heightPixels;
		appwidth=metrics.widthPixels;
		
		numPlayers = (EditText) findViewById(R.id.editText1);
		gameName = (EditText) findViewById(R.id.editText2);
		button = (Button) findViewById(R.id.button1);
		length = (EditText) findViewById(R.id.editText3);
		
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				mNetwork.SendMessage("newgame\t" + numPlayers.getText().toString() + '\t' + length.getText().toString());
				System.out.println("recieved newgame response " + mNetwork.RecieveMessage());
				Intent i;
				i = new Intent(GameSelectionScreen.this,HudScreen.class);
				
				//System.out.println("loading...");
		        startActivity(i);
			}
		});
		PopulateButtons();
	}
	

	public void PopulateButtons()
	{
		mNetwork.SendMessage("gamelist");
		String rstring = mNetwork.RecieveMessage();
		System.out.println("it may have worked if num = "+rstring);
		final int NUM_ROWS = Integer.parseInt(rstring);
		final int NUM_COLS = 1;
		buttons = new Button[NUM_ROWS][NUM_COLS];
		TableLayout table = (TableLayout) findViewById(R.id.tableForConnections);
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
		}
		if(NUM_ROWS > 0)
		buttons[currentSelected][0].setPressed(true);
		
	} 
	private void gridButtonClicked(int col, int row, int NUM_ROWS, int NUM_COLS) 
	{ 
		//Toast.makeText(this, "Button clicked: " + col + "," + currentSelected, Toast.LENGTH_SHORT).show(); 
		String game = getId(buttons[row][col].getText());
		mNetwork.SendMessage("connect\t" + game + "");
		String message = mNetwork.RecieveMessage();
		Button button = buttons[row][col];
		int newWidth = button.getWidth();
		int newHeight = button.getHeight();
		buttons[row][col].setPressed(true);
		if(!message.equals("failed"))
		{
			Intent i;
			i = new Intent(GameSelectionScreen.this,HudScreen.class);
			
			System.out.println("loading...");
	        startActivity(i);
		}
		
		// Lock Button Sizes: lockButtonSizes(NUM_ROWS, NUM_COLS); // Does not scale image.
		// button.setBackgroundResource(R.drawable.­action_lock_pink); // Scale image to button: Only works in JellyBean! 
		// Image from Crystal Clear icon set, under LGPL // http://commons.wikimedia.org/wiki/Cry...

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
		
	private String getId(CharSequence c)
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
	

}