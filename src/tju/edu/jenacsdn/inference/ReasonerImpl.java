package tju.edu.jenacsdn.inference;

import java.util.List;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.shared.RulesetNotFoundException;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.ReasonerVocabulary;


/**
 * @purpose 
 * @author 
 * 
 */
public class ReasonerImpl implements IReasoner {
	
	private InfModel inf = null;

	/**
	 * ��ȡһ������ӿ�
	 * @param path
	 * @return
	 * @throws RulesetNotFoundException
	 */
	private GenericRuleReasoner getReasoner(String path) throws RulesetNotFoundException {
		
		List<Rule> rules = Rule.rulesFromURL(path);  //"file:./family/family.rules"
		GenericRuleReasoner reasoner = new GenericRuleReasoner(rules);
		
		System.out.println("reasoner:"+reasoner);
		
		
		reasoner.setOWLTranslation(true);
		reasoner.setDerivationLogging(true);
		reasoner.setTransitiveClosureCaching(true);
		return reasoner;
		
	}
	
	/**
	 * ��ȡ����ı���
	 * @param path
	 * @return
	 */
	private OntModel getOntModel(String path) {
		
		Model model = ModelFactory.createDefaultModel();
		model.read(path);  //"file:./family/family.owl"
		OntModel ont = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_RDFS_INF,
				model);
		Resource configuration = ont.createResource(); //a new anonymous resource linked to this model
		configuration.addProperty(ReasonerVocabulary.PROPruleMode
				, "hybrid");
		return ont;
		
	}
	
	/**使用该模型
	 * InfModel�ǶԳ���Model����չ��֧���κ���ص���������
	 * @param ontPath
	 * @param rulePath
	 * @return
	 */
	public InfModel getInfModel(String rulePath, String ontPath) {
		 GenericRuleReasoner tmp = getReasoner(rulePath);
		 System.out.println("tmpywx:"+tmp.toString());
		 
		 Model model = FileManager.get().loadModel(ontPath);
		 System.out.println("model: "+ model.toString());
		    String NS = "http://www.semanticweb.org/ywx/ontologies/2016/6/tju#";
			Resource thero = model.getResource(NS + "therometer");
			Property prop = model.getProperty(NS, "temperature");
//			
//			Statement ss = model.getRequiredProperty(thero,prop);
//			System.out.println(ss);
			
//			
			
			//这里查找出推理之前的温度输出温度
			StmtIterator stmtIter = model.listStatements(thero, prop, (RDFNode)null);
//			StmtIterator stmtIter = model.listStatements();
			if(!stmtIter.hasNext()){
				System.out.println("ywxywxemp");
			}
			while(stmtIter.hasNext()){
				Statement s= stmtIter.nextStatement();
				System.out.println(s.toString());//输出全部的statement
				
				//只输出object
//				if(s instanceof Resource){
//					System.out.println("objectname: "+ s.getResource().getLocalName());//只有当客体为RDFNode的时候才能getResource
//				}
//				else{
//					System.out.println(s.getObject().toString());//这里注意当三元组客体是literal比如浮点数时，不能getResource,要getObject
//				}
			}
			//
			
			
		this.inf = ModelFactory.createInfModel(tmp, getOntModel(ontPath));
		return this.inf;
		
	}
	
	/**
	 * InfModel�ǶԳ���Model����չ��֧���κ���ص���������
	 * @param model
	 * @param rulePath
	 * @return
	 */
	public InfModel getInfModel(String rulePath, OntModel model) {
		//
		String NS = "http://www.semanticweb.org/ywx/ontologies/2016/6/tju#";
		Resource thero = model.getResource(NS + "therometer");
		Property prop = model.getProperty(NS, "temperature");
		StmtIterator stmtIter = model.listStatements(thero, prop, (RDFNode)null);
		while(stmtIter.hasNext()){
			Statement s= stmtIter.nextStatement();
			System.out.println("objectname: "+ s.getResource().getLocalName());
		}
		//
		this.inf = ModelFactory.createInfModel(getReasoner(rulePath), model);
		return this.inf;
	}
	

	
	/**
	 * 打印推理结果:输入两个类，输出两个类的关系，或者没有关系
	 * @param a
	 * @param b
	 */
	public void printInferResult(Resource a, Resource b) {
		
		StmtIterator stmtIter = this.inf.listStatements(a, null, b);//a和b之间存在的仍和关系的列表
		if (!stmtIter.hasNext()) {
			System.out.println("there is no relation between "
				      + a.getLocalName() + " and " + b.getLocalName());
			System.out.println("\n-------------------\n");
		}
		while (stmtIter.hasNext()) {//循环输出所有关系
			Statement s = stmtIter.nextStatement();
			System.out.println("Relation between " + a.getLocalName() + " and "
				      + b.getLocalName() + " is :");
			System.out.println(a.getLocalName() + " "
				      + s.getPredicate().getLocalName() + " " + b.getLocalName());
			System.out.println("\n-------------------\n");
		}
		
	}
	
	public void searchOnto(String queryString) {
		
		Query query = QueryFactory.create(queryString);	  
	    QueryExecution qe = QueryExecutionFactory.create(query, this.inf);
	    ResultSet results = qe.execSelect();
	    
	    ResultSetFormatter.out(System.out, results, query);
	    qe.close();
	    
	}
	
}
