package com.example.remunerativeindustries;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.example.remunerativeindustries.util.SystemUiHider;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.MotionEvent;
import android.view.View;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class Main_menu extends Activity {

	private Button button;
	private EditText username, password, IP, Port;
	final Context context = this;
	private Socket client;
	private PrintWriter printwriter;
	private EditText textField;
	private EditText ipField;
	private String message = "";
	private String user;
	private String pass;
	private String serverIp;
	private int PortNo;
	public  final static String SER_KEY = "com.example.remunerativeindustries.ser";

	

	@SuppressLint("NewApi")
	public void onCreate(Bundle savedInstanceState) {
		//
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		System.out.println("0");
		//for new android devices
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		System.out.println("1");
		//create instance
		super.onCreate(savedInstanceState);
		System.out.println("11");
		setContentView(R.layout.mains);
		System.out.println("12");
		
		// components from main.xml
		System.out.println("2");
		username = (EditText) findViewById(R.id.editText1);   //all the entry fields
		password = (EditText) findViewById(R.id.editText2);
		IP = (EditText) findViewById(R.id.editText3);
		Port = (EditText) findViewById(R.id.editText4);
		System.out.println("3");
		
		button = (Button) findViewById(R.id.button1);      //connect button
		System.out.println("4");
		button.setOnClickListener(new OnClickListener() {

			
			
			
			//where the button is pressed. OnClick is a function triggered by the button.
			@Override
			public void onClick(View view) {
				System.out.println("5");
				user = username.getText().toString();
				pass = password.getText().toString();
				serverIp = IP.getText().toString();
				PortNo = Integer.parseInt(Port.getText().toString());
				
				//send the login message to the server
				message = "name" + "\t"+user + "\t" + pass;
				 
				 Network.getInstance().SetNetInfo(user, pass, serverIp, PortNo);
				 
				 
				 String connect = Network.getInstance().StartConnection();
				 
				 if(!connect.equals("Incorrect log in"))
				 {
						Intent i;
						i = new Intent(Main_menu.this,GameSelectionScreen.class);

						System.out.println("loading...");
				        startActivity(i);
				 }
				 else
				 {
					 System.out.println("Incorrect log in");
					 Network.getInstance().CloseSocket();
				 }
				

			}
		});
	}
}
