import edu.princeton.cs.algs4.In;

public class FastCollinearPointsTest {

    public static void main(String[] args) {
        test();
    }

    private static void test() {
        test("resources/collinear/input6.txt");
        test("resources/collinear/input8.txt");
        test("resources/collinear/input9.txt");
        test("resources/collinear/input20.txt");
    }

    private static void test(String filename) {
        System.out.println("*---------------------------------------*");
        System.out.println(filename);
        System.out.println("*---------------------------------------*");
        In in = new In(filename); // input file

        Integer size = in.readInt();
        Point[] points = new Point[size];
        for(int i = 0; i < size; i++) {
            Integer x = in.readInt();
            Integer y = in.readInt();
            
            Point p = new Point(x, y);
            points[i] = p;
        }
        
        FastCollinearPoints fast = new FastCollinearPoints(points);
        LineSegment[] segments = fast.segments();
        System.out.println("*------------ SEGMENTS ---------------*");
        for (LineSegment lineSegment : segments) {
            System.out.println(lineSegment);
        }
        System.out.println("*---------------------------------------*");
        System.out.println();
    }
}
