package com.fh.SerialPort;

import java.util.ArrayList;
import java.util.List;

import com.fh.util.PageData;

/**
 * 支付宝获取用户信息是的性别类型
 * @author liuchi
 *
 */
public enum SerialPortTypeEnum {

	ZIDONGZHAN("49bb9487ac5546bab7d651cb5cb34ae0", "自动站"),
	JIANSHUI("4ebaeb66ac6d448f98f8faee3e72d711", "降水"),
	NENGJIANDU("5f9dc07b784e427c929cb6c13d1eaeed", "能见度"),
	RIZHAO("fb9aeaf2cd68414ea9fd9c78f831f187", "日照");
	
	SerialPortTypeEnum(String value,String desc){
		this.value = value;
		this.desc = desc;
	}
	private String value;
	private String desc;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	/**
	 * getSerialPortTypByValue
	 * @param value
	 * @return
	 */
	public static SerialPortTypeEnum getSerialPortTypByValue(String value){
		SerialPortTypeEnum serialPortType = null;
		switch (value) {
		case "49bb9487ac5546bab7d651cb5cb34ae0":
			serialPortType = ZIDONGZHAN;
			break;
		case "4ebaeb66ac6d448f98f8faee3e72d711":
			serialPortType = JIANSHUI;
			break;
		case "5f9dc07b784e427c929cb6c13d1eaeed":
			serialPortType = NENGJIANDU;
			break;
		case "fb9aeaf2cd68414ea9fd9c78f831f187":
			serialPortType = RIZHAO;
			break;
		default:
			break;
		}
		return serialPortType;
	}
	
	/**
	 * 获取状态列表
	 * @return
	 */
	public static List<PageData> GetStatusList(){
		List<PageData> pdList = new ArrayList<PageData>();
		for(SerialPortTypeEnum enumItem : SerialPortTypeEnum.values()){
			PageData pd = new PageData();
			pd.put("VALUE", enumItem.getValue());
			pd.put("DESC", enumItem.getDesc());
			pdList.add(pd);
		}
		
		return pdList;
	}
}
