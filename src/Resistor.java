import java.math.BigInteger;
import java.util.Random;

/**
 * A resistor is the main base class for most circuit elements.
 * We are given a resistors resistance and it's connections,
 * our algorithm will figure out the amps, watts, and voltage drops.
 * We'll use the replaces list to tell our algorithm how to backtrack.
 * @author Isaac Assegai
 *
 */
public class Resistor extends Element {
	private String name;
	private Double ohms;
	private Double amps;
	private Double voltageDrop;
	private Node nodeA;
	private Node nodeB;
	private Replacement replacement;
	
	public Resistor(Double o){
		name = newName(System.currentTimeMillis());
		ohms = o;
		amps = null;
		voltageDrop = null;
		nodeA = null;
		nodeB = null;
		replacement = null;
		
	}
	
	
	public Resistor(int o) {
		new Resistor((double)o);
	}

	/* Public Methods */
	public void setNodeA(Node n){
		nodeA = n;
	}
	
	public void setNodeB(Node n){
		nodeB = n;
	}
	
	public Node getNodeA(){
		return nodeA;
	}
	
	public Node getNodeB(){
		return nodeB;
	}
	
	

	/* Private Methods */
	private String newName(long l){
		Random random = new Random(l);
		return new BigInteger(32, random).toString(32);
	}
	
	/* Private Classes */
	private class Replacement{
		public String a;
		public String b;
		public String type;
		
		public Replacement(String a, String b, String type){
			this.a = a;
			this.b = b;
			this.type = type;
		}
		
	}
	
}
