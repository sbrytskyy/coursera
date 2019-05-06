import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;

public class KdTreeTest {

    public static void main(String[] args) {
        test();
//        visualTest();
    }

    private static void visualTest() {
        StdDraw.enableDoubleBuffering();
//        visualTest("resources/kdtree/input10.txt");
        visualTest("resources/kdtree/random1.txt");
//        visualTest("resources/kdtree/circle10.txt");
    }

    private static void visualTest(String filename) {
        In in = new In(filename); // input file

        KdTree tree = new KdTree();

        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            tree.insert(new Point2D(x, y));

            tree.draw();
            StdDraw.show();
            StdDraw.pause(1000);
        }
    }

    private static void test() {
//      test("resources/kdtree/input10.txt");
//        test("resources/kdtree/circle10.txt");
        test("resources/kdtree/random1.txt");
    }

    private static void test(String filename) {
        In in = new In(filename); // input file

        List<Point2D> points = new ArrayList<>();

        KdTree tree = new KdTree();

        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();

            Point2D p = new Point2D(x, y);
            System.out.println(p);

            points.add(p);
            tree.insert(p);
        }
        
//        tree.print();
        
        Point2D p = new Point2D(0.5, 0.25);
        System.out.println("Search");
        System.out.println(p);
        boolean contains = tree.contains(p);
        assert contains;
    }
}
