
package com.petrockz.climacast;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class FavoritesFragment extends Fragment implements OnItemClickListener{
	
	public static final String FILE_NAME = "favsArray";

	private FavoritesListener listener;
	
	ListView _listView;
	Context _context;
	ArrayList<String> _favorites = new ArrayList<String>();
	
	public interface FavoritesListener{
		
		public void onFavoriteSelected(String zip);
		public ArrayList<String> onGetFavorites();	
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
		try {
			listener = (FavoritesListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must be FavoritesListener");
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		_favorites = listener.onGetFavorites();
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		LinearLayout view = (LinearLayout) inflater.inflate(R.layout.listview, container, false);
		
			/// ATTACH LIST ADAPTER
		_listView = (ListView) view.findViewById(R.id.fav_list);
		final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, _favorites);
		_listView.setAdapter(arrayAdapter); 
		
		/// LIST ONITEMCLICKED 
		_listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				listener.onFavoriteSelected(_favorites.get(position));
			}
		});
		
		
		return view;
	}
	


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
	
}
