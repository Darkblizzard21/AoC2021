package Day03.PowerConsumtion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RateCalculator {

    public static int OtherRate(int in) {
        if (1 << 11 < Integer.highestOneBit(in)) throw new IllegalArgumentException();
        int xor = Integer.parseInt("111111111111", 2);
        return in ^ xor;
    }

    public int CalculateRate(List<Integer> input) {
        int numberOfBytes = 12;
        int[] bitCount = new int[numberOfBytes];
        int numberCount = 0;

        for (var i : input) {
            for (int j = 0; j < bitCount.length; j++) {
                if (((i & 1 << j) == 1 << j))
                    bitCount[j]++;
            }
            numberCount++;
        }

        int rate = 0;
        for (int i = 0; i < bitCount.length; i++) {
            if (numberCount / 2 < bitCount[i]) {
                rate += 1 << i;
            }
        }
        return rate;
    }

    public int calculateOxygenRating(List<Integer> input) {
        return calculateOxygenOrCO2Rating(input, false);
    }

    public int calculateCO2Rating(List<Integer> input) {
        return calculateOxygenOrCO2Rating(input, true);
    }

    private int calculateOxygenOrCO2Rating(List<Integer> input, boolean co2) {
        int numberOfBytes = 12;
        int numberCount = input.size();

        for (int i = numberOfBytes - 1; i >= 0; i--) {
            int finalI = i;
            int oneCount = input.stream().reduce(0,(subtotal, element) ->
                    ((element & 1 << finalI) == 1 << finalI) ? subtotal + 1 : subtotal);
            boolean keepOnes = co2 ^ (numberCount / 2 <= oneCount);
            Predicate<Integer> filter = keepOnes ?
                    item -> ((item & 1 << finalI) == 1 << finalI) :
                    item -> ((item & 1 << finalI) == 0);
            input = input.stream().filter(filter).collect(Collectors.toList());
            numberCount = input.size();
            if (numberCount == 1) {
                return input.get(0);
            }
        }
        throw new IllegalStateException();
    }

    public List<Integer> sampleInput() {
        URL url = getClass().getResource("input.txt");
        assert url != null;
        File file = new File(url.getPath());

        List<Integer> result = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                result.add(Integer.parseInt(line, 2));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
