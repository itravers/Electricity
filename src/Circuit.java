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
	Random random;
	
	/** Constructor
	 * Create a new empty circuit.
	 */
	public Circuit(){
		random = new Random(System.currentTimeMillis());
		name = newName();
		circuits = new ArrayList<Circuit>();
		nodes = new ArrayList<Node>();
		resistors = new ArrayList<Resistor>();
		supply = new PowerSupply(12, random);
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
	
	/**
	 * Series resistors are known because they share an ordinary node
	 * @return An ArrayList containing the two resistors in series.
	 */
	public ArrayList<Resistor>getSeriesResistors(){
		ArrayList<Resistor>results = null;
		for(int i = 0; i < resistors.size(); i++){
			for(int j = i+1; j < resistors.size(); j++){
				Resistor r1 = resistors.get(i);
				Resistor r2 = resistors.get(j);
				if ( ((r1.getNodeA() == r2.getNodeA()) || (r1.getNodeA() == r2.getNodeB() )) && !r1.getNodeA().isExtraOrdinary()){ //r1 and r2 are series
					results = new ArrayList<Resistor>();
					results.add(r1);
					results.add(r2);
					return results;
				}if( ((r1.getNodeB() == r2.getNodeA()) || (r1.getNodeB() == r2.getNodeB() )) && !r1.getNodeB().isExtraOrdinary()){ //r1 and r2 are series
					results = new ArrayList<Resistor>();
					results.add(r1);
					results.add(r2);
					return results;
				}else{//nothing is in series.
					//do nothing for this loop
				}
			}
		}
		return results; //this will alway be null, if a value appears it will be returned in the double loop
	}
	
	public void print(){
		System.out.println("C: " + name);
		printNodes();
		printResistors();
		printPowerSupplies();
		System.out.println();
		System.out.println();
	}
	
	/* Private Methods */
	private String newName(){
		return new BigInteger(16, random).toString(16);
	}
	
	private void printNodes(){
		for(int i = 0; i < nodes.size(); i++){
			Node n = nodes.get(i);
			System.out.format("%-14s", "Node: " + n.getName()+" ");
			System.out.print("V: " + n.getVoltage() + " ");
			System.out.format("%1s", "Connections: ");
			for(int j = 0; j < n.getConnections().size(); j++){
				System.out.format("%5s" , n.getConnections().get(j).getName());
			}
			System.out.println();
		}
	}
	
	private void printResistors(){
		for(int i = 0; i < resistors.size(); i++){
			Resistor r = resistors.get(i);
			System.out.format("%-1s", "Resistor: ");
			System.out.format("%6s", r.getName()+" ");
			
			System.out.format("%-2s", "Ohm: ");
			System.out.format("%8s", r.getOhms()+" ");
			
			System.out.format("%-2s", "Amp: ");
			System.out.format("%8s", r.getAmps()+" ");
			
			System.out.format("%-2s", "VDrop: ");
			System.out.format("%8s", r.getVoltageDrop()+" ");
			
			System.out.format("%-2s", "Watts: ");
			System.out.format("%8s", r.getWatts()+" ");
			
			System.out.format("%-2s", "NodeA: ");
			System.out.format("%8s", r.getNodeA().getName()+" ");
			
			System.out.format("%-2s", "NodeB: ");
			System.out.format("%8s", r.getNodeB().getName()+"\n");
		}
	}
	
	private void printPowerSupplies(){
		System.out.format("%-1s", "PowerSupply: ");
		System.out.format("%6s", supply.getName()+" ");
		
		System.out.format("%-1s", "Voltage: ");
		System.out.format("%6s", supply.getVoltage()+" ");
		
		System.out.format("%-1s", "+Node: ");
		System.out.format("%6s", supply.getPosNode().getName()+" ");
		
		System.out.format("%-1s", "-Node: ");
		System.out.format("%6s", supply.getNegNode().getName()+"\n");
	}
	
}
