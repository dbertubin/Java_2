package com.petrockz.climacast;

import java.util.ArrayList;

import com.petrockz.climacast.ReadWrite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Favorites extends Activity implements OnItemClickListener{

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
		
		_listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View arg1, int position,
					long arg3) {
				String item = adapter.getItemAtPosition(position).toString();
				Intent myIntent = new Intent(arg1.getContext(), MainActivity.class);
				myIntent.putExtra("item", item);
				startActivityForResult(myIntent, 0);
			}
		});
		
		
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





	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

}



