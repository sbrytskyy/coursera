import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class PermutationTest {

    public static void main(String[] args) {
        test();
//        testRandom();
    }

    private static void testRandom() {
        int size = 2;
        int trials = 5;
        while (trials-- > 0) {
            int[] x = StdRandom.permutation(size);
            System.out.println(Arrays.toString(x));
        }
    }

    private static void test() {
        test("resources/queues/test1.txt", 2, 5);
         test("resources/queues/distinct.txt", 3, 5);
         test("resources/queues/duplicates.txt", 8, 5);
    }

    private static void test(String filename, int k, int trials) {
        In in = new In(filename); // input file

        if (k > 0) {
            RandomizedQueue<String> rq = new RandomizedQueue<>();

            String line = null;
            while (!in.isEmpty()) {
                line = in.readString();
                rq.enqueue(line);
            }

            while (trials-- > 0) {
                Iterator<String> it = rq.iterator();
                System.out.println("------------");
                for (int i = 0; i < k; i++) {
                    String s = it.next();
                    StdOut.println(s);
                }
            }
        }
    }
}
