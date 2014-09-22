package com.jd.a6i.db.pojo;

import java.io.Serializable;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnore;

import android.os.Parcel;
import android.os.Parcelable;

public class AsynUploadLargeSortingInfo implements Parcelable, Serializable {

	@JsonIgnore(value=true)
	private static final long serialVersionUID = 3693453707279699465L;

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String toString() {
		return "AsynUploadLargeSortingInfo [type=" + type + ", siteCode="
				+ siteCode + ", keyword1=" + keyword1 + ", keyword2="
				+ keyword2 + ", body=" + body + ", boxCode="
				+ boxCode + ", receiveSiteCode=" + receiveSiteCode + "]";
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub

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

	public void setType(int type) {
		this.type = type;
	}

	public int getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(int siteCode) {
		this.siteCode = siteCode;
	}

	public String getKeyword1() {
		return keyword1;
	}

	public void setKeyword1(String keyword1) {
		this.keyword1 = keyword1;
	}

	public String getKeyword2() {
		return keyword2;
	}

	public void setKeyword2(String keyword2) {
		this.keyword2 = keyword2;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getBoxCode() {
		return boxCode;
	}

	public void setBoxCode(String boxCode) {
		this.boxCode = boxCode;
	}

	public int getReceiveSiteCode() {
		return receiveSiteCode;
	}

	public void setReceiveSiteCode(int receiveSiteCode) {
		this.receiveSiteCode = receiveSiteCode;
	}
}
