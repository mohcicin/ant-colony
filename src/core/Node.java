package core;

import java.util.ArrayList;

public class Node {

	public String name;
	public float pheromone;
	public ArrayList<Arc> siblings;

	public Node(String name) {
		this.name = name;

		pheromone = 1;
		siblings = new ArrayList<>();
	}

	public void addSibling(Arc sibling) {
		if (!siblings.contains(sibling)) {
			siblings.add(sibling);
			sibling.nodeB.addSibling(sibling);
		}
	}

	public void removeSibling(Arc sibling) {
		if (siblings.contains(sibling)) {
			siblings.remove(sibling);
			sibling.nodeB.removeSibling(sibling);
		}
	}

	public Arc getArcTo(Node testNode) {
		for (Arc sibling : siblings) {
			if (sibling.nodeA == testNode || sibling.nodeB == testNode)
				return sibling;
		}

		return null;
	}
	
	public void dropPheromone(float pheromone) {
		this.pheromone += pheromone;
	}

	@Override
	public String toString() {
		return name + " (" + String.valueOf(pheromone) + ")";
	}
}
