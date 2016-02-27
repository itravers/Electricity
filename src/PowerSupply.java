import java.math.BigInteger;
import java.util.Random;

/**
 * A power supply supplies power to a circuit.
 * It has a name, voltage, +node and -node.
 * @author Isaac Assegai
 *
 */
public class PowerSupply {
	String name;
	Double voltage;
	String posNode;
	String negNode;
	
	/**
	 * Create a new unconnected power supply.
	 */
	public PowerSupply(){
		name = newName(System.currentTimeMillis());
		voltage = null;
		posNode = null;
		negNode = null;
	}
	
	/* public methods. */
	public void connectPosNode(String n){
		posNode = n;
	}
	
	public void connectNegNode(String n){
		negNode = n;
	}
	
	
	/* Private Methods */
	private String newName(long l){
		Random random = new Random(l);
		return new BigInteger(130, random).toString(32);
	}
}
