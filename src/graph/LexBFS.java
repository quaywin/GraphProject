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
				v = getVertexWithLexicographicallyLagestLabel(label);
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
	
	private V getVertexWithLexicographicallyLagestLabel(Map<V, ArrayList<Integer>> label){
		V vertex = null;
		int largest = 0;
		// Get vertex that is lagest label. In this case we compare the first lable
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
		//Create map parent with verter is neighbor that nearlest in right
		Map<V, V> parent = new HashMap<V, V>();
		//Creat map RN with list vertex of neighbor in right
		Map<V, Set<V>> RN = new HashMap<V, Set<V>>();
		// Initial map parent and RN
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
			
			if(parent.get(v)!=null){
				RN.put(v, termRN);
			}
			
		}
		// Check list ordering
		for (V v : graph.vertices()) {
			if(parent.get(v)!=null){
				Set<V> xRN = new HashSet<V>();
				for (V nei : graph.neighbors(parent.get(v))) {
					if(ordering.indexOf(nei)>ordering.indexOf(v)){
						xRN.add(nei);
					}
				}
				// Initial list vertex is neighbor in right of parent
				// Remove element parent in list RN
				RN.get(v).remove(parent.get(v));
				// Check condition for list
				if(!RN.get(v).isEmpty()){
					for (V rn : RN.get(v)) {
						if(!xRN.contains(rn))
							return false;
					}
				}
				
			}
		}
		return true;
	}
	private boolean checkInClique(SimpleGraph<V,DirectedEdge<V>> graph,Set<V> set,V vertex) {
		for (V v : set) {
			if(!graph.areNeighbors(vertex, v)){
				return false;
			}
		}
		return true;
	}

}
