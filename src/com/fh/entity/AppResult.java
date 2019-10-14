package com.fh.entity;

import java.util.List;


/**
 * @Description:移动端响应对象
 * @author: wangwenwen
 * @Date: 2017-9-6下午05:03:13
 */
public class AppResult {
	//响应码
	private String code;
	//提示信息
	private String msg;
	//结果集合
	private List JsonData;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List getJsonData() {
		return JsonData;
	}
	public void setJsonData(List jsonData2) {
		JsonData = jsonData2;
	}
	
	
	
}
