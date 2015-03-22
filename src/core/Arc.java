package core;

public class Arc {

	public Node nodeA;
	public Node nodeB;

	public float cost;

	public Arc(Node nodeA, Node nodeB, int cost) {
		this.nodeA = nodeA;
		this.nodeB = nodeB;

		this.cost = cost;
	}

	@Override
	public String toString() {
		return nodeA + " <-> " + nodeB + ", Cout : " + cost;
	}
}
