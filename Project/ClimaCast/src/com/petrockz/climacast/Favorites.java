package com.petrockz.climacast;

import java.util.ArrayList;

import com.petrockz.climacast.ReadWrite;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
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
		_favorites  = getFavs();




		_listView = (ListView) findViewById(R.id.listview);
		final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, _favorites);
		_listView.setAdapter(arrayAdapter); 

		_listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View arg1, int position,
					long arg3) {
				String item = adapter.getItemAtPosition(position).toString();
				Intent myIntent = new Intent(arg1.getContext(), MainActivity.class);
				myIntent.putExtra("item", item);
				myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
				//				setResult(Activity.RESULT_OK, myIntent);
				startActivityForResult(myIntent, 0);
				finish();
			}
		});

		_listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View arg1, final int position,
					long arg3) {

				// AlertDialog if not connected
				AlertDialog.Builder alert = new AlertDialog.Builder(_context);
				alert.setTitle("Yo!");
				alert.setMessage("Do you want to delete " + adapter.getItemAtPosition(position).toString() + " ?");
				alert.setCancelable(false);
				alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						//						ArrayList<String> favorites = new ArrayList<String>();

						_favorites.remove(position);
						ReadWrite.storeObjectFile(_context, Favorites.FILE_NAME, _favorites, false);
						dialogInterface.dismiss();
						arrayAdapter.notifyDataSetChanged();
						if (getFavs().size()== 0) {
							finish();
						}
					}});

				alert.setNegativeButton("No", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						dialogInterface.cancel();
					}
				});		

				alert.show();

				return false;

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



