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


public class CountdownService extends IntentService{

	public static final String MESSENGER_KEY = "messenger";
	public static final String TIME_KEY = "time";
	
	public CountdownService() {
		super("CountdownService");
		// TODO Auto-generated constructor stub
		
		
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		
		
		Log.i("onHandleIntent", "Started");
		
		Bundle extras = intent.getExtras();
		Messenger messenger= (Messenger) extras.get(MESSENGER_KEY);	
		String timer = extras.getString(TIME_KEY);
		
		
		
		int countdown = 10;
		
		try {
			countdown = Integer.parseInt(timer);
			
		} catch (Exception e) {
			Log.e("onIntentHandler", e.getMessage().toString());
		}
		
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
		
		Message message = Message.obtain();
		message.arg1 = Activity.RESULT_OK;
		message.obj = "Service is Done";
		
		try {
			messenger.send(message);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			
			Log.e("onIntentHandler", e.getMessage().toString());
			e.printStackTrace();
		}
		
	}

}
