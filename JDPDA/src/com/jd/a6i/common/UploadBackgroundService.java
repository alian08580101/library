package com.jd.a6i.common;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;

import com.jd.a6i.JDApplication;
import com.jd.a6i.task.BackgroundTaskBase;
import com.jd.a6i.task.UploadBackgroundTask;


public class UploadBackgroundService extends Service {
	public static final String TAG = "UploadBackgroundService";
	private volatile Looper looper;
	private volatile TaskHandler taskHandler;
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		HandlerThread handlerThread = new HandlerThread(TAG);
		handlerThread.start();
		looper = handlerThread.getLooper();
		taskHandler = new TaskHandler(looper);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Class<?> taskClass = (Class<?>) intent.getSerializableExtra(ConstantUtils.ACTION_UPLOAD_TASK);
		try {
			UploadBackgroundTask uploadBackgroundTask = (UploadBackgroundTask) taskClass.getDeclaredConstructor(Context.class).newInstance(getApplicationContext());
			executeTask(uploadBackgroundTask, startId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return START_REDELIVER_INTENT;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		looper.quit();
	}
	
	
	private void executeTask(UploadBackgroundTask uploadBackgroundTask, int startId) {
        Message msg = taskHandler.obtainMessage();
        msg.arg1 = startId;
        msg.obj = uploadBackgroundTask;
        taskHandler.sendMessageAtFrontOfQueue(msg);
    }
	
	private final class TaskHandler extends Handler {
		public TaskHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            UploadBackgroundTask uploadBackgroundTask = (UploadBackgroundTask) msg.obj;
            uploadBackgroundTask.execute();
        }
    }
	
	public class UploadBinder extends Binder{
		public UploadBackgroundService getService(){
			return UploadBackgroundService.this;
		}
	}
	public static void submitTask(Class<? extends BackgroundTaskBase> backgroundTask) {
		Context context = JDApplication.getInstance();
		Intent intent = new Intent(context, UploadBackgroundService.class);
		intent.putExtra(ConstantUtils.ACTION_UPLOAD_TASK, backgroundTask);
		
		context.startService(intent);
	}
}
