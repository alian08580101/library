package com.jd.a6i.db.pojo;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class AsynUploadInspectionData implements Parcelable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3732146539764301800L;

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}

	private String sealBoxCode;
	private String boxCode;
	private String packageBarOrWaybillCode;
	private String exceptionType;
	private int operateType;
	private int receiveSiteCode;
	private int id;
	private int businessType;
	private int userCode;
	private String userName;
	private int siteCode;
	private String siteName;
	private String operateTime;
	
	public String getSealBoxCode() {
		return sealBoxCode;
	}

	public void setSealBoxCode(String sealBoxCode) {
		this.sealBoxCode = sealBoxCode;
	}

	public String getBoxCode() {
		return boxCode;
	}

	public void setBoxCode(String boxCode) {
		this.boxCode = boxCode;
	}

	public String getPackageBarOrWaybillCode() {
		return packageBarOrWaybillCode;
	}

	public void setPackageBarOrWaybillCode(String packageBarOrWaybillCode) {
		this.packageBarOrWaybillCode = packageBarOrWaybillCode;
	}

	public String getExceptionType() {
		return exceptionType;
	}

	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}

	public int getOperateType() {
		return operateType;
	}

	public void setOperateType(int operateType) {
		this.operateType = operateType;
	}

	public int getReceiveSiteCode() {
		return receiveSiteCode;
	}

	public void setReceiveSiteCode(int receiveSiteCode) {
		this.receiveSiteCode = receiveSiteCode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBusinessType() {
		return businessType;
	}

	public void setBusinessType(int businessType) {
		this.businessType = businessType;
	}

	public int getUserCode() {
		return userCode;
	}

	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(int siteCode) {
		this.siteCode = siteCode;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	@Override
	public String toString() {
		return "AsynUploadInspectionData [sealBoxCode=" + sealBoxCode
				+ ", boxCode=" + boxCode + ", packageBarOrWaybillCode="
				+ packageBarOrWaybillCode + ", exceptionType=" + exceptionType
				+ ", operateType=" + operateType + ", receiveSiteCode="
				+ receiveSiteCode + ", id=" + id + ", businessType="
				+ businessType + ", userCode=" + userCode + ", userName="
				+ userName + ", siteCode=" + siteCode + ", siteName="
				+ siteName + ", operateTime=" + operateTime + "]";
	}


}
