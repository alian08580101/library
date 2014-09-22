package com.jd.a6i.common;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.POWER_SERVICE;
import static android.os.PowerManager.PARTIAL_WAKE_LOCK;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import com.jd.a6i.task.UploadBackgroundTask;

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {
	private static final String TAG = "AlarmManagerBroadcastReceiver";
    private static final int RIGHT_NOW = 0;
    private static PendingIntent pendingIntent;

    public static void enableAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        pendingIntent = getPendingIntent(context);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, RIGHT_NOW, PropertyUtil.getProperty(context).getUploadIntervalTime(), pendingIntent);
    }

    public static void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    private static PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        return PendingIntent.getBroadcast(context, 0, intent, FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager powerManager = (PowerManager) context.getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PARTIAL_WAKE_LOCK, TAG);
        try {
        	wakeLock.acquire();
            UploadBackgroundService.submitTask(UploadBackgroundTask.class);
        } finally {
        	wakeLock.release();
        }
    }
}
