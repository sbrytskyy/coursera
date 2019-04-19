import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final double[] pts;
    private double mean = Double.NaN;
    private double stddev = Double.NaN;

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
        if (Double.isNaN(mean)) {
            mean = StdStats.mean(pts);
        }
        return mean;
    }

    public double stddev() { // sample standard deviation of percolation
                             // threshold
        if (Double.isNaN(stddev)) {
            stddev = StdStats.stddev(pts);
        }
        return stddev;
    }

    public double confidenceLo() { // low end point of 95% confidence interval
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(pts.length);
    }

    public double confidenceHi() { // high end point of 95% confidence interval
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(pts.length);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(n, trials);
        StdOut.println(ps.mean());
        StdOut.println(ps.stddev());
        StdOut.println(ps.confidenceLo());
        StdOut.println(ps.confidenceHi());
    }
}
