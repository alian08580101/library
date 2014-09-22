package com.jd.a6i.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jd.a6i.R;
import com.jd.a6i.db.pojo.MenuItem;

public class MenuItemAdapter extends JDBaseAdapter<MenuItem> {
	public MenuItemAdapter(List<MenuItem> jdList, Context context) {
		super(jdList, context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView = LayoutInflater.from(context).inflate(R.layout.menu_item, null);
		}
		
		MenuItem menuItem = (MenuItem) getItem(position);
		
		TextView itemText = (TextView) convertView.findViewById(R.id.itemText);
		itemText.setText(menuItem.getItemName());
		setTextViewDrawable(context, itemText, menuItem.getItemIcon(), null, menuItem.getItemArrow(), null);
		return convertView;
	}

}
