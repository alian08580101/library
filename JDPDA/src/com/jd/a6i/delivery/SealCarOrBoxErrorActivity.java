package com.jd.a6i.delivery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.jd.a6i.JDApplication;
import com.jd.a6i.JDBaseActivity;
import com.jd.a6i.R;
import com.jd.a6i.common.ConstantUtils;
import com.jd.a6i.db.pojo.Account;
import com.jd.a6i.db.pojo.ErrorType;
import com.jd.a6i.db.pojo.MenuItem;
import com.jd.a6i.db.pojo.SealBoxError;
import com.jd.a6i.db.pojo.SealCarError;
import com.jd.a6i.db.service.ErrorTypeService;
import com.jd.a6i.db.service.SealCarErrorService;
import com.jd.a6i.db.service.SealBoxErrorService;
import com.jd.a6i.view.DropDownView;
import com.jd.a6i.view.TextEditView;
import com.whl.dao.common.DaoException;
import com.whl.utils.SimpleDateUtils;

public class SealCarOrBoxErrorActivity extends JDBaseActivity implements OnClickListener{
	private TextEditView jdCarNo;
	private TextEditView jdBoxNo;
	private TextEditView jdSealCarNo;
	private TextEditView jdSealBoxNo;
	private EditText editCarNo;
	private EditText editBoxNo;
	private EditText editSealCarNo;
	private EditText editSealBoxNo;
	private DropDownView dropErrorType;
	private Button buttonUpload;
	
	private String pleaseSelect;
	
	public SealCarOrBoxErrorActivity(){
		prepareLayout(R.layout.seal_car_or_box_error);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		jdCarNo = (TextEditView) findViewById(R.id.jdCarNo);
		jdBoxNo = (TextEditView) findViewById(R.id.jdBoxNo);
		jdSealCarNo = (TextEditView) findViewById(R.id.jdSealCarNo);
		jdSealBoxNo = (TextEditView) findViewById(R.id.jdSealBoxNo);
		editCarNo = (EditText) jdCarNo.findViewById(R.id.editText);
		editBoxNo = (EditText) jdBoxNo.findViewById(R.id.editText);
		editSealCarNo = (EditText) jdSealCarNo.findViewById(R.id.editText);
		editSealBoxNo = (EditText) jdSealBoxNo.findViewById(R.id.editText);
		dropErrorType = (DropDownView) findViewById(R.id.dropErrorType);
		buttonUpload = (Button) findViewById(R.id.buttonUpload);
		
		editCarNo.setEnabled(false);
		editSealCarNo.setEnabled(false);
		
		pleaseSelect = getResources().getString(R.string.pleaseSelectErrorType);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		if("sealCarError".equals(getIntent().getStringExtra("sealType"))){
			textTitle.setText("封车异常处理");
			jdSealCarNo.setVisibility(View.VISIBLE);
			jdCarNo.setVisibility(View.VISIBLE);
			jdSealBoxNo.setVisibility(View.GONE);
			jdBoxNo.setVisibility(View.GONE);
			if(getIntent()!=null){
				String carNo = getIntent().getStringExtra("carNo");
				String sealCarNo = getIntent().getStringExtra("sealCarNo");
				editCarNo.setText(carNo);
				editSealCarNo.setText(sealCarNo);
			}
		}else if("sealBoxError".equals(getIntent().getStringExtra("sealType"))){
			textTitle.setText("封箱异常处理");
			jdSealBoxNo.setVisibility(View.VISIBLE);
			jdBoxNo.setVisibility(View.VISIBLE);
			jdSealCarNo.setVisibility(View.GONE);
			jdCarNo.setVisibility(View.GONE);
			if(getIntent()!=null){
				String boxNo = getIntent().getStringExtra("boxNo");
				String sealBoxNo = getIntent().getStringExtra("sealBoxNo");
				editBoxNo.setText(boxNo);
				editSealBoxNo.setText(sealBoxNo);
			}
		}
		
		dropErrorType.setDropList(loadErrorType(ConstantUtils.TYPE_GROUP_SEAL));
		
		
		buttonUpload.setOnClickListener(this);
	}
	
	protected List<String> loadErrorType(int typeGroup){
		ErrorTypeService errorTypeService = ErrorTypeService.getErrorTypeService(this);
		List<ErrorType> errorTypeList = errorTypeService.findErrorTypeList(typeGroup);
		List<String> errorList = new ArrayList<String>();
		errorList.add(pleaseSelect);
		for (ErrorType errorType : errorTypeList) {
			errorList.add(errorType.getTypeName());
		}
		return errorList;
	}
	
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.buttonUpload){
			if("sealCarError".equals(getIntent().getStringExtra("sealType"))){
				if(saveSealCarError()){
					this.finish();
				}
			}else if("sealBoxError".equals(getIntent().getStringExtra("sealType"))){
				if(saveSealBoxError()){
					this.finish();
				}
			}
			
		}
	}

	private boolean saveSealCarError() {
		if(checkErrorType()){
			Account account = JDApplication.getInstance().getAccount();
			SealCarError sealCarError = new SealCarError();
			sealCarError.setBusinessType(20);
			sealCarError.setCarCode(getIntent().getStringExtra("carNo"));
			sealCarError.setOperateTime(SimpleDateUtils.dateToString(new Date(), SimpleDateUtils.DATETIME_PATTERN));
			sealCarError.setShieldsCode(getIntent().getStringExtra("sealCarNo"));
			sealCarError.setShieldsError(dropErrorType.getEditContent());
			sealCarError.setSiteCode(account.getSiteCode());
			sealCarError.setSiteName(account.getSiteName());
			sealCarError.setUploadStatus(ConstantUtils.UPLOAD_0);
			sealCarError.setUserCode(account.getStaffId());
			sealCarError.setUserName(account.getStaffName());
			SealCarErrorService sealCarErrorService = SealCarErrorService.getInstance(this);
			try {
				if(sealCarErrorService.save(sealCarError)==1){
					return true;
				}
			} catch (DaoException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	private boolean saveSealBoxError() {
		if(checkErrorType()){
			Account account = JDApplication.getInstance().getAccount();
			SealBoxError sealBoxError = new SealBoxError();
			sealBoxError.setBusinessType(20);
			sealBoxError.setBoxCode(getIntent().getStringExtra("boxNo"));
			sealBoxError.setOperateTime(SimpleDateUtils.dateToString(new Date(), SimpleDateUtils.DATETIME_PATTERN));
			sealBoxError.setShieldsCode(getIntent().getStringExtra("sealBoxNo"));
			sealBoxError.setShieldsError(dropErrorType.getEditContent());
			sealBoxError.setSiteCode(account.getSiteCode());
			sealBoxError.setSiteName(account.getSiteName());
			sealBoxError.setUploadStatus(ConstantUtils.UPLOAD_0);
			sealBoxError.setUserCode(account.getStaffId());
			sealBoxError.setUserName(account.getStaffName());
			SealBoxErrorService sealBoxErrorService = SealBoxErrorService.getInstance(this);
			try {
				if(sealBoxErrorService.save(sealBoxError)==1){
					return true;
				}
			} catch (DaoException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	protected boolean checkErrorType(){
		if(dropErrorType.getEditContent()==null||
				   "".equals(dropErrorType.getEditContent())||
				   pleaseSelect.equals(dropErrorType.getEditContent())){
					baseToast(R.string.selectErrorTypePrompt);
					return false;
				}
		return true;
	}

}
