import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolverTest {

    public static void main(String[] args) {
        test1();
        test2();
    }

    private static void test1() {
        String[] dictionary = readDictionary("resources/boggle/dictionary-algs4.txt");
        BoggleBoard board = new BoggleBoard("resources/boggle/board4x4.txt");

        BoggleSolver solver = new BoggleSolver(dictionary);
        Iterable<String> allValidWords = solver.getAllValidWords(board);
        StdOut.println(allValidWords);

        int score = 0;
        for (String word : allValidWords) {
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
        assert score == 33;
    }

    private static void test2() {
        String[] dictionary = readDictionary("resources/boggle/dictionary-algs4.txt");
        BoggleBoard board = new BoggleBoard("resources/boggle/board-q.txt");

        BoggleSolver solver = new BoggleSolver(dictionary);
        Iterable<String> allValidWords = solver.getAllValidWords(board);
        StdOut.println(allValidWords);

        int score = 0;
        int count = 0;
        for (String word : allValidWords) {
            score += solver.scoreOf(word);
            count++;
        }
        StdOut.println("Score = " + score);
        StdOut.println("Count = " + count);
        assert count == 29;
        assert score == 84;
    }

    private static BoggleBoard createBoggleBoard() {
        return new BoggleBoard();
    }

    private static String[] readDictionary(String filename) {
        In in = new In(filename);
        return in.readAllStrings();
    }
}
