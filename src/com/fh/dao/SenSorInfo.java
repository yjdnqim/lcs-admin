package com.fh.dao;

public class SenSorInfo {

	private String MON_ID;
	private String MONITOR_INFO;
	private long MONITOR_TIME;
	private String SEN_ID;
	private String SEN_STATUS;
	public String getMON_ID() {
		return MON_ID;
	}
	public void setMON_ID(String mON_ID) {
		MON_ID = mON_ID;
	}
	public String getMONITOR_INFO() {
		return MONITOR_INFO;
	}
	public void setMONITOR_INFO(String mONITOR_INFO) {
		MONITOR_INFO = mONITOR_INFO;
	}
	public String getSEN_ID() {
		return SEN_ID;
	}
	public void setSEN_ID(String sEN_ID) {
		SEN_ID = sEN_ID;
	}
	public String getSEN_STATUS() {
		return SEN_STATUS;
	}
	public void setSEN_STATUS(String sEN_STATUS) {
		SEN_STATUS = sEN_STATUS;
	}
	@Override
	public String toString() {
		return "SenSorInfo [MON_ID=" + MON_ID + ", MONITOR_INFO="
				+ MONITOR_INFO + ", MONITOR_TIME=" + MONITOR_TIME + ", SEN_ID="
				+ SEN_ID + ", SEN_STATUS=" + SEN_STATUS + "]";
	}
	public long getMONITOR_TIME() {
		return MONITOR_TIME;
	}
	public void setMONITOR_TIME(long mONITOR_TIME) {
		MONITOR_TIME = mONITOR_TIME;
	}
	
	
	
}
