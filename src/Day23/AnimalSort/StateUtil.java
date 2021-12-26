package Day23.AnimalSort;

public class StateUtil {
    private StateUtil() {}

    public static char toChar(int i) {
        switch (i) {
            case 1:
                return 'A';
            case 10:
                return 'B';
            case 100:
                return 'C';
            case 1000:
                return 'D';
            default:
                throw new IllegalArgumentException("ARRRRRRRRRRG!");
        }
    }
}
