package graph;



import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SimpleGraph<V, E extends Graph.Edge<V>> implements Graph<V, E> {
	
	private Edges edges = new Edges();
	private Vertices vertices = new Vertices();
	private Map<V, Set<V>> neighborsMap = new HashMap<V, Set<V>>();

	private class VertexOrEdgeIterator<T> implements Iterator<T> {

		Iterator<T> delegate;

		VertexOrEdgeIterator(Iterator<T> it) {
			delegate = it;
		}

		VertexOrEdgeIterator(Iterable<T> iterable) {
			delegate = iterable.iterator();
		}

		public boolean hasNext() {
			return delegate.hasNext();
		}

		public T next() {
			return delegate.next();
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	private class VertexIterator extends VertexOrEdgeIterator<V> {

		VertexIterator(Iterator<V> it) {
			super(it);
		}

		VertexIterator(Iterable<V> iterable) {
			super(iterable);
		}
	}
	
	private class EdgeIterator extends VertexOrEdgeIterator<E> {

		EdgeIterator(Iterator<E> it) {
			super(it);
		}

		EdgeIterator(Iterable<E> iterable) {
			super(iterable);
		}
	}
	
	private class VertexOrEdgeIterable<T> implements Iterable<T> {

		Iterable<T> delegate;

		VertexOrEdgeIterable(Iterable<T> iterable) {
			delegate = iterable;
		}

		@Override
		public Iterator<T> iterator() {
			return new VertexOrEdgeIterator<T>(delegate);
		}
	}
	
	private class VertexIterable extends VertexOrEdgeIterable<V> {
		VertexIterable(Iterable<V> iterable) {
			super(iterable);
		}
	}
	
	private class Vertices extends HashSet<V> {

		public boolean add(V v) {
			boolean modified = super.add(v);
			if(modified){
				neighborsMap.put(v, new HashSet<V>());
			}
			return modified;
		}

		public boolean remove(Object o) {
			boolean modified = super.remove(o);
			if(modified){
				neighborsMap.remove(o);
			}
			return modified;
		}

		public void clear() {
			super.clear();
		}

		public Iterator<V> iterator() {
			return new VertexIterator(super.iterator());
		}
	}
	
	private class Edges extends HashSet<E> {
		private boolean check(E e){
			return vertices.contains(e.source())&&vertices.contains(e.target());
		}
		public boolean add(E e){
			boolean modified=false;
			if(check(e)){
				modified=super.add(e);
				if(modified){
					neighborsMap.get(e.source()).add(e.target());
					neighborsMap.get(e.target()).add(e.source());
				}				
			}
			
			return modified;	 
		}
		public boolean remove(E e){
			boolean modified = super.remove(e);
			if(check(e)){
				if(modified){
					V source = e.source();
					V target = e.target();
					neighborsMap.get(source).remove(target);
					neighborsMap.get(target).remove(source);
				}
			}
			return modified;
		}
		public void clear(){
			super.clear();
		}
		public Iterator<E> iterator() {
			return new EdgeIterator(super.iterator());
		}
	}
	
	public SimpleGraph<V,DirectedEdge<V>> complementaryGraph() {
		SimpleGraph<V,DirectedEdge<V>> graph = new SimpleGraph<V,DirectedEdge<V>>();
		DirectedEdge<V> edge;
		for (V v : this.vertices()) {
			graph.addVertex(v);
		}
		for (V v_s : this.vertices()) {
			for (V v_t : this.vertices()) {
				if(!this.areNeighbors(v_s, v_t) && !v_s.equals(v_t)){
					if(!graph.areNeighbors(v_s, v_t)){
						edge = new DirectedEdge<V>(v_s,v_t);
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
		if(!this.areNeighbors(edge.source(), edge.target()) && !edge.source().equals(edge.target()))
			return edges.add(edge);
		return false;
	}

	@Override
	public boolean addVertex(V vertex) {
		// TODO Auto-generated method stub
		return vertices.add(vertex);
	}
	
	private boolean checkExistingVertex(Object o) {
		return vertices.contains(o);
	}

	@Override
	public boolean areNeighbors(V vertex1, V vertex2) {
		// TODO Auto-generated method stub
		return checkExistingVertex(vertex1)&&checkExistingVertex(vertex2)?neighborsMap.get(vertex1).contains(vertex2):false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		vertices.clear();
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return vertices.isEmpty();
	}

	@Override
	public boolean containsVertex(V vertex) {
		// TODO Auto-generated method stub
		return vertices.contains(vertex);
	}

	@Override
	public boolean containsEdge(E edge) {
		// TODO Auto-generated method stub
		return edges.contains(edge);
	}

	@Override
	public boolean removeEdge(E edge) {
		// TODO Auto-generated method stub
		return edges.remove(edge);
	}

	@Override
	public boolean removeVertex(V vertex) {
		// TODO Auto-generated method stub
		return vertices.remove(vertex);
	}

	@Override
	public Set<V> vertices() {
		// TODO Auto-generated method stub
		return vertices;
	}

	@Override
	public Set<E> edges() {
		// TODO Auto-generated method stub
		return edges;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return edges.size();
	}

	@Override
	public int order() {
		// TODO Auto-generated method stub
		return vertices.size();
	}

	@Override
	public int degree(V vertex) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int indegree(V vertex) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int outdegree(V vertex) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterable<E> incidentEdges(V vertex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<E> incidentEdges(V vertex1, V vertex2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<E> incomingEdges(V vertex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<E> outgoingEdges(V vertex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<E> outgoingEdges(V source, V target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<V> neighbors(V vertex) {
		if(checkExistingVertex(vertex)){
			return new VertexIterable(neighborsMap.get(vertex));	
		}
		return null;
	}

	@Override
	public Iterable<V> predecessors(V vertex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<V> successors(V vertex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeAllEdges(Collection<E> edges) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAllVertices(Collection<V> vertices) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public InducedSubgraph<V, E> inducedSubgraph(Set<V> vertices) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PartialGraph<V, E> partialGraph(Set<E> edges) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Subgraph<V, E> subgraph(Set<V> vertices, Set<E> edges) {
		// TODO Auto-generated method stub
		return null;
	}

}
