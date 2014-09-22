package com.jd.a6i.volley;

import static com.jd.a6i.common.PropertyUtil.getProperty;

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
import com.jd.a6i.db.pojo.SealBox;
public class SealBoxRequest extends BaseRequest<SealBox> {
	private static JDApplication applicationContext = JDApplication.getInstance();
	public SealBoxRequest(int method, Handler handler, Map<String, Object> paramsMap,int...args) {
		super(method, getProperty(applicationContext).getSealBoxCodeUrl()+paramsMap.get("sealBoxCode"), handler, paramsMap,args);
	}
	@Override
	protected Response<SealBox> parseNetworkResponse(NetworkResponse networkResponse) {
		try {
			if(networkResponse.statusCode==HttpStatus.SC_OK){
				String jsonString = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
				SealBox sealBox = JsonConverter.convertJsonToObject(jsonString, SealBox.class);
				if(sealBox.getCode()==200){
					return Response.success(sealBox, HttpHeaderParser.parseCacheHeaders(networkResponse));
				}
			}
			return Response.error(new ParseError(networkResponse));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return Response.error(new ParseError(e));
		}
	}
}
