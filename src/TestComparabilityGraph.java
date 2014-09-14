
import graph.ComparabilityGraph;
import graph.DirectedEdge;
import graph.Graph;
import graph.SimpleGraph;

public class TestComparabilityGraph {
	static SimpleGraph<String, DirectedEdge<String>> g;
	static final String ONE = "one";
	static final String TWO = "two";
	static final String THREE = "three";
	static final String FOUR = "four";
	static final String FIVE = "five";
	static final String SIX = "six";
	
	static Graph.Edge<String> E1;
	public static void main(String[] args) {
		String[] vertices = { ONE, TWO, THREE, FOUR, FIVE, SIX };
		String[][] edges = { { ONE, TWO }, { ONE, FIVE }, { TWO, THREE } ,{TWO,SIX},{ THREE, FOUR },
				{ THREE, SIX } ,{FOUR,SIX},{FOUR,FIVE},{FIVE,SIX},{FIVE,TWO},{ONE,SIX}};
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
		ComparabilityGraph<String> comparabilityGraph = new ComparabilityGraph<String>();	
		boolean check = comparabilityGraph.checkComparabilityGraph(g.undirectedGraph());
		System.out.print("check comparability = "+ check);		
		
	}
}
