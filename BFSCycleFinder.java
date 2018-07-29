
package csc403;
import algs13.Queue;
import algs13.Stack;
import stdlib.*;
/* ***********************************************************************
 *
 *  Use breadth first search on an undirected graph to determine if it has 
 *  a cycle.  
 *************************************************************************/

public class BFSCycleFinder {

	private final boolean[] marked;  // marked[v] = is there an s-v path
	private final int[] edgeTo;      // edgeTo[v] = previous edge on shortest s-v path

	private boolean hasCycle;	     
	private Queue<Integer> aCycle;   // vertices that make up a found cycle

	public boolean hasCycle() { return hasCycle; }
	public Iterable<Integer> cycleVerts() {return aCycle; }

	// constructor
	public BFSCycleFinder(Graph G,int s) {
		marked = new boolean[G.V()];
		edgeTo = new int[G.V()];
		aCycle = new Queue<Integer>();
		bfs(G, s);  // search from s
	}

	// BFS from single source
	private void bfs(Graph G, int s) {
		Queue<Integer> q = new Queue<>();      // to implement BFS from this vertex

		marked[s] = true;
		q.enqueue(s);

		while (!q.isEmpty()) {
			int v = q.dequeue();
			for (int w : G.adj(v)) {
				if (!marked[w]) {
					edgeTo[w] = v;
					marked[w] = true;
					q.enqueue(w);
				}
				else if ( marked[w] && edgeTo[v] != w)  {
					generateCycle(  s, v, w);
					return;
				}

			}
		}
	}
	
	private void generateCycle( int s,int v,int w) { // a cycle has been found
		hasCycle = true;

		Stack<Integer> p1 = this.pathTo(s, v);  // Stack of nodes from v back to s
		Stack<Integer> p2 = this.pathTo(s, w);  // Stack of nodes from w back to s

		int p1t = p1.peek();             // find out where paths from s-v, s-w diverge
		int p2t = p2.peek();
		int last = p1t;             // last is the last value the two stack had in common
		while (  p1t == p2t) {      // pop the stack while the tops are the same
			last = p1t;
			p1.pop();
			p2.pop();
			p1t = p1.peek();
			p2t = p2.peek();
		}
		//  build the path.  
		aCycle.enqueue(last);     // add the vertex in common 
		while (! p1.isEmpty())    //  while path1 stack is not empty, add the nodes to the path
			aCycle.enqueue(p1.pop());

		while ( w != last) {     // use edgeTo array to add nodes from w back to the common node
			aCycle.enqueue(w);
			w = edgeTo[w];
		}
		aCycle.enqueue(last);    // add the common node again, for style reasons

	}

	//  path between s (or sources) and v; null if no such path
	public Stack<Integer> pathTo(int s, int v) {
		Stack<Integer> path = new Stack<Integer>();
		int x;
		for (x = v; x != s; x = edgeTo[x])
			path.push(x);
		path.push(x);
		return path;
	}


	// test client
	public static void main(String[] args) {
		args = new String [] { "data/tinyAG.txt", "0"};
		//args = new String [] { "data/tinyG.txt", "0" };

		In in = new In(args[0]);
		Graph G = new Graph(in);
		//G = GraphGenerator.path(8);
		//G = GraphGenerator.binaryTree(8);
		//G = GraphGenerator.complete(6);
		G = GraphGenerator.cycle(6);
		StdOut.println(G);
		G.toGraphviz ("g.png");   // comment out if GraphViz not installed

		BFSCycleFinder aBfsCycle = new BFSCycleFinder(G,0);

		if ( aBfsCycle.hasCycle() ) {
			StdOut.format(" graph has a cycle\n ");
			for (int x : aBfsCycle.cycleVerts()) {
				StdOut.format(" %d ", x);
			}
			StdOut.println("\n");
		}
		else 
			StdOut.format(" graph is acyclic\n ");

	}
}
