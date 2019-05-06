import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTreeTest {

    public static void main(String[] args) {
//        visualTest();
        test();
    }

    private static void visualTest() {
        StdDraw.enableDoubleBuffering();
        visualTest("resources/kdtree/input10.txt");
//        visualTest("resources/kdtree/random1.txt");
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
        
        RectHV rect = new RectHV(0.47, 0.18, 0.69, 0.27);
        rect.draw();
        StdDraw.show();
    }

    private static void test() {
//        test("resources/kdtree/circle10.txt");
//        testContain1();
        testRange1();
        testRange2();
    }

    private static void testContain1() {
        In in = new In("resources/kdtree/random1.txt"); // input file

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

    private static void testRange1() {
        In in = new In("resources/kdtree/input5.txt"); // input file

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
        
        RectHV rect = new RectHV(0.47, 0.18, 0.69, 0.27);
        System.out.println("Range");
        Iterable<Point2D> range = tree.range(rect);
        assert !range.iterator().hasNext();
    }

    private static void testRange2() {
        In in = new In("resources/kdtree/input10.txt"); // input file

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
        
        RectHV rect = new RectHV(0.26, 0.19, 0.35, 0.24);
        System.out.println("Range");
        Iterable<Point2D> range = tree.range(rect);
        assert !range.iterator().hasNext();
    }
}
