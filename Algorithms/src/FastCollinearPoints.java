import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {

    private final List<LineSegment> segmentsList = new ArrayList<>();

    public FastCollinearPoints(Point[] points) { // finds all line segments
                                                 // containing 4 or more points

        if (points == null) {
            throw new IllegalArgumentException();
        }

        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException();
            }
        }

        Point[] aux = Arrays.copyOf(points, points.length);
        Arrays.sort(aux);
        verifyDuplicates(aux);

        if (points.length < 4) {
            return;
        }

        for (int i = 0; i < points.length; i++) {
            Point start = points[i];

            Arrays.sort(aux);
            Arrays.sort(aux, start.slopeOrder());

            int counter = 1;
            Point end = aux[1]; // aux[0] should be the same as start
            double slopeRef = start.slopeTo(end);

            Point point = end;
            Point min = start.compareTo(point) < 0 ? start : point;
            Point max = start.compareTo(point) > 0 ? start : point;

            for (int ai = 2; ai < aux.length; ai++) {
                point = aux[ai];
                double slope = start.slopeTo(point);
                if (Double.compare(slope, slopeRef) == 0) {
                    if (point.compareTo(min) < 0) {
                        min = point;
                    } else if (point.compareTo(max) > 0) {
                        max = point;
                    }
                    end = point;
                    counter++;
                } else {
                    if (counter >= 3) {
                        if (start.compareTo(min) == 0 && end.compareTo(max) == 0) {
                            LineSegment lineSegment = new LineSegment(start, end);
                            segmentsList.add(lineSegment);
                        }
                    }
                    slopeRef = slope;
                    counter = 1;

                    min = start.compareTo(point) < 0 ? start : point;
                    max = start.compareTo(point) > 0 ? start : point;
                    end = point;
                }
            }
            if (counter >= 3) {
                if (start.compareTo(min) == 0 && end.compareTo(max) == 0) {
                    LineSegment lineSegment = new LineSegment(start, end);
                    segmentsList.add(lineSegment);
                }
            }
        }
    }

    private void verifyDuplicates(Point[] sorted) {
        for (int i = 1; i < sorted.length; i++) {
            Point p = sorted[i - 1];
            Point q = sorted[i];
            if (p == null || q == null) {
                throw new IllegalArgumentException();
            }
            if (q.compareTo(p) == 0) {
                throw new IllegalArgumentException();
            }
        }
    }

    public int numberOfSegments() { // the number of line segments
        return segmentsList.size();
    }

    public LineSegment[] segments() { // the line segments
        return segmentsList.toArray(new LineSegment[segmentsList.size()]);
    }
}