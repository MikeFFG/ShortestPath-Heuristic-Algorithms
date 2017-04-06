package com.cs526.finalproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Uses adjacency list data structure
 * @author mike
 *
 */
public class heuristicShortestPath {
	private static final String END_NODE = "Z";
	
	private static Map<String, Integer> directDistances = new HashMap<>();
	private static AdjacencyList graph = new AdjacencyList();
	private static String startNode = "J";
	
	public static void main(String[] args) {
		// Path hardcoded to distance.txt
		directDistances = readDirectDistancesFromFile();
		
		// Path set by passing in a command line arg of the path.
		graph = readGraphFromFile(args[0]);
		
		// Set the edge weights for the graph from directDistances
		setEdgeWeights(graph, directDistances);
		
		algorithmOne();
//		algorithmTwo(graph);
	}
	
	/**
	 * Algorithm 1: Among all nodes v that are adjacent to the node n, choose the one with 
	 * the smallest dd(v). 
	 */
	public static void algorithmOne() {
		
	}
	
	/**
	 * Algorithm 2: Among all nodes v that are adjacent to the node n, choose the one for 
	 * which w(n, v) + dd(v) is the smallest. 
	 */
	public static void algorithmTwo() {
		
	}
	
	private static void setEdgeWeights(AdjacencyList list, Map<String, Integer> distances) {
		if (list.numVertices() != distances.size()) {
			throw new IllegalArgumentException("The data does not match.");
		}
		
		for (String name : distances.keySet()) {
			list.findVertex(name).setDirectDistanceToZ(distances.get(name));;
		}
	}
	
	private static AdjacencyList readGraphFromFile(String filePath) {
		AdjacencyList temp = new AdjacencyList();
		BufferedReader br = null;
		FileReader fr = null;
		
		try {
			fr = new FileReader("distance.txt");
			br = new BufferedReader(fr);
			String currentLine;
			ArrayList<ArrayList<String>> dataStore = new ArrayList<>();
			
			/*
			 *  First, read each line and add as an entry into dataStore
			 *  Using an ArrayList of ArrayList<String> for ease of handling
			 */
			while ((currentLine = br.readLine()) != null) {
				dataStore.add((ArrayList<String>) Arrays.asList(currentLine.split(" ")));
			}
			
			// Then iterate through entries and create AdjacencyList
			for (int i = 0; i < dataStore.size(); i++) {
				/*
				 *  Because of the format of the input file, the first line will be shorter than the rest.
				 *  So here we are adding one element at the front of line 1
				 */
				if (i == 0) { 
					dataStore.get(0).add(0, "_");
					continue; 
				}
				
				Vertex v = null;
				for (int j = 0; j < dataStore.get(i).size(); j++) {
					if (j == 0) {
						v = new Vertex(dataStore.get(i).get(0));			// Create new vertex with name of row
						continue;
					}
					if (Integer.parseInt(dataStore.get(i).get(j)) != 0) {
						Edge newEdge = new Edge(
								v.getName(),
								dataStore.get(0).get(j),
								Integer.parseInt(dataStore.get(i).get(j))
								);
						v.addNewEdge(newEdge);
					}
				}
				// Add new vertex to temp AdjacencyList
				temp.addVertex(v);
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
