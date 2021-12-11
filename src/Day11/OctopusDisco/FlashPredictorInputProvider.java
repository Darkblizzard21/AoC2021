package Day11.OctopusDisco;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FlashPredictorInputProvider {
    protected int[][] loadInput(){
        URL url = getClass().getResource("input.txt");
        assert url != null;

        return load(url);
    }

    protected int[][] loadExample(){
        URL url = getClass().getResource("example.txt");
        assert url != null;

        return load(url);
    }

    private int[][] load(URL url){
        try {
            var out = new int[10][];
            return Files
                    .lines(Paths.get(url.getPath().substring(1)))
                    .map(String::chars)
                    .map(c -> c.map(Character::getNumericValue).toArray()).collect(Collectors.toList()).toArray(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }
}
