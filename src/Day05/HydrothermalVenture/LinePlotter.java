package Day05.HydrothermalVenture;

import Common.Tuple;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LinePlotter {
    private List<Line> lines;
    private Map<Tuple<Integer, Integer>, Integer> map;

    public long findNumberOfCrossings() {
        return loadInput()
                .filter(l -> l.x1 == l.x2 || l.y1 == l.y2)
                .map(Line::GetCoverPoints)
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(t -> t.x + t.y * 10000, Collectors.counting()))
                .values()
                .stream()
                .filter(l -> l > 1).count();
    }

    public long findNumberOfCrossings_IncDiagonals() {
        return loadInput()
                .map(Line::GetCoverPoints)
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(t -> t.x * 10000 + t.y, Collectors.counting()))
                .values()
                .stream()
                .filter(l -> l > 1).count();
    }

    private Stream<Line> loadInput() {
        URL url = getClass().getResource("input.txt");
        assert url != null;

        try {
            return Files
                    .lines(Paths.get(url.getPath().substring(1)))
                    .map(Line::fromString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }
}
