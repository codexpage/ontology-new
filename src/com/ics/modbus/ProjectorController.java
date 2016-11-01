package com.ics.modbus;

import com.ics.factory.ModbusMasterFactory;
import com.serotonin.modbus4j.ModbusMaster;

public class ProjectorController {
	private final static ModbusUtil modbusUtil=new ModbusUtil();
	private final static ModbusMaster master=ModbusMasterFactory.createTcpMaster("192.168.1.2", 502);
//	private final static ModbusMaster master2=ModbusMasterFactory.createTcpMaster("192.168.1.202", 502);
	
	//已经修改为小电视的开关，经测试没有反应
	public synchronized static void turnOn() throws InterruptedException
	{
		modbusUtil.writeCoil(master, 1, 67, true);
		Thread.sleep(100);
	//	modbusUtil.writeCoil(master2, 1, 65, true);
		
	//	Thread.sleep(100);

	}
	public synchronized static void turnOff() throws InterruptedException
	{
		modbusUtil.writeCoil(master, 1, 67, false);
		Thread.sleep(100);
	//	modbusUtil.writeCoil(master2, 1, 65, false);
	//	Thread.sleep(100);

	}
	public static void main(String args[]) throws InterruptedException{
		turnOff();
	}
}
