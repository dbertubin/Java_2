package com.petrockz.climacast;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class FormFragment extends Fragment {

	private FormListener listener;

	public interface FormListener{
		
		/// CUSTOM METHODS CALLED ON MAIN ACTIVITY
		public void onFavorites(View v);
		public void onSaveFavorites(String string);
		public void onGetWeather(String string);
		public void onShowMap(String string);

	}

	Button _startButton;
	Button _saveFavButton;
	Button _viewFavButton;
	Button _showMapButton;
	EditText _inputText;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		LinearLayout view = (LinearLayout) inflater.inflate(R.layout.form, container, false);

//		_startButton = (Button)getActivity().findViewById(R.id.startButton);
//		_startButton.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(final View v) {
//				// DISMISSES KEYBOARD on CLICK 
//				_inputText = (EditText)getActivity().findViewById(R.id.editText);
//				_inputText.setInputType(InputType.TYPE_CLASS_NUMBER);
//				InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//				imm.hideSoftInputFromWindow(_inputText.getWindowToken(), 0);
//			
//				listener.onGetWeather(_inputText.getText().toString());
//			}
//		});
//		
//		
//		_showMapButton = (Button) getActivity().findViewById(R.id.show);
//		_showMapButton.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//
//				listener.onShowMap(_inputText.getText().toString());
//			}
//		}); 
//
//
//		_saveFavButton = (Button) getActivity().findViewById(R.id.saveFav);
//		_saveFavButton.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				listener.onSaveFavorites(_inputText.getText().toString());
//				
//			}
//		});
//
//		_viewFavButton  = (Button) getActivity().findViewById(R.id.viewFav);
//		_viewFavButton.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				listener.onFavorites(v);
//			}
//		});
		return view;
	}

	@Override
	public void onAttach(Activity activity) {

		super.onAttach(activity);

		try {
			listener = (FormListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " Must be a FormListener");	
		}
	}
}
