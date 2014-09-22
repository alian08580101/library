package com.jd.a6i.task;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.HttpPost;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.jd.a6i.JDApplication;
import com.jd.a6i.MainActivity;
import com.jd.a6i.common.ConstantUtils;
import com.jd.a6i.common.DESEncrypt;
import com.jd.a6i.common.HttpClientUtils;
import com.jd.a6i.common.JsonConverter;
import com.jd.a6i.common.NetworkUtils;
import com.jd.a6i.common.PropertyUtil;
import com.jd.a6i.common.SharedPreferencesUtils;
import com.jd.a6i.db.pojo.Account;
import com.jd.a6i.db.service.AccountService;
import com.jd.a6i.db.service.DeliveryService;
import com.jd.a6i.db.service.InspectionService;
import com.jd.a6i.db.service.SealBoxErrorService;
import com.jd.a6i.db.service.SealBoxInfoService;
import com.jd.a6i.db.service.SealCarErrorService;
import com.jd.a6i.db.service.SortingTallyService;
import com.whl.dao.common.DaoException;

import static com.jd.a6i.common.PropertyUtil.getProperty;;

public class LoginAsyncTask extends JDBaseAsyncTask<String, Integer, Boolean> {
	private static final String TAG = "LoginAsyncTask";
	
	public static final int LOGIN_SUCCESS = 2;
	private int loginCount = 0;
	
	public LoginAsyncTask(Context context,Handler handler){
		this.context = context;
		this.handler = handler;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		deleteDatabase();
	}
	
	@Override
	protected Boolean doInBackground(String... params) {
		boolean isConnectNetwork = NetworkUtils.checkNetwork(context);
		if(isConnectNetwork){
			try {
				return loginStatus(params);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		Message message = Message.obtain();
		message.what = NETWORK_ERROR;
		handler.sendMessage(message);
		return false;
	}
	

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if(result){
			Message message = Message.obtain();
			message.what = LOGIN_SUCCESS;
			handler.sendMessage(message);
			gotoMain();
		}else{
			Message message = Message.obtain();
			message.what = CODE_ERROR;
			handler.sendMessage(message);
		}
	}
	
	private void deleteDatabase(){
		deleteSortingTally();
		deleteDelivery();
		deleteSealBoxError();
		deleteSealCarError();
		deleteSealBoxInfo();
		deleteInspectionInfo();
	}
	
	private Boolean loginStatus(String...params) throws JSONException {
		loginCount++;
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("erpAccount", params[0]);
		paramsMap.put("password", params[1]);
		String loginURI = getProperty(context).getAddressPort8080()+"bases/login";
		String response = HttpClientUtils.requestResult(HttpPost.METHOD_NAME, loginURI, JsonConverter.convertMapToJson(paramsMap));
		if(response!=null){
			JSONObject result = new JSONObject(response);
			int code = result.getInt("code");
			if(code==CODE_200){
				Account account = JsonConverter.convertJsonToObject(result.toString(), TypeToken.get(Account.class));
				//SharedPreferencesUtils.putPassword(context, account.getPassword());
				DESEncrypt desEncrypt = new DESEncrypt(ConstantUtils.DES_KEY);
				String encryptPassword = desEncrypt.encrypt(account.getPassword());
				account.setPassword(encryptPassword);
				saveAccount(account);
				JDApplication jdApplication = JDApplication.getInstance();
				jdApplication.setAccount(account);
				//SharedPreferencesUtils.putErpAccount(context, account.getErpAccount());
				Log.d(TAG, account.toString());
				return true;
			}else{
				if(loginCount<3){
					loginStatus(params);
				}else{
					loginCount = 0;
					Message message = Message.obtain();
					message.what = CODE_ERROR;
					handler.sendMessage(message);
				}
			}
		}
		else{
			Message message = Message.obtain();
			message.what = CODE_ERROR;
			handler.sendMessage(message);
		}
		return false;
	}

	public void saveAccount(Account account){
		AccountService accountService = AccountService.getInstance(context);
		Account resultAccount = accountService.findUniqueAccount(account.getErpAccount());
		try {
			if(resultAccount!=null){
				accountService.deleteUnique(resultAccount);
			}
			accountService.save(account);
		} catch (DaoException e) {
			e.printStackTrace();
		}finally{
			accountService = null;
		}
	}
	
	protected void gotoMain(){
		Intent loginIntent = new Intent(context,MainActivity.class);
		loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(loginIntent);
	}
	
	private boolean deleteSortingTally(){
		SortingTallyService sortingTallyService = SortingTallyService.getInstance(context);
		int result = sortingTallyService.deleteSortingTally(ConstantUtils.UPLOAD_1, PropertyUtil.getProperty(context).getDeleteIntervalHours());
		return result==1?true:false;
	}
	
	private boolean deleteDelivery(){
		DeliveryService deliveryService = DeliveryService.getInstance(context);
		int result = deliveryService.deleteSortingTally(ConstantUtils.UPLOAD_1, PropertyUtil.getProperty(context).getDeleteIntervalHours());
		return result==1?true:false;
	}
	
	private boolean deleteSealBoxError(){
		SealBoxErrorService sealBoxErrorService = SealBoxErrorService.getInstance(context);
		int result = sealBoxErrorService.deleteSealBoxError(ConstantUtils.UPLOAD_1, PropertyUtil.getProperty(context).getDeleteIntervalHours());
		return result==1?true:false;
	}
	
	private boolean deleteSealCarError(){
		SealCarErrorService sealCarErrorService = SealCarErrorService.getInstance(context);
		int result = sealCarErrorService.deleteSealCarError(ConstantUtils.UPLOAD_1, PropertyUtil.getProperty(context).getDeleteIntervalHours());
		return result==1?true:false;
	}
	
	private boolean deleteSealBoxInfo(){
		SealBoxInfoService sealBoxInfoService = SealBoxInfoService.getInstance(context);
		int result = sealBoxInfoService.deleteSealBoxInfo(ConstantUtils.UPLOAD_1, PropertyUtil.getProperty(context).getDeleteIntervalHours());
		return result==1?true:false;
	}
	
	private boolean deleteInspectionInfo(){
		int result = InspectionService.getInstance(context).deleteInspection(String.valueOf(PropertyUtil.getProperty(context).getDeleteIntervalHours()),String.valueOf(ConstantUtils.UPLOAD_1));
		return result==1?true:false;
	}
}
