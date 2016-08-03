package com.ics.modbus;

import com.ics.factory.ModbusMasterFactory;
import com.serotonin.modbus4j.ModbusMaster;

public class AirConditionController {

	private final static ModbusUtil modbusUtil=new ModbusUtil();
	private final static ModbusMaster master=ModbusMasterFactory.createTcpMaster("192.168.1.121", 502);
	
	public synchronized static void turnOn()
	{
		modbusUtil.writeCoil(master, 1, 0, true);
		try {
			Thread.sleep(1000);
			modbusUtil.writeCoil(master, 1, 0, false);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args)
	{
		turnOn();
	}
}
