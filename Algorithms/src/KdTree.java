import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    
    private SET<Point2D> tree = new SET<>();
    
    public KdTree() { // construct an empty set of points

    }

    public boolean isEmpty() { // is the set empty?
        return tree.isEmpty();
    }

    public int size() { // number of points in the set
        return tree.size();
    }

    public void insert(Point2D p) { // add the point to the set (if it is not
                                    // already in the set)
        if (p == null) throw new NullPointerException();
        tree.add(p);
    }

    public boolean contains(Point2D p) { // does the set contain point p?
        if (p == null) throw new NullPointerException();
        return tree.contains(p);
    }

    public void draw() { // draw all points to standard draw
        for (Point2D p : tree) {
            StdDraw.point(p.x(), p.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect) { // all points that are inside
                                                  // the rectangle
        if (rect == null) throw new NullPointerException();

        List<Point2D> l = new ArrayList<>();
        for (Point2D p : tree) {
            if (rect.contains(p)) {
                l.add(p);
            }
        }
        return l;
    }

    public Point2D nearest(Point2D p) { // a nearest neighbor in the set to
                                        // point p; null if the set is empty
        if (p == null) throw new NullPointerException();

        double dist = Double.MAX_VALUE;
        Point2D nearest = null;
        
        for (Point2D ap : tree) {
            double d = p.distanceTo(ap);
            if (d < dist) {
                dist = d;
                nearest = ap;
            }
        }
        return nearest;
    }

    public static void main(String[] args) { // unit testing of the methods
                                             // (optional)

        StdDraw.show();
        
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setXscale(0.0, 1.0);
        StdDraw.setYscale(0.0, 1.0);   // leave a border to write text

    }
}
