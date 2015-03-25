package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Stack;

public class Ant extends Thread {
	public Node initialNode; // noeud de départ (nid)
	public Node finalNode; // noeud d'arrivé (source de nourriture)
	public Node currentNode; // noeud courant
	public Node targetNode; // noeud cible (noeud de départ ou d'arrivé)
	public Stack<Node> path; // chemin parcouru (seulement dans le sens départ -> arrivé)
	public ArrayList<Node> blacklistNodes; // noeuds à éviter

	public Ant(Node initialNode, Node targetNode) {
		this.initialNode = initialNode;
		this.targetNode = targetNode;

		currentNode = initialNode;
		finalNode = targetNode;

		path = new Stack<>();
		blacklistNodes = new ArrayList<>();
	}

	/// Comportement de la fourmi :
	/// - voir les noeuds adjacents
	/// - avancer vers le meilleur noeud (meilleur coefficient ; avec part de hasard)
	/// - faire demi-tour si aucun n'est disponible (boucle ou impasse)
	/// - une fois arrivée à la cible, faire le chemin inverse
	/// - recommencer
	@Override
	public void run() {
		ArrayList<Node> adjacentNodes;

		path.add(currentNode);

		while (true) {
			while (true) {
				try {
					sleep((int) (Math.random() * 300) + 200);
				} catch (InterruptedException e) {
				}

				// Récupérer les noeuds proches
				adjacentNodes = getCorrectAdjacentNodes();

				// Avancer ou faire demi-tour (boucle ou impasse)
				if (adjacentNodes.isEmpty())
					currentNode = goBack();
				else
					currentNode = goNode(adjacentNodes);

				System.out.println("Arrivé au noeud " + currentNode);

				// Arrivée au noeud cible
				if (currentNode == targetNode) {
					// Faire le chemin du retour
					if (targetNode == initialNode)
						setTargetNode(finalNode);
					// Aller vers la source de nourriture
					else {
						setTargetNode(initialNode);
						path.pop();
					}

					break;
				}
			}

			System.out.println("[BORD] Arrivé au noeud " + currentNode);
		}
	}

	/// Changement du noeud cible
	public void setTargetNode(Node node) {
		targetNode = node;
		blacklistNodes = new ArrayList<>();

		if (node == finalNode)
			path.add(currentNode);
	}

	/// Retourner au noeud précédent (boucle ou impasse)
	public Node goBack() {
		Node badNode = path.pop();
		blacklistNodes.add(badNode);

		return path.pop();
	}

	/// Se rendre sur un noeud parmi la liste des noeuds adjacents (liés)
	public Node goNode(ArrayList<Node> adjacentNodes) {
		// Dans le cas du chemin de retour, la fourmi ne faire que
		// parcourir le chemin de l'aller
		if (targetNode == initialNode)
			return path.pop();

		Arc arc;
		HashMap<Node, Float> coefficients = new HashMap<>();

		// Calcul du coefficient pour chacun des noeuds adjacents
		for (Node adjacentNode : adjacentNodes) {
			arc = adjacentNode.getArcTo(currentNode);
			float pheromone = arc.pheromone;
			float coefficient = (float) (Math.pow(pheromone, Config.ALPHA) * Math.pow(arc.cost, Config.BETA));
			coefficients.put(adjacentNode, coefficient);
		}

		// Calcul du total des coefficients
		float total = 0;

		for (Entry<Node, Float> e : coefficients.entrySet())
			total += e.getValue();

		// Normalisation des coefficients (sur 100)
		for (Entry<Node, Float> e : coefficients.entrySet())
			e.setValue(e.getValue() * 100 / total);

		// Sélection d'un noeud au hasard (en prenant en compte les coefficients)
		Node nextNode = null;

		Stack<Node> listNodes = new Stack<>();

		for (Entry<Node, Float> e : coefficients.entrySet()) {
			int coefficient = (int) Math.round(e.getValue());

			for (int i = 0; i < coefficient; i++)
				listNodes.add(e.getKey());
		}

		int i = (int) (Math.random() * listNodes.size());
		nextNode = listNodes.get(i);

		// Déplacement vers le noeud sélectionné avec pose de phéromone
		arc = nextNode.getArcTo(currentNode);
		dropPheromone(arc);
		path.add(nextNode);
		return nextNode;
	}

	/// Récupération des noeuds adjacents
	public ArrayList<Node> getAdjacentNodes() {
		ArrayList<Node> adjacentNodes = new ArrayList<>();

		ArrayList<Arc> arcs = currentNode.siblings;

		for (Arc arc : arcs) {
			if (currentNode != arc.nodeA)
				adjacentNodes.add(arc.nodeA);

			if (currentNode != arc.nodeB)
				adjacentNodes.add(arc.nodeB);
		}

		return adjacentNodes;
	}

	/// Récupération des noeuds adjacents
	/// n'étant ni dans le chmin parcouru
	/// ni dans la liste noire
	public ArrayList<Node> getCorrectAdjacentNodes() {
		ArrayList<Node> adjacentNodes = getAdjacentNodes();

		adjacentNodes.removeAll(path);
		adjacentNodes.removeAll(blacklistNodes);

		return adjacentNodes;
	}

	/// Déposer des phéromone sur l'arc indiqué
	public void dropPheromone(Arc arc) {
		arc.dropPheromone(Config.Q / arc.cost);
	}
}
