package com.fh.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TooManyListenersException;

import com.fh.SerialPort.bean.FaultActionData;
import com.fh.SerialPort.bean.SerialPortData;
import com.fh.SerialPort.bean.WeatherData;
import com.fh.SerialPort.bean.ZiDongZhanData;
import com.fh.service.screen.serialportdatalog.SerialPortDataLogService;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;
import com.fh.util.WebSocket;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;


public class SerialPortThread extends Thread implements SerialPortEventListener {

	Timer timer;
    // RS232串口
    private SerialPort serialPort;
    // 输入流
    private InputStream inputStream;
    // 输出流
    private OutputStream outputStream;
    // 保存串口返回信息
    private String dataStr;
    // 保存串口返回信息十六进制
    private String dataHex;
    
    private ParamConfig paramConfig;
    
    SerialPortOperationResult operationResult;
    
    SerialPortTypeEnum serialPortType;
    
    SerialPortDataLogService serialportdatalogService;
    
    // 串口发送获取数据指令的频率  20s
    private long period = 20000;
    
    private String packageStartSign = ""; // 数据包开始标记
    
    private String packageEndSign = ""; // 数据包结束标记
    
    private String completePackageStr = ""; // 完整数据包
    
    public SerialPortThread(ParamConfig config, SerialPortTypeEnum type, SerialPortDataLogService service) {
    	
    	paramConfig = config;
    	serialPortType = type;
    	serialportdatalogService = service;
    	
    	// 根据串口类型 设置 开始/结束 标记
    	if(SerialPortTypeEnum.RIZHAO.equals(serialPortType)) {
    		packageStartSign = "BG";
    		packageEndSign = "ED";
    	}else if(SerialPortTypeEnum.NENGJIANDU.equals(serialPortType)) {
    		packageStartSign = "";
    		packageEndSign = "\r\n";
    	}else if(SerialPortTypeEnum.ZIDONGZHAN.equals(serialPortType)) {
    		packageStartSign = "";
    		packageEndSign = "\r\n";
    	}else if(SerialPortTypeEnum.JIANSHUI.equals(serialPortType)) {
    		packageStartSign = "BG";
    		packageEndSign = "ED";
    	}
    	
    }
    
    public SerialPortOperationResult getOperationResult() {
		return operationResult;
	}
    
    public ParamConfig getParamConfig() {
		return paramConfig;
	}
    
    public void run() {
    	// 打开串口
    	openSerialPort();
    }
    
    
    /**
     * 初始化串口
     * @Description: TODO
     * @param: paramConfig  存放串口连接必要参数的对象（会在下方给出类代码）    
     * @return: void      
     * @throws
     */
    public void openSerialPort() {
    	
    	try {
    		CommPortIdentifier commPort = CommPortIdentifier.getPortIdentifier(paramConfig.getSerialNumber());
            // open:（应用程序名【随意命名】，阻塞时等待的毫秒数）
            serialPort = (SerialPort) commPort.open(Object.class.getSimpleName(), 2000);
            // 设置串口监听
            serialPort.addEventListener(this);
            // 设置串口数据时间有效(可监听)
            serialPort.notifyOnDataAvailable(true);
            // 设置串口通讯参数:波特率，数据位，停止位,校验方式
            serialPort.setSerialPortParams(paramConfig.getBaudRate(), paramConfig.getDataBit(),
                    paramConfig.getStopBit(), paramConfig.getCheckoutBit());
            
            
            operationResult = new SerialPortOperationResult(paramConfig.getSerialNumber(), true, 0, "串口"+ paramConfig.getSerialNumber() +"打开成功");
            
    	} catch (PortInUseException e) {
        	operationResult = new SerialPortOperationResult(paramConfig.getSerialNumber(), false, 1, "串口被占用");
        } catch (TooManyListenersException e) {
        	operationResult = new SerialPortOperationResult(paramConfig.getSerialNumber(), false, 2, "监听过多");
        } catch (UnsupportedCommOperationException e) {
        	operationResult = new SerialPortOperationResult(paramConfig.getSerialNumber(), false, 3, "不支持的串口类型");
        }catch (NoSuchPortException e) {
			// TODO Auto-generated catch block
        	operationResult = new SerialPortOperationResult(paramConfig.getSerialNumber(), false, 4, "没有" + paramConfig.getSerialNumber() + "端口");
		}
    }
    
    /**
     * 开始循环发送获取数据命令
     */
    public void startSendGetDataCommTimer() {
    	if(timer != null) {
    		return;
    	}
    	timer = new Timer();
    	timer.schedule(new TimerTask() {
            @Override
            public void run() {
            	sendGetDataComm();
            }
        }, 1000, period);
	}
    
    /**
     * 停止循环发送获取数据命令
     */
    public void stopSendGetDataCommTimer() {
    	if(timer == null) {
    		return;
    	}
    	timer.cancel();
    	timer = null;
    }
    
    /**
     * 发送获取数据命令
     */
    public void sendGetDataComm() {
    	if(SerialPortTypeEnum.ZIDONGZHAN.equals(serialPortType)) {
    		sendComm("DMGD");
        }else if(SerialPortTypeEnum.JIANSHUI.equals(serialPortType)){
        	sendComm("READDATA");
		}else if(SerialPortTypeEnum.NENGJIANDU.equals(serialPortType)){
			sendComm("GETMINDATA!");
		}else if(SerialPortTypeEnum.RIZHAO.equals(serialPortType)){
			sendComm("READDATA");
		}
    }
 
    /**
     * 实现接口SerialPortEventListener中的方法 读取从串口中接收的数据
     */
    @Override
    public void serialEvent(SerialPortEvent event) {
        switch (event.getEventType()) {
        case SerialPortEvent.BI: // 通讯中断
        case SerialPortEvent.OE: // 溢位错误
        case SerialPortEvent.FE: // 帧错误
        case SerialPortEvent.PE: // 奇偶校验错误
        case SerialPortEvent.CD: // 载波检测
        case SerialPortEvent.CTS: // 清除发送
        case SerialPortEvent.DSR: // 数据设备准备好
        case SerialPortEvent.RI: // 响铃侦测
        case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 输出缓冲区已清空
            break;
        case SerialPortEvent.DATA_AVAILABLE: // 有数据到达
            // 调用读取数据的方法
            readComm();
            break;
        default:
            break;
        }
    }
 
    /**
     * 读取串口返回信息
     * @return: void      
     */
    public void readComm() {
        try {
            inputStream = serialPort.getInputStream();
            // 通过输入流对象的available方法获取数组字节长度
            byte[] readBuffer = new byte[inputStream.available()];
            // 从线路上读取数据流
            int len = 0;
            while ((len = inputStream.read(readBuffer)) != -1) {
                //获取串口返回数据
            	dataStr = new String(readBuffer, 0, len).trim();
                //转为十六进制数据
                dataHex = bytesToHexString(readBuffer);
                System.out.println(paramConfig.getSerialNumber()+ "收到数据:");
                System.out.println("dataStr:" + dataStr);
                System.out.println("dataHex:" + dataHex);// 读取后置空流对象
                
            	if(dataStr.startsWith("CM:K") && dataStr.endsWith("OK")) { // 判断是否为 故障命令结果反馈
            		formatFaultBackComm(dataStr);
            		return;
            	}
                
                if(packageStartSign.length() != 0 && dataStr.startsWith(packageStartSign)) { // 如果接收到开始标记，清空数据包。
                	completePackageStr = "";
                }
                
                // 追加数据
                completePackageStr += dataStr;
                
                if(completePackageStr.endsWith(packageEndSign)) { // 如果接收到结束标记，表示数据包已完整
            		// 保存数据日志
                    saveSerialPortDataLog();
            		// 解析数据
            		formatSerialPortData();
            		
            		// 处理完数据后，清空数据包
            		completePackageStr = "";
            	}
        		
                inputStream.close();
                inputStream = null;
                break;
            }
        } catch (IOException e) {
        	e.printStackTrace();  
        }
    }
    
    /**
     * 保存数据日志
     */
    private void saveSerialPortDataLog() {
    	
    	byte[] writerBuffer = completePackageStr.getBytes();
    	String completePackageHexStr = bytesToHexString(writerBuffer); // 6进制数据
    	
    	PageData pd = new PageData();
		pd.put("SERIALPORTDATALOG_ID", UuidUtil.get32UUID());	//主键
		pd.put("SERIALPORT_ID", serialPortType.getValue());	//串口ID
		pd.put("DATACONTENT", completePackageStr);	//字符串数据
		pd.put("DATACONTENT_HEX", completePackageHexStr);	//16进制数据
		pd.put("TIME", DateUtil.getTime());	//时间
		pd.put("DATATYPE", "0");	//收发类型
		pd.put("EXTEND_1", "");	//扩展字段1
		pd.put("EXTEND_2", "");	//扩展字段2
		pd.put("EXTEND_3", "");	//扩展字段3
		try {
			serialportdatalogService.save(pd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * 解析数据并websocket通知前端
     * @param dataStr
     */
    private void formatSerialPortData() {
    	
    	if(completePackageStr == null || completePackageStr.length() == 0) {
    		return;
    	}
    	
    	SerialPortData serialPortData = new SerialPortData();
        
        if(SerialPortTypeEnum.JIANSHUI.equals(serialPortType)) {
        	serialPortData.setDataType("JIANSHUI");
        	WeatherData weatherData = SerialPortDataUtils.getWeatherData(completePackageStr);
        	serialPortData.setData(weatherData);
        }else if(SerialPortTypeEnum.RIZHAO.equals(serialPortType)) {
        	serialPortData.setDataType("RIZHAO");
        	WeatherData rizhaoData = SerialPortDataUtils.getRiZhaoData(completePackageStr);
        	serialPortData.setData(rizhaoData);
        }else if(SerialPortTypeEnum.NENGJIANDU.equals(serialPortType)) {
        	serialPortData.setDataType("NENGJIANDU");
        	WeatherData nengjianduData = SerialPortDataUtils.getNengJianDuData(completePackageStr);
        	serialPortData.setData(nengjianduData);
        }else if(SerialPortTypeEnum.ZIDONGZHAN.equals(serialPortType)) {
        	serialPortData.setDataType("ZIDONGZHAN");
        	ZiDongZhanData ziDongZhanData = SerialPortDataUtils.getZiDongZhanData(completePackageStr);
        	serialPortData.setData(ziDongZhanData);
        }
        
        if(serialPortData.getData() != null) {
        	WebSocket.sendToAll(serialPortData.toString());
        }
    }
    
    /**
     * 解析故障命令结果反馈
     */
    private void formatFaultBackComm(String faultBackComm) {
    	//CM:K1_ON OK
    	String sensorParams = "";
    	String action = "";
    	if(faultBackComm.contains("ON")) {
    		action = "ON";
    		sensorParams = faultBackComm.replace("CM:K", "");
    		sensorParams = sensorParams.replace("_ON OK", "");
    	}else if(faultBackComm.contains("OFF")) {
    		action = "OFF";
    		sensorParams = faultBackComm.replace("CM:K", "");
    		sensorParams = sensorParams.replace("_OFF OK", "");
    	}
    	
    	if(sensorParams.length() == 0) {
    		return;
    	}
    	
    	String serialPortID = serialPortType.getValue();
    	PageData pd = new PageData();
    	pd.put("FAULTPARAMS", sensorParams);
    	pd.put("SERIALPORT_ID", serialPortID);
    	
    	try {
			PageData sensorPd = serialportdatalogService.findSensor(pd);
			if(sensorPd != null) {
				String sensorType = sensorPd.getString("EXTEND_1");
				SerialPortData serialPortData = new SerialPortData();
				serialPortData.setDataType("FAULT_ACTION");
				serialPortData.setData(new FaultActionData(sensorType, action));
				WebSocket.sendToAll(serialPortData.toString());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
 
    /**
     * 发送信息到串口
     * @param: data      
     * @return: void      
     * @throws
     */
    public void sendComm(String data) {
    	data = data + "\r\n";
        byte[] writerBuffer = null;
        try {
            writerBuffer = data.getBytes();
        } catch (NumberFormatException e) {
        	System.out.println(paramConfig.getSerialNumber()+ "sendComm NumberFormatException error:" + e.getMessage()); 
        }
        try {
            outputStream = serialPort.getOutputStream();
            outputStream.write(writerBuffer);
            outputStream.flush();
            System.out.println(paramConfig.getSerialNumber()+ "发送数据:" + data);
            
         // 保存数据日志
            PageData pd = new PageData();
    		pd.put("SERIALPORTDATALOG_ID", UuidUtil.get32UUID());	//主键
    		pd.put("SERIALPORT_ID", serialPortType.getValue());	//串口ID
    		pd.put("DATACONTENT", data);	//字符串数据
    		pd.put("DATACONTENT_HEX", bytesToHexString(writerBuffer));	//16进制数据
    		pd.put("TIME", DateUtil.getTime());	//时间
    		pd.put("DATATYPE", "1");	//收发类型
    		pd.put("EXTEND_1", "");	//扩展字段1
    		pd.put("EXTEND_2", "");	//扩展字段2
    		pd.put("EXTEND_3", "");	//扩展字段3
    		try {
				serialportdatalogService.save(pd);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } catch (NullPointerException e) {
        	System.out.println(paramConfig.getSerialNumber()+ "sendComm NullPointerException error:" + e.getMessage());
        } catch (IOException e) {
        	System.out.println(paramConfig.getSerialNumber()+ "sendComm IOException error:" + e.getMessage());
        }
    }
 
    /**
     * 关闭串口
     * @Description: 关闭串口
     * @param:       
     * @return: void      
     * @throws
     */
    public void closeSerialPort() {
        if (serialPort != null) {
            serialPort.notifyOnDataAvailable(false);
            serialPort.removeEventListener();
            if (inputStream != null) {
                try {
                    inputStream.close();
                    inputStream = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                    outputStream = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            serialPort.close();
            serialPort = null;
        }
        
        if(timer != null) {
        	timer.cancel();
        	timer = null;
        }
        
        if(!isInterrupted()) {
    		interrupt();
    	}
    }
 
    /**
     * 十六进制串口返回值获取
     */
    public String getDataHex() {
        String result = dataHex;
        // 置空执行结果
        dataHex = null;
        // 返回执行结果
        return result;
    }
 
    /**
     * 串口返回值获取
     */
    public String getData() {
        String result = dataStr;
        // 置空执行结果
        dataStr = null;
        // 返回执行结果
        return result;
    }
 
    /**
     * Hex字符串转byte
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte
     */
    public static byte hexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }
 
    /**
     * hex字符串转byte数组
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte数组结果
     */
    public static byte[] hexToByteArray(String inHex) {
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1) {
            // 奇数
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {
            // 偶数
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = hexToByte(inHex.substring(i, i + 2));
            j++;
        }
        return result;
    }
 
    /**
     * 数组转换成十六进制字符串
     * @param byte[]
     * @return HexString
     */
    public static final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

}
