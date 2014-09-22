package com.jd.a6i.common;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

public class PopupWindowUtils {
	public static PopupWindow popupWindow(final Activity activity, int rsid) {
		View windowView = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
		
		LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View popView = inflater.inflate(rsid, null, false);
		final PopupWindow popupWindow = new PopupWindow(popView,LayoutParams.MATCH_PARENT, windowView.getMeasuredHeight(), true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable(activity.getResources()));
		popupWindow.setFocusable(true);
		popupWindow.update();
		return popupWindow;
	}
	
	public static void show(Activity activity, View rootView, PopupWindow popupWindow){
		Rect rect = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
		popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, rect.top);
		rect = null;
	}
	
	
	
	public static int getStatusBarHeight(Activity activity){
		Rect rect = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
		View view = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
		return rect.top; 
	}
	
	public static int getScreenHeight(Activity activity){
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.heightPixels;
	}
}
