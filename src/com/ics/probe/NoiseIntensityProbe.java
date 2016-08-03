package com.ics.probe;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class NoiseIntensityProbe implements Runnable{

	/*range of noise intensity is [0, 100)*/
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
				int lightIntensity = (int) (random.nextDouble() * 100);
				writer.write(lightIntensity);
				writer.flush();
				writer.close();
				sender.close();
				Thread.sleep(5000);
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
