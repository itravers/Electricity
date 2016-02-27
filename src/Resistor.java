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
	
	private Double ohms;
	private Double amps;
	private Double watts;
	private Double voltageDrop;
	private Node nodeA;
	private Node nodeB;
	private Replacement replacement;
	
	public Resistor(Double o, Random r){
		super(r);
		ohms = o;
		amps = null;
		watts = null;
		voltageDrop = null;
		nodeA = null;
		nodeB = null;
		replacement = null;
		
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
	
	public Double getOhms(){
		return ohms;
	}
	
	public Double getAmps(){
		return amps;
	}
	
	public void setAmps(Double a){
		amps = a;
	}
	
	public Double getWatts(){
		return watts;
	}
	
	public void setWatts(Double w){
		watts = w;
	}
	
	public Double getVoltageDrop(){
		return voltageDrop;
	}
	
	public void setVoltageDrop(Double v){
		voltageDrop = v;
	}
	
	public void setReplacement(Replacement r){
		this.replacement = r;
	}
	
	public Replacement getReplacement(){
		return replacement;
	}
	
	
	
	

	/* Private Methods */
	
	
}
