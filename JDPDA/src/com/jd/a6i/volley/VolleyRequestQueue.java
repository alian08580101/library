package com.jd.a6i.volley;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.ResponseDelivery;

public class VolleyRequestQueue extends RequestQueue {

	public VolleyRequestQueue(Cache cache, Network network, int threadPoolSize,
			ResponseDelivery delivery) {
		super(cache, network, threadPoolSize, delivery);
	}

	public VolleyRequestQueue(Cache cache, Network network, int threadPoolSize) {
		super(cache, network, threadPoolSize);
	}

	public VolleyRequestQueue(Cache cache, Network network) {
		super(cache, network);
	}

}
