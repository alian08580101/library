package com.jd.a6i.task;

import java.util.List;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;

import com.google.gson.reflect.TypeToken;
import com.jd.a6i.R;
import com.jd.a6i.common.HttpClientUtils;
import com.jd.a6i.common.JsonConverter;
import com.jd.a6i.db.pojo.ErrorType;
import com.jd.a6i.db.service.ErrorTypeService;
import com.whl.dao.common.DaoException;


public class DownloadAsynctask extends JDBaseAsyncTask<String, Integer, Boolean> {
	public static final int DOWNLOAD_ERROR_TYPE = 0;
	
	private int downloadType;
	private Context context;
	private Handler handler;
	public DownloadAsynctask(Context context,int downloadType,Handler handler){
		this.context = context;
		this.downloadType = downloadType;
		this.handler = handler;
	}
	
	@Override
	protected Boolean doInBackground(String...params) {
		switch(downloadType){
		case DOWNLOAD_ERROR_TYPE:
			try {
				return downloadErrorType(params);
			} catch (DaoException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public boolean downloadErrorType(String...params) throws DaoException{
		if(getSubURI()==null||"".equals(getSubURI())){
			baseToast(R.string.errorURI);
			return false;
		}else{
			String uri = BASE_URI+getSubURI();
			String result = HttpClientUtils.requestResult(HttpGet.METHOD_NAME, uri, null);
			if(result!=null){
				List<ErrorType> errorTypeList = JsonConverter.convertObjectListFromJson(result, ErrorType[].class);
				ErrorTypeService errorTypeService = ErrorTypeService.getErrorTypeService(context);
				int deleteAll = errorTypeService.deleteAllErrorType();
				if(deleteAll==1){
					for (ErrorType errorType : errorTypeList) {
						errorTypeService.save(errorType);
					}
				}
			}
		}
		return true;
	}

}
