package Day16.ElvesMessageDecoder;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PackageParserInputProvider {
    protected BitQueue load(int loadId) {
        String toLoad;
        if (loadId > 0 & loadId <= 15) {
            // 1-7 examples for first task
            // 8-15
            toLoad = "example" + loadId + ".txt";
        } else {
            toLoad = "input.txt";
        }
        URL url = getClass().getResource(toLoad);
        assert url != null;
        return load(url);
    }

    private BitQueue load(URL url) {
        try {
            var x = Files.lines(Paths.get(url.getPath().substring(1)))
                    .map(String::chars)
                    .flatMap(IntStream::boxed)
                    .map(i -> (char) (int) i)
                    .collect(Collectors.toList());

            return new BitQueue(x);
        } catch (IOException | ClassCastException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }
}
