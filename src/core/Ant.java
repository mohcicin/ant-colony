package core;

import java.util.ArrayList;
import java.util.Random;
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
		this.currentNode = initialNode;

		this.targetNode = targetNode;
		this.finalNode = targetNode;

		this.path = new Stack<>();
		this.blacklistNodes = new ArrayList<>();
	}

	@Override
	public void run() {
		while (true) {
			try {
				sleep(500);
			} catch (InterruptedException e) {
			}

			path.add(currentNode);

			while (true) {
				ArrayList<Node> adjacentNodes = getCorrectAdjacentNodes();

				if (adjacentNodes.isEmpty())
					currentNode = goBack();
				else
					currentNode = goNode(adjacentNodes);

				if (currentNode == targetNode) {
					if (targetNode == initialNode)
						setTargetNode(finalNode);
					else
						setTargetNode(initialNode);

					break;
				}
			}

			System.out.println("Arrivé au noeud " + currentNode);
		}
	}

	public void setTargetNode(Node node) {
		targetNode = node;
		path = new Stack<>();
		blacklistNodes = new ArrayList<>();
	}

	public Node goBack() {
		Node badNode = path.pop();
		blacklistNodes.add(badNode);

		return path.pop();
	}

	public Node goNode(ArrayList<Node> adjacentNodes) {
		Node node = adjacentNodes.get(new Random().nextInt(adjacentNodes.size()));
		path.add(node);

		node.pheromone++;

		return node;
	}

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

	public ArrayList<Node> getCorrectAdjacentNodes() {
		ArrayList<Node> adjacentNodes = getAdjacentNodes();

		adjacentNodes.removeAll(path);
		adjacentNodes.removeAll(blacklistNodes);

		/*
		 System.out.println("=================");
		 for (Node node : adjacentNodes) {
		 System.out.println(node);
		 }
		 System.out.println("=================");
		 */
		return adjacentNodes;
	}
}
