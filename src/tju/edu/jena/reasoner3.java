package tju.edu.jena;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.reasoner.Derivation;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasonerFactory;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.PrintUtil;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.ReasonerVocabulary;


public class reasoner3 {

	public static void main(String[] args) {
		String rules = "[rule1: (?a eg:p ?b) (?b eg:p ?c) -> (?a eg:p ?c)]";
		Reasoner reasoner = new GenericRuleReasoner(Rule.parseRules(rules));
		reasoner.setDerivationLogging(true);
		
		Model rawData = FileManager.get().loadModel("data/demodata-1.rdf"); 
		InfModel inf = ModelFactory.createInfModel(reasoner, rawData);
		
		Property p =rawData.getProperty("p");
		Resource A =rawData.getResource("a");
		
		
		PrintWriter out = new PrintWriter(System.out);
		for (StmtIterator i = inf.listStatements(A, p, (RDFNode)null); i.hasNext(); ) {
		    Statement s = i.nextStatement();
		    System.out.println("Statement is " + s);
		    for (Iterator id = inf.getDerivation(s); id.hasNext(); ) {
		        Derivation deriv = (Derivation) id.next();
		        deriv.printTrace(out, true);
		    }
		}
		out.flush();
		
		System.out.println("completed.");
		
		

	}

}
