package ui;

import core.Ant;
import core.Arc;
import core.Node;

import java.awt.*;
import javax.swing.JPanel;

import java.util.ArrayList;

/// L'interface graphique a été codée rapidement pour afficher un visuel de ce qui se déroule ;
/// elle est optimisable, ici tous les chiffres sont écrits en brut
public class Viewer extends JPanel {
	ArrayList<Node> nodes;
	ArrayList<Arc> arcs;
	ArrayList<Ant> ants;

	public Viewer(ArrayList<Node> nodes, ArrayList<Arc> arcs, ArrayList<Ant> ants) {
		this.nodes = nodes;
		this.arcs = arcs;
		this.ants = ants;
	}

	@Override
	public void paintComponent(Graphics g) {
		int nodeSize = 40;
		int halfNodeSize = nodeSize / 2;
		int antSize = 6;

		// Fond
		g.setColor(Color.WHITE);
		
		// Noeuds
		g.fillRect(0, 0, Config.WIDTH, Config.HEIGHT);
		g.setColor(Color.BLACK);

		for (Node node : nodes) {
			switch (node.name) {
				case "F":
					g.fillOval(200, 50, nodeSize, nodeSize);
					break;

				case "E":
					g.fillOval(150, 100, nodeSize, nodeSize);
					break;
				case "D":
					g.fillOval(250, 100, nodeSize, nodeSize);
					break;

				case "C":
					g.fillOval(200, 150, nodeSize, nodeSize);
					break;

				case "A":
					g.fillOval(150, 200, nodeSize, nodeSize);
					break;
				case "B":
					g.fillOval(250, 200, nodeSize, nodeSize);
					break;

				case "N":
					g.fillOval(200, 250, nodeSize, nodeSize);
					break;
			}
		}

		// Arcs
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(4));

		for (Arc arc : arcs) {
			switch (arc.nodeA.name + " <-> " + arc.nodeB.name) {
				case "F <-> E":
					g.drawLine(200 + halfNodeSize, 50 + halfNodeSize, 150 + halfNodeSize, 100 + halfNodeSize);
					break;
				case "F <-> D":
					g.drawLine(200 + halfNodeSize, 50 + halfNodeSize, 250 + halfNodeSize, 100 + halfNodeSize);
					break;
				case "E <-> C":
					g.drawLine(150 + halfNodeSize, 100 + halfNodeSize, 200 + halfNodeSize, 150 + halfNodeSize);
					break;
				case "D <-> C":
					g.drawLine(250 + halfNodeSize, 100 + halfNodeSize, 200 + halfNodeSize, 150 + halfNodeSize);
					break;
				case "C <-> A":
					g.drawLine(200 + halfNodeSize, 150 + halfNodeSize, 150 + halfNodeSize, 200 + halfNodeSize);
					break;
				case "C <-> B":
					g.drawLine(200 + halfNodeSize, 150 + halfNodeSize, 250 + halfNodeSize, 200 + halfNodeSize);
					break;
				case "A <-> N":
					g.drawLine(150 + halfNodeSize, 200 + halfNodeSize, 200 + halfNodeSize, 250 + halfNodeSize);
					break;
				case "B <-> N":
					g.drawLine(250 + halfNodeSize, 200 + halfNodeSize, 200 + halfNodeSize, 250 + halfNodeSize);
					break;
			}
		}

		// Fourmis
		g.setColor(Color.orange);

		for (Ant ant : ants) {
			int randX = (int) (Math.random() * nodeSize);
			int randY = (int) (Math.random() * nodeSize);

			switch (ant.currentNode.name) {
				case "F":
					g.fillOval(200 + randX, 50 + randY, antSize, antSize);
					break;

				case "E":
					g.fillOval(150 + randX, 100 + randY, antSize, antSize);
					break;
				case "D":
					g.fillOval(250 + randX, 100 + randY, antSize, antSize);
					break;

				case "C":
					g.fillOval(200 + randX, 150 + randY, antSize, antSize);
					break;

				case "A":
					g.fillOval(150 + randX, 200 + randY, antSize, antSize);
					break;
				case "B":
					g.fillOval(250 + randX, 200 + randY, antSize, antSize);
					break;

				case "N":
					g.fillOval(200 + randX, 250 + randY, antSize, antSize);
					break;
			}
		}
	}
}
