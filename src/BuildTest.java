import java.util.ArrayList;

public class BuildTest {
	public static void main(String [] args){
		Circuit c1 = new Circuit();
		PowerSupply s = new PowerSupply(12);
		c1.addSupply(s);
		
		Node n1 = new Node();
		n1.addConnection(s);
		s.connectPosNode(n1);
		c1.addNode(n1);
		
		Resistor r1 = new Resistor(100);
		r1.setNodeA(n1);
		n1.addConnection(r1);
		c1.addResistor(r1);
		
		Node n2 = new Node();
		n2.addConnection(r1);
		r1.setNodeB(n2);
		c1.addNode(n2);
		
		Resistor r2 = new Resistor(50);
		r2.setNodeA(n2);
		n2.addConnection(r2);
		c1.addResistor(r2);
		
		Resistor r3 = new Resistor(150);
		r3.setNodeA(n2);
		n2.addConnection(r3);
		c1.addResistor(r3);
		
		Node n3 = new Node();
		n3.addConnection(s);
		s.connectNegNode(n3);
		n3.addConnection(r2);
		r2.setNodeB(n3);
		n3.addConnection(r3);
		r3.setNodeB(n3);
		c1.addNode(n3);
		
		ArrayList<Resistor>parallel = c1.getParallelResistors();
		System.out.println(c1);
		System.out.println("Test");
		
	}
}
