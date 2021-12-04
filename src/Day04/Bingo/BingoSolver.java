package Day04.Bingo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class BingoSolver {
    private List<Integer> calledNumbers;
    private List<BingoBoard> boards;

    private static List<Integer> StringToNumberList(String in, String splitter) {
        return Arrays.stream(in.split(",")).map(Integer::parseInt).collect(Collectors.toList());
    }

    public int SolveBingo() {
        loadInput();

        for (Integer i : calledNumbers) {
            for (BingoBoard bb : boards) {
                var res = bb.callNumber(i);
                if (res) {
                    return bb.unmarkedSum() * i;
                }
            }
        }
        return -1;
    }

    public int SolveBingo_FindLooser() {
        loadInput();

        boolean lastBoard = false;
        for (Integer i : calledNumbers) {
            List<BingoBoard> winners = new LinkedList<>();
            for (BingoBoard bb : boards) {
                var res = bb.callNumber(i);
                if (res) {
                    if (lastBoard) return bb.unmarkedSum() * i;

                    winners.add(bb);
                }
            }

            for (BingoBoard win : winners) {
                boards.remove(win);
            }

            if (boards.size() == 1) {
                lastBoard = true;
            }
        }

        return -1;
    }

    private void loadInput() {
        URL url = getClass().getResource("input.txt");
        assert url != null;
        File file = new File(url.getPath());

        boards = new LinkedList<>();
        calledNumbers = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();

            // Read in Numbers
            calledNumbers = Arrays.stream(line.split(",")).map(Integer::parseInt).collect(Collectors.toList());

            while ((line = br.readLine()) != null) {
                if (!line.equals("")) throw new IllegalArgumentException();
                int[][] arr = new int[5][5];
                for (int i = 0; i < 5; i++) {
                    line = br.readLine();
                    arr[i] = Arrays.stream(line.trim().replaceAll(" +", " ").split("\\s+")).map(Integer::parseInt).mapToInt(integer -> integer).toArray();
                }
                boards.add(new BingoBoard(arr));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
