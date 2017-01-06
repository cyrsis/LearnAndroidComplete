package com.viaviapp.newsapplication;

import com.viaviapp.newsapplication.R;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.content.Intent;

public class SplashActivity extends ActionBarActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		 
		
		Thread splashThread = new Thread() {
			@Override
			public void run() {
				try {
					int waited = 0;
					while (waited < 2000) {
						sleep(100);
						waited += 100;
					}
				} catch (InterruptedException e) {
					// do nothing
				} finally {
			 
					Intent i = new Intent(getApplicationContext(),MainActivity.class);
					startActivity(i);
					finish();
					 
				}
			}
		};
		splashThread.start();
	}
	
}