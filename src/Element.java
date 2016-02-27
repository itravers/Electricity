import java.math.BigInteger;
import java.util.Random;

/**
 * An Element is an abstract class that doesn't do anything
 * power supplies and resistors are elements, we want to be able to treat them in a single connection
 * list
 * @author Isaac Assegai
 *
 */
public class Element {
	private String name;
	private Random random;
	
	public Element(Random r){
		random = r;
		name = newName();
	}
	
	
	private String newName(){
		return new BigInteger(16, random).toString(16);
	}
	
	public String getName(){
		return name;
	}

}
