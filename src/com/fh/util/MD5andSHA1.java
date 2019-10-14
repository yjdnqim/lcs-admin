package com.fh.util;

import java.security.MessageDigest;

public class MD5andSHA1 {
	
	public final static String MD5_SHA1(String s, String method) {  
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',  'a', 'b', 'c', 'd', 'e', 'f' };  
        try {  
            byte[] strTemp = s.getBytes();  
            //如果输入“SHA”，就是实现SHA加密。  
            MessageDigest mdTemp = MessageDigest.getInstance(method);   
            mdTemp.update(strTemp);  
            byte[] md = mdTemp.digest();  
            int j = md.length;  
            char str[] = new char[j * 2];  
            int k = 0;  
            for (int i = 0; i < j; i++) {  
                byte byte0 = md[i];  
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];  
                str[k++] = hexDigits[byte0 & 0xf];  
            }  
            return new String(str);  
        } catch (Exception e) {  
            return null;  
        }  
    }  
}
