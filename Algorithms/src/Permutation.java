import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);

        if (k > 0) {
            RandomizedQueue<String> rq = new RandomizedQueue<>();

            String line = null;
            while (!StdIn.isEmpty()) {
                try {
                    line = StdIn.readString();
                    rq.enqueue(line);
                } catch (NoSuchElementException e) {
                    line = null;
                }
            }

            Iterator<String> it = rq.iterator();
            for (int i = 0; i < k; i++) {
                String s = it.next();
                StdOut.println(s);
            }
        }
    }
}
