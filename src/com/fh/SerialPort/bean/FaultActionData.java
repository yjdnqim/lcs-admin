package com.fh.SerialPort.bean;

import com.google.gson.Gson;

public class FaultActionData {
	String sensorType;
	String action;

	
	
	public String getSensorType() {
		return sensorType;
	}

	public void setSensorType(String sensorType) {
		this.sensorType = sensorType;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public FaultActionData(String sensorType, String action) {
		this.sensorType = sensorType;
		this.action = action;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return new Gson().toJson(this);
	}
}
