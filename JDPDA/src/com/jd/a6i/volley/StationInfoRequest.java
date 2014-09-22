package com.jd.a6i.volley;

import java.util.Map;

import android.os.Handler;

import com.jd.a6i.db.pojo.StationInfo;

public class StationInfoRequest extends BaseRequest<StationInfo> {
	public StationInfoRequest(int method, String url, Handler handler, Map<String, Object> paramsMap, int...args) {
		super(method, String.format(url, paramsMap.get("siteCode")), handler, paramsMap, args);
	}

}
