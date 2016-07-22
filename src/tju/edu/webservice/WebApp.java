package tju.edu.webservice;
import javax.jws.*;
import javax.xml.ws.Endpoint;


@WebService(name="WebApp",targetNamespace="http://ywx/")
public class WebApp {
	public String sayhi(String name){
		System.out.println("server go...");
		return "yourname:"+name;
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Endpoint.publish("http://localhost:1234/hi", new WebApp());
		System.out.println("server ready...");

	}

}
