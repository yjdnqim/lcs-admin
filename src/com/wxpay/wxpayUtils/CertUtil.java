package com.wxpay.wxpayUtils;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.ssl.SSLContexts;

import com.fh.util.PageData;
import com.fh.util.ResourceUtils;

public class CertUtil {
	
    /**
     * 加载证书
     */
    public static SSLConnectionSocketFactory initCert() throws Exception {
    	/**
		 * 商户配置的微信信息
		 */
//		String APP_ID = AlipayWeChatConfig.getString("WeChatAPP_ID");
//		String APP_SECRET = AlipayWeChatConfig.getString("WeChatAPP_SECRET");
//		String APP_KEY = AlipayWeChatConfig.getString("WeChatAPP_KEY");
		String MCH_ID = "";
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%");
		System.out.println("MCH_ID:"+MCH_ID);
		
    	//正式环境
    	String certPath = "cert/apiclient_cert.p12";
        //测试环境
    	//String certPath = "cert_ceshi/apiclient_cert.p12";
    	File file = new File(ResourceUtils.findResource(certPath));
        
    	FileInputStream instream = new FileInputStream(file);
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(instream, MCH_ID.toCharArray());
 
        if (null != instream) {
            instream.close();
        }
 
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore,MCH_ID.toCharArray()).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
 
        return sslsf;
    }

}
