package com.jd.a6i.sortingtally;

import static com.jd.a6i.common.PropertyUtil.getProperty;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.jd.a6i.JDApplication;
import com.jd.a6i.JDBaseActivity;
import com.jd.a6i.R;
import com.jd.a6i.common.Confirmation;
import com.jd.a6i.common.ConstantUtils;
import com.jd.a6i.common.JsonConverter;
import com.jd.a6i.db.pojo.Account;
import com.jd.a6i.db.pojo.BoxInfo;
import com.jd.a6i.db.pojo.SealBoxInfo;
import com.jd.a6i.db.service.SealBoxInfoService;
import com.jd.a6i.task.CheckAsyncTask;
import com.jd.a6i.view.JDProgressDialog;
import com.jd.a6i.view.TextEditView;
import com.jd.a6i.volley.BaseRequest;
import com.jd.a6i.volley.BoxInfoRequest;
import com.jd.a6i.volley.VolleyClient;
import com.whl.dao.common.DaoException;
import com.whl.utils.SimpleDateUtils;

public class SealBoxActivity extends JDBaseActivity implements OnKeyListener{
	private static final String TAG = "SealBoxActivity";
	private TextEditView jdSealLabelNo;
	private TextEditView jdBoxNo;
	private EditText editLabelNo;
	private EditText editBoxNo;
	private TextView textOwerStation;
	
	private String currentBoxCode;
	private String currentSealLabelCode;
	private JDProgressDialog sealBoxProgressDialog;
	private CheckAsyncTask checkAsyncTask;
	private BoxInfo boxInfo;
	/*private Handler sealBoxHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(sealBoxProgressDialog!=null&&sealBoxProgressDialog.isShowing()){
				sealBoxProgressDialog.dismiss();
				sealBoxProgressDialog = null;
			}
			if(checkAsyncTask.getStatus()==Status.FINISHED){
				checkAsyncTask = null;
			}
			if(msg.arg1==CheckAsyncTask.CHECK_TYPE_BOX_NO){
				switch(msg.what){
				case CheckAsyncTask.CHECK_SUCCESS:
					if(msg.obj!=null){
						boxInfo = JsonConverter.convertJsonToObject(msg.obj.toString(), BoxInfo.class);
						textOwerStation.setText(getResourcesString(R.string.owerStation, boxInfo.getReceiveSiteCode()+"-"+boxInfo.getReceiveSiteName()));
						currentBoxCode = boxInfo.getBoxCode();
					}
					editLabelNo.requestFocus();
					break;
				case CheckAsyncTask.CHECK_FAILURE:
					promptManager.playSound(1, 1);
					baseToast(R.string.noBoxInfo);
					editBoxNo.requestFocus();
					break;
				}
			}
		}
		
	};*/
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			finishProgressDialog();
			switch(msg.what){
			case BaseRequest.REQUEST_SUCCESS:
				if(msg.obj!=null){
					boxInfo = (BoxInfo) msg.obj;
					textOwerStation.setText(getResourcesString(R.string.owerStation, boxInfo.getReceiveSiteCode()+"-"+boxInfo.getReceiveSiteName()));
					currentBoxCode = boxInfo.getBoxCode();
				}
				editLabelNo.requestFocus();
				break;
			case BaseRequest.REQUEST_FAILURE:
				promptManager.playSound(1, 1);
				baseToast(R.string.noBoxInfo);
				editBoxNo.requestFocus();
				break;
			}
		}
		
	};
	
	public SealBoxActivity(){
		prepareLayout(R.layout.seal_box_popup_window);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		jdBoxNo = (TextEditView) findViewById(R.id.jdBoxNo);
		jdSealLabelNo = (TextEditView) findViewById(R.id.jdSealLabelNo);
		editBoxNo = (EditText) jdBoxNo.findViewById(R.id.editText);
		editLabelNo = (EditText) jdSealLabelNo.findViewById(R.id.editText);
		textOwerStation = (TextView) findViewById(R.id.textOwerStation);
	}

	@Override
	public void onResume() {
		super.onResume();
		textTitle.setText(getResourcesString(R.string.sortingSealLabel));
		
		Intent sealBoxIntent = getIntent();
		currentBoxCode = sealBoxIntent.getStringExtra("boxNo");
		String station = sealBoxIntent.getStringExtra("station");
		textOwerStation.setText(getResourcesString(R.string.owerStation, station));
		editBoxNo.setText(currentBoxCode);
		editBoxNo.setId(R.id.boxNoId);
		editLabelNo.setId(R.id.sealLabelNoId);
		editBoxNo.setOnKeyListener(this);
		editLabelNo.setOnKeyListener(this);
		
		if(editBoxNo.getText().toString()!=null&&!"".equals(editBoxNo.getText().toString())){
			editLabelNo.requestFocus();
		}
	}
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_ENTER) {
			if (event.getAction() == KeyEvent.ACTION_UP) {
				switch(v.getId()){
				case R.id.boxNoId:
					String boxNo = getEditTextContent(editBoxNo);
					return checkBoxNo(boxNo,event);
				case R.id.sealLabelNoId:
					if(currentBoxCode==null||"".equals(currentBoxCode)){
						promptManager.playSound(1, 1);
						baseToast(R.string.checkBoxNoPrompt);
						editBoxNo.requestFocus();
						return true;
					}
					String sealLabelNo = getEditTextContent(editLabelNo);
					return checkSealLabelNo(sealLabelNo, event);
				}
			}
		}
		return false;
	}
	
	/**
	 * 校验箱号
	 * @param boxNo
	 * @param event
	 * @return
	 */
	protected boolean checkBoxNo(String boxNo,KeyEvent event){
		if(boxNo!=null&&!"".equals(boxNo)){
			if(!Confirmation.isBoxCode(boxNo)){
				promptManager.playSound(1, 1);
				baseToast(R.string.errorBoxNo);
				return true;
			}else{
				//checkBoxNoFromServer(boxNo);
				checkBoxCodeFromServer(boxNo);
				return true;
			}
		}else{
			promptManager.playSound(1, 1);
			baseToast(R.string.checkBoxNoPrompt);
			return true;
		}
	}

	protected boolean checkSealLabelNo(String sealLabelNo, KeyEvent event) {
		if (sealLabelNo != null && !"".equals(sealLabelNo)) {
			if (!Confirmation.isLegalExpression(sealLabelNo,ConstantUtils.SEAL_BOX_NO_REGULAR_EXPRESSION)) {
				promptManager.playSound(1, 1);
				baseToast(R.string.errorSealLabelNo);
				return true;
			}
			currentSealLabelCode = sealLabelNo;
			if (saveSealBoxInfo()) {
				return false;
			}
			return true;
		} else {
			promptManager.playSound(1, 1);
			baseToast(R.string.checkSealLabelNoPrompt);
			return true;
		}
	}
	/**
	 * 根据箱号请求服务器
	 * @param boxNo
	 
	protected void checkBoxNoFromServer(String boxNo){
		if(sealBoxProgressDialog==null){
			sealBoxProgressDialog = new JDProgressDialog(this,ProgressDialog.STYLE_SPINNER);
		}
		sealBoxProgressDialog.show();
		sealBoxProgressDialog.setDialogText(R.string.loading);
		checkAsyncTask = new CheckAsyncTask(this, sealBoxHandler,CheckAsyncTask.CHECK_TYPE_BOX_NO);
		checkAsyncTask.execute(boxNo);
	}*/
	
	/**
	 * 根据箱号请求服务器
	 * @param boxNo
	 * */
	private void checkBoxCodeFromServer(String boxNo){
		showProgressDialog();
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("boxCode", boxNo);
		BoxInfoRequest boxInfoRequest = new BoxInfoRequest(Method.GET, getProperty(this).getBoxCodeUrl2(), handler, paramsMap);
		VolleyClient volleyClient = VolleyClient.getVolleyClient(this);
		volleyClient.addRequest(boxInfoRequest);
		volleyClient.startRequest();
	}
	
	
	protected boolean saveSealBoxInfo(){
		SealBoxInfoService sealBoxInfoService = SealBoxInfoService.getInstance(getApplicationContext());
		if(sealBoxInfoService.findUniqueSealBoxInfo(currentSealLabelCode)==null){
			Account account = JDApplication.getInstance().getAccount();
			SealBoxInfo sealBoxInfo = new SealBoxInfo();
			sealBoxInfo.setBoxCode(currentBoxCode);
			sealBoxInfo.setBusinessType(getProperty(this).getSortingType());
			sealBoxInfo.setOperateTime(SimpleDateUtils.dateToString(new Date(), SimpleDateUtils.DATETIME_PATTERN));
			sealBoxInfo.setSealCode(currentSealLabelCode);
			sealBoxInfo.setSiteCode(account.getSiteCode());
			sealBoxInfo.setSiteName(account.getSiteName());
			sealBoxInfo.setUserCode(account.getStaffId());
			sealBoxInfo.setUserName(account.getStaffName());
			
			try {
				if(sealBoxInfoService.save(sealBoxInfo)==1){
					clear();
					editBoxNo.requestFocus();
					return true;
				}
			} catch (DaoException e) {
				e.printStackTrace();
			}
		}else{
			promptManager.playSound(1, 1);
			baseToast(R.string.errorSealLableNo, currentSealLabelCode);
			return false;
		}
		return false;
	}
	
	protected void clear(){
		currentBoxCode = null;
		currentSealLabelCode = null;
		editBoxNo.setText(null);
		editLabelNo.setText(null);
		textOwerStation.setText(getResourcesString(R.string.owerStation, ""));
		promptManager.playSound(1, 1);
		baseToast(R.string.operateSuccess);
	}
}
