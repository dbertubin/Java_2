//package com.petrockz.climacast;
//
//
//import java.util.ArrayList;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.Fragment;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.AdapterView.OnItemLongClickListener;
//
//public class FavoritesFragment extends Fragment {
//
//	public static final String FILE_NAME = "favsArray";
////	
//	private FavoritesListener listener;
////	
//	ListView _listView;
//	static Context _context;
//	ArrayList<String> _favorites;
//	
//	public interface FavoritesListener{
//		
//		public void onFavoriteSelected(String zip);
//			
//	}
//	
//	
//	
//	
//	@Override
//	public void onAttach(Activity activity) {
//		// TODO Auto-generated method stub
//		super.onAttach(activity);
//		
//		try {
//			listener = (FavoritesListener) activity;
//		} catch (ClassCastException e) {
//			throw new ClassCastException(activity.toString() + " must be FavoritesListener");
//		}
//	}
//	
//	
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		_favorites  = getFavs();
//	
//	}
//	
//	
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		super.onCreateView(inflater, container, savedInstanceState);
//		
//		LinearLayout view = (LinearLayout) inflater.inflate(R.layout.listview, container, false);
//	
//		/// ATTACH LIST ADAPTER
//		_listView = (ListView) view.findViewById(R.id.fav_list);
//		final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, _favorites);
//		_listView.setAdapter(arrayAdapter); 
//
//		
//		/// LIST ONITEMCLICKED 
//		_listView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View arg1, int position,
//					long arg3) {
//				
//				listener.onFavoriteSelected(_favorites.get(position));
//			}
//		});
//		
//		/// LIST ONITEMLONGCLICK
//		_listView.setOnItemLongClickListener(new OnItemLongClickListener() {
//
//			@Override
//			public boolean onItemLongClick(AdapterView<?> parent, View arg1, final int position,
//					long arg3) {
//
//				// AlertDialog if not connected
//				AlertDialog.Builder alert = new AlertDialog.Builder(_context);
//				alert.setTitle("Yo!");
//				alert.setMessage("Do you want to delete " + parent.getItemAtPosition(position).toString() + " ?");
//				alert.setCancelable(false);
//				alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialogInterface, int i) {
//
//						_favorites.remove(position);
//						ReadWrite.storeObjectFile(_context, Favorites.FILE_NAME, _favorites, false);
//						dialogInterface.dismiss();
//						arrayAdapter.notifyDataSetChanged();
//						if (getFavs().size()== 0) {
//							getActivity().finish();
//						}
//					}});
//
//				alert.setNegativeButton("No", new DialogInterface.OnClickListener(){
//
//					@Override
//					public void onClick(DialogInterface dialogInterface, int i) {
//						dialogInterface.cancel();
//					}
//				});		
//
//				alert.show();
//
//				return false;
//
//			}
//
//		});
//		
//		return view;
//		
//	}
//	
//	@SuppressWarnings("unchecked")
//	public static ArrayList<String> getFavs (){
//
//		Object stored = ReadWrite.readStringObject(_context, FILE_NAME, false);
//		ArrayList<String> favs;
//		if (stored == null) {
//			Log.i("HISTORY", "NO HISTORY FILE FOUND");
//			favs = new ArrayList<String>();
//		} else {
//			favs = (ArrayList<String>) stored;
//		}
//		return favs;
//	}
//	
//}
