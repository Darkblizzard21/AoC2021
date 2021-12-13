package Day13.Origami;

import Common.Tuple;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class PaperFolderInputProvider {
    protected Tuple<List<Tuple<Integer, Integer>>, List<Tuple<Boolean, Integer>>> load() {
        return load(false);
    }

    protected Tuple<List<Tuple<Integer, Integer>>, List<Tuple<Boolean, Integer>>> load(boolean loadExample) {
        URL url = getClass().getResource(loadExample ? "example.txt" : "input.txt");
        assert url != null;
        return load_intern(url);
    }

    private Tuple<List<Tuple<Integer, Integer>>, List<Tuple<Boolean, Integer>>> load_intern(URL url) {
        try {
            List<Tuple<Integer, Integer>> coords = new LinkedList<>();
            List<Tuple<Boolean, Integer>> folds = new LinkedList<>();
            Files
                    .lines(Paths.get(url.getPath().substring(1)))
                    .filter(s-> s.length()>0)
                    .forEach(s -> {
                        if(s.contains("fold")){
                            folds.add(new Tuple<>(s.charAt(11) == 'x', Integer.parseInt(s.substring(13))));
                        }
                        else{
                            var c = s.split(",");
                            coords.add(new Tuple<>(Integer.parseInt(c[0]),Integer.parseInt(c[1])));
                        }
                    });
            return new Tuple<>(coords,folds);
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }
}
