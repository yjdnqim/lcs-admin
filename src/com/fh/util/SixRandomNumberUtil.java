package com.fh.util;

import java.util.Random;

public class SixRandomNumberUtil {
	
	public static String sixRandomNumber() {
		Random random = new Random();
		String result="";
		for (int i=0;i<6;i++) {
			result+=random.nextInt(10);
		}
		System.out.println(result);
		return result;
	}

}
