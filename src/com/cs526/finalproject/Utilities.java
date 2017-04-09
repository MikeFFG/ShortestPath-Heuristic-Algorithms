package com.cs526.finalproject;

import java.util.List;

/**
 * Created by mburke on 4/8/17.
 */
public class Utilities {

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

    /**
     * Prints a list of nodes.
     * @param neighbors - the list of neighbors to print
     */
    public static void printAdjacentNodes(List<Vertex> neighbors,
                                          List<Vertex> blackList,
                                          List<Vertex> history) {
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
}
