import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int n; // Number of items in deque
    private Node first; // Top of queue
    private Node last; // End of queue

    // helper linked list class
    private class Node {
        private Item item; // Item of linked list
        private Node next; // Next node in linked list
        private Node previous; // Previous node in linked list
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("Null object");
        // Corner case if queue is empty
        if (n == 0) {
            Node temp = new Node();
            temp.item = item;
            first = temp;
            last = temp;
            temp.next = null;
            temp.previous = null;
            n++;
        }
        else {
            Node oldfirst = first;
            Node newfirst = new Node();
            newfirst.item = item;
            newfirst.next = oldfirst;
            newfirst.previous = null;
            oldfirst.previous = newfirst;
            first = newfirst;
            n++;
        }

    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("Null object");
        // Corner case if queue is empty
        if (n == 0) {
            Node temp = new Node();
            temp.item = item;
            first = temp;
            last = temp;
            temp.next = null;
            temp.previous = null;
            n++;
        }
        else {
            Node newlast = new Node();
            newlast.item = item;
            Node oldlast = last;
            oldlast.next = newlast;
            newlast.previous = oldlast;
            newlast.next = null;
            last = newlast;
            n++;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (n == 0) throw new NoSuchElementException("Deque is empty");
        Node oldfirst = first;
        Node newfirst = oldfirst.next;
        first = newfirst;
        // Corner case if queue is 1 item
        if (n == 1) last = newfirst;
        n--;
        if (!isEmpty()) newfirst.previous = null;
        return oldfirst.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (n == 0) throw new NoSuchElementException("Deque is empty");
        Node oldlast = last;
        Node newlast = oldlast.previous;
        last = newlast;
        // Corner case if queue is one item
        if (n == 1) first = newlast;
        n--;
        if (!isEmpty()) newlast.next = null;
        return oldlast.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new LinkedIterator();
    }


    // Creates iterator for items from front to back
    /* @citation Adapted from:
     * https://www.cs.princeton.edu/courses/archive/spring24/cos226/lectures/AdvancedJava.pdf
     *           Accessed 02/11/2024.
     */
    private class LinkedIterator implements Iterator<Item> {
        private Node current = first; // Current node in linked list

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> test = new Deque<Integer>();
        System.out.println(test.isEmpty());
        test.addLast(4);
        test.addLast(5);
        test.addLast(6);
        test.addFirst(3);
        test.addFirst(2);
        test.addFirst(1);
        test.addFirst(0);
        test.removeFirst();
        test.removeLast();
        System.out.println("Size of new deque: " + test.size());
        for (Integer s : test) {
            System.out.println(s);
        }
    }
}
