import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private Comparator<Board> comparator = new Comparator<Board>() {

        @Override
        public int compare(Board o1, Board o2) {
            int compare = Integer.compare(o1.manhattan(), o2.manhattan());
            if (compare == 0) {
                compare = Integer.compare(o1.hamming(), o2.hamming());
            }
            return compare;
        }
    };

    private MinPQ<Board> minPQ = new MinPQ<Board>(comparator);

    private int moves = 0;

    private List<Board> solution = new ArrayList<>();

    private boolean solvable;

    public Solver(Board initial) { // find a solution to the initial board
                                   // (using the A* algorithm)

        Board current = initial;
        solution.add(current);
        while (true) {

            if (current.isGoal()) {
                solvable = true;
                break;
            }
            Iterable<Board> neighbors = current.neighbors();
            moves++;
            if (moves > 1000000) {
                break;
            }

            for (Board board : neighbors) {
                minPQ.insert(board);
            }

            while (true) {
                current = minPQ.delMin();
                if (!solution.contains(current)) {
                    solution.add(current);
                    //StdOut.println(current);
                    break;
                }
            }
        }
    }

    public boolean isSolvable() { // is the initial board solvable?
        return solvable;
    }

    public int moves() { // min number of moves to solve initial board; -1 if
                         // unsolvable
        return solvable ? moves : -1;
    }

    public Iterable<Board> solution() { // sequence of boards in a shortest
                                        // solution; null if unsolvable
        return solvable ? solution : null;
    }

    public static void main(String[] args) { // solve a slider puzzle (given
                                             // below)
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        // StdOut.println(initial);
        //
        // initial.neighbors();
        //
        // StdOut.println(initial.twin());

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
