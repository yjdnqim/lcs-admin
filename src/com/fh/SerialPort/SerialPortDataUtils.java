package com.fh.SerialPort;

import java.util.HashMap;
import java.util.Map;

import com.fh.SerialPort.bean.WeatherData;
import com.fh.SerialPort.bean.ZiDongZhanData;

public class SerialPortDataUtils {
	
	private static HashMap<String, String> weatherDataMapping = new HashMap<String, String>(){{  
	      put("01","雨");
	      put("02","阵雨"); 
	      put("03","毛毛雨"); 
	      put("04","雪"); 
	      put("05","阵雪"); 
	      put("06","雨夹雪"); 
	      put("07","阵性雨夹雪"); 
	      put("08","霰"); 
	      put("09","米雪"); 
	      put("10","冰粒"); 
	      put("11","冰雹"); 
	}};
	
	//降水天气现象的数据格式
	//BG,859022,01,YWTR,001,20161107030000,001,001,17,ANA,03,0,z,0,xD,0,xDV,137,xE,0,xEV,137,xH,0,xHV,051,xJ,0,xJV,01,xK,0,xKV,065,wF_B,0,wF_BV,0210,wF_C,0,wF_CV,0180,vA,0,sA,0,0494,ED
	public static WeatherData getWeatherData(String dataStr) {
		String[] dataArr = dataStr.split(",");
		if(dataArr.length <= 13) {
			return null;
		}
		
		if(dataStr.indexOf("ANA") == -1) {
			return new WeatherData("无");
		}
		
		String statusCode = dataArr[10];
		if(weatherDataMapping.keySet().contains(statusCode)) {
			return new WeatherData(weatherDataMapping.get(statusCode));
		}
		
		return null;
	}
	
	//日照数据
	//BG,812345,00,YSDR,000,20150115100000,001,0004,01,AJT,201501150900,AKA,1,AKB,60,AKC,035,0000,z,0,5018,ED
	public static WeatherData getRiZhaoData(String dataStr) {
		String[] dataArr = dataStr.split(",");
		if(dataStr.indexOf("AKC") == -1) {
			return null;
		}
		if(dataArr.length < 17) {
			return null;
		}
		
		String rizhaoStr = dataArr[16];
		float rizhaorizhaoVal = Float.parseFloat(rizhaoStr) / 10;
		return new WeatherData(rizhaorizhaoVal + "");
	}
	
	//能见度数据
	//20190425150000 50000 50000 15:00 5996 14:01 16699 16699 15:00 5928 14:01 138 218
	public static WeatherData getNengJianDuData(String dataStr) {
		String[] dataArr = dataStr.split(" ");
		String nengJianDuStr = dataArr[1];
		return new WeatherData(nengJianDuStr);
	}
	
	//自动站数据
	//2019-09-26 13:29 111111111111111111111111111111110000000000000 00880000000000000088888888882022 194 0 //// //// 113 0 0 0 0 0 0 0 255 * 18 59 -5 10110 //// //// //// //// //// //// //// //// //// //// 0 0 -9999 99999
	public static ZiDongZhanData getZiDongZhanData(String dataStr) {
		String[] dataArr = dataStr.split(" ");
		if(dataArr.length < 36) {
			return null;
		}
		
		String wendu = dataArr[16];
		if("////".equals(wendu)) {
			wendu = "";
		}else {
			wendu = Float.parseFloat(wendu) / 10 + "";
		}
		
		String shidu = "////".equals(dataArr[18]) ? "" : dataArr[18];
		
		String fengxiang = "////".equals(dataArr[4]) ? "" : dataArr[4];
		
		String fengsu = dataArr[5];
		if("////".equals(fengsu)) {
			fengsu = "";
		}else {
			fengsu = Float.parseFloat(fengsu) / 10 + "";
		}
		
		String yuliang = dataArr[11];
		if("////".equals(yuliang)) {
			yuliang = "";
		}else {
			yuliang = Float.parseFloat(yuliang) * 10 + "";
		}
		
		String qiya = dataArr[21];
		if("////".equals(qiya)) {
			qiya = "";
		}else {
			qiya = Float.parseFloat(qiya) / 10 + "";
		}
		
		return new ZiDongZhanData(wendu, shidu, fengxiang, fengsu, yuliang, qiya);
	}
	
	
	public static void main(String args[]) {
		//                时间（北京时）                      观测数据索引                                                                                             质量控制标志组                                                      2风向
		String dataStr = "2019-09-26 13:29 111111111111111111111111111111110000000000000 00880000000000000088888888882022 194 0 //// //// 113 0 0 0 0 0 0 0 255 * 18 59 -5 10110 //// //// //// //// //// //// //// //// //// //// 0 0 -9999 99999";
		WeatherData weatherData = getRiZhaoData(dataStr);
		System.out.println(dataStr.split(" ").length + "");
	}
}
