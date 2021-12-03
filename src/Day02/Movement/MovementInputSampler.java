package Day02.Movement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class MovementInputSampler {
    public List<Tuple<InputType, Integer>> SampleInput() {
        URL url = getClass().getResource("input.txt");
        assert url != null;
        File file = new File(url.getPath());

        List<Tuple<InputType, Integer>> result = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("forward ")) {
                    Integer parseInt = Integer.parseInt(line.substring(8));
                    result.add(new Tuple<>(InputType.forward, parseInt));
                } else if (line.startsWith("down ")) {
                    Integer parseInt = Integer.parseInt(line.substring(5));
                    result.add(new Tuple<>(InputType.down, parseInt));
                }
                if (line.startsWith("up ")) {
                    Integer parseInt = Integer.parseInt(line.substring(3));
                    result.add(new Tuple<>(InputType.up, parseInt));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
