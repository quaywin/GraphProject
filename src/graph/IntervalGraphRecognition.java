package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class IntervalGraphRecognition<V> {
	public ArrayList<V> ordering(SimpleGraph<V, DirectedEdge<V>> graph) {
		LexBFS<V> lexbfs = new LexBFS<V>();		
		ChordalGraphs<V> chordalGraph = new ChordalGraphs<V>();
		for (V v : graph.vertices()) {			
			if(chordalGraph.checkIsPerfectEliminationOdering(graph, lexbfs.LexBFSOrdering(graph, v))){
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
		int count = graph.degree(vertex)+1;
		for (V v : graph.neighbors(vertex)) {
			if(listVertex.contains(v)){
				count --;
			}else{
				if(graph.areNeighbors(v, listVertex.get(listVertex.size()-1))){
					count--;
				}
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
						Interval intv = new Interval(listInterval.get(listInterval.size()-1).max()+1,listInterval.get(listInterval.size()-1).max()+countNeighborIntersectYet(listVertexToCompute.get(i), listVertexAvailable, graph)+1,listVertexToCompute.get(i).toString());
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
									listInterval.add(intv);	
									check = true;
									break;
								}
							}
							if(check==false){
								Interval intv = new Interval(listInterval.get(0).min()+1,listInterval.get(listInterval.size()-1).max()+countNeighborIntersectYet(v, listVertexAvailable, graph),v.toString());
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
	
	private Map<Integer,ArrayList<Interval>> getMapInterval(ArrayList<Interval> listInterval) {
		Collections.reverse(listInterval);
		int count = (int)listInterval.get(0).max();
		Map<Integer,ArrayList<Interval>> setIntervalIndependent = new TreeMap<Integer, ArrayList<Interval>>(Collections.reverseOrder());
		for (Interval interval : listInterval) {
			if(setIntervalIndependent.isEmpty()){
				setIntervalIndependent.put(0, new ArrayList<Interval>());
				setIntervalIndependent.get(0).add(interval);
			}else{
				boolean intersect = true;
				for (Map.Entry<Integer, ArrayList<Interval>> item : setIntervalIndependent.entrySet()) {
					if(!item.getValue().isEmpty())
					{
						boolean addInterval = true;
						for (Interval itv : item.getValue()) {
							if(interval.intersect(itv)){
								addInterval = false;
								break;
							}
						}
						if(addInterval){
							item.getValue().add(interval);
							intersect = false;
							break;
						}
					}			
				}
				if(intersect){
					setIntervalIndependent.put(count-(int)interval.max(), new ArrayList<Interval>());
					setIntervalIndependent.get(count-(int)interval.max()).add(interval);
				}
			}
		}
		return setIntervalIndependent;
	}
	
	public void printIntervalGraph(ArrayList<Interval> listInterval) {
		Map<Integer,ArrayList<Interval>> mapInterval = getMapInterval(listInterval);
		for (Map.Entry<Integer, ArrayList<Interval>> item : mapInterval.entrySet()) {
			for (int i = 0; i < item.getKey()+10; i++) {
				System.out.print(" ");
			}
			for (int i=0;i<item.getValue().size();i++) {
				if(i>0){
					int temp = (int)item.getValue().get(i-1).min() - (int)item.getValue().get(i).max();
					for (int j = 0; j < temp; j++) {
						System.out.print(" ");
					}
				}
				item.getValue().get(i).printInterval();
			}		
			System.out.println();
		}
	}
	
	public ArrayList<Interval> reverse(ArrayList<Interval> listInterval) {
		double max = listInterval.get(listInterval.size()-1).max()+1;
		ArrayList<Interval> listIntervalReverse = new ArrayList<Interval>();
		for (int i = listInterval.size()-1; i >-1; i--) {
			Interval intv = new Interval(max-listInterval.get(i).max(), max-listInterval.get(i).min(), listInterval.get(i).name());
			listIntervalReverse.add(intv);
		}
		return listIntervalReverse;
	}
}
