import java.util.LinkedHashMap;
import java.util.Map;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Topological;

public class WordNet {

    private final Map<String, Integer> synsetsMap = new LinkedHashMap<>();
    private final Map<Integer, String> synsetsReverseMap = new LinkedHashMap<>();
    private final Digraph digraph;
    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException();
        }

        int size = readSynsets(synsets);
        digraph = new Digraph(size);
        readHypernyms(hypernyms);
        
        Topological top = new Topological(digraph);
        if (!top.hasOrder()) {
            throw new IllegalArgumentException();
        }
        sap = new SAP(digraph);
    }

    private int readSynsets(String synsets) {
        In in = new In(synsets);
        String line = null;
        int counter = 0;
        while (!in.isEmpty()) {
            line = in.readLine();
            counter++;
            String[] split = line.split(",");
            int synsetId = Integer.parseInt(split[0]);
            synsetsMap.put(split[1], synsetId);
            synsetsReverseMap.put(synsetId, split[1]);
        }
        return counter;
    }

   private void readHypernyms(String hypernyms) {
        In in = new In(hypernyms);
        String line = null;
        while (!in.isEmpty()) {
            line = in.readLine();
            String[] split = line.split(",");
            int synsetId = Integer.parseInt(split[0]);
            for (int i = 1; i < split.length; i++) {
                int hypernymId = Integer.parseInt(split[i]);
                digraph.addEdge(synsetId, hypernymId);
            }
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return synsetsMap.keySet();

    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
        return synsetsMap.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        int v = synsetsMap.get(nounA);
        int w = synsetsMap.get(nounB);

        return sap.length(v, w);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        int v = synsetsMap.get(nounA);
        int w = synsetsMap.get(nounB);

        int ancestor = sap.ancestor(v, w);
        return synsetsReverseMap.get(ancestor);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wn = new WordNet("resources/wordnet/synsets.txt", "resources/wordnet/hypernyms.txt");
        assert wn.digraph.E() == 84505;
        assert wn.digraph.V() == 82192;
    }
}
