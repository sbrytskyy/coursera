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
        BinaryStdIn.readString();

        // close output stream
        BinaryStdOut.close();
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
