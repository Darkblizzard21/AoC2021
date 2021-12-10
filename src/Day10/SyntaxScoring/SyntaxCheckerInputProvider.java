package Day10.SyntaxScoring;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class SyntaxCheckerInputProvider {
    protected Stream<String> loadExample() {
        URL url = getClass().getResource("example.txt");
        assert url != null;

        return load(url);
    }

    protected Stream<String> loadInput() {
        URL url = getClass().getResource("input.txt");
        assert url != null;

        return load(url);
    }

    private Stream<String> load(URL url) {
        try {
            return Files
                    .lines(Paths.get(url.getPath().substring(1)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }
}
