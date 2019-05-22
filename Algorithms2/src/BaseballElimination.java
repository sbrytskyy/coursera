import java.util.LinkedHashMap;
import java.util.Map;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BaseballElimination {

    private final int teamsNumber;
    private final Map<String, Integer> teams = new LinkedHashMap<>();
    private final int[] w;
    private final int[] l;
    private final int[] r;
    private final int[][] g;

    public BaseballElimination(String filename) { // create a baseball division from given filename in format specified below
        In in = new In(filename);

        teamsNumber = in.readInt();

        w = new int[teamsNumber];
        l = new int[teamsNumber];
        r = new int[teamsNumber];
        g = new int[teamsNumber][teamsNumber];

        for (int i = 0; i < teamsNumber; i++) {
            teams.put(in.readString(), i);

            w[i] = in.readInt();
            l[i] = in.readInt();
            r[i] = in.readInt();

            for (int j = 0; j < teamsNumber; j++) {
                g[i][j] = in.readInt();
            }
        }
    }

    public int numberOfTeams() { // number of teams
        return teamsNumber;
    }

    public Iterable<String> teams() { // all teams
        return teams.keySet();
    }

    public int wins(String team) { // number of wins for given team
        if (!teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        return w[teams.get(team)];
    }

    public int losses(String team) { // number of losses for given team
        if (!teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        return l[teams.get(team)];
    }

    public int remaining(String team) { // number of remaining games for given team
        if (!teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        return r[teams.get(team)];
    }

    public int against(String team1, String team2) { // number of remaining games between team1 and team2
        if (!teams.containsKey(team1) || !teams.containsKey(team2)) {
            throw new IllegalArgumentException();
        }
        return g[teams.get(team1)][teams.get(team2)];
    }

    public boolean isEliminated(String team) { // is given team eliminated?
        return false;
    }

    public Iterable<String> certificateOfElimination(String team) { // subset R of teams that eliminates given team; null if not eliminated
        return null;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
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
