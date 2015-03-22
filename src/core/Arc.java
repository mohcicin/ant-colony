package core;

public class Arc {
	public Node nodeA;
	public Node nodeB;

	public float cost;
	public float pheromone;

	public Arc(Node nodeA, Node nodeB, float cost) {
		this.nodeA = nodeA;
		this.nodeB = nodeB;

		this.cost = cost;
		this.pheromone = 1;
	}

	// Déposer des phéromones
	public void dropPheromone(float pheromone) {
		this.pheromone += pheromone;
	}

	// Évaporation des phéromones
	public void evaporationPheromone() {
		pheromone = (1 - Config.P) * pheromone;
	}

	@Override
	public String toString() {
		return nodeA + " <-> " + nodeB + " : cout : " + cost + ", phéromone : " + pheromone;
	}
}
