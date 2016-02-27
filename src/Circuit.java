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
	private Circuit complicatedCircuit; // In order to simplify circuits we will have to remember the previous ones.
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
		complicatedCircuit = null;
		nodes = new ArrayList<Node>();
		resistors = new ArrayList<Resistor>();
		supply = null;
	}
	
	/**
	 * This is the constructor used by the combine call. We create a new circuit based on the current circuit,
	 * however we replace the two resistors contained in toCombine, with a single combined resistor in the new circuit.
	 * We decide how to combine the two resistors into the new circuit using isSeries to tell if they are in Series, or Parallel.
	 * Our new circuit contains a reference to the more complicated circuit so we can backtrack later
	 * @param circuit The complicatedCircuit we are simplifying.
	 * @param toCombine The two Resistors we are combining
	 * @param isSeries True for Series resistors, false and these are parallel resistors
	 */
	public Circuit(Circuit oldCircuit, ArrayList<Resistor> toCombine, Boolean isSeries) {
		random = new Random(System.currentTimeMillis());
		name = newName();
		complicatedCircuit = oldCircuit;
		Resistor r1 = toCombine.get(0);
		Resistor r2 = toCombine.get(1);
		if(isSeries){ //toCombine resistors are in series
			
			/* We've need to complete and combine 2 resistors in series.
			 * 1. Create a new Resistor using r=r1+r2
			 * 2. Find the 1 common, and 2 uncommon nodes.
			 * 3. Tell the new Resistor what it is replaceing, 2 resistors and a node.
			 * 4. Copy nodes and resistors, and powerSupply from old circuit
			 * 5. Remove toCombine resistors from the copy.
			 * 5A. Remove commonNode from copy
			 * 5B. Remove all nodes references to toCombine resistors from copy
			 * 6. Wire up new resistors node references
			 * 6A. Wire up new resistors neighbors to new resistor
			 * 7. Add new resistor to the copy
			 * At this point the copy will be 1 step more simplified than the current circuit(this)
			 * */
			
			//1. Create a new resistor using r=r1+r2
			Resistor r = new Resistor(r1.getOhms() + r2.getOhms(), random);
			
			//2. Find the 1 common, and 2 uncommon nodes.
			Node commonNode = r1.getCommonNode(r2);
			Node uncommonNodeR1 = r1.getUncommonNode(r2);
			Node uncommonNodeR2 = r2.getUncommonNode(r1);
			
			//3. Tell new resistor it is replacing 2 old resistors, and an old node.
			r.setReplacement(new Replacement(r1, r2, commonNode, isSeries));
			
			//4. Copy nodes and resistors from oldCircuit
			nodes = copyNodes(oldCircuit.nodes);
			resistors = copyResistors(oldCircuit.resistors);
			supply = copyPowerSupply(oldCircuit.supply);
			
			//5. Remove toCombine Resistors from copy.
			removeResistor(r1, resistors);
			removeResistor(r2, resistors);
			
			//5A. Remove commonNode from copy
			nodes.remove(commonNode);
			
			//5B. Remove all node references to the toCombine resistors from the copy
			removeResistorFromNodeConnections(r1, nodes);
			removeResistorFromNodeConnections(r2, nodes);
			
			//6. Wire up new Resistors Node references. then nodes, resistor connections
			r.setNodeA(uncommonNodeR1);
			r.setNodeB(uncommonNodeR2);
			
			//6A. Wire up new resistors neighbors to new resistor
			addNeighborConnections(r, nodes);
			
			//7. Add new resistor to this new copy
			this.resistors.add(r);
			
					
			
		}else{ //toCombine resistors are in parallel
			/* To combine parallel Resistors:
			 * 1. Create new Resistor, using ohm (r1*r2)/(r1+r2)
			 * 2. Tell new resistor what it is replacing.
			 * 3. Copy nodes and resistors, and ps from oldCircuit
			 * 4. Remove toCombine resistors from copy.
			 * 5. Add new resistor to copy, wire up both sides
			 * 6. Add this to copy.complicatedCircuit
			 * At this point the copy will be 1 step more simplified than the current circuit(this)
			 */
			Double resistance = (r1.getOhms()*r2.getOhms())/(r1.getOhms()+r2.getOhms());
			Resistor r = new Resistor(resistance, random);
			r.setReplacement(new Replacement(r1, r2, isSeries));
			nodes = copyNodes(oldCircuit.nodes); 
			resistors = copyResistors(oldCircuit.resistors);
			supply = copyPowerSupply(oldCircuit.supply);
			
			removeResistor(r1, resistors); //will remove the same resistors name from resistors as r1.name
			removeResistor(r2, resistors);
			
			removeResistorFromNodeConnections(r1, nodes);
			removeResistorFromNodeConnections(r2, nodes);
			
			//wire up new resistor
			r.setNodeA(r1.getNodeA()); //since this is parallel, nodeA, and nodeB, will the same as both n1 and n2
			r.setNodeB(r1.getNodeB());
			
			//wire up new Resistors Nodes
			addNeighborConnections(r, nodes);
			
			resistors.add(r);
		}
	}
	
	private static void addNeighborConnections(Resistor r, ArrayList<Node>nodes){
		//our neighbor connections no longer work by using straight references, because we've been copying a lot of stuff
		//we need to check by
		
		Node nodeA = r.getNodeA();
		Node nodeB = r.getNodeB();
		
		//first we find the neighbor nodes of r, by searching for names
		for(int i = 0; i < nodes.size(); i++){
			Node changeNode = nodes.get(i);
			if(changeNode.getName().equals(nodeA.getName())){
				changeNode.addConnection(r);
			}else if(changeNode.getName().equals(nodeB.getName())){
				changeNode.addConnection(r);
			}
			
		}
	}
	
	private void removeResistorFromNodeConnections(Resistor r, ArrayList<Node>nodes){
		//loop through all nodes, check each connection in the nodes to see if the name equals r.name
		for(int i = 0; i < nodes.size(); i++){
			Node n = nodes.get(i);
			for(int j = 0; j < n.getConnections().size(); j++){
				Element connection = n.getConnections().get(j);
				if(connection.getName().equals(r.getName())){ //this nodes connections name is the same name as the resistor
					//remove the connection
					n.getConnections().remove(connection);
				}
			}
		}
	}

	/* Public Methods */
	public void addComplicatedCircuit(Circuit c){
		complicatedCircuit = c;
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
	 * Recursively simplify by finding first series or parallel resistors
	 * then create new circuit combining said resistors, and simplify that
	 */
	public void simplify(){
		//first we check if this is the most simple circuit possible, if so, we can go on to solve simple circuit.
		//we know it's the most simple circuit, if there is only 1 resistor
		if(resistors.size() <= 1){
			solve();
		}else{
			combine();
		}
	}
	
	/**
	 * Find the first Series, or Parallel resisistors, combine them and create a new
	 * circuit using the combined resistors, then simplify that one.
	 */
	private void combine(){
		Boolean isSeries = null;
		ArrayList<Resistor>toCombine = getSeriesResistors(); //If series resistors exists, this will return them, otherwise toCombine will be null.
		if(toCombine == null){ //There are no simple series resistors in this circuit.
			toCombine = getParallelResistors(); // If parallel resistors exists, this will return them, otherwise toCombine will be null
			if(toCombine == null){
				//we only get to this point if there are no series, or parallel resistors in circuit.
				//However, we only call combine if there are more than 1 resistor, therefore something
				//must be in series, or in parallel, therefore something is wrong and there is an error somewhere in 
				//the algorithm.
				System.out.println("ERROR: resistors.size() == " + resistors.size() + ", however, no series or parallel resistors were found.");
				System.exit(1);
			}else{
				isSeries = false; //We know that toCombine are resistors in parallel, so isSeries is false
			}
		}else{
			isSeries = true; //We know that toCombine are resistors in series, so isSeries is true
		}
		
		/* At this point toCombine is guaranteed not to be null, it has two elements. */
		Circuit c2 = new Circuit(this, toCombine, isSeries);
		c2.print();
		
		c2.simplify();
		
	}
	
	private void solve(){
		System.out.println("THIS IS WHERE WE WILL SOLVE!");
	}
	
	/**
	 * Parallel resistors are known because they
	 * share the same extraordinary nodes.
	 * @return
	 */
	public ArrayList<Resistor>getParallelResistors(){
		ArrayList<Resistor>results = null;
		System.out.print("Checking parallel Resistors: ");
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
						System.out.println("Parallels Found1");
						return results;
					}else if(r1.getNodeA() == r2.getNodeB() && r1.getNodeB() == r2.getNodeA()){ //nodes match
						results = new ArrayList<Resistor>();
						results.add(r1);
						results.add(r2);
						System.out.println("Parallels Found2");
						return results;
					}else{ //nodes don't match
						//do nothing and keep going through loop
					}
				}
			}
		}
		System.out.println(" No Parrallels found ");
		return results;
	}
	
	/**
	 * Series resistors are known because they share an ordinary node
	 * @return An ArrayList containing the two resistors in series.
	 */
	public ArrayList<Resistor>getSeriesResistors(){
		ArrayList<Resistor>results = null;
		System.out.print(" Checking Series Resistors ");
		for(int i = 0; i < resistors.size(); i++){
			for(int j = i+1; j < resistors.size(); j++){
				Resistor r1 = resistors.get(i);
				Resistor r2 = resistors.get(j);
				if ( r1.getNodeA() == r2.getNodeA()  && !r1.getNodeA().isExtraOrdinary()){ //r1 and r2 are series
					results = new ArrayList<Resistor>();
					results.add(r1);
					results.add(r2);
					System.out.println("Series Found1");
					return results;
				}else if(r1.getNodeA() == r2.getNodeB()  && !r1.getNodeA().isExtraOrdinary()){
					results = new ArrayList<Resistor>();
					results.add(r1);
					results.add(r2);
					System.out.println("Series Found2");
					return results;
				}
				
				
				
				if(r1.getNodeB() == r2.getNodeA() && !r1.getNodeB().isExtraOrdinary()){ //r1 and r2 are series
					results = new ArrayList<Resistor>();
					results.add(r1);
					results.add(r2);
					System.out.println("Series Found3");
					return results;
				}else if(r1.getNodeB() == r2.getNodeB()  && !r1.getNodeB().isExtraOrdinary()){
					results = new ArrayList<Resistor>();
					results.add(r1);
					results.add(r2);
					System.out.println("Series Found4");
					return results;
				}else{//nothing is in series.
				
					//do nothing for this loop
				}
			}
		}
		System.out.println(" No Series Found");
		return results; //this will alway be null, if a value appears it will be returned in the double loop
	}
	
	/**
	 * We want to duplicate all the info for the nodes, but not use the same objects themselves.
	 * @param nodes
	 * @return
	 */
	private ArrayList<Node> copyNodes(ArrayList<Node>nodes){
		ArrayList<Node>copyNodes = new ArrayList<Node>();
		for(int i = 0; i < nodes.size(); i++){
			Node oldNode = nodes.get(i);
			Node newNode = new Node(random);
			newNode.setName(oldNode.getName());
			newNode.setConnections(oldNode.getConnections());
			newNode.setVoltage(oldNode.getVoltage());
			copyNodes.add(newNode);
		}
		return copyNodes;
	}
	
	/**
	 * We want to copy all the info for the resistors, but not use the same objects themselves.
	 * @param oldResistors
	 * @return
	 */
	private ArrayList<Resistor> copyResistors(ArrayList<Resistor>oldResistors){
		ArrayList<Resistor>newResistors = new ArrayList<Resistor>();
		for(int i = 0; i < oldResistors.size(); i++){
			Resistor oldResistor = oldResistors.get(i);
			Resistor newResistor = new Resistor(oldResistor.getOhms(), oldResistor.getRandom());
			newResistor.setName(oldResistor.getName());
			newResistor.setAmps(oldResistor.getAmps());
			newResistor.setVoltageDrop(oldResistor.getVoltageDrop());
			newResistor.setWatts(oldResistor.getWatts());
			newResistor.setNodeA(oldResistor.getNodeA());
			newResistor.setNodeB(oldResistor.getNodeB());
			newResistors.add(newResistor);
		}
		return newResistors;
	}
	
	private PowerSupply copyPowerSupply(PowerSupply oldSupply){
		PowerSupply newSupply = new PowerSupply(oldSupply.getVoltage(), oldSupply.getRandom());
		newSupply.setName(oldSupply.getName());
		newSupply.setVoltage(oldSupply.getVoltage());
		newSupply.setPosNode(oldSupply.getPosNode());
		newSupply.setNegNode(oldSupply.getNegNode());
		return newSupply;
	}
	
	private static void removeResistor(Resistor r, ArrayList<Resistor>resistors){
		for(int i = 0; i < resistors.size(); i++){
			Resistor testResistor = resistors.get(i);
			if(testResistor.getName().equals(r.getName())){//testResistor has same name as r, remove it from resistors
				resistors.remove(testResistor);
			}
		}
	}
	
	public void print(){
		System.out.println("C: " + name);
		printComplicatedCircuit();
		printNodes();
		printResistors();
		printPowerSupplies();
		System.out.println();
		System.out.println();
	}
	
	
	
	/* Private Methods */
	private void printComplicatedCircuit(){
		System.out.print("Complicated Circuit: ");
		if(complicatedCircuit != null){
			System.out.format("%8s", complicatedCircuit.getName()+"\n");
		}else{
			System.out.format("%8s", "NULL \n");
		}
		
	}
	
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
			if(r.getNodeA() != null){
				System.out.format("%8s", r.getNodeA().getName()+" ");
			}else{
				System.out.format("%8s", "NULL");
			}
			
			System.out.format("%-2s", "NodeB: ");
			if(r.getNodeB() != null){
				System.out.format("%8s", r.getNodeB().getName()+" ");
			}else{
				System.out.format("%8s", "NULL");
			}
			
			System.out.format("%-2s", "Replacement: ");
			if(r.getReplacement() != null){
				System.out.format("%8s", "A: " + r.getReplacement().a.getName()+" ");
				System.out.format("%8s", "B: " + r.getReplacement().b.getName()+" ");
				System.out.format("%8s", "isSeries?: " + r.getReplacement().isSeries+"\n");
			}else{
				System.out.format("%-2s", "NULL \n");
			}
			
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
