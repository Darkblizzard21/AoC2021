package Day11.OctopusDisco;

import Common.Tuple;

import java.util.LinkedList;
import java.util.Queue;

public class FlashPredictor extends FlashPredictorInputProvider {
    public long Flashes(int steps) {
        var input = loadInput();
        long flashes = 0;
        for (int i = 0; i < steps; i++) {
            flashes += stepForward(input);
        }
        return flashes;
    }

    public long FirstSyncedFlash(){
        var input = loadInput();
        long steps = 1;
        while (stepForward(input) != 100)
            steps++;

        return steps;
    }

    protected long stepForward(int[][] input) {
        Queue<Tuple<Integer, Integer>> flashQueue = new LinkedList<>();
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                input[i][j]++;
                if (input[i][j] == 10)
                    flashQueue.add(new Tuple<>(i, j));
            }
        }

        while (!flashQueue.isEmpty()) {
            var next = flashQueue.poll();
            for (int i = Math.max(next.x - 1, 0); i < Math.min(next.x + 2, input.length); i++) {
                for (int j = Math.max(next.y - 1, 0); j < Math.min(next.y + 2, input[i].length); j++) {
                    input[i][j]++;
                    if (input[i][j] == 10)
                        flashQueue.add(new Tuple<>(i, j));
                }
            }
        }

        long flashes = 0;
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                if (input[i][j] > 9){
                    input[i][j] = 0;
                    flashes++;
                }
            }
        }
        return flashes;
    }
}
