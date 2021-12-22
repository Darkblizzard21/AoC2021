package Common;

import java.util.*;

public class Rectangle3D {
    Int3 lowerBound;
    Int3 upperBound;

    public Rectangle3D(int x1, int y1, int z1, int x2, int y2, int z2) {
        lowerBound = new Int3(Math.min(x1, x2), Math.min(y1, y2), Math.min(z1, z2));
        upperBound = new Int3(Math.max(x1, x2), Math.max(y1, y2), Math.max(z1, z2));
    }

    public Rectangle3D(Int3 v1, Int3 v2) {
        lowerBound = new Int3(Math.min(v1.x(), v2.x()), Math.min(v1.y(), v2.y()), Math.min(v1.z(), v2.z()));
        upperBound = new Int3(Math.max(v1.x(), v2.x()), Math.max(v1.y(), v2.y()), Math.max(v1.z(), v2.z()));
    }

    public Int3 getLowerBound() {
        return lowerBound;
    }

    public Int3 getUpperBound() {
        return upperBound;
    }

    public boolean isInRect(Int3 i3) {
        return i3.x() <= upperBound.x() && i3.x() >= lowerBound.x()
                && i3.y() <= upperBound.y() && i3.y() >= lowerBound.y()
                && i3.z() <= upperBound.z() && i3.z() >= lowerBound.z();
    }

    public long getVolume() {
        return (long) (upperBound.x() - lowerBound.x() + 1)
                * (upperBound.y() - lowerBound.y() + 1)
                * (upperBound.z() - lowerBound.z() + 1);
    }

    public Rectangle3D intersection(Rectangle3D rect) {
        int xmin = Math.max(this.lowerBound.x(), rect.lowerBound.x());
        int xmax = Math.min(this.upperBound.x(), rect.upperBound.x());
        int ymin = Math.max(this.lowerBound.y(), rect.lowerBound.y());
        int ymax = Math.min(this.upperBound.y(), rect.upperBound.y());
        int zmin = Math.max(this.lowerBound.z(), rect.lowerBound.z());
        int zmax = Math.min(this.upperBound.z(), rect.upperBound.z());
        return new Rectangle3D(xmin, ymin, zmin, xmax, ymax, zmax);
    }

    public boolean overlaps(Rectangle3D rect) {
        return !(rect.upperBound.x() < lowerBound.x()
                || rect.upperBound.y() < lowerBound.y()
                || rect.upperBound.z() < lowerBound.z()
                || rect.lowerBound.x() > upperBound.x()
                || rect.lowerBound.y() > upperBound.y()
                || rect.lowerBound.z() > upperBound.z());
    }

    public Rectangle3D getClamped(int bound) {
        return new Rectangle3D(
                Math.max(lowerBound.x(), -bound),
                Math.max(lowerBound.y(), -bound),
                Math.max(lowerBound.z(), -bound),
                Math.min(upperBound.x(), bound),
                Math.min(upperBound.y(), bound),
                Math.min(upperBound.z(), bound));
    }

    public Tuple<Rectangle3D, Rectangle3D> cutInHalfRandom() {
        Random random = new Random();
        return cutInHalfRandom(random);
    }

    private Tuple<Rectangle3D, Rectangle3D> cutInHalfRandom(Random random) {
        switch (random.nextInt(3)) {
            case 0:
                if (cuttableX()) return cutInHalfX();
                break;
            case 1:
                if (cuttableY()) return cutInHalfY();
                break;
            case 2:
                if (cuttableZ()) return cutInHalfZ();
                break;
        }
        return cutInHalfRandom(random);
    }

    public boolean cuttableX() {
        return lowerBound.x() != upperBound.x();
    }

    public Tuple<Rectangle3D, Rectangle3D> cutInHalfX() {
        return new Tuple<>(new Rectangle3D(lowerBound.x(),
                lowerBound.y(),
                lowerBound.z(),
                lowerBound.x() + (upperBound.x() - lowerBound.x()) / 2,
                upperBound.y(),
                upperBound.z()),
                new Rectangle3D(lowerBound.x() + (upperBound.x() - lowerBound.x()) / 2 + 1,
                        lowerBound.y(),
                        lowerBound.z(),
                        upperBound.x(),
                        upperBound.y(),
                        upperBound.z()));
    }

    public boolean cuttableY() {
        return lowerBound.y() != upperBound.y();
    }

    public Tuple<Rectangle3D, Rectangle3D> cutInHalfY() {
        return new Tuple<>(new Rectangle3D(lowerBound.x(),
                lowerBound.y(),
                lowerBound.z(),
                upperBound.x(),
                lowerBound.y() + (upperBound.y() - lowerBound.y()) / 2,
                upperBound.z()),
                new Rectangle3D(lowerBound.x(),
                        lowerBound.y() + (upperBound.y() - lowerBound.y()) / 2 + 1,
                        lowerBound.z(),
                        upperBound.x(),
                        upperBound.y(),
                        upperBound.z()));
    }

    public boolean cuttableZ() {
        return lowerBound.z() != upperBound.z();
    }

    public Tuple<Rectangle3D, Rectangle3D> cutInHalfZ() {
        return new Tuple<>(new Rectangle3D(lowerBound.x(),
                lowerBound.y(),
                lowerBound.z(),
                upperBound.x(),
                upperBound.y(),
                lowerBound.z() + (upperBound.z() - lowerBound.z()) / 2),
                new Rectangle3D(lowerBound.x(),
                        lowerBound.y(),
                        lowerBound.z() + (upperBound.z() - lowerBound.z()) / 2 + 1,
                        upperBound.x(),
                        upperBound.y(),
                        upperBound.z()));
    }

    @Override
    public String toString() {
        return "Rectangle3D{" + lowerBound +
                ", " + upperBound +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rectangle3D that = (Rectangle3D) o;
        return Objects.equals(lowerBound, that.lowerBound) && Objects.equals(upperBound, that.upperBound);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lowerBound, upperBound);
    }
}
