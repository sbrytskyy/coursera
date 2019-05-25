import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class FlowNetworkTest {

    @Override
    public String toString() {
        return "FlowNetworkTest [source=" + source + ", sink=" + sink + ", fn=\n" + fn + "]\n";
    }

    private final FlowNetwork fn;
    private final int source;
    private final int sink;

    public FlowNetworkTest(String filename) {
        In in = new In(filename);
        fn = new FlowNetwork(in);

        source = 0;
        sink = fn.V() - 1;
    }

    private void findMaxflow() {
        List<List<FlowEdge>> paths = new ArrayList<>();
        dfs(source, new ArrayList<>(), paths);
        StdOut.println(paths);
    }

    private void dfs(int v, List<FlowEdge> path, List<List<FlowEdge>> paths) {
        if (v == sink) {
            paths.add(new ArrayList<>(path));
            return;
        }

        Iterable<FlowEdge> adj = fn.adj(v);
        for (FlowEdge e : adj) {
            if (e.from() == v) {
                path.add(e);
                dfs(e.other(v), path, paths);
                path.remove(path.size() - 1);
            }
        }
    }

    public static void main(String[] args) {
        test1("resources/test/flownet1.txt");
    }

    private static void test1(String filename) {
        FlowNetworkTest fnt = new FlowNetworkTest(filename);
        StdOut.println(fnt);
        fnt.findMaxflow();
    }

}
