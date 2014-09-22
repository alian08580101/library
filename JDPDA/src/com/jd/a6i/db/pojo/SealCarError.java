package com.jd.a6i.db.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SealCarError {
	@JsonIgnore(value=true)
	private int id;
	private String shieldsCode;
	private String carCode;
	private String shieldsError;
	private int businessType;
	private int userCode;
	private String userName;
	private int siteCode;
	private String siteName;
	private String operateTime;
	private int uploadStatus;
	public int getId() {
		return id;
	}
	public String getShieldsCode() {
		return shieldsCode;
	}
	public String getCarCode() {
		return carCode;
	}
	public String getShieldsError() {
		return shieldsError;
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
	public int getUploadStatus() {
		return uploadStatus;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setShieldsCode(String shieldsCode) {
		this.shieldsCode = shieldsCode;
	}
	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}
	public void setShieldsError(String shieldsError) {
		this.shieldsError = shieldsError;
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
	public void setUploadStatus(int uploadStatus) {
		this.uploadStatus = uploadStatus;
	}
	@Override
	public String toString() {
		return "SealCarError [id=" + id + ", shieldsCode=" + shieldsCode
				+ ", carCode=" + carCode + ", shieldsError=" + shieldsError
				+ ", businessType=" + businessType + ", userCode=" + userCode
				+ ", userName=" + userName + ", siteCode=" + siteCode
				+ ", siteName=" + siteName + ", operateTime=" + operateTime
				+ ", uploadStatus=" + uploadStatus + "]";
	}
}
