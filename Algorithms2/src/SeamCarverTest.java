import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

public class SeamCarverTest {

    public static void main(String[] args) {
        SeamCarverTest sct = new SeamCarverTest();
        sct.test1("resources/seam/3x4.png");
    }

    private void test1(String filename) {

        Picture picture = new Picture(filename);
        SeamCarver sc = new SeamCarver(picture);

        int height = sc.height();
        int width = sc.width();
        StdOut.println("W: " + width + ", H: " + height);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                StdOut.print(String.format("%2f", sc.energy(x, y)));
                StdOut.print(" ");
            }
            StdOut.println();
        }

    }
}
