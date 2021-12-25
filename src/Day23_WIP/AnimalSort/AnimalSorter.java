package Day23_WIP.AnimalSort;

import java.util.*;

@SuppressWarnings("ALL")
public class AnimalSorter extends AnimalSorterInputProvider {
    private final int depthLimit = 75;
    private int statesVisited = 0;
    private long generatedStates = 0;
    private HashSet<Integer> visited;
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
        statesVisited = 0;
        generatedStates = 0;
        visited = new HashSet<>();
        var bestSolution = stateUnmutable(pods, new PodState(new int[11][3], Integer.MAX_VALUE), 0);
        System.out.println("Generated "+ generatedStates+ " and visited " + statesVisited + " states to find solution with cost " + bestSolution.cost + ".");
        return bestSolution.cost;
    }

    private PodStateUnmutable stateUnmutable(PodStateUnmutable previousState, PodStateUnmutable bestSolution, int depth) {
        if (depth > depthLimit) return bestSolution;
        if(visited.contains(previousState.hashCode()))
            return bestSolution;
        visited.add(previousState.hashCode());
        System.out.println(statesVisited++);
        List<PodStateUnmutable> nextStates = new LinkedList<>();
        //Generate All states
        var allLocs = previousState.getAllLocations();
        for (var t : allLocs) {
            var x = t.x;
            var y = t.y;
            if(!previousState.canMoveAtAll(x,y)) continue;
            if (previousState.isSolved(x, y)) continue;
            if ((x % 2 == 0 && x != 0 && x != 10)) {
                for (int toX = 0; toX < 11; toX++) {
                    if ((toX % 2 == 0 && toX != 0 && toX != 10)) continue;
                    if (previousState.canMove(x, toX) && !previousState.isBlockingMove(x,toX))
                        nextStates.add(previousState.copyMove(x, toX));
                }
            } else {
                int toX = previousState.targetX(x, y);
                if (previousState.canMove(x, toX) && !previousState.isBlockingMove(x,toX))
                    nextStates.add(previousState.copyMove(x, previousState.targetX(x, y)));
            }
        }
        generatedStates += nextStates.size();
        nextStates.sort(Comparator.comparingInt(c -> c.cost + c.minRestCost()));

        var bestOption = tryGetStateWithLessRemainingPods(nextStates);
        if (bestOption.isPresent()) {
            var bestMove = bestOption.get();
            bestMove.print();
            if (bestMove.isSolved())
                return bestMove.cost < bestSolution.cost ? bestMove : bestSolution;
            return stateUnmutable(bestOption.get(), bestSolution, depth + 1);
        }

        var nand = 0;

        for (var next : nextStates) {
            if (bestSolution.cost < next.cost)
                continue;
            var res = next.isSolved() ?
                    next :
                    stateUnmutable(next, bestSolution, depth + 1);
            if (res.cost < bestSolution.cost) {
                bestSolution = res;
            }
        }
        return bestSolution;
    }

    private Optional<PodStateUnmutable> tryGetStateWithLessRemainingPods(List<PodStateUnmutable> states) {
        tryAll:
        for (var state : states) {
            for (var loc : state.getAllLocations()) {
                var targetX = state.targetX(loc.x, loc.y);
                var firstEmpty = state.firstEmptySlot(targetX);
                if (firstEmpty == -1) continue;
                if (state.canMove(loc.x, loc.y, targetX, firstEmpty)) {
                    for (int y = firstEmpty + 1; y < state.pods[targetX].length; y++) {
                        if (state.pods[targetX][y] != state.pods[loc.x][loc.y]) continue tryAll;
                    }
                    return Optional.of(state.copyMove(loc.x, loc.y, targetX, firstEmpty));
                }
            }
        }
        return Optional.empty();
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
