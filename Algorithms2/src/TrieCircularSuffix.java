import java.util.Arrays;

public class TrieCircularSuffix {
    private static final int R = 256;

    private Node root;
    
    private int index = 0;

    private final int[] orders;

    private static class Node {
        private Node[] next = new Node[R];
        private int order;
        public boolean isString;
    }

    public TrieCircularSuffix(String s) {
        buildTrie(s);
        orders = new int[s.length()];
        dfs();
    }

    public int[] getOrderArray() {
        return orders;
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
        
        for (int i = 0; i < R; i++) {
            if (node.next[i] != null) {
                dfs(node.next[i], s + (char)i);
            }
        }
    }

    private void add(String key, int order) {
        root = add(root, key, 0, order);
    }

    private Node add(Node x, String key, int d, int order) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            x.isString = true;
            x.order = order;
        }
        else {
            char c = key.charAt(d);
            x.next[c] = add(x.next[c], key, d+1, order);
        }
        return x;
    }
    
    public static void main(String[] args) {
        String s = "ABRACADABRA!";
        
        TrieCircularSuffix t = new TrieCircularSuffix(s);
        int[] orderArray = t.getOrderArray();
        System.out.println(Arrays.toString(orderArray));
    }
}
