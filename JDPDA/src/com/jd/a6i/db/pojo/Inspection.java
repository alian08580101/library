package com.jd.a6i.db.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class Inspection {
	@JsonIgnore(value=true)
	private int id;
	private String sealBoxCode;
	private String boxCode;
	private String packageBarOrWaybillCode;
	private String exceptionType;
	private int operateType;
	private int businessType;
	private int userCode;
	private String userName;
	private int siteCode;
	private String siteName;
	private String operateTime;
	private int stateCode;
	private int receiveSiteCode;
	
	public String getSealBoxCode() {
		return sealBoxCode;
	}
	public void setSealBoxCode(String sealBoxCode) {
		this.sealBoxCode = sealBoxCode;
	}
	public int getReceiveSiteCode() {
		return receiveSiteCode;
	}
	public void setReceiveSiteCode(int receiveSiteCode) {
		this.receiveSiteCode = receiveSiteCode;
	}
	public int getStateCode() {
		return stateCode;
	}
	public void setStateCode(int stateCode) {
		this.stateCode = stateCode;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
