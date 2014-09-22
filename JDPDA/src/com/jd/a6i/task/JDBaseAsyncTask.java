package com.jd.a6i.task;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

public abstract class JDBaseAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
	public static final String BASE_URI = "http://192.168.226.157:8080/services/";
	public static final String CODE = "code";
	public static final int CODE_200 = 200;
	
	public static final int NETWORK_ERROR = 0;
	public static final int CODE_ERROR = 1;
	
	private boolean isFinish = false;
	
	public Context context;
	public Handler handler;
	
	private String subURI = null;

	public String getSubURI() {
		return subURI;
	}

	public void setSubURI(String subURI) {
		this.subURI = subURI;
	}

	public boolean isFinish() {
		return isFinish;
	}

	public void setFinish(boolean isFinish) {
		this.isFinish = isFinish;
	}
	
	public void baseToast(int stringId){
		Toast.makeText(context, context.getResources().getString(stringId), Toast.LENGTH_SHORT).show();
	}
}
