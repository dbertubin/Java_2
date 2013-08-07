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
					
				Toast toast = Toast.makeText(getApplicationContext(), "TEXT", Toast.LENGTH_LONG);
				toast.show();
				return;
				}
				
				Handler countdownHandler = new Handler(){

					@Override
					public void handleMessage(Message msg) {
						// TODO Auto-generated method stub
						super.handleMessage(msg);
						
						String response = null;
						
						
						if(msg.arg1 == RESULT_OK && msg.obj != null){
							
							try {
								response = (String) msg.obj;
							} catch (Exception e) {
								
								Log.e("HANDLER_MESSAGE" , e.getMessage().toString());
								e.printStackTrace();
					
							}
							
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