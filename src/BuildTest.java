import java.util.ArrayList;
import java.util.Random;

public class BuildTest {
	public static void main(String [] args){
		Random r = new Random(System.currentTimeMillis());
		Circuit c1 = new Circuit();
		PowerSupply s = new PowerSupply(new Double(12), r);
		c1.addSupply(s);
		
		Node n1 = new Node(r);
		n1.addConnection(s);
		s.connectPosNode(n1);
		c1.addNode(n1);
		
		Resistor r1 = new Resistor(new Double(100), r);
		r1.setNodeA(n1);
		n1.addConnection(r1);
		c1.addResistor(r1);
		
		Node n2 = new Node(r);
		n2.addConnection(r1);
		r1.setNodeB(n2);
		c1.addNode(n2);
		
		Resistor r2 = new Resistor(new Double(50), r);
		r2.setNodeA(n2);
		n2.addConnection(r2);
		c1.addResistor(r2);
		
		Resistor r3 = new Resistor(new Double(150), r);
		r3.setNodeA(n2);
		n2.addConnection(r3);
		c1.addResistor(r3);
		
		Node n3 = new Node(r);
		n3.addConnection(s);
		s.connectNegNode(n3);
		n3.addConnection(r2);
		r2.setNodeB(n3);
		n3.addConnection(r3);
		r3.setNodeB(n3);
		c1.addNode(n3);
		
		ArrayList<Resistor>parallel = c1.getParallelResistors();
		ArrayList<Resistor>series = c1.getSeriesResistors(); //this should return null at this point
		c1.print();
		System.out.println(c1);
		System.out.println("Test");
		
	}
}
