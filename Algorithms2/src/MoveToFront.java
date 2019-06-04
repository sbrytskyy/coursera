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

        char[] alphabet = new char[R];
        for (int i = 0; i < R; i++) {
            alphabet[i] = (char) i;
        }

        for (char c : input) {
            byte pos = 0;
            for (int i = 0; i < R; i++) {
                char next = alphabet[i];
                if (next == c) {
                    for (int j = i; j >= 1; j--) {
                        alphabet[j] = alphabet[j - 1];
                    }
                    alphabet[0] = c;
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

        char[] alphabet = new char[R];
        for (int i = 0; i < R; i++) {
            alphabet[i] = (char) i;
        }

        for (char c : input) {
            int pos = c;
            char ch = alphabet[pos];
            for (int j = pos; j >= 1; j--) {
                alphabet[j] = alphabet[j - 1];
            }
            alphabet[0] = ch;
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
