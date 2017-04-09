package com.cs526.finalproject;

import java.util.ArrayList;

/**
 * Model class to hold information about a Vertex (aka node)
 * @author mike
 */
public class Vertex {
	private ArrayList<Edge> edges;
	private int directDistanceToZ;
	private String name;
	
	public Vertex(String name, ArrayList<Edge> edges, int directDistanceToZ) {
		this.name = name;
		this.edges = edges;
		this.directDistanceToZ = directDistanceToZ;
	}
	
	public Vertex(String name, ArrayList<Edge> edges) {
		this(name, edges, -1);
	}
	
	public Vertex(String name) {
		this(name, new ArrayList<Edge>());
	}
	
	public void setDirectDistanceToZ(int distance) {
		directDistanceToZ = distance;
	}
	
	public int getDirectDistanceToZ() {
		return directDistanceToZ;
	}
	
	/**
	 * Start point of edge is assumed to be this Vertex
	 * @param edge
	 */
	public void addNewEdge(Edge edge) {
		edges.add(edge);
	}
	
	public ArrayList<Edge> getEdges() {
		return edges;
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * @return the number of edges for this vertex
	 */
	public int numEdges() {
		return edges.size();
	}
	
	/**
	 * Finds an edge that starts at the current vertex
	 * @param end - The other end of the edge (start is assumed
	 * to be the current vertex)
	 * @return - the edge if it is found. Null if not found
	 */
	public Edge findEdge(String end) {
		for (Edge edge : edges) {
			if (edge.getEnd().equals(end)) {
				return edge;
			}
		}
		return null;
	}

	public Edge findEdgeWithLowestWeight() {
		Edge lowestWeight = edges.get(0);
		for (int i = 1; i < edges.size(); i++) {
			if (edges.get(i).getWeight() < lowestWeight.getWeight()) {
				lowestWeight = edges.get(i);
			}
		}
		return lowestWeight;
	}

	public int getCombinedWeight(Vertex endNode) {
		return getDirectDistanceToZ() + endNode.findEdge(name).getWeight();
	}
	
	/**
	 * Overrides the standard equals method defining
	 * equals based only on the name.
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof Vertex)) { return false; }

		if (((Vertex) obj).getName().equals(name)) {
			return true;
		}
		return false;
	}
}
