import edu.princeton.cs.algs4.StdOut;

public class ManacherTest {
    public static void main(String[] args) {
        String s = "abad";
        Manacher manacher = new Manacher(s);
        StdOut.println(manacher.longestPalindromicSubstring());
    }
}
