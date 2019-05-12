
public class WordNetTest {

    // do unit testing of this class
    public static void main(String[] args) {
        test();
    }

    private static void test() {
        test1("resources/wordnet/synsets.txt", "resources/wordnet/hypernyms.txt");
        test1("resources/wordnet/synsets100-subgraph.txt", "resources/wordnet/hypernyms100-subgraph.txt");
    }

    private static void test1(String synsets, String hypernyms) {
        WordNet wn = new WordNet(synsets, hypernyms);
    }
}