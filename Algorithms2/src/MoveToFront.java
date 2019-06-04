import java.util.Iterator;
import java.util.LinkedList;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {

    // alphabet size of extended ASCII
    private static final int R = 256;

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        // read the input
        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();

        LinkedList<Character> alphabet = new LinkedList<>();
        for (char c = 0; c < R; c++) {
            alphabet.add(c);
        }

        for (char c : input) {
            byte pos = 0;
            Iterator<Character> iterator = alphabet.iterator();
            while (iterator.hasNext()) {
                char next = iterator.next();
                if (next == c) {
                    iterator.remove();
                    alphabet.addFirst(c);
                    BinaryStdOut.write(pos);
                    break;
                }
                pos++;
            }
        }

        // close output stream
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        // read the input
        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();

        LinkedList<Character> alphabet = new LinkedList<>();
        for (char c = 0; c < R; c++) {
            alphabet.add(c);
        }

        for (char c : input) {
            int pos = c;
            char ch = alphabet.remove(pos);
            alphabet.addFirst(ch);
            BinaryStdOut.write(ch);
        }

        // close output stream
        BinaryStdOut.close();
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        if (args.length < 1) {
            return;
        }

        if ("-".equals(args[0])) {
            encode();
        }

        if ("+".equals(args[0])) {
            decode();
        }
    }
}
