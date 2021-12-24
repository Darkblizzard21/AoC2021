package Day23_WIP.AnimalSort;

public class PodState extends PodStateUnmutable {
    public PodState(int[][] pods) {
        super(pods, 0);
    }
    public PodState(int[][] pods, int cost) {
        super(pods, cost);
    }

    public int move(int fromX, int toX) {
        return move(fromX, firstEmptySlot(fromX) + 1, toX, firstEmptySlot(toX));
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
}
