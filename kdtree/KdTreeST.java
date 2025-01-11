import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StopwatchCPU;

public class KdTreeST<Value> {
    private Node root; // root of BST
    private Point2D championPoint; // Champion point for nearest recursion

    // construct an empty 2d-tree
    public KdTreeST() {
        root = null;
    }

    private class Node {
        private Point2D p;     // the point
        private Value val;     // the symbol table maps the point to this value
        private RectHV rect;   // the axis-aligned rectangle corresponding to this node
        private Node lb;       // the left/bottom subtree
        private Node rt;       // the right/top subtree
        private int size; // # Nodes in subtree rooted here

        // Node function to initialize values
        public Node(Point2D key, Value val, int n, RectHV rect) {
            this.p = key;
            this.val = val;
            this.size = n;
            this.rect = rect;
        }
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // number of points
    public int size() {
        return size(root);
    }

    // return number of key-value pairs in BST rooted at x
    private int size(Node x) {
        if (x == null) return 0;
        return x.size;
    }

    // associate the value val with point p
    public void put(Point2D p, Value val) {
        if (p == null || val == null)
            throw new IllegalArgumentException("Invalid argument");
        root = put(root, p, val,
                   Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY,
                   Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY,
                   true);
    }

    // Recursive method to insert point
    private Node put(Node x, Point2D key, Value val, double xmin, double ymin,
                     double xmax, double ymax, boolean horizontal) {
        RectHV a = new RectHV(xmin, ymin, xmax, ymax);
        if (x == null) return new Node(key, val, 1, a);
        if (horizontal) {
            // Change key’s value to val if key in subtree rooted at x.
            // Otherwise, add new node to subtree associating key with val.
            double cmp = key.x() - x.p.x();
            if (cmp < 0) {
                // if (a.intersects(x.rect))
                xmax = x.p.x();
                x.lb = put(x.lb, key, val, xmin, ymin, xmax, ymax, false);
            }
            else if (x.p.equals(key)) {
                x.val = val;
                x.size = size(x.lb) + size(x.rt) + 1;
                return x;
            }
            else if (cmp >= 0) {
                // if (a.intersects(x.rect))
                xmin = x.p.x();
                x.rt = put(x.rt, key, val, xmin, ymin, xmax, ymax, false);
            }
            x.size = size(x.lb) + size(x.rt) + 1;
        }
        else {
            // Change key’s value to val if key in subtree rooted at x.
            // Otherwise, add new node to subtree associating key with val.
            double cmp = key.y() - x.p.y();
            if (cmp < 0) {
                // if (a.intersects(x.rect))
                ymax = x.p.y();
                x.lb = put(x.lb, key, val, xmin, ymin, xmax, ymax, true);
            }
            else if (x.p.equals(key)) {
                x.val = val;
                x.size = size(x.lb) + size(x.rt) + 1;
                return x;
            }
            else if (cmp >= 0) {
                // if (a.intersects(x.rect))
                ymin = x.p.y();
                x.rt = put(x.rt, key, val, xmin, ymin, xmax, ymax, true);
            }
            x.size = size(x.lb) + size(x.rt) + 1;
        }
        return x;
    }

    // value associated with point p
    public Value get(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Invalid argument");
        return get(root, p, true);
    }

    // Recursive method to obtain value associated with point p
    private Value get(Node x, Point2D key, boolean horizontal) {
        if (x == null) return null;
        if (horizontal) {
            // Return value associated with key in the subtree rooted at x;
            // return null if key not present in subtree rooted at x.
            double cmp = key.x() - x.p.x();
            if (cmp < 0) return get(x.lb, key, false);
            else if (x.p.equals(key)) return x.val;
            else if (cmp >= 0) return get(x.rt, key, false);

            // // Corner case if same x
            // cmp = key.y() - x.p.y();
            // if (cmp < 0) return get(x.lb, key, true);
            // else if (cmp > 0) return get(x.rt, key, true);
            // else if (x.p.equals(key)) return x.val;
        }
        else {
            // Return value associated with key in the subtree rooted at x;
            // return null if key not present in subtree rooted at x.
            double cmp = key.y() - x.p.y();
            if (cmp < 0) return get(x.lb, key, true);
            else if (x.p.equals(key)) return x.val;
            else if (cmp >= 0) return get(x.rt, key, true);
            // Corner case if same y
            // cmp = key.x() - x.p.x();
            // if (cmp < 0) return get(x.lb, key, false);
            // else if (cmp > 0) return get(x.rt, key, false);
        }
        return x.val;
    }

    // does the symbol table contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Invalid argument");
        return get(p) != null;
    }

    // all points in the symbol table
    public Iterable<Point2D> points() {
        Queue<Node> nqueue = new Queue<Node>();
        Queue<Point2D> queue = new Queue<Point2D>();
        nqueue.enqueue(root);
        while (!nqueue.isEmpty()) {
            Node x = nqueue.dequeue();
            if (x == null) continue;
            queue.enqueue(x.p);
            nqueue.enqueue(x.lb);
            nqueue.enqueue(x.rt);
        }
        return queue;
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Invalid argument");
        Queue<Point2D> queue = new Queue<Point2D>();
        return range(root, rect, queue);

    }

    // Recursive method to find range
    private Iterable<Point2D> range(Node x, RectHV rect, Queue<Point2D> queue) {
        if (x == null) return queue;
        if (rect.intersects(x.rect)) {
            RectHV temp = new RectHV(x.p.x(), x.p.y(), x.p.x(), x.p.y());
            if (temp.intersects(rect)) queue.enqueue(x.p);
            range(x.lb, rect, queue);
            range(x.rt, rect, queue);
        }
        return queue;
    }

    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Invalid argument");
        if (!isEmpty()) championPoint = root.p;
        else return null;
        return nearest(p, root, Double.POSITIVE_INFINITY, root.p, true);
    }

    // Recursive optimization to find nearest neighbor of point p
    private Point2D nearest(Point2D p, Node x, double champion,
                            Point2D champPointLocal, boolean horizontal) {
        if (isEmpty()) return null;
        if (x == null) return championPoint;
        if (championPoint.distanceSquaredTo(p)
                < x.rect.distanceSquaredTo(p)) return championPoint;
        if (x.p.distanceSquaredTo(p) < championPoint.distanceSquaredTo(p)) {
            champion = x.p.distanceSquaredTo(p);
            championPoint = x.p;
        }
        if (horizontal && p.x() <= x.p.x()) {
            nearest(p, x.lb, champion, championPoint, false);
            nearest(p, x.rt, champion, championPoint, false);
        }
        else if (horizontal && p.x() > x.p.x()) {
            nearest(p, x.rt, champion, championPoint, false);
            nearest(p, x.lb, champion, championPoint, false);
        }
        if (!horizontal && p.y() <= x.p.y()) {
            nearest(p, x.lb, champion, championPoint, true);
            nearest(p, x.rt, champion, championPoint, true);
        }
        else if (!horizontal && p.y() > x.p.y()) {
            nearest(p, x.rt, champion, championPoint, true);
            nearest(p, x.lb, champion, championPoint, true);
        }
        return championPoint;
    }


    // unit testing (required)
    public static void main(String[] args) {
        KdTreeST<Integer> kdTreeST = new KdTreeST<Integer>();
        String filename = args[0];
        In in = new In(filename);
        for (int i = 0; !in.isEmpty(); i++) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdTreeST.put(p, i);
        }
        StopwatchCPU time = new StopwatchCPU();
        for (int i = 0; i < 500000; i++) {
            double x = StdRandom.uniformDouble(0, 1);
            double y = StdRandom.uniformDouble(0, 1);
            Point2D testPoint = new Point2D(x, y);
            kdTreeST.nearest(testPoint);
        }
        System.out.println("CPU time: " + time.elapsedTime());

        double x = StdRandom.uniformDouble(0, 1);
        double y = StdRandom.uniformDouble(0, 1);
        Point2D testPoint = new Point2D(x, y);


        System.out.println("Nearest neighbor of point " + "" + testPoint
                                   + " : " + kdTreeST.nearest(testPoint));

        RectHV rect = new RectHV(0, 0, 0.01, 0.01);
        System.out.println("All points in the 0-0.3 "
                                   + "x plane: " + kdTreeST.range(rect));
        System.out.println("Value at point " + testPoint +
                                   ": " + kdTreeST.get(testPoint));
        System.out.println("Size: " + kdTreeST.size());
        System.out.println("Does the 2-d tree contain the point " + testPoint
                                   + " : " + kdTreeST.contains(testPoint));
        System.out.println("All " + kdTreeST.size() + " points in level-order "
                                   + "traversal: " + kdTreeST.points());


    }
}

