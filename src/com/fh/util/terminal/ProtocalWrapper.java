package com.fh.util.terminal;

public class ProtocalWrapper {

//    CID：01H
//    LEN：0
//    INFO：无
//    设备回复：
//    CID：81H
//    LEN：30
//INFO：无
//示例:  选择口味1
//发送:SOI,30H,31H,30H,30H,30H,30H,CHK1,CHK2,CHK3,CHK4,EOI
//回复:SOI,38H,31H,30H,30H,30H,30H,CHK1,CHK2,CHK3,CHK4,EOI
//CID:01H到0CH分别为咖啡口味1到口味12 
//30H,31H: CID(0x31)
//30H,30H,30H,30H : 数据区长度(0字节)
//CHK1,CHK2,CHK3,CHK4 为两字节校验和转换为ASCII码后的4个字符

	//咖啡选择
	public static byte[] genChoseCoffeeCommand(int coffeeType) {
		CoffeeProtocal coffeeProtocal = new CoffeeProtocal();
		coffeeProtocal.setCid(coffeeType);
		return coffeeProtocal.toByteArray();
	}
	
//	设置系统参数
//    CID：0DH
//    LEN：10
//INFO：加热温度上限(2)+加热温度下限(2)+制冷温度上限(2)+制冷温度下限(2)+设备工作方式(2)    (注:0xAA自动售卖;0xBB:人工售卖)
//    设备回复：
//    CID：87H
//    LEN：0
//    INFO：无
	//mode 1:自动  2：人工
	public static byte[] genSysConfigCommand(int heatUpper, int heatLower, int coldUpper, int coldLower, int mode) {
		byte[] info = new byte[5];
		info[0] = (byte) heatUpper;
		info[1] = (byte) heatLower;
		info[2] = (byte) coldUpper;
		info[3] = (byte) coldLower;
		if (mode == 1) {
			
			info[4] = (byte) 0xAA;
		}else if (mode  == 2) {
			info[4] = (byte) 0xBB;
			
		}
		CoffeeProtocal coffeeProtocal = new CoffeeProtocal();
		coffeeProtocal.setCid(0x0D);
		coffeeProtocal.setINFO(info);
		return coffeeProtocal.toByteArray();
	}
	
	
//	设置口味1配方(按键1配方)
//    CID：0EH
//    LEN：108(6CH)
//INFO：出水电磁阀编号(2),出水时间(2),是否与下一个同时启动(2), 出水电磁阀编号(2),出水时间(2),是否与下一个同时启动(2),
//      出水电磁阀编号(2),出水时间(2),是否与下一个同时启动(2), 出水电磁阀编号(2),出水时间(2),是否与下一个同时启动(2),
//      出水电磁阀编号(2),出水时间(2),是否与下一个同时启动(2), 出水电磁阀编号(2),出水时间(2),是否与下一个同时启动(2),
//      出粉电机编号(2), 出粉时间(2),是否与下一个同时启动(2), 出粉电机编号(2),出粉时间(2),是否与下一个同时启动(2),
//      出粉电机编号(2), 出粉时间(2),是否与下一个同时启动(2), 出粉电机编号(2),出粉时间(2),是否与下一个同时启动(2),
//      出粉电机编号(2), 出粉时间(2),是否与下一个同时启动(2), 出粉电机编号(2),出粉时间(2),是否与下一个同时启动(2),
//      搅拌电机编号(2), 搅拌时间(2),是否与下一个同时启动(2), 搅拌电机编号(2), 搅拌时间(2),是否与下一个同时启动(2),
//      搅拌电机编号(2), 搅拌时间(2),是否与下一个同时启动(2), 搅拌电机编号(2), 搅拌时间(2),是否与下一个同时启动(2),
//      搅拌电机编号(2), 搅拌时间(2),是否与下一个同时启动(2), 搅拌电机编号(2), 搅拌时间(2),是否与下一个同时启动(2),
//
//说明:电磁阀编号(1-12),出粉电机编号(1-6),搅拌电机编号(1-6),编号字段填0时,表示无执行组件
//     时间单位0.1秒,最大25.5秒
//     是否与下一个同时启动(2)字段,该字段为1时表示下一个组件与上一个同时启动,
//示例:设置口味1为1号出水电磁法出水时间15秒, 1号和3号出粉电机同时启动,4号出粉电机待1,3号结束后启动,1号出粉时间8秒,
//3号出粉时间10秒,4号出粉时间5秒,1号搅拌电机15秒
//   7EH,  30H,3EH,  30H,30H,36H,43H,
//  30H,31H,39H,36H,30H,30H,  30H,30H,30H,30H,30H,30H,  30H,30H,30H,30H,30H,30H,  30H,30H,30H,30H,30H,30H,  30H,30H,30H,30H,30H,30H,  
//  30H,30H,30H,30H,30H,30H,  
//   30H,31H,35H,30H,30H,31H,  30H,33H,36H,34H,30H,30H,  30H,34H,33H,32H,30H,30H,  30H,30H,30H,30H,30H,30H,  30H,30H,30H,30H,30H,30H,  
//   30H,30H,30H,30H,30H,30H,  
//  30H,31H,39H,36H,30H,30H,  30H,30H,30H,30H,30H,30H,  30H,30H,30H,30H,30H,30H,  30H,30H,30H,30H,30H,30H,  30H,30H,30H,30H,30H,30H,  
//  30H,30H,30H,30H,30H,30H,  
//  CHK1,CHK2,CHK3,CHK4,   0DH
//
//其它口味(按键(2-12))设置的CID分别为0FH到19H

	public static byte[] genFormulaCommand(int coffeeType, String commandString) {

		CoffeeProtocal coffeeProtocal = new CoffeeProtocal();
		coffeeProtocal.setCid(0x0E +coffeeType);
		coffeeProtocal.setINFO(commandString);
		return coffeeProtocal.toByteArray();
	}


	//虚拟投币器:钱数，脉冲宽度。暂时不支持大于256的值
	public static byte[] coinCommand(int price, int pulseWidth) {
		String hexStr = "23"+String.format ("%02X", 60)+String.format ("%02X", price)+"ff";
//		String hexStr = "23140800000000000000000000000000004C000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
		byte[] data = CoffeeProtocal.hexStringToBytes(hexStr);
		return data;
	}
}
