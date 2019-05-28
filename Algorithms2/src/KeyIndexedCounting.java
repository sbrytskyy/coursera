import java.util.Arrays;

public class KeyIndexedCounting {

    public static char[] sort(String s) {

        char[] a = s.toCharArray();

        int n = a.length;
        int R = 256; // extend ASCII alphabet size
        char[] aux = new char[n];

        // compute frequency counts
        int[] count = new int[R + 1];
        for (int i = 0; i < n; i++)
            count[a[i] + 1]++;
        System.out.println("Count 1: " + Arrays.toString(count));

        // compute cumulates
        for (int r = 0; r < R; r++)
            count[r + 1] += count[r];
        System.out.println("Count 2: " + Arrays.toString(count));

        // move data
        for (int i = 0; i < n; i++)
            aux[count[a[i]]++] = a[i];
        System.out.println("Aux: " + Arrays.toString(aux));

        // copy back
        for (int i = 0; i < n; i++)
            a[i] = aux[i];
        
        return a;
    }

    public static void main(String[] args) {
        
        char[] sort = sort("Hello, World!");
        System.out.println(Arrays.toString(sort));
    }

}
