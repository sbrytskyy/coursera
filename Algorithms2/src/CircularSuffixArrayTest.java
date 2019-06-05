import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class CircularSuffixArrayTest {

    public static void main(String[] args) {
        test1("resources/burrows/abra.txt");
        test2("ABBABABBBB");
    }

    private static void test1(String filename) {
        In in = new In(filename);
        String text = in.readAll();
        
        test2(text);
    }

    private static void test2(String text) {
        CircularSuffixArray cfa = new CircularSuffixArray(text);
        int length = cfa.length();
        StdOut.println(length);
        
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < length; i++) {
            sb.append(cfa.index(i));
            if (i < length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
        String result = sb.toString();
        StdOut.println(result);
        
        TrieCircularSuffix t = new TrieCircularSuffix(text);
        int[] orderArray = t.getOrderArray();
        String reference = Arrays.toString(orderArray);
        System.out.println(reference);
        
        assert reference.equals(result);
    }
}
