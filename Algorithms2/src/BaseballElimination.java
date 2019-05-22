import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import edu.princeton.cs.algs4.FlowEdge;
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

    private final boolean[] eliminated;

    public BaseballElimination(String filename) { // create a baseball division from given filename in format specified below
        In in = new In(filename);

        teamsNumber = in.readInt();

        wins = new int[teamsNumber];
        losses = new int[teamsNumber];
        remaining = new int[teamsNumber];
        games = new int[teamsNumber][teamsNumber];

        eliminated = new boolean[teamsNumber];

        for (int i = 0; i < teamsNumber; i++) {
            teams.put(in.readString(), i);

            wins[i] = in.readInt();
            losses[i] = in.readInt();
            remaining[i] = in.readInt();

            for (int j = 0; j < teamsNumber; j++) {
                games[i][j] = in.readInt();
            }
        }

        calculateTrivialElimination();
        calculateElimination();
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
        if (!teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }
        return eliminated[teams.get(team)];
    }

    public Iterable<String> certificateOfElimination(String team) { // subset R of teams that eliminates given team; null if not eliminated
        if (!teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }

        if (!isEliminated(team)) {
            return null;
        }

        List<String> cert = teams.keySet().stream().filter(name -> !eliminated[teams.get(name)])
                .collect(Collectors.toList());
        return cert;
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

    private void calculateElimination() {

        for (int x = 0; x < teamsNumber; x++) {
            if (eliminated[x]) {
                continue;
            }
            
            int source = 0;

            int teamV = source + 1;
            Map<Integer, Integer> teamVertices = new LinkedHashMap<>();
            for (int i = 0; i < teamsNumber; i++) {
                if (i == x) {
                    continue;
                }
                teamVertices.put(i, teamV++);
            }
            
            int gamesV = teamV;
            
            int gamesNum = 0;
            for (int i = 0; i < teamsNumber - 1; i++) {
                if (i == x) {
                    continue;
                }
                for (int j = i + 1; j < teamsNumber; j++) {
                    if (j == x) {
                        continue;
                    }
                    gamesNum++;
                }
            }

            int sink = gamesV + gamesNum;

            FlowNetwork G = new FlowNetwork(sink + 1);

            for (int i = 0; i < teamsNumber - 1; i++) {
                if (i == x) {
                    continue;
                }
                for (int j = i + 1; j < teamsNumber; j++) {
                    if (j == x) {
                        continue;
                    }
                    G.addEdge(new FlowEdge(source, gamesV, games[i][j]));
                    G.addEdge(new FlowEdge(gamesV, teamVertices.get(i), Double.POSITIVE_INFINITY));
                    G.addEdge(new FlowEdge(gamesV, teamVertices.get(j), Double.POSITIVE_INFINITY));
                    gamesV++;
                }
            }

            for (int i = 0; i < teamsNumber; i++) {
                if (i == x) {
                    continue;
                }

                int capacity = wins[x] + remaining[x] - wins[i];
                G.addEdge(new FlowEdge(teamVertices.get(i), sink, capacity));
            }

//          System.out.println(G.toString());
            FordFulkerson maxflow = new FordFulkerson(G, source, sink);

            Iterable<FlowEdge> adj = G.adj(source);
            for (FlowEdge flowEdge : adj) {
                if (Double.compare(flowEdge.flow(), flowEdge.capacity()) != 0) {
                    eliminated[x] = true;
                    break;
                }
            }

        }

    }

    private void calculateTrivialElimination() {
        for (int i = 0; i < teamsNumber; i++) {
            for (int j = 0; j < teamsNumber; j++) {
                if (i == j) {
                    continue;
                }
                if (wins[i] + remaining[i] < wins[j]) {
                    eliminated[i] = true;
                }
            }
        }
    }
}
