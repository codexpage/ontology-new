package tju.edu.jena;

import java.util.Iterator;
import java.util.Scanner;

import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;
import org.apache.jena.ontology.*;

import tju.edu.*;
import tju.edu.model.*;
public class Readinfo2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OntModel ontModel=ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		ontModel.read("file:/Users/ywx/Downloads/building.owl");
		
		while(true){
			Scanner sc=new Scanner(System.in);
			System.out.println("input the sensor type:");
			String src=sc.next();
			System.out.println(src);
			
			if(src.equals("tempsensor")){
				System.out.println("input the sensor ID");
				int id=sc.nextInt();
				for (Iterator<OntClass> i = ontModel.listClasses(); i.hasNext(); ) { 
					OntClass c = i.next();
					if (!c.isAnon()) {  //测试c是否匿名  
						
//						 System.out.println(c.getModel().getGraph().getPrefixMapping().shortForm(c.getURI()));  
//						 System.out.println(c.getLocalName());
						 String name=c.getLocalName();
						 
						 if(name.equals("tempsensor")){
							 for (Iterator<? extends OntResource> j = c.listInstances(); j.hasNext(); ) {
								 OntResource d = j.next();
//								 System.out.println(d.listProperties());
								 
								 if(d.getLocalName().equals("therometer"+id)){
									 System.out.println(d.getLocalName());
									 for (Iterator<Statement> k = d.listProperties(); k.hasNext(); ) {
									 	Statement e=k.next();
									 
									 	if(e.getPredicate().getLocalName().equals("temperature")){
									 		System.out.println(e.getPredicate().getLocalName());
									 		System.out.println(e.getObject());
									 		String info[]=e.getObject().toString().split(",");
									 		String database=info[0];
									 		String relation=info[1];
									 		String ID=info[2];
									 		
									 		System.out.println("database:"+info[0]);
									 		System.out.println("relation:"+info[1]);
									 		System.out.println("ID:"+info[2]);
									 		
									 		//上面获得了数据在数据库中的位置 三个参数
									 		temperaturesensorDAO tempDAO=new temperaturesensorDAO();
									 	    
									 		int rs=tempDAO.findAll(database,relation,Integer.parseInt(ID));
									 		System.out.println("rs:"+rs);
									 	}
								 	}
								 }
							 }
						 }
					}
				}
			}
			else{
				System.out.println("sensor not found.");
			}
		}

	}

}
