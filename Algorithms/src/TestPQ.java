import java.util.Comparator;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class TestPQ {
    
    private Comparator<User> comparator = new Comparator<User>() {

        @Override
        public int compare(User o1, User o2) {
            int compare = Integer.compare(o1.manhattan(), o2.manhattan());
            return compare;
        }
    };
    
    private MinPQ<User> minPQ = new MinPQ<User>(comparator);

    public void test() {
        minPQ.insert(new User(1));
        minPQ.insert(new User(3));
        minPQ.insert(new User(3));
        minPQ.insert(new User(2));
        minPQ.insert(new User(5));
        minPQ.insert(new User(7));
        minPQ.insert(new User(5));
        
        while (!minPQ.isEmpty()) {
            User min = minPQ.delMin();
            StdOut.println(min);
        }
    }
    
    public static void main(String[] args) {
        TestPQ t = new TestPQ();
        t.test();
    }
}
