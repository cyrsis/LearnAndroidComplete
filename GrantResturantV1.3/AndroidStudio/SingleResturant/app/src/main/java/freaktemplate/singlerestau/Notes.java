package freaktemplate.singlerestau;

import java.io.IOException;
import java.util.ArrayList;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import freaktemplate.singlerestau.utils.AlertDialogManager;
import freaktemplate.singlerestau.utils.ConnectionDetector;
import freaktemplate.singlerestau.utils.DataBaseHelper;
import freaktemplate.singlerestau.utils.ItemGetSet;

public class Notes extends Activity {

	ListView listview1;
	ProgressDialog dialog;
	String[] str = { "1", "2", "3" };
	SQLiteDatabase db;
	ArrayList<ItemGetSet> Item;
	String description, quentity, total, price;
	int MAINPOSITION = 0;
	Cursor cur = null;
	int inttotal;
	double percentage, finalsum;
	InterstitialAd mInterstitialAd;
	boolean interstitialCanceled;
	ConnectionDetector cd;
	AlertDialogManager alert = new AlertDialogManager();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notes);

		
		Typeface tf = Typeface.createFromAsset(Notes.this.getAssets(), "fonts/verdana.ttf");
		TextView txt_head = (TextView) findViewById(R.id.txt_head);
	//	TextView txt_service = (TextView) findViewById(R.id.txt_service);
		TextView txt_total = (TextView) findViewById(R.id.txt_total);
		if (getString(R.string.insertialads).equals("yes")) {
			interstitialCanceled = false;
			CallNewInsertial();
		} else if (getString(R.string.insertialads).equals("no")) {

		}
		
		Item = new ArrayList<ItemGetSet>();
		Get_ChapterList();

	}

	private void Get_ChapterList() {
		// TODO Auto-generated method stub
		/*
		 * dialog = new ProgressDialog(Notes.this);
		 * dialog.setTitle("Processing..."); dialog.show();
		 */
		new SplashScreenDelay().execute();
	}

	@SuppressLint("NewApi")
	private class SplashScreenDelay extends AsyncTask<String, Integer, Integer> {

		private static final String TAG = "SplashScreenTask";

		@Override
		protected Integer doInBackground(String... params) {
			// TODO Auto-generated method stub

			Item.clear();
			DataBaseHelper myDbHelper = new DataBaseHelper(Notes.this);
			myDbHelper = new DataBaseHelper(Notes.this);
			try {
				myDbHelper.createDataBase();
			} catch (IOException e) {

				e.printStackTrace();
			}

			try {

				myDbHelper.openDataBase();

			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}

			int i = 1;
			db = myDbHelper.getReadableDatabase();
			try {
				cur = db.rawQuery("select * from detail", null);
			} catch (SQLException e) {
				// TODO: handle exception
			}

			Log.d("SIZWA", "" + cur.getCount());
			if (cur.getCount() != 0) {
				if (cur.moveToFirst()) {
					do {
						ItemGetSet obj = new ItemGetSet();

						description = cur.getString(cur
								.getColumnIndex("Description"));
						Log.d("description", "" + description);
						quentity = cur
								.getString(cur.getColumnIndex("Quentity"));
						Log.d("quentity", "" + quentity);
						total = cur.getString(cur.getColumnIndex("Total"))
								.replace("$", "");
						Log.d("total", "" + total);

						try {
							inttotal = Integer.parseInt(total);
							Log.d("inttotal5", "" + inttotal);
						} catch (NumberFormatException e) {
							// TODO: handle exception
						}
						Log.d("inttota6", "" + inttotal);

						try {
							MAINPOSITION = MAINPOSITION + inttotal;

						} catch (NumberFormatException e) {
							// TODO: handle exception
						}
						Log.d("MAINPOSITION", "" + MAINPOSITION);

						try {
							percentage = ((MAINPOSITION * 12.5) / 100);

						} catch (NumberFormatException e) {
							// TODO: handle exception
						}
						Log.d("percentage", "" + percentage);
						try {
							//finalsum = (MAINPOSITION + percentage);
							finalsum = MAINPOSITION;
						} catch (NumberFormatException e) {
							// TODO: handle exception
						}
						Log.d("finalsum", "" + finalsum);

						price = cur.getString(cur.getColumnIndex("Price"));
						Log.d("price", "" + price);

						obj.setDescription(description);
						obj.setQuentity(quentity);
						obj.setTotal(total);
						obj.setPrice(price);

						Item.add(obj);

					} while (cur.moveToNext());
				}
			}

			cur.close();
			db.close();
			myDbHelper.close();
			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {

			Log.d("Vishal", "" + Item.size());

			listview1 = (ListView) findViewById(R.id.listView1);
			LazyAdapter lazy = new LazyAdapter(Notes.this, Item);
			listview1.setAdapter(lazy);
			listview1.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {

				}
			});

		}

	}

	public class LazyAdapter extends BaseAdapter {

		private Activity activity;
		private ArrayList<ItemGetSet> data;
		private LayoutInflater inflater = null;

		Typeface tf = Typeface.createFromAsset(Notes.this.getAssets(),
				"fonts/RestFont.TTF");

		public LazyAdapter(Activity a, ArrayList<ItemGetSet> d) {
			activity = a;
			data = d;
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
				vi = inflater.inflate(R.layout.cell_orderdetail, null);
			}

			Button btn_minus=(Button)vi.findViewById(R.id.btn_minus);
			btn_minus.setVisibility(View.INVISIBLE);
			ItemGetSet NewObj = Item.get(position);
			TextView title = (TextView) vi.findViewById(R.id.txt_item);
			title.setText(NewObj.getDescription());
			
			TextView noofitem = (TextView) vi
					.findViewById(R.id.txt_numberofitem);
			noofitem.setText("*" + NewObj.getQuentity());
			
			
			TextView total = (TextView) vi.findViewById(R.id.txt_total);
			total.setText("=" + NewObj.getTotal().replace("$", ""));
			
			
			TextView price = (TextView) vi.findViewById(R.id.txt_price);
			price.setText(getString(R.string.dollar_sign) + NewObj.getPrice());
			

			
			
			Log.d("finalsum12", ""+finalsum);
			TextView txtfainalprice = (TextView) findViewById(R.id.txt_fainal);
			txtfainalprice.setText(getString(R.string.dollar_sign) + finalsum);
			

			return vi;
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
		cd = new ConnectionDetector(Notes.this);

		if (!cd.isConnectingToInternet()) {
			alert.showAlertDialog(Notes.this, "Internet Connection Error",
					"Please connect to working Internet connection", false);
			return;
		} else {
			// AdView mAdView = (AdView) findViewById(R.id.adView);
			// AdRequest adRequest = new AdRequest.Builder().build();
			// mAdView.loadAd(adRequest);
			Log.d("call", "call");

			mInterstitialAd = new InterstitialAd(Notes.this);
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
