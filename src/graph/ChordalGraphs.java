package graph;

public class ChordalGraphs<V, E extends Graph.Edge<Interval>>{
	public  ChordalGraphs() {
		
	}
	
	public static <V, E extends Graph.Edge<Interval>> Graph<Interval,DirectedEdge<Interval>> chordalGraph(Interval[] intervals) {
		//E edge =null;
		SimpleGraph<Interval,DirectedEdge<Interval>> graph = new SimpleGraph<Interval,DirectedEdge<Interval>>();
		DirectedEdge<Interval> edge;
		for (Interval interval : intervals) {
			graph.addVertex(interval);
		}
		for (Interval interval_s : intervals) {
			for (Interval interval_t : intervals) {
				if(interval_s.intersect(interval_t)){
					edge = new DirectedEdge<Interval>(interval_s,interval_t);
					graph.addEdge(edge);
				}
			}
		}
		return graph;
	}	
}

