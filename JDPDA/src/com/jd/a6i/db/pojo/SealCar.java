package com.jd.a6i.db.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import android.os.Parcelable;

public class SealCar extends Result implements Parcelable, Serializable {

	/**
	 * 
	 */
	@JsonDeserialize
	@JsonIgnore(value=true)
	private static final long serialVersionUID = -3318768216439871270L;
	@JsonIgnore(value=true)
	private int id;
	private String sealCode;
	private String vehicleCode;
	private int driverCode;
	private String driver;

	public int getId() {
		return id;
	}

	public String getSealCode() {
		return sealCode;
	}

	public String getVehicleCode() {
		return vehicleCode;
	}

	public int getDriverCode() {
		return driverCode;
	}

	public String getDriver() {
		return driver;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setSealCode(String sealCode) {
		this.sealCode = sealCode;
	}

	public void setVehicleCode(String vehicleCode) {
		this.vehicleCode = vehicleCode;
	}

	public void setDriverCode(int driverCode) {
		this.driverCode = driverCode;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	@Override
	public String toString() {
		return "SealCar [id=" + id + ", sealCode=" + sealCode
				+ ", vehicleCode=" + vehicleCode + ", driverCode=" + driverCode
				+ ", driver=" + driver + "]";
	}

}
