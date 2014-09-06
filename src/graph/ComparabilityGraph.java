package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ComparabilityGraph<V> {
	public ComparabilityGraph() {
		
	}
	
	public boolean checkComparabilityGraph(MultiGraph<V, DirectedEdge<V>> graph) {
		MultiGraph<V, DirectedEdge<V>> undirectedGraph = new MultiGraph<V, DirectedEdge<V>>();
		undirectedGraph = graph;
		MultiGraph<V, DirectedEdge<V>> directGraph = new MultiGraph<V, DirectedEdge<V>>();
		for (V v : graph.vertices()) {
			directGraph.addVertex(v);
		}
		Set<DirectedEdge<V>> listEdgeImplicant = new HashSet<DirectedEdge<V>>();
		Set<DirectedEdge<V>> listEdgeToFindImplicant = new HashSet<DirectedEdge<V>>();
		DirectedEdge<V> arbitrarilyEdge = undirectedGraph.edges().iterator().next();
		directGraph.addEdge(arbitrarilyEdge);
		listEdgeToFindImplicant.add(arbitrarilyEdge);
		undirectedGraph.removeEdge(arbitrarilyEdge);
		for (DirectedEdge<V> directedEdge : undirectedGraph.incomingEdges(arbitrarilyEdge.source())) {
			if(directedEdge.source().equals(arbitrarilyEdge.target())){
				undirectedGraph.removeEdge(directedEdge);
				break;
			}
		}
		
		while (!listEdgeToFindImplicant.isEmpty()) {			
			arbitrarilyEdge = listEdgeToFindImplicant.iterator().next();
			listEdgeToFindImplicant.remove(arbitrarilyEdge);
			listEdgeImplicant = ListEdgeImplicant(undirectedGraph,arbitrarilyEdge);	
			if(!listEdgeImplicant.isEmpty()){
				for (DirectedEdge<V> edge : listEdgeImplicant) {
					directGraph.addEdge(edge);
					listEdgeToFindImplicant.add(edge);
					undirectedGraph.removeEdge(edge);
					for (DirectedEdge<V> directedEdge : undirectedGraph.incomingEdges(edge.source())) {
						if(directedEdge.source().equals(edge.target())){
							undirectedGraph.removeEdge(directedEdge);
							break;
						}
					}
				}
			}
		}
		
		if(testDirectForGraph(directGraph)){
			if(!undirectedGraph.edges().isEmpty()){
				return checkComparabilityGraph(undirectedGraph);
			}
			return true;
		}		
		return false;
	}
	
	private boolean testDirectForGraph(MultiGraph<V, DirectedEdge<V>> directGraph){
		Map<V,Set<V>> mapVertexOutGoing =  new HashMap<V,Set<V>>();
		Map<V,Set<V>> mapVertexW=  new HashMap<V,Set<V>>();	
		for (V v : directGraph.vertices()) {
			mapVertexOutGoing.put(v, new HashSet<V>());
			for (DirectedEdge<V> edge : directGraph.outgoingEdges(v)) {
				mapVertexOutGoing.get(v).add(edge.target());
			}
		}
		
		for (V v : directGraph.vertices()) {
			mapVertexW.put(v, new HashSet<V>());
			for (V v_v : mapVertexOutGoing.get(v)) {
				for (V v_w : mapVertexOutGoing.get(v_v)) {
					mapVertexW.get(v).add(v_w);
				}
			}
		}
		
		for (V v : directGraph.vertices()) {
			for (V v_w : mapVertexW.get(v)) {
				if(!mapVertexOutGoing.get(v).contains(v_w)){
					return false;
				}
			}
		}
		
		return true;
	}
	
	private Set<DirectedEdge<V>> ListEdgeImplicant(MultiGraph<V, DirectedEdge<V>> graph,DirectedEdge<V> edge) {
		Set<DirectedEdge<V>> listDirectedEdge = new HashSet<DirectedEdge<V>>();
		for (DirectedEdge<V> neiFormSource : graph.outgoingEdges(edge.source())) {
			if(!graph.areNeighbors(neiFormSource.target(),edge.target())){
				listDirectedEdge.add(neiFormSource);
			}
		}
		for (DirectedEdge<V> neiFormTarget : graph.incomingEdges(edge.target())) {
			if(!graph.areNeighbors(neiFormTarget.source(),edge.source())){
				listDirectedEdge.add(neiFormTarget);
			}
		}
		return listDirectedEdge;
	}
}
