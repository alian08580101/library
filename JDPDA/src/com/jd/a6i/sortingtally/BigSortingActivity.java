package com.jd.a6i.sortingtally;

import static com.jd.a6i.common.PropertyUtil.getProperty;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.jd.a6i.JDApplication;
import com.jd.a6i.JDBaseActivity;
import com.jd.a6i.R;
import com.jd.a6i.common.Confirmation;
import com.jd.a6i.common.Confirmation.SortingType;
import com.jd.a6i.common.ConstantUtils;
import com.jd.a6i.common.JsonConverter;
import com.jd.a6i.db.pojo.Account;
import com.jd.a6i.db.pojo.PackageInfo;
import com.jd.a6i.db.pojo.Result;
import com.jd.a6i.db.pojo.SortingTally;
import com.jd.a6i.db.pojo.StationInfo;
import com.jd.a6i.db.service.SortingTallyService;
import com.jd.a6i.query.QueryPackagePartActivity;
import com.jd.a6i.view.TextEditView;
import com.jd.a6i.volley.BaseRequest;
import com.jd.a6i.volley.ResultRequet;
import com.jd.a6i.volley.StationInfoRequest;
import com.jd.a6i.volley.VolleyClient;
import com.whl.dao.common.DaoException;
import com.whl.utils.SimpleDateUtils;



public class BigSortingActivity extends JDBaseActivity implements OnClickListener,OnKeyListener,DialogInterface.OnClickListener{
	public static final int ALERT_TYPE_DEFAULT = 0;
	public static final int ALERT_TYPE_STATION_NO_CHECK = 1;
	public static final int ALERT_TYPE_PACKAGE_NO_CHECK = 2;
	public static final int CHECK_SITE_CODE = 3;
	public static final int CHECK_PACKAGE_CODE = 4;
	
	private TextEditView jdStation;
	private TextEditView jdPackageNo;
	private TextEditView jdSiteName;
	private EditText editStation;
	private EditText editPackageNo;
	private EditText editSiteName;
	private Button buttonDifferenceQuery;
	private TextView textPromptContent;
	
	private AlertDialog.Builder bigSortingAlertBuilder;
	private String currentStationNo;
	private String currentPackageNo;
	private StationInfo stationInfo;
	private SortingType sortingType;
	private int waybillNoCount = 0;
    private int packageNoCount = 0;
    private int alertType = ALERT_TYPE_DEFAULT;
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			finishProgressDialog();
			if(msg.arg1==CHECK_SITE_CODE){
				switch(msg.what){
				case BaseRequest.REQUEST_SUCCESS:
					if(msg.obj!=null){
						stationInfo = (StationInfo) msg.obj;
						checkSiteCodeSuccess(stationInfo);
					}
					break;
				case BaseRequest.REQUEST_FAILURE:
					baseToast(msg.obj.toString());
					break;
				}
			}else if(msg.arg1==CHECK_PACKAGE_CODE){
				switch(msg.what){
				case BaseRequest.REQUEST_SUCCESS:
					if(msg.obj!=null){
						Result result = (Result) msg.obj;
						checkPackageCodeSuccess(result);
					}
					break;
				case BaseRequest.REQUEST_FAILURE:
					baseToast(msg.obj.toString());
					break;
				}
			}
		}
		private void checkSiteCodeSuccess(StationInfo stationInfo){
			if (stationInfo != null) {
				if (stationInfo.getCode() == 200) {
					StringBuffer outputMessage = new StringBuffer();
					if (!Confirmation.isMatchSortingType(sortingType,stationInfo.getDmsCode(), false, outputMessage)) {
						promptManager.playSound(1, 1);
						baseToast(outputMessage.toString());
						editStation.requestFocus();
						return;
					}
					currentStationNo = String.valueOf(stationInfo.getSiteCode());
					editSiteName.setText(stationInfo.getSiteName());
					editPackageNo.requestFocus();
				}else{
					editStation.requestFocus();
					promptManager.playSound(1, 1);
					baseToast(stationInfo.getMessage());
				}
			}
		}
		private void checkPackageCodeSuccess(Result result){
			if(result!=null){
				if(result.getCode()==200){
					if(saveSortingTally()){
						editPackageNo.setText(null);
						setPrompt(waybillNoCount,packageNoCount);
						promptManager.playSound(1, 1);
						baseToast(R.string.sortingSuccess);
					}else{
						promptManager.playSound(2, 1);
						baseToast(R.string.sortingFailure);
					}
				}else if(result.getCode()==10000){
					promptManager.playSound(1, 1);
					baseToast(result.getMessage());
					return;
				}
				setAlertType(ALERT_TYPE_PACKAGE_NO_CHECK);
				bigSortingAlertBuilder = createAlertDialogBuilder(result.getMessage(),BigSortingActivity.this);
				bigSortingAlertBuilder.show();
			}
		}
	};
	
	public BigSortingActivity() {
		prepareLayout(R.layout.big_sorting_fragment);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)  {
		super.onCreate(savedInstanceState);
		jdStation = (TextEditView) findViewById(R.id.jdStation);
		jdPackageNo = (TextEditView) findViewById(R.id.jdPackageNo);
		jdSiteName = (TextEditView) findViewById(R.id.jdSiteName);
		editStation = (EditText) jdStation.findViewById(R.id.editText);
		editPackageNo = (EditText) jdPackageNo.findViewById(R.id.editText);
		editSiteName = (EditText) jdSiteName.findViewById(R.id.editText);
		buttonDifferenceQuery = (Button) findViewById(R.id.buttonDifferenceQuery);
		textPromptContent = (TextView) findViewById(R.id.textPromptContent);
		editSiteName.setFocusable(false);
		editSiteName.setEnabled(false);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		setPrompt(waybillNoCount,packageNoCount);
		editStation.setId(R.id.stationId);
		editPackageNo.setId(R.id.packageNoId);
		
		editStation.setOnKeyListener(this);
		editPackageNo.setOnKeyListener(this);
		buttonDifferenceQuery.setOnClickListener(this);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		int sortingType = getIntent().getIntExtra(ConstantUtils.SORTING_TYPE, ConstantUtils.DEFAULT_SORTING);
		String smallSorting = "-"+getResourcesString(R.string.bigSorting);
		switch(sortingType){
		case ConstantUtils.WAREHOUSE_SORTING:
			this.sortingType = SortingType.WAREHOUSE;
			textTitle.setText(getResourcesString(R.string.warehouseSorting)+smallSorting);
			break;
		case ConstantUtils.AFTERSALE_SORTING:
			this.sortingType = SortingType.AFTERSALE;
			textTitle.setText(getResourcesString(R.string.aftersaleSorting)+smallSorting);
			break;
		case ConstantUtils.BMERCHANT_SORTING:
			this.sortingType = SortingType.BMERCHANT;
			textTitle.setText(getResourcesString(R.string.bMerchantSorting)+smallSorting);
			break;
		case ConstantUtils.STEP_SORT_CENTER_SORTING:
			this.sortingType = SortingType.SORTINGCENTER;
			textTitle.setText(getResourcesString(R.string.stepSortingCenterSorting)+"\n"+smallSorting);
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.buttonDifferenceQuery:
			differenceQueryAction();
			break;
		}
	}
	

	@Override
	public void onClick(DialogInterface dialog, int which) {
		if(getAlertType()==ALERT_TYPE_STATION_NO_CHECK){
			switch(which){
			case DialogInterface.BUTTON_NEGATIVE:
				checkSiteCodeFromServer(getEditTextContent(editStation));
				break;
			case DialogInterface.BUTTON_POSITIVE:
				editStation.setText(currentStationNo);
				break;
			}
		}else if(getAlertType()==ALERT_TYPE_PACKAGE_NO_CHECK){
			switch(which){
			case DialogInterface.BUTTON_NEGATIVE:
				if(saveSortingTally()){
					promptManager.playSound(1, 1);
					baseToast(R.string.sortingSuccess);
					editPackageNo.setText(null);
					setPrompt(waybillNoCount,packageNoCount);
				}else{
					promptManager.playSound(2, 1);
					baseToast(R.string.sortingFailure);
				}
				editPackageNo.requestFocus();
				break;
			case DialogInterface.BUTTON_POSITIVE:
				
				break;
			}
		}
		setAlertType(ALERT_TYPE_DEFAULT);
		dialog.dismiss();
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_ENTER){
			if(event.getAction()==KeyEvent.ACTION_UP){
				switch(v.getId()){
				case R.id.stationId:
					String stationNo = getEditTextContent(editStation);
					return checkStationNo(stationNo,event);
				case R.id.packageNoId:
					String packageNo = getEditTextContent(editPackageNo);
					if(Confirmation.isNullOrEmpty(currentStationNo)){
						promptManager.playSound(1, 1);
						baseToast(R.string.checkStationNoPrompt);
						editStation.requestFocus();
						return true;
					}else{
						return checkPackageNo(packageNo, event);
					}
				}
			}
		}
		return false;
	}
	
	private boolean checkStationNo(String stationNo,KeyEvent event){
		if(!Confirmation.isNullOrEmpty(stationNo)){
			if(!Confirmation.isNullOrEmpty(currentStationNo)){
				if(!currentStationNo.equals(stationNo)){
					setAlertType(ALERT_TYPE_STATION_NO_CHECK);
					bigSortingAlertBuilder = createAlertDialogBuilder(R.string.ifSwitchSite, this);
					bigSortingAlertBuilder.show();
					return true;
				}
			}else if(Confirmation.isPackNo(stationNo)||Confirmation.isWaybillNo(stationNo)){
				editPackageNo.setText(stationNo);
				editStation.setText(null);
				checkStationNo(currentStationNo, event);
				return true;
			}else{
				checkSiteCodeFromServer(stationNo);
				return true;
			}
		}else{
			promptManager.playSound(1, 1);
			baseToast(R.string.checkStationNoPrompt);
			return true;
		}
		return false;
	}
	
	/**
	 * 根据包裹、运单号及回车事件响应
	 * @param packageNo
	 * @param event
	 * @return
	 */
	protected boolean checkPackageNo(String packageNo,KeyEvent event){
		if(!Confirmation.isNullOrEmpty(packageNo)){
			if(Confirmation.isSiteNo(packageNo)){
				editStation.setText(packageNo);
				editPackageNo.setText(null);
				checkStationNo(packageNo, event);
				return true;
			}
			currentPackageNo = packageNo;
			if(!Confirmation.isPackSortNumber(packageNo)){
				promptManager.playSound(1, 1);
				baseToast(R.string.waybillNoError);
				return true;
			}else{
				if(checkPackageNoFromDB(currentPackageNo)){
					promptManager.playSound(2, 1);
					baseToast(R.string.packageNoSortingPrompt, currentPackageNo);
					return true;
				}
				checkPackageCodeFromServer(packageNo);
				return true;
			}
		}else{
			promptManager.playSound(1, 1);
			baseToast(R.string.noWaybillNo);
			return true;
		}
	}
	
	/**
	 * 保存分拣信息
	 * @return
	 */
	private boolean saveSortingTally(){
		Account account = JDApplication.getInstance().getAccount();
		SortingTally sortingTally = new SortingTally();
		sortingTally.setBoxCode(null);
		//sortingTally.setBusinessType(ConstantUtils.BUSINESS_TYPE_REVERSE);
		sortingTally.setBusinessType(getProperty(this).getSortingType());
		sortingTally.setFeatureType(0);
		sortingTally.setIsCancel(0);
		sortingTally.setIsLoss(0);
		sortingTally.setOperateTime(SimpleDateUtils.dateToString(new Date(), SimpleDateUtils.DATETIME_PATTERN));
		sortingTally.setPackageCode(currentPackageNo);
		sortingTally.setReceiveSiteCode(stationInfo.getSiteCode());
		sortingTally.setReceiveSiteName(stationInfo.getSiteName());
		sortingTally.setSiteCode(account.getSiteCode());
		sortingTally.setSiteName(account.getSiteName());
		sortingTally.setUserCode(account.getStaffId());
		sortingTally.setUserName(account.getStaffName());
		SortingTallyService sortingTallyService = SortingTallyService.getInstance(getApplicationContext());
		try {
			if(sortingTallyService.save(sortingTally)==1){
				if(Confirmation.isWaybillNo(currentPackageNo)){
					waybillNoCount++;
				}else if(Confirmation.isPackNo(currentPackageNo)){
					packageNoCount++;
				}
				return true;
			}
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void differenceQueryAction(){
		Intent differenceQueryIntent = new Intent(this,QueryPackagePartActivity.class);
		differenceQueryIntent.putExtra("receiveSiteNo", currentStationNo==null?null:currentStationNo);
		differenceQueryIntent.putExtra("boxNo", "");
		startActivity(differenceQueryIntent);
	}
	private boolean checkPackageNoFromDB(String packageCode){
		SortingTallyService sortingTallyService = SortingTallyService.getInstance(getApplicationContext());
		SortingTally sortingTally = sortingTallyService.findUniqueSortingTally(packageCode);
		if(sortingTally==null){
			return false;
		}
		return true;
	}
	
	/**
	 * 提示信息[运单数，包裹数]
	 * @param formatArgs
	 */
	protected void setPrompt(Object...formatArgs){
		textPromptContent.setText(getResourcesString(R.string.bigSortingTallyPrompt, formatArgs));
	}
	
	private void checkSiteCodeFromServer(String stationNo){
		showProgressDialog();
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("siteCode", stationNo);
		StationInfoRequest stationInfoRequest = new StationInfoRequest(Method.GET, getProperty(this).getSiteCodeUrl(), handler, paramsMap, CHECK_SITE_CODE);
		VolleyClient volleyClient = VolleyClient.getVolleyClient(this);
		volleyClient.addRequest(stationInfoRequest);
		volleyClient.startRequest();
	}
	
	private void checkPackageCodeFromServer(String packageCode){
		showProgressDialog();
		ResultRequet requetRequest = new ResultRequet(Method.POST, getProperty(this).getPackageCodeUrl(), handler, packageInfoToMap(),CHECK_PACKAGE_CODE);
		VolleyClient volleyClient = VolleyClient.getVolleyClient(this);
		volleyClient.addRequest(requetRequest);
		volleyClient.startRequest();
	}
	
	protected Map<String,Object> packageInfoToMap(){
		PackageInfo packageInfo = new PackageInfo();
		Account account = JDApplication.getInstance().getAccount();
		packageInfo.setBoxCode(currentPackageNo);
		//packageInfo.setBusinessType(ConstantUtils.BUSINESS_TYPE_REVERSE);
		packageInfo.setBusinessType(getProperty(this).getSortingType());
		packageInfo.setCreateSiteCode(account.getSiteCode());
		packageInfo.setCreateSiteName(account.getSiteName());
		packageInfo.setOperateTime(SimpleDateUtils.dateToString(new Date(), SimpleDateUtils.DATETIME_PATTERN));
		packageInfo.setOperateType(1);
		packageInfo.setOperateUserCode(account.getStaffId());
		packageInfo.setOperateUserName(account.getStaffName());
		packageInfo.setPackageCode(currentPackageNo);
		packageInfo.setReceiveSiteCode(Integer.valueOf(currentStationNo));
		return JsonConverter.getFieldValueMap(packageInfo);
	}


	public int getAlertType() {
		return alertType;
	}

	public void setAlertType(int alertType) {
		this.alertType = alertType;
	}
}
