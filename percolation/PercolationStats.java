import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96; // Constant for 95% CI
    private double[] values; // Array of values to store percolation threshhold
    private Stopwatch time; // Stopwatch to measure elapsed time

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("Values must be greater than 0");
        time = new Stopwatch();
        values = new double[trials];
        // open sites till n by n grid percolates, repeating for set trials
        for (int i = 0; i < trials; i++) {
            Percolation grid = new Percolation(n);
            do {
                int x = StdRandom.uniformInt(n);
                int y = StdRandom.uniformInt(n);
                // Opens site if blocked
                if (!grid.isOpen(x, y))
                    grid.open(x, y);
            } while (!grid.percolates());
            double temp = grid.numberOfOpenSites();
            values[i] = temp / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(values);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(values);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - (CONFIDENCE_95 * stddev() / Math.sqrt(values.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + (CONFIDENCE_95 * stddev() / Math.sqrt(values.length));
    }

    // elapsed time for percolation simulation
    private double elapsedTime() {
        return time.elapsedTime();
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats test = new PercolationStats(n, t);
        // Prints results to StdOut
        StdOut.printf("mean() = %.4f\n", test.mean());
        StdOut.printf("stddev() = %.4f\n", test.stddev());
        StdOut.printf("confidenceLow() = %.4f\n", test.confidenceLow());
        StdOut.printf("confidenceHigh() = %.4f\n", test.confidenceHigh());
        StdOut.printf("elapsed time = %.4f\n", test.elapsedTime());
    }
}
