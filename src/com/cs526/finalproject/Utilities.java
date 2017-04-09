package com.cs526.finalproject;

import java.util.List;

/**
 * Created by mburke on 4/8/17.
 */
public class Utilities {

    public static void printBacktrack(List<Vertex> history) {
        System.out.println("\tDead end.");
        System.out.println("\tBacktrack to " + history.get(history.size() - 2).getName());
    }

    public static void printEndings(Vertex node, List<Vertex> history) {
        // Logic for whether we are at the end or not.
        if (node.equals(heuristicShortestPath.END_NODE)) {
            printPath(history);
            printPathLength(history);
        } else {
            printSectionEnd(history, node);
        }
    }

    /**
     * Searches a list for a specific node
     * @param list - the list to search
     * @param node - the node to find
     * @return - true if we find the node. False if we don't find it.
     */
    public static boolean nodeIsInList(List<Vertex> list, Vertex node) {
        for (Vertex v : list) {
            if (v.equals(node)) {
                return true;
            }
        }
        return false;
    }
    /* Printing utilities */
    /**
     * Prints the output of a node for the DD algorithm
     * @param v - the node to print
     */
    public static StringBuilder getSingleNodeDDString(Vertex v,
                                                      List<Vertex> blackList,
                                                      List<Vertex> history) {
        StringBuilder output = new StringBuilder();
        if (nodeIsInList(history, v)) {
            output.append("\n\t").append(v.getName()).append(" is already in the path.");
        } else {
            return output.append("\n\t").append(v.getName()).
                    append(": dd(").append(v.getName()).
                    append(") = ").append(v.getDirectDistanceToZ());
        }
        return output;
    }

    public static StringBuilder getSingleNodeCombinedString(Vertex currentNode,
                                                            Vertex neighborNode,
                                                            List<Vertex> blackList,
                                                            List<Vertex> history) {
        StringBuilder output = new StringBuilder();
        if (nodeIsInList(history, neighborNode)) {
            output.append("\n\t").append(neighborNode.getName()).append(" is already in the path.");
        } else {
            return output.append("\n\t").append(neighborNode.getName()).
                    append(": w(").append(currentNode.getName()).append(", ").
                    append(neighborNode.getName()).append(") + dd(").append(neighborNode.getName()).
                    append(") = ").append(neighborNode.getCombinedWeight(currentNode));
        }
        return output;
    }

    public static void printCurrentNode(Vertex node, boolean isBackTrackCycle) {
        if (isBackTrackCycle == true) { return; } // Guard clause if we are in a back track cycle
        System.out.println("\tCurrent Node = " + node.getName());

    }
    /**
     * Prints a list of nodes.
     * @param neighbors - the list of neighbors to print
     */
    public static void printAdjacentNodes(List<Vertex> neighbors,
                                          List<Vertex> blackList,
                                          List<Vertex> history,
                                          boolean isBackTrackCycle) {
        if (isBackTrackCycle == true) { return; } // Guard clause if we are in a back track cycle

        System.out.print("\tAdjacent nodes: ");
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < neighbors.size(); i++ ) {
            if (i == neighbors.size() - 1) {
                System.out.print(neighbors.get(i).getName());
                output.append(getSingleNodeDDString(neighbors.get(i), blackList, history));
            } else {
                System.out.print(neighbors.get(i).getName() + ", ");
                output.append(getSingleNodeDDString(neighbors.get(i), blackList, history));
            }
        }
        System.out.println(output);
    }

    public static void printAdjacentNodes(List<Vertex> neighbors,
                                          List<Vertex> blackList,
                                          List<Vertex> history,
                                          boolean isBackTrackCycle,
                                          Vertex currentNode) {
        if (isBackTrackCycle == true) {
            return;
        } // Guard clause if we are in a back track cycle

        System.out.print("\tAdjacent nodes: ");
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < neighbors.size(); i++) {
            if (i == neighbors.size() - 1) {
                System.out.print(neighbors.get(i).getName());
                output.append(getSingleNodeCombinedString(currentNode, neighbors.get(i), blackList, history));
            } else {
                System.out.print(neighbors.get(i).getName() + ", ");
                output.append(getSingleNodeCombinedString(currentNode, neighbors.get(i), blackList, history));
            }
        }
        System.out.println(output);
    }

    public static void printSectionEnd(List<Vertex> history, Vertex currentNode) {
        System.out.println("\t" + currentNode.getName() + " is selected");
        printPath(history);
        System.out.println("\n");
    }

    public static void printPath(List<Vertex> history) {
        System.out.print("\tShortest Path = ");
        for (int i = 0; i < history.size(); i++) {
            System.out.print(history.get(i).getName());
            if (i != history.size() - 1) { System.out.print(" → "); }
        }
    }

    public static void printPathLength(List<Vertex> history) {
        System.out.print("\n\tShortest path length = ");
        int total = 0;
        for (int i = 0; i < history.size(); i++) {
            if (i != 0) {
                int weight = history.get(i - 1).findEdge(history.get(i).getName()).getWeight();
                total += weight;
                System.out.print(weight);
            }
            if (i != history.size() - 1 && i != 0) {
                System.out.print(" + ");
            }
        }
        System.out.print(" = " + total);
    }

    public static void printPathLengthWDD(List<Vertex> history) {
        System.out.print("\n\tShortest path length = ");
        int total = 0;
        for (int i = 0; i < history.size(); i++) {
            if (i != 0) {
                int weight = history.get(i - 1).getCombinedWeight(history.get(i));
                total += weight;
                System.out.print(weight);
            }
            if (i != history.size() - 1 && i != 0) {
                System.out.print(" + ");
            }
        }
        System.out.print(" = " + total);
    }
}
