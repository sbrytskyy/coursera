import java.awt.Color;
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
//        test();
        testLoad();
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
        testContains1();
        testRange1();
        testRange2();
        testNearest1();
    }

    private static void testContains1() {
        System.out.println("Contains 1");
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
        System.out.println(p);
        boolean contains = tree.contains(p);
        assert contains;
    }

    private static void testRange1() {
        System.out.println("Range 1");
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
        Iterable<Point2D> range = tree.range(rect);
        assert !range.iterator().hasNext();
    }

    private static void testRange2() {
        System.out.println("Range 2");
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
        Iterable<Point2D> range = tree.range(rect);
        assert !range.iterator().hasNext();
    }

    private static void testNearest5() {
        System.out.println("Nearest 5");
        In in = new In("resources/kdtree/nearest5.txt"); // input file

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

        Point2D p = new Point2D(0.75, 1.0);
        Point2D nearest = tree.nearest(p);
        Point2D expected = new Point2D(0.875, 0.625);
        assert nearest.equals(expected);
    }

    private static void testNearest1() {
        System.out.println("Nearest 1");
        In in = new In("resources/kdtree/nearest1.txt"); // input file

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

        Point2D p = new Point2D(0.707, 0.451);
        Point2D nearest = tree.nearest(p);
        Point2D expected = new Point2D(0.5, 0.4);

//        drawTreeAndPoint("resources/kdtree/nearest1.txt", p);
        assert nearest.equals(expected);
    }

    private static void drawTreeAndPoint(String filename, Point2D p) {
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

        StdDraw.setPenRadius(0.05);
        StdDraw.setPenColor(Color.PINK);
        p.draw();
        StdDraw.show();
    }

    private static void testLoad() {
//        testLoad("resources/kdtree/input10K.txt");
//        testLoad("resources/kdtree/input20K.txt");
//        testLoad("resources/kdtree/input40K.txt");
//        testLoad("resources/kdtree/input80K.txt");
//        testLoad("resources/kdtree/input400K.txt");
//        testLoad("resources/kdtree/input800K.txt");
        testLoad("resources/kdtree/input1M.txt");
    }

    private static void testLoad(String filename) {
        System.out.println(filename);
        KdTree tree = buildTree(filename);
        testNearest(tree);
    }

    private static KdTree buildTree(String filename) {
        In in = new In(filename); // input file

        KdTree tree = new KdTree();

        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();

            Point2D p = new Point2D(x, y);

            tree.insert(p);
        }
        return tree;
    }

    private static void testNearest(KdTree tree) {
        Random r = new Random();

        long totalX = 0;
        long totalY = 0;

        int trials = 10000000;

        for (int i = 0; i < trials; i++) {

            double x = r.nextDouble();
            double y = r.nextDouble();

            Point2D p = new Point2D(x, y);

            Point2D.xCounter = 0;
            Point2D.yCounter = 0;

            tree.nearest(p);

            totalX += Point2D.xCounter;
            totalY += Point2D.yCounter;
        }

        System.out.println("X(): " + (double) totalX / trials);
        System.out.println("Y(): " + (double) totalY / trials);
    }

}
