package Day22.CuboidReactor;

import Common.Int3;
import Common.Rectangle3D;
import Common.Tuple;
import Day22.CuboidReactor.BitArrays.BitArray3D;
import Day22.CuboidReactor.BitArrays.BitRectangle3D;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

public class ReactorRepairer extends ReactorRepairerInputProvider {
    public static final long MAX_MEMORY_MB = (1 << 9);

    public long getLightCount(int loadId, int bound) {
        return calculateShard(new Rectangle3D(new Int3(-bound), new Int3(bound)), load(loadId));
    }

    public long getLightCountMultiThreadedBruteForce(int loadId) {
        var input = load(loadId);
        Int3 lowerBound = new Int3(Integer.MAX_VALUE);
        Int3 upperBound = new Int3(Integer.MIN_VALUE);
        for (var entry : input) {
            var l = entry.y.getLowerBound();
            lowerBound = new Int3(Math.min(lowerBound.x(), l.x()),
                    Math.min(lowerBound.y(), l.y()),
                    Math.min(lowerBound.z(), l.z()));
            var u = entry.y.getUpperBound();
            upperBound = new Int3(Math.max(upperBound.x(), u.x()),
                    Math.max(upperBound.y(), u.y()),
                    Math.max(upperBound.z(), u.z()));
        }
        ForkJoinPool threadPool = new ForkJoinPool(6);
        var vector = upperBound.sub(lowerBound);
        long allMemory = Math.abs((long) vector.x() * vector.y() * vector.z());
        long allMemoryMB = (allMemory / 1000) / 1000;

        return threadPool.invoke(calculateShardTask(new Rectangle3D(lowerBound, upperBound), input));
    }

    private RecursiveTask<Long> calculateShardTask(Rectangle3D mask, List<Tuple<Boolean, Rectangle3D>> commands) {
        return new RecursiveTask<>() {
            @Override
            protected Long compute() {
                return calculateShardCheckedMemory(mask, commands);
            }
        };
    }

    private long calculateShardCheckedMemory(Rectangle3D mask, List<Tuple<Boolean, Rectangle3D>> commands) {
        var vector = mask.getUpperBound().sub(mask.getLowerBound());
        long allMemory = Math.abs((long) vector.x() * vector.y() * vector.z());
        long allMemoryMB = (allMemory / 1000) / 1000;

        var input = commands.stream()
                .filter(t -> t.y.overlaps(mask))
                .map(t -> new Tuple<>(t.x, t.y.intersection(mask)))
                .collect(Collectors.toList());
        if (input.size() <= 0)
            return 0;
        if(!containsAddCommands(input))
            return 0;

        if (allMemoryMB > MAX_MEMORY_MB) {

            //Create tasks
            var halvedMask = mask.cutInHalfRandom();
            RecursiveTask<Long> taskOne = calculateShardTask(halvedMask.x, input);
            RecursiveTask<Long> taskTwo = calculateShardTask(halvedMask.y, input);

            taskOne.fork();
            taskTwo.fork();

            long one = taskOne.join();
            long two = taskTwo.join();

            return one + two;
        } else {
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

    private boolean containsAddCommands(List<Tuple<Boolean, Rectangle3D>> commands){
        for (var command: commands) {
           if(command.x) return true;
        }
        return false;
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
