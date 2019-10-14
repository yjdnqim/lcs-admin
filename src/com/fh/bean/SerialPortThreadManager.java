package com.fh.bean;

import java.util.ArrayList;
import java.util.List;

import com.fh.SerialPort.ParamConfig;
import com.fh.SerialPort.SerialPortOperationResult;
import com.fh.SerialPort.SerialPortThread;
import com.fh.SerialPort.SerialPortTypeEnum;
import com.fh.service.screen.serialportdatalog.SerialPortDataLogService;

public class SerialPortThreadManager {
	List<SerialPortThread> serialPortThreadList = new ArrayList<SerialPortThread>();
	
	/**
	 * 启动串口
	 * @param port
	 * @throws InterruptedException 
	 */
	public SerialPortOperationResult startSerialPortThread(ParamConfig config, SerialPortTypeEnum serialPortType, SerialPortDataLogService serialportdatalogService) throws InterruptedException {
		SerialPortThread serialPortThread = getSerialPortThread(config.getSerialNumber());
		if(serialPortThread != null) {
			serialPortThread.closeSerialPort();
			serialPortThreadList.remove(serialPortThread);
		}
		serialPortThread = new SerialPortThread(config, serialPortType, serialportdatalogService);
		serialPortThread.start();
		serialPortThread.join();
		SerialPortOperationResult serialPortOperationResult = serialPortThread.getOperationResult();
		if(serialPortOperationResult.isSuc()) {
			serialPortThreadList.add(serialPortThread);
		}else {
			serialPortThread.closeSerialPort();
		}
		return serialPortOperationResult;
	}
	
	/**
	 * 停止串口
	 * @param port
	 */
	public void closeSerialPort(String port) {
		SerialPortThread serialPortThread = getSerialPortThread(port);
		if(serialPortThread != null) {
			serialPortThread.closeSerialPort();
			serialPortThreadList.remove(serialPortThread);
		}
	}
	
	/**
	 * 获取 SerialPortThread
	 * @param port
	 * @return
	 */
	public SerialPortThread getSerialPortThread(String port) {
		SerialPortThread serialPortThread = null;
		for(SerialPortThread item:serialPortThreadList) {
			if(port.equals(item.getParamConfig().getSerialNumber())) {
				serialPortThread = item;
			}
		}
		return serialPortThread;
	}
	
	/**
	 * 停止并删除SerialPortThread
	 * @param port
	 */
	public void deleteSerialPortThread(String port) {
		SerialPortThread serialPortThread = getSerialPortThread(port);
		if(serialPortThread != null) {
			serialPortThread.closeSerialPort();
			serialPortThreadList.remove(serialPortThread);
		}
	}
	
	/**
	 * 获取已启动的串口列表
	 * @return
	 */
	public List<SerialPortThread> getStartedSerialPortThreadList() {
		List<SerialPortThread> startedSerialPortThreadList = new ArrayList<SerialPortThread>();
		for(SerialPortThread item:serialPortThreadList) {
			if(!item.isInterrupted()) {
				startedSerialPortThreadList.add(item);
			}
		}
		return startedSerialPortThreadList;
	}

}
