package tju.edu.jena;

import java.util.Iterator;

import java.util.Iterator;

import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;
import org.apache.jena.ontology.*;

public class test_0010 {
	
	public static void main(String[] args) {
		
		OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		
		ontModel.read("file:/Users/ywx/Documents/java-workspace/random/src/tju/edu/jena/food.owl");
		
		OntClass cls = ontModel.createClass(":FoodClass");
		cls.addComment("the EquivalentClass of Food...", "EN");
		
		OntClass oc = ontModel.getOntClass("http://www.w3.org/2001/sw/WebOnt/guide-src/food#ConsumableThing");
		oc.addEquivalentClass(cls);
		
		for (Iterator<OntClass> i = ontModel.listClasses(); i.hasNext(); ) {
			OntClass c = i.next();
			if (!c.isAnon()) {  
				System.out.print("Class");
				System.out.println(c.getModel().getGraph().getPrefixMapping().shortForm(c.getURI()));
				
				if (c.getLocalName().equals("ConsumableThing")) {
					System.out.println("  URI@" + c.getURI());
					System.out.println("Animal's EquivalentClass is " + c.getEquivalentClass());//获得等价类
					System.out.println("m[Comments:" + c.getEquivalentClass().getComment("EN")  + "]");//获得comment
				}
				
				for (Iterator<OntClass> it = c.listSuperClasses(); it.hasNext(); ) {//父类
					OntClass sp = it.next();
					String str = c.getModel().getGraph().getPrefixMapping().shortForm(c.getURI());
					String strSP = sp.getURI();
					try {
						str = str + ":superclass " + strSP.substring(strSP.indexOf('#') + 1);
						System.out.println("  Class" + str);
					} catch (Exception e) {}
				}
				
				for (Iterator<OntClass> it = c.listSubClasses(); it.hasNext(); ) {//子类
					System.out.print("  Class");
					OntClass sb = it.next();
					System.out.println(c.getModel().getGraph().getPrefixMapping().shortForm(c.getURI()) +
							"'s suberClass is " + sb.getModel().getGraph().getPrefixMapping().shortForm(sb.getURI()));
					
				}
				
				for (Iterator<OntProperty> ipp = c.listDeclaredProperties(); ipp.hasNext(); ) {//属性（关系）
					OntProperty p = ipp.next();
					System.out.println("  associated property: " + p.getLocalName());
				}
				/**/
			}
			else {}
		}
	}
}
