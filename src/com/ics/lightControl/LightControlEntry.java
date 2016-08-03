package com.ics.lightControl;

public class LightControlEntry {
	
	private int row;
	private int start;
	private int num;
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
	public String toString()
	{
		return "row="+row+" start="+start+" num="+num;
	}

}
