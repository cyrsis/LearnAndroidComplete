package freaktemplate.singlerestau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import freaktemplate.singlerestau.utils.AlertDialogManager;
import freaktemplate.singlerestau.utils.CatGetSet;
import freaktemplate.singlerestau.utils.ConnectionDetector;

public class Offer extends Activity {

	ListView listview1;
	ProgressDialog dialog;
	String[] str = { "1", "2", "3" };
	ArrayList<CatGetSet> rest;
	ProgressDialog progressDialog;
	Boolean isInternetPresent = false;
	ConnectionDetector cd;
	View layout12;
	InterstitialAd mInterstitialAd;
	boolean interstitialCanceled;

	AlertDialogManager alert = new AlertDialogManager();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_offer);

		adsview();
		init();

	}

	private void init() {
		// TODO Auto-generated method stub
		
		cd = new ConnectionDetector(getApplicationContext());

		isInternetPresent = cd.isConnectingToInternet();

		if (isInternetPresent) {

			rest = new ArrayList<CatGetSet>();
			new getofferdetail().execute();
		} else {

			RelativeLayout rl_back = (RelativeLayout) findViewById(R.id.rl_back);
			if (rl_back == null) {
				Log.d("second", "second");
				RelativeLayout rl_dialoguser = (RelativeLayout) findViewById(R.id.rl_infodialog);

				
				try {
					layout12 = getLayoutInflater().inflate(R.layout.addcart, rl_dialoguser, false);
				} catch (InflateException e) {
					// TODO: handle exception
				}
				

				rl_dialoguser.addView(layout12);

				ImageView img = (ImageView) layout12.findViewById(R.id.imageView);
				img.setImageResource(R.drawable.warning_btn);
				TextView txt_dia = (TextView) layout12.findViewById(R.id.txt_dia);
				txt_dia.setText("" + getString(R.string.nointernet));
				Button btn_yes = (Button) layout12.findViewById(R.id.btn_yes);
				btn_yes.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						finish();
					}
				});
			}

		}

		
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

	// getting special offer detail
	public class getofferdetail extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(Offer.this);
			progressDialog.setMessage(getString(R.string.loading));
			progressDialog.setCancelable(true);
			progressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			getdetailforNearMe();
			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);
			if (progressDialog.isShowing()) {
				progressDialog.dismiss();

				listview1 = (ListView) findViewById(R.id.listView1);
				LazyAdapter lazy = new LazyAdapter(Offer.this, rest);
				listview1.setAdapter(lazy);
			}
		}

	}

	//get offer from json url
	
	private void getdetailforNearMe() {
		// TODO Auto-generated method stub

		URL hp = null;
		try {

			hp = new URL(getString(R.string.liveurl) + "webservice/offers.php");

			Log.d("URL", "" + hp);
			URLConnection hpCon = hp.openConnection();
			hpCon.connect();
			InputStream input = hpCon.getInputStream();
			Log.d("input", "" + input);

			BufferedReader r = new BufferedReader(new InputStreamReader(input));

			String x = "";
			x = r.readLine();
			String total = "";

			while (x != null) {
				total += x;
				x = r.readLine();
			}
			Log.d("URL", "" + total);
			JSONObject jObject = new JSONObject(total);
			JSONArray j = jObject.getJSONArray("Offers");
			// JSONArray j = new JSONArray(total);

			Log.d("URL1", "" + j);
			for (int i = 0; i < j.length(); i++) {

				JSONObject Obj;
				Obj = j.getJSONObject(i);

				CatGetSet temp = new CatGetSet();
				temp.setTitle(Obj.getString("title"));
				temp.setDescription(Obj.getString("description"));

				rest.add(temp);
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO: handle exception
		}
	}

	//bind offer in listview

	public class LazyAdapter extends BaseAdapter {

		private Activity activity;
		private ArrayList<CatGetSet> data;
		private LayoutInflater inflater = null;

		Typeface tf = Typeface.createFromAsset(Offer.this.getAssets(), "fonts/verdana.ttf");
		Typeface tf1 = Typeface.createFromAsset(Offer.this.getAssets(), "fonts/verdanab.ttf");

		public LazyAdapter(Activity a, ArrayList<CatGetSet> d) {
			activity = a;
			data = d;
			inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View vi = convertView;

			if (convertView == null) {
				vi = inflater.inflate(R.layout.offer_cell, null);
			}
			
			try {
				// GetSet NewObj = FileList.get(position);
				TextView title = (TextView) vi.findViewById(R.id.txt_price);
				TextView detail = (TextView) vi.findViewById(R.id.txt_detail);
				// title.setText(NewObj.getTitle());
				title.setText(data.get(position).getTitle());

				detail.setText(data.get(position).getDescription());
				title.setTypeface(tf1);
				detail.setTypeface(tf);
			} catch (NullPointerException e) {
				// TODO: handle exception
			}
			
			return vi;
		}
	}
	
	//to interstitial ad

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
		cd = new ConnectionDetector(Offer.this);

		if (!cd.isConnectingToInternet()) {
			alert.showAlertDialog(Offer.this,getString(R.string.internet),
					getString(R.string.internettext), false);
			return;
		} else {
			
			Log.d("call", "call");

			mInterstitialAd = new InterstitialAd(Offer.this);
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
