package com.ics.modbus;

import java.util.HashMap;
import java.util.Map;

import com.ics.factory.ModbusMasterFactory;
import com.serotonin.modbus4j.ModbusMaster;

enum Door{
	Door1,Door2 //Door1 in room 81001, Door2 in room 81002
}

public class DoorController {

	private final static ModbusUtil modbusUtil=new ModbusUtil();
	private final static ModbusMaster master=ModbusMasterFactory.createTcpMaster("192.168.1.202", 502);
	private static Map<Door,Integer> controlTable=new HashMap<Door,Integer>();
	static{
		controlTable.put(Door.Door1, 72);
		controlTable.put(Door.Door2, 73);
	}
	
	public synchronized static void unlock(Door door)
	{
		modbusUtil.writeCoil(master, 1, controlTable.get(door) , false);
	}
	
	public synchronized static void lock(Door door)
	{
		modbusUtil.writeCoil(master, 1, controlTable.get(door) , true);
	}
	
	public static void main(String args[]) 
	{
		unlock(Door.Door1);
//		lock(Door.Door1);
	}
	
}
