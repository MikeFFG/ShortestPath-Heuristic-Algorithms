package com.cs526.finalproject;

public class Edge {
	private int weight;
	private String start;
	private String end;
	
	public Edge(String start, String end, int weight) {
		this.weight = weight;
		this.start = start;
		this.end = end;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public String getStart() {
		return start;
	}
	
	public String getEnd() {
		return end;
	}
	
	public boolean equals(Object obj) {
		if (!(obj instanceof Edge)) { return false; }
		Edge otherEdge = (Edge) obj;
		if (otherEdge.getEnd().equals(end) &&
			otherEdge.getStart().equals(start)) {
			return true;
		}
		return false;
	}
}
