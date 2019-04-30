import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private final Stack<Board> solution;

    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private int level = 0;
        private final SearchNode prev;

        public SearchNode(Board board, SearchNode prev) {
            super();
            this.board = board;
            this.prev = prev;
            if (prev != null) {
                this.level = prev.getLevel() + 1;
            }
        }

        @Override
        public int compareTo(SearchNode that) {
            if (this == that)
                return 0;

//            int compare = Integer.compare(this.board.manhattan(), that.board.manhattan());
//            if (compare == 0) {
//                compare = Integer.compare(this.board.hamming(), that.board.hamming());
//            }
//            if (compare == 0) {
//                compare = Integer.compare(this.level, that.level);
//            }

            int compare = (this.board.manhattan() - that.board.manhattan()) + (this.level - that.level);

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
            return "SearchNode [level=" + level + ", board=" + board + ", prev="
                    + (prev == null ? "NULL" : prev.getBoard()) + "]";
        }
    }

    public Solver(Board initial) { // find a solution to the initial board
                                   // (using the A* algorithm)

        if (initial == null) {
            throw new IllegalArgumentException();
        }
        
        solution = new Stack<>();
        
        MinPQ<SearchNode> minPQ = new MinPQ<SearchNode>();

        SearchNode node = new SearchNode(initial, null);
        minPQ.insert(node);

        int iterations = 0;
        SearchNode current = node;
        while (true) {

            if (minPQ.isEmpty())
                break;

            current = minPQ.delMin();
//            StdOut.println(current);
            iterations++;

            if (current.getBoard().isGoal()) {
                solution.push(current.getBoard());

                SearchNode sn = current;
                while (true) {
                    sn = sn.getPrev();
                    if (sn == null)
                        break;
                    solution.push(sn.getBoard());
                }
                break;
            }

            Iterable<Board> neighbors = current.getBoard().neighbors();
            for (Board board : neighbors) {
                if (current.getPrev() == null || !board.equals(current.getPrev().getBoard())) {
                    SearchNode n = new SearchNode(board, current);
//                    StdOut.println("\t" + board);
                    minPQ.insert(n);
                }
            }

            if (iterations > 1000) {
                break;
            }
        }
    }

    public boolean isSolvable() { // is the initial board solvable?
        return !solution.isEmpty();
    }

    public int moves() { // min number of moves to solve initial board; -1 if
                         // unsolvable
        return isSolvable() ? solution.size() - 1 : -1;
    }

    public Iterable<Board> solution() { // sequence of boards in a shortest
                                        // solution; null if unsolvable
        return isSolvable() ? solution : null;
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
