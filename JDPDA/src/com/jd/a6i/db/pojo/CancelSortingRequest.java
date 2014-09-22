package com.jd.a6i.db.pojo;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class CancelSortingRequest implements Parcelable, Serializable {

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub

	}
    private int receiveSiteCode;
    private String receiveSiteName;
    private String boxCode;
    private String packageCode;
    private int isCancel;
    private int isLoss;
    private int featureType;
    private int id;
    private int businessType;
    private int userCode;
    private String userName;
    private int siteCode;
    private String siteName;
    private String operateTime;
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

	public String getBoxCode() {
		return boxCode;
	}

	public void setBoxCode(String boxCode) {
		this.boxCode = boxCode;
	}

	public String getPackageCode() {
		return packageCode;
	}

	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}

	public int getIsCancel() {
		return isCancel;
	}

	public void setIsCancel(int isCancel) {
		this.isCancel = isCancel;
	}

	public int getIsLoss() {
		return isLoss;
	}

	public void setIsLoss(int isLoss) {
		this.isLoss = isLoss;
	}

	public int getFeatureType() {
		return featureType;
	}

	public void setFeatureType(int featureType) {
		this.featureType = featureType;
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

}
