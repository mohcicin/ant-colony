package core;

import java.util.ArrayList;

public class Node {
	public String name;
	public ArrayList<Arc> siblings;
	public int x, y;

	public Node(String name, int x, int y) {
		this.name = name;

		this.x = x;
		this.y = y;

		siblings = new ArrayList<>();
	}

	/// Ajouter un lien (bidirectionnel)
	public void addSibling(Arc sibling) {
		if (!siblings.contains(sibling)) {
			siblings.add(sibling);
			sibling.nodeB.addSibling(sibling);
		}
	}

	/// Supprimer un lien (bidirectionnel)
	public void removeSibling(Arc sibling) {
		if (siblings.contains(sibling)) {
			siblings.remove(sibling);
			sibling.nodeB.removeSibling(sibling);
		}
	}

	/// Récupérer le lien entre le noeud actuel et un autre
	public Arc getArcTo(Node testNode) {
		for (Arc sibling : siblings) {
			if (sibling.nodeA == testNode || sibling.nodeB == testNode)
				return sibling;
		}

		return null;
	}

	@Override
	public String toString() {
		return name;
	}
}
