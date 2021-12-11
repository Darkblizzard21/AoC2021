package Day11.OctopusDisco;

import Common.ConsoleColors;

public class FlashPredictor_WithDebug extends FlashPredictor {
    public long Flashes_PrintLastBoard(int steps) {
        var input = loadInput();
        long flashes = 0;
        for (int i = 0; i < steps; i++) {
            flashes += stepForward(input);
        }

        System.out.println("\nAfter step " + steps + ":");
        printBoard(input);
        return flashes;
    }

    private void printBoard(int[][] input) {
        for (int[] ints : input) {
            for (int anInt : ints) {
                if (anInt == 0) {
                    System.out.print(ConsoleColors.DEFAULT + anInt);
                } else {
                    System.out.print(ConsoleColors.WHITE_BOLD + anInt);
                }
            }
            System.out.println();
        }
    }
}
