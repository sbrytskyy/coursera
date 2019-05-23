import edu.princeton.cs.algs4.StdOut;

public class BaseballEliminationTest {
    public static void main(String[] args) {
        test1("resources/baseball/teams4.txt");
        test1("resources/baseball/teams5.txt");
        test2("resources/baseball/teams12.txt");
    }

    private static void test2(String filename) {
        BaseballElimination division = new BaseballElimination(filename);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
        
        Iterable<String> elimination = division.certificateOfElimination("Japan");
        StdOut.println(elimination);
    }

    private static void test1(String filename) {
        BaseballElimination division = new BaseballElimination(filename);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
