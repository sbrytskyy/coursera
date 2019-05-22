import java.util.LinkedHashMap;
import java.util.Map;

import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BaseballElimination {

    private final int teamsNumber;
    private final Map<String, Integer> teams = new LinkedHashMap<>();
    private final int[] wins;
    private final int[] losses;
    private final int[] remaining;
    private final int[][] games;
    
    private FlowNetwork G;
    private FordFulkerson ff;

    public BaseballElimination(String filename) { // create a baseball division from given filename in format specified below
        In in = new In(filename);

        teamsNumber = in.readInt();

        wins = new int[teamsNumber];
        losses = new int[teamsNumber];
        remaining = new int[teamsNumber];
        games = new int[teamsNumber][teamsNumber];

        for (int i = 0; i < teamsNumber; i++) {
            teams.put(in.readString(), i);

            wins[i] = in.readInt();
            losses[i] = in.readInt();
            remaining[i] = in.readInt();

            for (int j = 0; j < teamsNumber; j++) {
                games[i][j] = in.readInt();
            }
        }
        
        G = new FlowNetwork(teamsNumber);
        ff = new FordFulkerson(G, 0, 1);
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
        return wins[teams.get(team)];
    }

    public int losses(String team) { // number of losses for given team
        if (!teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        return losses[teams.get(team)];
    }

    public int remaining(String team) { // number of remaining games for given team
        if (!teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        return remaining[teams.get(team)];
    }

    public int against(String team1, String team2) { // number of remaining games between team1 and team2
        if (!teams.containsKey(team1) || !teams.containsKey(team2)) {
            throw new IllegalArgumentException();
        }
        return games[teams.get(team1)][teams.get(team2)];
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
