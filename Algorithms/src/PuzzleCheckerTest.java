
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class PuzzleCheckerTest {

    public static void main(String[] args) {
        generalTests();
        callsTests();
    }

    private static void callsTests() {
        testCalls("resources/8puzzle/puzzle20.txt");
        testCalls("resources/8puzzle/puzzle21.txt");
        testCalls("resources/8puzzle/puzzle22.txt");
    }

    private static void generalTests() {
        test("resources/8puzzle/puzzle00.txt");
        test("resources/8puzzle/puzzle05.txt");
        test("resources/8puzzle/puzzle14.txt");
        test("resources/8puzzle/puzzle15.txt");
        test("resources/8puzzle/puzzle16.txt");
        test("resources/8puzzle/puzzle17.txt");
        test("resources/8puzzle/puzzle18.txt");
        test("resources/8puzzle/puzzle19.txt");
        test("resources/8puzzle/puzzle20.txt");
        test("resources/8puzzle/puzzle21.txt");
        test("resources/8puzzle/puzzle22.txt");
    }

    public static void test(String filename) {
        // read in the board specified in the filename
        In in = new In(filename);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }

        // solve the slider puzzle
        Board initial = new Board(tiles);
//        StdOut.println(initial);

//        Board twin = initial.twin();
//        StdOut.println(twin);
        
        Solver solver = new Solver(initial);
        StdOut.println(filename + ": " + solver.moves());
    }

    public static void testCalls(String filename) {
//        Board.boardCalls = 0;
//        Board.equalsCalls = 0;
//        Board.manhattanCalls = 0;

        // read in the board specified in the filename
        In in = new In(filename);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }

        // solve the slider puzzle
        Board initial = new Board(tiles);
//        StdOut.println(initial);

//        Board twin = initial.twin();
//        StdOut.println(twin);
        
        Solver solver = new Solver(initial);
        StdOut.println(filename + ": " + solver.moves());
//        StdOut.println(Board.boardCalls + ":" + Board.equalsCalls + ":" + Board.manhattanCalls);
//        
//        Board.boardCalls = 0;
//        Board.equalsCalls = 0;
//        Board.manhattanCalls = 0;
    }
}
