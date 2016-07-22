package tju.edu.jenacsdn.inference;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Resource;


//import com.hp.hpl.jena.ontology.OntModel;
//import com.hp.hpl.jena.rdf.model.InfModel;
//import com.hp.hpl.jena.rdf.model.Resource;

/**
 * 
 */
public interface IReasoner {

	public InfModel getInfModel(String ontPath, String rulePath);
	public InfModel getInfModel(String rulePath, OntModel model);
	public void printInferResult(Resource a, Resource b);
	public void searchOnto(String queryString);
	
}
