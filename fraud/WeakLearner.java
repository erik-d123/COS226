import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

// Corner case where line is below all points

public class WeakLearner {
    private int dimension; // Dimension learner uses
    private int value; // Value predictor
    private int sign; // Sign predictor
    private int k; // Length of array

    // Node class to store values and index
    private class Node {
        int value; // Value at input[x][y]
        int index; // Index of value (for sorted order to original input[][])

        // Initialize values
        Node(int value, int index) {
            this.value = value;
            this.index = index;
        }
    }

    private class NodeComparator implements Comparator<Node> {
        public int compare(Node n1, Node n2) {
            return Integer.compare(n1.value, n2.value);
        }
    }

    // give order of indices in order of growth 5, 6, 2, 3, 1 input(5 < input(6)
    // train the weak learner
    public WeakLearner(int[][] input, double[] weights, int[] labels) {
        validate(input, weights, labels);
        int n = input.length;
        this.k = input[0].length;
        double best = Double.POSITIVE_INFINITY;
        for (int s = 0; s <= 1; s++) {
            for (int d = 0; d < k; d++) {

                // Array to hold value and index
                Node[] orderedValues = new Node[n];
                for (int i = 0; i < n; i++)
                    orderedValues[i] = new Node(input[i][d], i);
                Arrays.sort(orderedValues, new NodeComparator());

                // calculate total weight
                // double totalWeight =
                //         calculateWeight(input, labels, weights,
                //                         d, orderedValues[0].value, s);

                double belowLine = 0;
                for (int i = 0; i < labels.length; i++)
                    if (labels[i] == s) belowLine += weights[i];

                if (belowLine < best) {
                    best = belowLine;
                    dimension = d;
                    sign = s;
                    value = orderedValues[0].value - 1;
                }

                double totalWeight = belowLine;
                for (int v = 0; v < n; v++) {

                    int index = orderedValues[v].index;

                    // check if was new line makes it correct or incorrect
                    if (labels[index] == s) totalWeight -= weights[index];
                    else totalWeight += weights[index];

                    // put check if same on same v value (check if no next point)

                    if (v + 1 != n)
                        if (orderedValues[v].value ==
                                orderedValues[v + 1].value) continue;
                    if (totalWeight < best) {
                        best = totalWeight;
                        dimension = d;
                        sign = s;
                        value = orderedValues[v].value;
                    }
                }
            }
        }
    }

    // return the prediction of the learner for a new sample
    public int predict(int[] sample) {
        validate(sample);
        if ((sample[dimension] <= value && sign == 0)
                || (sample[dimension] > value && sign == 1))
            return 0;
        else return 1;
    }

    // return the dimension the learner uses to separate the data
    public int dimensionPredictor() {
        return dimension;
    }

    // return the value the learner uses to separate the data
    public int valuePredictor() {
        return value;
    }

    // return the sign the learner uses to separate the data
    public int signPredictor() {
        return sign;
    }

    // Validate arguments
    private void validate(int[][] input, double[] weights, int[] labels) {
        if (input == null || weights == null || labels == null ||
                input.length != weights.length || input.length != labels.length)
            throw new IllegalArgumentException("Invalid arguments");
        for (int i : labels)
            if (i != 0 && i != 1)
                throw new IllegalArgumentException("Invalid argument");
        for (double i : weights)
            if (i < 0)
                throw new IllegalArgumentException("Invalid argument");
    }

    // Validate arguments
    private void validate(int[] sample) {
        if (sample == null || sample.length != k)
            throw new IllegalArgumentException("Invalid argument");
    }

    // unit testing (required)
    public static void main(String[] args) {
        In datafile = new In(args[0]);

        int n = datafile.readInt();
        int k = datafile.readInt();

        int[][] input = new int[n][k];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < k; j++) {
                input[i][j] = datafile.readInt();
            }
        }

        int[] labels = new int[n];
        for (int i = 0; i < n; i++) {
            labels[i] = datafile.readInt();
        }

        double[] weights = new double[n];
        for (int i = 0; i < n; i++) {
            weights[i] = datafile.readDouble();
        }

        WeakLearner weakLearner = new WeakLearner(input, weights, labels);


        StdOut.printf("vp = %d, dp = %d, sp = %d\n", weakLearner.valuePredictor(),
                      weakLearner.dimensionPredictor(), weakLearner.signPredictor());

        int[] sample = { 0, 5, 3, 2, 1, 7 };
        int prediction = weakLearner.predict(sample);
        System.out.println("Prediction: " + prediction);
    }
}
