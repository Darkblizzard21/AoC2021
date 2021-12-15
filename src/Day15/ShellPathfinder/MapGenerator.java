package Day15.ShellPathfinder;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MapGenerator {
    public static void main(String[] args) {
        MapGenerator mg = new MapGenerator();
        mg.generateMap("test",5);
    }

    public void generateMap(String toTransform, int repeats) {
        var x = toTransform + ".txt";
        URL url = getClass().getResource(x);
        assert url != null;

        StringBuilder str = new StringBuilder();
        try {
            int[][] vertices = Files
                    .lines(Paths.get(url.getPath().substring(1)))
                    .map(s -> s.chars()
                            .map(i -> i - 48)
                            .toArray())
                    .toArray(int[][]::new);

            for (int[] vertex : vertices) {
                assert vertices.length == vertex.length;
            }

            for (int i = 0; i < repeats; i++) {
                for (int[] vertex : vertices) {
                    for (int k = 0; k < repeats; k++) {
                        for (int j : vertex) {
                            str.append(in1to9(j + k));
                        }
                    }
                    str.append("\n");
                }
                //Increase
                for (int j = 0; j < vertices.length; j++) {
                    for (int k = 0; k < vertices[j].length; k++) {
                        vertices[j][k] = in1to9(vertices[j][k] + 1);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }


        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(".\\src\\Day15\\ShellPathfinder\\"+toTransform+"_"+repeats+".txt"));
            writer.write(str.toString());

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int in1to9(int i) {
        return ((i - 1) % 9) + 1;
    }
}
