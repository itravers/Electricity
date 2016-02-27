import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

/**
 * Nodes connect power supplies and resistors together and allow
 * the power to move through them, like the force.
 * A node has a list of connections, and a voltage.
 * @author Isaac Assegai
 *
 */
public class Node {
	/* Member Variables. */
	private String name; //The name of this node.
	private Double voltage; //The voltage of this node, or null if not known.
	private ArrayList<Element>connections; //A list of the names of r's, or ps's this node is connected to.
	Random random;
	
	/**
	 * Constructor - Create a new empty node;
	 */
	public Node(Random random){
		this.random = random;
		name = newName(random);
		voltage = null;
		connections = new ArrayList<Element>();
	}
	
	/* Public Methods. */
	public void addConnection(Element e){
		connections.add(e);
	}
	
	public boolean isExtraOrdinary(){
		if(connections.size() > 2){
			return true;
		}else{
			return false;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<Element>getConnections(){
		return connections;
	}
	
	/**
	 * This is the second step to removing a resistor, the first step is completed in circuit.java, and calls this step[
	 * @param e
	 */
	public void removeConnection(Element e){
		for(int i = 0; i < connections.size(); i++){
			Element testElement = connections.get(i);
			if(testElement.getName().equals(e.getName())){
				connections.remove(testElement);
			}
		}
	}
	
	public Double getVoltage(){
		return voltage;
	}
	
	public void setVoltage(Double v){
		voltage = v;
	}
	
	/* Private Methods */
	private String newName(Random random){
		return new BigInteger(16, random).toString(16);
	}

	public void setName(String name2) {
		name = name2;
	}

	/**
	 * Copy's a list of connections to this nodes connections
	 * they are copies.
	 * @param oldConnections
	 */
	public void setConnections(ArrayList<Element> oldConnections) {
		connections = new ArrayList<Element>();
		for(int i = 0; i < oldConnections.size(); i++){
			Element oldConnection = oldConnections.get(i);
			if(oldConnection instanceof Resistor){
				Resistor r = new Resistor(((Resistor) oldConnection).getOhms(), oldConnection.getRandom());
				r.setName(oldConnection.getName());
				r.setAmps(((Resistor) oldConnection).getAmps());
				r.setWatts(((Resistor) oldConnection).getWatts());
				r.setVoltageDrop(((Resistor) oldConnection).getVoltageDrop());
				r.setNodeA(((Resistor) oldConnection).getNodeA());
				r.setNodeB(((Resistor) oldConnection).getNodeB());
				connections.add(r);
			}else if(oldConnection instanceof PowerSupply){
				PowerSupply s = new PowerSupply(((PowerSupply) oldConnection).getVoltage(), oldConnection.getRandom());
				s.setName(oldConnection.getName());
				s.setPosNode(((PowerSupply) oldConnection).getPosNode());
				s.setNegNode(((PowerSupply) oldConnection).getNegNode());
				connections.add(s);
			}
		}
	}

	
}
