package Day14.PolymerizationExtention;

import Common.Tuple;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class PolymerBuilderInputProvider {
    protected Tuple<String, Map<String, Character>> load() {
        return load(false);
    }

    protected Tuple<String, Map<String, Character>> load(boolean loadExample) {
        URL url = getClass().getResource(loadExample ? "example.txt" : "input.txt");
        assert url != null;
        return load_intern(url);
    }

    private Tuple<String, Map<String, Character>> load_intern(URL url) {
        try {
            var stings = Files
                    .lines(Paths.get(url.getPath().substring(1)))
                    .filter(s -> s.length() > 0)
                    .collect(Collectors.toList());
            String input = stings.remove(0);
            Map<String, Character> mappings = new HashMap<>();
            stings.stream()
                    .map(s -> s.split(" -> "))
                    .forEach(s -> mappings.put(s[0], s[1].charAt(0)));
            return new Tuple<>(input, mappings);
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }
}
