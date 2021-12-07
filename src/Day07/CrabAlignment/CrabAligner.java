package Day07.CrabAlignment;

import Common.Tuple;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CrabAligner {

    private static final boolean loadExample = false;

    public Tuple<Integer, Long> CrabAlignCost_Median() {
        final var median = loadInput().sorted().collect(Collectors.collectingAndThen(
                Collectors.toList(),
                ages -> {
                    int count = ages.size();
                    if (count % 2 == 0) { // even number
                        return (ages.get(count / 2 - 1) + ages.get(count / 2)) / 2;
                    } else { // odd number
                        return ages.get(count / 2);
                    }
                }));
        return new Tuple<>(median, loadInput().mapToLong(i -> Math.abs(i - median)).sum());
    }

    public Tuple<Integer, Long> CrabAlignCost_Average() {
        final var t = loadInput().collect(Collectors.toList());
        final double s = t.size();
        final double averageD = t.stream().mapToInt(i -> i).sum() / s;
        final int average = t.stream().filter(i -> i < averageD).count() < s / 2 ? (int) averageD : (int) averageD + 1;

        return new Tuple<>(
                average,
                loadInput().mapToLong(i -> Math.abs(i - average)).map(i -> (i * i + i) / 2).sum());
    }


    private Stream<Integer> loadInput() {
        return loadExample ? loadExample() : loadData();
    }

    private Stream<Integer> loadData() {
        URL url = getClass().getResource("input.txt");
        assert url != null;

        try {
            return Files
                    .lines(Paths.get(url.getPath().substring(1)))
                    .map(s -> s.split(","))
                    .flatMap(Arrays::stream)
                    .map(Integer::parseInt);
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }

    private Stream<Integer> loadExample() {
        return Arrays.stream("16,1,2,0,4,2,7,1,2,14".split(",")).map(Integer::parseInt);
    }
}
