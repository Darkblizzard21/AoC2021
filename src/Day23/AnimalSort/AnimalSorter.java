package Day23.AnimalSort;

import Common.PriorityQueueWithKey;

import java.util.HashMap;
import java.util.Map;

public class AnimalSorter extends AnimalSorterInputProvider {

    public int MinSortPoints(int loadId) {
        var start = new State(loadAsArray(loadId));
        return AStarSolve(start);
    }

    public int MinSortPoints_Full(int loadId) {
        var start = new State(toFullInput(loadAsArray(loadId)));
        return AStarSolve(start);
    }


    private int AStarSolve(State start){
        PriorityQueueWithKey<State> fScoreQueue = new PriorityQueueWithKey<>();
        fScoreQueue.set(start.minmalRestCost(), start);

        Map<State, Integer> gScore = new HashMap<>();
        gScore.put(start, 0);

        Map<State, State> cameFrom = new HashMap<>();

        int i = 0;
        while (fScoreQueue.hasNext()) {
            var keyValue = fScoreQueue.pollWithKey();
            var current = keyValue.y;
            var currentFscore = keyValue.x;

            System.out.println(i + ".\nfScore: " + currentFscore + "\ngScore: " + gScore.get(current) + current);

            if (current.isSolved()) {
                printSolve(current, cameFrom);
                return gScore.get(current);
            }

            for (var t : current.allReachableStates()) {
                var neigbour = t.y;
                var distance = t.x;

                int tentative_gScore = gScore.get(current) + distance;
                if (!gScore.containsKey(neigbour) || tentative_gScore < gScore.get(neigbour)) {
                    cameFrom.put(neigbour, current);
                    gScore.put(neigbour, tentative_gScore);
                    fScoreQueue.set(tentative_gScore + neigbour.minmalRestCost(), neigbour);
                }
            }
        }
        throw new IllegalStateException();
    }

    private void printSolve(State current, Map<State, State> cameFrom) {
        if (cameFrom.containsKey(current))
            printSolve(cameFrom.get(current), cameFrom);
        current.print();
    }

    private int[][] toFullInput(int[][] in){
        int[][] next = new int[in.length][];
        for (int x = 0; x < in.length; x++) {
            next[x] = new int[in[x].length == 1 ? in[x].length : in[x].length * 2];
        }

        next[2][0] = in[2][0];
        next[2][1] = 1000;
        next[2][2] = 1000;
        next[2][3] = in[2][1];


        next[4][0] = in[4][0];
        next[4][1] = 100;
        next[4][2] = 10;
        next[4][3] = in[4][1];

        next[6][0] = in[6][0];
        next[6][1] = 10;
        next[6][2] = 1;
        next[6][3] = in[6][1];

        next[8][0] = in[8][0];
        next[8][1] = 1;
        next[8][2] = 100;
        next[8][3] = in[8][1];

        return next;
    }
}
