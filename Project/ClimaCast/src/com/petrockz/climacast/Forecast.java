package com.petrockz.climacast;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import com.petrockz.climacast.R;
import com.petrockz.climacast.WeatherContentProvider.WeatherData;

public class Forecast extends Activity {

	ListView _listView;
	Context _context;
	ArrayList<String> _history;
	Uri _uri;
	ArrayList<String> _dateArray = new ArrayList<String>();
	ArrayList<String> _hiArray = new ArrayList<String>();
	ArrayList<String> _lowArray = new ArrayList<String>();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.five_day_grid);

		Intent intent = getIntent();
		int option = intent.getExtras().getInt("URI");
		

		
		if (option ==0) {
			_uri = WeatherData.CONTENT_URI;
		} else if (option == 1) {
			_uri = WeatherData.ITEM_URI1;
		}else if (option == 2) {
			_uri = WeatherData.ITEM_URI2;
		}else if (option == 3) {
			_uri = WeatherData.ITEM_URI3;
		}else if (option == 4) {
			_uri = WeatherData.ITEM_URI4;
		}
			


		String[] projection = WeatherData.PROJECTION;
		Cursor myCursor = getContentResolver().query(_uri, projection, null, null, null); 
		int inte = myCursor.getCount();

		Log.i("URI" , Integer.valueOf(inte).toString());
		if (myCursor.moveToFirst() == true)
		{
			for (int i = 0; i < myCursor.getCount(); i++)
			{
				String date = myCursor.getString(1);
				String hi = myCursor.getString(2);
				String low = myCursor.getString(3);

				_dateArray.add(date);
				_hiArray.add(hi);
				_lowArray.add(low);

				Log.i("_dateArray", _dateArray.toString());
				myCursor.moveToNext();
			}
		}

		if (_dateArray.size() ==5) {

			TextView day1 = (TextView)findViewById(R.id.day1);
			TextView day2 = (TextView)findViewById(R.id.day2);
			TextView day3 = (TextView)findViewById(R.id.day3);
			TextView day4 = (TextView)findViewById(R.id.day4);
			TextView day5 = (TextView)findViewById(R.id.day5);

			day1.setText(_dateArray.get(0));
			day2.setText(_dateArray.get(1));
			day3.setText(_dateArray.get(2));
			day4.setText(_dateArray.get(3));
			day5.setText(_dateArray.get(4));

			TextView day1Max = (TextView)findViewById(R.id.day1Max);
			TextView day2Max = (TextView)findViewById(R.id.day2Max);
			TextView day3Max = (TextView)findViewById(R.id.day3Max);
			TextView day4Max = (TextView)findViewById(R.id.day4Max);
			TextView day5Max = (TextView)findViewById(R.id.day5Max);

			day1Max.setText(_hiArray.get(0)+ " F¡");
			day2Max.setText(_hiArray.get(1)+ " F¡");
			day3Max.setText(_hiArray.get(2)+ " F¡");
			day4Max.setText(_hiArray.get(3)+ " F¡");
			day5Max.setText(_hiArray.get(4)+ " F¡");

			TextView day1Min = (TextView)findViewById(R.id.day1Min);
			TextView day2Min = (TextView)findViewById(R.id.day2Min);
			TextView day3Min = (TextView)findViewById(R.id.day3Min);
			TextView day4Min = (TextView)findViewById(R.id.day4Min);
			TextView day5Min = (TextView)findViewById(R.id.day5Min);

			day1Min.setText(_lowArray.get(0)+ " F¡");
			day2Min.setText(_lowArray.get(1)+ " F¡");
			day3Min.setText(_lowArray.get(2)+ " F¡");
			day4Min.setText(_lowArray.get(3)+ " F¡");
			day5Min.setText(_lowArray.get(4)+ " F¡");
		} else {
			TextView day1 = (TextView)findViewById(R.id.day1);
			day1.setText(_dateArray.get(0));
			TextView day1Max = (TextView)findViewById(R.id.day1Max);
			day1Max.setText(_hiArray.get(0)+ " F¡");
			TextView day1Min = (TextView)findViewById(R.id.day1Min);
			day1Min.setText(_lowArray.get(0)+ " F¡");
		}



	}



}

