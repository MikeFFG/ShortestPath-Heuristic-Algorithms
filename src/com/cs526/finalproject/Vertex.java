package com.cs526.finalproject;

import java.util.ArrayList;

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
	 * Start point of edge should be Vertex.name
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
	
	public int numEdges() {
		return edges.size();
	}
	
}
