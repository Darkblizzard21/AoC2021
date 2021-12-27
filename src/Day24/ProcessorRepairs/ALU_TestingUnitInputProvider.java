package Day24.ProcessorRepairs;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class ALU_TestingUnitInputProvider {
    protected List<String> load(int loadId) {
        String toLoad;
        if (loadId > 0 && loadId < 3) {
            // 1 example for first task
            toLoad = "example" + loadId + ".txt";
        } else {
            toLoad = "input.txt";
        }
        URL url = getClass().getResource("input/" + toLoad);
        assert url != null;
        return load(url);
    }

    private List<String> load(URL url) {
        try {
            return Files.lines(Paths.get(url.getPath().substring(1))).collect(Collectors.toList());
        } catch (IOException | ClassCastException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }
}
