package com.jd.a6i.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;

public abstract class JDBaseSpinnerAdapter<T> extends JDBaseAdapter<T> implements SpinnerAdapter {
	public JDBaseSpinnerAdapter(List<T> jdList, Context context) {
		super(jdList, context);
	}
	@Override
	public abstract View getDropDownView(int position, View convertView, ViewGroup parent) ;
}
