package edu.bvu.algo;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import edu.bvu.algo.Graph;

public class AdjListGraph implements Graph {
	private Map<String, List<String>> g = new HashMap<String, List<String>>();
	private int numEdges = 0;
	
	/**
	 * Adds <code>node</code> to this graph.
	 * <code>node</code> needs to be a unique name.
	 * @param node the label for the node
	 * @throws IllegalArgumentException if <code>node</code>
	 *        is not unique
	 */
	@Override
	public void addNode(String node) {
		if (g.containsKey(node)) {
			throw new IllegalArgumentException(
					node + " already exists in this graph.");
		}
		
		g.put(node, new ArrayList<String>());
	}

	/**
	 * Adds a directed edge between <code>a</code>
	 * and <code>b</code>.  If either <code>a</code> or
	 * <code>b</code> do not already exist, this
	 * <code>Graph</code> will perform <code>addNode(a)</code>
	 * or <code>addNode(b)</code> accordingly.
	 * @param a the label for the edge's source node
	 * @param b the label for the edge's destination node
	 */
	@Override
	public void addEdge(String a, String b) {
		if (!g.containsKey(a))
			addNode(a);
		
		if (!g.containsKey(b))
			addNode(b);
		
		g.get(a).add(b);
		numEdges++;
	}

	@Override
	public Boolean containsNode(String node) {
		return g.containsKey(node);
	}

	@Override
	public Boolean containsEdge(String a, String b) {
		return g.get(a).contains(b);
	}

	@Override
	public Integer numberOfNodes() {
		return g.size();
	}

	@Override
	public Integer numberOfEdges() {
		return numEdges;
	}

	@Override
	public Map<String, GraphTuple> bfs(String startNode) {
		
		Map<String, Boolean> visited = new HashMap<String, Boolean>();
		Map<String, GraphTuple> m = new HashMap<String, GraphTuple>();
		
		for (String v : g.keySet()) {
			visited.put(v, false);
			GraphTuple tuple = new GraphTuple();
			m.put(v, tuple);
		}
		visited.put(startNode, true);
		m.get(startNode).d = 0;
				
		Queue<String> q = new ArrayDeque<String>();
		q.add(startNode);
		
		while (!q.isEmpty()) {
			String u = q.remove();
			
			for (String v : g.get(u)) {
				if (!visited.get(v)) {
					visited.put(v, true);
					m.get(v).d = m.get(u).d + 1;
					m.get(v).p = u;
					q.add(v);
				}
			}
			
		}
		
		return m;
	}
	
	public static void main(String[] args) {
		Graph myGraph = new AdjListGraph();
		
		/*
		   _____________
		  /             v
		 a ----> b ----> d ----> e
          \              ^
		   \            /
		    ---> c -----
		 
		 */
		
		myGraph.addNode("a");
		myGraph.addNode("b");
		myGraph.addEdge("a", "b");
		myGraph.addEdge("b", "d");
		myGraph.addEdge("a", "c");
		myGraph.addEdge("c", "d");
		myGraph.addEdge("a", "d");
		myGraph.addEdge("d", "e");
		
		Map<String, GraphTuple> gtmap = myGraph.bfs("a");
		
		System.out.println(gtmap);

		/*
		 {a=<GraphTuple(d=0,p=null)>,
		  b=<GraphTuple(d=1,p=a)>,
		  c=<GraphTuple(d=1,p=a)>,
		  d=<GraphTuple(d=1,p=a)>,
		  e=<GraphTuple(d=2,p=d)>}
		 */

	}

}
