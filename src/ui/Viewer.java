package ui;

import core.Ant;
import core.Arc;
import core.Node;

import java.awt.*;
import javax.swing.JPanel;

import java.util.ArrayList;

/// L'interface graphique a été codée rapidement
/// mais permet de se rendre un peu plus facilement ce qui se déroule
public class Viewer extends JPanel {
	ArrayList<Node> nodes;
	ArrayList<Arc> arcs;
	ArrayList<Ant> ants;

	public Viewer(ArrayList<Node> nodes, ArrayList<Arc> arcs, ArrayList<Ant> ants) {
		this.nodes = nodes;
		this.arcs = arcs;
		this.ants = ants;
	}

	/// Affichage des arcs, noeuds et fourmis
	@Override
	public void paintComponent(Graphics g) {
		int nodeSize = 40;
		int halfNodeSize = nodeSize / 2;
		int antSize = 6;

		// Fond
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Config.WIDTH, Config.HEIGHT);

		// Arcs
		g.setColor(Color.GRAY);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(4));

		for (Arc arc : arcs)
			g.drawLine(
					arc.nodeA.x + halfNodeSize,
					arc.nodeA.y + halfNodeSize,
					arc.nodeB.x + halfNodeSize,
					arc.nodeB.y + halfNodeSize
			);

		// Noeuds
		for (Node node : nodes) {
			g.setColor(Color.BLACK);
			g.fillOval(node.x, node.y, nodeSize, nodeSize);

			g.setColor(Color.WHITE);
			g.drawString(node.name, node.x + halfNodeSize, node.y + halfNodeSize);
		}

		// Fourmis
		g.setColor(Color.orange);

		for (Ant ant : ants) {
			int randX = (int) (Math.random() * nodeSize);
			int randY = (int) (Math.random() * nodeSize);

			g.fillOval(ant.currentNode.x + randX, ant.currentNode.y + randY, antSize, antSize);
		}
	}
}
