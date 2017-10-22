import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class KdTree {
   private Node root;
   private int N;
   
   // construct an empty set of points 
   public         KdTree() {
       N = 0;
   }
   
   private class Node {
       Point2D point;
       Node left;
       Node right;       
       boolean xCor;
       
       public Node(Point2D p, boolean xcor) {
           point = p;
           xCor = xcor;
       }
   }
   
   // is the set empty? 
   public           boolean isEmpty() {
       return N == 0;
   }
   
   // number of points in the set 
   public               int size() {
       return N;
   }
   
   // add the point to the set (if it is not already in the set)
   public              void insert(Point2D p) {
       if (p == null) throw new java.lang.IllegalArgumentException("Null argument for insert operation");
       if (!contains(p)) {
           root = insert(root, p, true);
           N++;
       }
   }
   
   private Node insert(Node node, Point2D p, boolean xcor) {          
       
       if (node == null) {
           node = new Node(p, xcor);           
           return node;
           
       }
       
       if (node.xCor) {
           if (p.x() < node.point.x()) node.left = insert(node.left, p, false);
           else node.right = insert(node.right, p, false);
       }
       
       else {
           if (p.y() < node.point.y()) node.left = insert(node.left, p, true);
           else node.right = insert(node.right, p, true);
       }
       
       return node;
       
       
   }
   
   // does the set contain point p? 
   public           boolean contains(Point2D p) {
       if (p == null) throw new java.lang.IllegalArgumentException("Null argument for contains operation");
       return contains(root, p);
   }
   
   private boolean contains(Node node, Point2D p) {
       if (node == null) return false;
       if (node.point.equals(p)) return true;
       
       if (node.xCor) {
           if (p.x() < node.point.x()) return contains(node.left, p);
           else return contains(node.right, p);
       }
       
       else {
           if (p.y() < node.point.y()) return contains(node.left, p);
           else return contains(node.right, p);
       }
   }
   
   // draw all points to standard draw 
   public              void draw() {
       draw(root);
   }
   
   private void draw(Node node) {
       if (node == null) return;
       node.point.draw();
       draw(node.left);
       draw(node.right);       
   }
   
   // all points that are inside the rectangle (or on the boundary) 
   public Iterable<Point2D> range(RectHV rect) {
       if (rect == null) throw new java.lang.IllegalArgumentException("Null argument for range operation");
       SET<Point2D> set = new SET<Point2D>();
       return range(set, root, rect);
   }
   
   private SET<Point2D> range(SET<Point2D> set, Node node, RectHV rect) {
       if (node == null) return set;
       if (node.xCor) {
           if (node.point.x() > rect.xmax()) range(set, node.left, rect);
           else if (node.point.x() < rect.xmin()) range(set, node.right, rect);
           else {
               if (rect.contains(node.point)) set.add(node.point);
               range(set, node.left, rect);
               range(set, node.right, rect);
           }
       } else {
           if (node.point.y() > rect.ymax()) range(set, node.left, rect);
           else if (node.point.y() < rect.ymin()) range(set, node.right, rect);
           else {
               if (rect.contains(node.point)) set.add(node.point);
               range(set, node.left, rect);
               range(set, node.right, rect);
           }
       }
       
       return set;
       
   }
   
   // a nearest neighbor in the set to point p; null if the set is empty 
   public           Point2D nearest(Point2D p) {
       if (p == null) throw new java.lang.IllegalArgumentException("Null argument for nearest operation");
       if (N == 0) return null;
       RectHV oriRect = new RectHV(0.0, 0.0, Double.MAX_VALUE, Double.MAX_VALUE);
       return nearest(oriRect, root, root.point, p);
   }
   
   private Point2D nearest(RectHV rect, Node node, Point2D pt, Point2D pq) {
       if (node == null) return pt; 
       
       if (node.point.distanceTo(pq) < pt.distanceTo(pq)) pt = node.point;   
       
       RectHV leftRect, rightRect;
       if (node.xCor) {
           leftRect = new RectHV(rect.xmin(), rect.ymin(), node.point.x(), rect.ymax());
           rightRect = new RectHV(node.point.x(), rect.ymin(), rect.xmax(), rect.ymax()); 
           
       } else {
           leftRect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.point.y());
           rightRect = new RectHV(rect.xmin(), node.point.y(), rect.xmax(), rect.ymax());           
       }
       
       if (leftRect.distanceTo(pq) >= pt.distanceTo(pq) && rightRect.distanceTo(pq) >= pt.distanceTo(pq)) return pt;
       else if (leftRect.distanceTo(pq) >= pt.distanceTo(pq)) return nearest(rightRect, node.right, pt, pq);
       else if (rightRect.distanceTo(pq) >= pt.distanceTo(pq)) return nearest(leftRect, node.left, pt, pq);
       else {
            if (leftRect.contains(pq)) {
                pt = nearest(leftRect, node.left, pt, pq);
                return nearest(rightRect, node.right, pt, pq);
               }
             pt = nearest(rightRect, node.right, pt, pq);
             return nearest(leftRect, node.left, pt, pq);
               
        }
   }
   
 
   
   // unit testing of the methods (optional) 
   public static void main(String[] args) {
   }
}