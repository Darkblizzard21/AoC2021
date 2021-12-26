package Day23.AnimalSort;

import Common.Tuple;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class State {
    public final int[][] pods;

    public State(int[][] pods) {
        this.pods = pods;
    }

    public List<Tuple<Integer, State>> allReachableStates() {
        List<Tuple<Integer, State>> states = new LinkedList<>();
        var allLocs = getAllLocations();
        for (var t : allLocs) {
            var x = t.x;
            var y = t.y;
            if (!canMoveAtAll(x, y)) continue;
            if (isSolved(x, y)) continue;

            //Check if it can move to target
            var targetX = targetX(x, y);
            var firstEmpty = firstEmptySlot(targetX);
            if (firstEmpty != -1 && canMove(x, y, targetX, firstEmpty)) {
                boolean belowRight = true;//Ceck if only right pods are in target
                for (int checkY = firstEmpty + 1; checkY < pods[targetX].length; checkY++) {
                    if (pods[targetX][checkY] != pods[x][y]) {
                        belowRight = false;
                        break;
                    }
                }
                if (belowRight) {
                    states.add(move(x, targetX));
                    continue;
                }
            }

            if ((x % 2 == 0 && x != 0 && x != 10)) {
                for (int toX = 0; toX < 11; toX++) {
                    if ((toX % 2 == 0 && toX != 0 && toX != 10)) continue;
                    if (canMove(x, toX))
                        states.add(move(x, toX));
                }
            }
        }
        return states;
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    public boolean isSolved() {
        for (int x = 2; x <= 8; x += 2) {
            for (int y = 0; y < pods[x].length; y++) {
                if (pods[x][y] != Math.pow(10, x / 2 - 1))
                    return false;
            }
        }
        return true;
    }

    public int minmalRestCost() {
        return getAllLocations().stream()
                .filter(t -> !isSolved(t.x, t.y))
                .mapToInt(t -> (int) (((Math.abs(t.x - targetX(t.x, t.y)) + t.y + 1 + ((t.x % 2 == 0 && t.x != 0 && t.x != 10) ? 1 : 0)) * pods[t.x][t.y]) * 0.9d)).sum();
    }

    private Tuple<Integer, State> move(int fromX, int toX) {
        var next = new int[pods.length][];
        for (int x = 0; x < pods.length; x++) {
            next[x] = new int[pods[x].length];
            System.arraycopy(pods[x], 0, next[x], 0, pods[x].length);
        }

        var fromY = firstEmptySlot(fromX) + 1;
        var toY = firstEmptySlot(toX);
        next[toX][toY] = next[fromX][fromY];
        next[fromX][fromY] = 0;

        int moveCost = Math.abs(toX - fromX) + toY + fromY;

        if (fromX % 2 == 0 && fromX != 0 && fromX != 10)
            moveCost++;
        if (toX % 2 == 0 && toX != 0 && toX != 10)
            moveCost++;

        moveCost *= next[toX][toY];

        return new Tuple<>(moveCost, new State(next));
    }

    private int firstEmptySlot(int forX) {
        for (int Y = pods[forX].length - 1; Y >= 0; Y--) {
            if (pods[forX][Y] == 0) return Y;
        }
        return -1;
    }

    private List<Tuple<Integer, Integer>> getAllLocations() {
        List<Tuple<Integer, Integer>> res = new LinkedList<>();
        for (int x = 0; x < pods.length; x++) {
            for (int y = 0; y < pods[x].length; y++) {
                if (pods[x][y] != 0)
                    res.add(new Tuple<>(x, y));
            }
        }
        return res;
    }

    private boolean isSolved(int x, int y) {
        if (x % 2 != 0 || x == 0 || x == 10) return false;
        if (targetX(x, y) != x) return false;
        if (y == pods[x].length - 1) return true;
        for (int i = y + 1; i < pods[x].length; i++) {
            if (pods[x][y] != pods[x][i])
                return false;
        }
        return true;
    }

    private boolean canMoveAtAll(int x, int y) {
        if (y == 0) return true;
        return pods[x][y - 1] == 0;
    }

    private boolean canMove(int fromX, int toX) {
        if (firstEmptySlot(toX) == -1) return false;
        return canMove(fromX, firstEmptySlot(fromX) + 1, toX, firstEmptySlot(toX));
    }

    private boolean canMove(int fromX, int fromY, int toX, int toY) {
        if (fromX == toX) return false; // can't move to same x
        if (pods[fromX][fromY] == 0) return false;
        if ((pods[toX].length > toY + 1) && (toX % 2 == 0 && toX != 0 && toX != 10) && (pods[toX][toY + 1] == 0))
            return false; // check that target not in thin air
        if (fromY != 0 && pods[fromX][fromY - 1] != 0) return false; //check if something is above
        if (pods[toX][toY] != 0) return false; //check if target is blocked

        //Ceck if path is clear
        for (int x = Math.min(fromX, toX) + 1; x < Math.max(fromX, toX); x++) {
            if (x % 2 == 0) continue;
            if (pods[x][0] != 0) return false;
        }

        return true;
    }

    private int targetX(int x, int y) {
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Arrays.deepEquals(pods, state.pods);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(pods);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("\n#############");
        str.append("\n#");
        for (int x = 0; x < 11; x++) {
            if (pods[x][0] == 0 || (x % 2 == 0 && x != 0 && x != 10)) {
                str.append(".");
            } else {
                str.append(StateUtil.toChar(pods[x][0]));
            }
        }
        str.append("#");
        str.append("\n###");
        for (int x = 2; x < 10; x += 2) {
            if (pods[x][0] == 0) {
                str.append(".");
            } else {
                str.append(StateUtil.toChar(pods[x][0]));
            }
            str.append("#");
        }
        str.append("##");
        for (int y = 1; y < pods[2].length; y++) {
            str.append("\n  #");
            for (int x = 2; x < 10; x += 2) {
                if (pods[x][y] == 0) {
                    str.append(".");
                } else {
                    str.append(StateUtil.toChar(pods[x][y]));
                }
                str.append("#");
            }

            str.append("  ");
        }
        str.append("\n  #########  ");

        return str.toString();
    }

    public void print() {
        System.out.println(this);
    }
}
