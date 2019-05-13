import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.StdOut;

public class OutcastTest {

    public static void main(String[] args) { // see test client below
        test1();
    }

    private static void test1() {
        List<String> nouns = new ArrayList<>();
        nouns.add("horse");
        nouns.add("zebra");
        nouns.add("cat");
        nouns.add("bear");
        nouns.add("table");
        String[] array = nouns.toArray(new String[nouns.size()]);

        String synsets = "resources/wordnet/synsets.txt";
        String hypernyms = "resources/wordnet/hypernyms.txt";
        WordNet wordnet = new WordNet(synsets, hypernyms);

        Outcast outcast = new Outcast(wordnet);
        String result = outcast.outcast(array);
        StdOut.println(result);
        assert "table".equals(result);
    }
}