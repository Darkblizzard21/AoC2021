package Day17.TargetTrickShots;

import Common.Rectangle;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class ProbeLauncherInputProvider {
    protected Rectangle load(int loadId) {
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

    private Rectangle load(URL url) {
        try {
            var str = Files.lines(Paths.get(url.getPath().substring(1)))
                    .collect(Collectors.toList())
                    .get(0)
                    .substring(13);
            var xy = str.split(", ");
            var x = xy[0].substring(2);
            var y = xy[1].substring(2);
            var xs = x.split("\\.\\.");
            var ys = y.split("\\.\\.");
            var x1 = Integer.parseInt(xs[0]);
            var x2 = Integer.parseInt(xs[1]);
            var y1 = Integer.parseInt(ys[0]);
            var y2 = Integer.parseInt(ys[1]);

            return new Rectangle(x1,y1,x2,y2);
        } catch (IOException | ClassCastException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }
}
