package com.petrockz.climacast;

import com.petrockz.climacast.FavoritesFragment.FavoritesListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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
		// TODO Auto-generated method stub
		Intent myIntent = new Intent(_context, MainActivity.class);
		myIntent.putExtra("item", zip);
		myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		//				setResult(Activity.RESULT_OK, myIntent);
		startActivityForResult(myIntent, 0);
		finish();
	}


}



