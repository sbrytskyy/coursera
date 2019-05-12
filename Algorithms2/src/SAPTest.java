import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class SAPTest {

    // do unit testing of this class
    public static void main(String[] args) {
        test1("resources/wordnet/digraph1.txt");
    }

    private static void test1(String filename) {
        In in = new In(filename);
        Digraph G = new Digraph(in);
        StdOut.println(G);
        StdOut.println("G.indegree(3): " + G.indegree(3));
        StdOut.println("G.indegree(11): " + G.indegree(11));
        StdOut.println("G.outdegree(3): " + G.outdegree(3));
        StdOut.println("G.outdegree(11): " + G.outdegree(11));
        printAdj(G, 5);

        int s = 3;
        testBFDP(G, s);

        
        SAP sap = new SAP(G);

        int v;
        int w;
        int length;
        int ancestor;

        v = 3;
        w = 11;
        length = sap.length(v, w);
        ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        assert length == 4;
        assert ancestor == 1;
    }

    private static void testBFDP(Digraph G, int s) {
        BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(G, s);

        for (int v = 0; v < G.V(); v++) {
            if (bfs.hasPathTo(v)) {
                StdOut.printf("%d to %d (%d):  ", s, v, bfs.distTo(v));
                for (int x : bfs.pathTo(v)) {
                    if (x == s) StdOut.print(x);
                    else        StdOut.print("->" + x);
                }
                StdOut.println();
            }

            else {
                StdOut.printf("%d to %d (-):  not connected\n", s, v);
            }

        }
    }

    private static void printAdj(Digraph G, int v) {
        List<Integer> list = new ArrayList<>();
        Iterable<Integer> adj = G.adj(v);
        for (Integer integer : adj) {
            list.add(integer);
        }
        StdOut.println("G.adj(" + v + "): " + list);
    }
}
