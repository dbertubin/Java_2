package com.petrockz.climacast;

import java.util.ArrayList;

import com.petrockz.climacast.FavoritesFragment.FavoritesListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class Favorites extends Activity implements FavoritesListener{

	Context _context;

	public static final String FILE_NAME = "favsArray";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favorites_fragment);
		_context = this;
				
		

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
	public  ArrayList<String> getFavs (){
		
		Object stored = ReadWrite.readStringFile(_context, FILE_NAME, false);
		ArrayList<String> favs;
		if (stored == null) {
			Log.i("HISTORY", "NO HISTORY FILE FOUND");
			favs = new ArrayList<String>();
		} else {
			favs = (ArrayList<String>) stored;
		}
		return favs;
	}

	@Override
	public ArrayList<String> onGetFavorites() {
		return getFavs();
	}
	

}



