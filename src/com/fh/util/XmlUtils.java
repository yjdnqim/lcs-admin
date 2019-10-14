package com.fh.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 解析xml
 * 
 * @author wanliyunbai
 * @time 2017年6月2日
 * @email wanliyunbai@163.com
 */
public class XmlUtils {
	public static void main(String[] args) throws Exception {
		parseXml();
	}
	public static void parseXml() throws Exception {
		InputStream is = new FileInputStream("C:/test.xml");
        Enumeration<InputStream> streams = Collections.enumeration(
           Arrays.asList(new InputStream[] {
               new ByteArrayInputStream("<root>".getBytes()),is,
               new ByteArrayInputStream("</root>".getBytes()),
           }));
        SequenceInputStream seqStream = new SequenceInputStream(streams);
        //使用xml解析器进行解析           
		SAXReader reader = new SAXReader();
		Document document = reader.read(seqStream);
//		Element templateRule = (Element)document.selectSingleNode("/root/group/rule[@id='40501']");
		Element selectSingleNode = (Element) document.selectSingleNode("/root/group[@name='syslog,recon,']");
		System.out.println(selectSingleNode.attributeValue("name"));
	}
	
	public static void writeFile() throws Exception {
		File file = new File("E:/myself_workspace/yundun/yundun/WebRoot/ossec_rule/template/attack_rules.xml");
		FileWriter fw = new FileWriter(file,false);
		fw.write("test");
		fw.close();
	}
}
