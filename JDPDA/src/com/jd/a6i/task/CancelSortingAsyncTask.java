package com.jd.a6i.task;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.json.JSONException;
import org.json.JSONObject;

import com.jd.a6i.common.ConstantUtils;
import com.jd.a6i.common.HttpClientUtils;
import com.jd.a6i.common.NetworkUtils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import static com.jd.a6i.common.PropertyUtil.getProperty;

public class CancelSortingAsyncTask extends AsyncTask<String, Integer, Boolean> {

	public static final int CANCELSORTING_ERROR = 0;
    public static final int CANCELSORTING_SUCCESS =1;
    
	private Handler mHandler;
	private Context context;
	
	public CancelSortingAsyncTask(Context context,Handler mHandler){
		this.context = context;
		this.mHandler = mHandler;
	}
	
	@Override
	protected Boolean doInBackground(String... params) {
		boolean isConnectNetwork = NetworkUtils.checkNetwork(context);
		if(isConnectNetwork){
			try {
				return cancelSorting(params);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		Message message = Message.obtain();
		message.what = CANCELSORTING_ERROR;
		mHandler.sendMessage(message);
		return false;
	}
	
	private boolean cancelSorting(String... params) throws JSONException {
		//String loginURI = ConstantUtils.BASE_ADDRESS+"sorting/cancel";
		String loginURI = getProperty(context).getAddressPort8080()+"sorting/cancel";
		String response = HttpClientUtils.requestResult(HttpPut.METHOD_NAME, loginURI, params[0]);
		if(response==null){
			Message message = Message.obtain();
			message.what = CANCELSORTING_ERROR;
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
			if (code == 200) {
				Message message = Message.obtain();
				message.what = CANCELSORTING_SUCCESS;
				message.obj = response;
				mHandler.sendMessage(message);
				return true;
			} 
			else {
				Message message = Message.obtain();
				message.what = CANCELSORTING_ERROR;
				message.obj = errorMessage;
				mHandler.sendMessage(message);
			}
		}
		else{
			Message message = Message.obtain();
			message.what = CANCELSORTING_ERROR;
			mHandler.sendMessage(message);
		}
		return false;
	}

}
