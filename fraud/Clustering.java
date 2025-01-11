import edu.princeton.cs.algs4.CC;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.KruskalMST;
import edu.princeton.cs.algs4.Point2D;

import java.util.Arrays;

public class Clustering {
    private CC components; // Clusters of points
    private int m; // Number of points

    // run the clustering algorithm and create the clusters
    public Clustering(Point2D[] locations, int k) {
        validate(locations, k);
        this.m = locations.length;
        EdgeWeightedGraph g = new EdgeWeightedGraph(m);
        for (int i = 0; i < m; i++) {
            for (int j = i + 1; j < m; j++)
                g.addEdge(new Edge(i, j,
                                   locations[i].distanceSquaredTo(locations[j])));
        }
        KruskalMST mst = new KruskalMST(g);
        EdgeWeightedGraph cluster = new EdgeWeightedGraph(m);
        Edge[] edges = new Edge[m - 1];
        int i = 0;
        for (Edge e : mst.edges()) edges[i++] = e;
        Arrays.sort(edges);
        for (int j = 0; j < m - k; j++) cluster.addEdge(edges[j]);
        components = new CC(cluster);
    }

    // return the cluster of the ith point
    public int clusterOf(int i) {
        validate(i);
        return components.id(i);
    }

    // use the clusters to reduce the dimensions of an input
    public int[] reduceDimensions(int[] input) {
        validate(input);
        int[] dimension = new int[components.count()];
        for (int i = 0; i < input.length; i++)
            dimension[components.id(i)] = dimension[components.id(i)] + input[i];
        return dimension;
    }

    // Check whether arguments are valid
    private void validate(Point2D[] locations, int k) {
        if (locations == null || k < 1 || k > locations.length)
            throw new IllegalArgumentException("Invalid argument");
        for (Point2D i : locations)
            if (i == null)
                throw new IllegalArgumentException("Invalid argument");
    }

    // Check whether arguments are valid
    private void validate(int[] input) {
        if (input == null || input.length != m)
            throw new IllegalArgumentException("Invalid argument");
    }

    // Check whether arguments are valid
    private void validate(int i) {
        if (i < 0 || i > m - 1)
            throw new IllegalArgumentException("Invalid argument");
    }

    // unit testing (required)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point2D[] points = new Point2D[n];
        for (int i = 0; i < n; i++)
            points[i] = new Point2D(in.readDouble(), in.readDouble());
        int[] transactions = {
                5, 6, 7, 0, 6, 7, 5, 6, 7, 0, 6,
                7, 0, 6, 7, 0, 6, 7, 0, 6, 7
        };
        Clustering clusters = new Clustering(points, 5);
        for (int i : clusters.reduceDimensions(transactions)) System.out.println(i);
        System.out.println("Cluster of 6th point: " + clusters.clusterOf(6));
    }
}
