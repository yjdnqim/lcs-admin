package com.fh.enums;

import java.util.ArrayList;
import java.util.List;

import com.fh.util.PageData;

/**
 * 微信企业付款到银行卡
 * （银行卡编号列表）
 * @author liuchi
 *
 */
public enum WeChatBankTypeEnum {
	ICBC_BANK("1002","工商银行"),
	ABC_BANK("1005","农业银行"),
	BOC_BANK("1026","中国银行"),
	CCB_BANK("1003","建设银行"),
	CMB_BANK("1001","招商银行"),
	PSBC_BANK("1066","邮储银行"),
	BCM_BANK("1020","交通银行"),
	SPDB_BANK("1004","浦发银行"),
	CMBC_BANK("1006","民生银行"),
	CIB_BANK("1009","兴业银行"),
	PAB_BANK("1010","平安银行"),
	CITIC_BANK("1021","中信银行"),
	HXB_BANK("1025","华夏银行"),
	CGB_BANK("1027","广发银行"),
	CEB_BANK("1022","光大银行"),
	BOB_BANK("4836","北京银行"),
	NBB_BANK("1056","宁波银行"),
	SHB_BANK("1024","上海银行")
	;
	
	WeChatBankTypeEnum(String value,String desc){
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
	 * 根据value获取描述
	 * @param value
	 * @return
	 */
	public static String GetDescByValue(String value){
		for(WeChatBankTypeEnum enumItem : WeChatBankTypeEnum.values()){
			if(enumItem.getValue() == value){
				return enumItem.getDesc();
			}
		}
		return "";
	}
	
	/**
	 * 获取状态列表
	 * @return
	 */
	public static List<PageData> GetStatusList(){
		List<PageData> pdList = new ArrayList<PageData>();
		for(WeChatBankTypeEnum enumItem : WeChatBankTypeEnum.values()){
			PageData pd = new PageData();
			pd.put("VALUE", enumItem.getValue());
			pd.put("DESC", enumItem.getDesc());
			pdList.add(pd);
		}
		
		return pdList;
	}
}
