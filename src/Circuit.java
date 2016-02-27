import java.util.ArrayList;


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
	private ArrayList<Circuit>circuits; // In order to simplify circuits we will have to remember the previous ones.
	private ArrayList<Node>nodes; // A List of all the nodes in the circuit.
	private ArrayList<Resistor>resistors; // An list of all the resistors in the circuit.
	private PowerSupply supply; // The power supply of the circuit.
	
	/** Constructor
	 * Create a new empty circuit.
	 */
	public Circuit(){
		circuits = new ArrayList<Circuit>();
		nodes = new ArrayList<Node>();
		resistors = new ArrayList<Resistor>();
		supply = new PowerSupply();
	}
	
	/* Public Methods */
	public void addCircuit(Circuit c){
		circuits.add(c);
	}
	
	public void addNode(Node n){
		nodes.add(n);
	}
	
	public void addResistor(Resistor r){
		resistors.add(r_;)
	}
	
	public void addSupply(PowerSupply s){
		supply = s;
	}
	
}
