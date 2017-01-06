package freaktemplate.singlerestau;



import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import freaktemplate.singlerestau.utils.AlertDialogManager;
import freaktemplate.singlerestau.utils.ConnectionDetector;

public class Aboutus extends Activity {

	TextView textView1, textView2, textView3, textView4, textView5;
	InterstitialAd mInterstitialAd;
	boolean interstitialCanceled;
	ConnectionDetector cd;
	AlertDialogManager alert = new AlertDialogManager();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aboutus);

		adsview();
		

	}

	private void adsview() {
		// TODO Auto-generated method stub
		if (getString(R.string.bannerads).equals("yes")) {
			AdView mAdView = (AdView) findViewById(R.id.adView);
			AdRequest adRequest = new AdRequest.Builder().build();
			mAdView.loadAd(adRequest);
		} else if (getString(R.string.bannerads).equals("no")) {

			AdView mAdView = (AdView) findViewById(R.id.adView);
			mAdView.setVisibility(View.GONE);

		}

		if (getString(R.string.insertialads).equals("yes")) {
			interstitialCanceled = false;
			CallNewInsertial();
		} else if (getString(R.string.insertialads).equals("no")) {

		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				// Your code to show add

				if (interstitialCanceled) {

				} else {

					if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
						mInterstitialAd.show();

					} else {

						// ContinueIntent();
					}
				}

			}
		}, 5000);
	}

	private void CallNewInsertial() {
		cd = new ConnectionDetector(Aboutus.this);

		if (!cd.isConnectingToInternet()) {
			alert.showAlertDialog(Aboutus.this,getString(R.string.internet),
					getString(R.string.internettext), false);
			return;
		} else {
			// AdView mAdView = (AdView) findViewById(R.id.adView);
			// AdRequest adRequest = new AdRequest.Builder().build();
			// mAdView.loadAd(adRequest);
			Log.d("call", "call");

			mInterstitialAd = new InterstitialAd(Aboutus.this);
			mInterstitialAd.setAdUnitId(getString(R.string.insertial_ad_key));
			requestNewInterstitial();
			mInterstitialAd.setAdListener(new AdListener() {
				@Override
				public void onAdClosed() {

				}

			});

		}
	}

	private void requestNewInterstitial() {
		Log.d("request", "request");
		final AdRequest adRequest = new AdRequest.Builder().build();
		mInterstitialAd.loadAd(adRequest);

	}

	@Override
	public void onPause() {
		mInterstitialAd = null;
		interstitialCanceled = true;
		super.onPause();
	}
}
