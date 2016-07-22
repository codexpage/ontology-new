package tju.edu.jenacsdn.inference;

/**
 * @purpose ���󴴽�����
 * @author zhaohongjie
 * 
 */
public class ReasonerFactory {

	public static IReasoner createFamilyReasoner() {
		IReasoner familyReasoner = new ReasonerImpl();
		return familyReasoner;
	}
}
