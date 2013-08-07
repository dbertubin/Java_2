/**
 * 
 */
package com.example.countdown;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

/**
 * @author PetRockz
 *
 */

// Create Countdown Service that extends IntentService 
public class CountdownService extends IntentService{

	
	// setup keys to key constants to pass data in thru the service 
	public static final String MESSENGER_KEY = "messenger";
	public static final String TIME_KEY = "time";
	
	
	public CountdownService() {
		super("CountdownService");
		
	}

	@Override
	
	
	protected void onHandleIntent(Intent intent) {
		
		Log.i("onHandleIntent", "Started");
		
		
		// The bundle is the container in which the data is passed about in the application 
		Bundle extras = intent.getExtras();
		
		// messenger is set by casting the value retrieved from the bundle with the key of MESSENGER_KEY
		Messenger messenger= (Messenger) extras.get(MESSENGER_KEY);	
		
		// We Create a string called timer and grab the value passed in the bundle associated with the TIME_KEY
		String timer = extras.getString(TIME_KEY);
		
		
		//We instantiate the init valued for the countdown int
		int countdown = 10;
		
		
		
		try {
			
			// since countdown is an int and we are passing a string we need to parse out the value of the string into an int. 
			// For this we will Integer.parseInt() and pass in the string timer 
			countdown = Integer.parseInt(timer);
			
		} catch (Exception e) {
			Log.e("onIntentHandler", e.getMessage().toString());
		}
		
		
		// The as long as the value of countdown is greater than 0 we will sleep the thread for x second interval 
		while (countdown > 0) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				Log.e("Sleep", e.getMessage().toString());
				e.printStackTrace();
			}
			
			countdown--;
			
			Log.i("onIntentHandler", "Counter = " + String.valueOf(countdown));
			
			
		}
		
		Log.i("onIntentHandler", "Countdown is done!");
		
		
		// once the while loop is done we create instantiate a instance of the Messenger Class and then obtain it 
		Message message = Message.obtain();
		
		// we set the message to send back as Activity.RESULT_OK using .arg1 and as well as send the obj string to give us some feed back that the action is complete
		message.arg1 = Activity.RESULT_OK;
		message.obj = "Service is Done";
		
		
		// now we use a try/catch to send the message using messenger.send() and pass the message.
		try {
			messenger.send(message);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			
			Log.e("onIntentHandler", e.getMessage().toString());
			e.printStackTrace();
		}
		
	}

}
