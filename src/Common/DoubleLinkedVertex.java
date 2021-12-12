package Common;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoubleLinkedVertex<?> that = (DoubleLinkedVertex<?>) o;
        return Objects.equals(neighbours, that.neighbours) && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(neighbours, content);
    }
}
