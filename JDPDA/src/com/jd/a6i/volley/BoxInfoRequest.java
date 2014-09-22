package com.jd.a6i.volley;

import java.util.Map;

import android.os.Handler;

import com.jd.a6i.db.pojo.BoxInfo;

public class BoxInfoRequest extends BaseRequest<BoxInfo> {

	public BoxInfoRequest(int method, String url, Handler handler,Map<String, Object> paramsMap,int...args) {
		super(method, String.format(url, paramsMap.get("boxCode")), handler, paramsMap,args);
	}
	
	/*@Override
	protected Response<BoxInfo> parseNetworkResponse(NetworkResponse networkResponse) {
		try {
			if(networkResponse.statusCode==HttpStatus.SC_OK){
				String jsonString = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
				BoxInfo boxInfo = JsonConverter.convertJsonToObject(jsonString, BoxInfo.class);
				if(boxInfo.getCode()==200){
					return Response.success(boxInfo, HttpHeaderParser.parseCacheHeaders(networkResponse));
				}
			}
			return Response.error(new ParseError(networkResponse));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return Response.error(new ParseError(e));
		}
	}*/
}
