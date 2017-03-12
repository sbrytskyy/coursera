import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    
    private List<LineSegment> arr = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) { // finds all line segments
                                                  // containing 4 points
        int len = points.length;
        
        for (int ip = 0; ip < len-3; ip++) {
            Point p = points[ip];
            for (int iq = ip + 1; iq < len-2; iq++) {
                Point q = points[iq];
                double slope_pq = p.slopeTo(q);
                //StdOut.println("p: " + p + ", q: " + q + ", slope: " + slope_pq);
                for (int ir = iq + 1; ir < len-1; ir++) {
                    Point r = points[ir];
                    double slope_pr = p.slopeTo(r);
                    //StdOut.println("p: " + p + ", r: " + r + ", slope: " + slope_pr);
                    if (Double.compare(slope_pq, slope_pr) == 0) {
                        for (int is = ir + 1; is < len; is++) {
                            Point s = points[is];
                            double slope_ps = p.slopeTo(s);
                            //StdOut.println("p: " + p + ", s: " + s + ", slope: " + slope_ps);
                            if (Double.compare(slope_pq, slope_ps) == 0) {
                                //StdOut.println("BINGO! p: " + p + ", q: " + q + ", r: " + r + ", s: " + s + ", slope: " + slope_pq);
                                LineSegment seg = createSegment(p, q, r, s);
                                arr.add(seg);
                            }
                        }
                    }
                }
            }
        }
    }

    private LineSegment createSegment(Point p, Point q, Point r, Point s) {
        List<Point> l = new ArrayList<>();
        l.add(p);
        l.add(q);
        l.add(r);
        l.add(s);
        Collections.sort(l);
        Point p1 = l.get(0);
        Point p2 = l.get(l.size()-1);
        
        //StdOut.println("p1: " + p1 + ", p2: " + p2);
        return new LineSegment(p1, p2);
    }

    public int numberOfSegments() { // the number of line segments
        return arr.size();
    }

    public LineSegment[] segments() { // the line segments
        return arr.toArray(new LineSegment[arr.size()]);
    }
}