package Common;

import java.util.Arrays;

public class Int3 {
    public static final Int3 yPositive = new Int3(0,1,0);
    public static final Int3 xPositive = new Int3(1,0,0);
    public static final Int3 xNegative = new Int3(-1,0,0);
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

    public Int3 withX(int x) {
        return new Int3(x,xyz[1],xyz[2]);
    }

    public Int3 withY(int y) {
        return new Int3(xyz[0],y,xyz[2]);
    }

    public Int3 withZ(int z) {
        return new Int3(xyz[0],xyz[1],z);
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
