package Common;

public class Tuple<X, Y> {
    public final X x;
    public final Y y;

    public Tuple(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    public Tuple<X, Y> withX(X x) {
        return new Tuple<>(x, y);
    }

    public Tuple<X, Y> withY(Y y) {
        return new Tuple<>(x, y);
    }
}
