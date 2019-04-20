import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class PermutationTest {

    public static void main(String[] args) {
        test();
    }

    private static void test() {
        test("resources/queues/distinct.txt", 3);
        test("resources/queues/duplicates.txt", 8);
    }

    private static void test(String filename, int k) {
        In in = new In(filename); // input file

        if (k > 0) {
            RandomizedQueue<String> rq = new RandomizedQueue<>();

            String line = null;
            do {
                try {
                    line = in.readString();
                    rq.enqueue(line);
                } catch (NoSuchElementException e) {
                    line = null;
                }

            } while (line != null);

            Iterator<String> it = rq.iterator();
            for (int i = 0; i < k; i++) {
                String s = it.next();
                StdOut.println(s);
            }
        }
    }
}
