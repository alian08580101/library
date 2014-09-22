package com.jd.a6i.task;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.reflect.TypeToken;
import com.jd.a6i.common.ConstantUtils;
import com.jd.a6i.common.HttpClientUtils;
import com.jd.a6i.common.NetworkUtils;
import com.jd.a6i.common.PropertyUtil;
import com.jd.a6i.db.pojo.Account;
import com.jd.a6i.db.pojo.StationInfo;
import com.jd.a6i.common.*;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

public class ScanSitecodeAsynTask extends AsyncTask<String, Integer, Boolean> {

    public static final int SCANSITECODE_ERROR = 2;
    public static final int SCANSITECODE_SUCCESS =3;
    
	private Handler mHandler;
	private Context context;
	
	public ScanSitecodeAsynTask(Context context,Handler mHandler){
		this.context = context;
		this.mHandler = mHandler;
	}
	
	@Override
	protected Boolean doInBackground(String... params) {
		boolean isConnectNetwork = NetworkUtils.checkNetwork(context);
		if(isConnectNetwork){
			try {
				return scanSitecode(params);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		Message message = Message.obtain();
		message.what = SCANSITECODE_ERROR;
		mHandler.sendMessage(message);
		return false;
	}
	
	private boolean scanSitecode(String... params) throws JSONException {
		String scanSitecodeURI = PropertyUtil.getProperty(context).getAddressPort8080()+"bases/site/"+params[0];
		String response = HttpClientUtils.requestResult(HttpGet.METHOD_NAME, scanSitecodeURI, null);
		if(response==null){
			Message message = Message.obtain();
			message.what = SCANSITECODE_ERROR;
			mHandler.sendMessage(message);
			return false;
		}
		JSONObject result = new JSONObject(response);
		if(result!=null){
			int code =0;
			String errorMessage = null;
			try {
				code = result.getInt("code");
				errorMessage = result.getString("message");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(code==200){
				StationInfo scanSiteCode = JsonConverter.convertJsonToObject(result.toString(), TypeToken.get(StationInfo.class));
				Message message = Message.obtain();
				message.what = SCANSITECODE_SUCCESS;
				message.obj = scanSiteCode;
				mHandler.sendMessage(message);
				return true;
			}
			else{
				Message message = Message.obtain();
				message.what = SCANSITECODE_ERROR;
				message.obj = errorMessage;
				mHandler.sendMessage(message);
			}
		}
		else{
			Message message = Message.obtain();
			message.what = SCANSITECODE_ERROR;
			mHandler.sendMessage(message);
		}
			
		
		return false;
	}

}
