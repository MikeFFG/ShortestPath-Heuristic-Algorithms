package com.cs526.finalproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
	private static String startNode = "J";									// Start node passed in as arg
	
	public static void main(String[] args) {
		directDistances = readDirectDistancesFromFile();					// Path hardcoded to distance.txt
		
		// Path set by passing in a command line arg of the path.
		graph = readGraphFromFile(args[0]);
		
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
	 */
	public static void algorithmOne(String start) {
		// Start
		Vertex currentNode = graph.findVertex(start);
		
		// Path history
		List<Vertex> history = new ArrayList<>();
		
		// Temp
		List<Vertex> neighbors;
		
		Vertex withShortestDistance = null;
		
		// Print title for algorithm 1
		System.out.println("Algorithm 1:\n");
		
		// Find the neighbor with the shortest distance to Z
		while (!currentNode.equals(END_NODE)) {
			System.out.println("\tCurrent Node = " + currentNode.getName());
			history.add(currentNode);

			neighbors = graph.getAdjacentNodes(currentNode);	// Get neighbors of current node
			printAdjacentNodes(neighbors);
			
			withShortestDistance = neighbors.get(0);		// Set shortest to 1st neighbor 
			for (int i = 0; i < neighbors.size(); i++) {
				if (isInHistoryList(history, neighbors.get(i))) {
					System.out.println("\t" + neighbors.get(i).getName() + 
							" is already in the path.");
					continue;
				} else if (neighbors.get(i).equals(END_NODE)){
					System.out.println("\tZ is the destination node. Stop.");
					currentNode = END_NODE;
					history.add(currentNode);
					break;
				} else {
					printSingleNodeDD(neighbors.get(i));
					
					if (neighbors.get(i).getDirectDistanceToZ() < 
							withShortestDistance.getDirectDistanceToZ()) {
						withShortestDistance = neighbors.get(i);
					}
					currentNode = withShortestDistance;
				}
			}
			
			if (currentNode.equals(END_NODE)) {
				System.out.print("\tShortest Path = ");
				for (int i = 0; i < history.size(); i++) {
					System.out.print(history.get(i).getName());
					if (i != history.size() - 1) { System.out.print(" → ");};
				}
				System.out.print("\n\tShortest path length = ");
				int total = 0;
				for (int i = 0; i < history.size(); i++) {
					System.out.println("Todo");
				}
				System.out.print(" = " + total);
			} else {
				System.out.println("\t" + currentNode.getName() + " is selected");
				System.out.println("\tShortest path: " + 
						history.get(history.size() - 1).getName() + 
						" → " + currentNode.getName());
				System.out.println("");
			}
		}
	}
	
	public static boolean isInHistoryList(List<Vertex> history, Vertex node) {
		for (Vertex v : history) {
			if (v.equals(node)) {
				return true;
			}
		}
		return false;
	}
	
	public static void printSingleNodeDD(Vertex v) {
		System.out.println("\t" + v.getName() + ": dd(" +
				v.getName() + ") = " + 
				v.getDirectDistanceToZ()
				);
	}
	
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
	 * into an AdjacencyList. This method is highly inefficient due to 
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
			
		// Then iterate through entries and create AdjacencyList
		for (int i = 1; i < dataStore.size(); i++) {
			/*
			 *  Because of the format of the input file, the first line will be shorter than the rest.
			 *  So here we are adding one element at the front of line 1. This will make it easy
			 *  for us to correlate the columns.
			 */
//			if (i == 0) {
//				
//				String[] tempArray = new 
//						String[dataStore.get(0).length + 1];	// Create a new slightly larger array
//				tempArray[0] = "_";								// Add our placeholder item
//				for (int j = 0; j < tempArray.length; j++) {	// Add all of our elements after it
//					System.out.println(dataStore.get(0)[j]);
//				}
//				dataStore.set(0, tempArray);					// save our temparray to the datastore
//				continue; 
//			}
			
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
//					System.out.println(v.getName());
//					System.out.println(dataStore.get(0)[j]);
//					System.out.println(Integer.parseInt(dataStore.get(i)[j]));
					v.addNewEdge(newEdge);
				}
			}
			// Finally we add our vertex to our AdjacencyList
			temp.addVertex(v);
		}
		
		// Return AdjacencyList
		return temp;
	}
	
	private static Map<String, Integer> readDirectDistancesFromFile() {
		Map<String, Integer> distances = new HashMap<>();
		BufferedReader br = null;
		FileReader fr = null;
		
		try {
			fr = new FileReader("distance.txt");
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
		
		return distances;
	}
}
