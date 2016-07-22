package tju.edu.myjena;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;


//import com.hp.hpl.jena.ontology.OntModel;
//import com.hp.hpl.jena.rdf.model.InfModel;
//import com.hp.hpl.jena.rdf.model.Resource;

/**
 * 
 */
public interface IReasoner {

	public OntModel getOntologyModel(Model path);
	public InfModel getInfModel(String ontPath, String rulePath);
	public InfModel getInfModel(String rulePath, OntModel model);
	public void printInferResult(Resource a, Resource b);
	public void searchOnto(String queryString);
	
	public void process_event(Property asso, Resource newI);
	public Model refreshdata(Model m);
	
}
