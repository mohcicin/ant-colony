package core;

import java.util.ArrayList;

public class Node {

	public String name;
	public float pheromone;
	public ArrayList<Arc> siblings;
	public boolean deadEnd;

	public Node(String name) {
		this.name = name;

		pheromone = 0;
		siblings = new ArrayList<>();
		deadEnd = false;
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
		Arc arc = null;

		for (Arc sibling : siblings) {
			if (sibling.nodeA == testNode)
				arc = sibling;

			if (sibling.nodeB == testNode)
				arc = sibling;
		}

		return arc;
	}

	@Override
	public String toString() {
		return name + " (" + String.valueOf(pheromone) + ")";
	}
}
