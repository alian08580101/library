package com.jd.a6i.cancelsorting;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jd.a6i.JDApplication;
import com.jd.a6i.JDBaseActivity;
import com.jd.a6i.R;
import com.jd.a6i.common.Confirmation;
import com.jd.a6i.common.JsonConverter;
import com.jd.a6i.common.PromptManager;
import com.jd.a6i.db.pojo.CancelSortingRequest;
import com.jd.a6i.task.CancelSortingAsyncTask;
import com.jd.a6i.view.TextEditView;

public class CancelSortingActivity extends JDBaseActivity {
	private TextEditView boxcodeTev = null;
	private EditText boxcodeEt = null;
	private TextView propmtMessageTv = null;
	private Button cancelBt = null;
	
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
			case CancelSortingAsyncTask.CANCELSORTING_ERROR:
				promptManager.playSound(1, 1);
				if(msg.obj!=null&&!msg.obj.toString().equals("")){
					boxcodeEt.setSelected(true);
					propmtMessageTv.setText(msg.obj.toString());
					propmtMessageTv.setTextColor(R.color.red);
				}
				else{
					boxcodeEt.setSelected(true);
					propmtMessageTv.setText(getResources().getString(R.string.networkError));
					propmtMessageTv.setTextColor(R.color.red);
				}
				boxcodeEt.requestFocus(); 
				break;
			case CancelSortingAsyncTask.CANCELSORTING_SUCCESS:
				boxcodeEt.requestFocus();
				boxcodeEt.setText("");
				propmtMessageTv.setText(R.string.cancelSortingSuccess);
				propmtMessageTv.setTextColor(R.color.blue);
				break;
			
			}
		}
	};
	
	public CancelSortingActivity(){
		prepareLayout(R.layout.cancel_sorting_activity);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		boxcodeTev = (TextEditView)findViewById(R.id.jdCancelSortingNo);
		boxcodeEt = (EditText)boxcodeTev.findViewById(R.id.editText);
		propmtMessageTv = (TextView)findViewById(R.id.textPrompt);
		cancelBt = (Button)findViewById(R.id.cancelsortingbt);
		cancelBt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cancelBtKeyDown();
			}
		});
		boxcodeEt.setOnKeyListener(new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(keyCode == KeyEvent.KEYCODE_ENTER){
					if(event.getAction() == KeyEvent.ACTION_DOWN){
						if(boxcodeEt.getText().toString().equals("")){
							promptManager.playSound(1, 1);
							propmtMessageTv.setText(R.string.cancelSortingNopackage);
							propmtMessageTv.setTextColor(R.color.red);
						}
						else{
							boolean isBox = Confirmation.isBoxCode(boxcodeEt.getText().toString());
							if(!Confirmation.isPackSortNumber(boxcodeEt.getText().toString())&&!isBox){
								boxcodeEt.setSelected(true);
								propmtMessageTv.setText(R.string.cancelSortingPackagefailed);
								propmtMessageTv.setTextColor(R.color.red);
								//return false;
							}
							propmtMessageTv.setText(String.format(getResources().getString(R.string.cancelSortingpackage), boxcodeEt.getText().toString()));
							propmtMessageTv.setTextColor(R.color.blue);
						}
						boxcodeEt.requestFocus();
						return true;
					}
					else
					{
						return true;
					}
				}
				return false;
			}
		});
		
		boxcodeEt.requestFocus();
	}

	@Override
	protected void onResume() {
		super.onResume();
		textTitle.setText(getResources().getString(R.string.cancelSorting));
		//textUser.setText(JDApplication.getInstance().getAccount().getErpAccount());
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_F1){
			cancelBtKeyDown();
			return true;
		}
			
		return super.onKeyDown(keyCode, event);
	}
	
	private void messagebox(int id){
		Toast.makeText(CancelSortingActivity.this, getResources().getText(id), Toast.LENGTH_LONG).show();
	}
	
	private void cancelBtKeyDown(){
		if(!boxcodeEt.getText().toString().equals("")){
			boolean isBox = Confirmation.isBoxCode(boxcodeEt.getText().toString());
			if(!Confirmation.isPackSortNumber(boxcodeEt.getText().toString())&&!isBox){
				boxcodeEt.setSelected(true);
				propmtMessageTv.setText(R.string.cancelSortingPackagefailed);
				propmtMessageTv.setTextColor(R.color.red);
				boxcodeEt.requestFocus();
				return;
			}
			CancelSortingAsyncTask cancelSortingAsyncTask = new CancelSortingAsyncTask(getApplicationContext(),mHandler);
			CancelSortingRequest cancelSortingRequest = new CancelSortingRequest();
			if(isBox){
				cancelSortingRequest.setBoxCode(boxcodeEt.getText().toString());
			}
			else{
				cancelSortingRequest.setBoxCode("");
			}
			
			cancelSortingRequest.setBusinessType(20);
			cancelSortingRequest.setFeatureType(0);
			cancelSortingRequest.setId(0);
			cancelSortingRequest.setIsCancel(0);
			cancelSortingRequest.setIsLoss(0);
			
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
			
			cancelSortingRequest.setOperateTime(formatter.format(curDate));
			cancelSortingRequest.setPackageCode(boxcodeEt.getText().toString());
			cancelSortingRequest.setReceiveSiteCode(0);
			cancelSortingRequest.setReceiveSiteName("");
			cancelSortingRequest.setSiteCode(JDApplication.getInstance().getAccount().getSiteCode());
			cancelSortingRequest.setSiteName(JDApplication.getInstance().getAccount().getSiteName());
			cancelSortingRequest.setUserCode(JDApplication.getInstance().getAccount().getStaffId());
			cancelSortingRequest.setUserName(JDApplication.getInstance().getAccount().getStaffName());
			
			
			cancelSortingAsyncTask.execute(JsonConverter.convertObjectToJson(cancelSortingRequest));
		}
		else{
			promptManager.playSound(1, 1);
			propmtMessageTv.setText(R.string.cancelSortingNopackage);
			propmtMessageTv.setTextColor(R.color.red);
			boxcodeEt.requestFocus();
		}
	
	}
}
