package com.ics.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;

public class LightController implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
        int port = 8897;
        try {
			ServerSocket server = new ServerSocket(port);
			while(true){
				char buf[] = new char[16];
				Socket socket = server.accept();
				Reader reader = new InputStreamReader(socket.getInputStream());
				reader.read(buf);
				String s = new String(buf);
				Float value = new Float(s);
				if(value >= 0){
					for (int j = 0; j < 8; j++) {
						com.ics.modbus.LightController.turnOn(j, 1, 8);
						Thread.sleep(100);
					}
				}
				else{
					for (int j = 0; j < 8; j++) {
						com.ics.modbus.LightController.turnOff(j, 1, 8);
						Thread.sleep(100);
					}
				}
				reader.close();
				socket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}
