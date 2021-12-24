package Day23.AnimalSort;

import Common.Int3;
import Common.Tuple;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CaveState {
    private final int cost;
    private final CaveState parent;
    List<Amphipod> amphipods;

    public CaveState(List<Amphipod> amphipods) {
        this.amphipods = amphipods;
        parent = null;
        cost = 0;
    }

    public CaveState(List<Amphipod> amphipods, int cost, CaveState parent) {
        this.parent = parent;
        this.amphipods = amphipods;
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public List<CaveState> getPossibleStates() {
        List<List<CaveState>> results = new LinkedList<>();
        for (var amphipod : amphipods) {
            results.add(getPossibleStatesWithMoving(amphipod));
        }
        return results.stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    private List<CaveState> getPossibleStatesWithMoving(Amphipod movingPod) {
        List<Amphipod> other = amphipods.stream()
                .filter(amphipod -> amphipod != movingPod)
                .collect(Collectors.toList());
        List<Int3> blockedPositions = other.stream()
                .map(Amphipod::getPosition)
                .collect(Collectors.toList());

        if (movingPod.getPosition().y() == 2 && blockedPositions.contains(movingPod.getPosition().sub(new Int3(0, 1, 0))))
            return new LinkedList<>();

        int preferredMovementDir = (int) Math.signum(movingPod.targetIndex - movingPod.getPosition().x());
        List<CaveState> results = new LinkedList<>();

        int target = movingPod.targetIndex + preferredMovementDir;
        if (preferredMovementDir == 0) {
            preferredMovementDir = 1;
            target = movingPod.getPosition().x() + 1;
        }
        for (int x = movingPod.getPosition().x() - (movingPod.getPosition().y() == 0 ? 2 * preferredMovementDir : preferredMovementDir);
             preferredMovementDir == 1 ? x <= target : x >= target;
             x = x + 2 * preferredMovementDir) {
            if (x == movingPod.getPosition().x()) continue;

            Int3 next = new Int3(x, 0, 0);
            if (blockedPositions.stream().anyMatch(int3 -> int3.equals(next))) {
                if (x != target && (preferredMovementDir == 1 ? x > movingPod.getPosition().x() : x < movingPod.getPosition().x()))
                    return results;
            } else {
                var replacingPod = movingPod.withNewPosition(x, 0);
                results.add(new CaveState(
                        Stream.concat(replacingPod.x.toStream(), other.stream())
                                .collect(Collectors.toList()),
                        cost + replacingPod.y,
                        this));
            }
        }

        Tuple<Amphipod, Integer> inFinalPosition = null;

        var posLower = new Int3(movingPod.targetIndex, 2, 0);
        var lower = other.stream().filter(s -> s.getPosition().equals(posLower)).findAny();
        if (lower.isPresent()) {
            var blockingTargetLower = lower.get();
            if (blockingTargetLower.targetIndex == movingPod.targetIndex) {
                var posUpper = new Int3(movingPod.targetIndex, 1, 0);
                var upper = other.stream().filter(s -> s.getPosition().equals(posUpper)).findAny();
                if (upper.isEmpty()) {
                    inFinalPosition = movingPod.withNewPosition(movingPod.targetIndex, 1);
                }
            }
        } else {
            inFinalPosition = movingPod.withNewPosition(movingPod.targetIndex, 2);
        }

        if (inFinalPosition != null && !inFinalPosition.x.getPosition().equals(movingPod.getPosition())) {
            results.add(new CaveState(
                    Stream.concat(inFinalPosition.x.toStream(), other.stream())
                            .collect(Collectors.toList()),
                    cost + inFinalPosition.y,
                    this)
            );
        }
        return results;
    }

    public int minimalRemainingCost() {
        return amphipods.stream().mapToInt(Amphipod::theoreticalRestCost).sum();
    }

    // Calculates boardState value (lower is better)
    public long evaluateBoardState() {
        List<Int3> blockedPositions = amphipods.stream()
                .map(Amphipod::getPosition)
                .collect(Collectors.toList());

        List<Amphipod> stillNeedMovement = amphipods.stream()
                .filter(a -> {
                    if (!a.isInRightCave()) return true;
                    if (a.getPosition().y() == 0) return true;
                    if (a.getPosition().y() == 1) {
                        var pBelow = a.getPosition().withY(2);
                        var aBelow = amphipods.stream().filter(o -> pBelow.equals(o.getPosition())).findAny().get();
                        return aBelow.moveCost != a.moveCost;
                    }
                    return false;
                }).collect(Collectors.toList());

        var moveAndNotMove = stillNeedMovement.stream().map(
                        c -> {
                            boolean bool;
                            switch (c.getPosition().y()) {
                                case 0:
                                    bool = ((c.getPosition().x() != 9)
                                            && (!blockedPositions.contains(c.getPosition().sub(new Int3(2, 0, 0)))
                                            || !blockedPositions.contains(c.getPosition().sub(new Int3(1, -1, 0))))
                                    )
                                            || ((c.getPosition().x() != 1)
                                            && (!blockedPositions.contains(c.getPosition().sub(new Int3(-2, 0, 0))))
                                            || !blockedPositions.contains(c.getPosition().sub(new Int3(-1, -1, 0)))
                                    );
                                    break;
                                case 1:
                                    bool = !blockedPositions.contains(c.getPosition().sub(new Int3(1, 1, 0)))
                                            || !blockedPositions.contains(c.getPosition().sub(new Int3(-1, 1, 0)));
                                    break;
                                case 2:
                                    bool = !blockedPositions.contains(c.getPosition().sub(Int3.yPositive)) &&
                                            (!blockedPositions.contains(c.getPosition().sub(new Int3(1, 2, 0)))
                                                    || !blockedPositions.contains(c.getPosition().sub(new Int3(-1, 2, 0))));
                                    break;
                                default:
                                    throw new IllegalStateException();
                            }
                            return new Tuple<>(bool, c);
                        }
                )
                .collect(Collectors.toList());

        //lower is better
        long theoreticalPenaltyFactor = 4;
        long inefficientUpperMovePenalty = 2;
        long boardScore = cost * 2L;

        for (var t : moveAndNotMove) {
            //Check if Amphipod can move to target
            var am = t.y;
            if (t.x && am.targetIndex != am.getPosition().x()) {
                var lowerTarget = new Int3(am.targetIndex, 2, 0);
                var canReachTarget = am.canReachTarget(
                        blockedPositions,
                        amphipods.stream()
                                .filter(a -> a.getPosition().equals(lowerTarget))
                                .findAny()
                );
                if (canReachTarget.isPresent()) {
                    boardScore += am.theoreticalRestCost_IgnoreWin();
                    continue;
                }
                var x = am.getPosition().x();
                if (am.getPosition().y() == 0 && x != am.targetIndex + 1 && x != am.targetIndex - 1) {
                    boardScore += am.theoreticalRestCost_IgnoreWin()
                            * theoreticalPenaltyFactor
                            * inefficientUpperMovePenalty;
                }

            }
            boardScore += am.theoreticalRestCost_IgnoreWin() * theoreticalPenaltyFactor;

        }


        return boardScore * ((1000L * stillNeedMovement.size()) / amphipods.size());
    }

    public boolean isSortedState() {
        return amphipods.stream().map(Amphipod::isInRightCave).filter(b -> b).count() == amphipods.size();
    }

    public double sortedPercent() {
        return amphipods.stream().map(Amphipod::isInRightCave).filter(b -> b).count() / (double) amphipods.size();
    }

    public void visualize() {
        char[][] chars = new char[3][11];
        for (var amphipod : amphipods) {
            chars[amphipod.getPosition().y()][amphipod.getPosition().x()] = amphipod.getChar();
        }
        System.out.println("#############");
        System.out.print("#");
        for (int x = 0; x < 11; x++) {
            if (chars[0][x] != 0) {
                System.out.print(chars[0][x]);
            } else {
                System.out.print('.');
            }
        }
        System.out.println("#");
        for (int y = 1; y < 3; y++) {
            System.out.print("###");
            for (int i = 2; i < 9; i += 2) {
                if (chars[y][i] != 0) {
                    System.out.print(chars[y][i]);
                } else {
                    System.out.print('.');
                }
                System.out.print("#");
            }
            System.out.println("##");
        }
        System.out.println("#############");
    }
}
