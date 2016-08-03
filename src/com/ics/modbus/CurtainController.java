package com.ics.modbus;

import java.util.HashMap;
import java.util.Map;

import com.ics.factory.ModbusMasterFactory;
import com.serotonin.modbus4j.ModbusMaster;
/*
enum Curtain{

	Cutain1,Cutain2,Cutain3,Cutain4
}
*/
public class CurtainController {

	private final static ModbusUtil modbusUtil=new ModbusUtil();
	private final static ModbusMaster master=ModbusMasterFactory.createTcpMaster("192.168.1.2", 502);
	private static Map<Curtain,Integer> controlTable=new HashMap<Curtain,Integer>();
	static{
		controlTable.put(Curtain.Cutain1, 74);
		controlTable.put(Curtain.Cutain2, 76);
		controlTable.put(Curtain.Cutain3, 78);
		controlTable.put(Curtain.Cutain4, 80);
	}
	public synchronized static void up(Curtain ids[])
	{
		
		for(Curtain id:ids)
		{
	//		modbusUtil.re
			modbusUtil.writeCoils(master, 1, controlTable.get(id), new boolean[]{true,false});
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public synchronized static void down(Curtain ids[])
	{
		for(Curtain id:ids)
		{
			modbusUtil.writeCoils(master, 1, controlTable.get(id), new boolean[]{false,true});
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public synchronized static void stop(Curtain ids[])
	{
		for(Curtain id:ids)
		{
			modbusUtil.writeCoils(master, 1, controlTable.get(id), new boolean[]{false,false});
		}
	}
	public static void main(String args[]) 
	{
		up(new Curtain[]{Curtain.Cutain1});
	}
}
