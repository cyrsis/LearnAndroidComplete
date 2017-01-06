package com.viaviapp.newsapplication;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.onesignal.OneSignal;
import com.startapp.android.publish.Ad;
import com.startapp.android.publish.AdEventListener;
import com.startapp.android.publish.StartAppAd;

public class MainActivity extends ActionBarActivity implements MaterialTabListener {

	MaterialTabHost tabHost;
	ViewPager pager;
	ViewPagerAdapter adapter;
	private String[] tabs = {"LATEST","ALL NEWS","MY FAVORITES"};
	Toolbar toolbar;
 	private AdView mAdView;
	private StartAppAd startAppAd = new StartAppAd(this); 

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		OneSignal.startInit(this).init();
		StartAppAd.init(this, getString(R.string.startapp_dev_id), getString(R.string.startapp_app_id,false));
		setContentView(R.layout.activity_main);
		toolbar = (Toolbar) this.findViewById(R.id.toolbar);
		toolbar.setTitle(getString(R.string.app_name));
		this.setSupportActionBar(toolbar);
		StartAppAd.showSlider(this);
		mAdView = (AdView) findViewById(R.id.adView);
		mAdView.loadAd(new AdRequest.Builder().build());

		startAppAd.loadAd(new AdEventListener() {

			@Override
			public void onReceiveAd(Ad arg0) {
				// TODO Auto-generated method stub
				startAppAd.showAd(); // show the ad
				startAppAd.loadAd(); // load the next ad
			}

			@Override
			public void onFailedToReceiveAd(Ad arg0) {
				// TODO Auto-generated method stub

			}
		});

		tabHost = (MaterialTabHost) this.findViewById(R.id.tabHost);
		pager = (ViewPager) this.findViewById(R.id.pager );
		adapter = new ViewPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(adapter);


		pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// when user do a swipe the selected tab change
				tabHost.setSelectedNavigationItem(position);

			}
		});

		for(String tab_name:tabs)
		{
			tabHost.addTab(
					tabHost.newTab()
					.setText(tab_name)
					.setTabListener(MainActivity.this)
					);
		}

	}

	@Override
	public void onTabSelected(MaterialTab tab) {
		// TODO Auto-generated method stub
		pager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabReselected(MaterialTab tab) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabUnselected(MaterialTab tab) {
		// TODO Auto-generated method stub

	}

	private class ViewPagerAdapter extends FragmentStatePagerAdapter {

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);

		}

		public Fragment getItem(int num) {

			switch (num) {
			case 0:
				return new LatestFragment();
			case 1:
				return new AllNewsFragment();
			case 2:
				return new FavoriteFragment();

			}
			return null;
		}

		@Override
		public int getCount() {
			return 3;
		}

	}
	 
	 
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// Toast.makeText(appContext, "BAck", Toast.LENGTH_LONG).show();
			AlertDialog.Builder alert = new AlertDialog.Builder(new ContextThemeWrapper(MainActivity.this,R.style.Dialog));
			alert.setTitle(getString(R.string.app_name));
			alert.setIcon(R.drawable.app_icon);
			alert.setMessage("Are You Sure You Want To Quit?");

			alert.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int whichButton) {

					finish();
				}



			});

			alert.setNegativeButton("Rate App",
					new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

					final String appName = getPackageName();//your application package name i.e play store application url
					try {
						startActivity(new Intent(Intent.ACTION_VIEW,
								Uri.parse("market://details?id="
										+ appName)));
					} catch (android.content.ActivityNotFoundException anfe) {
						startActivity(new Intent(
								Intent.ACTION_VIEW,
								Uri.parse("http://play.google.com/store/apps/details?id="
										+ appName)));
					}

				}
			});
			alert.show();
			return true;
		}

		return super.onKeyDown(keyCode, event);

	}
	 
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//
//		MenuInflater inflater = getMenuInflater();
//		inflater.inflate(R.menu.main, menu);
//
//
//		return super.onCreateOptionsMenu(menu);
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//
//		switch (item.getItemId()) 
//		{
//		case R.id.menu_about:
//
//			Intent about=new Intent(getApplicationContext(),AboutActivity.class);
//			startActivity(about);
//
//			return true;
//
//		case R.id.menu_overflow:
//			return true;
//
//		case R.id.menu_moreapp:
//
//			startActivity(new Intent(
//					Intent.ACTION_VIEW,
//					Uri.parse(getString(R.string.play_more_apps))));
//
//			return true;
//
//		case R.id.menu_rateapp:
//
//			final String appName = getPackageName();//your application package name i.e play store application url
//			try {
//				startActivity(new Intent(Intent.ACTION_VIEW,
//						Uri.parse("market://details?id="
//								+ appName)));
//			} catch (android.content.ActivityNotFoundException anfe) {
//				startActivity(new Intent(
//						Intent.ACTION_VIEW,
//						Uri.parse("http://play.google.com/store/apps/details?id="
//								+ appName)));
//			}
//			return true;
//
//		default:
//			return super.onOptionsItemSelected(item);
//		}
//}
}
