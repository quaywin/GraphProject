import java.util.ArrayList;

import graph.ChordalGraphs;
import graph.DirectedEdge;
import graph.Graph;
import graph.LexBFS;
import graph.SimpleGraph;

public class TestLexBFS {
	static SimpleGraph<String, DirectedEdge<String>> g;
<<<<<<< HEAD
	static final String ONE = "A";
	static final String TWO = "B";
	static final String THREE = "C";
	static final String FOUR = "D";
	static final String FIVE = "E";
	static final String SIX = "F";
	static final String SEVEN = "G";
=======
	static final String ONE = "one";
	static final String TWO = "two";
	static final String THREE = "three";
	static final String FOUR = "four";
	static final String FIVE = "five";
	static final String SIX = "six";
	static final String SEVEN = "seven";
	static final String EIGHT = "eight";
>>>>>>> FETCH_HEAD
	static Graph.Edge<String> E1;
	public static void main(String[] args) {
		String[] vertices = { ONE, TWO, THREE, FOUR, FIVE,SIX};
		String[][] edges = { { ONE, TWO }, { TWO, THREE } 
		,{THREE,FOUR},{ FIVE, TWO },{ THREE, FIVE },{ THREE, SIX },{ FIVE, SIX },{ SIX, TWO } };
		g = new SimpleGraph<String, DirectedEdge<String>>();
		for (String v : vertices)
			g.addVertex(v);
		for (String[] e : edges)
			g.addEdge(new DirectedEdge<String>(e[0], e[1]));
		System.out.println("Graph:");
		System.out.print("vertices =");
		for (String s : g.vertices())
			System.out.print(" " + s);
		System.out.println();
		System.out.print("edges =");
		for (DirectedEdge<String> e : g.edges())
			System.out.print(" (" + e.source() + "," + e.target() + ")");
		System.out.println();
		LexBFS<String> lexbfs = new LexBFS<String>();
		
		
		for (String v : g.vertices()) {
			ArrayList<String> ordering = lexbfs.LexBFSOrdering(g,v);
			System.out.print("LexBFS order = ");
			
			for (String strings : ordering) {
				System.out.print(" " + strings);
			}

			System.out.println();
			System.out.print("LexBFS is perfect elimination order = ");
			
			if(lexbfs.checkIsPerfectEliminationOdering(g, ordering)){
				System.out.print(" "+true);
			}else{
				System.out.print(" "+false);
			}
			System.out.println();
		}
		
		ChordalGraphs<String> chordalGraphs = new ChordalGraphs<>();
		chordalGraphs.generateFromGraphToSetInterval(g);
		
		//boolean test = lexbfs.checkIsPerfectEliminationOdering(g, ordering);
		
		
		
	}
}
