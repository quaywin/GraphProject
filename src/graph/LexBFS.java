package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class LexBFS<V> {
	public LexBFS() {
		
	}
	public Set<V> BFS(SimpleGraph<V,DirectedEdge<V>> graph, V s){
        Queue<V> queue = new LinkedList<V>();
        Set<V> ordering = new HashSet<>();
        queue.add(s);
        for(int i=0;i<graph.order();i++) {
			V v = queue.poll();
			ordering.add(v);
			for (V w : graph.neighbors(v)) {
				if(!ordering.contains(w)){
					queue.add(w);
				}					
			}
		}
        return ordering;
	}
	
	public ArrayList<V> LexBFSOrdering(SimpleGraph<V,DirectedEdge<V>> graph, V s) {
		//Create list order
		ArrayList<V> ordering = new ArrayList<V>();
		//Create map label for each vertex
		Map<V, ArrayList<Integer>> label = new HashMap<V, ArrayList<Integer>>();
		for (V v : graph.vertices()) {
			label.put(v,new ArrayList<Integer>());
		}
		//Get list ordering with label
		for (int i = graph.order()-1; i > -1; i--) {
			V v;
			if(!ordering.isEmpty()){
				// Get vertex with largest label
				v = getVertexWithLexicographicallyLagestLabel(graph,label);
				ordering.add(v);
			}else {
				v = s;
				ordering.add(v);
			}
			label.remove(v);
			// Set label for neighbor of each vertex
			for (V w : graph.neighbors(v)) {
				if(!ordering.contains(w)){
					label.get(w).add(i);
				}					
			}
		}
		Collections.reverse(ordering);
		return ordering;
	}
	
	private V getVertexWithLexicographicallyLagestLabel(SimpleGraph<V,DirectedEdge<V>> graph,Map<V, ArrayList<Integer>> label){
		V vertex = null;
		int largest = 0;
		// Get vertex that is lagest label. In this case we compare the first lable
		for (Map.Entry<V, ArrayList<Integer>> item : label.entrySet()) {
			if(!item.getValue().isEmpty())
			{
				if(item.getValue().get(0)>largest){
					vertex = item.getKey();
					largest = item.getValue().get(0);
				}
				if(item.getValue().get(0)==largest){
					if(graph.degree(item.getKey())>graph.degree(vertex)){
						vertex = item.getKey();
					}
				}
			}
			
		}
		return vertex;
	}
	
	
	

}
