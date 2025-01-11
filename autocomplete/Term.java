import java.util.Arrays;
import java.util.Comparator;

public class Term implements Comparable<Term> {
    private String query; // Query of search result
    private long weight; // Weight to represent search frequency

    // Initializes a term with the given query string and weight.
    public Term(String query, long weight) {
        if (weight < 0 || query == null)
            throw new IllegalArgumentException("Invalid argument");
        this.query = query;
        this.weight = weight;
    }

    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder() {
        return new ReverseOrder();
    }

    // Provides a comparator in descending order
    private static class ReverseOrder implements Comparator<Term> {
        public int compare(Term that, Term other) {
            return Long.compare(other.weight, that.weight);
        }
    }

    // Compares the two terms in lexicographic order,
    // but using only the first r characters of each query.
    public static Comparator<Term> byPrefixOrder(int r) {
        if (r < 0) throw new IllegalArgumentException("R must be positive");
        return new PrefixOrder(r);
    }

    // Implements comparator in Prefix Order
    private static class PrefixOrder implements Comparator<Term> {
        int r; // Prefix length

        // Constructor to initialize r
        public PrefixOrder(int r) {
            this.r = r;
        }

        public int compare(Term that, Term other) {
            int temp = r;
            String thatquery = that.query;
            String otherquery = other.query;
            // Tests for edge cases
            if (thatquery.length() > r) thatquery = thatquery.substring(0, r);
            if (otherquery.length() > r) otherquery = otherquery.substring(0, r);
            if (thatquery.length() < r || otherquery.length() < r)
                temp = Math.min(thatquery.length(), otherquery.length());
            if (thatquery.equals(otherquery)) return 0;
            for (int i = 0; i < temp; i++) {
                if (thatquery.charAt(i) != otherquery.charAt(i))
                    return thatquery.charAt(i) - otherquery.charAt(i);
            }
            return thatquery.length() - otherquery.length();
        }
    }

    // Compares the two terms in lexicographic order by query.
    public int compareTo(Term that) {
        if (that.query.equals(this.query)) return 0;
        for (int i = 0;
             i < Math.min(that.query.length(), this.query.length()); i++) {
            if (that.query.charAt(i) != this.query.charAt(i))
                return this.query.charAt(i) - that.query.charAt(i);
        }
        return this.query.length() - that.query.length();
    }

    // Returns a string representation of this term in the following format:
    // the weight, followed by a tab, followed by the query.
    public String toString() {
        return weight + "\t" + query;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Term a = new Term("ABCD", 8);
        Term b = new Term("ABCE", 5);
        Term d = new Term("ABCF", 5);
        Term e = new Term("ABCG", 5);
        Term[] c = { a, b, e, d };
        Comparator<Term> comparator = Term.byPrefixOrder(4);
        Arrays.sort(c, comparator);
        System.out.println(c[0]);
        comparator = Term.byReverseWeightOrder();
    }
}
