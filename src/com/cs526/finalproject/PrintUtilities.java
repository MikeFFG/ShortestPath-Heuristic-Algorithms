package com.cs526.finalproject;

import java.util.List;

/**
 * Class for holding printing methods to clean up heuristicShortestPath
 * Created by mburke on 4/8/17.
 */
public class PrintUtilities {
    /**
     * Prints the info when the path needs to backtrack
     * @param history - the history list
     */
    public static void printBacktrack(List<Vertex> history) {
        System.out.println("\tDead end.");
        System.out.println("\tBacktrack to " + history.get(history.size() - 2).getName());
    }

    /**
     * Prints the endings of the sections and the ending of the
     * algorithm based on whether or not the algorithm is at the
     * end node.
     * @param node - the current node. If it is END_NODe, it will change
     *             the printing logic
     * @param history - the history list
     */
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
     * Prints the output of a node for the DD algorithm
     * @param v - the node to print
     */
    public static StringBuilder getSingleNodeDDString(Vertex v,
                                                      List<Vertex> history) {
        StringBuilder output = new StringBuilder();
        if (heuristicShortestPath.nodeIsInList(history, v)) {
            output.append("\n\t").append(v.getName()).append(" is already in the path.");
        } else {
            return output.append("\n\t").append(v.getName()).
                    append(": dd(").append(v.getName()).
                    append(") = ").append(v.getDirectDistanceToZ());
        }
        return output;
    }

    /**
     * Prints the output of a node for algorithm two
     * @param currentNode - the current node
     * @param neighborNode - the neighbor node
     * @param history - the history list
     * @return - the StringBuilder that needs to be output to the console.
     */
    public static StringBuilder getSingleNodeCombinedString(Vertex currentNode,
                                                            Vertex neighborNode,
                                                            List<Vertex> history) {
        StringBuilder output = new StringBuilder();
        if (heuristicShortestPath.nodeIsInList(history, neighborNode)) {
            output.append("\n\t").append(neighborNode.getName()).append(" is already in the path.");
        } else {
            return output.append("\n\t").append(neighborNode.getName()).
                    append(": w(").append(currentNode.getName()).append(", ").
                    append(neighborNode.getName()).append(") + dd(").append(neighborNode.getName()).
                    append(") = ").append(neighborNode.getCombinedWeight(currentNode));
        }
        return output;
    }

    /**
     * Prints the current node
     * @param node - the node to print
     * @param isBackTrackCycle - the flag that tells whether or not this is a backtrack
     *                         cycle. If it is, we don't print anything.
     */
    public static void printCurrentNode(Vertex node, boolean isBackTrackCycle) {
        if (isBackTrackCycle == true) { return; } // Guard clause if we are in a back track cycle
        System.out.println("\tCurrent Node = " + node.getName());
    }

    /**
     * Prints a list of adjacent nodes
     * @param neighbors - the list of adjacent nodes
     * @param history - the history list
     * @param isBackTrackCycle - a flag that says whether this iteration of the loop
     *                         is a backtrack cycle. If so, nothing is printed.
     */
    public static void printAdjacentNodes(List<Vertex> neighbors,
                                          List<Vertex> history,
                                          boolean isBackTrackCycle) {
        if (isBackTrackCycle == true) { return; } // Guard clause if we are in a back track cycle

        System.out.print("\tAdjacent nodes: ");
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < neighbors.size(); i++ ) {
            if (i == neighbors.size() - 1) {
                System.out.print(neighbors.get(i).getName());
                output.append(getSingleNodeDDString(neighbors.get(i), history));
            } else {
                System.out.print(neighbors.get(i).getName() + ", ");
                output.append(getSingleNodeDDString(neighbors.get(i), history));
            }
        }
        System.out.println(output);
    }

    /**
     * Prints adjacent nodes for algorithm two. The difference is this version
     * needs to be called with the currentNode so it can calculate the weight of the edge
     * @param neighbors - list of adjacent nodes
     * @param history - history list
     * @param isBackTrackCycle - flag that say whether this iteration of the loop
     *                         is a backtrack cycle. If so, nothing is printed.
     * @param currentNode - the current node
     */
    public static void printAdjacentNodes(List<Vertex> neighbors,
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
                output.append(getSingleNodeCombinedString(currentNode, neighbors.get(i), history));
            } else {
                System.out.print(neighbors.get(i).getName() + ", ");
                output.append(getSingleNodeCombinedString(currentNode, neighbors.get(i), history));
            }
        }
        System.out.println(output);
    }

    /**
     * Prints the end of a 'section' of the algorithm
     * @param history - the history list
     * @param currentNode - the current node
     */
    public static void printSectionEnd(List<Vertex> history, Vertex currentNode) {
        System.out.println("\tNode " + currentNode.getName() + " is selected");
        printPath(history);
        System.out.println("\n");
    }

    /**
     * Prints the shortest path
     * @param history - the history list
     */
    public static void printPath(List<Vertex> history) {
        System.out.print("\tShortest Path = ");
        for (int i = 0; i < history.size(); i++) {
            System.out.print(history.get(i).getName());
            if (i != history.size() - 1) { System.out.print(" → "); }
        }
    }

    /**
     * Prints the shortest path length calculation.
     * @param history
     */
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
        System.out.println(" = " + total + "\n");
    }
}
