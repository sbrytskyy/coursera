package com.sb.algs;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Percolation {

    private final boolean[][] model;
    private final int n;
    private int openCount = 0;
    private final int top; // top virtual site
    private final int bottom; // bottom virtual site

    private final Union percolate;
    private final Union fill;

    public Percolation(int n) { // create n-by-n grid, with all sites blocked
        this.n = n;

        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        model = new boolean[n][n];
        top = n * n;
        bottom = n * n + 1;

        percolate = new Union(n * n + 2);
        fill = new Union(n * n + 2);
    }

    public void open(int row, int col) { // open site (row, col) if it is not
                                         // open already
        if (row <= 0 || row > n || col <= 0 || col > n)
            throw new IllegalArgumentException();

        int r = row - 1;
        int c = col - 1;

        if (!model[r][c]) {
            openCount++;
            model[r][c] = true;
        }

        // check upper site, if open - connect
        int p = r * n + c;
        if (row == 1) {
            percolate.union(p, top);
            fill.union(p, top);
        } else if (isOpen(row - 1, col)) {
            int q = (r - 1) * n + c;
            percolate.union(p, q);
            fill.union(p, q);
        }
        // lower
        if (row == n) {
            percolate.union(p, bottom);
        } else if (isOpen(row + 1, col)) {
            int q = (r + 1) * n + c;
            percolate.union(p, q);
            fill.union(p, q);
        }
        // left
        if ((col > 1 && isOpen(row, col - 1))) {
            int q = r * n + c - 1;
            percolate.union(p, q);
            fill.union(p, q);
        }
        // right
        if ((col < n && isOpen(row, col + 1))) {
            int q = r * n + c + 1;
            percolate.union(p, q);
            fill.union(p, q);
        }
    }

    public boolean isOpen(int row, int col) { // is site (row, col) open?
        if (row <= 0 || row > n || col <= 0 || col > n)
            throw new IllegalArgumentException();

        return model[row - 1][col - 1];

    }

    public boolean isFull(int row, int col) { // is site (row, col) full?
        if (row <= 0 || row > n || col <= 0 || col > n)
            throw new IllegalArgumentException();

        int q = (row - 1) * n + col - 1;

        return fill.connected(top, q);
    }

    public int numberOfOpenSites() { // number of open sites
        return openCount;
    }

    public boolean percolates() { // does the system percolate?
        return percolate.connected(top, bottom);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--------------------------------\n");
        int width = model[0].length;
        int height = model.length;
        sb.append(width + ":" + height + "\n");

        for (int x = 0; x < height; x++) {
            sb.append("[");
            for (int y = 0; y < width; y++) {
                sb.append(model[x][y] ? "X" : "O");
                if (y < width - 1) {
                    sb.append(",");
                }
            }
            sb.append("]\n");
        }
        sb.append("--------------------------------\n");
        return sb.toString();
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
