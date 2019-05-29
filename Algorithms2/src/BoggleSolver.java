import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class BoggleSolver {
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)

    private final Set<String> dictionary;
    private final Set<String> prefixes;

    private int rows;
    private int cols;
    private int max;
    private BoggleBoard bb;
    private Set<String> words;
    private boolean[][] visited;

    public BoggleSolver(String[] dictionary) {
        this.dictionary = new LinkedHashSet<>();
        this.prefixes = new LinkedHashSet<>();
        for (String word : dictionary) {
            this.dictionary.add(word);

            for (int i = 1; i <= word.length(); i++) {
                String substring = word.substring(0, i);
                if (substring.endsWith("Q")) {
                    substring += "U";
                }
                prefixes.add(substring);
            }
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {

        bb = board;
        rows = board.rows();
        cols = board.cols();
        max = rows * cols;

        words = new LinkedHashSet<>();
        visited = new boolean[rows][cols];

        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                dfs(x, y, "");
            }
        }

        List<String> validWords = new ArrayList<>();

        for (String w : words) {
            if (dictionary.contains(w)) {
                validWords.add(w);
            }
        }

        return validWords;
    }

    private void dfs(int x, int y, String word) {
        visited[x][y] = true;
        char letter = bb.getLetter(x, y);
        word += letter;
        if (letter == 'Q') {
            word += "U";
        }

        process(x, y, word);

        visited[x][y] = false;
    }

    private void process(int x, int y, String word) {

        if (!prefixes.contains(word)) {
            return;
        }

        if (word.length() >= 3) {
            words.add(word);
        }

        if (word.length() == max) {
            return;
        }

        // Move NW
        if (x > 0 && y > 0 && !visited[x - 1][y - 1]) {
            dfs(x - 1, y - 1, word);
        }

        // Move N
        if (y > 0 && !visited[x][y - 1]) {
            dfs(x, y - 1, word);
        }

        // Move NE
        if (x < cols - 1 && y > 0 && !visited[x + 1][y - 1]) {
            dfs(x + 1, y - 1, word);
        }

        // Move W
        if (x > 0 && !visited[x - 1][y]) {
            dfs(x - 1, y, word);
        }

        // Move E
        if (x < cols - 1 && !visited[x + 1][y]) {
            dfs(x + 1, y, word);
        }

        // Move SW
        if (x > 0 && y < rows - 1 && !visited[x - 1][y + 1]) {
            dfs(x - 1, y + 1, word);
        }

        // Move S
        if (y < rows - 1 && !visited[x][y + 1]) {
            dfs(x, y + 1, word);
        }

        // Move SE
        if (x < cols - 1 && y < rows - 1 && !visited[x + 1][y + 1]) {
            dfs(x + 1, y + 1, word);
        }
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (dictionary.contains(word)) {
            int len = word.length();

            switch (len) {
            case 0:
            case 1:
            case 2:
                return 0;
            case 3:
            case 4:
                return 1;
            case 5:
                return 2;
            case 6:
                return 3;
            case 7:
                return 5;
            default:
                return 11;
            }
        }
        return 0;
    }
}
