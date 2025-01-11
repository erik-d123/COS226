import java.util.Comparator;

public class BinarySearchDeluxe {
    // Returns the index of the first key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        if (a == null || key == null || comparator == null)
            throw new IllegalArgumentException("Argument can not be null");
        int lo = 0, hi = a.length - 1;
        int hit = -1;
        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            int compare = comparator.compare(key, a[mid]);
            if (compare < 0) hi = mid - 1;
            else if (compare > 0) lo = mid + 1;
            else {
                hi = mid - 1;
                hit = mid;
            }
        }
        return hit;
    }

    // Returns the index of the last key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        if (a == null || key == null || comparator == null)
            throw new IllegalArgumentException("Argument can not be null");
        int lo = 0, hi = a.length - 1;
        int hit = -1;
        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            int compare = comparator.compare(key, a[mid]);
            if (compare < 0) hi = mid - 1;
            else if (compare > 0) lo = mid + 1;
            else {
                lo = mid + 1;
                hit = mid;
            }
        }
        return hit;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Integer[] a = { 0, 0, 1, 1, 1, 2, 2, 2, 3, 4, 5, 6, 7, 8 };
        System.out.println("First: " + firstIndexOf(a, 2, tester()));
        System.out.println("Last: " + lastIndexOf(a, 2, tester()));
    }

    // Comparator for integer
    private static Comparator<Integer> tester() {
        return new Test();
    }

    // Implements integer comparator
    private static class Test implements Comparator<Integer> {
        public int compare(Integer that, Integer other) {
            return Integer.compare(that, other);
        }
    }
}
