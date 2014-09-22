package com.jd.a6i.db.pojo;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class ScanBoxCode extends Result implements Parcelable, Serializable {

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return super.describeContents();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		super.writeToParcel(dest, flags);
	}

	private String boxCode;
	private int createSiteCode;
	private String createSiteName;
	private int receiveSiteCode;
	private String receiveSiteName;
	private String type;
	public String getBoxCode() {
		return boxCode;
	}

	public void setBoxCode(String boxCode) {
		this.boxCode = boxCode;
	}

	public int getCreateSiteCode() {
		return createSiteCode;
	}

	public void setCreateSiteCode(int createSiteCode) {
		this.createSiteCode = createSiteCode;
	}

	public String getCreateSiteName() {
		return createSiteName;
	}

	public void setCreateSiteName(String createSiteName) {
		this.createSiteName = createSiteName;
	}

	public int getReceiveSiteCode() {
		return receiveSiteCode;
	}

	public void setReceiveSiteCode(int receiveSiteCode) {
		this.receiveSiteCode = receiveSiteCode;
	}

	public String getReceiveSiteName() {
		return receiveSiteName;
	}

	public void setReceiveSiteName(String receiveSiteName) {
		this.receiveSiteName = receiveSiteName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
