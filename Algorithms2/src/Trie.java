public class Trie {
    private static final int R = 26;

    private Node root;

    private static class Node {
        private Node[] next = new Node[R];
        private boolean isString;
    }

    public Trie() {
    }

    public boolean contains(String key) {
        Node x = get(root, key, 0);
        if (x == null) return false;
        return x.isString;
    }

    public boolean containsPrefix(String key) {
        Node x = get(root, key, 0);
        if (x == null) return false;
        return true;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        return get(x.next[c - 'A'], key, d+1);
    }

    public void add(String key) {
        root = add(root, key, 0);
    }

    private Node add(Node x, String key, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            x.isString = true;
        }
        else {
            char c = key.charAt(d);
            x.next[c - 'A'] = add(x.next[c - 'A'], key, d+1);
        }
        return x;
    }

    public static void main(String[] args) {
        Trie t = new Trie();
        t.add("HELLO");
        System.out.println(t.containsPrefix("H"));
        System.out.println(t.containsPrefix("HE"));
        System.out.println(t.containsPrefix("HEL"));
        System.out.println(t.containsPrefix("HELL"));
        System.out.println(t.containsPrefix("HELLO"));
        System.out.println(t.containsPrefix("HELO"));
    }
}
