package freaktemplate.singlerestau;

import java.io.File;
import java.nio.channels.FileChannel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import freaktemplate.singlerestau.utils.ConnectionDetector;
import freaktemplate.singlerestau.utils.GPSTracker;


public class Setting extends PreferenceActivity {
	SharedPreferences sharedPreferences;

	Preference contactUs, version, importExport, categoryList1, CurrencyList1;
	ListPreference sortBy, defultCurrency;

	Context context = this;
	File file;
	FileChannel src = null;
	FileChannel dst = null;
	int inAppFlag;
	String name, link, mailpage, storename, address, offer, latitude, longitude, distance, store_name, storeaddress,
			description, notice;

	Boolean isInternetPresent = false;
	ConnectionDetector cd;
	GPSTracker gps;
	double latitudecur;
	double longitudecur;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_setting);

		cd = new ConnectionDetector(getApplicationContext());

		isInternetPresent = cd.isConnectingToInternet();
		// check for Internet status
		if (isInternetPresent) {

			gps = new GPSTracker(Setting.this);

			if (gps.canGetLocation()) {
				latitudecur = gps.getLatitude();
				longitudecur = gps.getLongitude();

			} else {

				gps.showSettingsAlert();
			}

		}

		addPreferencesFromResource(R.xml.setting);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

		getintent();
		openGmail();
		
	}

	private void getintent() {
		// TODO Auto-generated method stub
		Intent iv = getIntent();
		
		mailpage = iv.getStringExtra("mail");
		

	}

	private void openGmail() {
		// TODO Auto-generated method stub
		try {

			Intent gmail = new Intent(Intent.ACTION_VIEW);
			gmail.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
			gmail.putExtra(Intent.EXTRA_EMAIL, new String[] { mailpage });
			gmail.setData(Uri.parse(""));
			gmail.putExtra(Intent.EXTRA_SUBJECT, "Grant Restaurant");
			gmail.setType("text/plain");
			gmail.putExtra(Intent.EXTRA_TEXT, "");
			startActivity(gmail);
		} catch (Exception e) {
			sendEmail();
		}
	}

	private void openGmail1() {
		// TODO Auto-generated method stub
		try {

			Intent gmail = new Intent(Intent.ACTION_VIEW);
			gmail.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
			gmail.putExtra(Intent.EXTRA_EMAIL, new String[] { "" });
			gmail.setData(Uri.parse(""));
			gmail.putExtra(Intent.EXTRA_SUBJECT, "Coupon");
			gmail.setType("text/plain");
			gmail.putExtra(Intent.EXTRA_TEXT,
					"Businessname: " + storename + "\n" + "Businessaddress: " + address + "\n" + "offer: " + offer
							+ "\n" + "user latitude: " + latitudecur + "\n" + "user longitude: " + longitudecur + "\n"
							+ "Business latitude: " + latitude + "\n" + "Business longitude: " + longitude + "\n"
							+ "Reported distance: " + distance + " km");
			startActivity(gmail);
		} catch (Exception e) {
			sendEmail();
		}
	}

	private void openGmail2() {
		// TODO Auto-generated method stub
		try {

			Intent gmail = new Intent(Intent.ACTION_VIEW);
			gmail.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
			gmail.putExtra(Intent.EXTRA_EMAIL, new String[] { "" });
			gmail.setData(Uri.parse(""));
			gmail.putExtra(Intent.EXTRA_SUBJECT, "Steal the Deal");
			gmail.setType("text/plain");
			gmail.putExtra(Intent.EXTRA_TEXT, storename + "\n" + storeaddress + "\n" + description + "\n" + notice
					+ "\n" + "Download now at: " + link);
			startActivity(gmail);
		} catch (Exception e) {
			sendEmail();
		}
	}

	private void sendEmail() {
		// TODO Auto-generated method stub
		String recipient = "";
		String subject = "Coupon";
		@SuppressWarnings("unused")
		String body = "";

		String[] recipients = { recipient };
		Intent email = new Intent(Intent.ACTION_SEND);

		email.setType("message/rfc822");

		email.putExtra(Intent.EXTRA_EMAIL, recipients);
		email.putExtra(Intent.EXTRA_SUBJECT, subject);

		try {

			startActivity(Intent.createChooser(email, getString(R.string.email_choose_from_client)));

		} catch (android.content.ActivityNotFoundException ex) {

			Toast.makeText(Setting.this, getString(R.string.email_no_client), Toast.LENGTH_LONG).show();

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setting, menu);
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
		Setting.this.finish();
	}

}
