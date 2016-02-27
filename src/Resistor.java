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

	/**
	 * Returns the first common node shared by both resistors
	 * @param r2 The second Resistor
	 * @return
	 */
	public Node getCommonNode(Resistor r2) {
		Node returnVal = null;
		if(this.getNodeA() == r2.getNodeA() || this.getNodeA() == r2.getNodeB()){
			returnVal = this.getNodeA();
		}else if(this.getNodeB() == r2.getNodeA() || this.getNodeB() == r2.getNodeB()){
			returnVal = this.getNodeB();
		}
		return returnVal;
	}
	
	
	/**
	 * Returns the first node that is not common to both resistors
	 * this assumes that you've checked and made sure that
	 * these two resistors are in series before you call this method.
	 * If you don't check they are in series first this method doesn't
	 * actually do anything useful.
	 * @param r2 The second resistor
	 * @return The first uncommon node
	 */
	public Node getUncommonNode(Resistor r2){
		Node returnVal = null;
		if(this.getNodeA() != r2.getNodeA() && this.getNodeA() != r2.getNodeB()){ //this.nodeA is not common with r2.nodeA and its not common with r2.nodeB
			returnVal =  this.getNodeA();
		}else if(this.getNodeB() != r2.getNodeA() && this.getNodeB() != r2.getNodeB()){ //this.nodeB is the uncommon one.
			returnVal =  this.getNodeB();
		}
		return returnVal;
	}
	
	
	

	/* Private Methods */
	
	
}
