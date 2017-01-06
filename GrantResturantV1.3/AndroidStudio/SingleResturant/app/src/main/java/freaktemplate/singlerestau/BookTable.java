package freaktemplate.singlerestau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import freaktemplate.singlerestau.utils.ItemGetSet;

public class BookTable extends Activity {

	Button btn_add, btn_minus, btn_total;
	TextView txt_date, txt_time;
	int value = 0;
	boolean isPressed = true;
	EditText edit_name, edit_number, edit_comment;
	String editname, editnumber, editcomment, buttontotal, time, date;
	ArrayList<ItemGetSet> Item;
	String description, quentity, total, price, dis1, dis2, finalsum, mainposition, percentage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_table);

		Intent iv = getIntent();

		description = iv.getStringExtra("description");
		quentity = iv.getStringExtra("quentity");
		total = iv.getStringExtra("total");
		price = iv.getStringExtra("price");

		dis1 = description + price + quentity + total;
		Log.d("dis1", "" + dis1);

		percentage = iv.getStringExtra("percentage");
		mainposition = iv.getStringExtra("mainposition");
		finalsum = iv.getStringExtra("finalsum");

		dis2 = percentage + mainposition + finalsum;
		Log.d("dis2", "" + dis2);

		edit_name = (EditText) findViewById(R.id.edit_name);
		editname = edit_name.getText().toString();
		// editname="10";
		// Toast.makeText(Setting.this, ""+editname, Toast.LENGTH_LONG).show();
		Log.d("editentertime", "" + editname);

		edit_number = (EditText) findViewById(R.id.edit_number);
		editnumber = edit_number.getText().toString();
		// editnumber="10";
		// Toast.makeText(Setting.this, ""+editnumber,
		// Toast.LENGTH_LONG).show();
		Log.d("editentertime", "" + editnumber);

		edit_comment = (EditText) findViewById(R.id.edit_comment);
		editcomment = edit_comment.getText().toString();
		// editcomment="10";
		// Toast.makeText(Setting.this, ""+editcomment,
		// Toast.LENGTH_LONG).show();
		Log.d("editcomment", "" + editcomment);

		btn_add = (Button) findViewById(R.id.btn_add);
		btn_minus = (Button) findViewById(R.id.btn_minus);
		btn_total = (Button) findViewById(R.id.btn_total);

		btn_total.setText("" + value);
		buttontotal = btn_total.getText().toString();

		btn_minus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (value <= 0) {
					value = 0;
					btn_total.setText("" + value);
					buttontotal = btn_total.getText().toString();
				} else {
					value--;
					btn_total.setText("" + value);
					buttontotal = btn_total.getText().toString();
				}

			}
		});

		// number of person(add button)
		btn_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				value++;
				btn_total.setText("" + value);
				buttontotal = btn_total.getText().toString();
			}
		});

		txt_date = (TextView) findViewById(R.id.txt_date);
		txt_time = (TextView) findViewById(R.id.txt_time);

		txt_date.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Calendar c = Calendar.getInstance();
				int mYear = c.get(Calendar.YEAR);
				int mMonth = c.get(Calendar.MONTH);
				int mDay = c.get(Calendar.DAY_OF_MONTH);
				System.out.println("the selected " + mDay);
				DatePickerDialog dialog = new DatePickerDialog(BookTable.this, new mDateSetListener(), mYear, mMonth,
						mDay);
				dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
				dialog.show();
			}
		});

		txt_time.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Calendar mcurrentTime = Calendar.getInstance();
				int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
				int minute = mcurrentTime.get(Calendar.MINUTE);
				TimePickerDialog mTimePicker;
				mTimePicker = new TimePickerDialog(BookTable.this, new TimePickerDialog.OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
						txt_time.setText(selectedHour + ":" + selectedMinute);

						time = txt_time.getText().toString();
						Log.d("time", "" + time);

					}
				}, hour, minute, true);// Yes 24 hour time
				mTimePicker.setTitle("Select  Time");
				mTimePicker.show();

			}
		});

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

	public class SplashScreenDelay extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected Void doInBackground(Void... params) {
			URL hp = null;
			try {

				// http://192.168.1.106/admin/webservice/bookorder.php?name=&&people=&&phone=&&datetime=&&comment=&&orderdis=&&orderdis2=
				hp = new URL(getString(R.string.liveurl)+"webservice/bookorder.php?name=" + editname + "" + "&&people="
						+ buttontotal + "&&phone=" + editnumber + "&&datetime=" + date + "" + "&&comment=" + editcomment
						+ "&&orderdis=&&orderdis2=");

				/*
				 * hp = new URL(
				 * "http://restaurant.admin.redixbit.com/userfeedback.php?res_id="
				 * + id + "&&user_id=" + user2 + "&&ratting=" + userrate +
				 * "&&comment=" + usercomment);
				 */

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

				JSONObject j = new JSONObject("Massage");

				Log.d("j", "" + j);
				for (int i = 0; i < j.length(); i++) {
					JSONObject Obj;
					Obj = j.getJSONObject(String.valueOf(i));
					// User temp = new User();
					// temp.setId(Obj.getString("id"));

					// Item.add(temp);
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

		}

	}

	class mDateSetListener implements DatePickerDialog.OnDateSetListener {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			// TODO Auto-generated method stub

			int mYear = year;
			int mMonth = monthOfYear;
			int mDay = dayOfMonth;
			txt_date.setText(new StringBuilder()
					// Month is 0 based so add 1
					.append(mMonth + 1).append("/").append(mDay).append("/").append(mYear).append(" "));

			date = txt_date.getText().toString();
			Log.d("date", "" + date);

		}
	}

}
