package Day05.HydrothermalVenture;

import Common.Tuple;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Line {
    public final int x1;
    public final int y1;
    public final int x2;
    public final int y2;

    public Line(int x1, int y1, int x2, int y2) {

        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public Line(List<Integer> in) {
        this.x1 = in.remove(0);
        this.y1 = in.remove(0);
        this.x2 = in.remove(0);
        this.y2 = in.remove(0);
    }

    public static Line fromString(String in) {
        var values = Arrays.stream(
                        in.replace(" -> ", ",")
                                .split(","))
                .map(Integer::parseInt).collect(Collectors.toList());
        return new Line(values);
    }

    public static List<Tuple<Integer, Integer>> GetCoverPointsFixX(final int x, final int smallerY, final int biggerY) {
        return IntStream.range(smallerY, biggerY + 1)
                .mapToObj(y -> new Tuple<>(x, y))
                .collect(Collectors.toList());
    }

    public static List<Tuple<Integer, Integer>> GetCoverPointsFixY(final int smallerX, final int biggerX, final int y) {
        return IntStream.range(smallerX, biggerX + 1)
                .mapToObj(x -> new Tuple<>(x, y))
                .collect(Collectors.toList());
    }

    public List<Tuple<Integer, Integer>> GetCoverPoints() {
        List<Tuple<Integer, Integer>> result;
        if (x1 == x2) {
            result = y1 <= y2 ? GetCoverPointsFixX(x1, y1, y2) : GetCoverPointsFixX(x1, y2, y1);
        } else if (y1 == y2) {
            result = x1 <= x2 ? GetCoverPointsFixY(x1, x2, y1) : GetCoverPointsFixY(x2, x1, y1);
        } else if (Math.abs(x1 - x2) == Math.abs(y1 - y2)) {
            result = new LinkedList<>();

            final int dirX = Integer.signum(x2 - x1);
            final int dirY = Integer.signum(y2 - y1);

            int iterX = x1;
            int iterY = y1;

            while (x1 < x2 ? iterX <= x2 : iterX >= x2) {
                result.add(new Tuple<>(iterX,iterY));

                iterX += dirX;
                iterY += dirY;
            }

        } else {
            throw new IllegalArgumentException("Only Straight Lines and 45 Degree Diagonal are allowed");
        }
        return result;
    }
}
