import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.In;

public class BurrowsWheelerTest {

    public static void main(String[] args) {
        testBurrowsWheeler("resources/burrows/abra.txt");
    }

    private static void testBurrowsWheeler(String filename) {
        In in = new In(filename);
        String text = in.readAll();

        InputStream stdin = System.in;
        PrintStream stdout = System.out;

        stdout.println("Input string: " + text);

        try {
            byte[] encoded = transform(text);
            
            stdout.print("Encoded string: ");
            for (byte b : encoded) {
                stdout.print(String.format("%02x", (b & 0xff)));
                stdout.print(" ");
            }
            stdout.println();

            String decoded = inverseTransform(encoded);
            stdout.println("Decoded string: " + decoded);

            assert text.equals(decoded);

        } finally {
            System.setIn(stdin);
            System.setOut(stdout);
        }
    }

    private static byte[] transform(String text) {
        System.setIn(new ByteArrayInputStream(text.getBytes()));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        BurrowsWheeler.transform();

        byte[] encoded = baos.toByteArray();
        BinaryStdIn.close();
        BinaryStdOut.close();

        return encoded;
    }

    private static String inverseTransform(byte[] encoded) {
        System.setIn(new ByteArrayInputStream(encoded));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        BurrowsWheeler.inverseTransform();

        BinaryStdIn.close();
        BinaryStdOut.close();

        return baos.toString();
    }
}
