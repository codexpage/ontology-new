package com.ics.lightControl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.ics.modbus.LightController;


public class LightMessageHandler implements Handler,Observable,Runnable{
	
	private final static int MAX_SIZE_ID=20;
	private final static int MAX_SIZE_NUM=10;

	private final static int LIGHT_MAX=100;
	private final static int LIGHT_MIN=50;
	
	private List<Observer> observers;
	private Map<Integer,Integer> numTable;
	private Map<Integer,Integer> sumTable;
	private Map<Integer,List<LightControlEntry>> controlTable;
	private LightSensorStatus lightStatus;
	
	private int id;
	private int message;
	public LightMessageHandler()
	{
		observers=new ArrayList<Observer>();
		numTable=new HashMap<Integer,Integer>();
		sumTable=new HashMap<Integer,Integer>();
		controlTable=new HashMap<Integer,List<LightControlEntry>>();
		lightStatus=new LightSensorStatus();
		this.addObserver(lightStatus);
	}
	
	public void handle(int id,int message)
	{


		if(!numTable.containsKey(id))
		{
			numTable.put(id, 1);
			sumTable.put(id, message);
			return;
		}
		
		if(numTable.get(id)>=MAX_SIZE_NUM)
		{
		
			int temp=(sumTable.get(id)+message)/(numTable.get(id)+1);
			numTable.put(id, 0);
			sumTable.put(id, 0);
			
			this.id=id;
			this.message=temp;
			
			this.run();
			
		}
		else
		{
			numTable.put(id, numTable.get(id)+1);
			sumTable.put(id, sumTable.get(id)+message);
		}
		
	}
	
	public void controlLigthByValue(int id,int value)
	{
		
		List<LightControlEntry> list=null;
		if(!controlTable.containsKey(id))
		{
			list=LightControlMapParser.parese(id);
			controlTable.put(id, list);
		}
		else
		{
			list=controlTable.get(id);
		}
		System.out.println(id+"="+list);
		if(value<LIGHT_MIN)
		{
			if(lightStatus.getStatusById(id)==false)
			{
				for(LightControlEntry e:list)
				{
					LightController.turnOn(e.getRow(), e.getStart(), e.getNum());
				}
				this.update(new LightSensorStatusEntry(id,true));
			}
			
		}
		
		else if(value>LIGHT_MAX)
		{
			if(lightStatus.getStatusById(id)==true)
			{
				for(LightControlEntry e:list)
				{
					LightController.turnOff(e.getRow(), e.getStart(), e.getNum());
				}
				this.update(new LightSensorStatusEntry(id,false));
			}
		}
		
	}

	@Override
	public void addObserver(Observer o) {

		observers.add(o);
	}

	@Override
	public void update(Object o) {

		if(o instanceof LightSensorStatusEntry)
		{
			for(Observer ob:observers)
			{
				ob.update(o);
			}
		}
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.controlLigthByValue(id, message);
	}



}
