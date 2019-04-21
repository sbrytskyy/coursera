import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int capacity = 32;
    private Item[] items = (Item[]) new Object[capacity];
    private int head = 0;
    private int tail = 0;
    private int size = 0;

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
            throw new IllegalArgumentException();
        }

        if (tail == capacity) {
            resize();
        }
        items[tail++] = item;
        size++;
    }
    
    private void resize() {
        capacity *= 2;
        Item[] newItems = (Item[]) new Object[capacity];
        int newIndex = 0;
        for (int i = head; i < tail; i++) {
            newItems[newIndex++] = items[i];
        }
        items = newItems;
        head = 0;
        tail = newIndex;
    }

    public Item dequeue() { // remove and return a random item
        if (size == 0) {
            throw new NoSuchElementException();
        }

        int uniform = StdRandom.uniform(size);
        int index = head + uniform;
        
        Item item = items[index];
        items[index] = items[head++];
        
        size--;
        return item;
    }

    public Item sample() { // return (but do not remove) a random item
        if (size == 0) {
            throw new NoSuchElementException();
        }

        int uniform = StdRandom.uniform(size);
        int index = head + uniform;
        
        Item item = items[index];
        return item;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private int current = -1;
        private int[] indices = StdRandom.permutation(size);
        
        public RandomizedQueueIterator() {
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
                return items[indices[current] + head];
            }
            if (current == size - 1) {
                throw new NoSuchElementException();
            }

            current++;
            return items[indices[current] + head];
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

        for (int i = 0; i < 8; i++) {
            s = dq.dequeue();
            System.out.println(i + ":" + s);
        }
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
