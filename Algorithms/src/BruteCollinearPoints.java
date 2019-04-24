import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BruteCollinearPoints {

	private List<LineSegment> arr = new ArrayList<>();

	public BruteCollinearPoints(Point[] points) { // finds all line segments
													// containing 4 points

		if (points == null) {
			throw new IllegalArgumentException();
		}

		int len = points.length;

		for (int ip = 0; ip < len - 1; ip++) {
			Point p = points[ip];
			if (p == null) {
				throw new IllegalArgumentException();
			}
			for (int iq = ip + 1; iq < len; iq++) {
				Point q = points[iq];
				if (q == null) {
					throw new IllegalArgumentException();
				}
				if (q.compareTo(p) == 0) {
					throw new IllegalArgumentException();
				}
			}
		}

		for (int ip = 0; ip < len - 3; ip++) {
			Point p = points[ip];
			for (int iq = ip + 1; iq < len - 2; iq++) {
				Point q = points[iq];
				double slopePQ = p.slopeTo(q);
				for (int ir = iq + 1; ir < len - 1; ir++) {
					Point r = points[ir];
					double slopePR = p.slopeTo(r);
					if (Double.compare(slopePQ, slopePR) == 0) {
						for (int is = ir + 1; is < len; is++) {
							Point s = points[is];
							double slopePS = p.slopeTo(s);
							if (Double.compare(slopePQ, slopePS) == 0) {
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
