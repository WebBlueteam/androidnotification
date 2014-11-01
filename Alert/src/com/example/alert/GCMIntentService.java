package com.example.alert;

import java.util.Calendar;

import notification.MyConstants;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {
	private NotificationManager mNotificationManager;
	// private Notification myNotification;
	private static final int MY_NOTIFICATION_ID = 1;
	private int notificationID = 0;

	public GCMIntentService() {
		super(MyConstants.SENDER_ID);

	}

//	@SuppressLint("NewApi")
//	public void notificat(String messg) {
//
//		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
//				this).setSmallIcon(R.drawable.ic_launcher)
//				.setContentTitle("message from service").setContentText(messg)
//				.setAutoCancel(true);
//		// Creates an explicit intent for an Activity in your app
//		Intent resultIntent = new Intent(this, main.class);
//
//		// The stack builder object will contain an artificial back stack for
//		// the
//		// started Activity.
//		// This ensures that navigating backward from the Activity leads out of
//		// your application to the Home screen.
//		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//		// Adds the back stack for the Intent (but not the Intent itself)
//		stackBuilder.addParentStack(main.class);
//		// Adds the Intent that starts the Activity to the top of the stack
//		stackBuilder.addNextIntent(resultIntent);
//		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
//				PendingIntent.FLAG_UPDATE_CURRENT);
//		mBuilder.setContentIntent(resultPendingIntent);
//		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//		// mId allows you to update the notification later on.
//		mNotificationManager.notify(MY_NOTIFICATION_ID, mBuilder.build());
//	}

	@Override
	protected void onMessage(final Context context, Intent intent) {

		String message = intent.getExtras().getString("message");
		Log.d("ms", message);
		long when = Calendar.getInstance().getTimeInMillis();

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				context);

		mBuilder.setContentTitle("Thông Báo");
		mBuilder.setContentText(message);
		mBuilder.setTicker("New Message Alert!");
		mBuilder.setSmallIcon(R.drawable.ic_launcher);
		//mBuilder.setLargeIcon(loadedImage);
		mBuilder.setWhen(when);
		// mBuilder.setAutoCancel(true);
		mBuilder.setDefaults(Notification.DEFAULT_LIGHTS
				| Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND);

		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		mNotificationManager.notify((int) when, mBuilder.build());

	}

	@Override
	protected void onError(Context context, String s) {
		// To change body of implemented methods use File | Settings | File
		// Templates.
	}

	@Override
	protected void onRegistered(final Context context, String registrationId) {
		// SharedPreferences sharedPreferences = PreferenceManager
		// .getDefaultSharedPreferences(getApplicationContext());
		// String name = sharedPreferences.getString(Constants.PREFERENCE_NAME,
		// "");
		// String email =
		// sharedPreferences.getString(Constants.PREFERENCE_EMAIL, "");
		Log.i("Key", registrationId);
	}

	@Override
	protected void onUnregistered(Context context, String registrationId) {
		String name = Thread.currentThread().getName();
		// serverUtil.unRegister(context, registrationId);
	}
}
