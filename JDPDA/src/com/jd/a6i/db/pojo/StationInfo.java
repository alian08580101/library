package com.jd.a6i.db.pojo;

public class StationInfo extends Result {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9204010975021387739L;
	/**{"code":200,
	 * "message":"OK",
	 * "siteCode":2201,
	 * "siteName":"南通如东合作站",
	 * "siteType":16,
	 * "dmsCode":"513R001"}*/
	private int siteCode;
	private String siteName;
	private int siteType;
	private String dmsCode;
	public int getSiteCode() {
		return siteCode;
	}
	public String getSiteName() {
		return siteName;
	}
	public int getSiteType() {
		return siteType;
	}
	public String getDmsCode() {
		return dmsCode;
	}
	public void setSiteCode(int siteCode) {
		this.siteCode = siteCode;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public void setSiteType(int siteType) {
		this.siteType = siteType;
	}
	public void setDmsCode(String dmsCode) {
		this.dmsCode = dmsCode;
	}
	@Override
	public String toString() {
		return "StationInfo [siteCode=" + siteCode + ", siteName=" + siteName
				+ ", siteType=" + siteType + ", dmsCode=" + dmsCode + "]";
	}
}
