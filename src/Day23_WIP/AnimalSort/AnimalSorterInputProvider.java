package Day23_WIP.AnimalSort;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class AnimalSorterInputProvider {
    protected int[][] loadAsArray(int loadId) {
        String toLoad;
        if (loadId > 0 && loadId < 3) {
            // 1 example for first task
            toLoad = "example" + loadId + ".txt";
        } else {
            toLoad = "input.txt";
        }
        URL url = getClass().getResource("input/" + toLoad);
        assert url != null;
        return loadAsArray(url);
    }

    private int[][] loadAsArray(URL url) {
        try {
            var lines = Files.lines(Paths.get(url.getPath().substring(1))).collect(Collectors.toList());
            var firstRow = lines.get(2);
            var secondRow = lines.get(3);
            int[][] amphipods = new int[11][1];
            amphipods[2] = new int[2];
            amphipods[4] = new int[2];
            amphipods[6] = new int[2];
            amphipods[8] = new int[2];
            amphipods[2][0] = fromChar(firstRow.charAt(3));
            amphipods[2][1] = fromChar(secondRow.charAt(3));

            amphipods[4][0] = fromChar(firstRow.charAt(5));
            amphipods[4][1] = fromChar(secondRow.charAt(5));

            amphipods[6][0] = fromChar(firstRow.charAt(7));
            amphipods[6][1] = fromChar(secondRow.charAt(7));

            amphipods[8][0] = fromChar(firstRow.charAt(9));
            amphipods[8][1] = fromChar(secondRow.charAt(9));

            return amphipods;
        } catch (IOException | ClassCastException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }

    private int fromChar(char c) {
        switch (c) {
            case 'A':
                return 1;
            case 'B':
                return 10;
            case 'C':
                return 100;
            case 'D':
                return 1000;
            default:
                throw new IllegalArgumentException("Char '" + c + "' is not convertible to Amphipod");
        }
    }
}
