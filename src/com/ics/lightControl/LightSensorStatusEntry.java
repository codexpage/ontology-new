package com.ics.lightControl;

public class LightSensorStatusEntry {
	private int id;
	private boolean state;
	
	
	public LightSensorStatusEntry(int id, boolean state) {
		super();
		this.id = id;
		this.state = state;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public boolean isState() {
		return state;
	}


	public void setState(boolean state) {
		this.state = state;
	}
	

}
