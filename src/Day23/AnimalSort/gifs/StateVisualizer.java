package Day23.AnimalSort.gifs;

import Day23.AnimalSort.State;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class StateVisualizer {
    private static final int podSize = 20;
    private static final int margin = 4;
    private static final Color amber = new Color((255 << 16) + (191 << 8));
    private static final Color bronze = new Color((205 << 16) + (127 << 8) + 50);
    private static final Color copper = new Color((222 << 16) + (49 << 8) + 99);
    private static final Color desert = new Color((128 << 16) + 128);
    private static final Color backGround = Color.BLACK;
    private static final Color empty = new Color((128 << 16) + (128 << 8) + 128);
    private static String projectName = "example";
    private static String prePath = "C:\\temp\\";

    public static void visualize(List<State> toVis) {
        var trace = fillStates(toVis).toArray(State[]::new);

        for (int i = 0; i < trace.length; i++) {
            createAndSave(trace[i], i);
        }
    }

    public static void visualize(List<State> toVis, String projectName) {
        StateVisualizer.projectName = projectName;
        visualize(toVis);
    }

    public static void createAndSave(State toVis, int Id) {
        var image = from(toVis);
        File dir = new File(prePath + projectName + "\\");
        File outputfile = new File(prePath + projectName + "\\" + Id + ".png");
        try {
            if (!dir.exists())
                dir.mkdirs();
            if (!outputfile.exists())
                outputfile.createNewFile();
            var x = ImageIO.write(image, "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage from(State toVis) {
        final int width = toVis.pods.length * (podSize + margin) + margin;
        final int height = (toVis.pods[2].length + 3) * (podSize + margin) + margin;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = image.createGraphics();

        graphics2D.setBackground(backGround);
        graphics2D.clipRect(0, 0, width, height);

        for (int podX = 0; podX < toVis.pods.length; podX++) {
            for (int podY = 0; podY < toVis.pods[podX].length; podY++) {

                graphics2D.setColor(getColor(toVis.pods[podX][podY]));
                graphics2D.fillRect(
                        podX * (margin + podSize) + margin,
                        podY * (margin + podSize) + margin,
                        podSize, podSize);
            }
        }
        return image;
    }

    private static Color getColor(int id) {
        switch (id) {
            case 1000:
                return desert;
            case 100:
                return copper;
            case 10:
                return bronze;
            case 1:
                return amber;
            default:
                return empty;
        }
    }

    private static List<State> fillStates(List<State> states) {
        var extendedStates = states.stream().peek(state -> {
                    var pods = state.pods;
                    for (int x = 2; x <= 8; x += 2) {
                        int[] next = new int[pods[x].length + 1];
                        System.arraycopy(pods[x], 0, next, 1, pods[x].length);
                        pods[x] = next;
                    }
                })
                .collect(Collectors.toList())
                .toArray(State[]::new);

        List<State> allStates = new LinkedList<>();
        allStates.add(extendedStates[0]);
        for (int i = 0; i < extendedStates.length - 1; i++) {
            var from = extendedStates[i];
            var to = extendedStates[i + 1];

            int fromX = 0;
            int fromY = 0;
            int toX = 0;
            int toY = 0;
            for (int x = 0; x < from.pods.length; x++) {
                for (int y = 0; y < from.pods[x].length; y++) {
                    if (from.pods[x][y] != 0 && to.pods[x][y] == 0) {
                        fromX = x;
                        fromY = y;
                    }
                    if (from.pods[x][y] == 0 && to.pods[x][y] != 0) {
                        toX = x;
                        toY = y;
                    }
                }
            }
            int x = fromX;
            int y = fromY;
            while (y > 0) {
                y -= 1;
                allStates.add(move(from, fromX, fromY, x, y));
            }
            while (x != toX) {
                x += (int) Math.signum(toX - fromX);
                allStates.add(move(from, fromX, fromY, x, y));
            }
            while (y != toY) {
                y += 1;
                allStates.add(move(from, fromX, fromY, x, y));
            }

            allStates.add(to);
        }
        return allStates;
    }

    private static State move(State s, int fromX, int fromY, int toX, int toY) {
        var next = new int[s.pods.length][];
        for (int x = 0; x < s.pods.length; x++) {
            next[x] = new int[s.pods[x].length];
            System.arraycopy(s.pods[x], 0, next[x], 0, s.pods[x].length);
        }

        next[toX][toY] = next[fromX][fromY];
        next[fromX][fromY] = 0;

        return new State(next);
    }
}
