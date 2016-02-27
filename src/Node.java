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
	
	/**
	 * Constructor - Create a new empty node;
	 */
	public Node(){
		name = newName(System.currentTimeMillis());
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
	
	/* Private Methods */
	private String newName(long l){
		Random random = new Random(l);
		return new BigInteger(32, random).toString(32);
	}
}
