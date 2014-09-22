package com.jd.a6i.volley;

import static com.jd.a6i.common.PropertyUtil.getProperty;

import java.util.Map;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.jd.a6i.JDApplication;
import com.jd.a6i.MainActivity;
import com.jd.a6i.R;
import com.jd.a6i.common.AlarmManagerBroadcastReceiver;
import com.jd.a6i.common.ConstantUtils;
import com.jd.a6i.common.DESEncrypt;
import com.jd.a6i.common.PromptToast;
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

public class AccountRequest extends BaseRequest<Account> {
	private static Handler accountHandler = new Handler(){
		private JDApplication applicationContext = JDApplication.getInstance();
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
			case REQUEST_SUCCESS:
				if(msg.obj!=null){
					Account account = (Account) msg.obj;
					if(account.getCode()==200){
						checkBoxStatus(account);
						applicationContext.setAccount(account);
						deleteDatabase();
						AlarmManagerBroadcastReceiver.enableAlarm(applicationContext);
						//gotoMain();
					}else{
						PromptToast.prompt(R.string.errorCode, account.getCode(),account.getMessage());
					}
				}
				break;
			case REQUEST_FAILURE:
				PromptToast.prompt(msg.obj.toString());
				break;
			}
		}
		
		public void checkBoxStatus(Account account){
			boolean rememberUsername = SharedPreferencesUtils.getRememberUsernameCheck(applicationContext);
			boolean rememberPassword = SharedPreferencesUtils.getRememberPasswordCheck(applicationContext);
			if(rememberUsername){
				if(rememberPassword){
					account.setRememberPassword(ConstantUtils.REMEMBER_PASSWORD_1);
				}
				saveAccount(account);
			}
			
		}
		
		public boolean saveAccount(Account account){
			DESEncrypt desEncrypt = new DESEncrypt(getProperty(applicationContext).getDesKey());
			String encryptPassword = desEncrypt.encrypt(account.getPassword());
			account.setPassword(encryptPassword);
			AccountService accountService = AccountService.getInstance(applicationContext);
			Account resultAccount = accountService.findUniqueAccount(account.getErpAccount());
			try {
				if(resultAccount!=null){
					accountService.deleteUnique(resultAccount);
				}
				return accountService.save(account)==1?true:false;
			} catch (DaoException e) {
				e.printStackTrace();
			}finally{
				accountService = null;
			}
			return false;
		}
		
		protected void gotoMain(){
			Intent loginIntent = new Intent(applicationContext,MainActivity.class);
			loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			applicationContext.startActivity(loginIntent);
		}
		
		private void deleteDatabase(){
			deleteSortingTally();
			deleteDelivery();
			deleteSealBoxError();
			deleteSealCarError();
			deleteSealBoxInfo();
			deleteInspectionInfo();
		}
		
		private boolean deleteSortingTally(){
			SortingTallyService sortingTallyService = SortingTallyService.getInstance(applicationContext);
			int result = sortingTallyService.deleteSortingTally(ConstantUtils.UPLOAD_1, getProperty(applicationContext).getDeleteIntervalHours());
			return result==1?true:false;
		}
		
		private boolean deleteDelivery(){
			DeliveryService deliveryService = DeliveryService.getInstance(applicationContext);
			int result = deliveryService.deleteSortingTally(ConstantUtils.UPLOAD_1, getProperty(applicationContext).getDeleteIntervalHours());
			return result==1?true:false;
		}
		
		private boolean deleteSealBoxError(){
			SealBoxErrorService sealBoxErrorService = SealBoxErrorService.getInstance(applicationContext);
			int result = sealBoxErrorService.deleteSealBoxError(ConstantUtils.UPLOAD_1, getProperty(applicationContext).getDeleteIntervalHours());
			return result==1?true:false;
		}
		
		private boolean deleteSealCarError(){
			SealCarErrorService sealCarErrorService = SealCarErrorService.getInstance(applicationContext);
			int result = sealCarErrorService.deleteSealCarError(ConstantUtils.UPLOAD_1, getProperty(applicationContext).getDeleteIntervalHours());
			return result==1?true:false;
		}
		
		private boolean deleteSealBoxInfo(){
			SealBoxInfoService sealBoxInfoService = SealBoxInfoService.getInstance(applicationContext);
			int result = sealBoxInfoService.deleteSealBoxInfo(ConstantUtils.UPLOAD_1, getProperty(applicationContext).getDeleteIntervalHours());
			return result==1?true:false;
		}
		
		private boolean deleteInspectionInfo(){
			int result = InspectionService.getInstance(applicationContext).deleteInspection(String.valueOf(PropertyUtil.getProperty(applicationContext).getDeleteIntervalHours()),String.valueOf(ConstantUtils.UPLOAD_1));
			return result==1?true:false;
		}
	};
	
	
	public AccountRequest(int method, String url, Map<String, Object> paramsMap,int...args) {
		super(method, url, accountHandler, paramsMap,args);
	}
}
