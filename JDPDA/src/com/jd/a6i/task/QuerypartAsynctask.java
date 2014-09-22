package com.jd.a6i.task;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONException;
import org.json.JSONObject;

import com.jd.a6i.common.ConstantUtils;
import com.jd.a6i.common.HttpClientUtils;
import com.jd.a6i.common.NetworkUtils;
import com.jd.a6i.common.PropertyUtil;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

public class QuerypartAsynctask extends AsyncTask<String, Integer, Boolean> {

	public static final int QUERYPART_ERROR = 0;
    public static final int QUERYPART_SUCCESS =1;
    
	private Handler mHandler;
	private Context context;
	
	public QuerypartAsynctask(Context context,Handler mHandler){
		this.context = context;
		this.mHandler = mHandler;
	}
	
	@Override
	protected Boolean doInBackground(String... params) {
		boolean isConnectNetwork = NetworkUtils.checkNetwork(context);
		if(isConnectNetwork){
			try {
				return query(params);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		Message message = Message.obtain();
		message.what = QUERYPART_ERROR;
		mHandler.sendMessage(message);
		return false;
	}

	private boolean query(String... params) throws JSONException {
		String loginURI = PropertyUtil.getProperty(context).getAddressPort8080()+"delivery/sortingdiff";
		String response = HttpClientUtils.requestResult(HttpPost.METHOD_NAME, loginURI, params[0]);
		if(response==null){
			Message message = Message.obtain();
			message.what = QUERYPART_ERROR;
			mHandler.sendMessage(message);
			return false;
		}
		JSONObject result=null;
		try {
			result = new JSONObject(response);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		if (result!=null) {
			int code = 0;
			String errorMessage = null;
			try {
				code = result.getInt("code");
				errorMessage = result.getString("message");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (code == 200||code==30005) {
				Message message = Message.obtain();
				message.what = QUERYPART_SUCCESS;
				message.obj = response;
				mHandler.sendMessage(message);
				return true;
			} else {
				Message message = Message.obtain();
				message.what = QUERYPART_ERROR;
				message.obj = errorMessage;
				mHandler.sendMessage(message);
			}
		}
		else{
			Message message = Message.obtain();
			message.what = QUERYPART_ERROR;
			mHandler.sendMessage(message);
		}
		return false;
	}
	
}
