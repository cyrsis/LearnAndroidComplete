package freaktemplate.singlerestau.utils;

import android.content.Context;
import android.os.PowerManager;

public class WakeLocker {
	private static PowerManager.WakeLock wakeLock;

	public static void acquire(Context context) {
		if (wakeLock != null) {
			try {
				wakeLock.release();
			} catch (Throwable th) {
				// ignoring this exception, probably wakeLock was already
				// released
			}
		}

		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		wakeLock = pm.newWakeLock(
				PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE,
				"WakeLock");
		wakeLock.acquire();
	}

	public static void release() {
		if (wakeLock != null) {
			try {
				wakeLock.release();
			} catch (Throwable th) {
				// ignoring this exception, probably wakeLock was already
				// released
			}
			// wakeLock.release();
		} else {
			wakeLock = null;
		}

	}
}
