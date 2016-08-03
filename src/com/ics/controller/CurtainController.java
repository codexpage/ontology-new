package com.ics.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;

import com.ics.modbus.Curtain;

/*
 * value > 0 :up
 * value = 0 : stop
 * value < 0 : down
 * */
public class CurtainController implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
        int port = 8898;
        try {
			ServerSocket server = new ServerSocket(port);
			while(true){
				char buf[] = new char[16];
				Socket socket = server.accept();
				Reader reader = new InputStreamReader(socket.getInputStream());
				reader.read(buf);
				String s = new String(buf);
				Float value = new Float(s);
				if(value > 0)
					com.ics.modbus.CurtainController.up(new Curtain[]{Curtain.Cutain4, Curtain.Cutain3,Curtain.Cutain1,Curtain.Cutain2});
				else if(value < 0)
					com.ics.modbus.CurtainController.down(new Curtain[]{Curtain.Cutain4, Curtain.Cutain3,Curtain.Cutain1,Curtain.Cutain2});
				else
					com.ics.modbus.CurtainController.stop(new Curtain[]{Curtain.Cutain4, Curtain.Cutain3,Curtain.Cutain1,Curtain.Cutain2});
				reader.close();
				socket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
