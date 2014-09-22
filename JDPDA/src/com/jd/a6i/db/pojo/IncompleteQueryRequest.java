package com.jd.a6i.db.pojo;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class IncompleteQueryRequest implements Parcelable, Serializable {

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub

	}

	private String siteCode;
	private String receiveSiteCode;
	private String boxCode;
	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	public String getReceiveSiteCode() {
		return receiveSiteCode;
	}

	public void setReceiveSiteCode(String receiveSiteCode) {
		this.receiveSiteCode = receiveSiteCode;
	}

	public String getBoxCode() {
		return boxCode;
	}

	public void setBoxCode(String boxCode) {
		this.boxCode = boxCode;
	}
}
