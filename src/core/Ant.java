package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Stack;

public class Ant extends Thread {
	public Node initialNode;
	public Node finalNode;
	public Node currentNode;
	public Node targetNode;
	public Stack<Node> path;
	public ArrayList<Node> blacklistNodes;

	public Ant(Node initialNode, Node targetNode) {
		this.initialNode = initialNode;
		this.targetNode = targetNode;

		currentNode = initialNode;
		finalNode = targetNode;

		path = new Stack<>();
		blacklistNodes = new ArrayList<>();
	}

	// Comportement de la fourmi :
	// - voir les noeuds adjacents
	// - avancer vers le meilleur noeud (meilleur coefficient ; avec part de hasard)
	// - faire demi-tour si aucun n'est disponible (boucle ou impasse)
	// - une fois arrivée à la cible, faire le chemin inverse
	// - recommencer
	@Override
	public void run() {
		ArrayList<Node> adjacentNodes;

		while (true) {
			try {
				sleep(500);
			} catch (InterruptedException e) {
			}

			path.add(currentNode);

			while (true) {
				adjacentNodes = getCorrectAdjacentNodes();

				if (adjacentNodes.isEmpty())
					currentNode = goBack();
				else
					currentNode = goNode(adjacentNodes);

				System.out.println("Arrivé au noeud " + currentNode);

				if (currentNode == targetNode) {
					if (targetNode == initialNode)
						setTargetNode(finalNode);
					else
						setTargetNode(initialNode);

					break;
				}
			}

			System.out.println("[BORD] Arrivé au noeud " + currentNode);
		}
	}

	// Changement du noeud cible
	public void setTargetNode(Node node) {
		targetNode = node;
		path = new Stack<>();
		blacklistNodes = new ArrayList<>();
	}

	// Retourner au noeud précédent (boucle ou impasse)
	public Node goBack() {
		Node badNode = path.pop();
		blacklistNodes.add(badNode);

		return path.pop();
	}

	// Aller à un noeud parmi la liste des noeuds adjacents (liés)
	public Node goNode(ArrayList<Node> adjacentNodes) {
		Arc arc;
		HashMap<Node, Float> coefficients = new HashMap<>();

		// Calcul du coefficient pour chaque noeud adjacent
		for (Node adjacentNode : adjacentNodes) {
			arc = adjacentNode.getArcTo(currentNode);
			float pheromone = arc.pheromone;
			float coefficient = (float) (Math.pow(pheromone, Config.BETA) * Math.pow(arc.cost, Config.ALPHA));
			coefficients.put(adjacentNode, coefficient);
		}

		// Calcul du total des coefficients
		float total = 0;

		for (Entry<Node, Float> e : coefficients.entrySet())
			total += e.getValue();

		// Normalisation des coefficients
		for (Entry<Node, Float> e : coefficients.entrySet())
			e.setValue(e.getValue() * 100 / total);

		// Sélection d'un noeud au hasard (en prenant en compte les coefficients)
		Node nextNode = null;

		Stack<Node> randomNodes = new Stack<>();

		for (Entry<Node, Float> e : coefficients.entrySet()) {
			int coefficient = (int) Math.floor(e.getValue());

			for (int i = 0; i < coefficient; i++)
				randomNodes.add(e.getKey());
		}

		Collections.shuffle(randomNodes);
		int i = (int) (Math.random() * randomNodes.size());
		nextNode = randomNodes.get(i);

		// Déplacement vers le noeud sélectionné avec pose de phéromone
		arc = nextNode.getArcTo(currentNode);
		arc.dropPheromone(Config.Q / arc.cost);
		path.add(nextNode);
		return nextNode;
	}

	// Récupération des noeuds adjacents
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

	// Récupération des noeuds adjacents
	// n'étant ni dans le chmin parcouru
	// ni dans la liste noire
	public ArrayList<Node> getCorrectAdjacentNodes() {
		ArrayList<Node> adjacentNodes = getAdjacentNodes();

		adjacentNodes.removeAll(path);
		adjacentNodes.removeAll(blacklistNodes);

		return adjacentNodes;
	}
}
