package com.jd.a6i.volley;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.http.HttpStatus;

import android.os.Handler;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.jd.a6i.JDApplication;
import com.jd.a6i.common.JsonConverter;
import com.jd.a6i.common.PropertyUtil;
import com.jd.a6i.db.pojo.UploadResult;

public class UploadResultRequest extends BaseRequest<UploadResult> {
	private static JDApplication applicationContext = JDApplication.getInstance();
	public UploadResultRequest(int method, Handler handler, Map<String, Object> paramsMap,int...args) {
		super(method, PropertyUtil.getProperty(applicationContext).getAddressUploadTasks(), handler, paramsMap,args);
	}
	@Override
	protected Response<UploadResult> parseNetworkResponse(NetworkResponse networkResponse) {
		try {
			if(networkResponse.statusCode==HttpStatus.SC_OK){
				String jsonString = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
				UploadResult uploadResult = JsonConverter.convertJsonToObject(jsonString, UploadResult.class);
				if(uploadResult.getCode()==200){
					return Response.success(uploadResult, HttpHeaderParser.parseCacheHeaders(networkResponse));
				}
			}
			return Response.error(new ParseError(networkResponse));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return Response.error(new ParseError(e));
		}
	}
	
}
