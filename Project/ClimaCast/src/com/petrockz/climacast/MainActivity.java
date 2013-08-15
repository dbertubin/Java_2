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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.petrockz.chucknorris.lib.NetworkConnection;
import com.petrockz.climacast.WeatherContentProvider.WeatherData;

import android.net.Uri;
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
import android.database.Cursor;
import android.text.InputType;
import android.util.Log;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;

import android.widget.Toast;




public class MainActivity extends Activity {

	Context _context;
	Button _startButton;
	EditText _inputText;
	EditText _numDaysInput;
	GridLayout _resultsGrid;
	Boolean _connected;
	String _baseURL;
	String _finalURLString;

	String _temp ;
	String _humidity ;
	String _windSpeed ;
	String _windDirection ;
	String _weatherDescValue;
	String _zip;
	String _numDays;
	Spinner _selector;
	ArrayList<String> _options = new ArrayList<String>();
	ArrayList<String> _dateArray = new ArrayList<String>();
	ArrayList<String> _hiArray = new ArrayList<String>();
	ArrayList<String> _lowArray = new ArrayList<String>();
	ArrayList<String> _weatherDescArray = new ArrayList<String>();
	
	
	JSONObject _dataObj;
	int _optionSelected;
	
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_layout);

		updateOptionsArray();

		_context = this;
		_resultsGrid = (GridLayout) findViewById(R.id.resultsData);
		_inputText = (EditText)findViewById(R.id.editText);
		_inputText.setInputType(InputType.TYPE_CLASS_NUMBER);
		_numDays = "5";
		

		_selector = (Spinner)findViewById(R.id.spinner1);
		ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(_context, android.R.layout.simple_spinner_item, _options);
		listAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		_selector.setAdapter(listAdapter);
		_selector.setOnItemSelectedListener(new OnItemSelectedListener() {
		
			@Override	
		public void onItemSelected(AdapterView<?> parent, View v, int pos, long id){
			Log.i("OPTIONSELECTED", parent.getItemAtPosition(pos).toString());
			_optionSelected = pos;
			
		}
		

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
		
		});
		
	

		_finalURLString = null;
		_startButton = (Button)findViewById(R.id.startButton);
		_startButton.setOnClickListener(new OnClickListener() {

			@SuppressLint("HandlerLeak")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				netCon();
				Log.i("ONLICK", "hit");
				if(_connected){
					if (_inputText.getText().toString().length() == 5 && _optionSelected ==0) {

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


					} else if (_inputText.getText().toString().length() == 5 && _optionSelected == 1) {

						
						Uri uri = WeatherData.CONTENT_URI;
						String[] projection = WeatherData.PROJECTION;
						Cursor myCursor = getContentResolver().query(uri, projection, null, null, null); 
						int inte = myCursor.getCount();
						
						Log.i("URI" , Integer.valueOf(inte).toString());
						if (myCursor.moveToFirst() == true)
						{
							for (int i = 0; i < myCursor.getCount(); i++)
							{
								String date = myCursor.getString(1);
								String hi = myCursor.getString(2);
								String low = myCursor.getString(3);
//								String weatherDesc = myCursor.getString(4);
								


								_dateArray.add(date);
								_hiArray.add(hi);
								_lowArray.add(low);
//								_weatherDescArray.add(weatherDesc);
								
								int inte2 = myCursor.getCount();
								
								Log.i("URI2" , Integer.valueOf(inte2).toString());

								myCursor.moveToNext();


							}
						}
						
						
//						Toast toast = Toast.makeText(getApplicationContext(), "5 Day ForeCast", Toast.LENGTH_SHORT);
//						toast.show();

					} else if (_inputText.getText().toString().length() == 5 && _optionSelected == 2) {

						// URI AND CURSOR WITH DATA at index 0 from weather 
						Toast toast = Toast.makeText(getApplicationContext(), "Tommorrows", Toast.LENGTH_SHORT);
						toast.show();

					} else if (_inputText.getText().toString().length() == 5 && _optionSelected == 3) {

						Toast toast = Toast.makeText(getApplicationContext(), "2 days from now", Toast.LENGTH_SHORT);
						toast.show();

					} else if (_inputText.getText().toString().length() == 5 && _optionSelected == 4) {

						// URI AND CURSOR WITH DATA at index 0 from weather 
						
						Toast toast = Toast.makeText(getApplicationContext(), "3 days from now", Toast.LENGTH_SHORT);
						toast.show();

					} else if (_inputText.getText().toString().length() == 5 && _optionSelected == 5) {

						// URI AND CURSOR WITH DATA at index 0 from weather 
						Toast toast = Toast.makeText(getApplicationContext(), "4 days from now", Toast.LENGTH_SHORT);
						toast.show();

					} else if (_inputText.getText().toString().length() != 5) {

						Toast toast = Toast.makeText(getApplicationContext(), R.string.enter_a_valid_zip_code_, Toast.LENGTH_LONG);
						toast.show();
						return;

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
				// This will change to current plus 5 day to fit week 2 assignment and CP query
				// http://api.worldweatheronline.com/free/v1/weather.ashx?q=32707&format=json&num_of_days=5&key=p5rbnjhy84gpvc7arr3qb38c
				String _baseURL = "http://api.worldweatheronline.com/free/v1/weather.ashx?q=";
				String apiKey = "p5rbnjhy84gpvc7arr3qb38c";

				String qs = "";
				
				try {
					qs = URLEncoder.encode(zip, "UTF-8");
					
					finalURLString = _baseURL + qs + "&format=json&num_of_days=5"+ "&key=" +apiKey;
				} catch (Exception e) {
					Log.e("BAD URL", "ENCODING PROBLEM");
					finalURLString = null;
				}

				return finalURLString;
			}


			private void displayFromWrite() throws JSONException{
				String fileContents = ReadWrite.readStringFile(_context, "weatherData", false);

				JSONObject mainObj = new JSONObject(fileContents);
				_dataObj = mainObj.getJSONObject("data");
				JSONArray conditionsObj = _dataObj.getJSONArray("current_condition");
				JSONObject weatherObj = conditionsObj.getJSONObject(0);
				_temp = weatherObj.getString("temp_F");
				_humidity = weatherObj.getString("humidity");
				_windSpeed = weatherObj.getString("windspeedMiles");
				_windDirection = weatherObj.getString("winddir16Point");
				JSONArray weatherDesc = weatherObj.getJSONArray("weatherDesc");
				_weatherDescValue = weatherDesc.getJSONObject(0).getString("value");
				_zip = _dataObj.getJSONArray("request").getJSONObject(0).getString("query");

				displayData();
			}

			private void displayData(){

				((TextView) findViewById(R.id.data_tempF)).setText(_temp + " F¡");
				((TextView) findViewById(R.id.data_humidity)).setText(_humidity + "%");
				((TextView) findViewById(R.id.data_windSpeed)).setText(_windSpeed + " MPH");
				((TextView) findViewById(R.id.data_windDirection)).setText(_windDirection);
				((TextView) findViewById(R.id.weatherDesc)).setText(_weatherDescValue);
				((TextView) findViewById(R.id.data_location)).setText(_zip);
			}


		});

	}


	private void updateOptionsArray() {	
		_options.add("Get Current Weather");
		_options.add("Get 5 Day Forecast");
		_options.add("Get Weather for " + getDate().get(1));
		_options.add("Get Weather for " + getDate().get(2));
		_options.add("Get Weather for " + getDate().get(3));
		_options.add("Get Weather for " + getDate().get(4));
		
	}


	@SuppressLint("SimpleDateFormat")
	private ArrayList<String> getDate(){
		Calendar c = Calendar.getInstance();
		System.out.println("Current time => " + c.getTime());

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MMM-dd");
		String formattedDate = df.format(c.getTime());
		
		c.add(Calendar.DATE, 1);  // number of days to add
		String formattedDateAdd1 = df.format(c.getTime());
		
		c.add(Calendar.DATE, 1);  // number of days to add
		String formattedDateAdd2 = df.format(c.getTime());
		
		c.add(Calendar.DATE, 1);  // number of days to add
		String formattedDateAdd3 = df.format(c.getTime());
		
		c.add(Calendar.DATE, 1);  // number of days to add
		String formattedDateAdd4 = df.format(c.getTime());
		
		ArrayList<String> list = new ArrayList<String>();
		list.add(formattedDate);
		list.add(formattedDateAdd1);
		list.add(formattedDateAdd2);
		list.add(formattedDateAdd3);
		list.add(formattedDateAdd4);
		
		return list;
		
	}

	

}
