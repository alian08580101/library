package com.jd.a6i.volley;

import java.util.Map;

import android.os.Handler;
import android.os.Message;

import com.jd.a6i.JDApplication;
import com.jd.a6i.common.PromptToast;
import com.jd.a6i.db.pojo.ErrorType;
import com.jd.a6i.db.service.ErrorTypeService;
import com.whl.dao.common.DaoException;

public class ErrorTypeRequest extends BaseRequest<ErrorType[]> {
	private static JDApplication applicationContext = JDApplication.getInstance();
	private static Handler errorTypeHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
			case REQUEST_SUCCESS:
				if(msg.obj!=null){
					ErrorType[] errorTypeArray = (ErrorType[]) msg.obj;
					saveErrorType(errorTypeArray);
				}
				break;
			case REQUEST_FAILURE:
				PromptToast.prompt(msg.obj.toString());
				break;
			}
		}
		
		private void saveErrorType(ErrorType[] errorTypeArray){
			ErrorTypeService errorTypeService = ErrorTypeService.getErrorTypeService(applicationContext);
			int deleteAll = errorTypeService.deleteAllErrorType();
			if(deleteAll==1){
				for (ErrorType errorType : errorTypeArray) {
					try {
						errorTypeService.save(errorType);
					} catch (DaoException e) {
						e.printStackTrace();
					}
				}
			}
		}
	};
	public ErrorTypeRequest(int method, String url, Map<String, Object> paramsMap,int...args) {
		super(method, url, errorTypeHandler, paramsMap,args);
	}
}
