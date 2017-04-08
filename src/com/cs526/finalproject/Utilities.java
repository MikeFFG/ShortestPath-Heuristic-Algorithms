package com.cs526.finalproject;

import java.util.List;

/**
 * Created by mburke on 4/8/17.
 */
public class Utilities {
    /* Printing utilities */
    /**
     * Prints the output of a node for the DD algorithm
     * @param v - the node to print
     */
    public static void printSingleNodeDD(Vertex v) {
        System.out.println("\t" + v.getName() + ": dd(" +
                v.getName() + ") = " +
                v.getDirectDistanceToZ()
        );
    }

    /**
     * Prints a list of nodes.
     * @param neighbors - the list of neighbors to print
     */
    public static void printAdjacentNodes(List<Vertex> neighbors) {
        System.out.print("\tAdjacent nodes: ");
        for (int i = 0; i < neighbors.size(); i++ ) {
            if (i == neighbors.size() - 1) {
                System.out.print(neighbors.get(i).getName());
            } else {
                System.out.print(neighbors.get(i).getName() + ", ");
            }
        }
        System.out.println("");
    }

    public static void printSectionEnd(List<Vertex> history, Vertex currentNode) {
        System.out.println("\t" + currentNode.getName() + " is selected");
        System.out.println("\tShortest path: " +
                history.get(history.size() - 1).getName() +
                " → " + currentNode.getName());
        System.out.println("");
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
