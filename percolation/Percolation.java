import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF grid; // Union-find data structure
    private int n; // Dimension of grid
    private int openSites; // Number of open sites
    private boolean[][] state; // Array to track whether site is open or closed

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1) throw new IllegalArgumentException("N must be greater than 0");
        this.n = n;
        openSites = 0;
        state = new boolean[n][n];
        grid = new WeightedQuickUnionUF(n * n + 2);

    }

    // Tests if coordinate is outside range
    private void range(int row, int col) {
        if (row >= n || row < 0 || col >= n || col < 0)
            throw new IllegalArgumentException("Outside prescribed range");
    }

    // Converts 2D coordinate to 1D union-find index
    private int convert(int row, int col) {
        return row * n + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        range(row, col);
        if (!state[row][col]) {
            state[row][col] = true;
            openSites++;
            // detects if up, down, left, or right neighbors are open
            // unions if so
            if (row + 1 < n && state[row + 1][col])
                grid.union(convert(row, col), convert(row + 1, col));
            if (row > 0 && state[row - 1][col])
                grid.union(convert(row, col), convert(row - 1, col));
            if (col + 1 < n && state[row][col + 1])
                grid.union(convert(row, col), convert(row, col + 1));
            if (col > 0 && state[row][col - 1])
                grid.union(convert(row, col), convert(row, col - 1));
            if (row == 0)
                grid.union(n * n, convert(row, col));
            if (row == n - 1) {
                grid.union(n * n + 1, convert(row, col));
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        range(col, row);
        return (state[row][col]);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        range(col, row);
        return (grid.find(convert(row, col)) == grid.find(n * n));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate
    // change for extra credit
    // can only use extra array for memory
    public boolean percolates() {
        return grid.find(n * n) == grid.find(n * n + 1);
    }

    // unit testing (required)
    public static void main(String[] args) {
        Percolation temp = new Percolation(3);
        StdOut.printf("Number of open sites = %.4b\n", temp.numberOfOpenSites());
        temp.open(2, 1);
        temp.open(1, 1);
        StdOut.printf("Is the site (1,1) full? = %.4b\n", temp.isFull(1, 1));
        StdOut.printf("Is the site (0,1) open? = %.4b\n", temp.isOpen(0, 1));
        temp.open(0, 1);
        StdOut.printf("Does the system percolate = %.4b\n", temp.percolates());
    }
}
