package Day15.ShellPathfinder;

import Common.DoubleLinkedVertex;
import Common.Tuple;

import java.util.HashMap;
import java.util.Map;

public class DijkstraMetaData<E> {
    private final Map<E, Tuple<Integer, E>> data;

    public DijkstraMetaData() {
        data = new HashMap<>();
    }

    public int getDistance(E dlv) {
        if (data.containsKey(dlv)) {
            return data.get(dlv).x;
        }
        return Integer.MAX_VALUE;
    }

    public E getPrevious(E dlv){
        if (data.containsKey(dlv)) {
            return data.get(dlv).y;
        }
        return null;
    }

    public void setData(E dlv, int distance, E previous){
        data.put(dlv,new Tuple<>(distance,previous));
    }

    public void printParents(E dlv){
        System.out.println("Value: " + getDistance(dlv) + " " + dlv);
        if(getPrevious(dlv) != null)
            printParents(getPrevious(dlv));
    }
}
