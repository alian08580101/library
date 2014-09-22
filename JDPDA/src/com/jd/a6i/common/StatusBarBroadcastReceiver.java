package com.jd.a6i.common;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import com.jd.a6i.JDApplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StatusBarBroadcastReceiver extends BroadcastReceiver {
    public static final String KEY_TYPE_STATUS_BROADCAST = "status_broadcast_type";
    public static final String ACTION_REFRESH_STATUS_BAR = "com.jd.action.refresh.status.bar";
    public static final String KEY_VALUE_BUSINESS = "value";
    public static final String KEY_UPLOAD_COUNT = "count";
    public static final String KEY_TYPE_CONTENT = "typeContent";
    public static final String KEY_UPLOAD_TYPE = "uploadType";
    public static final String KEY_UPLOAD_JSON_LIST = "jsonList";
    public static final String KEY_UPLOAD_CLASS = "clazz";
    @Override
    public void onReceive(Context context, Intent intent) {
        StatusBarNotifyType statusBarNotifyType = (StatusBarNotifyType) intent.getExtras().get(KEY_TYPE_STATUS_BROADCAST);
        if (statusBarNotifyType == StatusBarNotifyType.PENDING_COUNT_CHANGED) {
            int count = intent.getIntExtra(KEY_UPLOAD_COUNT, 0);
            String typeContent = intent.getStringExtra(KEY_TYPE_CONTENT);
            if (count > 0) {
            	NotificationHelper.sendPendingCountChangedNotification(context, typeContent, count);
            	//NotificationHelper.sendPendingCountChangedNotification(context, intent);
            } else {
            	NotificationHelper.cancelPendingCountNotification(JDApplication.getInstance());
            }
        } 
    }

    public static void sendBroadcastForUploadPendingCount(Context context, String typeContent, int count) {
    	Intent intent = new Intent();
        intent.setAction(ACTION_REFRESH_STATUS_BAR);
        intent.putExtra(KEY_TYPE_STATUS_BROADCAST, StatusBarNotifyType.PENDING_COUNT_CHANGED);
        intent.putExtra(KEY_UPLOAD_COUNT, count);
        intent.putExtra(KEY_TYPE_CONTENT, typeContent);
        context.sendBroadcast(intent);
    }
    
    public static void sendBroadcastForUploadPendingCount(Context context, String typeContent, int uploadType, String jsonList,Class<?> clazz) {
    	JDApplication.getInstance().setJsonList(jsonList);
    	
    	List<?> list = JsonConverter.convertObjectListFromJson(jsonList,clazz);
    	Intent intent = new Intent();
        intent.setAction(ACTION_REFRESH_STATUS_BAR);
        intent.putExtra(KEY_TYPE_STATUS_BROADCAST, StatusBarNotifyType.PENDING_COUNT_CHANGED);
        intent.putExtra(KEY_UPLOAD_JSON_LIST, jsonList);
        intent.putExtra(KEY_UPLOAD_COUNT, list.size());
        intent.putExtra(KEY_TYPE_CONTENT, typeContent);
        intent.putExtra(KEY_UPLOAD_TYPE, uploadType);
        intent.putExtra(KEY_UPLOAD_CLASS, clazz);
        context.sendBroadcast(intent);
    }

    public static enum StatusBarNotifyType implements Serializable {
        INTERNAL_MESSAGE, PENDING_COUNT_CHANGED, RE_PULLING_ORDER_TIP
    }
}