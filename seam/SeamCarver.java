import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {
    private Picture picture; // Store picture to make immutable


    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        validate(picture);
        this.picture = new Picture(picture);
        // energy = new double[picture().width()][picture.height()];
    }

    // current picture
    public Picture picture() {
        return new Picture(picture);
    }

    // width of current picture
    public int width() {
        return picture.width();

    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        validate(x, y);

        int xRightPixel = x + 1;
        int xLeftPixel = x - 1;
        int yAbovePixel = y + 1;
        int yBelowPixel = y - 1;

        // Check if pixel is at border
        if (x == 0) xLeftPixel = picture.width() - 1;
        if (x == picture.width() - 1) xRightPixel = 0;
        if (y == 0) yBelowPixel = picture.height() - 1;
        if (y == picture.height() - 1) yAbovePixel = 0;

        Color xRight = picture.get(xRightPixel, y);
        Color xLeft = picture.get(xLeftPixel, y);
        Color yAbove = picture.get(x, yAbovePixel);
        Color yBelow = picture.get(x, yBelowPixel);
        return Math.sqrt(equation(xRight, xLeft) + equation(yAbove, yBelow));
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        Picture oldPicture = picture;
        transpose();
        int[] seam = findVerticalSeam();
        this.picture = oldPicture;
        return seam;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        double[][] distTo = new double[picture.width()][picture.height()];
        int[][] edgeTo = new int[picture.width()][picture.height()];
        // Row-major order traversal of picture
        // column-major order
        for (int y = 0; y < picture.height(); y++) {
            for (int x = 0; x < picture.width(); x++) {
                if (y == 0) {
                    distTo[x][y] = energy(x, y);
                    edgeTo[x][y] = x;
                }
                else {
                    int minimumSeam = Integer.MAX_VALUE;

                    // Edge case at left or right border
                    if (width() == 1) minimumSeam = x;
                    else if (x == 0) {
                        if (distTo[x][y - 1] <= distTo[x + 1][y - 1]) minimumSeam = x;
                        else minimumSeam = x + 1;
                    }
                    else if (x == picture.width() - 1) {
                        if (distTo[x][y - 1] <= distTo[x - 1][y - 1]) minimumSeam = x;
                        else minimumSeam = x - 1;
                    }

                    // Finds minimum seam via checking 3 distanceTo
                    else {
                        if (distTo[x][y - 1] <= distTo[x - 1][y - 1]
                                && distTo[x][y - 1] <= distTo[x
                                + 1][y - 1]) minimumSeam = x;
                        if (distTo[x - 1][y - 1] <= distTo[x][y - 1]
                                && distTo[x - 1][y - 1] <= distTo[x + 1][y - 1])
                            minimumSeam = x - 1;
                        if (distTo[x + 1][y - 1] <= distTo[x][y - 1]
                                && distTo[x + 1][y - 1] <= distTo[x - 1][y - 1])
                            minimumSeam = x + 1;
                    }

                    edgeTo[x][y] = minimumSeam;
                    distTo[x][y] = energy(x, y) + distTo[minimumSeam][y - 1];
                }
            }
        }

        // Computes the minimum seam by traversing the minimum edgeTo graph
        double xEnergy = Double.POSITIVE_INFINITY;
        int x = -1;
        int[] indices = new int[picture.height()];
        for (int i = 0; i < picture.width(); i++) {
            if ((distTo[i][picture.height() - 1] < xEnergy)) {
                x = i;
                xEnergy = distTo[i][picture.height() - 1];
            }
        }
        for (int i = picture.height() - 1; i >= 0; i--)
        {
            indices[i] = x;
            x = edgeTo[x][i];
        }
        return indices;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        transpose();
        removeVerticalSeam(seam);
        transpose();
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        validate(seam);
        Picture newPicture = new Picture(picture.width() - 1, picture.height());
        for (int y = 0; y < newPicture.height(); y++) {
            for (int x = 0; x < newPicture.width(); x++) {
                if (x < seam[y]) newPicture.set(x, y, picture.get(x, y));
                else newPicture.set(x, y, picture.get(x + 1, y))    ;
            }
        }
        this.picture = newPicture;
    }

    // Calculates energy values
    private double equation(Color positiveSkew, Color negativeSkew) {
        return Math.pow(positiveSkew.getRed() - negativeSkew.getRed(), 2) +
                Math.pow(positiveSkew.getBlue() - negativeSkew.getBlue(), 2) +
                Math.pow(positiveSkew.getGreen() - negativeSkew.getGreen(), 2);
    }

    // Transpose the picture (swapping col and row)
    private void transpose() {
        Picture temp = new Picture(picture.height(), picture.width());
        // double[][] tempEnergy = new double[picture.height()][picture.width()];
        for (int x = 0; x < picture.width(); x++) {
            for (int y = 0; y < picture.height(); y++) {
                temp.set(y, x, picture.get(x, y));
                // tempEnergy[y][x] = energy[x][y];
            }
        }
        // this.energy = tempEnergy;
        this.picture = temp;
    }

    // Checks if point is valid
    private void validate(int x, int y) {
        if (x + 1 > picture.width() || x < 0 || y + 1 > picture.height() || y < 0)
            throw new IllegalArgumentException("Index out of bounds");
    }

    // Check for illegal argument
    private void validate(int[] seam) {
        if (seam == null || picture.width() == 1 || seam.length
                != picture.height() || seam[0] < 0)
            throw new IllegalArgumentException("Invalid seam");
        for (int i = 0; i < seam.length; i++) {
            if (i != 0) {1
                int difference = Math.abs(seam[i] - seam[i - 1]);
                if (difference != 0 && difference != 1)
                    throw new IllegalArgumentException("Invalid seam");
            }
            if (seam[i] < 0 || seam[i] >= picture.width())
                throw new IllegalArgumentException("Invalid seam");
        }
    }

    // Check for illegal argument in constructor
    private void validate(Picture constuctor) {
        if (constuctor == null) throw
                new IllegalArgumentException("Invalid argument");
    }

    //  unit testing (required)
    public static void main(String[] args) {
        Picture picture = new Picture(args[0]);
        SeamCarver seamCarver = new SeamCarver(picture);

        System.out.println("Original dimensions: "
                                   + seamCarver.width() + " x " + seamCarver.height());
        System.out.println(seamCarver.energy(0, 0));
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        System.out.println("New dimensions: "
                                   + seamCarver.width() + " x " + seamCarver.height());
        seamCarver.picture().show();
    }

}
