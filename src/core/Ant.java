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

            if (siblings.isEmpty()) {
                goBack();
            }

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

    public void setGoalNode(Node node) {
        targetNode = node;
        path = new Stack<>();
    }

    public ArrayList<Node> getValidRoutes() {
        ArrayList<Node> res = new ArrayList<>();

        ArrayList<Arc> siblings = currentNode.siblings;

        for (Arc arc : siblings) {
            if (arc.nodeA != currentNode) {
                res.add(arc.nodeB);
            }

            if (arc.nodeB != currentNode) {
                res.add(arc.nodeB);
            }
        }

        res.removeAll(path);
        res.removeAll(blacklistNodes);
        return res;
    }

    public void goBack() {
        Node badNode = path.pop();
        blacklistNodes.add(badNode);
        currentNode = path.pop();
    }
}
