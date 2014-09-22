package com.jd.a6i.volley;

import static com.jd.a6i.common.PropertyUtil.getProperty;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import org.apache.http.HttpStatus;

import android.os.Handler;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.jd.a6i.JDApplication;
import com.jd.a6i.common.JsonConverter;
import com.jd.a6i.db.pojo.SealCar;

public class SealCarRequest extends BaseRequest<SealCar> {
	private static JDApplication applicationContext = JDApplication.getInstance();
	public SealCarRequest(int method, Handler handler,Map<String, Object> paramsMap,int...args) {
		super(method, getProperty(applicationContext).getSealCarCodeUrl()+paramsMap.get("sealCarCode"), handler, paramsMap,args);
	}
	
	@Override
	protected Response<SealCar> parseNetworkResponse(NetworkResponse networkResponse) {
		try {
			if(networkResponse.statusCode==HttpStatus.SC_OK){
				String jsonString = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
				SealCar sealCar = JsonConverter.convertJsonToObject(jsonString, SealCar.class);
				if(sealCar.getCode()==200){
					return Response.success(sealCar, HttpHeaderParser.parseCacheHeaders(networkResponse));
				}
			}
			return Response.error(new ParseError(networkResponse));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return Response.error(new ParseError(e));
		}
	}
}
