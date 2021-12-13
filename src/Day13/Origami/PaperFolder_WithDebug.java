package Day13.Origami;

import Common.Tuple;

import java.util.Arrays;
import java.util.List;

public class PaperFolder_WithDebug extends PaperFolder {
    public static void visualize(List<Tuple<Integer,Integer>> in){
        visualize(in,false,false,0);
    }
    public static void visualize(List<Tuple<Integer,Integer>> in, boolean fold, boolean foldX, int foldInt){
        var ref = new Object() {
            int maxX = 0;
            int maxY = 0;
        };
        in.forEach(t -> {
            ref.maxX = Math.max(ref.maxX,t.x);
            ref.maxY = Math.max(ref.maxY,t.y);
        });
        int[][] map = new int[ref.maxY+1][ref.maxX+1];
        in.forEach(t->map[t.y][t.x] = 1);

        if(fold){
            if(foldX){
                for (int i = 0; i < map.length; i++) {
                    map[i][foldInt] = 2;
                }
            }
            else {
                Arrays.fill(map[foldInt], 2);
            }
        }

        for (int[] ints : map) {
            for (int anInt : ints) {
                switch (anInt) {
                    case 0:
                        System.out.print('.');
                        break;
                    case 1:
                        System.out.print('#');
                        break;
                    case 2:
                        System.out.print(foldX ? '|' : '-');
                        break;
                    default:
                        System.out.print('?');
                        break;
                }
            }
            System.out.println();
        }
    }
}
