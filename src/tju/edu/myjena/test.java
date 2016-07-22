package tju.edu.myjena;

import java.util.List;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.reasoner.rulesys.ClauseEntry;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.util.FileManager;
import org.apache.jena.rdf.model.Property;

public class test {
	
	public static void test1() {
		

		String ontoFile = "file:./data/test.owl";
		String ruleFile = "file:./data/test.rules";
		
		IReasoner famRea = ReasonerFactory.createReasoner();
		
		Model m = FileManager.get().loadModel(ontoFile);		
		
		String NS = "http://www.semanticweb.org/ywx/ontologies/2016/6/tju#";
		
		Resource Event = m.getResource(NS + "openwindowI");
		Resource newI = m.getResource(NS + "newI");		
		
		famRea.getInfModel(ruleFile, ontoFile);
		famRea.printInferResult(Event, newI);
	}
	
	
	public static void test2(){
		String ontoFile = "file:./data/test.owl";
		String ruleFile = "file:./data/test.rules";
		
		IReasoner myRea = ReasonerFactory.createReasoner();
		
		Model m = FileManager.get().loadModel(ontoFile);
		
		
		String NS = "http://www.semanticweb.org/ywx/ontologies/2016/6/tju#";
		
		Property asso = m.getProperty(NS + "associate");
		Resource newI = m.getResource(NS + "newI");
		
		while(true){
			Model x = myRea.refreshdata(m);//数据更新
			OntModel o = myRea.getOntologyModel(x);//转换
			InfModel inferred = myRea.getInfModel(ruleFile, o);//推理
			myRea.process_event(asso, newI);//处理事件
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
	

	
	public static void testQuery() {
		String ruleFile = "file:./expert/Expert.rules";
		String ontoFile = "file:./expert/Expert.owl";
		String queryString = "PREFIX Expert:<http://www.owl-ontologies.com/Expert.owl#> " +
	    	"SELECT ?expert ?subject " +
	    	"WHERE {?expert Expert:familiar_with ?subject} ";
		
		IReasoner famRea = ReasonerFactory.createReasoner();
		famRea.getInfModel(ruleFile, ontoFile);
		famRea.searchOnto(queryString);
	}
	

	public static void main(String[] args) {
//		testQuery();
		test2 ();
	}
	
}
