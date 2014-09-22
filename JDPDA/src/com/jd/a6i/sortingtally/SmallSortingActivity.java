package com.jd.a6i.sortingtally;

import static com.jd.a6i.common.PropertyUtil.getProperty;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
import com.jd.a6i.db.pojo.BoxInfo;
import com.jd.a6i.db.pojo.PackageInfo;
import com.jd.a6i.db.pojo.Result;
import com.jd.a6i.db.pojo.SortingTally;
import com.jd.a6i.db.service.SortingTallyService;
import com.jd.a6i.query.QueryPackagePartActivity;
import com.jd.a6i.view.TextEditView;
import com.jd.a6i.volley.BaseRequest;
import com.jd.a6i.volley.BoxInfoRequest;
import com.jd.a6i.volley.ResultRequet;
import com.jd.a6i.volley.VolleyClient;
import com.jd.a6i.volley.VolleyResultListener;
import com.whl.dao.common.DaoException;
import com.whl.utils.SimpleDateUtils;

public class SmallSortingActivity extends JDBaseActivity implements OnClickListener,OnKeyListener,DialogInterface.OnClickListener,VolleyResultListener{
	public static final int ALERT_TYPE_DEFAULT = 0;
	public static final int ALERT_TYPE_BOX_NO_CHECK = 1;
	public static final int ALERT_TYPE_PACKAGE_NO_CHECK = 2;
	public static final int CHECK_BOX_CODE = 3;
	public static final int CHECK_PACKAGE_CODE = 4;
	
	private HashSet<String> boxNoSet = new HashSet<String>();
	
	private TextEditView jdStation;
	private TextEditView jdBoxNo;
	private TextEditView jdPackageNo;
	private EditText editStation;
	private EditText editBoxNo;
	private EditText editPackageNo;
	private TextView textPromptContent;
	
	private Button buttonSealBox;
	private Button buttonDifferenceQuery;
	
	private AlertDialog.Builder smallSortingAlertBuilder;
	private BoxInfo boxInfo;
	
	private int boxNoCount = 0;
    private int waybillNoCount = 0;
    private int packageNoCount = 0;
	private String currentBoxNo;
	private String currentPackageNo;
	private int alertType = ALERT_TYPE_DEFAULT;
	private SortingType sortingType;
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			finishProgressDialog();
			if(msg.arg1==CHECK_BOX_CODE){
				switch(msg.what){
				case BaseRequest.REQUEST_SUCCESS:
					if(msg.obj!=null){
						boxInfo = (BoxInfo) msg.obj;
						checkBoxCodeSuccess(boxInfo);
					}
					break;
				case BaseRequest.REQUEST_FAILURE:
					baseToast(msg.obj.toString());
					break;
				}
			}else if(msg.arg1==CHECK_PACKAGE_CODE){
				switch (msg.what) {
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
		private void checkBoxCodeSuccess(BoxInfo boxInfo){
			if(boxInfo.getCode()==200){
				editStation.setText(boxInfo.getReceiveSiteCode()+"-"+boxInfo.getReceiveSiteName());
				editPackageNo.requestFocus();
				currentBoxNo = boxInfo.getBoxCode();
				packageAndWaybillCount(currentBoxNo);
				if(!boxNoSet.contains(currentBoxNo)){
					boxNoSet.add(currentBoxNo);
					boxNoCount++;
				}
				setPrompt(boxNoCount,waybillNoCount,packageNoCount);
			}else{
				promptManager.playSound(1, 1);
				baseToast(boxInfo.getMessage());
				editBoxNo.requestFocus();
			}
		}
		private void checkPackageCodeSuccess(Result result){
			if(result.getCode()==200){
				if (saveSortingTally()) {
					editPackageNo.setText(null);
					packageAndWaybillCount(currentBoxNo);
					setPrompt(boxNoCount,waybillNoCount,packageNoCount);
					promptManager.playSound(1, 1);
					baseToast(R.string.sortingSuccess);
				} else {
					promptManager.playSound(1, 2);
					baseToast(R.string.sortingFailure);
				}
			}else{
				if (result.getCode() == 10000) {
					promptManager.playSound(1, 1);
					baseToast(result.getMessage());
					return;
				}
				setAlertType(ALERT_TYPE_PACKAGE_NO_CHECK);
				smallSortingAlertBuilder = createAlertDialogBuilder(result.getMessage(), SmallSortingActivity.this);
				smallSortingAlertBuilder.show();
			}
		}
	};
	
	public SmallSortingActivity() {
		prepareLayout(R.layout.small_sorting_fragment);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		jdStation = (TextEditView) findViewById(R.id.jdStation);
		jdBoxNo = (TextEditView) findViewById(R.id.jdBoxNo);
		jdPackageNo = (TextEditView) findViewById(R.id.jdPackageNo);
		
		buttonSealBox = (Button) findViewById(R.id.buttonSealBox);
		buttonDifferenceQuery = (Button) findViewById(R.id.buttonDifferenceQuery);
		
		textPromptContent = (TextView) findViewById(R.id.textPromptContent);
		
		editStation = (EditText) jdStation.findViewById(R.id.editText);
		editBoxNo = (EditText) jdBoxNo.findViewById(R.id.editText);
		editPackageNo = (EditText) jdPackageNo.findViewById(R.id.editText);
		editBoxNo.requestFocus();
		editStation.setFocusable(false);
		editStation.setEnabled(false);
	}
	
	
	@Override
	public void onStart() {
		super.onStart();
		setPrompt(boxNoCount,waybillNoCount,packageNoCount);
		editStation.setId(R.id.stationId);
		editBoxNo.setId(R.id.boxNoId);
		editPackageNo.setId(R.id.packageNoId);
		
		editBoxNo.setOnKeyListener(this);
		editPackageNo.setOnKeyListener(this);
		buttonSealBox.setOnClickListener(this);
		buttonDifferenceQuery.setOnClickListener(this);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		int sortingType = getIntent().getIntExtra(ConstantUtils.SORTING_TYPE, ConstantUtils.DEFAULT_SORTING);
		String smallSorting = "-"+getResourcesString(R.string.smallSorting);
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
		case R.id.buttonSealBox:
			sealBoxAction();
			break;
		case R.id.buttonDifferenceQuery:
			differenceQueryAction();
			break;
		}
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		if(getAlertType()==ALERT_TYPE_PACKAGE_NO_CHECK){
			switch(which){
			case DialogInterface.BUTTON_NEGATIVE:
				if(saveSortingTally()){
					promptManager.playSound(1, 1);
					baseToast(R.string.sortingSuccess);
					editPackageNo.setText(null);
				}else{
					promptManager.playSound(1, 2);
					baseToast(R.string.sortingFailure);
				}
				packageAndWaybillCount(currentBoxNo);
				editPackageNo.requestFocus();
				setPrompt(boxNoCount,waybillNoCount,packageNoCount);
				break;
			case DialogInterface.BUTTON_POSITIVE:
				
				break;
			}
		}else if(getAlertType()==ALERT_TYPE_BOX_NO_CHECK){
			switch(which){
			case DialogInterface.BUTTON_NEGATIVE:
				checkBoxCodeFromServer(getEditTextContent(editBoxNo));
				break;
			case DialogInterface.BUTTON_POSITIVE:
				editBoxNo.setText(currentBoxNo);
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
				case R.id.boxNoId:
					String boxNo = getEditTextContent(editBoxNo);
					return checkBoxNo(boxNo,event);
				case R.id.packageNoId:
					String packageNo = getEditTextContent(editPackageNo);
					if(currentBoxNo==null||"".equals(currentBoxNo)){
						promptManager.playSound(1, 1);
						baseToast(R.string.checkBoxNoPrompt);
						editBoxNo.requestFocus();
						return true;
					}else{
						return checkPackageNo(packageNo, event);
					}
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
			StringBuffer outputMessage = new StringBuffer();
			if(!Confirmation.isBoxCode(boxNo)){
				if(Confirmation.isPackNo(boxNo)||Confirmation.isWaybillNo(boxNo)){
					editPackageNo.setText(boxNo);
					editBoxNo.setText(null);
				}
				promptManager.playSound(1, 1);
				baseToast(R.string.errorBoxNo);
				return true;
			}else if(!Confirmation.isMatchSortingType(sortingType, boxNo, true, outputMessage)){
				promptManager.playSound(1, 1);
				baseToast(outputMessage.toString());
				return true;
			}else{
				if(!"".equals(currentBoxNo)&&currentBoxNo!=null){
					if(!boxNo.equals(currentBoxNo)){
						setAlertType(ALERT_TYPE_BOX_NO_CHECK);
						smallSortingAlertBuilder = createAlertDialogBuilder(R.string.ifSwitchBox, this);
						smallSortingAlertBuilder.show();
						return true;
					}
				}
				checkBoxCodeFromServer(boxNo);
				return true;
			}
		}else{
			promptManager.playSound(1, 1);
			baseToast(R.string.checkBoxNoPrompt);
			return true;
		}
	}
	 
	/**
	 * 根据箱号请求服务器
	 * @param boxNo
	 * */
	private void checkBoxCodeFromServer(String boxNo){
		showProgressDialog();
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("boxCode", boxNo);
		//BaseRequest<BoxInfo> baseRequest = new BaseRequest<BoxInfo>(Method.GET, getProperty(this).getBoxCodeUrl1(), this, paramsMap, CHECK_BOX_CODE);
		
		BoxInfoRequest boxInfoRequest = new BoxInfoRequest(Method.GET, getProperty(this).getBoxCodeUrl1(), handler, paramsMap,CHECK_BOX_CODE);
		VolleyClient volleyClient = VolleyClient.getVolleyClient(this);
		volleyClient.addRequest(boxInfoRequest);
		volleyClient.startRequest();
	}
	/**
	 * 根据包裹、运单号及回车事件响应
	 * @param packageNo
	 * @param event
	 * @return
	 */
	protected boolean checkPackageNo(String packageNo,KeyEvent event){
		if(packageNo!=null&&!"".equals(packageNo)){
			currentPackageNo = packageNo;
			if(!Confirmation.isPackSortNumber(packageNo)){
				promptManager.playSound(1, 1);
				baseToast(R.string.waybillNoError);
				return true;
			}else{
				if(checkPackageNoFromDB(currentPackageNo)){
					promptManager.playSound(1, 1);
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
	private void checkPackageCodeFromServer(String packageCode){
		showProgressDialog();
		ResultRequet requetRequest = new ResultRequet(Method.POST, getProperty(this).getPackageCodeUrl(), handler, packageInfoToMap(),CHECK_PACKAGE_CODE);
		VolleyClient volleyClient = VolleyClient.getVolleyClient(this);
		volleyClient.addRequest(requetRequest);
		volleyClient.startRequest();
	}
	/**
	 * 包装请求包裹、运单号json串
	 * {"boxCode":"170188553-1-3-",
	 * "businessType":20,
	 * "createSiteCode":480,
	 * "createSiteName":"南京配送中心44",
	 * "operateTime":"2014-05-05 10:23:00",
	 * "operateType":1,
	 * "operateUserCode":7595,
	 * "operateUserName":"金大中",
	 * "receiveSiteCode":2201,
	 * "packageCode":"170188553-1-3-"}
	 * @return
	 */
	protected String packageInfoToJson(){
		PackageInfo packageInfo = new PackageInfo();
		Account account = JDApplication.getInstance().getAccount();
		packageInfo.setBoxCode(currentBoxNo);
		packageInfo.setBusinessType(getProperty(this).getSortingType());
		packageInfo.setCreateSiteCode(account.getSiteCode());
		packageInfo.setCreateSiteName(account.getSiteName());
		packageInfo.setOperateTime(SimpleDateUtils.dateToString(new Date(), SimpleDateUtils.DATETIME_PATTERN));
		packageInfo.setOperateType(1);
		packageInfo.setOperateUserCode(account.getStaffId());
		packageInfo.setOperateUserName(account.getStaffName());
		packageInfo.setPackageCode(currentPackageNo);
		packageInfo.setReceiveSiteCode(boxInfo.getReceiveSiteCode());
		return JsonConverter.convertObjectToJson(packageInfo);
	}
	
	protected Map<String,Object> packageInfoToMap(){
		PackageInfo packageInfo = new PackageInfo();
		Account account = JDApplication.getInstance().getAccount();
		packageInfo.setBoxCode(currentBoxNo);
		packageInfo.setBusinessType(getProperty(this).getSortingType());
		packageInfo.setCreateSiteCode(account.getSiteCode());
		packageInfo.setCreateSiteName(account.getSiteName());
		packageInfo.setOperateTime(SimpleDateUtils.dateToString(new Date(), SimpleDateUtils.DATETIME_PATTERN));
		packageInfo.setOperateType(1);
		packageInfo.setOperateUserCode(account.getStaffId());
		packageInfo.setOperateUserName(account.getStaffName());
		packageInfo.setPackageCode(currentPackageNo);
		packageInfo.setReceiveSiteCode(boxInfo.getReceiveSiteCode());
		return JsonConverter.getFieldValueMap(packageInfo);
	}
	
	/**
	 * 提示信息[箱数，运单数，包裹数]
	 * @param formatArgs
	 */
	protected void setPrompt(Object...formatArgs){
		textPromptContent.setText(getResourcesString(R.string.smallSortingTallyPrompt, formatArgs));
	}
	
	public void sealBoxAction(){
		Intent sealBoxIntent = new Intent(this,SealBoxActivity.class);
		sealBoxIntent.putExtra("boxNo", boxInfo==null?null:boxInfo.getBoxCode());
		startActivity(sealBoxIntent);
	}
	
	public void differenceQueryAction(){
		Intent differenceQueryIntent = new Intent(this,QueryPackagePartActivity.class);
		differenceQueryIntent.putExtra("receiveSiteNo", boxInfo==null?null:String.valueOf(boxInfo.getReceiveSiteCode()));
		differenceQueryIntent.putExtra("boxNo", boxInfo==null?null:boxInfo.getBoxCode());
		startActivity(differenceQueryIntent);
	}
	
	private void packageAndWaybillCount(String boxCode){
		packageNoCount = 0;
		waybillNoCount=0;
		SortingTallyService sortingTallyService = SortingTallyService.getInstance(getApplicationContext());
		List<SortingTally> sortingTallyList = sortingTallyService.findSortingTallyList(boxCode);
		if(sortingTallyList!=null){
			for (SortingTally sortingTally : sortingTallyList) {
				if(Confirmation.isPackNo(sortingTally.getPackageCode())){
					packageNoCount++;
				}else if(Confirmation.isWaybillNo(sortingTally.getPackageCode())){
					waybillNoCount++;
				}
			}
		}
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
	 * 返回当前alert标志
	 * @return
	 */
	public int getAlertType() {
		return alertType;
	}
	/**
	 * 设置当前alert标志
	 * @param alertType
	 */
	public void setAlertType(int alertType) {
		this.alertType = alertType;
	}
	/**
	 * 保存分拣信息
	 * @return
	 */
	private boolean saveSortingTally(){
		Account account = JDApplication.getInstance().getAccount();
		SortingTally sortingTally = new SortingTally();
		sortingTally.setBoxCode(currentBoxNo);
		sortingTally.setBusinessType(getProperty(this).getSortingType());
		sortingTally.setFeatureType(0);
		sortingTally.setIsCancel(0);
		sortingTally.setIsLoss(0);
		sortingTally.setOperateTime(SimpleDateUtils.dateToString(new Date(), SimpleDateUtils.DATETIME_PATTERN));
		sortingTally.setPackageCode(currentPackageNo);
		sortingTally.setReceiveSiteCode(boxInfo.getReceiveSiteCode());
		sortingTally.setReceiveSiteName(boxInfo.getReceiveSiteName());
		sortingTally.setSiteCode(account.getSiteCode());
		sortingTally.setSiteName(account.getSiteName());
		sortingTally.setUserCode(account.getStaffId());
		sortingTally.setUserName(account.getStaffName());
		SortingTallyService sortingTallyService = SortingTallyService.getInstance(getApplicationContext());
		try {
			if(sortingTallyService.save(sortingTally)==1){
				return true;
			}
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void handlerSuccessResult(Message message) {
		
	}

	@Override
	public void handlerFailureResult(Message message) {
		
	}

}
