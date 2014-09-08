import java.util.ArrayList;

import graph.ChordalGraphs;
import graph.DirectedEdge;
import graph.Graph;
import graph.Interval;
import graph.LexBFS;
import graph.SimpleGraph;

public class TestComputeIntervalGraph {
	static SimpleGraph<String, DirectedEdge<String>> g;
	static final String ONE = "one";
	static final String TWO = "two";
	static final String THREE = "three";
	static final String FOUR = "four";
	static final String FIVE = "five";
	static final String SIX = "six";
	static final String SEVEN = "seven";
	static final String EIGHT = "eight";
	static Graph.Edge<String> E1;
	public static void main(String[] args) {
		String[] vertices = { ONE, TWO, THREE, FOUR, FIVE};
		String[][] edges = { { ONE, TWO }, { ONE, THREE }, { TWO, THREE } ,{THREE,FOUR},{ FIVE, FOUR },
				{ THREE, FIVE } };
		g = new SimpleGraph<String, DirectedEdge<String>>();
		for (String v : vertices)
			g.addVertex(v);
		for (String[] e : edges)
			g.addEdge(new DirectedEdge<String>(e[0], e[1]));
		System.out.println("Graph:");

		
		ChordalGraphs<String> chordalGraphs = new ChordalGraphs<>();
		ArrayList<Interval> listInterval = chordalGraphs.generateFromGraphToSetInterval(g);
		System.out.print("LexBFS order = ");
		
		for (String strings : chordalGraphs.ordering(g)) {
			System.out.print(" " + strings);
		}
		System.out.println();
		System.out.println();
		System.out.print("INTERVAL =");
		for (Interval intv: listInterval){
			System.out.print(" (" + intv.min() + "," + intv.max() + ")");
			System.out.println();
		}
			
		
		//boolean test = lexbfs.checkIsPerfectEliminationOdering(g, ordering);
		
		
		
	}
}
