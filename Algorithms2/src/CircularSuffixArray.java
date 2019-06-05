import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class CircularSuffixArray {

    private final Integer[] orders;

    private class SortbySuffix implements Comparator<Integer> {
        private final char[] ca;

        public SortbySuffix(String s) {
            ca = s.toCharArray();
        }

        @Override
        public int compare(Integer a, Integer b) {
            
            int index1 = a;
            int index2 = b;
            
            for (int i = 0; i < ca.length; i++) {

                if (index1 > ca.length - 1) {
                    index1 = 0;
                }
                if (index2 > ca.length - 1) {
                    index2 = 0;
                }

                char c1 = ca[index1];
                char c2 = ca[index2];

                if (c1 != c2) {
                    return c1 - c2;
                }
                
                index1++;
                index2++;
            }

            return 0;
        }
    }

    public CircularSuffixArray(String s) { // circular suffix array of s

        if (s == null) {
            throw new IllegalArgumentException();
        }

        int len = s.length();
        orders = new Integer[len];
        for (int i = 0; i < len; i++) {
            orders[i] = i;
        }
        Comparator<Integer> comparator = new SortbySuffix(s);
        Arrays.sort(orders, comparator);
    }

    public int length() { // length of s
        return orders.length;
    }

    public int index(int i) { // returns index of ith sorted suffix
        if (i < 0 || i > orders.length - 1) {
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
