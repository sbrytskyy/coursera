import java.util.ArrayList;
import java.util.List;

public class Board {

    private int dim;
    private int[][] array;
    private int hamming;
    private int manhattan;

    private int zeroRow;
    private int zeroCol;

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
        array = new int[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                int index = i * dim + j;
                int value = blocks[i][j];

                array[i][j] = value;
                if (value != 0) {
                    if ((index != len - 1 && value != index + 1) || (index == len - 1 && value != 0)) {
                        hamming++;
                    }

                    // StdOut.println("i: " + i + ", j: " + j + ", value: " +
                    // value + ", home: " + (value - 1));
                    int rowsDiff = (value - 1) / dim - index / dim;
                    int colsDiff = (value - 1) % dim - index % dim;
                    int m = Math.abs(rowsDiff) + Math.abs(colsDiff);
                    // StdOut.println("; rowsDiff: " + rowsDiff + ", colsDiff: "
                    // + colsDiff);
                    manhattan += m;
                } else {
                    zeroRow = i;
                    zeroCol = j;
                }
            }
        }
    }

    private void findNeighbors(List<Board> neighbors) {
        // StdOut.println("zero row: " + zeroRow + ", col: " + zeroCol);
        Board top = moveZero(-1, 0);
        if (top != null) {
            neighbors.add(top);
        }
        // StdOut.println("Zero Top: " + top);
        Board bottom = moveZero(1, 0);
        if (bottom != null) {
            neighbors.add(bottom);
        }
        // StdOut.println("Zero Bottom: " + bottom);

        Board left = moveZero(0, -1);
        if (left != null) {
            neighbors.add(left);
        }
        // StdOut.println("Zero Left: " + left);

        Board right = moveZero(0, 1);
        if (right != null) {
            neighbors.add(right);
        }
        // StdOut.println("Zero Right: " + right);
    }

    private Board moveZero(int rowShift, int colShift) {
        int newZeroRow = zeroRow + rowShift;
        int newZeroCol = zeroCol + colShift;

        if (newZeroRow >= dim || newZeroRow < 0)
            return null;
        if (newZeroCol >= dim || newZeroCol < 0)
            return null;

        int[][] newBoard = new int[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                newBoard[i][j] = array[i][j];
            }
        }

        newBoard[zeroRow][zeroCol] = newBoard[newZeroRow][newZeroCol];
        newBoard[newZeroRow][newZeroCol] = 0;

        return new Board(newBoard);
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

        int row1 = -1;
        int col1 = -1;

        int row2 = -1;
        int col2 = -1;

        int[][] newBoard = new int[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                int value = array[i][j];
                newBoard[i][j] = value;
                if (row1 == -1 && value != 0) {
                    row1 = i;
                    col1 = j;
                } else if (row2 == -1 && value != 0) {
                    row2 = i;
                    col2 = j;
                }
            }
        }

        newBoard[row1][col1] = array[row2][col2];
        newBoard[row2][col2] = array[row1][col1];

        return new Board(newBoard);
    }

    public boolean equals(Object y) { // does this board equal y?
        if (this == y)
            return true;
        if (!(y instanceof Board))
            return false;
        Board b2 = (Board) y;

        if (dim != b2.dim)
            return false;

        if (array.length != b2.array.length)
            return false;

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (array[i][j] != (b2.array[i][j])) {
                    return false;
                }
            }
        }

        return true;
    }

    public Iterable<Board> neighbors() { // all neighboring boards
        List<Board> neighbors = new ArrayList<Board>();
        findNeighbors(neighbors);

        return neighbors;
    }

    public String toString() { // string representation of this board (in the
                               // output format specified below)
        StringBuilder sb = new StringBuilder();
        sb.append(dim);
//        sb.append(", ");
//        sb.append(manhattan);
//        sb.append(":");
//        sb.append(hamming);
        sb.append('\n');
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                sb.append(String.format("%2d", array[i][j]));
                sb.append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public static void main(String[] args) { // unit tests (not graded)
    }
}
