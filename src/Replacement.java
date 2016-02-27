/* Private Classes */
	public class Replacement{
		public Resistor a;
		public Resistor b;
		public Node node; //A replacement has a node in it, if it is a series replacement, there is a node between the two resistors
		public boolean isSeries;
		
		public Replacement(Resistor a, Resistor b, boolean isSeries){
			this.a = a;
			this.b = b;
			this.node = null;
			this.isSeries = isSeries;
		}
		
		public Replacement(Resistor a, Resistor b, Node n, boolean isSeries){
			this.a = a;
			this.b = b;
			this.node = n;
			this.isSeries = isSeries;
		}
		
	}