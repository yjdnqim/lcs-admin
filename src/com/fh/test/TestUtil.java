package com.fh.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.TooManyListenersException;

import org.junit.Test;

import com.fh.SerialPort.ParamConfig;
import com.fh.SerialPort.SerialPortOperationResult;
import com.fh.SerialPort.SerialPortThread;
import com.fh.SerialPort.SerialPortUtils;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

public class TestUtil {
	
	
	
	@SuppressWarnings("unchecked")
	public static void main(String args[]) throws InterruptedException { 
//		ParamConfig paramConfig = new ParamConfig("COM1", 9600, 0, 8, 1);
//		SerialPortThread serialPortThread = new SerialPortThread(paramConfig);
//		serialPortThread.start();
//		serialPortThread.join();
//		System.out.println(serialPortThread.getOperationResult().toString());
		
//		ParamConfig paramConfig2 = new ParamConfig("COM3", 4800, 0, 8, 1);
//		SerialPortThread serialPortThread2 = new SerialPortThread(paramConfig2);
//		serialPortThread2.start();
//		serialPortThread2.join();
//		System.out.println(serialPortThread2.getOperationResult().toString());
		
//		List<String> portList = SerialPortUtils.findSerialPortList();
//		if(portList.size() > 0) {
//			for(String port: portList) {
//				System.out.println(port);
//			}
//		}else {
//			System.out.println("没有检测到串口");
//		}
	}
}
