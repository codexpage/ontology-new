package com.ics.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;

public class AirConditionController implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
        int port = 8895;
        try {
			ServerSocket server = new ServerSocket(port);
			while(true){
				char buf[] = new char[16];
				Socket socket = server.accept();
				Reader reader = new InputStreamReader(socket.getInputStream());
				reader.read(buf);
				String s = new String(buf);
				if(!s.equals(""))
					com.ics.modbus.AirConditionController.turnOn();
				reader.close();
				socket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
