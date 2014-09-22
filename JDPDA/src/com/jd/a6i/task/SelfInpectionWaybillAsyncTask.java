package com.jd.a6i.task;

import org.apache.http.client.methods.HttpGet;
import org.json.JSONException;
import org.json.JSONObject;

import com.jd.a6i.R;
import com.jd.a6i.common.ConstantUtils;
import com.jd.a6i.common.HttpClientUtils;
import com.jd.a6i.common.NetworkUtils;
import com.jd.a6i.common.PropertyUtil;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

public class SelfInpectionWaybillAsyncTask extends AsyncTask<String, Integer, Boolean> {
    public static final int SELFINPECTION_ERROR = 0;
    public static final int SELFINPECTION_SUCCESS =1;
    
	private Handler selfInpectionWaybillHandler;
	private Context context;
	
	public SelfInpectionWaybillAsyncTask(Context context,Handler selfInpectionWaybillHandler){
		this.context = context;
		this.selfInpectionWaybillHandler = selfInpectionWaybillHandler;
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
		message.what = SELFINPECTION_ERROR;
		selfInpectionWaybillHandler.sendMessage(message);
		return false;
	}
	
	private boolean inpection(String... params) throws JSONException {
		String loginURI = PropertyUtil.getProperty(context).getAddressPort8080()+"sortingRet/checkReDispatch?packageCode="+params[0];
		String response = HttpClientUtils.requestResult(HttpGet.METHOD_NAME, loginURI, null);
		if(response==null){
			Message message = Message.obtain();
			message.what = SELFINPECTION_ERROR;
			selfInpectionWaybillHandler.sendMessage(message);
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
				message.what = SELFINPECTION_SUCCESS;
				selfInpectionWaybillHandler.sendMessage(message);
				return true;
			} else {
				Message message = Message.obtain();
				message.what = SELFINPECTION_ERROR;
				message.obj = errorMessage;
				selfInpectionWaybillHandler.sendMessage(message);
			}
		}
		else{
			Message message = Message.obtain();
			message.what = SELFINPECTION_ERROR;
			selfInpectionWaybillHandler.sendMessage(message);
		}
		return false;
	}

}
