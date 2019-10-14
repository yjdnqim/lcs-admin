package com.sms.smsUtils;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.fh.controller.base.BaseController;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.SixRandomNumberUtil;
/**
 * 短信服务
 * @author lc980
 *
 */
public class SmsServiceController extends BaseController {
	
	
	/**
	 * 发送短信（阿里云）
	 * @param accessId -- ACCESS_KEY_ID 
	 * @param accessSecret -- ACCESS_KEY_SECRET
	 * @param messageSignAtures -- 短信签名
	 * @param messageTemplate -- 短信模板编号
	 * @param sendContent -- 发送短信的手机号
	 * @param sendContent -- 发送的内容
	 * "{\"code\":\""+sendContent+"\"}"
	 * //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为="{\"name\":\"Tom\", \"code\":\"123\"}"
	 * //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
	 * @return
	 */
	public static JSONObject sendSms(String accessId, String accessSecret, String messageSignAtures, String messageTemplate, String cellphone, String sendContent) {
		JSONObject json = new JSONObject();
		
		try {
			//设置超时时间-可自行调整
			System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
			System.setProperty("sun.net.client.defaultReadTimeout", "10000");
			//初始化ascClient需要的几个参数
			final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
			final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
			//替换成你的AK
			final String accessKeyId = accessId;//你的accessKeyId,参考本文档步骤2
			final String accessKeySecret = accessSecret;//你的accessKeySecret，参考本文档步骤2
			//初始化ascClient,暂时不支持多region（请勿修改）
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
			IAcsClient acsClient = new DefaultAcsClient(profile);
			//组装请求对象
			SendSmsRequest request1 = new SendSmsRequest();
			//使用post提交
			request1.setMethod(MethodType.POST);
			//必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为国际区号+号码，如“85200000000”
			request1.setPhoneNumbers(cellphone);//手机号
			//必填:短信签名-可在短信控制台中找到
			request1.setSignName(messageSignAtures);
			//必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
			request1.setTemplateCode(messageTemplate);
			//可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为="{\"name\":\"Tom\", \"code\":\"123\"}"
			//友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
			request1.setTemplateParam(sendContent);
			//可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
			//request.setSmsUpExtendCode("90997");
			//可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
			//request1.setOutId("yourOutId");
			//请求失败这里会抛ClientException异常
			SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request1);
			if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
				//请求成功
				System.out.println("用户绑定手机号验证码发送成功！！！");
				json.put("result", "SUCCESS");
				json.put("msg", "成功");
			}
			if(sendSmsResponse.getCode() == null && !sendSmsResponse.getCode().equals("OK")) {
				//请求失败
				System.out.println("用户绑定手机号验证码发送失败！！！");
				json.put("result", "ERROR");
				json.put("msg", "失败");
			}
		}catch (Exception e) {
			// TODO: handle exception
			json.put("result", "SYSTEM");
			json.put("msg", "系统故障");
		}
		return json;
	}
}
