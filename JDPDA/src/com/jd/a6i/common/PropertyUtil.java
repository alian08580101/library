package com.jd.a6i.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.content.Context;

import com.jd.a6i.R;

public class PropertyUtil {
	/**key*/
	public final static String UPLOAD_INTERVAL_TIME = "upload.interval.time";
	public final static String ADDRESS_PORT_8080 = "address.port.8080";
	public final static String ADDRESS_PORT_8081 = "address.port.8081";
	public final static String DELETE_INTERVAL_HOURS = "delete.interval.hours";
	public final static String ADDRESS_UPLOAD_TASKS = "address.upload.tasks";
	public final static String DES_KEY = "des.key";
	public final static String LOGIN_URL = "login.url";
	public final static String ERROR_TYPE_URL = "error.type.url";
	public final static String SEAL_CAR_CODE_URL = "seal.car.code.url";
	public final static String SEAL_BOX_CODE_URL = "seal.box.code.url";
	public final static String BOX_CODE_URL1 = "box.code.url1";
	public final static String BOX_CODE_URL2 = "box.code.url1";
	public final static String PACKAGE_CODE_URL = "package.code.url";
	public final static String SITE_CODE_URL = "site.code.url";
	public final static String SORTING_TYPE = "sorting.type";
	
	
	/**default value*/
	public final static String DEFAULT_UPLOAD_INTERVAL_TIME = "6000";
	public final static String DEFAULT_ADDRESS_PORT_8080 = "http://192.168.226.157:8080/services/";
	public final static String DEFAULT_ADDRESS_PORT_8081 = "http://192.168.226.157:8081/services/";
	public final static String DEFAULT_DELETE_INTERVAL_HOURS = "8";
	public final static String DEFAULT_ADDRESS_UPLOAD_TASKS = "http://192.168.226.157:8080/services/tasks/";
	public final static String DEFAULT_DES_KEY = "JD.COM";
	public final static String DEFAULT_LOGIN_URL = "http://192.168.226.157:8080/services/bases/login";
	public final static String DEFAULT_ERROR_TYPE_URL = "http://192.168.226.157:8080/services/bases/errorlist/";
	public final static String DEFAULT_SEAL_CAR_CODE_URL = "http://192.168.226.157:8080/services/seal/vehicle/";
	public final static String DEFAULT_SEAL_BOX_CODE_URL = "http://192.168.226.157:8080/services/seal/box/";
	public final static String DEFAULT_BOX_CODE_URL1 = "http://192.168.226.157:8080/services/boxes/validation?boxCode=%s&operateType=2";
	public final static String DEFAULT_BOX_CODE_URL2 = "http://192.168.226.157:8080/services/boxes/%s";
	public final static String DEFAULT_PACKAGE_CODE_URL = "http://192.168.226.157:8081/services/sorting/post/check";
	public final static String DEFAULT_SITE_CODE_URL = "http://192.168.226.157:8080/services/bases/site/%s";
	public final static String DEFAULT_SORTING_TYPE = "20";
	
	private static PropertyUtil instance;
    private Properties properties;
    private Context context;

    private PropertyUtil(Context applicationContext) {
        initProperty(applicationContext);
    }

    public static PropertyUtil getProperty(Context applicationContext) {
        if (instance == null) {
            instance = new PropertyUtil(applicationContext);
        }
        instance.refreshIfContextChanged(applicationContext);
        return instance;
    }
    
    private void initProperty(Context context) {
        this.context = context;
        InputStream inputStream = context.getResources().openRawResource(R.raw.app_config);
        properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void refreshIfContextChanged(Context context) {
        if (sameInstance(context)) 
        	return;
        initProperty(context);
    }
    
    private boolean sameInstance(Context context) {
        return this.context == context;
    }
    
    public String getAddressPort8080(){
    	return properties.getProperty(ADDRESS_PORT_8080, DEFAULT_ADDRESS_PORT_8080);
    }
    
    public String getAddressPort8081(){
    	return properties.getProperty(ADDRESS_PORT_8081, DEFAULT_ADDRESS_PORT_8081);
    }
    
    public int getUploadIntervalTime(){
    	String value = properties.getProperty(UPLOAD_INTERVAL_TIME, DEFAULT_UPLOAD_INTERVAL_TIME);
    	return Integer.valueOf(value);
    }
    
    public int getDeleteIntervalHours(){
    	String value = properties.getProperty(DELETE_INTERVAL_HOURS, DEFAULT_DELETE_INTERVAL_HOURS);
    	return Integer.valueOf(value);
    }
    
    public String getAddressUploadTasks(){
    	return properties.getProperty(ADDRESS_UPLOAD_TASKS, DEFAULT_ADDRESS_UPLOAD_TASKS);
    }
    
    public String getDesKey(){
    	return properties.getProperty(DES_KEY, DEFAULT_DES_KEY);
    }
    
    public String getLoginUrl(){
    	return properties.getProperty(LOGIN_URL, DEFAULT_LOGIN_URL);
    }
    
    public String getErrorTypeUrl(){
    	return properties.getProperty(ERROR_TYPE_URL, DEFAULT_ERROR_TYPE_URL);
    }
    
    public String getSealCarCodeUrl(){
    	return properties.getProperty(SEAL_CAR_CODE_URL, DEFAULT_SEAL_CAR_CODE_URL);
    }
    
    public String getSealBoxCodeUrl(){
    	return properties.getProperty(SEAL_BOX_CODE_URL, DEFAULT_SEAL_BOX_CODE_URL);
    }
    
    public String getBoxCodeUrl1(){
    	return properties.getProperty(BOX_CODE_URL1, DEFAULT_BOX_CODE_URL1);
    }
    
    public String getBoxCodeUrl2(){
    	return properties.getProperty(BOX_CODE_URL2, DEFAULT_BOX_CODE_URL2);
    }
    
    public String getPackageCodeUrl(){
    	return properties.getProperty(PACKAGE_CODE_URL, DEFAULT_PACKAGE_CODE_URL);
    }
    
    public String getSiteCodeUrl(){
    	return properties.getProperty(SITE_CODE_URL, DEFAULT_SITE_CODE_URL);
    }
    
    public int getSortingType(){
    	String value = properties.getProperty(SORTING_TYPE, DEFAULT_SORTING_TYPE);
    	return Integer.valueOf(value);
    }
}
