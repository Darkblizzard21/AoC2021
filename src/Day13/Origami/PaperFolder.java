package Day13.Origami;

import Common.Tuple;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class PaperFolder extends PaperFolderInputProvider {
    public int pointCountAfterFolds(boolean useExample) {
        return pointsAfterFolds(useExample, Integer.MAX_VALUE).size();
    }

    public int pointCountAfterFolds(boolean useExample, int maxFolds) {
        return pointsAfterFolds(useExample, maxFolds).size();
    }

    public List<Tuple<Integer, Integer>> pointsAfterFolds(boolean useExample) {
        return pointsAfterFolds(useExample, Integer.MAX_VALUE);
    }

    public List<Tuple<Integer, Integer>> pointsAfterFolds(boolean useExample, int maxFolds) {
        var x = load(useExample);

        List<Tuple<Integer, Integer>> currentList = x.x;
        List<Tuple<Integer, Integer>> nextList = new LinkedList<>();

        int steps = 0;
        for (var fold : x.y) {
            if (!(steps < maxFolds)) break;
            for (var t : currentList) {
                if ((fold.x ? t.x : t.y).equals(fold.y)) throw new InputMismatchException();
                nextList.add(foldIfNeeded(t, fold.y, fold.x));
            }

            currentList = nextList.stream().distinct().collect(Collectors.toList());
            nextList = new LinkedList<>();
            steps++;
        }
        return currentList;
    }

    protected Tuple<Integer, Integer> foldIfNeeded(Tuple<Integer, Integer> in, int fold, boolean foldX) {
        if ((foldX ? in.x : in.y) < fold) return in;
        return foldX ? foldX(in, fold) : foldY(in, fold);
    }

    protected Tuple<Integer, Integer> fold(Tuple<Integer, Integer> in, int fold, boolean foldX) {
        return foldX ? foldX(in, fold) : foldY(in, fold);
    }

    protected Tuple<Integer, Integer> foldX(Tuple<Integer, Integer> in, int fold) {
        return new Tuple<>(2 * fold - in.x, in.y);
    }

    protected Tuple<Integer, Integer> foldY(Tuple<Integer, Integer> in, int fold) {
        return new Tuple<>(in.x, 2 * fold - in.y);
    }
}
