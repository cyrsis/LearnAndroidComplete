package freaktemplate.singlerestau;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import freaktemplate.singlerestau.utils.AlertDialogManager;
import freaktemplate.singlerestau.utils.CatGetSet;
import freaktemplate.singlerestau.utils.ConnectionDetector;
import freaktemplate.singlerestau.utils.DataBaseHelper;
import freaktemplate.singlerestau.utils.ImageLoader;

public class Menu extends Activity {

	ListView listview1;
	ProgressDialog dialog;
	Spinner spiner;
	TextView txt_dollar;
	ProgressBar pr;
	float floatprice1;
	TextView txt_spin;
	ListView list_category;
	ProgressDialog progressDialog;
	EditText edit_search;
	View layout12;
	RelativeLayout rl_dialoguser;
	Button btn_add, btn_minus, btn_display;
	Button btn_cartto;
	float floatprice;
	SQLiteDatabase db;
	DataBaseHelper myDbHelpel;

	String[] str = { "1", "2", "3" };
	String[] str1 = { "All", "Gujarati", "Panjabi", "Chinese" };
	int poscart;
	int MAINPOSITION = 0;
	String item, txttotal;
	float count;
	int pos;
	float dollar1;
	String search, buttontotal;
	int start = 0;
	int cart = 0;
	String[] country_array;
	String[] food_array;
	private String Error = null;
	Boolean isInternetPresent = false;
	ConnectionDetector cd;
	int value = 1;
	int check = 0;
	String url = "http://192.168.1.109/admin/webservice/";
	String cnt;
	public static final String MY_PREFS_NAME = "Restaurant";
	AlertDialogManager alert = new AlertDialogManager();

	ArrayList<CatGetSet> rest;
	ArrayAdapter<String> foodtype;
	private boolean isSpinnerInitial = true;

	InterstitialAd mInterstitialAd;
	boolean interstitialCanceled;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		Runtime.getRuntime().gc();

		getpreference();
		adview();
		init();

	}

	private void init() {
		// TODO Auto-generated method stub
		cd = new ConnectionDetector(getApplicationContext());

		isInternetPresent = cd.isConnectingToInternet();

		if (isInternetPresent) {

			rest = new ArrayList<CatGetSet>();

			new getcategorydetail().execute();

			btn_cartto = (Button) findViewById(R.id.btn_cart);
			btn_cartto.setText("" + cart);

			// cart button click

			btn_cartto.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub

					if (cart == 0) {

						RelativeLayout rl_back = (RelativeLayout) findViewById(R.id.rl_back);
						if (rl_back == null) {
							Log.d("second", "second");
							final RelativeLayout rl_dialoguser = (RelativeLayout) findViewById(R.id.rl_infodialog);
							try {
								layout12 = getLayoutInflater().inflate(R.layout.addcart, rl_dialoguser, false);
							} catch (InflateException e) {
								// TODO: handle exception
							}

							rl_dialoguser.addView(layout12);

							ImageView img = (ImageView) layout12.findViewById(R.id.imageView);
							img.setImageResource(R.drawable.warning_btn);
							TextView txt_dia = (TextView) layout12.findViewById(R.id.txt_dia);
							txt_dia.setText("" + getString(R.string.emptycart) + "\n" + getString(R.string.noselect));

							Button btn_yes = (Button) layout12.findViewById(R.id.btn_yes);
							btn_yes.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									// finish();
									// rl_dialoguser.setVisibility(View.GONE);
									View myView = findViewById(R.id.rl_back);
									ViewGroup parent = (ViewGroup) myView.getParent();
									parent.removeView(myView);
								}
							});
						}

					} else {
						Intent iv = new Intent(Menu.this, Orderdetail.class);
						startActivity(iv);
					}
				}
			});

			// search in menu

			edit_search = (EditText) findViewById(R.id.edit_search);
			edit_search.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					// TODO Auto-generated method stub

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {
					// TODO Auto-generated method stub

				}

				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
					TextView txt_spin = (TextView) findViewById(android.R.id.text1);
					search = s.toString();

					new getcategorydetail12().execute();

				}
			});
		} else {
			// Internet connection is not present
			// Ask user to connect to Internet
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

	private void adview() {
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

	private void getpreference() {
		// TODO Auto-generated method stub
		SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
		if (prefs.getString("count", null) != null) {
			cnt = prefs.getString("count", null);

		} else {
			cnt = "0";
		}

		try {
			cart = Integer.parseInt(cnt);
		} catch (NumberFormatException e) {
			// TODO: handle exception
		}
	}

	// get menu data from server

	public class getcategorydetail extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(Menu.this);
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

				list_category = (ListView) findViewById(R.id.listView1);

				LazyAdapter lazy = new LazyAdapter(Menu.this, rest);

				list_category.setAdapter(lazy);

				list_category.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						// TODO Auto-generated method stub

						poscart = position;
						// TODO Auto-generated method stub
						rl_dialoguser = (RelativeLayout) findViewById(R.id.rl_infodialog);
						rl_dialoguser.setVisibility(View.VISIBLE);

						Log.d("second", "second");

						// open particular menu dialog for add to cart
						//try {
							layout12 = getLayoutInflater().inflate(R.layout.menudialog, rl_dialoguser, false);
							rl_dialoguser.addView(layout12);
				//		} catch (InflateException e) {
							// TODO: handle exception
					//	}

						
						Typeface tf = Typeface.createFromAsset(Menu.this.getAssets(), "fonts/verdana.ttf");
						Typeface tf1 = Typeface.createFromAsset(Menu.this.getAssets(), "fonts/verdanab.ttf");
						try {

							TextView txt_namemain = (TextView) layout12.findViewById(R.id.txt_namemain);
							txt_namemain.setText("" + rest.get(position).getName());
							txt_namemain.setTypeface(tf);

							TextView txt_name = (TextView) layout12.findViewById(R.id.txt_name);
							txt_name.setText("" + rest.get(position).getSubname());
							txt_name.setTypeface(tf);

							TextView txt_dollar1 = (TextView) layout12.findViewById(R.id.txt_dollar1);
							floatprice = Float.parseFloat(rest.get(position).getPrice());
							txt_dollar1.setText(getString(R.string.dollar_sign) + String.format("%.1f", floatprice));
							txt_dollar1.setTypeface(tf1);

							txt_dollar = (TextView) layout12.findViewById(R.id.txt_dollar);
							floatprice1 = Float.parseFloat(rest.get(position).getPrice());
							txt_dollar.setText(getString(R.string.dollar_sign) + String.format("%.1f", floatprice1));
							txt_dollar.setTypeface(tf);

							TextView txt_desc = (TextView) layout12.findViewById(R.id.txt_description);
							txt_desc.setText("" + rest.get(position).getDescription());
							txt_desc.setTypeface(tf);

							TextView txt_desc1 = (TextView) layout12.findViewById(R.id.txt_des);
							txt_desc1.setTypeface(tf);

							dollar1 = Integer.parseInt(rest.get(position).getPrice().replace(".0", ""));

						} catch (NumberFormatException e) {
							// TODO: handle exception
						} catch (NullPointerException e) {
							// TODO: handle exception
						}

						ImageView img_subcat = (ImageView) layout12.findViewById(R.id.img_subname);
						// img_subcat.setImageResource(R.drawable.menu_dialog_preloadimg);
						ImageLoader img_loader = new ImageLoader(Menu.this);
						pr = (ProgressBar) layout12.findViewById(R.id.progressmenu);
						img_loader.DisplayImage1(
								getString(R.string.liveurl) + "uploads/image/" + rest.get(position).getImage(),
								img_subcat, pr);

						btn_add = (Button) layout12.findViewById(R.id.btn_add);
						btn_minus = (Button) layout12.findViewById(R.id.btn_minus);
						btn_display = (Button) layout12.findViewById(R.id.btn_display);
						value = 1;
						btn_display.setText("" + value);

						btn_minus.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								try {
									if (value <= 0) {
										value = 0;
										btn_display.setText("" + value);
										count = value * floatprice1;
										String s = "<font color='#ff6d00'>" + getString(R.string.dollar_sign)
												+ "</font>";
										txt_dollar.setText(
												getString(R.string.dollar_sign) + String.format("%.1f", count));
										txttotal = txt_dollar.getText().toString();
										buttontotal = btn_display.getText().toString();
										Log.d("buttontotal2", "" + buttontotal);
									} else {
										value--;
										btn_display.setText("" + value);
										count = value * floatprice1;
										String s = "<font color='#ff6d00'>" + getString(R.string.dollar_sign)
												+ "</font>";
										txt_dollar.setText(
												getString(R.string.dollar_sign) + String.format("%.1f", count));
										txttotal = txt_dollar.getText().toString();
										buttontotal = btn_display.getText().toString();
										Log.d("buttontotal3", "" + buttontotal);
									}
								} catch (NumberFormatException e) {
									// TODO: handle exception
								}

							}
						});

						// number of person(add button)
						btn_add.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

								try {
									value++;
									btn_display.setText("" + value);
									count = value * floatprice1;
									String s = "<font color='#ff6d00'>" + getString(R.string.dollar_sign) + "</font>";
									txt_dollar.setText(getString(R.string.dollar_sign) + String.format("%.1f", count));
									txttotal = txt_dollar.getText().toString();
									buttontotal = btn_display.getText().toString();
									Log.d("buttontotal4", "" + buttontotal);
								} catch (NumberFormatException e) {
									// TODO: handle exception
								}
							}
						});

						// add to cart button click store data in database

						Button btn_cart = (Button) layout12.findViewById(R.id.btn_addcart);
						// btn_cart.setTypeface(tf);
						btn_cart.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								String numberoforder = btn_display.getText().toString();
								if (numberoforder.equals("0")) {
									Toast.makeText(Menu.this, getString(R.string.noselect), Toast.LENGTH_LONG).show();
								} else {
									txttotal = txt_dollar.getText().toString();
									buttontotal = btn_display.getText().toString();
									cart = cart + 1;
									SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE)
											.edit();
									editor.putString("count", "" + cart);
									editor.commit();
									btn_cartto.setText("" + cart);
									myDbHelpel = new DataBaseHelper(Menu.this);
									try {
										myDbHelpel.createDataBase();
									} catch (IOException io) {
										throw new Error("Unable TO Create DataBase");
									}
									try {
										myDbHelpel.openDataBase();
									} catch (SQLException e) {
										e.printStackTrace();
									}
									db = myDbHelpel.getWritableDatabase();
									ContentValues values = new ContentValues();

									Log.d("buttontotal1", "" + buttontotal);
									Log.d("buttontotal2", "" + dollar1);
									Log.d("buttontotal3", "" + txttotal);
									values.put("Description", "" + rest.get(poscart).getSubname());
									values.put("Quentity", buttontotal);
									values.put("Total", txttotal);
									values.put("Price", dollar1);

									db.insert("detail", null, values);

									myDbHelpel.close();

									View myView = findViewById(R.id.rl_back);
									ViewGroup parent = (ViewGroup) myView.getParent();
									parent.removeView(myView);
									Toast.makeText(Menu.this, getString(R.string.orderplace), Toast.LENGTH_LONG).show();

								}
							}
						});

						// cancel button click

						Button cancel = (Button) layout12.findViewById(R.id.btn_cancel);
						cancel.setTypeface(tf);
						cancel.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

								// rl_dialoguser.setVisibility(View.GONE);
								View myView = findViewById(R.id.rl_back);
								ViewGroup parent = (ViewGroup) myView.getParent();
								parent.removeView(myView);
							}
						});

					}
				});

			}

		}
	}

	// get particular menu full detail

	private void getdetailforNearMe() {
		// TODO Auto-generated method stub

		URL hp = null;
		try {
			rest.clear();
			hp = new URL(getString(R.string.liveurl) + "webservice/foodcategorychange.php");

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
			JSONArray j = jObject.getJSONArray("menu");
			Log.d("URL1", "" + j);

			country_array = new String[j.length()];
			for (int i = 0; i < j.length(); i++) {

				JSONObject Obj;
				Obj = j.getJSONObject(i);

				CatGetSet temp = new CatGetSet();

				temp.setName(Obj.getString("name"));
				country_array[i] = Obj.getString("name");
				String name = Obj.getString("name");
				JSONArray guj = Obj.getJSONArray(name);

				for (int k = 0; k < guj.length(); k++) {
					JSONObject Obj1;
					Obj1 = guj.getJSONObject(k);
					CatGetSet temp1 = new CatGetSet();
					temp1.setName(Obj.getString("name"));
					temp1.setId(Obj1.getString("id"));
					temp1.setSubname(Obj1.getString("name"));
					temp1.setImage(Obj1.getString("thumbnail"));
					temp1.setDescription(Obj1.getString("desc"));
					temp1.setPrice(Obj1.getString("price"));
					rest.add(temp1);
				}

			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Error = e.getMessage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Error = e.getMessage();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Error = e.getMessage();
		} catch (NullPointerException e) {
			// TODO: handle exception
			Error = e.getMessage();
		}
	}

	// bid data in listview

	public class LazyAdapter extends BaseAdapter {

		private Activity activity;
		private ArrayList<CatGetSet> data;
		private LayoutInflater inflater = null;

		Typeface tf = Typeface.createFromAsset(Menu.this.getAssets(), "fonts/verdana.ttf");
		Typeface tf1 = Typeface.createFromAsset(Menu.this.getAssets(), "fonts/verdanab.ttf");

		public LazyAdapter(Activity a, ArrayList<CatGetSet> str) {
			activity = a;
			data = str;
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
			CatGetSet cgs = rest.get(position);
			String Str_name = cgs.getName();
			Log.d("str_name", "" + Str_name);
			if (convertView == null) {

				vi = inflater.inflate(R.layout.menu_cell, null);

			}

			try {
				RelativeLayout rel_alpha = (RelativeLayout) vi.findViewById(R.id.rel_alpha);

				rel_alpha.setVisibility(View.VISIBLE);
				TextView txt_alpha = (TextView) vi.findViewById(R.id.txt_alpha);
				txt_alpha.setText(data.get(position).getName());

				if (position == 0) {
					if (data.get(position).getName().equals(data.get(position).getName())) {

						rel_alpha.setVisibility(View.VISIBLE);
						txt_alpha.setText("" + data.get(position).getName());
					}

				} else {
					if (data.get(position).getName().equals(data.get(position - 1).getName())) {

						rel_alpha.setVisibility(View.GONE);
					} else {
						rel_alpha.setVisibility(View.VISIBLE);
					}
				}

				txt_alpha.setTypeface(tf1);
				TextView txt_item = (TextView) vi.findViewById(R.id.txt_item);
				txt_item.setText(data.get(position).getSubname());
				txt_item.setTypeface(tf);

				String s = "<font color='#ff6d00'>" + getString(R.string.dollar_sign) + "</font>";
				TextView txt_price = (TextView) vi.findViewById(R.id.txt_price1);

				float floatprice = Float.parseFloat(rest.get(position).getPrice());

				txt_price.setText(getString(R.string.dollar_sign) + String.format("%.1f", floatprice));

				txt_price.setTypeface(tf);

				String image = data.get(position).getImage();
				String name = data.get(position).getSubname();
				String id = data.get(position).getId();
				String guj = data.get(position).getName();
				Log.d("image", "" + image);
				Log.d("name", "" + name);
				Log.d("id", "" + id);
				Log.d("guj", "" + guj);

				ImageView img_cat = (ImageView) vi.findViewById(R.id.img_item);
				img_cat.setImageResource(R.drawable.menu_round_preloadimg);
				ImageLoader imgloader = new ImageLoader(Menu.this);
				Log.d("imagemenu", "" + getString(R.string.liveurl) + "uploads/" + image);
				ProgressBar prlist = (ProgressBar) vi.findViewById(R.id.progress);
				imgloader.DisplayImage1(getString(R.string.liveurl) + "uploads/" + image, img_cat, prlist);
			} catch (NumberFormatException e) {
				// TODO: handle exception
			} catch (NullPointerException e) {
				// TODO: handle exception
			}
			return vi;
		}
	}

	// image download from url

	class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;
		Bitmap mIcon11;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		@Override
		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];

			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);

			} catch (Exception e) {
				Log.e("Error", "" + e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			// pd.dismiss();
			bmImage.setImageBitmap(result);
		}
	}

	@Override
	public void onBackPressed() {
		Intent iv = new Intent(Menu.this, Home.class);
		iv.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(iv);
	}

	// get data from server which is searched

	public class getcategorydetail12 extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(Menu.this);
			progressDialog.setMessage(getString(R.string.loading));
			progressDialog.setCancelable(true);
			progressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			getdetailforNearMe12();
			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);

			if (progressDialog.isShowing()) {
				progressDialog.dismiss();

				list_category = (ListView) findViewById(R.id.listView1);

				LazyAdapter lazy = new LazyAdapter(Menu.this, rest);
				list_category.setAdapter(lazy);

				list_category.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						// TODO Auto-generated method stub

						// TODO Auto-generated method stub
						value = 1;
						// btn_display.setText(""+value);
						poscart = position;
						// TODO Auto-generated method stub
						rl_dialoguser = (RelativeLayout) findViewById(R.id.rl_infodialog);
						rl_dialoguser.setVisibility(View.VISIBLE);

						Log.d("second", "second");
						try {
							layout12 = getLayoutInflater().inflate(R.layout.menudialog, rl_dialoguser, false);
						} catch (InflateException e) {
							// TODO: handle exception
						}

						rl_dialoguser.addView(layout12);
						Typeface tf = Typeface.createFromAsset(Menu.this.getAssets(), "fonts/verdana.ttf");
						Typeface tf1 = Typeface.createFromAsset(Menu.this.getAssets(), "fonts/verdanab.ttf");
						try {
							TextView txt_namemain = (TextView) layout12.findViewById(R.id.txt_namemain);
							txt_namemain.setText("" + rest.get(position).getName());
							txt_namemain.setTypeface(tf);

							TextView txt_name = (TextView) layout12.findViewById(R.id.txt_name);
							txt_name.setText("" + rest.get(position).getSubname());
							txt_name.setTypeface(tf);

							TextView txt_dollar1 = (TextView) layout12.findViewById(R.id.txt_dollar1);
							float floatprice = Float.parseFloat(rest.get(position).getPrice());
							txt_dollar1.setText(getString(R.string.dollar_sign) + String.format("%.1f", floatprice));
							txt_dollar1.setTypeface(tf1);

							txt_dollar = (TextView) layout12.findViewById(R.id.txt_dollar);
							floatprice1 = Float.parseFloat(rest.get(position).getPrice());
							txt_dollar.setText(getString(R.string.dollar_sign) + String.format("%.1f", floatprice1));
							txt_dollar.setTypeface(tf);

							TextView txt_desc = (TextView) layout12.findViewById(R.id.txt_description);
							txt_desc.setText("" + rest.get(position).getDescription());
							txt_desc.setTypeface(tf);

							TextView txt_desc1 = (TextView) layout12.findViewById(R.id.txt_des);
							txt_desc1.setTypeface(tf);

							dollar1 = Float.parseFloat(rest.get(position).getPrice());

						} catch (NullPointerException e) {
							// TODO: handle exception
						} catch (NumberFormatException e) {
							// TODO: handle exception
						}

						ImageView img_subcat = (ImageView) layout12.findViewById(R.id.img_subname);
						// img_subcat.setImageResource(R.drawable.menu_dialog_preloadimg);

						new DownloadImageTask(img_subcat).execute(
								getString(R.string.liveurl) + "uploads/image/" + rest.get(position).getImage());

						btn_add = (Button) layout12.findViewById(R.id.btn_add);
						btn_minus = (Button) layout12.findViewById(R.id.btn_minus);
						btn_display = (Button) layout12.findViewById(R.id.btn_display);
						btn_display.setText("" + value);

						btn_minus.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								try {
									if (value <= 0) {
										value = 0;
										btn_display.setText("" + value);
										count = value * floatprice1;
										String s = "<font color='#ff6d00'>" + getString(R.string.dollar_sign)
												+ "</font>";
										txt_dollar
												.setText(getString(R.string.dollar_sign) + " " + String.valueOf(count));
										txttotal = txt_dollar.getText().toString();
										buttontotal = btn_display.getText().toString();
										Log.d("buttontotal2", "" + buttontotal);
									} else {
										value--;
										btn_display.setText("" + value);
										count = value * floatprice1;
										String s = "<font color='#ff6d00'>" + getString(R.string.dollar_sign)
												+ "</font>";
										txt_dollar
												.setText(getString(R.string.dollar_sign) + " " + String.valueOf(count));
										txttotal = txt_dollar.getText().toString();
										buttontotal = btn_display.getText().toString();
										Log.d("buttontotal3", "" + buttontotal);
									}
								} catch (NumberFormatException e) {
									// TODO: handle exception
								}

							}
						});

						// number of person(add button)
						btn_add.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

								try {
									value++;
									btn_display.setText("" + value);
									count = value * floatprice1;
									String s = "<font color='#ff6d00'>" + getString(R.string.dollar_sign) + "</font>";
									txt_dollar.setText(getString(R.string.dollar_sign) + " " + String.valueOf(count));
									txttotal = txt_dollar.getText().toString();
									buttontotal = btn_display.getText().toString();
									Log.d("buttontotal4", "" + buttontotal);
								} catch (NumberFormatException e) {
									// TODO: handle exception
								}

							}
						});

						Button btn_cart = (Button) layout12.findViewById(R.id.btn_addcart);

						btn_cart.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								String numberoforder = btn_display.getText().toString();
								if (numberoforder.equals("0")) {
									Toast.makeText(Menu.this, getString(R.string.noselect), Toast.LENGTH_LONG).show();
								} else {
									cart = cart + 1;
									SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE)
											.edit();
									editor.putString("count", "" + cart);
									editor.commit();
									btn_cartto.setText("" + cart);
									myDbHelpel = new DataBaseHelper(Menu.this);
									try {
										myDbHelpel.createDataBase();
									} catch (IOException io) {
										throw new Error("Unable TO Create DataBase");
									}
									try {
										myDbHelpel.openDataBase();
									} catch (SQLException e) {
										e.printStackTrace();
									}
									db = myDbHelpel.getWritableDatabase();
									ContentValues values = new ContentValues();

									values.put("Description", "" + rest.get(poscart).getSubname());
									values.put("Quentity", buttontotal);
									values.put("Total", txttotal);
									values.put("Price", dollar1);

									db.insert("detail", null, values);

									myDbHelpel.close();
									View myView = findViewById(R.id.rl_back);
									ViewGroup parent = (ViewGroup) myView.getParent();
									parent.removeView(myView);
									Toast.makeText(Menu.this, getString(R.string.orderplace), Toast.LENGTH_LONG).show();

								}
							}
						});

						Button cancel = (Button) layout12.findViewById(R.id.btn_cancel);
						cancel.setTypeface(tf);
						cancel.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

								// rl_dialoguser.setVisibility(View.GONE);
								View myView = findViewById(R.id.rl_back);
								ViewGroup parent = (ViewGroup) myView.getParent();
								parent.removeView(myView);
							}
						});

					}
				});

			}
		}

	}

	// get data from json url which is serach in app

	private void getdetailforNearMe12() {
		// TODO Auto-generated method stub

		URL hp = null;
		try {
			rest.clear();
			hp = new URL(getString(R.string.liveurl) + "webservice/foodcategorychange.php?search=" + search);

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
			JSONArray j = jObject.getJSONArray("menu");
			Log.d("URL1", "" + j);

			// Log.d("URL2", "" + j1);
			for (int i = 0; i < j.length(); i++) {

				JSONObject Obj;
				Obj = j.getJSONObject(i);

				CatGetSet temp = new CatGetSet();

				temp.setName(Obj.getString("name"));

				country_array[i] = Obj.getString("name");
				String name = Obj.getString("name");
				JSONArray guj = Obj.getJSONArray(name);

				for (int k = 0; k < guj.length(); k++) {
					JSONObject Obj1;
					Obj1 = guj.getJSONObject(k);
					CatGetSet temp1 = new CatGetSet();
					temp1.setName(Obj.getString("name"));
					temp1.setId(Obj1.getString("id"));
					temp1.setSubname(Obj1.getString("name"));
					temp1.setImage(Obj1.getString("thumbnail"));
					temp1.setDescription(Obj1.getString("desc"));
					temp1.setPrice(Obj1.getString("price"));
					rest.add(temp1);
				}

			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Error = e.getMessage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Error = e.getMessage();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Error = e.getMessage();
		} catch (NullPointerException e) {
			// TODO: handle exception
			Error = e.getMessage();
		}
	}

	// show Interstitial ad

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
		cd = new ConnectionDetector(Menu.this);

		if (!cd.isConnectingToInternet()) {
			alert.showAlertDialog(Menu.this, getString(R.string.internet), getString(R.string.internettext), false);
			return;
		} else {

			Log.d("call", "call");

			mInterstitialAd = new InterstitialAd(Menu.this);
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
