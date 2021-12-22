package Day22.CuboidReactor;

import Common.Int3;
import Common.Rectangle3D;
import Common.Tuple;
import Day22.CuboidReactor.BitArrays.BitArray3D;
import Day22.CuboidReactor.BitArrays.BitRectangle3D;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReactorRepairer extends ReactorRepairerInputProvider {
    public long getLightCount(int loadId, int bound) {
        return calculateShard(new Rectangle3D(new Int3(-bound), new Int3(bound)), load(loadId));
    }

    public long getLightCount(int loadId) {
        var input = load(loadId).stream().map(Cube::map).collect(Collectors.toList());
        List<Cube> cubes = new LinkedList<>();

        for (var command : input) {
            cubes = Stream.concat(cubes.stream(),
                            cubes.stream()
                                    .map(cube -> cube.intersection(command.y))
                                    .filter(Optional::isPresent)
                                    .map(Optional::get))
                    .collect(Collectors.toList());
            if (command.x)
                cubes.add(command.y);
        }

        return cubes.stream().mapToLong(Cube::getLength).sum();
    }

    private long calculateShard(Rectangle3D mask, List<Tuple<Boolean, Rectangle3D>> commands) {
        var input = commands.stream()
                .filter(t -> t.y.overlaps(mask))
                .map(t -> new Tuple<>(t.x, t.y.intersection(mask)))
                .collect(Collectors.toList());

        BitArray3D bits = new BitRectangle3D(mask);
        for (var entry : input) {
            var rect = entry.y;
            var value = entry.x;
            for (int x = rect.getLowerBound().x(); x <= rect.getUpperBound().x(); x++) {
                for (int y = rect.getLowerBound().y(); y <= rect.getUpperBound().y(); y++) {
                    for (int z = rect.getLowerBound().z(); z <= rect.getUpperBound().z(); z++) {
                        bits.set(x, y, z, value);
                    }
                }
            }
        }
        return bits.count();
    }
}
