package ui;

import javax.swing.JFrame;
import core.Node;
import core.Arc;
import core.Ant;
import java.util.ArrayList;

public class Window extends JFrame {
	private final Viewer viewer;

	public Window(ArrayList<Node> nodes, ArrayList<Arc> arcs, ArrayList<Ant> ants) {
		viewer = new Viewer(nodes, arcs, ants);
		this.setTitle("AntColony");
		this.setSize(Config.WIDTH, Config.HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setContentPane(viewer);
		this.setVisible(true);

		run();
	}

	private void run() {
		while (true) {
			viewer.repaint();

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
	}
}
