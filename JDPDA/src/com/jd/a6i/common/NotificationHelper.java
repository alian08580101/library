package com.jd.a6i.common;

import java.util.List;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;

import com.jd.a6i.R;
import com.jd.a6i.volley.AfterUploadActivity;

public class NotificationHelper {

    private static final int ID_PENDING_COUNT = 1;
    private static final int ID_INTERNAL_MESSAGE = 2;
    private static final int ID_RE_PULL_ORDER = 3;

    public static void sendPendingCountChangedNotification(Context context, String typeContent, int count) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = getNotification(context, typeContent, count);
        notificationManager.notify(ID_PENDING_COUNT, notification);
    }
    
    public static void sendPendingCountChangedNotification(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = getNotification(context, intent);
        notificationManager.notify(ID_PENDING_COUNT, notification);
    }

    public static void cancelPendingCountNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(ID_PENDING_COUNT);
    }

    private static Notification getNotification(Context context,String typeContent, int count) {
    	Notification notification = getBuilder(context, typeContent ,count).build();
    	notification.flags = Notification.FLAG_AUTO_CANCEL;
    	return notification;
    }
    
    private static <T> Notification getNotification(Context context,Intent intent) {
    	Notification notification = getBuilder(context, intent).build();
    	notification.flags = Notification.FLAG_AUTO_CANCEL;
    	return notification;
    }

    private static NotificationCompat.Builder getBuilder(Context context, String typeContent, int count){
    	NotificationCompat.Builder builder = new Builder(context);
    	builder.setContentTitle(context.getString(R.string.submit))
    		   .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.notification_large_64))
    		   .setContentText(typeContent)
    		   .setContentInfo(String.valueOf(count))
    		   //.setContentIntent(getPendingIntent(context, MainActivity.class))
    		   .setNumber(count)
    		   .setTicker(context.getString(R.string.submit))
    		   .setWhen(System.currentTimeMillis())
    		   .setPriority(Notification.PRIORITY_DEFAULT)
    		   .setAutoCancel(true)
    		   .setOngoing(false)
    		   .setDefaults(Notification.DEFAULT_ALL)
    		   .setSmallIcon(R.drawable.notification_small_16);
    	return builder;
    }
    
    private static NotificationCompat.Builder getBuilder(Context context, Intent intent){
    	String contentText = intent.getStringExtra(StatusBarBroadcastReceiver.KEY_TYPE_CONTENT);
    	int number = intent.getIntExtra(StatusBarBroadcastReceiver.KEY_UPLOAD_COUNT, 0);
    	String contentInfo = String.valueOf(number);
    	
    	NotificationCompat.Builder builder = new Builder(context);
    	builder.setContentTitle(context.getString(R.string.submit))
    		   .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.notification_large_64))
    		   .setContentText(contentText)
    		   .setContentInfo(contentInfo)
    		   .setContentIntent(getPendingIntent(context, intent, AfterUploadActivity.class))
    		   .setNumber(number)
    		   .setTicker(context.getString(R.string.submit))
    		   .setWhen(System.currentTimeMillis())
    		   .setPriority(Notification.PRIORITY_DEFAULT)
    		   .setAutoCancel(true)
    		   .setOngoing(false)
    		   .setDefaults(Notification.DEFAULT_ALL)
    		   .setSmallIcon(R.drawable.notification_small_16);
    	return builder;
    }
    
    private static PendingIntent getPendingIntent(Context context, Class<? extends Activity> clazz) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setClass(context, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

        return PendingIntent.getActivity(context, 0, intent, 0);
    }
    
    private static <T> PendingIntent getPendingIntent(Context context, Intent uploadIntent,Class<? extends Activity> clazz) {
        Intent intent = new Intent(uploadIntent);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setClass(context, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
  
        return PendingIntent.getActivity(context, 0, intent, 0);
    }
    
    public static void cancelAllNotification(Context context) {
        cancelInternalMessageNotification(context);
        cancelPendingCountNotification(context);
        cancelRePullOrderNotification(context);
    }
    
    public static void cancelInternalMessageNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(ID_INTERNAL_MESSAGE);
    }
    
    public static void cancelRePullOrderNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(ID_RE_PULL_ORDER);
    }
}
