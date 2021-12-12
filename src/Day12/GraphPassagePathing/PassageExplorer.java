package Day12.GraphPassagePathing;

import Common.DoubleLinkedVertex;

import java.util.HashSet;

import static Common.Util.isStringLowerCase;

public class PassageExplorer extends PassageExplorerInputProvider {
    public int calculatePathCount(int inputId) {
        return calculatePathCount(inputId, false);
    }

    public int calculatePathCount(int inputId, boolean allowVisitTwice) {
        var startEndTuple = toGraph(load(inputId));

        return allowVisitTwice ?
                exploreAllPaths_WithDoubleVisit(
                        startEndTuple.x,
                        startEndTuple.y,
                        new HashSet<>(),
                        null) :
                exploreAllPaths(
                        startEndTuple.x,
                        startEndTuple.y,
                        new HashSet<>());
    }

    private int exploreAllPaths(DoubleLinkedVertex<String> startPoint,
                                DoubleLinkedVertex<String> endPoint,
                                HashSet<DoubleLinkedVertex<String>> alreadyVisited) {
        if (startPoint.equals(endPoint)) return 1;

        int pathCount = 0;

        if (isStringLowerCase(startPoint.getContent()))
            alreadyVisited.add(startPoint);

        for (var neighbour : startPoint.neighbours()) {
            if (alreadyVisited.contains(neighbour)) continue;
            pathCount += exploreAllPaths(neighbour, endPoint, alreadyVisited);
        }

        alreadyVisited.remove(startPoint);

        return pathCount;
    }

    private int exploreAllPaths_WithDoubleVisit(DoubleLinkedVertex<String> startPoint,
                                                DoubleLinkedVertex<String> endPoint,
                                                HashSet<DoubleLinkedVertex<String>> alreadyVisited,
                                                DoubleLinkedVertex<String> doubleVisitPoint) {
        if (startPoint.equals(endPoint)) return 1;

        int pathCount = 0;

        if (!startPoint.equals(doubleVisitPoint) && isStringLowerCase(startPoint.getContent()))
            alreadyVisited.add(startPoint);

        for (var neighbour : startPoint.neighbours()) {
            if (alreadyVisited.contains(neighbour)) {
                if (doubleVisitPoint == null
                        && !neighbour.getContent().equals("start")
                        && !neighbour.getContent().equals("end")) {
                    pathCount += exploreAllPaths_WithDoubleVisit(neighbour, endPoint, alreadyVisited, neighbour);
                }
            } else {
                pathCount += exploreAllPaths_WithDoubleVisit(neighbour, endPoint, alreadyVisited, doubleVisitPoint);
            }
        }

        if (!startPoint.equals(doubleVisitPoint)) alreadyVisited.remove(startPoint);

        return pathCount;
    }
}
