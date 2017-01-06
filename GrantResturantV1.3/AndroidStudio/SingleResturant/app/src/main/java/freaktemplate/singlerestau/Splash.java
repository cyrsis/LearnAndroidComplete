package freaktemplate.singlerestau;

import com.google.android.gcm.GCMRegistrar;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import freaktemplate.singlerestau.utils.CommonUtilities;
import freaktemplate.singlerestau.utils.WakeLocker;

public class Splash extends Activity {
	String regId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_splash);
		
		gcm();
		
		Thread th = new Thread() {
			@Override
			public void run() {
				try {

					sleep(2000);
					Intent i = new Intent(getBaseContext(), Home.class);
					startActivity(i);
					finish();

				} catch (Exception e) {

				}

			}
		};
		th.start();
	}

	private void gcm() {
		// TODO Auto-generated method stub
		GCMRegistrar.checkDevice(Splash.this);
		GCMRegistrar.checkManifest(Splash.this);
		registerReceiver(mHandleMessageReceiver, new IntentFilter(CommonUtilities.DISPLAY_MESSAGE_ACTION));
		GCMRegistrar.register(Splash.this, CommonUtilities.SENDER_ID);
		// Get GCM registration id
		regId = GCMRegistrar.getRegistrationId(Splash.this);
		Log.d("regIdrest", "" + regId);
	}

	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(CommonUtilities.EXTRA_MESSAGE);

			// Waking up mobile if it is sleeping
			WakeLocker.acquire(getApplicationContext());

			// Showing received message
			// lblMessage.append(newMessage + "\n");

			// Toast.makeText(getApplicationContext(), "New Message: " +
			// newMessage, Toast.LENGTH_LONG).show();

			// Releasing wake lock
			WakeLocker.release();
		}
	};

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
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
}
