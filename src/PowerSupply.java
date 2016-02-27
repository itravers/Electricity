import java.math.BigInteger;
import java.util.Random;

/**
 * A power supply supplies power to a circuit.
 * It has a name, voltage, +node and -node.
 * @author Isaac Assegai
 *
 */
public class PowerSupply extends Element{
	Double voltage;
	Node posNode;
	Node negNode;
	
	/**
	 * Create a new unconnected power supply.
	 */
	public PowerSupply(Double v, Random r){
		super(r);
		voltage = v;
		posNode = null;
		negNode = null;
	}

	/* public methods. */
	public void setPosNode(Node n){
		posNode = n;
	}
	
	public void setNegNode(Node n){
		negNode = n;
	}

	public Double getVoltage() {
		return voltage;
	}
	
	public void setVoltage(Double v){
		voltage = v;
	}

	public Node getPosNode() {
		return posNode;
	}
	
	public Node getNegNode(){
		return negNode;
	}
	
}
