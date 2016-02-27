import java.math.BigInteger;
import java.util.Random;

/**
 * A power supply supplies power to a circuit.
 * It has a name, voltage, +node and -node.
 * @author Isaac Assegai
 *
 */
public class PowerSupply extends Element{
	String name;
	Double voltage;
	Node posNode;
	Node negNode;
	
	/**
	 * Create a new unconnected power supply.
	 */
	public PowerSupply(Double v){
		name = newName(System.currentTimeMillis());
		voltage = v;
		posNode = null;
		negNode = null;
	}
	
	public PowerSupply(int i) {
		new PowerSupply((double)i);
	}

	/* public methods. */
	public void connectPosNode(Node n){
		posNode = n;
	}
	
	public void connectNegNode(Node n){
		negNode = n;
	}
	
	
	/* Private Methods */
	private String newName(long l){
		Random random = new Random(l);
		return new BigInteger(32, random).toString(32);
	}
}
