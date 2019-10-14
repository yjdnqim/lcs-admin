package com.fh.dao;

public class SensorBean {

	//传感器ID
	private String SEN_ID;
	//传感器名称
	private String SEN_NAME;
	//位置ID
	private String TERMINAL_ID;
	//传感器状态
	private String SEN_STATUS;
	//传感器类型ID
	private String SENTYPE_ID;
	//正常频率
	private String COMMON_RATE;
	//高频频率
	private String HIGHT_RATE;
	//传感器信息
	private String SEN_INFO;
	
	
	public String getSEN_ID() {
		return SEN_ID;
	}
	public void setSEN_ID(String sEN_ID) {
		SEN_ID = sEN_ID;
	}
	public String getSEN_NAME() {
		return SEN_NAME;
	}
	public void setSEN_NAME(String sEN_NAME) {
		SEN_NAME = sEN_NAME;
	}
	public String getTERMINAL_ID() {
		return TERMINAL_ID;
	}
	public void setTERMINAL_ID(String tERMINAL_ID) {
		TERMINAL_ID = tERMINAL_ID;
	}
	public String getSEN_STATUS() {
		return SEN_STATUS;
	}
	public void setSEN_STATUS(String sEN_STATUS) {
		SEN_STATUS = sEN_STATUS;
	}
	public String getSENTYPE_ID() {
		return SENTYPE_ID;
	}
	public void setSENTYPE_ID(String sENTYPE_ID) {
		SENTYPE_ID = sENTYPE_ID;
	}
	public String getCOMMON_RATE() {
		return COMMON_RATE;
	}
	public void setCOMMON_RATE(String cOMMON_RATE) {
		COMMON_RATE = cOMMON_RATE;
	}
	public String getHIGHT_RATE() {
		return HIGHT_RATE;
	}
	public void setHIGHT_RATE(String hIGHT_RATE) {
		HIGHT_RATE = hIGHT_RATE;
	}
	public String getSEN_INFO() {
		return SEN_INFO;
	}
	public void setSEN_INFO(String sEN_INFO) {
		SEN_INFO = sEN_INFO;
	}
	@Override
	public String toString() {
		return "SensorBean [SEN_ID=" + SEN_ID + ", SEN_NAME=" + SEN_NAME
				+ ", TERMINAL_ID=" + TERMINAL_ID + ", SEN_STATUS=" + SEN_STATUS
				+ ", SENTYPE_ID=" + SENTYPE_ID + ", COMMON_RATE=" + COMMON_RATE
				+ ", HIGHT_RATE=" + HIGHT_RATE + ", SEN_INFO=" + SEN_INFO + "]";
	}
	
	
	
	
	
}
