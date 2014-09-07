package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ComparabilityGraph<V> {
	public ComparabilityGraph() {
		
	}
	
	public boolean checkComparabilityGraph(SimpleGraph<V, DirectedEdge<V>> graph) {
		//Create a undirected graph
		SimpleGraph<V, DirectedEdge<V>> undirectedGraph = graph;
		//Create a directed graph
		SimpleGraph<V, DirectedEdge<V>> directGraph = new SimpleGraph<V, DirectedEdge<V>>();
		for (V v : graph.vertices()) {
			directGraph.addVertex(v);
		}
		// List Edge Implicant and EdgeToFindImplicant
		Set<DirectedEdge<V>> listEdgeImplicant = new HashSet<DirectedEdge<V>>();
		Set<DirectedEdge<V>> listEdgeToFindImplicant = new HashSet<DirectedEdge<V>>();
		// Initial arbitrarily Edge
		DirectedEdge<V> arbitrarilyEdge = undirectedGraph.edges().iterator().next();
		// Add this edge to directed graph
		directGraph.addEdge(arbitrarilyEdge);
		// Add this edge to list edge to find implicant
		listEdgeToFindImplicant.add(arbitrarilyEdge);
		// Remove this edge in undirected graph 
		undirectedGraph.removeEdge(arbitrarilyEdge);
		for (DirectedEdge<V> directedEdge : undirectedGraph.incomingEdges(arbitrarilyEdge.source())) {
			if(directedEdge.source().equals(arbitrarilyEdge.target())){
				undirectedGraph.removeEdge(directedEdge);
				break;
			}
		}
		
		while (!listEdgeToFindImplicant.isEmpty()) {	
			// get an edge is a first edge in listEdgeToFindImplicant
			arbitrarilyEdge = listEdgeToFindImplicant.iterator().next();
			// then remove it in listEdgeToFindImplicant
			listEdgeToFindImplicant.remove(arbitrarilyEdge);
			// Find list listEdgeImplicant
			listEdgeImplicant = ListEdgeImplicant(undirectedGraph,arbitrarilyEdge);	
			if(!listEdgeImplicant.isEmpty()){
				for (DirectedEdge<V> edge : listEdgeImplicant) {
					// add edge in listEdgeImplicant into directed graph
					directGraph.addEdge(edge);
					// add edge in listEdgeImplicant into listEdgeToFindImplicant
					listEdgeToFindImplicant.add(edge);
					// remove this add on undirected graph
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
		// check directly for directed graph
		System.out.println();
		System.out.print("direct edges =");
		for (DirectedEdge<V> e : directGraph.edges())
			System.out.print(" (" + e.source() + "," + e.target() + ")");
		System.out.println();
		if(testDirectForGraph(directGraph)){
			
			// if still edge remain in undirected graph, we call check comparability for this remain part graph
			if(!undirectedGraph.edges().isEmpty()){
				return checkComparabilityGraph(undirectedGraph);
			}
			return true;
		}		
		return false;
	}
	
	
	//test direct for graph
	private boolean testDirectForGraph(SimpleGraph<V, DirectedEdge<V>> directGraph){
		// Create  map for list vertex out going of vertex
		Map<V,Set<V>> mapVertexOutGoing =  new HashMap<V,Set<V>>();
		// Create map for list vertex of list vertex out going of vertex
		Map<V,Set<V>> mapVertexW=  new HashMap<V,Set<V>>();	
		// Initial Map out going
		for (V v : directGraph.vertices()) {
			mapVertexOutGoing.put(v, new HashSet<V>());
			for (DirectedEdge<V> edge : directGraph.outgoingEdges(v)) {
				mapVertexOutGoing.get(v).add(edge.target());
			}
		}
		
		//Initial map W
		for (V v : directGraph.vertices()) {
			mapVertexW.put(v, new HashSet<V>());
			for (V v_v : mapVertexOutGoing.get(v)) {
				for (V v_w : mapVertexOutGoing.get(v_v)) {
					mapVertexW.get(v).add(v_w);
				}
			}
		}
		
		// check it
		for (V v : directGraph.vertices()) {
			for (V v_w : mapVertexW.get(v)) {
				if(!mapVertexOutGoing.get(v).contains(v_w)){
					return false;
				}
			}
		}
		
		return true;
	}
	
	// Get list edge implicant
	private Set<DirectedEdge<V>> ListEdgeImplicant(SimpleGraph<V, DirectedEdge<V>> graph,DirectedEdge<V> edge) {
		
		Set<DirectedEdge<V>> listDirectedEdge = new HashSet<DirectedEdge<V>>();
		// add edge neighbor from source of current edge
		for (DirectedEdge<V> neiFormSource : graph.outgoingEdges(edge.source())) {
			if(!graph.areNeighbors(neiFormSource.target(),edge.target())){
				listDirectedEdge.add(neiFormSource);
			}
		}
		// add edge neighbor from target of current edge
		for (DirectedEdge<V> neiFormTarget : graph.incomingEdges(edge.target())) {
			if(!graph.areNeighbors(neiFormTarget.source(),edge.source())){
				listDirectedEdge.add(neiFormTarget);
			}
		}
		return listDirectedEdge;
	}
}
