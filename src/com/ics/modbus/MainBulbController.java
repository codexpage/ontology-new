package com.ics.modbus;

import java.util.HashMap;
import java.util.Map;

import com.ics.factory.ModbusMasterFactory;
import com.serotonin.modbus4j.ModbusMaster;

public class MainBulbController {

	private final static ModbusUtil modbusUtil=new ModbusUtil();
	private final static ModbusMaster master=ModbusMasterFactory.createTcpMaster("192.168.1.202", 502);
	private static Map<Integer,Integer> controlTable=new HashMap<Integer,Integer>();
	static{
		controlTable.put(1, 70);
		controlTable.put(2, 69);
		controlTable.put(3, 64);
		controlTable.put(4, 71);
		controlTable.put(201,70);
	}
	
	public synchronized static void turnOn(Integer bulb)
	{
		System.out.println("handling......"+bulb);
		modbusUtil.writeCoil(master, 1, controlTable.get(bulb) , true);
	}
	
	public synchronized static void turnOff(Integer bulb)
	{
		modbusUtil.writeCoil(master, 1, controlTable.get(bulb) , false);
	}
	
	public synchronized static void turnOnAll(){
		turnOn(1);
		try {
			Thread.sleep(100);
			turnOn(2);
			Thread.sleep(100);
			turnOn(3);
			Thread.sleep(100);
			turnOn(4);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public synchronized static void turnOffAll(){
		turnOff(1);
		try {
			Thread.sleep(100);
			turnOff(2);
			Thread.sleep(100);
			turnOff(3);
			Thread.sleep(100);
			turnOff(4);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String args[])
	{
//		turnOnAll();
		turnOffAll();
//		turnOn(4);
	}
}
