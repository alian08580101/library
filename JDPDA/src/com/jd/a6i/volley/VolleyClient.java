package com.jd.a6i.volley;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.OnRequestQueueListener;
import com.android.volley.toolbox.Volley;
import com.jd.a6i.R;
import com.jd.a6i.common.NetworkUtils;


public class VolleyClient{
	
	private static RequestQueue requestQueue;
	
	private static VolleyClient instance;
	
	private OnRequestQueueListener onRequestQueueListener;
	private Context context;

	private VolleyClient(Context context){
		this.context = context;
		if(requestQueue==null){
			requestQueue = getRequestQueue(context);
		}
	}
	
	public static VolleyClient getVolleyClient(Context context){
		if(instance==null){
			instance = new VolleyClient(context);
		}
		return instance;
	}
	
	private RequestQueue getRequestQueue(Context context){
		requestQueue = Volley.newRequestQueue(context);
		return requestQueue;
	}
	
	public void addRequest(BaseRequest<?> request){
		if(request!=null){
			requestQueue.add(request);
		}
	}
	
	public void startRequest(){
		boolean isConnectNetwork = NetworkUtils.checkNetwork(context);
		if(isConnectNetwork){
			if(requestQueue.getSequenceNumber()>0){
				requestQueue.start();
			}
		}else{
			Toast.makeText(context, R.string.networkError, Toast.LENGTH_SHORT).show();
		}
	}
	
	public void stopRequest(){
		requestQueue.stop();
	}
	
	public void setOnRequestQueueListener(OnRequestQueueListener onRequestQueueListener){
		requestQueue.setOnRequestQueueListener(onRequestQueueListener);
	}
	
	public OnRequestQueueListener getOnreOnRequestQueueListener(){
		return onRequestQueueListener;
	}
}
