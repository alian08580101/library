package com.jd.a6i.db.pojo;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class UploadRequest implements Parcelable, Serializable {

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

	}
	private int type;
	private int siteCode;
	private String keyword1;
	private String keyword2;
	private String body;
	private String boxCode;
	private int receiveSiteCode;

	public int getType() {
		return type;
	}

	public int getSiteCode() {
		return siteCode;
	}

	public String getKeyword1() {
		return keyword1;
	}

	public String getKeyword2() {
		return keyword2;
	}

	public String getBody() {
		return body;
	}

	public String getBoxCode() {
		return boxCode;
	}

	public int getReceiveSiteCode() {
		return receiveSiteCode;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setSiteCode(int siteCode) {
		this.siteCode = siteCode;
	}

	public void setKeyword1(String keyword1) {
		this.keyword1 = keyword1;
	}

	public void setKeyword2(String keyword2) {
		this.keyword2 = keyword2;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setBoxCode(String boxCode) {
		this.boxCode = boxCode;
	}

	public void setReceiveSiteCode(int receiveSiteCode) {
		this.receiveSiteCode = receiveSiteCode;
	}

	@Override
	public String toString() {
		return "UploadRequest [type=" + type + ", siteCode=" + siteCode
				+ ", keyword1=" + keyword1 + ", keyword2=" + keyword2
				+ ", body=" + body + ", boxCode=" + boxCode
				+ ", receiveSiteCode=" + receiveSiteCode + "]";
	}

}
