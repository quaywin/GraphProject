import graph.DirectedEdge;
import graph.Interval;
import graph.IntervalGraph;
import graph.MultiGraph;
import graph.SimpleGraph;
public class TestIntervalGenerate {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//--------- Test part 1, 2
		IntervalGraph<DirectedEdge<Interval>> intervals = new IntervalGraph<DirectedEdge<Interval>>();
		SimpleGraph<Interval, DirectedEdge<Interval>> g = new SimpleGraph<Interval, DirectedEdge<Interval>>();
		MultiGraph<Interval, DirectedEdge<Interval>> multi = new MultiGraph<Interval, DirectedEdge<Interval>>();
		Interval a = new Interval(1, 3,"A");
		Interval b = new Interval(2, 7,"B");
		Interval c = new Interval(6, 8,"C");
		Interval d = new Interval(4, 8,"D");
		Interval e = new Interval(2, 5,"E");
		intervals.addVertex(a);
		intervals.addVertex(b);
		intervals.addVertex(c);
		intervals.addVertex(d);
		intervals.addVertex(e);
		g = intervals.generateGraph();
		System.out.print("vertices =");
		for (Interval i : g.vertices())
			System.out.print(" " + i.name());
		System.out.println();
		System.out.print("edges =");
		
		for (DirectedEdge<Interval> edges : g.edges()) {
			System.out.print(" (" + edges.source().name() + "," + edges.target().name() + ")");
		}
		
		g = intervals.generateGraph().complementaryGraph();
		for (Interval vertex : g.vertices()) {
			multi.addVertex(vertex);
		}
		for (DirectedEdge<Interval> edge : g.edges()) {
			multi.addEdge(edge);
		}
		System.out.println();
		System.out.print("edges from complementary graph =");
		for (DirectedEdge<Interval> edges : g.edges()) {
			System.out.print(" (" + edges.source().name() + "," + edges.target().name() + ")");
		}
		System.out.println();
		System.out.print("edges from complementary multi graph =");
		for (DirectedEdge<Interval> edges : multi.edges()) {
			System.out.print(" (" + edges.source().name() + "," + edges.target().name() + ")");
		}
	}

}
