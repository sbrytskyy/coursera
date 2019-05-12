import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class SAPTest {

    // do unit testing of this class
    public static void main(String[] args) {
        test1("resources/wordnet/digraph1.txt", 3, 11, 4, 1);
        test1("resources/wordnet/digraph1.txt", 9, 11, 3, 5);
        test1("resources/wordnet/digraph1.txt", 7, 2, 4, 0);
        test1("resources/wordnet/digraph1.txt", 1, 6, -1, -1);
        test1("resources/wordnet/digraph2.txt", 1, 5, 2, 0);
        test1("resources/wordnet/digraph6.txt", 5, 3, 2, 0);

        List<Integer> v = null;
        List<Integer> w = null;
        try {
            test2("resources/wordnet/digraph1.txt", v, w, 4, 1);
        } catch (IllegalArgumentException ex) {
            StdOut.println(ex.getMessage());
        }

        v = new ArrayList<>();
        w = new ArrayList<>();
        w.add(null);
        try {
            test2("resources/wordnet/digraph1.txt", v, w, 4, 1);
        } catch (IllegalArgumentException ex) {
            StdOut.println(ex.getMessage());
        }
    }

    private static void test1(String filename, int v, int w, int lengthRef, int ancestorRef) {
        In in = new In(filename);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        int length = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        assert length == lengthRef;
//        assert ancestor == ancestorRef;
    }

    private static void test2(String filename, Iterable<Integer> v, Iterable<Integer> w, int lengthRef,
            int ancestorRef) {
        In in = new In(filename);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        int length = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        assert length == lengthRef;
//        assert ancestor == ancestorRef;
    }

    @SuppressWarnings("unused")
    private static void testBFDP(Digraph G, int s) {
        BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(G, s);

        for (int v = 0; v < G.V(); v++) {
            if (bfs.hasPathTo(v)) {
                StdOut.printf("%d to %d (%d):  ", s, v, bfs.distTo(v));
                for (int x : bfs.pathTo(v)) {
                    if (x == s)
                        StdOut.print(x);
                    else
                        StdOut.print("->" + x);
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
