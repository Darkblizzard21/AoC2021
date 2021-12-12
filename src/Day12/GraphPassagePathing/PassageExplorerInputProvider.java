package Day12.GraphPassagePathing;

import Common.DoubleLinkedVertex;
import Common.Tuple;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PassageExplorerInputProvider {
    protected static Tuple<DoubleLinkedVertex<String>, DoubleLinkedVertex<String>> toGraph(
            List<Tuple<String, String>> connections
    ) {
        Map<String, DoubleLinkedVertex<String>> map = new HashMap<>();
        for (var t : connections) {
            DoubleLinkedVertex<String> x;
            if(map.containsKey(t.x)){
                x = map.get(t.x);
            }
            else {
                x = new DoubleLinkedVertex<>(t.x);
                map.put(t.x,x);
            }

            DoubleLinkedVertex<String> y;
            if(map.containsKey(t.y)){
                y = map.get(t.y);
            }
            else {
                y = new DoubleLinkedVertex<>(t.y);
                map.put(t.y,y);
            }

            x.addNeighbour(y);
        }

        DoubleLinkedVertex<String> start = map.get("start");
        if(start == null)
            throw new InputMismatchException("Start was not present in set");

        DoubleLinkedVertex<String> end = map.get("end");
        if(end == null)
            throw new InputMismatchException("End was not present in set");
        return new Tuple<>(start,end);
    }

    protected List<Tuple<String, String>> load() {
        return load(0);
    }

    protected List<Tuple<String, String>> load(int inputId) {
        String toLoad;
        switch (inputId) {
            case 0:
                toLoad = "input.txt";
                break;
            case 1:
            case 2:
            case 3:
                toLoad = "example" + inputId + ".txt";
                break;
            default:
                throw new InputMismatchException();
        }
        URL url = getClass().getResource(toLoad);
        assert url != null;

        return load_intern(url);
    }

    private List<Tuple<String, String>> load_intern(URL url) {
        try {
            return Files
                    .lines(Paths.get(url.getPath().substring(1)))
                    .map(s -> s.split("-"))
                    .map(sa -> new Tuple<>(sa[0], sa[1]))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }
}
