package Day22.CuboidReactor.BitArrays;

import Common.Rectangle3D;

public class BitRectangle3D implements BitArray3D {
    private final IntBitArray3D bitarray3D;

    private final int xOffset;
    private final int yOffset;
    private final int zOffset;

    public BitRectangle3D(Rectangle3D rectangle3D) {
        xOffset = -rectangle3D.getLowerBound().x();
        yOffset = -rectangle3D.getLowerBound().y();
        zOffset = -rectangle3D.getLowerBound().y();
        bitarray3D = new IntBitArray3D(xOffset + rectangle3D.getUpperBound().x() + 1,
                yOffset + rectangle3D.getUpperBound().y() + 1,
                zOffset + rectangle3D.getUpperBound().z() + 1);
    }

    public boolean get(int x, int y, int z) {
        return bitarray3D.get(x + xOffset, y + yOffset, z + zOffset);
    }

    public void set(int x, int y, int z, boolean value) {
        bitarray3D.set(x + xOffset, y + yOffset, z + zOffset, value);
    }

    public long count(){
        return bitarray3D.count();
    }

    @Override
    public String toString() {
        return "BitRectangle3D{" +
                "bitarray3D=" + bitarray3D +
                ", xOffset=" + xOffset +
                ", yOffset=" + yOffset +
                ", zOffset=" + zOffset +
                '}';
    }
}
