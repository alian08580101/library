package com.jd.a6i.volley;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import org.apache.http.HttpStatus;

import android.os.Handler;
import android.os.Message;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.jd.a6i.common.JsonConverter;

public class BaseRequest<T> extends Request<T>{
	private static final String TAG = "BaseRequest";
	//private ProgressDialog progressDialog;
	
	public static final int REQUEST_SUCCESS = 0;
	public static final int REQUEST_FAILURE = 1;
	public static final String CONTENT_VALUE = "application/json;charset=UTF-8";
	public static final String CONTENT_KEY = "content-type";
	
	private Map<String,Object> paramsMap;
	private Listener<T> listener;
	//private VolleyResultListener volleyResultListener;
	/**
	 * 异步访问远程服务基类构造方法
	 * @param method 访问方法{@link Method}
	 * @param url 访问的URL
	 * @param volleyResultListener 监听访问完成结果
	 * @param paramsMap 访问需携带的参数
	 * @param args 访问可能需要回传的标记
	 */
	public BaseRequest(int method,String url,final VolleyResultListener volleyResultListener,Map<String,Object> paramsMap,final int...args){
		super(method, url, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				if(volleyResultListener!=null){
					int arg1 = args.length>0?args[0]:0;
					int arg2 = args.length>1?args[1]:0;
					Message message = Message.obtain();
					message.what = REQUEST_FAILURE;
					message.arg1 = arg1;
					message.arg2 = arg2;
					message.obj = volleyError.getMessage()==null?"No error Message":volleyError.getMessage();
					volleyResultListener.handlerFailureResult(message);
				}
			}
		});
		this.paramsMap = paramsMap;
		this.listener = new Listener<T>() {
			@Override
			public void onResponse(T entity) {
				if(volleyResultListener!=null){
					int arg1 = args.length>0?args[0]:0;
					int arg2 = args.length>1?args[1]:0;
					Message message = Message.obtain();
					message.what = REQUEST_SUCCESS;
					message.arg1 = arg1;
					message.arg2 = arg2;
					message.obj = entity;
					volleyResultListener.handlerSuccessResult(message);
				}
			}
		};
		setShouldCache(false);
		setRetryPolicy(new DefaultRetryPolicy(20*1000, 1, 1.0f));
	}

	/**
	 * @param method 访问方法{@link Method}
	 * @param url 访问地址
	 * @param handler 刷新UI
	 * @param clazz 转换的泛型
	 * @param paramsMap 传递的参数
	 */
	public BaseRequest(int method,String url,final Handler handler,Map<String,Object> paramsMap,final int...args) {
		super(method, url, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				if(handler!=null){
					int arg1 = args.length>0?args[0]:0;
					int arg2 = args.length>1?args[1]:0;
					Message message = handler.obtainMessage(REQUEST_FAILURE, arg1, arg2, volleyError.getMessage());
					handler.sendMessage(message);
				}
			}
		});
		this.paramsMap = paramsMap;
		this.listener = new Listener<T>() {
			@Override
			public void onResponse(T entity) {
				if(handler!=null){
					int arg1 = args.length>0?args[0]:0;
					int arg2 = args.length>1?args[1]:0;
					Message message = handler.obtainMessage(REQUEST_SUCCESS,  arg1, arg2, entity);
					handler.sendMessage(message);
				}
			}
		};
		setShouldCache(false);
		setRetryPolicy(new DefaultRetryPolicy(20*1000, 1, 1.0f));
	}

	@Override
	protected void deliverResponse(T entity) {
		listener.onResponse(entity);
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
		try {
			if(networkResponse.statusCode==HttpStatus.SC_OK){
				String jsonString = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
				ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
				Type type = parameterizedType.getActualTypeArguments()[0];
				T entity = JsonConverter.convertJsonToObject(jsonString, type);
				return Response.success(entity, HttpHeaderParser.parseCacheHeaders(networkResponse));
			}
			return Response.error(new ParseError(networkResponse));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return Response.error(new ParseError(e));
		}
	}
	
	@Override
	protected VolleyError parseNetworkError(VolleyError volleyError) {
		return super.parseNetworkError(volleyError);
	}

	@Override
	public void deliverError(VolleyError error) {
		super.deliverError(error);
	}

	@Override
	public String getBodyContentType() {
		return CONTENT_VALUE;
	}
	
	@Override
	public byte[] getBody() throws AuthFailureError {
		if(paramsMap!=null){
			String json = JsonConverter.convertMapToJson(paramsMap);
			return json.getBytes();
		}
		return null;
	}
	/*public VolleyResultListener getVolleyRefreshUI() {
		return volleyResultListener;
	}

	public void setVolleyRefreshUI(VolleyResultListener volleyResultListener) {
		this.volleyResultListener = volleyResultListener;
	}*/
}
