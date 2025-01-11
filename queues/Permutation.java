import edu.princeton.cs.algs4.StdIn;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue temp = new RandomizedQueue();
        while (!StdIn.isEmpty()) {
            temp.enqueue(StdIn.readString());
        }
        Iterator run = temp.iterator();
        for (int i = 0; i < k; i++) System.out.println(run.next());
    }
}
