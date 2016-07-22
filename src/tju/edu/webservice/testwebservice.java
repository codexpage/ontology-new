package tju.edu.webservice;
import ywx.*;

public class testwebservice {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WebAppService ws=new WebAppService();
		ywx.WebApp wa=ws.getWebAppPort();
		String str=wa.sayhi("jack");
		System.out.println(str);
	}
	
}
