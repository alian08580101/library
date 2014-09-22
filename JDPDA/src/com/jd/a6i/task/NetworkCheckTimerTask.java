package com.jd.a6i.task;

import java.util.TimerTask;

import com.jd.a6i.common.NetworkUtils;

import android.content.Context;
import android.util.Log;

public class NetworkCheckTimerTask extends TimerTask {
	public static final String TAG = "NetworkCheckTimerTask";
	private Context context;
	private boolean isConnect = false;
	public NetworkCheckTimerTask(Context context){
		this.context = context;
	}
	
	@Override
	public void run() {
		setConnect(NetworkUtils.checkNetwork(context));
		Log.d(TAG, "--"+isConnect);
	}

	public boolean isConnect() {
		return isConnect;
	}

	public void setConnect(boolean isConnect) {
		this.isConnect = isConnect;
	}
}
