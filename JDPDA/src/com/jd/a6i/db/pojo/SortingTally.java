package com.jd.a6i.db.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SortingTally {
/**{
 * \"receiveSiteCode\":5000,
 * \"receiveSiteName\":\"武汉一号库\",
 * \"boxCode\":\"TC010F002027W00100001002\",
 * \"packageCode\":\"170188553-3-3-\",
 * \"isCancel\":0,
 * \"isLoss\":0,
 * \"featureType\":0,
 * \"id\":5496,
 * \"businessType\":20,
 * \"userCode\":7595,
 * \"userName\":\"金大中\",
 * \"siteCode\":480,
 * \"siteName\":\"南京配送中心44\",
 * \"operateTime\":\"2014-05-05 10:03:08.187\"}*/
	@JsonIgnore(value=true)
	private int id;
	private int receiveSiteCode;
	private String receiveSiteName;
	private String boxCode;
	private String packageCode;
	private int isLoss;
	private int isCancel;
	private int featureType;
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
	public int getReceiveSiteCode() {
		return receiveSiteCode;
	}
	public String getReceiveSiteName() {
		return receiveSiteName;
	}
	public String getBoxCode() {
		return boxCode;
	}
	public String getPackageCode() {
		return packageCode;
	}
	public int getIsLoss() {
		return isLoss;
	}
	public int getIsCancel() {
		return isCancel;
	}
	public int getFeatureType() {
		return featureType;
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
	public void setReceiveSiteCode(int receiveSiteCode) {
		this.receiveSiteCode = receiveSiteCode;
	}
	public void setReceiveSiteName(String receiveSiteName) {
		this.receiveSiteName = receiveSiteName;
	}
	public void setBoxCode(String boxCode) {
		this.boxCode = boxCode;
	}
	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}
	public void setIsLoss(int isLoss) {
		this.isLoss = isLoss;
	}
	public void setIsCancel(int isCancel) {
		this.isCancel = isCancel;
	}
	public void setFeatureType(int featureType) {
		this.featureType = featureType;
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
		return "SortingTally [id=" + id + ", receiveSiteCode="
				+ receiveSiteCode + ", receiveSiteName=" + receiveSiteName
				+ ", boxCode=" + boxCode + ", packageCode=" + packageCode
				+ ", isLoss=" + isLoss + ", isCancel=" + isCancel
				+ ", featureType=" + featureType + ", businessType="
				+ businessType + ", userCode=" + userCode + ", userName="
				+ userName + ", siteCode=" + siteCode + ", siteName="
				+ siteName + ", operateTime=" + operateTime + ", uploadStatus="
				+ uploadStatus + "]";
	}
}
