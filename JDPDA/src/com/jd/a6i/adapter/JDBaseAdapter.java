package com.jd.a6i.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public abstract class JDBaseAdapter<T> extends BaseAdapter {
	public static final String TAG = "JDBaseAdapter";
	
	
	protected List<T> jdList;
	protected Context context;

	public JDBaseAdapter(List<T> jdList,Context context){
		this.jdList = jdList;
		this.context = context;
		Log.d(TAG, this.getClass().getName());
	}
	
	@Override
	public int getCount() {
		return (jdList==null)?0:jdList.size();
	}

	@Override
	public Object getItem(int position) {
		return jdList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
 
	@Override
	public int getViewTypeCount() {
		return (jdList==null||jdList.size()==0)?1:jdList.size();
	}
	/**
	 * @param context
	 * @param textView
	 * @param leftDrawable
	 * @param topDrawable
	 * @param rightDrawable
	 * @param bottomDrawable
	 */
	protected void setTextViewDrawable(Context context, TextView textView,
			Drawable leftDrawable, Drawable topDrawable,
			Drawable rightDrawable, Drawable bottomDrawable) {
		if (leftDrawable != null) {
			leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(),
					leftDrawable.getMinimumHeight());
		}
		if (topDrawable != null) {
			topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(),
					topDrawable.getMinimumHeight());
		}
		if (rightDrawable != null) {
			rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(),
					rightDrawable.getMinimumHeight());
		}
		if (bottomDrawable != null) {
			bottomDrawable.setBounds(0, 0, bottomDrawable.getMinimumWidth(),
					bottomDrawable.getMinimumHeight());
		}
		textView.setCompoundDrawables(leftDrawable, topDrawable, rightDrawable,
				bottomDrawable);
	}
	
	@Override
	public abstract View getView(int position, View convertView, ViewGroup parent);
	
	public enum TextViewDrawableLocation {
		LOCATION_LEFT,LOCATION_TOP,LOCATION_RIGHT,LOCATON_BOTTOM;
	}
}
