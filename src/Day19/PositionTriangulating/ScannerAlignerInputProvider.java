package Day19.PositionTriangulating;

import Common.Int3;
import Day18.SnailHomework.SnailNumber;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ScannerAlignerInputProvider {
    protected List<Scanner> load(int loadId) {
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

    private List<Scanner> load(URL url) {
        try {
            List<String> strings = Files.lines(Paths.get(url.getPath().substring(1)))
                    .collect(Collectors.toList());
            strings.remove(0);

            List<List<Int3>> localBeacons = new LinkedList<>();

            List<Int3> cur = new LinkedList<>();
            for (var str: strings) {
                if(str.length() <= 0)
                    continue;
                if(str.startsWith("---")){
                    localBeacons.add(cur);
                    cur = new LinkedList<>();
                    continue;
                }
                cur.add(new Int3(str));
            }
            localBeacons.add(cur);
            return localBeacons.stream().map(Scanner::new).collect(Collectors.toList());
        } catch (IOException | ClassCastException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }
}
