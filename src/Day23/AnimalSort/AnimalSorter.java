package Day23.AnimalSort;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

public class AnimalSorter extends AnimalSorterInputProvider {
    public int MinSortPoints(int loadId) {
        CaveState startState = new CaveState(load(loadId));

        startState.visualize();

        startState.evaluateBoardState();
        var nextState = startState.getPossibleStates().stream().min(Comparator.comparingLong(CaveState::evaluateBoardState)).get();

        nextState.visualize();

        var allStates = nextState.getPossibleStates().stream().sorted(Comparator.comparingLong(CaveState::evaluateBoardState)).collect(Collectors.toList());
        int i= 0;
        for (var caveState: allStates) {
            System.out.println("\n" + (i++));
            System.out.println("CurrentCost: " + caveState.getCost());
            System.out.println("MinRemainingCost: " + caveState.minimalRemainingCost());
            System.out.println("Evaluated: " + caveState.evaluateBoardState());
            caveState.visualize();
        }

        var bestState = MinSortPoints(startState, Integer.MAX_VALUE);

        return bestState.get().getCost();
    }

    private Optional<CaveState> MinSortPoints(CaveState currentState, final int BestSolution) {
        if (currentState.getCost() > BestSolution)
            return Optional.empty();

        CaveState bestState = null;
        int localBest = BestSolution;
        var allStates = currentState.getPossibleStates().stream()
                .sorted(Comparator.comparingLong(CaveState::evaluateBoardState))
                .limit(3)
                .collect(Collectors.toList());
        System.out.println("{");
        for (var state : allStates) {
            System.out.println("\nCurrentCost: " + state.getCost());
            System.out.println("MinRemainingCost: " + state.minimalRemainingCost());
            System.out.println("Evaluated: " + state.evaluateBoardState());
            state.visualize();
            var resOpt = MinSortPoints(state, localBest);
            if (resOpt.isPresent()) {
                var res = resOpt.get();
                if (res.getCost() < localBest) {
                    bestState = res;
                    localBest = res.getCost();
                }
            }
        }
        System.out.println("}");
        if (localBest < BestSolution) {
            return Optional.of(bestState);
        } else {
            return Optional.empty();
        }
    }
}
