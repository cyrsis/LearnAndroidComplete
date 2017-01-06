package freaktemplate.singlerestau;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import freaktemplate.singlerestau.utils.DataBaseHelper;
import freaktemplate.singlerestau.utils.ItemGetSet;

public class Message extends Activity {
	ListView list_message;

	SQLiteDatabase db;
	ArrayList<ItemGetSet> Item;

	Cursor cur = null;
	String message, chemsg, sapp;

	public static final String MY_PREFS_NAME = "Restaurant";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);

		/*
		 * SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME,
		 * MODE_PRIVATE); if (prefs.getString("chemsg", null) != null) {
		 * 
		 * chemsg = prefs.getString("chemsg", null);
		 * 
		 * }
		 * 
		 * Log.d("chemsg", "" + chemsg);
		 */

		list_message = (ListView) findViewById(R.id.list_message);
		Item = new ArrayList<ItemGetSet>();
		new SplashScreenDelay().execute();

	}

	// get data from database which user is ordered and order is confirm or
	// reject

	private class SplashScreenDelay extends AsyncTask<String, Integer, Integer> {

		private static final String TAG = "SplashScreenTask";

		@Override
		protected Integer doInBackground(String... params) {
			// TODO Auto-generated method stub

			Item.clear();
			DataBaseHelper myDbHelper = new DataBaseHelper(Message.this);
			myDbHelper = new DataBaseHelper(Message.this);
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
				cur = db.rawQuery("select * from orderdetail order by id desc", null);
			} catch (SQLException e) {
				// TODO: handle exception
			}

			Log.d("SIZWA", "" + cur.getCount());
			if (cur.getCount() != 0) {
				if (cur.moveToFirst()) {
					do {
						ItemGetSet obj = new ItemGetSet();

						message = cur.getString(cur.getColumnIndex("message"));
						sapp = cur.getString(cur.getColumnIndex("sapp"));
						Log.d("description", "" + message);

						obj.setDescription(message);
						obj.setSapp(sapp);
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

			LazyAdapter lazy = new LazyAdapter(Message.this, Item);
			list_message.setAdapter(lazy);

		}

	}

	// bind data in listview

	public class LazyAdapter extends BaseAdapter {

		private Activity activity;
		private ArrayList<ItemGetSet> data;
		private LayoutInflater inflater = null;

		Typeface tf = Typeface.createFromAsset(Message.this.getAssets(), "fonts/RestFont.TTF");

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
				if (Item.get(position).getSapp().equals("0")) {
					vi = inflater.inflate(R.layout.cell_message, null);
				} else if (Item.get(position).getSapp().equals("1")) {
					vi = inflater.inflate(R.layout.cell_messageserver, null);
				}

			}
			try {
				ItemGetSet NewObj = Item.get(position);
				String text = NewObj.getDescription();
				TextView title = (TextView) vi.findViewById(R.id.txt_msg);

				title.setText(NewObj.getDescription());

				Rect bounds = new Rect();
				Paint textPaint = title.getPaint();
				textPaint.getTextBounds(text, 0, text.length(), bounds);
				int height = bounds.height();
				int width = bounds.width();

				Log.d("titleheight", " height " + height + " width " + width);
			} catch (NullPointerException e) {
				// TODO: handle exception
			}

			return vi;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.message, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent iv = new Intent(Message.this, Home.class);
		startActivity(iv);
	}
}
