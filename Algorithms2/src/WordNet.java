import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Topological;

public class WordNet {

    private final Map<Integer, Set<String>> synsetsMap = new LinkedHashMap<>();
    private final Map<String, Set<Integer>> nounsMap = new LinkedHashMap<>();
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
            Set<String> nounsSet = synsetsMap.getOrDefault(synsetId, new LinkedHashSet<>());
            nounsSet.add(split[1]);
            synsetsMap.put(synsetId, nounsSet);

            String[] nouns = split[1].split(" ");
            for (String noun : nouns) {
                Set<Integer> ids = nounsMap.getOrDefault(noun, new LinkedHashSet<>());
                ids.add(synsetId);
                nounsMap.put(noun, ids);
            }
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
        return nounsMap.keySet();

    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
        return nounsMap.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        Set<Integer> v = nounsMap.get(nounA);
        Set<Integer> w = nounsMap.get(nounB);

        return sap.length(v, w);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        Set<Integer> v = nounsMap.get(nounA);
        Set<Integer> w = nounsMap.get(nounB);

        int ancestor = sap.ancestor(v, w);
        return synsetsMap.get(ancestor).iterator().next();
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wn = new WordNet("resources/wordnet/synsets.txt", "resources/wordnet/hypernyms.txt");
        assert wn.digraph.E() == 84505;
        assert wn.digraph.V() == 82192;
    }
}
