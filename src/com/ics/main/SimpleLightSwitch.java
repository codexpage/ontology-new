package com.ics.main;

import com.ics.modbus.Curtain;
import com.ics.modbus.CurtainController;
import com.ics.modbus.DoorController;
import com.ics.modbus.LightController;
import com.ics.modbus.MainBulbController;
import com.ics.modbus.PosterController;
import com.ics.modbus.TVController;

public class SimpleLightSwitch {

	public static void main(String args[]) throws InterruptedException {
		
		//TVController.turnOff();
		
//		PosterController.turnOff();
//		LightController.turnOff(0,1,8);
		turnOffOfAll();
		
		//MainBulbController.turnOffAll();
		
		//LightController.turnOff(0, 1, 2);
	/*	for (int j = 2; j < 3; j++) {
			LightController.turnOn(j, 2, 3);
		}*/
		//turnOffOfAll();
	//turnOnOfAll();

		/*try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//CurtainController.up(new Curtain[]{Curtain.Cutain1,Curtain.Cutain2});
		//CurtainController.stop(new Curtain[]{Curtain.Cutain4, Curtain.Cutain3,Curtain.Cutain1,Curtain.Cutain2});
		//CurtainController.up(new Curtain[]{Curtain.Cutain4, Curtain.Cutain3,Curtain.Cutain1,Curtain.Cutain2});
		//CurtainController.down(new Curtain[]{Curtain.Cutain4, Curtain.Cutain3,Curtain.Cutain1,Curtain.Cutain2});
	}

	public static void turnOnOfAll() {
		for (int j = 0; j < 8; j++) {
			LightController.turnOn(j, 1, 8);
		}
	}

	public static void turnOffOfAll() {
		for (int j = 0; j < 8; j++) {
			LightController.turnOff(j, 1, 8);
		}
	}

	
}
