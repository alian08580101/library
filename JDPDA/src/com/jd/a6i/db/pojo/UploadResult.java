package com.jd.a6i.db.pojo;

import java.io.Serializable;

import android.os.Parcelable;

public class UploadResult extends Result implements Parcelable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 579891991766709662L;
	private String createTime;
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "UploadResult [createTime=" + createTime + "]";
	}
	
}
