/**
 * 
 */
package com.example.countdown;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * @author PetRockz
 *
 */
public class CountdownService extends IntentService{

	public CountdownService() {
		super("CountdownService");
		// TODO Auto-generated constructor stub
		
		
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		
		int countdown = 10;
		
		while (countdown > 0) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				Log.e("Sleep", e.getMessage().toString());
				e.printStackTrace();
			}
		}
		
	}

}
