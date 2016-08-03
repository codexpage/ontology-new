package com.ics.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;

import com.ics.bulbControl.Controller;
import com.ics.bulbControl.HueProperties;
import com.philips.lighting.hue.sdk.PHHueSDK;

public class BulbController implements Runnable{

	private Controller controller;
	@Override
	public void run() {
		// TODO Auto-generated method stub
        PHHueSDK phHueSDK = PHHueSDK.create();

        HueProperties.storeLastIPAddress("192.168.1.107");//set the zigbee's ip address here
        HueProperties.storeUsername("2259fe13e3d936f9ddfd0b137a56e7");//set a legal user name here
        HueProperties.loadProperties();  // Load in HueProperties, if first time use a properties file is created.

        controller = new Controller();
        controller.connectToLastKnownAccessPoint();
        
        phHueSDK.getNotificationManager().registerSDKListener(controller.getListener());
        
        int port = 8896;
        try {
			ServerSocket server = new ServerSocket(port);
			while(true){
				char buf[] = new char[32];
				Socket socket = server.accept();
				Reader reader = new InputStreamReader(socket.getInputStream());
				reader.read(buf);
				String s = new String(buf);
				s = s.substring(0, s.indexOf("\r\n"));
				String[] command = s.split(" ");
				if(command[0].equals("o")){
					if(command[1].equals("0"))
						controller.turnOnOrOffBulb(false);
					else
						controller.turnOnOrOffBulb(true);
				}
				
				else if(command[0].equals("c"))
					controller.changeColor(new Integer(command[1]), new Integer(command[2]), new Integer(command[3]));
				else if(command[0].equals("b"))
					controller.changeBrightness(new Integer(command[1]));
				reader.close();
				socket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
