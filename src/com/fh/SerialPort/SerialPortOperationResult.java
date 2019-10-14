package com.fh.SerialPort;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.google.gson.Gson;

public class SerialPortOperationResult {
	
	boolean isSuc;
	int errCode;
	String errMsg;
	String portName;
	
	public SerialPortOperationResult(String portName, boolean isSuc, int errCode, String errMsg) {
		this.portName = portName;
		this.isSuc = isSuc;
		this.errCode = errCode;
		try {
			this.errMsg = URLEncoder.encode(errMsg,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		
	}
	
	public boolean isSuc() {
		return isSuc;
	}
	
	public int getErrCode() {
		return errCode;
	}
	
	public String getErrMsg() {
		return errMsg;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return new Gson().toJson(this);
	}
}
