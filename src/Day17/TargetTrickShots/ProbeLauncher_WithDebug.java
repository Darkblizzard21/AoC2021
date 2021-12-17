package Day17.TargetTrickShots;

import Common.Rectangle;
import Common.Tuple;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProbeLauncher_WithDebug extends ProbeLauncher {

    public void drawLaunch(int x, int y, int loadId) {
        drawLaunch(x, y, load(loadId));
    }

    public void drawLaunch(int x, int y, Rectangle rectangle) {
        HashSet<Tuple<Integer, Integer>> points = new HashSet<>();
        var currX = 0;
        var currY = 0;
        boolean hasEntered = false;
        while ((!hasEntered || rectangle.isInRect(currX, currY)) &&
                (currX <= rectangle.getUpperX() && currY >= rectangle.getLowerY())) {
            currX += x;
            currY += y;
            points.add(new Tuple<>(currX, currY));

            if (!hasEntered && rectangle.isInRect(currX, currY)) {
                hasEntered = true;
            }

            x = x == 0 ? 0 : x < 0 ? x + 1 : x - 1;
            y--;
        }

        int maxX = Math.max(points.stream().mapToInt(t -> t.x).max().getAsInt(), 0);
        int minX = Math.min(points.stream().mapToInt(t -> t.x).min().getAsInt(), 0);
        int maxY = Math.max(points.stream().mapToInt(t -> t.y).max().getAsInt(), 0);
        int minY = Math.min(points.stream().mapToInt(t -> t.y).min().getAsInt(), 0);

        System.out.println();
        for (int i = maxY; i >= minY; i--) {
            for (int j = minX; j <= maxX; j++) {
                if (i == 0 && j == 0) {
                    System.out.print("S");
                } else if (points.contains(new Tuple<>(j, i))) {
                    System.out.print("#");
                } else if (rectangle.isInRect(j, i)) {
                    System.out.print("T");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void testAllDebugDirections() {
        var dirs = getAllDebugDirections();
        var rect = load(1);
        for (var dir : dirs) {
            if(willHit(dir.x,dir.y,rect).isEmpty()) {
                System.out.println("ERROR!");
                drawLaunch(dir.x,dir.y,rect);
                willHit(dir.x,dir.y,rect);
            }
        }
    }

    private HashSet<Tuple<Integer, Integer>> getAllDebugDirections() {
        URL url = getClass().getResource("input/debug.txt");
        assert url != null;

        try {
            return Files.lines(Paths.get(url.getPath().substring(1)))
                    .map(s -> s.split(" "))
                    .flatMap(Stream::of)
                    .filter(s -> !s.equals("") && !s.equals(" "))
                    .map(s -> s.split(","))
                    .map(s -> new Tuple<>(Integer.parseInt(s[0]), Integer.parseInt(s[1])))
                    .collect(Collectors.toCollection(HashSet::new));
        } catch (IOException | ClassCastException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }
}
