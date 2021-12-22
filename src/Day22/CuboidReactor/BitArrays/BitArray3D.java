package Day22.CuboidReactor.BitArrays;

public interface BitArray3D {
    void set(int x, int y, int z, boolean value);

    boolean get(int x, int y, int z);

    long count();
}
