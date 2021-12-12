package Common;

import java.util.LinkedList;
import java.util.List;

public class DoubleLinkedVertex<E> {
    private final List<DoubleLinkedVertex<E>> neighbours;
    private final E content;

    public DoubleLinkedVertex(E content){
        this.content = content;
        neighbours = new LinkedList<>();
    }

    public void addNeighbour(DoubleLinkedVertex<E> neighbour){
        neighbour.neighbours.add(this);
        neighbours.add(neighbour);
    }

    public Iterable<DoubleLinkedVertex<E>> neighbours() {
        return neighbours;
    }

    public E getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "DoubleLinkedVertex{" +
                " content=" + content.toString() +
                ", neighbourCount=" + neighbours.size() +
                '}';
    }
}
