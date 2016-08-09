package tju.edu.process;

import java.util.List;

import org.apache.jena.graph.impl.LiteralLabelFactory;
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
import org.apache.jena.reasoner.rulesys.builtins.Remove;
import org.apache.jena.shared.RulesetNotFoundException;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.ReasonerVocabulary;

import com.ics.modbus.MainBulbController;

import tju.edu.model.humiditysensorDAO;
import tju.edu.model.temperaturesensorDAO;


/**
 * @purpose 
 * @author 
 * 
 */
public class ReasonerImpl implements IReasoner {
	
	private InfModel inf = null;

	/**
	 * 
	 * @param path
	 * @return
	 * @throws RulesetNotFoundException
	 */
	private GenericRuleReasoner getReasoner(String path) throws RulesetNotFoundException {
		
		List<Rule> rules = Rule.rulesFromURL(path);  //"file:./family/family.rules"
		GenericRuleReasoner reasoner = new GenericRuleReasoner(rules);
		
//		System.out.println("reasoner:"+reasoner);
		
		reasoner.setOWLTranslation(true);
		reasoner.setDerivationLogging(true);
		reasoner.setTransitiveClosureCaching(true);
		return reasoner;
		
	}
	
	/**
	 * 
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
	
	//public函数，用于把model转为OntModel
	public OntModel getOntologyModel(Model model){
		OntModel ont = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_RDFS_INF,
				model);
		Resource configuration = ont.createResource(); //a new anonymous resource linked to this model
		configuration.addProperty(ReasonerVocabulary.PROPruleMode
				, "hybrid");
		return ont;
	}
	
	/**使用该模型
	 * InfMode
	 * @param ontPath
	 * @param rulePath
	 * @return
	 */
	public InfModel getInfModel(String rulePath, String ontPath) {//两个字符串参数
		 
		 Model model = FileManager.get().loadModel(ontPath);
//		 System.out.println("model: "+ model.toString());
		 
		    String NS = "http://www.semanticweb.org/ywx/ontologies/2016/6/tju#";
			Resource thero = model.getResource(NS + "therometer");
			Property prop = model.getProperty(NS, "temperature");
			
//			StmtIterator stmtIter = model.listStatements(thero, prop, (RDFNode)null);
			StmtIterator stmtIter = model.listStatements();
			if(!stmtIter.hasNext()){
				System.out.println("the list is empty.");
			}
			while(stmtIter.hasNext()){
				Statement s= stmtIter.nextStatement();
//				System.out.println(s.toString());
//				System.out.println("objectname: "+ s.getResource().getLocalName());
			}
			
		this.inf = ModelFactory.createInfModel(getReasoner(rulePath), getOntModel(ontPath));
		return this.inf;
		
	}
	
	/**
	 * InfModel
	 * @param model
	 * @param rulePath
	 * @return
	 */
	public InfModel getInfModel(String rulePath, OntModel model) {

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
	
	public void process_event(Property asso, Resource newI){
		
		StmtIterator stmtIter = this.inf.listStatements(null,asso,newI);
		if(!stmtIter.hasNext()){
			System.out.println("there is no new event.");
		}
		while(stmtIter.hasNext()){
			Statement s = stmtIter.nextStatement();
//			System.out.println(s.toString());
			
//			System.out.println(s.getSubject().getLocalName());
			String actionname = s.getSubject().getLocalName();
			if(actionname.equals("openwindowI")){
				MainBulbController.turnOn(3);
				System.out.println("the window is going to open....done.");
			}
			else if(actionname.equals("closewindowI")){
				MainBulbController.turnOff(3);
				System.out.println("the window is going to close... done.");
			}
			else if(actionname.equals("openhumidifierI")){
//				System.out.println("the humidifier is going to open...done");
			}
			else if(actionname.equals("closehumidifierI")){
				System.out.println("the humidifier is going to close....done");
			}
		}
		
		System.out.println("process_event_end.");
	}
	
	private String[] getDBFromOnt(Model m,String a, String b){//从本体的位置获得数据库指针
		String NS = "http://www.semanticweb.org/ywx/ontologies/2016/6/tju#";
		Resource aa = m.getResource(NS + a);	
		Property bb = m.getProperty(NS + b);
		StmtIterator stmtIter = m.listStatements(aa,bb,(RDFNode)null);
		Statement s = stmtIter.nextStatement();//应该有且只有一个
		String str = s.getObject().toString();
		
		String info[]=str.split(",");
 		return info;
	}
	
	private void editOnt(Model m,double rs,String a,String b){ //把数据库的数值写入对应的本体三元组
		String NS = "http://www.semanticweb.org/ywx/ontologies/2016/6/tju#";
		Resource aa = m.getResource(NS + a);
		Property bb = m.getProperty(NS + b);
		
		StmtIterator stmtIter = m.listStatements(aa,bb,(RDFNode)null);
		Statement s2 = stmtIter.nextStatement();//应该有且只有一个
		
//		System.out.println("before: " + m.toString());
		m.remove(s2);
//		System.out.println("after: " + m.toString());
		m.addLiteral(aa, bb, (float)rs);
//		System.out.println("then: "+m.toString());
	}
	
	public Model refreshdata(Model m){
		
		//温度更新
		String info[]=getDBFromOnt(m, "therometer", "temperatureDB");//读取位置
 		String database=info[0];
 		String relation=info[1];
 		String ID=info[2];
 		
// 		System.out.println("database:"+info[0]);
// 		System.out.println("relation:"+info[1]);
// 		System.out.println("ID:"+info[2]);
 		
		temperaturesensorDAO tempDAO=new temperaturesensorDAO();
 	    
 		int rs=tempDAO.findAll(database,relation,Integer.parseInt(ID));//读取值
 		System.out.println("temp:"+rs);
 		
 		editOnt(m,rs,"therometer","temperature");//修改
 		
 		//湿度更新
 		String info2[]=getDBFromOnt(m, "humiditysensor1", "humidityDB");
 		database=info2[0];
 		relation=info2[1];
 		ID=info2[2];
 		humiditysensorDAO humDAO = new humiditysensorDAO();
 		double rs2 = humDAO.findAll(database, relation, Integer.parseInt(ID));
 		editOnt(m,rs2,"humiditysensor1","humidity");
 		
 		//
 		
 		return  m;
	}
	
	
	public void searchOnto(String queryString) {
		
		Query query = QueryFactory.create(queryString);	  
	    QueryExecution qe = QueryExecutionFactory.create(query, this.inf);
	    ResultSet results = qe.execSelect();
	    
	    ResultSetFormatter.out(System.out, results, query);
	    qe.close();
	    
	}
	
}
