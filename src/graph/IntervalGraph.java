package graph;

import java.util.Collection;
import java.util.Set;

public class IntervalGraph<E extends Graph.Edge<Interval>> implements Graph<Interval, E> {
	private SimpleGraph<Interval, E> delegate = new SimpleGraph<Interval, E>();
	public <V, E extends Graph.Edge<Interval>> SimpleGraph<Interval,DirectedEdge<Interval>> generateGraph() {
		SimpleGraph<Interval,DirectedEdge<Interval>> graph = new SimpleGraph<Interval,DirectedEdge<Interval>>();
		DirectedEdge<Interval> edge;
		for (Interval interval : this.vertices()) {
			graph.addVertex(interval);
		}
		for (Interval interval_s : this.vertices()) {
			for (Interval interval_t : this.vertices()) {
				if(interval_s.intersect(interval_t) && !interval_s.equals(interval_t)){
					if(!graph.areNeighbors(interval_s, interval_t)){
						edge = new DirectedEdge<Interval>(interval_s,interval_t);
						graph.addEdge(edge);
					}
				}
			}
		}
		return graph;
	}	
	@Override
	public boolean addEdge(E edge) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addVertex(Interval vertex) {
		// TODO Auto-generated method stub
		return delegate.addVertex(vertex);
	}

	@Override
	public boolean areNeighbors(Interval vertex1, Interval vertex2) {
		// TODO Auto-generated method stub
		return delegate.areNeighbors(vertex1, vertex2);
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		delegate.clear();
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return delegate.isEmpty();
	}

	@Override
	public boolean containsVertex(Interval vertex) {
		// TODO Auto-generated method stub
		return delegate.containsVertex(vertex);
	}

	@Override
	public boolean containsEdge(E edge) {
		// TODO Auto-generated method stub
		return delegate.containsEdge(edge);
	}

	@Override
	public boolean removeEdge(E edge) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeVertex(Interval vertex) {
		// TODO Auto-generated method stub
		return delegate.removeVertex(vertex);
	}

	@Override
	public Set<Interval> vertices() {
		// TODO Auto-generated method stub
		return delegate.vertices();
	}

	@Override
	public Set<E> edges() {
		// TODO Auto-generated method stub
		return delegate.edges();
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return delegate.size();
	}

	@Override
	public int order() {
		// TODO Auto-generated method stub
		return delegate.order();
	}

	@Override
	public int degree(Interval vertex) {
		// TODO Auto-generated method stub
		return degree(vertex);
	}

	@Override
	public int indegree(Interval vertex) {
		// TODO Auto-generated method stub
		return delegate.indegree(vertex);
	}

	@Override
	public int outdegree(Interval vertex) {
		// TODO Auto-generated method stub
		return delegate.outdegree(vertex);
	}

	@Override
	public Iterable<E> incidentEdges(Interval vertex) {
		// TODO Auto-generated method stub
		return delegate.incidentEdges(vertex);
	}

	@Override
	public Iterable<E> incidentEdges(Interval vertex1, Interval vertex2) {
		// TODO Auto-generated method stub
		return delegate.incidentEdges(vertex1, vertex2);
	}

	@Override
	public Iterable<E> incomingEdges(Interval vertex) {
		// TODO Auto-generated method stub
		return delegate.incomingEdges(vertex);
	}

	@Override
	public Iterable<E> outgoingEdges(Interval vertex) {
		// TODO Auto-generated method stub
		return delegate.outgoingEdges(vertex);
	}

	@Override
	public Iterable<E> outgoingEdges(Interval source, Interval target) {
		// TODO Auto-generated method stub
		return delegate.outgoingEdges(source, target);
	}

	@Override
	public Iterable<Interval> neighbors(Interval vertex) {
		// TODO Auto-generated method stub
		return delegate.neighbors(vertex);
	}

	@Override
	public Iterable<Interval> predecessors(Interval vertex) {
		// TODO Auto-generated method stub
		return delegate.predecessors(vertex);
	}

	@Override
	public Iterable<Interval> successors(Interval vertex) {
		// TODO Auto-generated method stub
		return delegate.successors(vertex);
	}

	@Override
	public boolean removeAllEdges(Collection<E> edges) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAllVertices(Collection<Interval> vertices) {
		// TODO Auto-generated method stub
		return delegate.removeAllVertices(vertices);
	}

	@Override
	public InducedSubgraph<Interval, E> inducedSubgraph(Set<Interval> vertices) {
		// TODO Auto-generated method stub
		return delegate.inducedSubgraph(vertices);
	}

	@Override
	public PartialGraph<Interval, E> partialGraph(Set<E> edges) {
		// TODO Auto-generated method stub
		return delegate.partialGraph(edges);
	}

	@Override
	public Subgraph<Interval, E> subgraph(Set<Interval> vertices, Set<E> edges) {
		// TODO Auto-generated method stub
		return delegate.subgraph(vertices, edges);
	}

}
