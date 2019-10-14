package com.fh.SerialPort;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import gnu.io.CommPortIdentifier;

public class SerialPortUtils {
	/**
	  * 查找所有可用端口
	  * @return 可用端口名称列表
	  */
	 public static List<String> findSerialPortList() {
		//获得当前所有可用串口
		  @SuppressWarnings("unchecked")
		  Enumeration<CommPortIdentifier> serialPortList = CommPortIdentifier.getPortIdentifiers(); 
		  List<String> portNameList = new ArrayList<String>();
		  
		  //将可用串口名添加到List并返回该List
		  while (serialPortList.hasMoreElements()) {
			  CommPortIdentifier commPort = serialPortList.nextElement();
			  // 判断是否是串口
	          if (commPort.getPortType() == CommPortIdentifier.PORT_SERIAL) {
	        	  String serialPortName = commPort.getName();
	        	  portNameList.add(serialPortName);
	          }
		  }
		  return portNameList;
	 }
	 
}
