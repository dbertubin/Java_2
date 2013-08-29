package com.petrockz.climacast;

import java.util.ArrayList;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class Favorites extends Activity implements FavoritesFragment.FavoritesListener{

	Context _context;

	public static final String FILE_NAME = "favsArray";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("Favorites" , "Setting CV");
		setContentView(R.layout.favorites_fragment);
		_context = this;
		Log.i("CONTEXT =", "YAY");
	}

	@Override
	public void onFavoriteSelected(String zip) {
		Intent myIntent = new Intent(_context, MainActivity.class);
		myIntent.putExtra("item", zip);
		myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		//				setResult(Activity.RESULT_OK, myIntent);
		startActivityForResult(myIntent, 0);
		finish();
	}



	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<String> onGetFavorites() {
		
		Object stored = ReadWrite.readStringFile(_context, FILE_NAME, false);
		ArrayList<String> favs;
		if (stored == null) {
			Log.i("HISTORY", "NO HISTORY FILE FOUND");
			favs = new ArrayList<String>();
		} else {
			favs = (ArrayList<String>) stored;
		}
		
		Log.i("Favorites", favs.toString());
		return favs;
		
	}
	


}



