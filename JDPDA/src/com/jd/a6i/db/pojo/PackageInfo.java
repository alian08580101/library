package com.jd.a6i.db.pojo;

public class PackageInfo {
	/*"boxCode":"TC010F002027W00100001002",
	"businessType":20,
	"createSiteCode":480,
	"createSiteName":"南京配送中心44",
	"operateTime":"2014-05-05 10:02:45",
	"operateType":1,
	"operateUserCode":7595,
	"operateUserName":"金大中",
	"receiveSiteCode":5000,
	"packageCode":"170188553-3-3-"*/
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
	public int getCreateSiteCode() {
		return createSiteCode;
	}
	public void setCreateSiteCode(int createSiteCode) {
		this.createSiteCode = createSiteCode;
	}
	public int getBusinessType() {
		return businessType;
	}
	public String getCreateSiteName() {
		return createSiteName;
	}
	public String getOperateTime() {
		return operateTime;
	}
	public int getOperateType() {
		return operateType;
	}
	public int getOperateUserCode() {
		return operateUserCode;
	}
	public String getOperateUserName() {
		return operateUserName;
	}
	public int getReceiveSiteCode() {
		return receiveSiteCode;
	}
	public String getPackageCode() {
		return packageCode;
	}
	public void setBoxCode(String boxCode) {
		this.boxCode = boxCode;
	}
	public void setBusinessType(int businessType) {
		this.businessType = businessType;
	}
	public void setCreateSiteName(String createSiteName) {
		this.createSiteName = createSiteName;
	}
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	public void setOperateType(int operateType) {
		this.operateType = operateType;
	}
	public void setOperateUserCode(int operateUserCode) {
		this.operateUserCode = operateUserCode;
	}
	public void setOperateUserName(String operateUserName) {
		this.operateUserName = operateUserName;
	}
	public void setReceiveSiteCode(int receiveSiteCode) {
		this.receiveSiteCode = receiveSiteCode;
	}
	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}
	@Override
	public String toString() {
		return "PackageInfo [boxCode=" + boxCode + ", businessType="
				+ businessType + ", createSiteCode=" + createSiteCode
				+ ", createSiteName=" + createSiteName + ", operateTime="
				+ operateTime + ", operateType=" + operateType
				+ ", operateUserCode=" + operateUserCode + ", operateUserName="
				+ operateUserName + ", receiveSiteCode=" + receiveSiteCode
				+ ", packageCode=" + packageCode + "]";
	}
}
