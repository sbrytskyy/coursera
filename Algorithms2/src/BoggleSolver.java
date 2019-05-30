import java.util.LinkedHashSet;
import java.util.Set;

public class BoggleSolver {
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)

    private final Trie dictionary;

    private int rows;
    private int cols;
    private BoggleBoard bb;
    private Set<String> words;
    private boolean[][] visited;

    public BoggleSolver(String[] dictionary) {
        this.dictionary = new Trie();
        for (String word : dictionary) {
            this.dictionary.add(word);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {

        bb = board;
        rows = board.rows();
        cols = board.cols();

        words = new LinkedHashSet<>();
        visited = new boolean[rows][cols];

        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                dfs(x, y, "");
            }
        }

        return words;
    }

    private void dfs(int x, int y, String word) {
        visited[y][x] = true;
        char letter = bb.getLetter(y, x);
        word += letter;
        if (letter == 'Q') {
            word += "U";
        }

        process(x, y, word);

        visited[y][x] = false;
    }

    private void process(int x, int y, String word) {

        if (!dictionary.containsPrefix(word)) {
            return;
        }

        if (word.length() >= 3) {
            if (dictionary.contains(word)) {
                words.add(word);
            }
        }

        // Move NW
        if (x > 0 && y > 0 && !visited[y - 1][x - 1]) {
            dfs(x - 1, y - 1, word);
        }

        // Move N
        if (y > 0 && !visited[y - 1][x]) {
            dfs(x, y - 1, word);
        }

        // Move NE
        if (x < cols - 1 && y > 0 && !visited[y - 1][x + 1]) {
            dfs(x + 1, y - 1, word);
        }

        // Move W
        if (x > 0 && !visited[y][x - 1]) {
            dfs(x - 1, y, word);
        }

        // Move E
        if (x < cols - 1 && !visited[y][x + 1]) {
            dfs(x + 1, y, word);
        }

        // Move SW
        if (x > 0 && y < rows - 1 && !visited[y + 1][x - 1]) {
            dfs(x - 1, y + 1, word);
        }

        // Move S
        if (y < rows - 1 && !visited[y + 1][x]) {
            dfs(x, y + 1, word);
        }

        // Move SE
        if (x < cols - 1 && y < rows - 1 && !visited[y + 1][x + 1]) {
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
