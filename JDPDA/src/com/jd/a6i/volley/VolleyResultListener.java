package com.jd.a6i.volley;

import android.os.Message;

public interface VolleyResultListener {
	public void handlerSuccessResult(Message message);
	public void handlerFailureResult(Message message);
}
