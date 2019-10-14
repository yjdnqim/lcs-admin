package com.alipay.alipayUtils;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class AllTimes {
	
	public static String alltimes() {
		
		Calendar nowtime = new GregorianCalendar();
		String strDateTime=String.format("%04d", nowtime.get(Calendar.YEAR))+
				String.format("%02d", nowtime.get(Calendar.MONTH)+1) +
				String.format("%02d", nowtime.get(Calendar.DATE)) +
				String.format("%02d", nowtime.get(Calendar.HOUR)) +
				String.format("%02d", nowtime.get(Calendar.MINUTE)) +
				String.format("%02d", nowtime.get(Calendar.SECOND)) +
				String.format("%03d", nowtime.get(Calendar.MILLISECOND));
		System.out.println("strDateTime:"+strDateTime);
		
		return strDateTime;
		//精确到纳秒
		//long nanoTime = System.nanoTime();
		//String namiaoTime = strDateTime + nanoTime;
		//return namiaoTime;
	}
	
}
