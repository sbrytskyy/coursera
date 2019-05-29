import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class BoggleSolver {
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)

    private final Set<String> dictionary;
    private final Set<String> prefixes;

    public BoggleSolver(String[] dictionary) {
        this.dictionary = new LinkedHashSet<>();
        this.prefixes = new LinkedHashSet<>();
        for (String word : dictionary) {
            this.dictionary.add(word);
            
            for (int i = 1; i <= word.length(); i++) {
                prefixes.add(word.substring(0, i));
            }
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {

        int rows = board.rows();
        int cols = board.cols();
        int counter = 0;
        int max = rows * cols;

        Set<String> words = new LinkedHashSet<>();
        boolean[][] visited = new boolean[rows][cols];
        List<Character> word = new ArrayList<>();

        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                dfs(x, y, counter + 1, visited, word, words, rows, cols, board, max);
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

    private void dfs(int x, int y, int counter, boolean[][] visited, List<Character> word, Set<String> words, int rows,
            int cols, BoggleBoard board, int max) {
        visited[x][y] = true;
        word.add(board.getLetter(x, y));

        process(x, y, counter + 1, visited, word, words, rows, cols, board, max);

        visited[x][y] = false;
        word.remove(word.size() - 1);
    }

    private void process(int x, int y, int counter, boolean[][] visited, List<Character> word, Set<String> words,
            int rows, int cols, BoggleBoard board, int max) {

        StringBuilder sb = new StringBuilder();
        for (Character c : word) {
            sb.append(c);
        }
        String w = sb.toString();
        if (!prefixes.contains(w)) {
            return;
        }
        
        if (counter > 3) {
            words.add(w);
        }

        if (counter == max) {
            return;
        }

        // Move NW
        if (x > 0 && y > 0 && !visited[x - 1][y - 1]) {
            dfs(x - 1, y - 1, counter, visited, word, words, rows, cols, board, max);
        }

        // Move N
        if (y > 0 && !visited[x][y - 1]) {
            dfs(x, y - 1, counter, visited, word, words, rows, cols, board, max);
        }

        // Move NE
        if (x < cols - 1 && y > 0 && !visited[x + 1][y - 1]) {
            dfs(x + 1, y - 1, counter, visited, word, words, rows, cols, board, max);
        }

        // Move W
        if (x > 0 && !visited[x - 1][y]) {
            dfs(x - 1, y, counter, visited, word, words, rows, cols, board, max);
        }

        // Move E
        if (x < cols - 1 && !visited[x + 1][y]) {
            dfs(x + 1, y, counter, visited, word, words, rows, cols, board, max);
        }

        // Move SW
        if (x > 0 && y < rows - 1 && !visited[x - 1][y + 1]) {
            dfs(x - 1, y + 1, counter, visited, word, words, rows, cols, board, max);
        }

        // Move S
        if (y < rows - 1 && !visited[x][y + 1]) {
            dfs(x, y + 1, counter, visited, word, words, rows, cols, board, max);
        }

        // Move SE
        if (x < cols - 1 && y < rows - 1 && !visited[x + 1][y + 1]) {
            dfs(x + 1, y + 1, counter, visited, word, words, rows, cols, board, max);
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
