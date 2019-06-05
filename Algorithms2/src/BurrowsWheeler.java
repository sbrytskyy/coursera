import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {
    // apply Burrows-Wheeler transform, reading from standard input and writing to standard output
    public static void transform() {
        // read the input
        String s = BinaryStdIn.readString();

        CircularSuffixArray cfa = new CircularSuffixArray(s);
        int length = cfa.length();

        int first = 0;
        for (; first < length; first++) {
            if (cfa.index(first) == 0) {
                break;
            }
        }

        BinaryStdOut.write(first);

        for (int i = 0; i < length; i++) {
            int shift = cfa.index(i);
            int index = (length - 1 + shift) % length;
            BinaryStdOut.write(s.charAt(index));
        }

        // close output stream
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
    public static void inverseTransform() {
        // read the input
        int first = BinaryStdIn.readInt();
        String encoded = BinaryStdIn.readString();
        char[] t = encoded.toCharArray();

        int len = t.length;

        Map<Character, LinkedList<Integer>> map = new LinkedHashMap<>();
        for (int i = 0; i < len; i++) {
            LinkedList<Integer> linkedList = map.getOrDefault(t[i], new LinkedList<>());
            linkedList.add(i);
            map.put(t[i], linkedList);
        }

        countingSort(t);
        int next[] = new int[len];
        for (int i = 0; i < len; i++) {
            next[i] = map.get(t[i]).remove();
        }

        for (int i = 0; i < len; i++) {
            BinaryStdOut.write(t[first]);
            first = next[first];
        }

        // close output stream
        BinaryStdOut.close();
    }

    private static void countingSort(char arr[]) {
        int n = arr.length;

        // The output character array that will have sorted arr 
        char output[] = new char[n];

        // Create a count array to store count of inidividul 
        // characters and initialize count array as 0 
        int count[] = new int[256];
        for (int i = 0; i < 256; ++i)
            count[i] = 0;

        // store count of each character 
        for (int i = 0; i < n; ++i)
            ++count[arr[i]];

        // Change count[i] so that count[i] now contains actual 
        // position of this character in output array 
        for (int i = 1; i <= 255; ++i)
            count[i] += count[i - 1];

        // Build the output character array 
        // To make it stable we are operating in reverse order. 
        for (int i = n - 1; i >= 0; i--) {
            output[count[arr[i]] - 1] = arr[i];
            --count[arr[i]];
        }

        // Copy the output array to arr, so that arr now 
        // contains sorted characters 
        for (int i = 0; i < n; ++i)
            arr[i] = output[i];
    }

    // if args[0] is '-', apply Burrows-Wheeler transform
    // if args[0] is '+', apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args.length < 1) {
            return;
        }

        if ("-".equals(args[0])) {
            transform();
        }

        if ("+".equals(args[0])) {
            inverseTransform();
        }
    }
}
