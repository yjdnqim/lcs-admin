package com.fh.SerialPort.bean;

import com.google.gson.Gson;

public class ZiDongZhanData {
	
	String wendu;
	String shidu;
	String fengxiang;
	String fengsu;
	String yuliang;
	String qiya;
	
	
	public String getWendu() {
		return wendu;
	}

	public void setWendu(String wendu) {
		this.wendu = wendu;
	}

	public String getShidu() {
		return shidu;
	}

	public void setShidu(String shidu) {
		this.shidu = shidu;
	}

	public String getFengxiang() {
		return fengxiang;
	}

	public void setFengxiang(String fengxiang) {
		this.fengxiang = fengxiang;
	}

	public String getFengsu() {
		return fengsu;
	}

	public void setFengsu(String fengsu) {
		this.fengsu = fengsu;
	}

	public String getYuliang() {
		return yuliang;
	}

	public void setYuliang(String yuliang) {
		this.yuliang = yuliang;
	}

	public String getQiya() {
		return qiya;
	}

	public void setQiya(String qiya) {
		this.qiya = qiya;
	}

	
	
	public ZiDongZhanData(String wendu, String shidu, String fengxiang, String fengsu, String yuliang, String qiya) {
		this.wendu = wendu;
		this.shidu = shidu;
		this.fengxiang = fengxiang;
		this.fengsu = fengsu;
		this.yuliang = yuliang;
		this.qiya = qiya;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return new Gson().toJson(this);
	}
}
