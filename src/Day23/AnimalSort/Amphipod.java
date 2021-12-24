package Day23.AnimalSort;

import Common.Int3;
import Common.Tuple;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Amphipod {
    public final int targetIndex;
    public final int moveCost;
    private final Int3 position;

    private Amphipod(int targetIndex, int moveCost, Int3 position) {
        this.targetIndex = targetIndex;
        this.moveCost = moveCost;
        this.position = position;
    }

    public static Amphipod fromChar(char c, int x, int y) {
        Int3 position = new Int3(x, y, 0);
        switch (c) {
            case 'A':
                return Amber(position);
            case 'B':
                return Bronze(position);
            case 'C':
                return Copper(position);
            case 'D':
                return Desert(position);
            default:
                throw new IllegalArgumentException("Char '" + c + "' is not convertible to Amphipod");
        }
    }


    public static Amphipod Amber(Int3 position) {
        return new Amphipod(2, 1, position);
    }

    public static Amphipod Bronze(Int3 position) {
        return new Amphipod(4, 10, position);
    }

    public static Amphipod Copper(Int3 position) {
        return new Amphipod(6, 100, position);
    }

    public static Amphipod Desert(Int3 position) {
        return new Amphipod(8, 1000, position);
    }

    public Tuple<Amphipod, Integer> withNewPosition(int x, int y) {
        var newPosition = new Int3(x, y, 0);
        var diff = newPosition.sub(position);
        var newPositionCost = (Math.abs(diff.x()) + Math.abs(diff.y()));
        if ((y == 1 || y == 2) && (position.y() == 1 || position.y() == 2))
            newPositionCost += y + position.y();
        return new Tuple<>(new Amphipod(targetIndex, moveCost, newPosition), moveCost * newPositionCost);
    }

    public Int3 getPosition() {
        return position;
    }

    public boolean isInRightCave() {
        return (position.y() == 1 || position.y() == 2) && position.x() == targetIndex;
    }

    public int theoreticalRestCost() {
        return isInRightCave() ? 0 : (Math.abs(position.x() - targetIndex) + position.y() + 2) * moveCost;
    }

    public int theoreticalRestCost_IgnoreWin() {
        return (isInRightCave() ? position.y() + 3 : (Math.abs(position.x() - targetIndex) + position.y() + 2)) * moveCost;
    }

    public Optional<Integer> canReachTarget(List<Int3> blocked, Optional<Amphipod> lowerTargetPod) {
        if (lowerTargetPod.isPresent() && lowerTargetPod.get().moveCost != moveCost) return Optional.empty();
        int movementDir = (int) Math.signum(targetIndex - getPosition().x());

        for (int x = getPosition().x() + (getPosition().y() == 0 ? 0 : movementDir);
             movementDir == 1 ? x < targetIndex : x > targetIndex;
             x += 2 * movementDir) {
            if (blocked.contains(new Int3(x, 0, 0))) return Optional.empty();
        }
        if (lowerTargetPod.isPresent())
            return blocked.contains(new Int3(targetIndex, 1, 0)) ? Optional.empty() : Optional.of(1);
        return Optional.of(2);
    }

    public Stream<Amphipod> toStream() {
        return Stream.of(this);
    }

    public char getChar() {
        switch (targetIndex) {
            case 2:
                return 'A';
            case 4:
                return 'B';
            case 6:
                return 'C';
            case 8:
                return 'D';
            default:
                return '?';
        }
    }

    @Override
    public String toString() {
        return "" + getChar() + position;
    }
}
