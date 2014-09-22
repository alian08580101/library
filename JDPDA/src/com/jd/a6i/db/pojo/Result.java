package com.jd.a6i.db.pojo;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class Result implements Parcelable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7041883127745153429L;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

	}
	
	protected int code;
	protected String message;

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Result [code=" + code + ", message=" + message + "]";
	}

}
