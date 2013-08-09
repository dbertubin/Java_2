/*
 * project	ClimaCast
 * 
 * package 	com.petrockz.climacast
 * 
 * @author 	Derek Bertubin
 * 
 * date 	Aug 5, 2013
 */
package com.petrockz.climacast;



//import java.net.MalformedURLException;
//import java.net.URL;
//
//import org.json.JSONException;
//import org.json.JSONObject;

import com.petrockz.chucknorris.lib.NetworkConnection;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.util.Log;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;

import android.widget.Toast;




public class MainActivity extends Activity {

	Context _context;
	Button _startButton;
	EditText _inputText;
	GridLayout _resultsGrid;
	Boolean _connected;
	String _baseURL;

	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		_context = this;
		_resultsGrid = (GridLayout) findViewById(R.id.resultsData);
		_inputText = (EditText)findViewById(R.id.editText);
		_inputText.setInputType(InputType.TYPE_CLASS_NUMBER);
		_startButton = (Button)findViewById(R.id.startButton);
		_startButton.setOnClickListener(new OnClickListener() {


			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				netCon();

				if(_connected){
					if (_inputText.getText().toString().length() == 0) {

						Toast toast = Toast.makeText(getApplicationContext(), "Enter a valid zip", Toast.LENGTH_LONG);
						toast.show();
						return;
					} else {
						Log.i("ONLICK", "hit");
						getURLString(_inputText.getText().toString());

						Handler weatherHandler = new Handler(){

							public void handleMessage(Message msg) {
								super.handleMessage(msg);


								if(msg.arg1 == RESULT_OK && msg.obj != null){

									Log.i("HANDLER", "is being hit");

								}
							}

						};

					
						Messenger weatherMessenger = new Messenger(weatherHandler);

 
						Intent startWeatherIntent = new Intent(_context, WeatherService.class);


						startWeatherIntent.putExtra(WeatherService.MESSENGER_KEY, weatherMessenger);


						startWeatherIntent.putExtra(WeatherService.FINALURL_KEY, _baseURL);

						// Start the service remember that the handleMessage method will not be called until the Service is done. 
						startService(startWeatherIntent);
					}
					//				}

				};
			}


			// DETECT NETWORK CONNECTION

			private void netCon(){

				_connected = NetworkConnection.getConnectionStatus(_context);
				if (_connected) {
					Log.i("NETWORK CONNECTION", NetworkConnection.getConnectionType(_context));

				} else{

					// AlertDialog if not connected
					AlertDialog.Builder alert = new AlertDialog.Builder(_context);
					alert.setTitle("Oops!");
					alert.setMessage("Please check your network connection and try again.");
					alert.setCancelable(false);
					alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
							dialogInterface.cancel();
						}
					});
					alert.show();


				}
			} 


			private  String getURLString (String zip) {

				String apikey = "426e0ad4896f241d";	
				String zipQuery = zip;
				String _baseURL = "http://api.wunderground.com/api/"+apikey+"/geolookup/q/" +zipQuery+".json";

				return _baseURL;
			}
		});
	}
}
