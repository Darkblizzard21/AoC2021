package Common;

public class WithKey<T> implements Comparable<WithKey<T>>{
    private int key;
    private final T value;
    public WithKey(T value, int key){
        this.value = value;
        this.key = key;
    }

    public void setKey(int key){
        this.key =key;
    }
    public int getKey() {
        return key;
    }

    public T getValue() {
        return value;
    }

    @Override
    public int compareTo(WithKey<T> o) {
        return key - o.key;
    }
}
