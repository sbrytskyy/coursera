import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class CircularSuffixArrayTest {

    public static void main(String[] args) {
        test1("resources/burrows/abra.txt");
    }

    private static void test1(String filename) {
        In in = new In(filename);
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
