package com.jd.a6i.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jd.a6i.R;

public class JDSpinnerAdapter extends JDBaseSpinnerAdapter<String> {
	public JDSpinnerAdapter(List<String> jdList, Context context) {
		super(jdList, context);
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView = LayoutInflater.from(context).inflate(R.layout.spinner_drop_item, null);
		}
		TextView textContent = (TextView) convertView.findViewById(R.id.textContent);
		textContent.setText(getItem(position).toString());
		return convertView;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView = LayoutInflater.from(context).inflate(R.layout.spinner_drop_item, null);
		}
		TextView textContent = (TextView) convertView.findViewById(R.id.textContent);
		textContent.setText(getItem(position).toString());
		return convertView;
	}

}
