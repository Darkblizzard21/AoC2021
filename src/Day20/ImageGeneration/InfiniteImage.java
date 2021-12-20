package Day20.ImageGeneration;

import java.util.Arrays;

public class InfiniteImage {
    private final boolean[] enhancementTable;

    private final boolean[][] mapCenter;
    private final boolean voidValue;

    public InfiniteImage(boolean[][] mapCenter,boolean[] enhancementTable){
        this.enhancementTable = enhancementTable;
        this.mapCenter = mapCenter;
        this.voidValue = false;
    }

    private InfiniteImage(boolean[][] mapCenter, boolean voidValue,  boolean[] enhancementTable){
        this.enhancementTable = enhancementTable;
        this.mapCenter = mapCenter;
        this.voidValue = voidValue;
    }

    public InfiniteImage enhance(){
        boolean[][] enhancedMap = new boolean[mapCenter.length+2][mapCenter[0].length+2];

        for (int i = 0; i < enhancedMap.length; i++) {
            for (int j = 0; j < enhancedMap[i].length; j++) {
                enhancedMap[i][j] = sample(i-1,j-1);
            }
        }

        boolean newVoidValue = voidValue ? enhancementTable[511] : enhancementTable[0];
        return new InfiniteImage(enhancedMap, newVoidValue, enhancementTable);
    }

    private boolean sample(int i, int j){
        int shift = 8;
        int lookId = 0;
        for (int k = i-1; k < i+2; k++) {
            for (int l = j-1; l < j+2; l++) {
                if(get(k,l)){
                    lookId += 1<<shift;
                }
                shift--;
            }
        }
        return enhancementTable[lookId];
    }

    public boolean get(int i, int j){
        if(i<0 || j < 0 || i >= mapCenter.length || j >= mapCenter[0].length) return voidValue;
        return mapCenter[i][j];
    }

    public long countValues(){
        long res = 0;
        for (boolean[] booleans : mapCenter) {
            for (boolean aBoolean : booleans) {
                if (aBoolean) res++;
            }
        }
        return res;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        String voidValueStr = voidValue ? "#" : ".";
        str.append(voidValueStr.repeat(mapCenter[0].length + 2)).append("\n");
        for (boolean[] booleans : mapCenter) {
            str.append(voidValueStr);
            for (boolean aBoolean : booleans) {
                str.append(aBoolean ? '#' : '.');
            }
            str.append(voidValueStr);
            str.append("\n");
        }
        str.append(voidValueStr.repeat(mapCenter[0].length + 2)).append("\n");

        return str.toString();
    }
}
