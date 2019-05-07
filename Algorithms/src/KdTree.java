import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

    private Node root;
    private int size = 0;

    private Point2D nearest;
    private double delta;

    private static class Node {

        public final Point2D point;
        public final RectHV rect;
        public boolean vertical;
        public Node left;
        public Node right;

        public Node(Point2D point, RectHV rect, boolean vertical) {
            this.point = point;
            this.rect = rect;
            this.vertical = vertical;
        }
    }

    public KdTree() { // construct an empty set of points

    }

    public boolean isEmpty() { // is the set empty?
        return root == null;
    }

    public int size() { // number of points in the set
        return size;
    }

    public void insert(Point2D p) { // add the point to the set (if it is not
                                    // already in the set)
        if (p == null)
            throw new IllegalArgumentException();

        if (root == null) {
            RectHV rect = new RectHV(0, 0, 1, 1);
            Node node = new Node(p, rect, true);
            root = node;
            size++;

            return;
        }

        addNode(root, p);
    }

    private void addNode(Node node, Point2D p) {

        if (p.equals(node.point)) {
            return;
        }

        if (node.vertical) {
            if (Double.compare(p.x(), node.point.x()) < 0) {
                if (node.left == null) {
                    RectHV rect = new RectHV(node.rect.xmin(), node.rect.ymin(), node.point.x(), node.rect.ymax());
                    Node newNode = new Node(p, rect, !node.vertical);
                    node.left = newNode;
                    size++;
                } else {
                    addNode(node.left, p);
                }
            } else {
                if (node.right == null) {
                    RectHV rect = new RectHV(node.point.x(), node.rect.ymin(), node.rect.xmax(), node.rect.ymax());
                    Node newNode = new Node(p, rect, !node.vertical);
                    node.right = newNode;
                    size++;
                } else {
                    addNode(node.right, p);
                }
            }
        } else {
            if (Double.compare(p.y(), node.point.y()) < 0) {
                if (node.left == null) {
                    RectHV rect = new RectHV(node.rect.xmin(), node.rect.ymin(), node.rect.xmax(), node.point.y());
                    Node newNode = new Node(p, rect, !node.vertical);
                    node.left = newNode;
                    size++;
                } else {
                    addNode(node.left, p);
                }
            } else {
                if (node.right == null) {
                    RectHV rect = new RectHV(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.rect.ymax());
                    Node newNode = new Node(p, rect, !node.vertical);
                    node.right = newNode;
                    size++;
                } else {
                    addNode(node.right, p);
                }
            }
        }
    }

    public boolean contains(Point2D p) { // does the set contain point p?
        if (p == null)
            throw new IllegalArgumentException();

        return find(root, p);
    }

    private boolean find(Node node, Point2D p) {

        if (node == null) {
            return false;
        }

        if (p.equals(node.point)) {
            return true;
        }

        Node toSearch;

        if (node.vertical) {
            if (p.x() < node.point.x()) {
                toSearch = node.left;
            } else {
                toSearch = node.right;
            }
        } else {
            if (p.y() < node.point.y()) {
                toSearch = node.left;
            } else {
                toSearch = node.right;
            }
        }

        return find(toSearch, p);
    }

    public void draw() { // draw all points to standard draw
        drawNodes(root, true);
    }

    private void drawNodes(Node node, boolean vertical) {
        if (node == null) {
            return;
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);

        Point2D p = node.point;
        RectHV rect = node.rect;
        p.draw();

        StdDraw.setPenRadius();
        if (vertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(p.x(), rect.ymin(), p.x(), rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(rect.xmin(), p.y(), rect.xmax(), p.y());
        }

        drawNodes(node.left, !vertical);
        drawNodes(node.right, !vertical);
    }

    public Iterable<Point2D> range(RectHV rect) { // all points that are inside
                                                  // the rectangle
        if (rect == null)
            throw new IllegalArgumentException();

        List<Point2D> result = new ArrayList<>();

        verifyRange(root, rect, result);
        return result;
    }

    private void verifyRange(Node node, RectHV rect, List<Point2D> result) {
        if (node == null) {
            return;
        }

        Point2D p = node.point;
        if (rect.contains(p)) {
            result.add(p);
        }

        if (node.left != null && node.left.rect.intersects(rect)) {
            verifyRange(node.left, rect, result);
        }
        if (node.right != null && node.right.rect.intersects(rect)) {
            verifyRange(node.right, rect, result);
        }
    }

    public Point2D nearest(Point2D p) { // a nearest neighbor in the set to
                                        // point p; null if the set is empty
        if (p == null)
            throw new IllegalArgumentException();

        nearest = null;
        delta = Double.POSITIVE_INFINITY;

        findNearest(root, p);

        return nearest;
    }

    private void findNearest(Node node, Point2D p) {
        if (node == null) {
            return;
        }

        if (node.point.equals(p)) {
            nearest = node.point;
            delta = 0.0;
            return;
        }

        double distance = node.point.distanceSquaredTo(p);
        if (Double.compare(distance, delta) < 0) {
            delta = distance;
            nearest = node.point;
        }

        if (Double.compare(node.rect.distanceSquaredTo(p), delta) < 0) {
            Node first;
            Node second;

            if (node.vertical) {
                if (p.x() < node.point.x()) {
                    first = node.left;
                    second = node.right;
                } else {
                    first = node.right;
                    second = node.left;
                }
            } else {
                if (p.y() < node.point.y()) {
                    first = node.left;
                    second = node.right;
                } else {
                    first = node.right;
                    second = node.left;
                }
            }

            findNearest(first, p);
            findNearest(second, p);
        }
    }

    public static void main(String[] args) { // unit testing of the methods
                                             // (optional)

        StdDraw.show();

        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setXscale(0.0, 1.0);
        StdDraw.setYscale(0.0, 1.0); // leave a border to write text

    }
}
