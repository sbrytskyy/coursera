
public class WordNetTest {

    // do unit testing of this class
    public static void main(String[] args) {
        test();
    }

    private static void test() {
        test0();
//        test1("resources/wordnet/synsets100-subgraph.txt", "resources/wordnet/hypernyms100-subgraph.txt");
    }

    private static void test0() {
        String synsets = "resources/wordnet/synsets.txt";
        String hypernyms = "resources/wordnet/hypernyms.txt";
        WordNet wn = new WordNet(synsets, hypernyms);

        String nounA;
        String nounB;
        int referenceDistance;
        String referenceSap;
        int distance;
        String sap;

        nounA = "VII";
        nounB = "SOB";
        referenceDistance = 13;
        referenceSap = "entity";
        distance = wn.distance(nounA, nounB);
        sap = wn.sap(nounA, nounB);
        assert distance == referenceDistance : distance + ":" + referenceDistance;
        assert sap.contains(referenceSap) : sap + ":" + referenceSap;

        nounA = "Saxon";
        nounB = "bosom";
        referenceDistance = 10;
        referenceSap = "entity";
        distance = wn.distance(nounA, nounB);
        sap = wn.sap(nounA, nounB);
        assert distance == referenceDistance : distance + ":" + referenceDistance;
//        assert sap.contains(referenceSap) : sap + ":" + referenceSap;
    }

    private static void test1(String synsets, String hypernyms) {
        WordNet wn = new WordNet(synsets, hypernyms);

        String nounA;
        String nounB;
        int referenceDistance;
        String referenceSap;
        int distance;
        String sap;

        nounA = "VII";
        nounB = "SOB";
        referenceDistance = 13;
        referenceSap = "entity";
        distance = wn.distance(nounA, nounB);
        sap = wn.sap(nounA, nounB);
        assert distance == referenceDistance : distance + ":" + referenceDistance;
//        assert sap.contains(referenceSap) : sap + ":" + referenceSap;
    }
}