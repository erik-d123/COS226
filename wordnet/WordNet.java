import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

import java.util.HashMap;

public class WordNet {
    // Hash map with string as key
    private HashMap<String, Queue<Integer>> st1 = new HashMap<String, Queue<Integer>>();
    // Hash map with int as key
    private HashMap<Integer, String> st2 = new HashMap<Integer, String>();
    private ShortestCommonAncestor sca; // SCA to find shortest path

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        // Make 2 symbols for vice-versa order
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException("Invalid argument");
        In synset = new In(synsets);
        while (!synset.isEmpty()) {
            String line = synset.readLine();
            String[] tokens = line.split(",");
            int index = Integer.parseInt(tokens[0]);
            st2.put(index, tokens[1]);
            String[] nouns = tokens[1].split(" ");
            for (int i = 0; i < nouns.length; i++) {
                if (st1.get(nouns[i]) == null) {
                    Queue<Integer> queue = new Queue<Integer>();
                    queue.enqueue(index);
                    st1.put(nouns[i], queue);
                }
                else {
                    Queue<Integer> queue = st1.get(nouns[i]);
                    queue.enqueue(index);
                    st1.put(nouns[i], queue);
                }
            }
        }
        In hypernym = new In(hypernyms);
        Digraph g = new Digraph(st2.size());
        while (!hypernym.isEmpty()) {
            String line = hypernym.readLine();
            String[] tokens = line.split(",");
            int id = Integer.parseInt(tokens[0]);
            for (int i = 1; i < tokens.length; i++)
                g.addEdge(id, Integer.parseInt(tokens[i]));
        }
        sca = new ShortestCommonAncestor(g);
    }

    // the set of all WordNet nouns
    public Iterable<String> nouns() {
        return st1.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException("Invalid argument");
        return (st1.get(word) != null);
    }

    // a synset (second field of synsets.txt) that is a shortest common ancestor
    // of noun1 and noun2 (defined below)
    public String sca(String noun1, String noun2) {
        if (noun1 == null || noun2 == null)
            throw new IllegalArgumentException("Invalid argument");
        if (!isNoun(noun1) || !isNoun(noun2))
            throw new IllegalArgumentException("Invalid argument");
        Queue<Integer> index1 = st1.get(noun1);

        Queue<Integer> index2 = st1.get(noun2);
        return st2.get(sca.ancestorSubset(index1, index2));
    }

    // distance between noun1 and noun2 (defined below)
    public int distance(String noun1, String noun2) {
        if (noun1 == null || noun2 == null)
            throw new IllegalArgumentException("Invalid argument");
        if (!isNoun(noun1) || !isNoun(noun2))
            throw new IllegalArgumentException("Invalid argument");
        Queue<Integer> index1 = st1.get(noun1);
        Queue<Integer> index2 = st1.get(noun2);
        return sca.lengthSubset(index1, index2);
    }

    // unit testing (required)
    public static void main(String[] args) {
        WordNet wordNet = new WordNet(args[0], args[1]);
        String a = "Gaetano_Donizetti";
        String b = "criollo";
        System.out.println("Is the word " + b + " a noun: "
                                   + wordNet.isNoun(b));
        System.out.print("First 10 nouns: ");
        int counter = 0;
        for (String i : wordNet.nouns()) {
            System.out.print(i + " ");
            counter++;
            if (counter == 9) break;
        }
        System.out.println();
        System.out.println("sca: " + wordNet.sca(a, b));
        System.out.println("Distance: " + wordNet.distance(a, b));
    }
}
