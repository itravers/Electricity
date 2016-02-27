import java.util.ArrayList;

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
	ArrayList<Circuit>circuits; // In order to simplify circuits we will have to remember the previous ones.
	ArrayList<Node>nodes; // A List of all the nodes in the circuit.
	ArrayList<Resistor>resistors; // An list of all the resistors in the circuit.
}
