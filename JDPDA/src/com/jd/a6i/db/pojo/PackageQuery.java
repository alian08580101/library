package com.jd.a6i.db.pojo;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class PackageQuery extends Result implements Parcelable, Serializable {
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		//return super.describeContents();
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		//super.writeToParcel(dest, flags);
	}

	@Override
	public String toString() {
		return "PackageQuery [totalPack=" + totalPack + ", receiveSiteCode="
				+ receiveSiteCode + ", receiveSiteName=" + receiveSiteName
				+ "]";
	}

	private int totalPack;
	private int receiveSiteCode;
	private String receiveSiteName;
	
	public String getReceiveSiteName() {
		return receiveSiteName;
	}
	public void setReceiveSiteName(String receiveSiteName) {
		this.receiveSiteName = receiveSiteName;
	}
	public int getTotalPack() {
		return totalPack;
	}
	public void setTotalPack(int totalPack) {
		this.totalPack = totalPack;
	}
	public int getReceiveSiteCode() {
		return receiveSiteCode;
	}
	public void setReceiveSiteCode(int receiveSiteCode) {
		this.receiveSiteCode = receiveSiteCode;
	}

}
