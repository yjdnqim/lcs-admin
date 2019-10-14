package com.fh.listener;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.poi.util.ArrayUtil;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fh.configuration.ConfigurationManager;
import com.fh.util.Const;
import com.fh.util.Logger;
import com.fh.util.PageData;
import com.fh.util.terminal.ByteUtil;
import com.fh.util.terminal.CoffeeProtocal;
/**
 * 
* 类名称：WebAppContextListener.java
* 类描述： 
* 作者单位： 
* 联系方式：
* @version 1.0
 */
public class WebAppContextListener implements ServletContextListener{
	
	public static WebAppContextListener mWebAppContextListener;
	
	private static Logger log = Logger.getLogger (WebAppContextListener.class);
	

	public void contextInitialized(ServletContextEvent event) {
		
	}
	
	public void contextDestroyed(ServletContextEvent event) {
		
	}

}
