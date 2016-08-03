package tju.edu.modbus;

import java.net.*;
import java.io.*;
import net.wimpi.modbus.*;
import net.wimpi.modbus.msg.*;
import net.wimpi.modbus.io.*;
import net.wimpi.modbus.net.*;
import net.wimpi.modbus.util.*;

public class ModebusUtil {

	public static void main(String[] args) {

		try {
			/* The important instances of the classes mentioned before */
			ModbusTCPTransaction trans = null; // the transaction
			ReadInputDiscretesRequest req = null; // the request
			ReadInputDiscretesResponse res = null; // the response
			TCPMasterConnection con = null; // the connection
			
			InetAddress addr = null;
			/* Variables for storing the parameters */
			addr = InetAddress.getByName("172.28.16.209");
			int port = 1502;
			
			con = new TCPMasterConnection(addr);
			con.setPort(port);
			con.connect();
			System.out.println("connected.");
			
			req = new ReadInputDiscretesRequest(4, 3);// 0号寄存器，长度3bit
			// 此处1表示之前在Modbus Slave中选中的Function:Input Status(1x)
			req.setUnitID(0);
			trans = new ModbusTCPTransaction(con);
//			trans.setRetries(5);
			trans.setRequest(req);
			int repeat = 1; // a loop for repeating the transaction
			int k = 0;
			do {
				trans.execute();				
				res = (ReadInputDiscretesResponse) trans.getResponse();
				System.out.println("Digital Inputs Status=" + res.getDiscretes().toString());
				k++;
			} while (k < repeat);
			
			con.close();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// int port = Modbus.DEFAULT_PORT;
		// int ref = 0; //the reference; offset where to start reading from
		// int count = 0; //the number of DI's to read


	}

}
