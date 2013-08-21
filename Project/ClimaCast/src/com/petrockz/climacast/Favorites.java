package com.petrockz.climacast;

import java.util.ArrayList;

import com.petrockz.climacast.ReadWrite;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Favorites extends Activity{

	ListView _listView;
	static Context _context;
	ArrayList<String> _favorites;

	public static final String FILE_NAME = "favsArray";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		_context = this;
		_favorites = getFavs();
		_listView = (ListView) findViewById(R.id.listview);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, _favorites);
		_listView.setAdapter(arrayAdapter); 
	}



	@SuppressWarnings("unchecked")
	public static ArrayList<String> getFavs (){
		
		Object stored = ReadWrite.readStringObject(_context, FILE_NAME, false);
		ArrayList<String> favs;
		if (stored == null) {
			Log.i("HISTORY", "NO HISTORY FILE FOUND");
			favs = new ArrayList<String>();
		} else {
			favs = (ArrayList<String>) stored;
		}
		return favs;
	}

}



