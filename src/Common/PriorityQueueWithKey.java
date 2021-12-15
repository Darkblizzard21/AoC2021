package Common;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class PriorityQueueWithKey<E> {
    private final PriorityQueue<Tuple<Integer, E>> queue;
    private final HashMap<E, Tuple<Integer, E>> map;

    public PriorityQueueWithKey() {
        queue = new PriorityQueue<>(Comparator.comparingInt(i -> i.x));
        map = new HashMap<>();
    }

    public void set(int prio, E in) {
        if (in == null || prio < 0) throw new IllegalArgumentException();
        if (map.containsKey(in)) {
            var toUpdate = map.get(in);
            queue.remove(toUpdate);
        }
        var t = new Tuple<>(prio, in);
        queue.add(t);
        map.put(in,t);
    }


    public E poll() {
        var x = queue.poll();
        if (x == null) return null;
        map.remove(x.y);
        return x.y;
    }

    public int currentPriority(E in) {
        return map.containsKey(in) ? map.get(in).x : -1;
    }

    public boolean contains(E in) {
        return map.containsKey(in);
    }

    public boolean hasNext() {
        return !queue.isEmpty();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }


}
