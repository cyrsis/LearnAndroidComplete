package freaktemplate.singlerestau.utils;

import java.io.IOException;
import java.util.Random;

import com.google.android.gcm.GCMBaseIntentService;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import freaktemplate.singlerestau.Message;
import freaktemplate.singlerestau.R;

public class GCMIntentService extends GCMBaseIntentService {
	private static final String TAG = "GCMIntentService";
	SQLiteDatabase db;
	
	public static final String MY_PREFS_NAME = "Restaurant";

	public GCMIntentService() {
		super(CommonUtilities.SENDER_ID);
	}

	/**
	 * Method called on device registered
	 **/

	@Override
	protected void onRegistered(Context context, String registrationId) {
		Log.d("life", "Device registered: regId = " + registrationId);
		//CommonUtilities.displayMessage(context, "Your device registred with GCM");
		// Log.d("NAME", "" + Settingcoupon.latitudecur);
		// ServerUtilities.register(context, Settingcoupon.latitudecur,
		// Settingcoupon.longitudecur, registrationId,
		// Settingcoupon.deviceId);
	}

	/**
	 * Method called on device un registred
	 */
	@Override
	protected void onUnregistered(Context context, String registrationId) {
		Log.i(TAG, "Device unregistered");
		Log.d("unrecv", "unregister");
		CommonUtilities.displayMessage(context, getString(R.string.gcm_unregistered));
		//ServerUtilities.unregister(context, registrationId);
	}

	/**
	 * Method called on Receiving a new message
	 */
	@Override
	protected void onMessage(Context context, Intent intent) {
		
		Log.d("receivemsg", "Receive message");
		String message = intent.getExtras().getString("price");

		CommonUtilities.displayMessage(context, message);
		// notifies user
		//String chemsg="server";
		//SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
		//editor.putString("chemsg", "" + chemsg);
		//editor.commit();
		DataBaseHelper myDbHelper = new DataBaseHelper(context);
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
		values.put("sapp", "1");

		db.insert("orderdetail", null, values);

		myDbHelper.close();
		generateNotification(context, message);
	}

	/**
	 * Method called on receiving a deleted message
	 */

	@Override
	protected void onDeletedMessages(Context context, int total) {
		Log.i(TAG, "Received deleted messages notification");
		String message = getString(R.string.gcm_deleted, total);
		CommonUtilities.displayMessage(context, message);
		// notifies user
		generateNotification(context, message);
	}

	/**
	 * Method called on Error
	 */

	@Override
	public void onError(Context context, String errorId) {
		Log.i(TAG, "Received error: " + errorId);
		CommonUtilities.displayMessage(context, getString(R.string.gcm_error, errorId));
	}

	@Override
	protected boolean onRecoverableError(Context context, String errorId) {
		// log message
		Log.i(TAG, "Received recoverable error: " + errorId);
		CommonUtilities.displayMessage(context, getString(R.string.gcm_recoverable_error, errorId));
		return super.onRecoverableError(context, errorId);
	}

	/**
	 * Issues a notification to inform the user that server has sent a message.
	 */
	private static void generateNotification(Context context, String message) {

		Random r = new Random();
		int i1 = r.nextInt(100 - 1) + 1;

		int icon = R.drawable.ic_launcher;
		long when = System.currentTimeMillis();
		Log.d("message1", message);
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		@SuppressWarnings("deprecation")
		Notification notification = new Notification(icon, message, when);

		String title = context.getString(R.string.app_name);

		Intent notificationIntent = new Intent(context, Message.class);
		// set intent so it does not start a new activity
		
		// notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
		// Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent intent = PendingIntent.getActivity(context, i1, notificationIntent, 0);

		Notification.Builder builder = new Notification.Builder(context).setContentIntent(intent).setSmallIcon(icon)
				.setContentTitle(title).setContentText(message);
		builder.setAutoCancel(true);

		notification = builder.build();
		// notification.setLatestEventInfo(context, notificationText,
		// notificationTitle, pendingNotificationIntent);
		// notification.flags = Notification.FLAG_AUTO_CANCEL;

		Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		notification.sound = uri;
		// notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notificationManager.notify(0, notification);
		/*
		 * notification.setLatestEventInfo(context, title, message, intent);
		 * notification.flags |= Notification.FLAG_AUTO_CANCEL;
		 * 
		 * // Play default notification sound notification.defaults |=
		 * Notification.DEFAULT_SOUND;
		 * 
		 * // Vibrate if vibrate is enabled notification.defaults |=
		 * Notification.DEFAULT_VIBRATE; notificationManager.notify(0,
		 * notification);
		 */

	}

}
