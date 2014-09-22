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
import com.jd.a6i.db.pojo.UploadResponse;

public class UploadResponseRequest extends BaseRequest<UploadResponse> {
	private static JDApplication applicationContext = JDApplication.getInstance();
	public UploadResponseRequest(int method, Handler handler, Map<String, Object> paramsMap) {
		super(method, PropertyUtil.getProperty(applicationContext).getAddressUploadTasks(), handler, paramsMap);
	}
	@Override
	protected Response<UploadResponse> parseNetworkResponse(NetworkResponse networkResponse) {
		try {
			if(networkResponse.statusCode==HttpStatus.SC_OK){
				String jsonString = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
				UploadResponse uploadResponse = JsonConverter.convertJsonToObject(jsonString, UploadResponse.class);
				if(uploadResponse.getCode()==200){
					return Response.success(uploadResponse, HttpHeaderParser.parseCacheHeaders(networkResponse));
				}
			}
			return Response.error(new ParseError(networkResponse));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return Response.error(new ParseError(e));
		}
	}
	
}
