package com.ics.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;

public class MainBulbController implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
        int port = 8894;
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
					com.ics.modbus.MainBulbController.turnOnAll();
				}
				else{
					com.ics.modbus.MainBulbController.turnOffAll();
				}
				reader.close();
				socket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}
