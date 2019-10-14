package com.wxpay.wxpayUtils;

import java.util.Map;
import java.util.TreeMap;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.fh.configuration.Configuration;
import com.fh.configuration.ConfigurationManager;
import com.fh.controller.base.BaseController;
import com.wxpay.sdk.WXPayUtil;
import com.wxpay.sdk.WXPayConstants.SignType;
import com.wxpay.wxpayUtils.ConstantUtil;
import com.wxpay.wxpayUtils.HttpUtil;
import com.wxpay.wxpayUtils.RSAUtils;
/**
 * 企业付款
 * @author lc980
 *
 */
public class WeChatEnterprisePaymentUtils extends BaseController {
	
	public final static Configuration configuration = new ConfigurationManager().getConfiguration();
	/**
	 * 商户配置的微信信息
	 */
	public final static String APP_ID = "";
	public final static String APP_SECRET = "";
	public final static String APP_KEY = "";
	public final static String MCH_ID = "";

	
	/**
	 *    微信企业付款到零钱
	 * @return
	 * @param withDawalDataId -- 商户订单号，需保持唯一性(只能是字母或者数字，不能包含有其他字符)
	 * @param openid -- 商户appid下，某用户的openid
	 * @param withDawalMoney -- 企业付款金额，单位为分
	 * @return
	 */
	public static JSONObject paymentSmallChange(String withDawalDataId, String openid, String withDawalMoney) {
		JSONObject json = new JSONObject();
		
		try {
			//系统部署的服务器IP地址
			String ipAddress = "";
			
			Map<String, String> data = new TreeMap<>();
			data.put("mch_appid", APP_ID);//申请商户号的appid或商户号绑定的appid
			data.put("mchid", MCH_ID);//微信支付分配的商户号
			//data.put("device_info", );//	微信支付分配的终端设备号
			data.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串，不长于32位
			data.put("partner_trade_no", withDawalDataId);//商户订单号，需保持唯一性(只能是字母或者数字，不能包含有其他字符)
			data.put("openid", openid);//商户appid下，某用户的openid
			data.put("check_name", "NO_CHECK");//校验用户姓名选项  NO_CHECK：不校验真实姓名   FORCE_CHECK：强校验真实姓名
			//data.put("re_user_name", );//收款用户真实姓名。如果check_name设置为FORCE_CHECK，则必填用户真实姓名
			data.put("amount", withDawalMoney);//企业付款金额，单位为分
			data.put("desc", "提现");//企业付款备注，必填。注意：备注中的敏感词会被转成字符*
			data.put("spbill_create_ip", ipAddress);//该IP同在商户平台设置的IP白名单中的IP没有关联，该IP可传用户端或者服务端的IP。
	    	
			data.put("sign", WXPayUtil.generateSignature(data, APP_KEY, SignType.MD5));
			
			String mapToXml = WXPayUtil.mapToXml(data);
			System.out.println("-------------------------------------------");
			System.out.println("data:"+data);
			System.out.println("-------------------------------------------");
			
			
            CloseableHttpResponse response =  HttpUtil.Post(ConstantUtil.TRANS_FRES_URL, mapToXml, true);
            String transfersXml = EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println("***************************************");
            System.out.println("transfersXml:"+transfersXml);
            Map<String, String> transferMap = WXPayUtil.xmlToMap(transfersXml);
            System.out.println("***************************************");
            System.out.println("transferMap:"+transferMap);
            System.out.println("***************************************");
            if (transferMap.size()>0) {
                if (transferMap.get("result_code").equals("SUCCESS") && transferMap.get("return_code").equals("SUCCESS")) {
                	/**
                	 * 微信请求成功后操作
                	 */
                	System.out.println("成功");
                	
                	
                	json.put("result", "SUCCESS");
                	json.put("msg", "成功");
                }else {
                	/**
                	 * 微信请求失败后操作
                	 */
                	System.out.println("失败");
                	
        			
                	String causeError = transferMap.get("err_code_des");
    	            json.put("result", "ERROR");
    	            json.put("msg", causeError);
    	        }
            }
        } catch (Exception e) {
            System.out.println("系统异常:"+e.getMessage());
			//throw new BasicRuntimeException(this, "企业付款异常" + e.getMessage());
            json.put("result", "SYSTEM");
            json.put("msg", "系统异常，请联系管理员！");
        }
		return json;
	}
	
	
	
	/**
	 *    微信企业付款到银行卡
	 * @return
	 * @param withDawalDataId -- 商户订单号，需保持唯一性(只能是字母或者数字，不能包含有其他字符)
	 * @param openid -- 商户appid下，某用户的openid
	 * @param withDawalMoney -- 企业付款金额，单位为分
	 * @return
	 */
	public static JSONObject paymentBank(String withDawalDataId, String bankNo, String userName, String bankCode, String withDawalMoney) {
		JSONObject json = new JSONObject();
		
		try {
			Map<String, String> data = new TreeMap<>();
			data.put("mchid", MCH_ID);//微信支付分配的商户号
			data.put("partner_trade_no", withDawalDataId);//商户订单号，需保持唯一（只允许数字[0~9]或字母[A~Z]和[a~z]，最短8位，最长32位）
			data.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串，不长于32位
			
			data.put("enc_bank_no", RSAUtils.encryptData(bankNo));//收款方银行卡号（采用标准RSA算法，公钥由微信侧提供）,详见获取RSA加密公钥API
			data.put("enc_true_name", RSAUtils.encryptData(userName));//收款方用户名（采用标准RSA算法，公钥由微信侧提供）详见获取RSA加密公钥API
			data.put("bank_code", bankCode);//银行卡所在开户行编号,详见银行编号列表
			data.put("amount", withDawalMoney);//付款金额：RMB分（支付总额，不含手续费）  注：大于0的整数
			data.put("desc", "提现");//企业付款到银行卡付款说明,即订单备注（UTF8编码，允许100个字符以内）
	    	
			data.put("sign", WXPayUtil.generateSignature(data, APP_KEY, SignType.MD5));
			
			String mapToXml = WXPayUtil.mapToXml(data);
			System.out.println("-------------------------------------------");
			System.out.println("data:"+data);
			System.out.println("-------------------------------------------");
			
			
            CloseableHttpResponse response =  HttpUtil.Post(ConstantUtil.PAY_BANK_URL, mapToXml, true);
            String transfersXml = EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println("***************************************");
            System.out.println("transfersXml:"+transfersXml);
            Map<String, String> transferMap = WXPayUtil.xmlToMap(transfersXml);
            System.out.println("***************************************");
            System.out.println("transferMap:"+transferMap);
            System.out.println("***************************************");
            if (transferMap.size()>0) {
                if (transferMap.get("result_code").equals("SUCCESS") && transferMap.get("return_code").equals("SUCCESS")) {
                	/**
                	 * 微信请求成功后操作
                	 */
                	System.out.println("成功");
                	
                	
                	json.put("result", "SUCCESS");
                	json.put("msg", "成功");
                }else {
                	/**
                	 * 微信请求失败后操作
                	 */
                	System.out.println("失败");
                	
                	String causeError = transferMap.get("err_code_des");
    	            json.put("result", "ERROR");
    	            json.put("msg", causeError);
    	        }
            }
        } catch (Exception e) {
            System.out.println("系统异常:"+e.getMessage());
			//throw new BasicRuntimeException(this, "企业付款异常" + e.getMessage());
            json.put("result", "SYSTEM");
            json.put("msg", "系统异常，请联系管理员！");
        }
		return json;
	}
	
	
}
