package com.jd.a6i.task;

import org.apache.http.client.methods.HttpGet;
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

public class SortingCenterInpectionWaybillCalloutAsyncTask extends
		AsyncTask<String, Integer, Boolean> {

	public static final int SORTINGCENTERCALLOUT_ERROR = 8;
    public static final int SORTINGCENTERCALLOUT_SUCCESS =9;
    
	private Handler mHandler;
	private Context context;
	
	public SortingCenterInpectionWaybillCalloutAsyncTask(Context context,Handler mHandler){
		this.context = context;
		this.mHandler =mHandler;
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
		message.what = SORTINGCENTERCALLOUT_ERROR;
		mHandler.sendMessage(message);
		return false;
	}

	private boolean inpection(String... params)throws JSONException {
		String loginURI = PropertyUtil.getProperty(context).getAddressPort8080()+"abnormalorder/query?orderId="+params[0]+"&type=2";
		String response = HttpClientUtils.requestResult(HttpGet.METHOD_NAME, loginURI, null);
		if(response==null){
			Message message = Message.obtain();
			message.what = SORTINGCENTERCALLOUT_ERROR;
			mHandler.sendMessage(message);
			return false;
		}
		JSONObject result = new JSONObject(response);
		if (result !=null) {
			int code = 0;
			try {
				code = result.getInt("code");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (code == 200) {
				Message message = Message.obtain();
				message.what = SORTINGCENTERCALLOUT_SUCCESS;
				mHandler.sendMessage(message);
				return true;
			} else {
				Message message = Message.obtain();
				message.what = SORTINGCENTERCALLOUT_ERROR;
				mHandler.sendMessage(message);
			}
		}
		else{
			Message message = Message.obtain();
			message.what = SORTINGCENTERCALLOUT_ERROR;
			mHandler.sendMessage(message);
		}
		return false;
	}
	
}
