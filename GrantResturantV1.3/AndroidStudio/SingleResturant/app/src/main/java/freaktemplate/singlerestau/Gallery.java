package freaktemplate.singlerestau;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.InflateException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import freaktemplate.singlerestau.utils.AlertDialogManager;
import freaktemplate.singlerestau.utils.ConnectionDetector;
import freaktemplate.singlerestau.utils.ImageAdapter;
import freaktemplate.singlerestau.utils.ImageLoader;
import freaktemplate.singlerestau.utils.ImagePagerAdapter;

public class Gallery extends Activity {
	ImageView imageView;
	RelativeLayout rl_back, rl_back1;
	View layout12;

	String[] separated = null;
	public static final String MY_PREFS_NAME = "Restaurant";
	String images;

	InterstitialAd mInterstitialAd;
	boolean interstitialCanceled;
	ConnectionDetector cd;
	AlertDialogManager alert = new AlertDialogManager();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);

		adviews();
		init();

	}

	private void init() {
		// TODO Auto-generated method stub
		
		cd = new ConnectionDetector(getApplicationContext());
		
		//get preference data
		
		SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
		if (prefs.getString("images", null) != null) {
			images = prefs.getString("images", null);
		} else {
			images = "";
		}
		separated = images.split(",");
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);

		//Image bind in gridview
		GridView gridview = (GridView) findViewById(R.id.gridview);
		ImageAdapter lazy = new ImageAdapter(Gallery.this, separated);
		gridview.setAdapter(lazy);

		gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				// Send intent to SingleViewActivity

				layout12 = v;
				if (rl_back == null) {
					Log.d("second", "second");
					RelativeLayout rl_dialoguser = (RelativeLayout) findViewById(R.id.rl_infodialog);

					try {
						layout12 = getLayoutInflater().inflate(R.layout.activity_image_view_pager, rl_dialoguser, false);
					} catch (InflateException e) {
						// TODO: handle exception
					}
					

					rl_dialoguser.addView(layout12);

					ImageAdapter imageAdapter = new ImageAdapter(Gallery.this, separated);
					List<ImageView> images = new ArrayList<ImageView>();

					// Retrieve all the images
					for (int i = 0; i < imageAdapter.getCount(); i++) {
						ImageView imageView = new ImageView(Gallery.this);
						imageView.setImageResource(R.drawable.gallery_img_bg);
						String imageurl = getString(R.string.liveurl) + "uploads/slide/"
								+ separated[i].replace("[", "").replace("]", "").replace("\"", "");
						// imageView.setImageReso(imageurl);
						ImageLoader imgLoader = new ImageLoader(Gallery.this);
						imgLoader.DisplayImage(imageurl.replace(" ", "%20"), imageView);
						imageView.setLayoutParams(new GridView.LayoutParams(350, 350));
						// imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

						images.add(imageView);
					}

					// Set the images into ViewPager
					ImagePagerAdapter pageradapter = new ImagePagerAdapter(images);
					ViewPager viewpager = (ViewPager) layout12.findViewById(R.id.pager);
					viewpager.setAdapter(pageradapter);
					
					// Show images following the position
					viewpager.setCurrentItem(position);
					rl_back1 = (RelativeLayout) layout12.findViewById(R.id.rl_back1);
					Button btn_close = (Button) layout12.findViewById(R.id.btn_close);
					btn_close.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							rl_back1.setVisibility(View.INVISIBLE);
						}
					});
				}

			}
		});
	}

	private void adviews() {
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

	// show InterstitialAd
	
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
		cd = new ConnectionDetector(Gallery.this);

		if (!cd.isConnectingToInternet()) {
			alert.showAlertDialog(Gallery.this, getString(R.string.internet),
					getString(R.string.internettext), false);
			return;
		} else {
			
			Log.d("call", "call");

			mInterstitialAd = new InterstitialAd(Gallery.this);
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
