package com.ics.lightControl;

import java.util.ArrayList;
import java.util.List;

import com.ics.file.FileOperator;


public class LightControlMapParser {
	
	
	private final static String filename="./data/lightControlMap.properties"; 
	private static String file=FileOperator.readFile(filename);
	public static List<LightControlEntry> parese(int id)
	{
		List<LightControlEntry> list=new ArrayList<LightControlEntry>();
		String finder=id+"=";
		int start=file.indexOf(finder);
		int end=file.indexOf(";",start);
		String temp=file.substring(start+finder.length(), end);
		
		finder=null;
		//System.out.println(temp.substring(1,temp.length()-1));
		String args[]=temp.substring(1, temp.length()-1).split("\\),\\(");
		for(String s:args)
		{
			//System.out.println(s);
			String elements[]=s.split(",");
			LightControlEntry entry=new LightControlEntry();
			entry.setRow(Integer.parseInt(elements[0]));
			entry.setStart(Integer.parseInt(elements[1]));
			entry.setNum(Integer.parseInt(elements[2]));
			list.add(entry);
			
		}
		return list;
		
		
	}
	
	public static void main(String args[])
	{
		LightControlMapParser.parese(1);
	}
}
