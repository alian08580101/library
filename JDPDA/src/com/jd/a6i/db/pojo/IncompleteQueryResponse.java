package com.jd.a6i.db.pojo;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class IncompleteQueryResponse extends Result implements Parcelable,
		Serializable {

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return super.describeContents();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		super.writeToParcel(dest, flags);
	}
    
	private IncompleteQueryResponseData[] data;

	public IncompleteQueryResponseData[] getData() {
		return data;
	}

	public void setData(IncompleteQueryResponseData[] data) {
		this.data = data;
	}
}
