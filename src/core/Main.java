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
		// La position ne sert que pour l'affichage graphique
		Node nodeF = new Node("F", 200, 50);
		Node nodeE = new Node("E", 250, 100);
		Node nodeD = new Node("D", 150, 100);
		Node nodeC = new Node("C", 200, 150);
		Node nodeA = new Node("A", 250, 200);
		Node nodeB = new Node("B", 150, 200);
		Node nodeN = new Node("N", 200, 250);

		final ArrayList<Node> nodes = new ArrayList<>();
		nodes.add(nodeF);
		nodes.add(nodeE);
		nodes.add(nodeD);
		nodes.add(nodeC);
		nodes.add(nodeA);
		nodes.add(nodeB);
		nodes.add(nodeN);

		// Création des arcs
		// Le coût a été inversé afin que la formule fonctionne correctement
		// en faisant 6 - n
		Arc arc1 = new Arc(nodeF, nodeE, 5);
		nodeF.addSibling(arc1);

		Arc arc2 = new Arc(nodeF, nodeD, 3);
		nodeF.addSibling(arc2);

		Arc arc3 = new Arc(nodeE, nodeC, 5);
		nodeE.addSibling(arc3);

		Arc arc4 = new Arc(nodeD, nodeC, 2);
		nodeD.addSibling(arc4);

		Arc arc5 = new Arc(nodeC, nodeA, 1);
		nodeC.addSibling(arc5);

		Arc arc6 = new Arc(nodeC, nodeB, 5);
		nodeC.addSibling(arc6);

		Arc arc7 = new Arc(nodeA, nodeN, 1);
		nodeA.addSibling(arc7);

		Arc arc8 = new Arc(nodeB, nodeN, 5);
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

		// Évaporation toutes les 10 secondes
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

					System.out.println("[Évaporation des phéromones]");
				}
			}
		}.start();

		// Affichage d'une interface graphique sommaire
		new Window(nodes, arcs, ants);
	}
}
