package Day04.Bingo;

import General.Tuple;

import java.util.Arrays;

public class BingoBoard {

    private final Tuple<Integer, Boolean>[][] content;

    public BingoBoard(int[][] in) {
        content = new Tuple[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                content[i][j] = new Tuple<>(in[i][j], false);
            }
        }
    }

    public boolean callNumber(int number) {
        int setI = -1;
        int setJ = -1;

        findNumber:
        for (int i = 0; i < content.length; i++) {
            for (int j = 0; j < content[i].length; j++) {
                var t = content[i][j];
                if (t.x == number) {
                    content[i][j] = t.withY(true);
                    setI = i;
                    setJ = j;
                    break findNumber;
                }
            }
        }

        if (setI == -1) return false;

        // Check Row
        boolean won = true;
        for (Tuple<Integer, Boolean>[] tuples : content) {
            won &= tuples[setJ].y;
        }

        if (won) return true;

        won = true;

        for (int j = 0; j < content.length; j++) {
            won &= content[setI][j].y;
        }

        return won;
    }

    public int unmarkedSum() {
        return Arrays.stream(content).sequential()
                .flatMap(tuples -> Arrays.stream(tuples).sequential())
                .filter(tuple -> !tuple.y)
                .map(tuple -> tuple.x)
                .reduce(0, Integer::sum);
    }

    public void printNumberMatrix() {
        for (Tuple<Integer, Boolean>[] tuples : content) {
            StringBuilder line = new StringBuilder();
            for (Tuple<Integer, Boolean> tuple : tuples) {
                int x = tuple.x;
                if (x < 10) line.append(" ");
                line.append(x).append(" ");
            }
            System.out.println(line);
        }
        System.out.println();
    }

    public void printBooleanMatrix() {
        for (Tuple<Integer, Boolean>[] tuples : content) {
            StringBuilder line = new StringBuilder();
            for (Tuple<Integer, Boolean> tuple : tuples) {
                boolean b = tuple.y;
                line.append(b ? "T" : "F").append(" ");
            }
            System.out.println(line);
        }
    }

}
