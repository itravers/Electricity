import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;


/**
 * A circuit represents an actual electrical circuit.
 * We model ours with nodes, resistances and power supplies.
 * A circuit will have 1 power supply, 1 or more resistances, and 2 or more nodes
 * connecting the power supplies and resistances.
 * @author Isaac Assegai
 *
 */
public class Circuit {
	/* Member Variables */
	private String name; //The name we refer to this particular circuit by.
	private ArrayList<Circuit>circuits; // In order to simplify circuits we will have to remember the previous ones.
	private ArrayList<Node>nodes; // A List of all the nodes in the circuit.
	private ArrayList<Resistor>resistors; // An list of all the resistors in the circuit.
	private PowerSupply supply; // The power supply of the circuit.
	
	/** Constructor
	 * Create a new empty circuit.
	 */
	public Circuit(){
		name = newName(System.currentTimeMillis());
		circuits = new ArrayList<Circuit>();
		nodes = new ArrayList<Node>();
		resistors = new ArrayList<Resistor>();
		supply = new PowerSupply(12);
	}
	
	/* Public Methods */
	public void addCircuit(Circuit c){
		circuits.add(c);
	}
	
	public void addNode(Node n){
		nodes.add(n);
	}
	
	public void addResistor(Resistor r){
		resistors.add(r);
	}
	
	public void addSupply(PowerSupply s){
		supply = s;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	/**
	 * Parallel resistors are known because they
	 * share the same extraordinary nodes.
	 * @return
	 */
	public ArrayList<Resistor>getParallelResistors(){
		ArrayList<Resistor>results = null;
		System.out.println("Checking parallel Resistors");
		for(int i = 0; i < resistors.size(); i++){
			for(int j = i+1; j < resistors.size(); j++){
				Resistor r1 = resistors.get(i);
				Resistor r2 = resistors.get(j);
				if(r1.getNodeA().isExtraOrdinary() && r1.getNodeB().isExtraOrdinary() 
						&& r2.getNodeA().isExtraOrdinary() && r2.getNodeB().isExtraOrdinary()){ //Both nodes, from both resistors must be extraordinary
					if(r1.getNodeA() == r2.getNodeA() && r1.getNodeB() == r2.getNodeB()){ //nodes match
						results = new ArrayList<Resistor>();
						results.add(r1);
						results.add(r2);
						return results;
					}else if(r1.getNodeA() == r2.getNodeB() && r1.getNodeB() == r2.getNodeA()){ //nodes match
						results = new ArrayList<Resistor>();
						results.add(r1);
						results.add(r2);
						return results;
					}else{ //nodes don't match
						//do nothing and keep going through loop
					}
				}
			}
		}
		return results;
	}
	
	/* Private Methods */
	private String newName(long l){
		Random random = new Random(l);
		return new BigInteger(32, random).toString(32);
	}
	
}
