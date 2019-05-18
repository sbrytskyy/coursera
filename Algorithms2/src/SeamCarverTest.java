import java.util.Arrays;

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

public class SeamCarverTest {

    public static void main(String[] args) {
        SeamCarverTest sct = new SeamCarverTest();
        sct.test1("resources/seam/6x5.png");
        sct.test1("resources/seam/chameleon.png");
        
//        sct.test2("resources/seam/7x3.png");
    }

    private void test1(String filename) {

        Picture picture = new Picture(filename);
        SeamCarver sc = new SeamCarver(picture);

        int height = sc.height();
        int width = sc.width();
        StdOut.println("W: " + width + ", H: " + height);

//        for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
//                StdOut.print(String.format("%2f", sc.energy(x, y)));
//                StdOut.print("\t");
//            }
//            StdOut.println();
//        }
        
        int[] verticalSeam = sc.findVerticalSeam();
        StdOut.println(Arrays.toString(verticalSeam));

    }

    private void test2(String filename) {

        Picture picture = new Picture(filename);
        SeamCarver sc = new SeamCarver(picture);

        int height = sc.height();
        int width = sc.width();
        StdOut.println("W: " + width + ", H: " + height);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                StdOut.print(String.format("%2f", sc.energy(x, y)));
                StdOut.print("\t");
            }
            StdOut.println();
        }

        int[] seam = new int[] {2, 3, 2};
        sc.removeVerticalSeam(seam);
    }
}
