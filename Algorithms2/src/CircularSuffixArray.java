import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class CircularSuffixArray {

    private final int[] orders;

    public CircularSuffixArray(String s) { // circular suffix array of s
        TrieCircularSuffix t = new TrieCircularSuffix(s);
        orders = t.getOrderArray();
    }

    public int length() { // length of s
        return orders.length;
    }

    public int index(int i) { // returns index of ith sorted suffix
        if (i < 0 || i >= orders.length) {
            throw new IllegalArgumentException();
        }

        return orders[i];
    }

    public static void main(String[] args) { // unit testing (required)
        In in = new In(args[0]);
        String text = in.readAll();

        CircularSuffixArray cfa = new CircularSuffixArray(text);
        int length = cfa.length();
        StdOut.println(length);

        for (int i = 0; i < length; i++) {
            StdOut.print(cfa.index(i));
            if (i < length - 1) {
                StdOut.print(", ");
            }
        }
        StdOut.println();
    }
}
