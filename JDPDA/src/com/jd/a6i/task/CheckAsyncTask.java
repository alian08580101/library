package com.jd.a6i.task;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.google.gson.reflect.TypeToken;
import com.jd.a6i.common.ConstantUtils;
import com.jd.a6i.common.HttpClientUtils;
import com.jd.a6i.common.JsonConverter;
import com.jd.a6i.common.NetworkUtils;
import com.jd.a6i.common.PropertyUtil;
import com.jd.a6i.db.pojo.BoxInfo;
import com.jd.a6i.db.pojo.Result;
import com.jd.a6i.db.pojo.SealBox;
import com.jd.a6i.db.pojo.SealCar;
import com.jd.a6i.db.pojo.StationInfo;

import static com.jd.a6i.common.PropertyUtil.getProperty;

public class CheckAsyncTask extends JDBaseAsyncTask<String, Integer, Boolean> {
	/**校验包裹号标志*/
	public static final int CHECK_TYPE_PACKAGE_NO = 3;
	/**校验封车号标志*/
	public static final int CHECK_TYPE_SEAL_CAR_NO = 0;
	/**校验封箱号标志*/
	public static final int CHECK_TYPE_SEAL_BOX_NO = 1;
	/**校验箱号标志*/
	public static final int CHECK_TYPE_BOX_NO = 2;
	/**校验站点号标志*/
	public static final int CHECK_TYPE_STATION_NO = 4;
	
	/**校验成功标志*/
	public static final int CHECK_SUCCESS = 7;
	/**校验失败标志*/
	public static final int CHECK_FAILURE = 8;
	/**封车号校验成功标志*//*
	public static final int CHECK_SEAL_CAR_NO_SUCCESS = 3;
	*//**封车号校验失败标志*//*
	public static final int CHECK_SEAL_CAR_NO_FAILURE = 4;
	*//**封箱号校验成功标志*//*
	public static final int CHECK_SEAL_BOX_NO_SUCCESS = 5;
	*//**封箱号校验失败标志*//*
	public static final int CHECK_SEAL_BOX_NO_FAILURE = 6;*/
	/**封车号响应字符串结果*/
	private String sealCarJson;
	/**封箱号响应字符串结果*/
	private String sealBoxJson;
	/**小件分拣-箱号信息返回字符串结果*/
	private String boxInfoJson;
	/**校验包裹号返回字符串结果*/
	private String packageInfoJson;
	/**大件分拣-站点信息返回字符串结果*/
	private String stationInfoJson;
	
	/**校验类型标志*/
	private int checkType;
	
	/**
	 * 异步校验构造方法
	 * @param context
	 * @param handler
	 * @param checkType
	 */
	public CheckAsyncTask(Context context,Handler handler,int checkType){
		this.context = context;
		this.handler = handler;
		this.checkType = checkType;
	}
	@Override
	protected Boolean doInBackground(String... params) {
		boolean isConnectNetwork = NetworkUtils.checkNetwork(context);
		if(isConnectNetwork){
			try {
				if(checkType==CHECK_TYPE_SEAL_CAR_NO||
				   checkType==CHECK_TYPE_SEAL_BOX_NO){
					return checkDeivery(params);
				}else if(checkType==CHECK_TYPE_BOX_NO){
					return checkBoxNo(params);
				}else if(checkType==CHECK_TYPE_PACKAGE_NO){
					return checkPackageNo(params);
				}else if(checkType==CHECK_TYPE_STATION_NO){
					return checkStationNo(params);
				}
					
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	
	
	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		Message message = Message.obtain();
		setFinish(true);
		if(checkType==CHECK_TYPE_SEAL_CAR_NO){
			message.arg1 = CHECK_TYPE_SEAL_CAR_NO;
			if(result){
				message.what = CHECK_SUCCESS;
				message.obj = sealCarJson;
			}else{
				message.what = CHECK_FAILURE;
			}
		}else if(checkType==CHECK_TYPE_SEAL_BOX_NO){
			message.arg1 = CHECK_TYPE_SEAL_BOX_NO;
			if(result){
				message.what = CHECK_SUCCESS;
				message.obj = sealBoxJson;
			}else{
				message.what = CHECK_FAILURE;
			}
		}else if(checkType==CHECK_TYPE_BOX_NO){
			message.arg1 = CHECK_TYPE_BOX_NO;
			message.obj = boxInfoJson;
			if(result){
				message.what=CHECK_SUCCESS;
			}else{
				message.what=CHECK_FAILURE;
			}
		}else if(checkType==CHECK_TYPE_PACKAGE_NO){
			message.arg1 = CHECK_TYPE_PACKAGE_NO;
			message.obj = packageInfoJson;
			if(result){
				message.what=CHECK_SUCCESS;
			}else{
				message.what=CHECK_FAILURE;
			}
		}else if(checkType==CHECK_TYPE_STATION_NO){
			message.arg1 = CHECK_TYPE_STATION_NO;
			message.obj = stationInfoJson;
			if(result){
				message.what = CHECK_SUCCESS;
			}else{
				message.what = CHECK_FAILURE;
			}
		}
		handler.sendMessage(message);
	}
	/**
	 * 开始校验
	 * @param params
	 * @return
	 * @throws Exception
	 */
	private boolean checkDeivery(String...params) throws Exception{
		String paramValue = params[0];
		String checkURI = getProperty(context).getAddressPort8080()+getSubURI()+paramValue;
		String result = HttpClientUtils.requestResult(HttpGet.METHOD_NAME, checkURI, null);
		if(result!=null){
			if (checkType==CHECK_TYPE_SEAL_CAR_NO){
				sealCarJson = result;
				SealCar sealCar = JsonConverter.convertJsonToObject(sealCarJson, TypeToken.get(SealCar.class));
				if(sealCar.getCode()==200){
					return true;
				}
			}else if(checkType==CHECK_TYPE_SEAL_BOX_NO){
				sealBoxJson = result;
				SealBox sealBox = JsonConverter.convertJsonToObject(sealBoxJson, TypeToken.get(SealBox.class));
				if(sealBox.getCode()==200){
					return true;
				}
			}
		}
		return false;
	}
	
	/**http://192.168.226.157:8080/services/boxes/TC010F002027W00100001002
	 * http://192.168.226.157:8080/services/boxes/validation?boxCode=TC010F002027W00100001002&operateType=2
	 * @param params
	 * @return
	 */
	private boolean checkBoxNo(String...params){
		String checkURI = PropertyUtil.getProperty(context).getAddressPort8080()+ 
				"boxes/"+
				((params.length==1)?params[0]:"validation?boxCode="+params[0]+"&operateType="+params[1]);
		String result = HttpClientUtils.requestResult(HttpGet.METHOD_NAME, checkURI, null);
		if(result!=null){
			boxInfoJson = result;
			BoxInfo boxInfo = JsonConverter.convertJsonToObject(result, BoxInfo.class);
			if(boxInfo.getCode()==200){
				return true;
			}
		}
		return false;
	}
	
	private boolean checkPackageNo(String...params){
		String checkURI = PropertyUtil.getProperty(context).getAddressPort8081()+"sorting/post/check";
		String response = HttpClientUtils.requestResult(HttpPost.METHOD_NAME, checkURI, params[0]);
		if(response!=null){
			packageInfoJson = response;
			Result result = JsonConverter.convertJsonToObject(response, Result.class);
			if(result.getCode()==200){
				return true;
			}
		}
		return false;
	}
	
	private boolean checkStationNo(String...params){
		String checkURI = PropertyUtil.getProperty(context).getAddressPort8080()+"bases/site/"+params[0];
		String result = HttpClientUtils.requestResult(HttpGet.METHOD_NAME, checkURI, null);
		if(result!=null){
			stationInfoJson = result;
			StationInfo stationInfo = JsonConverter.convertJsonToObject(result, StationInfo.class);
			if(stationInfo.getCode()==200){
				return true;
			}
		}
		return false;
	}
}
