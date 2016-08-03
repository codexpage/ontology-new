package com.ics.lightControl;

import java.util.HashMap;
import java.util.Map;

public class LightSensorStatus implements Observer{
	
	private static Map<Integer,Boolean> status=new HashMap<Integer,Boolean>();
	
	public void update(int id,boolean state)
	{
		synchronized(status)
		{
			status.put(id, state);
		}
	}
	
	public boolean getStatusById(int id)
	{
		synchronized(status)
		{
			if(status.containsKey(id))
				return status.get(id);
			else
			{
				status.put(id, false);
				return false;
			}
		}
	}

	@Override
	public void update(Object msg) {

		if(msg instanceof LightSensorStatusEntry)
		{
			LightSensorStatusEntry lsse=(LightSensorStatusEntry)msg;
			this.update(lsse.getId(), lsse.isState());
		}
	}
}
