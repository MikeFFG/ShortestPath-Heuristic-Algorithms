package com.cs526.finalproject;

import java.util.ArrayList;

public class Vertex {
	private ArrayList<Edge> edges;
	private int directDistanceToZ;
	
	public Vertex(ArrayList<Edge> edges, int directDistanceToZ) {
		this.directDistanceToZ = directDistanceToZ;
		this.edges = edges;
	}
	
	public Vertex(ArrayList<Edge> edges) {
		this.edges = edges;
	}
	
	public Vertex() {
	}
	
	public void setDirectDistanceToZ(int distance) {
		directDistanceToZ = distance;
	}
	
	public int getDirectDistanceToZ() {
		return directDistanceToZ;
	}
	
	public void addNewEdge(Edge edge) {
		edges.add(edge);
	}
	
	public ArrayList<Edge> getEdges() {
		return edges;
	}
	
	
}
