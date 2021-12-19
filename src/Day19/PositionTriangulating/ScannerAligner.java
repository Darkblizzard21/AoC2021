package Day19.PositionTriangulating;

import java.util.*;
import java.util.stream.Stream;

public class ScannerAligner extends ScannerAlignerInputProvider {
    private final Map<Integer, List<Scanner>> cache = new HashMap<>();

    public long getNumberOfBeacons(int loadID) {
        return alignToFirstFromLoad(loadID).stream().map(Scanner::getAlignedBeacons).flatMap(Collection::stream).distinct().count();
    }

    public long biggestManhattansDistance(int loadID) {
        var aligned = alignToFirstFromLoad(loadID);
        return aligned.stream()
                .map(scanner ->
                        aligned.stream()
                                .map(scanner1 -> scanner1.getAlignedPosition()
                                        .sub(scanner.getAlignedPosition())
                                        .manhattanLength()
                                )
                )
                .flatMap(Stream::distinct)
                .mapToLong(i -> i)
                .max()
                .getAsLong();
    }

    private List<Scanner> alignToFirstFromLoad(int loadID) {
        if (!cache.containsKey(loadID))
            cache.put(loadID, alignToFirst(load(loadID)));
        return cache.get(loadID);
    }

    private List<Scanner> alignToFirst(List<Scanner> scanners) {
        var scanner0 = scanners.remove(0);

        List<Scanner> in0Space = new LinkedList<>();
        in0Space.add(scanner0);
        System.out.println("\nStarting Alignment of " + scanners.size() + " Scanners:");
        while (scanners.size() > 0) {
            align:
            for (var aligned : in0Space) {
                for (var toAlign : scanners) {
                    toAlign.alignTo(aligned);
                    if (toAlign.isAligned()) {
                        in0Space.add(toAlign);
                        scanners.remove(toAlign);
                        break align;
                    }
                }
            }
            System.out.println("Aligned: " + in0Space.get(in0Space.size() - 1));
        }
        return in0Space;
    }
}
