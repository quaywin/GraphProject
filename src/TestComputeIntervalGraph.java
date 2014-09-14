import java.util.ArrayList;

import graph.DirectedEdge;
import graph.Graph;
import graph.Interval;
import graph.IntervalGraphRecognition;
import graph.SimpleGraph;

public class TestComputeIntervalGraph {
	static SimpleGraph<String, DirectedEdge<String>> g;
	static final String ONE = "A";
	static final String TWO = "B";
	static final String THREE = "C";
	static final String FOUR = "D";
	static final String FIVE = "E";
	static final String SIX = "F";
	static final String SEVEN = "G";
	static Graph.Edge<String> E1;
	public static void main(String[] args) {
		String[] vertices = { ONE, TWO, THREE, FOUR, FIVE,SIX,SEVEN};
		//String[][] edges = { { ONE, TWO },{ ONE, THREE },{ ONE, FOUR }, { TWO, THREE } ,{THREE,FOUR},{ FIVE, FOUR },{ THREE, FIVE },{ THREE, SIX },{ FOUR, SIX },{ SIX, SEVEN } };
		//String[][] edges = { { ONE, TWO }, { TWO, THREE } ,{THREE,FOUR},{ FIVE, TWO },{ THREE, FIVE },{ THREE, SIX },{ FIVE, SIX },{ SIX, TWO },{ SIX, ONE } };
		String[][] edges = { { ONE, TWO }, { TWO, THREE } ,{THREE,FOUR},{ THREE, SEVEN },{ THREE, SIX },{ SEVEN, SIX },{ FOUR, FIVE } };
		g = new SimpleGraph<String, DirectedEdge<String>>();
		for (String v : vertices)
			g.addVertex(v);
		for (String[] e : edges)
			g.addEdge(new DirectedEdge<String>(e[0], e[1]));
		IntervalGraphRecognition<String> intervalGraphRecognition = new IntervalGraphRecognition<String>();
		ArrayList<Interval> listInterval = intervalGraphRecognition.generateFromGraphToSetInterval(g);
		if(intervalGraphRecognition.ordering(g)!=null){
			System.out.print("INTERVAL");
			System.out.println();
			for (Interval intv: intervalGraphRecognition.reverse(listInterval)){
				System.out.print(intv.name() + " (" + (int)intv.min() + "," + (int)intv.max() + ")");
				System.out.println();
			}
			System.out.print("DRAW");
			System.out.println();
			intervalGraphRecognition.printIntervalGraph(listInterval);
		}else{
			System.out.print("This is not interval graph");
		}
		
	}
}
