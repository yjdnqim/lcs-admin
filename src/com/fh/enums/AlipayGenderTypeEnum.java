package com.fh.enums;

import java.util.ArrayList;
import java.util.List;

import com.fh.util.PageData;

/**
 * 支付宝获取用户信息是的性别类型
 * @author liuchi
 *
 */
public enum AlipayGenderTypeEnum {

	MAN("m","男"),
	WOMAN("f","女")
	;
	
	AlipayGenderTypeEnum(String value,String desc){
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
		for(AlipayGenderTypeEnum enumItem : AlipayGenderTypeEnum.values()){
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
		for(AlipayGenderTypeEnum enumItem : AlipayGenderTypeEnum.values()){
			PageData pd = new PageData();
			pd.put("VALUE", enumItem.getValue());
			pd.put("DESC", enumItem.getDesc());
			pdList.add(pd);
		}
		
		return pdList;
	}
}
