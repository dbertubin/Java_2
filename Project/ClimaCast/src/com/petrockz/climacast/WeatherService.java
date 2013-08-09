/**
 * 
 */
package com.petrockz.climacast;

import java.net.MalformedURLException;
import java.net.URL;

import com.petrockz.chucknorris.lib.NetworkConnection;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.IntentService;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.content.Context;




public class WeatherService extends IntentService{

	Context _context;
	URL _finalURL;
	public static final String MESSENGER_KEY = "messenger";

	public static final String FINALURL_KEY = "url";

	public WeatherService() {
		super("WeatherService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.i("onHandleIntent", "Started");


		// The bundle is the container in which the data is passed about in the application 
		Bundle extras = intent.getExtras();

		// messenger is set by casting the value retrieved from the bundle with the key of MESSENGER_KEY
		Messenger messenger= (Messenger) extras.get(MESSENGER_KEY);


		// We Create a string called timer and grab the value passed in the bundle associated with the FINALURL_KEY

		String url = extras.getString(FINALURL_KEY);

		URL _finalURL = null;
		try {
			_finalURL = new URL(url);
			
			
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

			// once the while loop is done we create instantiate a instance of the Messenger Class and then obtain it 
			Message message = Message.obtain();

			// we set the message to send back as Activity.RESULT_OK using .arg1 and as well as send the obj string to give us some feed back that the action is complete
			message.arg1 = Activity.RESULT_OK;

			message.obj = getResponse(_finalURL);

			// now we use a try/catch to send the message using messenger.send() and pass the message.
			try {
				messenger.send(message);
				Log.i("MESSENGER", "Sending");
			} catch (RemoteException e) {
				// TODO Auto-generated catch block

				Log.e("onIntentHandler", e.getMessage().toString());
				e.printStackTrace();
			}

		}

	private String getResponse(URL url) {
		String response = "";

		response = NetworkConnection.getURLStringResponse(url);


		if (response == null ) {

			Log.i("SERVCE", "Response is not null");

			// AlertDialog if not connected
			AlertDialog.Builder alert = new AlertDialog.Builder(_context);
			alert.setTitle("UTT-OOOH!");
			alert.setMessage("It looks like the API is down and pleae try again later.");
			alert.setCancelable(false);
			alert.setPositiveButton("Hiyah!", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					dialogInterface.cancel();
				}
			});
			alert.show();

			return response;
		} else { 

			return response;
		}
	}
}







