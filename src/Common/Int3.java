package Common;

import java.util.Arrays;

public class Int3 {
    private int[] xyz = new int[3];

    public Int3(String str) {
        var strings = str.split(",", 3);
        xyz[0] = Integer.parseInt(strings[0]);
        xyz[1] = Integer.parseInt(strings[1]);
        if (2 < strings.length)
            xyz[2] = Integer.parseInt(strings[2]);
    }

    public Int3(int num) {
        xyz[0] = num;
        xyz[1] = num;
        xyz[2] = num;
    }

    public Int3(int x, int y, int z) {
        xyz[0] = x;
        xyz[1] = y;
        xyz[2] = z;
    }

    public Int3(Int3 copy) {
        xyz = copy.xyz.clone();
    }

    public int x() {
        return xyz[0];
    }

    public int y() {
        return xyz[1];
    }

    public int z() {
        return xyz[2];
    }

    public void setX(int x) {
        xyz[0] = x;
    }

    public void setY(int y) {
        xyz[1] = y;
    }

    public void setZ(int z) {
        xyz[2] = z;
    }

    public Int3 add(Int3 o) {
        return new Int3(x() + o.x(), y() + o.y(), z() + o.z());
    }

    public Int3 sub(Int3 o) {
        return new Int3(x() - o.x(), y() - o.y(), z() - o.z());
    }

    public Int3 mul(int mul) {
        return new Int3(x() * mul, y() * mul, z() * mul);
    }

    public Int3 rotX90() {
        return new Int3(x(), z(), -y());
    }

    public Int3 rotY90() {
        return new Int3(z(), y(), -x());
    }

    public Int3 rotZ90() {
        return new Int3(y(), -x(), z());
    }

    public double length() {
        return Math.sqrt(x() * x() + y() * y() + z() * z());
    }

    public int manhattanLength() {
        return (int) Math.signum(x()) * x() +
                (int) Math.signum(y()) * y() +
                (int) Math.signum(z()) * z();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Int3 int3 = (Int3) o;
        return x() == int3.x() && y() == int3.y() && z() == int3.z();
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(xyz);
    }

    @Override
    public String toString() {
        return "{" +
                x() + "," +
                y() + "," +
                z() +
                '}';
    }
}
