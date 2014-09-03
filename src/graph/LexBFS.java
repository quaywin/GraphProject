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
		ArrayList<V> ordering = new ArrayList<V>();
		Map<V, ArrayList<Integer>> label = new HashMap<V, ArrayList<Integer>>();
		for (V v : graph.vertices()) {
			label.put(v,new ArrayList<Integer>());
		}
		for (int i = graph.order()-1; i > -1; i--) {
			V v;
			if(!ordering.isEmpty()){
				v = getVertexWithLexicographicallyLagestLabel(label);
				ordering.add(v);
			}else {
				v = s;
				ordering.add(v);
			}
			label.remove(v);
			for (V w : graph.neighbors(v)) {
				if(!ordering.contains(w)){
					label.get(w).add(i);
				}					
			}
		}
		Collections.reverse(ordering);
		return ordering;
	}
	
	private V getVertexWithLexicographicallyLagestLabel(Map<V, ArrayList<Integer>> label){
		V vertex = null;
		int largest = 0;
		for (Map.Entry<V, ArrayList<Integer>> item : label.entrySet()) {
			if(!item.getValue().isEmpty())
				if(item.getValue().get(0)>largest){
					vertex = item.getKey();
					largest = item.getValue().get(0);
				}			
		}
		return vertex;
	}
	
	public boolean checkIsPerfectEliminationOdering(SimpleGraph<V,DirectedEdge<V>> graph,ArrayList<V> ordering) {
		Map<V, V> parent = new HashMap<V, V>();
		Map<V, Set<V>> RN = new HashMap<V, Set<V>>();	
		for (V v : graph.vertices()) {
			Set<V> termRN = new HashSet<V>();
			for (V nei : graph.neighbors(v)) {
				if(ordering.indexOf(nei)>ordering.indexOf(v)){
					termRN.add(nei);
					if(parent.get(v)==null)
						parent.put(v, nei);
					else if(ordering.indexOf(nei)<ordering.indexOf(parent.get(v))){
						parent.put(v, nei);
					}
				}
			}
			RN.put(v, termRN);			
		}
		for (V v : graph.vertices()) {
			Set<V> parentRN = new HashSet<V>();
			if(parent.get(v)!=null){
				for (V nei : graph.neighbors(parent.get(v))) {
					if(ordering.indexOf(nei)>ordering.indexOf(v)){
						parentRN.add(nei);
					}
				}
				RN.get(v).remove(parent.get(v));
				for (V rn : RN.get(v)) {
					if(!parentRN.contains(rn))
						return false;
				}
			}
		}
		return true;
	}

}
