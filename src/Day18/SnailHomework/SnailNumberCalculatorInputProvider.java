package Day18.SnailHomework;

import Common.DoubleLinkedVertex;
import Common.Tuple;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class SnailNumberCalculatorInputProvider {
    protected List<SnailNumber> load(int loadId) {
        String toLoad;
        if (0 < loadId && loadId <= 2) {
            // 1 example for first task
            toLoad = "example" + loadId + ".txt";
        } else {
            toLoad = "input.txt";
        }
        URL url = getClass().getResource("input/" + toLoad);
        assert url != null;
        return load(url);
    }

    private List<SnailNumber> load(URL url) {
        try {
            return Files.lines(Paths.get(url.getPath().substring(1)))
                    .map(SnailNumber::FromString)
                    .collect(Collectors.toList());

        } catch (IOException | ClassCastException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }
}
