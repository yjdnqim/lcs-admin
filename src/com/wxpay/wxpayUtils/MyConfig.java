package com.wxpay.wxpayUtils;

import com.fh.util.ResourceUtils;
import com.wxpay.sdk.IWXPayDomain;
import com.wxpay.sdk.WXPayConfig;
import com.wxpay.sdk.WXPayConstants;

import java.io.*;


public class MyConfig implements WXPayConfig{
	/**
	 * 商户配置的微信信息
	 */
	public final static String APP_ID = "";
	public final static String APP_SECRET = "";
	public final static String APP_KEY = "";
	public final static String MCH_ID = "";
	
	
    private byte[] certData;
    
    public MyConfig() throws Exception {
    	//正式环境
    	//String certPath = "cert/apiclient_cert.p12";
    	//测试使用
    	String certPath = "cert_ceshi/apiclient_cert.p12";
        File file = new File(ResourceUtils.findResource(certPath));
        System.out.println("---------------------------------");
        System.out.println("file:"+file);
        System.out.println("file.length():"+file.length());
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }
    
	@Override
	public String getAppID() {
		// TODO Auto-generated method stub
       
		return APP_ID;
	}

	@Override
	public String getMchID() {
		// TODO Auto-generated method stub
       
		return MCH_ID;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
       
		return APP_KEY;
	}

	@Override
	public InputStream getCertStream() {
		// TODO Auto-generated method stub
		ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
	}

	@Override
	public int getHttpConnectTimeoutMs() {
		// TODO Auto-generated method stub
		return 8000;
	}

	@Override
	public int getHttpReadTimeoutMs() {
		// TODO Auto-generated method stub
		return 10000;
	}

	@Override
	public IWXPayDomain getWXPayDomain() {
		// TODO Auto-generated method stub
		IWXPayDomain iwxPayDomain = new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }
            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new IWXPayDomain.DomainInfo(WXPayConstants.DOMAIN_API, true);
            }
        };
		return iwxPayDomain;
	}

	@Override
	public boolean shouldAutoReport() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int getReportWorkerNum() {
		// TODO Auto-generated method stub
		return 6;
	}

	@Override
	public int getReportQueueMaxSize() {
		// TODO Auto-generated method stub
		return 10000;
	}

	@Override
	public int getReportBatchSize() {
		// TODO Auto-generated method stub
		return 10;
	}
}