package com.cs526.finalproject;

import java.util.ArrayList;

public class AdjacencyList {
	private ArrayList<Vertex> list;
	
	public AdjacencyList(int capacity) {
		list = new ArrayList<Vertex>(capacity);
	}
	
	public AdjacencyList() {
		this(26);
	}
	
	public ArrayList<Vertex> getList() {
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
}
