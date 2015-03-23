package core;

import ui.Window;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		/*
		   F
		 3/ \1
		 D   E
		 4\ /1
		   C
		 1/ \5
		 B   A
		 1\ /5
		   N
		 */

		// Création des noeuds
		Node nodeF = new Node("F");
		Node nodeE = new Node("E");
		Node nodeD = new Node("D");
		Node nodeC = new Node("C");
		Node nodeA = new Node("A");
		Node nodeB = new Node("B");
		Node nodeN = new Node("N");

		final ArrayList<Node> nodes = new ArrayList<>();
		nodes.add(nodeF);
		nodes.add(nodeE);
		nodes.add(nodeD);
		nodes.add(nodeC);
		nodes.add(nodeA);
		nodes.add(nodeB);
		nodes.add(nodeN);

		// Création des arcs
		Arc arc1 = new Arc(nodeF, nodeE, 1);
		nodeF.addSibling(arc1);

		Arc arc2 = new Arc(nodeF, nodeD, 3);
		nodeF.addSibling(arc2);

		Arc arc3 = new Arc(nodeE, nodeC, 1);
		nodeE.addSibling(arc3);

		Arc arc4 = new Arc(nodeD, nodeC, 4);
		nodeD.addSibling(arc4);

		Arc arc5 = new Arc(nodeC, nodeA, 5);
		nodeC.addSibling(arc5);

		Arc arc6 = new Arc(nodeC, nodeB, 1);
		nodeC.addSibling(arc6);

		Arc arc7 = new Arc(nodeA, nodeN, 5);
		nodeA.addSibling(arc7);

		Arc arc8 = new Arc(nodeB, nodeN, 1);
		nodeB.addSibling(arc8);

		final ArrayList<Arc> arcs = new ArrayList<>();
		arcs.add(arc1);
		arcs.add(arc2);
		arcs.add(arc3);
		arcs.add(arc4);
		arcs.add(arc5);
		arcs.add(arc6);
		arcs.add(arc7);
		arcs.add(arc8);

		// Création des fourmis
		final ArrayList<Ant> ants = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			Ant ant = new Ant(nodeN, nodeF);
			ant.start();
			ants.add(ant);
		}

		// Evaporation toutes les 10 secondes
		new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						sleep(10000);
					} catch (InterruptedException e) {
					}

					for (Arc arc : arcs)
						arc.evaporationPheromone();
				}
			}
		}.start();

		// Affichage graphique sommaire
		final Window win = new Window(nodes, arcs, ants);
	}
}
