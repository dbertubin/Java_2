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
	String _response;



	public static final String MESSENGER_KEY = "messenger";

	public static final String FINALURL_KEY = "url";

	public WeatherService() {
		super("WeatherService");
		_context = this;
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.i("onHandleIntent", "Started");

		Bundle extras = intent.getExtras();
		Messenger messenger= (Messenger) extras.get(MESSENGER_KEY);
		String url = extras.getString(FINALURL_KEY);


		URL _finalURL = null;
		String _response = null;
		try {
			_finalURL = new URL(url);
			_response = getResponse(_finalURL);
			ReadWrite.storeStringFile(_context, "weatherData", _response, false);		
			Log.i("WRITE", "SUCCESS");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Message message = Message.obtain();

		message.arg1 = Activity.RESULT_OK;

		message.obj = _response;

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
		String response = null;
		response = NetworkConnection.getURLStringResponse(url);

		if (response != null) {


			return response;

		} else{
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

			return null;

		}
	}
}







