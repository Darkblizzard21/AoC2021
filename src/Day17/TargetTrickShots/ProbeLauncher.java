package Day17.TargetTrickShots;

import Common.Rectangle;

import java.util.Optional;
import java.util.stream.IntStream;

public class ProbeLauncher extends ProbeLauncherInputProvider {
    public int highestY(int loadId) {
        Rectangle rectangle = load(loadId);

        int range = 500;
        int minimalX = minimalX(rectangle);

        return IntStream.range(minimalX, range)
                .map(x ->
                        IntStream.range(-range, range)
                                .mapToObj(y -> willHit(x, y, rectangle))
                                .filter(Optional::isPresent)
                                .mapToInt(Optional::get)
                                .max()
                                .orElse(-1)
                )
                .max()
                .getAsInt();
    }

    public long numberOfOptions(int loadId) {
        Rectangle rectangle = load(loadId);

        int range = 1000;
        int minimalX = minimalX(rectangle);

        return IntStream.range(minimalX, range)
                .map(x ->
                        IntStream.range(-range, range)
                                .mapToObj(y -> willHit(x, y, rectangle))
                                .filter(Optional::isPresent)
                                .mapToInt(o->1)
                                .sum()
                )
                .sum();
    }

    protected Optional<Integer> willHit(int x, int y, Rectangle rectangle) {
        int currX = 0;
        int currY = 0;

        int maxY = -1;

        var d = 0;
        while (currX <= rectangle.getUpperX() && currY >= rectangle.getLowerY()) {
            currX += x;
            currY += y;

            if (currY > maxY)
                maxY = currY;

            if (rectangle.isInRect(currX, currY))
                return Optional.of(maxY);

            y--;
            x = x == 0 ? 0 : x < 0 ? x + 1 : x - 1;
            d++;
        }

        return Optional.empty();
    }

    private int minimalX(Rectangle rectangle) {
        int startVelocity = 0;
        while (true) {
            int x = 0;
            for (int i = startVelocity; i > 0; i--) {
                x += i;
                if (rectangle.getLowerX() <= x) {
                    return startVelocity;
                }
            }
            startVelocity++;
        }
    }
}
