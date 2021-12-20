package Day20.ImageGeneration;

import Day18.SnailHomework.SnailNumber;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.stream.Collectors;

public class ImageEnhancerInputProvider {
    protected InfiniteImage load(int loadId) {
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

    private InfiniteImage load(URL url) {
        try {
            var lines = Files.lines(Paths.get(url.getPath().substring(1)))
                    .map(this::mapString)
                    .collect(Collectors.toList());

            if(lines.get(0).length != 512) throw new InputMismatchException();
            var enhancementTable = lines.remove(0);

            while (lines.get(0).length == 0){
                lines.remove(0);
            }

            var mapCenter = new boolean[lines.size()][];
            int lineLength = lines.get(0).length;
            for (int i = 0; i < mapCenter.length; i++) {
                mapCenter[i] = lines.get(i);
                if(lineLength != mapCenter[i].length)
                    throw new InputMismatchException();
            }


            return new InfiniteImage(mapCenter, enhancementTable);
        } catch (IOException | ClassCastException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }

    private boolean[] mapString(String str){
        var bools= str.chars().mapToObj(i-> i == '#').toArray(Boolean[]::new);
        boolean[] res = new boolean[bools.length];

        for (int i = 0; i < bools.length; i++) {
            res[i] = bools[i];
        }

        return res;
    }
}
