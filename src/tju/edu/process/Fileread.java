package tju.edu.process;

import java.io.FileReader;
import java.io.IOException;

public class Fileread {

	public static void main(String[] args) {
		try {
			
			FileReader fr = new FileReader("./data/sensors.txt"); 
			int ch = 0;
			while((ch = fr.read())!=-1 )   
			{
				System.out.print((char)ch);     
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
