import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int[][] model;
    private int n;
    private int openCount = 0;

    private WeightedQuickUnionUF uf;

    public Percolation(int n) { // create n-by-n grid, with all sites blocked
        this.n = n;

        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        model = new int[n][n];
        uf = new WeightedQuickUnionUF(n * n);
    }

    public void open(int row, int col) { // open site (row, col) if it is not
                                         // open already
        if (row <= 0 || row > n || col <= 0 || col > n)
            throw new IndexOutOfBoundsException();

        if (model[row - 1][col - 1] == 1) return;
        model[row - 1][col - 1] = 1;
        openCount++;

        int r = row - 1;
        int c = col - 1;

        // check upper site, if open - connect
        if ((row > 1 && isOpen(row - 1, col))) {
            int p = r * n + c;
            int q = (r - 1) * n + c;
            uf.union(p, q);
        }
        // lower
        if ((row < n && isOpen(row + 1, col))) {
            int p = r * n + c;
            int q = (r + 1) * n + c;
            uf.union(p, q);
        }
        // left
        if ((col > 1 && isOpen(row, col - 1))) {
            int p = r * n + c;
            int q = r * n + c - 1;
            uf.union(p, q);
        }
        // right
        if ((col < n && isOpen(row, col + 1))) {
            int p = r * n + c;
            int q = r * n + c + 1;
            uf.union(p, q);
        }
    }

    public boolean isOpen(int row, int col) { // is site (row, col) open?
        if (row <= 0 || row > n || col <= 0 || col > n)
            throw new IndexOutOfBoundsException();

        return model[row - 1][col - 1] == 1;

    }

    public boolean isFull(int row, int col) { // is site (row, col) full?
        if (row <= 0 || row > n || col <= 0 || col > n)
            throw new IndexOutOfBoundsException();

        if (row == 1)
            return isOpen(row, col);

        int q = (row - 1) * n + col - 1;

        for (int i = 0; i < n; i++) {
            if (uf.connected(i, q)) {
                return true;
            }
        }
        return false;
    }

    public int numberOfOpenSites() { // number of open sites
        return openCount;
    }

    public boolean percolates() { // does the system percolate?
        for (int i = 1; i <= n; i++) {
            if (isFull(n, i)) {
                return true;
            }
        }
        return false;
    }

    private void test() {
        int[] x = StdRandom.permutation(n * n);

        for (int i = 0; i < n * n; i++) {
            int row = x[i] / n + 1;
            int col = x[i] % n + 1;

            open(row, col);
            if (percolates()) {
                StdOut.println(numberOfOpenSites() * 1.0 / (n * n));
                return;
            }
        }
    }

    public static void main(String[] args) {
        int n = 20;
        Percolation p = new Percolation(n);
        p.test();
    }
}
