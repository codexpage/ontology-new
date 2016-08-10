package dataCollector;

import message.AppMessage;

import java.math.BigDecimal;

import file.FileOperator;
import net.tinyos.message.Message;
import net.tinyos.message.MessageListener;
import net.tinyos.message.MoteIF;
import net.tinyos.packet.BuildSource;
import net.tinyos.util.PrintStreamMessenger;
import tju.edu.model.temperaturesensor;
import tju.edu.model.temperaturesensorDAO;

/**
 * 
 * @author
 */
public class DataCollector implements MessageListener {

	private MoteIF moteIF;
//	private final static String co2FilePathandName = "./data/co2.txt";
//	private final static String tempFilePathandName = "./data/temp.txt";
//	private final static String humiFilePathandName = "./data/humi.txt";
//	private final static String lightFilePathandName = "./data/light.txt";
//	private final static String infraFilePathandName = "./data/infra.txt";

	public DataCollector(String source) {
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
		/*
		FileOperator fileOperator = new FileOperator();
		if (fileOperator.isFileExisted(co2FilePathandName) == false) {
			fileOperator.cretaeFile(co2FilePathandName, "");
		}
		if (fileOperator.isFileExisted(tempFilePathandName) == false) {
			fileOperator.cretaeFile(tempFilePathandName, "");
		}
		if (fileOperator.isFileExisted(humiFilePathandName) == false) {
			fileOperator.cretaeFile(humiFilePathandName, "");
		}
		if (fileOperator.isFileExisted(lightFilePathandName) == false) {
			fileOperator.cretaeFile(lightFilePathandName, "");
		}
		if (fileOperator.isFileExisted(infraFilePathandName) == false) {
			fileOperator.cretaeFile(infraFilePathandName, "");
		}*/
		
		DataCollector reader = new DataCollector("serial@/dev/ttyUSB0:telosb");

		Message msg = new AppMessage();
		reader.addMessageType(msg);
		reader.start();

	}

	public void messageReceived(int i, Message msg) {
		double co2, temp, humi, light , infra1, infra2;
		if (msg instanceof AppMessage) {
			AppMessage appMsg = (AppMessage) msg;

			if (appMsg.get_dataType() == 0) { // CO2
			
				// v0 = val / 4096 * 3.0
				// ppm = (v0 - 0.8)/3.2 * 1600 + 400
				co2 = (appMsg.get_data1() / 4096 * 3.0 - 0.8) / 3.2 * 1600 + 400;
				
//				FileOperator.writeFile(co2FilePathandName, System.currentTimeMillis()
//						+ "	" + appMsg.get_data1() + System.getProperty("line.separator"));
			} else if (appMsg.get_dataType() == 1) { // temperature
				// temp = -39.60 + 0.01*val, powered by Batteries
				// temp = -40.10 + 0.01*val, powered by USB
				double tmp = appMsg.get_data1()*0.01-39.6;
				
				int id = appMsg.get_nodeId();
				//写入
				temperaturesensor tempsensor=new temperaturesensor();
				temperaturesensorDAO tempDAO=new temperaturesensorDAO();
				tempsensor.setSensorid(id);//No.x sensor
				BigDecimal   b   =   new   BigDecimal(tmp);  
				tmp  =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();  
				tempsensor.setTemperature(tmp);
				System.out.println("temp: "+tmp);
				tempDAO.insert(tempsensor);//write into database
				
//				FileOperator.writeFile(tempFilePathandName, System.currentTimeMillis()
//						+ "	" + appMsg.get_data1() + System.getProperty("line.separator"));
			} else if (appMsg.get_dataType() == 2) { // humidity
				// Humi = -4 + 0.0405 * val + (-2.8 * 10^-6) * (val^2)
				// Humi = (Temp - 25) * (0.01 + 0.00008 * valh) + Humi;
//				FileOperator.writeFile(humiFilePathandName, System.currentTimeMillis()
//						+ "	" + appMsg.get_data1() + System.getProperty("line.separator"));
			} else if (appMsg.get_dataType() == 3) { // light
				// V0 = value / 4096 * 3.0
				// I = V0 / 100,000
				// lux = 0.625 * 1e6 * I * 1000, sensing device: S1087
				// lux = 0.769 * 1e5 * I * 1000, sensing device: S1087-01
				double lux = appMsg.get_data1()*3.0*769/4096.0;
				System.out.println("light "+lux);
				
//				FileOperator.writeFile(lightFilePathandName, System.currentTimeMillis()
//						+ "	" + appMsg.get_data1() + System.getProperty("line.separator"));
			} else if (appMsg.get_dataType() == 4) { // infra
//				FileOperator.writeFile(infraFilePathandName, System.currentTimeMillis()
//						+ "	" + appMsg.get_data1() + System.getProperty("line.separator"));
			}

//			System.out.println(System.currentTimeMillis() + "	" + appMsg.get_dataType() + "    " + appMsg.get_count()
//					+ System.getProperty("line.separator"));
			System.out.println("data1:"+appMsg.get_data1() + "	data2:" + appMsg.get_data2() + "    type:" + appMsg.get_dataType()+"    count:"+appMsg.get_count()
			+ System.getProperty("line.separator"));
			
			
		}
	}
}
