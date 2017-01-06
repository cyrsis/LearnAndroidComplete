package freaktemplate.singlerestau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import freaktemplate.singlerestau.utils.AlarmReceiver;
import freaktemplate.singlerestau.utils.AlertDialogManager;
import freaktemplate.singlerestau.utils.CommonUtilities;
import freaktemplate.singlerestau.utils.ConnectionDetector;
import freaktemplate.singlerestau.utils.DataBaseHelper;
import freaktemplate.singlerestau.utils.ItemGetSet;
import freaktemplate.singlerestau.utils.WakeLocker;

public class Orderdetail extends Activity {
	String message;
	String[] str = { "1", "2", "3" };
	double MAINPOSITION = 0;
	boolean isPressed = true;
	String cid, responseStr, Error, idres;
	String description, quentity, total, price, description1, id;
	double inttotal;
	String id1;
	ProgressDialog progressDialog;
	String key;
	double percentage, finalsum;
	String button = "unpress";
	int start = 0;
	int value = 0;
	String name, email, mobile, address, state, city, nbh, zip, detail;
	String editname, editnumber, editcomment, buttontotal, time, date;
	String dis2, mainposition, totalvalue, datetime, chemsg;
	Boolean isInternetPresent = false;
	public static final String MY_PREFS_NAME = "Restaurant";
	int mYear, mMonth, mDay, hour, minute;
	String regId = "", deviceId;
	AlertDialogManager alert = new AlertDialogManager();
	String cnt, nextpage = "restaurant";
	int cart;

	ListView listview1;
	View layout12;
	Button btn_add, btn_minus, btn_total;
	TextView txt_date, txt_time;
	ProgressDialog dialog;
	Button btn_book, btn_small, btn_conform, btn_smallhomedel;
	EditText edt_name, edt_email, edt_mobile, edt_address, edt_state, edt_city, edt_nbh, edt_zip, edt_detail;

	SQLiteDatabase db;
	Cursor cur = null;

	ArrayList<ItemGetSet> Item;

	EditText edit_name, edit_number, edit_comment;

	ConnectionDetector cd;
	InterstitialAd mInterstitialAd;
	boolean interstitialCanceled;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orderdetail);
		Runtime.getRuntime().gc();

		getpreference();
		init();

	}

	private void init() {
		// TODO Auto-generated method stub

		key = "normal";
		Typeface tf = Typeface.createFromAsset(Orderdetail.this.getAssets(), "fonts/verdana.ttf");
		TextView txt_head = (TextView) findViewById(R.id.txt_head);

		TextView txt_total = (TextView) findViewById(R.id.txt_total);

		txt_head.setTypeface(tf);

		txt_total.setTypeface(tf);

		// get register id from gcm
		gcmregister();

		cd = new ConnectionDetector(getApplicationContext());

		isInternetPresent = cd.isConnectingToInternet();

		if (getString(R.string.insertialads).equals("yes")) {
			interstitialCanceled = false;
			CallNewInsertial();
		} else if (getString(R.string.insertialads).equals("no")) {

		}

		if (isInternetPresent) {

			initialise();
		} else {

			RelativeLayout rl_back = (RelativeLayout) findViewById(R.id.rl_back);
			if (rl_back == null) {
				Log.d("second", "second");
				RelativeLayout rl_dialoguser = (RelativeLayout) findViewById(R.id.rl_infodialog);

				try {
					layout12 = getLayoutInflater().inflate(R.layout.addcart, rl_dialoguser, false);
				} catch (NullPointerException e) {
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

	private void gcmregister() {
		// TODO Auto-generated method stub
		GCMRegistrar.checkDevice(Orderdetail.this);
		GCMRegistrar.checkManifest(Orderdetail.this);
		registerReceiver(mHandleMessageReceiver, new IntentFilter(CommonUtilities.DISPLAY_MESSAGE_ACTION));

		// Get GCM registration id
		regId = GCMRegistrar.getRegistrationId(Orderdetail.this);
		Log.d("regIdrest", "" + regId);
		GCMRegistrar.register(Orderdetail.this, CommonUtilities.SENDER_ID);
	}

	private void getpreference() {
		// TODO Auto-generated method stub
		SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
		if (prefs.getString("count", null) != null) {

			cnt = prefs.getString("count", null);

		} else {
			cnt = "0";
		}
		deviceId = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);
		try {
			cart = Integer.parseInt(cnt);
		} catch (NumberFormatException e) {
			// TODO: handle exception
		}
	}

	private void initialise() {
		// TODO Auto-generated method stub
		Item = new ArrayList<ItemGetSet>();
		Get_ChapterList();
		TextView txt_editorder = (TextView) findViewById(R.id.txt_edit);

		// edit order click
		txt_editorder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				key = "edit";
				inttotal = 0.0;
				MAINPOSITION = 0.0;
				new SplashScreenDelay().execute();
			}
		});
		btn_small = (Button) findViewById(R.id.btn_small);
		btn_small.setBackgroundResource(R.drawable.small_button);
		btn_smallhomedel = (Button) findViewById(R.id.btn_small1);
		btn_smallhomedel.setBackgroundResource(R.drawable.small_btn_uncheck);

		// home delivery button select

		btn_smallhomedel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				nextpage = "home";
				btn_small.setBackgroundResource(R.drawable.small_btn_uncheck);
				btn_smallhomedel.setBackgroundResource(R.drawable.small_button);
			}
		});

		// book order button select

		btn_small.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				nextpage = "restaurant";
				btn_small.setBackgroundResource(R.drawable.small_button);
				btn_smallhomedel.setBackgroundResource(R.drawable.small_btn_uncheck);

			}

		});

		// next button click

		btn_book = (Button) findViewById(R.id.btn_book);
		btn_book.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// on next button check if it is book order or home delivery
				// book order

				if (nextpage.equals("restaurant")) {
					SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

					RelativeLayout rl_dialoguser = (RelativeLayout) findViewById(R.id.rl_infodialog);
					try {
						layout12 = getLayoutInflater().inflate(R.layout.booktable1, rl_dialoguser, false);
					} catch (InflateException e) {
						// TODO: handle exception
					}

					rl_dialoguser.addView(layout12);
					Log.d("user4", "" + button);

					Typeface tf = Typeface.createFromAsset(Orderdetail.this.getAssets(), "fonts/verdana.ttf");
					TextView txt_view = (TextView) findViewById(R.id.textView1);
					TextView txt_datetime = (TextView) findViewById(R.id.txtdate);
					TextView txt_full = (TextView) findViewById(R.id.txt_full);
					TextView txt_mobile = (TextView) findViewById(R.id.txt_mobile);
					TextView txt_comment = (TextView) findViewById(R.id.txt_comment);

					txt_view.setTypeface(tf);
					txt_datetime.setTypeface(tf);
					txt_full.setTypeface(tf);
					txt_mobile.setTypeface(tf);
					txt_comment.setTypeface(tf);

					btn_add = (Button) layout12.findViewById(R.id.btn_add);
					btn_minus = (Button) layout12.findViewById(R.id.btn_minus);
					btn_total = (Button) layout12.findViewById(R.id.btn_total);
					btn_minus.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							try {
								if (value <= 0) {
									value = 0;
									btn_total.setText("" + value);
									buttontotal = btn_total.getText().toString();
								} else {
									value--;
									btn_total.setText("" + value);
									buttontotal = btn_total.getText().toString();
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
								btn_total.setText("" + value);
								buttontotal = btn_total.getText().toString();
							} catch (NumberFormatException e) {
								// TODO: handle exception
							}

						}
					});

					try {
						txt_date = (TextView) layout12.findViewById(R.id.txt_date);
						Calendar c = Calendar.getInstance();
						mYear = c.get(Calendar.YEAR);
						mMonth = c.get(Calendar.MONTH);
						mDay = c.get(Calendar.DAY_OF_MONTH);
						txt_date.setText(mDay + "/" + (mMonth + 1) + "/" + mYear);
						hour = c.get(Calendar.HOUR_OF_DAY);
						minute = c.get(Calendar.MINUTE);
						String min = String.valueOf(minute);
						Log.d("minute", "" + minute + "  " + min.length());
						txt_time = (TextView) layout12.findViewById(R.id.txt_time);
						if (min.length() == 1) {
							txt_time.setText(hour + ":" + "0" + minute);
						} else {
							txt_time.setText(hour + ":" + minute);
						}
					} catch (NumberFormatException e) {
						// TODO: handle exception
					}

					// datepicker

					txt_date.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

							Calendar c = Calendar.getInstance();
							mYear = c.get(Calendar.YEAR);
							mMonth = c.get(Calendar.MONTH);
							mDay = c.get(Calendar.DAY_OF_MONTH);
							System.out.println("the selected " + mDay);
							DatePickerDialog dialog = new DatePickerDialog(Orderdetail.this, new mDateSetListener(),
									mYear, mMonth, mDay);
							dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
							dialog.show();
						}
					});

					// timepicker

					txt_time.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							try {
								Calendar mcurrentTime = Calendar.getInstance();
								hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
								minute = mcurrentTime.get(Calendar.MINUTE);
								TimePickerDialog mTimePicker;
								mTimePicker = new TimePickerDialog(Orderdetail.this,
										new TimePickerDialog.OnTimeSetListener() {
											@Override
											public void onTimeSet(TimePicker timePicker, int selectedHour,
													int selectedMinute) {
												hour = selectedHour;
												minute = selectedMinute;
												String min = String.valueOf(minute);
												// txt_time.setText(selectedHour
												// + ":" +
												// selectedMinute);
												if (min.length() == 1) {
													txt_time.setText(hour + ":" + "0" + minute);
												} else {
													txt_time.setText(hour + ":" + minute);
												}
												time = txt_time.getText().toString();
												Log.d("time", "" + time);

											}
										}, hour, minute, true);// Yes 24 hour
																// time
								mTimePicker.setTitle("Select  Time");
								mTimePicker.show();
							} catch (NumberFormatException e) {
								// TODO: handle exception
							}

						}
					});

					edit_name = (EditText) layout12.findViewById(R.id.edit_name);
					edit_number = (EditText) layout12.findViewById(R.id.edit_number);
					edit_comment = (EditText) layout12.findViewById(R.id.edit_comment);

					// place order button click

					btn_conform = (Button) layout12.findViewById(R.id.btn_conform);
					btn_conform.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub

							Log.d("NOmonth", "" + mMonth);
							Log.d("NOday", "" + mDay);
							Log.d("NOyear", "" + mYear);
							Log.d("NOhour", "" + hour);
							Log.d("NOminute", "" + minute);
							// mMonth=mMonth+1;
							Calendar Calendar_Object1 = Calendar.getInstance();
							Calendar_Object1.set(Calendar.MONTH, mMonth);
							Calendar_Object1.set(Calendar.YEAR, mYear);
							Calendar_Object1.set(Calendar.DAY_OF_MONTH, mDay);

							Calendar_Object1.set(Calendar.HOUR_OF_DAY, hour);
							Calendar_Object1.set(Calendar.MINUTE, minute);
							Calendar_Object1.set(Calendar.SECOND, 0);

							long ONE_MINUTE_IN_MILLIS = 60000;

							long t = Calendar_Object1.getTimeInMillis();
							long minus15min = t - (15 * ONE_MINUTE_IN_MILLIS);
							if (hour > 12) {
								Calendar_Object1.set(Calendar.AM_PM, Calendar.PM);
							} else {
								Calendar_Object1.set(Calendar.AM_PM, Calendar.AM);
							}
							Intent myIntent1 = new Intent(Orderdetail.this, AlarmReceiver.class);

							PendingIntent pendingIntent1 = PendingIntent.getBroadcast(Orderdetail.this, 0, myIntent1,
									0);

							AlarmManager alarmManager1 = (AlarmManager) getSystemService(ALARM_SERVICE);
							alarmManager1.set(AlarmManager.RTC, minus15min, pendingIntent1);

							if (button.equals("unpress")) {
								date = txt_date.getText().toString();
								time = txt_time.getText().toString();
								editname = edit_name.getText().toString().replace(" ", "%20");
								Log.d("editentertime", "" + editname);
								editnumber = edit_number.getText().toString().replace(" ", "%20");
								Log.d("editentertime", "" + editnumber);

								editcomment = edit_comment.getText().toString().replace(" ", "%20");
								Log.d("editcomment", "" + editcomment);
								btn_total.setText("" + value);
								buttontotal = btn_total.getText().toString();
								datetime = date + "At" + time;
								datetime = datetime.replace(" ", "%20");
								Log.d("datetime", "" + datetime);
								dis2 = String.valueOf(finalsum);
								totalvalue = totalvalue.replace(" ", "%20");
								Log.d("totalvalue", "" + totalvalue);

								Log.d("dis2", "" + dis2);
								if (editname.equals("")) {
									edit_name.setError("Enter Name");
								} else if (buttontotal.equals("0")) {
									btn_total.setError("Enter Number of person");
								} else if (editnumber.equals("")) {
									edit_number.setError("Enter Contact number");
								} else {
									new SplashScreenDelay1().execute();
								}

							} else if (button.equals("press")) {
								date = txt_date.getText().toString().replace(" ", "%20");
								time = txt_time.getText().toString().replace(" ", "%20");
								editname = edit_name.getText().toString().replace(" ", "%20");
								Log.d("editentertime", "" + editname);
								editnumber = edit_number.getText().toString();
								Log.d("editentertime", "" + editnumber);

								editcomment = edit_comment.getText().toString().replace(" ", "%20");
								Log.d("editcomment", "" + editcomment);
								btn_total.setText("" + value);
								buttontotal = btn_total.getText().toString();
								datetime = date + "At" + time;
								datetime = datetime.replace(" ", "%20");
								Log.d("datetime", "" + datetime);
								dis2 = "I will Order at Restaurant";
								dis2 = dis2.replace(" ", "%20");
								totalvalue = "";
								Log.d("dis2", "" + dis2);
								if (editname.equals("")) {
									edit_name.setError("Enter Name");
								} else if (buttontotal.equals("0")) {
									btn_total.setError("Enter Number of person");
								} else if (editnumber.equals("")) {
									edit_number.setError("Enter Contact number");
								} else {
									new SplashScreenDelay1().execute();
								}

							}

							DataBaseHelper myDbHelper = new DataBaseHelper(Orderdetail.this);
							myDbHelper = new DataBaseHelper(Orderdetail.this);
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
							db = myDbHelper.getWritableDatabase();

							cur = db.rawQuery("Delete from detail;", null);
							if (cur.getCount() != 0) {
								if (cur.moveToFirst()) {
									do {
										ItemGetSet obj = new ItemGetSet();

										description = cur.getString(cur.getColumnIndex("Description"));
										Log.d("description", "" + description);
										quentity = cur.getString(cur.getColumnIndex("Quentity"));
										Log.d("quentity", "" + quentity);
										total = cur.getString(cur.getColumnIndex("Total")).replace("$", "");
										Log.d("total", "" + total);

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
							cart = 0;
							SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
							editor.putString("count", "" + cart);
							editor.commit();
						}
					});

					// home delivery

				} else if (nextpage.equals("home")) {

					Log.d("totalvalue", "" + totalvalue);
					Log.d("totalvalue", "" + finalsum);
					RelativeLayout rl_dialoguser = (RelativeLayout) findViewById(R.id.rl_infodialog);
					layout12 = getLayoutInflater().inflate(R.layout.homedelinfo, rl_dialoguser, false);
					rl_dialoguser.addView(layout12);

					edt_name = (EditText) layout12.findViewById(R.id.edt_name);
					edt_email = (EditText) layout12.findViewById(R.id.edt_refer);
					edt_mobile = (EditText) layout12.findViewById(R.id.edt_mobile);
					edt_address = (EditText) layout12.findViewById(R.id.edt_add);
					edt_nbh = (EditText) layout12.findViewById(R.id.edt_neighbour);
					edt_city = (EditText) layout12.findViewById(R.id.edt_city);
					edt_zip = (EditText) layout12.findViewById(R.id.edt_zip);
					edt_state = (EditText) layout12.findViewById(R.id.edt_state);
					edt_detail = (EditText) layout12.findViewById(R.id.edt_details);

					// submit button click

					Button btn_submit = (Button) layout12.findViewById(R.id.btn_send);
					btn_submit.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							name = edt_name.getText().toString();
							email = edt_email.getText().toString();
							mobile = edt_mobile.getText().toString();
							address = edt_address.getText().toString();
							nbh = edt_nbh.getText().toString();
							zip = edt_zip.getText().toString();
							city = edt_city.getText().toString();
							state = edt_state.getText().toString();
							detail = edt_detail.getText().toString();
							// email1=edt_email.getText().toString();
							if (name.equals("")) {
								edt_name.setError("Enter Name");
							} else if (email.equals("")) {
								edt_email.setError("Enter Email Id.");
							} else if (mobile.equals("")) {
								edt_mobile.setError("Enter Mobile No");
							} else if (address.equals("")) {
								edt_address.setError("Enter address");
							} else if (zip.equals("")) {
								edt_zip.setError("Enter Zipcode");
							} else if (city.equals("")) {
								edt_city.setError("Enter City");
							} else if (state.equals("")) {
								edt_state.setError("Enter State");
							} else {
								new posthome().execute();
							}
							DataBaseHelper myDbHelper = new DataBaseHelper(Orderdetail.this);
							myDbHelper = new DataBaseHelper(Orderdetail.this);
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
							db = myDbHelper.getWritableDatabase();

							cur = db.rawQuery("Delete from detail;", null);
							if (cur.getCount() != 0) {
								if (cur.moveToFirst()) {
									do {
										ItemGetSet obj = new ItemGetSet();

										description = cur.getString(cur.getColumnIndex("Description"));
										Log.d("description", "" + description);
										quentity = cur.getString(cur.getColumnIndex("Quentity"));
										Log.d("quentity", "" + quentity);
										total = cur.getString(cur.getColumnIndex("Total")).replace("$", "");
										Log.d("total", "" + total);

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
							cart = 0;
							SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
							editor.putString("count", "" + cart);
							editor.commit();

						}
					});
				}
			}

		});
	}

	// home delivery order post on server

	public class posthome extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// do stuff before posting data
			progressDialog = new ProgressDialog(Orderdetail.this);
			progressDialog.setMessage("Loading..");
			progressDialog.setCancelable(true);
			progressDialog.show();
		}

		@Override
		protected String doInBackground(String... strings) {
			try {

				// postText();
				postdatahome();
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String lenghtOfFile) {
			// do stuff after posting data
			Log.d("successful", "successful");
			if (progressDialog.isShowing()) {
				progressDialog.dismiss();
				new getlogin().execute();
			}

		}
	}

	private void postdatahome() {
		// TODO Auto-generated method stub
		HttpClient httpClient = new DefaultHttpClient();
		HttpEntity entity = null;

		entity = MultipartEntityBuilder.create().addTextBody("name", name).addTextBody("email", email)
				.addTextBody("address", address).addTextBody("number", mobile).addTextBody("neighborhood", nbh)
				.addTextBody("reg_id", regId).addTextBody("city", city).addTextBody("state", state)
				.addTextBody("details", detail).addTextBody("zipcode", zip).addTextBody("user_type", "Android")
				.addTextBody("orderdesc", totalvalue).addTextBody("orderdesc1", String.valueOf(finalsum))
				.addTextBody("device_id", deviceId).build();

		HttpPost httpPost = new HttpPost(getString(R.string.liveurl) + "webservice/bookorder_homedelivery.php");
		httpPost.setEntity(entity);
		HttpResponse response = null;
		try {
			response = httpClient.execute(httpPost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpEntity result = response.getEntity();
		if (result != null) {

			// String responseStr = "";
			try {
				responseStr = EntityUtils.toString(result).trim();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.v("Response", "Response: " + responseStr);

		}
	}

	private void Get_ChapterList() {
		// TODO Auto-generated method stub

		new SplashScreenDelay().execute();
	}

	// edit order database class

	@SuppressLint("NewApi")
	private class SplashScreenDelay extends AsyncTask<String, Integer, Integer> {

		private static final String TAG = "SplashScreenTask";

		@Override
		protected Integer doInBackground(String... params) {
			// TODO Auto-generated method stub

			Item.clear();
			DataBaseHelper myDbHelper = new DataBaseHelper(Orderdetail.this);
			myDbHelper = new DataBaseHelper(Orderdetail.this);
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

						cid = cur.getString(cur.getColumnIndex("id"));
						Log.d("id", "" + id);

						description = cur.getString(cur.getColumnIndex("Description"));
						Log.d("description", "" + description);

						quentity = cur.getString(cur.getColumnIndex("Quentity"));
						Log.d("quentity", "" + quentity);

						total = cur.getString(cur.getColumnIndex("Total")).replace("$", "");
						Log.d("total", "" + total);

						try {
							inttotal = Integer.parseInt(total);
							Log.d("inttotal5", "" + inttotal);
						} catch (NumberFormatException e) {
							// TODO: handle exception
						}
						Log.d("inttota6", "" + inttotal);

						try {
							MAINPOSITION = MAINPOSITION + Float.parseFloat(total);

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
							// finalsum = (MAINPOSITION + percentage);
							finalsum = MAINPOSITION;
						} catch (NumberFormatException e) {
							// TODO: handle exception
						}
						Log.d("finalsum", "" + finalsum);

						price = cur.getString(cur.getColumnIndex("Price"));
						Log.d("price", "" + price);

						obj.setId(cid);
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

			Log.d("size", "" + Item.size());
			if (Item.size() == 0) {
				Toast.makeText(Orderdetail.this, getString(R.string.emptycart), Toast.LENGTH_LONG).show();
			}
			totalvalue = "";
			Log.d("size", "" + Item.size());

			for (int j = 0; j < Item.size(); j++) {

				ItemGetSet obj1 = Item.get(j);

				String temp_id = obj1.getId();
				Log.d("temp_id", "" + temp_id);

				String temp_des = obj1.getDescription();
				Log.d("temp_des", "" + temp_des);

				String temp_quentity = obj1.getQuentity();
				Log.d("temp_quentity", "" + temp_quentity);

				String temp_total = obj1.getTotal().replace("$", "");
				Log.d("temp_total", "" + temp_total);

				String temp_price = obj1.getPrice();
				Log.d("temp_price", "" + temp_price);

				String temp = "Description=" + temp_des + ";" + "Price=" + temp_price + ";" + "Quantity="
						+ temp_quentity + ";" + "Total="
						+ temp_total /* + ";" + "id=" + temp_id + ";" */;

				Log.d("temp", "" + temp);

				totalvalue = totalvalue + temp;
				Log.d("temp1", "" + totalvalue);
			}

			listview1 = (ListView) findViewById(R.id.listView1);
			LazyAdapter lazy = new LazyAdapter(Orderdetail.this, Item);
			listview1.setAdapter(lazy);
			TextView txtfainalprice = (TextView) findViewById(R.id.txt_fainal);
			txtfainalprice.setText(getString(R.string.dollar_sign) + finalsum);
			if (key.equals("edit")) {

				listview1.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
						// TODO Auto-generated method stub

						final int pos = arg2;

						// custom dialog for edit order user permission

						AlertDialog.Builder builder1 = new AlertDialog.Builder(Orderdetail.this);
						builder1.setTitle(getString(R.string.delete));
						builder1.setMessage(getString(R.string.deletetext));
						builder1.setCancelable(true);
						builder1.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								// Home.this.finish();
								// finishAffinity();

								DataBaseHelper myDbHelper = new DataBaseHelper(Orderdetail.this);
								myDbHelper = new DataBaseHelper(Orderdetail.this);
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
								db = myDbHelper.getWritableDatabase();

								cur = db.rawQuery("Delete from detail where id=" + Item.get(pos).getId() + ";", null);
								if (cur.getCount() != 0) {
									if (cur.moveToFirst()) {
										do {
											ItemGetSet obj = new ItemGetSet();

											cid = cur.getString(cur.getColumnIndex("id"));

											description = cur.getString(cur.getColumnIndex("Description"));
											quentity = cur.getString(cur.getColumnIndex("Quentity"));
											price = cur.getString(cur.getColumnIndex("Price"));

											total = cur.getString(cur.getColumnIndex("Total")).replace("$", "");

											obj.setId(cid);
											obj.setDescription(description);

											obj.setTotal(total);
											obj.setPrice(price);
											obj.setQuentity(quentity);

											Item.add(obj);

										} while (cur.moveToNext());
									}
								}
								cur.close();
								db.close();
								myDbHelper.close();
								cart = cart - 1;
								SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE)
										.edit();
								editor.putString("count", "" + cart);
								editor.commit();

								// new getorder().execute();
								Intent iv = new Intent(Orderdetail.this, Orderdetail.class);
								// iv.putExtra("id", "" + Orderdetail.this.id);
								iv.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(iv);
							}
						});
						builder1.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

						AlertDialog alert11 = builder1.create();
						alert11.show();
					}
				});
			}

		}

	}

	// book table service

	public class SplashScreenDelay1 extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected Void doInBackground(Void... params) {
			URL hp = null;
			try {

				if (button.equals("press")) {

					hp = new URL(getString(R.string.liveurl) + "webservice/bookorder.php?name=" + editname + ""
							+ "&&people=" + buttontotal + "&&phone=" + editnumber + "&&datetime=" + datetime + ""
							+ "&&comment=" + editcomment + "&&orderdis=" + totalvalue + "&&orderdis2=" + dis2
							+ "&&reg_id=" + regId + "&&device=Android" + "&&device_id=" + deviceId);
				} else if (button.equals("unpress")) {
					hp = new URL(getString(R.string.liveurl) + "webservice/bookorder.php?name=" + editname + ""
							+ "&&people=" + buttontotal + "&&phone=" + editnumber + "&&datetime=" + datetime + ""
							+ "&&comment=" + editcomment + "&&orderdis=" + totalvalue + "&&orderdis2=" + dis2
							+ "&&reg_id=" + regId + "&&device=Android" + "&&device_id=" + deviceId);
				} else {
					hp = new URL(getString(R.string.liveurl) + "webservice/bookorder.php?name=" + editname + ""
							+ "&&people=" + buttontotal + "&&phone=" + editnumber + "&&datetime=" + datetime + ""
							+ "&&comment=" + editcomment + "&&orderdis=" + "" + "&&orderdis2=" + dis2 + "&&reg_id="
							+ regId + "&&device=Android" + "&&device_id=" + deviceId);
				}
				// http://192.168.1.106/admin/webservice/bookorder.php?name=&&people=&&phone=&&datetime=&&comment=&&orderdis=&&orderdis2=

				Log.d("userurl", "" + hp);
				URLConnection hpCon = hp.openConnection();
				hpCon.connect();
				InputStream input = hpCon.getInputStream();
				Log.d("input", "" + input);

				BufferedReader r = new BufferedReader(new InputStreamReader(input));

				String x = "";
				// x = r.readLine();
				String total = "";

				while (x != null) {
					total += x;
					x = r.readLine();
				}
				Log.d("totalid", "" + total);

				JSONObject j = new JSONObject(total);
				JSONArray jarr = j.getJSONArray("Massage");

				Log.d("j", "" + jarr);
				for (int i = 0; i < jarr.length(); i++) {
					JSONObject Obj;
					Obj = jarr.getJSONObject(i);
					ItemGetSet temp = new ItemGetSet();
					temp.setId(Obj.getString("id"));
					id1 = Obj.getString("id");
					Log.d("id1", "" + id1);
					Item.add(temp);
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
			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);
			Log.d("idbooked", "" + id1);
			if (id1.equals("False")) {
				RelativeLayout rl_back = (RelativeLayout) findViewById(R.id.rl_back);
				if (rl_back == null) {
					Log.d("second", "second");
					RelativeLayout rl_dialoguser1 = (RelativeLayout) findViewById(R.id.rl_infodialog);

					try {
						layout12 = getLayoutInflater().inflate(R.layout.addcart, rl_dialoguser1, false);
						rl_dialoguser1.addView(layout12);
					} catch (InflateException e) {
						// TODO: handle exception
					}

					ImageView img = (ImageView) layout12.findViewById(R.id.imageView);
					img.setImageResource(R.drawable.warning_btn);
					TextView txt_dia = (TextView) layout12.findViewById(R.id.txt_dia);
					txt_dia.setText("" + getString(R.string.notbooked));

					Button btn_yes = (Button) layout12.findViewById(R.id.btn_yes);
					btn_yes.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

							// Intent iv = new Intent(Orderdetail.this,
							// Home.class);
							// startActivity(iv);

							View myView = findViewById(R.id.rl_back);
							ViewGroup parent = (ViewGroup) myView.getParent();
							parent.removeView(myView);
						}
					});
				}
			} else {

				// if (button.equals("press")) {
				if (regId != null) {
					RelativeLayout rl_back = (RelativeLayout) findViewById(R.id.rl_back);
					if (rl_back == null) {
						Log.d("second", "second");
						RelativeLayout rl_dialoguser1 = (RelativeLayout) findViewById(R.id.rl_infodialog);

						try {
							layout12 = getLayoutInflater().inflate(R.layout.addcart, rl_dialoguser1, false);
							rl_dialoguser1.addView(layout12);
						} catch (InflateException e) {
							// TODO: handle exception
						}

						ImageView img = (ImageView) layout12.findViewById(R.id.imageView);
						img.setImageResource(R.drawable.yes_btn);
						TextView txt_dia = (TextView) layout12.findViewById(R.id.txt_dia);
						txt_dia.setText("" + getString(R.string.booked));
						totalvalue = totalvalue.replace(";", ",");
						message = "Your Book table request is placed with request Id " + id1 + " and item: "
								+ totalvalue + "; Total Amount is " + dis2;
						message = message.replace("%20", " ").replace("Description=", " ");
						Log.d("message", "" + message);
						Button btn_yes = (Button) layout12.findViewById(R.id.btn_yes);
						btn_yes.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								// chemsg="app";
								// SharedPreferences.Editor editor =
								// getSharedPreferences(MY_PREFS_NAME,
								// MODE_PRIVATE).edit();
								// editor.putString("chemsg", "" + chemsg);
								// editor.commit();
								DataBaseHelper myDbHelper = new DataBaseHelper(Orderdetail.this);
								try {
									myDbHelper.createDataBase();
								} catch (IOException io) {
									throw new Error("Unable TO Create DataBase");
								}
								try {
									myDbHelper.openDataBase();
								} catch (SQLException e) {
									e.printStackTrace();
								}
								db = myDbHelper.getWritableDatabase();
								ContentValues values = new ContentValues();

								values.put("message", message);
								values.put("sapp", "0");
								db.insert("orderdetail", null, values);

								myDbHelper.close();
								Intent iv = new Intent(Orderdetail.this, Message.class);
								startActivity(iv);
							}
						});
					}
				} else {

					RelativeLayout rl_back = (RelativeLayout) findViewById(R.id.rl_back);
					if (rl_back == null) {
						Log.d("second", "second");
						RelativeLayout rl_dialoguser = (RelativeLayout) findViewById(R.id.rl_infodialog);

						try {
							layout12 = getLayoutInflater().inflate(R.layout.addcart, rl_dialoguser, false);
						} catch (Exception e) {
							// TODO: handle exception
						}

						rl_dialoguser.addView(layout12);
						ImageView img = (ImageView) layout12.findViewById(R.id.imageView);
						img.setImageResource(R.drawable.cancel_icon);
						TextView txt_dia = (TextView) layout12.findViewById(R.id.txt_dia);
						txt_dia.setText("" + getString(R.string.notbooked));

						Button btn_yes = (Button) layout12.findViewById(R.id.btn_yes);
						btn_yes.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

								Intent iv = new Intent(Orderdetail.this, Home.class);
								startActivity(iv);
							}
						});
					}

				}
			}
		}

	}

	// listview of selected order from menu

	public class LazyAdapter extends BaseAdapter {

		private Activity activity;
		private ArrayList<ItemGetSet> data;
		private LayoutInflater inflater = null;

		Typeface tf = Typeface.createFromAsset(Orderdetail.this.getAssets(), "fonts/RestFont.TTF");

		public LazyAdapter(Activity a, ArrayList<ItemGetSet> d) {
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
				vi = inflater.inflate(R.layout.cell_orderdetail, null);
			}

			Button btn_minus = (Button) vi.findViewById(R.id.btn_minus);

			if (key.equals("normal")) {
				btn_minus.setVisibility(View.INVISIBLE);
			} else if (key.equals("edit")) {
				btn_minus.setVisibility(View.VISIBLE);
			}

			try {
				ItemGetSet NewObj = Item.get(position);
				TextView title = (TextView) vi.findViewById(R.id.txt_item);
				title.setText(NewObj.getDescription());

				TextView noofitem = (TextView) vi.findViewById(R.id.txt_numberofitem);
				noofitem.setText("* " + NewObj.getQuentity() + " =");

				TextView total = (TextView) vi.findViewById(R.id.txt_total);

				total.setText("$" + NewObj.getTotal());

				TextView price = (TextView) vi.findViewById(R.id.txt_price);
				price.setText("$" + NewObj.getPrice());
			} catch (NullPointerException e) {
				// TODO: handle exception
			}

			return vi;
		}
	}

	// select date from datepicker

	class mDateSetListener implements DatePickerDialog.OnDateSetListener {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			// TODO Auto-generated method stub

			try {
				mYear = year;
				mMonth = monthOfYear;
				mDay = dayOfMonth;

				String date12 = mDay + "-" + mMonth + "-" + mYear;
				String day = String.valueOf(mDay);
				/*
				 * Date date3 = new Date(); try { SimpleDateFormat format = new
				 * SimpleDateFormat("dd-MMM-yyyy"); date3 =
				 * format.parse(date12); Log.d("date3", ""+date3);
				 * txt_date.setText(""+date3); } catch (Exception e) { // TODO:
				 * handle exception }
				 */

				if (day.length() < 2) {
					day = "0" + day;
				}
				Log.d("day", "" + day);
				String yearf = String.valueOf(mYear);
				yearf = yearf.replace("20", "");
				mMonth = mMonth + 1;
				if (mMonth == 1) {
					day = "Jan";
				} else if (mMonth == 2) {
					day = "Feb";
				} else if (mMonth == 3) {
					day = "Mar";
				} else if (mMonth == 4) {
					day = "Apr";
				} else if (mMonth == 5) {
					day = "May";
				} else if (mMonth == 6) {
					day = "Jun";
				} else if (mMonth == 7) {
					day = "Jul";
				} else if (mMonth == 8) {
					day = "Aug";
				} else if (mMonth == 9) {
					day = "Sep";
				} else if (mMonth == 10) {
					day = "Oct";
				} else if (mMonth == 11) {
					day = "Nov";
				} else if (mMonth == 12) {
					day = "Dec";
				}
				txt_date.setText(new StringBuilder().append(mDay).append("-").append(day).append("-").append(yearf

				).append(" "));

				date = txt_date.getText().toString();
				Log.d("date", "" + date);

			} catch (NumberFormatException e) {
				// TODO: handle exception
			}

		}
	}

	// show interstitial ads

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
		cd = new ConnectionDetector(Orderdetail.this);

		if (!cd.isConnectingToInternet()) {
			alert.showAlertDialog(Orderdetail.this, getString(R.string.internet), getString(R.string.internettext),
					false);
			return;
		} else {
			// AdView mAdView = (AdView) findViewById(R.id.adView);
			// AdRequest adRequest = new AdRequest.Builder().build();
			// mAdView.loadAd(adRequest);
			Log.d("call", "call");

			mInterstitialAd = new InterstitialAd(Orderdetail.this);
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

	// for push notification

	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(CommonUtilities.EXTRA_MESSAGE);

			// Waking up mobile if it is sleeping
			WakeLocker.acquire(getApplicationContext());

			// Releasing wake lock
			WakeLocker.release();
		}
	};

	// after submit order check successfully place or not

	public class getlogin extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected Void doInBackground(Void... params) {

			// TODO Auto-generated method stub

			URL hp = null;
			try {

				JSONObject jObject = new JSONObject(responseStr);
				Log.d("URL1", "" + jObject);

				JSONArray j1 = jObject.getJSONArray("Status");
				Log.d("jsonarray", "" + j1);
				for (int i = 0; i < j1.length(); i++) {

					JSONObject Obj;
					Obj = j1.getJSONObject(i);

					idres = Obj.getString("id");

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Error = e.getMessage();
			} catch (NullPointerException e) {
				// TODO: handle exception
				Error = e.getMessage();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);

			// successfully placed

			if (idres.equals("True")) {

				RelativeLayout rl_back = (RelativeLayout) findViewById(R.id.rl_back);
				if (rl_back == null) {
					Log.d("second", "second");
					RelativeLayout rl_dialoguser1 = (RelativeLayout) findViewById(R.id.rl_infodialog);

					// try {
					layout12 = getLayoutInflater().inflate(R.layout.addcart, rl_dialoguser1, false);
					rl_dialoguser1.addView(layout12);
					Log.d("inflate", "inflate");

					ImageView img = (ImageView) layout12.findViewById(R.id.imageView);
					img.setImageResource(R.drawable.yes_btn);
					TextView txt_dia = (TextView) layout12.findViewById(R.id.txt_dia);
					txt_dia.setText("" + getString(R.string.booked));
					totalvalue = totalvalue.replace(";", ",");
					message = "Your order is placed with item " + totalvalue + " ;Total Amount is " + finalsum;
					message = message.replace("%20", " ").replace("Description=", " ");
					Log.d("message", "" + message);

					// on click of ok button your order detail store in database
					// and redirect to order detail page

					Button btn_yes = (Button) layout12.findViewById(R.id.btn_yes);
					btn_yes.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							chemsg = "app";
							SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
							editor.putString("chemsg", "" + chemsg);
							editor.commit();
							DataBaseHelper myDbHelper = new DataBaseHelper(Orderdetail.this);
							try {
								myDbHelper.createDataBase();
							} catch (IOException io) {
								throw new Error("Unable TO Create DataBase");
							}
							try {
								myDbHelper.openDataBase();
							} catch (SQLException e) {
								e.printStackTrace();
							}
							db = myDbHelper.getWritableDatabase();
							ContentValues values = new ContentValues();

							values.put("message", message);
							values.put("sapp", "0");
							db.insert("orderdetail", null, values);

							myDbHelper.close();
							Intent iv = new Intent(Orderdetail.this, Message.class);
							startActivity(iv);
						}
					});
				}

				// Toast.makeText(Orderdetail.this, "Your Order is booked
				// successfully", Toast.LENGTH_LONG).show();
			} else if (idres.equals("False")) {
				Toast.makeText(Orderdetail.this, "Try again later", Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	protected void onDestroy() {

		try {
			unregisterReceiver(mHandleMessageReceiver);
			GCMRegistrar.onDestroy(this);
		} catch (Exception e) {
			Log.e("UnRegister Receiver Error", "> " + e.getMessage());
		}
		super.onDestroy();
	}
}
