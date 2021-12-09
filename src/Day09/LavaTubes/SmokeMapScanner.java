package Day09.LavaTubes;

import Common.Tuple;

import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;

public class SmokeMapScanner extends SmokeMapInputProvider {

    private static int getBasinSize(Tuple<Integer, Integer> start, int[][] map) {
        return getBasin(start, map).size();
    }

    // Calculates the basin size for a given point in the map (x,y)
    protected static HashSet<Tuple<Integer, Integer>> getBasin(Tuple<Integer, Integer> start, int[][] map) {
        var basin = new HashSet<Tuple<Integer, Integer>>();
        Queue<Tuple<Integer, Integer>> frontier = new LinkedList<>();
        frontier.add(start);

        while (!frontier.isEmpty()) {
            final Tuple<Integer, Integer> cur = frontier.poll();
            final List<Tuple<Integer, Integer>> toFrontier = new LinkedList<>();
            boolean inBasin = true;

            // Check left
            Tuple<Integer, Integer> left = new Tuple<>(cur.x - 1, cur.y);
            if (cur.x != 0 && map[left.x][left.y] != 9 && !basin.contains(left)) {
                toFrontier.add(left);
            }

            // Check right
            Tuple<Integer, Integer> right = new Tuple<>(cur.x + 1, cur.y);
            if (cur.x != map.length - 1 && map[right.x][right.y] != 9 && !basin.contains(right)) {
                toFrontier.add(right);
            }

            // Check down
            Tuple<Integer, Integer> down = new Tuple<>(cur.x, cur.y - 1);
            if (cur.y != 0 && map[down.x][down.y] != 9 && !basin.contains(down)) {
                toFrontier.add(down);
            }

            // Check right
            Tuple<Integer, Integer> up = new Tuple<>(cur.x, cur.y + 1);
            if (cur.y != map[cur.x].length - 1 && map[up.x][up.y] != 9 && !basin.contains(up)) {
                toFrontier.add(up);
            }

            frontier.addAll(toFrontier);
            basin.add(cur);
        }
        return basin;
    }

    protected static boolean checkLowPoint(int x, int y, int[][] map) {
        if (x != 0 && map[x][y] >= map[x - 1][y])//check left
            return false;
        if (x != map.length - 1 && map[x][y] >= map[x + 1][y])//check left
            return false;
        if (y != 0 && map[x][y] >= map[x][y - 1])//check down
            return false;
        if (y != map[x].length - 1 && map[x][y] >= map[x][y + 1])//check up
            return false;
        return true;
    }

    public int findBasinScore() {
        final List<int[]> input = loadInput();
        final int[][] map = toArray2d(input, input.size() <= input.get(0).length);
        return IntStream.range(0, map.length)
                .mapToObj(i ->
                        IntStream.range(0, map[i].length)
                                .filter(j -> checkLowPoint(i, j, map))
                                .mapToObj(j -> new Tuple<>(i, j))
                )
                .flatMap(Function.identity())
                .mapToInt(t -> getBasinSize(t, map))
                .map(i -> -i).sorted().map(i -> -i)
                .limit(3)
                .reduce(1, (p, c) -> p * c);
    }

    public long findLowPointScore() {
        final List<int[]> input = loadInput();
        final int[][] map = toArray2d(input, input.size() <= input.get(0).length);
        return IntStream.range(0, map.length)
                .map(i ->
                        IntStream.range(0, map[i].length)
                                .filter(j -> checkLowPoint(i, j, map))
                                .map(j -> map[i][j] + 1)
                                .sum()
                )
                .sum();
    }
}
