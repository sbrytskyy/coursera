import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.Huffman;
import edu.princeton.cs.algs4.In;

public class MoveToFrontTest {

    public static void main(String[] args) {
        testHuffman("resources/burrows/abra.txt");
    }

    private static void testHuffman(String filename) {
        In in = new In(filename);
        String text = in.readAll();

        InputStream stdin = System.in;
        PrintStream stdout = System.out;

        try {
            byte[] encoded = encode(text);
            stdout.print("Encoded string: ");
            for (byte b : encoded) {
                stdout.print(String.format("%02x", (b & 0xff)));
                stdout.print(" ");
            }
            stdout.println();
            
            String decoded = decode(encoded);
            stdout.println("Decoded string: " + decoded);
            
            assert text.equals(decoded);

        } finally {
            System.setIn(stdin);
            System.setOut(stdout);
        }
    }

    private static byte[] encode(String text) {
        System.setIn(new ByteArrayInputStream(text.getBytes()));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        Huffman.compress();

        byte[] encoded = baos.toByteArray();
        BinaryStdIn.close();
        BinaryStdOut.close();

        return encoded;
    }

    private static String decode(byte[] encoded) {
        System.setIn(new ByteArrayInputStream(encoded));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        Huffman.expand();

        BinaryStdIn.close();
        BinaryStdOut.close();

        return baos.toString();
    }
}
