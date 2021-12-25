package Day25.SeaCucumberCurrents;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SeaCucumberShepardInputProvider {
    protected SeaCucumberLayer load(int loadId) {
        String toLoad;
        if (loadId > 0 && loadId < 4) {
            // 1 example for first task
            toLoad = "example" + loadId + ".txt";
        } else {
            toLoad = "input.txt";
        }
        URL url = getClass().getResource("input/" + toLoad);
        assert url != null;
        return load(url);
    }

    private SeaCucumberLayer load(URL url) {
        try {
            return new SeaCucumberLayer(Files.lines(Paths.get(url.getPath().substring(1))));
        } catch (IOException | ClassCastException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }
}
