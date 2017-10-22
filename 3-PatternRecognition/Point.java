import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {
    private final int x;
    private final int y;
    
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void draw() {
        StdDraw.point(x, y);
    }
    
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }
    
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
    
    public int compareTo(Point that) {
        if (this.y < that.y) return -1;
        else if (this.y > that.y) return 1;
        else {
            if (this.x < that.x) return -1;
            else if (this.x > that.x) return 1;
            else return 0;
        }
    }
    
    public double slopeTo(Point that) {
        if (this.x == that.x) {
            if (this.y == that.y) return Double.NEGATIVE_INFINITY;
            return Double.POSITIVE_INFINITY;
        }
        else if (this.y == that.y) return +0.0;
        else return (double)(that.y - this.y) / (that.x - this.x);
    }
    
    public Comparator<Point> slopeOrder() {
        return new pointComparator();
    }
    
    private class pointComparator implements Comparator<Point> {
        public int compare(Point a, Point b) {
            if (slopeTo(a) - slopeTo(b) > 0) return 1;
            else if (slopeTo(a) - slopeTo(b) < 0) return -1;
            else return 0;
        }
    }
    
    public static void main(String[] args){
        Point a = new Point(100,100);
        Point b = new Point(100,200);
        Point c = new Point(200,100);
        a.draw();
        a.drawTo(b);
        a.drawTo(c);
        StdDraw.show();
        Comparator<Point> e = a.slopeOrder();        
        System.out.println(e.compare(b, c));
        System.out.println(e.compare(a, c));
        System.out.println(e.compare(b, b));
    }
    
    
}