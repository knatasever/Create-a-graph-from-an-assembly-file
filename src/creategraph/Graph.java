package creategraph;;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import creategraph.Vertex;

class Graph{
	
	public ArrayList<Vertex> vertexList;
	public HashMap<Vertex,LinkedList<Vertex>> adjList;
	
	public int index;
	
	public Graph(){
		this.vertexList = new ArrayList<Vertex>();
		this.adjList = new HashMap<Vertex,LinkedList<Vertex>>();
		
	}
	
	public void addVertex(Vertex vertex){
		
		vertexList.add(index++, vertex);
		
		adjList.put(vertex, new LinkedList<Vertex>());
	}
	
	
	public void addEdge(Vertex from, Vertex to){
		
		adjList.get(from).add(to);
		adjList.put(from, adjList.get(from));
		
	}
}


