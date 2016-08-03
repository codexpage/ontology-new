package com.ics.controller;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.*;
import javax.sound.sampled.*;

import com.ics.main.IHelper;

/*
 * telnet localhost 8899
 * data
 * */

/*
 * volume ranges from 0-100, minimum value permitted is -80.0, 
 * maximum value permitted is 6.0206, mapping value from [0-100] to [minimum, maximum]
 * */

public class MusicPlayerController implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
        Clip clip;
		try {
			clip = AudioSystem.getClip();
	        AudioInputStream ais = AudioSystem.getAudioInputStream(new File(IHelper.MUSICFILE2));
	        clip.open(ais);
	        clip.loop(Clip.LOOP_CONTINUOUSLY);
            final FloatControl control = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue(-80.0f);
            int port = 8899;
            try {
				ServerSocket server = new ServerSocket(port);
				while(true){
					char buf[] = new char[16];
					Socket socket = server.accept();
					Reader reader = new InputStreamReader(socket.getInputStream());
					reader.read(buf);
					String s = new String(buf);
					Float value = new Float(s);
					float volume = 0;
					if(value < 0)
						value = 0f;
					else if(value > 100)
						value = 100f;
					volume = (float) (0.86*value - 80f);
					control.setValue(volume);
					reader.close();
					socket.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	
 /*   public static void main(String[] args) throws Exception {
        final Clip clip = AudioSystem.getClip();
        AudioInputStream ais = AudioSystem.getAudioInputStream(new File(IHelper.MUSICFILE2));
        clip.open(ais);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        Runnable r = new Runnable() {
            @Override
            public void run() {
               final FloatControl control = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
               int port = 8899;
               try {
				ServerSocket server = new ServerSocket(port);
				while(true){
					char buf[] = new char[16];
					Socket socket = server.accept();
					Reader reader = new InputStreamReader(socket.getInputStream());
					reader.read(buf);
					String s = new String(buf);
					Float value = new Float(s);

					float volume = 0;
					if(value < 0)
						value = 0f;
					else if(value > 100)
						value = 100f;
					volume = (float) (0.86*value - 80f);
					control.setValue(volume);
					reader.close();
					socket.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            }
        };
        SwingUtilities.invokeLater(r);
        }*/

}