import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {

    private final Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        digraph = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (v == w) {
            return 0;
        }

        BreadthFirstDirectedPaths bfspV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfspW = new BreadthFirstDirectedPaths(digraph, w);

        return getLength(bfspV, bfspW);
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        BreadthFirstDirectedPaths bfspV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfspW = new BreadthFirstDirectedPaths(digraph, w);

        return getAncestor(bfspV, bfspW);
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        BreadthFirstDirectedPaths bfspV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfspW = new BreadthFirstDirectedPaths(digraph, w);

        return getLength(bfspV, bfspW);
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        BreadthFirstDirectedPaths bfspV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfspW = new BreadthFirstDirectedPaths(digraph, w);

        return getAncestor(bfspV, bfspW);
    }

    private int getLength(BreadthFirstDirectedPaths bfspV, BreadthFirstDirectedPaths bfspW) {
        int length = Integer.MAX_VALUE;

        for (int i = 0; i < digraph.V(); i++) {
            if (bfspV.hasPathTo(i) && bfspW.hasPathTo(i)) {
                int len = bfspV.distTo(i) + bfspW.distTo(i);
                if (len < length) {
                    length = len;
                }
            }
        }

        return length < Integer.MAX_VALUE ? length : -1;
    }

    private int getAncestor(BreadthFirstDirectedPaths bfspV, BreadthFirstDirectedPaths bfspW) {
        int ancestor = -1;
        int length = Integer.MAX_VALUE;

        for (int i = 0; i < digraph.V(); i++) {
            if (bfspV.hasPathTo(i) && bfspW.hasPathTo(i)) {
                int len = bfspV.distTo(i) + bfspW.distTo(i);
                if (len < length) {
                    length = len;
                    ancestor = i;
                }
            }
        }

        return ancestor;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
