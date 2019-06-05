import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class TrieCircularSuffix {
    private static final int R = 256;

    private Node root;

    private int index = 0;

    private final int[] orders;

    private static class Node {
        private Map<Character, Node> next = new LinkedHashMap<>();
        private int order;
        public boolean isString;
    }

    public TrieCircularSuffix(String s) {
        buildTrie(s);
        orders = new int[s.length()];
        dfs();
    }

    public int[] getOrderArray() {
        return Arrays.copyOf(orders, orders.length);
    }

    private void buildTrie(String s) {
        int order = 0;

        add(s, order++);

        char[] ca = s.toCharArray();
        int len = ca.length;

        char[] copy = new char[len];

        int start = 1;
        while (start < len) {

            System.arraycopy(ca, start, copy, 0, len - start);
            System.arraycopy(ca, 0, copy, len - start, start);

            String aux = new String(copy);
            add(aux, order++);

            start++;
        }
    }

    private void dfs() {
        index = 0;
        dfs(root, "");
    }

    private void dfs(Node node, String s) {
        if (node.isString) {
            orders[index] = node.order;
            index++;
            return;
        }

        for (char c = 0; c < R; c++) {
            if (node.next.get(c) != null) {
                dfs(node.next.get(c), s + c);
            }
        }
    }

    private void add(String key, int order) {
        root = add(root, key, 0, order);
    }

    private Node add(Node x, String key, int d, int order) {
        if (x == null)
            x = new Node();
        if (d == key.length()) {
            x.isString = true;
            x.order = order;
        } else {
            char c = key.charAt(d);
            // x.next[c] = add(x.next[c], key, d + 1, order);
            x.next.put(c, add(x.next.get(c), key, d + 1, order));
        }
        return x;
    }

    public static void main(String[] args) {
        test1("ABRACADABRA!");
        test1("ABBABABBBB");
    }

    private static void test1(String s) {
        TrieCircularSuffix t = new TrieCircularSuffix(s);
        int[] orderArray = t.getOrderArray();
        System.out.println(Arrays.toString(orderArray));
    }
}
