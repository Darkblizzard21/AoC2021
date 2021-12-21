package Day21.DiracDice;

import Common.Tuple;
import Day21.DiracDice.Dice.Dice;

import java.util.LinkedList;
import java.util.List;

public class GameSimulator extends GameSimulatorInputProvider {
    private final int[] multipliers;

    public GameSimulator() {
        multipliers = new int[7];
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                for (int k = 1; k <= 3; k++) {
                    multipliers[i + j + k - 3] += 1;
                }
            }
        }
    }

    public long getGameScore(int loadId, Dice dice) {
        int[] players = load(loadId);
        if (players.length != 2)
            System.out.println("Length not as expected: Player Count: " + players.length);
        int[] playerScore = new int[players.length];

        int currentPlayer = 0;
        while (!hasWinner(playerScore)) {
            int playerRolls = dice.roll() + dice.roll() + dice.roll();
            int newPosition = in1to10(players[currentPlayer] + playerRolls);

            playerScore[currentPlayer] += newPosition;
            players[currentPlayer] = newPosition;

            currentPlayer = (currentPlayer + 1) % 2;
        }

        return dice.rollCount() * playerScore[currentPlayer];
    }

    public long getMostQuantumWins(int loadId) {
        int[] players = load(loadId);
        if (players.length != 2)
            System.out.println("Length not as expected: Player Count: " + players.length);
        var result = winsPlayer(0,players[0],0,players[1],true);
        return result.x > result.y ? result.x : result.y;
    }

    private Tuple<Long, Long> winsPlayer(int p1Score, int p1Position, int p2Score, int p2Position, boolean isP1Turn) {
        if (p1Score > 20)
            return new Tuple<>(1L, 0L);
        if (p2Score > 20)
            return new Tuple<>(0L, 1L);

        List<Tuple<Long,Long>> results =  new LinkedList<>();
        for (int i = 3; i <= 9; i++) {
            results.add(winsPlayer(
                    isP1Turn ? p1Score + in1to10(p1Position + i) : p1Score,
                    isP1Turn ? in1to10(p1Position + i) : p1Position,
                    isP1Turn ? p2Score : p2Score + in1to10(p2Position + i),
                    isP1Turn ? p2Position : in1to10(p2Position + i),
                    !isP1Turn));
        }

        long p1Wins = 0;
        long p2Wins = 0;
        for (int i = 0; i < 7; i++) {
            p1Wins += results.get(i).x * multipliers[i];
            p2Wins += results.get(i).y * multipliers[i];
        }
        return new Tuple<>(p1Wins,p2Wins);
    }

    private int in1to10(int i) {
        return ((i - 1) % 10) + 1;
    }

    private boolean hasWinner(int[] scores) {
        for (int score : scores) {
            if (score > 999)
                return true;
        }
        return false;
    }
}
