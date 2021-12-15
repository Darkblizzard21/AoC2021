package Day15.ShellPathfinder;

import Common.DoubleLinkedVertex;
import Common.Tuple;

import java.util.HashMap;
import java.util.Map;

public class DijkstraMetaData {
    private final Map<DoubleLinkedVertex<Integer>, Tuple<Integer, DoubleLinkedVertex<Integer>>> data;

    public DijkstraMetaData() {
        data = new HashMap<>();
    }

    public int getDistance(DoubleLinkedVertex<Integer> dlv) {
        if (data.containsKey(dlv)) {
            return data.get(dlv).x;
        }
        return Integer.MAX_VALUE;
    }

    public DoubleLinkedVertex<Integer> getPrevious(DoubleLinkedVertex<Integer> dlv){
        if (data.containsKey(dlv)) {
            return data.get(dlv).y;
        }
        return null;
    }

    public void setData(DoubleLinkedVertex<Integer> dlv, int distance, DoubleLinkedVertex<Integer> previous){
        data.put(dlv,new Tuple<>(distance,previous));
    }

    public void printParents(DoubleLinkedVertex<Integer> dlv){
        System.out.println("Value: " + getDistance(dlv));
        if(getPrevious(dlv) != null)
            printParents(getPrevious(dlv));
    }
}
