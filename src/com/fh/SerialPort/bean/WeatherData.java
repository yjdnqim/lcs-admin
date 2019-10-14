package com.fh.SerialPort.bean;

import com.google.gson.Gson;

public class WeatherData {
	String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public WeatherData(String val) {
		value = val;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return new Gson().toJson(this);
	}
}
