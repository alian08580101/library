package com.jd.a6i.volley;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.http.HttpStatus;

import android.os.Handler;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.jd.a6i.common.JsonConverter;
import com.jd.a6i.db.pojo.BoxInfo;
import com.jd.a6i.db.pojo.Result;

public class ResultRequet extends BaseRequest<Result> {

	public ResultRequet(int method, String url, Handler handler, Map<String, Object> paramsMap,int...args) {
		super(method, url, handler, paramsMap,args);
	}
	/*@Override
	protected Response<Result> parseNetworkResponse(NetworkResponse networkResponse) {
		try {
			if(networkResponse.statusCode==HttpStatus.SC_OK){
				String jsonString = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
				Result result = JsonConverter.convertJsonToObject(jsonString, BoxInfo.class);
				if(result.getCode()==200){
					return Response.success(result, HttpHeaderParser.parseCacheHeaders(networkResponse));
				}
			}
			return Response.error(new ParseError(networkResponse));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return Response.error(new ParseError(e));
		}
	}*/
}
