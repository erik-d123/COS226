import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private WordNet wordnet; // Wordnet to reflect graph of words

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int[] length = new int[nouns.length];
        int maximumDistance = -1;
        int champion = 0;
        for (int i = 0; i < nouns.length; i++) {
            for (String j : nouns)
                length[i] = length[i] + wordnet.distance(nouns[i], j);
            if (length[i] > maximumDistance) {
                maximumDistance = length[i];
                champion = i;
            }
        }
        return nouns[champion];
    }

    // test client (see below)
    public static void main(String[] args) {
        final WordNet wordnet = new WordNet(args[0], args[1]);
        final Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
