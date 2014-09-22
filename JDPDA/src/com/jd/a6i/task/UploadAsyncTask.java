package com.jd.a6i.task;

import static com.jd.a6i.common.PropertyUtil.getProperty;

import java.util.Date;
import java.util.List;

import org.apache.http.client.methods.HttpPost;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.jd.a6i.JDApplication;
import com.jd.a6i.R;
import com.jd.a6i.common.ConstantUtils;
import com.jd.a6i.common.HttpClientUtils;
import com.jd.a6i.common.JsonConverter;
import com.jd.a6i.common.PropertyUtil;
import com.jd.a6i.common.StatusBarBroadcastReceiver;
import com.jd.a6i.db.pojo.Account;
import com.jd.a6i.db.pojo.AsynUploadLargeSortingInfo;
import com.jd.a6i.db.pojo.Delivery;
import com.jd.a6i.db.pojo.Inspection;
import com.jd.a6i.db.pojo.SealBoxError;
import com.jd.a6i.db.pojo.SealBoxInfo;
import com.jd.a6i.db.pojo.SealCarError;
import com.jd.a6i.db.pojo.SortingTally;
import com.jd.a6i.db.pojo.UploadRequest;
import com.jd.a6i.db.pojo.UploadResult;
import com.jd.a6i.db.service.DeliveryService;
import com.jd.a6i.db.service.InspectionService;
import com.jd.a6i.db.service.SealBoxErrorService;
import com.jd.a6i.db.service.SealBoxInfoService;
import com.jd.a6i.db.service.SealCarErrorService;
import com.jd.a6i.db.service.SortingTallyService;
import com.whl.dao.common.DaoException;
import com.whl.utils.SimpleDateUtils;
public class UploadAsyncTask extends JDBaseAsyncTask<String, Integer, Integer> {
	public static final String TAG = "UploadAsyncTask";
	public static final int UPLOAD_ERROR_SEAL_CAR = 0;
	public static final int UPLOAD_ERROR_SEAL_BOX = 1;
	
	private String uploadType;
	public UploadAsyncTask(Context context){
		super.context = context;
	}
	@Override
	protected Integer doInBackground(String... params) {
		try {
			if(upLoadInpection()>0){
				return upLoadInpection();
			}
			if(uploadSortingTally()>0){
				return uploadSortingTally();
			}
			if(uploadSortingTally()>0){
				return uploadSortingTally();
			}
			if(uploadSealBoxError()>0){
				return uploadSealBoxError();
			}
			if(uploadSealCarError()>0){
				return uploadSealCarError();
			}
			if(uploadDelivery()>0){
				return uploadDelivery();
			}
			if(uploadSealBoxInfo()>0){
				return uploadSealBoxInfo();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	
	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		if(result>0){
			StatusBarBroadcastReceiver.sendBroadcastForUploadPendingCount(context,getUploadType(),result);
		}
	}
	/***
	 * 异步上传车封异常数据
	 * @return
	 * @throws DaoException
	 */
	private Integer uploadSealCarError() throws DaoException{
		Account account = JDApplication.getInstance().getAccount();
		Integer uploadCount = 0;
		if(account!=null){
			logTime("uploadSealCarError");
			SealCarErrorService sealCarErrorService = SealCarErrorService.getInstance(context);
			List<SealCarError> sealCarErrorList = sealCarErrorService.findSealCarErrorList(ConstantUtils.UPLOAD_0);
			if(sealCarErrorList!=null&&sealCarErrorList.size()>0){
				SealCarError[] sealCarErrorArray = new SealCarError[sealCarErrorList.size()];
				sealCarErrorList.toArray(sealCarErrorArray);
				UploadRequest uploadRequest = new UploadRequest();
				uploadRequest.setBody(JsonConverter.convertObjectToJson(sealCarErrorArray));
				uploadRequest.setBoxCode("");
				uploadRequest.setType(ConstantUtils.UPLOAD_SEAL_CAR_ERROR_TYPE);
				uploadRequest.setKeyword1(String.valueOf(account.getCode()));
				uploadRequest.setKeyword2(sealCarErrorList.get(0).getCarCode());
				uploadRequest.setReceiveSiteCode(account.getSiteCode());
				uploadRequest.setSiteCode(account.getSiteCode());
				String json = JsonConverter.convertObjectToJson(uploadRequest);
				String result = HttpClientUtils.requestResult(HttpPost.METHOD_NAME,PropertyUtil.getProperty(context).getAddressUploadTasks(), json);
				if(result!=null){
					UploadResult uploadResult = JsonConverter.convertJsonToObject(result, UploadResult.class);
					if(uploadResult.getCode()==200){
						for (SealCarError sealCarError : sealCarErrorList) {
							sealCarError.setUploadStatus(ConstantUtils.UPLOAD_1);
							sealCarErrorService.update(sealCarError);
						}
						setUploadType(context.getResources().getString(R.string.uploadSealCarError));
						return uploadCount = sealCarErrorList.size();
					}
					return uploadCount;
				}
			}
		}
		return uploadCount;
	}
	
	/***
	 * 异步上传箱封异常数据
	 * @return
	 * @throws DaoException
	 */
	private Integer uploadSealBoxError() throws DaoException{
		Account account = JDApplication.getInstance().getAccount();
		int uploadCount = 0;
		if(account!=null){
			logTime("uploadSealBoxError");
			SealBoxErrorService sealBoxErrorService = SealBoxErrorService.getInstance(context);
			List<SealBoxError> sealBoxErrorList = sealBoxErrorService.findSealBoxErrorList(ConstantUtils.UPLOAD_0);
			if(sealBoxErrorList!=null&&sealBoxErrorList.size()>0){
				SealBoxError[] sealBoxErrorArray = new SealBoxError[sealBoxErrorList.size()];
				sealBoxErrorList.toArray(sealBoxErrorArray);
				UploadRequest uploadRequest = new UploadRequest();
				uploadRequest.setBody(JsonConverter.convertObjectToJson(sealBoxErrorArray));
				uploadRequest.setBoxCode("");
				uploadRequest.setType(ConstantUtils.UPLOAD_SEAL_CAR_ERROR_TYPE);
				uploadRequest.setKeyword1(String.valueOf(account.getCode()));
				uploadRequest.setKeyword2(sealBoxErrorList.get(0).getBoxCode());
				uploadRequest.setReceiveSiteCode(account.getSiteCode());
				uploadRequest.setSiteCode(account.getSiteCode());
				String json = JsonConverter.convertObjectToJson(uploadRequest);
				String result = HttpClientUtils.requestResult(HttpPost.METHOD_NAME, PropertyUtil.getProperty(context).getAddressUploadTasks(), json);
				if(result!=null){
					UploadResult uploadResult = JsonConverter.convertJsonToObject(result, UploadResult.class);
					if(uploadResult.getCode()==200){
						for (SealBoxError sealBoxError : sealBoxErrorList) {
							sealBoxError.setUploadStatus(ConstantUtils.UPLOAD_1);
							sealBoxErrorService.update(sealBoxError);
						}
						setUploadType(context.getResources().getString(R.string.uploadSealBoxError));
						return uploadCount = sealBoxErrorList.size();
					}
					return uploadCount;
				}
			}
		}
		return uploadCount;
	}
	

	private Integer upLoadInpection() throws DaoException,JSONException{
		int uploadCount = 0;
		List<Inspection> inpectionList = InspectionService.getInstance(context).findAllInspectionType("0");
		if (inpectionList!=null&&inpectionList.size()>0) {
			Log.d(TAG, "Inspection count:"+inpectionList.size());
			AsynUploadLargeSortingInfo asynUploadLargeSortingInfo = new AsynUploadLargeSortingInfo();
			asynUploadLargeSortingInfo.setType(1130);
			asynUploadLargeSortingInfo.setSiteCode(inpectionList.get(0)
					.getSiteCode());
			asynUploadLargeSortingInfo.setKeyword1(String.valueOf(inpectionList
					.get(0).getSiteCode()));
			asynUploadLargeSortingInfo.setKeyword2(inpectionList.get(0)
					.getBoxCode());
			asynUploadLargeSortingInfo.setBoxCode("");
			asynUploadLargeSortingInfo.setReceiveSiteCode(inpectionList.get(0)
					.getSiteCode());
			//给body赋值
			Inspection[] inspectionArray = new Inspection[inpectionList.size()];
			inpectionList.toArray(inspectionArray);
			asynUploadLargeSortingInfo.setBody(JsonConverter
					.convertObjectToJson(inspectionArray));
			String json = JsonConverter
					.convertObjectToJson(asynUploadLargeSortingInfo);
			String loginURI = PropertyUtil.getProperty(context).getAddressPort8080() + "tasks";
			String response = HttpClientUtils.requestResult(
					HttpPost.METHOD_NAME, loginURI, json);
			if(response==null){
				return uploadCount;
			}
			JSONObject result = new JSONObject(response);
			if (result != null) {
				int code = 0;
				try {
					code = result.getInt("code");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (code == 200) {
					for (Inspection inspection : inpectionList) {
						inspection.setStateCode(ConstantUtils.UPLOAD_1);
						InspectionService.getInstance(context).update(inspection);
					}
					setUploadType(context.getResources().getString(R.string.uploadInspection));
					return uploadCount = inpectionList.size();
				}
			}
		}
		return uploadCount;
	}
	
	/***
	 * 异步上传分拣数据
	 * @return
	 * @throws DaoException
	 */
	private Integer uploadSortingTally() throws DaoException{
		Account account = JDApplication.getInstance().getAccount();
		int uploadCount = 0;
		if(account!=null){
			logTime("uploadSortingTally");
			SortingTallyService sortingTallyService = SortingTallyService.getInstance(context);
			List<SortingTally> sortingTallyList = sortingTallyService.findSortingTallyList(ConstantUtils.UPLOAD_0);
			if(sortingTallyList!=null&&sortingTallyList.size()>0){
				SortingTally[] sortingTallyArray = new SortingTally[sortingTallyList.size()]; 
				sortingTallyList.toArray(sortingTallyArray);
				UploadRequest uploadRequest = new UploadRequest();
				uploadRequest.setBody(JsonConverter.convertObjectToJson(sortingTallyArray));
				uploadRequest.setType(ConstantUtils.UPLOAD_SORTING_TALLY_TYPE);
				uploadRequest.setSiteCode(account.getSiteCode());
				uploadRequest.setKeyword1(String.valueOf(account.getSiteCode()));
				uploadRequest.setKeyword2(sortingTallyList.get(0).getBoxCode());
				uploadRequest.setReceiveSiteCode(sortingTallyList.get(0).getReceiveSiteCode());
				uploadRequest.setBoxCode(sortingTallyList.get(0).getBoxCode());
				String json = JsonConverter.convertObjectToJson(uploadRequest);
				String result = HttpClientUtils.requestResult(HttpPost.METHOD_NAME, getProperty(context).getAddressUploadTasks(), json);
				if(result!=null){
					UploadResult uploadResult = JsonConverter.convertJsonToObject(result, UploadResult.class);
					if(uploadResult.getCode()==200){
						for (SortingTally sortingTally : sortingTallyList) {
							sortingTally.setUploadStatus(ConstantUtils.UPLOAD_1);
							sortingTallyService.update(sortingTally);
						}
						setUploadType(context.getResources().getString(R.string.uploadSortingTally));
						return uploadCount = sortingTallyList.size();
					}
				}
			}
		}
		return uploadCount;
	}
	
	/***
	 * 异步上传收货信息
	 * @return
	 * @throws DaoException
	 */
	private Integer uploadDelivery() throws DaoException{
		Account account = JDApplication.getInstance().getAccount();
		int uploadCount = 0;
		if(account!=null){
			logTime("uploadDelivery");
			DeliveryService deliveryService = DeliveryService.getInstance(context);
			List<Delivery> deliveryList = deliveryService.findDeliveryList(ConstantUtils.UPLOAD_0);
			if(deliveryList!=null&&deliveryList.size()>0){
				Delivery[] deliveryArray = new Delivery[deliveryList.size()];
				deliveryList.toArray(deliveryArray);
				UploadRequest uploadRequest = new UploadRequest();
				uploadRequest.setBody(JsonConverter.convertObjectToJson(deliveryArray));
				uploadRequest.setBoxCode("");
				uploadRequest.setKeyword1(String.valueOf(account.getSiteCode()));
				uploadRequest.setKeyword2(deliveryList.get(0).getPackOrBox());
				uploadRequest.setSiteCode(account.getSiteCode());
				uploadRequest.setType(ConstantUtils.UPLOAD_DELIVERY_TYPE);
				uploadRequest.setReceiveSiteCode(account.getSiteCode());
				String json = JsonConverter.convertObjectToJson(uploadRequest);
				String result = HttpClientUtils.requestResult(HttpPost.METHOD_NAME, getProperty(context).getAddressUploadTasks(), json);
				if(result!=null){
					UploadResult uploadResult = JsonConverter.convertJsonToObject(result, UploadResult.class);
					if(uploadResult.getCode()==200){
						for (Delivery delivery : deliveryList) {
							delivery.setUploadStatus(ConstantUtils.UPLOAD_1);
							deliveryService.update(delivery);
						}
						setUploadType(context.getResources().getString(R.string.uploadDelivery));
						return uploadCount = deliveryList.size();
					}
				}
			}
		}
		return uploadCount;
	}
	
	/**
	 * 异步上传封箱信息
	 * @return
	 * @throws DaoException 
	 */
	private Integer uploadSealBoxInfo() throws DaoException{
		Account account = JDApplication.getInstance().getAccount();
		int uploadCount = 0;
		if(account!=null){
			logTime("uploadSealBoxInfo");
			SealBoxInfoService sealBoxInfoService = SealBoxInfoService.getInstance(context);
			List<SealBoxInfo> sealBoxInfoList = sealBoxInfoService.findSealBoxInfoList(ConstantUtils.UPLOAD_0);
			if(sealBoxInfoList!=null&&sealBoxInfoList.size()>0){
				SealBoxInfo[] sealBoxInfoArray = new SealBoxInfo[sealBoxInfoList.size()];
				sealBoxInfoList.toArray(sealBoxInfoArray);
				UploadRequest uploadRequest = new UploadRequest();
				uploadRequest.setBody(JsonConverter.convertObjectToJson(sealBoxInfoArray));
				uploadRequest.setBoxCode("");
				uploadRequest.setKeyword1(String.valueOf(account.getSiteCode()));
				uploadRequest.setKeyword2(sealBoxInfoList.get(0).getBoxCode());
				uploadRequest.setReceiveSiteCode(account.getSiteCode());
				uploadRequest.setType(ConstantUtils.UPLOAD_SEAL_BOX_INFO_TYPE);
				uploadRequest.setSiteCode(account.getSiteCode());
				String json = JsonConverter.convertObjectToJson(uploadRequest);
				String result = HttpClientUtils.requestResult(HttpPost.METHOD_NAME, getProperty(context).getAddressUploadTasks(), json);
				if(result!=null){
					UploadResult uploadResult = JsonConverter.convertJsonToObject(result, UploadResult.class);
					if(uploadResult.getCode()==200){
						for (SealBoxInfo sealBoxInfo : sealBoxInfoList) {
							sealBoxInfo.setUploadStatus(ConstantUtils.UPLOAD_1);
							sealBoxInfoService.update(sealBoxInfo);
						}
						setUploadType(context.getResources().getString(R.string.uploadSealBoxInfo));
						return uploadCount = sealBoxInfoList.size();
					}
				}
			}
		}
		return uploadCount;
	}
	
	public void logTime(String upload){
		Log.d(TAG, "start upload:"+upload+"["+SimpleDateUtils.dateToString(new Date(), SimpleDateUtils.DATETIME_PATTERN)+"]");
	}
	public String getUploadType() {
		return uploadType;
	}
	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}
}