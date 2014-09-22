package com.jd.a6i.task;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONException;
import org.json.JSONObject;

import com.jd.a6i.common.ConstantUtils;
import com.jd.a6i.common.HttpClientUtils;
import com.jd.a6i.common.NetworkUtils;
import com.jd.a6i.common.PropertyUtil;
import com.jd.a6i.common.SharedPreferencesUtils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

public class SortingCenterInpectionWaybilInterceptAsyncTask extends
		AsyncTask<String, Integer, Boolean> {

	public static final int SORTINGCENTERINTERCEPT_ERROR = 6;
	public static final int SORTINGCENTERINTERCEPT_SUCCESS = 7;

	private Handler mHandler;
	private Context context;

	public SortingCenterInpectionWaybilInterceptAsyncTask(Context context,
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
		message.what = SORTINGCENTERINTERCEPT_ERROR;
		mHandler.sendMessage(message);
		return false;
	}

	private boolean inpection(String...params)throws JSONException{
		//拦截正向未操作退库订单(扫描包裹号时验证)
		int code = 0;
		String loginURI = PropertyUtil.getProperty(context).getAddressPort8080()+"sortingRet/haveSortingRet?packageCode="+params[0];
		String response = HttpClientUtils.requestResult(HttpGet.METHOD_NAME, loginURI, null);
		if(response==null){
			Message message = Message.obtain();
			message.what = SORTINGCENTERINTERCEPT_ERROR;
			mHandler.sendMessage(message);
			return false;
		}
		
		JSONObject result = new JSONObject(response);
		String errorMessage = null;
		if (result != null) {
			try {
				code = result.getInt("code");
				errorMessage = result.getString("message");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (code != 200) {				
				Message message = Message.obtain();
				message.what = SORTINGCENTERINTERCEPT_ERROR;
				message.obj = errorMessage;
				mHandler.sendMessage(message);
				return false;
			}
		}
		else{
			Message message = Message.obtain();
			message.what = SORTINGCENTERINTERCEPT_ERROR;
			mHandler.sendMessage(message);
			return false;
		}
		//外呼退库-验证申请状态(扫描包裹号时验证)
		loginURI = PropertyUtil.getProperty(context).getAddressPort8080()+"abnormalorder/query?orderId="+params[0]+"&type=2";
		response = HttpClientUtils.requestResult(HttpGet.METHOD_NAME, loginURI, null);
		if(response==null){
			Message message = Message.obtain();
			message.what = SORTINGCENTERINTERCEPT_ERROR;
			mHandler.sendMessage(message);
			return false;
		}
		result = new JSONObject(response);
		if(result != null){
			code = 0;
			try {
				code = result.getInt("code");
				errorMessage = result.getString("message");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(code != 200){
				Message message = Message.obtain();
				message.what = SORTINGCENTERINTERCEPT_ERROR;
				message.obj = errorMessage;
				mHandler.sendMessage(message);
				return false;
			}
		}else{
			Message message = Message.obtain();
			message.what = SORTINGCENTERINTERCEPT_ERROR;
			mHandler.sendMessage(message);
			return false;
		}
		
		//新快速退款验证 (扫描包裹号时验证，若调用此接口，则不必调用快速退款验证服务接口)
		int nserviceType = SharedPreferencesUtils
				.getKstkService(context);
		if (nserviceType > 0) {
			loginURI = PropertyUtil.getProperty(context).getAddressPort8080()+"fastrefundmq";
			response = HttpClientUtils.requestResult(HttpPost.METHOD_NAME, loginURI, params[1]);
		}
		else{
			loginURI = PropertyUtil.getProperty(context).getAddressPort8080()+"fastrefund?waybillCode="+params[0];
			response = HttpClientUtils.requestResult(HttpGet.METHOD_NAME, loginURI, params[1]);
		}
		if(response==null){
			Message message = Message.obtain();
			message.what = SORTINGCENTERINTERCEPT_ERROR;
			mHandler.sendMessage(message);
			return false;
		}
		result = new JSONObject(response);
		if(result != null){
			code = 0;
			try {
				code = result.getInt("code");
				errorMessage = result.getString("message");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(code != 200){
				Message message = Message.obtain();
				message.what = SORTINGCENTERINTERCEPT_ERROR;
				message.obj = errorMessage;
				mHandler.sendMessage(message);
				return false;
			}
		}
		else{
			Message message = Message.obtain();
			message.what = SORTINGCENTERINTERCEPT_ERROR;
			mHandler.sendMessage(message);
			return false;
		}
		
		return true;
	}
}
