package com.jd.a6i.common;

import com.jd.a6i.JDApplication;

import android.widget.Toast;

public class PromptToast {
	private static JDApplication applicationContext = JDApplication.getInstance();
	public static  void prompt(int stringId){
		Toast.makeText(applicationContext, applicationContext.getResources().getString(stringId), Toast.LENGTH_SHORT).show();
	}
	
	public static void prompt(String message){
		Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show();
	}
	
	public static void prompt(int stringId,Object...values){
		Toast.makeText(applicationContext,applicationContext.getResources().getString(stringId, values), Toast.LENGTH_SHORT).show();
	}
}
