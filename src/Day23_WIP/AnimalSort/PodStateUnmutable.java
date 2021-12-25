package Day23_WIP.AnimalSort;

import Common.Tuple;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class PodStateUnmutable {
    public final int[][] pods;
    public int cost;

    public PodStateUnmutable(int[][] pods, int cost) {
        this.pods = new int[pods.length][];
        for (int x = 0; x < pods.length; x++) {
            this.pods[x] = new int[pods[x].length];
            System.arraycopy(pods[x], 0, this.pods[x], 0, pods[x].length);
        }
        this.cost = cost;
    }

    private static char toChar(int i) {
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

    public int targetX(int x, int y) {
        switch (pods[x][y]) {
            case 1:
                return 2;
            case 10:
                return 4;
            case 100:
                return 6;
            case 1000:
                return 8;
            default:
                throw new IllegalArgumentException();
        }
    }

    public boolean canMoveAtAll(int x, int y){
        if(y == 0) return true;
        return pods[x][y-1]==0;

    }

    public boolean canMove(int fromX, int toX) {
        if (firstEmptySlot(toX) == -1) return false;
        return canMove(fromX, firstEmptySlot(fromX) + 1, toX, firstEmptySlot(toX));
    }

    public boolean canMove(int fromX, int fromY, int toX, int toY) {
        if (fromX == toX) return false; // can't move to same x
        if (pods[fromX][fromY] == 0) return false;
        if ((pods[toX].length > toY + 1) && (toX % 2 == 0 && toX != 0 && toX != 10) && (pods[toX][toY + 1] == 0))
            return false; // check that target not in thin air
        if (fromY != 0 && pods[fromX][fromY - 1] != 0) return false; //check if something is above
        if (pods[toX][toY] != 0) return false; //check if target is blocked

        for (int x = Math.min(fromX, toX) + 1; x < Math.max(fromX, toX); x++) {
            if (x % 2 == 0) continue;
            if (pods[x][0] != 0) return false;
        }

        return true;
    }

    public PodStateUnmutable copyMove(int fromX, int toX) {
        return copyMove(fromX, firstEmptySlot(fromX) + 1, toX, firstEmptySlot(toX));
    }

    public PodStateUnmutable copyMove(int fromX, int fromY, int toX, int toY) {
        PodState podState = new PodState(pods, cost);
        podState.move(fromX, fromY, toX, toY);
        return podState;
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

    public List<Tuple<Integer, Integer>> getAllLocations() {
        List<Tuple<Integer, Integer>> res = new LinkedList<>();
        for (int x = 0; x < pods.length; x++) {
            for (int y = 0; y < pods[x].length; y++) {
                if (pods[x][y] != 0)
                    res.add(new Tuple<>(x, y));
            }
        }
        return res;
    }

    public int firstEmptySlot(int forX) {
        for (int Y = pods[forX].length - 1; Y >= 0; Y--) {
            if (pods[forX][Y] == 0) return Y;
        }
        return -1;
    }

    public boolean isSolved() {
        return pods[2][0] == 1 && pods[2][1] == 1
                && pods[4][0] == 10 && pods[4][1] == 10
                && pods[6][0] == 100 && pods[6][1] == 100
                && pods[8][0] == 1000 && pods[8][1] == 1000;
    }

    public boolean isSolved(int x, int y) {
        if (x % 2 != 0 || x == 0 || x == 10) return false;
        if (targetX(x, y) != x) return false;
        if (!(y + 1 < pods[x].length)) return true;
        for (int i = y + 1; i < pods[x].length; i++) {
            if (pods[x][y] != pods[x][i])
                return false;
        }
        return true;
    }

    public boolean isBlockingMove(int fromX, int toX) {
        return isBlockingMove(fromX, firstEmptySlot(fromX) + 1, toX, firstEmptySlot(toX));

    }

    public boolean isBlockingMove(int fromX, int fromY, int toX, int toY) {
        if (toX < 2 || toX > 8) return false;

        int targetX = targetX(fromX, fromY);

        if(targetX < toX)

        for (int y = 0; y < pods[targetX].length; y++) {
            if (pods[targetX][y] == 0) continue;
            var otherTargetX = targetX(targetX, y);
            if (toX < otherTargetX)
                return true;
        }
        return false;
    }

    public int minRestCost() {
        return getAllLocations().stream().mapToInt(
                t -> {
                    int fromX = t.x;
                    int fromY = t.y;
                    int toX = targetX(fromX, fromY);
                    int moveCost = Math.abs(toX - fromX) + fromY + fromY;

                    if (fromX % 2 == 0 && fromX != 0 && fromX != 10)
                        moveCost++;
                    if (toX % 2 == 0 && toX != 0 && toX != 10)
                        moveCost++;

                    moveCost *= pods[fromX][fromY];
                    return moveCost;
                }).sum();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PodStateUnmutable that = (PodStateUnmutable) o;
        return cost == that.cost && Arrays.deepEquals(pods, that.pods);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(cost);
        result = 31 * result + Arrays.deepHashCode(pods);
        return result;
    }
}
