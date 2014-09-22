package com.jd.a6i.db.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import android.os.Parcel;
import android.os.Parcelable;

public class BackGoods implements Parcelable, Serializable {
	/**
	 * 
	 */
	@JsonIgnore(value=true)
	private static final long serialVersionUID = -3659141727703456176L;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

	}
	private String sealBoxCode;
	private String boxCode;
	private String packageBarOrWaybillCode;
	private String exceptionType;
	private int operateType;
	private int receiveSiteCode;
	private int businessType;
	private int userCode;
	private String userName;
	private int siteCode;
	private String siteName;
	private String operateTime;
	private int id;
	

	public String getSealBoxCode() {
		return sealBoxCode;
	}

	public String getBoxCode() {
		return boxCode;
	}

	public String getPackageBarOrWaybillCode() {
		return packageBarOrWaybillCode;
	}

	public String getExceptionType() {
		return exceptionType;
	}

	public int getOperateType() {
		return operateType;
	}

	public int getReceiveSiteCode() {
		return receiveSiteCode;
	}

	public int getBusinessType() {
		return businessType;
	}

	public int getUserCode() {
		return userCode;
	}

	public String getUserName() {
		return userName;
	}

	public int getSiteCode() {
		return siteCode;
	}

	public String getSiteName() {
		return siteName;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setSealBoxCode(String sealBoxCode) {
		this.sealBoxCode = sealBoxCode;
	}

	public void setBoxCode(String boxCode) {
		this.boxCode = boxCode;
	}

	public void setPackageBarOrWaybillCode(String packageBarOrWaybillCode) {
		this.packageBarOrWaybillCode = packageBarOrWaybillCode;
	}

	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}

	public void setOperateType(int operateType) {
		this.operateType = operateType;
	}

	public void setReceiveSiteCode(int receiveSiteCode) {
		this.receiveSiteCode = receiveSiteCode;
	}

	public void setBusinessType(int businessType) {
		this.businessType = businessType;
	}

	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setSiteCode(int siteCode) {
		this.siteCode = siteCode;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "BackGoods [sealBoxCode=" + sealBoxCode + ", boxCode=" + boxCode
				+ ", packageBarOrWaybillCode=" + packageBarOrWaybillCode
				+ ", exceptionType=" + exceptionType + ", operateType="
				+ operateType + ", receiveSiteCode=" + receiveSiteCode
				+ ", businessType=" + businessType + ", userCode=" + userCode
				+ ", userName=" + userName + ", siteCode=" + siteCode
				+ ", siteName=" + siteName + ", operateTime=" + operateTime
				+ ", id=" + id + "]";
	}
	
}
