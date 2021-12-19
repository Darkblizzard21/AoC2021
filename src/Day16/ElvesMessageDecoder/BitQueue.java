package Day16.ElvesMessageDecoder;

import java.util.*;


public class BitQueue {
    private final Queue<Byte> bits;
    private final Iterator<Character> input;
    private long consumedBits = 0;

    public BitQueue(List<Character> input){
        bits = new LinkedList<>();
        this.input = input.iterator();
    }

    public int getNext(int numbers) {
        if (numbers > 32 || numbers < 1) throw new IllegalArgumentException();
        int res = 0;
        for (int i = numbers - 1; i >= 0; i--) {
            if (isEmpty()) throw new NoSuchElementException();
            //noinspection ConstantConditions
            res += bits.poll() << i;
        }
        consumedBits += numbers;
        return res;
    }

    public boolean isEmpty() {
        if (bits.isEmpty()) {
            if (!input.hasNext()) return true;
            var bytes = interpretHex(input.next());
            for (int i = 3; i >= 0; i--) {
                bits.add((byte) (bytes >> i & 1));
            }
        }
        return false;
    }

    private byte interpretHex(char hex) {
        return Byte.parseByte(""+hex,16);
    }

    public long getConsumedBitsCount() {
        return consumedBits;
    }
}
