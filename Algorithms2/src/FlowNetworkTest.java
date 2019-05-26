import java.util.ArrayList;
import java.util.Iterator;
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
    
    double maxFlow = 0.0;

    public FlowNetworkTest(String filename) {
        In in = new In(filename);
        fn = new FlowNetwork(in);

        source = 0;
        sink = fn.V() - 1;
    }

    public double findMaxflow() {
        maxFlow = 0.0;
        
        List<List<FlowEdge>> paths = new ArrayList<>();
        dfs(source, new ArrayList<>(), paths);
        StdOut.println(paths);

        explorePaths(paths);
        
        return maxFlow;
    }

    private void explorePaths(List<List<FlowEdge>> paths) {
        while (!paths.isEmpty()) {
            Iterator<List<FlowEdge>> iterator = paths.iterator();
            
            while (iterator.hasNext()) {
                List<FlowEdge> path = iterator.next();
                
                double flow = Double.POSITIVE_INFINITY;
                boolean pathIsFull = false;
                for (FlowEdge e : path) {
                    if (Double.compare(e.flow(), e.capacity()) >= 0) {
                        pathIsFull = true;
                        break;
                    }
                    double f = e.capacity() - e.flow();
                    if (Double.compare(f, flow) < 0) {
                        flow = f;
                    }
                }
                if (pathIsFull) {
                    iterator.remove();
                    continue;
                }
                
                maxFlow += flow;
                for (FlowEdge e : path) {
                    e.addResidualFlowTo(e.to(), flow);
                }
            }
        }
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
        test1("resources/test/flownet1.txt", 5.0);
        test1("resources/test/flownet2.txt", 23.0);
        test1("resources/test/flownet3.txt", 19.0);
    }

    private static void test1(String filename, double expected) {
        FlowNetworkTest fnt = new FlowNetworkTest(filename);
        StdOut.println(fnt);
        double maxflow = fnt.findMaxflow();
        StdOut.println("Maxflow: " + maxflow);
        assert Double.compare(maxflow, expected) == 0;
    }

}
