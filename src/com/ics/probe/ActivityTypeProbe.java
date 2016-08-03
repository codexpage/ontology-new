package com.ics.probe;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class ActivityTypeProbe implements Runnable{

	private static String[] activities = {"Reading", "Watching", "Listening", "Sleeping", "Talkng", "Singing"};
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String host = "192.168.1.1";
		int port = 8000;
		try {
			Random random = new Random();
			while(true){
				Socket sender = new Socket(host, port);
				Writer writer = new OutputStreamWriter(sender.getOutputStream());
				int index = (int) (random.nextDouble() * activities.length);
				writer.write(activities[index]);
				writer.flush();
				writer.close();
				sender.close();
				Thread.sleep(10000);
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
