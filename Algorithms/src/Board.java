import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;

public class Board {

    private int dim;
    private int[] array;
    private int hamming;
    private int manhattan;
    private int zero;
    private Iterable<Board> neighbors;

    public Board(int[][] blocks) { // construct a board from an n-by-n array of
                                   // blocks
                                   // (where blocks[i][j] = block in row i,
                                   // column j)

        if (blocks == null) {
            throw new NullPointerException();
        }
        dim = blocks[0].length;
        if (dim * dim != blocks.length) {
            // not square
        }
        int len = dim * dim;
        array = new int[len];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                int index = i * dim + j;
                int value = blocks[i][j];

                array[index] = value;
                if (value != 0) {
                    if ((index != len - 1 && value != index + 1) || (index == len - 1 && value != 0)) {
                        hamming++;
                    }

                    // StdOut.print("index: " + index + ", value: " + value + ",
                    // home: " + (value - 1));
                    int rowsDiff = (value - 1) / dim - index / dim;
                    int colsDiff = (value - 1) % dim - index % dim;
                    int m = Math.abs(rowsDiff) + Math.abs(colsDiff);
                    // StdOut.println("; rowsDiff: " + rowsDiff + ", colsDiff: "
                    // + colsDiff);
                    manhattan += m;
                } else {
                    zero = index;
                }
            }
        }
        
        findNeighbors();
    }

    private void findNeighbors() {
        int row = zero / dim;
        int col = zero % dim;
        
        StdOut.println("zero row: " + row + ", col: " + col);
    }

    public int dimension() { // board dimension n
        return dim;
    }

    public int hamming() { // number of blocks out of place
        return hamming;
    }

    public int manhattan() { // sum of Manhattan distances between blocks and
                             // goal
        return manhattan;
    }

    public boolean isGoal() { // is this board the goal board?
        return hamming == 0;
    }

    public Board twin() { // a board that is obtained by exchanging any pair of
                          // blocks
        return null;
    }

    public boolean equals(Object y) { // does this board equal y?
        if (this == y)
            return true;
        if (!(y instanceof Board))
            return false;
        Board b2 = (Board) y;
        
        if (dim != b2.dim) return false;
        
        if (array.length != b2.array.length) return false;
        
        for (int i = 0; i < array.length; i++) {
            if (array[i] != b2.array[i]) {
                return false;
            }
        }
        
        return true;
    }

    public Iterable<Board> neighbors() { // all neighboring boards
        return neighbors;
    }

    public String toString() { // string representation of this board (in the
                               // output format specified below)
        return "Board: " + Arrays.toString(array) + ", hamming: " + hamming + ", manhattan: " + manhattan;
    }

    public static void main(String[] args) { // unit tests (not graded)
    }
}