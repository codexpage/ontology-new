package com.ics.modbus;

import com.ics.factory.ModbusMasterFactory;
import com.serotonin.modbus4j.ModbusMaster;

public class PosterController {
	private final static ModbusUtil modbusUtil=new ModbusUtil();
	private final static ModbusMaster master2=ModbusMasterFactory.createTcpMaster("192.168.1.202", 502);
	
	public synchronized static void turnOn() throws InterruptedException
	{
		modbusUtil.writeCoil(master2, 1, 65, true);
		
		Thread.sleep(100);

	}
	public synchronized static void turnOff() throws InterruptedException
	{
		modbusUtil.writeCoil(master2, 1, 65, false);
		Thread.sleep(100);

	}
}
