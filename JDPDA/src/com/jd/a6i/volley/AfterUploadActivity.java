package com.jd.a6i.volley;

import java.util.List;

import android.os.Bundle;

import com.jd.a6i.JDApplication;
import com.jd.a6i.JDBaseActivity;
import com.jd.a6i.R;
import com.jd.a6i.common.JsonConverter;
import com.jd.a6i.common.StatusBarBroadcastReceiver;
import com.jd.a6i.common.StringUtils;
import com.jd.a6i.db.pojo.Delivery;
import com.jd.a6i.db.pojo.Inspection;
import com.jd.a6i.db.pojo.SealBoxError;
import com.jd.a6i.db.pojo.SealBoxInfo;
import com.jd.a6i.db.pojo.SealCarError;
import com.jd.a6i.db.pojo.SortingTally;
import com.jd.a6i.task.UploadBackgroundTask;

public class AfterUploadActivity extends JDBaseActivity{
	
	public AfterUploadActivity() {
		prepareLayout(R.layout.after_upload_activity);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String format = "ÒÑÉÏ´«-%s";
		int uploadType = getIntent().getIntExtra(StatusBarBroadcastReceiver.KEY_UPLOAD_TYPE, -1);
		String jsonList = JDApplication.getInstance().getJsonList();
		switch(uploadType){
		case UploadBackgroundTask.UPLOAD_DELIVERY:
			textTitle.setText(String.format(format, getResourcesString(R.string.uploadDelivery)));
			List<Delivery> deliveryList = JsonConverter.convertObjectListFromJson(jsonList, Delivery[].class);
			showUploadDelivery(deliveryList);
			break;
		case UploadBackgroundTask.UPLOAD_INSPECTION:
			textTitle.setText(String.format(format, getResourcesString(R.string.uploadInspection)));
			List<Inspection> inspectionList = JsonConverter.convertObjectListFromJson(jsonList, Inspection[].class);
			showUploadInspection(inspectionList);
			break;
		case UploadBackgroundTask.UPLOAD_SEAL_BOX_ERROR:
			textTitle.setText(String.format(format, getResourcesString(R.string.uploadSealBoxError)));
			List<SealBoxError> sealBoxErrorList = JsonConverter.convertObjectListFromJson(jsonList, SealBoxError[].class);
			showUploadSealBoxError(sealBoxErrorList);
			break;
		case UploadBackgroundTask.UPLOAD_SEAL_BOX_INFO:
			textTitle.setText(String.format(format, getResourcesString(R.string.uploadSealBoxInfo)));
			List<SealBoxInfo> sealBoxInfoList = JsonConverter.convertObjectListFromJson(jsonList, SealBoxInfo[].class);
			showUploadSealBoxInfo(sealBoxInfoList);
			break;
		case UploadBackgroundTask.UPLOAD_SEAL_CAR_ERROR:
			textTitle.setText(String.format(format, getResourcesString(R.string.uploadSealCarError)));
			List<SealCarError> sealCarErrorList = JsonConverter.convertObjectListFromJson(jsonList, SealCarError[].class);
			showUploadSealCarError(sealCarErrorList);
			break;
		case UploadBackgroundTask.UPLOAD_SORTING_TALLY:
			textTitle.setText(String.format(format, getResourcesString(R.string.uploadSortingTally)));
			List<SortingTally> sortingTallyList = JsonConverter.convertObjectListFromJson(jsonList, SortingTally[].class);
			showUploadSortingTally(sortingTallyList);
			break;
		}
	}
	
	private void showUploadDelivery(List<Delivery> deliveryList){
		
	}
	private void showUploadInspection(List<Inspection> inspectionList){
		
	}
	private void showUploadSealBoxError(List<SealBoxError> sealBoxErrorList){
		
	}
	private void showUploadSealCarError(List<SealCarError> sealCarErrorList){
		
	}
	private void showUploadSealBoxInfo(List<SealBoxInfo> sealBoxInfoList){
		
	}
	private void showUploadSortingTally(List<SortingTally> sortingTallyList){
		
	}
	
	
}
