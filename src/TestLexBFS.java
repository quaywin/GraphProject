import java.util.ArrayList;

import graph.ChordalGraphs;
import graph.DirectedEdge;
import graph.Graph;
import graph.LexBFS;
import graph.SimpleGraph;

public class TestLexBFS {
	static SimpleGraph<String, DirectedEdge<String>> g;
	static final String A = "A";
	static final String B = "B";
	static final String C = "C";
	static final String D = "D";
	static final String E = "E";
	static final String F = "F";
	static final String G = "G";
	static Graph.Edge<String> E1;
	public static void main(String[] args) {
		String[] vertices = { A, B, C, D, E,F,G};
		String[][] edges = { { A, B },{ A, C },{ A, D }, { B, C } ,{C,D},{ C, E },{ C, F },{ D, F },{ F, G },{ E, D } };
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
		ChordalGraphs<String> chordalGraph = new ChordalGraphs<String>();
		
		for (String v : g.vertices()) {
			ArrayList<String> ordering = lexbfs.LexBFSOrdering(g,v);
			System.out.print("LexBFS order = ");
			
			for (String strings : ordering) {
				System.out.print(" " + strings);
			}

			System.out.println();
			System.out.print("LexBFS is perfect elimination order = ");
			
			if(chordalGraph.checkIsPerfectEliminationOdering(g, ordering)){
				System.out.print(" "+true);
			}else{
				System.out.print(" "+false);
			}
			System.out.println();
		}
		
		//boolean test = lexbfs.checkIsPerfectEliminationOdering(g, ordering);
		
		
		
	}
}
