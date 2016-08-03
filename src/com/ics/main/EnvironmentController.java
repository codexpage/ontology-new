package com.ics.main;

import com.ics.controller.AirConditionController;
import com.ics.controller.BulbController;
import com.ics.controller.CurtainController;
import com.ics.controller.LightController;
import com.ics.controller.MainBulbController;
import com.ics.controller.MusicPlayerController;
/*
 * four bulbs controller port : 8894
 * command format: [parameter]
 * parameter>=0, turn on all bulbs; otherwise, turn off all bulbs
 * 
 * aircondition controller port : 8895
 * command format: [parameter]
 * parameter != '', change the state(on/off) of aircondition
 * 
 * bulb controller port : 8896
 * command format: [opt] [parameter] [parameter] ...
 * o [parameter] parameter==0 : turn off bulb; otherwise turn on bulb
 * b [parameter] parameter range:[0, 254], change bulb's brightness
 * c [r] [g] [b] parameter range:[0, 255], change bulb's color which is described in RGB color model
 *
 * light controller port : 8897
 * command format: [parameter]
 * parameter >= 0, turn on all lights; otherwise turn off all lights
 * 
 * curtain controller port : 8898
 * command format: [parameter]
 * parameter > 0, turn up all curtains; parameter == 0, stop all curtains; otherwise turn down all curatins
 * 
 * music player controller port : 8899
 * command format: [parameter]
 * parameter range: [0, 100], set the music player's volume to parameter
 * */

public class EnvironmentController implements Runnable{
	public static void main(String[] args){
		Runnable musicControllerPlayer = new MusicPlayerController();
		Runnable lightController = new LightController();
		Runnable curtainController = new CurtainController();
		Runnable bulbController = new BulbController();
		Runnable airConditionController = new AirConditionController();
		Runnable mainBulbController = new MainBulbController();
		
		Thread thread1 = new Thread(musicControllerPlayer);
		Thread thread2 = new Thread(lightController);
		Thread thread3 = new Thread(curtainController);
		Thread thread4 = new Thread(bulbController);
		Thread thread5 = new Thread(airConditionController);
		Thread thread6 = new Thread(mainBulbController);

		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		thread5.start();
		thread6.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Runnable musicControllerPlayer = new MusicPlayerController();
		Runnable lightController = new LightController();
		Runnable curtainController = new CurtainController();
		Runnable bulbController = new BulbController();
		Runnable airConditionController = new AirConditionController();
		Runnable mainBulbController = new MainBulbController();
		
		Thread thread1 = new Thread(musicControllerPlayer);
		Thread thread2 = new Thread(lightController);
		Thread thread3 = new Thread(curtainController);
		Thread thread4 = new Thread(bulbController);
		Thread thread5 = new Thread(airConditionController);
		Thread thread6 = new Thread(mainBulbController);

		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		thread5.start();
		thread6.start();
	}
}
