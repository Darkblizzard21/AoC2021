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
        switch (hex) {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                return Byte.parseByte("" + hex);
            case 'A':
                return 10;
            case 'B':
                return 11;
            case 'C':
                return 12;
            case 'D':
                return 13;
            case 'E':
                return 14;
            case 'F':
                return 15;
            default:
                throw new InputMismatchException();
        }
    }

    public long getConsumedBitsCount() {
        return consumedBits;
    }
}
