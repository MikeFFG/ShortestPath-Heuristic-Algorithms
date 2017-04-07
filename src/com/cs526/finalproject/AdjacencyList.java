package com.cs526.finalproject;

import java.util.ArrayList;
import java.util.List;

public class AdjacencyList {
	private List<Vertex> list;
	
	public AdjacencyList(int capacity) {
		list = new ArrayList<Vertex>(capacity);
	}
	
	public AdjacencyList() {
		this(26);
	}
	
	public List<Vertex> getList() {
		return list;
	}
	
	public int numVertices() {
		return list.size();
	}
	
	/**
	 * Could implement with binary search because we can compare A-Z?
	 * @param key
	 * @return
	 */
	public Vertex findVertex(String key) {
		for (Vertex v : list) {
			if (v.getName().equals(key)) {
				return v;
			}
		}
		return null;
	}
	
	public ArrayList<Vertex> getAdjacentNodes(Vertex v) {
		ArrayList<Vertex> adjacentNodes = new ArrayList<>();
		
		for (Edge e : v.getEdges()) {
			adjacentNodes.add(findVertex(e.getEnd()));
		}
		
		return adjacentNodes;
	}
	
	public void addVertex(Vertex v) {
		list.add(v);
	}
}
