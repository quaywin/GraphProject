import java.util.ArrayList;

import graph.DirectedEdge;
import graph.Graph;
import graph.LexBFS;
import graph.SimpleGraph;

public class TestLexBFS {
	static SimpleGraph<String, DirectedEdge<String>> g;
	static final String ONE = "one";
	static final String TWO = "two";
	static final String THREE = "three";
	static final String FOUR = "four";
	static final String FIVE = "five";

	static Graph.Edge<String> E1;
	public static void main(String[] args) {
		String[] vertices = { ONE, TWO, THREE, FOUR, FIVE };
		String[][] edges = { { ONE, TWO },{ TWO, TWO }, { ONE, THREE }, { THREE, TWO } ,{THREE,FOUR},{ ONE, FOUR },{ FIVE, ONE } ,{FIVE,FOUR}};
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
		ArrayList<String> ordering = lexbfs.LexBFSOrdering(g,FOUR);
		System.out.print("LexBFS order = ");
		for (String strings : ordering) {
			System.out.print(" " + strings);
		}
		System.out.println();
		boolean test = lexbfs.checkIsPerfectEliminationOdering(g, ordering);
		System.out.print("LexBFS is perfect elimination order = ");
		System.out.print(" "+test);
		
		
	}
}
