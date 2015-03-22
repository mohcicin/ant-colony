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

			/*
			 if (siblings.isEmpty()) {
			 goBack();
			 }
			 */
			Node nextNode = null;
			Arc arc = null;
			ArrayList<Node> adjacentsNodes = getCorrectAdjacentNodes();

			do {
				arc = siblings.get(new Random().nextInt(siblings.size()));

				/*
				 if (arc.nodeA != currentNode)
				 nextNode = arc.nodeA;
				 else
				 nextNode = arc.nodeB;

				 nextNode.deadEnd = true;

				 if (nextNode.deadEnd)
				 nextNode = path.remove(path.size() - 1);

				 if (Math.random() > 0.5)
				 nextNode = arc.nodeA;
				 else
				 nextNode = arc.nodeB;
				 */
				if (adjacentsNodes.isEmpty()) {
					goBack();
				} else {
					nextNode = adjacentsNodes.get(new Random().nextInt(adjacentsNodes.size()));
				}

				//System.out.println("non : " + nextNode);
				if (nextNode == targetNode && targetNode != initialNode) {
					//setGoalNode(initialNode);
					running = false;

					System.out.println("== ARRIVE ==");

					break;
				}
			} while (path.contains(nextNode));

			currentNode = nextNode;

			System.out.println("ok : " + currentNode);
		}
	}

	public void setTargetNode(Node node) {
		targetNode = node;
		path = new Stack<>();
	}

	public void goBack() {
		Node badNode = path.pop();
		blacklistNodes.add(badNode);
		currentNode = path.pop();
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

		return adjacentNodes;
	}
}
