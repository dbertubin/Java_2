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
				
				// The Handler Class is used to pass messages between background threads, such as Services, and the main application thread.
				Handler countdownHandler = new Handler(){

					@Override
					
					// The handleMessage method can be used similar to the way the onPostExecute method is used upon completion of an AsyncTask. 
					// the handleMessage is explicitly called when the Service sends a message
					public void handleMessage(Message msg) {
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
				
				//Once you have designed your Service Class and instantiated the Handler in your launching Activity you need a way for the 
				//Service to access that Handler so it can communicate with the Activity. 
				
				
				//The Messenger Class in instantiated in the calling activity and passed to the Service to provide a reference to the Handler.
				// in this case it will be "countdownHandler"
				Messenger countdownMessenger = new Messenger(countdownHandler);
				
				
				//The Activity has to command the operating system to start the Service. This operation is done through an Intent Class.
				//The Intent Class is a container for delivering information to the Android operating system, as well as, in this case the Service being started.
				//The Intent constructor takes as input parameters the calling Activity's context, and the Service Class to start. 
				Intent startCountdownIntent = new Intent(_context, CountdownService.class);
				
				//Then the Intent is loaded with any data that is to be passed to this Service. 
				//You can put any type of data into the intent using the "putExtra" method, where you enter two parameters. 
				//The first parameter is a key string, giving a name to the data for the recipient to access. 
				//The second parameter in the putExtra method is the data, which can be of any type of data, even a class such as Messenger.
				
				
				startCountdownIntent.putExtra(CountdownService.MESSENGER_KEY, countdownMessenger);
				
				
				startCountdownIntent.putExtra(CountdownService.TIME_KEY, inputText.getText().toString());
				
				// Start the service remember that the handleMessage method will not be called until the Service is done. 
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
