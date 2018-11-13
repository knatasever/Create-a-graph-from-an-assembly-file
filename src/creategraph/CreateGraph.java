/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package creategraph;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author KÜBRA NUR
 */
public class CreateGraph {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	Graph graph1 = new Graph();

        HashMap<String, ArrayList<String>> functionMap = new HashMap<String,ArrayList<String>>();
	funQueue queue = new funQueue();

        graph1= createGraph(functionMap,queue,graph1);
			

    }
      private static Graph createGraph(HashMap<String, ArrayList<String>> functionMap, funQueue queue, Graph graph) {
		boolean alreadyVertex=false; 
		while(!queue.isEmpty()){
			String funName = queue.remove();
			ArrayList<String> callee = null;
			ArrayList<String> opcode = null;
			alreadyVertex=false;
			int length = 0;
                      
			if(funName.substring(0, 3).equals("sub"))
			{
                           
				ArrayList<String> funData = functionMap.get(funName);
				callee = new ArrayList<String>();
				opcode = new ArrayList<String>();
				/*Construct Vertex object from function data */
				for(int i=0;i<funData.size();i++)
				{
					String instruction = funData.get(i);
					String [] array = instruction.split(" ");
					if(array[0].equals("call"))
					{
                                              
						if(array[1].substring(0, 3).equals("sub") || (!array[1].substring(0, 3).equals("loc")))
						{
                                                  callee.add(array[1]);
                                                   // System.out.println(array[1]);
                                                  
						}
					}
					if(!array[0].equals("length_"))
					{	
						opcode.add(array[0]);
					}	
					
					if(array[0].equals("length_"))
					{	
						length = Integer.parseInt(array[1]);
					}
				}
			}
			/*if vertex is already created i.e. it is exists in vertexList don't create it */
			int k=0;
			Vertex vertex = null;
                        
			for(k=0;k<graph.vertexList.size();k++){
				if(graph.vertexList.get(k).function_name.equals(funName)){
                                    alreadyVertex=true;
					break;
                                        
				}
			}
			if(alreadyVertex==false){
			if(funName.substring(0, 3).equals("sub")){	
                            			
                            vertex = new Vertex(funName,"local_routine",0,0,callee,opcode,length);
			}
			else
			{
                            vertex = new Vertex(funName,"lib_routine",0,0,null,null,0);

			}
			vertex.enqueFlag=true;
			graph.addVertex(vertex);
			}
			else if(funName.substring(0, 3).equals("sub"))
			{
				graph.vertexList.get(k).callees=callee;
				graph.vertexList.get(k).opcode_seq=opcode;
				graph.vertexList.get(k).length=length;
			}
			
			else
			{
				graph.vertexList.get(k).callees=null;
				graph.vertexList.get(k).opcode_seq=null;
			}
			
			/*This part is executed only if it is subroutine function, as we don't
			  track calls from library function
			 */
			if(funName.substring(0, 3).equals("sub")){
			for(int i=0;i<callee.size();i++){
			
				/*Construct Vertex object from function data, again 
		 		 * these are local subroutine and not dynamic or 
		 		 * static function calls
		 		   */
			
				Vertex vertex1 = null;
				boolean alreadyVertex1=false;
				int l=0;
				for(l=0;l<graph.vertexList.size();l++){
					if(graph.vertexList.get(l).function_name.equals(callee.get(i))){
						alreadyVertex1=true;
                                                                                               
						break;
					}
				}
				if(alreadyVertex1==false)
				{
					if(callee.get(i).substring(0, 3).equals("sub"))
					{
						vertex1 = new Vertex(callee.get(i),"local_routine",0,0,null,null,0);
					}
					else
					{
						vertex1 = new Vertex(callee.get(i),"lib_routine",0,0,null,null,0);
					}
					
					graph.addVertex(vertex1);
					if(vertex == null)
					{
						vertex = graph.vertexList.get(k);
						graph.addEdge(vertex, vertex1);
						vertex.outdegree++;
						vertex1.indegree++;
						
					}
					else
					{
						graph.addEdge(vertex, vertex1);
						vertex.outdegree++;
						vertex1.indegree++;
					}
					
				}
				
				else
				{
					if(vertex==null){
						vertex = graph.vertexList.get(k);
						graph.addEdge(vertex, graph.vertexList.get(l));
						vertex.outdegree++;
						graph.vertexList.get(l).indegree++;
					}
					else
					{
						graph.addEdge(vertex, graph.vertexList.get(l));
						vertex.outdegree++;
						graph.vertexList.get(l).indegree++;
					}
					
				}
				/*if there is edge between vertex and vertex1 
				 * increase weight, vertex outdegree and vertex1 indgree
				 * by 1
				 * */
				if(alreadyVertex1!=true){
				//if(vertex1.enqueFlag==false && alreadyVertex!=true){
					queue.insert(callee.get(i));
					vertex1.enqueFlag=true;
				}
			}
			}

		}
		
		return graph;
	}

}
