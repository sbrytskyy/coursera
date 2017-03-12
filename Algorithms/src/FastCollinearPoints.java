import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FastCollinearPoints {
    
    private List<LineSegment> arr = new ArrayList<>();
    
    private class Item implements Comparable<Item> {
        
        private double slope;
        private Point p;
        
        public int compareTo(Item that) {
            if (slope > that.slope) {
                return 1;
            } else if (slope < that.slope) {
                return -1;
            }
            return 0;
        }
    }


    public FastCollinearPoints(Point[] points) { // finds all line segments
                                                 // containing 4 or more points

        if (points == null) {
            throw new NullPointerException();
        }

        int len = points.length;

        for (int ip = 0; ip < len - 1; ip++) {
            Point p = points[ip];
            if (p == null) {
                throw new NullPointerException();
            }
            
            List<Item> items = new ArrayList<>();
            
            for (int iq = ip + 1; iq < len; iq++) {
                Point q = points[iq];
                if (q == null) {
                    throw new NullPointerException();
                }
                if (q.compareTo(p) == 0) {
                    throw new IllegalArgumentException();
                }
                
                double slopePQ = p.slopeTo(q);
                Item item = new Item();
                item.slope = slopePQ;
                item.p = q;
                items.add(item);
            }
            Collections.sort(items);

            List<Point> l = new ArrayList<>();

            int counter = 0;
            for (int i = 1; i < items.size(); i++) {
                Item itemPrev = items.get(i-1);
                Item item = items.get(i);
                if (Double.compare(item.slope, itemPrev.slope) == 0) {
                    if (l.isEmpty()) {
                        l.add(itemPrev.p);
                        counter++;
                    }
                    l.add(item.p);
                    counter++;
                } else {
                    if (counter >= 3) {
                        l.add(p);
                        LineSegment seg = createSegment(l);
                        arr.add(seg);
                    }
                    counter = 0;
                    l = new ArrayList<>();
                }
            }
            if (counter >= 3) {
                l.add(p);
                LineSegment seg = createSegment(l);
                arr.add(seg);
            }
        }
    }

    private LineSegment createSegment(List<Point> l) {
        Collections.sort(l);
        Point p1 = l.get(0);
        Point p2 = l.get(l.size() - 1);

        return new LineSegment(p1, p2);
    }

    public int numberOfSegments() { // the number of line segments
        return arr.size();
    }

    public LineSegment[] segments() { // the line segments
        return arr.toArray(new LineSegment[arr.size()]);
    }
}