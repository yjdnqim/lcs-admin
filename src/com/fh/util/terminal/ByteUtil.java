package com.fh.util.terminal;

import org.apache.poi.util.ArrayUtil;
import org.springframework.web.servlet.RequestToViewNameTranslator;

import com.mysql.fabric.xmlrpc.base.Data;

public class ByteUtil {
	public static int getNoneZeroLengthOfByteArray(byte[] data) {
		if (data == null) {
			return -1;
		}
		
		for (int i = 0; i < data.length; i++) {
			if (data[i] == 0) {
				return i;
			}
		}
		return 0;
	}

	public static byte[] getLineOfBytes(byte[] data) {
		if (data == null) {
			return null;
		}
		for (int i = 0; i < data.length; i++) {
			if (data[i] == '\n') {
				byte[] ret = new byte[i +1];
				ArrayUtil.arraycopy(data, 0, ret, 0	, i +1);
				return ret;
			}
		}
		return null;
	}
}
