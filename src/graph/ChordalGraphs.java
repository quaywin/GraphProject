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
	
	private boolean checkVertexAvaiable(V vertex, ArrayList<V> listVertex,SimpleGraph<V, DirectedEdge<V>> graph){
		for (V v : graph.neighbors(vertex)) {
			if(!listVertex.contains(v)){
				return false;
			}
		}
		return true;
	}
	
	private double countNeighborIntersectYet(V vertex, ArrayList<V> listVertex,SimpleGraph<V, DirectedEdge<V>> graph){
		int count = graph.degree(vertex);
		for (V v : graph.neighbors(vertex)) {
			if(listVertex.contains(v)){
				count --;
			}
		}
		return count;
	}
	
	private int checkSameStart(Interval vertex, ArrayList<Interval> list){
		int count =0;
		for (Interval v : list) {
			if(v.min() == vertex.min()){
				vertex.setMin(vertex.min()+1);
				count = count+ checkSameStart(vertex, list);
			}
		}
		return count;
	}
	
	public ArrayList<Interval> generateFromGraphToSetInterval(SimpleGraph<V, DirectedEdge<V>> graph) {
		ArrayList<Interval> listInterval = new ArrayList<Interval>();
		ArrayList<V> listVertexToCheck = new ArrayList<V>();
		ArrayList<V> listVertexUnavailable = new ArrayList<V>();
		ArrayList<V> listVertexAvailable = new ArrayList<V>();
		ArrayList<V> order = this.ordering(graph);
		LexBFS<V> lexbfs = new LexBFS<V>();
		ComparabilityGraph<V> comparabilityGraph = new ComparabilityGraph<V>();
		SimpleGraph<V, DirectedEdge<V>> complementGraph = graph.complementaryGraph();
		V firstVertex = graph.vertices().iterator().next();
		for (V v : graph.vertices()) {
			if(graph.degree(v)<graph.degree(firstVertex)){
				firstVertex = v;
			}
		}
		
		ArrayList<V> listVertexToCompute = lexbfs.LexBFSOrdering(graph, firstVertex);
		Collections.reverse(listVertexToCompute);
		if(this.checkChordalGraph(order) && comparabilityGraph.checkComparabilityGraph(complementGraph.undirectedGraph())){
			int n = listVertexToCompute.size();
			for (int i = 0; i < n; i++) {
				int count = i+1;
				listVertexToCheck.add(listVertexToCompute.get(i));
				if(listInterval.isEmpty()){
					Interval intv = new Interval(count,count+graph.degree(listVertexToCompute.get(i))+1,listVertexToCompute.get(i).toString());
					listInterval.add(intv);	
					listVertexAvailable.add(listVertexToCompute.get(i));
				}else{
					if(checkVertexAvaiable(listVertexToCompute.get(i), listVertexToCheck, graph)){
						Interval intv = new Interval(listInterval.get(listInterval.size()-1).max()+1,listInterval.get(listInterval.size()-1).max()+graph.degree(listVertexToCompute.get(i))+1,listVertexToCompute.get(i).toString());
						listInterval.add(intv);	
						listVertexAvailable.add(listVertexToCompute.get(i));
					}else {
						listVertexUnavailable.add(listVertexToCompute.get(i));
					}
					ArrayList<V> listRemove = new ArrayList<V>();
					for (V v : listVertexUnavailable) {
						if(checkVertexAvaiable(v, listVertexToCheck, graph)){
							boolean check=false;
							for (int j = listVertexAvailable.size()-1; j > -1; j--) {		
								if(!graph.areNeighbors(v, listVertexAvailable.get(j))){									
									Interval intv = new Interval(listInterval.get(j).max()+1,listInterval.get(listInterval.size()-1).max()+countNeighborIntersectYet(v, listVertexAvailable, graph),v.toString());
									if(checkSameStart(intv, listInterval)>0){
										intv.setMin(intv.min()+checkSameStart(intv, listInterval));
									}
									if(countNeighborIntersectYet(v, listVertexAvailable, graph)==0){
										intv.setMax(intv.max()+1);
									}
									listInterval.add(intv);	
									check = true;
									break;
								}
							}
							if(check==false){
								Interval intv = new Interval(listInterval.get(0).min()+1,listInterval.get(listInterval.size()-1).max()+countNeighborIntersectYet(v, listVertexAvailable, graph)+1,v.toString());
								listInterval.add(intv);
							}
							listVertexAvailable.add(v);
							listRemove.add(v);
						}
					}
					if(!listRemove.isEmpty()){
						for (V v : listRemove) {
							listVertexUnavailable.remove(v);
						}
					}
				}		
			}
		}
		return listInterval;
	}

}

