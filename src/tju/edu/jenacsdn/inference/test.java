package tju.edu.jenacsdn.inference;

import java.awt.Component;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.reasoner.rulesys.ClauseEntry;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.util.FileManager;

public class test {
	
	public static void testExpert() {
		

//        List<Rule> rules=Rule.rulesFromURL("file:./expert/Expert.rules");  
//        System.out.println(rules.get(0));  
//        ClauseEntry[] body=rules.get(0).getBody();  
//        int j=rules.get(0).bodyLength();  
//          
//        System.out.println(j);  
//          
//        for (int i=0;i<j;i++){  
//            System.out.print(body[i]+",");  
//        }  
//        System.out.println("/n-------------------/n");  
//          
//        ClauseEntry[] head=rules.get(0).getHead();  
//        int k=rules.get(0).headLength();  
//        for (int i=0;i<k;i++){  
//            System.out.print(head[i]+",");  
//        }  
//        System.out.println("/n-------------------/n");  
//          
//        System.out.println(rules.get(0).getName());  
        
//		String ruleFile = "file:./expert/Expert.rules";
//		String ontoFile = "file:./expert/Expert.owl";
//		
//		IReasoner famRea = ReasonerFactory.createFamilyReasoner();
//		
//		Model m = FileManager.get().loadModel("file:./expert/Expert.owl");
//		String NS = "http://www.owl-ontologies.com/Expert.owl#";
//		Resource Jim = m.getResource(NS + "ZhaoHongJie");
//		Resource John = m.getResource(NS + "Computer_Applied_Technology");
//		System.out.println("Jim: "+Jim.toString());
//		
//		famRea.getInfModel(ruleFile, ontoFile);
//		famRea.printInferResult(Jim, John);

		String ontoFile = "file:./data/test.owl";
		String ruleFile = "file:./data/test.rules";
		
		IReasoner famRea = ReasonerFactory.createFamilyReasoner();
		
		Model m = FileManager.get().loadModel(ontoFile);		
		
		String NS = "http://www.semanticweb.org/ywx/ontologies/2016/6/tju#";
		
		Resource Jim = m.getResource(NS + "openwindowI");
		Resource John = m.getResource(NS + "newI");
//		System.out.println("Jimis "+ Jim.getLocalName());
//		System.out.println(John);
		
		
		famRea.getInfModel(ruleFile, ontoFile);
		famRea.printInferResult(Jim, John);
	}
	
	public static void testFamily() {
		String ruleFile = "file:./family/family.rules";
		String ontoFile = "file:./family/family.owl";
		
		IReasoner famRea = ReasonerFactory.createFamilyReasoner();
		
		Model m = FileManager.get().loadModel("file:./family/family.owl");
		String NS = "http://www.semanticweb.org/ontologies/2010/0/family.owl#";
		Resource Jim = m.getResource(NS + "Jim");
		Resource John = m.getResource(NS + "John");
		
		/*
		Resource Lucy = m.getResource(NS + "Lucy");
		Resource Kate = m.getResource(NS + "Kate");
		Resource Sam = m.getResource(NS + "Sam");
		Resource James = m.createResource(NS + "James");
		Resource Anna = m.getResource(NS + "Anna");
		Resource Holly = m.createResource(NS + "Holly");
		*/
		
		famRea.getInfModel(ruleFile, ontoFile);
		famRea.printInferResult(Jim, John);
	}
	
	public static void testQuery() {
		String ruleFile = "file:./expert/Expert.rules";
		String ontoFile = "file:./expert/Expert.owl";
		String queryString = "PREFIX Expert:<http://www.owl-ontologies.com/Expert.owl#> " +
	    	"SELECT ?expert ?subject " +
	    	"WHERE {?expert Expert:familiar_with ?subject} ";
		
		IReasoner famRea = ReasonerFactory.createFamilyReasoner();
		famRea.getInfModel(ruleFile, ontoFile);
		famRea.searchOnto(queryString);
	}

	public static void main(String[] args) {
//		testQuery();
		testExpert();
	}
	
}
