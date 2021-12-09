package Day09.LavaTubes;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class SmokeMapInputProvider {
    private final static int NUMBER_OFFSET = 48;

    protected static int[][] toArray2d(List<int[]> list, boolean arraysAreSecondDimension) {
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

    protected List<int[]> loadExample() {
        URL url = getClass().getResource("example.txt");
        assert url != null;

        return load(url);
    }

    protected List<int[]> loadInput() {
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
