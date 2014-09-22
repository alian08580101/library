package com.jd.a6i;

import static com.jd.a6i.common.PropertyUtil.getProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.OnRequestQueueListener;
import com.android.volley.toolbox.OnRequestQueueListener.Process;
import com.jd.a6i.common.AlarmManagerBroadcastReceiver;
import com.jd.a6i.common.ConstantUtils;
import com.jd.a6i.common.DESEncrypt;
import com.jd.a6i.common.SharedPreferencesUtils;
import com.jd.a6i.db.pojo.Account;
import com.jd.a6i.db.service.AccountService;
import com.jd.a6i.view.DropDownView;
import com.jd.a6i.view.DropDownView.OnDropDownItemListener;
import com.jd.a6i.view.JDProgressDialog;
import com.jd.a6i.view.PasswordShow;
import com.jd.a6i.volley.AccountRequest;
import com.jd.a6i.volley.ErrorTypeRequest;
import com.jd.a6i.volley.VolleyAsyncTask;
import com.whl.dao.common.DaoException;

public class LoginActivity extends Activity implements 
OnClickListener,OnCheckedChangeListener,OnDropDownItemListener{
	public static final String TAG = "LoginActivity";

	private DropDownView usernameDropDownView = null;
	private PasswordShow passwordShow = null;
	private EditText editUsername = null;
	private EditText editPassword = null;
	private Button buttonLogin = null;
	private Button buttonCancel = null;
	private CheckBox rememberUsername = null;
	private CheckBox rememberPassword = null;
	private TextView textVersionNo;
	
	private List<Account> accountList;
	private DESEncrypt desEncrypt;
	private JDProgressDialog jdProgressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		usernameDropDownView = (DropDownView) findViewById(R.id.usernameDropDown);
		passwordShow = (PasswordShow) findViewById(R.id.passwordShow);

		usernameDropDownView.setChecked(true);
		usernameDropDownView.setDropList(erpAccountList());
		usernameDropDownView.setOnDropDownItemListener(this);
		editUsername = (EditText) usernameDropDownView.findViewById(R.id.editText);
		editUsername.requestFocus();
		
		editPassword = (EditText) passwordShow.findViewById(R.id.editText);
		buttonLogin = (Button) findViewById(R.id.buttonLogin);
		buttonCancel = (Button)findViewById(R.id.buttonCancel);

		rememberUsername = (CheckBox) findViewById(R.id.rememberUsername);
		rememberPassword = (CheckBox) findViewById(R.id.rememberPassword);
		textVersionNo = (TextView) findViewById(R.id.textVersionNo);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		JDApplication.getInstance().setLogin(true);
		UncaughtExceptionHandlerManager.deletePastFile(UncaughtExceptionHandlerManager.CRASH_SAVE_DIRECTORY);
		checkBoxStatus();
		setVersion();	
				
		buttonLogin.setOnClickListener(this);
		buttonCancel.setOnClickListener(this);
		rememberUsername.setOnCheckedChangeListener(this);
		rememberPassword.setOnCheckedChangeListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.buttonLogin:
			login();
			break;
		case R.id.buttonCancel:
			this.finish();
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		checkBoxAction(buttonView);
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
	}
	
	@Override
	public void finish() {
		super.finish();
		System.exit(0);
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		JDApplication.getInstance().setLogin(false);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		AlarmManagerBroadcastReceiver.cancelAlarm(getApplicationContext());
		JDApplication.getInstance().setLogin(false);
	}
	
	@Override
	public boolean onClickItem(View view, int position, String itemContent) {
		if(desEncrypt==null){
			desEncrypt = new DESEncrypt(getProperty(this).getDesKey());
		}
		Account account  = accountList.get(position);
		if(account.getRememberPassword()==ConstantUtils.REMEMBER_PASSWORD_1){
			String password = desEncrypt.decrypt(account.getPassword());
			editPassword.setText(password);
		}
		return false;
	}
	
	
	protected void checkBoxAction(CompoundButton buttonView){
		switch(buttonView.getId()){
		case R.id.rememberUsername:
			String erpAccount = editUsername.getText().toString();
			if("".equals(erpAccount)||null==erpAccount){
				loginToast(R.string.nullErpAccount);
				rememberUsername.setChecked(false);
				return;
			}
			SharedPreferencesUtils.putRememberUsernameCheck(this, rememberUsername.isChecked());
			break;
		case R.id.rememberPassword:
			String password = editPassword.getText().toString();
			if(!rememberUsername.isChecked()){
				loginToast(R.string.checkErpAccount);
				rememberPassword.setChecked(false);
				return;
			}else if("".equals(password)||null==password){
				loginToast(R.string.nullPassword);
				rememberPassword.setChecked(false);
				return;
			}
			SharedPreferencesUtils.putRememberPasswordCheck(this, rememberPassword.isChecked());
			break;
		}
		
	}
	
	protected void checkBoxStatus(){
		if(SharedPreferencesUtils.getRememberUsernameCheck(this)){
			rememberUsername.setChecked(true);
		}
		if(SharedPreferencesUtils.getRememberPasswordCheck(this)){
			rememberPassword.setChecked(true);
		}
	}
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what==Process.REQUEST_FINISH){
				onFinishRequest();
			}
		}
		
	};
	public void login(){
		String erpAccount = editUsername.getText().toString();
		String password = editPassword.getText().toString();
		if("".equals(erpAccount)||null==erpAccount){
			loginToast(R.string.nullErpAccount);
			rememberUsername.setChecked(false);
			rememberPassword.setChecked(false);
			return;
		}else if("".equals(password)||null==password){
			loginToast(R.string.nullPassword);
			rememberUsername.setChecked(false);
			rememberPassword.setChecked(false);
			return;
		}else{
			onStartRequest();
			Map<String,Object> paramsMap = new HashMap<String,Object>();
			paramsMap.put("erpAccount", erpAccount);
			paramsMap.put("password", password);
			AccountRequest accountRequest = new AccountRequest(Request.Method.POST, getProperty(this).getLoginUrl(), paramsMap);
			ErrorTypeRequest errorListRequest = new ErrorTypeRequest(Request.Method.GET,getProperty(this).getErrorTypeUrl(),null);
			VolleyAsyncTask volleyAsyncTask = new VolleyAsyncTask(this, handler);
			volleyAsyncTask.execute(accountRequest,errorListRequest);
		}
	}
	
	public void loginToast(int stringId){
		Toast.makeText(getApplicationContext(), getResources().getString(stringId), Toast.LENGTH_SHORT).show();
	}
	
	private void setVersion(){
		PackageManager packageManager = this.getPackageManager();
		try {
			PackageInfo packageInfo = packageManager.getPackageInfo(this.getPackageName(), 0);
			String versionName = packageInfo.versionName;
			textVersionNo.setText(getResources().getString(R.string.version)+versionName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private List<String> erpAccountList(){
		AccountService accountService = AccountService.getInstance(this);
		try {
			accountList = accountService.getAll();
			if(accountList!=null){
				List<String> erpAccountList = new ArrayList<String>();
				for (Account account : accountList) {
					erpAccountList.add(account.getErpAccount());
				}
				return erpAccountList;
			}
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void onStartRequest() {
		jdProgressDialog = JDProgressDialog.getJDProgressDialog(this, ProgressDialog.STYLE_SPINNER);
		jdProgressDialog.show();
		jdProgressDialog.setDialogText(R.string.loginPrompt);
	}

	public void onFinishRequest() {
		if(jdProgressDialog!=null){
		   jdProgressDialog.dismiss();
		   jdProgressDialog.cancel();
		   jdProgressDialog=null;
		}
		gotoMain();
	}
	protected void gotoMain(){
		JDApplication.getInstance().setLogin(true);
		Intent loginIntent = new Intent(this,MainActivity.class);
		loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		this.startActivity(loginIntent);
	}
}
