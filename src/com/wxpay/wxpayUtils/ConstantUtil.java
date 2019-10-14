package com.wxpay.wxpayUtils;

import com.wxpay.sdk.WXPayConstants.SignType;

public class ConstantUtil {
	
	//统一下单所用的签名方式默认为MD5
	public static SignType typeSign = SignType.MD5; 
	
    /**
     * 获取预支付id的接口url(微信提供的支付接口链接)
     */
    public final static String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    
    /**
     * 微信服务器回调通知url  yes
     * http://ntqcoffee.resolr.com
     */
    public static String NOTIFY_URL=""; //可以访问的url
    
    /**
     * 微信服务器查询订单url
     */
    public final static String ORDER_QUERY_URL="https://api.mch.weixin.qq.com/pay/orderquery";
    
    /**
     * 企业付款到零钱请求url
     */
    public final static String TRANS_FRES_URL="https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
    
    /**
     * 企业付款到银行卡请求url
     */
    public final static String PAY_BANK_URL="https://api.mch.weixin.qq.com/mmpaysptrans/pay_bank";
    
    /**
     * 获取RSA的公共密钥的请求url
     */
    public final static String GET_RSAPUBLICKEY_URL="https://fraud.mch.weixin.qq.com/risk/getpublickey";
    
}
