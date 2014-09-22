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

public class ThreeSideInpectionNewWaybillAsyncTask extends
		AsyncTask<String, Integer, Boolean> {

	public static final int THREEINPECTION_ERROR = 4;
	public static final int THREEINPECTION_SUCCESS = 5;

	private Handler mHandler;
	private Context context;

	public ThreeSideInpectionNewWaybillAsyncTask(Context context,
			Handler mHandler) {
		this.context = context;
		this.mHandler = mHandler;
	}
		
	@Override
	protected Boolean doInBackground(String... params) {
		boolean isConnectNetwork = NetworkUtils.checkNetwork(context);
		if(isConnectNetwork){
			try {
				return inpection(params);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		Message message = Message.obtain();
		message.what = THREEINPECTION_ERROR;
		mHandler.sendMessage(message);
		return false;
	}
	
	private boolean inpection(String... params)throws JSONException {
		String loginURI = PropertyUtil.getProperty(context).getAddressPort8080()+"fastrefundmq";
		String response = HttpClientUtils.requestResult(HttpPost.METHOD_NAME, loginURI, params[0]);
		if(response==null){
			Message message = Message.obtain();
			message.what = THREEINPECTION_ERROR;
			mHandler.sendMessage(message);
			return false;
		}
		JSONObject result = new JSONObject(response);
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
			if (code == 200) {
				Message message = Message.obtain();
				message.what = THREEINPECTION_SUCCESS;
				mHandler.sendMessage(message);
				return true;
			} else {
				Message message = Message.obtain();
				message.what = THREEINPECTION_ERROR;
				message.obj = errorMessage;
				mHandler.sendMessage(message);
			}
		}
		else{
			Message message = Message.obtain();
			message.what = THREEINPECTION_ERROR;
			mHandler.sendMessage(message);
		}
		return false;
	}

}
