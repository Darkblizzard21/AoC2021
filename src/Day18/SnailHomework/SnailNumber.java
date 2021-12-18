package Day18.SnailHomework;

import java.util.Objects;
import java.util.Optional;
import java.util.Stack;

public class SnailNumber {
    private final SnailNumber[] children = new SnailNumber[2];
    private Optional<SnailNumber> parent;
    private boolean isNumber;
    private int number;

    public SnailNumber(int number, SnailNumber parent) {
        this.isNumber = true;
        this.number = number;
        this.parent = parent == null ? Optional.empty() : Optional.of(parent);
    }

    public SnailNumber(SnailNumber first, SnailNumber second) {
        this.isNumber = false;
        first.parent = Optional.of(this);
        this.children[0] = first;
        second.parent = Optional.of(this);
        this.children[1] = second;
        this.parent = Optional.empty();
        reduce();
    }

    private SnailNumber(SnailNumber parent) {
        this.isNumber = false;
        this.children[0] = null;
        this.children[1] = null;
        this.parent = parent == null ? Optional.empty() : Optional.of(parent);
    }

    public SnailNumber(SnailNumber first, SnailNumber second, SnailNumber parent) {
        this.isNumber = false;
        this.children[0] = first;
        this.children[1] = second;
        this.parent = parent == null ? Optional.empty() : Optional.of(parent);
        reduce();
    }

    public static SnailNumber FromString(String str) {
        var chars = str.substring(1, str.length() - 1).chars().toArray();

        SnailNumber curr = new SnailNumber(null);
        Stack<Integer> child = new Stack<>();
        child.push(0);
        for (int i : chars) {
            char c = (char) i;
            switch (c) {
                case '[':
                    var next = new SnailNumber(curr);
                    curr.children[child.peek()] = next;
                    curr = next;
                    child.push(0);
                    break;
                case ']':
                    curr = curr.getParent();
                    child.pop();
                    break;
                case ',':
                    var top = child.pop();
                    if (top != 0) throw new IllegalStateException();
                    child.push(1);
                    break;
                default:
                    curr.children[child.peek()] = new SnailNumber(Integer.parseInt("" + c), curr);
                    break;
            }
        }
        return curr.parent.isPresent() ? curr.getParent() : curr;
    }

    public SnailNumber add(SnailNumber other) {
        return new SnailNumber(this.copy(), other.copy());
    }

    public void reduce() {
        while (true){
            if(tryExplode(0)) continue;
            if(trySplit()) continue;
            break;
        }
    }

    private boolean tryExplode(int depth) {
        if (depth >= 4 && !isNumber && children[0].isNumber && children[1].isNumber) {
            explode();
            return true;
        }
        return !isNumber && (children[0].tryExplode(depth + 1) || children[1].tryExplode(depth + 1));
    }

    private boolean trySplit(){
        if (isNumber && number > 9) {
            //Split
            isNumber = false;
            this.children[0] = new SnailNumber(number / 2, this);
            this.children[1] = new SnailNumber(number / 2 + (number % 2), this);
            return true;
        }
        return !isNumber && (children[0].trySplit() || children[1].trySplit());

    }

    private void explode() {
        if (isNumber && !children[0].isNumber && !children[1].isNumber) throw new IllegalStateException();

        Optional<SnailNumber> leftNeighbour = Optional.empty();
        SnailNumber curr = this;
        while (curr.isFirstChild()) curr = curr.getParent();
        if (curr.parent.isPresent()) {
            curr = curr.getParent().children[0];
            while (!curr.isNumber) curr = curr.children[1];
            leftNeighbour = Optional.of(curr);
        }

        Optional<SnailNumber> rightNeighbour = Optional.empty();
        curr = this;
        while (curr.isSecondChild()) curr = curr.getParent();
        if (curr.parent.isPresent()) {
            curr = curr.getParent().children[1];
            while (!curr.isNumber) curr = curr.children[0];
            rightNeighbour = Optional.of(curr);
        }

        if (rightNeighbour.isEmpty() && leftNeighbour.isEmpty())
            throw new IllegalStateException();

        if (rightNeighbour.isPresent()) {
            var rn = rightNeighbour.get();
            rn.updateNumber(rn.number + children[1].number);
        }

        if (leftNeighbour.isPresent()) {
            var ln = leftNeighbour.get();
            ln.updateNumber(ln.number + children[0].number);
        }


        isNumber = true;
        number = 0;
        children[0] = null;
        children[1] = null;
    }

    public long magnitude() {
        return isNumber ? number : 3 * children[0].magnitude() + 2 * children[1].magnitude();
    }

    private boolean isFirstChild() {
        if (parent.isEmpty()) return false;
        return parent.get().children[0] == this;
    }

    private boolean isSecondChild() {
        if (parent.isEmpty()) return false;
        return parent.get().children[1] == this;
    }

    public SnailNumber getParent() {
        return parent.get();
    }

    private void updateNumber(int newNumber) {
        if (!isNumber) throw new IllegalStateException();
        number = newNumber;
    }

    @Override
    public String toString() {
        return isNumber ? "" + number : "[" + children[0].toString() + "," + children[1].toString() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SnailNumber that = (SnailNumber) o;
        return isNumber ?
                isNumber == that.isNumber && number == that.number :
                children[0].equals(that.children[0]) && children[1].equals(that.children[1]);
    }

    @Override
    public int hashCode() {
        return isNumber ? Objects.hash(number) : children[0].hashCode() * 31 + children[1].hashCode() * 73;
    }

    public SnailNumber copy(){
        if(isNumber || parent.isPresent()) throw new IllegalStateException();
        var copy = new SnailNumber(null);
        copy.children[0] = children[0].copyWithParent(copy);
        copy.children[1] = children[1].copyWithParent(copy);
        return copy;
    }

    private SnailNumber copyWithParent(SnailNumber parent){
        if(isNumber) return new SnailNumber(number, parent);

        var copy = new SnailNumber(parent);
        copy.children[0] = children[0].copyWithParent(copy);
        copy.children[1] = children[1].copyWithParent(copy);
        return copy;
    }
}
