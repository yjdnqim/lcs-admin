package com.fh.util.terminal;

import javax.print.attribute.standard.RequestingUserName;

import org.apache.commons.lang.ArrayUtils;
import org.apache.poi.util.ArrayUtil;
import org.etsi.uri.x01903.v13.UnsignedPropertiesType;
import org.springframework.web.servlet.RequestToViewNameTranslator;
//
//import jdk.nashorn.internal.runtime.arrays.ContinuousArrayData;

public class CoffeeProtocal {
//
//	序号	符号		长度		表示意义
//    1		SOI		1		报头(0x7E)                      
//    2		CID		1		命令类型   
//    3     	LEN   	2		数据区长度         
//    4     	INFO 	n		数据区
//    5     	CHK  	2		校验和     
//    6     	EOI   	1		报尾(0x0D)
//   说明：
//   1 命令模式：
//上位机根据需要主动向设备发起查询和设置命令。设备成功接收后返回应答。
//设备可主动向上位机发送报警和按键操作信息.
//   2 数据格式: 
//   所有数据除SOI和EOI以十六进制方式传输外，数值型数据都转换成ASCII码传输，字符型不变。长度为未转换成ASCII码的长度，
//   以字节为单位。如0x3A要转换成两个字节0x33(‘3’),0x41(‘A’),先发送0x33再发送0x41．如果是两个字节的数值则转换成四个字节ASCII，先发送高字节，后发送低字节。
//   3 校验和计算: 
//   除SOI,EOI,CHKSUM外其他字节求和后所得结果模65536余数取反加1。

	private byte[] SOI;
	private byte[] CID;
	private int cid ;
	private byte[] LEN;
	private int len;
	private byte[] INFO;
	private byte[] CHK;
	private byte[] EOI;
	
	public CoffeeProtocal() {
		SOI = new byte[] { 0x7E };
		EOI = new byte[] { 0x0D };
	}

	//parse content to CoffeeProtocal
	public CoffeeProtocal(byte[] content) throws Exception {
		if (content[0] == 0x7E && content[content.length - 1] == 0x0D) {

			SOI = new byte[] { 0x7E };
			EOI = new byte[] { 0x0D };
			
			CID = new byte[] {content[1], content[2]};
			byte[] cidbyte = hexStringToBytes( new String( CID) );
			cid = cidbyte[0] ;
			
			LEN = new byte[] {content[3], content[4],content[5],content[6]};
			byte[] lenByte = hexStringToBytes(new String(LEN));
			len = lenByte[0] * 256 +lenByte[1];
			
			if (len >0) {
				INFO = new byte[len];
				ArrayUtil.arraycopy(content, 7, INFO, 0, len);
				
			}
			
			CHK = new byte[4];
			ArrayUtil.arraycopy(content, len +7, CHK, 0, 4);
			
			
		} else {
			throw  new Exception("content to insistant with protal");
		}
		
	}
	
//  3 校验和计算: 
//  除SOI,EOI,CHKSUM外其他字节求和后所得结果模65536余数取反加1。
	private byte[] calcCheck() {
		//开始组装成byte[]
		byte[] bytesall = new byte[2 + 4 + len];
		int index  = 0;
		ArrayUtil.arraycopy(CID, 0, bytesall, index, CID.length);
		index += CID.length;
		ArrayUtil.arraycopy(LEN, 0, bytesall, index, LEN.length);
		index += LEN.length;
		if (len > 0) {
			ArrayUtil.arraycopy(INFO, 0, bytesall, index, INFO.length);
			index += INFO.length;
		}
		
		long sum = 0;
		for(int i =0; i <bytesall.length ; i++) {
			sum += bytesall[i];
		}
		sum %= 65536;
		sum = ~sum +1;
		byte[] chk = bytesToHexString(
				new byte[] {(byte)(sum >>> 8), (byte)(sum & 0xff)}
				).getBytes() ;
		
		return chk;
	}
	
	//turn all package to byte[]
	public byte[] toByteArray() {
		if (INFO == null) {
			len = 0;
		}else {
			len = INFO.length * 2;
		}
		LEN = bytesToHexString(
				new byte[] {(byte)(len >>> 8), (byte)(len & 0xff)}
				).getBytes() ;
		
		//校验字段
		CHK = calcCheck();
		
		//开始组装
		byte[] bytesall = new byte[1 + 2 + 4 + len + 4 + 1];
		int index  = 0;
		ArrayUtil.arraycopy(SOI, 0, bytesall, index, SOI.length);
		index += SOI.length;
		ArrayUtil.arraycopy(CID, 0, bytesall, index, CID.length);
		index += CID.length;
		ArrayUtil.arraycopy(LEN, 0, bytesall, index, LEN.length);
		index += LEN.length;
		if (len > 0) {
			ArrayUtil.arraycopy(INFO, 0, bytesall, index, INFO.length);
			index += INFO.length;
		}
		ArrayUtil.arraycopy(CHK, 0, bytesall, index, CHK.length);
		index += CHK.length;
		ArrayUtil.arraycopy(EOI, 0, bytesall, index, EOI.length);
		
		return bytesall;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
		CID = bytesToHexString(
				new byte[] {(byte)cid}
				).getBytes() ;
	    
	}

	public byte[] getSOI() {
		return SOI;
	}


	public byte[] getCID() {
		return CID;
	}

	public void setCID(byte[] cID) {
		CID = cID;
	}

	public byte[] getLEN() {
		return LEN;
	}

	public byte[] getINFO() {
		return INFO;
	}

	public void setINFO(byte[] iNFO) {
		INFO = iNFO;
	}

	public void setINFO(String iNFO) {
		INFO = hexStringToBytes(iNFO);
	}
	public byte[] getCHK() {
		return CHK;
	}

	public byte[] getEOI() {
		return EOI;
	}

	   /**
  * byte转hexString
  * @param buffer 数据
  * @return
  */
 public static String bytesToHexString(final byte[] buffer){
	 int size = buffer.length;
     StringBuilder stringBuilder=new StringBuilder("");
     if (buffer==null||size<=0) return null;
     for (int i = 0; i <size ; i++) {
         String hex=Integer.toHexString(buffer[i]&0xff);
         if(hex.length()<2) stringBuilder.append(0);
         stringBuilder.append(hex);
     }
     return stringBuilder.toString();
 }

 /**
  * hexString转byte
  * @param hexString
  * @return
  */
 public static byte[] hexStringToBytes(String hexString){
     if (hexString==null||hexString.equals("")) return null;
     hexString=hexString.toUpperCase();
     int length=hexString.length()/2;
     char[] hexChars=hexString.toCharArray();
     byte[] d=new byte[length];
     for (int i = 0; i <length ; i++) {
         int pos=i*2;
         d[i]=(byte)(charToByte(hexChars[pos])<<4|charToByte(hexChars[pos+1]));
     }
     return d;
 }

 private static byte charToByte(char c) {
     return (byte) "0123456789ABCDEF".indexOf(c);
 }
	
 //虚拟投币器协议
 
}
