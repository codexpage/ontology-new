package com.ics.modbus;

import java.util.HashMap;
import java.util.Map;

import com.ics.factory.ModbusMasterFactory;
import com.serotonin.modbus4j.ModbusMaster;

public class Socketcontroller {

	private final static ModbusUtil modbusUtil=new ModbusUtil();
	private final static ModbusMaster master=ModbusMasterFactory.createTcpMaster("192.168.1.200", 502);
//	private final static ModbusMaster master2=ModbusMasterFactory.createTcpMaster("192.168.1.2", 502);
	private static Map<Integer,Integer> map = new HashMap<Integer,Integer>();
	static{
		//主要控制2，4，7对于的柱子上的三个插座
		//注意这里的地址=电表上对应的地址-1  (3,69(70-1))
		map.put(3, 69);//#2 bottom 
//		map.put(3, 70);
		map.put(2, 71);//#4 middle
		
//		map.put(5, 64);
//		map.put(6, 65);//on the floor
		map.put(1, 66);//#7 top
		map.put(4, 67);//#8 也是集群下面那个插座
//		map.put(4, 71 );//#12 单片机集群下面那个插座 注意ip不同
		
	}
	public synchronized static void turnOn(int id) throws InterruptedException
	{
		modbusUtil.writeCoil(master, 1, map.get(id), true);
		Thread.sleep(100);
	//	modbusUtil.writeCoil(master2, 1, 65, true);
		
	//	Thread.sleep(100);

	}
	public synchronized static void turnOff(int id) throws InterruptedException
	{
		modbusUtil.writeCoil(master, 1, map.get(id), false);
		Thread.sleep(100);
	//	modbusUtil.writeCoil(master2, 1, 65, false);
	//	Thread.sleep(100);

	}
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		turnOff(8);

	}

}
