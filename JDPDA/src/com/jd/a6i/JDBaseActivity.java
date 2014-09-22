package com.jd.a6i;

import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jd.a6i.adapter.MenuItemAdapter;
import com.jd.a6i.common.AlarmManagerBroadcastReceiver;
import com.jd.a6i.common.SharedPreferencesUtils;
import com.jd.a6i.common.PromptManager;
import com.jd.a6i.db.pojo.Account;
import com.jd.a6i.db.pojo.MenuItem;
import com.jd.a6i.view.JDProgressDialog;

import static com.jd.a6i.common.PropertyUtil.getProperty;
public class JDBaseActivity extends FragmentActivity {
	protected int layoutId;
	protected Resources resources = null;
	
	protected TextView textTitle = null;
	protected TextView textUser = null;
	protected ListView menuList = null;
	protected PromptManager promptManager;
	protected MenuItemAdapter menuItemAdapter = null;
	
	protected JDProgressDialog jdProgressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(layoutId);
		promptManager = loadMediaManager();
		resources = getResources();
		textTitle = (TextView) findViewById(R.id.textTitle);
		textUser = (TextView) findViewById(R.id.textUser);
		Account account = JDApplication.getInstance().getAccount();
		if(account!=null){
			textUser.setText(account.getStaffName());
		}else{
			//loginError();
		}
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
	}
	@Override
	public void finish() {
		super.finish();
		JDApplication.getInstance().setLogin(false);
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		AlarmManagerBroadcastReceiver.cancelAlarm(getApplicationContext());
		JDApplication.getInstance().setLogin(false);
	}
	protected void loginError(){
		Intent intent = new Intent();
		intent.setClass(this, LoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(getIntent());
		baseToast(getString(R.string.loginError));
		this.finish();
	}
	protected void prepareLayout(int layoutId){
		this.layoutId = layoutId;
	}
	
	protected void menuLayout(List<MenuItem> menuItemList){
		menuList = (ListView) findViewById(R.id.menuList);
		menuItemAdapter = new MenuItemAdapter(menuItemList, getApplicationContext());
	}
	
	public void baseToast(int stringId){
		Toast.makeText(getApplicationContext(), getResources().getString(stringId), Toast.LENGTH_SHORT).show();
	}
	
	public void baseToast(int stringId,Object...fromatArgs){
		Toast.makeText(getApplicationContext(), getResources().getString(stringId, fromatArgs), Toast.LENGTH_SHORT).show();
	}
	
	public void baseToast(String message){
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}
	
	public String getResourcesString(int stringId,Object...formatArgs){
		return getResources().getString(stringId, formatArgs);
	}
	
	protected View messageView(int stringId){
		View messageView = LayoutInflater.from(this).inflate(R.layout.jd_custom_alert_dialog, null);
		TextView textMessage = (TextView) messageView.findViewById(R.id.textMessage);
		textMessage.setText(stringId);
		return messageView;
	}
	
	protected View messageView(String messageContent){
		View messageView = LayoutInflater.from(this).inflate(R.layout.jd_custom_alert_dialog, null);
		TextView textMessage = (TextView) messageView.findViewById(R.id.textMessage);
		textMessage.setText(messageContent);
		return messageView;
	}
	
	protected AlertDialog.Builder createAlertDialogBuilder(int stringId,OnClickListener alertDialogOnClickListener){
		return initAlertDialogBuilder(0, stringId, null,alertDialogOnClickListener);
	}
	
	protected AlertDialog.Builder createAlertDialogBuilder(String messageContent,OnClickListener alertDialogOnClickListener){
		return initAlertDialogBuilder(1, 0, messageContent,alertDialogOnClickListener);
	}
	
	private AlertDialog.Builder initAlertDialogBuilder(int type,int stringId,String messageContent,OnClickListener alertDialogOnClickListener){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
		alertDialogBuilder.setIcon(R.drawable.ico_question);
		alertDialogBuilder.setTitle(R.string.prompt);
		if(type==0){
			alertDialogBuilder.setView(messageView(stringId));
		}else if(type==1){
			alertDialogBuilder.setView(messageView(messageContent));
		}
		alertDialogBuilder.setNegativeButton(R.string.yes,  alertDialogOnClickListener);
		alertDialogBuilder.setPositiveButton(R.string.no, alertDialogOnClickListener);
		return alertDialogBuilder;
	}
	/**
	 * ªÒ»°EditTextƒ⁄»›
	 * @param editText
	 * @return
	 */
	protected String getEditTextContent(EditText editText){
		if(editText.getText()==null){
			return "";
		}
		return editText.getText().toString();
	}
	protected void showProgressDialog(){
		if(jdProgressDialog==null){
			jdProgressDialog = new JDProgressDialog(this, ProgressDialog.STYLE_SPINNER);
		}
		jdProgressDialog.show();
		jdProgressDialog.setDialogText(R.string.loading);
	}
	
	protected void finishProgressDialog(){
		if(jdProgressDialog!=null){
			jdProgressDialog.dismiss();
			jdProgressDialog.cancel();
			jdProgressDialog=null;
		}
	}
	
	protected PromptManager loadMediaManager(){
		PromptManager promptManager = PromptManager.getInstance(getApplicationContext());
		promptManager.initSounds(getApplicationContext());
		promptManager.addSound(1, R.raw.jinggao);
		promptManager.addSound(2, R.raw.fjshb);
		return promptManager;
	}
}
