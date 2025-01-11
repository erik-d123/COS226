import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StopwatchCPU;

public class PointST<Value> {
    private RedBlackBST<Point2D, Value> redBlackST; // Data structure for ST

    // construct an empty symbol table of points
    public PointST() {
        redBlackST = new RedBlackBST<Point2D, Value>();
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return redBlackST.isEmpty();
    }

    // number of points
    public int size() {
        return redBlackST.size();
    }

    // associate the value val with point p
    public void put(Point2D p, Value val) {
        if (p == null || val == null)
            throw new IllegalArgumentException("Invalid argument");
        redBlackST.put(p, val);
    }

    // value associated with point p
    public Value get(Point2D p) {
        return redBlackST.get(p);
    }

    // does the symbol table contain point p?
    public boolean contains(Point2D p) {
        return redBlackST.contains(p);
    }

    // all points in the symbol table
    public Iterable<Point2D> points() {
        return redBlackST.keys();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Invalid argument");
        Queue<Point2D> queue = new Queue<Point2D>();
        for (Point2D a : redBlackST.keys()) {
            RectHV temp = new RectHV(a.x(), a.y(), a.x(), a.y());
            if (rect.intersects(temp)) queue.enqueue(a);
        }
        return queue;
    }

    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Invalid argument");
        if (redBlackST.isEmpty()) return null;
        Point2D championPoint = new Point2D(0, 0);
        double champion = Double.POSITIVE_INFINITY;
        for (Point2D a : redBlackST.keys()) {
            if (a.distanceSquaredTo(p) < champion) {
                champion = a.distanceSquaredTo(p);
                championPoint = a;
            }
        }
        return championPoint;
    }

    // unit testing (required)
    public static void main(String[] args) {
        PointST<Integer> pointST = new PointST<Integer>();
        String filename = args[0];
        In in = new In(filename);
        for (int i = 0; !in.isEmpty(); i++) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            pointST.put(p, i);
        }
        StopwatchCPU time = new StopwatchCPU();
        for (int i = 0; i < 100; i++) {
            double x = StdRandom.uniformDouble(0, 1);
            double y = StdRandom.uniformDouble(0, 1);
            Point2D testPoint = new Point2D(x, y);
            pointST.nearest(testPoint);
        }
        System.out.println("CPU time: " + time.elapsedTime());

        double x = StdRandom.uniformDouble(0, 1);
        double y = StdRandom.uniformDouble(0, 1);
        Point2D testPoint = new Point2D(x, y);


        System.out.println("Nearest neighbor of point " + "" + testPoint
                                   + " : " + pointST.nearest(testPoint));

        RectHV rect = new RectHV(0, 0, 0.01, 0.01);
        System.out.println("All points in the 0-0.3 "
                                   + "x plane: " + pointST.range(rect));
        System.out.println("Value at point " + testPoint +
                                   ": " + pointST.get(testPoint));
        System.out.println("Size: " + pointST.size());
        System.out.println("Does the 2-d tree contain the point " + testPoint
                                   + " : " + pointST.contains(testPoint));
        System.out.println("All " + pointST.size() + " points in level-order "
                                   + "traversal: " + pointST.points());

    }

}
