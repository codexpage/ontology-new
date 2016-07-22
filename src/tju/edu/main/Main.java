package tju.edu.main;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import tju.edu.model.*;
public class Main {
	
//四个线程同时往数据库中写入数据
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				write(1);
			}
		}).start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				write(2);
			}
		}).start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				write(3);
			}
		}).start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				write(4);
			}
		}).start();
	}

	//线程函数
	private static void write(int id) {
		int t=100;//最多模拟100轮数据
		
		temperaturesensor temp=new temperaturesensor();
		temperaturesensorDAO tempDAO=new temperaturesensorDAO();
		temp.setSensorid(id);//No.1 sensor
		
		humiditysensor hum =new humiditysensor();
		humiditysensorDAO humDAO = new humiditysensorDAO();
		hum.setSensorid(id);
		
		while(t-->=0){
			
			//温度计写入
			int tmpi=(int)(1+Math.random()*(20-1+1))+15;//温度[21,31)的整数
			temp.setTemperature(tmpi);
			tempDAO.insert(temp);
//			System.out.println(i);
			
			//湿度计写入
			double humi = (double)(1+Math.random()*(30))+40; //湿度[41,71)的double
			BigDecimal b = new BigDecimal(humi);  
			humi = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();//保留两位小数			
			hum.setHumidity(humi);
			humDAO.insert(hum);
			
			
			
			//wait for 30s
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
