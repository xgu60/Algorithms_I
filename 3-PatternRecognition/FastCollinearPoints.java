import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;


public class FastCollinearPoints {
    private int N;
    private LineSegment[] segs;
    
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new java.lang.IllegalArgumentException("null argument!");
        for(int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new java.lang.IllegalArgumentException("null elements!");
        }
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) throw new java.lang.IllegalArgumentException("identical elements!");
            }
        }
        N = 0;
        segs = new LineSegment[2];
                       
        for (int i = 0; i < points.length - 3; i++) { 
            //System.out.println(points[i]);
            Comparator<Point> comp = points[i].slopeOrder();
            Arrays.sort(points, i + 1, points.length, comp);
            int j = i + 1;            
            while (j < points.length - 2) {
                if (comp.compare(points[j], points[j + 2]) != 0) j++;
                else {
                    int end = j + 2;
                    while (end < points.length - 1 && comp.compare(points[j], points[end + 1]) == 0) end++;
                    Point[] temp = new Point[end - j + 2];
                    temp[0] = points[i];
                    for (int k = 1; k < temp.length; k++) {
                        temp[k] = points[j + k - 1];
                    }
                    
                    N++;
                    if (N > segs.length) segmentsDouble();
                    segs[N - 1] = new LineSegment(findMin(temp), findMax(temp));
                    //System.out.println(segs[N - 1]);
                    //System.out.println("----------");
                    j = end + 1;
                }
            }
        }
    }
    
    private Point findMax(Point[] points) {
        Point temp = points[0];
        for (int i = 1; i < points.length; i++) {
            if (points[i].compareTo(temp) > 0) temp = points[i];
        }
        return temp;
    }
    
    private Point findMin(Point[] points) {
        Point temp = points[0];
        for (int i = 1; i < points.length; i++) {
            if (points[i].compareTo(temp) < 0) temp = points[i];
        }
        return temp;
    }
    
    private void segmentsDouble() {
        LineSegment[] newSegs = new LineSegment[segs.length * 2];
        for (int i = 0; i < segs.length; i++) {
            newSegs[i] = segs[i];
        }
        segs = newSegs;            
    }
    
    public int numberOfSegments() {
        return N;
    }
    
    public LineSegment[] segments() {
        LineSegment[] segs = new LineSegment[N];
        for (int i = 0; i < N; i++) {
            segs[i] = this.segs[i];
        }
        return segs;
    }
    
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
    
    
}