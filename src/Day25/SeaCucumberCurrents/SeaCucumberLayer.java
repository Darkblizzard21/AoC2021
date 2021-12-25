package Day25.SeaCucumberCurrents;

import Common.Tuple;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class SeaCucumberLayer {
    private final SclState[][] layer;
    private final int eastLenth;
    private final int southLenth;

    public SeaCucumberLayer(Stream<String> lines) {
        layer = lines
                .map(String::chars)
                .map(s -> s.mapToObj(SclStateUtil::fromChar).toArray(SclState[]::new))
                .toArray(SclState[][]::new);
        southLenth = layer.length;
        eastLenth = layer[0].length;
    }

    public boolean nextStep() {
        boolean movedAny = false;
        List<Tuple<Integer, Integer>> moveableHeads = new LinkedList<>();
        for (int southIdx = 0; southIdx < southLenth; southIdx++) {
            findEast: for (int eastIdx = 0; eastIdx < eastLenth; eastIdx++) {
                if(layer[southIdx][eastIdx] == SclState.East){
                    while (layer[southIdx][(eastIdx + 1) % eastLenth] == SclState.East) {
                        eastIdx = (eastIdx + 1) % eastLenth;
                        if(eastIdx == 0){
                            break findEast;
                        }
                    }
                    if (layer[southIdx][(eastIdx + 1) % eastLenth] == SclState.None)
                        moveableHeads.add(new Tuple<>(southIdx, eastIdx));
                }
            }
        }
        if (!moveableHeads.isEmpty()) {
            moveableHeads.forEach(eastHead -> moveNext(eastHead.x, eastHead.y));
            movedAny = true;
        }

        moveableHeads = new LinkedList<>();
        for (int eastIdx = 0; eastIdx < eastLenth; eastIdx++) {
            findSouth: for (int southIdx = 0; southIdx < southLenth; southIdx++) {
                if(layer[southIdx][eastIdx] == SclState.South){
                    while (layer[(southIdx+1) % southLenth][eastIdx] == SclState.South) {
                        southIdx = (southIdx + 1) % southLenth;
                        if(southIdx == 0){
                            break findSouth;
                        }
                    }
                    if (layer[(southIdx+1) % southLenth][eastIdx] == SclState.None)
                        moveableHeads.add(new Tuple<>(southIdx, eastIdx));
                }
            }
        }
        if (!moveableHeads.isEmpty()) {
            moveableHeads.forEach(southHead -> moveNext(southHead.x, southHead.y));
            movedAny = true;
        }

        return movedAny;
    }

    private boolean canStepNext(int southIdx, int eastIdx) {
        var val = layer[southIdx][eastIdx];
        switch (val) {
            case East:
                return layer[southIdx][(eastIdx + 1) % eastLenth] == SclState.None;
            case South:
                return layer[(southIdx + 1) % southLenth][eastIdx] == SclState.None;
            case None:
                return false;
            default:
                throw new IllegalStateException();
        }
    }

    private boolean tryMoveNext(int southIdx, int eastIdx) {
        if (!canStepNext(southIdx, eastIdx)) return false;

        moveNext(southIdx, eastIdx);
        return true;
    }

    private void moveNext(int southIdx, int eastIdx) {
        var val = layer[southIdx][eastIdx];
        layer[southIdx][eastIdx] = SclState.None;
        switch (val) {
            case East:
                layer[southIdx][(eastIdx + 1) % eastLenth] = SclState.East;
                break;
            case South:
                layer[(southIdx + 1) % southLenth][eastIdx] = SclState.South;
                break;
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < southLenth; i++) {
            for (int j = 0; j < eastLenth; j++) {
                switch (layer[i][j]) {
                    case East:
                        str.append(">");
                        break;
                    case South:
                        str.append("v");
                        break;
                    case None:
                        str.append(".");
                        break;
                }
            }
            str.append("\n");
        }

        return str.toString();
    }
}
