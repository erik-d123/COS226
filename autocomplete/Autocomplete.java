import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class Autocomplete {
    private Term[] term; //

    // Initializes the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        if (terms == null) throw new IllegalArgumentException("Array is null");
        for (int i = 0; i < terms.length; i++) {
            if (terms[i] == null)
                throw new IllegalArgumentException("Array entry is null");
        }
        term = new Term[terms.length];
        for (int i = 0; i < terms.length; i++)
            term[i] = terms[i];
        Arrays.sort(term);
    }

    // Returns all terms that start with the given prefix,
    // in descending order of weight.
    public Term[] allMatches(String prefix) {
        if (prefix == null)
            throw new IllegalArgumentException("Argument is null");
        Comparator<Term> comparator = Term.byPrefixOrder(prefix.length());
        Term test123 = new Term(prefix, 0);
        int start = BinarySearchDeluxe.firstIndexOf(term, test123, comparator);
        int end = BinarySearchDeluxe.lastIndexOf(term, test123, comparator);
        if (start == -1) return new Term[0];
        Term[] thing = new Term[end - start + 1];
        for (int i = 0; i < thing.length; i++) {
            thing[i] = term[i + start];
        }
        comparator = Term.byReverseWeightOrder();
        Arrays.sort(thing, comparator);
        return thing;
    }

    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        if (prefix == null)
            throw new IllegalArgumentException("Argument is null");
        Comparator<Term> comparator = Term.byPrefixOrder(prefix.length());
        Term test123 = new Term(prefix, 0);
        int start = BinarySearchDeluxe.firstIndexOf(term, test123, comparator);
        int end = BinarySearchDeluxe.lastIndexOf(term, test123, comparator);
        if (start == -1) return 0;
        return end - start + 1;
    }

    // unit testing (required)
    public static void main(String[] args) {
        // read in the terms from a file
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        Term[] terms = new Term[n];
        for (int i = 0; i < n; i++) {
            long weight = in.readLong();           // read the next weight
            in.readChar();                         // scan past the tab
            String query = in.readLine();          // read the next query
            terms[i] = new Term(query, weight);    // construct the term
        }

        // read in queries from standard input and print the top k matching terms
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            StdOut.printf("%d matches\n", autocomplete.numberOfMatches(prefix));
            for (int i = 0; i < Math.min(k, results.length); i++)
                StdOut.println(results[i]);
        }
    }
}
