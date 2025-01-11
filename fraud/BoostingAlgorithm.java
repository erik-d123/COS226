import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.LinkedList;

public class BoostingAlgorithm {
    private int[][] inputs; // Reduced transaction summaries
    private int[] labels; // Correct labeled transaction summaries
    private double[] weights; // Weights of transaction summaries
    private LinkedList<WeakLearner> learners; // Collection to store weak learners
    private Clustering cluster; // Clustering to reduce dimensions

    // create the clusters and initialize  data structures
    public BoostingAlgorithm(int[][] input, int[] labels, Point2D[] locations, int k) {
        validate(input, labels, locations, k);
        cluster = new Clustering(locations, k);
        this.inputs = new int[input.length][k];
        for (int col = 0; col < input.length; col++) {
            int[] sample = new int[input[col].length];
            for (int row = 0; row < input[col].length; row++)
                sample[row] = input[col][row];
            int[] reducedTransactions = cluster.reduceDimensions(sample);
            for (int i = 0; i < reducedTransactions.length; i++)
                inputs[col][i] = reducedTransactions[i];
        }
        learners = new LinkedList<>();
        this.labels = labels;
        weights = new double[input.length];
        for (int i = 0; i < weights.length; i++)
            weights[i] = (1.0 / weights.length);
    }

    // return the current weight of the ith point
    public double weightOf(int i) {
        return weights[i];
    }

    // apply one step of the boosting algorithm
    public void iterate() {
        WeakLearner learner = new WeakLearner(inputs, weights, labels);
        learners.add(learner);
        for (int i = 0; i < inputs.length; i++) {
            if (learner.predict(inputs[i]) != labels[i]) weights[i] = weights[i] * 2;
        }
        double sum = 0;
        for (double i : weights) sum += i;
        for (int i = 0; i < weights.length; i++) weights[i] = weights[i] / sum;
    }

    // return the prediction of the learner for a new sample
    public int predict(int[] sample) {
        validate(sample);
        int[] input = cluster.reduceDimensions(sample);
        int clean = 0;
        int fraud = 0;
        for (WeakLearner i : learners) {
            int prediction = i.predict(input);
            if (prediction == 0) clean++;
            else fraud++;
        }
        if (clean >= fraud) return 0;
        else return 1;
    }

    // Validate arguments
    private void validate(int[] sample) {
        if (sample == null) throw new IllegalArgumentException("Invalid argument");
    }

    // Validate arguments
    private void validate(int[][] input, int[] labels, Point2D[] locations, int k) {
        if (input == null || locations == null || labels == null ||
                input.length != labels.length)
            throw new IllegalArgumentException("Invalid arguments");
        for (int i : labels)
            if (i != 0 && i != 1)
                throw new IllegalArgumentException("Invalid argument");
        if (k < 1 || k > input[0].length)
            throw new IllegalArgumentException("Invalid argument");
    }

    // unit testing (required)
    public static void main(String[] args) {
        Stopwatch a = new Stopwatch();
        // read in the terms from a file
        DataSet training = new DataSet(args[0]);
        DataSet testing = new DataSet(args[1]);
        int k = Integer.parseInt(args[2]);
        int T = Integer.parseInt(args[3]);

        int[][] trainingInput = training.getInput();
        int[][] testingInput = testing.getInput();
        int[] trainingLabels = training.getLabels();
        int[] testingLabels = testing.getLabels();
        Point2D[] trainingLocations = training.getLocations();

        // train the model
        BoostingAlgorithm model = new BoostingAlgorithm(trainingInput, trainingLabels,
                                                        trainingLocations, k);
        for (int t = 0; t < T; t++)
            model.iterate();

        // calculate the training data set accuracy
        double training_accuracy = 0;
        for (int i = 0; i < training.getN(); i++)
            if (model.predict(trainingInput[i]) == trainingLabels[i])
                training_accuracy += 1;
        training_accuracy /= training.getN();

        // calculate the test data set accuracy
        double test_accuracy = 0;
        for (int i = 0; i < testing.getN(); i++)
            if (model.predict(testingInput[i]) == testingLabels[i])
                test_accuracy += 1;
        test_accuracy /= testing.getN();
        StdOut.println("Training accuracy of model: " + training_accuracy);
        StdOut.println("Test accuracy of model: " + test_accuracy);
        System.out.println(model.weightOf(5));
        System.out.println("Time: " + a.elapsedTime());

    }
}
