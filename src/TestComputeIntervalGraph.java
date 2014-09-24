import java.util.ArrayList;

import graph.DirectedEdge;
import graph.Graph;
import graph.Interval;
import graph.IntervalGraphRecognition;
import graph.SimpleGraph;

public class TestComputeIntervalGraph {
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
		//String[][] edges = { { A, B }, { B, C } ,{C,D},{ E, B },{ C, E },{ C, F },{ E, F },{ F, B },{ F, A } };
		//String[][] edges = { { A, B }, { B, C } ,{C,D},{ C, G },{ C, F },{ F, G },{ D, E } };
		g = new SimpleGraph<String, DirectedEdge<String>>();
		for (String v : vertices)
			g.addVertex(v);
		for (String[] e : edges)
			g.addEdge(new DirectedEdge<String>(e[0], e[1]));
		IntervalGraphRecognition<String> intervalGraphRecognition = new IntervalGraphRecognition<String>();
		ArrayList<Interval> listInterval = intervalGraphRecognition.generateFromGraphToSetInterval(g);
		if(intervalGraphRecognition.ordering(g)!=null){
			/*System.out.print("LexBFS order = ");
			
			for (String strings : intervalGraphRecognition.orderingExtend(g, intervalGraphRecognition.ordering(g))) {
				System.out.print(" " + strings);
			}

			System.out.println();
			for (String strings : intervalGraphRecognition.ordering(g)) {
				System.out.print(" " + strings);
			}

			System.out.println();*/
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
