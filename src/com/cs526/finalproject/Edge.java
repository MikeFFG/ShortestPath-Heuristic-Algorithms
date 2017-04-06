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
}
