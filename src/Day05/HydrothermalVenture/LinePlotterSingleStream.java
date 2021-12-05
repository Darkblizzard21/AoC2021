package Day05.HydrothermalVenture;

import Common.Tuple;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static Day05.HydrothermalVenture.Line.GetCoverPointsFixX;
import static Day05.HydrothermalVenture.Line.GetCoverPointsFixY;

// Day 5 almost in one line of code.

public class LinePlotterSingleStream {
    public long findNumberOfAllCrossings() throws IOException {
        return Files
                .lines(Paths.get(
                        Objects.requireNonNull(getClass().getResource("input.txt"))
                                .getPath()
                                .substring(1))
                )
                .map(str -> new Line(
                                Arrays.stream(str.replace(" -> ", ",").split(","))
                                        .map(Integer::parseInt)
                                        .collect(Collectors.toList())
                        )
                )
                .map(l -> { // Is there any option to do this block in a functional call?
                            List<Tuple<Integer, Integer>> result;
                            if (l.x1 == l.x2) {
                                result = l.y1 <= l.y2 ?
                                        GetCoverPointsFixX(l.x1, l.y1, l.y2) :
                                        GetCoverPointsFixX(l.x1, l.y2, l.y1);
                            } else if (l.y1 == l.y2) {
                                result = l.x1 <= l.x2 ?
                                        GetCoverPointsFixY(l.x1, l.x2, l.y1) :
                                        GetCoverPointsFixY(l.x2, l.x1, l.y1);
                            } else if (Math.abs(l.x1 - l.x2) == Math.abs(l.y1 - l.y2)) {
                                result = new LinkedList<>();

                                final int dirX = Integer.signum(l.x2 - l.x1);
                                final int dirY = Integer.signum(l.y2 - l.y1);

                                int iterX = l.x1;
                                int iterY = l.y1;

                                while (l.x1 < l.x2 ? iterX <= l.x2 : iterX >= l.x2) {
                                    result.add(new Tuple<>(iterX, iterY));

                                    iterX += dirX;
                                    iterY += dirY;
                                }

                            } else {
                                throw new IllegalArgumentException("Only Straight Lines and 45 Degree Diagonal are allowed");
                            }
                            return result;
                        }
                )
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(t -> t.x + t.y * 10000, Collectors.counting()))
                .values()
                .stream()
                .filter(l -> l > 1).count();
    }
}
