package com.jd.a6i.db.pojo;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class BoxQuery extends Result implements Parcelable, Serializable {

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

	public BoxQueryData[] getBoxPackList() {
		return boxPackList;
	}

	public void setBoxPackList(BoxQueryData[] boxPackList) {
		this.boxPackList = boxPackList;
	}

	private BoxQueryData[] boxPackList;
}
