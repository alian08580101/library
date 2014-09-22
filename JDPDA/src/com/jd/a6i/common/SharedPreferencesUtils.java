package com.jd.a6i.common;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public final class SharedPreferencesUtils {
	private static final String SHARED_PREFERENCES_NAME = "jd_dms";
	private static SharedPreferences sharedPreferences = null;
	private static Editor editor = null;

	//private static final String LOGIN_STATUS = "isLogin";
	private static final String CHECK_USERNAME = "checkBoxUsername";
	private static final String CHECK_PASSWORD = "checkBoxPassword";
	private static final String KSTK_SERVICE = "KSTK";
	
	private static void initSharedPreferences(Context context){
		sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0);
		editor = sharedPreferences.edit();
	}
	
	public static void putKstkService(Context context,int kstkService){
		initSharedPreferences(context);
		editor.putInt(KSTK_SERVICE, kstkService);
		editor.commit();
	}
	
	public static int getKstkService(Context context){
		sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0);
		return sharedPreferences.getInt(KSTK_SERVICE, 0);
	}
	
	/**
	 * 
	 * @param context
	 * @param isCheck
	 */
	public static void putRememberUsernameCheck(Context context,boolean isCheck){
		initSharedPreferences(context);
		editor.putBoolean(CHECK_USERNAME, isCheck);
		editor.commit();
	}
	/**
	 * 
	 * @param context
	 * @return
	 */
	public static boolean getRememberUsernameCheck(Context context){
		sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0);
		return sharedPreferences.getBoolean(CHECK_USERNAME, false);
	}
	
	/**
	 * 
	 * @param context
	 * @param isCheck
	 */
	public static void putRememberPasswordCheck(Context context,boolean isCheck){
		initSharedPreferences(context);
		editor.putBoolean(CHECK_PASSWORD, isCheck);
		editor.commit();
	}
	/**
	 * 
	 * @param context
	 * @return
	 */
	public static boolean getRememberPasswordCheck(Context context){
		sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0);
		return sharedPreferences.getBoolean(CHECK_PASSWORD, false);
	}
	
	/**
	 * 
	 * @param context
	 * @param isLogin
	 
	public static void putLoginStatus(Context context,boolean isLogin){
		initSharedPreferences(context);
		editor.putBoolean(LOGIN_STATUS, isLogin);
		editor.commit();
	}*/
	
	/**
	 * 
	 * @param context
	 * @return
	
	public static boolean getLoginStatus(Context context){
		sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0);
		return sharedPreferences.getBoolean(LOGIN_STATUS, false);
	} */
}
