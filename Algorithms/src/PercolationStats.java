import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    
    private double[] pts;
    
    public PercolationStats(int n, int trials) { // perform trials independent
                                                 // experiments on an n-by-n
                                                 // grid
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        pts = new double[trials];

        for (int t = 0; t < trials; t++) {

            Percolation perc = new Percolation(n);

            int[] x = StdRandom.permutation(n * n);

            for (int i = 0; i < n * n; i++) {
                int row = x[i] / n + 1;
                int col = x[i] % n + 1;

                perc.open(row, col);
                if (perc.percolates()) {
                    double pt = perc.numberOfOpenSites() * 1.0 / (n * n);
                    // StdOut.println(pt);
                    pts[t] = pt;
                    break;
                }
            }
        }
    }

    public double mean() { // sample mean of percolation threshold
        return StdStats.mean(pts);
    }

    public double stddev() { // sample standard deviation of percolation
                             // threshold
        return StdStats.stddev(pts);
    }

    public double confidenceLo() { // low endpoint of 95% confidence interval
        return StdStats.mean(pts) - 1.96 * StdStats.stddev(pts) / Math.sqrt(pts.length);
    }

    public double confidenceHi() { // high endpoint of 95% confidence interval
        return StdStats.mean(pts) + 1.96 * StdStats.stddev(pts) / Math.sqrt(pts.length);
    }

    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(20, 1000);
        StdOut.println(ps.mean());
        StdOut.println(ps.stddev());
        StdOut.println(ps.confidenceLo());
        StdOut.println(ps.confidenceHi());
    }
}