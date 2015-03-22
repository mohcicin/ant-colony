package core;

import java.util.ArrayList;

public class Node {

	public String name;
	public int pheromone;
	public ArrayList<Arc> siblings;
	public boolean deadEnd;

	public Node(String name) {
		this.name = name;
		this.pheromone = 0;
		this.siblings = new ArrayList<>();
		this.deadEnd = false;
	}

	public void addSibling(Arc sibling) {
		if (!this.siblings.contains(sibling)) {
			this.siblings.add(sibling);
			sibling.nodeB.addSibling(sibling);
		}
	}

	public void removeSibling(Arc sibling) {
		if (this.siblings.contains(sibling)) {
			this.siblings.remove(sibling);
			sibling.nodeB.removeSibling(sibling);
		}
	}

	@Override
	public String toString() {
		return name + " (" + String.valueOf(pheromone) + ")";
	}
}
