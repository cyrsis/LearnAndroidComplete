package freaktemplate.singlerestau;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import freaktemplate.singlerestau.utils.Getset;
import freaktemplate.singlerestau.utils.ImageLoader;

public class Home extends Activity {

	Button btn_aboutus, btn_offer, btn_gallery, btn_menu, btn_booktable, btn_share, btn_map;
	TextView txt_address, txt_phone, txt_email, txt_web, textView1;
	ImageView img_scroll;
	ImageView imageview;
	LinearLayout ll_menu, ll_booktable;
	LinearLayout rel_call, rel_web, rel_mail;

	private int currentImage = 0;
	private int numImages = 10;
	String tempimage;
	String cnt;
	String Error;
	String[] separated = null;
	public static final String MY_PREFS_NAME = "Restaurant";

	JSONArray jarr;

	ProgressDialog progressDialog;

	ArrayList<Getset> rest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		init();

	}

	private void init() {
		// TODO Auto-generated method stub

		rest = new ArrayList<Getset>();
		textView1 = (TextView) findViewById(R.id.textView1);
		txt_address = (TextView) findViewById(R.id.txt_address);
		txt_phone = (TextView) findViewById(R.id.txt_phone);
		txt_email = (TextView) findViewById(R.id.txt_email);
		txt_web = (TextView) findViewById(R.id.txt_web);

		new getrestaudetail().execute();
		setLayout();

		rel_call = (LinearLayout) findViewById(R.id.rel_call);
		rel_mail = (LinearLayout) findViewById(R.id.rel_mail);
		rel_web = (LinearLayout) findViewById(R.id.rel_web);

		// call button click

		rel_call.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					String uri = "tel:" + rest.get(0).getPhone();
					Intent i = new Intent(Intent.ACTION_DIAL);
					i.setData(Uri.parse(uri));
					startActivity(i);
				} catch (NullPointerException e) {
					// TODO: handle exception
				}

			}
		});

		// mail button click

		rel_mail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				try {
					String mail = rest.get(0).getEmail();

					Intent iv = new Intent(Home.this, Setting.class);
					iv.putExtra("mail", "" + mail);

					startActivity(iv);
				} catch (NullPointerException e) {
					// TODO: handle exception
				}

			}
		});

		// websir=te click

		rel_web.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				try {
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(rest.get(0).getWebsite()));
					startActivity(browserIntent);
				} catch (NullPointerException e) {
					// TODO: handle exception
				}

			}
		});
	}

	// get restaurant detail from server

	public class getrestaudetail extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(Home.this);
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
			if (Error != null) {
				if (progressDialog.isShowing()) {
					progressDialog.dismiss();
				}
				Toast.makeText(Home.this, getString(R.string.nodata), Toast.LENGTH_LONG).show();
			} else {
				if (progressDialog.isShowing()) {
					progressDialog.dismiss();

					try {
						// set images in preference
						String my = jarr.toString();
						SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
						editor.putString("images", "" + my);

						editor.commit();
						separated = my.split(",");
						Log.d("images", "" + separated.length);

						CustomPagerAdapter mCustomPagerAdapter = new CustomPagerAdapter(Home.this);
						ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);

						mViewPager.setAdapter(mCustomPagerAdapter);
						TextView txt_location = (TextView) findViewById(R.id.txt_location);
						Typeface tf = Typeface.createFromAsset(Home.this.getAssets(), "fonts/verdana.ttf");
						Typeface tf1 = Typeface.createFromAsset(Home.this.getAssets(), "fonts/Arimo-Regular.ttf");
						txt_location.setTypeface(tf);
						txt_address.setText("" + rest.get(0).getAddress() + "," + rest.get(0).getCountry() + "\n"
								+ rest.get(0).getState());
						txt_address.setTypeface(tf1);
						txt_phone.setText("" + rest.get(0).getPhone());
						txt_phone.setTypeface(tf);
						txt_email.setText("" + rest.get(0).getEmail());
						txt_email.setTypeface(tf);
						txt_web.setText("" + rest.get(0).getWebsite().replace("http://", ""));
						txt_web.setTypeface(tf);
						TextView txt_call = (TextView) findViewById(R.id.txt_call);
						TextView txt_web1 = (TextView) findViewById(R.id.txt_web1);
						TextView txt_mail = (TextView) findViewById(R.id.txt_mail);
						txt_call.setTypeface(tf);
						txt_web1.setTypeface(tf);
						txt_mail.setTypeface(tf);

						TextView txt_menu = (TextView) findViewById(R.id.btn_menu);
						TextView txt_booktable = (TextView) findViewById(R.id.btn_booktable);
						txt_menu.setTypeface(tf);
						txt_booktable.setTypeface(tf);
					} catch (NullPointerException e) {
						// TODO: handle exception
					}

				}

			}

		}

	}

	// get restaurant detail from json url

	private void getdetailforNearMe() {
		// TODO Auto-generated method stub

		URL hp = null;
		try {
			rest.clear();

			hp = new URL(getString(R.string.liveurl) + "webservice/restaurantdetail.php");

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
			JSONObject j = new JSONObject(total);
			Log.d("total", "" + j);

			Log.d("jsonobject", "" + j);
			for (int i = 0; i < j.length(); i++) {

				JSONObject obj = j.getJSONObject("Restaurant");
				JSONObject obj1 = obj.getJSONObject("location");

				Getset temp = new Getset();
				jarr = obj.getJSONArray("images");
				Log.d("jarr", "" + jarr);
				for (int k = 0; k < jarr.length(); k++) {

					temp.setImages(jarr.getString(k));
					tempimage = jarr.getString(k);
					Log.d("images12", "" + tempimage);
					rest.add(temp);
				}
				temp.setRestaurantname(obj.getString("restaurantname"));
				temp.setPhone(obj.getString("phone"));
				temp.setEmail(obj.getString("email"));
				temp.setWebsite(obj.getString("website"));
				temp.setAddress(obj1.getString("address"));
				temp.setCountry(obj1.getString("country"));
				temp.setState(obj1.getString("state"));
				temp.setLat(obj.getString("lat"));
				temp.setLongi(obj.getString("long"));
				rest.add(temp);
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

	private void setLayout() {
		// TODO Auto-generated method stub

		// about us button click

		btn_aboutus = (Button) findViewById(R.id.btn_aboutus);
		btn_aboutus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent i = new Intent(Home.this, Aboutus.class);
				startActivity(i);

			}

		});

		// map button click

		btn_map = (Button) findViewById(R.id.btn_map);
		btn_map.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent i = new Intent(Home.this, MainActivity.class);
				i.putExtra("lat", "" + rest.get(0).getLat());
				i.putExtra("longi", "" + rest.get(0).getLongi());
				startActivity(i);

			}

		});

		// offer button click

		btn_offer = (Button) findViewById(R.id.btn_offer);
		btn_offer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent i = new Intent(Home.this, Offer.class);

				startActivity(i);

			}

		});

		// gallery button click

		btn_gallery = (Button) findViewById(R.id.btn_gallery);
		btn_gallery.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent i = new Intent(Home.this, Gallery.class);

				startActivity(i);

			}

		});

		// menu button click

		ll_menu = (LinearLayout) findViewById(R.id.ll_buttonmenu);
		ll_menu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Home.this, freaktemplate.singlerestau.Menu.class);

				startActivity(i);

			}
		});

		// order detail button click

		ll_booktable = (LinearLayout) findViewById(R.id.ll_buttonbook);
		ll_booktable.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				Intent i = new Intent(Home.this, Message.class);
				startActivity(i);
			}
		});

		btn_share = (Button) findViewById(R.id.btn_notes);
		btn_share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Uri bmpUri = getLocalBitmapUri(imageview);

				Log.d("imageuri", "" + bmpUri);
				Intent share = new Intent(android.content.Intent.ACTION_SEND);
				share.setType("text/plain");

				share.setType("image/*");
				share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
				share.putExtra(Intent.EXTRA_SUBJECT, rest.get(0).getRestaurantname());
				share.putExtra(Intent.EXTRA_STREAM, bmpUri);

				share.putExtra(Intent.EXTRA_TEXT,
						"https://play.google.com/store/apps/details?id=" + Home.this.getPackageName() + "\n" + "Email: "
								+ rest.get(0).getEmail() + "\n" + "Address: " + rest.get(0).getAddress() + " , "
								+ rest.get(0).getState() + " , " + rest.get(0).getCountry() + "\n" + "Website:"
								+ rest.get(0).getWebsite() + "\n" + "Contact:" + rest.get(0).getPhone());
				startActivity(Intent.createChooser(share, getString(R.string.sharelink)));

			}

		});
	}

	// image bind in slider

	class CustomPagerAdapter extends PagerAdapter {

		Context mContext;
		LayoutInflater mLayoutInflater;

		public CustomPagerAdapter(Context context) {
			mContext = context;
			mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			return separated.length;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == ((RelativeLayout) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View itemView = mLayoutInflater.inflate(R.layout.image_pager, container, false);
			try {
				imageview = (ImageView) itemView.findViewById(R.id.image_page_fliper);
				
				String imageurl = getString(R.string.liveurl) + "uploads/slide/"
						+ separated[position].replace("[", "").replace("]", "").replace("\"", "");
				Log.d("imagesurl", "" + imageurl);
				ImageLoader imgLoader = new ImageLoader(Home.this);
				imgLoader.DisplayImage(imageurl.replace(" ", "%20"), imageview);
				container.addView(itemView);
			} catch (Exception e) {
				// TODO: handle exception
			}
			

			return itemView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((RelativeLayout) object);
		}
	}

	// home page backpressed

	@Override
	public void onBackPressed() {

		AlertDialog.Builder builder1 = new AlertDialog.Builder(Home.this);
		builder1.setTitle(getString(R.string.Quit));
		builder1.setMessage(getString(R.string.statementquit));
		builder1.setCancelable(true);
		builder1.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// Home.this.finish();
				finishAffinity();

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

		// super.onBackPressed();

	}

	// image share on social site

	public Uri getLocalBitmapUri(ImageView imageView) {
		// Extract Bitmap from ImageView drawable
		Drawable drawable = imageView.getDrawable();
		Bitmap bmp = null;
		if (drawable instanceof BitmapDrawable) {
			bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
		} else {
			return null;
		}
		// Store image to default external storage directory
		Uri bmpUri = null;
		try {

			File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),
					"share_image_" + System.currentTimeMillis() + ".png");
			FileOutputStream out = new FileOutputStream(file);
			bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
			out.close();
			bmpUri = Uri.fromFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bmpUri;
	}
}
