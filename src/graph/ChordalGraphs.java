package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ChordalGraphs<V>{
	public  ChordalGraphs() {
		
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

}

