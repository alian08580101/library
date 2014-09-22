package com.jd.a6i.db.pojo;

import android.graphics.drawable.Drawable;

public class MenuItem {
	private Drawable itemIcon;
	private String itemName;
	private Drawable itemArrow;
	public Drawable getItemIcon() {
		return itemIcon;
	}
	public void setItemIcon(Drawable itemIcon) {
		this.itemIcon = itemIcon;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Drawable getItemArrow() {
		return itemArrow;
	}
	public void setItemArrow(Drawable itemArrow) {
		this.itemArrow = itemArrow;
	}
	
}
