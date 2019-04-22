import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private final Node first = new Node();
    private final Node last = new Node();


    private int size = 0;

    private class Node {
        private Item item;
        private Node prev;
        private Node next;
    }

    public Deque() { // construct an empty deque
        first.next = last;
        last.prev = first;
    }

    public boolean isEmpty() { // is the deque empty?
        return size == 0;
    }

    public int size() { // return the number of items on the deque
        return size;
    }

    public void addFirst(Item item) { // add the item to the front
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node node = new Node();
        node.item = item;

        node.prev = first;
        node.next = first.next;
        
        first.next.prev = node;
        first.next = node;

        size++;
    }

    public void addLast(Item item) { // add the item to the end
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

    public Item removeFirst() { // remove and return the item from the front
        if (size == 0) {
            throw new NoSuchElementException();
        }

        Node node = first.next;
        
        first.next = node.next;
        node.next.prev = first;
        
        Item item = node.item;
        
        size--;
        return item;
    }

    public Item removeLast() { // remove and return the item from the end
        if (size == 0) {
            throw new NoSuchElementException();
        }

        Node node = last.prev;
        
        last.prev = node.prev;
        last.prev.next = last;
        
        Item item = node.item;
        
        size--;
        return item;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current;

        @Override
        public boolean hasNext() {
            if (size == 0) {
                return false;
            }
            if (current == null) {
                return true;
            } else {
                return current.next != last;
            }
        }

        @Override
        public Item next() {
            if (size == 0) {
                throw new NoSuchElementException();
            }
            if (current == null) {
                current = first.next;
                return current.item;
            }
            if (current.next == last) {
                throw new NoSuchElementException();
            }

            current = current.next;
            Item item = current.item;

            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public Iterator<Item> iterator() {
        Iterator<Item> it = new DequeIterator();
        return it; // return an iterator over items in order
                   // from front to end

    }

    public static void main(String[] args) { // unit testing (optional)
        Deque<String> dq = new Deque<>();

        dq.addFirst("4");
        dq.addFirst("3");
        dq.addLast("5");
        dq.addFirst("2");
        dq.addLast("6");
        dq.addLast("7");
        dq.addLast("8");
        dq.addFirst("1");

        printDeque(dq);

        String i = dq.removeFirst();
        System.out.println("first: " + i);
        printDeque(dq);

        i = dq.removeLast();
        System.out.println("last: " + i);

        printDeque(dq);

        i = dq.removeFirst();
        System.out.println("first: " + i);
        i = dq.removeFirst();
        System.out.println("first: " + i);
        i = dq.removeLast();
        System.out.println("last: " + i);

        printDeque(dq);

        i = dq.removeFirst();
        System.out.println("first: " + i);
        i = dq.removeFirst();
        System.out.println("first: " + i);
        i = dq.removeLast();
        System.out.println("last: " + i);

        printDeque(dq);
    }

    private static void printDeque(Deque<String> dq) {
        System.out.println("------------");
        Iterator<String> it = dq.iterator();
        while (it.hasNext()) {
            String s = it.next();
            System.out.println(s);
        }
        System.out.println("------------");
    }
}