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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
	

	

	@SuppressLint("NewApi")
	public void onCreate(Bundle savedInstanceState) {
		//
		
		//for new android devices
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		
		//create instance
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);

		// components from main.xml
		
		username = (EditText) findViewById(R.id.editText1);   //all the entry fields
		password = (EditText) findViewById(R.id.editText2);
		IP = (EditText) findViewById(R.id.editText3);
		Port = (EditText) findViewById(R.id.editText4);
		
		
		button = (Button) findViewById(R.id.button1);      //connect button
		button.setOnClickListener(new OnClickListener() {

			
			
			
			//where the button is pressed. OnClick is a function triggered by the button.
			@Override
			public void onClick(View view) {
				
				/*for debugging*/
				//System.out.println("clicked");
				//System.out.println(message);
				
				//transfer all the data in the entry fields to text
				user = username.getText().toString();
				pass = password.getText().toString();
				serverIp = IP.getText().toString();
				PortNo = Integer.parseInt(Port.getText().toString());
				
				//send the login message to the server
				 message = "name "+user + " " + pass;
				 
				 /*testing */
				 //System.out.println(message);
				 
				 
				 /*try to connect to the Server*/
				try {
		            Socket echoSocket = new Socket(serverIp, PortNo);
		            PrintWriter out =
		                new PrintWriter(echoSocket.getOutputStream(), true);
		            BufferedReader in =
		                new BufferedReader(
		                    new InputStreamReader(echoSocket.getInputStream()));
		            BufferedReader stdIn =
		                new BufferedReader(
		                    new InputStreamReader(System.in));
		        
		            String userInput;
					out.println(message);
					
					
					
					
					
					String loginStatement = in.readLine();
					System.out.println(loginStatement);
					
					
					if (!loginStatement.equals("Incorrect log in"))					// if login not successful, then keep trying until you get through.
					{
						System.out.println("YAY! I'm in!");
					}
					else
					{
						out.println("Bye.");//message for server to cut this thread
						
						//close the i/o streams
						out.close();
						in.close();
						         
						//close the socket
						echoSocket.close();
					}
					
		        } catch (UnknownHostException e) {
		            System.err.println("Don't know about host ");
		            System.exit(1);
		        } catch (IOException e) {
		            System.err.println("Couldn't get I/O for the connection ");
		            System.exit(1);
		        }
				

			}
		});
	}
}
