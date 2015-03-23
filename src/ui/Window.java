package ui;

import javax.swing.JFrame;
import core.Node;
import core.Arc;
import core.Ant;
import java.util.ArrayList;

public class Window extends JFrame {
    private Viewer pan;

    public Window(ArrayList<Node> nodes, ArrayList<Arc> arcs, ArrayList<Ant> ants) {
        pan = new Viewer(nodes, arcs, ants);
        this.setTitle("Animation");
        this.setSize(Config.WIDTH, Config.HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setContentPane(pan);
        this.setVisible(true);
        run();
    }

    private void run() {
        while (true) {
            pan.repaint();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) { }
        }
    }
}
