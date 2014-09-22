package com.jd.a6i.task;

import org.apache.http.client.methods.HttpGet;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.reflect.TypeToken;
import com.jd.a6i.common.ConstantUtils;
import com.jd.a6i.common.HttpClientUtils;
import com.jd.a6i.common.JsonConverter;
import com.jd.a6i.common.NetworkUtils;
import com.jd.a6i.common.PropertyUtil;
import com.jd.a6i.db.pojo.BoxQuery;
import com.jd.a6i.db.pojo.SealCar;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

public class QueryPackageBoxAsyncTask extends
		AsyncTask<String, Integer, Boolean> {

	public static final int QUERYPACKAGEBOX_ERROR = 0;
    public static final int QUERYPACKAGEBOX_SUCCESS =1;
    
	private Handler mHandler;
	private Context context;
	
	public QueryPackageBoxAsyncTask(Context context,Handler mHandler){
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
		message.what = QUERYPACKAGEBOX_ERROR;
		mHandler.sendMessage(message);
		return false;
	}

	private boolean query(String... params) throws JSONException {
		String loginURI = PropertyUtil.getProperty(context).getAddressPort8080()+"boxPackList/"+params[0]+"/"+params[1]+"/"+params[2];
		String response = HttpClientUtils.requestResult(HttpGet.METHOD_NAME, loginURI, null);
		if(response==null){
			Message message = Message.obtain();
			message.what = QUERYPACKAGEBOX_ERROR;
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
				message.what = QUERYPACKAGEBOX_SUCCESS;
				message.obj = response;
				mHandler.sendMessage(message);
				return true;
			} else {
				Message message = Message.obtain();
				message.what = QUERYPACKAGEBOX_ERROR;
				message.obj = errorMessage;
				mHandler.sendMessage(message);
			}
		}
		else{
			Message message = Message.obtain();
			message.what = QUERYPACKAGEBOX_ERROR;
			mHandler.sendMessage(message);
		}
		return false;
	}
}
