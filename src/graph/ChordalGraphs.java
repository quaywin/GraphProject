package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ChordalGraphs<V>{
	public  ChordalGraphs() {
		
	}
	
	public ArrayList<V> ordering(SimpleGraph<V, DirectedEdge<V>> graph) {
		LexBFS<V> lexbfs = new LexBFS<V>();		
		for (V v : graph.vertices()) {			
			if(lexbfs.checkIsPerfectEliminationOdering(graph, lexbfs.LexBFSOrdering(graph, v))){
				return lexbfs.LexBFSOrdering(graph, v);
			}
		}
		return null;
	}
	
	public boolean checkChordalGraph(ArrayList<V> ordering) {
		if(ordering!=null){
			return true;
		}
		return false;
	}
	
	public ArrayList<Interval> generateFromGraphToSetInterval(SimpleGraph<V, DirectedEdge<V>> graph) {
		ArrayList<Interval> listInterval = new ArrayList<Interval>();
		ArrayList<V> order = this.ordering(graph);
		ComparabilityGraph<V> comparabilityGraph = new ComparabilityGraph<V>();
		SimpleGraph<V, DirectedEdge<V>> complementGraph = graph.complementaryGraph();
		if(this.checkChordalGraph(order) && comparabilityGraph.checkComparabilityGraph(complementGraph.undirectedGraph())){
			int n = order.size();
			for (int i = 0; i < n; i++) {
				double count =i+1;
				for(int k=0;k<i;k++){
					if(!graph.areNeighbors(order.get(i), order.get(k))){
						count = listInterval.get(k).max()+1;
						for(int t=k+1;t<i;t++){
							if(listInterval.get(t).min() == count){
								count++;
							}
						}
					}
				}
				double j = count + graph.degree(order.get(i))+1;
				Interval intv = new Interval(count, j);
				listInterval.add(intv);				
			}
		}
		return listInterval;
	}

}

