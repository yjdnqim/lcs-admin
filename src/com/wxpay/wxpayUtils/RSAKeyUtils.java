package com.wxpay.wxpayUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.fh.configuration.Configuration;
import com.fh.configuration.ConfigurationManager;
import com.fh.util.UuidUtil;
import com.wxpay.sdk.WXPayConstants.SignType;
import com.wxpay.sdk.WXPayUtil;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.nio.file.Paths.get;

/**
 * 描述：
 *
 * @author chen_q_i@163.com
 * 2018/5/9 : 18:43.
 * @version : 1.0
 */

public class RSAKeyUtils {

    Logger log = LoggerFactory.getLogger(RSAKeyUtils.class);
    private static String PUBLIC_KEY_FILE_NAME = "rsa_public.pem";
    
    public final static Configuration configuration = new ConfigurationManager().getConfiguration();
    /**
	 * 商户配置的微信信息
	 */
	public final static String APP_ID = "";
	public final static String APP_SECRET = "";
	public final static String APP_KEY = "";
	public final static String MCH_ID = "";
    
    /**
     * 第一次从微信拿到公钥，落地生成本地文件 需要转PKCS#8
     *
     * @return
     * @throws IllegalAccessException
     * @throws IOException
     */
    public JSON getPubKeyForRemote() {
    	JSONObject json = new JSONObject();
		try {
			Map<String, String> data = new TreeMap<>();
			data.put("mchid", MCH_ID);//微信支付分配的商户号
			data.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串，不长于32位
			data.put("sign_type", SignType.MD5 + "");//	签名类型，支持HMAC-SHA256和MD5。
			data.put("sign", WXPayUtil.generateSignature(data, APP_KEY, SignType.MD5));
			
			String mapToXml = WXPayUtil.mapToXml(data);
			System.out.println("-------------------------------------------");
			System.out.println("data:"+data);
			System.out.println("-------------------------------------------");
			
			
            CloseableHttpResponse response =  HttpUtil.Post(ConstantUtil.GET_RSAPUBLICKEY_URL, mapToXml, true);
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
                	
                	savePubKeyToLocal(transferMap.get("pub_key"));
                	json.put("data", "SUCCESS");
                	json.put("msg", "成功");
                	json.put("RSA_PUBLICKEY", transferMap.get("pub_key"));
                }else {
                	/**
                	 * 微微信请求失败后操作
                	 */
                	System.out.println("失败");
                	
        			
                	String causeError = transferMap.get("err_code_des");
    	            json.put("data", "ERROR");
    	            json.put("msg", causeError);
    	        }
            }
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("系统异常:"+e.getMessage());
            json.put("data", "SYSTEM");
            json.put("msg", "系统异常，请联系管理员！");
		}
		return json;
    }

    /**
     * 将公钥保存到本地
     *
     * @param pubKey
     */
    private void savePubKeyToLocal(String pubKey) {
        String classPath = this.getClass().getResource("/").getPath();
        log.info(classPath);
        File file = new File(classPath + PUBLIC_KEY_FILE_NAME);
        String absolutePath = file.getAbsolutePath();
        System.out.println(absolutePath);
        Path path = get(absolutePath);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(pubKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 读取本地公钥
     * 将公钥放入spring容器，这样就不必每次用的时候都要读取
     *
     * @return
     * @throws IOException
     */
    public static String readLocalPubKey() throws IOException {
    	String classPath="/cert/";
        
    	File file = new File(classPath + PUBLIC_KEY_FILE_NAME);
        String absolutePath = file.getAbsolutePath();
        List<String> lines = Files.readAllLines(Paths.get(absolutePath), StandardCharsets.UTF_8);
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            if (line.charAt(0) == '-') {
                continue;
            } else {
                sb.append(line);
                sb.append('\r');
            }
        }
        return sb.toString();
    }


}

/*<!--放入spring容器-->
 <bean id="rsaKeyUtils" class="com.chen.utils.wechat.RSAKeyUtils"/>
 <bean id="rsaPubK" factory-bean="rsaKeyUtils" factory-method="readLocalPubKey"/>*/

