package tju.edu.process;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.ics.main.SimpleLightSwitch;
import com.ics.modbus.AirConditionController;
import com.ics.modbus.Curtain;
import com.ics.modbus.CurtainController;
import com.ics.modbus.DoorController;
import com.ics.modbus.MainBulbController;
import com.ics.modbus.PosterController;
import com.ics.modbus.Socketcontroller;
import com.ics.modbus.TVController;

import tju.edu.model.humiditysensorDAO;
import tju.edu.model.sensor;
import tju.edu.model.sensorDAO;
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

		List<Rule> rules = Rule.rulesFromURL(path); // "file:./family/family.rules"
		GenericRuleReasoner reasoner = new GenericRuleReasoner(rules);

		// System.out.println("reasoner:"+reasoner);

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
		model.read(path); // "file:./family/family.owl"
		OntModel ont = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_RDFS_INF, model);
		Resource configuration = ont.createResource(); // a new anonymous
														// resource linked to
														// this model
		configuration.addProperty(ReasonerVocabulary.PROPruleMode, "hybrid");
		return ont;

	}

	// public函数，用于把model转为OntModel
	public OntModel getOntologyModel(Model model) {
		OntModel ont = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_RDFS_INF, model);
		Resource configuration = ont.createResource(); // a new anonymous
														// resource linked to
														// this model
		configuration.addProperty(ReasonerVocabulary.PROPruleMode, "hybrid");
		return ont;
	}

	/**
	 * 使用该模型 InfMode
	 * 
	 * @param ontPath
	 * @param rulePath
	 * @return
	 */
	public InfModel getInfModel(String rulePath, String ontPath) {// 两个字符串参数

		Model model = FileManager.get().loadModel(ontPath);
		// System.out.println("model: "+ model.toString());

		String NS = "http://www.semanticweb.org/ywx/ontologies/2016/6/tju#";
		Resource thero = model.getResource(NS + "therometer");
		Property prop = model.getProperty(NS, "temperature");

		// StmtIterator stmtIter = model.listStatements(thero, prop,
		// (RDFNode)null);
		StmtIterator stmtIter = model.listStatements();
		if (!stmtIter.hasNext()) {
			System.out.println("the list is empty.");
		}
		while (stmtIter.hasNext()) {
			Statement s = stmtIter.nextStatement();
			// System.out.println(s.toString());
			// System.out.println("objectname: "+
			// s.getResource().getLocalName());
		}

		this.inf = ModelFactory.createInfModel(getReasoner(rulePath), getOntModel(ontPath));
		return this.inf;

	}

	/**
	 * InfModel
	 * 
	 * @param model
	 * @param rulePath
	 * @return
	 */
	public InfModel getInfModel(String rulePath, OntModel model) {// 使用的是这个

		this.inf = ModelFactory.createInfModel(getReasoner(rulePath), model);
		return this.inf;
	}

	/**
	 * 打印推理结果:输入两个类，输出两个类的关系，或者没有关系
	 * 
	 * @param a
	 * @param b
	 */
	public void printInferResult(Resource a, Resource b) {

		StmtIterator stmtIter = this.inf.listStatements(a, null, b);// a和b之间存在的仍和关系的列表
		if (!stmtIter.hasNext()) {
			System.out.println("there is no relation between " + a.getLocalName() + " and " + b.getLocalName());
			System.out.println("\n-------------------\n");
		}
		while (stmtIter.hasNext()) {// 循环输出所有关系
			Statement s = stmtIter.nextStatement();
			System.out.println("Relation between " + a.getLocalName() + " and " + b.getLocalName() + " is :");
			System.out.println(a.getLocalName() + " " + s.getPredicate().getLocalName() + " " + b.getLocalName());
			System.out.println("\n-------------------\n");
		}

	}

	public void process_event(Property asso, Resource newI) {

		StmtIterator stmtIter = this.inf.listStatements(null, asso, newI);
		if (!stmtIter.hasNext()) {
			System.out.println("there is no new event.");
		}
		while (stmtIter.hasNext()) {
			Statement s = stmtIter.nextStatement();
			String actionname = s.getSubject().getLocalName();

			// String prename =actionname.substring(0,
			// actionname.length()-1);//前缀
			// String para
			// =actionname.substring(actionname.length()-1,actionname.length());//最后一位
			// if(prename.equals("openbulb")){
			// if(para.equals("a")){
			// MainBulbController.turnOnAll();
			// }
			// int paraid = Integer.parseInt(para);
			// MainBulbController.turnOn(paraid);
			// }

			// 4个大灯
			for (int i = 1; i <= 4; i++) {
				if (actionname.equals("openbulb" + i)) {
					MainBulbController.turnOn(i);
					System.out.println("the bulb" + i + " is going to open....done.");
				} else if (actionname.equals("closebulb" + i)) {
					MainBulbController.turnOff(i);
					System.out.println("the bulb" + i + " is going to close... done.");
				}
			}
			if (actionname.equals("openbulball")) {
				MainBulbController.turnOnAll();
				System.out.println("all bulbs are going to open... done.");
			} else if (actionname.equals("closebulball")) {
				MainBulbController.turnOffAll();
				System.out.println("all bulbs are going to close... done.");
			}

			// 灯条
			if (actionname.equals("openbarlight")) {
				SimpleLightSwitch.turnOnOfAll();
				System.out.println("all lightbars are going to open... done.");
			} else if (actionname.equals("closebarlight")) {
				SimpleLightSwitch.turnOffOfAll();
				System.out.println("all lightbars are going to close... done.");
			}

			// 广告机
			if (actionname.equals("openposter")) {
				try {
					PosterController.turnOn();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("poster is going to open... done.");
			} else if (actionname.equals("closeposter")) {
				try {
					PosterController.turnOff();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("poster is going to close... done.");
			}
			// TV
			if (actionname.equals("openTV")) {
				try {
					TVController.turnOn();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("all lightbars are going to open... done.");
			} else if (actionname.equals("closeTV")) {
				try {
					TVController.turnOff();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("all lightbars are going to close... done.");
			}

			// 两个门
			for (int i = 1; i <= 2; i++) {
				if (actionname.equals("opendoor" + i)) {
					DoorController.unlockdoor(i);
					System.out.println("the door" + i + " is going to open....done.");
				} else if (actionname.equals("closedoor" + i)) {
					DoorController.unlockdoor(i);
					System.out.println("the door" + i + " is going to close... done.");
				}
			}

			// 四个窗帘
			Map<Integer, Curtain> map = new HashMap<Integer, Curtain>();
			map.put(1, Curtain.Cutain1);
			map.put(2, Curtain.Cutain2);
			map.put(3, Curtain.Cutain3);
			map.put(4, Curtain.Cutain4);
			for (int i = 1; i <= 4; i++) {
				if (actionname.equals("rollup" + i)) {
					CurtainController.up(new Curtain[] { map.get(i) });
					System.out.println("the curtain" + i + " is going to open....done.");
				} else if (actionname.equals("rolldown" + i)) {
					CurtainController.down(new Curtain[] { map.get(i) });
					System.out.println("the curtain" + i + " is going to close... done.");
				}
			}

			// 空调
			if (actionname.equals("openairconditioner")) {// 确保空调开始的时候是关着的，这样状态才是对的
				AirConditionController.turnOn();
				System.out.println("airconditioner is going to open... done.");
			} else if (actionname.equals("closeairconditioner")) {
				AirConditionController.turnOn();
				System.out.println("airconditioner is going to close... done.");
			}

			// 三个插座 从上到下1，2，3
			for (int i = 1; i <= 3; i++) {
				if (actionname.equals("opensocket" + i)) {
					try {
						Socketcontroller.turnOn(i);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("the socket" + i + " is going to open....done.");
				} else if (actionname.equals("closesocket" + i)) {
					try {
						Socketcontroller.turnOff(i);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("the socket" + i + " is going to close... done.");
				}
			}

		}

		while (stmtIter.hasNext()) {
			Statement s = stmtIter.nextStatement();
			// System.out.println(s.toString());

			// System.out.println(s.getSubject().getLocalName());
			String actionname = s.getSubject().getLocalName();
			if (actionname.equals("openwindowI")) {
				MainBulbController.turnOn(3);
				System.out.println("the window is going to open....done.");
			} else if (actionname.equals("closewindowI")) {
				MainBulbController.turnOff(3);
				System.out.println("the window is going to close... done.");
			} else if (actionname.equals("openhumidifierI")) {
				// System.out.println("the humidifier is going to open...done");
			} else if (actionname.equals("closehumidifierI")) {
				System.out.println("the humidifier is going to close....done");
			}
		}

		System.out.println("process_event_end.");
	}

	private String[] getDBFromOnt(Model m, String a, String b) {// 从本体的位置获得数据库指针
		String NS = "http://www.semanticweb.org/ywx/ontologies/2016/6/tju#";
		Resource aa = m.getResource(NS + a);
		Property bb = m.getProperty(NS + b);
		StmtIterator stmtIter = m.listStatements(aa, bb, (RDFNode) null);
		Statement s = stmtIter.nextStatement();// 应该有且只有一个
		String str = s.getObject().toString();

		String info[] = str.split(",");
		return info;
	}

	private void editOnt(Model m, double rs, String a, String b) { // 把数据库的数值写入对应的本体三元组
		String NS = "http://www.semanticweb.org/ywx/ontologies/2016/6/tju#";
		Resource aa = m.getResource(NS + a);
		Property bb = m.getProperty(NS + b);

		StmtIterator stmtIter = m.listStatements(aa, bb, (RDFNode) null);
		Statement s2 = stmtIter.nextStatement();// 应该有且只有一个

		// System.out.println("before: " + m.toString());
		m.remove(s2);
		// System.out.println("after: " + m.toString());
		m.addLiteral(aa, bb, (float) rs);
		// System.out.println("then: "+m.toString());
	}

	public Model refreshdata(Model m) {

		// TODO 这里写一个循环，循环读入x,hasname,y这种属性的三元组，获得每个x,y(name)，
		// y-->name x,hasdb,z--->获得值database,relation,id id是独一无二的。
		// 然后根据这个来读取数据库的值 存到x,hasvalue,a里面做refresh,然后推理（根据rule），然后再检查事件触发效应器。
		// 每当加一个新的传感器，只需要改owl，然后加上rule，加上效应器处理代码即可，如果是新品种则要改data collector,数据库表单
		// datacollector可以通用化吗，不可以，不同传感器data1,data2不同，单位不同，不能直接通用户，这部分适配代码必须写，数据库表单也许
		// 可以自动操作，没有表单时创建表单

		String NS = "http://www.semanticweb.org/ywx/ontologies/2016/6/tju#";
		Property bb = m.getProperty(NS + "hasinfo");// 所有hasinfo属性的都认为是一个传感器
		StmtIterator stmtIter = m.listStatements(null, bb, (RDFNode) null);
		int count = 0;
		// 这里假如从owl中读出的是一个新的之前没有的表，可以考虑在数据库中新建
		while (stmtIter.hasNext()) {
			// Resource aa = m.getResource(NS + "");
			Statement s = stmtIter.nextStatement();
			String str = s.getObject().toString();
			String owlname = s.getSubject().getLocalName().toString();
			System.out.println(owlname);
			// System.out.println(str);
			count++;
			String info[] = str.split(",");
			// =====格式=======
			String database = info[0];
			String relation = info[1];
			String ID = info[2];
			String name = info[3];
			String type = info[4];
			// =========读取值=======
			sensor sen = new sensor();
			sen.setName(name);
			sen.setSensorid(Integer.parseInt(ID));
			sen.setType(Integer.parseInt(type));
			sensorDAO senDAO = new sensorDAO();
			senDAO.read(sen);
			double value = sen.getValue();
			System.out.println("value of " + name + "sensor: " + value);
			// ======写入=======
			editOnt(m, value, owlname, "hasvalue");

		}
		System.out.println("sensor count: " + count);

		// //温度更新
		// String info[]=getDBFromOnt(m, "therometer", "temperatureDB");//读取位置
		// String database=info[0];
		// String relation=info[1];
		// String ID=info[2];
		//
		// //
		// temperaturesensorDAO tempDAO=new temperaturesensorDAO();
		//
		// int rs=tempDAO.findAll(database,relation,Integer.parseInt(ID));//读取值
		// System.out.println("temp:"+rs);
		//
		// editOnt(m,rs,"therometer","temperature");//修改
		//
		// //湿度更新
		// String info2[]=getDBFromOnt(m, "humiditysensor1", "humidityDB");
		// database=info2[0];
		// relation=info2[1];
		// ID=info2[2];
		// humiditysensorDAO humDAO = new humiditysensorDAO();
		// double rs2 = humDAO.findAll(database, relation,
		// Integer.parseInt(ID));
		// editOnt(m,rs2,"humiditysensor1","humidity");
		//
		// //

		return m;
	}

	public void searchOnto(String queryString) {

		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, this.inf);
		ResultSet results = qe.execSelect();

		ResultSetFormatter.out(System.out, results, query);
		qe.close();

	}

	public static void main(String[] args) {
		try {
			Socketcontroller.turnOff(3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
