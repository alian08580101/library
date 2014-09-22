package com.jd.a6i.task;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.Request.Method;
import com.jd.a6i.JDApplication;
import com.jd.a6i.R;
import com.jd.a6i.common.Clock;
import com.jd.a6i.common.ConstantUtils;
import com.jd.a6i.common.JsonConverter;
import com.jd.a6i.common.PromptToast;
import com.jd.a6i.common.PropertyUtil;
import com.jd.a6i.common.StatusBarBroadcastReceiver;
import com.jd.a6i.db.pojo.Delivery;
import com.jd.a6i.db.pojo.Inspection;
import com.jd.a6i.db.pojo.SealBoxError;
import com.jd.a6i.db.pojo.SealBoxInfo;
import com.jd.a6i.db.pojo.SealCarError;
import com.jd.a6i.db.pojo.SortingTally;
import com.jd.a6i.db.pojo.UploadRequest;
import com.jd.a6i.db.service.DeliveryService;
import com.jd.a6i.db.service.InspectionService;
import com.jd.a6i.db.service.SealBoxErrorService;
import com.jd.a6i.db.service.SealBoxInfoService;
import com.jd.a6i.db.service.SealCarErrorService;
import com.jd.a6i.db.service.SortingTallyService;
import com.jd.a6i.volley.BaseRequest;
import com.jd.a6i.volley.UploadResultRequest;
import com.jd.a6i.volley.VolleyClient;
import com.whl.dao.common.DaoException;
import com.whl.utils.SimpleDateUtils;

public class UploadBackgroundTask extends BackgroundTaskBase {
	public static final String TAG = "UploadBackgroundTask";
	public static final int UPLOAD_SEAL_CAR_ERROR = 0;
	public static final int UPLOAD_SEAL_BOX_ERROR = 1;
	public static final int UPLOAD_INSPECTION = 2;
	public static final int UPLOAD_SORTING_TALLY = 3;
	public static final int UPLOAD_DELIVERY = 4;
	public static final int UPLOAD_SEAL_BOX_INFO = 5;
	
	private static final int UPLOAD_START = 0;
	private static final int UPLOAD_FINISH = 1;
	private static final int INTERVAL = PropertyUtil.getProperty(JDApplication.getInstance()).getUploadIntervalTime();
	private static long lastExecuteTime;
	
	private Logger logger = Logger.getLogger(getClass());
	private String uploadTaskName;
	private UploadHandler handler = null;
	private JDApplication jdApplication = null;
	
	public UploadBackgroundTask(Context applicationContext) {
		super(applicationContext);
		if(handler==null){
			handler = new UploadHandler();
		}
		if(jdApplication==null){
			jdApplication = JDApplication.getInstance();
		}
	}

	@Override
	public void execute() {
		/*if (!(Clock.now().minusMinutes(INTERVAL).getMillis() > lastExecuteTime)) {
            return;
        }
		lastExecuteTime = Clock.now().getMillis();*/
		logger.debug("upload task from device");
		startUpload();
	}
	
	private void startUpload(){
		VolleyClient volleyClient = VolleyClient.getVolleyClient(context);
		volleyClient.addRequest(uploadSealCarError(UPLOAD_START));
		volleyClient.addRequest(uploadSealBoxError(UPLOAD_START));
		volleyClient.addRequest(uploadInspection(UPLOAD_START));
		volleyClient.addRequest(uploadSortingTally(UPLOAD_START));
		volleyClient.addRequest(uploadDelivery(UPLOAD_START));
		volleyClient.addRequest(uploadSealBoxInfo(UPLOAD_START));
		volleyClient.startRequest();
	}
	
	private UploadResultRequest uploadSealCarError(int process){
		logger.debug("upload task SealCarError from device");
		SealCarErrorService sealCarErrorService = SealCarErrorService.getInstance(jdApplication);
		List<SealCarError> sealCarErrorList = sealCarErrorService.findSealCarErrorList(ConstantUtils.UPLOAD_0);
		if (sealCarErrorList != null && sealCarErrorList.size() > 0) {
			if (process == UPLOAD_START) {
				logTime("uploadSealCarError");
				SealCarError[] sealCarErrorArray = new SealCarError[sealCarErrorList.size()];
				sealCarErrorList.toArray(sealCarErrorArray);
				UploadRequest uploadRequest = new UploadRequest();
				uploadRequest.setBody(JsonConverter.convertObjectToJson(sealCarErrorArray));
				uploadRequest.setBoxCode("");
				uploadRequest.setType(ConstantUtils.UPLOAD_SEAL_CAR_ERROR_TYPE);
				uploadRequest.setKeyword1(String.valueOf(sealCarErrorList.get(0).getSiteCode()));
				uploadRequest.setKeyword2(sealCarErrorList.get(0).getCarCode());
				uploadRequest.setReceiveSiteCode(sealCarErrorList.get(0).getSiteCode());
				uploadRequest.setSiteCode(sealCarErrorList.get(0).getSiteCode());

				return getUploadResultRequest(uploadRequest,UPLOAD_SEAL_CAR_ERROR);
			} else if (process == UPLOAD_FINISH) {
				for (SealCarError sealCarError : sealCarErrorList) {
					sealCarError.setUploadStatus(ConstantUtils.UPLOAD_1);
					try {
						sealCarErrorService.update(sealCarError);
					} catch (DaoException e) {
						e.printStackTrace();
					}
				}
				pushNotification(R.string.uploadSealCarError,sealCarErrorList.size());
				//pushNotification(R.string.uploadSealCarError,UPLOAD_SEAL_CAR_ERROR,sealCarErrorList,SealCarError[].class);
			}
		}
		return null;
	}
	
	private UploadResultRequest uploadSealBoxError(int process){
		logger.debug("upload task SealBoxError from device");
		SealBoxErrorService sealBoxErrorService = SealBoxErrorService.getInstance(jdApplication);
		List<SealBoxError> sealBoxErrorList = sealBoxErrorService.findSealBoxErrorList(ConstantUtils.UPLOAD_0);
		if (sealBoxErrorList != null && sealBoxErrorList.size() > 0) {
			if (process == UPLOAD_START) {
				logTime("uploadSealBoxError");
				SealBoxError[] sealBoxErrorArray = new SealBoxError[sealBoxErrorList.size()];
				sealBoxErrorList.toArray(sealBoxErrorArray);
				UploadRequest uploadRequest = new UploadRequest();
				uploadRequest.setBody(JsonConverter.convertObjectToJson(sealBoxErrorArray));
				uploadRequest.setBoxCode("");
				uploadRequest.setType(ConstantUtils.UPLOAD_SEAL_CAR_ERROR_TYPE);
				uploadRequest.setKeyword1(String.valueOf(sealBoxErrorList.get(0).getSiteCode()));
				uploadRequest.setKeyword2(sealBoxErrorList.get(0).getBoxCode());
				uploadRequest.setReceiveSiteCode(sealBoxErrorList.get(0).getSiteCode());
				uploadRequest.setSiteCode(sealBoxErrorList.get(0).getSiteCode());

				return getUploadResultRequest(uploadRequest,UPLOAD_SEAL_BOX_ERROR);
			} else if (process == UPLOAD_FINISH) {
				for (SealBoxError sealBoxError : sealBoxErrorList) {
					sealBoxError.setUploadStatus(ConstantUtils.UPLOAD_1);
					try {
						sealBoxErrorService.update(sealBoxError);
					} catch (DaoException e) {
						e.printStackTrace();
					}
				}
				pushNotification(R.string.uploadSealBoxError,sealBoxErrorList.size());
				//pushNotification(R.string.uploadSealBoxError,UPLOAD_SEAL_BOX_ERROR,sealBoxErrorList,SealBoxError[].class);
			}
		}
		return null;
	}

	private UploadResultRequest uploadInspection(int process){
		logger.debug("upload task Inpection from device");
		InspectionService inspectionService = InspectionService.getInstance(jdApplication);
		List<Inspection> inspectionList = inspectionService.findAllInspectionType("0");
		if (inspectionList != null && inspectionList.size() > 0) {
			if (process == UPLOAD_START) {
				logTime("uploadInpection");
				UploadRequest uploadRequest = new UploadRequest();
				uploadRequest.setType(1130);
				uploadRequest.setSiteCode(inspectionList.get(0).getSiteCode());
				uploadRequest.setKeyword1(String.valueOf(inspectionList.get(0).getSiteCode()));
				uploadRequest.setKeyword2(inspectionList.get(0).getBoxCode());
				uploadRequest.setBoxCode("");
				uploadRequest.setReceiveSiteCode(inspectionList.get(0).getSiteCode());
				Inspection[] inspectionArray = new Inspection[inspectionList.size()];
				inspectionList.toArray(inspectionArray);
				uploadRequest.setBody(JsonConverter.convertObjectToJson(inspectionArray));

				return getUploadResultRequest(uploadRequest,UPLOAD_INSPECTION);
			} else if (process == UPLOAD_FINISH) {
				for (Inspection inspection : inspectionList) {
					inspection.setStateCode(ConstantUtils.UPLOAD_1);
					try {
						inspectionService.update(inspection);
					} catch (DaoException e) {
						e.printStackTrace();
					}
				}
				pushNotification(R.string.uploadInspection,inspectionList.size());
				//pushNotification(R.string.uploadInspection,UPLOAD_INSPECTION,inspectionList,Inspection[].class);
			}
		}
		return null;
	}
	
	private UploadResultRequest uploadSortingTally(int process){
		logger.debug("upload task SortingTally from device");
		SortingTallyService sortingTallyService = SortingTallyService.getInstance(jdApplication);
		List<SortingTally> sortingTallyList = sortingTallyService.findSortingTallyList(ConstantUtils.UPLOAD_0);
		if (sortingTallyList != null && sortingTallyList.size() > 0) {
			if (process == UPLOAD_START) {
				logTime("uploadSortingTally");
				SortingTally[] sortingTallyArray = new SortingTally[sortingTallyList.size()];
				sortingTallyList.toArray(sortingTallyArray);
				UploadRequest uploadRequest = new UploadRequest();
				uploadRequest.setBody(JsonConverter.convertObjectToJson(sortingTallyArray));
				uploadRequest.setType(ConstantUtils.UPLOAD_SORTING_TALLY_TYPE);
				uploadRequest.setSiteCode(sortingTallyList.get(0).getSiteCode());
				uploadRequest.setKeyword1(String.valueOf(sortingTallyList.get(0).getSiteCode()));
				uploadRequest.setKeyword2(sortingTallyList.get(0).getBoxCode());
				uploadRequest.setReceiveSiteCode(sortingTallyList.get(0).getReceiveSiteCode());
				uploadRequest.setBoxCode(sortingTallyList.get(0).getBoxCode());

				return getUploadResultRequest(uploadRequest,UPLOAD_SORTING_TALLY);
			} else if (process == UPLOAD_FINISH) {
				for (SortingTally sortingTally : sortingTallyList) {
					sortingTally.setUploadStatus(ConstantUtils.UPLOAD_1);
					try {
						sortingTallyService.update(sortingTally);
					} catch (DaoException e) {
						e.printStackTrace();
					}
				}
				pushNotification(R.string.uploadSortingTally,sortingTallyList.size());
				//pushNotification(R.string.uploadSortingTally,UPLOAD_SORTING_TALLY,sortingTallyList,SortingTally[].class);
			}
		}
		return null;
	}
	
	private UploadResultRequest uploadDelivery(int process){
		logger.debug("upload task Delivery from device");
		DeliveryService deliveryService = DeliveryService.getInstance(jdApplication);
		List<Delivery> deliveryList = deliveryService.findDeliveryList(ConstantUtils.UPLOAD_0);
		if (deliveryList != null && deliveryList.size() > 0) {
			if (process == UPLOAD_START) {
				logTime("uploadDelivery");
				Delivery[] deliveryArray = new Delivery[deliveryList.size()];
				deliveryList.toArray(deliveryArray);
				UploadRequest uploadRequest = new UploadRequest();
				uploadRequest.setBody(JsonConverter.convertObjectToJson(deliveryArray));
				uploadRequest.setBoxCode("");
				uploadRequest.setKeyword1(String.valueOf(deliveryList.get(0).getSiteCode()));
				uploadRequest.setKeyword2(deliveryList.get(0).getPackOrBox());
				uploadRequest.setSiteCode(deliveryList.get(0).getSiteCode());
				uploadRequest.setType(ConstantUtils.UPLOAD_DELIVERY_TYPE);
				uploadRequest.setReceiveSiteCode(deliveryList.get(0).getSiteCode());

				return getUploadResultRequest(uploadRequest,UPLOAD_DELIVERY);
			} else if (process == UPLOAD_FINISH) {
				for (Delivery delivery : deliveryList) {
					delivery.setUploadStatus(ConstantUtils.UPLOAD_1);
					try {
						deliveryService.update(delivery);
					} catch (DaoException e) {
						e.printStackTrace();
					}
				}
				pushNotification(R.string.uploadDelivery, deliveryList.size());
				//pushNotification(R.string.uploadDelivery, UPLOAD_DELIVERY,deliveryList,Delivery[].class);
			}
		}
		return null;
	}
	
	private UploadResultRequest uploadSealBoxInfo(int process){
		logger.debug("upload task SealBoxInfo from device");
		SealBoxInfoService sealBoxInfoService = SealBoxInfoService.getInstance(jdApplication);
		List<SealBoxInfo> sealBoxInfoList = sealBoxInfoService.findSealBoxInfoList(ConstantUtils.UPLOAD_0);
		if (sealBoxInfoList != null && sealBoxInfoList.size() > 0) {
			if (process == UPLOAD_START) {
				logTime("uploadSealBoxInfo");
				SealBoxInfo[] sealBoxInfoArray = new SealBoxInfo[sealBoxInfoList.size()];
				sealBoxInfoList.toArray(sealBoxInfoArray);
				UploadRequest uploadRequest = new UploadRequest();
				uploadRequest.setBody(JsonConverter.convertObjectToJson(sealBoxInfoArray));
				uploadRequest.setBoxCode("");
				uploadRequest.setKeyword1(String.valueOf(sealBoxInfoList.get(0).getSiteCode()));
				uploadRequest.setKeyword2(sealBoxInfoList.get(0).getBoxCode());
				uploadRequest.setReceiveSiteCode(sealBoxInfoList.get(0).getSiteCode());
				uploadRequest.setType(ConstantUtils.UPLOAD_SEAL_BOX_INFO_TYPE);
				uploadRequest.setSiteCode(sealBoxInfoList.get(0).getSiteCode());

				return getUploadResultRequest(uploadRequest,UPLOAD_SEAL_BOX_INFO);
			} else if (process == UPLOAD_FINISH) {
				for (SealBoxInfo sealBoxInfo : sealBoxInfoList) {
					sealBoxInfo.setUploadStatus(ConstantUtils.UPLOAD_1);
					try {
						sealBoxInfoService.update(sealBoxInfo);
					} catch (DaoException e) {
						e.printStackTrace();
					}
				}
				pushNotification(R.string.uploadSealBoxInfo,sealBoxInfoList.size());
				//pushNotification(R.string.uploadSealBoxInfo,UPLOAD_SEAL_BOX_INFO, sealBoxInfoList,SealBoxInfo[].class);
			}
		}
		return null;
	}
	
	private void pushNotification(int stringId,int uploadCount){
		setUploadTaskName(context.getResources().getString(stringId));
		StatusBarBroadcastReceiver.sendBroadcastForUploadPendingCount(jdApplication,getUploadTaskName(),uploadCount);
	}
	
	private <T> void pushNotification(int stringId,int uploadType,List<T> list,Class<?> clazz){
		setUploadTaskName(context.getResources().getString(stringId));
		StatusBarBroadcastReceiver.sendBroadcastForUploadPendingCount(jdApplication,getUploadTaskName(),uploadType,JsonConverter.convertObjectToJson(list),clazz);
	}
	
	private UploadResultRequest getUploadResultRequest(UploadRequest uploadRequest,int uploadType){
		Map<String,Object> paramsMap = JsonConverter.getFieldValueMap(uploadRequest);
		//handler.setUploadType(uploadType);
		UploadResultRequest uploadResultRequest = new UploadResultRequest(Method.POST, handler, paramsMap,uploadType);
		return uploadResultRequest;
	}

	public String getUploadTaskName() {
		return uploadTaskName;
	}

	public void setUploadTaskName(String uploadTaskName) {
		this.uploadTaskName = uploadTaskName;
	}
	
	public void logTime(String upload){
		Log.d(TAG, "start upload:"+upload+"["+SimpleDateUtils.dateToString(new Date(), SimpleDateUtils.DATETIME_PATTERN)+"]");
	}
	
	class UploadHandler extends Handler {
		/*public static final int UPLOAD_SEAL_CAR_ERROR = 0;
		public static final int UPLOAD_SEAL_BOX_ERROR = 1;
		public static final int UPLOAD_INSPECTION = 2;
		public static final int UPLOAD_SORTING_TALLY = 3;
		public static final int UPLOAD_DELIVERY = 4;
		public static final int UPLOAD_SEAL_BOX_INFO = 5;*/
		
		//private int uploadType = -1;

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.arg1 == UPLOAD_SEAL_CAR_ERROR) {
				switch (msg.what) {
				case BaseRequest.REQUEST_SUCCESS:
					uploadSealCarError(UPLOAD_FINISH);
					break;
				case BaseRequest.REQUEST_FAILURE:
					PromptToast.prompt(msg.obj.toString());
					break;
				}
			} else if (msg.arg1 == UPLOAD_SEAL_BOX_ERROR) {
				switch (msg.what) {
				case BaseRequest.REQUEST_SUCCESS:
					uploadSealBoxError(UPLOAD_FINISH);
					break;
				case BaseRequest.REQUEST_FAILURE:
					PromptToast.prompt(msg.obj.toString());
					break;
				}
			} else if (msg.arg1 == UPLOAD_INSPECTION) {
				switch (msg.what) {
				case BaseRequest.REQUEST_SUCCESS:
					uploadInspection(UPLOAD_FINISH);
					break;
				case BaseRequest.REQUEST_FAILURE:
					PromptToast.prompt(msg.obj.toString());
					break;
				}
			}else if(msg.arg1 == UPLOAD_SORTING_TALLY){
				switch (msg.what) {
				case BaseRequest.REQUEST_SUCCESS:
					uploadSortingTally(UPLOAD_FINISH);
					break;
				case BaseRequest.REQUEST_FAILURE:
					PromptToast.prompt(msg.obj.toString());
					break;
				}
			}else if(msg.arg1 == UPLOAD_DELIVERY){
				switch(msg.what){
				case BaseRequest.REQUEST_SUCCESS:
					uploadDelivery(UPLOAD_FINISH);
					break;
				case BaseRequest.REQUEST_FAILURE:
					PromptToast.prompt(msg.obj.toString());
					break;
				}
			}else if(msg.arg1 == UPLOAD_SEAL_BOX_INFO){
				switch(msg.what){
				case BaseRequest.REQUEST_SUCCESS:
					uploadSealBoxInfo(UPLOAD_FINISH);
					break;
				case BaseRequest.REQUEST_FAILURE:
					PromptToast.prompt(msg.obj.toString());
					break;
				}
			}
		}

		/*public int getUploadType() {
			return uploadType;
		}

		public void setUploadType(int uploadType) {
			this.uploadType = uploadType;
		}*/
	}
}