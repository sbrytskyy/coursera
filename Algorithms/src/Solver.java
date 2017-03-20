import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private MinPQ minPQ = new MinPQ();

    public Solver(Board initial) { // find a solution to the initial board
                                   // (using the A* algorithm)

    }

    public boolean isSolvable() { // is the initial board solvable?
        return false;
    }

    public int moves() { // min number of moves to solve initial board; -1 if
                         // unsolvable
        return 0;
    }

    public Iterable<Board> solution() { // sequence of boards in a shortest
                                        // solution; null if unsolvable
        return null;
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
        StdOut.println(initial);
        
        initial.neighbors();

        StdOut.println(initial.twin());

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
