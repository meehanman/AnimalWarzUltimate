package com.threeml.awu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * SplashScreen that shows before game starts. 
 * After nSeconds, Starts Main Activity (mGame)
 * Shows the 3ml Logo
 * 
 * @author Mark
 *
 */



public class SplashScreen extends Activity{
	
	private static int SPLASH_TIME_OUT = 500;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);
		
		new Handler().postDelayed(new Runnable () {
			
			// Upon completion of the splash screen the main activity is started.
			@Override
			public void run() {
		
				Intent i = new Intent(getBaseContext(), MainActivity.class);
				startActivity(i);
				
				finish();
			}
			
		}, SPLASH_TIME_OUT);
	}
}
