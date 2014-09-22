package com.jd.a6i.volley;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.OnRequestQueueListener;
import com.android.volley.toolbox.Volley;

public class VolleyAsyncTask extends AsyncTask<Request<?>, Integer, Void> implements OnRequestQueueListener{
	public static final String TAG = "VolleyAsyncTask";
	//public static final int REQUEST_FINISH = 0;
	
	private RequestQueue requestQueue;
	private Handler handler;
	public VolleyAsyncTask(Context context,Handler handler){
		requestQueue = Volley.newRequestQueue(context);
		requestQueue.setOnRequestQueueListener(this);
		this.handler = handler;
	}
	@Override
	protected Void doInBackground(Request<?>... params) {
		for(Request<?> request:params){
			requestQueue.add(request);
		}
		requestQueue.start();
		return null;
	}
	
	@Override
	public void onRequest(int process) {
		if(process==Process.REQUEST_FINISH){
			if(handler!=null){
				Message message = handler.obtainMessage(Process.REQUEST_FINISH);
				handler.sendMessage(message);
			}
		}
	}
}
