package Day22.CuboidReactor;

import Common.Rectangle3D;
import Common.Tuple;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class ReactorRepairerInputProvider {
    protected List<Tuple<Boolean, Rectangle3D>> load(int loadId) {
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

    private List<Tuple<Boolean,Rectangle3D>> load(URL url) {
        try {
            return Files.lines(Paths.get(url.getPath().substring(1)))
                    .map(ReactorRepairerInputProvider::parseInput)
                    .collect(Collectors.toList());
        } catch (IOException | ClassCastException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }

    private static Tuple<Boolean,Rectangle3D> parseInput(String s){
        var splitIndex = s.indexOf(' ');
        boolean onOff = splitIndex == 2;
        var coordinateStrings = s.substring(splitIndex+1).split(",");
        var coordinates = new String[3][2];
        for (int i = 0; i < coordinateStrings.length; i++) {
            coordinates[i] = coordinateStrings[i].substring(2).split("\\.\\.");
        }

        return new Tuple<>(onOff,new Rectangle3D(
                Integer.parseInt(coordinates[0][0]),
                Integer.parseInt(coordinates[1][0]),
                Integer.parseInt(coordinates[2][0]),
                Integer.parseInt(coordinates[0][1]),
                Integer.parseInt(coordinates[1][1]),
                Integer.parseInt(coordinates[2][1])
        ));
    }
}
