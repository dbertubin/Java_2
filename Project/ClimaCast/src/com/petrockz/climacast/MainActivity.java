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

import java.net.MalformedURLException;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.TextView;

import android.widget.Toast;




public class MainActivity extends Activity {

	Context _context;
	Button _startButton;
	EditText _inputText;
	GridLayout _resultsGrid;
	Boolean _connected;
	String _baseURL;
	String _finalURLString;

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
		_finalURLString = null;
		_startButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				netCon();
				Log.i("ONLICK", "hit");
				if(_connected){
					if (_inputText.getText().toString().length() == 0) {

						Toast toast = Toast.makeText(getApplicationContext(), "Enter a valid zip", Toast.LENGTH_LONG);
						toast.show();
						return;
					} else {
						

						Handler weatherHandler = new Handler(){

							public void handleMessage(Message msg) {
								super.handleMessage(msg);

								Log.i("HANDLER", "is being hit");
								if (msg.arg1 == RESULT_OK && msg.obj != null) {
	                                String messageString = msg.obj.toString();
	                                Log.i("URL_RESPONSE", messageString);

	                                try {
	                                    // Pull JSON data from API
	                                    JSONObject json = new JSONObject(messageString);
	                                    JSONObject data = json.getJSONObject("data");
	                                    Boolean error = data.has("error");
	                                    if (error) {
	                                        
	                                        Toast toast = Toast.makeText(_context,"Sorry we were not able to find the zip you entered", Toast.LENGTH_SHORT);
	                                        toast.show();
	                                    }else{
	                                    	
	                                    	displayFromWrite();
	                                   
	                                    }
	                                } catch (JSONException e) {
	                                    Log.e("JSON ERROR", e.toString());
	                                }
	                            }
							}

						};

						try {
							_finalURLString = getURLString(_inputText.getText().toString());
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						Messenger weatherMessenger = new Messenger(weatherHandler);
						Intent startWeatherIntent = new Intent(_context, WeatherService.class);
						startWeatherIntent.putExtra(WeatherService.MESSENGER_KEY, weatherMessenger);
						startWeatherIntent.putExtra(WeatherService.FINALURL_KEY, _finalURLString);

						// Start the service remember that the handleMessage method will not be called until the Service is done. 
						startService(startWeatherIntent);
					}

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
		       alert.setPositiveButton("Hiyah!", new DialogInterface.OnClickListener() {
		           @Override
		           public void onClick(DialogInterface dialogInterface, int i) {
		               dialogInterface.cancel();
		           }
		       });
		       alert.show();

		       
			}		 
		}


			private  String getURLString (String zip) throws MalformedURLException {
				
				String finalURLString = "";
				String _baseURL = "http://api.worldweatheronline.com/free/v1/weather.ashx";
		        String apiKey = "qsxcvw8kpztq9hpwjsm3yaa6";
		        String qs = "";
		        try {
		            qs = URLEncoder.encode(zip, "UTF-8");
		            
		            finalURLString = _baseURL + "?q=" + qs + "&format=json&key=" + apiKey;
		        } catch (Exception e) {
		            Log.e("BAD URL", "ENCODING PROBLEM");
		            finalURLString = null;
		        }
		      
				return finalURLString;
			}
			
			
			private void displayFromWrite() throws JSONException{
				String fileContents = ReadWrite.readStringFile(_context, "weatherInfo", false);
					
				JSONObject mainObj = new JSONObject(fileContents);
				JSONObject dataObj = mainObj.getJSONObject("data");
				JSONArray conditionsObj = dataObj.getJSONArray("current_condition");
				JSONObject weatherObj = conditionsObj.getJSONObject(0);
				String temp = weatherObj.getString("temp_F");
				String humidity = weatherObj.getString("humidity");
				String windSpeed = weatherObj.getString("windspeedMiles");
				String windDirection = weatherObj.getString("winddir16Point");
//				JSONObject weatherDesc = weatherObj.getJSONObject("weatherDesc");
//				String weatherDescValue = weatherDesc.getString("value");
				
		       
		        ((TextView) findViewById(R.id.data_tempF)).setText(temp);
		        ((TextView) findViewById(R.id.data_humidity)).setText(humidity);
		        ((TextView) findViewById(R.id.data_windSpeed)).setText(windSpeed + " MPH");
		        ((TextView) findViewById(R.id.data_windDirection)).setText(windDirection);
				
				
			
			}
			
	
		});
	}
}
