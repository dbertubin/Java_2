package com.petrockz.climacast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class WeatherContentProvider extends ContentProvider{

	public static final String AUTHORITY = "com.petrockz.climacast.weathercontentprovider";

	public static class WeatherData implements BaseColumns{

		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/items");

		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.petrockz.climacast.item";

		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.dir/vnd.com.petrockz.climacast.item";

		// Define Columns 
		public static final String DATE_COLUMN = "date";
		public static final String MAXTEMPF_COLUMN = "hi";
		public static final String MINTEMPF_COLUMN = "low";
		public static final String WEATHERDESC_COLUMN = "description";

		public static final String[] PROJECTION = { "_Id", DATE_COLUMN, MAXTEMPF_COLUMN, MINTEMPF_COLUMN, WEATHERDESC_COLUMN};

		private WeatherData(){};


	}

	public static final int ITEMS = 1;
	public static final int ITEMS_ID = 2;
	//	public static final int
	//	public static final int


	private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

	static {
		uriMatcher.addURI(AUTHORITY, "items/", ITEMS);
		uriMatcher.addURI(AUTHORITY, "items/", ITEMS_ID);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub

		switch (uriMatcher.match(uri)) {
		case ITEMS:
			return WeatherData.CONTENT_TYPE;

		case ITEMS_ID:
			return WeatherData.CONTENT_ITEM_TYPE;
		}
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();	
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		return false;
	}

	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		MatrixCursor result = new MatrixCursor(WeatherData.PROJECTION);

		String JSONString  = ReadWrite.readStringFile(getContext(),"weatherData", false);
		JSONObject JSONObj = null;
		JSONObject data = null;
		JSONArray weatherArray = null; 
		JSONObject details = null; 
//		JSONArray weatherDesc = null;



		try {
			JSONObj = new JSONObject(JSONString);
			data = JSONObj.getJSONObject(DataStrings.JSON_DATA);
			weatherArray = data.getJSONArray(DataStrings.JSON_WEATHER);
		} catch (JSONException e) {
			e.printStackTrace();
		}


		if (data == null) {
			return result;
		}



		switch (uriMatcher.match(uri)) {
		case ITEMS:

			for (int i = 0; i < weatherArray.length(); i++) {

				try {
					details = data.getJSONArray(DataStrings.JSON_WEATHER).getJSONObject(i);
//					weatherDesc = details.getJSONArray(DataStrings.JSON_WEATHER_WEATHERDESC).getJSONArray(0);
					result.addRow(new Object[]{ i + 1, details.get(DataStrings.JSON_WEATHER_DATE),details.get(DataStrings.JSON_WEATHER_HI),details.get(DataStrings.JSON_WEATHER_LO),details.get(DataStrings.JSON_WEATHER_WEATHERDESC_VALUE)});
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}


			//			return WeatherData.CONTENT_TYPE;

		case ITEMS_ID:
			//			return WeatherData.CONTENT_ITEM_TYPE;
		}

		return result;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}


}
