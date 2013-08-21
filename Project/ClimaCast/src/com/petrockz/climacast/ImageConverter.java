package com.petrockz.climacast;
import android.util.Log;

import com.petrockz.climacast.R;

// The ImageConverter class acts as an adapter to convert the weatherDesc string to the corresponding image 


public class ImageConverter {


	public static Integer getConditionImage (String weatherDesc) {
		
		Integer image = null;

		if (weatherDesc.equals("Sunny")||weatherDesc.equals("Clear")) {
			
			image = R.drawable.sunny;

		} else if (weatherDesc.equals("Patchy light rain in area with thunder")|| weatherDesc.equals("Thundery outbreaks in nearby") ) {

			image = R.drawable.tstorm1;
			
		} else if (weatherDesc.equals("Moderate or heavy rain shower")|| weatherDesc.equals("Moderate rain")||weatherDesc.equals("Moderate rain at times"))  {

			image = R.drawable.shower3;
			
		} else if (weatherDesc.equals("Heavy rain") || weatherDesc.equals("Heavy rain at times")) {

			image = R.drawable.shower1;
			
		} else if (weatherDesc.equals("Light rain") || weatherDesc.equals("Patchy light rain")|| weatherDesc.equals("Patchy rain nearby")||weatherDesc.equals("Light rain shower")) {

			image = R.drawable.shower1;
			
		} else if (weatherDesc.equals("Partly Cloudy")) {

			image = R.drawable.cloudy2;
			
		} else if (weatherDesc.equals("Cloudy")) {

			image = R.drawable.cloudy4;
		}  else if (weatherDesc.equals("Mist")) {

			image = R.drawable.mist;
		}

		else  {
			image = R.drawable.dunno;
		}

		Log.i("weatherDesc" , weatherDesc);
		return image;
	}
}
