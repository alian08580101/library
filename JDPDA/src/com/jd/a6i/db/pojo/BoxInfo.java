package com.jd.a6i.db.pojo;

public class BoxInfo extends Result{
	private static final long serialVersionUID = -2757860153208277613L;
	/**"boxCode":"TC010F002027W00100001002","createSiteCode":910,"createSiteName":"北京马驹桥分拣中心","receiveSiteCode":5000,"receiveSiteName":"武汉一号库","type":"TC"*/
	private String boxCode;
	private int createSiteCode;
	private String createSiteName;
	private int receiveSiteCode;
	private String receiveSiteName;
	private String type;
	public String getBoxCode() {
		return boxCode;
	}
	public int getCreateSiteCode() {
		return createSiteCode;
	}
	public String getCreateSiteName() {
		return createSiteName;
	}
	public int getReceiveSiteCode() {
		return receiveSiteCode;
	}
	public String getReceiveSiteName() {
		return receiveSiteName;
	}
	public String getType() {
		return type;
	}
	public void setBoxCode(String boxCode) {
		this.boxCode = boxCode;
	}
	public void setCreateSiteCode(int createSiteCode) {
		this.createSiteCode = createSiteCode;
	}
	public void setCreateSiteName(String createSiteName) {
		this.createSiteName = createSiteName;
	}
	public void setReceiveSiteCode(int receiveSiteCode) {
		this.receiveSiteCode = receiveSiteCode;
	}
	public void setReceiveSiteName(String receiveSiteName) {
		this.receiveSiteName = receiveSiteName;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "BoxInfo [boxCode=" + boxCode + ", createSiteCode="
				+ createSiteCode + ", createSiteName=" + createSiteName
				+ ", receiveSiteCode=" + receiveSiteCode + ", receiveSiteName="
				+ receiveSiteName + ", type=" + type + "]";
	}
	
}
