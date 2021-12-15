package Day15.ShellPathfinder;

import Common.DoubleLinkedVertex;
import Common.PriorityQueueWithKey;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Pathfinder extends PathfinderInputProvider {
    public int minimalPathRisk5(boolean loadExample){
        var x = load5(loadExample);
        return minimalPathRisk(x.x,x.y);
    }
    public int minimalPathRisk(boolean loadExample){
        var x = load(loadExample);
        return minimalPathRisk(x.x,x.y);
    }

    private int minimalPathRisk(DoubleLinkedVertex<Integer> start, DoubleLinkedVertex<Integer> end) {
        var metaData = new DijkstraMetaData();
        PriorityQueueWithKey<DoubleLinkedVertex<Integer>> queue = new PriorityQueueWithKey<>();

        metaData.setData(start,0,null);
        queue.set(0,start);

        while (!queue.isEmpty()){
            var u = queue.poll();

            for (var v : u.neighbours()){
                if(metaData.getDistance(v) < Integer.MAX_VALUE && !queue.contains(v)) continue;
                var alt = metaData.getDistance(u) + v.getContent();
                if(alt < metaData.getDistance(v)){
                    metaData.setData(v,alt,u);
                    queue.set(alt,v);
                }
            }
        }

        return metaData.getDistance(end);
    }
}
