package Common.Tests;

import Common.F_Rectangle3D;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class F_Rectangle3D_Tests {
    public static void main(String[] args) {
        int failCount = 0;
        if (!TestFullImmersedSubtract()) failCount++;

        System.out.println("TEST: " + (failCount == 0 ? "SUCCEEDED" : "FAILED (with " + failCount + " tests failing)"));
    }

    public static boolean TestFullImmersedSubtract() {
        var outer = new F_Rectangle3D(0, 0, 0, 3, 3, 3);
        var inner = new F_Rectangle3D(1, 1, 1, 2, 2, 2);

        List<F_Rectangle3D> expected = new LinkedList<>();
        expected.add(new F_Rectangle3D(0, 0, 0, 1, 1, 1));
        expected.add(new F_Rectangle3D(1, 0, 0, 2, 1, 1));
        expected.add(new F_Rectangle3D(2, 0, 0, 3, 1, 1));
        expected.add(new F_Rectangle3D(0, 1, 0, 1, 2, 1));
        expected.add(new F_Rectangle3D(1, 1, 0, 2, 2, 1));
        expected.add(new F_Rectangle3D(2, 1, 0, 3, 2, 1));
        expected.add(new F_Rectangle3D(0, 2, 0, 1, 3, 1));
        expected.add(new F_Rectangle3D(1, 2, 0, 2, 3, 1));
        expected.add(new F_Rectangle3D(2, 2, 0, 3, 3, 1));

        expected.add(new F_Rectangle3D(0, 0, 1, 1, 1, 2));
        expected.add(new F_Rectangle3D(1, 0, 1, 2, 1, 2));
        expected.add(new F_Rectangle3D(2, 0, 1, 3, 1, 2));
        expected.add(new F_Rectangle3D(0, 1, 1, 1, 2, 2));

        expected.add(new F_Rectangle3D(2, 1, 1, 3, 2, 2));
        expected.add(new F_Rectangle3D(0, 2, 1, 1, 3, 2));
        expected.add(new F_Rectangle3D(1, 2, 1, 2, 3, 2));
        expected.add(new F_Rectangle3D(2, 2, 1, 3, 3, 2));

        expected.add(new F_Rectangle3D(0, 0, 2, 1, 1, 3));
        expected.add(new F_Rectangle3D(1, 0, 2, 2, 1, 3));
        expected.add(new F_Rectangle3D(2, 0, 2, 3, 1, 3));
        expected.add(new F_Rectangle3D(0, 1, 2, 1, 2, 3));
        expected.add(new F_Rectangle3D(1, 1, 2, 2, 2, 3));
        expected.add(new F_Rectangle3D(2, 1, 2, 3, 2, 3));
        expected.add(new F_Rectangle3D(0, 2, 2, 1, 3, 3));
        expected.add(new F_Rectangle3D(1, 2, 2, 2, 3, 3));
        expected.add(new F_Rectangle3D(2, 2, 2, 3, 3, 3));

        var result = outer.subtract(inner);

        if (result.isEmpty())
            return false;
        var resultPresent = result.get();
        for (int i = 0; i < resultPresent.size(); i++) {
            var expectedExplicit = expected.get(i);
            var resultExplicit = resultPresent.get(i);
            if (!resultExplicit.equals(expectedExplicit))
                return false;
        }
        return true;
    }
}
