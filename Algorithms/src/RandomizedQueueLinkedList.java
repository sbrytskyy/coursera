import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueueLinkedList<Item> implements Iterable<Item> {

    private Node first = new Node();
    private Node last = new Node();

    private int size = 0;

    private class Node {
        private Item item;
        private Node prev;
        private Node next;
    }

    public RandomizedQueueLinkedList() { // construct an empty randomized queue
        first.next = last;
        last.prev = first;
    }

    public boolean isEmpty() { // is the queue empty?
        return size == 0;
    }

    public int size() { // return the number of items on the queue
        return size;
    }

    public void enqueue(Item item) { // add the item
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node node = new Node();
        node.item = item;

        node.prev = last.prev;
        node.next = last;
        
        last.prev.next = node;
        last.prev = node;

        size++;
    }

    public Item dequeue() { // remove and return a random item
        if (size == 0) {
            throw new NoSuchElementException();
        }

        Node node = first.next;
        
        first.next = node.next;
        node.next.prev = first;
        
        size--;
        return node.item;
    }

    public Item sample() { // return (but do not remove) a random item
        if (size == 0) {
            throw new NoSuchElementException();
        }

        int uniform = StdRandom.uniform(size);

        Node node = first;
        while (uniform > 0) {
            uniform--;
            node = node.next;
        }

        return node.item;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private int current = -1;
        private Item[] items;

        public RandomizedQueueIterator() {
            if (size > 0) {
                int[] x = StdRandom.permutation(size);
                items = (Item[]) new Object[size];

                Node node = first.next;
                int i = 0;
                while (node != last) {
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
        RandomizedQueueLinkedList<String> dq = new RandomizedQueueLinkedList<>();

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
        
        for (int i = 0; i < 8; i++) {
            s = dq.dequeue();
            System.out.println(i + ":" + s);
        }
    }

    private static void printDeque(RandomizedQueueLinkedList<String> dq) {
        System.out.println("------------");
        Iterator<String> it = dq.iterator();
        while (it.hasNext()) {
            String s = it.next();
            System.out.println(s);
        }
        System.out.println("------------");
    }
}
