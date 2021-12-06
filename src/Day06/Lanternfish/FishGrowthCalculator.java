package Day06.Lanternfish;

import Day05.HydrothermalVenture.Line;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FishGrowthCalculator {

    public long countFishAfterDay(int day){
        if(day < 0) return 0;

        var in = loadInput().collect(Collectors.groupingBy(t -> t, Collectors.counting()));
        FishSchool fs = new FishSchool(in);

        for (int i = 0; i < day; i++) {
            fs.nextDay();
        }
        return fs.fishCount();
    }


    private Stream<Integer> loadInput(){
        URL url = getClass().getResource("input.txt");
        assert url != null;

        try {
            return Files
                    .lines(Paths.get(url.getPath().substring(1)))
                    .map(s -> s.split(","))
                    .flatMap(Arrays::stream)
                    .map(Integer::parseInt);
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }
}
