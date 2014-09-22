package com.jd.a6i.db.pojo;

import java.io.Serializable;

import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Account extends Result implements Parcelable, Serializable {

	/**
	 * 
	 */
	@JsonIgnore(value=true)
	private static final long serialVersionUID = 5258271591877017975L;
	@JsonIgnore(value=true)
	private int id;
	private String erpAccount;
	private String password;
	private int siteCode;
	private String siteName;
	private int staffId;
	private String staffName;
	private int orgId;
	private String dmsCode;
	private int rememberPassword;
	public int getId() {
		return id;
	}
	public String getErpAccount() {
		return erpAccount;
	}
	public String getPassword() {
		return password;
	}
	public int getSiteCode() {
		return siteCode;
	}
	public String getSiteName() {
		return siteName;
	}
	public int getStaffId() {
		return staffId;
	}
	public String getStaffName() {
		return staffName;
	}
	public int getOrgId() {
		return orgId;
	}
	public String getDmsCode() {
		return dmsCode;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setErpAccount(String erpAccount) {
		this.erpAccount = erpAccount;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setSiteCode(int siteCode) {
		this.siteCode = siteCode;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}
	public void setDmsCode(String dmsCode) {
		this.dmsCode = dmsCode;
	}
	public int getRememberPassword() {
		return rememberPassword;
	}
	public void setRememberPassword(int rememberPassword) {
		this.rememberPassword = rememberPassword;
	}
	@Override
	public String toString() {
		return "Account [id=" + id + ", erpAccount=" + erpAccount
				+ ", password=" + password + ", siteCode=" + siteCode
				+ ", siteName=" + siteName + ", staffId=" + staffId
				+ ", staffName=" + staffName + ", orgId=" + orgId
				+ ", dmsCode=" + dmsCode + ", rememberPassword="
				+ rememberPassword + "]";
	}
	
}
