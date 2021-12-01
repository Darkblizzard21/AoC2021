package Day01.DepthMeasurement;

public class SumRingBuffer {
    private final int[] buffer;
    private int head = 0;
    private int sum = 0;

    public SumRingBuffer(int size) {
        buffer = new int[size];
    }

    public int push(int i) {
        sum -= buffer[head];
        sum += i;

        buffer[head] = i;
        head = buffer.length - 1 <= head ? 0 : head + 1;

        return sum;
    }

    public int getSum() {
        return sum;
    }

}
