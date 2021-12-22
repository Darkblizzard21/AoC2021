package Common;

import java.util.Arrays;

public class Float3 {
    private float[] xyz = new float[3];

    public Float3(String str) {
        var strings = str.split(",", 3);
        xyz[0] = Float.parseFloat(strings[0]);
        xyz[1] = Float.parseFloat(strings[1]);
        if (2 < strings.length)
            xyz[2] = Float.parseFloat(strings[2]);
    }

    public Float3(float num) {
        xyz[0] = num;
        xyz[1] = num;
        xyz[2] = num;
    }

    public Float3(float x, float y, float z) {
        xyz[0] = x;
        xyz[1] = y;
        xyz[2] = z;
    }

    public Float3(Float3 copy) {
        xyz = copy.xyz.clone();
    }

    public Float3(Int3 copy) {
        xyz[0] = copy.x();
        xyz[1] = copy.y();
        xyz[2] = copy.z();
    }

    public float x() {
        return xyz[0];
    }

    public float y() {
        return xyz[1];
    }

    public float z() {
        return xyz[2];
    }

    public void setX(float x) {
        xyz[0] = x;
    }

    public void setY(float y) {
        xyz[1] = y;
    }

    public void setZ(float z) {
        xyz[2] = z;
    }

    public Float3 withX(float x) {
        return new Float3(x, xyz[1], xyz[2]);
    }

    public Float3 withY(float y) {
        return new Float3(xyz[0], y, xyz[2]);
    }

    public Float3 withZ(float z) {
        return new Float3(xyz[0], xyz[1], z);
    }

    public Float3 add(Float3 o) {
        return new Float3(x() + o.x(), y() + o.y(), z() + o.z());
    }

    public Float3 sub(Float3 o) {
        return new Float3(x() - o.x(), y() - o.y(), z() - o.z());
    }

    public Float3 mul(float mul) {
        return new Float3(x() * mul, y() * mul, z() * mul);
    }

    public Float3 rotX90() {
        return new Float3(x(), z(), -y());
    }

    public Float3 rotY90() {
        return new Float3(z(), y(), -x());
    }

    public Float3 rotZ90() {
        return new Float3(y(), -x(), z());
    }

    public double length() {
        return Math.sqrt(x() * x() + y() * y() + z() * z());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Float3 f3 = (Float3) o;
        return x() == f3.x() && y() == f3.y() && z() == f3.z();
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
