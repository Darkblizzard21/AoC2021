package Day23.AnimalSort;

import Common.Rectangle3D;
import Common.Tuple;
import Day22.CuboidReactor.ReactorRepairerInputProvider;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class AnimalSorterInputProvider {
        protected List<Amphipod> load(int loadId) {
            String toLoad;
            if (loadId > 0 && loadId < 3) {
                // 1 example for first task
                toLoad = "example" + loadId + ".txt";
            } else {
                toLoad = "input.txt";
            }
            URL url = getClass().getResource("input/" + toLoad);
            assert url != null;
            return load(url);
        }

        private List<Amphipod> load(URL url) {
            try {
                var lines = Files.lines(Paths.get(url.getPath().substring(1))).collect(Collectors.toList());
                var firstRow = lines.get(2);
                var secondRow = lines.get(3);
                List<Amphipod> amphipods = new LinkedList<>();
                amphipods.add(Amphipod.fromChar(firstRow.charAt(3),2,1));
                amphipods.add(Amphipod.fromChar(firstRow.charAt(5),4,1));
                amphipods.add(Amphipod.fromChar(firstRow.charAt(7),6,1));
                amphipods.add(Amphipod.fromChar(firstRow.charAt(9),8,1));

                amphipods.add(Amphipod.fromChar(secondRow.charAt(3),2,2));
                amphipods.add(Amphipod.fromChar(secondRow.charAt(5),4,2));
                amphipods.add(Amphipod.fromChar(secondRow.charAt(7),6,2));
                amphipods.add(Amphipod.fromChar(secondRow.charAt(9),8,2));

                return amphipods;
            } catch (IOException | ClassCastException e) {
                e.printStackTrace();
            }
            throw new IllegalStateException();
        }
}
