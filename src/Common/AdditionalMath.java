package Common;

public class AdditionalMath {
    private AdditionalMath() {
    }

    public static long factorial(int l) {
        long result = 1;

        for (long factor = 2; factor <= l; factor++) {
            result *= factor;
        }
        return result;
    }
}
