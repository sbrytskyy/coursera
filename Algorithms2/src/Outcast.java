import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

    private final WordNet wordnet;

    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet; // constructor takes a WordNet object
    }

    public String outcast(String[] nouns) { // given an array of WordNet nouns, return an outcast
        int distance = Integer.MIN_VALUE;
        String result = null;
        for (int i = 0; i < nouns.length; i++) {
            String noun = nouns[i];
            int dist = 0;
            for (int j = 0; j < nouns.length; j++) {
                if (i == j) {
                    continue;
                }
                dist += wordnet.distance(noun, nouns[j]);
            }
            if (dist > distance) {
                distance = dist;
                result = noun;
            }
        }
        return result;
    }

    public static void main(String[] args) { // see test client below
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}