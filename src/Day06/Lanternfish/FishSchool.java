package Day06.Lanternfish;

import java.util.Arrays;
import java.util.Map;

public class FishSchool {
    private final long[] fish;
    private int currentDay = 0;

    public FishSchool(Map<Integer,Long> fishState) {
        fish = new long[9];
        for (int i = 0; i < 7; i++) {
            if(fishState.containsKey(i))
                fish[i] = fishState.get(i);
        }
    }


    public void nextDay() {
        long newBornFish = fish[currentDay];

        // move young fish into cycle
        fish[inBounds(currentDay)] += fish[7];
        fish[7] = fish[8];

        // add young fish
        fish[8] = newBornFish;

        // advance day
        currentDay = inBounds(currentDay+1);
    }

    public long fishCount(){
        return Arrays.stream(fish).sum();
    }

    private int inBounds(int i) {
        while (i > 6) {
            i = i - 7;
        }
        while (i < 0){
            i = i + 7;
        }
        return i;
    }
}