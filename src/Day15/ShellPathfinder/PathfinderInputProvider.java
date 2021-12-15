package Day15.ShellPathfinder;

import Common.DoubleLinkedVertex;
import Common.Tuple;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PathfinderInputProvider {
    protected Tuple<DoubleLinkedVertex<Integer>, DoubleLinkedVertex<Integer>> load() {
        return load(false);
    }

    protected Tuple<DoubleLinkedVertex<Integer>, DoubleLinkedVertex<Integer>> load5(boolean loadExample) {
        URL url = getClass().getResource(loadExample ? "example_5.txt" : "input_5.txt");
        assert url != null;
        return load_intern(url);
    }

    protected Tuple<DoubleLinkedVertex<Integer>, DoubleLinkedVertex<Integer>> load(boolean loadExample) {
        URL url = getClass().getResource(loadExample ? "example.txt" : "input.txt");
        assert url != null;
        return load_intern(url);
    }

    private Tuple<DoubleLinkedVertex<Integer>, DoubleLinkedVertex<Integer>> load_intern(URL url) {
        try {
            @SuppressWarnings("unchecked")
            DoubleLinkedVertex<Integer>[][] vertices = (DoubleLinkedVertex<Integer>[][]) Files
                    .lines(Paths.get(url.getPath().substring(1)))
                    .map(s -> (DoubleLinkedVertex<Integer>[]) s.chars()
                            .map(i -> i - 48)
                            .mapToObj(DoubleLinkedVertex<Integer>::new)
                            .toArray(DoubleLinkedVertex[]::new))
                    .toArray(DoubleLinkedVertex[][]::new);

            System.out.println("vertices: " + (vertices.length* vertices[0].length));

            for (int i = 0; i < vertices.length - 1; i++) {
                for (int j = 0; j < vertices.length - 1; j++) {
                    vertices[i][j].addNeighbour(vertices[i + 1][j]);
                    vertices[i][j].addNeighbour(vertices[i][j + 1]);
                }
            }

            var l = vertices.length;
            var ll = vertices[l - 1].length;

            vertices[l-1][ll-1].addNeighbour(vertices[l-2][ll-1]);
            vertices[l-1][ll-1].addNeighbour(vertices[l-1][ll-2]);

            return new Tuple<>(vertices[0][0], vertices[l - 1][ll-1]);
        } catch (IOException | ClassCastException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }
}
