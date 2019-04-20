import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class PercolationTest {

    public static void main(String[] args) {
        test();
    }

    private static void test() {
        // test("resources/input_sb1.txt", 55, 9, 1);
        test("resources/percolation/greeting57.txt", 2526, 1, 6, true);
        test("resources/percolation/input6.txt", 1, 1, 6, true);
        test("resources/percolation/input3.txt", 4, 3, 1, true);
        test("resources/percolation/input10.txt", 56, 9, 1, false);
        test("resources/percolation/input20.txt", 250, 18, 1, false);
    }


    private static void test(String filename, int expectedNumberOfOpenSites, int isFullRow, int isFullCol,
            boolean expected) {
        In in = new In(filename); // input file
        int n = in.readInt(); // n-by-n percolation system

        // repeatedly read in sites to open and draw resulting system
        Percolation perc = new Percolation(n);
        StdOut.println(perc.toString());

        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
            StdOut.println("Opening " + i + ":" + j);
        }
        StdOut.println(perc.toString());
        int numberOfOpenSites = perc.numberOfOpenSites();
        StdOut.println("numberOfOpenSites: " + numberOfOpenSites);
        assert numberOfOpenSites == expectedNumberOfOpenSites;
        assert perc.isFull(isFullRow, isFullCol) == expected;
    }
}
