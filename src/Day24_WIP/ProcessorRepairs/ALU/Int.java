package Day24_WIP.ProcessorRepairs.ALU;

import java.util.Objects;

public class Int {
    public int value;

    public Int(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Int anInt = (Int) o;
        return value == anInt.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return ""+value;
    }
}
