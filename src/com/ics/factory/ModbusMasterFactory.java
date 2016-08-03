package com.ics.factory;

import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.ip.IpParameters;

public class ModbusMasterFactory {
	
	
	//private static ModbusMaster master=null;
	private static ModbusFactory modbusFactory = new ModbusFactory();
	public static ModbusMaster createTcpMaster(String host,int port)
	{
		IpParameters ipParameters = new IpParameters();
	    ipParameters.setHost(host);
	    ipParameters.setPort(port);
	    ModbusMaster master = modbusFactory.createTcpMaster(ipParameters, false);
	    try {
			master.init();
		} catch (ModbusInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return master;

	}
	
	
}
