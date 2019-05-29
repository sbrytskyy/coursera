import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolverTest {

    public static void main(String[] args) {
        test1();
        test2();
        test3();
        test4();
        test5();
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

    private static void test3() {
        String[] dictionary = readDictionary("resources/boggle/dictionary-yawl.txt");
        BoggleBoard board = new BoggleBoard("resources/boggle/board-antidisestablishmentarianisms.txt");

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
        assert count == 40;
        assert score == 172;
    }

    private static void test4() {
        String[] dictionary = readDictionary("resources/boggle/dictionary-16q.txt");
        BoggleBoard board = new BoggleBoard("resources/boggle/board-16q.txt");

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
        assert count == 15;
        assert score == 147;
    }

    private static void test5() {
        String[] dictionary = readDictionary("resources/boggle/dictionary-16q.txt");
        BoggleBoard board = new BoggleBoard("resources/boggle/board-4q.txt");

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
        assert count == 3;
        assert score == 15;
    }

    private static String[] readDictionary(String filename) {
        In in = new In(filename);
        return in.readAllStrings();
    }
}
