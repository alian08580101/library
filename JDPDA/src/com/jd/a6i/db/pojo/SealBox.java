package com.jd.a6i.db.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import android.os.Parcelable;

public class SealBox extends Result implements Parcelable, Serializable {
	@JsonDeserialize
	@JsonIgnore(value=true)
	private static final long serialVersionUID = -6212434414438750342L;
	@JsonIgnore(value=true)
	private int id;
	private String sealCode;
	private String boxCode;
	public int getId() {
		return id;
	}
	public String getSealCode() {
		return sealCode;
	}
	public String getBoxCode() {
		return boxCode;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setSealCode(String sealCode) {
		this.sealCode = sealCode;
	}
	public void setBoxCode(String boxCode) {
		this.boxCode = boxCode;
	}
	@Override
	public String toString() {
		return "SealBox [id=" + id + ", sealCode=" + sealCode + ", boxCode="
				+ boxCode + "]";
	}
}
