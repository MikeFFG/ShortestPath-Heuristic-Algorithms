package com.cs526.finalproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Uses adjacency list data structure
 * @author mike
 *
 */
public class heuristicShortestPath {
	private static final Vertex END_NODE = new Vertex("Z"); 	// End node is hardcoded								
	
	private static Map<String, Integer> directDistances = new HashMap<>();	// Holds direct distances
	private static AdjacencyList graph = new AdjacencyList();				// Main graph data structure
	private static String startNode = "H";									// Start node passed in as arg
	
	public static void main(String[] args) {

		// Path set by passing in a command line arg of the path.
		if (args.length < 1) {
			System.out.print("Please enter the path to the input file.");
			System.exit(0);
		} else {
			// Read the input file and prepare the AdjacencyList
			graph = readGraphFromFile(args[0]);
		}

		directDistances = readDirectDistancesFromFile();					// Path hardcoded to direct_distance.txt
		// Set the edge weights for the graph from directDistances
		setEdgeWeights(graph, directDistances);


		// Run algorithm one
		algorithmOne(startNode);
		
		// Run algorithm two
//		algorithmTwo(graph);
	}
	
	/**
	 * Algorithm 1: Among all nodes v that are adjacent to the node n, choose the one with 
	 * the smallest dd(v). 
	 * 
	 * The algorithm itself is fairly simple. The code logic gets difficult only because
	 * we need to keep adding print statements all over the place to get the output
	 * to look per the specifications.
	 */
	public static void algorithmOne(String start) {
		Vertex currentNode = graph.findVertex(start); 		// Start Node
		List<Vertex> history = new ArrayList<>(); 			// Path history
		List<Vertex> neighbors;								// Neighbors List
		Vertex withShortestDistance = null;					// Shortest Distance Node placeholder
		List<Vertex> blackList = new ArrayList<>();

		// Print title for algorithm 1
		System.out.println("Algorithm 1:\n");

		/*
		 *	Find the neighbor with the shortest distance to Z
		 *	We loop this code until we hit the end node
		 */
		while (!currentNode.equals(END_NODE)) {		// Until we find the END_NODE
			history.add(currentNode);							// Add node to the history list
			neighbors = graph.getAdjacentNodes(currentNode);	// Get neighbors of current node
			Vertex prevNode = currentNode;						// Save previous node for later comparison

			// Print statements
			System.out.println("\tCurrent Node = " + currentNode.getName());
			Utilities.printAdjacentNodes(neighbors);
			
			// withShortestDistance = neighbors.get(0);		// Set shortest to 1st neighbor
			
			// Iterate through all nodes in neighbors list to find node with shortest distance
			nodeLoop:
				for (int i = 0; i < neighbors.size(); i++) {
					if (isInBlackList(blackList, neighbors.get(i))) {
						continue nodeLoop;
					} else if (isInHistoryList(history, neighbors.get(i))) {	// Node is already in history list
						System.out.println("\t" + neighbors.get(i).getName() +
								" is already in the path.");
						continue nodeLoop;										// Go to next iteration
					} else if (neighbors.get(i).equals(END_NODE)){		// We have reached the end
						System.out.println("\tZ is the destination node. Stop.");
						currentNode = END_NODE;							// Set currentNode (to break while loop)
						history.add(currentNode);						// Add to history
						break nodeLoop;									// Leave for loop
					} else {											// Normal node case
						Utilities.printSingleNodeDD(neighbors.get(i));

						if (withShortestDistance == null) {
							withShortestDistance = neighbors.get(i);
						} else if (neighbors.get(i).getDirectDistanceToZ() <
								withShortestDistance.getDirectDistanceToZ()) {
							// If this node is shorter, replace withShortestDistance with current
							withShortestDistance = neighbors.get(i);
						}

						// Reset currentNode
						currentNode = withShortestDistance;
					}
				}

			if (currentNode.equals(prevNode)) { 	// This means we hit a dead end and weren't able to move on
				System.out.println("\tDead end.");
				System.out.println("\tBacktrack to " + history.get(history.size() - 2).getName());
				currentNode = history.get(history.size() - 2);
				history.remove(currentNode);
				blackList.add(history.remove(history.size() - 1));		// Make sure we can't do this again.
				withShortestDistance = null;
			} else if (currentNode.equals(END_NODE)) {
				// Print either the end of the section or end of the algorithm info
				Utilities.printPath(history);
				Utilities.printPathLength(history);
			} else { 	// We haven't reached the end yet
				Utilities.printSectionEnd(history, currentNode);
			}
		}
	}


	public static boolean isInBlackList(List<Vertex> blackList, Vertex node) {
		for (Vertex v : blackList) {
			if (v.equals(node)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Searches history list for a specific node
	 * @param history - the history list to search
	 * @param node - the node to find
	 * @return - true if we find the node. False if we don't find it.
	 */
	public static boolean isInHistoryList(List<Vertex> history, Vertex node) {
		for (Vertex v : history) {
			if (v.equals(node)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Algorithm 2: Among all nodes v that are adjacent to the node n, choose the one for 
	 * which w(n, v) + dd(v) is the smallest. 
	 */
	public static void algorithmTwo() {
		
	}
	
	/**
	 * Sets the edge weights given a graph and a Map of the distances
	 * @param list - the Adjacency List for the graph to be worked on
	 * @param distances - a Map<String, Integer> of the Key Value pairs where
	 * nodes are the vertex names and the Values are the Integers of the distance.
	 * @precondition - inputs should have equal numbers of items
	 */
	private static void setEdgeWeights(AdjacencyList list, Map<String, Integer> distances) {
		/* 
		 * If these sizes don't match, something is wrong with the input
		 * and we should end the program.
		 */
		if (list.numVertices() != distances.size()) {
			throw new IllegalArgumentException("The data does not match.");
		}
		
		/*
		 *  Iterate over the keys of distances.
		 *  Set the distance for each key.
		 */
		for (String name : distances.keySet()) {
			list.findVertex(name).setDirectDistanceToZ(distances.get(name));;
		}
	}
	
	/**
	 * This method reads in a text file of a graph representation and turns it
	 * into an AdjacencyList. This method seems pretty  inefficient due to
	 * multiple for loops, but a specification of this project is that there are
	 * only a maximum of 26 nodes, so the data will never get large.
	 * @param filePath - the path to read in the graph input file
	 * @return - a new AdjacencyList filled with the data from the input file
	 */
	private static AdjacencyList readGraphFromFile(String filePath) {
		// We will return this temp AdjacencyList
		AdjacencyList temp = new AdjacencyList();
		
		/* This is our intermediary data structure for transferring data
		 * from the file to our AdjacencyList
		 */
		List<String[]> dataStore = new ArrayList<>();
		
		// File reading utils
		BufferedReader br = null;
		FileReader fr = null;
		
		// Try/catch the file reading process
		try {
			fr = new FileReader(filePath);
			br = new BufferedReader(fr);
			String currentLine;
			
			/*
			 *  First, read each line, split it on whitespace
			 *  and add as an entry into dataStore
			 *  using an ArrayList<String[]> for ease of handling
			 */
			while ((currentLine = br.readLine()) != null) {
				dataStore.add((String[])currentLine.split("\\s+"));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// Closing resources
			closeResources(br, fr);
		}
			
		// Then iterate through entries and create AdjacencyList
		for (int i = 1; i < dataStore.size(); i++) {
			Vertex v = null;										// Create new vertex for this entry
			for (int j = 0; j < dataStore.get(i).length; j++) {		// Loop through this entry
				/*
				 * If we are in the first column, we know we don't have
				 * ints but we have vertex names. So, we create a new 
				 * vertex with the name of the row.
				 */
				if (j == 0) {										
					v = new Vertex(dataStore.get(i)[0]);			
					continue;
				}
				
				/*
				 * Now we add each edge to the Vertex.
				 */
				if (Integer.parseInt(dataStore.get(i)[j]) != 0) {
					Edge newEdge = new Edge(
							v.getName(),
							dataStore.get(0)[j],
							Integer.parseInt(dataStore.get(i)[j])
							);
					v.addNewEdge(newEdge);
				}
			}
			// Finally we add our vertex to our AdjacencyList
			temp.addVertex(v);
		}
		
		// Return AdjacencyList
		return temp;
	}
	
	/**
	 * Reads "distance.txt" and loads the direct distances into a Map.
	 * @return - a Map<String, Integer> of the direct distances
	 */
	private static Map<String, Integer> readDirectDistancesFromFile() {
		Map<String, Integer> distances = new HashMap<>();
		BufferedReader br = null;
		FileReader fr = null;
		
		try {
			fr = new FileReader("direct_distance.txt");
			br = new BufferedReader(fr);
			String currentLine;
			
			while ((currentLine = br.readLine()) != null) {
				String[] keyValuePair = currentLine.split(" ");
				distances.put(keyValuePair[0], Integer.parseInt(keyValuePair[1]));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// Closing resources
			closeResources(br, fr);
		}
		
		return distances;
	}

	public static void closeResources(BufferedReader br, FileReader fr) {
		try {
			if (br != null) {
				br.close();
			}
			if (fr != null) {
				fr.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
