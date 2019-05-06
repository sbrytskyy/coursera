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
        public Node left;
        public Node right;

        public Node(Point2D point) {
            this.point = point;
        }

        public int compareTo(Node that) {
            if (this == that)
                return 0;

            int compare = Double.compare(this.point.x(), that.point.x());
            if (compare != 0) {
                return compare;
            }
            compare = Double.compare(this.point.y(), that.point.y());
            if (compare != 0) {
                return compare;
            }

            return 0;
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

        Node node = new Node(p);
        if (root == null) {
            root = node;
            size++;

            return;
        }

        addNode(root, node);
    }

    private void addNode(Node node, Node newNode) {

        if (node.compareTo(newNode) < 0) {
            if (node.left == null) {
                node.left = newNode;
                size++;
            } else {
                addNode(node.left, newNode);
            }
        } else if (node.compareTo(newNode) > 0) {
            if (node.right == null) {
                node.right = newNode;
                size++;
            } else {
                addNode(node.right, newNode);
            }
        }
    }

    public boolean contains(Point2D p) { // does the set contain point p?
        if (p == null)
            throw new IllegalArgumentException();

        Node newNode = new Node(p);
        Node node = root;
        return find(node, newNode);
    }

    private boolean find(Node node, Node newNode) {
        if (node == null) {
            return false;
        }
        int compareTo = node.compareTo(newNode);
        if (compareTo == 0) {
            return true;
        }
        if (compareTo < 0) {
            return find(node.left, newNode);
        }
        return find(node.right, newNode);
    }

    public void draw() { // draw all points to standard draw
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        
        drawNodes(root);
    }

    private void drawNodes(Node node) {
        if (node == null) {
            return;
        }
        Point2D p = node.point;
        StdDraw.point(p.x(), p.y());

        drawNodes(node.left);
        drawNodes(node.right);
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
        
        verifyRange(node.left, rect, result);
        verifyRange(node.right, rect, result);
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
        
        double distance = node.point.distanceSquaredTo(p);
        if (Double.compare(distance, delta) < 0) {
            delta = distance;
            nearest = node.point;
        }
        
        findNearest(node.left, p);
        findNearest(node.right, p);
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
