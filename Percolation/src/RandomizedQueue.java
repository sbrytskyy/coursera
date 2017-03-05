import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Node first = null;
    private Node last = null;

    private int size = 0;

    private class Node {
        private Item item;
        private Node prev;
        private Node next;
    }

    public RandomizedQueue() { // construct an empty randomized queue
    }

    public boolean isEmpty() { // is the queue empty?
        return size == 0;
    }

    public int size() { // return the number of items on the queue
        return size;
    }

    public void enqueue(Item item) { // add the item
        if (item == null) {
            throw new NullPointerException();
        }

        Node node = new Node();
        node.item = item;

        if (size == 0) {
            first = node;
            last = node;
        } else {
            node.next = first;
            first.prev = node;
            first = node;
        }
        size++;
    }

    public Item dequeue() { // remove and return a random item
        if (size == 0) {
            throw new NoSuchElementException();
        }

        Item item = last.item;
        if (size == 1) {
            first.next = null;
            last = first;
        } else {
            Node newLast = last.prev;
            newLast.next = null;
            last = newLast;
        }
        size--;
        return item;
    }

    public Item sample() { // return (but do not remove) a random item
        return iterator().next();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private int current = -1;
        private Item[] items;

        public RandomizedQueueIterator() {
            if (size > 0) {
                int[] x = StdRandom.permutation(size);
                items = (Item[]) new Object[size];

                Node node = first;
                int i = 0;
                while (node != null) {
                    items[x[i]] = node.item;
                    i++;
                    node = node.next;
                }
            }
        }

        @Override
        public boolean hasNext() {
            if (size == 0) {
                return false;
            }
            if (current == -1) {
                return true;
            } else {
                return current < size - 1;
            }
        }

        @Override
        public Item next() {
            if (size == 0) {
                throw new NoSuchElementException();
            }
            if (current == -1) {
                current = 0;
                return items[current];
            }
            if (current == size - 1) {
                throw new NoSuchElementException();
            }

            current++;
            return items[current];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public Iterator<Item> iterator() {
        Iterator<Item> it = new RandomizedQueueIterator();
        return it; // return an independent iterator over
                   // items in random order

    }

    public static void main(String[] args) { // unit testing (optional)
        RandomizedQueue<String> dq = new RandomizedQueue<>();

        dq.enqueue("1");
        dq.enqueue("2");
        dq.enqueue("3");
        dq.enqueue("4");
        dq.enqueue("5");
        dq.enqueue("6");
        dq.enqueue("7");
        dq.enqueue("8");

        printDeque(dq);
        
        String s = dq.sample();
        System.out.println(s);
        s = dq.sample();
        System.out.println(s);
        s = dq.sample();
        System.out.println(s);
        s = dq.sample();
        System.out.println(s);
        s = dq.sample();
        System.out.println(s);
    }

    private static void printDeque(RandomizedQueue<String> dq) {
        System.out.println("------------");
        Iterator<String> it = dq.iterator();
        while (it.hasNext()) {
            String s = it.next();
            System.out.println(s);
        }
        System.out.println("------------");
    }
}
