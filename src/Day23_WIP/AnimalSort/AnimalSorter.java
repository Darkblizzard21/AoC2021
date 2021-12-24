package Day23_WIP.AnimalSort;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("ALL")
public class AnimalSorter extends AnimalSorterInputProvider {
    public int MinSortPoints(int loadId) {
        var pods = new PodState(loadAsArray(loadId));

        solveLoop:
        while (!pods.isSolved()) {
            //Can Any EnterHome Solved
            tryEnterHome:
            for (var loc : pods.getAllLocations()) {
                var targetX = pods.targetX(loc.x, loc.y);
                var firstEmpty = pods.firstEmptySlot(targetX);
                if (firstEmpty == -1) continue;
                if (pods.canMove(loc.x, loc.y, targetX, firstEmpty)) {
                    for (int y = firstEmpty + 1; y < pods.pods[targetX].length; y++) {
                        if (pods.pods[targetX][y] != pods.pods[loc.x][loc.y]) continue tryEnterHome;
                    }
                    pods.move(loc.x, loc.y, targetX, firstEmpty);
                    continue solveLoop;
                }
            }

        }


        return pods.cost;
    }

    public int MinSortPoints_BruteForce(int loadId) {
        var pods = new PodStateUnmutable(loadAsArray(loadId), 0);

        return stateUnmutable(pods, new PodState(new int[11][3], Integer.MAX_VALUE)).cost;
    }

    private PodStateUnmutable stateUnmutable(PodStateUnmutable previousState, PodStateUnmutable bestSolution) {
        List<PodStateUnmutable> nextStates = new LinkedList<>();
        for (var t :previousState.getAllLocations()) {
            var x = t.x;
            var y = t.y;
            if ((x % 2 == 0 && x != 0 && x != 10)) {
                for (int toX = 0; toX < 11; toX++) {
                    if ((toX % 2 == 0 && toX != 0 && toX != 10)) continue;
                    if (previousState.canMove(x, toX))
                        nextStates.add(previousState.copyMove(x, toX));
                }
            } else {
                if (previousState.canMove(x, previousState.targetX(x, y)))
                    nextStates.add(previousState.copyMove(x, previousState.targetX(x, y)));
            }
        }
        nextStates.sort(Comparator.comparingInt(c->c.cost+c.minRestCost()));
        for (var next : nextStates) {
            if (next.cost > bestSolution.cost)
                continue;
            var res = next.isSolved() ? next : stateUnmutable(next, bestSolution);
            if (res.cost < bestSolution.cost) {
                bestSolution = res;
            }
        }
        return bestSolution;
    }

    public int solveExampleHardCoded() {
        var pods = new PodState(loadAsArray(1));

        pods.move(6, 3);

        pods.move(4, 6);

        pods.move(4, 5);
        pods.move(3, 4);

        pods.move(2, 4);

        pods.move(8, 7);
        pods.move(8, 9);

        pods.move(7, 8);
        pods.move(5, 8);

        pods.move(9, 2);

        System.out.println(pods.isSolved());
        return pods.cost;
    }
}
