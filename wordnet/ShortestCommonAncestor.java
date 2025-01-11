import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class ShortestCommonAncestor {
    private Digraph digraph; // Digraph to represent

    // constructor takes a rooted DAG as argument
    public ShortestCommonAncestor(Digraph G) {
        DirectedCycle cycle = new DirectedCycle(G);
        if (cycle.hasCycle()) throw new
                IllegalArgumentException("Digraph is cyclical");
        this.digraph = new Digraph(G);
        int counter = 0;
        for (int i = 0; i < G.V(); i++)
            if (G.outdegree(i) == 0) counter++;
        if (counter != 1) throw new IllegalArgumentException("Digraph is not rooted");
    }

    // length of shortest ancestral path between v and w
    public int length(int v, int w) {
        BreadthFirstDirectedPaths a = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths b = new BreadthFirstDirectedPaths(digraph, w);
        return minimum(a, b);
    }

    // a shortest common ancestor of vertices v and w
    public int ancestor(int v, int w) {
        BreadthFirstDirectedPaths a = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths b = new BreadthFirstDirectedPaths(digraph, w);
        return minimumPoint(a, b);
    }

    // length of shortest ancestral path of vertex subsets A and B
    public int lengthSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        checkIterable(subsetA, subsetB);
        BreadthFirstDirectedPaths a = new BreadthFirstDirectedPaths(digraph, subsetA);
        BreadthFirstDirectedPaths b = new BreadthFirstDirectedPaths(digraph, subsetB);
        return minimum(a, b);
    }

    // a shortest common ancestor of vertex subsets A and B
    public int ancestorSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        checkIterable(subsetA, subsetB);
        BreadthFirstDirectedPaths a = new BreadthFirstDirectedPaths(digraph, subsetA);
        BreadthFirstDirectedPaths b = new BreadthFirstDirectedPaths(digraph, subsetB);
        return minimumPoint(a, b);
    }

    // Calculates length of shortest ancestral path
    private int minimum(BreadthFirstDirectedPaths a, BreadthFirstDirectedPaths b) {
        int minimum = Integer.MAX_VALUE;
        for (int i = 0; i < digraph.V(); i++) {
            if (a.hasPathTo(i) && b.hasPathTo(i)) {
                int distanceTo = a.distTo(i) + b.distTo(i);
                if (distanceTo < minimum) {
                    minimum = distanceTo;
                }
            }
        }
        return minimum;
    }

    // Calculates shortest common ancestor
    private int minimumPoint(BreadthFirstDirectedPaths a, BreadthFirstDirectedPaths b) {
        int minimum = Integer.MAX_VALUE;
        int minimumPoint = Integer.MAX_VALUE;
        // use own bfs
        // without iterating over all the vertices
        for (int i = 0; i < digraph.V(); i++) {
            if (a.hasPathTo(i) && b.hasPathTo(i)) {
                int distanceTo = a.distTo(i) + b.distTo(i);
                if (distanceTo < minimum) {
                    minimum = distanceTo;
                    minimumPoint = i;
                }
            }
        }
        return minimumPoint;
    }

    // Checks iterable
    private void checkIterable(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        if (subsetA == null || subsetB == null)
            throw new IllegalArgumentException("Invalid argument");
        if (!subsetA.iterator().hasNext())
            throw new IllegalArgumentException("Invalid argument");
        if (!subsetB.iterator().hasNext())
            throw new IllegalArgumentException("Invalid argument");
        for (Integer i : subsetA) {
            if (i == null || i > digraph.V())
                throw new IllegalArgumentException("Invalid argument");
        }
        for (Integer i : subsetB) {
            if (i == null || i > digraph.V())
                throw new IllegalArgumentException("Invalid argument");
        }
    }


    // unit testing (required)
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        final ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sca.length(v, w);
            int ancestor = sca.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
        Queue<Integer> subsetA = new Queue<Integer>();
        Queue<Integer> subsetB = new Queue<Integer>();
        subsetB.enqueue(0);
        subsetB.enqueue(2);

        StdOut.printf("ancestorSubset = %d, lengthSubset = %d\n",
                      sca.lengthSubset(subsetA, subsetB),
                      sca.ancestorSubset(subsetA, subsetB));
    }
}
