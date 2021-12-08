package Day08.SevenSegmentSearch;

import Common.Tuple;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NumberCounter {
    public long count1478(){
        var x = loadOutputOnly().collect(Collectors.toList());
        return loadOutputOnly()
                .flatMap(Arrays::stream)
                .filter(s -> s.length() == 2 || s.length() == 3 || s.length() == 4 || s.length() == 7)
                .count();
    }

    private Stream<String[]> loadOutputOnly(){
        URL url = getClass().getResource("input.txt");
        assert url != null;

        try {
            return Files
                    .lines(Paths.get(url.getPath().substring(1)))
                    .map(s -> s.substring(61))
                    .map(s -> s.split(" "));
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }
}
