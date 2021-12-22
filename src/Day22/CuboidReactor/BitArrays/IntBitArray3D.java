package Day22.CuboidReactor.BitArrays;

public class IntBitArray3D implements BitArray3D {
    int[][][] internal;
    int zSize;
    public IntBitArray3D(int xSize, int ySize, int zSize){
        internal = new int[xSize][ySize][(int) Math.ceil(zSize/32.d)];
        this.zSize = zSize;
    }

    public void set(int x, int y, int z, boolean value){
        if(z >= zSize) throw new IndexOutOfBoundsException();
        int internalZ = 0;
        while (z>31) {
            z-=32;
            internalZ++;
        }
        z=31-z;
        int storageInt = internal[x][y][internalZ];
        if(((storageInt >> z & 1) == 1) != value){
            if(value){
                storageInt = storageInt | (1 << z);
            }
            else {
                storageInt = storageInt & (~(1 << z));
            }
            internal[x][y][internalZ] = storageInt;
        }
    }

    public boolean get(int x, int y, int z){
        if(z >= zSize) throw new IndexOutOfBoundsException();
        int internalZ = 0;
        while (z>31) {
            z-=32;
            internalZ++;
        }
        z=31-z;

        return (internal[x][y][internalZ] >> z & 1) == 1;
    }

    public long count(){
        long res = 0;
        for (int x = 0; x < internal.length; x++) {
            for (int y = 0; y < internal[x].length; y++) {
                for (int z = 0; z < zSize; z++) {
                    if(get(x,y,z)) res++;}
            }
        }
        return res;
    }

    @Override
    public String toString() {
        return "Bitarray3D{" +
                "Count: " + count()+
                '}';
    }
}
