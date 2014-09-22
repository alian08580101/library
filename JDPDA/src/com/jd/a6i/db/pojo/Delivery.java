package com.jd.a6i.db.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import android.os.Parcel;
import android.os.Parcelable;

public class Delivery implements Serializable, Parcelable {
	@JsonDeserialize
	@JsonIgnore(value=true)
	private static final long serialVersionUID = 7738905640934389323L;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
	}
	@JsonIgnore(value=true)
	private int id;
	private String shieldsCarCode;
	private String carCode;
	private String sealBoxCode;
	private String packOrBox;
	private String turnoverBoxCode;
	private String queueNo;
	private String departureCarId;
	private String shieldsCarTime;
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

	public String getShieldsCarCode() {
		return shieldsCarCode;
	}

	public String getCarCode() {
		return carCode;
	}

	public String getSealBoxCode() {
		return sealBoxCode;
	}

	public String getPackOrBox() {
		return packOrBox;
	}

	public String getTurnoverBoxCode() {
		return turnoverBoxCode;
	}

	public String getQueueNo() {
		return queueNo;
	}

	public String getDepartureCarId() {
		return departureCarId;
	}

	public String getShieldsCarTime() {
		return shieldsCarTime;
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

	public void setShieldsCarCode(String shieldsCarCode) {
		this.shieldsCarCode = shieldsCarCode;
	}

	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}

	public void setSealBoxCode(String sealBoxCode) {
		this.sealBoxCode = sealBoxCode;
	}

	public void setPackOrBox(String packOrBox) {
		this.packOrBox = packOrBox;
	}

	public void setTurnoverBoxCode(String turnoverBoxCode) {
		this.turnoverBoxCode = turnoverBoxCode;
	}

	public void setQueueNo(String queueNo) {
		this.queueNo = queueNo;
	}

	public void setDepartureCarId(String departureCarId) {
		this.departureCarId = departureCarId;
	}

	public void setShieldsCarTime(String shieldsCarTime) {
		this.shieldsCarTime = shieldsCarTime;
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
		return "Delivery [id=" + id + ", shieldsCarCode=" + shieldsCarCode
				+ ", carCode=" + carCode + ", sealBoxCode=" + sealBoxCode
				+ ", packOrBox=" + packOrBox + ", turnoverBoxCode="
				+ turnoverBoxCode + ", queueNo=" + queueNo
				+ ", departureCarId=" + departureCarId + ", shieldsCarTime="
				+ shieldsCarTime + ", businessType=" + businessType
				+ ", userCode=" + userCode + ", userName=" + userName
				+ ", siteCode=" + siteCode + ", siteName=" + siteName
				+ ", operateTime=" + operateTime + ", uploadStatus="
				+ uploadStatus + "]";
	}
}
