package com.petrockz.climacast;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class FormFragment extends Fragment {

	private FormListener listener; 
	
	static Context _context;
	Button _getWeatherButton;
	Button _saveFavButton;
	Button _viewFavButton;
	Button _showMapButton;
	EditText _inputText;
	String _zip;
	
	
	public interface FormListener{
		
		public void getWeather(String zip);
		public void saveFavorite(String zip);
		public void viewFavorites();
		public void showMap(String zip);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			super.onCreateView(inflater, container, savedInstanceState);
			
			LinearLayout view = (LinearLayout) inflater.inflate(R.layout.form, container, false);
			
			_inputText = (EditText) view.findViewById(R.id.editText);
			_inputText.setInputType(InputType.TYPE_CLASS_NUMBER);
			
			_zip = _inputText.getText().toString();
			
			/// SAVE FAVORITES 
			
			_saveFavButton = (Button) view.findViewById(R.id.saveFav);
			_saveFavButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					listener.saveFavorite(_zip);
					
				}	
			});
			
			
			/// GET MAP 
			_showMapButton = (Button) view.findViewById(R.id.show);
			_showMapButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

				listener.showMap(_zip);	

				}
			}); 
			
			/// VIEW FAVORITES 
			_viewFavButton  = (Button) view.findViewById(R.id.viewFav);
			_viewFavButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					listener.viewFavorites();

				}
			});
			
			
			/// GET WEATHER 
			
			_getWeatherButton = (Button)view.findViewById(R.id.startButton);
			_getWeatherButton.setOnClickListener(new OnClickListener() {

				@SuppressLint("HandlerLeak")
				@Override
				public void onClick(final View v) {
				
					listener.getWeather(_zip);
				}

			});
			
			return view;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
			listener = (FormListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must be FormListener");
		}
	}
	
}
