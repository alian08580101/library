package com.jd.a6i.db.pojo;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class IncompleteQueryResponseData implements Parcelable, Serializable {

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub

	}
    
	private String boxCode;
	private String packageBarcode;
	private String mark;
	
	public String getBoxCode() {
		return boxCode;
	}

	public void setBoxCode(String boxCode) {
		this.boxCode = boxCode;
	}

	public String getPackageBarcode() {
		return packageBarcode;
	}

	public void setPackageBarcode(String packageBarcode) {
		this.packageBarcode = packageBarcode;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	
}
