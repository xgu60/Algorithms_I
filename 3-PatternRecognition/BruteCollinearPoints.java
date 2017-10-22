import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class BruteCollinearPoints {
    private int N;
    private LineSegment[] segs;
    
    public BruteCollinearPoints(Point[] points) {
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
        Point[] temp = new Point[4];
        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    //if i j k are not in one line, no need to test l 
                    if (points[i].slopeTo(points[j]) != points[i].slopeTo(points[k])) continue;
                    for (int l = k + 1; l < points.length; l++) {
                        //i j k l are on same line
                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[l])) {
                            N++;
                            //double the size of segs
                            if (N > segs.length) segmentsDouble();                             
                            temp[0] = points[i];
                            temp[1] = points[j];
                            temp[2] = points[k];
                            temp[3] = points[l];
                            segs[N - 1] = new LineSegment(findMin(temp), findMax(temp));
                            
                        }
                    
                    }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
    
    
}