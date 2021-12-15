package Common;

import Day10.SyntaxScoring.SyntaxUtility;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DoubleLinkedVertex<E> {
    public static long nextId = 0;
    private final long id;
    private final List<DoubleLinkedVertex<E>> neighbours;
    private final E content;

    public DoubleLinkedVertex(E content){
        id = nextId++;
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
        var res = id == that.id;
        assert !res || Objects.equals(content, that.content);
        return res;
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, neighbours.stream().map(x->x.id).collect(Collectors.toList()));
    }
}
