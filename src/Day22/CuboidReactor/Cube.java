package Day22.CuboidReactor;

import Common.Int3;
import Common.Rectangle3D;
import Common.Tuple;

import java.util.Optional;

public class Cube {
    final Int3 min;
    final Int3 max;
    final int sign;
    public Cube(int x1, int x2, int y1, int y2, int z1, int z2, int sign) {
        min = new Int3(Math.min(x1, x2), Math.min(y1, y2), Math.min(z1, z2));
        max = new Int3(Math.max(x1, x2), Math.max(y1, y2), Math.max(z1, z2));
        this.sign = sign;
    }

    public Cube(Int3 v1, Int3 v2, int sign) {
        min = new Int3(Math.min(v1.x(), v2.x()), Math.min(v1.y(), v2.y()), Math.min(v1.z(), v2.z()));
        max = new Int3(Math.max(v1.x(), v2.x()), Math.max(v1.y(), v2.y()), Math.max(v1.z(), v2.z()));
        this.sign = sign;
    }

    public static Tuple<Boolean, Cube> map(Tuple<Boolean, Rectangle3D> in) {
        return new Tuple<>(in.x, new Cube(in.y.getLowerBound(), in.y.getUpperBound(), in.x ? 1 : -1));
    }

    public long getLength() {
        return (1L + Math.abs(max.x() - min.x()))
                * (1L + Math.abs(max.y() - min.y()))
                * (1L + Math.abs(max.z() - min.z())) * sign;
    }

    public Optional<Cube> intersection(Cube other) {
        var difMin = new Int3(
                Math.max(min.x(), other.min.x()),
                Math.max(min.y(), other.min.y()),
                Math.max(min.z(), other.min.z()));
        var difMax = new Int3(
                Math.min(max.x(), other.max.x()),
                Math.min(max.y(), other.max.y()),
                Math.min(max.z(), other.max.z()));
        if (difMax.x() < difMin.x() ||
                difMax.y() < difMin.y() ||
                difMax.z() < difMin.z()) return Optional.empty();
        return Optional.of(new Cube(difMin, difMax, -sign));
    }

    @Override
    public String toString() {
        return "Cube{" +
                "c=" + getLength() +
                ", min=" + min +
                ", max=" + max +
                ", sign=" + sign +
                '}';
    }
}
