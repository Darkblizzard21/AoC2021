package Day21.DiracDice;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GameSimulatorInputProvider {
    protected int[] load(int loadId) {
        String toLoad;
        if (loadId == 1) {
            // 1 example for first task
            toLoad = "example" + loadId + ".txt";
        } else {
            toLoad = "input.txt";
        }
        URL url = getClass().getResource("input/" + toLoad);
        assert url != null;
        return load(url);
    }

    private int[] load(URL url) {
        try {
            return Files.lines(Paths.get(url.getPath().substring(1)))
                    .map(s->s.substring(s.length()-2))
                    .map(s->s.replace(" ",""))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        } catch (IOException | ClassCastException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }
}
