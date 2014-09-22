package com.jd.a6i.db.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SealBoxInfo {
	@JsonIgnore(value=true)
	private int id;
	private String sealCode;
	private String boxCode;
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
	public String getSealCode() {
		return sealCode;
	}
	public String getBoxCode() {
		return boxCode;
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
	public void setSealCode(String sealCode) {
		this.sealCode = sealCode;
	}
	public void setBoxCode(String boxCode) {
		this.boxCode = boxCode;
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
		return "SealBoxInfo [id=" + id + ", sealCode=" + sealCode
				+ ", boxCode=" + boxCode + ", businessType=" + businessType
				+ ", userCode=" + userCode + ", userName=" + userName
				+ ", siteCode=" + siteCode + ", siteName=" + siteName
				+ ", operateTime=" + operateTime + ", uploadStatus="
				+ uploadStatus + "]";
	}
	
}
