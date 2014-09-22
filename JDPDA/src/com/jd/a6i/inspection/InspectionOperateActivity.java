package com.jd.a6i.inspection;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jd.a6i.JDApplication;
import com.jd.a6i.JDBaseActivity;
import com.jd.a6i.R;
import com.jd.a6i.common.Confirmation;
import com.jd.a6i.common.Confirmation.SortingType;
import com.jd.a6i.common.JsonConverter;
import com.jd.a6i.common.SharedPreferencesUtils;
import com.jd.a6i.db.pojo.ErrorType;
import com.jd.a6i.db.pojo.FastRefundInpection;
import com.jd.a6i.db.pojo.Inspection;
import com.jd.a6i.db.pojo.StationInfo;
import com.jd.a6i.db.service.ErrorTypeService;
import com.jd.a6i.db.service.InspectionService;
import com.jd.a6i.task.ScanSitecodeAsynTask;
import com.jd.a6i.task.SelfInpectionWaybillAsyncTask;
import com.jd.a6i.task.SortingCenterInpectionWaybilInterceptAsyncTask;
import com.jd.a6i.task.ThreeSideInpectionNewWaybillAsyncTask;
import com.jd.a6i.task.ThreeSideInpectionWaybillAsyncTask;
import com.jd.a6i.view.TextEditView;
import com.whl.dao.common.DaoException;

public class InspectionOperateActivity extends JDBaseActivity {

	private static final String TAG = "InspectionOperateActivity";
    
	private TextEditView threeSides = null;
	private TextEditView caseNo = null;
	private TextEditView waybillNo = null;
	private TextView currentwaybillNo = null;
	private Spinner exceptionType = null;
	private TextView tv_operate = null;
	private EditText threeSideEditText = null;
	private EditText caseNoEditText = null;
	private EditText waybillNoEditText = null;
	private Button nextBox =null;
	private TextView packageCountTv = null;
	private TextView siteName = null;
	
	private int ntype =0;
	private boolean bKeyState = false;
	private StationInfo scanSiteCode = null;
    public int wayBillNums=0;
    public int packNoNums=0;
    private List<String> waybilllists = new ArrayList<String>();;
    private List<String> packnolists = new ArrayList<String>();
    private boolean isFirst = true;
    private int sortingType = 131;//默认是三分验货
	private Handler inpectionHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch(msg.what){
			case SelfInpectionWaybillAsyncTask.SELFINPECTION_ERROR:
				if(msg.obj!=null&&!msg.obj.toString().equals("")){
					Toast.makeText(InspectionOperateActivity.this, msg.obj.toString(), Toast.LENGTH_LONG).show();
				}
				else{
					messagebox(R.string.networkError);
				}
				
				promptManager.playSound(1, 1);
				waybillNoEditText.requestFocus();
				break;
			case SelfInpectionWaybillAsyncTask.SELFINPECTION_SUCCESS:
				selfInpectionWaybillhandler();
				break;
			case ScanSitecodeAsynTask.SCANSITECODE_ERROR:
				promptManager.playSound(1, 1);
				if(msg.obj!=null&&!msg.obj.toString().equals("")){
					Toast.makeText(InspectionOperateActivity.this, msg.obj.toString(), Toast.LENGTH_LONG).show();
				}
				else{
					messagebox(R.string.networkError);
				}
				threeSideEditText.requestFocus();
				break;
			case ScanSitecodeAsynTask.SCANSITECODE_SUCCESS:
				StringBuffer message = new StringBuffer();
				scanSiteCode = (StationInfo)msg.obj;
				if(ntype==23){
					if(!Confirmation.isMatchSortingType(SortingType.THIRDPARTY,scanSiteCode.getDmsCode(),false,message)){
						messagebox(R.string.errorThridNo);
						threeSideEditText.requestFocus();
						promptManager.playSound(1, 1);
						return;
					}
				}
				else{
                    if(!Confirmation.isMatchSortingType(SortingType.SORTINGCENTER,scanSiteCode.getDmsCode(),false,message)){
                    	messagebox(R.string.errorSortingNo);
                    	threeSideEditText.requestFocus();
                    	promptManager.playSound(1, 1);
                    	return;
					}
				}
				siteName.setVisibility(View.VISIBLE);
				
				siteName.setText(scanSiteCode.getSiteName());
				waybillNoEditText.requestFocus();
				break;
			case ThreeSideInpectionWaybillAsyncTask.THREEINPECTION_ERROR:
				promptManager.playSound(1, 1);
				if(msg.obj!=null&&!msg.obj.toString().equals("")){
					Toast.makeText(InspectionOperateActivity.this, msg.obj.toString(), Toast.LENGTH_LONG).show();
				}
				else{
					messagebox(R.string.networkError);
				}
				waybillNoEditText.requestFocus();
				break;
			case ThreeSideInpectionWaybillAsyncTask.THREEINPECTION_SUCCESS:
				threeSideInpectionWaybillHandler();
				waybillNoEditText.requestFocus();
				break;
			case SortingCenterInpectionWaybilInterceptAsyncTask.SORTINGCENTERINTERCEPT_ERROR:
				if(msg.obj!=null&&!msg.obj.toString().equals("")){
					Toast.makeText(InspectionOperateActivity.this, msg.obj.toString(), Toast.LENGTH_LONG).show();
				}
				else{
					messagebox(R.string.networkError);
				}
				waybillNoEditText.requestFocus();
				break;
			case SortingCenterInpectionWaybilInterceptAsyncTask.SORTINGCENTERINTERCEPT_SUCCESS:
				threeSideInpectionWaybillHandler();
				waybillNoEditText.requestFocus();
				break;
			}
		}
		
	};
	
	public InspectionOperateActivity()
	{
		prepareLayout(R.layout.inspectionoperate_activity);
	}
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		threeSides = (TextEditView)findViewById(R.id.threeSidesNo);
		caseNo = (TextEditView)findViewById(R.id.caseNo);
		waybillNo = (TextEditView)findViewById(R.id.waybillNo);
		currentwaybillNo = (TextView)findViewById(R.id.currentWaybillNo);
		exceptionType = (Spinner)findViewById(R.id.spinnerExceptionType);
		tv_operate = (TextView)threeSides.findViewById(R.id.textView);
		siteName =(TextView)caseNo.findViewById(R.id.textView);
		
		threeSideEditText = (EditText)threeSides.findViewById(R.id.editText);
		caseNoEditText = (EditText)caseNo.findViewById(R.id.editText);
		waybillNoEditText = (EditText)waybillNo.findViewById(R.id.editText);
		packageCountTv = (TextView)findViewById(R.id.packageCount);
		nextBox = (Button)findViewById(R.id.nextBox);
		
		exceptionType.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if(isFirst){
					if(ntype ==21){
						caseNoEditText.requestFocus();
					}
					else{
						threeSideEditText.requestFocus();
					}
					isFirst = false;
				}
				else{
					waybillNoEditText.requestFocus();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				waybillNoEditText.requestFocus();
			}
			
		});

		
		
		nextBox.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				buttonClick();
			}
		});
		
		threeSideEditText.setOnKeyListener(new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if(keyCode == KeyEvent.KEYCODE_ENTER){
					if(event.getAction() == KeyEvent.ACTION_DOWN){
						return threeSideKeyDown();
					}
					else{
						return true;
					}
				}
				return false;
			}
		});
		caseNoEditText.setOnKeyListener(new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
					if(keyCode == KeyEvent.KEYCODE_ENTER)
					{
						if(event.getAction() == KeyEvent.ACTION_DOWN){
							return boxcodeKeyDown();
						}
						else
						{
							return !bKeyState;
						}	
						
					}
				

				return false;
			}
		});
		waybillNoEditText.setOnKeyListener(new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				
				if(keyCode == KeyEvent.KEYCODE_ENTER)
				{
					if(event.getAction()==KeyEvent.ACTION_DOWN){
						
						return waybillKeyDown();
					}
					else{
						return !bKeyState;
					}
				}
				
				return false;
			}
		});

		// 加载数据到spinner
		try {
			ErrorTypeService errorTypeService = ErrorTypeService.getErrorTypeService(this);
			List<ErrorType> errorTypeList = errorTypeService
					.findErrorType("130");
			List<String> typeNameList = new ArrayList<String>();
			typeNameList.add(getResources().getString(R.string.errorTypeCheck));

			for (ErrorType errorType : errorTypeList) {
				typeNameList.add(errorType.getTypeName().toString());
			}
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, typeNameList);
			adapter.setDropDownViewResource(R.layout.spinner_item);
			exceptionType.setAdapter(adapter);

		} catch (DaoException e) {
			// TODO: handle exception
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		LinearLayout.LayoutParams lp = null;
		Intent intent = getIntent();
		ntype = intent.getIntExtra("type", 0);
		switch(ntype){
		case 0:
			threeSides.setVisibility(View.GONE);
			textTitle.setText(R.string.selfInspection);
			ntype = 21;
			//caseNoEditText.requestFocus();
			break;
		case 1:
			//caseNo.setVisibility(View.INVISIBLE);
			caseNoEditText.setVisibility(View.INVISIBLE);
			siteName.setText("");
			siteName.setTextColor(R.color.red);
			lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			siteName.setLayoutParams(lp);
			
			textTitle.setText(R.string.threeInspection);
			tv_operate.setText(R.string.operateType_threeSide);
			ntype = 23;
			sortingType = 131;
			//threeSideEditText.requestFocus();
			break;
		case 2:
			//caseNo.setVisibility(View.GONE);
			caseNoEditText.setVisibility(View.INVISIBLE);
			siteName.setText("");
			siteName.setTextColor(R.color.red);
			lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			siteName.setLayoutParams(lp);
			
			textTitle.setText(R.string.sortingInspection);
			tv_operate.setText(R.string.operateType_sortingCenter);
			ntype = 22;
			sortingType = 112;
			//threeSideEditText.requestFocus();
			break;
		default:
			break;
		}
		//textUser.setText(JDApplication.getInstance().getAccount().getErpAccount());
		packageCountTv.setText(String.format(getResources().getString(R.string.packageCount),wayBillNums,packNoNums));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.inspection_operate, menu);
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_F1){
			buttonClick();
			return true;
		}
			
		return super.onKeyDown(keyCode, event);
	}

	private void messagebox(int id){
		Toast.makeText(InspectionOperateActivity.this, getResources().getText(id), Toast.LENGTH_LONG).show();
	}
	
	private boolean waybillKeyDown(){
		if(ntype!=21){
			if(threeSideEditText.getText().toString().equals("")){
				promptManager.playSound(1, 1);
				if(ntype==23){
					messagebox(R.string.noThreeSide);
					
				}
				else{
					messagebox(R.string.noSorting);
				}
				threeSideEditText.requestFocus();
				return true;
			}
		}
		if(waybillNoEditText.getText()==null||"".equals(waybillNoEditText.getText().toString())){
			messagebox(R.string.noWaybillNo);
			promptManager.playSound(1, 1);
			waybillNoEditText.requestFocus();
			bKeyState = false;
			return bKeyState;
		}
		else{
			bKeyState = Confirmation.isWaybillNo(waybillNoEditText.getText().toString())||Confirmation.isPackNo(waybillNoEditText.getText().toString())||Confirmation.isSurfaceNo(waybillNoEditText.getText().toString());
			if(!bKeyState){
				messagebox(R.string.waybillNoError);
				promptManager.playSound(1, 1);
				waybillNoEditText.requestFocus();
			}
			else{
				Inspection inspection = InspectionService.getInstance(getApplicationContext()).findUniqueInspection(
						waybillNoEditText.getText().toString(),
						String.valueOf(ntype));
				if (inspection == null) {
					if (ntype == 21) {

						SelfInpectionWaybillAsyncTask selfInpectionWaybillAsyncTask = new SelfInpectionWaybillAsyncTask(
								getApplicationContext(), inpectionHandler);
						selfInpectionWaybillAsyncTask.execute(waybillNoEditText
								.getText().toString());

					} else if (ntype == 23) {

						int nserviceType = SharedPreferencesUtils
								.getKstkService(getApplicationContext());
						if (nserviceType > 0) {
							ThreeSideInpectionNewWaybillAsyncTask threeSideInpectionNewWaybillAsyncTask = new ThreeSideInpectionNewWaybillAsyncTask(
									getApplicationContext(), inpectionHandler);
							FastRefundInpection fastRefundInpection = new FastRefundInpection();
							fastRefundInpection
									.setWaybillCode(waybillNoEditText.getText()
											.toString());
							fastRefundInpection.setUserName(JDApplication.getInstance().getAccount()
									.getStaffName());
							fastRefundInpection.setUserCode(JDApplication.getInstance().getAccount()
									.getStaffId());
							fastRefundInpection.setSiteName(JDApplication.getInstance().getAccount()
									.getSiteName());
							fastRefundInpection.setSiteCode(JDApplication.getInstance().getAccount()
									.getSiteCode());

							SimpleDateFormat formatter = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss");
							Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
							fastRefundInpection.setOperateTime(formatter
									.format(curDate));

							threeSideInpectionNewWaybillAsyncTask
									.execute(JsonConverter
											.convertObjectToJson(fastRefundInpection));
						} else {
							ThreeSideInpectionWaybillAsyncTask threeSideInpectionWaybillAsyncTask = new ThreeSideInpectionWaybillAsyncTask(
									getApplicationContext(), inpectionHandler);
							threeSideInpectionWaybillAsyncTask
									.execute(waybillNoEditText.getText()
											.toString());
						}

					} else if (ntype == 22) {
						SortingCenterInpectionWaybilInterceptAsyncTask sortingCenterInpectionWaybilInterceptAsyncTask = new SortingCenterInpectionWaybilInterceptAsyncTask(getApplicationContext(), inpectionHandler);
						FastRefundInpection fastRefundInpection = new FastRefundInpection();
						fastRefundInpection
								.setWaybillCode(waybillNoEditText.getText()
										.toString());
						fastRefundInpection.setUserName(JDApplication.getInstance().getAccount()
								.getStaffName());
						fastRefundInpection.setUserCode(JDApplication.getInstance().getAccount()
								.getStaffId());
						fastRefundInpection.setSiteName(JDApplication.getInstance().getAccount()
								.getSiteName());
						fastRefundInpection.setSiteCode(JDApplication.getInstance().getAccount()
								.getSiteCode());

						SimpleDateFormat formatter = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
						fastRefundInpection.setOperateTime(formatter
								.format(curDate));

						
						sortingCenterInpectionWaybilInterceptAsyncTask.execute(waybillNoEditText.getText()
								.toString(),JsonConverter
								.convertObjectToJson(fastRefundInpection));
					}
				} else {
					promptManager.playSound(1, 1);
					Toast.makeText(
							getApplicationContext(),
							String.format(
									getResources().getString(
											R.string.haveInpection),
									waybillNoEditText.getText().toString()),
							Toast.LENGTH_SHORT).show();
					waybillNoEditText.requestFocus();
					bKeyState = false;

				}
				
				
			}
			
			return !bKeyState;
		}
	}
	
	private boolean boxcodeKeyDown(){
		if(caseNoEditText.getText()==null||"".equals(caseNoEditText.getText().toString()))
		{
			bKeyState = true;
			return bKeyState;
		}
		else
		{
			bKeyState = Confirmation.isBoxCode(caseNoEditText.getText().toString());
			if(!bKeyState){
				promptManager.playSound(1, 1);
				messagebox(R.string.boxCodeError);
			}
			else{
			    waybilllists = new ArrayList<String>();;
			    packnolists = new ArrayList<String>();
				getBoxCount();
			}
			return !bKeyState;
		}
	}
	
	private boolean threeSideKeyDown(){
		if(threeSideEditText.getText().toString().equals("")){
			promptManager.playSound(1, 1);
			if(ntype==23){
				messagebox(R.string.noThreeSide);
				
			}
			else{
				messagebox(R.string.noSorting);
			}
			threeSideEditText.requestFocus();
		}
		else{
			ScanSitecodeAsynTask scanSitecodeAsynTask = new ScanSitecodeAsynTask(getApplicationContext(),inpectionHandler);
			scanSitecodeAsynTask.execute(threeSideEditText.getText().toString());
		}
	    return true;
	}
	
	private void selfInpectionWaybillhandler(){
		Inspection inspection;
		List<Inspection> listInspection = null;
		int packCount=0,waybillCount = 0;

		inspection = new Inspection();
		inspection.setSealBoxCode("");
		inspection.setBoxCode(caseNoEditText.getText().toString());
		inspection.setBusinessType(20);
		if(exceptionType.getSelectedItemId()>0){
			inspection.setExceptionType(exceptionType.getSelectedItem().toString());
		}
		else{
			inspection.setExceptionType("");
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		inspection.setOperateTime(formatter.format(curDate));
		inspection.setOperateType(ntype);
		inspection.setPackageBarOrWaybillCode(waybillNoEditText.getText()
				.toString());
		inspection.setSiteCode(JDApplication.getInstance().getAccount().getSiteCode());
		inspection.setSiteName(JDApplication.getInstance().getAccount().getSiteName());
		inspection.setStateCode(0);
		inspection.setUserCode(JDApplication.getInstance().getAccount().getStaffId());
		inspection.setUserName(JDApplication.getInstance().getAccount().getStaffName());
        inspection.setReceiveSiteCode(0);
        
		try {
			InspectionService.getInstance(getApplicationContext()).save(inspection);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			messagebox(R.string.saveDataError);
			waybillNoEditText.requestFocus();
			return;
		}
		currentwaybillNo.setText(getResources().getString(
				R.string.currentWaybill)
				+ waybillNoEditText.getText().toString());

		//查询运单和包裹的数量
		Setcount();
		
		waybillNoEditText.setText("");
		waybillNoEditText.requestFocus();
	}
	
	private void threeSideInpectionWaybillHandler(){
		Inspection inspection = new Inspection();
		inspection.setSealBoxCode("");
		inspection.setBoxCode(caseNoEditText.getText().toString());
		inspection.setBusinessType(20);
		if(exceptionType.getSelectedItemId()>0){
			inspection.setExceptionType(exceptionType.getSelectedItem().toString());
		}
		else{
			inspection.setExceptionType("");
		}

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		inspection.setOperateTime(formatter.format(curDate));
		inspection.setOperateType(ntype);
		inspection.setPackageBarOrWaybillCode(waybillNoEditText.getText()
				.toString());
		inspection.setSiteCode(JDApplication.getInstance().getAccount().getSiteCode());
		inspection.setSiteName(JDApplication.getInstance().getAccount().getSiteName());
		inspection.setStateCode(0);
		inspection.setUserCode(JDApplication.getInstance().getAccount().getStaffId());
		inspection.setUserName(JDApplication.getInstance().getAccount().getStaffName());
		inspection.setReceiveSiteCode(scanSiteCode==null?0:scanSiteCode.getSiteCode());

		try {
			InspectionService.getInstance(getApplicationContext()).save(inspection);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		currentwaybillNo.setText(getResources().getString(
				R.string.currentWaybill)
				+ waybillNoEditText.getText().toString());

		// 查询运单和包裹的数量
		Setcount();

		waybillNoEditText.setText("");
		waybillNoEditText.requestFocus();
	}
	
	private void getBoxCount(){
	    int wayBillNums=0;
	    int packNoNums=0;
		List<Inspection> listInspection = InspectionService.getInstance(getApplicationContext()).findInspection(caseNoEditText.getText().toString(),String.valueOf(ntype));
		if(!(listInspection==null||listInspection.size()<=0)){
			for(Inspection inspection:listInspection){
				if(Confirmation.isPackNo(inspection.getPackageBarOrWaybillCode())){
					packNoNums++;
				}
				else{
					wayBillNums++;
				}
			}
		}
		packageCountTv.setText(String.format(getResources().getString(R.string.packageCount),wayBillNums,packNoNums));
	}
	
	private void Setcount(){
		if(Confirmation.isWaybillNo(waybillNoEditText.getText().toString())){
			if(!waybilllists.contains(waybillNoEditText.getText().toString())){
				waybilllists.add(waybillNoEditText.getText().toString());
			}
		}
		else{
			if(!packnolists.contains(waybillNoEditText.getText().toString())){
				packnolists.add(waybillNoEditText.getText().toString());
			}
		}
		int wayBillNumsstr = wayBillNums + waybilllists.size();
        int packNoNumsstr = packNoNums + packnolists.size();
        
        packageCountTv.setText(String.format(getResources().getString(R.string.packageCount),wayBillNumsstr,packNoNumsstr));
	}
	
	private void buttonClick(){
		if(waybillNoEditText.getText()!=null&&!"".equals(waybillNoEditText.getText().toString())){
			//waybillKeyDown();
		}
		
		if (ntype == 21) {
			caseNoEditText.setText("");
			waybillNoEditText.setText("");
			threeSideEditText.setText("");
			caseNoEditText.requestFocus();
			currentwaybillNo.setText(R.string.currentWaybill);
		}
		else if (ntype == 23) {
			siteName.setText("");
			caseNoEditText.setText("");
			waybillNoEditText.setText("");
			threeSideEditText.setText("");
			threeSideEditText.requestFocus();
			currentwaybillNo.setText(R.string.currentWaybill);
		}
		else if(ntype == 22){
			siteName.setText("");
			caseNoEditText.setText("");
			waybillNoEditText.setText("");
			threeSideEditText.setText("");
			threeSideEditText.requestFocus();
			currentwaybillNo.setText(R.string.currentWaybill);
		}
		exceptionType.setSelection(0);
		
//		AsynUploadLargeSortingInfo asynUploadLargeSortingInfo = new AsynUploadLargeSortingInfo();
//		AsynUploadInspectionData asynUploadInspectionData = new AsynUploadInspectionData();
//		asynUploadLargeSortingInfo.setType(1130);
//		asynUploadLargeSortingInfo.setSiteCode(account.getSiteCode());
//		asynUploadLargeSortingInfo.setKeyword1(Integer.toString(account.getSiteCode()));
//		asynUploadLargeSortingInfo.setKeyword2(caseNoEditText.getText().toString());
//		asynUploadLargeSortingInfo.setBoxCode("");
//		asynUploadLargeSortingInfo.setReceiveSiteCode(account.getSiteCode());
//		//给body赋值
//		asynUploadInspectionData.setSealBoxCode("");
//		asynUploadInspectionData.setBoxCode(caseNoEditText.getText().toString());
//		asynUploadInspectionData.setPackageBarOrWaybillCode(waybillNoEditText.getText().toString());
//		asynUploadInspectionData.setExceptionType(exceptionType.getSelectedItem().toString());
//		asynUploadInspectionData.setOperateType(21);
//		asynUploadInspectionData.setReceiveSiteCode(0);
//		asynUploadInspectionData.setId(2);
//		asynUploadInspectionData.setBusinessType(21);
//		asynUploadInspectionData.setUserCode(account.getStaffId());
//		asynUploadInspectionData.setUserName(account.getStaffName());
//		asynUploadInspectionData.setSiteCode(account.getSiteCode());
//		asynUploadInspectionData.setSiteName(account.getSiteName());
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HH:mm:ss");       
//        Date curDate = new Date(System.currentTimeMillis());//获取当前时间       
//		asynUploadInspectionData.setOperateTime(formatter.format(curDate));
//		
//		asynUploadLargeSortingInfo.setBody(JsonConverter.convertObjectToJson(asynUploadInspectionData));
//
//		String loginURI = ConstantUtils.BASE_ADDRESS+"tasks";
//		JSONObject result = HttpClientUtils.requestResult(HttpPost.METHOD_NAME, loginURI, JsonConverter.convertObjectToJson(asynUploadLargeSortingInfo)); 
	
	}
}
