package Day23.AnimalSort;

import Common.Tuple;

import java.util.LinkedList;
import java.util.List;

public class PodState {
    public final int[][] pods;
    public int cost;

    public PodState(int[][] pods) {
        this.pods = pods;
        cost = 0;
    }

    public boolean canMove(int fromX, int fromY, int toX, int toY) {
        if (fromY != 0 && pods[fromX][fromY - 1] != 0) return false; //check if something is above
        if (pods[toX][toY] != 0) return false; //check if target is blocked

        for (int x = Math.min(fromX, toX) + 1; x < Math.max(fromX, toX); x++) {
            if (x % 2 == 0) continue;
            if (pods[x][0] != 0) return false;
        }

        return true;
    }

    public int move(int fromX, int fromY, int toX, int toY) {
        if (!canMove(fromX, fromY, toX, toY)) throw new IllegalArgumentException();
        pods[toX][toY] = pods[fromX][fromY];
        pods[fromX][fromY] = 0;

        int moveCost = Math.abs(toX - fromX) + toY + fromY;

        if (fromX % 2 == 0 && fromX != 0 && fromX != 10)
            moveCost++;
        if (toX % 2 == 0 && toX != 0 && toX != 10)
            moveCost++;

        moveCost *= pods[toX][toY];
        cost += moveCost;

        return moveCost;
    }

    public List<Tuple<Integer, Integer>> getLocationOf(int number) {
        List<Tuple<Integer, Integer>> res = new LinkedList<>();
        for (int x = 0; x < pods.length; x++) {
            for (int y = 0; y < pods[x].length; y++) {
                if (pods[x][y] == number)
                    res.add(new Tuple<>(x, y));
            }
        }
        return res;
    }

    public void print() {
        System.out.println("\n#############");
        System.out.print("#");
        for (int x = 0; x < 11; x++) {
            if (pods[x][0] == 0 || (x % 2 == 0 && x != 0 && x != 10)) {
                System.out.print(".");
            } else {

                System.out.print(toChar(pods[x][0]));
            }
        }
        System.out.println("#");
        System.out.print("###");
        for (int x = 2; x < 10; x += 2) {
            if (pods[x][0] == 0) {
                System.out.print(".");
            } else {
                System.out.print(toChar(pods[x][0]));
            }
            System.out.print("#");
        }
        System.out.println("##");
        System.out.print("  #");
        for (int x = 2; x < 10; x += 2) {
            if (pods[x][1] == 0) {
                System.out.print(".");
            } else {

                System.out.print(toChar(pods[x][1]));
            }
            System.out.print("#");
        }
        System.out.println("  ");
        System.out.println("  #########  ");
    }

    private static char toChar(int i){
        switch (i) {
            case 1:
                return 'A';
            case 10:
                return 'B';
            case 100:
                return 'C';
            case 1000:
                return 'D';
            default:
                throw new IllegalArgumentException("ARRRRRRRRRRG!");
        }
    }
}
