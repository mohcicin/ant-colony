package core;

import java.util.ArrayList;
import java.util.Random;

public class Ant extends Thread {

    public Node initialNode;
    public Node finalNode;
    public Node currentNode;
    public Node goalNode;
    public ArrayList<Node> path;

    public Ant(Node initialNode, Node goalNode) {
        this.initialNode = initialNode;
        this.currentNode = initialNode;

        this.goalNode = goalNode;
        this.finalNode = goalNode;

        this.path = new ArrayList<>();
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

            do {
                Arc arc = null;

                arc = siblings.get(new Random().nextInt(siblings.size()));

                if (arc.nodeA != currentNode) {
                    nextNode = arc.nodeA;
                } else {
                    nextNode = arc.nodeB;
                }

                if (path.contains(nextNode)) {
                    nextNode.deadEnd = true;
                }

                if (nextNode.deadEnd) {
                    nextNode = path.remove(path.size() - 1);
                }

                if (Math.random() > 0.5) {
                    nextNode = arc.nodeA;
                } else {
                    nextNode = arc.nodeB;
                }

                //System.out.println("non : " + nextNode);
                if (nextNode == goalNode && goalNode != initialNode) {
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

    public void setGoalNode(Node node) {
        goalNode = node;
        path = new ArrayList<>();
    }
}
