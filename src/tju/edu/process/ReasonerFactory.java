package tju.edu.process;

/**
 * @purpose 
 * @author 
 * 
 */
public class ReasonerFactory {

	public static IReasoner createReasoner() {
		IReasoner familyReasoner = new ReasonerImpl();
		return familyReasoner;
	}
}
