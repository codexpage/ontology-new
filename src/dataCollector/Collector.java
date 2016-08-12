package dataCollector;

import java.math.BigDecimal;

import message.AppMessage;
import net.tinyos.message.Message;
import net.tinyos.message.MessageListener;
import net.tinyos.message.MoteIF;
import net.tinyos.packet.BuildSource;
import net.tinyos.util.PrintStreamMessenger;
import tju.edu.model.sensor;
import tju.edu.model.sensorDAO;
import tju.edu.model.temperaturesensor;
import tju.edu.model.temperaturesensorDAO;

public class Collector implements MessageListener {
	private MoteIF moteIF;

	public Collector(String source) {
		if (source != null) {
			moteIF = new MoteIF(BuildSource.makePhoenix(source,
					PrintStreamMessenger.err));
		} else {
			moteIF = new MoteIF(
					BuildSource.makePhoenix(PrintStreamMessenger.err));
		}
	}

	public void start() {
	}

	private void addMessageType(Message msg) {
		moteIF.registerListener(msg, this);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Collector reader = new Collector("serial@/dev/ttyUSB0:telosb");

		Message msg = new AppMessage();
		reader.addMessageType(msg);
		reader.start();
	}
	
	public void insertdata(int id, double value, String name){
		sensor sen =new sensor();
		sensorDAO senDAO = new sensorDAO();
		sen.setSensorid(id);
		sen.setName(name);
		BigDecimal b = new BigDecimal(value); 
		value = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		sen.setValue(value);
//		System.out.println("value :"+value);
		senDAO.insert(sen);
		System.out.println(sen.getName()+" "+sen.getSensorid()+" "+sen.getValue());
	}
	
	public double temp = -310;
	
	public void messageReceived(int i, Message msg) {
		double co2, humi, light , infra1, infra2;
//		temp = -310;
		if (msg instanceof AppMessage) {
			AppMessage appMsg = (AppMessage) msg;

			if (appMsg.get_dataType() == 0) { // CO2
				co2 = (appMsg.get_data1() / 4096 * 3.0 - 0.8) / 3.2 * 1600 + 400;//ppm
				int id = appMsg.get_nodeId();
				insertdata(id,co2,"CO2");
			} else if (appMsg.get_dataType() == 1) { // temperature
				// temp = -39.60 + 0.01*val, powered by Batteries
				// temp = -40.10 + 0.01*val, powered by USB
				temp = appMsg.get_data1()*0.01-39.6;
				int id = appMsg.get_nodeId();
				//写入
				insertdata(id,temp,"temperature");
				
			} else if (appMsg.get_dataType() == 2) { // humidity
				// Humi = -4 + 0.0405 * val + (-2.8 * 10^-6) * (val^2)
				// Humi = (Temp - 25) * (0.01 + 0.00008 * valh) + Humi;
				if(temp < -300){//必须要有温度传感器为前提
					System.err.println("can't get effective temperature,can not calculate Humidity!");
				}
				else{
					double data = appMsg.get_data1();
					data = 1705;
					double value =  -4 + 0.0405 * data + (-2.8 * 1e-6) * (Math.pow(data, 2));
					value = (temp - 25)*(0.01 + 0.0008 * data)+value;
					int id = appMsg.get_nodeId();
					insertdata(id,value,"humidity");
				}
			} else if (appMsg.get_dataType() == 3) { // light
				// V0 = value / 4096 * 3.0
				// I = V0 / 100,000
				// lux = 0.625 * 1e6 * I * 1000, sensing device: S1087
				// lux = 0.769 * 1e5 * I * 1000, sensing device: S1087-01
				double lux = appMsg.get_data1()*3.0*769/4096.0;
				int id = appMsg.get_nodeId();
				insertdata(id, lux, "luminance");
			} else if (appMsg.get_dataType() == 4) { // infra
				double value = appMsg.get_data1();
				int id = appMsg.get_nodeId();
				insertdata(id,value,"infrared");
			}
			System.out.println("data1:"+appMsg.get_data1() + "	data2:" + appMsg.get_data2() + "    type:" + appMsg.get_dataType()+"    count:"+appMsg.get_count()
			+ System.getProperty("line.separator"));
			
			
		}
	}
}
