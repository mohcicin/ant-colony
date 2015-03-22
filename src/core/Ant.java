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
		boolean running = true;

		while (running) {
			try {
				sleep(100);
			} catch (InterruptedException e) {
			}

			path.add(currentNode);
			currentNode.pheromone++;

			ArrayList<Arc> siblings = currentNode.siblings;

			Node nextNode = null;

			boolean runDo = true;
			
			do {
				ArrayList<Node> adjacentsNodes = getCorrectAdjacentNodes();

				if (adjacentsNodes.isEmpty()) {
					nextNode = goBack();
				} else {
					nextNode = adjacentsNodes.get(new Random().nextInt(adjacentsNodes.size()));
					currentNode = nextNode;
					path.add(currentNode);
				}

				//System.out.println("non : " + nextNode);
				if (nextNode == targetNode && targetNode != initialNode) {
					//setGoalNode(initialNode);
					running = false;

					System.out.println("== ARRIVE ==");

					break;
				}
			} while (runDo);

			System.out.println("ok : " + currentNode);
		}
	}

	public void setTargetNode(Node node) {
		targetNode = node;
		path = new Stack<>();
	}

	public Node goBack() {
		Node badNode = path.pop();
		blacklistNodes.add(badNode);
		return path.pop();
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
