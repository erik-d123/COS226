import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int n; // Size of queue
    private Item[] s; // Resizing array for randomized queue

    // construct an empty randomized queue
    public RandomizedQueue() {
        n = 0;
        s = (Item[]) new Object[10];
    }


    // is the randomized queue empty?
    public boolean isEmpty() {
        return (n == 0);
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // Creates new array of inputted capacity (smaller or bigger) and
    // moves over values from original array
    /* @citation Adapted from:
     * https://www.cs.princeton.edu/courses/archive/spring24/cos226/lectures/13StacksAndQueues.pdf
     *           Accessed 02/11/2024.
     */
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++)
            copy[i] = s[i];
        s = copy;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Item can not be null");
        if (n == s.length) resize(2 * s.length);
        s[n++] = item;

    }

    // remove and return a random item
    // cite and check if double function is okay
    public Item dequeue() {
        if (n == 0) throw new NoSuchElementException("Queue is empty");
        int k = StdRandom.uniformInt(0, n);
        Item item = s[k];
        s[k] = s[n - 1];
        s[n - 1] = null;
        n--;
        if (n > 0 && n == s.length / 4)
            resize(s.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (n == 0) throw new NoSuchElementException("Queue is empty");
        return s[StdRandom.uniformInt(0, n)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomArrayIterator();
    }

    private class RandomArrayIterator implements Iterator<Item> {
        private Item[] temp; // Copy array of s
        private int i = n - 1; // index of next item to return

        // creates copy array of size n and shuffles
        public RandomArrayIterator() {
            temp = (Item[]) new Object[n];
            for (int j = 0; j < n; j++) {
                temp[j] = s[j];
            }
            StdRandom.shuffle(temp);
        }

        public boolean hasNext() {
            return i >= 0;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return temp[i--];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> test = new RandomizedQueue<Integer>();
        System.out.println(test.isEmpty());
        for (int i = 0; i < 10; i++) {
            test.enqueue(i);
        }
        System.out.println("Size of array: " + test.size());
        System.out.println("Random value: " + test.sample());
        test.dequeue();

        for (Integer s : test) {
            System.out.println(s);
        }
    }
}
