package com.fh.SerialPort.bean;

import com.google.gson.Gson;

public class SerialPortData {
	String dataType;
	Object data;
	public String getDataType() {
		return dataType;
	}
	public Object getData() {
		return data;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return new Gson().toJson(this);
	}
}
