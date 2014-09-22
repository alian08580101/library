package com.jd.a6i.common;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

public class PollingServiceManager {
	public static void startPollingService(Context context,int intervalMillis,Class<?> pollingService,String action){
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context,pollingService);
		intent.setAction(action);
		PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		long triggerAtMillis = SystemClock.elapsedRealtime();
		alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, triggerAtMillis, intervalMillis*1000, pendingIntent);
	}
	public static void stopPollingService(Context context,Class<?> pollingService,String action){
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context,pollingService);
		intent.setAction(action);
		PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		alarmManager.cancel(pendingIntent);
	}
}
