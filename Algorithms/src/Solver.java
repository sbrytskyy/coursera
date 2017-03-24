import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    
    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int level;
        private SearchNode prev;
        
        public SearchNode(Board board, int level, SearchNode prev) {
            super();
            this.board = board;
            this.level = level;
            this.prev = prev;
        }

        @Override
        public int compareTo(SearchNode that) {
            if (this == that)
                return 0;

            int compare = Integer.compare(this.board.manhattan(), that.board.manhattan());
            if (compare == 0) {
                compare = Integer.compare(this.board.hamming(), that.board.hamming());
            }

            return compare;
        }
        
        public int getLevel() {
            return level;
        }
        
        public Board getBoard() {
            return board;
        }
        
        public SearchNode getPrev() {
            return prev;
        }

        @Override
        public String toString() {
            return "SearchNode [level=" + level + ", board=" + board + ", prev=" + (prev == null ? "NULL" : prev.getBoard()) + "]";
        }
    }

    private MinPQ<SearchNode> minPQ = new MinPQ<SearchNode>();

    private int moves = -1;

    private List<Board> visited = new ArrayList<>();
    private List<Board> solution = new ArrayList<>();

    private boolean solvable;

    public Solver(Board initial) { // find a solution to the initial board
                                   // (using the A* algorithm)

        // Board previous = null;
        
        SearchNode node = new SearchNode(initial, 0, null);
        visited.add(initial);
        minPQ.insert(node);

        int iterations = 0;
        SearchNode current = node;
        while (true) {

            if (minPQ.isEmpty()) break;
            
            current = minPQ.delMin();
            visited.add(current.getBoard());
            StdOut.println(current);
            iterations++;
            
            if (current.getBoard().isGoal()) {
                solvable = true;
                moves = current.getLevel();
                
                solution.add(current.getBoard());
                
                SearchNode sn = current;
                while (true) {
                    sn = sn.getPrev();
                    if (sn == null) break;
                    solution.add(sn.getBoard());
                }
                break;
            }

            Iterable<Board> neighbors = current.getBoard().neighbors();
            for (Board board : neighbors) {
                if (!visited.contains(board)) {
                    SearchNode n = new SearchNode(board, current.getLevel() + 1, current);
                    StdOut.println("\t" + board);
                    minPQ.insert(n);
                }
            }

            if (iterations > 100) {
                break;
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
