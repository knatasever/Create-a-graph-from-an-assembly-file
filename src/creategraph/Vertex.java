package creategraph;

import java.util.ArrayList;


class Vertex {
	public String function_name;
	public String function_type;
	double indegree;
	double outdegree;
	ArrayList<String> callees;
	ArrayList<String> opcode_seq;
	public boolean enqueFlag;
	double length;
	
	public Vertex(String string, String string2, int i, int j, ArrayList<String> object,
			 ArrayList<String> function,int length) {
		this.function_name = string;
		this.function_type = string2;
		this.indegree=i;
		this.outdegree=j;
		this.callees=object;
		this.opcode_seq=function;
		this.enqueFlag=false;
		this.length=length;
	}
	
	
}

