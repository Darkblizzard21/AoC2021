package Day09.LavaTubes;

import Common.ConsoleColors;
import Common.Tuple;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SmokeMapScanner {
    private final static int NUMBER_OFFSET = 48;

    private static int getBasinSize(Tuple<Integer, Integer> start, int[][] map) {
        return getBasin(start, map).size();
    }

    // Calculates the basin size for a given point in the map (x,y)
    private static HashSet<Tuple<Integer, Integer>> getBasin(Tuple<Integer, Integer> start, int[][] map) {
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
                if (map[cur.x][cur.y] >= map[left.x][left.y]) {
                    continue;
                } else {
                    toFrontier.add(left);
                }
            }

            // Check right
            Tuple<Integer, Integer> right = new Tuple<>(cur.x + 1, cur.y);
            if (cur.x != map.length - 1 && map[right.x][right.y] != 9 && !basin.contains(right)) {
                if (map[cur.x][cur.y] >= map[right.x][right.y]) {
                    continue;
                } else {
                    toFrontier.add(right);
                }
            }

            // Check down
            Tuple<Integer, Integer> down = new Tuple<>(cur.x, cur.y - 1);
            if (cur.y != 0 && map[down.x][down.y] != 9 && !basin.contains(down)) {
                if (map[cur.x][cur.y] >= map[down.x][down.y]) {
                    continue;
                } else {
                    toFrontier.add(down);
                }
            }

            // Check right
            Tuple<Integer, Integer> up = new Tuple<>(cur.x, cur.y + 1);
            if (cur.y != map[cur.x].length - 1 && map[up.x][up.y] != 9 && !basin.contains(up)) {
                if (map[cur.x][cur.y] >= map[up.x][up.y]) {
                    continue;
                } else {
                    toFrontier.add(up);
                }
            }

            frontier.addAll(toFrontier);
            basin.add(cur);
        }
        return basin;
    }

    private static boolean checkLowPoint(int x, int y, int[][] map) {
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

    private static int[][] toArray2d(List<int[]> list, boolean arraysAreSecondDimension) {
        final int listSize = list.size();
        int[][] result;

        if (arraysAreSecondDimension) {
            result = new int[listSize][];
            for (int i = 0; i < listSize; i++) {
                result[i] = list.get(i);
            }
        } else {
            int xSize = list.get(0).length;
            result = new int[xSize][listSize];
            for (int i = 0; i < listSize; i++) {
                int[] tmp = list.get(i);
                for (int j = 0; j < xSize; j++) {
                    result[j][i] = tmp[j];
                }
            }
        }

        return result;
    }

    private static void debugPrintBasin(HashSet<Tuple<Integer, Integer>> basin, int[][] map) {
        int minX = basin.stream().mapToInt(i -> i.x).min().orElseThrow();
        int maxX = basin.stream().mapToInt(i -> i.x).max().orElseThrow();
        int minY = basin.stream().mapToInt(i -> i.y).min().orElseThrow();
        int maxY = basin.stream().mapToInt(i -> i.y).max().orElseThrow();
        System.out.println("\nBasin Size: " + basin.size());
        for (int x = Math.max(minX - 1, 0); x < Math.min(maxX + 2, map.length); x++) {
            System.out.print(" ");
            for (int y = Math.max(minY - 1, 0); y < Math.min(maxY + 2, map[x].length); y++) {
                if (basin.contains(new Tuple<>(x, y))) {
                    System.out.print(ConsoleColors.BLUE + map[x][y]);
                } else {
                    System.out.print(ConsoleColors.DEFAULT + map[x][y]);
                }
            }
            System.out.println();
        }
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

    public void debugPrintBasin_Map() {
        final List<int[]> input = loadInput();
        final int[][] map = toArray2d(input, input.size() <= input.get(0).length);
        var basins = IntStream.range(0, map.length)
                .mapToObj(i ->
                        IntStream.range(0, map[i].length)
                                .filter(j -> checkLowPoint(i, j, map))
                                .mapToObj(j -> new Tuple<>(i, j))
                )
                .flatMap(Function.identity())
                .map(t -> getBasin(t, map))
                .flatMap(Collection::stream)
                .collect(Collectors.toCollection(HashSet::new));
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (basins.contains(new Tuple<>(i, j))) {
                    System.out.print(ConsoleColors.BLUE + map[i][j]);
                } else {
                    System.out.print(ConsoleColors.DEFAULT + map[i][j]);
                }
            }
            System.out.println();
        }

    }

    public void debugPrintBasin_Singles() {
        final List<int[]> input = loadInput();
        final int[][] map = toArray2d(input, input.size() <= input.get(0).length);
        System.out.println("Map: " + map.length + "x" + map[0].length);
        IntStream.range(0, map.length)
                .mapToObj(i ->
                        IntStream.range(0, map[i].length)
                                .filter(j -> checkLowPoint(i, j, map))
                                .mapToObj(j -> new Tuple<>(i, j))
                )
                .flatMap(Function.identity())
                .map(t -> getBasin(t, map))
                .sorted(Comparator.comparingInt(HashSet::size))
                .forEach(basin -> debugPrintBasin(basin, map));
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

    private List<int[]> loadExample() {
        URL url = getClass().getResource("example.txt");
        assert url != null;

        return load(url);
    }

    private List<int[]> loadInput() {
        URL url = getClass().getResource("input.txt");
        assert url != null;

        return load(url);
    }

    private List<int[]> load(URL url) {
        try {
            return Files
                    .lines(Paths.get(url.getPath().substring(1)))
                    .map(s -> s.chars().map(c -> c - NUMBER_OFFSET).toArray())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }
}
