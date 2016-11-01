package com.ics.modbus;

import com.ics.factory.ModbusMasterFactory;
import com.serotonin.modbus4j.ModbusMaster;


public class LightController {
	
	private final static ModbusUtil modbusUtil=new ModbusUtil();
	private final static ModbusMaster master=ModbusMasterFactory.createTcpMaster("192.168.1.200", 502);
	
	public synchronized static void turnOn(int row,int start,int num)
	{
		int s=row*8;
		boolean temp[]=modbusUtil.readCoil(master, 1, s, 8);
		
		if(num<=8)
		for(int i=0;i<num;i++)
		{
			temp[start+i-1]=true;
		}  //http://start.ubuntu.com/11.04/Google/?sourceid=hp
		modbusUtil.writeCoils(master, 1, s,temp);
	}
	
	public synchronized static void turnOff(int row,int start,int num)
	{
		int s=row*8;
		boolean temp[]=modbusUtil.readCoil(master, 1, s, 8);

		if(num<=8)
		for(int i=0;i<num;i++)
		{
			temp[start+i-1]=false;
		}
		modbusUtil.writeCoils(master, 1, s,temp );
	}
	public static void main(String args[]){
		turnOff(0,2,5);
	}

}
