package com.petrockz.climacast;

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
		startActivity(myIntent);
		finish();
	}

}



