package tju.edu.model;

public class sensor  {
	private int type;// 0 represents double, 1 for int, 2 for bool
	private String name;
	private int sensorid;
	private double value;

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSensorid() {
		return sensorid;
	}

	public void setSensorid(int sensorid) {
		this.sensorid = sensorid;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public static void main(String[] args) {
		
	}

}
