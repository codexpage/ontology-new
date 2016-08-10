package tju.edu.process;

import java.util.List;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.reasoner.rulesys.ClauseEntry;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.util.FileManager;
import org.apache.jena.rdf.model.Property;

public class Process {
	
	public static void process_reasoner(){
		//the path 
		String ontoFile = "file:./data/room.owl";
		String ruleFile = "file:./data/test.rules";
		
		IReasoner Rea = ReasonerFactory.createReasoner();
		Model m = FileManager.get().loadModel(ontoFile);
		String NS = "http://www.semanticweb.org/ywx/ontologies/2016/6/tju#";
		
		Property asso = m.getProperty(NS + "associate");
		Resource newI = m.getResource(NS + "newI");
		
		//to find if there are any new event
		while(true){
			Model x = Rea.refreshdata(m);//数据更新 database to model
			OntModel o = Rea.getOntologyModel(x);//转换 Model to ontModel
			InfModel inferred = Rea.getInfModel(ruleFile, o);//推理
			Rea.process_event(asso, newI);//处理事件 control the relay
			try {
				Thread.sleep(1000);// process every 1s
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		
	}
	


	
	public static void testQuery() {
		String ruleFile = "file:./expert/Expert.rules";
		String ontoFile = "file:./expert/Expert.owl";
		
		//查询sparql语句
		String queryString = "PREFIX Expert:<http://www.owl-ontologies.com/Expert.owl#> " +
	    	"SELECT ?expert ?subject " +
	    	"WHERE {?expert Expert:familiar_with ?subject} ";
		
		IReasoner famRea = ReasonerFactory.createReasoner();
		famRea.getInfModel(ruleFile, ontoFile);
		famRea.searchOnto(queryString);
	}
	

	public static void main(String[] args) {
		process_reasoner();
	}
	
}
