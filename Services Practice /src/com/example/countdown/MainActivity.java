package com.example.countdown;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	TextView resultText;
	Button startButton;
	EditText inputText;
	Context _context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		_context = this;
		resultText = (TextView) findViewById(R.id.resultText);
		inputText = (EditText)findViewById(R.id.editText);
		inputText.setInputType(InputType.TYPE_CLASS_NUMBER);
		startButton = (Button)findViewById(R.id.startButton);
		startButton.setOnClickListener(new OnClickListener() {
			
		
			@SuppressLint("HandlerLeak")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (inputText.getText().toString().length() == 0) {
					
				Toast toast = Toast.makeText(getApplicationContext(), "Enter a Number", Toast.LENGTH_LONG);
				toast.show();
				return;
				}
				
				// setup handler to pass message 
				Handler countdownHandler = new Handler(){

					@Override
					
					// create method to handle the message - default method of the Handler Class  
					public void handleMessage(Message msg) {
						// TODO Auto-generated method stub
						super.handleMessage(msg);
						
						// Since this is going to pass a string, we should instantiate a string for the response and set it to NULL
						String response = null;
					
						// check to see what the result is of the msg passed in the method as well a check to see that it is not null
						// checking for null tells us that the message is has been received 
						if(msg.arg1 == RESULT_OK && msg.obj != null){
							
							try {
								
								// assign value of the message object to the response string and be sure to case it 
								response = (String) msg.obj;
							} catch (Exception e) {
								
								Log.e("HANDLER_MESSAGE" , e.getMessage().toString());
								e.printStackTrace();
					
							}
							
							// set the text to the casted string response; 
							resultText.setText(response);
								
						}
				
					}
					
				};
				
				Messenger countdownMessenger = new Messenger(countdownHandler);
				
				Intent startCountdownIntent = new Intent(_context, CountdownService.class);
				
				startCountdownIntent.putExtra(CountdownService.MESSENGER_KEY, countdownMessenger);
				startCountdownIntent.putExtra(CountdownService.TIME_KEY, inputText.getText().toString());
				startService(startCountdownIntent);
				
				resultText.setText("Waiting...");
				
				
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	

}
