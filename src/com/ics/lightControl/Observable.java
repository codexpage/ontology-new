package com.ics.lightControl;

public interface Observable {
	
	public void addObserver(Observer o);
	public void update(Object o);

}
