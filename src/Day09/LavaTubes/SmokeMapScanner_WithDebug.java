package Day09.LavaTubes;

import Common.ConsoleColors;
import Common.Tuple;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SmokeMapScanner_WithDebug extends SmokeMapScanner {

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

    public void debugPrintBasin_Map() {
        debugPrintBasin_Map(false);
    }

    public void debugPrintBasin_Map(boolean invert) {
        final var basinHighlight = invert ? ConsoleColors.DEFAULT : ConsoleColors.BLUE;
        final var ridgeHighlight = invert ? ConsoleColors.BLUE : ConsoleColors.DEFAULT;
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
                    System.out.print(basinHighlight + map[i][j]);
                } else {
                    System.out.print(ridgeHighlight + map[i][j]);
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

}
