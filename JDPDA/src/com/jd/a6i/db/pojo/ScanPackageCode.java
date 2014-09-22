package com.jd.a6i.db.pojo;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class ScanPackageCode implements Parcelable, Serializable {

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
	private int businessType;
	private int createSiteCode;
	private String createSiteName;
	private String operateTime;
	private int operateType;
	private int operateUserCode;
	private String operateUserName;
	private int receiveSiteCode;
	private String packageCode;
	public String getBoxCode() {
		return boxCode;
	}

	public void setBoxCode(String boxCode) {
		this.boxCode = boxCode;
	}

	public int getBusinessType() {
		return businessType;
	}

	public void setBusinessType(int businessType) {
		this.businessType = businessType;
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

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public int getOperateType() {
		return operateType;
	}

	public void setOperateType(int operateType) {
		this.operateType = operateType;
	}

	public int getOperateUserCode() {
		return operateUserCode;
	}

	public void setOperateUserCode(int operateUserCode) {
		this.operateUserCode = operateUserCode;
	}

	public String getOperateUserName() {
		return operateUserName;
	}

	public void setOperateUserName(String operateUserName) {
		this.operateUserName = operateUserName;
	}

	public int getReceiveSiteCode() {
		return receiveSiteCode;
	}

	public void setReceiveSiteCode(int receiveSiteCode) {
		this.receiveSiteCode = receiveSiteCode;
	}

	public String getPackageCode() {
		return packageCode;
	}

	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}
}
