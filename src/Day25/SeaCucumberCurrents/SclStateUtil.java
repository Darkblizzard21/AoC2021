package Day25.SeaCucumberCurrents;

public class SclStateUtil {
    private SclStateUtil(){}

    public static SclState fromChar(int i) {
        return fromChar((char) i);
    }

    public static SclState fromChar(char c) {
        switch (c) {
            case '>':
                return SclState.East;
            case 'v':
                return SclState.South;
            default:
                return SclState.None;
        }
    }
}
